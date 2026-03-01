#!/bin/bash

# Single source of truth for project version
GRADLE_PROPERTIES="./gradle.properties"

# Initialize increment steps
step_major=1
step_minor=1
step_patch=1

SNAPSHOT=false

# Parse command line options
while [[ "$#" -gt 0 ]]; do
  key="$1"
  case $key in
    -M)
      INCREMENT="major"
      if [[ "$2" =~ ^[0-9]+$ ]]; then
        step_major="$2"
        shift
      fi
      ;;
    -m)
      INCREMENT="minor"
      if [[ "$2" =~ ^[0-9]+$ ]]; then
        step_minor="$2"
        shift
      fi
      ;;
    -p)
      INCREMENT="patch"
      if [[ "$2" =~ ^[0-9]+$ ]]; then
        step_patch="$2"
        shift
      fi
      ;;
    -s)
      SNAPSHOT=true
      ;;
    *)
      echo "Invalid option: $key. Use -M, -m, -p (with optional step), -s for snapshot."
      exit 2
      ;;
  esac
  shift
done

# Check if increment type was provided
if [ -z "$INCREMENT" ]; then
  echo "You must specify an increment type: -M, -m, or -p"
  exit 3
fi

replace_version() {
  local file="$1"
  local new_version="$2"

  if [[ "$OSTYPE" == "darwin"* ]]; then
    sed -i '' -E "s/^version=.*/version=$new_version/" "$file"
  else
    sed -i -E "s/^version=.*/version=$new_version/" "$file"
  fi
}

# Function to increment version
increment_version() {
  local file="$1"
  local current_version
  current_version=$(grep -E '^version=' "$file" | head -n 1 | cut -d'=' -f2- | tr -d '[:space:]')

  if [[ -z "$current_version" ]]; then
    echo "$file - version property not found."
    exit 4
  fi

  if [[ $current_version =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)(-[[:alnum:].-]+)?$ ]]; then
    local major=${BASH_REMATCH[1]}
    local minor=${BASH_REMATCH[2]}
    local patch=${BASH_REMATCH[3]}
    local new_version

    case $INCREMENT in
      major)
        new_version="$(($major + $step_major)).0.0"
        ;;
      minor)
        new_version="$major.$(($minor + $step_minor)).0"
        ;;
      patch)
        new_version="$major.$minor.$(($patch + $step_patch))"
        ;;
    esac

    # Append SNAPSHOT if required.
    if $SNAPSHOT; then
      new_version="$new_version-SNAPSHOT"
    fi

    replace_version "$file" "$new_version"
    echo "$file - Version updated from \"$current_version\" to \"$new_version\""
  else
    echo "$file - Version format not found or not in the expected format."
    exit 4
  fi
}

increment_version "$GRADLE_PROPERTIES"

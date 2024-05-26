#!/bin/bash

# Hardcoded file paths
ROOT_BUILD_GRADLE="./build.gradle.kts"
LIB_BUILD_GRADLE="./lib/build.gradle.kts"

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

# Function to increment version
increment_version() {
    current_version=$(grep 'version =' $1 | sed "s/.*version = \"\([^\"]*\)\".*/\1/")
    if [[ $current_version =~ ([0-9]+)\.([0-9]+)\.([0-9]+)(-SNAPSHOT)? ]]; then
        major=${BASH_REMATCH[1]}
        minor=${BASH_REMATCH[2]}
        patch=${BASH_REMATCH[3]}

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

        # Append SNAPSHOT if required
        if $SNAPSHOT; then
            new_version="$new_version-SNAPSHOT"
        fi

        # Update the file using macOS compatible sed command
        sed -i '' "s/version = \".*\"/version = \"$new_version\"/g" $1
        echo "$1 - Version updated from \"$current_version\" to \"$new_version\""
    else
        echo "$1 - Version format not found or not in the expected format."
        exit 4
    fi
}

# Increment version in Gradle files
increment_version $ROOT_BUILD_GRADLE
increment_version $LIB_BUILD_GRADLE
// swift-tools-version: 5.9

import PackageDescription
let package = Package(
    name: "AptosKit",
    platforms: [
        .iOS("18.0"),
        .macOS("15.0")
    ],
    products: [
        .library(
            name: "AptosKitLibrary",
            targets: ["ExportedKotlinPackages", "KotlinRuntimeSupport", "KotlinCoroutineSupport", "AptosKit", "XyzMcxrossKaptosKaptos", "XyzMcxrossKaptosKaptosEncryptedTransactions", "XyzMcxrossKaptosKaptosConfidentialAssets", "OrgJetbrainsKotlinxKotlinxCoroutinesCore", "OrgJetbrainsKotlinxKotlinxSerializationCore", "OrgJetbrainsKotlinxKotlinxSerializationJson", "KotlinStdlib"]
        )
    ],
    targets: [
        .binaryTarget(name: "AptosKitKotlin", url: "https://github.com/mcxross/kaptos/releases/download/v1.1.0-SNAPSHOT.1793729839.ga13d0365379d/AptosKitKotlin-1.1.0-SNAPSHOT.1793729839.ga13d0365379d.xcframework.zip", checksum: "1d587eb18478138b75156033438abfac5384602a1b1c89d753259c1e29b8442d"),
        .target(
            name: "ExportedKotlinPackages",
            dependencies: ["KotlinRuntime"]
        ),
        .target(
            name: "KotlinRuntimeSupport",
            dependencies: ["KotlinRuntimeSupportBridge", "KotlinRuntime"]
        ),
        .target(
            name: "KotlinRuntimeSupportBridge"
        ),
        .target(
            name: "KotlinCoroutineSupport",
            dependencies: ["KotlinRuntimeSupport", "KotlinCoroutineSupportBridge", "KotlinRuntime"]
        ),
        .target(
            name: "KotlinCoroutineSupportBridge"
        ),
        .target(
            name: "AptosKit",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "KotlinStdlib", "OrgJetbrainsKotlinxKotlinxCoroutinesCore", "XyzMcxrossKaptosKaptos", "XyzMcxrossKaptosKaptosConfidentialAssets", "XyzMcxrossKaptosKaptosEncryptedTransactions", "SharedBridge_AptosKit", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_AptosKit"
        ),
        .target(
            name: "XyzMcxrossKaptosKaptos",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "KotlinStdlib", "OrgJetbrainsKotlinxKotlinxSerializationCore", "OrgJetbrainsKotlinxKotlinxSerializationJson", "SharedBridge_XyzMcxrossKaptosKaptos", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_XyzMcxrossKaptosKaptos"
        ),
        .target(
            name: "XyzMcxrossKaptosKaptosEncryptedTransactions",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "XyzMcxrossKaptosKaptos", "SharedBridge_XyzMcxrossKaptosKaptosEncryptedTransactions", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_XyzMcxrossKaptosKaptosEncryptedTransactions"
        ),
        .target(
            name: "XyzMcxrossKaptosKaptosConfidentialAssets",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "KotlinStdlib", "OrgJetbrainsKotlinxKotlinxSerializationJson", "XyzMcxrossKaptosKaptos", "SharedBridge_XyzMcxrossKaptosKaptosConfidentialAssets", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_XyzMcxrossKaptosKaptosConfidentialAssets"
        ),
        .target(
            name: "OrgJetbrainsKotlinxKotlinxCoroutinesCore",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore"
        ),
        .target(
            name: "OrgJetbrainsKotlinxKotlinxSerializationCore",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationCore", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationCore"
        ),
        .target(
            name: "OrgJetbrainsKotlinxKotlinxSerializationJson",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "KotlinStdlib", "OrgJetbrainsKotlinxKotlinxSerializationCore", "SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationJson", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationJson"
        ),
        .target(
            name: "KotlinStdlib",
            dependencies: ["ExportedKotlinPackages", "KotlinCoroutineSupport", "KotlinRuntimeSupport", "KotlinBridges_KotlinStdlib", "KotlinRuntime"]
        ),
        .target(
            name: "KotlinBridges_KotlinStdlib"
        ),
        .target(
            name: "KotlinRuntime",
            dependencies: ["AptosKitKotlin"]
        )
    ]
)

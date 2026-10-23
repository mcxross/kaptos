// swift-tools-version: 6.0
import PackageDescription
import Foundation

let environment = ProcessInfo.processInfo.environment
let aptosDependency: Package.Dependency
let aptosIdentity: String
if let localPackage = environment["KAPTOS_SWIFT_PACKAGE"] {
    aptosDependency = .package(name: "AptosKitExport", path: localPackage)
    aptosIdentity = "AptosKitExport"
} else {
    let url = environment["APTOSKIT_PACKAGE_URL"] ?? "https://github.com/mcxross/kaptos.git"
    let version = Version(environment["APTOSKIT_PACKAGE_VERSION"] ?? "1.0.1")!
    if environment["APTOSKIT_PACKAGE_EXACT"] == "true" {
        aptosDependency = .package(url: url, exact: version)
    } else {
        aptosDependency = .package(url: url, from: version)
    }
    aptosIdentity = "kaptos"
}

let package = Package(
    name: "KaptosSwiftSample",
    platforms: [.macOS(.v15), .iOS(.v18)],
    dependencies: [aptosDependency],
    targets: [
        .executableTarget(name: "KaptosSample", dependencies: [
            .product(name: "AptosKitLibrary", package: aptosIdentity)
        ]),
        .testTarget(name: "KaptosConsumerTests", dependencies: [
            .product(name: "AptosKitLibrary", package: aptosIdentity)
        ])
    ],
    swiftLanguageModes: [.v6]
)

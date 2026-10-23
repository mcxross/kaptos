// swift-tools-version: 6.0
import PackageDescription
import Foundation

let package = Package(
    name: "KaptosSwiftSample",
    platforms: [.macOS(.v15), .iOS(.v18)],
    dependencies: [.package(name: "AptosKitExport", path: ProcessInfo.processInfo.environment["KAPTOS_SWIFT_PACKAGE"] ?? "../../build/distributions/AptosKit")],
    targets: [
        .executableTarget(name: "KaptosSample", dependencies: [
            .product(name: "AptosKitLibrary", package: "AptosKitExport")
        ]),
        .testTarget(name: "KaptosConsumerTests", dependencies: [
            .product(name: "AptosKitLibrary", package: "AptosKitExport")
        ])
    ],
    swiftLanguageModes: [.v6]
)

// swift-tools-version: 6.0
import PackageDescription
import Foundation

let package = Package(
    name: "KaptosSwiftSample",
    platforms: [.macOS(.v15), .iOS(.v18)],
    dependencies: [.package(name: "KaptosExport", path: ProcessInfo.processInfo.environment["KAPTOS_SWIFT_PACKAGE"] ?? "../../swift/build/swift-package/Debug")],
    targets: [
        .executableTarget(name: "KaptosSample", dependencies: [
            .product(name: "KaptosLibrary", package: "KaptosExport")
        ]),
        .testTarget(name: "KaptosConsumerTests", dependencies: [
            .product(name: "KaptosLibrary", package: "KaptosExport")
        ])
    ],
    swiftLanguageModes: [.v5]
)

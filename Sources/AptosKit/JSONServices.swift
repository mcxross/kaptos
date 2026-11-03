import Foundation

@MainActor
public struct AptosViews {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func call<T: Decodable>(_ function: String, typeArguments: [String] = [],
                                   arguments: [MoveValue] = [], ledgerVersion: UInt64? = nil,
                                   as type: T.Type, decoder: JSONDecoder = JSONDecoder()) async throws -> T {
        let json = try await callRaw(function, typeArguments: typeArguments,
                                     arguments: arguments, ledgerVersion: ledgerVersion)
        return try aptosCall { try AptosJSON.decode(type, from: json, using: decoder) }
    }

    public func callRaw(_ function: String, typeArguments: [String] = [],
                        arguments: [MoveValue] = [], ledgerVersion: UInt64? = nil) async throws -> String {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.views.call(function: function, typeArguments: typeArguments,
                arguments: arguments.map { try $0.bridge() },
                ledgerVersionDecimal: ledgerVersion.map(String.init))
        }
    }

    public func callRaw(_ function: String, typeArguments: [String] = [],
                        argumentsJSON: String, ledgerVersion: UInt64? = nil) async throws -> String {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.views.callRaw(function: function, typeArguments: typeArguments,
                argumentsJson: argumentsJSON, ledgerVersionDecimal: ledgerVersion.map(String.init))
        }
    }
}

@MainActor
public struct AptosIndexer {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func queryRaw(_ document: String, variablesJSON: String = "{}") async throws -> String {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.indexer.query(document: document, variablesJson: variablesJSON)
        }
    }

    public func query<T: Decodable>(_ document: String, variablesJSON: String = "{}", as type: T.Type,
                                    decoder: JSONDecoder = JSONDecoder()) async throws -> T {
        let json = try await queryRaw(document, variablesJSON: variablesJSON)
        return try aptosCall { try AptosJSON.decode(type, from: json, using: decoder) }
    }

    public func query<T: Decodable, Variables: Encodable>(_ document: String, variables: Variables,
                                    as type: T.Type, encoder: JSONEncoder = JSONEncoder(),
                                    decoder: JSONDecoder = JSONDecoder()) async throws -> T {
        let json = try aptosCall { String(decoding: try encoder.encode(variables), as: UTF8.self) }
        return try await query(document, variablesJSON: json, as: type, decoder: decoder)
    }
}

@MainActor
public struct AptosTables {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func itemRaw(handle: String, keyType: String, valueType: String, keyJSON: String,
                        ledgerVersion: UInt64? = nil) async throws -> String {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.tables.getItem(handle: handle, keyType: keyType, valueType: valueType,
                keyJson: keyJSON, ledgerVersionDecimal: ledgerVersion.map(String.init))
        }
    }

    public func item<T: Decodable, Key: Encodable>(handle: String, keyType: String, valueType: String,
                        key: Key, ledgerVersion: UInt64? = nil, as type: T.Type,
                        encoder: JSONEncoder = JSONEncoder(), decoder: JSONDecoder = JSONDecoder()) async throws -> T {
        let keyJSON = try aptosCall { String(decoding: try encoder.encode(key), as: UTF8.self) }
        let json = try await itemRaw(handle: handle, keyType: keyType, valueType: valueType,
                                    keyJSON: keyJSON, ledgerVersion: ledgerVersion)
        return try aptosCall { try AptosJSON.decode(type, from: json, using: decoder) }
    }
}

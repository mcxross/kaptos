@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
@_implementationOnly import SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore

@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlinx.coroutines.flow.Flow where Self : ExportedKotlinPackages.kotlinx.coroutines.flow.__Flow {
    /// Accepts the given [collector] and [emits][FlowCollector.emit] values into it.
    /// 
    /// This method can be used along with SAM-conversion of [FlowCollector]:
    /// ```
    /// myFlow.collect { value -> println("Collected $value") }
    /// ```
    /// 
    /// ### Method inheritance
    /// 
    /// To ensure the context preservation property, it is not recommended implementing this method directly.
    /// Instead, [AbstractFlow] can be used as the base type to properly ensure flow's properties.
    /// 
    /// All default flow implementations ensure context preservation and exception transparency properties on a best-effort basis
    /// and throw [IllegalStateException] if a violation was detected.
    public func collect(
        collector: any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector
    ) async throws -> Swift.Void {
        try await withKotlinContinuation { continuation, exception, cancellation in
            let _: Bool = kotlinx_coroutines_flow_Flow_collect__TypesOfArguments__anyU20ExportedKotlinPackages_kotlinx_coroutines_flow_FlowCollector__(self.__externalRCRef(), collector.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
        }
    }
}
extension ExportedKotlinPackages.kotlinx.coroutines.flow.Flow {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector where Self : ExportedKotlinPackages.kotlinx.coroutines.flow.__FlowCollector {
    /// Collects the value emitted by the upstream.
    /// This method is not thread-safe and should not be invoked concurrently.
    public func emit(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) async throws -> Swift.Void {
        try await withKotlinContinuation { continuation, exception, cancellation in
            let _: Bool = kotlinx_coroutines_flow_FlowCollector_emit__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
        }
    }
}
extension ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlinx.coroutines.flow.Flow, ExportedKotlinPackages.kotlinx.coroutines.flow.__Flow, KotlinCoroutineSupport.KotlinFlow where Wrapped : ExportedKotlinPackages.kotlinx.coroutines.flow._Flow {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector, ExportedKotlinPackages.kotlinx.coroutines.flow.__FlowCollector where Wrapped : ExportedKotlinPackages.kotlinx.coroutines.flow._FlowCollector {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlinx.coroutines.flow._Flow {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlinx.coroutines.flow._FlowCollector {
}
extension ExportedKotlinPackages.kotlinx.coroutines.flow {
    /// An asynchronous data stream that sequentially emits values and completes normally or with an exception.
    /// 
    /// _Intermediate operators_ on the flow such as [map], [filter], [take], [zip], etc are functions that are
    /// applied to the _upstream_ flow or flows and return a _downstream_ flow where further operators can be applied to.
    /// Intermediate operations do not execute any code in the flow and are not suspending functions themselves.
    /// They only set up a chain of operations for future execution and quickly return.
    /// This is known as a _cold flow_ property.
    /// 
    /// _Terminal operators_ on the flow are either suspending functions such as [collect], [single], [reduce], [toList], etc.
    /// or [launchIn] operator that starts collection of the flow in the given scope.
    /// They are applied to the upstream flow and trigger execution of all operations.
    /// Execution of the flow is also called _collecting the flow_  and is always performed in a suspending manner
    /// without actual blocking. Terminal operators complete normally or exceptionally depending on successful or failed
    /// execution of all the flow operations in the upstream. The most basic terminal operator is [collect], for example:
    /// 
    /// ```
    /// try {
    ///     flow.collect { value ->
    ///         println("Received $value")
    ///     }
    /// } catch (e: Exception) {
    ///     println("The flow has thrown an exception: $e")
    /// }
    /// ```
    /// 
    /// By default, flows are _sequential_ and all flow operations are executed sequentially in the same coroutine,
    /// with an exception for a few operations specifically designed to introduce concurrency into flow
    /// execution such as [buffer] and [flatMapMerge]. See their documentation for details.
    /// 
    /// The `Flow` interface does not carry information whether a flow is a _cold_ stream that can be collected repeatedly and
    /// triggers execution of the same code every time it is collected, or if it is a _hot_ stream that emits different
    /// values from the same running source on each collection. Usually flows represent _cold_ streams, but
    /// there is a [SharedFlow] subtype that represents _hot_ streams. In addition to that, any flow can be turned
    /// into a _hot_ one by the [stateIn] and [shareIn] operators, or by converting the flow into a hot channel
    /// via the [produceIn] operator.
    /// 
    /// ### Flow builders
    /// 
    /// There are the following basic ways to create a flow:
    /// 
    /// - [flowOf(...)][flowOf] functions to create a flow from a fixed set of values.
    /// - [asFlow()][asFlow] extension functions on various types to convert them into flows.
    /// - [flow { ... }][flow] builder function to construct arbitrary flows from
    /// sequential calls to [emit][FlowCollector.emit] function.
    /// - [channelFlow { ... }][channelFlow] builder function to construct arbitrary flows from
    /// potentially concurrent calls to the [send][kotlinx.coroutines.channels.SendChannel.send] function.
    /// - [MutableStateFlow] and [MutableSharedFlow] define the corresponding constructor functions to create
    /// a _hot_ flow that can be directly updated.
    /// 
    /// ### Flow constraints
    /// 
    /// All implementations of the `Flow` interface must adhere to two key properties described in detail below:
    /// 
    /// - Context preservation.
    /// - Exception transparency.
    /// 
    /// These properties ensure the ability to perform local reasoning about the code with flows and modularize the code
    /// in such a way that upstream flow emitters can be developed separately from downstream flow collectors.
    /// A user of a flow does not need to be aware of implementation details of the upstream flows it uses.
    /// 
    /// ### Context preservation
    /// 
    /// The flow has a context preservation property: it encapsulates its own execution context and never propagates or leaks
    /// it downstream, thus making reasoning about the execution context of particular transformations or terminal
    /// operations trivial.
    /// 
    /// There is only one way to change the context of a flow: the [flowOn][Flow.flowOn] operator
    /// that changes the upstream context ("everything above the `flowOn` operator").
    /// For additional information refer to its documentation.
    /// 
    /// This reasoning can be demonstrated in practice:
    /// 
    /// ```
    /// val flowA = flowOf(1, 2, 3)
    ///     .map { it + 1 } // Will be executed in ctxA
    ///     .flowOn(ctxA) // Changes the upstream context: flowOf and map
    /// 
    /// // Now we have a context-preserving flow: it is executed somewhere but this information is encapsulated in the flow itself
    /// 
    /// val filtered = flowA // ctxA is encapsulated in flowA
    ///    .filter { it == 3 } // Pure operator without a context yet
    /// 
    /// withContext(Dispatchers.Main) {
    ///     // All non-encapsulated operators will be executed in Main: filter and single
    ///     val result = filtered.single()
    ///     myUi.text = result
    /// }
    /// ```
    /// 
    /// From the implementation point of view, it means that all flow implementations should
    /// only emit from the same coroutine context.
    /// This constraint is efficiently enforced by the default [flow] builder.
    /// The [flow] builder should be used if the flow implementation does not start any coroutines.
    /// Its implementation prevents most of the development mistakes:
    /// 
    /// ```
    /// val myFlow = flow {
    ///     // GlobalScope.launch { // is prohibited
    ///     // launch(Dispatchers.IO) { // is prohibited
    ///     // withContext(CoroutineName("myFlow")) { // is prohibited
    ///     emit(1) // OK
    ///     coroutineScope {
    ///         emit(2) // OK -- still the same coroutine
    ///     }
    /// }
    /// ```
    /// 
    /// Use [channelFlow] if the collection and emission of a flow are to be separated into multiple coroutines.
    /// It encapsulates all the context preservation work and allows you to focus on your
    /// domain-specific problem, rather than invariant implementation details.
    /// It is possible to use any combination of coroutine builders from within [channelFlow].
    /// 
    /// If you are looking for performance and are sure that no concurrent emits and context jumps will happen,
    /// the [flow] builder can be used alongside a [coroutineScope] or [supervisorScope] instead:
    /// - Scoped primitive should be used to provide a [CoroutineScope].
    /// - Changing the context of emission is prohibited, no matter whether it is `withContext(ctx)` or
    /// a builder argument (e.g. `launch(ctx)`).
    /// - Collecting another flow from a separate context is allowed, but it has the same effect as
    /// applying the [flowOn] operator to that flow, which is more efficient.
    /// 
    /// ### Exception transparency
    /// 
    /// When `emit` or `emitAll` throws, the Flow implementations must immediately stop emitting new values and finish with an exception.
    /// For diagnostics or application-specific purposes, the exception may be different from the one thrown by the emit operation,
    /// suppressing the original exception as discussed below.
    /// If there is a need to emit values after the downstream failed, please use the [catch][Flow.catch] operator.
    /// 
    /// The [catch][Flow.catch] operator only catches upstream exceptions, but passes
    /// all downstream exceptions. Similarly, terminal operators like [collect][Flow.collect]
    /// throw any unhandled exceptions that occur in their code or in upstream flows, for example:
    /// 
    /// ```
    /// flow { emitData() }
    ///     .map { computeOne(it) }
    ///     .catch { ... } // catches exceptions in emitData and computeOne
    ///     .map { computeTwo(it) }
    ///     .collect { process(it) } // throws exceptions from process and computeTwo
    /// ```
    /// The same reasoning can be applied to the [onCompletion] operator that is a declarative replacement for the `finally` block.
    /// 
    /// All exception-handling Flow operators follow the principle of exception suppression:
    /// 
    /// If the upstream flow throws an exception during its completion when the downstream exception has been thrown,
    /// the downstream exception becomes superseded and suppressed by the upstream exception, being a semantic
    /// equivalent of throwing from `finally` block. However, this doesn't affect the operation of the exception-handling operators,
    /// which consider the downstream exception to be the root cause and behave as if the upstream didn't throw anything.
    /// 
    /// Failure to adhere to the exception transparency requirement can lead to strange behaviors which make
    /// it hard to reason about the code because an exception in the `collect { ... }` could be somehow "caught"
    /// by an upstream flow, limiting the ability of local reasoning about the code.
    /// 
    /// Flow machinery enforces exception transparency at runtime and throws [IllegalStateException] on any attempt to emit a value,
    /// if an exception has been thrown on previous attempt.
    /// 
    /// ### Reactive streams
    /// 
    /// Flow is [Reactive Streams](http://www.reactive-streams.org/) compliant, you can safely interop it with
    /// reactive streams using `Flow.asPublisher` and `Publisher.asFlow` from `kotlinx-coroutines-reactive` module.
    /// 
    /// ### Not stable for inheritance
    /// 
    /// **The `Flow` interface is not stable for inheritance in 3rd party libraries**, as new methods
    /// might be added to this interface in the future, but is stable for use.
    /// 
    /// Use the `flow { ... }` builder function to create an implementation, or extend [AbstractFlow].
    /// These implementations ensure that the context preservation property is not violated, and prevent most
    /// of the developer mistakes related to concurrency, inconsistent flow dispatchers, and cancellation.
    public protocol Flow: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlinx.coroutines.flow._Flow, KotlinCoroutineSupport.KotlinFlow {
        /// Accepts the given [collector] and [emits][FlowCollector.emit] values into it.
        /// 
        /// This method can be used along with SAM-conversion of [FlowCollector]:
        /// ```
        /// myFlow.collect { value -> println("Collected $value") }
        /// ```
        /// 
        /// ### Method inheritance
        /// 
        /// To ensure the context preservation property, it is not recommended implementing this method directly.
        /// Instead, [AbstractFlow] can be used as the base type to properly ensure flow's properties.
        /// 
        /// All default flow implementations ensure context preservation and exception transparency properties on a best-effort basis
        /// and throw [IllegalStateException] if a violation was detected.
        func collect(
            collector: any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector
        ) async throws -> Swift.Void
    }
    /// [FlowCollector] is used as an intermediate or a terminal collector of the flow and represents
    /// an entity that accepts values emitted by the [Flow].
    /// 
    /// This interface should usually not be implemented directly, but rather used as a receiver in a [flow] builder when implementing a custom operator,
    /// or with SAM-conversion.
    /// Implementations of this interface are not thread-safe.
    /// 
    /// Example of usage:
    /// 
    /// ```
    /// val flow = getMyEvents()
    /// try {
    ///     flow.collect { value ->
    ///         println("Received $value")
    ///     }
    ///     println("My events are consumed successfully")
    /// } catch (e: Throwable) {
    ///     println("Exception from the flow: $e")
    /// }
    /// ```
    public protocol FlowCollector: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlinx.coroutines.flow._FlowCollector {
        /// Collects the value emitted by the upstream.
        /// This method is not thread-safe and should not be invoked concurrently.
        func emit(
            value: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) async throws -> Swift.Void
    }
    @objc(_ExportedKotlinPackages_kotlinx_coroutines_flow_Flow)
    public protocol _Flow {
    }
    @objc(_ExportedKotlinPackages_kotlinx_coroutines_flow_FlowCollector)
    public protocol _FlowCollector {
    }
    public protocol __Flow: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __FlowCollector: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public static func flowCollector(
        function: @escaping ((any KotlinRuntimeSupport._KotlinBridgeable)?) async throws -> Swift.Void
    ) -> any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlinx_coroutines_flow_FlowCollector__TypesOfArguments__U28Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_U2920asyncU20throwsU202D_U20Swift_Void__(Unmanaged.passRetained((function as (Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) async throws -> Swift.Void) as AnyObject).toOpaque()), conformsTo: ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector.Type.self) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector
    }
}
@_cdecl("OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___")
package func OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<Swift.Error>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: KotlinRuntimeSupport.swiftError(fromKotlinThrowable: KotlinRuntime.KotlinBase.__createClassWrapper(externalRCRef: res)); } }())
    return { _result; return true }()
}

@_cdecl("OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__")
package func OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Bool) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Void) -> Swift.Void)({ _1; return () }())
    return { _result; return true }()
}

@_cdecl("OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_async_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
package func OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_callee_async_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let __continuation: (Swift.Void) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__(pointerToBlock.__externalRCRef(), { _1; return true }()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) async throws -> Swift.Void)({ switch _1 { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    }
    return true
}

@_cdecl("kotlinx_coroutines_flow_FlowCollector_emit__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift")
package func kotlinx_coroutines_flow_FlowCollector_emit__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ value: Swift.UnsafeMutableRawPointer?, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector.Type.self) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector
    let __continuation: (Swift.Void) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__(pointerToBlock.__externalRCRef(), { _1; return true }()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.emit(value: { switch value { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    }
    return true
}

@_cdecl("kotlinx_coroutines_flow_Flow_collect__TypesOfArguments__anyU20ExportedKotlinPackages_kotlinx_coroutines_flow_FlowCollector____reverse_swift")
package func kotlinx_coroutines_flow_Flow_collect__TypesOfArguments__anyU20ExportedKotlinPackages_kotlinx_coroutines_flow_FlowCollector____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ collector: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlinx.coroutines.flow.Flow.Type.self) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.Flow
    let __continuation: (Swift.Void) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__(pointerToBlock.__externalRCRef(), { _1; return true }()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { OrgJetbrainsKotlinxKotlinxCoroutinesCore_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.collect(collector: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: collector, conformsTo: ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector.Type.self) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.FlowCollector)
    }
    return true
}


package eu.compassresearch.core.interpreter.api.behaviour;
//package eu.compassresearch.core.interpreter.core;
//
//import java.util.List;
//import java.util.Stack;
//
//import org.overture.ast.node.INode;
//import org.overture.interpreter.runtime.Context;
//
//import eu.compassresearch.core.interpreter.Pair;
//import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
//import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateEvent;
//import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
//import eu.compassresearch.core.interpreter.api.events.TraceEvent;
//import eu.compassresearch.core.interpreter.api.events.TraceObserver;
//import eu.compassresearch.core.interpreter.utility.events.EventSourceHandler;
//
//class RestorePoint {
//
//	public RestorePoint(Stack<Pair<INode,Context>> executionStack,
//			CmlBehaviour 				parent,
//			List<CmlBehaviour> 		children,
//			CmlBehaviorState 					state,
//			CmlSupervisorEnvironment 			env,
//			CmlAlphabet 						hidingAlphabet,
//			CmlTrace 							trace,
//			EventSourceHandler<CmlBehaviorStateObserver,CmlBehaviorStateEvent>  stateEventhandler,
//			EventSourceHandler<TraceObserver,TraceEvent>  traceEventHandler)
//	{
//		this.executionStack = executionStack;
//		this.parent = parent;
//		this.children = children;
//		this.state = state;
//		this.env = env;
//		this.hidingAlphabet = hidingAlphabet;
//		this.trace = trace;
//		this.stateEventhandler = stateEventhandler;
//		this.traceEventHandler = traceEventHandler;
//	}
//	
//	public final Stack<Pair<INode,Context>> 		executionStack;
//	public final CmlBehaviour 				parent;
//	public final List<CmlBehaviour> 		children;
//	public final CmlBehaviorState 					state;
//	public final CmlSupervisorEnvironment 			env;
//	public final CmlAlphabet 						hidingAlphabet;
//	public final CmlTrace 							trace;
//	public final EventSourceHandler<CmlBehaviorStateObserver,CmlBehaviorStateEvent>  stateEventhandler;
//	public final EventSourceHandler<TraceObserver,TraceEvent>  traceEventHandler;
//}

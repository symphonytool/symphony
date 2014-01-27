package eu.compassresearch.core.analysis.modelchecker.graphBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Event;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Tau;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Tock;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Divergence;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Skip;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Stop;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.GraphResult;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.LinkedListTransition;
//import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.PathCollection;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.Constructor;


public class GraphBuilder {
	
	private static int STATE_NUMBER = 1;
	
	Utilities util;
	public GraphBuilder() {
		util = new Utilities();
	}
	
	
	public LinkedList<Object> loadLTSObjects(String filePath) throws IOException{
		InputStream is = Utilities.class.getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bfReader = new BufferedReader(isr);
		LinkedList<Object> result = new LinkedList<Object>();
		try {
			String line = "";
			while((line = bfReader.readLine() )!= null){
				Constructor c = util.determineConstructor(line);
				if(c != null){
					if (c.equals(Constructor.GivenProc ) ||
						c.equals(Constructor.ProcDef ) ||
						c.equals(Constructor.Transition ) )  {
						result.add(util.createObject(line));					
					}
				}
			}
				
		} finally{
			bfReader.close();
		}
		return result;
	}
	
	public LinkedList<Object> loadLTSObjects(StringBuilder content) throws IOException{
		ByteArrayInputStream is = new ByteArrayInputStream(content.toString().getBytes());
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bfReader = new BufferedReader(isr);
		LinkedList<Object> result = new LinkedList<Object>();
		try {
			
			String line = "";
			while((line = bfReader.readLine() )!= null){
				Constructor c = Utilities.determineConstructor(line);
				if(c != null){
					if (c.equals(Constructor.GivenProc ) ||
						c.equals(Constructor.ProcDef ) ||
						c.equals(Constructor.Transition ) )  {
						Object o = Utilities.createObject(line);
						result.add(o);					
					}
				}
			}
				
		} catch(Exception ex){
			ex.printStackTrace();
		} finally{
			bfReader.close();
			isr.close();
			is.close();
		}
		return result;
	}

	public String generateDot(String input, String property) throws IOException{
		STATE_NUMBER = 1; //resets the state number
		StringBuilder result = new StringBuilder();
		LinkedList<Object> objects = this.loadLTSObjects(input);
		GraphResult graph = new GraphResult();
		if(property.equals(Utilities.DEADLOCK)){
			graph = this.shortestPathToDeadlock(objects);
		}else if(property.equals(Utilities.LIVELOCK)){
			graph = this.shortestPathToLivelock(objects);
			
		}else if(property.equals(Utilities.NONDETERMINISM)){
			graph = this.shortestPathToNondeterminism(objects);
			
		}
		writeDotToBuffer(result, graph);
		return result.toString();
	}
	public String generateDot(StringBuilder input, String property) throws IOException{
		STATE_NUMBER = 1; //resets the state number
		StringBuilder result = new StringBuilder();
		LinkedList<Object> objects = this.loadLTSObjects(input);
		GraphResult graph = new GraphResult();
		if(property.equals(Utilities.DEADLOCK)){
			graph = this.shortestPathToDeadlock(objects);
		}else if(property.equals(Utilities.LIVELOCK)){
			graph = this.shortestPathToLivelock(objects);
		}else if(property.equals(Utilities.NONDETERMINISM)){
			graph = this.shortestPathToNondeterminism(objects);
		}
		writeDotToBuffer(result, graph);
		
		return result.toString();
	}
	
	private void writeDotToBuffer(StringBuilder result, GraphResult graph) {
		result.append( "digraph { \n ");
		result.append( "rankdir=\"LR\";\n ");
		
		for (State state : graph.getStates()) {
			result.append( state.toString() + " [shape=" + state.getShape() + ",style=filled,fillcolor="+state.getFillCollor()+",label= " +"\"" + state.getNumber() + "\"]\n" );
			
		}
		
		result.append( "\n");
		for (Transition trans : graph.getTransitions()) {
			result.append(((Transition) trans).getSourceState().toString()
					+ "->" + ((Transition) trans).getTargetState().toString() 
					+ " [label= " + "\""
					+ ((Transition) trans).getEvent().toString() + "\""); 
			if(trans.getEvent().equals(new Tock())){
				result.append(",fontcolor=\"#D80032\", color=\"#D80032\"");
			}
			
			result.append("]\n");
			
		}
		
		result.append("\n }");
	}
		
	private String getMainProcessName(LinkedList<Object> objects){
		String result = "";
		for (Object object : objects) {
			if (object instanceof GivenProc) {
				result = ((GivenProc) object).getName();
			}
		}
		return result;
	}
	
	private Process getMainProcessBody(LinkedList<Object> objects) {
		String givenProcName = this.getMainProcessName(objects);
		Process mainProcessBody = null;
		
		for (Object object : objects) {
			if ((object instanceof ProcDef)) {
				if (((ProcDef) object).getName().equals(givenProcName)) {
					mainProcessBody = ((ProcDef) object).getProcess();
				}
			} 

		}
		return mainProcessBody;
	}
	

	private LinkedList<Transition> filterTransitions(LinkedList<Object> objetos){
		LinkedList<Transition> result = new LinkedList<Transition>();
		for (Object o : objetos) {
			if(o instanceof Transition){
				result.add((Transition)o);
			}
		}
		return result;
	}
	
	public State getInitialState(LinkedList<Object> objects){
		State initialState = null;
		
		String mainProcessName = this.getMainProcessName(objects);
		Process mainProcessBody = this.getMainProcessBody(objects);
		for (Object object : objects) {
			if(object instanceof Transition){
				if(((Transition) object).getSourceState().getProcess().equals(mainProcessBody)){
					initialState = ((Transition) object).getSourceState();
				}
			}
		}
		if(initialState == null){
			initialState = new State(new NullBinding(),mainProcessBody);
		}
		return initialState;
	}
	
	
	
	private LinkedList<Transition> getAllTransitionsFrom(LinkedList<Transition> transitions, State sourceState){
		LinkedList<Transition> result = new LinkedList<Transition>();
		
		for (Transition transition : transitions) {
			if(transition.getSourceState().equals(sourceState)){
				result.add(transition);
			}
		}
		
		return result;
	}
	
	
	private LinkedList<Transition> getAllTransitionsTo(LinkedList<Transition> transitions, State targetState){
		LinkedList<Transition> result = new LinkedList<Transition>();
		
		for (Transition transition : transitions) {
			if(transition.getTargetState().equals(targetState)){
				result.add(transition);
			}
		}
		
		return result;
	}
	
	public GraphResult shortestPathToDeadlock(LinkedList<Object> objects){
		GraphResult result = new GraphResult();
		
		//this removes all procdefs and givenproc from the list 
		State initialState = this.getInitialState(objects);
		initialState.setShape("doublecircle");
		
		State realFinalState = null; //this will be the real deadlock state found in the graph
		
		State basicDeadlock = new State(new NullBinding(),new Stop());
		//if(initialState.getProcess() instanceof Stop){
		if(initialState.getProcess().isDeadlock()){
			basicDeadlock = initialState;
			realFinalState = basicDeadlock;
		}
	
		State basicTermination = new State(new NullBinding(),new Skip());
		if(initialState.getProcess() instanceof Skip){
			basicTermination = initialState;
			realFinalState = basicTermination;
		}
		
		LinkedList<Transition> transitions = this.filterTransitions(objects);
		
		ArrayDeque<State> toVisit = new ArrayDeque<State>();
		LinkedList<State> visitedStates = new LinkedList<State>();
		LinkedList<Transition> visitedTransitions = new LinkedList<Transition>(); 
		
		toVisit.addLast(initialState);
		
		boolean deadlockFound = false;

		while(toVisit.size() > 0 && !deadlockFound){
			//System.out.println("Initial size of to visit states: " + toVisit.size());
			State current = toVisit.pollFirst();
			current.setVisited(true);
			if(!visitedStates.contains(current)){
				visitedStates.addLast(current);
			}
			//System.out.println("Size of visited states: " + visitedStates.size());
			//visitedStates.add(current);
			
			LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(transitions, current);
			if(transitionsFrom.size() == 0){ //if there is outgoing transition
				if (current.equals(basicDeadlock)){ //if the state is basic deadlock
					realFinalState = current;
					deadlockFound = true;
					break;
				}else if(current.equals(basicTermination)) { //if the state is termination
					//do nothing
				} else {
					//a new deadlock situation (not default) was found
					realFinalState = current;
					deadlockFound = true;
					break;
				}
			}else if (transitionsFrom.size() == 1){
				//the existing transition must be tock
				Transition transition = transitionsFrom.getFirst();
				
				if(transition.getEvent().equals(new Tock())){
					if(transition.getSourceState().equals(transition.getTargetState())){
						//this is a deadlock
						realFinalState = transition.getSourceState();
						deadlockFound = true;
						break;
					}else{
						//is not a deadlock
						State target = transition.getTargetState();
						
						if(!visitedStates.contains(target)){
							//if(!toVisit.contains(target)){
								toVisit.addLast(target);
							//}
						}
						if(!visitedTransitions.contains(transition)){
							visitedTransitions.add(transition);
						}
					}
				}else{
					//there is one one transition different from tock
					State target = transition.getTargetState();
					
					if(!visitedStates.contains(target)){
						if(!toVisit.contains(target)){
							toVisit.addLast(target);
						}
					}
					if(!visitedTransitions.contains(transition)){
						visitedTransitions.add(transition);
					}
				}
			}else{
				//there are outgoing transitions. they can be tock transitions
				for (Transition transition : transitionsFrom) {
					State target = transition.getTargetState();
					
					if(!visitedStates.contains(target)){
						if(!toVisit.contains(target)){
							toVisit.addLast(target);
						}
					}
					if(!visitedTransitions.contains(transition)){
						visitedTransitions.add(transition);
					}
					//if (current.equals(basicDeadlock)){ //if the state is basic deadlock
					//	realFinalState = current;
					//}else{ //if the state is not basic deadlock but has only one tock transition to itself
					//	if(current.equals(target) && transition.getEvent().equals(new Tock())){
					//		realFinalState = current;
					//		deadlockFound = true;
					//		break;
					//	}
					//}
				
				}
			}
			//System.out.println("Final size of to visit states: " + toVisit.size());
		}
		
		//at the end of this loop visitedTransitions contains transitions used to  
		//reach a deadlock. We must build the path from initial state to the real deadlock state.
		//we use a reverse path construction: from the deadlock state to the first state.  
		LinkedList<Transition> reversePath = new LinkedList<Transition>();
		//realFinalState = this.getDeadlockState(visitedStates, visitedTransitions);
		buildReversePath(reversePath, visitedTransitions, initialState, realFinalState);
		//at the end, reversePath contains the transitions of our interest to build the graph
		//but we need to build a single path from initial state to deadlock
		LinkedList<Transition> singlePath = new LinkedList<Transition>();
		buildSinglePath(reversePath, singlePath, initialState);
		
		//singlePath contains the single path with numbered states. we separate states and transitions
		LinkedList<State> pathStates = this.getSourceStates(singlePath);
		
		LinkedList<State> targetStates = this.getTargetStates(singlePath);
		for (State state : targetStates) {
			if(!pathStates.contains(state)){
				pathStates.add(state);
			}
		}
		
		if(pathStates.size() == 0){
			if(realFinalState != null) //TODO
				realFinalState.setFillCollor(Utilities.DEADLOCK_STATE_COLOUR);
			pathStates.add(realFinalState);
		} else{
			State deadlock = this.getDeadlockState(targetStates, singlePath);
			if(deadlock != null){
				deadlock.setFillCollor(Utilities.DEADLOCK_STATE_COLOUR);
			}
		}
		result.setStates(pathStates);
		result.setTransitions(singlePath);
		
		
		return result;
	}
	public boolean containsStateByString(LinkedList<State> states, State state){
		for (State s : states) {
			if(s.toString().equals(state.toString())){
				return true;
			}
		}
		return false;
	}
	
	
	private State getDeadlockState(LinkedList<State> targetStates, LinkedList<Transition> singlePath){
		State result = null;
		for (State state : targetStates) {
			LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(singlePath, state);
			if(transitionsFrom.size() == 0){
				result = state;
				break;
			} else if (transitionsFrom.size() == 1){
				Transition t = transitionsFrom.getFirst();
				if(t.getSourceState().equals(t.getTargetState()) && t.getEvent().equals(new Tock())){
					result = state;
					break;
				}
			}
		}
		return result;
	}
	
	public GraphResult shortestPathToNondeterminism(LinkedList<Object> objects){
		GraphResult result = new GraphResult();
		
		//this removes all procdefs and givenproc from the list 
		State initialState = this.getInitialState(objects);
		initialState.setShape("doublecircle");
		
		State nonDeterministicState = null; 
		
		LinkedList<Transition> transitions = this.filterTransitions(objects);
		ArrayDeque<State> toVisit = new ArrayDeque<State>();
		LinkedList<Transition> visitedTransitions = new LinkedList<Transition>(); 
		
		toVisit.addLast(initialState);
		while(toVisit.size() > 0){
			State current = toVisit.pollFirst();
			current.setVisited(true);
			if(this.isNondeterministic(current, transitions)){
				nonDeterministicState = current;
				nonDeterministicState.setFillCollor(Utilities.NONDETERMINISTIC_STATE_COLOUR);
				break;
			}else{
				LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(transitions, current);
				for (Transition transition : transitionsFrom) {
					State target = transition.getTargetState();
					
					if(!target.isVisited()){
						toVisit.addLast(target);
					}
					if(!visitedTransitions.contains(transition)){
						visitedTransitions.add(transition);
					}
				}
			}
		}
		
		//at the end of this loop visitedTransitions contains transitions used to  
		//reach the first nondeterminist state and the replicated transitions from it.
		// We must build the path from initial state to the nondeterministic state plus replicated transitions.
		LinkedList<Transition> reversePath = new LinkedList<Transition>();
		buildReversePath(reversePath, visitedTransitions, initialState, nonDeterministicState);
		
		LinkedList<Transition> singlePath = new LinkedList<Transition>();
		buildSinglePath(reversePath, singlePath, initialState);
		
		//adds the replicated transitions from the nondeterministic state
		int[] indexes = this.indexesOfReplicatedTransition(nonDeterministicState, transitions);
		for (int i : indexes) {
			Transition current = transitions.get(i);
			current.setSourceState(nonDeterministicState);
			current.getTargetState().setNumber(STATE_NUMBER++);
			singlePath.add(transitions.get(i));
		}
		
		//singlePath contains the single path with numbered states. we separate states and transitions
		LinkedList<State> pathStates = this.getSourceStates(singlePath);
		
		LinkedList<State> targetStates = this.getTargetStates(singlePath);
		for (State state : targetStates) {
			if(!pathStates.contains(state)){
				pathStates.add(state);
			}
		}
		
		
		result.setStates(pathStates);
		result.setTransitions(singlePath);
		
		
		return result;
	}
	
	private HashMap<Event,LinkedList<Transition>> createMap(LinkedList<Transition> transitions){
		HashMap<Event,LinkedList<Transition>> map = new HashMap<Event,LinkedList<Transition>>();
		for (Transition transition : transitions) {
			if(map.containsKey(transition.getEvent())){
				map.get(transition.getEvent()).add(transition);
			}else{
				LinkedList<Transition> list = new LinkedList<Transition>();
				list.add(transition);
				map.put(transition.getEvent(), list);
			}
			
		}
		return map;
	}
	
	private boolean isSameEvents(State firststate,State secondState, LinkedList<Transition> transitions){
		boolean result = false;
		LinkedList<Event> firstEvents = this.getAllEvents(this.getAllTransitionsFrom(transitions, firststate));
		LinkedList<Event> secondEvents = this.getAllEvents(this.getAllTransitionsFrom(transitions, secondState));
		
		for (Event event : firstEvents) {
			if(!event.equals(new Tau())){
				result = result || !secondEvents.contains(event);
				if(result){
					return result;
				}
			}
		}
		
		for (Event event : secondEvents) {
			if(!event.equals(new Tau())){
				result = result || !firstEvents.contains(event);
				if(result){
					return result;
				}
			}
		}
		return result;		
	}
	
	private boolean isNondeterministic(State state, LinkedList<Transition> transitions){
		boolean result = false;
		LinkedList<Transition> transFrom = this.getAllTransitionsFrom(transitions, state);
		HashMap<Event,LinkedList<Transition>> map = this.createMap(transFrom);

		for (Event key : map.keySet()) {
			if(map.get(key).size() > 1){
				LinkedList<Transition> transSameEvent = map.get(key);
				LinkedList<State> targetStates = this.getTargetStates(transSameEvent);
				
				for (int i = 0; i < targetStates.size() - 1; i++) {
					for (int j = i+1; j < targetStates.size(); j++) {
						result = isSameEvents(targetStates.get(i), targetStates.get(j), transitions);
						if(result){
							return result;
						}
					}
				}
			}
		}

			return result;
	}
	private LinkedList<Event> getAllEvents(LinkedList<Transition> transitions) {
		LinkedList<Event> result = new LinkedList<Event>();
		for (Transition transition : transitions) {
			result.add(transition.getEvent());
		}
		
		return result;
	}
/*	
	private void DFS(PathCollection paths, LinkedList<Transition> transitions, State fromState){
		if(!fromState.isVisited()){
			visit(fromState);
			LinkedList<Transition> outTrans = this.getAllTransitionsFrom(transitions, fromState);
			paths.replicatePaths(fromState, outTrans.size());
			for (Transition transition : outTrans) {
				LinkedList<State> neighbors = getTargetStates(outTrans);
				for (State state : neighbors) {
					if(state.isVisited()){ //if cycle
						paths.addTransition(transition);
					}else{ //not cycle
						paths.addTransition(transition);
						DFS(paths,transitions,state);
					}
				}
			}
		}
	}
	*/
	private void visit(State state){
		state.setVisited(true);
	}
	
	private boolean pathHasCycle(LinkedList<Transition> path){
		boolean result = false;
		
		for (Transition transition : path) {
			State sourceState = transition.getSourceState();
			for (Transition transition2 : path) {
				State targetState = transition.getTargetState();
				if(sourceState.equals(targetState)){
					result = true;
					return result;
				}
			}
		}
		
		return result;
	}
	
	
	private int[] indexesOfReplicatedTransition(State state, LinkedList<Transition> transitions){
		int[] indexesOf = new int[0];
		
		LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(transitions, state);
		LinkedListTransition outTransitions = new LinkedListTransition(transitions);
		
		for (int i = 0; i < outTransitions.size(); i++) {
			Transition current = transitionsFrom.get(i);
			indexesOf = outTransitions.indexesOf(current);
			if(indexesOf.length > 1){
				break;
			}
		}
		
		return indexesOf;
	}
	
	private void getCicles(State state, LinkedList<Transition> transitions,
			LinkedList<Transition> visitedTrans,
			LinkedList<LinkedList<Transition>> cicles) {
		
		this.filterLiveLockTransitions(transitions);
		LinkedList<Transition> auxList = new LinkedList<Transition>();
		LinkedList<Transition> transFrom = this.getAllTransitionsFrom(transitions, state);
		if (transFrom.size() > 0) {
			for (Transition transition : transFrom) {
				if (isCicle(visitedTrans, transition.getTargetState())) {// IS
					auxList.addAll(visitedTrans);
				//	auxList.add(transition); //  transition that closes the cycle
					if(!cicles.contains(auxList))
					cicles.add(auxList);
				} else {
					visitedTrans.add(transition);
					getCicles(transition.getTargetState(), transitions,visitedTrans, cicles);
					visitedTrans.remove(transition);
				}
			}

		}else{
			if(state.equals(new Divergence())){
				auxList.addAll(visitedTrans);
				if(!cicles.contains(auxList))
					cicles.add(auxList);
				return;
			}
		}
	}
	
	private void filterLiveLockTransitions(LinkedList<Transition> transitions){
		for (Transition transition : transitions) {
			LinkedList<Transition> transFrom = this.getAllTransitionsFrom(transitions, transition.getSourceState());
			if (transFrom.size() == 0) {
				transitions.remove(transition);
			}
		}
	}
	
	private boolean isCicleOfTau(LinkedList<Transition> cicle){
		boolean result = true;
		for (Transition transition : cicle) {
			if (!transition.getEvent().equals(new Tau())) {
				return !result;
			}
			
		}
		return result;
		
	}
		
	
	private boolean isCicle(LinkedList<Transition> transitions,State current){
		boolean result = false;
		if(transitions.size() != 0){
			for (int i = 0; i < transitions.size(); i++) {
				result = transitions.get(i).getTargetState().equals(current) || transitions.getFirst().getSourceState().equals(current);
				if(result)
					return result  ;
			}
		}
		
		return result;
		
	}
	
	private LinkedList<Transition> getShortPath(LinkedList<LinkedList<Transition>> cicles) {
		LinkedList<Transition> result = cicles.getFirst(); 
		for (LinkedList<Transition> cicle : cicles) {
			if (cicle.size() > result.size()) {
				result = cicle;
			}
		}
		
		return result;
	}

	public GraphResult shortestPathToLivelock(LinkedList<Object> objects){
		GraphResult result = new GraphResult();
		State initialState = this.getInitialState(objects);
		LinkedList<LinkedList<Transition>> cicles = new LinkedList<LinkedList<Transition>>();
		LinkedList<Transition> transitions = this.filterTransitions(objects);		
		LinkedList<Transition> visitedTransitions = new LinkedList<Transition>(); 
		this.getCicles(initialState, transitions, visitedTransitions, cicles);
		LinkedList<LinkedList<Transition>> ciclesOfTau = new LinkedList<LinkedList<Transition>>();
		
		
		for (LinkedList<Transition> cicle : cicles) {
			if (this.isCicleOfTau(cicle)) {
				ciclesOfTau.add(cicle);
			}
		}
		
		LinkedList<Transition> shortCicle = this.getShortPath(ciclesOfTau); 	
		initialState = shortCicle.getFirst().getSourceState();
		initialState.setShape("doublecircle");
		State finalState = shortCicle.getLast().getTargetState();
		finalState.setFillCollor(Utilities.LIVELOCK_STATE_COLOUR);
		
		LinkedList<Transition> reversePath = new LinkedList<Transition>();
		buildReversePathToLiveLock(reversePath, shortCicle, initialState,finalState);
		//but we need to build a single path from initial state to deadlock
		LinkedList<Transition> singlePath = new LinkedList<Transition>();
		buildSinglePath(reversePath, singlePath, initialState);
		
		//singlePath contains the single path with numbered states. we separate states and transitions
		LinkedList<State> pathStates = this.getSourceStates(singlePath);
		if(pathStates.size() == 0){
			shortCicle.getLast().getTargetState().setNumber(STATE_NUMBER++);
		}
		LinkedList<State> targetStates = this.getTargetStates(singlePath);
		for (State state : targetStates) {
			if(!pathStates.contains(state)){
				pathStates.add(state);
			}
		}
		
		if(pathStates.size() == 0){
			pathStates.add(shortCicle.getLast().getTargetState());
		}
		result.setStates(pathStates);
		result.setTransitions(singlePath);
		
		return result;
	}
	
/*
	public void getLivelockPaths(LinkedList<Object> objects){
		PathCollection paths = new PathCollection();
		State initialState = this.getInitialState(objects);
		initialState.setShape("doublecircle");

		LinkedList<Transition> transitions = this.filterTransitions(objects);
		this.DFS(paths, transitions, initialState);

		int k = 0;
	}
	
*/	
	private void buildReversePathToLiveLock(LinkedList<Transition> reversePath,
			LinkedList<Transition> transitions, State initialState,
			State finalState) {

		State currentState = finalState;
		if (currentState != null) {
			if (transitions.size() > 0) {
				LinkedList<Transition> transitionsTo = this.getAllTransitionsTo(transitions, finalState);
				for (Transition transition : transitionsTo) {
					reversePath.add(transition);
					transitions.remove(transition);
				}
				LinkedList<State> previousStates = this.getSourceStates(transitionsTo);
				for (State previousState : previousStates) {


					buildReversePathToLiveLock(reversePath, transitions, initialState,previousState);
				}
			}
		}
	}

	private void buildReversePath(	LinkedList<Transition> reversePath, 
									LinkedList<Transition> transitions, 
									State initialState, State finalState){

		State currentState = finalState;
		if(currentState != null){
			if(!currentState.equals(initialState)){
				LinkedList<Transition> transitionsTo = this.getAllTransitionsTo(transitions,finalState);
				for (Transition transition : transitionsTo) {
					reversePath.add(transition);
					transitions.remove(transition);
				}
				LinkedList<State> previousStates = this.getSourceStates(transitionsTo);
				for (State previousState : previousStates) {
					buildReversePath(reversePath, transitions, initialState, previousState);
				}
			}
		}
	}
	
	
	
	private void buildSinglePath(LinkedList<Transition> transitions,LinkedList<Transition> singlePath, State initialState){
		
		LinkedList<Transition> currentTransitions = this.getAllTransitionsFrom(transitions,initialState);
		initialState.setNumber(STATE_NUMBER++);
		
		if(currentTransitions.size() > 0){
			
			Transition currentTransition = currentTransitions.getFirst();
			currentTransition.setSourceState(initialState);
			singlePath.add(currentTransition);
			State newInitialState = currentTransition.getTargetState();
			currentTransitions.remove(currentTransition);
			
			for (Transition transition : currentTransitions) {
				if(transition.getEvent().equals(new Tock())){
					if(initialState.equals(transition.getTargetState())){
						transition.setSourceState(initialState);
						singlePath.add(transition);
					}
				}
			}
			
			transitions.remove(currentTransition);
			transitions.removeAll(currentTransitions); //for optimization
						
			buildSinglePath(transitions, singlePath, newInitialState);
		}else{ //initialState is a state without outgoing transitions = deadlock
			
			
		}
		//at the end singlePath contains a single path
	}
	
	private LinkedList<State> getSourceStates(LinkedList<Transition> transitions){
		LinkedList<State> result = new LinkedList<State>();
		for (Transition transition : transitions) {
			result.add(transition.getSourceState());
		}
		return result;
	}
	
	private LinkedList<State> getTargetStates(LinkedList<Transition> transitions){
		LinkedList<State> result = new LinkedList<State>();
		for (Transition transition : transitions) {
			result.add(transition.getTargetState());
		}
		return result;
	}
	
	
	public static void main(String[] args) throws IOException {
		GraphBuilder gb = new GraphBuilder();
		//String filePath = "/examples/action-nondet.facts.ND.txt";
		//String filePath = "/examples/NDet.facts.txt";
		//String filePath = "/examples/NDet3.facts.txt";
		//String filePath = "/examples/operation.facts.txt";
		//String filePath = "/examples/simple-state.facts.txt";
		//String filePath = "/examples/dphils.facts.txt";
		//String filePath = "/examples/chaos.facts.txt";
		//String filePath = "/examples/dphils-d.facts.txt";
		//String filePath = "D:\\COMPASS\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources\\action-wait.facts";
		//String filePath = "/examples/phils-and-fork0.facts.txt";
		//String filePath = "D:\\COMPASS\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources\\timed-interrupt2.facts";
		//String filePath = "D:\\COMPASS\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources\\simpler-register.facts";
		String filePath = "D:\\COMPASS\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources\\DPhils.facts";
		
		//String filePath = "/examples/NDet2.facts.txt";
		//String filePath = "/examples/Livelock2.facts.txt";
		//String filePath = "/examples/Livelock1.facts.txt";
		//String filePath = "/examples/action-internal-choice.facts.D.txt";
		//String filePath = "/examples/Livelock.facts.txt";
		StringBuilder facts = Utilities.readScriptFromAbsoluteFile(filePath);
		//eu.compassresearch.core.analysis.modelchecker.visitors.Utilities.readScriptFromFile(filePath);
		String dotCode = gb.generateDot(facts,Utilities.DEADLOCK);
		System.out.println(dotCode);
	}
	
	
//	public static void main(String[] args) throws IOException {
	//	Buffer bf = new Buffer();
	//	List<Object> objs = bf.listObjects("TC01.D.facts.txt");
	//	System.out.println(bf.firstState(objs));
//	}
	

}


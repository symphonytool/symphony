package eu.compassresearch.core.analysis.modelchecker.graphBuilder;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Tau;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Skip;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Stop;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.GraphResult;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.Utilities.Constructor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.ReverbType;


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
				Constructor c = util.determineConstructor(line);
				if(c != null){
					if (c.equals(Constructor.GivenProc ) ||
					    c.equals(Constructor.ProcDef ) ||
					    c.equals(Constructor.Transition ) )  {
						Object o = util.createObject(line);
						result.add(o);					
					}
				}
			}
				
		} finally{
			bfReader.close();
			isr.close();
			is.close();
		}
		return result;
	}

	
	public String generateDot(String input) throws IOException{
		STATE_NUMBER = 1; //resets the state number
		StringBuilder result = new StringBuilder();
		
		LinkedList<Object> objects = this.loadLTSObjects(input);
		GraphResult graph = this.shortestPathToDeadlock(objects);
		
		writeDotToBuffer(result, graph);
		return result.toString();
		
	}

	public String generateDot(StringBuilder formulaOutput) throws IOException{
		STATE_NUMBER = 1; //resets the state number
		StringBuilder result = new StringBuilder();
		LinkedList<Object> objects = this.loadLTSObjects(formulaOutput);
		GraphResult graph = this.shortestPathToDeadlock(objects);
		
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
					+ ((Transition) trans).getEvent().toString() + "\"]" + "\n");
			
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
			initialState = new State(1,new NullBinding(),mainProcessName,mainProcessBody);
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
		
		State basicDeadlock = new State(0, new NullBinding(),"Deadlock", new Stop());
		//if(initialState.getProcess() instanceof Stop){
		if(initialState.getProcess().isDeadlock()){
			basicDeadlock = initialState;
			realFinalState = basicDeadlock;
		}
	
		State basicTermination = new State(0, new NullBinding(),"Skip", new Skip());
		if(initialState.getProcess() instanceof Skip){
			basicTermination = initialState;
			realFinalState = basicTermination;
		}
		
		//it is better to see transitions as Transition objects
		//LinkedList<Transition> transitions = new LinkedList<Transition>();
		//for (Object current : objects) {
		//	transitions.add((Transition)current);
		//}
		LinkedList<Transition> transitions = this.filterTransitions(objects);
		
		ArrayDeque<State> toVisit = new ArrayDeque<State>();
		//LinkedList<State> visitedStates = new LinkedList<State>();
		LinkedList<Transition> visitedTransitions = new LinkedList<Transition>(); 
		
		toVisit.addLast(initialState);
		while(toVisit.size() > 0){
			State current = toVisit.pollFirst();
			current.setVisited(true);
			//visitedStates.add(current);
			LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(transitions, current);
			if(transitionsFrom.size() == 0){ //if there is outgoing transition
				if (current.equals(basicDeadlock)){ //if the state is basic deadlock
					realFinalState = basicDeadlock;
					break;
				}else if(current.equals(basicTermination)) { //if the state is termination
					//do nothing
				} else {
					//a new deadlock situation (not default) was found
					realFinalState = current;
					break;
				}
			}else{
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
		//reach a deadlock. We must build the path from initial state to the real deadlock state.
		//we use a reverse path construction: from the deadlock state to the first state.  
		LinkedList<Transition> reversePath = new LinkedList<Transition>();
		buildReversePath(reversePath, visitedTransitions, initialState, realFinalState);
		//at the end, reversePath contains the transitions of our interest to build the graph
		//but we need to build a single path from initial state to deadlock
		LinkedList<Transition> singlePath = new LinkedList<Transition>();
		buildSinglePath(reversePath, singlePath, initialState);
		
		//singlePath contains the single path with numbered states. we separate states and transitions
		LinkedList<State> pathStates = this.getSourceStates(singlePath);
		//if(pathStates.size() == 0){
			//realFinalState.setNumber(STATE_NUMBER++);
			//realFinalState.setFillCollor("\"#FF9696\"");
		//}
		LinkedList<State> targetStates = this.getTargetStates(singlePath);
		for (State state : targetStates) {
			if(!pathStates.contains(state)){
				pathStates.add(state);
			}
		}
		
		if(pathStates.size() == 0){
			realFinalState.setFillCollor("\"#FF9696\"");
			pathStates.add(realFinalState);
		}
		result.setStates(pathStates);
		result.setTransitions(singlePath);
		
		
		return result;
	}
	
	
	public GraphResult shortestPathToLivelock(LinkedList<Object> objects){
GraphResult result = new GraphResult();
		
		//this removes all procdefs and givenproc from the list 
		State initialState = this.getInitialState(objects);
		initialState.setShape("doublecircle");
		
		State tauFinalState = null; 
		//this will be the real deadlock state found in the graph		
		//it is better to see transitions as Transition objects
		//LinkedList<Transition> transitions = new LinkedList<Transition>();
		//for (Object current : objects) {
		//	transitions.add((Transition)current);
		//}
		LinkedList<Transition> transitions = this.filterTransitions(objects);
		
		ArrayDeque<State> toVisit = new ArrayDeque<State>();
		//LinkedList<State> visitedStates = new LinkedList<State>();
		LinkedList<Transition> visitedTransitions = new LinkedList<Transition>(); 
		LinkedList<Transition> cicleTransitions = new LinkedList<Transition>();
		toVisit.addLast(initialState);
		while(toVisit.size() > 0){
			State current = toVisit.pollFirst();
			current.setVisited(true);
			//visitedStates.add(current);
			LinkedList<Transition> transitionsFrom = this.getAllTransitionsFrom(transitions, current);
			if(transitionsFrom.size() == 0){
				//então eh deadLock
				break;				
			}
			for (Transition transition : transitionsFrom) {
				State target = transition.getTargetState();
				if (transition.getEvent().equals(new Tau())) { // if the transition is basiclivelock
					if (transition.getSourceState().equals(transition.getTargetState())){// tauFinalState = basicLivelock;
						tauFinalState = transition.getTargetState();
						if(cicleTransitions.getLast().getTargetState().equals(current)){
							if (!cicleTransitions.contains(transition)){
								cicleTransitions.add(transition);
							}
						}else{
							if (!visitedTransitions.contains(transition)) 
								visitedTransitions.add(transition);
						}
						break;
					} else { // if is a cicle
						if (!cicleTransitions.contains(transition)) {
							if(cicleTransitions.size() == 0){
								cicleTransitions.add(transition);							
							}else if(cicleTransitions.getLast().getTargetState().equals(current)){
								cicleTransitions.add(transition);
							}
						}
					}
					
				}

				if (!target.isVisited()) {
					toVisit.addLast(target);
				}
				if (!visitedTransitions.contains(transition) && !cicleTransitions.contains(transition)) {
					visitedTransitions.add(transition);
				}

			}
			
		}
		
		//reversePath 
		//simglePath
		
		return result;
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
			transitions.remove(currentTransition);
			transitions.removeAll(currentTransitions); //for optimization
						
			buildSinglePath(transitions, singlePath, newInitialState);
		}else{ //initialState is a state withour outgoing transitions = deadlock
			initialState.setFillCollor("\"#FF9696\"");
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
		String filePath = "/examples/action-vardecl.facts.D.txt";
		String facts = "GivenProc(\"ImmediateDeadlock\")\n ProcDef(\"ImmediateDeadlock\",nopar,Stop)\n reachable(State(0,nBind,\"ImmediateDeadlock\",Stop))\n State(0,nBind,\"ImmediateDeadlock\",Stop)";
		//String dotCode = gb.generateDot(new StringBuilder(facts));
		String dotCode = gb.generateDot(filePath);
		System.out.println(dotCode);
	}
	
	
//	public static void main(String[] args) throws IOException {
	//	Buffer bf = new Buffer();
	//	List<Object> objs = bf.listObjects("TC01.D.facts.txt");
	//	System.out.println(bf.firstState(objs));
//	}
	

}


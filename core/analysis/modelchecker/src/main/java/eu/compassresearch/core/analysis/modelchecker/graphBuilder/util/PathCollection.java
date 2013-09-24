package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.State;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;

public class PathCollection  {
	
	protected LinkedList<LinkedList<Transition>> paths;
	
	public PathCollection() {
		paths = new LinkedList<LinkedList<Transition>>();
	}

	public void addTransition(Transition transition){
		for (LinkedList<Transition> currentList : paths) {
			Transition last =  currentList.getLast();
			if(last.getTargetState().equals(transition.getSourceState())){
				currentList.add(transition);
				break;
			}
		}
		//if there is no previous path to add a new transition then creates a new path
		LinkedList<Transition> newPath = new LinkedList<Transition>();
		newPath.add(transition);
		paths.add(newPath);
	}
	
	public void replicatePaths(State lastState, int times){
		if(times > 1){
			LinkedList<LinkedList<Transition>> newPaths = new LinkedList<LinkedList<Transition>>();
			for (LinkedList<Transition> path : paths) {
				Transition last =  path.getLast();
				if(last.getTargetState().equals(lastState)){
					for (int i = 0; i < times; i++) {
						LinkedList<Transition> pathCopy = new LinkedList<Transition>();
						pathCopy.addAll(path);
						newPaths.add(pathCopy);
					}
					break;
				}
			}
			paths.addAll(newPaths);
		}
		
	}
}

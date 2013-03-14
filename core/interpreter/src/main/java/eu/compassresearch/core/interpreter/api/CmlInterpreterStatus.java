package eu.compassresearch.core.interpreter.api;

public enum CmlInterpreterStatus {

	RUNNING, 			//Fires when just before interpreter starts to run
	TERMINATED,         //Fires when the interpreter successfully terminates
	DEADLOCKED          //Fires when the interpreter has deadlocked
}

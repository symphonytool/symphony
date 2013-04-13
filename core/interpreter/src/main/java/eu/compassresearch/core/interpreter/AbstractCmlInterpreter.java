package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.types.PType;
import org.overture.interpreter.debug.DBGPReader;
import org.overture.interpreter.runtime.Breakpoint;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.SourceFile;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexException;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;


@SuppressWarnings("serial")
abstract class AbstractCmlInterpreter implements CmlInterpreter {

		
	protected EventSourceHandler<CmlInterpreterStatusObserver, InterpreterStatusEvent> statusEventHandler =
			new EventSourceHandler<CmlInterpreterStatusObserver, InterpreterStatusEvent>(this, 
					new EventFireMediator<CmlInterpreterStatusObserver, InterpreterStatusEvent>() {

				@Override
				public void fireEvent(
						CmlInterpreterStatusObserver observer,
						Object source, InterpreterStatusEvent event) {
					
					observer.onStatusChanged(source, event);

				}
			});

	protected CmlSupervisorEnvironment currentSupervisor;

	@Override
	public File getDefaultFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CmlSupervisorEnvironment getCurrentSupervisor()
	{
		return currentSupervisor;
	}
	
//	@Override
//	public void init(DBGPReader dbgp) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void traceInit(DBGPReader dbgp) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Value execute(String line, DBGPReader dbgp) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public Value evaluate(String line, Context ctxt) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Breakpoint> getBreakpoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceLine(LexLocation src) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceLine(File file, int line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceLine(File file, int line, String sep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceFile getSourceFile(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<File> getSourceFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PAction findStatement(File file, int lineno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PExp findExpression(File file, int lineno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value findGlobal(LexNameToken name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Breakpoint setTracepoint(PAction stmt, String trace)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Breakpoint setTracepoint(PExp exp, String trace) throws LexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Breakpoint setBreakpoint(PAction stmt, String condition)
			throws LexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Breakpoint setBreakpoint(PExp exp, String condition)
			throws LexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Breakpoint clearBreakpoint(int bpno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearBreakpointHits() {
		// TODO Auto-generated method stub

	}

	@Override
	public PType findType(String typename) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public EventSource<CmlInterpreterStatusObserver> onStatusChanged() {

		return statusEventHandler;
	}

}

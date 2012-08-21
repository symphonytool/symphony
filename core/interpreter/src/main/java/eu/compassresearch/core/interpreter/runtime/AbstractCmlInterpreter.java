package eu.compassresearch.core.interpreter.runtime;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.overture.interpreter.debug.DBGPReader;
import org.overture.interpreter.runtime.Breakpoint;
import org.overture.interpreter.runtime.SourceFile;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexException;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.lex.LexLocation;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.typechecker.Environment;


public abstract class AbstractCmlInterpreter extends
		QuestionAnswerAdaptor<Context, Value> implements CmlInterpreter {

	protected List<PSource> sourceForest;
	
	@Override
	public Context getInitialContext() {
		
		//collect all the top 
		
		
		return null;
	}

	@Override
	public Environment getGlobalEnvironment() {
	
		return EnvironmentBuilder.BuildGlobalEnvironment(sourceForest);
	}

	@Override
	public String getDefaultName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDefaultFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultName(String name) throws Exception {
		
		
	}

	@Override
	public void init(DBGPReader dbgp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void traceInit(DBGPReader dbgp) {
		// TODO Auto-generated method stub

	}

	@Override
	public Value execute(String line, DBGPReader dbgp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value execute(File file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

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

}

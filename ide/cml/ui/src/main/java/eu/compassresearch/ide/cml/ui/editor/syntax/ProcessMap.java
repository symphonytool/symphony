package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ide.cml.ui.editor.syntax.DefinitionMap.DefinitionHandler;

public class ProcessMap {

	public interface ProcessHandler {
		public List<Wrapper<? extends INode>> makeEntries(PProcess proc);
	}

	public static final Map<Class<?>, ? extends ProcessHandler> PROCESS_MAP = createMap();

	private static Map<Class<?>, ? extends ProcessHandler> createMap() {
		Map<Class<?>, ProcessHandler> map = new HashMap<Class<?>, ProcessHandler>();
		map.put(AStateProcess.class, new AStateProcessHandler());
		map.put(AExternalChoiceProcess.class, new AExternalChoiceProcessHandler());
		map.put(ASynchronousParallelismProcess.class, new ASynchronousParallelismProcessHandler());
		map.put(AGeneralisedParallelismProcess.class, new AGeneralisedParallelismProcessHandler());
		
		//TODO-ldc Add a ton more remaining processes :(
		return Collections.unmodifiableMap(map);
	}

	public static ProcessHandler getDelegate(Class<?> cls) {
		ProcessHandler r = PROCESS_MAP.get(cls);
		if (r == null)
			System.err.println("No delegate found for process class "
					+ cls.getCanonicalName());
		return r;
	}
	
	private static class AGeneralisedParallelismProcessHandler implements ProcessHandler{

	    @Override
	    public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
		List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
		AGeneralisedParallelismProcess gpp = (AGeneralisedParallelismProcess) proc;
		AInstantiationProcess lp = (AInstantiationProcess) gpp.getLeft();
		AInstantiationProcess rp = (AInstantiationProcess) gpp.getRight();
		StringBuilder sb = new StringBuilder();
		sb.append(lp.getProcessName().name+" ");
		sb.append("[|{| ... |}|]");
		sb.append(" "+rp.getProcessName().name);
		r.add(Wrapper.newInstance(proc, sb.toString()));
		return r;
	    }

	    private String prettyPrint(PExp chansetExpression) {
		StringBuilder sb = new StringBuilder();
		if (chansetExpression instanceof AEnumChansetSetExp){
		    AEnumChansetSetExp temp = (AEnumChansetSetExp) chansetExpression;
		    sb.append("[|{| ");
		    for (LexIdentifierToken lit :  temp.getIdentifiers()){
			sb.append(lit.toString()+", ");
		    }
		    sb.delete(sb.length()-2, sb.length());
		    sb.append(" |}|]");
		}
		return sb.toString();
	    }
	    
	}
	
	private static class ASynchronousParallelismProcessHandler implements ProcessHandler{

	    @Override
	    public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
		List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
		ASynchronousParallelismProcess spp = (ASynchronousParallelismProcess) proc;
		AInstantiationProcess lp = (AInstantiationProcess) spp.getLeft();
		AInstantiationProcess rp = (AInstantiationProcess) spp.getRight();
		StringBuilder sb = new StringBuilder();
		sb.append(lp.getProcessName().name);
		sb.append(" || ");
		sb.append(rp.getProcessName().name);
		r.add(Wrapper.newInstance(proc, sb.toString()));
		return r;
	    }
	    
	}
	
	private static class AExternalChoiceProcessHandler implements ProcessHandler{

	private static class AExternalChoiseProcessHandler implements
			ProcessHandler {

		@Override
		public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
			List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
			AExternalChoiceProcess ecp = (AExternalChoiceProcess) proc;
			AInstantiationProcess lp = (AInstantiationProcess) ecp.getLeft();
			AInstantiationProcess rp = (AInstantiationProcess) ecp.getRight();
			StringBuilder sb = new StringBuilder();
			sb.append(lp.toString());
			sb.append(" [] ");
			sb.append(rp.toString());
			r.add(Wrapper.newInstance(proc, sb.toString()));
			return r;
		}

	}

	private static class AStateProcessHandler implements ProcessHandler {

		public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
			List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
			AStateProcess asp = (AStateProcess) proc;
			r.add(Wrapper.newInstance(asp.getAction(), "@ "
					+ asp.getAction().toString()));
			for (PDefinition pdef : asp.getDefinitionParagraphs()) {
				DefinitionHandler dh = DefinitionMap.getDelegate(pdef
						.getClass());
				if (dh != null)
					r.addAll(dh.extractSubdefinition(pdef));
			}
			return r;
		}

	}

}

package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ide.cml.ui.editor.syntax.DefinitionMap.DefinitionHandler;

public class ProcessMap {

	public interface ProcessHandler {
		public List<Wrapper<? extends INode>> makeEntries(PProcess proc);
	}

	public static final Map<Class<?>, ? extends ProcessHandler> PROCESS_MAP = createMap();

	private static Map<Class<?>, ? extends ProcessHandler> createMap() {
		Map<Class<?>,ProcessHandler> map = new HashMap<Class<?>, ProcessHandler>();
		map.put(AStateProcess.class, new AStateProcessHandler());
		return Collections.unmodifiableMap(map);
	}

	public static Map<Class<?>, ? extends ProcessHandler> getProcessMap() {
		return PROCESS_MAP;
	}

	private static String makeInitActionLine(PAction action) {
		StringBuilder sb = new StringBuilder();
		// TODO breakdown by action types
		sb.append(action.toString());
		return sb.toString();
	}

	private static class AStateProcessHandler implements ProcessHandler {

		public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
		    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
			AStateProcess asp = (AStateProcess) proc;
			r.add(Wrapper.newInstance(asp.getAction(), "@ " + makeInitActionLine(asp.getAction())));				
			for (PDefinition pdef : asp.getDefinitionParagraphs()) {
				DefinitionHandler dh = DefinitionMap.getDelegate(pdef.getClass());						
				if (dh != null)
					r.addAll(dh.extractSubdefinition(pdef));
			}
			return r;
		}

	}

}

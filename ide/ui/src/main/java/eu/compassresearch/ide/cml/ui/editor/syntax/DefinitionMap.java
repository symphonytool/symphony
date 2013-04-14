package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;

public class DefinitionMap {

	public interface DefinitionHandler {
		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef);
	}

	private static final Map<Class<?>, ? extends DefinitionHandler> DEFINITION_MAP = createMap();

	public static DefinitionHandler getDelegate(Class<?> cls) {
		DefinitionHandler r = DEFINITION_MAP.get(cls);
		if (r == null)
			System.err.println("No delegate found for definition class "
					+ cls.getCanonicalName());
		return r;
	}

	private static Map<Class<?>, ? extends DefinitionHandler> createMap() {
		Map<Class<?>, DefinitionHandler> map = new HashMap<Class<?>, DefinitionHandler>();
		map.put(AActionsDefinition.class, new AActionsDefinitionHandler());
		map.put(AValuesDefinition.class, new AValuesDefinitionHandler());
		map.put(AFunctionsDefinition.class,
				new AFunctionParagraphDefinitionHandler());
		map.put(ATypesDefinition.class, new ATypesParagraphDefinitionHandler());
		map.put(AOperationsDefinition.class,
				new AOperationParagraphDefinitionHandler());
		return Collections.unmodifiableMap(map);
	}

	private static class AValuesDefinitionHandler implements
			DefinitionHandler {

		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef) {
			List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();

			for (PDefinition subdef : ((AValuesDefinition) pdef)
					.getValueDefinitions()) {
				String typeguard = "?";
				if (null != subdef.getType())
					typeguard = subdef.getType().toString();
					
				String nameguard = "?";
				if (null != subdef.getName())
					nameguard=subdef.getName().getName();
			
				r.add(Wrapper.newInstance(subdef, nameguard
						+ ": " + typeguard));
			}
			return r;
		}
	}

	private static class AFunctionParagraphDefinitionHandler implements
			DefinitionHandler {

		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef) {
			List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
			for (PDefinition subdef : ((AFunctionsDefinition) pdef)
					.getFunctionDefinitions()) {
				r.add(Wrapper.newInstance(subdef, subdef.getName().getName() + ": "
						+ subdef.getType()));
			}
			return r;
		}
	}

	private static class ATypesParagraphDefinitionHandler implements
			DefinitionHandler {

		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef) {
			List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
			for (ATypeDefinition subdef : ((ATypesDefinition) pdef).getTypes())
				r.add(Wrapper.newInstance(subdef, subdef.getName().getName()));
			return r;
		}
	}

	private static class AOperationParagraphDefinitionHandler implements
			DefinitionHandler {

		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef) {
			List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
			for (SCmlOperationDefinition subdef : ((AOperationsDefinition) pdef)
					.getOperations())
				r.add(Wrapper.newInstance(subdef, subdef.getName().getName() + ": "
						+ subdef.getType()));
			return r;
		}
	}

	private static class AActionsDefinitionHandler implements DefinitionHandler {
		public List<Wrapper<? extends PDefinition>> extractSubdefinition(
				PDefinition pdef) {
			List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
			AActionsDefinition actionsDef = (AActionsDefinition) pdef;
			String nameguard;
			for (AActionDefinition aADef : actionsDef.getActions()) {
				if (null == aADef.getName())
					nameguard = "anonymous";
				else
					nameguard = aADef.getName().toString();
				r.add(Wrapper.newInstance(aADef, nameguard));
			}
			return r;
		}
	}

}

package eu.compassresearch.ide.ui.editor.syntax;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;

public class TopLevelDefinitionMap
{

	public interface DefinitionHandler
	{
		public List<String> extractSubdefinition(PDefinition pdef);
	}

	private static final Map<Class<?>, String> TOPLEVEL_DEFINITION_MAP = createMap();

	public static String getDescription(Class<?> cls)
	{
		String r = TOPLEVEL_DEFINITION_MAP.get(cls);
		if (r == null)
			System.err.println("No top level entry found for class "
					+ cls.getCanonicalName());
		return r;
	}

	private static Map<Class<?>, String> createMap()
	{
		Map<Class<?>, String> map = new HashMap<Class<?>, String>();
		map.put(AValuesDefinition.class, "global value declarations");
		map.put(AFunctionsDefinition.class, "global function declarations");
		map.put(ATypesDefinition.class, "global type declarations");
		map.put(AChannelsDefinition.class, "channel declarations");
		map.put(AChansetsDefinition.class, "chanset declarations");
		return Collections.unmodifiableMap(map);
	}

}

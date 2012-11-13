package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;

public class TopLevelDefinitionMap {

	public interface DefinitionHandler {
		public List<String> extractSubdefinition(PDefinition pdef);
	}

	private static final Map<Class<?>, String> TOPLEVEL_DEFINITION_MAP = createMap();

	public static String getDescription(Class<?> cls) {
		String r = TOPLEVEL_DEFINITION_MAP.get(cls);
		if (r == null)
			System.err.println("No top level entry found for class "
					+ cls.getCanonicalName());
		return r;
	}

	private static Map<Class<?>, String> createMap() {
		Map<Class<?>, String> map = new HashMap<Class<?>, String>();
		map.put(AValueParagraphDefinition.class, "global value declarations");
		map.put(AFunctionParagraphDefinition.class, "global function declarations");
		map.put(ATypesParagraphDefinition.class, "global type declarations");
		map.put(AChannelParagraphDefinition.class, "channel declarations");
		map.put(AChansetParagraphDefinition.class, "chanset declarations");
		return Collections.unmodifiableMap(map);
	}

}

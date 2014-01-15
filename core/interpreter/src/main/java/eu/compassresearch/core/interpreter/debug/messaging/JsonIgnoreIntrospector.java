package eu.compassresearch.core.interpreter.debug.messaging;

import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;

public class JsonIgnoreIntrospector extends AnnotationIntrospector
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4080324790812049795L;
	private Map<Class<?>, String[]> jsonignoreProperties;
	
	public JsonIgnoreIntrospector(Map<Class<?>,String[]> jsonignoreProperties)
	{
		this.jsonignoreProperties = jsonignoreProperties;
	}

	@Override
	public Version version()
	{
		return VersionUtil.parseVersion("0.0.1", this.getClass().getPackage().getName(), "ignoredefinitions");
	}

	@Override
	public String[] findPropertiesToIgnore(Annotated ac)
	{
		for (Entry<Class<?>, String[]> entry : jsonignoreProperties.entrySet())
		{
			if (entry.getKey().isAssignableFrom(ac.getRawType()))
			{
				return entry.getValue();
			}
		}
		return null;
	}

}

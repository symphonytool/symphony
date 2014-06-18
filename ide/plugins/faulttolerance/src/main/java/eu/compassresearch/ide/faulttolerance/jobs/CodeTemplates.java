/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andr&eacute; Didier (<a
 *         href="mailto:alrd@cin.ufpe.br">alrd@cin.ufpe.br</a>)
 * 
 */
@XmlRootElement(name = "code-templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeTemplates {

	@XmlElement(name = "code-template")
	@XmlElementWrapper(name = "code-template")
	private final Map<String, String> templates;

	public CodeTemplates() {
		templates = new HashMap<>();
	}

	/**
	 * @param name
	 * @return
	 */
	public String get(String name) {
		return templates.get(name);
	}

	public Iterable<String> getKeys() {
		return templates.keySet();
	}

}

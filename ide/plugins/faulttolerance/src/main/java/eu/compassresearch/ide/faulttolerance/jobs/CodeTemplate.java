/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Andr&eacute; Didier (<a
 *         href="mailto:alrd@cin.ufpe.br">alrd@cin.ufpe.br</a>)
 * 
 */
public enum CodeTemplate {
	ChaosE, Limit, DivergenceFreedom, Semifairness, LazyDeadlockCheck, LazyLimitDeadlockCheck, NoFaults, Lazy, LazyLimit, Unwanted, Hidden, STOP;

	private String template;

	static {
		loadTemplates();
	}

	private static void loadTemplates() {
		try {
			JAXBContext jc = JAXBContext.newInstance(CodeTemplates.class);
			Unmarshaller um = jc.createUnmarshaller();
			CodeTemplates pts = (CodeTemplates) um
					.unmarshal(CodeTemplates.class.getClassLoader()
							.getResourceAsStream("CodeTemplates.xml"));
			for (String key : pts.getKeys()) {
				CodeTemplate ct = valueOf(key);
				ct.template = pts.get(key);
			}
		} catch (JAXBException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * @param systemName
	 * @return
	 */
	public String format(Object... args) {
		return String.format(template, args);
	}

}

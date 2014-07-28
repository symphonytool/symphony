package eu.compassresearch.ide.refinementtool.maude;

import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;

public class MaudePrettyPrinter extends RefinePrettyPrinter {

	public MaudePrettyPrinter() {
		super();
		cmlpp = new MaudePExprPrettyPrinter();
	}

}

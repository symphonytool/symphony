package eu.compassresearch.core.analysis.pog.utility;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.pog.utility.UniqueNameGenerator;

import eu.compassresearch.ast.lex.LexNameToken;

public class CmlUniqueNameGenerator extends UniqueNameGenerator {

	public CmlUniqueNameGenerator() {
		super(null);
	}

	@Override
	public ILexNameToken getUnique(String name) {
		return new LexNameToken("", (name + ++counter), null);
	}

}

package eu.compassresearch.ide.ui.editor.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.overture.ide.ui.editor.syntax.VdmColorProvider;

public class CmlSingleLineCommentScanner extends RuleBasedScanner
{

	public CmlSingleLineCommentScanner(VdmColorProvider provider)
	{
		IToken comment = new Token(new TextAttribute(provider.getColor(VdmColorProvider.SINGLE_LINE_COMMENT)));

		List<IRule> rules = new ArrayList<IRule>();
		// Add rule for single line comments.
		rules.add(new EndOfLineRule("--", comment));
		rules.add(new EndOfLineRule("//", comment));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
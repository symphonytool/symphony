/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package eu.compassresearch.ide.ui.editor.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.overture.ide.ui.editor.syntax.IVdmKeywords;
import org.overture.ide.ui.editor.syntax.VdmCodeScanner;
import org.overture.ide.ui.editor.syntax.VdmColorProvider;

public class CmlCodeScanner extends VdmCodeScanner
{
	public static final RGB TYPE = new RGB(0, 0, 255);
	public CmlCodeScanner(VdmColorProvider provider)
	{
		super(provider);
		final IToken stringBold = new Token(new TextAttribute(provider.getColor(TYPE), null, SWT.NONE));
		IToken type = new Token(new TextAttribute(provider.getColor(VdmColorProvider.TYPE), null, SWT.BOLD));
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new PrefixedUnderscoreIdentifierRule("act", stringBold));
		rules.add(new PrefixedUnderscoreIdentifierRule("ch", new Token(new TextAttribute(provider.getColor(new RGB(128, 128, 0)), null, SWT.NONE))));
		rules.add(new QuoteRule(type));
		rules.addAll(Arrays.asList(this.fRules));
	
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

	@Override
	protected IVdmKeywords getKeywords()
	{
		return new CmlKeywords();
	}

}

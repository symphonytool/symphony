package eu.compassresearch.ide.pog.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import eu.compassresearch.ide.ui.editor.syntax.CmlKeywords;

public class PogLineSyleListener implements LineStyleListener
{

	private Color keyword_color;
	private Color comment_color;

	private List<String> keywords = new ArrayList<String>();

	public PogLineSyleListener(Color kwcolor, Color ccolor)
	{
		super();
		keyword_color = kwcolor;
		comment_color = ccolor;

		String[] kws = CmlKeywords.reservedwords;

		for (int i = 0; i < kws.length; i++)
		{
			keywords.add(kws[i]);
		}

	}

	@Override
	public void lineGetStyle(LineStyleEvent event)
	{
		List<StyleRange> styles = new ArrayList<StyleRange>();
		int start = 0;
		int length = event.lineText.length();

		if (event.lineText.startsWith("-- "))
		{
			// comment
			styles.add(new StyleRange(event.lineOffset, length, comment_color, null, SWT.ITALIC));
		} else
		{
			while (start < length)
			{
				if (Character.isLetter(event.lineText.charAt(start)))
				{
					StringBuffer buf = new StringBuffer();
					int i = start;
					for (; i < length
							&& Character.isLetter(event.lineText.charAt(i)); i++)
					{
						buf.append(event.lineText.charAt(i));
					}
					if (keywords.contains(buf.toString()))
					{
						styles.add(new StyleRange(event.lineOffset + start, i
								- start, keyword_color, null, SWT.BOLD));
					}
					start = i;
				} else
				{
					start++;
				}
			}
		}

		event.styles = (StyleRange[]) styles.toArray(new StyleRange[0]);
	}

}
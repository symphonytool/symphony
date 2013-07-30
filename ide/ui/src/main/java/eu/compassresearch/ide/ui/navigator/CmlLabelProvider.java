package eu.compassresearch.ide.ui.navigator;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.overture.ast.node.INode;

public class CmlLabelProvider extends LabelProvider implements IColorProvider,
		IFontProvider, IStyledLabelProvider
{
	WorkbenchLabelProvider workbenchLabelProvider = new WorkbenchLabelProvider();
	CmlUiLabelProvider cmlLabelProvider = new CmlUiLabelProvider();

	@Override
	public StyledString getStyledText(Object element)
	{
		if (element instanceof INode)
		{
			return this.cmlLabelProvider.getStyledText(element);
		}
		return workbenchLabelProvider.getStyledText(element);
	}

	@Override
	public Image getImage(Object element)
	{
		if (element instanceof INode)
		{
			return this.cmlLabelProvider.getImage(element);
		}
		return workbenchLabelProvider.getImage(element);
	}

	@Override
	public Font getFont(Object element)
	{
		return workbenchLabelProvider.getFont(element);
	}

	@Override
	public Color getForeground(Object element)
	{
		return workbenchLabelProvider.getForeground(element);
	}

	@Override
	public Color getBackground(Object element)
	{
		return workbenchLabelProvider.getBackground(element);
	}
}

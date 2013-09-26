package eu.compassresearch.ide.interpreter.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import eu.compassresearch.ide.core.ICmlCoreConstants;
import eu.compassresearch.ide.ui.ICmlUiConstants;

public class CmlDebugModelPresentation extends LabelProvider implements
		IDebugModelPresentation
{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
	 * org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener)
	{
		String detail = "";
		try
		{
			if (value instanceof CmlValue)
			{
				CmlValue vdmValue = (CmlValue) value;
				vdmValue.getVariables();
				detail = vdmValue.getRawValue();
			} else
			{
				detail = value.getValueString();
			}

		} catch (DebugException e)
		{
		}
		listener.detailComputed(value, detail);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput, java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element)
	{
		if (element instanceof ILineBreakpoint)
		{
			ILineBreakpoint breakpoint = (ILineBreakpoint) element;
			element = breakpoint.getMarker().getResource();
		}

		if (element instanceof IFile)
		{
			IFile file = (IFile) element;
			try
			{
				String contentTypeId = file.getContentDescription().getContentType().getId();

				if (contentTypeId.equals(ICmlCoreConstants.CONTENT_TYPE))
				{
					return ICmlUiConstants.CML_EDITOR_ID;
				}
			} catch (CoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public IEditorInput getEditorInput(Object element)
	{
		if (element instanceof IFile)
			return new FileEditorInput((IFile) element);
		if (element instanceof ILineBreakpoint)
			return new FileEditorInput((IFile) ((ILineBreakpoint) element).getMarker().getResource());
		return null;
	}

	public void setAttribute(String attribute, Object value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getText(Object element)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

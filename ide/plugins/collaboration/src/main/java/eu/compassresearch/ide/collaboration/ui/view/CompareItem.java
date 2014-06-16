package eu.compassresearch.ide.collaboration.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IFlushable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Image;

import eu.compassresearch.ide.collaboration.Activator;

public class CompareItem implements IFlushable, IStreamContentAccessor,
		ITypedElement, IModificationDate, IEditableContent
{
	private String name;
	private long time;
	private byte[] contents = null;
	private boolean editable = true;
	private IFile toSave;

	CompareItem(IFile file, String name, String contents, long time)
	{
		this.name = name;
		this.contents = contents == null ? null : contents.getBytes();
		this.time = time;
		this.toSave = file;
	}

	CompareItem(String name, String contents, long time)
	{
		this(null, name, contents, time);
	}

	public CompareItem(String name, String contents)
	{
		this(name, contents, 0);
	}

	public InputStream getContents() throws CoreException
	{
		return contents == null ? null : new ByteArrayInputStream(contents);
	}

	public Image getImage()
	{
		return null;
	}

	public long getModificationDate()
	{
		return time;
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return ITypedElement.TEXT_TYPE;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	@Override
	public ITypedElement replace(ITypedElement dest, ITypedElement src)
	{
		return dest;
	}

	@Override
	public void setContent(byte[] newContent)
	{
		contents = newContent;
	}

	@Override
	public void flush(IProgressMonitor monitor)
	{
		try
		{
			toSave.setContents(getContents(), IFile.KEEP_HISTORY
					| IFile.FORCE, monitor);
		} catch (CoreException e)
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
			e.printStackTrace();
		}
	}
}
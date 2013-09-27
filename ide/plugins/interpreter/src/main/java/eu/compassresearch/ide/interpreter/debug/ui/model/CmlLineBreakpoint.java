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
package eu.compassresearch.ide.interpreter.debug.ui.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;

import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CmlLineBreakpoint extends Breakpoint implements
		org.eclipse.debug.core.model.ILineBreakpoint
{

	protected String getMarkerId()
	{
		return ICmlDebugConstants.LINE_BREAKPOINT_MARKER_ID;
	}

	protected void addLineBreakpointAttributes(Map<String, Object> attributes,
			IPath path, int lineNumber, int charStart, int charEnd)
	{
		if (path != null)
		{
			attributes.put(IMarker.LOCATION, path.toPortableString());
		}
		attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
		attributes.put(IMarker.CHAR_START, new Integer(charStart));
		attributes.put(IMarker.CHAR_END, new Integer(charEnd));
	}

	public CmlLineBreakpoint()
	{

	}

	public CmlLineBreakpoint(final String debugModelId,
			final IResource resource, final IPath path, final int lineNumber,
			final int charStart, final int charEnd, final boolean add)
			throws DebugException
	{

		IWorkspaceRunnable wr = new IWorkspaceRunnable()
		{
			public void run(IProgressMonitor monitor) throws CoreException
			{
				// create the marker
				IMarker marker = resource.createMarker(getMarkerId());

				setMarker(marker);// getMarkerId()));

				// add attributes
				final Map<String, Object> attributes = new HashMap<String, Object>();
				addVdmBreakpointAttributes(attributes, debugModelId, true);
				addLineBreakpointAttributes(attributes, path, lineNumber, charStart, charEnd);

				// set attributes
				ensureMarker().setAttributes(attributes);

				// add to breakpoint manager if requested
				register(add);
			}
		};
		run(getMarkerRule(resource), wr);
	}

	protected void addVdmBreakpointAttributes(Map<String, Object> attributes,
			String debugModelId, boolean enabled)
	{
		this.debugModelId = debugModelId;
		attributes.put(IBreakpoint.ID, debugModelId);
		attributes.put(IBreakpoint.ENABLED, Boolean.valueOf(enabled));
	}

	/**
	 * Add this breakpoint to the breakpoint manager, or sets it as unregistered.
	 */
	public void register(boolean register) throws CoreException
	{
		DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null && register)
		{
			plugin.getBreakpointManager().addBreakpoint(this);
		} else
		{
			setRegistered(false);
		}
	}

	// ILineBreakpoint
	public int getLineNumber() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.LINE_NUMBER, -1);
	}

	public int getCharStart() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.CHAR_START, -1);
	}

	public int getCharEnd() throws CoreException
	{
		return ensureMarker().getAttribute(IMarker.CHAR_END, -1);

	}

	public String getResourceName() throws CoreException
	{
		IResource resource = ensureMarker().getResource();
		if (!resource.equals(getWorkspaceRoot()))
			return resource.getName();

		// else
		String portablePath = (String) ensureMarker().getAttribute(IMarker.LOCATION);
		if (portablePath != null)
		{
			IPath path = Path.fromPortableString(portablePath);
			return path.lastSegment();
		} else
		{
			return null;
		}
	}

	// IScriptLineBreakpoint
	public IResource getResource()
	{
		try
		{
			final IResource resource = ensureMarker().getResource();
			if (!resource.equals(getWorkspaceRoot()))
			{
				return resource;
			}
		} catch (CoreException e)
		{
			CmlDebugPlugin.log(e);
		}
		return null;
	}

	private static IWorkspaceRoot getWorkspaceRoot()
	{
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public IPath getResourcePath()
	{
		try
		{
			final IResource resource = ensureMarker().getResource();
			if (!resource.equals(getWorkspaceRoot()))
				return ensureMarker().getResource().getFullPath();
			final String path = (String) ensureMarker().getAttribute(IMarker.LOCATION);
			if (path != null)
			{
				return Path.fromPortableString(path);
			}
		} catch (CoreException e)
		{
			CmlDebugPlugin.log(e);
		}
		return null;
	}

	public URI getResourceURI()
	{
		try
		{
			IResource resource = ensureMarker().getResource();
			return resource.getLocationURI();

		} catch (DebugException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// private static final String[] UPDATABLE_ATTRS = new String[] {
	// IMarker.LINE_NUMBER, IBreakpoint.ENABLED,
	// AbstractVdmBreakpoint.HIT_CONDITION,
	// AbstractVdmBreakpoint.HIT_VALUE,
	// AbstractVdmBreakpoint.EXPRESSION,
	// AbstractVdmBreakpoint.EXPRESSION_STATE };

	// public String[] getUpdatableAttributes() {
	// return UPDATABLE_ATTRS;
	// }
	private String debugModelId;

	public String getModelIdentifier()
	{
		if (debugModelId == null)
		{
			try
			{
				debugModelId = ensureMarker().getAttribute(IBreakpoint.ID, null);
			} catch (DebugException e)
			{
				if (CmlDebugPlugin.DEBUG)
				{
					e.printStackTrace();
				}
				return null;
			}
		}
		return debugModelId;
	}

	public void setMessage(String message) throws CoreException
	{
		setAttribute(IMarker.MESSAGE, message);
	}

}

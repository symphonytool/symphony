package eu.compassresearch.ide.collaboration.ui.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatus;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatus.ConfigurationNegotiationStatus;
import eu.compassresearch.ide.collaboration.datamodel.ConfigurationStatuses;
import eu.compassresearch.ide.collaboration.datamodel.Configurations;
import eu.compassresearch.ide.collaboration.datamodel.File;
import eu.compassresearch.ide.collaboration.datamodel.Files;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.datamodel.Visibility;
import eu.compassresearch.ide.collaboration.datamodel.Visible;
import eu.compassresearch.ide.collaboration.ui.TreeViewerPlugin;

public class CollaborationLabelProvider extends LabelProvider implements
		IColorProvider
{
	private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(11);

	public Image getImage(Object element)
	{
		ImageDescriptor descriptor = null;
		if (element instanceof CollaborationProject)
		{
			descriptor = getImageDescriptor("contracts.gif");
		} else if (element instanceof Configurations)
		{
			descriptor = getImageDescriptor("contracts.gif");
		} else if (element instanceof Configuration)
		{
			descriptor = getImageDescriptor("contract.gif");
		} else if (element instanceof Files)
		{
			descriptor = getImageDescriptor("versions.gif");
		} else if (element instanceof File)
		{
			descriptor = getImageDescriptor("version.gif");
		} else if (element instanceof ConfigurationStatuses)
		{
			descriptor = getImageDescriptor("shares.gif");
		} else if (element instanceof ConfigurationStatus)
		{
			descriptor = getImageDescriptor("shares.gif");
		} else if (element instanceof Visibility)
		{
			descriptor = getImageDescriptor("shares.gif");
		} else if (element instanceof Visible)
		{
			descriptor = getImageDescriptor("share.gif");
		} else if (element instanceof CollaborationGroup)
		{
			descriptor = getImageDescriptor("collaborationgroup.gif");
		} else if (element instanceof User)
		{
			descriptor = getImageDescriptor("user.gif");
		} else
		{
			throw unknownElement(element);
		}

		Image image = (Image) imageCache.get(descriptor);
		if (image == null)
		{
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}

	public String getText(Object element)
	{

		if (element instanceof CollaborationProject)
		{
			CollaborationProject collabProject = ((CollaborationProject) element);

			return collabProject.getTitle() + " (attached to project: "
					+ collabProject.getProjectWorkspaceName() + ") "
					+ collabProject.getUniqueID();
		} else if (element instanceof Configurations)
		{
			if (((Configurations) element).getName() == null)
			{
				return "Configurations";
			} else
			{
				return ((Configurations) element).toString();
			}
		} else if (element instanceof Configuration)
		{

			Configuration config = (Configuration) element;

			String toString = config.toString();
			if (config.isSigned())
			{
				toString += " (Signed by: " + config.getSignedBy() + ")";
			}

			if (config.isShared())
			{
				toString += " (Shared)";
			} else if (config.isLocal())
			{
				toString += " (Not shared - local configuration)";
			} else if (config.isReceived())
			{
				toString += " (Received)";
			}
			return toString;

		} else if (element instanceof Files)
		{
			return ((Files) element).toString();
		} else if (element instanceof Visibility)
		{
			return ((Visibility) element).toString();
		} else if (element instanceof File)
		{

			File file = (File) element;
			String toString;

			if (file.isNewFile())
			{
				toString = file + " (New file)";
			} else if (file.isUpdatedFile())
			{
				toString = file + " (Changed)";
			} else
			{
				toString = file.toString();
			}

			return toString;
		} else if (element instanceof ConfigurationStatuses)
		{
			ConfigurationStatuses cs = (ConfigurationStatuses) element;
			return cs.toString();
		} else if (element instanceof ConfigurationStatus)
		{
			return ((ConfigurationStatus) element).toString();
		} else if (element instanceof Visible)
		{
			return ((Visible) element).toString();
		} else if (element instanceof CollaborationGroup)
		{
			return ((CollaborationGroup) element).toString();
		} else if (element instanceof User)
		{

			User usr = (User) element;
			String toString;

			if (usr.hasJoinedGroup())
			{
				toString = usr.getName();
			} else if (usr.hasDeclinedGroup())
			{
				toString = usr.getName() + " (Declined Request)";
			} else
			{
				toString = usr.getName() + " (Request sent, pending response.)";
			}

			return toString;
		} else
		{
			throw unknownElement(element);
		}
	}

	public void dispose()
	{
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();)
		{
			i.next().dispose();
		}
		imageCache.clear();
	}

	public static ImageDescriptor getImageDescriptor(String name)
	{
		Bundle bundle = FrameworkUtil.getBundle(TreeViewerPlugin.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + name), null);
		return ImageDescriptor.createFromURL(url);
	}

	protected RuntimeException unknownElement(Object element)
	{
		return new RuntimeException("Unknown type of element in tree of type "
				+ element.getClass().getName());
	}

	@Override
	public Color getBackground(Object element)
	{
		if (element instanceof Configuration)
		{
			Configuration c = (Configuration) element;

//			if (c.getStatus() == NegotiationStatus.ACCEPT)
//			{
//				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
//			} else if (c.getStatus() == NegotiationStatus.REJECT)
//			{
//				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//			}

			if (c.isShared())
			{
				// return Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
			} else if (c.isReceived())
			{
				// return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
			} else if (c.isLocal())
			{
				// return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
			}
		} 
		

		return null;
	}

	@Override
	public Color getForeground(Object element)
	{
		if (element instanceof Configuration)
		{
			Configuration c = (Configuration) element;

			if (c.isShared())
			{
				return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
			} else if (c.isReceived())
			{
				return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
			} else if (c.isLocal())
			{
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
			}
		} else  if (element instanceof ConfigurationStatus){
			ConfigurationStatus cs = (ConfigurationStatus) element;
			
			ConfigurationNegotiationStatus status = cs.getStatus();
			
			switch(status){
				case ACCEPT:
					return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
				case NOTSET:
					break;
				case REJECT:
					return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				case RENEGOTIATED:
					break;
				default:
					break;
			}
		}
		return null;
	}
}

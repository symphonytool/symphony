package eu.compassresearch.ide.collaboration.treeview.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import eu.compassresearch.ide.collaboration.messages.FileStatusMessage.NegotiationStatus;
import eu.compassresearch.ide.collaboration.treeview.TreeViewerPlugin;
import eu.compassresearch.ide.collaboration.treeview.model.CollaborationGroup;
import eu.compassresearch.ide.collaboration.treeview.model.Contract;
import eu.compassresearch.ide.collaboration.treeview.model.Contracts;
import eu.compassresearch.ide.collaboration.treeview.model.Share;
import eu.compassresearch.ide.collaboration.treeview.model.Shares;
import eu.compassresearch.ide.collaboration.treeview.model.User;
import eu.compassresearch.ide.collaboration.treeview.model.Version;
import eu.compassresearch.ide.collaboration.treeview.model.Versions;

public class CollaborationLabelProvider extends LabelProvider implements IColorProvider {	
	private Map imageCache = new HashMap(11);
	
	public Image getImage(Object element) {
		ImageDescriptor descriptor = null;
		if (element instanceof Contracts) {
			descriptor = getImageDescriptor("contracts.gif");
		} else if (element instanceof Contract) {
			descriptor = getImageDescriptor("contract.gif");
		} else if (element instanceof Versions) {
			descriptor = getImageDescriptor("versions.gif");
		} else if (element instanceof Version) {
			descriptor = getImageDescriptor("version.gif");
		} else if (element instanceof Shares) {	
			descriptor = getImageDescriptor("shares.gif");
		} else if (element instanceof Share) {
			descriptor = getImageDescriptor("share.gif");
		} else if (element instanceof CollaborationGroup) {
			descriptor = getImageDescriptor("collaborationgroup.gif");
		} else if (element instanceof User) {
			descriptor = getImageDescriptor("user.gif");
		} else {
			throw unknownElement(element);
		}

		Image image = (Image)imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}

	public String getText(Object element) {
		if (element instanceof Contracts) {
			if(((Contracts)element).getName() == null) {
				return "Contracts";
			} else {
				return ((Contracts)element).getName();
			}
		} else if (element instanceof Contract) {
			return ((Contract)element).toString();
		} else if (element instanceof Versions) {
			return ((Versions)element).toString();
		} else if (element instanceof Shares) {
			return ((Shares)element).toString();
		} else if (element instanceof Version) {
			return ((Version)element).toString();
		} else if (element instanceof Share) {
			return ((Share)element).toString();
		} else if (element instanceof CollaborationGroup) {
			return ((CollaborationGroup)element).toString();
		} else if (element instanceof User) {
			return ((User)element).toString();
		} else {
			throw unknownElement(element);
		}
	}

	public void dispose() {
		for (Iterator i = imageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		imageCache.clear();
	}
	
	public static ImageDescriptor getImageDescriptor(String name) {
		Bundle bundle = FrameworkUtil.getBundle(TreeViewerPlugin.class);
		 URL url = FileLocator.find(bundle, new Path("icons/" + name), null);
		return ImageDescriptor.createFromURL(url);
	}

	protected RuntimeException unknownElement(Object element) {
		return new RuntimeException("Unknown type of element in tree of type " + element.getClass().getName());
	}


	@Override
	public Color getBackground(Object element)
	{
		if(element instanceof Contract){
			Contract c = (Contract) element;
			
			if(c.getStatus() == NegotiationStatus.ACCEPT) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
			} else if (c.getStatus() == NegotiationStatus.REJECT) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
			}
		}
		
		return null;
	}

	@Override
	public Color getForeground(Object arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

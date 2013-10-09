package eu.compassresearch.ide.collaboration.treeview.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import eu.compassresearch.ide.collaboration.treeview.TreeViewerPlugin;
import eu.compassresearch.ide.collaboration.treeview.model.CollaborationGroup;
import eu.compassresearch.ide.collaboration.treeview.model.Contract;
import eu.compassresearch.ide.collaboration.treeview.model.Contracts;
import eu.compassresearch.ide.collaboration.treeview.model.Share;
import eu.compassresearch.ide.collaboration.treeview.model.Shares;
import eu.compassresearch.ide.collaboration.treeview.model.User;
import eu.compassresearch.ide.collaboration.treeview.model.Version;
import eu.compassresearch.ide.collaboration.treeview.model.Versions;

public class CollaborationLabelProvider extends LabelProvider {	
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
			return ((Contract)element).getName();
		} else if (element instanceof Versions) {
			return ((Versions)element).getName();
		} else if (element instanceof Shares) {
			return ((Shares)element).getName();
		} else if (element instanceof Version) {
			return ((Version)element).getName();
		} else if (element instanceof Share) {
			return ((Share)element).getName();
		} else if (element instanceof CollaborationGroup) {
			return ((CollaborationGroup)element).getName();
		} else if (element instanceof User) {
			return ((User)element).getName();
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

}

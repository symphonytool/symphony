/*******************************************************************************
 * Copyright (c) 2004 - 2006 University Of British Columbia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     University Of British Columbia - initial API and implementation
 *     Rob Elves - creator of the original TaskListNotificationPopup class
 *     
 *     
 * Based on ECF org.eclipse.ecf.presence.ui.MessageNotificationPopup
 *     
 *******************************************************************************/

package eu.compassresearch.ide.collaboration.menu;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

import eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView;


public class CollaborationNotificationPopUp extends PopupDialog
{
	private FormToolkit toolkit;

	private Form form;

	private Composite sectionClient;

	private IWorkbenchWindow window;
	
	CollaborationView collabview; 

	private String userName;
	private String message;


	public CollaborationNotificationPopUp(IWorkbenchWindow window, CollaborationView collaborationview) {
		super(window.getShell(), PopupDialog.INFOPOPUP_SHELLSTYLE | SWT.ON_TOP, false, false, false, false, false, null, null);
		this.window = window;
		collabview = collaborationview;
	}

	public boolean close() {
		toolkit.dispose();
		return super.close();
	}

	public void setContent(String userName, String message) {
		this.userName = userName;
		this.message = message;
	}

	protected Control createContents(Composite parent) {
		
		getShell().setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_GRAY));
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createForm(parent);
		form.getBody().setLayout(new FillLayout());

		Section section = toolkit.createSection(form.getBody(), ExpandableComposite.TITLE_BAR);
		section.setText("Collaborative Modelling plugin");
		section.setLayout(new FillLayout());

		sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout());
		
		sectionClient = toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout());
		Hyperlink link = toolkit.createHyperlink(sectionClient, userName + ":\n" +  message, SWT.NONE);
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
					collabview.setFocus();
			}
		});

		section.setClient(sectionClient);
		

		form.pack();
		return parent;
	}

	/**
	 * Initialize the shell's bounds.
	 */
	public void initializeBounds() {
		getShell().setBounds(restoreBounds());
	}

	private Rectangle restoreBounds() {
		Rectangle bounds = form.getBounds();
		Rectangle maxBounds = window.getShell().getMonitor().getClientArea();

		if (bounds.width > -1 && bounds.height > -1) {
			if (maxBounds != null) {
				bounds.width = Math.min(bounds.width, maxBounds.width);
				bounds.height = Math.min(bounds.height, maxBounds.height);
			}
			// Enforce an absolute minimal size
			bounds.width = Math.max(bounds.width, 30);
			bounds.height = Math.max(bounds.height, 30);
		}

		if (bounds.x > -1 && bounds.y > -1 && maxBounds != null) {
			if (bounds.width > -1 && bounds.height > -1) {
				bounds.x = maxBounds.x + maxBounds.width - bounds.width;
				bounds.y = maxBounds.y + maxBounds.height - bounds.height;
			}
		}

		return bounds;
	}
}

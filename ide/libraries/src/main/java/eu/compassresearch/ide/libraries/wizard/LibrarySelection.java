/*******************************************************************************
 * Copyright (c) 2010, 2011 DESTECS Team and others.
 *
 * DESTECS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DESTECS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DESTECS.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The DESTECS web-site: http://destecs.org/
 *******************************************************************************/
package eu.compassresearch.ide.libraries.wizard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import eu.compassresearch.ide.libraries.store.LibStore;
import eu.compassresearch.ide.libraries.store.Library;

public class LibrarySelection extends Composite
{

	private Label label = null;

	Map<Button, Library> libMap = new HashMap<Button, Library>();

	Button buttonLinkedLibs;
	boolean showTitle = false;

	public LibrarySelection(Composite parent, int style)
	{
		super(parent, style);
		initialize();
	}

	public LibrarySelection(Composite parent, int style, boolean showTitle)
	{
		super(parent, style);
		this.showTitle = showTitle;
		initialize();
	}

	private void initialize()
	{
		if (showTitle)
		{
			label = new Label(this, SWT.NONE);
			label.setText("Select libraries to include");
		}
		LibStore store = new LibStore();
		Set<Library> libs = store.getLibraries();

		Group libGroup = createGroup(this, "Libraries");
		for (Library library : libs)
		{
			Button b = new Button(libGroup, SWT.CHECK);
			b.setText(library.name + " - v."+library.version);
			b.setToolTipText(library.description);
			libMap.put(b, library);
		}

		buttonLinkedLibs = new Button(createGroup(this, "Configuration"), SWT.CHECK);
		buttonLinkedLibs.setText("Used linked libraries");
		buttonLinkedLibs.setToolTipText("This creates symbolic links to the libraries");
		buttonLinkedLibs.setSelection(false);
		buttonLinkedLibs.setEnabled(false);

		setSize(new Point(300, 200));
		setLayout(new GridLayout());
	}

	private Group createGroup(Composite comp, String name)
	{
		Group group = new Group(comp, SWT.NONE);
		group.setText(name);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		group.setLayout(layout);

		return group;
	}

	public Set<Library> getSelectedLibs()
	{
		Set<Library> selectedLibs = new HashSet<Library>();
		for (Entry<Button, Library> entry : libMap.entrySet())
		{
			if (entry.getKey().getSelection())
			{
				selectedLibs.add(entry.getValue());
			}
		}
		return selectedLibs;
	}

	public boolean useLinkedLibs()
	{
		return buttonLinkedLibs.getSelection();
	}

}

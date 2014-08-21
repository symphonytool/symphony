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
package eu.compassresearch.ide.interpreter.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.overture.parser.config.Properties;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CmlAdvancedLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab
{
	class WidgetListener implements ModifyListener, SelectionListener
	{
		public void modifyText(ModifyEvent e)
		{
			// validatePage();
			updateLaunchConfigurationDialog();
		}

		public void widgetDefaultSelected(SelectionEvent e)
		{
			/* do nothing */
		}

		public void widgetSelected(SelectionEvent e)
		{
			// fOperationText.setEnabled(!fdebugInConsole.getSelection());

			updateLaunchConfigurationDialog();
		}
	}

	private WidgetListener fListener = new WidgetListener();
	private Text fRandomSeedText;
	private Text fIntMinText;
	private Text fIntMaxText;

	public void createControl(Composite parent)
	{
		Composite comp = new Composite(parent, SWT.NONE);

		setControl(comp);
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());

		Group group = new Group(comp, SWT.NONE);
		group.setText("Advanced Interpreter Settings");
		// GridLayout layout = new GridLayout();
		// layout.numColumns = 1;
		// interperterGroup.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 2;
		group.setLayout(layout);

		Label label = new Label(group, SWT.MIN);
		label.setText("Random seed:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);
		
		Listener numberVerifier = new Listener() {
		      public void handleEvent(Event e) {
		          String string = e.text;
		          char[] chars = new char[string.length()];
		          string.getChars(0, chars.length, chars, 0);
		          for (int i = 0; i < chars.length; i++) {
		            if (!('0' <= chars[i] && chars[i] <= '9')) {
		              e.doit = false;
		              return;
		            }
		          }
		        }
		      };

		fRandomSeedText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fRandomSeedText.setLayoutData(gd);
		fRandomSeedText.addModifyListener(fListener);
		fRandomSeedText.addListener(SWT.Verify, numberVerifier);

		label = new Label(group, SWT.MIN);
		label.setText("Int min:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		fIntMinText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fIntMinText.setLayoutData(gd);
		fIntMinText.addModifyListener(fListener);
		fIntMinText.addListener(SWT.Verify, numberVerifier);

		label = new Label(group, SWT.MIN);
		label.setText("Int max:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		fIntMaxText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fIntMaxText.setLayoutData(gd);
		fIntMaxText.addModifyListener(fListener);
		fIntMaxText.addListener(SWT.Verify, numberVerifier);

	}

	public boolean isValid()
	{
		setErrorMessage(null);

		try
		{
			Integer.parseInt(fRandomSeedText.getText());
		} catch (NumberFormatException e)
		{
			setErrorMessage("Random Seed not a valid integer");
			return false;
		}

		try
		{
			Integer.parseInt(fIntMinText.getText());
		} catch (NumberFormatException e)
		{
			setErrorMessage("Int min not a valid integer");
			return false;
		}

		try
		{
			Integer.parseInt(fIntMaxText.getText());
		} catch (NumberFormatException e)
		{
			setErrorMessage("Int max not a valid integer");
			return false;
		}

		if (Integer.valueOf(fIntMinText.getText()) >= Integer.valueOf(fIntMaxText.getText()))
		{
			setErrorMessage("Int min must be smaller than int max");
			return false;
		}
		return true;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig)
	{
		return isValid() && super.isValid(launchConfig);
	}

	@Override
	public boolean canSave()
	{
		return isValid() && super.canSave();
	}

	public String getName()
	{
		return "Advanced";
	}

	public void initializeFrom(ILaunchConfiguration configuration)
	{
		try
		{
			fRandomSeedText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_RANDOM_SEED, CmlRuntime.randomSeed)
					+ "");
			fIntMinText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MININT, Properties.minint)
					+ "");
			fIntMaxText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MAXINT, Properties.maxint)
					+ "");
		} catch (CoreException e)
		{
			if (CmlDebugPlugin.DEBUG)
			{
				CmlDebugPlugin.logError("Error in " + getName()
						+ " launch configuration tab", e);
			}
		}

	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_RANDOM_SEED, Integer.valueOf(fRandomSeedText.getText()));
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MININT, Integer.valueOf(fIntMinText.getText()));
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MAXINT, Integer.valueOf(fIntMaxText.getText()));
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_RANDOM_SEED, CmlRuntime.randomSeed);
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MININT, Properties.minint);
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_MAXINT, Properties.maxint);
	}

}

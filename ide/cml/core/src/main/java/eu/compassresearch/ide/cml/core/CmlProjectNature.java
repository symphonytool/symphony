package eu.compassresearch.ide.cml.core;
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


import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.overture.ide.core.resources.VdmProjectNature;


public class CmlProjectNature extends VdmProjectNature
{

	@Override
	public void configure() throws CoreException {
		   final String BUILDER_ID = "eu.compassresearch.ide.cml.ui.cmlbuilder";
		   IProjectDescription desc = project.getDescription();
		   ICommand[] commands = desc.getBuildSpec();
		   boolean found = false;

		   for (int i = 0; i < commands.length; ++i) {
		      if (commands[i].getBuilderName().equals(BUILDER_ID)) {
		         found = true;
		         break;
		      }
		   }
		   
		   if (!found) { 
		      //add builder to project
		      ICommand command = desc.newCommand();
		      command.setBuilderName(BUILDER_ID);
		      ICommand[] newCommands = new ICommand[commands.length + 1];

		      // Add it before other builders.
		      System.arraycopy(commands, 0, newCommands, 1, commands.length);
		      newCommands[0] = command;
		      desc.setBuildSpec(newCommands);
		      project.setDescription(desc, null);
		   }
	}

	@Override
	public void deconfigure() throws CoreException {
		// FIXME: DeConfiguration should go here		
	}
	
}
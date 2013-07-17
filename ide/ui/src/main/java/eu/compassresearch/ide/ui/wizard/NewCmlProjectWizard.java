package eu.compassresearch.ide.ui.wizard;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.overture.ide.core.ICoreConstants;

import eu.compassresearch.ide.core.ICmlCoreConstants;

public class NewCmlProjectWizard extends BasicNewProjectResourceWizard
		implements INewWizard
{

	public NewCmlProjectWizard()
	{
	}

	public static void addNature(IProject project, String nature)
			throws CoreException
	{
		if (!project.hasNature(nature))
		{
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = nature;
			description.setNatureIds(newNatures);

			IProgressMonitor monitor = null;
			project.setDescription(description, monitor);
		}
	}

	public boolean performFinish()
	{

		boolean ok = super.performFinish();

		IProject prj = getNewProject();

		if (prj != null)
		{
			try
			{
				setVdmBuilder(prj);
			} catch (CoreException e)
			{
				e.printStackTrace();
				return false;
			}
		}

		return ok;
	}

	public static void setVdmBuilder(IProject prj) throws CoreException
	{
		addNature(prj, ICmlCoreConstants.NATURE);
		// addBuilder(prj,
		// "eu.compassresearch.ide.cml.CmlIncrementalBuilder",
		// "", "");
		addBuilder(prj, ICoreConstants.BUILDER_ID, null, null);
	}

	@SuppressWarnings("unchecked")
	public static void addBuilder(IProject project, String name,
			String argumentKey, String argumentValue) throws CoreException
	{
		Vector<ICommand> buildCommands = new Vector<ICommand>();
		boolean found = false;
		IProjectDescription description = project.getDescription();
		for (ICommand command : description.getBuildSpec())
		{
			buildCommands.add(command);
			if (command.getBuilderName().equals(name))
			{
				found = true;
				if (argumentKey != null && argumentValue != null)
				{

					@SuppressWarnings("rawtypes")
					Map arguments = command.getArguments();
					if (arguments == null)
						arguments = new HashMap<String, String>();

					if (arguments.containsKey(argumentKey))
						arguments.remove(argumentKey);

					arguments.put(argumentKey, argumentValue);

					command.setArguments(arguments);
				}

			}
		}

		if (!found)
		{
			ICommand newCommand = description.newCommand();
			newCommand.setBuilderName(name);
			if (argumentKey != null && argumentValue != null)
			{
				@SuppressWarnings("rawtypes")
				Map arguments = new HashMap<String, String>();
				arguments.put(argumentKey, argumentValue);
				newCommand.setArguments(arguments);
			}

			buildCommands.add(newCommand);

		}
		ICommand[] commands = new ICommand[buildCommands.size()];
		commands = buildCommands.toArray(commands);
		description.setBuildSpec(commands);

		project.setDescription(description, null);

	}

}

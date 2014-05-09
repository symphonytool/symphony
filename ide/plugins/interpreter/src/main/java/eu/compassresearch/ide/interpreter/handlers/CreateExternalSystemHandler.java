package eu.compassresearch.ide.interpreter.handlers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ide.ui.wizard.pages.WizardProjectsImportPageProxy;

import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CreateExternalSystemHandler extends AbstractHandler implements
		IHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IResource)
			{
				IProject proj = ((IResource) firstElement).getProject();

				IFolder base = createExternalSystemStructure(proj,null);

				try
				{
					URL url = WizardProjectsImportPageProxy.getResource(ICmlDebugConstants.PLUGIN_ID, "external-system-template");
					String path = FileLocator.resolve(url).getPath();

					// gets URI for EFS.
					URI uri = base.getLocationURI();

					// what if file is a link, resolve it.
					if (base.isLinked())
					{
						uri = base.getRawLocationURI();
					}

					// Gets native File using EFS
					File javaFile = EFS.getStore(uri).toLocalFile(0, new NullProgressMonitor());

					copyDirectory(new File(path), javaFile);

					base.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (Exception e)
				{

				}
			}
		}

		return null;
	}

	public static IFolder createExternalSystemStructure(IProject proj, String config)
	{
		IFolder base = proj.getFolder("ExternalSystem");

		try
		{
			if (!base.exists())
			{
				base.create(true, false, new NullProgressMonitor());
			}

			IFolder gen = base.getFolder("generates");

			if (!gen.exists())
			{
				gen.create(true, false, new NullProgressMonitor());
			}

			IFile cfg = gen.getFile("CoSimConfig.hpp");
			if (cfg.exists())
			{
				cfg.delete(true, new NullProgressMonitor());
			}

			if (config == null)
			{
				config = "#define SYMPHONY_HOST \"localhost\"\n#define SYMPHONY_PORT 8882\n#define EXTERNAL_PROCESS \""
						+ proj.getName().toUpperCase()
						+ "_NOT_CONFIGURED"
						+ "\"";
			}
			InputStream stream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
			cfg.create(stream, true, new NullProgressMonitor());
		} catch (CoreException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base;
	}

	public void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException
	{
		if (sourceLocation.isDirectory())
		{
			if (!targetLocation.exists())
			{
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++)
			{
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		} else
		{

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}

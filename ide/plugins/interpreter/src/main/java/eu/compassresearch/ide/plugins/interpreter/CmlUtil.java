package eu.compassresearch.ide.plugins.interpreter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.overture.ast.node.INode;
import org.overture.ide.core.ICoreConstants;
import org.overture.ide.core.IVdmModel;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.GlobalEnvironmentBuilder;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public final class CmlUtil
{

	public static IProject getCurrentSelectedProject()
	{
		IProject project = null;
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

		ISelection selection = selectionService.getSelection();

		if (selection instanceof IStructuredSelection)
		{
			Object element = ((IStructuredSelection) selection).getFirstElement();

			if (element instanceof IResource)
			{
				project = ((IResource) element).getProject();
			}
			// else if (element instanceof PackageFragmentRoot) {
			// IJavaProject jProject =
			// ((PackageFragmentRoot)element).getJavaProject();
			// project = jProject.getProject();
			// } else if (element instanceof IJavaElement) {
			// IJavaProject jProject= ((IJavaElement)element).getJavaProject();
			// project = jProject.getProject();
			// }
		}
		return project;
	}

	public static List<AProcessDefinition> GetGlobalProcessesFromProject(
			IProject project)
	{
		return CmlUtil.GetGlobalProcessesFromSource(CmlUtil.getCmlAstSourcesFromProject(project));
	}

	public static List<PSource> getPSources(IVdmModel mode)
	{
		List<PSource> sources = new LinkedList<PSource>();
		for (INode n : mode.getRootElementList())
		{
			if (n instanceof PSource)
			{
				sources.add((PSource) n);
			}
		}
		return sources;
	}

	public static List<PSource> getCmlAstSourcesFromProject(IProject project)
	{
		List<PSource> sources = new LinkedList<PSource>();

		try
		{
			for (IResource res : project.members())
			{
				if (res instanceof IFile
						&& ((IFile) res).getFileExtension().toLowerCase().equals("cml"))
				{
					ICmlSourceUnit source = (ICmlSourceUnit) ((IFile) res).getAdapter(ICmlSourceUnit.class);
					PSource ast = source.getSourceAst();

					if (ast != null)
						sources.add(ast);
				}
			}
		} catch (CoreException e)
		{
			e.printStackTrace();
		}

		return sources;
	}

	public static List<String> getCmlSourcesPathsFromProject(IProject project)
	{
		List<String> sources = new LinkedList<String>();

		try
		{
			for (IResource res : project.members())
			{
				if (res instanceof IFile
						&& ((IFile) res).getFileExtension().toLowerCase().equals("cml"))
				{
					ICmlSourceUnit source = (ICmlSourceUnit) ((IFile) res).getAdapter(ICmlSourceUnit.class);
					PSource ast = source.getSourceAst();
					if (ast != null && ast instanceof AFileSource)
						sources.add(((AFileSource) ast).getFile().getCanonicalPath());
				}
			}
		} catch (CoreException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return sources;
	}

	public static String getProjectPath(IProject project)
	{
		String projectPath = project.getFullPath().toOSString();
		// String pat = System.getProperty("user.dir");

		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString();

		return workspacePath + projectPath;
	}

	public static List<AProcessDefinition> GetGlobalProcessesFromSource(
			List<PSource> projectSources)
	{
		if (projectSources.isEmpty())
			return new LinkedList<AProcessDefinition>();

		GlobalEnvironmentBuilder builder = new GlobalEnvironmentBuilder(projectSources);

		return builder.getGlobalProcesses();

	}

	// static public IVdmProject getVdmProject(ILaunchConfiguration configuration)
	// throws CoreException
	// {
	//
	// IProject project = getProject(configuration);
	//
	// if (project != null)
	// {
	// IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);
	// return vdmProject;
	// }
	// return null;
	// }
	public static List<String> collectJars(String bundleId)
	{
		List<String> bundleIds = new ArrayList<String>();
		bundleIds.add(bundleId);

		final Bundle bundle = Platform.getBundle(bundleId);
		if (bundle == null)
		{
			System.out.println("Bundle " + bundleId + " not found.");
			return new ArrayList<String>();
		}

		try
		{
			String requires = (String) bundle.getHeaders().get(Constants.REQUIRE_BUNDLE);
			ManifestElement[] elements = ManifestElement.parseHeader(Constants.REQUIRE_BUNDLE, requires);

			for (ManifestElement manifestElement : elements)
			{
				String value = manifestElement.getValue();
				if (value.startsWith("org.overture")
						|| value.startsWith("eu.compassresearch"))
					bundleIds.add(value);
			}
		} catch (BundleException e)
		{
			return new ArrayList<String>();
		}

		List<String> preliminary = new ArrayList<String>();
		collectClasspath(bundleIds.toArray(new String[] {}), preliminary);

		List<String> filtered = new ArrayList<String>();
		for (String s : preliminary)
		{
			if (s.endsWith(".jar"))
				filtered.add(s);
		}
		return filtered;
	}

	public static void collectClasspath(String[] bundleIds, List<String> entries)
	{
		try
		{
			final boolean developmentMode = Platform.inDevelopmentMode();
			ArrayList<File> fileList = new ArrayList<File>();
			for (int i = 0; i < bundleIds.length; ++i)
			{
				final String bundleId = bundleIds[i];
				getPluginClassPath(bundleId, entries);
				final File file = getPluginLocation(bundleId);
				fileList.add(file);
				for (File f : fileList)
				{
					if (developmentMode && file.isDirectory())
					{
						final File bin = new File(f, "bin"); //$NON-NLS-1$
						if (bin.isDirectory())
						{
							entries.add(bin.getAbsolutePath());
							continue;
						}
					}
					entries.add(f.getAbsolutePath());
				}
			}
		} catch (CoreException e)
		{
			// LaunchingPlugin.error(e);
		}
	}

	private static void getPluginClassPath(String bundleId, List<String> entries)
	{
		try
		{
			final Bundle bundle = Platform.getBundle(bundleId);
			if (bundle == null)
			{
				throw new BundleException(bundleId
						+ " cannot be retrieved from the Platform");
			}

			String requires = (String) bundle.getHeaders().get((Constants.BUNDLE_CLASSPATH));
			if (requires == null)
				requires = ".";
			ManifestElement[] elements = ManifestElement.parseHeader(Constants.REQUIRE_BUNDLE, requires);
			for (ManifestElement manifestElement : elements)
			{
				String value = manifestElement.getValue();
				if (".".equals(value))
					value = "/";

				URL url = bundle.getEntry(value);
				if (url != null)
				{
					URL resolvedFile = FileLocator.resolve(url);
					if ("file".equals(resolvedFile.getProtocol())) //$NON-NLS-1$
					{
						File file = new File(resolvedFile.getPath());
						// adding jar-files
						if (file.isDirectory() == false)
						{
							entries.add(file.getAbsolutePath());
						}
						// System.out.println("addedFile" + resolvedFile.getPath() + " isDir: " + file.isDirectory());
					}
				}
			}
		} catch (Exception e)
		{
			final String msg = NLS.bind(PLUGIN_LOCATION_ERROR, bundleId);
			System.out.println(msg);
		}

	}

	private static File getPluginLocation(String bundleId) throws CoreException
	{
		try
		{
			final Bundle bundle = Platform.getBundle(bundleId);
			return getBundleFile(bundle);
		} catch (IOException e)
		{
			final String msg = NLS.bind(PLUGIN_LOCATION_ERROR, bundleId);
			throw new CoreException(new Status(IStatus.ERROR, ICoreConstants.PLUGIN_ID, IStatus.ERROR, msg, e));
		}
	}

	/**
	 * Returns a file for the contents of the specified bundle. Depending on how the bundle is installed the returned
	 * file may be a directory or a jar file containing the bundle content. XXX 3.3 compatibility =
	 * {@link FileLocator#getBundleFile(Bundle)}
	 * 
	 * @param bundle
	 *            the bundle
	 * @return a file with the contents of the bundle
	 * @throws IOException
	 *             if an error occurs during the resolution
	 */
	public static File getBundleFile(Bundle bundle) throws IOException
	{
		URL rootEntry = bundle.getEntry("/"); //$NON-NLS-1$
		rootEntry = FileLocator.resolve(rootEntry);
		if ("file".equals(rootEntry.getProtocol())) //$NON-NLS-1$
			return new File(rootEntry.getPath());
		if ("jar".equals(rootEntry.getProtocol())) { //$NON-NLS-1$
			String path = rootEntry.getPath();
			if (path.startsWith("file:"))
			{
				// strip off the file: and the !/
				path = path.substring(5, path.length() - 2);
				return new File(path);
			}
		}
		throw new IOException("Unknown protocol"); //$NON-NLS-1$
	}

	// public static String[] getClassPath(IJavaProject myJavaProject) throws IOException, URISyntaxException {
	// final List<String> result = new ArrayList<String>();
	// // TODO ClasspathUtils.collectClasspath(new String[] { GenericOvertureInstallType.EMBEDDED_VDMJ_BUNDLE_ID,
	// GenericOvertureInstallType.DBGP_FOR_VDMJ_BUNDLE_ID }, result);
	// ClasspathUtils.collectClasspath
	// (
	// new String[]
	// {
	// "org.overture.ide.generated.vdmj" //TODO put in constant file
	// //VDMToolsInstallType.DBGP_FOR_VDMTOOLS_BUNDLE_ID,
	// //GenericOvertureInstalltype.DBGP_FOR_VDMTOOLS_BUNDLE_ID,
	// //GenericOvertureInstalltype.DBGP_FOR_ABSTRACT_BUNDLE_ID
	// },
	// result
	// );
	// try {
	// final String[] classPath = computeBaseClassPath(myJavaProject);
	// for (int i = 0; i < classPath.length; ++i) {
	// result.add(classPath[i]);
	// }
	// } catch (CoreException e) {
	// }
	// return (String[]) result.toArray(new String[result.size()]);
	// }
	//
	// protected static String[] computeBaseClassPath(IJavaProject myJavaProject) throws CoreException {
	// if (!myJavaProject.exists())
	// return CharOperation.NO_STRINGS;
	// return JavaRuntime.computeDefaultRuntimeClassPath(myJavaProject);
	// }

	private static final String PLUGIN_LOCATION_ERROR = "Error determining classpath from bundle {0}"; //$NON-NLS-1$

}

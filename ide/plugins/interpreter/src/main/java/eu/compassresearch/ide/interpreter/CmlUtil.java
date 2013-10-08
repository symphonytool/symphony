package eu.compassresearch.ide.interpreter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ide.core.ICoreConstants;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.GlobalEnvironmentBuilder;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

public final class CmlUtil
{

	public static void clearSelections(Map<StyledText, List<StyleRange>> map)
	{
		for (Entry<StyledText, List<StyleRange>> entry : map.entrySet())
		{
			for (StyleRange sr : entry.getValue())
			{
				sr.background = null;
				if (!entry.getKey().isDisposed())
				{
					entry.getKey().setStyleRange(sr);
				}
			}
			entry.getValue().clear();
		}
	}

	public static void clearAllSelections()
	{
		IEditorPart editor = null;
		IWorkbenchWindow wbw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (wbw != null)
			editor = wbw.getActivePage().getActiveEditor();

		if (editor != null && editor instanceof CmlEditor)
		{
			StyledText styledText = (StyledText) ((CmlEditor) editor).getAdapter(Control.class);
			for (StyleRange sr : styledText.getStyleRanges())
			{
				sr.background = null;
				styledText.setStyleRange(sr);
			}
		}
	}

	public static CmlDebugTarget findCmlDebugTarget()
	{
		for (IDebugTarget f : DebugPlugin.getDefault().getLaunchManager().getDebugTargets())
			if (f instanceof CmlDebugTarget && f.isSuspended())
				return (CmlDebugTarget) f;

		return null;
	}

	private static void setSelectionFromLocation(ILexLocation loc,
			List<StyleRange> lastSelectedRanges, StyledText styledText)
	{

		int length = loc.getEndOffset() - loc.getStartOffset() + 1;
		StyleRange sr = styledText.getStyleRangeAtOffset(loc.getStartOffset());

		// if nothing is found we try to look nearby
		if (sr == null)
			for (int i = loc.getStartOffset() - 50; i < loc.getStartOffset() + 50; i++)
			{
				sr = styledText.getStyleRangeAtOffset(i);
				if (sr != null)
					break;
			}

		if (sr != null)
		{
			sr.length = length;
			sr.background = new Color(null, new RGB(java.awt.Color.GRAY.getRed(), java.awt.Color.GRAY.getGreen(), java.awt.Color.GRAY.getBlue()));
			styledText.setStyleRange(sr);
			lastSelectedRanges.add(sr);
		}
	}

	private static IEditorPart findEditorFromLocation(ILexLocation loc)
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath location = Path.fromOSString(loc.getFile().getAbsolutePath());
		IFile file = workspace.getRoot().getFileForLocation(location);
		// It may be a linked resource
		if (file == null
				&& workspace.getRoot().findFilesForLocation(location).length > 0)
			file = workspace.getRoot().findFilesForLocation(location)[0];
		IEditorPart editor = null;
		try
		{
			IWorkbenchWindow wbw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			editor = IDE.openEditor(wbw.getActivePage(), file, true);
		} catch (PartInitException e)
		{
			e.printStackTrace();
		}

		return editor;
	}

	public static void setSelectionFromLocation(ILexLocation loc,
			Map<StyledText, List<StyleRange>> map)
	{
		IEditorPart editor = findEditorFromLocation(loc);

		if (editor != null)
		{
			StyledText styledText = (StyledText) ((CmlEditor) editor).getAdapter(Control.class);

			List<StyleRange> lastSelectedRanges = null;

			if (map.containsKey(styledText))
			{
				lastSelectedRanges = map.get(styledText);
			} else
			{
				lastSelectedRanges = new LinkedList<StyleRange>();
				map.put(styledText, lastSelectedRanges);
			}

			setSelectionFromLocation(loc, lastSelectedRanges, styledText);
		}
	}

	public static void setSelectionFromLocations(
			List<ILexLocation> lexLocations,
			Map<StyledText, List<StyleRange>> map)
	{
		for (ILexLocation loc : lexLocations)
		{
			setSelectionFromLocation(loc, map);
		}
	}

	public static void showLocation(StyledText st, ILexLocation loc)
	{
		st.setCaretOffset(loc.getStartOffset());
		st.showSelection();
	}

	public static List<AProcessDefinition> getGlobalProcessesFromSource(
			List<PSource> projectSources)
	{
		if (projectSources.isEmpty())
			return new LinkedList<AProcessDefinition>();

		try
		{
			GlobalEnvironmentBuilder builder = new GlobalEnvironmentBuilder(projectSources);

			return builder.getGlobalProcesses();
		} catch (AnalysisException e)
		{
			e.printStackTrace();
		}

		return new Vector<AProcessDefinition>();

	}

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
			System.err.println(msg);
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

	private static final String PLUGIN_LOCATION_ERROR = "Error determining classpath from bundle {0}"; //$NON-NLS-1$

}

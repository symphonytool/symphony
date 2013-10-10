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
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.osgi.util.ManifestElement;
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

	public static String[] collectRequiredBundleIds(String bundleId)
	{
		List<String> bundleIds = new ArrayList<String>();
		bundleIds.add(bundleId);

		final Bundle bundle = Platform.getBundle(bundleId);
		if (bundle == null)
		{
			System.out.println("Bundle " + bundleId + " not found.");
			return null;
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
			return null;
		}
		return bundleIds.toArray(new String[] {});
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


}

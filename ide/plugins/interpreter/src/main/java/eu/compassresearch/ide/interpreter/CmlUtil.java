package eu.compassresearch.ide.interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.preference.PreferenceConverter;
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
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ast.definitions.AProcessDefinition;
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
		{
			editor = wbw.getActivePage().getActiveEditor();
		}

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
		{
			if (f instanceof CmlDebugTarget && f.isSuspended())
			{
				return (CmlDebugTarget) f;
			}
		}

		return null;
	}

	private static void setSelectionFromLocation(ILexLocation loc,
			List<StyleRange> lastSelectedRanges, StyledText styledText)
	{

		StyleRange sr = styledText.getStyleRangeAtOffset(loc.getStartOffset());

		if (sr != null)
		{
			int length = loc.getEndOffset() - loc.getStartOffset() + 1;
			sr.length = length;
			RGB colorRGB = PreferenceConverter.getColor(CmlDebugPlugin.getDefault().getPreferenceStore(), ICmlDebugConstants.PREFERENCES_DEBUG_HIGHLIGHT_COLOR);
			Color color = new Color(null, colorRGB);
			sr.background = color;
			styledText.setStyleRange(sr);
			lastSelectedRanges.add(sr);
		} else
		{
			String message = "Unable to obtain styled text for location: "
					+ loc + " not found in editor offset: "
					+ loc.getStartOffset();
			System.err.println(message);
			CmlDebugPlugin.logWarning(message);
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
		{
			file = workspace.getRoot().findFilesForLocation(location)[0];
		}
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
			List<PDefinition> definitions)
	{
		List<AProcessDefinition> processes = new Vector<AProcessDefinition>();
		if (definitions.isEmpty())
		{
			return processes;
		}

		for (PDefinition def : definitions)
		{
			if (def instanceof AProcessDefinition)
			{
				processes.add((AProcessDefinition) def);
			}
		}

		return processes;

	}

}

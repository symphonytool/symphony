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
package eu.compassresearch.ide.ui.editor.core;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.part.FileEditorInput;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.ui.editor.core.VdmEditor;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;
import org.overture.ide.ui.internal.viewsupport.DecorationgVdmLabelProvider;
import org.overture.ide.ui.outline.VdmContentOutlinePage;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.editor.syntax.INodeFromCaret;
import eu.compassresearch.ide.ui.navigator.CmlTreeContentProvider;
import eu.compassresearch.ide.ui.navigator.CmlUiLabelProvider;
import eu.compassresearch.ide.ui.utility.ast.CmlAstLocationSearcher;

public class CmlEditor extends VdmEditor
{
	public CmlEditor()
	{
		super();
		this.locationSearcher = new ILocationSearcher()
		{

			@Override
			public INode search(List<INode> nodes, int offSet)
			{
				return CmlAstLocationSearcher.search(nodes, offSet);
			}

			@Override
			public int[] getNodeOffset(INode node)
			{
				return CmlAstLocationSearcher.getNodeOffset(node);
			}
		};
	}

	@Override
	protected VdmSourceViewerConfiguration getVdmSourceViewerConfiguration(
			IPreferenceStore fPreferenceStore)
	{
		return new CmlSourceViewerConfiguration();
	}

	//FIXME: we need a new extension in overture to do this now - caused by a fix for kepler in overture
//	protected VdmContentOutlinePage createOutlinePage()
//	{
//		VdmContentOutlinePage page = super.createOutlinePage();
//
//		page.configure(new CmlTreeContentProvider(), new DecorationgVdmLabelProvider(new CmlUiLabelProvider()));
//		return page;
//	}

	@Override
	protected ISelectionChangedListener createOutlineSelectionChangedListener()
	{
		return new ISelectionChangedListener()
		{

			@SuppressWarnings("rawtypes")
			public void selectionChanged(SelectionChangedEvent event)
			{

				ISelection s = event.getSelection();
				if (s instanceof IStructuredSelection)
				{
					IStructuredSelection ss = (IStructuredSelection) s;
					List elements = ss.toList();
					if (!elements.isEmpty())
					{
						Object firstSelection = elements.get(0);
						
						if (firstSelection instanceof INode)
						{
							INode node = (INode) firstSelection;
							selectAndReveal(node);
						}
					}
				}
			}
		};
	}

	/**
	 * Selects a node existing within the ast presented by the editor
	 * 
	 * @param node
	 */
	public void selectAndReveal(INode node)
	{
		int[] offsetLength = locationSearcher.getNodeOffset(node);
		if(offsetLength!=CmlAstLocationSearcher.NO_LOCATION)
		{
			selectAndReveal(offsetLength[0], offsetLength[1]);
		}
	}

	protected INode computeHighlightRangeSourceReference()
	{

		// FIXME if the AST is just the source node return null
		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer == null)
			return null;

		StyledText styledText = sourceViewer.getTextWidget();
		if (styledText == null)
			return null;

		int caret = 0;
		if (sourceViewer instanceof ITextViewerExtension5)
		{
			ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
			caret = extension.widgetOffset2ModelOffset(styledText.getCaretOffset());
		} else
		{
			int offset = sourceViewer.getVisibleRegion().getOffset();
			caret = offset + styledText.getCaretOffset();
		}
		INode element = getElementAt(caret, false);

		return element;
	}

	@Override
	protected void synchronizeOutlinePage(INode element)
	{
		super.synchronizeOutlinePage(element);
	}

	//
	protected INode getElementAt(int caret, boolean b)
	{
		FileEditorInput fei = (FileEditorInput) getEditorInput();
		INode r = null;
		// FIXME get source unit, update implementation
		ICmlSourceUnit csu = (ICmlSourceUnit) fei.getFile().getAdapter(ICmlSourceUnit.class);
		if (csu == null || csu.hasParseErrors() || !csu.hasParseTree())
		{
			return null;
		}
		List<PDefinition> ast = csu.getParseListDefinitions();

		// FIXME is this correct ast.get(0)
		INodeFromCaret visitor = new INodeFromCaret(caret, ast.get(0));
		try
		{
			for (PDefinition def : ast)
			{
				def.apply(visitor);
			}
			// ast.apply(visitor);
			return visitor.getBestCandidate();
		} catch (AnalysisException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;
	}

}

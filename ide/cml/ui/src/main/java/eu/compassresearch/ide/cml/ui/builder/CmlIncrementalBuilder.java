package eu.compassresearch.ide.cml.ui.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.commands.CommandService;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeWarning;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlIncrementalBuilder extends IncrementalProjectBuilder {

	/*
	 * Run the type checker.
	 */

	private static void setProblem(IMarker marker, String text, int... more)
			throws CoreException {
		marker.setAttribute(IMarker.MESSAGE, text);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		if (more.length == 2)
		{
			marker.setAttribute(IMarker.CHAR_START, more[0]);
			marker.setAttribute(IMarker.CHAR_END, more[1]);
		}
	}

	private static void setWarning(IMarker marker, String text, int... more) throws CoreException
	{
		marker.setAttribute(IMarker.MESSAGE,text);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		if (more.length == 2)
		{
			marker.setAttribute(IMarker.CHAR_START, more[0]);
			marker.setAttribute(IMarker.CHAR_END, more[1]);
		}
	}


	private synchronized static boolean typeCheck(IProject project, Map<PSource,IFile> sourceToFileMap)
	{
		if (project == null) return false;
		if (sourceToFileMap == null) return false;

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(sourceToFileMap.keySet(), issueHandler);
		try {
			boolean result =  typeChecker.typeCheck();
			// set error markers
			for(CMLTypeError error : issueHandler.getTypeErrors())
			{
				INode offendingNode = error.getOffendingNode();
				if (offendingNode != null)
				{
					PSource source = ( offendingNode instanceof PSource ? (PSource)offendingNode : offendingNode.getAncestor(PSource.class));
					if (source != null)
					{
						IFile file = sourceToFileMap.get(source);
						if (file != null)
						{
							IMarker errorMarker = file.createMarker(IMarker.PROBLEM);
							LexLocation loc = error.getLocation();
							if (loc != null)
								setProblem(errorMarker,error.getDescription(), loc.startOffset, loc.endOffset);
							else
							{
								setProblem(errorMarker,error.getDescription(), 1,1);
								setWarning(project.createMarker(IMarker.PROBLEM), "AstNode: "+offendingNode+" has null location.");
							}
						}
						else
							System.out.println("No IFile resource found for source: "+source);
					}
					else
						System.out.println("Could not find source for: "+offendingNode);
				}
				else
					System.out.println("Error messages with null node: "+error);
			}

			// set warning markers
			for(CMLTypeWarning warning : issueHandler.getTypeWarnings())
			{
				INode offendingNode = warning.getOffendingNode();
				PSource source = offendingNode.getAncestor(PSource.class);
				IFile file = sourceToFileMap.get(source);
				IMarker errorMarker = file.createMarker(IMarker.PROBLEM);
				LexLocation loc = warning.getLocation();
				if (loc != null )
					setWarning(errorMarker,warning.getDescription(), loc.startOffset, loc.endOffset);

			}

			return result;
		} catch (Exception e)
		{	
			try {
				IMarker projectMarker = project.createMarker(IMarker.PROBLEM);
				setProblem(projectMarker, "Type checking on this project failed.");
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}

	}




	private IProject[] buildit() throws CoreException {
		// Remove all markers from project
		getProject().deleteMarkers(IMarker.PROBLEM, true,
				IResource.DEPTH_INFINITE);

		// Create a visitor
		CmlBuildVisitor buildVisitor = new CmlBuildVisitor();

		// get project
		IProject project = getProject();

		// run the parser on every cml file in the project
		project.accept(buildVisitor);

		// Type Check all sources in this project
		Collection<CmlSourceUnit> allSourceUnits = CmlSourceUnit.getAllSourceUnits();
		Map<PSource,IFile> sourceToFileMap = new HashMap<PSource,IFile>();
		for(CmlSourceUnit sourceUnit : allSourceUnits)
		{
			if (sourceUnit.isParsedOk() && 
					sourceUnit.getFile().getProject() == project 
					&& sourceUnit.getSourceAst() != null)
				sourceToFileMap.put(sourceUnit.getSourceAst(), sourceUnit.getFile());
		}
		typeCheck(project,sourceToFileMap);

		
		
		
		// Return the projects that should be build also as result of rebuilding
		// this
		return null;

	}

	@Override
	protected void startupOnInitialize() {
		super.startupOnInitialize();
		try {
			super.forgetLastBuiltState();
			buildit();
		} catch (CoreException e) {

		}
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args,
			IProgressMonitor monitor) throws CoreException {
		return buildit();
	}

}

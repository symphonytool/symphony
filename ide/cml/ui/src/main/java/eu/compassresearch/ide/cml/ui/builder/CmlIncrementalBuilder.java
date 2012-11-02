package eu.compassresearch.ide.cml.ui.builder;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.overture.parser.messages.VDMMessage;
import org.overture.typechecker.TypeChecker;

public class CmlIncrementalBuilder extends IncrementalProjectBuilder
  {
    
    @Override
    protected IProject[] build(int kind, Map<String, String> args,
        IProgressMonitor monitor) throws CoreException
      {
    	// Remove all markers from project
    	getProject().deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

        // Create a visitor
        CmlBuildVisitor buildVisitor = new CmlBuildVisitor();
        
        // For now we build everything every time
        getProject().accept(buildVisitor);
        
        // Return the projects that should be build also as result of rebuilding
        // this
        return null;
      }
    
  }

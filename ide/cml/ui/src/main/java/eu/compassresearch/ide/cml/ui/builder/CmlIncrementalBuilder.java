package eu.compassresearch.ide.cml.ui.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class CmlIncrementalBuilder extends IncrementalProjectBuilder
  {
    
    @Override
    protected IProject[] build(int kind, Map<String, String> args,
        IProgressMonitor monitor) throws CoreException
      {
        
        // Create a visitor
        CmlBuildVisitor buildVisitor = new CmlBuildVisitor();
        
        // For now we build everything every time
        getProject().accept(buildVisitor);        
        
        // Return the projects that should be build also as result of rebuilding
        // this
        return null;
      }
    
  }

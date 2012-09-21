package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.node.INode;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * Very simple LinkedList based TypeIssueHandler that simply collects errors and
 * warnings for future retrieval.
 * 
 * @author rwl
 * 
 */
class CollectingIssueHandler implements TypeIssueHandler
  {
    
    CollectingIssueHandler()
      {
        errors = new LinkedList<CMLTypeError>();
        warnings = new LinkedList<CMLTypeWarning>();
      }
    
    private final List<CMLTypeError>   errors;
    private final List<CMLTypeWarning> warnings;
    
    @Override
    public List<CMLTypeError> getTypeErrors() throws IllegalStateException
      {
        return errors;
      }
    
    @Override
    public List<CMLTypeWarning> getTypeWarnings() throws IllegalStateException
      {
        return warnings;
      }
    
    @Override
    public void addTypeError(INode offendingSubtree, String message)
      {
        this.errors.add(new CMLTypeError(offendingSubtree, message));
      }
    
    @Override
    public void addTypeWarning(INode hazardousSubtree, String message)
      {
        this.warnings.add(new CMLTypeWarning(hazardousSubtree, message));
      }
    
    @Override
    public boolean hasErrors()
      {
        return !errors.isEmpty();
      }
    
    @Override
    public boolean hasWarnings()
      {
        return !warnings.isEmpty();
      }
    
    @Override
    public boolean hasIssues()
      {
        return hasErrors() || hasWarnings();
      }
    
  }

package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.node.INode;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PType;

/**
 * 
 * @author rwl
 * 
 *         Abstract type checker place holder for errors and warnings.
 * 
 *         Additionally, the cleared state is facilitated by providing a cleared
 *         flag. It is up to an implementation to enforce the cleared flag
 *         throwing an IllegalStateException if getTypeErrors or getTypeWarnings
 *         are invoked inappropriately.
 * 
 *         Also a place holder for the sourceForest is provided. However, it is
 *         up to the implementation to actually load and set the sourceForest.
 * 
 *         The constructor is kept package scoped as this class is intimate to
 *         the eu.compassresearch.core.typechecker package.
 */
@SuppressWarnings("serial")
public abstract class AbstractTypeChecker extends
    QuestionAnswerAdaptor<TypeCheckQuestion, PType> implements CmlTypeChecker
  {
    
    protected List<CmlTypeChecker.CMLTypeError>   errors;
    protected List<CmlTypeChecker.CMLTypeWarning> warnings;
    protected boolean                             cleared;
    protected List<PSource>                       sourceForest;
    
    AbstractTypeChecker()
      {
        clear();
      }
    
    /**
     * Report a type error has been found for the given location.
     * 
     * @param offendingSubtree
     *          - The offending AST node
     * 
     * @param message
     *          - A message detailing the nature of the the error and preferably
     *          hinting how to fix it.
     */
    public void addTypeError(INode offendingSubtree, String message)
      {
        CMLTypeError error = new CMLTypeError(offendingSubtree, message);
        errors.add(error);
      }
    
    /**
     * Return a type warning.
     * 
     * @param hazardousSubtree
     *          - The subtree found to be inhibiting an ill shape.
     * @param message
     *          - A message detailing the kind of check made to trigger this
     *          warning.
     * 
     */
    void addTypeWarning(INode hazardousSubtree, String message)
      {
        warnings.add(new CMLTypeWarning(hazardousSubtree, message));
      }
    
    /**
     * Clear out all warnings and errors and resets the type checker.
     */
    void clear()
      {
        errors = new LinkedList<CMLTypeError>();
        warnings = new LinkedList<CMLTypeWarning>();
        cleared = true;
        sourceForest = null;
      }
    
  }

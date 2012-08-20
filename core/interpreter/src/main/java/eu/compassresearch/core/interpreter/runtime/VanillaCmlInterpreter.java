package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.program.PSource;

public class VanillaCmlInterpreter extends AbstractCmlInterpreter {

	
	/**
     * Construct a CmlTypeChecker with the intension of checking a list of
     * PSources. These source may refer to each other.
     * 
     * 
     * @param cmlSources
     *          - Source containing CML Paragraphs for type checking.
     */
    public VanillaCmlInterpreter(List<PSource> cmlSources)
      {
//    	initialize();
        this.sourceForest = cmlSources;
      }
    
    /**
     * Construct a CmlTypeChecker with the intension of checking a single
     * source.
     * 
     * @param singleSource
     */
    public VanillaCmlInterpreter(PSource singleSource)
      {
        //initialize();
        this.sourceForest = new LinkedList<PSource>();
        this.sourceForest.add(singleSource);
      }
	
	public String getAnalysisName()
    {
      return "The CML Interpreter";
    }
}

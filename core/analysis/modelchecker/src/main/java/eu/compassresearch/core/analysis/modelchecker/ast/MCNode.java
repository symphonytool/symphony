package eu.compassresearch.core.analysis.modelchecker.ast;

/**
 * A MCNode represents a node manipulated by the model checker visitors. They must be convertible
 * to formula and are wrappers a specific CML ast node. The idea is that the visitors 
 * create these nodes when visiting an ast. At the end, a root node is converted to formula at once.
 **/
 public interface MCNode {
 
	//the default translation
	public static final String DEFAULT = "DEFAULT";
	
	//the generic translation (with underscore)
	public static final String GENERIC = "GENERIC";
	
	//the named translation (with variable name)
	public static final String NAMED = "NAMED";
		
	public String toFormula(String option);
	
}

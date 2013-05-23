package eu.compassresearch.core.typechecker.api;

import org.overture.ast.types.PType;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AErrorType;

/**
 * 
 * @author rwl
 * 
 *         A CML Type checker has the characteristics of this interface.
 * 
 */
public interface CmlTypeChecker {

	/**
	 * This is once and for all how we determine if a Cml Source is well typed.
	 * 
	 * @param sourceNode
	 *            - The Source to check
	 * @return boolean - true if the sourceNode has a proper type.
	 */
	public static class Utils {
		public static boolean isWellType(PSource sourceNode) {

			if (sourceNode == null)
				return false;

			PType type = sourceNode.getType();
			if (type == null)
				return false;

			if (type instanceof AErrorType)
				return false;

			return true;
		}
	}

	/**
	 * Run the type checker. This will update the source(s) this type checker
	 * instance was constructed with.
	 * 
	 * @return - Returns true if the entire tree could be type checked without
	 *         errors. It returns false otherwise and courses to failing the
	 *         type check can be inspected though getErrors.
	 */
	public boolean typeCheck();

	/**
	 * Return a pretty name for this type checker user-interactive tools can use
	 * for printing.
	 * 
	 * @return - analysis pretty name.
	 */
	public String getAnalysisName();
}

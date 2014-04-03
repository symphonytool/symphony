package eu.compassresearch.core.typechecker.api;

import org.overture.ast.expressions.PExp;
import org.overture.ast.messages.InternalException;
import org.overture.ast.types.PType;

/**
 * @author rwl A CML Type checker has the characteristics of this interface.
 */
public interface ICmlTypeChecker
{
	public final static String GLOBAL_CLASS_NAME = "$global";

	public class AbortTypecheck extends InternalException
	{

		public AbortTypecheck(int number, String message)
		{
			super(number, message);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 789485822073572522L;

	}

	/**
	 * Run the type checker. This will update the source(s) this type checker instance was constructed with.
	 * 
	 * @return - Returns true if the entire tree could be type checked without errors. It returns false otherwise and
	 *         courses to failing the type check can be inspected though getErrors.
	 */
	public boolean typeCheck();

	public PType typeCheck(PExp expr) throws Exception;

	/**
	 * Return a pretty name for this type checker user-interactive tools can use for printing.
	 * 
	 * @return - analysis pretty name.
	 */
	public String getAnalysisName();
}

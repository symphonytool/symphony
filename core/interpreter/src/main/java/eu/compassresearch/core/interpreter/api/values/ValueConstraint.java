package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.values.Value;

public interface ValueConstraint
{

	public boolean isValid(Value val) throws AnalysisException;
}

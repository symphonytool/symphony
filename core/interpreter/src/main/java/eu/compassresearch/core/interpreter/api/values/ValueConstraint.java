package eu.compassresearch.core.interpreter.api.values;

import java.io.Serializable;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.values.Value;

public interface ValueConstraint extends Serializable
{

	public boolean isValid(Value val) throws AnalysisException;
}

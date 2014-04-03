package eu.compassresearch.ide.refinementtool;

import java.util.List;

import org.overture.ast.expressions.PExp;

public interface IRefinement {
	public String getResult();
	public List<PExp> getProvisos();
}

package eu.compassresearch.ide.core.unsupported;

public enum UnsupportingFeatures
{
	POG("Proof Obligation Generator"),
	TP("Theorem Prover"),
	MC("Model Checker");
	
	private String featurename;

	UnsupportingFeatures (String featurename)
	{
		this.featurename=featurename;
	}
	
	@Override
	public String toString()
	{
		return featurename;
	}
	
}

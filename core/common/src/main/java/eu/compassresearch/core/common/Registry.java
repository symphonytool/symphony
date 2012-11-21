package eu.compassresearch.core.common;

import java.util.Map;

import org.overture.ast.node.INode;

public interface Registry {

	/**
	 * 
	 * Look up in the registry any K artifact of class K Registered for the
	 * given astNode.
	 * 
	 **/
	public <K extends AnalysisArtifact> K lookup(INode astNode, Class<K> clz);

	/**
	 * Recursively down the tree structure find Artifacts of class K and return
	 * a map from Node to Artifact.
	 * 
	 * Notice result.keySet() will give all the nodes having K and
	 * result.valueSet() will give all the K artifacts.
	 */
	public <K extends AnalysisArtifact> Map<INode, K> lookupRecursively(
			INode root, Class<K> clz);

	/**
	 * 
	 * Adds artifact to the registry for the given astNode.
	 * 
	 */
	public <K extends AnalysisArtifact> void put(INode astNode, K artifact);
}

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
	 * 
	 * 
	 * @return true if the artifact is successfully inserted into the registry, 
	 * false otherwise.
	 */
	public <K extends AnalysisArtifact> boolean store(INode astNode, K artifact);
	
	
	/**
	 * Clear the registry for a certain type of artifacts.
	 * E.g. before type checker all type errors and warnings
	 * are pruned from the registry.
	 * 
	 * @param artifact - the class of artifact to remove all instances of 
	 *        referenced in this registry.
	 */
	public <K extends AnalysisArtifact> void prune(Class<K> artifactClz);
	
}

package eu.compassresearch.core.common;

public class Registry {
  
  /**
   *
   * Look up in the registry any K artifact of class
   * K Registered for the given astNode.
   *
   **/
  public <K extends AnalysisArtifact> 
  K lookup(INode astNode, Class<K> clz);
  
  /**
   * Recursively down the tree structure find Artifacts 
   * of class K and return a map from Node to Artifact.
   *
   * Notice result.keySet() will give all the nodes having K
   * and result.valueSet() will give all the K artifacts.
   */
  public <K extends AnalysisArtifact>
  Map<INode, K>  lookupRecursively(INode root, Class<K> clz);
  
  /**
   *
   * Adds artifact to the registry for the given astNode.
   *
   */
  public <K extends AnalysisArtifact>
  void put(INode astNode, K artifact);
}

class PO implements AnalysisArtifact {
  // 
  public INode node;
  
  boolean migrate(INode node, Registry registry);
}


class AstClient {
  
  public PoList caseASomeKindOfExpression(IASomeKindOfExpression astNode, Question q)
    {
      Registry registry = new Registry();
      register.put(astNode, new PO(<...>));
      
    }
  
  
  public void someTimeLater(Registry registry, INode subTree)
    {
      List<Pair<INode,PO>> allPOsInSubTree = registry.lookupRecursively(subTree, PO.class);
      
    }
  
}
}


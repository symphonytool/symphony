package eu.compassresearch.core.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;

import org.overture.ast.node.GraphNodeList;
import org.overture.ast.node.GraphNodeListList;
import org.overture.ast.node.INode;
import org.overture.ast.node.NodeList;
import org.overture.ast.node.NodeListList;

import eu.compassresearch.ast.program.PSource;

/**
 * The Registry is singleton of the RegistryFactory instance. For testability
 * and loose coupling the Registry is not going to be a static singleton.
 * 
 * 
 * 
 * @author rwl
 * 
 * 
 */
public class RegistryFactory {

	private static final String DEFAULT_FACTORY_NAME = "Default";
	private static Map<String, RegistryFactory> instanceMap = new HashMap<String, RegistryFactory>();

	/**
	 * Get a fresh Registry Factory the registry instance.
	 * 
	 * With no arguments given this function gives back the default instance. An
	 * alternative registry can be created if a name is provided.
	 * 
	 * Canonical Usage:
	 * 
	 * ==============
	 * 
	 * RegistryFactory rf = RegistryFactory.getInstance();
	 * 
	 * Registry reg = rf.getRegistry();
	 * 
	 * Special Usage:
	 * 
	 * ==============
	 * 
	 * RegistryFactory testRegistry = RegistryFactory.getInstance("test 42");
	 * 
	 * Registry freshRegistry = rf.getRegistry();
	 * 
	 * RegistryFactory testRegistry = RegistryFactory.getInstance("test 43");
	 * 
	 * Registry freshRegistry = rf.getRegistry();
	 * 
	 * And so on...
	 * 
	 * @return
	 */
	public synchronized static RegistryFactory getInstance(String... id) {
		String factoryInstanceName = null;

		if (id.length < 1)
			factoryInstanceName = DEFAULT_FACTORY_NAME;
		else
			factoryInstanceName = id[0];

		if (!instanceMap.containsKey(factoryInstanceName))
			instanceMap.put(factoryInstanceName, new RegistryFactory());

		return instanceMap.get(factoryInstanceName);
	}

	// The instance of the global registry
	private Registry instance;

	// Private constructor
	private RegistryFactory() {
		instance = new CMLRegistry();
	}

	/**
	 * Get the currently shared instance of the registry for this factory.
	 * 
	 * @return
	 */
	public synchronized Registry getRegistry() {
		return instance;
	}

	private static class NodeClassPair<C extends AnalysisArtifact> {
		public INode node;
		public Class<C> clz;

		public NodeClassPair() {
		}

		public NodeClassPair(INode node, Class<C> clz) {
			this.node = node;
			this.clz = clz;
		}

		@Override
		public int hashCode() {
			int nodeHash = (node == null ? 0 : node.hashCode());
			int clzHash = (clz == null ? 0 : clz.hashCode());
			return nodeHash + clzHash;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof NodeClassPair) {
				NodeClassPair<C> other = (NodeClassPair<C>) obj;

				boolean nodeSame = (this.node == null && other.node == null)
						|| (this.node != null && other.node != null && this.node
						.equals(other.node));

				boolean clzSame = (this.clz == null && other.clz == null)
						|| (this.clz != null && other.clz != null && this.clz
						.equals(other.clz));

				return nodeSame && clzSame;

			} else
				return false;
		}

		@Override
		public String toString() {

			String nodeStr = (node == null ? "null" : node.toString());
			String clzStr = (clz == null ? "null" : clz.toString());

			return "Node Class Pair [Node=\"" + nodeStr + "\"; Class=\""
			+ clzStr + "\"";
		}

	}

	private static class CMLRegistry implements Registry {

		private static PSource getRoot(INode node)
		{
			Set<INode> seen = new HashSet<INode>();
			INode current = node;
			while (current != null)
			{
				// cycle detection
				if (seen.contains(current))
					return null;
				
				// is it a top level node?
				if (current instanceof PSource)
					return (PSource)current;
				
				//mark this node as seen
				seen.add(current);
				
				// go up the tree
				current=current.parent();
			}
			return null;
		}

		private WeakHashMap<PSource, Map<NodeClassPair<AnalysisArtifact>,AnalysisArtifact>> masterMap =new WeakHashMap<PSource, Map<NodeClassPair<AnalysisArtifact>,AnalysisArtifact>>();
		// private HashMap<NodeClassPair<AnalysisArtifact>, AnalysisArtifact> map = new HashMap<NodeClassPair<AnalysisArtifact>, AnalysisArtifact>();

		@SuppressWarnings("unchecked")
		@Override
		public <K extends AnalysisArtifact> K lookup(INode astNode, Class<K> clz) {
			NodeClassPair<AnalysisArtifact> key = new NodeClassPair<AnalysisArtifact>(
					astNode, (Class<AnalysisArtifact>) clz);

			PSource root = getRoot(astNode);
			if (root == null)
				return null;

			Map<NodeClassPair<AnalysisArtifact>,AnalysisArtifact> map = masterMap.get(root);
			if (map == null)
				return null;

			// Unchecked cast is all right as we cannot add something which is
			// wrong. E.g. (Node,PogArtifact) -> ProffArtifact
			return (K) map.get(key);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <K extends AnalysisArtifact> Map<INode, K> lookupRecursively(
				INode root, Class<K> clz) {
			Map<INode, K> result = new HashMap<INode, K>();

			// Breath first pre-order traversal. (Changing add to push will make
			// this a Depth first traversal, however there is no pushAll and
			// hence it is a bit inconvenient to implement.)
			Stack<INode> workQ = new Stack<INode>();
			workQ.add(root);
			while (!workQ.isEmpty()) {

				// Visit node
				INode currentNode = workQ.pop();

				K artifact = lookup(currentNode, clz);
				if (artifact != null)
					result.put(currentNode, artifact);

				// put all children on the workQ
				Map<String, Object> e = currentNode.getChildren(true);

				for (Object node : e.values()) {

					if (node instanceof NodeList) {
						workQ.addAll((NodeList<INode>) node);
					}

					if (node instanceof GraphNodeList) {
						workQ.addAll((GraphNodeList<INode>) node);
					}

					if (node instanceof GraphNodeListList) {
						GraphNodeListList<INode> listList = (GraphNodeListList<INode>) node;
						for (List<INode> list : listList)
							workQ.addAll(list);
					}

					if (node instanceof NodeListList) {
						NodeListList<INode> listList = (NodeListList<INode>) node;
						for (List<INode> list : listList)
							workQ.addAll(list);
					}

					if (node instanceof INode) {
						INode n = (INode) node;
						workQ.add(n);
					}
				}
			}

			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <K extends AnalysisArtifact> boolean store(INode astNode, K artifact) {
			NodeClassPair<AnalysisArtifact> key = ((NodeClassPair<AnalysisArtifact>) new NodeClassPair<K>());
			key.node = astNode;
			key.clz = (Class<AnalysisArtifact>) artifact.getClass();

			PSource root = getRoot(astNode);
			if (root == null)
				return false;

			Map<NodeClassPair<AnalysisArtifact>,AnalysisArtifact>  map = masterMap.get(root);
			if (map == null) { map = new HashMap<RegistryFactory.NodeClassPair<AnalysisArtifact>, AnalysisArtifact>(); masterMap.put(root, map);}

			map.put(key, artifact);

			return true;
		}

		@Override
		public <K extends AnalysisArtifact> void prune(Class<K> artifact) {

			Collection<Map<NodeClassPair<AnalysisArtifact>, AnalysisArtifact>> maps = masterMap.values();

			for(Map<NodeClassPair<AnalysisArtifact>, AnalysisArtifact> map : maps)
			{
				Set<NodeClassPair<AnalysisArtifact>> toberemoved = new HashSet<RegistryFactory.NodeClassPair<AnalysisArtifact>>();
				Set<NodeClassPair<AnalysisArtifact>> allKeys = map.keySet();
				for(NodeClassPair<AnalysisArtifact> key : allKeys)
				{
					if (key.clz == artifact)
						toberemoved.add(key);
				}

				for(NodeClassPair<AnalysisArtifact> key : toberemoved)
					map.remove(key);
			}
		}

	}

}

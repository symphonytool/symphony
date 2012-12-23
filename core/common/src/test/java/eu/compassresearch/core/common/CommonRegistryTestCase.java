package eu.compassresearch.core.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.ast.node.NodeEnum;

import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.core.common.AnalysisArtifact;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;

public class CommonRegistryTestCase {

	@SuppressWarnings("serial")
	class DummyAstNode extends Node {

		private List<INode> children = new LinkedList<INode>();

		DummyAstNode() {

		}

		DummyAstNode(INode[] children) {
			this.children.addAll(Arrays.asList(children));
		}

		@Override
		public Map<String, Object> getChildren(Boolean includeInheritedFields) {
			HashMap<String, Object> result = new HashMap<String, Object>();
			for (int i = 0; i < children.size(); i++)
				result.put("" + i, children.get(i));
			return result;
		}

		@Override
		public Object clone() {
			return null;
		}

		@Override
		public INode clone(Map<INode, INode> oldToNewMap) {
			return null;
		}

		@Override
		public NodeEnum kindNode() {
			return null;
		}

		@Override
		public void removeChild(INode child) {

		}

		@Override
		public void apply(IAnalysis analysis) throws AnalysisException {

		}

		@Override
		public <A> A apply(IAnswer<A> caller) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <Q> void apply(IQuestion<Q> caller, Q question)
				throws AnalysisException {
			// TODO Auto-generated method stub

		}

		@Override
		public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class DummyArtifact implements AnalysisArtifact {

	}

	@Test
	public void instantiation() {
		RegistryFactory rf = RegistryFactory.getInstance();
		Registry reg = rf.getRegistry();
		Assert.assertNotNull(reg);
	}

	@Test
	public void addAnElement() {

		RegistryFactory rf = RegistryFactory.getInstance();
		Registry res = rf.getRegistry();
		res.put(new DummyAstNode(), new DummyArtifact());
	}

	@Test
	public void addAndGetElement() {
		DummyAstNode node = new DummyAstNode();
		DummyArtifact artifact = new DummyArtifact();
		RegistryFactory rf = RegistryFactory.getInstance();
		Registry res = rf.getRegistry();
		res.put(node, artifact);
		AnalysisArtifact artifactCollected = res.lookup(node,
				DummyArtifact.class);
		Assert.assertNotNull(artifactCollected);
		Assert.assertSame(artifact, artifactCollected);

	}

	@Test
	public void addAndGetRecursively() {

		DummyArtifact inLeaf = new DummyArtifact();

		DummyAstNode a = new DummyAstNode(); // leaf
		DummyAstNode d = new DummyAstNode(); // leaf
		DummyAstNode c = new DummyAstNode(); // leaf
		DummyAstNode b = new DummyAstNode(new INode[] { a, d }); // intermediate
		DummyAstNode root = new DummyAstNode(new INode[] { b, c }); // root

		RegistryFactory rf = RegistryFactory
				.getInstance("addAndGetRecursively");
		Registry reg = rf.getRegistry();

		reg.put(a, inLeaf);

		Map<INode, DummyArtifact> collectedArtifacts = reg.lookupRecursively(
				root, DummyArtifact.class);

		Assert.assertSame(inLeaf, collectedArtifacts.values().toArray()[0]);
	}

	@Test
	public void testOnARealPieceOfCML() {
		AClassDefinition paragraph = new AClassDefinition();
		AValuesDefinition values = new AValuesDefinition();
		AValueDefinition valDef = new AValueDefinition();
		paragraph.setBody(Arrays.asList(new PDefinition[] { values }));
		values.setValueDefinitions(Arrays.asList(new PDefinition[] { valDef }));

		RegistryFactory rf = RegistryFactory.getInstance();
		Registry reg = rf.getRegistry();

		DummyArtifact artifact = new DummyArtifact();

		reg.put(valDef, artifact);

		Map<INode, DummyArtifact> collected = reg.lookupRecursively(paragraph,
				DummyArtifact.class);

		Assert.assertSame(artifact, collected.values().toArray()[0]);
	}
}

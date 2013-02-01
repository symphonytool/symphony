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
import org.overture.ast.types.PType;

import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.program.ESource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.common.AnalysisArtifact;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;

public class CommonRegistryTestCase {

	PSource root = new PSource() {

		@Override
		public INode parent() {
			return null;
		}

		@Override
		public void parent(INode parent) {
			
		}

		@Override
		public <T extends INode> T getAncestor(Class<T> classType) {
			return null;
		}

		@Override
		public void apply(IAnalysis analysis) throws AnalysisException {
			
		}

		@Override
		public <A> A apply(IAnswer<A> caller) throws AnalysisException {
			return null;
		}

		@Override
		public <Q> void apply(IQuestion<Q> caller, Q question)
				throws AnalysisException {
			
		}

		@Override
		public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
				throws AnalysisException {
			return null;
		}

		@Override
		public ESource kindPSource() {
			return null;
		}

		@Override
		public Map<String, Object> getChildren(Boolean includeInheritedFields) {
			return null;
		}

		@Override
		public NodeEnum kindNode() {
			return null;
		}

		@Override
		public void removeChild(INode child) {
			// TODO Auto-generated method stub
			
		}

		public PSource clone() {return null;};
		
		@Override
		public PSource clone(Map<INode, INode> oldToNewMap) {
			return null;
		}

		@Override
		public void setParagraphs(List<? extends PDefinition> value) {
		}

		@Override
		public LinkedList<PDefinition> getParagraphs() {
			return null;
		}

		@Override
		public void replaceChild(INode oldChild, INode newChild) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setType(PType value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PType getType() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	@SuppressWarnings("serial")
	class DummyAstNode extends Node {

		private List<INode> children = new LinkedList<INode>();

		DummyAstNode() {
			super();
		}

		DummyAstNode(INode[] children) {
			this.children.addAll(Arrays.asList(children));
			for(INode n : children)
				n.parent(this);
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
		res.store(new DummyAstNode(), new DummyArtifact());
	}

	@Test
	public void addAndGetElement() {
		DummyAstNode node = new DummyAstNode();node.parent(this.root);
		DummyArtifact artifact = new DummyArtifact();
		RegistryFactory rf = RegistryFactory.getInstance();
		Registry res = rf.getRegistry();
		Assert.assertTrue("Artifact did not get inserted.",res.store(node, artifact));
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
		root.parent(this.root);

		RegistryFactory rf = RegistryFactory
				.getInstance("addAndGetRecursively");
		Registry reg = rf.getRegistry();

		Assert.assertTrue("Node did not get inserted.",reg.store(a, inLeaf));

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

		paragraph.parent(this.root);
		
		RegistryFactory rf = RegistryFactory.getInstance();
		Registry reg = rf.getRegistry();

		DummyArtifact artifact = new DummyArtifact();

		Assert.assertTrue("Node did not get inserted.",reg.store(valDef, artifact));

		Map<INode, DummyArtifact> collected = reg.lookupRecursively(paragraph,
				DummyArtifact.class);

		Assert.assertSame(artifact, collected.values().toArray()[0]);
	}
}

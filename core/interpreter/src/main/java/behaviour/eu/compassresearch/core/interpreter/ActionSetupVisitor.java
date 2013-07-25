package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;
import eu.compassresearch.core.interpreter.utility.SetMath;

class ActionSetupVisitor extends AbstractSetupVisitor {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner,visitorAccess);
	}

	/*
	 * Sequential Composition -- Start 
	 */
	
	private INode caseASequentialComposition(INode node, INode leftNode, Context question) throws AnalysisException
	{
		//We set up the left child of the sequential process/action before entering. The right will not
		//be touched before the left has terminated
		setLeftChild(new ConcreteCmlBehaviour(leftNode, question,new LexNameToken("",owner.name().getSimpleName() + ";",owner.name().getLocation()), owner));
		return node;
	}

	@Override
	public INode caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
					throws AnalysisException {

		return caseASequentialComposition(node, node.getLeft(), question);
	}

	@Override
	public INode caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
					throws AnalysisException {

		return caseASequentialComposition(node, node.getLeft(), question);
	}
	
	/*
	 * Sequential Composition -- End
	 */

	@Override
	public INode caseAHidingAction(AHidingAction node, Context question)
			throws AnalysisException {

		//We setup the child node for the hiding operator
		setLeftChild(new ConcreteCmlBehaviour(node.getLeft(),question,owner));
		return node;
	}

	@Override
	public INode caseATimeoutAction(ATimeoutAction node, Context question)
			throws AnalysisException {

		//We setup the child nodes 
		setLeftChild(new ConcreteCmlBehaviour(node.getLeft(),question,owner));
		return node;
	}

	@Override
	public INode caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			Context question) throws AnalysisException {

		//We setup the child nodes 
		setLeftChild(new ConcreteCmlBehaviour(node.getLeft(),question,owner));
		return node;
	}
	
	
	interface ReplicationFactory
	{
		INode createNextReplication();
		INode createLastReplication();
	}
	
	protected INode caseReplicatedAction(SReplicatedAction node, ReplicationFactory factory, Context question) throws AnalysisException
	{
		NameValuePairList replicationDecls = new  NameValuePairList();
		Pair<SetValue,Context> pair = getCurrentReplicationValue(node.getLocation(), node.getReplicationDeclaration(),replicationDecls, question);
		
		SetValue setValue = pair.first;
		Context nextContext = pair.second;

		INode nextNode = null;

		if(setValue.values.size() == 1)
			throw new AnalysisException("A replicated action must have at least two enumeration values");
		//If we have two replication values then we need to have one interleaving action, since
		//each value represents one process replication 
		else if(setValue.values.size() == 2)
		{
			nextNode = factory.createNextReplication();

			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
					convertReplicationToContext(setValue.values.get(1),replicationDecls,node.getLocation(),question)));
		}
		//If we have more than two replication values then we make an interleaving between the
		//first value and the rest of the replicated values
		else
		{
			nextNode = factory.createLastReplication();

			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
					nextContext));

			setValue.values.remove(0);
		}

		return nextNode;
	}
	
	@Override
	public INode caseAInterleavingReplicatedAction(
			final AInterleavingReplicatedAction node, Context question)
					throws AnalysisException {

		return caseReplicatedAction(node,new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new AInterleavingParallelAction(node.getLocation(), 
						node.getReplicatedAction().clone(), 
						node.getNamesetExpression().clone(),
						node.getNamesetExpression().clone(),
						node.getReplicatedAction().clone());
			}
			
			@Override
			public INode createLastReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new AInterleavingParallelAction(node.getLocation(), 
						node.getReplicatedAction().clone(), 
						node.getNamesetExpression().clone(),
						node.getNamesetExpression().clone(),
						node);
			}
			
		},question);
	}

	@Override
	public INode caseAGeneralisedParallelismReplicatedAction(
			final AGeneralisedParallelismReplicatedAction node, Context question)
			throws AnalysisException {

		return caseReplicatedAction(node, new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new AGeneralisedParallelismParallelAction(node.getLocation(), 
						node.getReplicatedAction().clone(),
						node.getNamesetExpression(),
						node.getNamesetExpression(),
						node.getReplicatedAction().clone(),
						node.getChansetExpression().clone());
			}
			
			@Override
			public INode createLastReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new AGeneralisedParallelismParallelAction(node.getLocation(),
						node.getReplicatedAction().clone(),
						node.getNamesetExpression(),
						node.getNamesetExpression(),
						node,
						node.getChansetExpression().clone());
			}
		}, question);
	}
	
	@Override
	public INode caseASynchronousParallelismReplicatedAction(
			final ASynchronousParallelismReplicatedAction node, Context question)
			throws AnalysisException {
		
		return caseReplicatedAction(node, new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new ASynchronousParallelismParallelAction(node.getLocation(), 
						node.getReplicatedAction().clone(),
						node.getNamesetExpression(),
						node.getNamesetExpression(),
						node.getReplicatedAction().clone());
			}
			
			@Override
			public INode createLastReplication() {
				//TODO The i'th namesetexpression should be evaluated in the i'th context 
				return new ASynchronousParallelismParallelAction(node.getLocation(),
						node.getReplicatedAction().clone(),
						node.getNamesetExpression(),
						node.getNamesetExpression(),
						node);
			}
		}, question);
	}
	
	@Override
	public INode caseAExternalChoiceReplicatedAction(
			final AExternalChoiceReplicatedAction node, Context question)
					throws AnalysisException {

		return caseReplicatedAction(node, new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				return new AExternalChoiceAction(node.getLocation(), 
						node.getReplicatedAction().clone(),
						node.getReplicatedAction().clone());
			}
			
			@Override
			public INode createLastReplication() {
				return new AExternalChoiceAction(node.getLocation(),
						node.getReplicatedAction().clone(), 
						node);
			}
		}, question);
	}
	
	protected INode caseReplicatedProcess(SReplicatedProcess node, ReplicationFactory factory, Context question) throws AnalysisException
	{
		NameValuePairList replicationDecls = new  NameValuePairList();
		Pair<SetValue,Context> pair = getCurrentReplicationValue(node.getLocation(), node.getReplicationDeclaration(),replicationDecls, question);
		
		SetValue setValue = pair.first;
		Context nextContext = pair.second;

		INode nextNode = null;

		if(setValue.values.size() == 1)
			throw new AnalysisException("A replicated action must have at least two enumeration values");
		//If we have two replication values then we need to have one interleaving action, since
		//each value represents one process replication 
		else if(setValue.values.size() == 2)
		{
			nextNode = factory.createNextReplication();

			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
					convertReplicationToContext(setValue.values.get(1),replicationDecls,node.getLocation(),question)));
		}
		//If we have more than two replication values then we make an interleaving between the
		//first value and the rest of the replicated values
		else
		{
			nextNode = factory.createLastReplication();

			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
					nextContext));

			setValue.values.remove(0);
		}

		return nextNode;
	}
	
	@Override
	public INode caseAGeneralisedParallelismReplicatedProcess(
			final AGeneralisedParallelismReplicatedProcess node, Context question)
			throws AnalysisException {
		
		return caseReplicatedProcess(node, new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				return new AGeneralisedParallelismProcess(node.getLocation(), 
						node.getReplicatedProcess().clone(),
						node.getChansetExpression().clone(),
						node.getReplicatedProcess().clone());
			}
			
			@Override
			public INode createLastReplication() {
				return new AGeneralisedParallelismProcess(node.getLocation(), 
						node.getReplicatedProcess().clone(),
						node.getChansetExpression().clone(),
						node);
			}
		}, question);
	}

	@Override
	public INode caseASynchronousParallelismReplicatedProcess(
			final ASynchronousParallelismReplicatedProcess node, Context question)
					throws AnalysisException {
		
		return caseReplicatedProcess(node, new ReplicationFactory() {
			
			@Override
			public INode createNextReplication() {
				return new ASynchronousParallelismProcess(node.getLocation(), 
						node.getReplicatedProcess().clone(),
						node.getReplicatedProcess().clone());
			}
			
			@Override
			public INode createLastReplication() {
				return new ASynchronousParallelismProcess(node.getLocation(),
						node.getReplicatedProcess().clone(), 
						node);
			}
		}, question);
		
//		NameValuePairList replicationDecls = new  NameValuePairList();
//		Pair<SetValue,Context> pair = getCurrentReplicationValue(node.getLocation(), node.getReplicationDeclaration(),replicationDecls, question);
//		
//		SetValue setValue = pair.first;
//		Context nextContext = pair.second;
//
//		INode returnNode = null;
//
//		if(setValue.values.size() == 1)
//			throw new AnalysisException("A replicated action must have at least two enumeration values");
//		//If we have two replication values then we need to have one interleaving action, since
//		//each value represents one process replication 
//		else if(setValue.values.size() == 2)
//		{
//			returnNode = new ASynchronousParallelismProcess(node.getLocation(), 
//					node.getReplicatedProcess().clone(),
//					node.getReplicatedProcess().clone());
//
//			setChildContexts(new Pair<Context,Context>(
//					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
//					convertReplicationToContext(setValue.values.get(1),replicationDecls,node.getLocation(),question)));
//
//			setValue.values.remove(0);
//			setValue.values.remove(0);
//		}
//		//If we have more than two replication values then we make an interleaving between the
//		//first value and the rest of the replicated values
//		else
//		{
//			returnNode = new ASynchronousParallelismProcess(node.getLocation(),
//					node.getReplicatedProcess().clone(), 
//					node);
//
//			setChildContexts(new Pair<Context,Context>(
//					convertReplicationToContext(setValue.values.get(0),replicationDecls,node.getLocation(),question),
//					nextContext));
//
//			setValue.values.remove(0);
//		}
//
//		return returnNode;
	}
	
	/*
	 * Non public replication helper methods -- Start
	 */
	
	private Context convertReplicationToContext(Value value, NameValuePairList replicationDecls,ILexLocation location, Context outer) throws ValueException
	{
		Context context = CmlContextFactory.newContext(location, "", outer);

		ValueList tuple = value.tupleValue(outer);
		for(int i = 0; i < tuple.size(); i++)
		{
			context.putNew(new NameValuePair(replicationDecls.get(i).name,tuple.get(i)));
		}

		return context;
	}

	private SetValue convertReplDeclToSetValue(NameValuePairList replicationDecls, Context question) throws ValueException
	{
		SetValue setValue = null;

		if(replicationDecls.size() == 1)
		{
			setValue = new SetValue();

			NameValuePair nvp = replicationDecls.get(0);
			for(Value val : nvp.value.setValue(question))
				setValue.values.add(new TupleValue(new ValueList(val)));

		}
		else
		{
			//If more than one decl then we need to calculate the cross product of them
			//First we append all the sets into a list
			List<SetValue> sets = new LinkedList<SetValue>();
			for(NameValuePair nvp : replicationDecls)
				sets.add(new SetValue(nvp.value.setValue(question)));

			setValue = SetMath.getCrossProduct(sets);
		}

		return setValue;
	}
	
	protected Pair<SetValue,Context> getCurrentReplicationValue(ILexLocation location,List<PSingleDeclaration> replicationDeclaration, NameValuePairList replicationNvpl, Context question) throws AnalysisException
	{
		//The name of the value holding the state of the remaining values of the replication
		LexNameToken replicationContextValueName = new LexNameToken("|REPLICATION|",location.toShortString(),location); 

		Value value = question.check(replicationContextValueName);

		//Convert all the single decls into a NameValuePairList
		for(PSingleDeclaration singleDecl :  replicationDeclaration)
			replicationNvpl.addAll(singleDecl.apply(this.cmlDefEvaluator,question));

		SetValue setValue = null;
		Context nextContext = question;

		//if null then this is the first action of the replication
		//then we need to evaluate the 
		if(value == null)
		{
			setValue = convertReplDeclToSetValue(replicationNvpl,question);
			//Make a set of tuples
			nextContext = CmlContextFactory.newContext(location, "replication contexts", question);
			nextContext.putNew(new NameValuePair(replicationContextValueName,setValue));
		}
		else
			setValue = new SetValue(value.setValue(question));

		return new Pair<SetValue,Context>(setValue,nextContext);
	}
	
	/*
	 * Non public replication helper methods -- End
	 */
}

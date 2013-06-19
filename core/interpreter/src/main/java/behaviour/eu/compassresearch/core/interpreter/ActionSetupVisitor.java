package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
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

import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
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
	
	private INode caseASequentialComposition(INode node, INode leftNode, Context question) throws AnalysisException
	{
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
		//setRightChild(VanillaInterpreterFactory.newCmlBehaviour(node.getRight(),question,owner));
		return node;
	}
	
	@Override
	public INode caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, Context question)
			throws AnalysisException {
				
		LexNameToken replicationContextValueName = new LexNameToken("|REPLICATION|",Integer.toString(node.hashCode()),node.getLocation()); 
		
		Value value = question.check(replicationContextValueName);
		
		//We only take te 
		NameValuePairList replicationDecls = new  NameValuePairList();
		
		//Collect all the single decls into a NameValuePairList
		for(PSingleDeclaration singleDecl :  node.getReplicationDeclaration())
			replicationDecls.addAll(singleDecl.apply(this.cmlDefEvaluator,question));
		
		SetValue setValue = null;
		Context nextContext = question;

		//if null then this is the start of the replication
		if(value == null)
		{
			
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
			
			//Make a set of tuples
			nextContext = CmlContextFactory.newContext(node.getLocation(), "replication contexts", question);
			nextContext.putNew(new NameValuePair(replicationContextValueName,setValue));
		}
		else
			setValue = new SetValue(value.setValue(question));

		AInterleavingParallelAction interleavingNode = null;

		if(setValue.values.size() == 1)
			throw new AnalysisException("A replicated action must have at least two enumeration values");
		else if(setValue.values.size() == 2)
		{
			interleavingNode = new AInterleavingParallelAction(node.getLocation(), 
					node.getReplicatedAction().clone(), 
					node.getNamesetExpression().clone(),
					node.getNamesetExpression().clone(),
					node.getReplicatedAction().clone());
			
			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node,question),
					convertReplicationToContext(setValue.values.get(1),replicationDecls,node,question)));
		}
		else
		{
			interleavingNode = new AInterleavingParallelAction(node.getLocation(), 
					node.getReplicatedAction().clone(), 
					node.getNamesetExpression().clone(),
					node.getNamesetExpression().clone(),
					node);
			
			setChildContexts(new Pair<Context,Context>(
					convertReplicationToContext(setValue.values.get(0),replicationDecls,node,question),
					nextContext));
			
			setValue.values.remove(0);
		}
		
		return interleavingNode;
	}
	
	private Context convertReplicationToContext(Value value, NameValuePairList replicationDecls,AInterleavingReplicatedAction node, Context outer) throws ValueException
	{
		Context context = CmlContextFactory.newContext(node.getLocation(), "", outer);
		
		ValueList tuple = value.tupleValue(outer);
		for(int i = 0; i < tuple.size(); i++)
		{
			context.putNew(new NameValuePair(replicationDecls.get(i).name,tuple.get(i)));
		}
		
		return context;
	}
	
}

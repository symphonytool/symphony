//package eu.compassresearch.core.interpreter.old;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.node.INode;
//import org.overture.interpreter.runtime.Context;
//import org.overture.interpreter.runtime.ValueException;
//import org.overture.interpreter.values.NameValuePair;
//import org.overture.interpreter.values.NameValuePairList;
//import org.overture.interpreter.values.SetValue;
//import org.overture.interpreter.values.ValueList;
//
//import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
//import eu.compassresearch.ast.declarations.PSingleDeclaration;
//import eu.compassresearch.ast.lex.LexIdentifierToken;
//import eu.compassresearch.ast.lex.LexNameToken;
//import eu.compassresearch.core.interpreter.AbstractEvaluationVisitor;
//import eu.compassresearch.core.interpreter.VisitorAccess;
//import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
//import eu.compassresearch.core.interpreter.utility.Pair;
//
///**
// * Visitor that handles all the replicated action logic.
// * @author akm
// *
// */
//public class ReplicatedActionEvaluationVisitor extends AbstractEvaluationVisitor {
//
//	public ReplicatedActionEvaluationVisitor(
//			AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner,
//			VisitorAccess visitorAccess) {
//		super(parentVisitor, owner, visitorAccess);
//	}
//	/**
//	 * Replicated interleaving
//	 * Syntax 	: '|||' , replication declarations , @ , action
//	 * 
//	 * Example 	: |||i:e @ A(i)
//	 * 
//	 * Execute all the actions A(i) in parallel without sync
//	 * 
//	 */
////	@Override
////	public Pair<INode, Context> caseAInterleavingReplicatedAction(
////			AInterleavingReplicatedAction node, Context question)
////			throws AnalysisException {
////		
////		//We only take te 
////		NameValuePairList replicationDecls = new  NameValuePairList();
////		List<CmlBehaviour> replicatedActions = new LinkedList<CmlBehaviour>();
////		
////		//Collect all the single decls into a NameValuePairList
////		for(PSingleDeclaration singleDecl :  node.getReplicationDeclaration())
////			replicationDecls.addAll(singleDecl.apply(this.cmlDefEvaluator,question));
////		
////		List<SetValue> sets = new LinkedList<SetValue>();
////		
////		//collect all the setValues from the decls
////		for(NameValuePair nvp : replicationDecls)
////			sets.add(new SetValue(nvp.value.setValue(question)));
////			
////		SetValue replicatedArguments = null;
////		
////		if(sets.size() == 1)
////			replicatedArguments = sets.get(0);
////		//else
////				
////		//Contains all the arguments for each replicated action
////		List<NameValuePairList> allArguments = new LinkedList<NameValuePairList>();
////			
////		
////		for(NameValuePairList nvpl : allArguments)
////		{
////			Context context = CmlContextFactory.newContext(node.getLocation(), "Replicated Context", question);
////			context.putAllNew(nvpl);
////			//replicatedActions.add(new Concr)
////		}
////		
//////		Context context = CmlContextFactory.newContext(node.getLocation(), "Replicated Context", question);
//////		context.putNew(new NameValuePair(name, value))
//////		
//////		
//////		for(ValueSet valSet : replicationDecls)
//////		{
//////			for(Value val : valSet)
//////				;
//////			
//////		}
////		//node.getReplicatedAction()
////		
////		
////		//replicationDecls.
////		
////		//AInterleavingParallelAction newInterleaving = new AInterleavingParallelAction(node.getLocation(),);
////			
////		//return new Pair<INode, Context>(first, question);
////		return null;
////	}
////	
////	private CmlBehaviour buildReplicationTree(List<LexIdentifierToken> argNames, SetValue setValue, AInterleavingReplicatedAction node, Context contexts) throws ValueException
////	{
////		
////		
////		if(setValue.values.size() == 1)
////		{
////			ValueList values = setValue.values.firstElement().tupleValue(contexts);	
////			
////			Context context = CmlContextFactory.newContext(node.getLocation(), "Replicated Context", contexts);
////			
////			for(int i = 0; i < values.size(); i++)
////				context.putNew(new NameValuePair(new LexNameToken("",argNames.get(i).clone()), values.get(i)));
////				
////			//return new ConcreteCmlBehaviour(node.getReplicatedAction(), context, node)
////			
////		}
////		else if (setValue.values.size() == 2)
////			;
////		else
////		{
////			
////		
////		}
////		
////		return null;
////	}
//
//}

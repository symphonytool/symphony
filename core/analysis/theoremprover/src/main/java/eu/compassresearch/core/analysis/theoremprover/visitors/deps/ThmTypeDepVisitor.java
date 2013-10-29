package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AInMapMapType;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AOptionalType;
import org.overture.ast.types.AParameterType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.AUndefinedType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidReturnType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmTypeDepVisitor extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmTypeDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}
	
	
	public NodeNameList caseARecordInvariantType(ARecordInvariantType tp, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		for (AFieldField field: tp.getFields())
		{
			nodeDeps.addAll(field.getType().apply(thmDepVisitor, bvars));				
		}
		return nodeDeps;

	}
	

	public NodeNameList caseAInMapMapType(AInMapMapType tp, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(tp.getFrom().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(tp.getTo().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}	
		
		
	public NodeNameList caseAMapMapType(AMapMapType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(tp.getFrom().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(tp.getTo().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}


	public NodeNameList caseASeqSeqType(ASeqSeqType tp, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(tp.getSeqof().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
	
	public NodeNameList caseASeq1SeqType(ASeq1SeqType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		nodeDeps.addAll(tp.getSeqof().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
		
	public NodeNameList caseASetType(ASetType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		nodeDeps.addAll(tp.getSetof().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
		
	public NodeNameList caseABracketType(ABracketType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		nodeDeps.addAll(tp.getType().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}	
		
	public NodeNameList caseAClassType(AClassType tp, NodeNameList bvars) throws AnalysisException{
		
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}	

	public NodeNameList caseAFunctionType(AFunctionType tp, NodeNameList bvars) throws AnalysisException{
		
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	public NodeNameList caseAOperationType(AOperationType tp, NodeNameList bvars) throws AnalysisException{
	
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}
		
	public NodeNameList caseAOptionalType(AOptionalType tp, NodeNameList bvars) throws AnalysisException{
		
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(tp.getType().apply(thmDepVisitor, bvars));	
		return nodeDeps;
	}
		
	public NodeNameList caseAParameterType(AParameterType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
		
	public NodeNameList caseAProductType(AProductType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
			
		for(PType t: tp.getTypes())
		{
			nodeDeps.addAll(t.apply(thmDepVisitor, bvars));	
		}
		return nodeDeps;
	}
	
		
	public NodeNameList caseAQuoteType(AQuoteType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		//Don't think anything should be added here
		return nodeDeps;
	}
		

	public NodeNameList caseAUndefinedType(AUndefinedType tp, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
		

	public NodeNameList caseAUnionType(AUnionType tp, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
			
		for(PType t: tp.getTypes())
		{
			nodeDeps.addAll(t.apply(thmDepVisitor, bvars));	
		}
		return nodeDeps;
	}
		
	public NodeNameList caseAUnknownType(AUnknownType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
		

	public NodeNameList caseAUnresolvedType(AUnresolvedType tp, NodeNameList bvars) throws AnalysisException{
		
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
		

	public NodeNameList caseAVoidReturnType(AVoidReturnType tp, NodeNameList bvars) throws AnalysisException{
		
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
		

	public NodeNameList caseAVoidType(AVoidType tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
		
		

	//NEED TO CHECK THIS WORKS FOR INVARIANTS
	
	/***
	 * Method to return the list of dependencies for the type invariant
	 * @param node - the type AST node
	 * @return the list of dependencies
	 */
	public NodeNameList caseATypeDefinition(ATypeDefinition tp, NodeNameList bvars) throws AnalysisException{

		NodeNameList nodeDeps = new NodeNameList();

		PExp invExp = tp.getInvExpression();
		PPattern invPatt = tp.getInvPattern();
		if(invExp != null && invPatt != null){
			NodeNameList evars = new NodeNameList();
			if (invPatt instanceof AIdentifierPattern)
			{
				evars.add(((AIdentifierPattern) invPatt).getName());

			}
			else if (invPatt instanceof ARecordPattern)
			{
				ARecordPattern recInvPatt = (ARecordPattern) invPatt;
				for(PPattern p : recInvPatt.getPlist())
				{
					if (p instanceof AIdentifierPattern)
					{
						evars.add(((AIdentifierPattern) p).getName());
					}
				}
			}
			nodeDeps.addAll(invExp.apply(thmDepVisitor, bvars));	
		}
		
		return nodeDeps;
	}
	

	@Override
	public NodeNameList createNewReturnValue(INode arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeNameList createNewReturnValue(Object arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

}

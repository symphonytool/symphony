package eu.compassresearch.core.analysis.theoremprover.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmRecType;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmType;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class ThmTypeVisitor extends QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> {
	
	private ThmDepVisitor depVisitor;
	private ThmStringVisitor stringVisitor;
	
	public ThmTypeVisitor(QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> parent, ThmDepVisitor depVis, ThmStringVisitor stringVis)
	{
		depVisitor = depVis;
		stringVisitor = stringVis;
	}

	@Override
	public ThmNodeList caseATypesDefinition(ATypesDefinition node, ThmVarsContext vars)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		for(ATypeDefinition t : node.getTypes())
		{
			tnl.addAll(t.apply(this, vars));
		}
		return tnl;
	}
	

	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node, ThmVarsContext vars)
			throws AnalysisException {

		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		ILexNameToken name = node.getName();
		PType type = node.getType();
		String typeStr = "";
		NodeNameList nodeDeps = new NodeNameList();
		String inv = "";
		
		if (type instanceof ANamedInvariantType)
		{
			ANamedInvariantType nametype = (ANamedInvariantType) type;
			PType tp = nametype.getType();
			//Send to visitor for Type String
			typeStr = tp.apply(stringVisitor, new ThmVarsContext());//ThmTypeUtil.getIsabelleType(tp);
			//Send to visitor for Deplist
			nodeDeps.addAll(tp.apply(depVisitor, new NodeNameList()));//ThmTypeUtil.getIsabelleTypeDeps(tp));
			//Handle invariant.
			nodeDeps.addAll(getIsabelleTypeInvDeps(node));
			inv = getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), typeStr, inv));
			
		}
		else if (type instanceof ARecordInvariantType)
		{
			ARecordInvariantType rtype = (ARecordInvariantType) type;
			
			//Send to visitor for Deplist
			nodeDeps.addAll(rtype.apply(depVisitor, new NodeNameList()));

			//Handle invariant.
			nodeDeps.addAll(getIsabelleTypeInvDeps(node));
			inv = getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmRecType(name.toString(), rtype.getFields(), inv));
		}
		else
		{
			typeStr = "(* Type not handled: " + type.toString() + ", type = " + type.getClass() + "*)";
		}
		
		tnl.add(tn);
		return tnl;
	}

	/***
	 * Method to return a string for the type invariant
	 * @param node - the type AST node
	 * @return an invariant string
	 * @throws AnalysisException 
	 */
	private String getIsabelleTypeInv(ATypeDefinition node) throws AnalysisException
	{
		String inv = "";
		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		ThmVarsContext vc = new ThmVarsContext();
		if(invExp != null && invPatt != null){
			//NodeNameList svars = new NodeNameList();
			//NodeNameList evars = new NodeNameList();

			if (invPatt instanceof AIdentifierPattern)
			{
				vc.addBVar(((AIdentifierPattern) invPatt).getName());
			}
			else if (invPatt instanceof ARecordPattern)
			{
				ARecordPattern recInvPatt = (ARecordPattern) invPatt;
				for(PPattern p : recInvPatt.getPlist())
				{
					if (p instanceof AIdentifierPattern)
					{
						vc.addBVar(((AIdentifierPattern) p).getName());
					}
				}
			}
			inv = (" " + ThmTypeUtil.isaInv  + " " + invPatt.toString() + " == " + invExp.apply(stringVisitor, vc)); //ThmExprUtil.getIsabelleExprStr(svars, evars, invExp));
		}
		
		return inv;
	}


	/***
	 * Method to return the list of dependencies for the type invariant
	 * @param node - the type AST node
	 * @return the list of dependencies
	 * @throws AnalysisException 
	 */
	private NodeNameList getIsabelleTypeInvDeps(ATypeDefinition node) throws AnalysisException
	{
		NodeNameList nodeDeps = new NodeNameList();

		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			NodeNameList bvars = new NodeNameList();
			if (invPatt instanceof AIdentifierPattern)
			{
				bvars.add(((AIdentifierPattern) invPatt).getName());

			}
			else if (invPatt instanceof ARecordPattern)
			{
				ARecordPattern recInvPatt = (ARecordPattern) invPatt;
				for(PPattern p : recInvPatt.getPlist())
				{
					if (p instanceof AIdentifierPattern)
					{
						bvars.add(((AIdentifierPattern) p).getName());
					}
				}
			}
			nodeDeps.addAll(invExp.apply(depVisitor,bvars));//(ThmExprUtil.getIsabelleExprDeps(evars, invExp));
		}
		
		return nodeDeps;
	}
	
	@Override
	public ThmNodeList createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThmNodeList createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

}

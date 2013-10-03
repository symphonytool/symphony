package eu.compassresearch.core.analysis.theoremprover.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmRecType;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmType;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

@SuppressWarnings("serial")
public class ThmTypeVisitor extends AnswerCMLAdaptor<ThmNodeList> {

    public ThmTypeVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
    }


	@Override
	public ThmNodeList caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		for(ATypeDefinition t : node.getTypes())
		{
			tnl.addAll(t.apply(this));
		}
		return tnl;
	}
	

	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node)
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
			//Send to Utils for Type String
			typeStr = ThmTypeUtil.getIsabelleType(tp);
			//Send to Utils for Deplist
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tp));
			//Handle invariant.
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeInvDeps(node));
			inv = ThmTypeUtil.getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), typeStr, inv));
			
		}
		else if (type instanceof ARecordInvariantType)
		{
			ARecordInvariantType rtype = (ARecordInvariantType) type;
			
			//Send to Utils for Deplist
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(rtype));

			//Handle invariant.
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeInvDeps(node));
			inv = ThmTypeUtil.getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmRecType(name.toString(), rtype.getFields(), inv));
		}
		else
		{
			typeStr = "(* Type not handled: " + type.toString() + ", type = " + type.getClass() + "*)";
		}
		
		tnl.add(tn);
		return tnl;
	}


	@Override
	public ThmNodeList createNewReturnValue(INode node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ThmNodeList createNewReturnValue(Object node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

}

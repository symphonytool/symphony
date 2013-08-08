package eu.compassresearch.theoremprover.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SBasicType;
import org.overture.ast.types.SMapType;
//import org.overture.ast.types.ACharBasicType;
//import org.overture.ast.types.AIntNumericBasicType;
//import org.overture.ast.types.ANamedInvariantType;
//import org.overture.ast.types.ANatNumericBasicType;
//import org.overture.ast.types.AQuoteType;
//import org.overture.ast.types.ARationalNumericBasicType;
//import org.overture.ast.types.ARealNumericBasicType;
//import org.overture.ast.types.ASeq1SeqType;
//import org.overture.ast.types.ASeqSeqType;
//import org.overture.ast.types.ASetType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmRecType;
import eu.compassresearch.theoremprover.thms.ThmType;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

@SuppressWarnings("serial")
public class ThmPTypeVisitor extends AnswerCMLAdaptor<ThmNodeList> {


	private static String typeDelim = "\\<parallel>";
	private static String isaType = "definition";
	
    final private AnswerCMLAdaptor<ThmNodeList> parentVisitor;

    public ThmPTypeVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
    	this.parentVisitor = parentVisitor;
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
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		String inv = "";
		
		if (type instanceof ANamedInvariantType)
		{
			//TODO: May need to move the following two lines into the getIsabelleType method...
			ANamedInvariantType nametype = (ANamedInvariantType) type;
			PType tp = nametype.getType();
			//Send to Utils for Type String
			typeStr = ThmTypeUtil.getIsabelleType(tp);
			//Send to Utils for Deplist
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tp));
			//TODO: Handle invariant.
	//		String invExp = ThmExprUtil.getIsabelleExprStr(node.getInvExpression());
	//		String invPat = node.getInvPattern().toString();
			inv = ThmTypeUtil.getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), typeStr, inv));
			
		}
		else if (type instanceof ARecordInvariantType)
		{
			ARecordInvariantType rtype = (ARecordInvariantType) type;
			
			//Send to Utils for Type String
		//	typeStr = ThmTypeUtil.getIsabelleType(rtype);
			//Send to Utils for Deplist
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(rtype));

			//TODO: Handle invariant.
		//	String invExp = ThmExprUtil.getIsabelleExprStr(node.getInvExpression());
		//	String invPat = node.getInvPattern().toString();
		//	inv = ("inv " + invPat + " == " + invExp);
			inv = ThmTypeUtil.getIsabelleTypeInv(node);
			
			tn = new ThmNode(name, nodeDeps, new ThmRecType(name.toString(), rtype.getFields(), inv));
		}
		else
		{
			typeStr = "Type not handled: " + type.toString() + ", type = " + type.getClass();
		}
		
		
//		ThmNode tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), typeStr, inv, record));
		tnl.add(tn);
		return tnl;
	}

}

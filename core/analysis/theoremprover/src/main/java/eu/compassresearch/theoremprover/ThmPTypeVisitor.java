package eu.compassresearch.theoremprover;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
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
		
		ILexNameToken name = node.getName();
		PType nType = node.getType();
		String type = "";
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if (nType instanceof ANamedInvariantType)
		{
			//TODO: May need to move the following two lines into the getIsabelleType method...
			ANamedInvariantType nametype = (ANamedInvariantType) nType;
			PType tp = nametype.getType();
			//Send to Utils for Type String
			type = ThmUtil.getIsabelleType(tp);
			//Send to Utils for Deplist
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(tp));
		}
		else
		{
			type = "Top Level Type not handled: " + nType.toString() + ", type = " + nType.getClass();
		}
		//TODO: Handle Record Types.
//		else if (nType instanceof ARecordInvariantType)
//		{
//			ARecordInvariantType rtype = (ARecordInvariantType)type;
//			
//			for (AFieldField field: rtype.getFields())
//			{
//				if (PTypeAssistantTC.narrowerThan(field.getType(), node.getAccess()))
//				{
//					TypeCheckerErrors.report(3321, "Field type visibility less than type's definition", field.getTagname().getLocation(), field.getTagname());
//				}
//			}
//		}
		
		ThmNode tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), type));
		tnl.add(tn);
		return tnl;
	}

//	@Override
//	public ThmNodeList caseANatNumericBasicType(ANatNumericBasicType node)
//			throws AnalysisException {
//		ThmNodeList tnl = new ThmNodeList();
//		ThmNode tn = new ThmNode();
//		tn.setString(node.toString() +  "@nat");
//		tnl.add(tn);
//		return tnl;
//	}
	
    
	
//	@Override
//	public String caseAQuoteType(AQuoteType node) throws AnalysisException {
//		// TODO Auto-generated method stub
//		return super.caseAQuoteType(node);
//	}
//
//	@Override
//	public String caseASeq1SeqType(ASeq1SeqType node) throws AnalysisException {
//		return "@seq1 of " + node.getSeqof().apply(this);
//	}
//
//	@Override
//	public String caseASetType(ASetType node) throws AnalysisException {
//		return "@set of " + node.getSetof().apply(this);
//	}
//
//	@Override
//	public String caseANamedInvariantType(ANamedInvariantType node)
//			throws AnalysisException {
//		return node.getType().apply(this);
//	}
//
//	@Override
//	public String caseASeqSeqType(ASeqSeqType node) throws AnalysisException {
//		return "@seq of " + node.getSeqof().apply(this);
//	}
//
//	@Override
//	public String caseACharBasicType(ACharBasicType node)
//			throws AnalysisException {
//		return "@char";
//	}
//
//	@Override
//	public String caseAIntNumericBasicType(AIntNumericBasicType node)
//			throws AnalysisException {
//		return "@int";
//	}
//
//
//	@Override
//	public String caseARationalNumericBasicType(ARationalNumericBasicType node)
//			throws AnalysisException {
//		return "@rat";
//	}
//
//	@Override
//	public String caseARealNumericBasicType(ARealNumericBasicType node)
//			throws AnalysisException {
//		return "@real";
//	}
//
//	
//
//	
//	
//	

}

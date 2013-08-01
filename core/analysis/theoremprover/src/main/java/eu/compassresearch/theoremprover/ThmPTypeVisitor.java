package eu.compassresearch.theoremprover;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
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

@SuppressWarnings("serial")
public class ThmPTypeVisitor extends AnswerCMLAdaptor<ThmNodeList> {



    final private AnswerCMLAdaptor<ThmNodeList> parentVisitor;

    public ThmPTypeVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
    	this.parentVisitor = parentVisitor;
    }
	

	// Call Overture for the other expressions
    @Override
    public ThmNodeList defaultPExp(PExp node)
	    throws AnalysisException {
    	ThmNodeList tnl = new ThmNodeList();
//    	pol.addAll(node.apply(overtureVisitor, question));
    	return tnl;
    }

    
	
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
//	@Override
//	public String caseANatNumericBasicType(ANatNumericBasicType node)
//			throws AnalysisException {
//		return "@nat";
//	}
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

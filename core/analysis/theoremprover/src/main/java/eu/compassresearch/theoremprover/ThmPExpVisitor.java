package eu.compassresearch.theoremprover;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

@SuppressWarnings("serial")
public class ThmPExpVisitor extends AnswerCMLAdaptor<ThmNodeList> {


    final private AnswerCMLAdaptor<ThmNodeList> parentVisitor;

    public ThmPExpVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
	this.parentVisitor = parentVisitor;
    }
	
	
//	@Override
//	public ThmNodeList caseBoolean(Boolean node) throws AnalysisException {
//		if (node) return "true"; 
//		else return "false";
//	}
//
//	@Override
//	public String caseALessNumericBinaryExp(ALessNumericBinaryExp node)
//			throws AnalysisException {
//		return node.getLeft().apply(this) + " < " + node.getRight().apply(this);
//	}
//
//	@Override
//	public String caseAIntLiteralExp(AIntLiteralExp node)
//			throws AnalysisException {
//		return "\\<langle>" + node.toString() + "\\<rangle>";
//	}
//
//	@Override
//	public String caseInteger(Integer node) throws AnalysisException {
//		return "\\<langle>" + node.toString() + "\\<rangle>";
//	}
//
//	@Override
//	public String caseAStringLiteralExp(AStringLiteralExp node)
//			throws AnalysisException {
//		String s = node.toString();
//		return "\\<langle>''" + s.substring(1, s.length() - 1) + "''\\<rangle>";
//	}
//	
//	

}
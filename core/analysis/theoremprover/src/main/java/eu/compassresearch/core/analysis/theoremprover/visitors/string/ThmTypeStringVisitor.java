package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AInMapMapType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ANatOneNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AOptionalType;
import org.overture.ast.types.AParameterType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.types.AUndefinedType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidReturnType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

@SuppressWarnings("serial")
public class ThmTypeStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmTypeStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}
	
	public String caseAChannelType(AChannelType tp, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		

		//For each type in the product, add it to the string
		for (Iterator<PType> itr = tp.getParameters().listIterator(); itr.hasNext(); ) {
			PType t = itr.next();
			sb.append(t.apply(thmStringVisitor, vars));
			//If there are remaining types, add a isaProd *
			if(itr.hasNext()){	
				sb.append(ThmTypeUtil.isaProd);
			}
		}
		
		return sb.toString();
	}
	
	public String caseANamedInvariantType(ANamedInvariantType tp, ThmVarsContext vars) throws AnalysisException{
		String type = "";

		type = "@" + tp.toString();
		return type;
	}
	
	public String caseANatNumericBasicType(ANatNumericBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaNat;
	}
	
	public String caseABooleanBasicType(ABooleanBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaBool;
	}
	
	public String caseACharBasicType(ACharBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaChar;
	}
	
	public String caseATokenBasicType(ATokenBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaToken;
	}
	
	public String caseANatOneNumericBasicType(ANatOneNumericBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaNat1;
	}
	
	public String caseARationalNumericBasicType(ARationalNumericBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaRat;
	}
	
	public String caseARealNumericBasicType(ARealNumericBasicType tp, ThmVarsContext vars) throws AnalysisException{
		return ThmTypeUtil.isaReal;
	}
	
	public String caseAIntNumericBasicType(AIntNumericBasicType tp, ThmVarsContext vars) throws AnalysisException{	
		return ThmTypeUtil.isaInt;
	}

	public String caseAInMapMapType(AInMapMapType tp, ThmVarsContext vars) throws AnalysisException{	
		
		return (ThmTypeUtil.isaInMap + " " + tp.getFrom().apply(thmStringVisitor, vars) + " " + ThmTypeUtil.isaMapTo + tp.getTo().apply(thmStringVisitor, vars));
	}
	
	public String caseAMapMapType(AMapMapType tp, ThmVarsContext vars) throws AnalysisException{	
		
		return (ThmTypeUtil.isaMap + " " + tp.getFrom().apply(thmStringVisitor, vars) + " " + ThmTypeUtil.isaMapTo +  tp.getTo().apply(thmStringVisitor, vars));
	}

	public String caseASeqSeqType(ASeqSeqType tp, ThmVarsContext vars) throws AnalysisException{	
		return (ThmTypeUtil.isaSeq + " " + tp.getSeqof().apply(thmStringVisitor, vars));
	}
	
	public String caseASeq1SeqType(ASeq1SeqType tp, ThmVarsContext vars) throws AnalysisException{	
		
		return (ThmTypeUtil.isaSeq1 + " " + tp.getSeqof().apply(thmStringVisitor, vars));
	}

	public String caseASetType(ASetType tp, ThmVarsContext vars) throws AnalysisException{	
		return (ThmTypeUtil.isaSet  + " " + tp.getSetof().apply(thmStringVisitor, vars));
	}
	
	public String caseABracketType(ABracketType tp, ThmVarsContext vars) throws AnalysisException{	
		return (tp.getType().apply(thmStringVisitor, vars));
	}
	
	public String caseAOptionalType(AOptionalType tp, ThmVarsContext vars) throws AnalysisException{	
		
		return (ThmTypeUtil.isaOtpOpen + tp.getType().apply(thmStringVisitor, vars) +  ThmTypeUtil.isaOtpClose);
	}
	
	public String caseAProductType(AProductType tp, ThmVarsContext vars) throws AnalysisException{	
		LinkedList<PType> types = tp.getTypes();
		StringBuilder sb = new StringBuilder();
		
		//For each type in the product, add it to the string
		for (Iterator<PType> itr = types.listIterator(); itr.hasNext(); ) {
			PType t = itr.next();
			sb.append(t.apply(thmStringVisitor, vars));
			//If there are remaining types, add a isaProd *
			if(itr.hasNext()){	
				sb.append(ThmTypeUtil.isaProd);
			}
		}
		return sb.toString();
	}
	
	public String caseAQuoteType(AQuoteType tp, ThmVarsContext vars) throws AnalysisException{	
		return (ThmTypeUtil.isaQuoteLeft + tp.getValue().getValue() + ThmTypeUtil.isaQuoteRight);
	}
	
	public String caseAUndefinedType(AUndefinedType tp, ThmVarsContext vars) throws AnalysisException{	
		
		return ThmTypeUtil.isaUndefined;
	}
	public String caseAUnionType(AUnionType tp, ThmVarsContext vars) throws AnalysisException{	
		

		LinkedList<PType> types = tp.getTypes();
		StringBuilder sb = new StringBuilder();
		
		//For each type in the product, add it to the string
		for (Iterator<PType> itr = types.listIterator(); itr.hasNext(); ) {
			PType t = itr.next();
			sb.append(t.apply(thmStringVisitor, vars));
			//If there are remaining types, add a isaUnion |
			if(itr.hasNext()){	
				sb.append(ThmTypeUtil.isaUnion);
			}
		}
		return sb.toString();
	}
	public String caseAUnknownType(AUnknownType tp, ThmVarsContext vars) throws AnalysisException{
		return tp.getDefinitions().toString(); //???
		//return notHandled;
	}
	
	public String caseAUnresolvedType(AUnresolvedType tp, ThmVarsContext vars) throws AnalysisException{	
	//Several types are not handled - mostly by design
		return ThmTypeUtil.notHandled;
	}
	
	public String caseAVoidReturnType(AVoidReturnType tp, ThmVarsContext vars) throws AnalysisException{	
		return "()";
	}
	
	public String caseAVoidType(AVoidType tp, ThmVarsContext vars) throws AnalysisException{	
		return "()";
	}
	
	public String caseAClassType(AClassType tp, ThmVarsContext vars) throws AnalysisException{	
		return ThmTypeUtil.notHandled;
	}
	
	public String caseAFunctionType(AFunctionType tp, ThmVarsContext vars) throws AnalysisException{	
		return ThmTypeUtil.notHandled;
	}
	
	public String caseAOperationType(AOperationType tp, ThmVarsContext vars) throws AnalysisException{	
		return ThmTypeUtil.notHandled;
	}
	
	public String caseAParameterType(AParameterType tp, ThmVarsContext vars) throws AnalysisException{	
		return ThmTypeUtil.notHandled;
	}

	public String caseARecordInvariantType(ARecordInvariantType tp, ThmVarsContext vars) throws AnalysisException{	
		String recName = "@" + tp.getName().toString();
		return recName;
	}
	
	@Override
	public String createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

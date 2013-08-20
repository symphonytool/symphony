package eu.compassresearch.theoremprover.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFieldField;
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
import org.overture.ast.types.PTypeBase;
import org.overture.ast.types.SBasicType;
import org.overture.ast.types.SMapType;
import org.overture.ast.types.SSeqType;

public class ThmTypeUtil {

	public static String isaFunc = "definition";
	public static String typeDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaType = "definition";
	public static String isaChan = "definition";
	public static String isaAbbr = "abbreviation";
	public static String isaInv = "inv";
	public static String isaFuncLambaVal = "d";
	
	
	static String isaNat = "@nat";
	static String isaBool = "@bool";
	static String isaChar = "@char";
	static String isaInt = "@int";
 	static String isaRat = "@rat";
 	static String isaReal = "@real";
 	static String isaToken = "@token";
 	static String isaErrBT = "@unknownBasicType";
	static String isaNat1 = "@nat1";

 	static String isaInMap= "@inmap of";
 	static String isaMap= "@map of";
 	static String isaMapTo= "to ";
 	static String isaErrMT= "@unknownMapType";
 	
 	static String isaSeq1= "@seq1 of";
 	static String isaSeq= "@seq of";
 	static String isaErrST= "@unknownSeqType";

	static String isaSet = "@set of";
	static String isaOtpOpen = "[";
	static String isaOtpClose = "]";
	static String isaProd = "\\<times>";
	static String isaQuoteLeft = "<\'\'";
	static String isaQuoteRight = "\'\'>";
	static String isaUnion = "|";
 	static String isaErrTBT= "@unknownBaseType";
 	
 	static String notHandled = "NOT_YET_HANDLED";
 	static String isaUndefined = "undefined";
	public static String isaFuncBar = "|";
	public static String isaFuncLambda = "lambda";
	public static String isaFuncLambdaPost = "iota";
	public static String isaFuncLambdaPostVal = "r";
	
	
	public static String getIsabelleType(PType tp){
		String type = "";
		
		if (tp instanceof ANamedInvariantType)
		{
			ANamedInvariantType tempType = (ANamedInvariantType) tp;
			type = "@" + tempType.toString();
		}//ELSE IS A RECORD TYPE
		else if (tp instanceof ARecordInvariantType)
		{
			type = ThmTypeUtil.getIsabelleRecordType(tp);
		}
		//ELSE IS A BASIC TYPE
		else if (tp instanceof SBasicType)
		{
			 type = ThmTypeUtil.getIsabelleBasicType(tp);
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SMapType)
		{
			 type = ThmTypeUtil.getIsabelleMapType(tp);
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SSeqType)
		{
			type = ThmTypeUtil.getIsabelleSeqType(tp);
		}
		//ELSE IS ANOTHER TYPE
		else if (tp instanceof PTypeBase)
		{
			type = ThmTypeUtil.getIsabelleTypeBaseType(tp);
		}//ELSE IS ANOTHER TYPE
		else if (tp instanceof ARecordInvariantType)
		{
			type = ThmTypeUtil.getIsabelleRecordType(tp);
		}
		return type;
	}
	

	public static String getIsabelleBasicType(PType type) {
		
		if (type instanceof ANatNumericBasicType)
		{
			return isaNat;
		}
		else if (type instanceof ABooleanBasicType)
		{
			return isaBool;
		}
		else if (type instanceof ACharBasicType)
		{
			return isaChar;
		}
		else if (type instanceof ATokenBasicType)
		{
			return isaToken;
		}
		else if (type instanceof ANatOneNumericBasicType)
		{
			return isaNat1;
		}
		else if (type instanceof ARationalNumericBasicType)
		{
			return isaRat;
		}
		else if (type instanceof ARealNumericBasicType)
		{
			return isaReal;
		}
		else if (type instanceof AIntNumericBasicType)
		{
			return isaInt;
		}
		else
		{
			return isaErrBT;
		}
	}
	
	public static String getIsabelleMapType(PType type) {
		
		if (type instanceof AInMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			return (isaInMap + " " + ThmTypeUtil.getIsabelleType(m.getFrom()) + " " + isaMapTo +  ThmTypeUtil.getIsabelleType(m.getTo()));
		}
		else if (type instanceof AMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			return (isaMap + " " + ThmTypeUtil.getIsabelleType(m.getFrom()) + " " + isaMapTo +  ThmTypeUtil.getIsabelleType(m.getTo()));
		}
		else
		{
			return isaErrMT;
		}
	}
	
	public static String getIsabelleSeqType(PType type) {
		if (type instanceof ASeqSeqType)
		{
			ASeqSeqType s = (ASeqSeqType) type;
			return (isaSeq + " " + ThmTypeUtil.getIsabelleType(s.getSeqof()));
		}
		else if (type instanceof ASeq1SeqType)
		{
			ASeq1SeqType s = (ASeq1SeqType) type;
			return (isaSeq1 + " " + ThmTypeUtil.getIsabelleType(s.getSeqof()));
		}
		else
		{
			return isaErrST;
		}
	}
	

	public static String getIsabelleTypeBaseType(PType type) {
		
		if (type instanceof ASetType)
		{
			ASetType s = (ASetType) type;
			return (isaSet  + " " + ThmTypeUtil.getIsabelleType(s.getSetof()));
		}
		else if (type instanceof ABracketType)
		{
			ABracketType b = (ABracketType) type;
			return (ThmTypeUtil.getIsabelleType(b.getType()));
		}
		else if (type instanceof AClassType)
		{
			return notHandled;
		}
		else if (type instanceof AFunctionType)
		{
			return notHandled;
		}
		else if (type instanceof AOperationType)
		{
			return notHandled;
		}
		else if (type instanceof AOptionalType)
		{
			AOptionalType o = (AOptionalType) type;
			return (isaOtpOpen + ThmTypeUtil.getIsabelleType(o.getType()) +  isaOtpClose);
		}
		else if (type instanceof AParameterType)
		{
			return notHandled;
		}
		else if (type instanceof AProductType)
		{
			AProductType p = (AProductType) type;
			LinkedList<PType> types = p.getTypes();
			StringBuilder sb = new StringBuilder();
			
			//For each type in the product, add it to the string
			for (Iterator<PType> itr = types.listIterator(); itr.hasNext(); ) {
				PType t = itr.next();
				sb.append(ThmTypeUtil.getIsabelleType(t));
				//If there are remaining types, add a isaProd *
				if(itr.hasNext()){	
					sb.append(ThmTypeUtil.isaProd);
				}
			}
			return sb.toString();
		}
		else if (type instanceof AQuoteType)
		{
			AQuoteType q = (AQuoteType) type;
			return (ThmTypeUtil.isaQuoteLeft + q.getValue().getValue() + ThmTypeUtil.isaQuoteRight);
		}
		else if (type instanceof AUndefinedType)
		{
			AUndefinedType u = (AUndefinedType) type;
			return isaUndefined;
		}
		else if (type instanceof AUnionType)
		{
			AUnionType u = (AUnionType) type;

			LinkedList<PType> types = u.getTypes();
			StringBuilder sb = new StringBuilder();
			
			//For each type in the product, add it to the string
			for (Iterator<PType> itr = types.listIterator(); itr.hasNext(); ) {
				PType t = itr.next();
				sb.append(ThmTypeUtil.getIsabelleType(t));
				//If there are remaining types, add a isaUnion |
				if(itr.hasNext()){	
					sb.append(ThmTypeUtil.isaUnion);
				}
			}
			return sb.toString();
		}
		else if (type instanceof AUnknownType)
		{
			AUnknownType u = (AUnknownType) type;
			return u.getDefinitions().toString(); //???
			//return notHandled;
		}
		else if (type instanceof AUnresolvedType)
		{
			AUnresolvedType u = (AUnresolvedType) type;
			return notHandled;
		}
		else if (type instanceof AVoidReturnType)
		{
			AVoidReturnType vr = (AVoidReturnType) type;
			return notHandled;
		}
		else if (type instanceof AVoidType)
		{
			AVoidType v = (AVoidType) type;
			return notHandled;
		}
		else
		{
			return isaErrTBT;
		}
	}
	
	
	private static String getIsabelleRecordType(PType tp) {

		ARecordInvariantType rtype = (ARecordInvariantType) tp;
		String recName = rtype.getName().toString();
		StringBuilder sb = new StringBuilder();
		
		sb.append("typedef " + recName + "_Tag = \"{True}\" by auto \n");
		sb.append("instantiation " + recName + "_Tag :: tag\n");
		sb.append("begin \n");
		sb.append(isaType + " \"tagName_" + recName + "_Tag (x::" + recName + "_Tag) = \'\'"+ recName +"\'\'\" \n");
		sb.append("instance  \n");
		sb.append("by (intro_classes, metis (full_types) Abs_" + recName +"_Tag_cases singleton_iff) \n");
		sb.append("end \n\n");

		int count = 1;
		for (AFieldField field: rtype.getFields())
		{
			String fldNm = field.getTag();
			String fldTp = ThmTypeUtil.getIsabelleType(field.getType());
	
			sb.append(ThmTypeUtil.isaAbbr + "\"" + fldNm +"_fld " + ThmTypeUtil.isaEquiv + " MkField TYPE(" + recName + "_Tag) #" + count+ ThmTypeUtil.typeDelim + fldTp + ThmTypeUtil.typeDelim + "\"\n");  
		
			count ++;
		}
		sb.append("\n");

		for (AFieldField field: rtype.getFields())
		{
			String fldNm = field.getTag();
			String fldTp = ThmTypeUtil.getIsabelleType(field.getType());
		
			sb.append(ThmTypeUtil.isaAbbr + "\"" + fldNm + " " + ThmTypeUtil.isaEquiv + " SelectRec " + fldNm + "_fld\"\n");  
				
		}
		sb.append("\n");
				
		sb.append(ThmTypeUtil.isaType +"\n");
		sb.append("\"" + recName + " " + ThmTypeUtil.isaEquiv + ThmTypeUtil.typeDelim + "[");
		
		LinkedList<AFieldField> flds = rtype.getFields();
		//For each type in the product, add it to the string
		for (Iterator<AFieldField> itr = flds.listIterator(); itr.hasNext(); ) {
			AFieldField fld = itr.next();
			sb.append(fld.getTag() + "_fld");
			
			//If there are remaining fields, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		sb.append("]" + ThmTypeUtil.typeDelim + "\"\n\n");
		sb.append(ThmTypeUtil.isaType + " \"mk_" + recName + ThmTypeUtil.isaEquiv + "MkRec "+ recName+ "\"\n");
		
		return sb.toString();
	}

	
	
	public static LinkedList<ILexNameToken> getIsabelleTypeDeps(PType tp){
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if (tp instanceof ANamedInvariantType)
		{
			ANamedInvariantType tempType = (ANamedInvariantType) tp;
			nodeDeps.add(tempType.getName());
		}
		//ELSE IS A RECORD TYPE
		else if (tp instanceof ARecordInvariantType)
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleRecordDeps(tp));
		}
		//ELSE IS A BASIC TYPE
		else if (tp instanceof SBasicType)
		{
			//DO NOTHING
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SMapType)
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleMapDeps(tp));
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SSeqType)
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleSeqDeps(tp));
		}
		//ELSE IS ANOTHER TYPE
		else if (tp instanceof PTypeBase)
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeBaseDeps(tp));
		}
		return nodeDeps;
	}
	
	private static LinkedList<ILexNameToken> getIsabelleRecordDeps(
			PType tp) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		//TODO: Deduce deps for records (essentially look at each field type

		ARecordInvariantType rtype = (ARecordInvariantType) tp;

		for (AFieldField field: rtype.getFields())
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(field.getType()));				
		}
		return nodeDeps;
	}


	public static LinkedList<ILexNameToken> getIsabelleMapDeps(PType type) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if (type instanceof AInMapMapType)
		{
			AInMapMapType m = (AInMapMapType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(m.getFrom()));
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(m.getTo()));
		}
		else if (type instanceof AMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(m.getFrom()));
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(m.getTo()));
		}
		else
		{
		}
		return nodeDeps;
	}

	public static LinkedList<ILexNameToken> getIsabelleSeqDeps(PType type) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if (type instanceof ASeqSeqType)
		{
			ASeqSeqType s = (ASeqSeqType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(s.getSeqof()));
		}
		else if (type instanceof ASeq1SeqType)
		{
			ASeq1SeqType s = (ASeq1SeqType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(s.getSeqof()));
		}
		else
		{
		}
		return nodeDeps;
	}
	
	public static LinkedList<ILexNameToken> getIsabelleTypeBaseDeps(PType type) {
		//TODO: Here need to return type in Isabelle syntax 
		//return type.toString();
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if (type instanceof ASetType)
		{
			ASetType s = (ASetType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(s.getSetof()));
		}
		//TODO: Address other types
		else if (type instanceof ABracketType)
		{
			ABracketType b = (ABracketType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(b.getType()));
		}
		else if (type instanceof AClassType)
		{
		}
		else if (type instanceof AFunctionType)
		{
		}
		else if (type instanceof AOperationType)
		{
		}
		else if (type instanceof AOptionalType)
		{
			AOptionalType o = (AOptionalType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(o.getType()));
		}
		else if (type instanceof AParameterType)
		{
		}
		else if (type instanceof AProductType)
		{
			AProductType p = (AProductType) type;
			
			for(PType tp: p.getTypes())
			{
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tp));
			}
		}
		else if (type instanceof AQuoteType)
		{
			//Don't think anything should be added here
		}
		else if (type instanceof AUndefinedType)
		{
		}
		else if (type instanceof AUnionType)
		{
			AUnionType u = (AUnionType) type;
			
			for(PType tp: u.getTypes())
			{
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tp));
			}
		}
		else if (type instanceof AUnknownType)
		{
		}
		else if (type instanceof AUnresolvedType)
		{
		}
		else if (type instanceof AVoidReturnType)
		{
		}
		else if (type instanceof AVoidType)
		{
		}
		else
		{
		}
		return nodeDeps;
	}
	
	public static String getIsabelleTypeInv(ATypeDefinition node)
	{
		String inv = "";
		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			LinkedList<ILexNameToken> svars = new LinkedList<ILexNameToken>();
			LinkedList<ILexNameToken> evars = new LinkedList<ILexNameToken>();
			evars.add(((AIdentifierPattern) invPatt).getName());
			inv = (" " + ThmTypeUtil.isaInv  + " " + invPatt.toString() + " == " + ThmExprUtil.getIsabelleExprStr(svars, evars, invExp));
		}
		
		return inv;
	}
	
	
	public static LinkedList<ILexNameToken>  getIsabelleTypeInvDeps(ATypeDefinition node)
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			LinkedList<ILexNameToken> evars = new LinkedList<ILexNameToken>();
			evars.add(((AIdentifierPattern) invPatt).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(evars, invExp));
		}
		
		return nodeDeps;
	}
}

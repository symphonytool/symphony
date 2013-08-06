package eu.compassresearch.theoremprover;

import java.util.LinkedList;

import org.overture.ast.intf.lex.ILexNameToken;
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

public class ThmUtil {

	static String typeDelim = "\\<parallel>";
	static String isaType = "definition";
	public static String isaInv = "inv";
	
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
 	static String isaErrTBT= "@unknownBaseType";
 	
 	static String notHandled = "NOT_YET_HANDLED";
	
	
	public static String getIsabelleType(PType tp){
		String type = "";
		
		if (tp instanceof ANamedInvariantType)
		{
			ANamedInvariantType tempType = (ANamedInvariantType) tp;
			type = "@" + tempType.toString();
		}
		//ELSE IS A BASIC TYPE
		else if (tp instanceof SBasicType)
		{
			 type = ThmUtil.getIsabelleBasicType(tp);
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SMapType)
		{
			 type = ThmUtil.getIsabelleMapType(tp);
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SSeqType)
		{
			type = ThmUtil.getIsabelleSeqType(tp);
		}
		//ELSE IS ANOTHER TYPE
		else if (tp instanceof PTypeBase)
		{
		//	type = ThmUtil.getIsabelleTypeBaseType(tp);
		}
		return type;
	}
	
	public static String getIsabelleBasicType(PType type) {
		//TODO: Here need to return type in Isabelle syntax 
		//return type.toString();
		
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
		//TODO: Here need to return type in Isabelle syntax 
		//return type.toString();
		
		if (type instanceof AInMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			return (isaInMap + " " + ThmUtil.getIsabelleType(m.getFrom()) + " " + isaMapTo +  ThmUtil.getIsabelleType(m.getTo()));
		}
		else if (type instanceof AMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			return (isaMap + " " + ThmUtil.getIsabelleType(m.getFrom()) + " " + isaMapTo +  ThmUtil.getIsabelleType(m.getTo()));
		}
		else
		{
			return isaErrMT;
		}
	}
	
	public static String getIsabelleSeqType(PType type) {
		//TODO: Here need to return type in Isabelle syntax 
		//return type.toString();
		
		if (type instanceof ASeqSeqType)
		{
			ASeqSeqType s = (ASeqSeqType) type;
			return (isaSeq + " " + ThmUtil.getIsabelleType(s.getSeqof()));
		}
		else if (type instanceof ASeq1SeqType)
		{
			ASeq1SeqType s = (ASeq1SeqType) type;
			return (isaSeq1 + " " + ThmUtil.getIsabelleType(s.getSeqof()));
		}
		else
		{
			return isaErrST;
		}
	}
	

	public static String getIsabelleTypeBaseType(PType type) {
		//TODO: Here need to return type in Isabelle syntax 
		//return type.toString();
		
		if (type instanceof ASetType)
		{
			ASetType s = (ASetType) type;
			return (isaSet  + " " + ThmUtil.getIsabelleType(s.getSetof()));
		}
		//TODO: Address other types
		else if (type instanceof ABracketType)
		{
			ABracketType b = (ABracketType) type;
			return notHandled;
		}
		else if (type instanceof AClassType)
		{
			AClassType c = (AClassType) type;
			return notHandled;
		}
		else if (type instanceof AFunctionType)
		{
			AFunctionType f = (AFunctionType) type;
			return notHandled;
		}
		else if (type instanceof AOperationType)
		{
			AOperationType o = (AOperationType) type;
			return notHandled;
		}
		else if (type instanceof AOptionalType)
		{
			AOptionalType o = (AOptionalType) type;
			return notHandled;
		}
		else if (type instanceof AParameterType)
		{
			AParameterType p = (AParameterType) type;
			return notHandled;
		}
		else if (type instanceof AProductType)
		{
			AProductType p = (AProductType) type;
			return notHandled;
		}
		else if (type instanceof AQuoteType)
		{
			AQuoteType q = (AQuoteType) type;
			return notHandled;
		}
		else if (type instanceof AUndefinedType)
		{
			AUndefinedType u = (AUndefinedType) type;
			return notHandled;
		}
		else if (type instanceof AUnionType)
		{
			AUnionType u = (AUnionType) type;
			return notHandled;
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
	
	
	public static LinkedList<ILexNameToken> getIsabelleTypeDeps(PType tp){
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();
		
		if (tp instanceof ANamedInvariantType)
		{
			ANamedInvariantType tempType = (ANamedInvariantType) tp;
			nodeDeps.add(tempType.getName());
		}
		//ELSE IS A BASIC TYPE
		else if (tp instanceof SBasicType)
		{
			//DO NOTHING
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SMapType)
		{
			nodeDeps.addAll(ThmUtil.getIsabelleMapDeps(tp));
		}
		//ELSE IS A MAP TYPE
		else if (tp instanceof SSeqType)
		{
			nodeDeps.addAll(ThmUtil.getIsabelleSeqDeps(tp));
		}
		//ELSE IS ANOTHER TYPE
		else if (tp instanceof PTypeBase)
		{
		//	nodeDeps.addAll(ThmUtil.getIsabelleTypeBaseDeps(tp));
		}
		return nodeDeps;
	}
	
	public static LinkedList<ILexNameToken> getIsabelleMapDeps(PType type) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();
		
		if (type instanceof AInMapMapType)
		{
			AInMapMapType m = (AInMapMapType) type;
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(m.getFrom()));
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(m.getTo()));
		}
		else if (type instanceof AMapMapType)
		{
			AMapMapType m = (AMapMapType) type;
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(m.getFrom()));
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(m.getTo()));
		}
		else
		{
		}
		return nodeDeps;
	}

	public static LinkedList<ILexNameToken> getIsabelleSeqDeps(PType type) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();
		
		if (type instanceof ASeqSeqType)
		{
			ASeqSeqType s = (ASeqSeqType) type;
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(s.getSeqof()));
		}
		else if (type instanceof ASeq1SeqType)
		{
			ASeq1SeqType s = (ASeq1SeqType) type;
			nodeDeps.addAll(ThmUtil.getIsabelleTypeDeps(s.getSeqof()));
		}
		else
		{
		}
		return nodeDeps;
	}
	

}

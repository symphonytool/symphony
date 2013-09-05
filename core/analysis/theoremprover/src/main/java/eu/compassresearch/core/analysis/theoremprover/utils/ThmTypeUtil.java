package eu.compassresearch.core.analysis.theoremprover.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.expressions.PExp;
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

import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

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
 	
 	static String notHandled = "(*type not yet handled*)";
 	static String isaUndefined = "undefined";
	public static String isaFuncBar = "|";
	public static String isaFuncLambda = "lambda";
	public static String isaFuncLambdaPost = "iota";
	public static String isaFuncLambdaPostVal = "r";
	
	
	/***
	 * Method to return an Isabelle String for a CML Type 
	 * @param tp - the type AST node
	 * @return The Isabelle string
	 */
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
	

	/***
	 * Method to return an Isabelle String for a CML Basic Type 
	 * @param tp - the type AST node
	 * @return The Isabelle string
	 */
	private static String getIsabelleBasicType(PType type) {
		
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
	

	/***
	 * Method to return an Isabelle String for a CML map type 
	 * @param tp - the map type AST node
	 * @return The Isabelle string
	 */
	private static String getIsabelleMapType(PType type) {
		
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
	

	/***
	 * Method to return an Isabelle String for a CML sequence type 
	 * @param tp - the sequence type AST node
	 * @return The Isabelle string
	 */
	private static String getIsabelleSeqType(PType type) {
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
	


	/***
	 * Method to return an Isabelle String for any other CML Type 
	 * @param tp - the type AST node
	 * @return The Isabelle string
	 */
	private static String getIsabelleTypeBaseType(PType type) {
		
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
		else if (type instanceof AOptionalType)
		{
			AOptionalType o = (AOptionalType) type;
			return (isaOtpOpen + ThmTypeUtil.getIsabelleType(o.getType()) +  isaOtpClose);
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
		//Several types are not handled - mostly be design
		else if (type instanceof AUnresolvedType)
		{
			return notHandled;
		}
		else if (type instanceof AVoidReturnType)
		{
			return notHandled;
		}
		else if (type instanceof AVoidType)
		{
			return notHandled;
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
		else if (type instanceof AParameterType)
		{
			return notHandled;
		}
		else
		{
			return isaErrTBT;
		}
	}
	
	/***
	 * Method to return an Isabelle String for a CML record type 
	 * @param tp - the record type AST node
	 * @return The Isabelle string
	 */
	private static String getIsabelleRecordType(PType tp) {

		ARecordInvariantType rtype = (ARecordInvariantType) tp;
		String recName = "@" + rtype.getName().toString();
		return recName;
	}

	
	/***
	 * Method to return the list of dependencies for a CML Type 
	 * @param tp - the type AST node
	 * @return a list of node dependencies
	 */
	public static NodeNameList getIsabelleTypeDeps(PType tp){
		NodeNameList nodeDeps = new NodeNameList();
		
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

	/***
	 * Method to return the list of dependencies for a CML record Type 
	 * @param tp - the record type AST node
	 * @return a list of node dependencies
	 */
	private static NodeNameList getIsabelleRecordDeps(
			PType tp) {
		NodeNameList nodeDeps = new NodeNameList();
		
		ARecordInvariantType rtype = (ARecordInvariantType) tp;

		for (AFieldField field: rtype.getFields())
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(field.getType()));				
		}
		return nodeDeps;
	}


	/***
	 * Method to return the list of dependencies for a CML map type 
	 * @param tp - the map type AST node
	 * @return a list of node dependencies
	 */
	private static NodeNameList getIsabelleMapDeps(PType type) {
		NodeNameList nodeDeps = new NodeNameList();
		
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


	/***
	 * Method to return the list of dependencies for a CML sequence type 
	 * @param tp - the sequence type AST node
	 * @return a list of node dependencies
	 */
	private static NodeNameList getIsabelleSeqDeps(PType type) {
		NodeNameList nodeDeps = new NodeNameList();
		
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

	/***
	 * Method to return the list of dependencies for any other CML Type 
	 * @param tp - the type AST node
	 * @return a list of node dependencies
	 */
	private static NodeNameList getIsabelleTypeBaseDeps(PType type) {
		NodeNameList nodeDeps = new NodeNameList();
		
		if (type instanceof ASetType)
		{
			ASetType s = (ASetType) type;
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(s.getSetof()));
		}
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
	

	/***
	 * Method to return a string for the type invariant
	 * @param node - the type AST node
	 * @return an invariant string
	 */
	public static String getIsabelleTypeInv(ATypeDefinition node)
	{
		String inv = "";
		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			NodeNameList svars = new NodeNameList();
			NodeNameList evars = new NodeNameList();
			evars.add(((AIdentifierPattern) invPatt).getName());
			inv = (" " + ThmTypeUtil.isaInv  + " " + invPatt.toString() + " == " + ThmExprUtil.getIsabelleExprStr(svars, evars, invExp));
		}
		
		return inv;
	}
	

	/***
	 * Method to return the list of dependencies for the type invariant
	 * @param node - the type AST node
	 * @return the list of dependencies
	 */
	public static NodeNameList getIsabelleTypeInvDeps(ATypeDefinition node)
	{
		NodeNameList nodeDeps = new NodeNameList();

		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			NodeNameList evars = new NodeNameList();
			evars.add(((AIdentifierPattern) invPatt).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(evars, invExp));
		}
		
		return nodeDeps;
	}
}

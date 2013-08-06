package eu.compassresearch.theoremprover;

import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ANatOneNumericBasicType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.types.PType;

public class ThmUtil {

	static String isaNat = "@nat";
	static String isaBool = "@bool";
	static String isaChar = "@char";
	static String isaInt = "@int";
 	static String isaRat = "@rat";
 	static String isaReal = "@real";
 	static String isaToken = "@token";
 	static String isaErrTp = "@typeNotHandled";
	static String isaNat1 = "@nat1";

	static String typeDelim = "\\<parallel>";
	static String isaType = "definition";
	public static String isaInv = "inv";
	
	public static String getIsabelleNamedInvType(PType type) {
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
			return isaErrTp;
		}
	}

}

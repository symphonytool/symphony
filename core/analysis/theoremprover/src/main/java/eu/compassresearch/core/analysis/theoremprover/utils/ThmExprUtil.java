package eu.compassresearch.core.analysis.theoremprover.utils;


public class ThmExprUtil {
	
	public static String typeDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaAcute = "\\<acute>";
	public static String isaType = "definition";
	public static String isaAbbr = "abbreviation";
 
	public static String comp = " comp ";
	public static String domainResBy = " <-: ";
	public static String domainResTo = " <: ";
	public static String equals = " = ";
	public static String inSet = " in @set ";
	public static String mapUnion= " munion ";
	public static String notEqual = " <> ";
	public static String notInSet= " not in @set ";
	public static String plusPlus= " ++ ";
	public static String properSubset = " psubset ";
	public static String rangeResBy = ":->";
	public static String rangeResTo = ":>";
	public static String seqConcat = " ^ ";
	public static String setDifference = " setminus " ;
	public static String setIntersect = " inter ";
	public static String setUnion = " union ";
	public static String starStar = " ** ";
	public static String subset = " subset ";
	
	public static String absolute = "abs ";
	public static String cardinality = "card ";
	public static String distConcat = "conc ";
	public static String distIntersect = "dinter ";
	public static String distMerge = "dmerge ";
	public static String distUnion = "dunion ";
	public static String elements = "elems ";
	public static String floor = "floor ";
	public static String head = "hd ";
	public static String indices = "inds ";
	public static String len = "len ";
	public static String mapDomain = "dom ";
	public static String mapInverse = "inverse ";
	public static String mapRange = "rng ";
	public static String not = "not ";
	public static String powerSet = "power ";
	public static String reverse = "reverse ";
	public static String tail = "tl ";
	public static String unaryMinus = "-";
	public static String unaryPlus = "+ ";
 
	public static String notHandled = "(*expr not yet handled*)";
	public static String isaUndefined = "undefined";
	public static String isaDown = "\\<down>";
	
	public static boolean isPost = false;
	
	public static void setPostExpr(boolean post)
	{
		isPost = post;
	}
}
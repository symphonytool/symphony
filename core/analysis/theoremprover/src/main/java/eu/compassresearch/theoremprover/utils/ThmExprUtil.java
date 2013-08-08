package eu.compassresearch.theoremprover.utils;

import org.overture.ast.expressions.AAbsoluteUnaryExp;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.ACardinalityUnaryExp;
import org.overture.ast.expressions.ADistConcatUnaryExp;
import org.overture.ast.expressions.ADistIntersectUnaryExp;
import org.overture.ast.expressions.ADistMergeUnaryExp;
import org.overture.ast.expressions.ADistUnionUnaryExp;
import org.overture.ast.expressions.ADivNumericBinaryExp;
import org.overture.ast.expressions.ADivideNumericBinaryExp;
import org.overture.ast.expressions.AElementsUnaryExp;
import org.overture.ast.expressions.AEquivalentBooleanBinaryExp;
import org.overture.ast.expressions.AFloorUnaryExp;
import org.overture.ast.expressions.AGreaterEqualNumericBinaryExp;
import org.overture.ast.expressions.AGreaterNumericBinaryExp;
import org.overture.ast.expressions.AHeadUnaryExp;
import org.overture.ast.expressions.AIfExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.AIndicesUnaryExp;
import org.overture.ast.expressions.ALenUnaryExp;
import org.overture.ast.expressions.ALessEqualNumericBinaryExp;
import org.overture.ast.expressions.ALessNumericBinaryExp;
import org.overture.ast.expressions.AMapCompMapExp;
import org.overture.ast.expressions.AMapDomainUnaryExp;
import org.overture.ast.expressions.AMapEnumMapExp;
import org.overture.ast.expressions.AMapInverseUnaryExp;
import org.overture.ast.expressions.AMapRangeUnaryExp;
import org.overture.ast.expressions.AModNumericBinaryExp;
import org.overture.ast.expressions.ANilExp;
import org.overture.ast.expressions.ANotUnaryExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.APowerSetUnaryExp;
import org.overture.ast.expressions.AReverseUnaryExp;
import org.overture.ast.expressions.ASeqCompSeqExp;
import org.overture.ast.expressions.ASeqEnumSeqExp;
import org.overture.ast.expressions.ASetCompSetExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.ASetRangeSetExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATailUnaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AUnaryMinusUnaryExp;
import org.overture.ast.expressions.AUnaryPlusUnaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.PExpBase;
import org.overture.ast.expressions.SBinaryExpBase;
import org.overture.ast.expressions.SBooleanBinaryBase;
import org.overture.ast.expressions.SBooleanBinaryExp;
import org.overture.ast.expressions.SMapExp;
import org.overture.ast.expressions.SNumericBinaryExp;
import org.overture.ast.expressions.SSeqExp;
import org.overture.ast.expressions.SSetExp;
import org.overture.ast.expressions.SUnaryExpBase;

public class ThmExprUtil {
	
	public static String typeDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaType = "definition";
	public static String isaAbbr = "abbreviation";
 	
 	static String notHandled = "NOT_YET_HANDLED";
 	static String isaUndefined = "undefined";
 

	public static String getIsabelleExprStr(PExp ex)
	{
		String expr = "";
		// is a bool binary expression
		if (ex instanceof SBooleanBinaryExp){
			expr = ThmExprUtil.getBooleanBinaryExp(ex);
		}
		//is a numeric expression
		else if (ex instanceof SNumericBinaryExp){
			expr = ThmExprUtil.getNumericBinaryExp(ex);
		}
		else if (ex instanceof SMapExp){
			expr = ThmExprUtil.getMapExp(ex);
		}
		else if (ex instanceof SSeqExp){
			expr = ThmExprUtil.getSeqExp(ex);
		}
		else if (ex instanceof SSetExp){
			expr = ThmExprUtil.getSetExp(ex);
		}
		else if (ex instanceof SUnaryExpBase){
			expr = ThmExprUtil.getUnaryExp(ex);
		}
		else if (ex instanceof SBinaryExpBase){
			//TODO: This.
			expr = "binary exp not handled";
		}
		else if (ex instanceof PExpBase){
			expr = ThmExprUtil.getOtherExp(ex);
		}
		return expr;
	}

	private static String getUnaryExp(PExp ex) {
		if(ex instanceof AAbsoluteUnaryExp){
			AAbsoluteUnaryExp abs = (AAbsoluteUnaryExp) ex;
			return "abs " + ThmExprUtil.getIsabelleExprStr(abs.getExp());
		}
		else if(ex instanceof ACardinalityUnaryExp){
			ACardinalityUnaryExp card = (ACardinalityUnaryExp) ex;
			return "card " + ThmExprUtil.getIsabelleExprStr(card.getExp());
		}
		else if(ex instanceof ADistConcatUnaryExp){
			ADistConcatUnaryExp en = (ADistConcatUnaryExp) ex;
			return "expr not handled";
		}
		else if(ex instanceof ADistIntersectUnaryExp){
			ADistIntersectUnaryExp dinter = (ADistIntersectUnaryExp) ex;
			return "dinter " + ThmExprUtil.getIsabelleExprStr(dinter.getExp());
		}
		else if(ex instanceof ADistMergeUnaryExp){
			ADistMergeUnaryExp dmerge = (ADistMergeUnaryExp) ex;
			return "dmerge " + ThmExprUtil.getIsabelleExprStr(dmerge.getExp());
		}
		else if(ex instanceof ADistUnionUnaryExp){
			ADistUnionUnaryExp dunion = (ADistUnionUnaryExp) ex;
			return "dunion " + ThmExprUtil.getIsabelleExprStr(dunion.getExp());
		}
		else if(ex instanceof AElementsUnaryExp){
			AElementsUnaryExp e = (AElementsUnaryExp) ex;
			return "elems " + ThmExprUtil.getIsabelleExprStr(e.getExp());
		}
		else if(ex instanceof AFloorUnaryExp){
			AFloorUnaryExp fl = (AFloorUnaryExp) ex;
			return "floor " + ThmExprUtil.getIsabelleExprStr(fl.getExp());
		}
		else if(ex instanceof AHeadUnaryExp){
			AHeadUnaryExp hd = (AHeadUnaryExp) ex;
			return "hd " + ThmExprUtil.getIsabelleExprStr(hd.getExp());
		}
		else if(ex instanceof AIndicesUnaryExp){
			AIndicesUnaryExp inds = (AIndicesUnaryExp) ex;
			return "inds " + ThmExprUtil.getIsabelleExprStr(inds.getExp());
		}
		else if(ex instanceof ALenUnaryExp){
			ALenUnaryExp len = (ALenUnaryExp) ex;
			return "len " + ThmExprUtil.getIsabelleExprStr(len.getExp());
		}
		else if(ex instanceof AMapDomainUnaryExp){
			AMapDomainUnaryExp dom = (AMapDomainUnaryExp) ex;
			return "dom " + ThmExprUtil.getIsabelleExprStr(dom.getExp());
		}
		else if(ex instanceof AMapInverseUnaryExp){
			AMapInverseUnaryExp inverse = (AMapInverseUnaryExp) ex;
			return "inverse " + ThmExprUtil.getIsabelleExprStr(inverse.getExp());
		}
		else if(ex instanceof AMapRangeUnaryExp){
			AMapRangeUnaryExp rng = (AMapRangeUnaryExp) ex;
			return "rng " + ThmExprUtil.getIsabelleExprStr(rng.getExp());
		}
		else if(ex instanceof ANotUnaryExp){
			ANotUnaryExp not = (ANotUnaryExp) ex;
			return "not " + ThmExprUtil.getIsabelleExprStr(not.getExp());
		}
		else if(ex instanceof APowerSetUnaryExp){
			APowerSetUnaryExp pwr = (APowerSetUnaryExp) ex;
			return "power " + ThmExprUtil.getIsabelleExprStr(pwr.getExp());
		}
		else if(ex instanceof AReverseUnaryExp){
			AReverseUnaryExp rev = (AReverseUnaryExp) ex;
			return "reverse " + ThmExprUtil.getIsabelleExprStr(rev.getExp());
		}
		else if(ex instanceof ATailUnaryExp){
			ATailUnaryExp tl = (ATailUnaryExp) ex;
			return "tl " + ThmExprUtil.getIsabelleExprStr(tl.getExp());
		}
		else if(ex instanceof AUnaryMinusUnaryExp){
			AUnaryMinusUnaryExp minus = (AUnaryMinusUnaryExp) ex;
			return "- " + ThmExprUtil.getIsabelleExprStr(minus.getExp());
		}
		else if(ex instanceof AUnaryPlusUnaryExp){
			AUnaryPlusUnaryExp plus = (AUnaryPlusUnaryExp) ex;
			return "+" + ThmExprUtil.getIsabelleExprStr(plus.getExp());
		}
		else{
			return "map expr not handled";
		}
	}

	private static String getOtherExp(PExp ex) {
		if(ex instanceof ABooleanConstExp){
			ABooleanConstExp bool = (ABooleanConstExp) ex;
			return bool.getValue().toString();
		}
		if(ex instanceof ANilExp){
			return "nil";
		}
		if(ex instanceof AIfExp){
			AIfExp ifExp = (AIfExp) ex;
			return "if " + ThmExprUtil.getIsabelleExprStr(ifExp.getTest()) + "then" + ThmExprUtil.getIsabelleExprStr(ifExp.getThen());
		}
		else{
			return "map expr not handled";
		}
		
		
		//AApplyExp, , ACasesExp, ACharLiteralExp, ADefExp, AElseIfExp, AExists1Exp, AExistsExp, AFieldExp, AFieldNumberExp, AForAllExp, AFuncInstatiationExp, AHistoryExp, AIfExp, AIntLiteralExp, AIotaExp, AIsExp, AIsOfBaseClassExp, AIsOfClassExp, ALambdaExp, ALetBeStExp, ALetDefExp, AMapletExp, AMkBasicExp, AMkTypeExp, AMuExp, ANarrowExp, ANewExp, ANilExp, ANotYetSpecifiedExp, APostOpExp, APreExp, APreOpExp, AQuoteLiteralExp, ARealLiteralExp, ASameBaseClassExp, ASameClassExp, ASelfExp, AStateInitExp, AStringLiteralExp, ASubclassResponsibilityExp, ASubseqExp, AThreadIdExp, ATimeExp, ATupleExp, AUndefinedExp, AVariableExp
	}

	private static String getMapExp(PExp ex) {
		if(ex instanceof AMapCompMapExp){
			AMapCompMapExp comp = (AMapCompMapExp) ex;
			return "expr not handled";
		}
		else if(ex instanceof AMapEnumMapExp){
			AMapEnumMapExp en = (AMapEnumMapExp) ex;
			return "expr not handled";
		}
		else{
			return "map expr not handled";
		}
	}
	
	private static String getSeqExp(PExp ex) {
		if(ex instanceof ASeqCompSeqExp){
			ASeqCompSeqExp comp = (ASeqCompSeqExp) ex;
			return "expr not handled";
		}
		else if(ex instanceof ASeqEnumSeqExp){
			ASeqEnumSeqExp en = (ASeqEnumSeqExp) ex;
			return "expr not handled";
		}
		else{
			return "seq expr not handled";
		}
	}
	
	private static String getSetExp(PExp ex) {
		if(ex instanceof ASetCompSetExp){
			ASetCompSetExp comp = (ASetCompSetExp) ex;
			return "expr not handled";
		}
		else if(ex instanceof ASetEnumSetExp){
			ASetEnumSetExp en = (ASetEnumSetExp) ex;
			return "expr not handled";
		}
		else if(ex instanceof ASetRangeSetExp){
			ASetRangeSetExp en = (ASetRangeSetExp) ex;
			return "expr not handled";
		}
		else
		{
			return "Set expr not handled";
		}
	}


	private static String getNumericBinaryExp(PExp ex) {

		if(ex instanceof ADivideNumericBinaryExp){
			ADivideNumericBinaryExp div = (ADivideNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(div.getLeft()) + " \'/ " + ThmExprUtil.getIsabelleExprStr(div.getRight()));
		}
		else if(ex instanceof ADivNumericBinaryExp){
			ADivNumericBinaryExp div = (ADivNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(div.getLeft()) + " div " + ThmExprUtil.getIsabelleExprStr(div.getRight()));
		}
		else if(ex instanceof AGreaterEqualNumericBinaryExp){
			AGreaterEqualNumericBinaryExp ge = (AGreaterEqualNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(ge) + " >= " + ThmExprUtil.getIsabelleExprStr(ge.getRight()));

		}
		else if(ex instanceof AGreaterNumericBinaryExp){
			AGreaterNumericBinaryExp gt = (AGreaterNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(gt) + " > " + ThmExprUtil.getIsabelleExprStr(gt.getRight()));

		}
		else if(ex instanceof ALessEqualNumericBinaryExp){
			ALessEqualNumericBinaryExp le = (ALessEqualNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(le) + " <= " + ThmExprUtil.getIsabelleExprStr(le.getRight()));

		}
		else if(ex instanceof ALessNumericBinaryExp){
			ALessNumericBinaryExp lt = (ALessNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(lt) + " < " + ThmExprUtil.getIsabelleExprStr(lt.getRight()));

		}
		else if(ex instanceof AModNumericBinaryExp){
			AModNumericBinaryExp mod = (AModNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(mod) + " mod " + ThmExprUtil.getIsabelleExprStr(mod.getRight()));

		}
		else if(ex instanceof APlusNumericBinaryExp){
			APlusNumericBinaryExp pl = (APlusNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(pl) + " + " + ThmExprUtil.getIsabelleExprStr(pl.getRight()));

		}
		else if(ex instanceof ASubtractNumericBinaryExp){
			ASubtractNumericBinaryExp sub = (ASubtractNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(sub) + " - " + ThmExprUtil.getIsabelleExprStr(sub));

		}
		else if(ex instanceof ATimesNumericBinaryExp){
			ATimesNumericBinaryExp times = (ATimesNumericBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(times) + " * " + ThmExprUtil.getIsabelleExprStr(times));

		}
		else //ARemNumericBinaryExp?
		{
			return  "expr not handled";
		}
	}


	private static String getBooleanBinaryExp(PExp ex) {
		
		if(ex instanceof AAndBooleanBinaryExp){
			AAndBooleanBinaryExp and = (AAndBooleanBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(and.getLeft()) + "and" + ThmExprUtil.getIsabelleExprStr(and.getRight()));
		}
		else if(ex instanceof AEquivalentBooleanBinaryExp){
			return "expr not handled";
		}
		else if(ex instanceof AImpliesBooleanBinaryExp){
			AImpliesBooleanBinaryExp imp = (AImpliesBooleanBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(imp.getLeft()) + "=>" + ThmExprUtil.getIsabelleExprStr(imp.getRight()));
		}
		else if(ex instanceof AOrBooleanBinaryExp){
			AOrBooleanBinaryExp or = (AOrBooleanBinaryExp) ex;
			return (ThmExprUtil.getIsabelleExprStr(or.getLeft()) + "or" +ThmExprUtil.getIsabelleExprStr(or.getRight()));
			
		}
		else
		{
			return "expr not handled";
		}
	}
	
	

 	
}

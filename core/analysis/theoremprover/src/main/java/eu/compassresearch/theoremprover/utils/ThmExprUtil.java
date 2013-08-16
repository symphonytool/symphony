package eu.compassresearch.theoremprover.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.*;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;

public class ThmExprUtil {
	
	public static String typeDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaType = "definition";
	public static String isaAbbr = "abbreviation";
 	
 	static String notHandled = "NOT_YET_HANDLED";
 	static String isaUndefined = "undefined";
	private static String isaDown = "\\<down>";
 

	public static String getIsabelleExprStr(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex)
	{
		String expr = "";
		// is a bool binary expression
		if (ex instanceof SBooleanBinaryExp){
			expr = ThmExprUtil.getBooleanBinaryExp(svars, bvars, ex);
		}
		//is a numeric expression
		else if (ex instanceof SNumericBinaryExp){
			expr = ThmExprUtil.getNumericBinaryExp(svars, bvars, ex);
		}
		else if (ex instanceof SMapExp){
			expr = ThmExprUtil.getMapExp(svars, bvars, ex);
		}
		else if (ex instanceof SSeqExp){
			expr = ThmExprUtil.getSeqExp(svars, bvars, ex);
		}
		else if (ex instanceof SSetExp){
			expr = ThmExprUtil.getSetExp(svars, bvars, ex);
		}
		else if (ex instanceof SUnaryExpBase){
			expr = ThmExprUtil.getUnaryExp(svars, bvars, ex);
		}
		else if (ex instanceof SBinaryExpBase){
			expr = ThmExprUtil.getBinaryExp(svars, bvars, ex);
		}
		else{//if (ex instanceof PExpBase){
			expr = ThmExprUtil.getOtherExp(svars, bvars, ex);
		}
		return expr;
	}

	private static String getBinaryExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		
		if(ex instanceof ACompBinaryExp){
			ACompBinaryExp comp = (ACompBinaryExp) ex;
			return "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, comp.getLeft()) + " comp " + ThmExprUtil.getIsabelleExprStr(svars, bvars, comp.getRight()) + ")";
		}
		else if(ex instanceof ADomainResByBinaryExp){
			ADomainResByBinaryExp dres = (ADomainResByBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, dres.getLeft()) + " <-: " + ThmExprUtil.getIsabelleExprStr(svars, bvars, dres.getRight())+ ")";
		}
		else if(ex instanceof ADomainResToBinaryExp){
			ADomainResToBinaryExp dres = (ADomainResToBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, dres.getLeft()) + " <: " + ThmExprUtil.getIsabelleExprStr(svars, bvars, dres.getRight())+ ")";
		}
		else if(ex instanceof AEqualsBinaryExp){
			AEqualsBinaryExp eq = (AEqualsBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, eq.getLeft()) + " = " + ThmExprUtil.getIsabelleExprStr(svars, bvars, eq.getRight())+ ")";
		}
		else if(ex instanceof AInSetBinaryExp){
			AInSetBinaryExp ins = (AInSetBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, ins.getLeft()) + " in @set " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ins.getRight())+ ")";
		}
		else if(ex instanceof AMapUnionBinaryExp){
			AMapUnionBinaryExp mun = (AMapUnionBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, mun.getLeft()) + " munion " + ThmExprUtil.getIsabelleExprStr(svars, bvars, mun.getRight())+ ")";
		}
		else if(ex instanceof ANotEqualBinaryExp){
			ANotEqualBinaryExp neq = (ANotEqualBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, neq.getLeft()) + " <> " + ThmExprUtil.getIsabelleExprStr(svars, bvars, neq.getRight())+ ")";
		}
		else if(ex instanceof ANotInSetBinaryExp){
			ANotInSetBinaryExp nins = (ANotInSetBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, nins.getLeft()) + " not in @set " + ThmExprUtil.getIsabelleExprStr(svars, bvars, nins.getRight())+ ")";
		}
		else if(ex instanceof APlusPlusBinaryExp){
			APlusPlusBinaryExp plpl = (APlusPlusBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, plpl.getLeft()) + " ++ " + ThmExprUtil.getIsabelleExprStr(svars, bvars, plpl.getRight())+ ")";
		}
		else if(ex instanceof AProperSubsetBinaryExp){
			AProperSubsetBinaryExp psub = (AProperSubsetBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, psub.getLeft()) + " psubset " + ThmExprUtil.getIsabelleExprStr(svars, bvars, psub.getRight())+ ")";
		}
		else if(ex instanceof ARangeResByBinaryExp){
			ARangeResByBinaryExp rres = (ARangeResByBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, rres.getLeft()) + ":->" + ThmExprUtil.getIsabelleExprStr(svars, bvars, rres.getRight())+ ")";
		}
		else if(ex instanceof ARangeResToBinaryExp){
			ARangeResToBinaryExp rres = (ARangeResToBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, rres.getLeft()) + ":>" + ThmExprUtil.getIsabelleExprStr(svars, bvars, rres.getRight())+ ")";
		}
		else if(ex instanceof ASeqConcatBinaryExp){
			ASeqConcatBinaryExp scon = (ASeqConcatBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, scon.getLeft()) + " ^ " + ThmExprUtil.getIsabelleExprStr(svars, bvars, scon.getRight())+ ")";
		}
		else if(ex instanceof ASetDifferenceBinaryExp){
			ASetDifferenceBinaryExp sdiff = (ASetDifferenceBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, sdiff.getLeft()) + " setminus " + ThmExprUtil.getIsabelleExprStr(svars, bvars, sdiff.getRight())+ ")";
		}
		else if(ex instanceof ASetIntersectBinaryExp){
			ASetIntersectBinaryExp inter = (ASetIntersectBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, inter.getLeft()) + " inter " + ThmExprUtil.getIsabelleExprStr(svars, bvars, inter.getRight())+ ")";
		}
		else if(ex instanceof ASetUnionBinaryExp){
			ASetUnionBinaryExp union = (ASetUnionBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, union.getLeft()) + " union " + ThmExprUtil.getIsabelleExprStr(svars, bvars, union.getRight())+ ")";
		}
		else if(ex instanceof AStarStarBinaryExp){
			AStarStarBinaryExp star = (AStarStarBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, star.getLeft()) + " ** " + ThmExprUtil.getIsabelleExprStr(svars, bvars, star.getRight())+ ")";
		}
		else if(ex instanceof ASubsetBinaryExp){
			ASubsetBinaryExp subs = (ASubsetBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, subs.getLeft()) + " subset " + ThmExprUtil.getIsabelleExprStr(svars, bvars, subs.getRight())+ ")";
		}
		else{
			return "binary expr not handled";
		}
	}

	private static String getUnaryExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		if(ex instanceof AAbsoluteUnaryExp){
			AAbsoluteUnaryExp abs = (AAbsoluteUnaryExp) ex;
			return "abs " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, abs.getExp())+ ")";
		}
		else if(ex instanceof ACardinalityUnaryExp){
			ACardinalityUnaryExp card = (ACardinalityUnaryExp) ex;
			return "card " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, card.getExp())+ ")";
		}
		else if(ex instanceof ADistConcatUnaryExp){
			ADistConcatUnaryExp conc = (ADistConcatUnaryExp) ex;
			return "conc " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, conc.getExp())+ ")";
		}
		else if(ex instanceof ADistIntersectUnaryExp){
			ADistIntersectUnaryExp dinter = (ADistIntersectUnaryExp) ex;
			return "dinter " + "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, dinter.getExp())+ ")";
		}
		else if(ex instanceof ADistMergeUnaryExp){
			ADistMergeUnaryExp dmerge = (ADistMergeUnaryExp) ex;
			return "dmerge " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, dmerge.getExp())+ ")";
		}
		else if(ex instanceof ADistUnionUnaryExp){
			ADistUnionUnaryExp dunion = (ADistUnionUnaryExp) ex;
			return "dunion " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, dunion.getExp())+ ")";
		}
		else if(ex instanceof AElementsUnaryExp){
			AElementsUnaryExp e = (AElementsUnaryExp) ex;
			return "elems " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, e.getExp())+ ")";
		}
		else if(ex instanceof AFloorUnaryExp){
			AFloorUnaryExp fl = (AFloorUnaryExp) ex;
			return "floor " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, fl.getExp())+ ")";
		}
		else if(ex instanceof AHeadUnaryExp){
			AHeadUnaryExp hd = (AHeadUnaryExp) ex;
			return "hd " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, hd.getExp())+ ")";
		}
		else if(ex instanceof AIndicesUnaryExp){
			AIndicesUnaryExp inds = (AIndicesUnaryExp) ex;
			return "inds " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, inds.getExp())+ ")";
		}
		else if(ex instanceof ALenUnaryExp){
			ALenUnaryExp len = (ALenUnaryExp) ex;
			return "len " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, len.getExp())+ ")";
		}
		else if(ex instanceof AMapDomainUnaryExp){
			AMapDomainUnaryExp dom = (AMapDomainUnaryExp) ex;
			return "dom " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, dom.getExp())+ ")";
		}
		else if(ex instanceof AMapInverseUnaryExp){
			AMapInverseUnaryExp inverse = (AMapInverseUnaryExp) ex;
			return "inverse " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, inverse.getExp())+ ")";
		}
		else if(ex instanceof AMapRangeUnaryExp){
			AMapRangeUnaryExp rng = (AMapRangeUnaryExp) ex;
			return "rng " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, rng.getExp())+ ")";
		}
		else if(ex instanceof ANotUnaryExp){
			ANotUnaryExp not = (ANotUnaryExp) ex;
			return "not " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, not.getExp())+ ")";
		}
		else if(ex instanceof APowerSetUnaryExp){
			APowerSetUnaryExp pwr = (APowerSetUnaryExp) ex;
			return "power " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, pwr.getExp())+ ")";
		}
		else if(ex instanceof AReverseUnaryExp){
			AReverseUnaryExp rev = (AReverseUnaryExp) ex;
			return "reverse " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, rev.getExp())+ ")";
		}
		else if(ex instanceof ATailUnaryExp){
			ATailUnaryExp tl = (ATailUnaryExp) ex;
			return "tl " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, tl.getExp())+ ")";
		}
		else if(ex instanceof AUnaryMinusUnaryExp){
			AUnaryMinusUnaryExp minus = (AUnaryMinusUnaryExp) ex;
			return "- " + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, minus.getExp())+ ")";
		}
		else if(ex instanceof AUnaryPlusUnaryExp){
			AUnaryPlusUnaryExp plus = (AUnaryPlusUnaryExp) ex;
			return "+" + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, plus.getExp())+ ")";
		}
		else{
			return "map expr not handled";
		}
	}

	private static String getOtherExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		if(ex instanceof ABooleanConstExp){
			ABooleanConstExp bool = (ABooleanConstExp) ex;
			return bool.getValue().toString();
		}
		else if(ex instanceof ANilExp){
			return "nil";
		}
		else if(ex instanceof AIfExp){
			AIfExp ifExp = (AIfExp) ex;
			return "if " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ifExp.getTest()) + " then " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ifExp.getThen()) + " else " +ThmExprUtil.getIsabelleExprStr(svars, bvars, ifExp.getElse());
		}
		else if(ex instanceof ACharLiteralExp){
			ACharLiteralExp ch = (ACharLiteralExp) ex;
			return "<<CHR ''" + ch.getValue().getValue() +"''>>";
		}
		else if(ex instanceof AElseIfExp){
			AElseIfExp ifExp = (AElseIfExp) ex;
			return "else if " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ifExp.getElseIf()) + " then " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ifExp.getThen());
		}
		else if(ex instanceof AExistsExp){
			AExistsExp exists = (AExistsExp) ex;
			LinkedList<PMultipleBind> binds = exists.getBindList();
			StringBuilder sb = new StringBuilder();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						sb.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							sb.append(", ");
						}
					}
					sb.append(" : ");
					sb.append("@" + tmb.getType().toString());
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						sb.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							sb.append(", ");
						}
					}
				//	sb.append(smb.getPlist().toString());
					sb.append(" in @set ");
					sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
				}
			}
			return "exists " + sb.toString() + " @ " + ThmExprUtil.getIsabelleExprStr(svars, boundvars, exists.getPredicate()); 
		}
		else if(ex instanceof AExists1Exp){
			AExists1Exp exists = (AExists1Exp) ex;

			StringBuilder sb = new StringBuilder();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			PBind b = exists.getBind();
			if (b instanceof ATypeBind)
			{
				ATypeBind tmb = (ATypeBind) b;
				sb.append(tmb.getPattern().toString());
				boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
				sb.append(" : ");
				sb.append("@" + tmb.getType().toString());
			}
			else if (b instanceof ASetBind)
			{
				ASetBind smb = (ASetBind) b;
				sb.append(smb.getPattern().toString());	
				boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
				sb.append(" in @set ");
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
			}
			
			return "exists1 " + sb.toString() + " @ " + ThmExprUtil.getIsabelleExprStr(svars, boundvars, exists.getPredicate()); 
		} 
		else if(ex instanceof AForAllExp){
			AForAllExp exists = (AForAllExp) ex;
			LinkedList<PMultipleBind> binds = exists.getBindList();
			StringBuilder sb = new StringBuilder();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						sb.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							sb.append(", ");
						}
					}
					sb.append(" : ");
					sb.append("@" + tmb.getType().toString());
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						sb.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							sb.append(", ");
						}
					}
				//	sb.append(smb.getPlist().toString());
					sb.append(" in @set ");
					sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
				}
			}
			return "forall " + sb.toString() + " @ " + ThmExprUtil.getIsabelleExprStr(svars, boundvars, exists.getPredicate()); 
		}
		else if(ex instanceof AIntLiteralExp){
			AIntLiteralExp i = (AIntLiteralExp) ex;
			return i.getValue().toString();
		}
		else if(ex instanceof AApplyExp){
			AApplyExp app = (AApplyExp) ex;
			StringBuilder sb = new StringBuilder();
			for (Iterator<PExp> itr = app.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars,e));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			//Need to remove final  ^'s due to the way we handle variable expressions. 
			//Can't currently determine if root expression is a function variable.
			StringBuilder rootsb = new StringBuilder(ThmExprUtil.getIsabelleExprStr(svars, bvars,app.getRoot()));
			int count = 0;
			while(count <2){
				int lastHat = rootsb.lastIndexOf("^");
				rootsb.deleteCharAt(lastHat);
				count++;
			}
			
			return rootsb.toString() + "(" + sb.toString() + ")";
		}		
		else if(ex instanceof ACasesExp){
//			ACasesExp c = (ACasesExp) ex;
//			PExp exp = c.getExpression();
//			LinkedList<ACaseAlternative> cases = c.getCases();
//			PExp others = c.getOthers();
			//TODO: Handle cases
			return "expr not yet handled";
		}
		else if(ex instanceof AFieldExp){
			AFieldExp fe = (AFieldExp) ex;
			
			return fe.getMemberName().toString() + "." + fe.getField().toString();
		}
		else if(ex instanceof AFieldNumberExp){
			AFieldNumberExp fe = (AFieldNumberExp) ex;
			
			return ThmExprUtil.getIsabelleExprStr(svars, bvars, fe.getTuple()) + ".#" + fe.getField().toString();
		}
		else if(ex instanceof AIotaExp){
			AIotaExp i = (AIotaExp) ex;
			StringBuilder sb = new StringBuilder();
			PBind b = i.getBind();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			if (b instanceof ATypeBind)
			{
				ATypeBind tmb = (ATypeBind) b;
				sb.append(tmb.getPattern().toString());
				boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
				sb.append(" : ");
				sb.append("@" + tmb.getType().toString());
			}
			else if (b instanceof ASetBind)
			{
				ASetBind smb = (ASetBind) b;
				sb.append(smb.getPattern().toString());
				boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
				sb.append(" in @set ");
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
			}
			
			return "iota " + sb.toString() + " @ " + ThmExprUtil.getIsabelleExprStr(svars, boundvars, i.getPredicate());
		}
		else if(ex instanceof ALambdaExp){
			ALambdaExp l = (ALambdaExp) ex;
			
			StringBuilder sb = new StringBuilder();
			LinkedList<ATypeBind> b = l.getBindList();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for (Iterator<ATypeBind> itr = b.listIterator(); itr.hasNext(); ) {
				ATypeBind p = itr.next();
					
				sb.append(p.getPattern().toString());
				boundvars.add(((AIdentifierPattern) p.getPattern()).getName());
				sb.append(" : ");
				sb.append("@" + p.getType().toString());
				//If there are remaining patterns, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			return "lambda " + sb.toString() + " @ " + ThmExprUtil.getIsabelleExprStr(svars, boundvars, l.getExpression());
		}
		else if(ex instanceof ALetDefExp){
			ALetDefExp l = (ALetDefExp) ex;
			StringBuilder sb = new StringBuilder();

			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			LinkedList<PDefinition> ldefs = l.getLocalDefs();
			for (PDefinition d : ldefs)
			{
				boundvars.add(d.getName());
				sb.append(d.getName().toString());
			}
			
			return "let " + sb + " in " +  ThmExprUtil.getIsabelleExprStr(svars, boundvars, l.getExpression());
		}
		else if(ex instanceof AMapletExp){
			AMapletExp m = (AMapletExp) ex;
			
			return ThmExprUtil.getIsabelleExprStr(svars, bvars, m.getLeft()) + " |-> " + ThmExprUtil.getIsabelleExprStr(svars, bvars, m.getRight());
		}
		else if(ex instanceof AMkBasicExp){
			AMkBasicExp mk = (AMkBasicExp) ex;
			
			return "mk_token(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, mk.getArg()) + ")";
		}
		else if(ex instanceof AMkTypeExp){
			AMkTypeExp mk = (AMkTypeExp) ex;
			StringBuilder sb = new StringBuilder();
			
			for (Iterator<PExp> itr = mk.getArgs().listIterator(); itr.hasNext(); ) {
				PExp p = itr.next();
					
				ThmExprUtil.getIsabelleExprStr(svars, bvars, p);
				//If there are remaining patterns, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			return "mk_" + mk.getRecordType().getName().toString() + "(" + sb.toString() + ")";
		}
		
		else if(ex instanceof ANotYetSpecifiedExp){
			return "is not yet specified";
		}
		else if(ex instanceof APostOpExp){
//			APostOpExp i = (APostOpExp) ex;
			//TODO: Handle postop exp
			return "expr not yet handled";
		}
		else if(ex instanceof APreExp){
//			APreExp i = (APreExp) ex;
			//TODO: Handle pre exp
			return "expr not yet handled";
		}
		else if(ex instanceof AQuoteLiteralExp){
			AQuoteLiteralExp q = (AQuoteLiteralExp) ex;
			return "<\'\'" + q.getValue().getValue() + "\'\'>";
		}
		else if(ex instanceof ARealLiteralExp){
			ARealLiteralExp r = (ARealLiteralExp) ex;
			return r.getValue().toString();
		}
		else if(ex instanceof AStringLiteralExp){
			AStringLiteralExp s = (AStringLiteralExp) ex;
			
			return "<<''" + s.getValue().toString() + "''>>";
		}
		else if(ex instanceof ASubseqExp){
			ASubseqExp sub = (ASubseqExp) ex;
			
			return ThmExprUtil.getIsabelleExprStr(svars, bvars, sub.getSeq()) + "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, sub.getFrom()) + ",...," + ThmExprUtil.getIsabelleExprStr(svars, bvars, sub.getTo()) + ")";
		}
		else if(ex instanceof ATupleExp){
			ATupleExp tup = (ATupleExp) ex;
			StringBuilder sb = new StringBuilder();
			
			for (Iterator<PExp> itr = tup.getArgs().listIterator(); itr.hasNext(); ) {
				PExp p = itr.next();
						
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, p));
				//If there are remaining exprs, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			return "mk_(" + sb.toString() + ")";
		}
		else if(ex instanceof AUndefinedExp){
			return "undefined";
		}
		else if(ex instanceof AVariableExp){
			AVariableExp v = (AVariableExp) ex;
			ILexNameToken varName = v.getName();
			
			for(ILexNameToken var : svars){
				if (varName.toString().equals(var.getName()))
				{
					return  "$" + varName.toString();
				}
			}
			for(ILexNameToken var : bvars){
				if (varName.toString().equals(var.getName()))
				{
					return  "^" + varName.toString() + "^";
				}
			}
			//assume is value?
			return "^" + varName.toString() + "^";
		}
		else if(ex instanceof AUnresolvedPathExp){
			//As this is a slightly iffy bit of syntax, we hack the Isabelle string a bit.
			//First need to check if the first element is a state variable. If it is, then use
			//$$, otherwise if a bound value, use ^^, otherwise, ??
			AUnresolvedPathExp e = (AUnresolvedPathExp) ex;
			StringBuilder sb = new StringBuilder();
			
			Iterator<ILexIdentifierToken> itr = e.getIdentifiers().listIterator();
			ILexIdentifierToken first = itr.next();
			boolean isState = false;
//			boolean isBound = false;
			for(ILexNameToken var : svars){
				
				if (first.toString().equals(var.getName()))
				{
					sb.append("$" + first.toString() + "$.");
					isState = true;
					break;
				}
			}
			if(!isState)
			{
				sb.append("^" + first.toString() + "^.");
			}
			
			while( itr.hasNext() ) {
				ILexIdentifierToken i = itr.next();
						
				sb.append(i.getName().toString());
				//If there are remaining exprs, add a ","
				if(itr.hasNext()){	
					sb.append(".");
				}
			}
			return sb.toString();
		}
		else if (ex instanceof ABracketedExp){
			ABracketedExp e = (ABracketedExp) ex;
			
			return "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, e.getExpression()) + ")";
		}
		return "expr not handled";
	}
	

	

//	else if(ex instanceof ADefExp){
//		ADefExp i = (ADefExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AFuncInstatiationExp){
//		AFuncInstatiationExp i = (AFuncInstatiationExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AHistoryExp){
//		AHistoryExp i = (AHistoryExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ANarrowExp){
//		ANarrowExp i = (ANarrowExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AStateInitExp){
//		AStateInitExp i = (AStateInitExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsExp){
//		AIsExp i = (AIsExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsOfBaseClassExp){
//		AIsOfBaseClassExp i = (AIsOfBaseClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsOfClassExp){
//		AIsOfClassExp i = (AIsOfClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ALetBeStExp){
//		ALetBeStExp i = (ALetBeStExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AMuExp){
//		AMuExp i = (AMuExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASameBaseClassExp){
//		ASameBaseClassExp i = (ASameBaseClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASameClassExp){
//		ASameClassExp i = (ASameClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASelfExp){
//		ASelfExp i = (ASelfExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASubclassResponsibilityExp){
//		ASubclassResponsibilityExp i = (ASubclassResponsibilityExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AThreadIdExp){
//		AThreadIdExp i = (AThreadIdExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ATimeExp){
//		ATimeExp i = (ATimeExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ANewExp){
//		ANewExp i = (ANewExp) ex;
//		return "expr not yet handled";
//	}

	private static String getMapExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		if(ex instanceof AMapCompMapExp){
			AMapCompMapExp comp = (AMapCompMapExp) ex;
			StringBuilder bindstr = new StringBuilder();
			
			LinkedList<PMultipleBind> binds = comp.getBindings();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						bindstr.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							bindstr.append(", ");
						}
					}
					bindstr.append(" : ");
					bindstr.append("@" + tmb.getType().toString());
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						bindstr.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							bindstr.append(", ");
						}
					}
				//	sb.append(smb.getPlist().toString());
					bindstr.append(" in @set ");
					bindstr.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
				}
			}
			String firstString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getFirst()); 
			String predString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getPredicate()); 
			return "{" + firstString + " | " + bindstr + " @ " + predString + "}";
		}
		else if(ex instanceof AMapEnumMapExp){
			AMapEnumMapExp en = (AMapEnumMapExp) ex;
			StringBuilder sb = new StringBuilder();
			LinkedList<AMapletExp> mem = en.getMembers();

			for (Iterator<AMapletExp> itr = mem.listIterator(); itr.hasNext(); ) {
				PExp m = itr.next();
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, m));
				//If there are remaining types, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			return "{" + sb.toString() +"}";
			
		}
		else{
			return "map expr not handled";
		}
	}
	
	private static String getSeqExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		if(ex instanceof ASeqCompSeqExp){
			ASeqCompSeqExp comp = (ASeqCompSeqExp) ex;
			StringBuilder bindstr = new StringBuilder();
			
			ASetBind bind = comp.getSetBind();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			bindstr.append(bind.getPattern().toString());
			boundvars.add(((AIdentifierPattern) bind.getPattern()).getName());
			bindstr.append(" in @set ");
			bindstr.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, bind.getSet()));
			
			String firstString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getFirst()); 
			String predString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getPredicate()); 
			return "{" + firstString + " | " + bindstr + " @ " + predString + "}";
		}
		else if(ex instanceof ASeqEnumSeqExp){
			ASeqEnumSeqExp en = (ASeqEnumSeqExp) ex;
			
			StringBuilder sb = new StringBuilder();
			LinkedList<PExp> mem = en.getMembers();

			for (Iterator<PExp> itr = mem.listIterator(); itr.hasNext(); ) {
				PExp m = itr.next();
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, m));
				//If there are remaining types, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			return "[" + sb.toString() +"]";
		}
		else{
			return "seq expr not handled";
		}
	}
	
	private static String getSetExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		if(ex instanceof ASetCompSetExp){
			ASetCompSetExp comp = (ASetCompSetExp) ex;
			StringBuilder bindstr = new StringBuilder();
			
			LinkedList<PMultipleBind> binds = comp.getBindings();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						bindstr.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							bindstr.append(", ");
						}
					}
					bindstr.append(" : ");
					bindstr.append("@" + tmb.getType().toString());
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						bindstr.append(p.getName());
						boundvars.add(p.getName());
						//If there are remaining patterns, add a ","
						if(itr.hasNext()){	
							bindstr.append(", ");
						}
					}
					bindstr.append(" in @set ");
					bindstr.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, smb.getSet()));
				}
			}
			String firstString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getFirst()); 
			String predString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getPredicate()); 
			return "{" + firstString + " | " + bindstr + " @ " + predString + "}";
		}
		else if(ex instanceof ASetEnumSetExp){
			ASetEnumSetExp en = (ASetEnumSetExp) ex;
			StringBuilder sb = new StringBuilder();
			LinkedList<PExp> mem = en.getMembers();

			for (Iterator<PExp> itr = mem.listIterator(); itr.hasNext(); ) {
				PExp m = itr.next();
				sb.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, m));
				//If there are remaining types, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			
			return "{" + sb.toString() +"}";
		}
		else if(ex instanceof ASetRangeSetExp){
			ASetRangeSetExp en = (ASetRangeSetExp) ex;
			
			String first = ThmExprUtil.getIsabelleExprStr(svars, bvars, en.getFirst());
			String last = ThmExprUtil.getIsabelleExprStr(svars, bvars, en.getLast());
			
			return "{" + first + ", ..., " + last + "}";
		}
		else
		{
			return "Set expr not handled";
		}
	}


	private static String getNumericBinaryExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {

		if(ex instanceof ADivideNumericBinaryExp){
			ADivideNumericBinaryExp div = (ADivideNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, div.getLeft()) + " \'/ " + ThmExprUtil.getIsabelleExprStr(svars, bvars, div.getRight())+ ")";
		}
		else if(ex instanceof ADivNumericBinaryExp){
			ADivNumericBinaryExp div = (ADivNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, div.getLeft()) + " div " + ThmExprUtil.getIsabelleExprStr(svars, bvars, div.getRight())+ ")";
		}
		else if(ex instanceof AGreaterEqualNumericBinaryExp){
			AGreaterEqualNumericBinaryExp ge = (AGreaterEqualNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, ge.getLeft()) + " >= " + ThmExprUtil.getIsabelleExprStr(svars, bvars, ge.getRight())+ ")";

		}
		else if(ex instanceof AGreaterNumericBinaryExp){
			AGreaterNumericBinaryExp gt = (AGreaterNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, gt.getLeft()) + " > " + ThmExprUtil.getIsabelleExprStr(svars, bvars, gt.getRight())+ ")";

		}
		else if(ex instanceof ALessEqualNumericBinaryExp){
			ALessEqualNumericBinaryExp le = (ALessEqualNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, le.getLeft()) + " <= " + ThmExprUtil.getIsabelleExprStr(svars, bvars, le.getRight())+ ")";

		}
		else if(ex instanceof ALessNumericBinaryExp){
			ALessNumericBinaryExp lt = (ALessNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, lt.getLeft()) + " < " + ThmExprUtil.getIsabelleExprStr(svars, bvars, lt.getRight())+ ")";

		}
		else if(ex instanceof AModNumericBinaryExp){
			AModNumericBinaryExp mod = (AModNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, mod.getLeft()) + " mod " + ThmExprUtil.getIsabelleExprStr(svars, bvars, mod.getRight())+ ")";

		}
		else if(ex instanceof APlusNumericBinaryExp){
			APlusNumericBinaryExp pl = (APlusNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, pl.getLeft()) + " + " + ThmExprUtil.getIsabelleExprStr(svars, bvars, pl.getRight())+ ")";

		}
		else if(ex instanceof ASubtractNumericBinaryExp){
			ASubtractNumericBinaryExp sub = (ASubtractNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, sub.getLeft()) + " - " + ThmExprUtil.getIsabelleExprStr(svars, bvars, sub.getRight())+ ")";

		}
		else if(ex instanceof ATimesNumericBinaryExp){
			ATimesNumericBinaryExp times = (ATimesNumericBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, times.getLeft()) + " * " + ThmExprUtil.getIsabelleExprStr(svars, bvars, times.getRight())+ ")";

		}
		else //ARemNumericBinaryExp?
		{
			return  "expr not handled";
		}
	}


	private static String getBooleanBinaryExp(LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars, PExp ex) {
		
		if(ex instanceof AAndBooleanBinaryExp){
			AAndBooleanBinaryExp and = (AAndBooleanBinaryExp) ex;
			return "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, and.getLeft()) + " and " + ThmExprUtil.getIsabelleExprStr(svars, bvars, and.getRight())+ ")";
		}
		else if(ex instanceof AEquivalentBooleanBinaryExp){
			AEquivalentBooleanBinaryExp eq = (AEquivalentBooleanBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, eq.getLeft()) + " <=> " + ThmExprUtil.getIsabelleExprStr(svars, bvars, eq.getRight())+ ")";
		
		}
		else if(ex instanceof AImpliesBooleanBinaryExp){
			AImpliesBooleanBinaryExp imp = (AImpliesBooleanBinaryExp) ex;
			return "(" +ThmExprUtil.getIsabelleExprStr(svars, bvars, imp.getLeft()) + " => " + ThmExprUtil.getIsabelleExprStr(svars, bvars, imp.getRight())+ ")";
		}
		else if(ex instanceof AOrBooleanBinaryExp){
			AOrBooleanBinaryExp or = (AOrBooleanBinaryExp) ex;
			return "(" + ThmExprUtil.getIsabelleExprStr(svars, bvars, or.getLeft()) + " or " +ThmExprUtil.getIsabelleExprStr(svars, bvars, or.getRight())+ ")";
			
		}
		else
		{
			return "expr not handled";
		}
	}
	
	
	public static LinkedList<ILexNameToken> getIsabelleExprDeps(LinkedList<ILexNameToken> bvars, PExp ex)
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		// is a bool binary expression
		if (ex instanceof SBooleanBinaryExp){
			nodeDeps = ThmExprUtil.getBooleanBinaryDeps(bvars, ex);
		}
		//is a numeric expression
		else if (ex instanceof SNumericBinaryExp){
			nodeDeps = ThmExprUtil.getNumericBinaryDeps(bvars, ex);
		}
		else if (ex instanceof SMapExp){
			nodeDeps = ThmExprUtil.getMapExpDeps(bvars, ex);
		}
		else if (ex instanceof SSeqExp){
			nodeDeps = ThmExprUtil.getSeqExpDeps(bvars, ex);
		}
		else if (ex instanceof SSetExp){
			nodeDeps = ThmExprUtil.getSetExpDeps(bvars, ex);
		}
		else if (ex instanceof SUnaryExpBase){
			nodeDeps = ThmExprUtil.getUnaryExpDeps(bvars, ex);
		}
		else if (ex instanceof SBinaryExpBase){
			nodeDeps = ThmExprUtil.getBinaryExpDeps(bvars, ex);
		}
		else{ //if (ex instanceof PExpBase){
			nodeDeps = ThmExprUtil.getOtherExpDeps(bvars, ex);
		}
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getOtherExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		if(ex instanceof ABooleanConstExp){
		}
		else if(ex instanceof ANilExp){
		}
		else if(ex instanceof AIfExp){
			AIfExp ifExp = (AIfExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getTest()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getThen()));	
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getElse()));	
		}
		else if(ex instanceof ACharLiteralExp){
		}
		else if(ex instanceof AElseIfExp){
			AElseIfExp ifExp = (AElseIfExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getElseIf()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getThen()));	
		}
		else if(ex instanceof AExistsExp){
				
			AExistsExp exists = (AExistsExp) ex;
			LinkedList<PMultipleBind> binds = exists.getBindList();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
				}
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, exists.getPredicate()));
			
		}
		else if(ex instanceof AExists1Exp){
			AExists1Exp exists = (AExists1Exp) ex;

			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			PBind b = exists.getBind();
			
			if (b instanceof ATypeBind)
			{
				ATypeBind tmb = (ATypeBind) b;
				boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetBind)
			{
				ASetBind smb = (ASetBind) b;
				boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, exists.getPredicate()));
		} 
		else if(ex instanceof AForAllExp){
			AForAllExp forall = (AForAllExp) ex;
			LinkedList<PMultipleBind> binds = forall.getBindList();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
				}
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, forall.getPredicate()));
		}
		else if(ex instanceof AIntLiteralExp){
		}
		else if(ex instanceof AApplyExp){
			AApplyExp app = (AApplyExp) ex;
			for (PExp a : app.getArgs())
			{
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a));
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, app.getRoot()));
		}		
		else if(ex instanceof ACasesExp){
//			ACasesExp c = (ACasesExp) ex;
//			PExp exp = c.getExpression();
//			LinkedList<ACaseAlternative> cases = c.getCases();
//			PExp others = c.getOthers();
			//TODO: Handle cases
		}
		else if(ex instanceof AFieldExp){
			AFieldExp fe = (AFieldExp) ex;
			nodeDeps.add(fe.getMemberName());
		}
		else if(ex instanceof AFieldNumberExp){}
		else if(ex instanceof AIotaExp){
			AIotaExp i = (AIotaExp) ex;

			PBind b = i.getBind();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			if (b instanceof ATypeBind)
			{
				ATypeBind tmb = (ATypeBind) b;
				boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetBind)
			{
				ASetBind smb = (ASetBind) b;
				boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, i.getPredicate()));
			
		}
		else if(ex instanceof ALambdaExp){
			ALambdaExp l = (ALambdaExp) ex;
		
			LinkedList<ATypeBind> b = l.getBindList();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for (Iterator<ATypeBind> itr = b.listIterator(); itr.hasNext(); ) {
				ATypeBind p = itr.next();
					
				boundvars.add(((AIdentifierPattern) p.getPattern()).getName());
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(p.getType()));

			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, l.getExpression()));
			
		}
		else if(ex instanceof ALetDefExp){
			ALetDefExp l = (ALetDefExp) ex;
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			LinkedList<PDefinition> ldefs = l.getLocalDefs();
			for (PDefinition d : ldefs)
			{
				boundvars.add(((AIdentifierPattern) d).getName());
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(d.getType()));
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, l.getExpression()));
			
		}
		else if(ex instanceof AMapletExp){
			AMapletExp m = (AMapletExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m.getRight()));		
			
		}
		else if(ex instanceof AMkBasicExp){
			AMkBasicExp mk = (AMkBasicExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mk.getArg()));	
		}
		else if(ex instanceof AMkTypeExp){
			AMkTypeExp mk = (AMkTypeExp) ex;
			mk.getRecordType();
			mk.getArgs();
			for (PExp e : mk.getArgs())
			{
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e));	
			}
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(mk.getRecordType()));
		}
		
		else if(ex instanceof ANotYetSpecifiedExp){}
		else if(ex instanceof APostOpExp){
//			APostOpExp i = (APostOpExp) ex;
			//TODO: Handle postop exp
		}
		else if(ex instanceof APreExp){
//			APreExp i = (APreExp) ex;
			//TODO: Handle pre exp
		}
		else if(ex instanceof AQuoteLiteralExp){}
		else if(ex instanceof ARealLiteralExp){}
		else if(ex instanceof AStringLiteralExp){}
		else if(ex instanceof ASubseqExp){
			ASubseqExp sub = (ASubseqExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getSeq()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getFrom()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getTo()));
		}
		else if(ex instanceof ATupleExp){
			ATupleExp tup = (ATupleExp) ex;
			
			for (PExp p: tup.getArgs()) {
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, p));
			}
		}
		else if(ex instanceof AUndefinedExp){}
		else if(ex instanceof AVariableExp){
			AVariableExp v = (AVariableExp) ex;
			
			boolean boundV = false;
			ILexNameToken varName = v.getName();

			for(ILexNameToken var : bvars){
				if (!boundV && varName.toString().equals(var.getName()))
				{
					boundV = true;
				}
			}
			if(!boundV)
			{
				nodeDeps.add(varName);
			}
			
		}
		else if(ex instanceof AUnresolvedPathExp){
			AUnresolvedPathExp e = (AUnresolvedPathExp) ex;

			//Get the first part of the path (this is assuming it is a record.field expression...
			LinkedList<ILexIdentifierToken> ids = e.getIdentifiers();
			ILexIdentifierToken fId = ids.getFirst();
					
			//check that the record part isn't a bound variable
			boolean boundV = false;
			LexNameToken lnt = new LexNameToken("", fId.getName().toString(), fId.getLocation());

			for(ILexNameToken var : bvars){
				if (!boundV && lnt.toString().equals(var.getName()))
				{
					boundV = true;
				}
			}
			if(!boundV)
			{
				nodeDeps.add(lnt);
			}
		}
		else if (ex instanceof ABracketedExp){
			ABracketedExp e = (ABracketedExp) ex;

			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getExpression()));
		}
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getBinaryExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if(ex instanceof ACompBinaryExp){
			ACompBinaryExp comp = (ACompBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, comp.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, comp.getRight()));		
		}
		else if(ex instanceof ADomainResByBinaryExp){
			ADomainResByBinaryExp dres = (ADomainResByBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getRight()));		
		}
		else if(ex instanceof ADomainResToBinaryExp){
			ADomainResToBinaryExp dres = (ADomainResToBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getRight()));		
		}
		else if(ex instanceof AEqualsBinaryExp){
			AEqualsBinaryExp eq = (AEqualsBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, eq.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, eq.getRight()));		
		}
		else if(ex instanceof AInSetBinaryExp){
			AInSetBinaryExp ins = (AInSetBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ins.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ins.getRight()));		
		}
		else if(ex instanceof AMapUnionBinaryExp){
			AMapUnionBinaryExp mun = (AMapUnionBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mun.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mun.getRight()));		
		}
		else if(ex instanceof ANotEqualBinaryExp){
			ANotEqualBinaryExp neq = (ANotEqualBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, neq.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, neq.getRight()));		
		}
		else if(ex instanceof ANotInSetBinaryExp){
			ANotInSetBinaryExp nins = (ANotInSetBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, nins.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, nins.getRight()));		
		}
		else if(ex instanceof APlusPlusBinaryExp){
			APlusPlusBinaryExp plpl = (APlusPlusBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plpl.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plpl.getRight()));		
		}
		else if(ex instanceof AProperSubsetBinaryExp){
			AProperSubsetBinaryExp psub = (AProperSubsetBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, psub.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, psub.getRight()));		
		}
		else if(ex instanceof ARangeResByBinaryExp){
			ARangeResByBinaryExp rres = (ARangeResByBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getRight()));		
		}
		else if(ex instanceof ARangeResToBinaryExp){
			ARangeResToBinaryExp rres = (ARangeResToBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getRight()));		
		}
		else if(ex instanceof ASeqConcatBinaryExp){
			ASeqConcatBinaryExp scon = (ASeqConcatBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, scon.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, scon.getRight()));		
		}
		else if(ex instanceof ASetDifferenceBinaryExp){
			ASetDifferenceBinaryExp sdiff = (ASetDifferenceBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sdiff.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sdiff.getRight()));		
		}
		else if(ex instanceof ASetIntersectBinaryExp){
			ASetIntersectBinaryExp inter = (ASetIntersectBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inter.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inter.getRight()));		
		}
		else if(ex instanceof ASetUnionBinaryExp){
			ASetUnionBinaryExp union = (ASetUnionBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, union.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, union.getRight()));		
		}
		else if(ex instanceof AStarStarBinaryExp){
			AStarStarBinaryExp star = (AStarStarBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, star.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, star.getRight()));		
		}
		else if(ex instanceof ASubsetBinaryExp){
			ASubsetBinaryExp subs = (ASubsetBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, subs.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, subs.getRight()));		
		}
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getUnaryExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		if(ex instanceof AAbsoluteUnaryExp){
			AAbsoluteUnaryExp abs = (AAbsoluteUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, abs.getExp()));
		}
		else if(ex instanceof ACardinalityUnaryExp){
			ACardinalityUnaryExp card = (ACardinalityUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, card.getExp()));
		}
		else if(ex instanceof ADistConcatUnaryExp){
			ADistConcatUnaryExp conc = (ADistConcatUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, conc.getExp()));
		}
		else if(ex instanceof ADistIntersectUnaryExp){
			ADistIntersectUnaryExp dinter = (ADistIntersectUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dinter.getExp()));
		}
		else if(ex instanceof ADistMergeUnaryExp){
			ADistMergeUnaryExp dmerge = (ADistMergeUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dmerge.getExp()));
		}
		else if(ex instanceof ADistUnionUnaryExp){
			ADistUnionUnaryExp dunion = (ADistUnionUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dunion.getExp()));
		}
		else if(ex instanceof AElementsUnaryExp){
			AElementsUnaryExp e = (AElementsUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getExp()));
		}
		else if(ex instanceof AFloorUnaryExp){
			AFloorUnaryExp fl = (AFloorUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, fl.getExp()));
		}
		else if(ex instanceof AHeadUnaryExp){
			AHeadUnaryExp hd = (AHeadUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, hd.getExp()));
		}
		else if(ex instanceof AIndicesUnaryExp){
			AIndicesUnaryExp inds = (AIndicesUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inds.getExp()));
		}
		else if(ex instanceof ALenUnaryExp){
			ALenUnaryExp len = (ALenUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, len.getExp()));
		}
		else if(ex instanceof AMapDomainUnaryExp){
			AMapDomainUnaryExp dom = (AMapDomainUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dom.getExp()));
		}
		else if(ex instanceof AMapInverseUnaryExp){
			AMapInverseUnaryExp inverse = (AMapInverseUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inverse.getExp()));
		}
		else if(ex instanceof AMapRangeUnaryExp){
			AMapRangeUnaryExp rng = (AMapRangeUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rng.getExp()));
		}
		else if(ex instanceof ANotUnaryExp){
			ANotUnaryExp not = (ANotUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, not.getExp()));
		}
		else if(ex instanceof APowerSetUnaryExp){
			APowerSetUnaryExp pwr = (APowerSetUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pwr.getExp()));
		}
		else if(ex instanceof AReverseUnaryExp){
			AReverseUnaryExp rev = (AReverseUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rev.getExp()));
		}
		else if(ex instanceof ATailUnaryExp){
			ATailUnaryExp tl = (ATailUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, tl.getExp()));
		}
		else if(ex instanceof AUnaryMinusUnaryExp){
			AUnaryMinusUnaryExp minus = (AUnaryMinusUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, minus.getExp()));
		}
		else if(ex instanceof AUnaryPlusUnaryExp){
			AUnaryPlusUnaryExp plus = (AUnaryPlusUnaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plus.getExp()));
		}
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getSetExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if(ex instanceof ASetCompSetExp){
			ASetCompSetExp comp = (ASetCompSetExp) ex;

			LinkedList<PMultipleBind> binds = comp.getBindings();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
				}
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
		}
		else if(ex instanceof ASetEnumSetExp){
			ASetEnumSetExp en = (ASetEnumSetExp) ex;

			for (PExp m : en.getMembers()){
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
			}
		}
		else if(ex instanceof ASetRangeSetExp){
			ASetRangeSetExp en = (ASetRangeSetExp) ex;

			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, en.getFirst()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, en.getLast()));
		}
		
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getSeqExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		if(ex instanceof ASeqCompSeqExp){
			ASeqCompSeqExp comp = (ASeqCompSeqExp) ex;
			ASetBind binds = comp.getSetBind();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);			
			boundvars.add(((AIdentifierPattern) binds.getPattern()).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, binds.getSet()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
		}
		else if(ex instanceof ASeqEnumSeqExp){
			ASeqEnumSeqExp en = (ASeqEnumSeqExp) ex;
			
			for (PExp m : en.getMembers()){
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
			}
		}

		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getMapExpDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if(ex instanceof AMapCompMapExp){
			AMapCompMapExp comp = (AMapCompMapExp) ex;
			LinkedList<PMultipleBind> binds = comp.getBindings();
			LinkedList<ILexNameToken> boundvars = new LinkedList<ILexNameToken>();
			boundvars.addAll(bvars);
			
			for(PMultipleBind b: binds)
			{
				if (b instanceof ATypeMultipleBind)
				{
					ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
					for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
				}
				else if (b instanceof ASetMultipleBind)
				{
					ASetMultipleBind smb = (ASetMultipleBind) b;
					for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
						AIdentifierPattern p = (AIdentifierPattern) itr.next();
						
						boundvars.add(p.getName());
					}
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
				}
			}
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
		}
		else if(ex instanceof AMapEnumMapExp){
			AMapEnumMapExp en = (AMapEnumMapExp) ex;
			for (PExp m : en.getMembers()){
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
			}
		}
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getNumericBinaryDeps(
			LinkedList<ILexNameToken> bvars, PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if(ex instanceof ADivideNumericBinaryExp){
			ADivideNumericBinaryExp e = (ADivideNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof ADivNumericBinaryExp){
			ADivNumericBinaryExp e = (ADivNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof AGreaterEqualNumericBinaryExp){
			AGreaterEqualNumericBinaryExp e = (AGreaterEqualNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof AGreaterNumericBinaryExp){
			AGreaterNumericBinaryExp e = (AGreaterNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof ALessEqualNumericBinaryExp){
			ALessEqualNumericBinaryExp e = (ALessEqualNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof ALessNumericBinaryExp){
			ALessNumericBinaryExp e = (ALessNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof AModNumericBinaryExp){
			AModNumericBinaryExp e = (AModNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof APlusNumericBinaryExp){
			APlusNumericBinaryExp pl = (APlusNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pl.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pl.getRight()));		
		}
		else if(ex instanceof ASubtractNumericBinaryExp){
			ASubtractNumericBinaryExp sub = (ASubtractNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getRight()));		
		}
		else if(ex instanceof ATimesNumericBinaryExp){
			ATimesNumericBinaryExp times = (ATimesNumericBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, times.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, times.getRight()));		
		}

		return nodeDeps;
	}
	


	private static LinkedList<ILexNameToken>  getBooleanBinaryDeps(LinkedList<ILexNameToken> bvars,
			PExp ex) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		if(ex instanceof AAndBooleanBinaryExp){
			AAndBooleanBinaryExp e = (AAndBooleanBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof AEquivalentBooleanBinaryExp){
			AEquivalentBooleanBinaryExp e = (AEquivalentBooleanBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));	
		}
		else if(ex instanceof AImpliesBooleanBinaryExp){
			AImpliesBooleanBinaryExp e = (AImpliesBooleanBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		else if(ex instanceof AOrBooleanBinaryExp){
			AOrBooleanBinaryExp e = (AOrBooleanBinaryExp) ex;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
		}
		return nodeDeps;
	}
	
	
	//Method to return VARSET expression. This is new to COMPASS AST
	public static LinkedList<ILexNameToken> getIsabelleVarsetExprDeps(PVarsetExpression vExpr) {
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
	
		if (vExpr instanceof AFatEnumVarsetExpression)
		{
			AFatEnumVarsetExpression e = (AFatEnumVarsetExpression) vExpr;
			
			for (Iterator<ILexIdentifierToken> itr = e.getIdentifiers().listIterator(); itr.hasNext(); ) {
				ILexIdentifierToken i = itr.next();
				nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
				
				
			}
		}
		else if (vExpr instanceof AFatCompVarsetExpression)
		{
			//AFatCompVarsetExpression e = (AFatCompVarsetExpression) vExpr;

			// TODO COMPLETE
		}
		else if (vExpr instanceof AIdentifierVarsetExpression)
		{
			//AIdentifierVarsetExpression e = (AIdentifierVarsetExpression) vExpr;

			// TODO COMPLETE
		}
		else if (vExpr instanceof AEnumVarsetExpression)
		{
			AEnumVarsetExpression e = (AEnumVarsetExpression) vExpr;
			
			for (Iterator<ILexIdentifierToken> itr = e.getIdentifiers().listIterator(); itr.hasNext(); ) {
				ILexIdentifierToken i = itr.next();
				nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
			}
		}
		else if (vExpr instanceof AInterVOpVarsetExpression)
		{
			AInterVOpVarsetExpression e = (AInterVOpVarsetExpression) vExpr;

			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
		}
		else if (vExpr instanceof ASubVOpVarsetExpression)
		{
			ASubVOpVarsetExpression e = (ASubVOpVarsetExpression) vExpr;

			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
		}
		else if (vExpr instanceof AUnionVOpVarsetExpression)
		{
			AUnionVOpVarsetExpression e = (AUnionVOpVarsetExpression) vExpr;

			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
		}

		return nodeDeps;
	}
	
	//Method to return VARSET expression. This is new to COMPASS AST
		public static String getIsabelleVarsetExpr(PVarsetExpression vExpr) {
			if (vExpr instanceof AFatEnumVarsetExpression)
			{
				AFatEnumVarsetExpression e = (AFatEnumVarsetExpression) vExpr;
				StringBuilder sb = new StringBuilder();

				for (Iterator<ILexIdentifierToken> itr = e.getIdentifiers().listIterator(); itr.hasNext(); ) {
					ILexIdentifierToken i = itr.next();
					sb.append(i.getName().toString());
					sb.append(ThmExprUtil.isaDown );
					//If there are remaining channels, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
				return "{" + sb.toString() +"}";
			}
			else if (vExpr instanceof AFatCompVarsetExpression)
			{
				//AFatCompVarsetExpression e = (AFatCompVarsetExpression) vExpr;

				// TODO COMPLETE
				return "varset comp not handled";
			}
			else if (vExpr instanceof AIdentifierVarsetExpression)
			{
				//AIdentifierVarsetExpression e = (AIdentifierVarsetExpression) vExpr;

				// TODO COMPLETE
				return "varset id not handled";
			}
			else if (vExpr instanceof AEnumVarsetExpression)
			{
				AEnumVarsetExpression e = (AEnumVarsetExpression) vExpr;
				StringBuilder sb = new StringBuilder();

				for (Iterator<ILexIdentifierToken> itr = e.getIdentifiers().listIterator(); itr.hasNext(); ) {
					ILexIdentifierToken i = itr.next();
					sb.append(i.getName().toString());
					sb.append(ThmExprUtil.isaDown );
					//If there are remaining channels, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
				return "{" + sb.toString() +"}";
			}
			else if (vExpr instanceof AInterVOpVarsetExpression)
			{
				AInterVOpVarsetExpression e = (AInterVOpVarsetExpression) vExpr;
				
				String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
				String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

				return left + " inter " + right;
			}
			else if (vExpr instanceof ASubVOpVarsetExpression)
			{
				ASubVOpVarsetExpression e = (ASubVOpVarsetExpression) vExpr;

				String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
				String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

				return left + " setminus " + right;
			}
			else if (vExpr instanceof AUnionVOpVarsetExpression)
			{
				AUnionVOpVarsetExpression e = (AUnionVOpVarsetExpression) vExpr;

				String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
				String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

				return left + " union " + right;
			}
			else
			{
				return "varset expr not handled";
			}
		}
 	
}

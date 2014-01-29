package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallObjectStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.ASkipStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

@SuppressWarnings("serial")
public class ThmStmStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmStmStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}

	public String caseAActionStm(AActionStm a, ThmVarsContext vars)
			throws AnalysisException {

		return a.getAction().apply(thmStringVisitor, vars);
	}
	
	public String caseASkipStm(ASkipStm a, ThmVarsContext vars) throws AnalysisException{
		
		return "II";		
	}
	
	
	public String caseACallStm(ACallStm a, ThmVarsContext vars) throws AnalysisException{
		StringBuilder args = new StringBuilder();
		
		for (Iterator<PExp> itr = a.getArgs().listIterator(); itr.hasNext(); ) {
			PExp e = itr.next();
			
			args.append(e.apply(thmStringVisitor, vars));
			//If there are remaining expressions, add a ","
			if(itr.hasNext()){	
				args.append(", ");
			}
		}
		
		ILexNameToken name = a.getName();
		return "call " + name.getName()+ "[" + args.toString() + "]";		
	}
	
	public String caseABlockSimpleBlockStm(ABlockSimpleBlockStm a, ThmVarsContext vars) throws AnalysisException{
		LinkedList<String> varsStr = new LinkedList<String>();
		StringBuilder assignStr = new StringBuilder();
		StringBuilder blockSb = new StringBuilder();
		
		for(Iterator<AAssignmentDefinition> itr = a.getAssignmentDefs().listIterator(); itr.hasNext();) {
			AAssignmentDefinition aDef = itr.next();

			varsStr.add(aDef.getName().getName().toString());
			if(aDef.getExpression() != null)
			{
				assignStr.append(aDef.getName().getName().toString() + ":=" + aDef.getExpression().apply(thmStringVisitor, vars));
				//If there are remaining assignments, add a ";"
				if(itr.hasNext()){	
					assignStr.append("; ");
				}
			}
		}
		
		for(Iterator<PStm> itr = a.getStatements().listIterator(); itr.hasNext();) {
			PStm s = itr.next();

			blockSb.append(s.apply(thmStringVisitor, vars));	
			//If there are remaining statements, add a ","
			if(itr.hasNext()){	
				blockSb.append("; ");
			}
		}
		String blockStr = blockSb.toString();
		
		

		blockStr = assignStr.toString() + blockStr;
		for(String as : varsStr)
		{
			blockStr = "var " + as + "; " + blockStr + "; end " + as; 
		}
		
		return "(" + blockStr + ")"; 
	}

		
	public String caseALetStm(ALetStm a, ThmVarsContext vars) throws AnalysisException{

		LinkedList<String> varsStr = new LinkedList<String>();
		StringBuilder assignStr = new StringBuilder();

		String blockStr = a.getStatement().apply(thmStringVisitor, vars);
		LinkedList<PDefinition> defs = a.getLocalDefs();
		if(! defs.isEmpty()) 
		{
			for(Iterator<PDefinition> itr = a.getLocalDefs().listIterator(); itr.hasNext();) {
				PDefinition pdef = itr.next();

				AAssignmentDefinition aDef = (AAssignmentDefinition) pdef;
				varsStr.add(aDef.getName().getName().toString());
				if(aDef.getExpression() != null)
				{
					assignStr.append(aDef.getName().getName().toString() + ":=" + aDef.getExpression().apply(thmStringVisitor, vars));
					//If there are remaining assignments, add a ";"
					if(itr.hasNext()){	
						assignStr.append("; ");
					}
				}
			}

			blockStr = assignStr.toString() + blockStr;
			for(String as : varsStr)
			{
				blockStr = "var " + as + "; " + blockStr + "; end " + as; 
			}
		}
		
		return "(" + blockStr + ")"; 
	}
	
	public String caseAIfStm(AIfStm a, ThmVarsContext vars) throws AnalysisException{
		String ifStr = a.getIfExp().apply(thmStringVisitor, vars);
		String thenStr = a.getThenStm().apply(thmStringVisitor, vars);	
		String elseStr = "Skip";
		if(a.getElseStm() != null)
		{
			elseStr = a.getElseStm().apply(thmStringVisitor, vars);	
		}
		String elseIfStr = "";
		String closingIf = "";
		LinkedList<AElseIfStm> elseIf = a.getElseIf();
		for(AElseIfStm e : elseIf)
		{
			elseIfStr = elseIfStr + "("+ e.apply(thmStringVisitor, vars);	
			closingIf = ")";
		}
		
		return thenStr + ThmProcessUtil.ifLeft + ifStr + ThmProcessUtil.ifRight + elseIfStr + elseStr + closingIf;
	}
	
	public String caseAElseIfStm(AElseIfStm a, ThmVarsContext vars) throws AnalysisException{
		String elseIfStr = a.getElseIf().apply(thmStringVisitor, vars);
		String thenStr = a.getThenStm().apply(thmStringVisitor, vars);		

		return thenStr + ThmProcessUtil.ifLeft + elseIfStr+ ThmProcessUtil.ifRight;
	}
	
	public String caseAAssignmentStm(AAssignmentStm a, ThmVarsContext vars) throws AnalysisException{
		PStateDesignator designator = a.getTarget();
		String aExp = designator.toString();
	//	String aExp = a.getStateDesignator().apply(thmStringVisitor, vars);
		String exp = a.getExp().apply(thmStringVisitor, vars);
		
		//Isabelle doesn't want state variables to have a $ on the lhs of an assignment.
		//TODO: This may be too harsh
		aExp = aExp.replace("$", "");
		
		return aExp + ThmProcessUtil.assign + exp;
	}
	
	
//	//DON'T THINK IS HANDLED IN ISABELLE YET
//	public String caseACallObjectStm(ACallObjectStm a, ThmVarsContext vars) throws AnalysisException{
//		
//		String aExp = a.getDesignator().apply(thmStringVisitor, vars);
//		// String callExp = a.geta.getCall().apply(thmStringVisitor, vars);
////           
//		//Isabelle doesn't want state variables to have a $ on the lhs of an assignment.
//		//TODO: This may be too harsh
//		aExp = aExp.replace("$", "");
//		return aExp + ThmProcessUtil.assign// + callExp;
//	}
	
	public String caseAWhileStm(AWhileStm a, ThmVarsContext vars) throws AnalysisException{
		String cond = a.getExp().apply(thmStringVisitor, vars);
		String actStr = a.getStatement().apply(thmStringVisitor, vars);
//	        | {declare}  [assignmentDefs]:definition*
		
		return ThmProcessUtil.isaWhile  + ThmProcessUtil.opExpLeft + cond + ThmProcessUtil.isaDo  + ThmProcessUtil.opExpRight + actStr + ThmProcessUtil.isaOd;
	}

	public String caseAReturnStm(AReturnStm a, ThmVarsContext vars) throws AnalysisException{
		String exp = a.getExpression().apply(thmStringVisitor, vars);
		
		return ThmProcessUtil.isaReturn + exp;
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
	
//	public String caseANonDeterministicDoStatementAction(ANonDeterministicDoStatementAction a, ThmVarsContext vars) throws AnalysisException{
////	        [alternatives]:action.#Statement.nonDeterministicAlt*
//		//TODO: NOT HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseANonDeterministicIfStatementAction(ANonDeterministicIfStatementAction a, ThmVarsContext vars) throws AnalysisException{
////           [alternatives]:action.#Statement.nonDeterministicAlt*
//		//TODO: NOT HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseANotYetSpecifiedStatementAction(ANotYetSpecifiedStatementAction a, ThmVarsContext vars) throws AnalysisException{
////           [opname]:LexNameToken
////           [args]:exp*
//		//TODO: NOT HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseALetStatementAction(ALetStatementAction a, ThmVarsContext vars) throws AnalysisException{
//		//[action]:action [localDefinitions]:definition*
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseANonDeterministicAltStatementAction(ANonDeterministicAltStatementAction a, ThmVarsContext vars) throws AnalysisException{
////           [guard]:exp
////           [action]:action
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseACasesStatementAction(ACasesStatementAction a, ThmVarsContext vars) throws AnalysisException{
////           [exp]:exp
////           [cases]:alternativeAction.case*
////           [others]:action
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseAMultipleGeneralAssignmentStatementAction(AMultipleGeneralAssignmentStatementAction a, ThmVarsContext vars) throws AnalysisException{
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseASpecificationStatementAction(ASpecificationStatementAction a, ThmVarsContext vars) throws AnalysisException{
////           [externals]:clause.external*
////           [precondition]:exp
////           [postcondition]:exp
//		//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseANewStatementAction(ANewStatementAction a, ThmVarsContext vars) throws AnalysisException{
////	        [destination]:exp
////	        [className]:LexNameToken
////	        [args]:exp*
////	        (classdef):definition.#class
////	        (ctorDefinition):definition
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseAForSetStatementAction(AForSetStatementAction a, ThmVarsContext vars) throws AnalysisException{
////	        [pattern]:pattern
////	        [set]:exp
////	        [action]:action
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseAForIndexStatementAction(AForIndexStatementAction a, ThmVarsContext vars) throws AnalysisException{
////	        [var]:LexNameToken
////	        [from]:exp
////	        [to]:exp
////	        [by]:exp
////	        [action]:action
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//	
//	public String caseAForSequenceStatementAction(AForSequenceStatementAction a, ThmVarsContext vars) throws AnalysisException{
////	        [patternBind]:patternBind.def
////	        [exp]:exp
////	        [action]:action
////	        (seqType):type.#seq
//		//TODO: NOT YET HANDLED
//		return ThmProcessUtil.stmtNotHandled;
//	}
//		
}

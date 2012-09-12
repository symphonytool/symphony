package eu.compassresearch.core.analysis.pog.visitors;

//import java.lang.reflect.Method;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;

import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.*;
import eu.compassresearch.core.analysis.pog.obligations.MapApplyObligation;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.POContext;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligation;
//import org.overturetool.vdmj.lex.LexIdentifierToken;
//import org.overturetool.vdmj.lex.LexNameToken;

import org.overture.ast.types.PType;
import org.overture.ast.types.SMapType;
import org.overture.ast.types.assistants.PTypeAssistant;//ERROR
import org.overture.typecheck.TypeComparator;//ERROR


import org.overture.ast.node.INode;

//import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.expressions.PExp;
//import eu.compassresearch.ast.types.PType;

public class POGExpressionVisitor extends
		QuestionAnswerAdaptor<POContextStack, ProofObligationList>
  {
        
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> parent;
    
    public POGExpressionVisitor(ProofObligationGenerator parentVisitor)
    {
        parent = parentVisitor;
    }

	@Override
	public ProofObligationList defaultPExp(PExp node, POContextStack question)
	{
		System.out.println("Reached POGExpressionVisitor - defaultPEx ");
		return new ProofObligationList();
	}
}

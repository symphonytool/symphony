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

public class PogExpVisitor extends
		QuestionAnswerAdaptor<POContextStack, ProofObligationList>
{

	final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> rootVisitor;

	public PogExpVisitor(ProofObligationGenerator pogVisitor)
	{
		this.rootVisitor = pogVisitor;

	}

	@Override
	public ProofObligationList caseAApplyExp(AApplyExp node, POContextStack question)
	{

		ProofObligationList obligations = new ProofObligationList();

		PExp root = node.getRoot();

		// is it a map?
		PType type = root.getType();
		if (PTypeAssistant.isMap(type))
		{
			if (node.getLocation().startLine == 64);
			SMapType mapType = PTypeAssistant.getMap(type);
			obligations.add(new MapApplyObligation(node.getRoot(), node.getArgs().get(0), question));
			PType aType = question.checkType(node.getArgs().get(0), node.getArgtypes().get(0));
		}
		
//		obligations.addAll(node.getRoot().apply(this, question));
//
//		for (PExp arg : node.getArgs())
//		{
//			obligations.addAll(arg.apply(this, question));
//		}

		return obligations;
		
	}

	@Override
	public ProofObligationList defaultPExp(PExp node, POContextStack question)
	{
		return new ProofObligationList();
	}

}

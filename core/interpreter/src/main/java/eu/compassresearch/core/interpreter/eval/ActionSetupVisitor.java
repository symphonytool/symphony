package eu.compassresearch.core.interpreter.eval;

import java.util.Iterator;
import java.util.ListIterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;

public class ActionSetupVisitor extends AbstractSetupVisitor {

	
	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner,visitorAccess);
	}
	
	private void caseASequentialComposition(INode node, INode leftNode, Context question) throws AnalysisException
	{
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(leftNode, question,new LexNameToken("",owner.name().getSimpleName() + ";",owner.name().location), owner));
	}
	
	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {
		
		caseASequentialComposition(node, node.getLeft(), question);
	}
	
	@Override
	public void caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {
		
		caseASequentialComposition(node, node.getLeft(), question);
	}
	
	
}

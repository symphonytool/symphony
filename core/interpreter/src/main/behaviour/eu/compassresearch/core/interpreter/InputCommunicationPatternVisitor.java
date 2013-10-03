package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ACharacterPattern;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARealPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.AStringPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.TupleValue;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.values.LatticeElement;
import eu.compassresearch.core.interpreter.api.values.LatticeTopElement;
import eu.compassresearch.core.interpreter.api.values.ValueLatticeElement;

public class InputCommunicationPatternVisitor extends
		QuestionAnswerCMLAdaptor<PType, LatticeElement>
{
	@Override
	public LatticeElement defaultINode(INode node, PType question)
			throws AnalysisException
	{
		throw new AnalysisException("Unsupported construct slipped through the parser");
	}
	
	@Override
	public LatticeElement caseAIdentifierPattern(AIdentifierPattern node,
			PType question) throws AnalysisException
	{
		return new LatticeTopElement(question);
	}
	
	@Override
	public LatticeElement caseAExpressionPattern(AExpressionPattern node,
			PType question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseAExpressionPattern(node, question);
	}
	
	@Override
	public LatticeElement caseATuplePattern(ATuplePattern node, PType question)
			throws AnalysisException
	{
//		List<>
//		for(PPattern p : node.getPlist())
			
//		return new ValueLatticeElement(type, new TupleValue(argvals));
		return null;
	}
	
	@Override
	public LatticeElement caseARecordPattern(ARecordPattern node, PType question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseARecordPattern(node, question);
	}
	
	/*
	 * Symbolic literals
	 */
	
	@Override
	public LatticeElement caseACharacterPattern(ACharacterPattern node,
			PType question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseACharacterPattern(node, question);
	}
	
	@Override
	public LatticeElement caseAStringPattern(AStringPattern node, PType question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseAStringPattern(node, question);
	}
	
	@Override
	public LatticeElement caseARealPattern(ARealPattern node, PType question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseARealPattern(node, question);
	}

	@Override
	public LatticeElement createNewReturnValue(INode node, PType question)throws AnalysisException
	{
		throw new AnalysisException("Unsupported construct slipped through the parser");
	}

	@Override
	public LatticeElement createNewReturnValue(Object node, PType question)throws AnalysisException
	{
		throw new AnalysisException("Unsupported construct slipped through the parser");
	}
	
}

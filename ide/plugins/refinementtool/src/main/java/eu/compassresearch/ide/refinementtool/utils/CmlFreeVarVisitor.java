package eu.compassresearch.ide.refinementtool.utils;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AForAllExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.SBinaryExp;
import org.overture.ast.expressions.SBooleanBinaryExp;
import org.overture.ast.expressions.SUnaryExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

public class CmlFreeVarVisitor extends AnswerCMLAdaptor<Set<ILexNameToken>> {

	@Override
	public Set<ILexNameToken> createNewReturnValue(INode arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ILexNameToken> createNewReturnValue(Object arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ILexNameToken> caseAVariableExp(AVariableExp node)
			throws AnalysisException {
		Set<ILexNameToken> xs = new HashSet<ILexNameToken>();
		xs.add(node.getName());
		return xs;
	}

	@Override
	public Set<ILexNameToken> defaultSBinaryExp(SBinaryExp node)
			throws AnalysisException {
		Set<ILexNameToken> xs = node.getLeft().apply(this);
		xs.addAll(node.getRight().apply(this));
		return xs;
	}

	@Override
	public Set<ILexNameToken> defaultSBooleanBinaryExp(SBooleanBinaryExp node)
			throws AnalysisException {
		Set<ILexNameToken> xs = node.getLeft().apply(this);
		xs.addAll(node.getRight().apply(this));
		return xs;
	}

	@Override
	public Set<ILexNameToken> defaultSUnaryExp(SUnaryExp node)
			throws AnalysisException {
		return node.getExp().apply(this);
	}
	
	@Override
	public Set<ILexNameToken> caseAForAllExp(AForAllExp node)
			throws AnalysisException {
		Set<ILexNameToken> bvars = new HashSet<ILexNameToken>();
		
		for (PMultipleBind b : node.getBindList()) {
			bvars.addAll(b.apply(this));
		}
		
		Set<ILexNameToken> xs = node.getPredicate().apply(this);
		xs.removeAll(bvars);
		
		return xs;
	}

	@Override
	public Set<ILexNameToken> caseAIdentifierPattern(AIdentifierPattern node)
			throws AnalysisException {
		Set<ILexNameToken> xs = new HashSet<ILexNameToken>();
		xs.add(node.getName());
		return xs;
	}

	@Override
	public Set<ILexNameToken> defaultPMultipleBind(PMultipleBind node)
			throws AnalysisException {
		Set<ILexNameToken> xs = new HashSet<ILexNameToken>();
		for (PPattern p : node.getPlist()) {
			xs.addAll(p.apply(this));
		}
		return xs;
	}

	
	
}

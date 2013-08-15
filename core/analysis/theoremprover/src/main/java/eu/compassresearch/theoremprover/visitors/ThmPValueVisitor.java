package eu.compassresearch.theoremprover.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmValue;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.theoremprover.utils.ThmValueUtil;

public class ThmPValueVisitor extends AnswerCMLAdaptor<ThmNodeList> {

	
    final private AnswerCMLAdaptor<ThmNodeList> parentVisitor;

    public ThmPValueVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
    	this.parentVisitor = parentVisitor;
    }


	@Override
	public ThmNodeList caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		for(PDefinition t : node.getValueDefinitions())
		{
			tnl.addAll(t.apply(this));
		}
		return tnl;
	}
	

	@Override
	public ThmNodeList caseAValueDefinition(AValueDefinition node)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		ILexNameToken valName =  ((AIdentifierPattern) node.getPattern()).getName();
		String nameStr = valName.toString();
		
		String typeStr = ThmTypeUtil.getIsabelleType(node.getType());

		LinkedList<ILexNameToken> svars = new LinkedList<ILexNameToken>();
		LinkedList<ILexNameToken> evars = new LinkedList<ILexNameToken>();
		String exprStr = ThmExprUtil.getIsabelleExprStr(svars, evars, node.getExpression());

		//TODO GET DEPENDANCIES!
		LinkedList<ILexNameToken> nodeDeps = ThmValueUtil.getIsabelleValueDeps(node);
		
		ThmNode tn = new ThmNode(valName, nodeDeps, new ThmValue(nameStr, typeStr, exprStr));
		tnl.add(tn);
		
		return tnl;
	}
}
package eu.compassresearch.theoremprover.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.theoremprover.thms.NodeNameList;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmValue;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.theoremprover.utils.ThmValueUtil;

public class ThmValueVisitor extends AnswerCMLAdaptor<ThmNodeList> {

    public ThmValueVisitor(
    		AnswerCMLAdaptor<ThmNodeList> parentVisitor) {
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

		NodeNameList svars = new NodeNameList();
		NodeNameList evars = new NodeNameList();
		String exprStr = ThmExprUtil.getIsabelleExprStr(svars, evars, node.getExpression());

		NodeNameList nodeDeps = ThmValueUtil.getIsabelleValueDeps(node);
		
		ThmNode tn = new ThmNode(valName, nodeDeps, new ThmValue(nameStr, typeStr, exprStr));
		tnl.add(tn);
		
		return tnl;
	}
}
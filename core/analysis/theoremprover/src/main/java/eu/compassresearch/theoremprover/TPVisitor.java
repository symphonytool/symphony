package eu.compassresearch.theoremprover;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AStringLiteralExp;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.types.AFunctionParagraphType;

@SuppressWarnings("serial")
public class TPVisitor extends
	DepthFirstAnalysisCMLAdaptor
  {

	List<ThmDecl>  declList = new LinkedList<ThmDecl>();
	List<ThmType>  typeList = new LinkedList<ThmType>();
    List<ThmValue> valueList = new LinkedList<ThmValue>();
	
	public List<ThmValue> getValueList() {
		return valueList;
	}



	public void setValueList(List<ThmValue> valueList) {
		this.valueList = valueList;
	}



	public List<ThmType> getTypeList() {
		return typeList;
	}



	public void setTypeList(List<ThmType> typeList) {
		this.typeList = typeList;
	}



	@Override
	public void caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException {		
		
		ThmPTypeVisitor tv = new ThmPTypeVisitor();
		
		typeList.add(new ThmTypeAbbrev(node.getType().toString(), node.getType().apply(tv)));
		
		super.caseATypeDefinition(node);
	}


	@Override
	public void caseAValueDefinition(AValueDefinition node)
			throws AnalysisException {
		ThmPExpVisitor ev = new ThmPExpVisitor();
		
		for (PDefinition d : node.getDefs()) {
			valueList.add(new ThmValue(d.getName().toString(), node.getExpression().apply(ev)));
		}
		
		super.caseAValueDefinition(node);
	}



	@Override
	public void caseAExplicitFunctionDefinition(AExplicitFunctionDefinition arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		super.caseAExplicitFunctionDefinition(arg0);
	}



	@Override
	public void caseAImplicitFunctionDefinition(AImplicitFunctionDefinition arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		super.caseAImplicitFunctionDefinition(arg0);
	}


	
	
  }
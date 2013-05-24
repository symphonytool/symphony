package eu.compassresearch.theoremprover;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;

@SuppressWarnings("serial")
public class TPVisitor extends
	DepthFirstAnalysisCMLAdaptor
  {

	List<String> typeList = new LinkedList<String>();

	
	
	public List<String> getTypeList() {
		return typeList;
	}



	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}



	@Override
	public void caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException {		
		
		ThmPTypeVisitor tv = new ThmPTypeVisitor();
		
		typeList.add(node.getType().apply(tv));
		
		super.caseATypeDefinition(node);
	}



	@Override
	public void caseATypeSingleDeclaration(ATypeSingleDeclaration node)
			throws AnalysisException {

		ThmPTypeVisitor tv = new ThmPTypeVisitor();
		
		typeList.add(node.getType().apply(tv));
		
		super.caseATypeSingleDeclaration(node);
	}

	
	
  }
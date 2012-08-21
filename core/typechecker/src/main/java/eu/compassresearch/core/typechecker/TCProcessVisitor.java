package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.PType;

@SuppressWarnings("serial")
public class TCProcessVisitor extends
    QuestionAnswerAdaptor<TypeCheckInfo, PType>
  {
    
    private VanillaCmlTypeChecker parentChecker;
    
    public TCProcessVisitor(VanillaCmlTypeChecker parentChecker)
    {
    	this.parentChecker = parentChecker;
    }

    @Override
    public PType caseAStateProcess(AStateProcess node, TypeCheckInfo question)
    		throws AnalysisException {
    	
    	for(PDefinition def : node.getDefinitionParagraphs())
    	{
    		def.apply(this.parentChecker, question);
    	}
    	
    	
    	return new AProcessParagraphType();
    }
    
    
    
  }

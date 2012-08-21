package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.types.AProcessType;
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

    	//Type check all the paragraph definitions	
    	for(PDefinition def : node.getDefinitionParagraphs())
    	{
    		def.apply(this.parentChecker, question);
    		
    		if (def.getType() == null)
    			throw new AnalysisException("Unable to type process definition :" + def.toString());
    	}
    	
    	//types check the main action
    	node.getAction().apply(this.parentChecker, question);
    	
    	return new AProcessType();
    }
    
    
    
  }

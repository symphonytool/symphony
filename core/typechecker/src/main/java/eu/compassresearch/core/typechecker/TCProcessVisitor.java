package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;

@SuppressWarnings("serial")
public class TCProcessVisitor extends
    QuestionAnswerAdaptor<TypeCheckQuestion, PType>
  {
    
    private VanillaCmlTypeChecker parentChecker;
    
    public TCProcessVisitor(VanillaCmlTypeChecker parentChecker)
    {
    	this.parentChecker = parentChecker;
    }

    
    @Override
    public PType caseASynchronousParallelismProcess(
    		ASynchronousParallelismProcess node, TypeCheckQuestion question)
    		throws AnalysisException {
    	
    	node.getLeft().apply(this,question);
    	node.getRight().apply(this,question);
    	
    	//TODO:  missing marker on processes
    	    	
    	return new AProcessType();
    }
    
    @Override
    public PType caseASequentialCompositionProcess(
    		ASequentialCompositionProcess node, TypeCheckQuestion question)
    		throws AnalysisException {
    	
    	node.getLeft().apply(this,question);
    	node.getRight().apply(this,question);
    	
    	//TODO:  missing marker on processes
    	    	
    	return new AProcessType();
    }
    
    @Override
    public PType caseAInstantiationProcess(AInstantiationProcess node,
    		TypeCheckQuestion question) throws AnalysisException {
    	
    	//TODO: implement this!
    	PDefinition processDef = question.lookupVariable(node.getProcessName().getIdentifier());
    
    	if(!(processDef instanceof AProcessParagraphDefinition))
    			throw new AnalysisException("The Identifier " + node.getProcessName().getIdentifier() + 
        				" is refered to as a process, No processes with that name can be found");
    	
    	node.setProcessDefinition(((AProcessParagraphDefinition)processDef).getProcessDefinition());
    	    	    	
       	return new AProcessType();
    }
    
    @Override
    public PType caseAStateProcess(AStateProcess node, TypeCheckQuestion question)
    		throws AnalysisException {

    	//Type check all the paragraph definitions	
    	for(PDefinition def : node.getDefinitionParagraphs())
    	{
    		def.apply(this.parentChecker, question);
    		
    		if (def.getType() == null)
    			throw new AnalysisException("Unable to type check process definition :" + def.toString());
    	}
    	
    	//types check the main action
    	node.getAction().apply(this.parentChecker, question);
    	//TODO: We need to put a type on all actions to mark that they have been type checked
//    	if (node.getAction().getType() == null)
//			throw new AnalysisException("Unable to typechecl process definition :" + node.getAction().toString());
    	
    	return new AProcessType();
    }
    
    
    
  }

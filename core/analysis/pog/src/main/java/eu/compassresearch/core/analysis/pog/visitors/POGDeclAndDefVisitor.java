package eu.compassresearch.core.analysis.pog.visitors;

import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
// import java.util.LinkedList;
// import java.util.List;
// 
// import org.overture.parser.messages.VDMError;
// 
// import eu.compassresearch.ast.actions.SStatementAction;
// import eu.compassresearch.ast.analysis.AnalysisException;
// import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
// import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
// import eu.compassresearch.ast.definitions.AActionDefinition;
// import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
// import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
// import eu.compassresearch.ast.definitions.AChansetDefinition;
// import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
// import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
// import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
// import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
// import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
// import eu.compassresearch.ast.definitions.ALocalDefinition;
// import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
// import eu.compassresearch.ast.definitions.AProcessDefinition;
// import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
// import eu.compassresearch.ast.definitions.AValueDefinition;
// import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
// import eu.compassresearch.ast.definitions.PDefinition;
// import eu.compassresearch.ast.expressions.PExp;
// import eu.compassresearch.ast.patterns.AIdentifierPattern;
// import eu.compassresearch.ast.patterns.PPattern;
// import eu.compassresearch.ast.typechecker.NameScope;
// import eu.compassresearch.ast.types.AChannelType;
// import eu.compassresearch.ast.types.AChansetParagraphType;
// import eu.compassresearch.ast.types.AClassType;
// import eu.compassresearch.ast.types.AErrorType;
// import eu.compassresearch.ast.types.AFunctionParagraphType;
// import eu.compassresearch.ast.types.AFunctionType;
// import eu.compassresearch.ast.types.AOperationType;
// import eu.compassresearch.ast.types.AProcessParagraphType;
// import eu.compassresearch.ast.types.AValueParagraphType;
// import eu.compassresearch.ast.types.PType;
// import eu.compassresearch.transformation.CmlAstToOvertureAst;
// import eu.compassresearch.transformation.CopyTypesFromOvtToCmlAst;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends
		QuestionAnswerAdaptor<POContextStack, ProofObligationList>
  {
    
    // Errors and other things are recorded on this guy
    private ProofObligationGenerator parentPOG;
    
    public POGDeclAndDefVisitor(ProofObligationGenerator parent)
      {
        this.parentPOG = parent;
      }
      
      
    @Override
    public ProofObligationList defaultPDeclaration(PDeclaration node, POContextStack question)
        throws AnalysisException
      {
		System.out.println("Reached POGDeclAndDefVisitor - defaultPDeclaration");
		return new ProofObligationList();
      }
    
    @Override
    public ProofObligationList defaultPDefinition(PDefinition node, POContextStack question)
        throws AnalysisException
      {
		System.out.println("Reached POGDeclAndDefVisitor - defaultPDefinition");
		return new ProofObligationList();
      }
      
    }
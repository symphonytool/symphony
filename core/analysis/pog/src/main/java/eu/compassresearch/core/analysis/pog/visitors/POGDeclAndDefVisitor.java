package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp; 

//POG-related imports
import eu.compassresearch.core.analysis.pog.obligations.MapApplyObligation;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.POContext;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligation;
//import eu.compassresearch.core.analysis.pog.obligations.SatisfiabilityObligation;
//import eu.compassresearch.core.analysis.pog.obligations.POFunctionResultContext;

import java.util.LinkedList;
import java.util.List;

//import org.overture.parser.messages.VDMError;

 


import eu.compassresearch.ast.declarations.PDeclaration;
// import eu.compassresearch.ast.actions.SStatementAction;
// import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
// import eu.compassresearch.ast.definitions.AActionDefinition;
// import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
// import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
// import eu.compassresearch.ast.definitions.AChansetDefinition;
// import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
// import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
// import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
// import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
// import eu.compassresearch.ast.definitions.AImplicitFunctionDefinition;
// import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
// import eu.compassresearch.ast.definitions.ALocalDefinition;
// import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
// import eu.compassresearch.ast.definitions.AProcessDefinition;
// import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
// import eu.compassresearch.ast.definitions.ATypeDefinition;
// import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
// import eu.compassresearch.ast.definitions.AValueDefinition;
// import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
// import eu.compassresearch.ast.process.PProcess;
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
      
      
//    @Override
//    public ProofObligationList defaultPDeclaration(PDeclaration node, POContextStack question)
//        throws AnalysisException
//      {
//		System.out.println("Reached POGDeclAndDefVisitor - defaultPDeclaration");
//		System.out.println(node.getClass());
//		return new ProofObligationList();
//      }
    
    @Override
    public ProofObligationList defaultPDefinition(PDefinition node, POContextStack question)
        throws AnalysisException
    {
		System.out.println("Reached POGDeclAndDefVisitor - defaultPDefinition");
		System.out.println(node.getClass());
		return new ProofObligationList();
    }
    
    

//     @Override
//     public ProofObligationList caseAClassParagraphDefinition(AClassParagraphDefinition node,
//         POContextStack question) throws AnalysisException
//     {		
//         System.out.println("------");
//       	System.out.println("Reached POGDeclAndDefVisitor - caseAClassParagraphDefinition");
// 
// 		for (PDefinition def : node.getDefinitions())
//         {
//             System.out.println("In defn Paragraph Loop: " + def.toString());
// 
//             ProofObligationList pol = def.apply(parentPOG, question);
//         }
//               
// 		return new ProofObligationList();
//     }
//     
//     @Override
//     public ProofObligationList caseAProcessParagraphDefinition(
//          AProcessParagraphDefinition node, POContextStack question)
//          throws AnalysisException
//     {
//         System.out.println("- caseAProcessParagraphDefinition - ");
//          
//         AProcessDefinition pdef = node.getProcessDefinition();
//         ProofObligationList pol = pdef.apply(this, question);
//          
// 		return new ProofObligationList();
//     }
//        
//     @Override
//     public ProofObligationList caseAProcessDefinition(
//          AProcessDefinition node, POContextStack question)
//          throws AnalysisException
//     {
//         System.out.println("- caseAProcessDefinition - ");
//          
//        	System.out.println("----------***----------");
//         System.out.println(node.toString());
//         
//         //NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
//         //LinkedList<> lstate = node.getLocalState();
//         //for ( s : lstate)
//         //{
//         //	System.out.println("----------***----------");
//         //    ProofObligationList td = s.apply(this, question);
//         //	System.out.println("----------***----------");
//         //}
//             
//         PProcess pdef = node.getProcess();
//         	System.out.println("----------***----------");
//         ProofObligationList pol = pdef.apply(this, question);
//             System.out.println(pdef.toString());
//         	System.out.println("----------***----------");
//         
//         
//         
//         //ProofObligationList td = d.apply(this, question);
//         System.out.println("----------***----------");
//         
// 		return new ProofObligationList();
//     }
//     /**
//       *
//       * VDM ELEMENTS
//       *
//       */
// 	@Override
// 	public ProofObligationList caseATypesParagraphDefinition(ATypesParagraphDefinition node,
// 			POContextStack question)
//          throws AnalysisException
// 	{
//       	System.out.println("- caseATypesProcessDefinition -");
// 
// 		for (PDefinition def : node.getTypes())
//         {
//             System.out.println("----------***----------");
//             ProofObligationList td = def.apply(this, question);
//         	System.out.println("----------***----------");
//         }
//               
// 		return new ProofObligationList();
// 	}
// 	
// 	@Override
// 	public ProofObligationList caseATypeDefinition(ATypeDefinition node,
// 			POContextStack question)
//          throws AnalysisException
// 	{
//         System.out.println("- caseATypeDefinition -");
//         
//         System.out.println(node.toString());
// 		ProofObligationList list = new ProofObligationList();
// 
// 		return list;
// 	}
//     
//     
//     
//        
//       
//     @Override
//     public ProofObligationList caseAChansetParagraphDefinition(
//         AChansetParagraphDefinition node, POContextStack question)
//         throws AnalysisException
//     {
//         System.out.println("- caseAChansetParagraphDefinition -");
//       
//         LinkedList<AChansetDefinition> subNodes = node.getChansets();
//         for (AChansetDefinition d : subNodes)
//         {
//         	System.out.println("----------***----------");
//             ProofObligationList td = d.apply(this, question);
//         	System.out.println("----------***----------");
//         }
//         
// 		return new ProofObligationList();
//     }
//     
//     @Override
//     public ProofObligationList caseAChansetDefinition(
//         AChansetDefinition node, POContextStack question)
//         throws AnalysisException
//     {
//         System.out.println("- caseAChansetDefinition -");
// 
//         System.out.println(node.toString());
// 		ProofObligationList list = new ProofObligationList();
// 
// 		return list;
//     }
//       
//       
//     @Override
//     public ProofObligationList caseAChannelParagraphDefinition(
//         AChannelParagraphDefinition node, POContextStack question)
//         throws AnalysisException
//     {
//         System.out.println("- caseAChannelParagraphDefinition -");
//          
//          
//          LinkedList<AChannelNameDeclaration> cns = node.getChannelNames();
//          for (AChannelNameDeclaration decl : cns)
//          {
//              
//         	 System.out.println("----------***----------");
//              System.out.println(decl.toString());
//              ProofObligationList typeBack = decl.apply(this, question);
//         	 System.out.println("----------***----------");
//     	 }
//          
// 		 return new ProofObligationList();
//     } 
//       
//     @Override
//     public ProofObligationList caseAValueParagraphDefinition(AValueParagraphDefinition node,
//         POContextStack question) throws AnalysisException
//     {
//         System.out.println("------");
//         System.out.println("Reached POGDeclAndDefVisitor - caseAValueParagraphDefinition");
//         
//         LinkedList<PDefinition> list = node.getValueDefinitions();
//         for (PDefinition def : list)
//           {
//             System.out.println("In Value Paragraph Loop: " + def.toString());
//             ProofObligationList defType = def.apply(parentPOG, question);
//           }
//         
// 		return new ProofObligationList();
//     }
//     
//     @Override
//     public ProofObligationList caseAValueDefinition(AValueDefinition node,
//         POContextStack question) throws AnalysisException
//     {
//         System.out.println("------");
//         System.out.println("Reached POGDeclAndDefVisitor - caseAValueDefinition");
//         
// 		return new ProofObligationList();
//     }    
// 
// 
//     @Override
//     public ProofObligationList caseAFunctionParagraphDefinition(
//          AFunctionParagraphDefinition node, POContextStack question)
//          throws AnalysisException
//     {
//         System.out.println("-caseAFunctionParagraphDefinition-");
//         
//         for (PDefinition def : node.getFunctionDefinitions())
//         {
//         	ProofObligationList pol = def.apply(parentPOG, question);
//         }
//            
// 		return new ProofObligationList();
//     }
//     
//     @Override
//     public ProofObligationList caseAExplicitFunctionDefinition(
//         AExplicitFunctionDefinition node, POContextStack question)
//         throws AnalysisException
//       {
//          System.out.println("----------***----------");
//          System.out.println("caseAExplicitFunctionDefinition");
//          System.out.println(node.toString());
//          System.out.println("----------***----------");
// 		return new ProofObligationList();
//       }
//       
//     @Override
// 	public ProofObligationList caseAImplicitFunctionDefinition(
// 		AImplicitFunctionDefinition node, POContextStack question)
//         throws AnalysisException
// 	{
// 		ProofObligationList obligations = new ProofObligationList();
// 
// 		//if implicit function has a precondition, dispatch for PO checking 
// 		if (node.getPrecondition() != null)
// 		{
// 			obligations.addAll(node.getPrecondition().apply(parentPOG, question));
// 		}
// 		
// 		//if has a postcondition, should generate SatisfiabilityObligation
// 		if (node.getPostcondition() != null)
// 		{
// 			obligations.add(new SatisfiabilityObligation(node, question));
// 			
// 			question.push(new POFunctionResultContext(node));
// 			obligations.addAll(node.getPostcondition().apply(parentPOG, question));
// 			question.pop();
// 		}
// 		return obligations;
// 	}  
//       
//       
//     @Override
//     public ProofObligationList caseAOperationParagraphDefinition(
//         AOperationParagraphDefinition node, POContextStack question)
//         throws AnalysisException
//     {
//         System.out.println("------");
//         System.out.println("Reached POGDeclAndDefVisitor - caseAFunctionParagraphDefinition");
//          
//         for (PDefinition def : node.getOperations())
//         {
//             System.out.println("In Operation Paragraph Loop: " + def.toString());
//             ProofObligationList pol = def.apply(this, question);
//          }
//            
// 		return new ProofObligationList();
//     }
//      
// 
// 
//     @Override
//     public ProofObligationList caseAExplicitOperationDefinition(
//         AExplicitOperationDefinition node, POContextStack question)
//         throws AnalysisException
//       {
//         System.out.println("------");
//         System.out.println("Reached POGDeclAndDefVisitor - caseAExplicitOperationDefinition");
//          
// 		return new ProofObligationList();	
//       }
//     
// 
//     
//     @Override
//     public ProofObligationList caseAActionParagraphDefinition(
//         AActionParagraphDefinition node, POContextStack question)
//         throws AnalysisException
//       {
//         System.out.println("------");
//         System.out.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");
//                  
//         LinkedList<AActionDefinition> actions = node.getActions();
//         for (AActionDefinition action : actions)
//         {
//             System.out.println("In Action Paragraph Loop: " + action.toString());
//         }
//           
// 		return new ProofObligationList();   
//       }

}
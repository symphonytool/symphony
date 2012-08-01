package eu.compassresearch.core.typechecker;

import java.util.LinkedList;

import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChansetParagraphType;
import eu.compassresearch.ast.types.AClassType;
import eu.compassresearch.ast.types.AFunctionType;
import eu.compassresearch.ast.types.AOperationType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.transformation.CmlAstToOvertureAst;
import eu.compassresearch.transformation.CopyTypesFromOvtToCmlAst;

public class TCDeclAndDefVisitor extends
    QuestionAnswerAdaptor<TypeCheckInfo, PType>
  {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -3595335302756624612L;
    
    private CmlTypeChecker    parentChecker;
    
    public TCDeclAndDefVisitor(CmlTypeChecker parent)
      {
        this.parentChecker = parent;
      }
    
    // -------------------------------------------------------
    // Cases
    // -------------------------------------------------------
    
    @Override
    public PType caseAValueParagraphDefinition(AValueParagraphDefinition node,
        TypeCheckInfo question) throws AnalysisException
      {
        
        LinkedList<PDefinition> list = node.getValueDefinitions();
        for (PDefinition def : list)
          {
            PType defType = def.apply(parentChecker, question);
            question.env.put(def.getName(), def);
          }
        
        node.setType(new AValueParagraphType());
        return node.getType();
      }
    
    @Override
    public PType caseAValueDefinition(AValueDefinition node,
        TypeCheckInfo question) throws AnalysisException
      {
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        INode ovtNode = node.apply(transform);
        CopyTypesFromOvtToCmlAst copy = new CopyTypesFromOvtToCmlAst(
            transform.getNodeMap());
        try
          {
            ovtNode.apply(copy);
          } catch (org.overture.ast.analysis.AnalysisException e)
          {
            throw new AnalysisException(e);
          }
        return node.getType();
      }
    
    // ------------------------------------------------
    // Paragraphs
    // -------------------------------------------------
    /**
     * <pre>
     * C:AClassParagraphDefinition Def_1 Type Ok 
     * . 
     * . 
     * . 
     * Def_n Type Ok
     * -------------- C |- C Type OK
     * </pre>
     * 
     */
    @Override
    public PType caseAClassParagraphDefinition(AClassParagraphDefinition node,
        TypeCheckInfo question) throws AnalysisException
      {
        
        for (PDefinition def : node.getDefinitions())
          {
            PType type = def.apply(parentChecker, question);
            if (type == null)
              throw new AnalysisException("Unable to determine type for: "
                  + def);
          }
        
        PType clzType = new AClassType();
        clzType.setDefinitions(node.getDefinitions());
        node.setType(clzType);
        
        return clzType;
      }
    
    @Override
    public PType caseAOperationParagraphDefinition(
        AOperationParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        for (PDefinition def : node.getOperations())
          {
            def.apply(this, question);
            question.env.put(def.getName(), def);
          }
        
        return node.getType();
      }
    
    /**
     * A process paragraph is well typed if all its constituents are.
     * 
     * <pre>
     * |- P:AProcessParagraphDefinition 
     * |- Process_1 Type Ok 
     * . 
     * . 
     * . 
     * |- Process_N Type Ok 
     *    --------- 
     * |- P Type Ok
     * </pre>
     * 
     * 
     */
    @Override
    public PType caseAProcessParagraphDefinition(
        AProcessParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        for (PProcess d : node.getProcesses())
          {
            // check the process
            PType type = d.apply(parentChecker, question);
            if (type == null)
              throw new AnalysisException("Unable to determine type for: " + d);
          }
        
        // Marker type indicating paragraph type check ok
        node.setType(new AProcessParagraphType());
        question.env.put(node.getName(), node);
        
        return node.getType();
      }
    
    @Override
    public PType caseAChannelParagraphDefinition(
        AChannelParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        LinkedList<AChannelNameDeclaration> cns = node.getChannelNames();
        for (AChannelNameDeclaration decl : cns)
          {
            PType typeBack = decl.apply(this, question);
            if (typeBack == null)
              {
                PDefinition def = question.env.lookupName(decl.getIdentifier());
                if (def == null)
                  throw new AnalysisException("Channel name: "
                      + decl.getIdentifier() + " cannot be resolved.");
                typeBack = def.getType();
              }
            if (typeBack == null)
              throw new AnalysisException("Unable to determine type for: "
                  + decl);
          }
        
        node.setType(new AChannelType());
        question.env.put(node.getName(), node);
        
        return node.getType();
      }
    
    private eu.compassresearch.ast.node.INode applyTransformToGetType(
        eu.compassresearch.ast.node.INode cml) throws AnalysisException,
        org.overture.ast.analysis.AnalysisException
      {
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        CopyTypesFromOvtToCmlAst copier = new CopyTypesFromOvtToCmlAst(
            transform.getNodeMap());
        return cml.apply(transform).apply(copier);
      }
    
    @Override
    public PType caseAExplicitOperationDefinition(
        AExplicitOperationDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        // check the body
        SStatementAction operationBody = node.getBody();
        PType bodyType = operationBody.apply(parentChecker, question);
        if (bodyType == null)
          throw new AnalysisException("Unable to type check operation body "
              + node.getName());
        
        // check constructor
        boolean isCtor = node.getIsConstructor();
        if (isCtor) // check type is of class type
          {
            AOperationType operType = node.getType();
            if (!operType.getResult().equals(
                node.getAncestor(AClassParagraphDefinition.class).getType()))
              {
                
              }
            
          }
        
        return node.getType();
      }
    
    @Override
    public PType caseAExplicitFunctionDefinition(
        AExplicitFunctionDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        PExp body = node.getBody();
        try
          {
            applyTransformToGetType(body);
          } catch (org.overture.ast.analysis.AnalysisException e)
          {
            throw new AnalysisException(e.getMessage());
          }
        
        AFunctionType funcType = node.getType();
        if (!funcType.getResult().equals(body.getType()))
          parentChecker
              .addTypeError(
                  funcType.getLocation(),
                  "Function return type is different than the type of the function body. ReturnType: ["
                      + funcType.getResult()
                      + "], BodyType: ["
                      + body.getType() + "]");
        
        return super.caseAExplicitFunctionDefinition(node, question);
      }
    
    @Override
    public PType caseAChansetParagraphDefinition(
        AChansetParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        LinkedList<AChansetDefinition> subNodes = node.getChansets();
        for (AChansetDefinition d : subNodes)
          {
            PType td = d.apply(this, question);
            if (td == null)
              throw new AnalysisException(
                  "In ChansetParagraph unable to determine type for Chanset Definition.");
            question.env.put(d.getIdentifier(), d);
          }
        
        node.setType(new AChansetParagraphType()); // mark typed ok
        
        return node.getType();
        
      }
    
    @Override
    public PType caseAActionParagraphDefinition(
        AActionParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        LinkedList<AActionDefinition> actions = node.getActions();
        for (AActionDefinition action : actions)
          {
            PType actionType = action.apply(this, question);
            if (actionType == null)
              throw new AnalysisException(
                  "In Action Paragraph, unable to determine type for Action Definition.");
            if (question.env.lookupName(action.getName()) != null)
              throw new AnalysisException("Cannot hide action.");
            question.env.put(action.getName(), action);
          }
        return node.getType();
      }
    
  }
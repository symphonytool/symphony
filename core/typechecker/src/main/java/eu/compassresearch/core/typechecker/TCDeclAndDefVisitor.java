package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.node.INode;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.visitor.TypeCheckerExpVisitor;

import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.ALocalDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.patterns.AIdentifierPattern;
import eu.compassresearch.ast.patterns.PPattern;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.typechecker.NameScope;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChannelsParagraphType;
import eu.compassresearch.ast.types.AChansetParagraphType;
import eu.compassresearch.ast.types.AClassType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.ast.types.AFunctionType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AOperationType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.ast.types.PType;
import eu.compassresearch.transformation.CmlAstToOvertureAst;
import eu.compassresearch.transformation.CopyTypesFromOvtToCmlAst;

@SuppressWarnings("serial")
public class TCDeclAndDefVisitor extends
    QuestionAnswerAdaptor<TypeCheckInfo, PType>
  {
    
    // Errors and other things are recorded on this guy
    private VanillaCmlTypeChecker parentChecker;
    
    public TCDeclAndDefVisitor(VanillaCmlTypeChecker parent)
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
    
    /**
     * A Function Paragraph is well typed if all the type definitions it
     * contains are well typed.
     * 
     * 
     * 
     */
    @Override
    public PType caseAFunctionParagraphDefinition(
        AFunctionParagraphDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        for (PDefinition def : node.getFunctionDefinitions())
          {
            PType type = def.apply(parentChecker, question);
            if (type == null)
              throw new AnalysisException(
                  "Unable to determined type for function definition: " + def);
          }
        node.setType(new AFunctionParagraphType());
        
        return node.getType();
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
        
        node.setType(new AOperationParagraphType());
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
        
           // check the process
       	PProcess d = node.getProcessDefinition().getProcess();
        PType type = d.apply(parentChecker, question);
        if (type == null)
        	throw new AnalysisException("Unable to determine type for: " + d);
        
        // Marker type indicating paragraph type check ok
        node.setType(new AProcessParagraphType());
        question.env.put(node.getName(), node);
        
        return node.getType();
      }
   
    @Override
    public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
    		TypeCheckInfo question) throws AnalysisException {
    	    	    	
    	AChannelType ctype = new AChannelType();
    	ctype.setType(node.getType());
    	node.setType(new AChannelType());
    	
    	return node.getType();
    }
    
    @Override
    public PType caseAChannelParagraphDefinition(
    		AChannelParagraphDefinition node, TypeCheckInfo question)
    				throws AnalysisException
    {
    	LinkedList<AChannelNameDeclaration> cns = node.getChannelNameDeclarations();
    	for (AChannelNameDeclaration decl : cns)
    	{
    		decl.getSingleType().apply(this,question); 

    		//PType typeBack = decl.apply(this, question);

    		//    		if (typeBack == null)
    		//    		{
    		//    			PDefinition def = question.env.lookupName(decl.getIdentifier());
    		//    			if (def == null)
    		//    				throw new AnalysisException("Channel name: "
    		//    						+ decl.getIdentifier() + " cannot be resolved.");
    		//    			typeBack = def.getType();
    		//    		}
    		//    		if (typeBack == null)
    		//    			throw new AnalysisException("Unable to determine type for: "
    		//    					+ decl);
    	}

    	node.setType(new AChannelsParagraphType());
    	return node.getType();
   }

    /*
     * The Overture TypeCheckVisitor needs a parent visitor to invoke. At this
     * time we provide the empty one.
     */
    private static class FakeOvertureRootVisitor extends
        org.overture.typechecker.visitor.TypeCheckVisitor
      {
        
      }
    
    /*
     * Utility method to transform from CmlAst to OvtAst, type check OvtAst copy
     * the types froom OvtAst and Back to the CmlAst.
     * 
     * 
     * 
     * @param cml - cml subtree
     * 
     * @param nfo - the current environment
     * 
     * @return - cml subtree with types
     * 
     * @throws AnalysisException - if things goes wrong, like if some tree-node
     * is named differently in the OveAst than in the CmlAst.
     * 
     * @throws org.overture.ast.analysis.AnalysisException
     */
    private eu.compassresearch.ast.node.INode applyTransformToGetType(
        eu.compassresearch.ast.expressions.PExp cml, TypeCheckInfo nfo)
        throws AnalysisException
      {
        
        org.overture.typechecker.TypeChecker.clearErrors();
        
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        org.overture.ast.expressions.PExp ovtNode = (org.overture.ast.expressions.PExp) cml
            .apply(transform);
        org.overture.typechecker.visitor.TypeCheckerExpVisitor exprCheckerExpVisitor = new TypeCheckerExpVisitor(
            new FakeOvertureRootVisitor());
        org.overture.typechecker.TypeCheckInfo ovtQuestion = new org.overture.typechecker.TypeCheckInfo(
            nfo.env.getOvertureEnv());
        
        try
          {
            ovtNode.apply(exprCheckerExpVisitor, ovtQuestion);
          } catch (org.overture.ast.analysis.AnalysisException ae)
          {
            throw new AnalysisException(ae.getMessage());
          }
        
        if (org.overture.typechecker.TypeChecker.getErrorCount() > 0)
          {
            for (VDMError vdme : org.overture.typechecker.TypeChecker
                .getErrors())
              parentChecker.addTypeError(cml, vdme.message);
            cml.setType(new AErrorType());
            return cml;
          }
        
        CopyTypesFromOvtToCmlAst copier = new CopyTypesFromOvtToCmlAst(
            transform.getNodeMap());
        
        eu.compassresearch.ast.node.INode result = cml;
        try
          {
            result = ovtNode.apply(copier);
          } catch (org.overture.ast.analysis.AnalysisException ae)
          {
            throw new AnalysisException(ae.getMessage());
          }
        
        return result;
      }
    
    @Override
    public PType caseAExplicitOperationDefinition(
        AExplicitOperationDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        // add the state of the enclosing class to the Environment
        
        // add the parameter to the Environment
        Environment bodyEnv = createEnvironmentWithFormals(question.env, node);
        
        // check the body
        TypeCheckInfo newQuestion = new TypeCheckInfo(bodyEnv);
        SStatementAction operationBody = node.getBody();
        PType bodyType = operationBody.apply(parentChecker, newQuestion);
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
    
    private Environment createEnvironmentWithFormals(Environment existingEnv,
        PDefinition funDef) throws AnalysisException
      {
        
        List<PType> paramTypes = null;
        List<PPattern> patterns = null;
        
        if (funDef instanceof AExplicitFunctionDefinition)
          {
            // Flatten list of lists into one list.
            patterns = new LinkedList<PPattern>();
            for (List<PPattern> lp : AExplicitFunctionDefinition.class.cast(
                funDef).getParamPatternList())
              for (PPattern p : lp)
                patterns.add(p);
            
            paramTypes = AExplicitFunctionDefinition.class.cast(funDef)
                .getType().getParameters();
          } else if (funDef instanceof AExplicitOperationDefinition)
          {
            patterns = AExplicitOperationDefinition.class.cast(funDef)
                .getParameterPatterns();
          }
        
        // setup local environment
        Environment functionBodyEnv = new Environment(existingEnv);
        
        // add formal arguments to the environment
        int i = 0;
        for (PPattern p : patterns)
          {
            if (p instanceof AIdentifierPattern)
              {
                AIdentifierPattern idp = (AIdentifierPattern) p;
                ALocalDefinition local = new ALocalDefinition(
                    idp.getLocation(), idp.getName(), NameScope.LOCAL, false,
                    null, paramTypes.get(i), null);
                functionBodyEnv.put(idp.getName(), local);
              } else
              throw new AnalysisException(
                  "Can only handle identifier patterns at this time.");
            i++;
          }
        
        /*
         * TODO: Question, are identifier patterns the only one we care about
         * for function parameters? If they are the AnalysisException thrown
         * above must be turned into a type error.
         */
        return functionBodyEnv;
      }
    
    @Override
    public PType caseAExplicitFunctionDefinition(
        AExplicitFunctionDefinition node, TypeCheckInfo question)
        throws AnalysisException
      {
        
        //
        AFunctionType funcType = node.getType();
        
        // Setup local environment
        Environment functionBodyEnv = createEnvironmentWithFormals(
            question.env, node);
        
        // Type check the function body in the functionBodyEnv.
        PExp body = node.getBody();
        TypeCheckInfo newQuestion = new TypeCheckInfo(functionBodyEnv);
        applyTransformToGetType(body, newQuestion);
        if (body.getType() == null)
          throw new AnalysisException(
              "Transformation and type checking with Overture type checker failed for expression: "
                  + body);
        
        // Check funcType <: bodyType in question.env {@link subTypeOf}
        if (subTypeOf(question.env, funcType, body.getType()))
          parentChecker
              .addTypeError(
                  funcType,
                  "Function return type is different than the type of the function body. ReturnType: ["
                      + funcType.getResult()
                      + "], BodyType: ["
                      + body.getType() + "]");
        
        // Nonetheless the function type will be the type its definition to
        // facilitate further type checking even in the presents of errors.
        return funcType;
      }
    
    /*
     * This method determines the sub type relation between two types. It is
     * denoted: "A <: B" and we say A is a sub type of B.
     * 
     * @return true => A <: B, false => not A <: B
     */
    private static boolean subTypeOf(Environment env, PType typeA, PType typeB)
      {
        // TODO: Implement this guy proper
        return typeA.equals(typeB);
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
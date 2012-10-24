package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AStateParagraphType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
class TCDeclAndDefVisitor extends
    QuestionAnswerCMLAdaptor<TypeCheckQuestion, PType>
  {
    
    // Errors and other things are recorded on this guy
    private CmlTypeChecker         parentChecker;
    private TypeComparator         typeComparator;
    private final TypeIssueHandler issueHandler;
    
    public TCDeclAndDefVisitor(CmlTypeChecker parent,
        TypeComparator typeComparator, TypeIssueHandler issueHandler)
      {
        this.parentChecker = parent;
        this.issueHandler = issueHandler;
        this.typeComparator = typeComparator;
      }
    
    // -------------------------------------------------------
    // Cases
    // -------------------------------------------------------
    
    @Override
    public PType caseATypesParagraphDefinition(ATypesParagraphDefinition node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        LinkedList<ATypeDefinition> defs = node.getTypes();
        for (ATypeDefinition d : defs)
          {
            PType type = d.apply(parentChecker, question);
            question.addType(d.getName(), type);
          }
        node.setType(new ATypeParagraphType());
        return node.getType();
      }
    
    @Override
    public PType caseAValueParagraphDefinition(AValueParagraphDefinition node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        LinkedList<PDefinition> list = node.getValueDefinitions();
        for (PDefinition def : list)
          {
            PType defType = def.apply(parentChecker, question);
            question.addType(def.getName(), defType);
          }
        
        node.setType(new AValueParagraphType());
        return node.getType();
      }
    
    @Override
    public PType caseAValueDefinition(AValueDefinition node,
        TypeCheckQuestion question) throws AnalysisException
      {
        // Acquire declared type and expression type
        PExp exp = node.getExpression();
        PType declaredType = node.getType().apply(parentChecker, question);
        PType expressionType = exp.apply(parentChecker, question);
        node.setExpType(expressionType);
        
        // Check type consistency
        if (!typeComparator.isSubType(expressionType, declaredType))
          issueHandler.addTypeError(node,
              TypeErrorMessages.EXPECTED_SUBTYPE_RELATION.customizeMessage(
                  expressionType.toString(), declaredType.toString()));
        
        // No matter the declared type is the type of the definition
        node.setType(declaredType);
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
        TypeCheckQuestion question) throws AnalysisException
      {
        
        // Add this class to the current environment
        PType clzType = new AClassType();
        clzType.setDefinitions(node.getDefinitions());
        node.setType(clzType);
        
        // Create scope for the class body
        TypeCheckQuestion classQuestion = question.newScope(node);
        for (PDefinition def : node.getDefinitions())
          {
            question.updateContextNameToCurrentScope(def);
            PType type = def.apply(parentChecker, classQuestion);
            if (type == null)
              throw new AnalysisException("Unable to determine type for: "
                  + def);
          }
        
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
        AFunctionParagraphDefinition node, TypeCheckQuestion question)
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
        AOperationParagraphDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        
        for (PDefinition def : node.getOperations())
          {
            PType defType = def.apply(parentChecker, question);
            if (defType == null)
              issueHandler.addTypeError(def,
                  TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
                      .customizeMessage(def.toString()));
            question.addVariable(def.getName(), def);
          }
        
        node.setType(new AOperationParagraphType());
        return node.getType();
      }
    
    @Override
    public PType caseAStateParagraphDefinition(AStateParagraphDefinition node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        // Go through all the state defs and typecheck them
        for (PDefinition def : node.getStateDefs())
          {
            def.apply(this, question);
            question.addVariable(def.getName(), def);
          }
        
        node.setType(new AStateParagraphType());
        
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
        AProcessParagraphDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        
        // TODO: Rethink the environment
        AProcessDefinition pdef = node.getProcessDefinition();
        pdef.apply(parentChecker, question);
        
        // Marker type indicating paragraph type check ok
        node.setType(new AProcessParagraphType());
        question.addVariable(node.getName(), node);
        
        return node.getType();
      }
    
    @Override
    public PType caseAProcessDefinition(AProcessDefinition node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        return node.getProcess().apply(this.parentChecker, question);
      }
    
    @Override
    public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
        TypeCheckQuestion question) throws AnalysisException
      {
        
        AChannelType ctype = new AChannelType();
        ctype.setType(node.getType());
        node.setType(new AChannelType());
        
        for (LexIdentifierToken id : node.getIdentifiers())
          {
            question.addChannel(id, node);
          }
        
        return node.getType();
      }
    
    @Override
    public PType caseAChannelParagraphDefinition(
        AChannelParagraphDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        
        LinkedList<AChannelNameDeclaration> cns = node
            .getChannelNameDeclarations();
        for (AChannelNameDeclaration decl : cns)
          {
            PType typeBack = decl.getSingleType().apply(this, question); // decl.apply(parentChecker,
                                                                         // question);
            if (typeBack == null)
              issueHandler.addTypeError(decl,
                  TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
                      .customizeMessage(decl.toString()));
            else
              for (LexIdentifierToken id : decl.getSingleType()
                  .getIdentifiers())
                question.addChannel(id, decl);
          }
        
        node.setType(new AChannelType());
        question.addType(node.getName(), node.getType());
        
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
    
    @Override
    public PType caseAExplicitOperationDefinition(
        AExplicitOperationDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        // add the state of the enclosing class to the Environment
        
        // add the parameter to the Environment
        
        // check the body
        TypeCheckQuestion newQuestion = createEnvironmentWithFormals(question,
            node);
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
    
    private TypeCheckQuestion createEnvironmentWithFormals(
        TypeCheckQuestion current, PDefinition funDef) throws AnalysisException
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
        TypeCheckQuestion functionBodyEnv = current.newScope(funDef);
        
        // add formal arguments to the environment
        int i = 0;
        for (PPattern p : patterns)
          {
            if (p instanceof AIdentifierPattern)
              {
                PType paramType = (i < paramTypes.size() ? paramTypes.get(i)
                    : new AErrorType(p.getLocation(), true));
                AIdentifierPattern idp = (AIdentifierPattern) p;
                LexLocation location_ = p.getLocation();
                org.overture.ast.typechecker.NameScope nameScope_ = NameScope.LOCAL;
                Boolean used_ = false;
                SClassDefinition classDefinition_ = null;
                AAccessSpecifierAccessSpecifier access_;
                PType type_ = paramType;
                Pass pass_ = Pass.DEFS;
                Boolean valueDefinition_ = false;
                LexNameToken name_ = ((AIdentifierPattern) p).getName();
                ALocalDefinition local = new ALocalDefinition(location_,
                    nameScope_, used_, classDefinition_, null, type_, pass_,
                    valueDefinition_, name_);
                functionBodyEnv.addVariable(idp.getName(), local);
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
        AExplicitFunctionDefinition node, TypeCheckQuestion question)
        throws AnalysisException
      {
        
        // Type check the function body in an augmented environment
        TypeCheckQuestion newQuestion = createEnvironmentWithFormals(question,
            node);
        PExp body = node.getBody();
        body.apply(parentChecker, newQuestion);
        if (body.getType() == null)
          issueHandler.addTypeError(body,
              TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node
                  .getName().name));
        
        // Check funcType <: bodyType in question
        AFunctionType funcType = node.getType();
        if (!typeComparator.isSubType(funcType, body.getType()))
          issueHandler.addTypeError(body,
              TypeErrorMessages.EXPECTED_SUBTYPE_RELATION.customizeMessage(
                  funcType.toString(), body.getType().toString()));
        
        // Nonetheless the function type will be the type its definition to
        // facilitate further type checking even in the presents of errors.
        return funcType;
      }
    
  }
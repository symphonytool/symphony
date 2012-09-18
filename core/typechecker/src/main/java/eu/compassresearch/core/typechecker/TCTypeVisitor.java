package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.types.ABooleanBasicType;
import eu.compassresearch.ast.types.ACharBasicType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AIntNumericBasicType;
import eu.compassresearch.ast.types.ANamedInvariantType;
import eu.compassresearch.ast.types.ANatNumericBasicType;
import eu.compassresearch.ast.types.ANatOneNumericBasicType;
import eu.compassresearch.ast.types.ARationalNumericBasicType;
import eu.compassresearch.ast.types.ARealNumericBasicType;
import eu.compassresearch.ast.types.ASeqSeqType;
import eu.compassresearch.ast.types.ATokenBasicType;
import eu.compassresearch.ast.types.PType;

@SuppressWarnings({ "deprecation", "serial" })
public class TCTypeVisitor extends
    QuestionAnswerAdaptor<TypeCheckQuestion, PType>
  {
    
    private final VanillaCmlTypeChecker parentChecker;
    
    public TCTypeVisitor(VanillaCmlTypeChecker vanillaCmlTypeChecker)
      {
        this.parentChecker = vanillaCmlTypeChecker;
      }
    
    @Override
    public PType caseABooleanBasicType(ABooleanBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseACharBasicType(ACharBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseATokenBasicType(ATokenBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseAIntNumericBasicType(AIntNumericBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseANatOneNumericBasicType(ANatOneNumericBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseANatNumericBasicType(ANatNumericBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseARationalNumericBasicType(ARationalNumericBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseARealNumericBasicType(ARealNumericBasicType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        return node;
      }
    
    @Override
    public PType caseANamedInvariantType(ANamedInvariantType node,
        TypeCheckQuestion question) throws AnalysisException
      {
        PType lookedupType = question.lookupType(node.getName());
        if (lookedupType == null)
          {
            parentChecker.addTypeError(node,
                TypeErrorMessages.NAMED_TYPE_UNDEFINED.customizeMessage(node
                    .getName().name));
            lookedupType = new AErrorType(node.getLocation(), true);
          }
        
        return lookedupType;
      }
    
    @Override
    public PType caseASeqSeqType(ASeqSeqType node, TypeCheckQuestion question)
        throws AnalysisException
      {
        PType innerType = node.getSeqof().apply(parentChecker, question);
        if (innerType == null)
          {
            parentChecker.addTypeError(node,
                TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
                    .customizeMessage(node.getSeqof().toString()));
            return new AErrorType();
          }
        return node;
      }
    
  }

package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.pattern.PBindAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternBindAssistantTC;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;

@SuppressWarnings({ "serial", "unused" })
class TCBindVisitor extends QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
implements ICMLQuestionAnswer<TypeCheckInfo, PType> {

	private CmlTypeChecker parent;
	private TypeIssueHandler issueHandler;

	public TCBindVisitor(CmlTypeChecker vanillaCmlTypeChecker, TypeIssueHandler issueHandler) {

		this.parent = vanillaCmlTypeChecker;
		this.issueHandler = issueHandler;
	}

	
	
	
	
	@Override
	public PType caseATuplePattern(ATuplePattern node, TypeCheckInfo question)
			throws AnalysisException {
		PType result = new AUnknownType(node.getLocation(), false);
		
		List<PDefinition> definitions = new LinkedList<PDefinition>();
		LinkedList<PPattern> plist = node.getPlist();
		for (PPattern p : plist)
		{
			PType ptype = p.apply(parent,question);
			if (!TCDeclAndDefVisitor.successfulType(ptype))
				return issueHandler.addTypeError(p, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(p+""));
			definitions.addAll(ptype.getDefinitions());
		}
		result.setDefinitions(definitions);
		return result;
	}





	@Override
	public PType caseAIdentifierPattern(AIdentifierPattern node,
			TypeCheckInfo question) throws AnalysisException {

		// This is a bit wierd. But we are simply adding a binding to an unresolved type
		// which some one further up the tree needs to fix as that information is not available here
		// Support "for a in set {1,2,3}" the "for"-expression will tell us that a has type nat1 
		// from the set expression "{1,2,3}" .
		
		PType result = AstFactory.newAUnresolvedType(node.getName());
		ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), node.getName(), question.scope, result);
		result.getDefinitions().add(localDef);
	
		return result;
	}



	@Override
	public PType caseADefPatternBind(ADefPatternBind node,
			TypeCheckInfo question) throws AnalysisException {


		// this is a bit unusual, the type is expected to be already set
		PType topType = node.getType();
		if (topType == null)
		{
			node.setType(issueHandler.addTypeError(node,TypeErrorMessages.PATTERN_TYPE_MISSING.customizeMessage(node+"")));
			return node.getType();
		}

		// TODO RWL: Maybe this should go recursively too, Okay we build the definition list
		PPattern pattern = node.getPattern();
		if (pattern != null)
		{
			if (pattern instanceof AIdentifierPattern)
			{
				ALocalDefinition localDef = AstFactory.newALocalDefinition(pattern.getLocation(), ((AIdentifierPattern) pattern).getName(), NameScope.LOCAL, topType);
				node.getDefs().add(localDef);
			}
			else
			{
				issueHandler.addTypeWarning(pattern, "Type checking may be incomplete here.");
			}
		}
		
		// TODO RWL: maybe, just maybe this should also go recursively !
		PBind bind = node.getBind();
		if (bind != null)
		{
			if (bind instanceof ATypeBind)
			{
				ATypeBind typeBind = (ATypeBind)bind;
				PPattern innerPattern = typeBind.getPattern();
				PType type = typeBind.getType();
				if (innerPattern instanceof AIdentifierPattern)
				{
					AIdentifierPattern idPtern = (AIdentifierPattern)innerPattern;
					ALocalDefinition localDef = AstFactory.newALocalDefinition(innerPattern.getLocation(), idPtern.getName(), NameScope.LOCAL, topType);
					node.getDefs().add(localDef);
				}
				else
					issueHandler.addTypeWarning(innerPattern, "Expecting an identifer pattern, type checking may be incomplete here.");
			}
			else
				issueHandler.addTypeWarning(bind, TypeWarningMessages.EXPECTED_AN_IDENTIFIER_PATTERN.customizeMessage(""+bind,""+bind.kindPBind()));
		}
		
		
		// return the type.
		return node.getType();	
	}





	@Override
	public PType caseARecordPattern(ARecordPattern node, TypeCheckInfo question)
			throws AnalysisException {
	
		LinkedList<PPattern> patterns = node.getPlist();
		LexNameToken typeName = node.getTypename();
		
		NameScope scope = question.scope == null ? NameScope.LOCAL : question.scope;
		PDefinition definition = question.env.findType(typeName, "");
		if (definition == null) {
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(typeName+"")));
			return node.getType();
		}
		
		if (!(definition instanceof ATypeDefinition)) {
			node.setType(issueHandler.addTypeError(node, "Expected a record type."));
			return node.getType();
		}
		
		if (!(definition.getType() instanceof ARecordInvariantType)) {
			node.setType(issueHandler.addTypeError(definition, TypeErrorMessages.EXPECTED_TYPE_DEFINITION.customizeMessage(""+new ARecordInvariantType(definition.getLocation(), true, definition.getName()))));
			return node.getType();
			
		}
		
		ARecordInvariantType recordType = (ARecordInvariantType)definition.getType();
		LinkedList<AFieldField> fields = recordType.getFields();


		if (fields.size() != patterns.size()) {
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS.customizeMessage(""+fields.size(), patterns.size()+"")));
			return node.getType();
		}

		PType resultType = definition.getType().clone();
		int fieldCount = 0;		
		for(PPattern p : patterns) {
			PType pType = p.apply(parent, question);
			
			if (!TCDeclAndDefVisitor.successfulType(pType)) {
				node.setType(issueHandler.addTypeError(p, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(p+"")));
				return node.getType();
			}
			
			// Safe as we have ensured the same number of patterns and fields
			AFieldField field = fields.get(fieldCount);
			for(PDefinition d : pType.getDefinitions()) {
				d.setType(field.getType());
				resultType.getDefinitions().add(d);
			}
			fieldCount++;
		}

		node.setType(resultType);
		return node.getType();
	}

	
	


}

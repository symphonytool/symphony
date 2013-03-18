package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExternalDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SInvariantType;
import org.overture.typechecker.LexNameTokenAssistent;
import org.overture.typechecker.assistant.definition.AValueDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.assistant.type.ARecordInvariantTypeAssistantTC;
import org.overture.typechecker.util.HelpLexNameToken;
import org.xml.sax.ext.LexicalHandler;

import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;


/**
 * The CmlAssistant is for methods and utility-functionality for assisting the Cml Type Checker visitors. 
 * 
 * 
 * 
 * @author rwl
 *
 */
class CmlAssistant {

	/**
	 * Find members inside a given definition.
	 * 
	 * Ex. find member totalSeats in Flight:
	 * 
	 * types
	 * 	Flight :: 
	 * 		totalSeats : int
	 * 		id : token
	 * 		;
	 * 
	 * 
	 * values
	 * 	k : Flight := mk_Flight(42,mk_token("Flight with 42 seats"))
	 * 
	 * functions
	 * 	getTotalSeats: Flight -> int
	 * 	getTotalSeats(Flight f) == f.totalSeats; <- this expression will be 
	 * resolved by the FindMemberNameFinderStrategy for ATypeDefinition and inturn 
	 * handled as a ARecordInvariantType for which we craete a LocalDefinition named 
	 * totalSeats of type int.
	 * 
	 * 
	 * @author rwl
	 *
	 */
	interface FindMemberNameFinderStrategy {
		/**
		 * Type Erasure forces us to maintain type information explicitly.
		 * @return
		 */
		Class<?> getType();
		/**
		 * 
		 * Search for a member with name <em>name</em> the definition <em>def</em>.
		 * more contains cmlQuestion and prevRoot typically... 
		 * 
		 * @param def
		 * @param name
		 * @param more
		 * @return
		 */
		PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more);
	}


	private void t() {
		AImplicitFunctionDefinition f;
		
	}
	/*
	 * Type erasure? Well then we gotta do it our self ... 
	 * 
	 * Each supported kind of ast node that we can lookup with findMemberName method 
	 * should be added here.
	 * 
	 */
	private final Map<Class<?>, FindMemberNameFinderStrategy> findMemberNameBaseCases = new HashMap<Class<?>, FindMemberNameFinderStrategy>();
	public CmlAssistant() {
		injectFindMemberNameBaseCase(new ValuePararagraphDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new ClassParagraphFindMemberStrategy());
		injectFindMemberNameBaseCase(new ClassClassDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new LocalDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new TypeDefinitionFindNameMemberStrategy());
		injectFindMemberNameBaseCase(new OperationsDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new ExternalDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new AssignmentDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new AStateDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new AFunctionsDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new AProcessDefinitionNameMemberStrategy());
		injectFindMemberNameBaseCase(new AActionsDefinitionNameMemberStrategy());
		
	}


	/**
	 * Inject a new strategy for finding named members of Ast subtrees. 
	 * 
	 * @param strategy
	 */
	void injectFindMemberNameBaseCase(FindMemberNameFinderStrategy strategy)
	{
		findMemberNameBaseCases.put(strategy.getType(), strategy);
	}

	/**
	 * 
	 * Look up the first definition in t with name as its name.
	 * 
	 * @param t - definition to look inside.
	 * @param name - name to search for.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public<T extends PDefinition> PDefinition findMemberName(T t, LexIdentifierToken name, Object... more)
	{
		if (t == null) return null;
		Class<T> c = (Class<T>)t.getClass();
		if (!findMemberNameBaseCases.containsKey(c))
		{
			return t;
		}
		FindMemberNameFinderStrategy strategy = findMemberNameBaseCases.get(c);

		return strategy.findMemberName(t, name, more);

	}

	class AActionsDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AActionsDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {
			
			AActionsDefinition acts = (AActionsDefinition)def;
			for(PDefinition actDef : acts.getActions()) {
				if(HelpLexNameToken.isEqual(actDef.getName(), name)) {
					return actDef;
				}
			}
			
			return null;
		}
		
	}
	
	class AProcessDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AProcessDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AProcessDefinition pdef = AProcessDefinition.class.cast(def);
			
			PProcess process = pdef.getProcess();
			if (process instanceof AActionProcess) {
				AActionProcess aProcess = (AActionProcess)process;
				for(PDefinition p : aProcess.getDefinitionParagraphs()) {
					PDefinition res = CmlAssistant.this.findMemberName(p,name,more);
					if (res != null) return res;
				}
			}
			
			
			return null;
		}

	}

	class AFunctionsDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AFunctionsDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AFunctionsDefinition fns = (AFunctionsDefinition)def;
			LinkedList<PDefinition> fndefs = fns.getFunctionDefinitions();

			for(PDefinition fdef : fndefs) {
				PDefinition predef = null;
				PDefinition postdef = null;
				if (fdef instanceof AExplicitFunctionDefinition) {
					AExplicitFunctionDefinition efd = (AExplicitFunctionDefinition)fdef;
					predef = efd.getPredef();
					postdef = efd.getPostdef();
				}

				if (fdef instanceof AImplicitFunctionDefinition) {
					AImplicitFunctionDefinition efd = (AImplicitFunctionDefinition)fdef;
					predef = efd.getPredef();
					postdef = efd.getPostdef();
				}

				if (HelpLexNameToken.isEqual(fdef.getName(), name)) return fdef;
				if (predef != null && predef.getName().getName().equals(name.getName())) return predef;
				if (postdef != null && HelpLexNameToken.isEqual(postdef.getName(), name)) return postdef;
			}
			return null;
		}

	}

	class AssignmentDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AAssignmentDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AAssignmentDefinition assignDef = (AAssignmentDefinition)def;
			PType type = assignDef.getType();
			
			if (type instanceof AClassType) {
					return CmlAssistant.this.findMemberName(((AClassType) type).getClassdef(), name, more);
			}
			
			if (type.getDefinitions().size() > 0)
			{
				PDefinition def0 = type.getDefinitions().get(0);
				if (def0 instanceof ATypeDefinition)
				{
					ATypeDefinition tDef = (ATypeDefinition)def0;
					return CmlAssistant.this.findMemberName(tDef, name, more);

				}

				if (def0 instanceof AOperationsDefinition) {
					return CmlAssistant.this.findMemberName(def0, name, more);
				}
			}

			if (type instanceof ANamedInvariantType){
				return handleNamedInvariantType((ANamedInvariantType)type, name, more);
			}

			if (type instanceof ARecordInvariantType) {
				return handleRecordInvariantType((ARecordInvariantType)type, name, more);
			}


			return def;
		}

	}

	class AStateDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AStateDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AStateDefinition stateDef = (AStateDefinition)def;

			for(PDefinition d : stateDef.getStateDefs()) {
				if (d.getName() != null && LexNameTokenAssistent.isEqual(d.getName(), name)) return d;
			}

			return null;
		}

	}

	class ExternalDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy {

		@Override
		public Class<?> getType() {
			return AExternalDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AExternalDefinition extDef = (AExternalDefinition)def;


			return CmlAssistant.this.findMemberName(extDef.getState(), name, more);
		}

	}

	/*
	 * Find a named member of a class paragraph.
	 * 
	 * @author rwl
	 *
	 */
	private class ClassParagraphFindMemberStrategy implements FindMemberNameFinderStrategy{
		/*
		 * 
		 * Given a class paragraph definition and a name to look for, see if that name is defined inside this class.
		 * 
		 * @param def
		 * @param name
		 * @return
		 */
		@Override
		public PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more)
		{
			if (def.getName() != null && LexNameTokenAssistent.isEqual(def.getName(), name))
				return def;

			AClassDefinition cpar = AClassDefinition.class.cast(def); 

			// pre: def.definition is not null
			for(PDefinition d : cpar.getBody())
			{
				// invariant: all elements before d is not the one we are looking for
				PDefinition member = CmlAssistant.this.findMemberName(d,name);
				if (member != null)
					return member;
			}
			// post: we did not find the element

			return null;
		}

		@Override
		public Class<?> getType() {
			return AClassDefinition.class;
		}
	}


	/*
	 * 
	 * Find a named member of a value definition paragraph.
	 * 
	 * @author rwl
	 * 
	 */
	private static class ValuePararagraphDefinitionFindMemberStrategy implements FindMemberNameFinderStrategy
	{
		/*
		 * 
		 * Search all ValueDefinition in def and return the first definition with the same name as name.
		 * 
		 * @param def
		 * @param name
		 * @return
		 */
		@Override
		public PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more)
		{
			AValuesDefinition vdef = (AValuesDefinition)def;
			for(PDefinition d : vdef.getValueDefinitions())
			{
				if (d instanceof AValueDefinition)
				{
					AValueDefinition valueDef = (AValueDefinition)d;
					for (PDefinition ldef : valueDef.getDefs())
						if (ldef.getName() != null && LexNameTokenAssistent.isEqual(ldef.getName(), name)) return ldef;
				}
			}
			return null;
		}

		@Override
		public Class<?> getType() {
			return AValuesDefinition.class;
		}
	}



	private class OperationsDefinitionNameMemberStrategy implements FindMemberNameFinderStrategy
	{

		@Override
		public Class<?> getType() {
			return AOperationsDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AOperationsDefinition oddef = (AOperationsDefinition)def;

			for(PDefinition odef : oddef.getOperations())
			{
				LexNameToken name2 = odef.getName();
				if (name2 != null)
					if (name2.equals(name)) return odef;
			}
			return null;
		}

	}

	/*
	 * Find a named member inside a class class definition (Overture class definition).
	 * 
	 * @author rwl
	 */
	private class ClassClassDefinitionFindMemberStrategy implements FindMemberNameFinderStrategy
	{

		@Override
		public Class<?> getType() {
			return AClassClassDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			CmlTypeCheckInfo cmlQuestion = (CmlTypeCheckInfo)more[0];

			LexNameToken searchFor = new LexNameToken("",def.getName());

			PDefinition classDef = cmlQuestion.lookup(searchFor, AClassDefinition.class);
			if (classDef != null)
				return CmlAssistant.this.findMemberName(classDef, name, more);

			
			AClassClassDefinition cdef = AClassClassDefinition.class.cast(def);
			
			AFunctionsDefinition temp = new AFunctionsDefinition(def.getLocation(), NameScope.LOCAL, false, null, Pass.DEFS);
			temp.setFunctionDefinitions(cdef.getDefinitions());
			PDefinition res = CmlAssistant.this.findMemberName(temp, name, more);
			if (res != null) return res;
			return SClassDefinitionAssistantTC.findName(cdef, (LexNameToken)name, NameScope.NAMESANDANYSTATE);

		}
	}

	// *********** Helper methods for LocalDefinitionFindMemberStrategy ************

	// Looking for a field in a record, alright look up the Type def of the record
	// and find the field. Return a local definition for that field.
	private PDefinition handleRecordInvariantType(ARecordInvariantType recordInvType, LexIdentifierToken name, Object... more)
	{
		AFieldField field = ARecordInvariantTypeAssistantTC.findField(recordInvType, name.getName());
		PDefinition defOfTheTypeOfThisLocalDef = null;
		if (field != null){
			return AstFactory.newALocalDefinition(recordInvType.getLocation(), field.getTagname(), NameScope.LOCAL, field.getType());
		}
		return CmlAssistant.this.findMemberName(defOfTheTypeOfThisLocalDef,name,more);		
	}

	// Lookup the named type in the environment, if found we are happy
	private PDefinition handleNamedInvariantType(ANamedInvariantType namedInvType, LexIdentifierToken name, Object... more)
	{
		CmlTypeCheckInfo cmlEnv = (CmlTypeCheckInfo)more[0];
		PDefinition defOfTheTypeOfThisLocalDef = cmlEnv.env.findType(namedInvType.getName(),"");
		while(defOfTheTypeOfThisLocalDef != null && 
				defOfTheTypeOfThisLocalDef.getType() != null && 
				defOfTheTypeOfThisLocalDef.getType() instanceof ANamedInvariantType)
		{

			ANamedInvariantType nameType = (ANamedInvariantType)defOfTheTypeOfThisLocalDef.getType();
			PType typeType = nameType.getType();
			if (typeType instanceof ANamedInvariantType)
				defOfTheTypeOfThisLocalDef = cmlEnv.env.findType(((ANamedInvariantType) typeType).getName(), "");
			else 
			{
				ALocalDefinition resolvedType = AstFactory.newALocalDefinition(defOfTheTypeOfThisLocalDef.getLocation(), defOfTheTypeOfThisLocalDef.getName(), NameScope.LOCAL, typeType);
				return CmlAssistant.this.findMemberName(resolvedType, name, more);
			}
		}


		return CmlAssistant.this.findMemberName(defOfTheTypeOfThisLocalDef,name,more);
	}

	private class LocalDefinitionFindMemberStrategy implements FindMemberNameFinderStrategy
	{

		@Override
		public Class<?> getType() {
			return ALocalDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			ALocalDefinition ldef = (ALocalDefinition)def;


			// So what if it is a record type
			if (def.getType() instanceof ARecordInvariantType)
			{
				ARecordInvariantType recType = ARecordInvariantType.class.cast(def.getType());
				return handleRecordInvariantType(recType, name, more);
			}

			// It could be a named type
			if (ldef.getType() instanceof ANamedInvariantType)
				return handleNamedInvariantType(ANamedInvariantType.class.cast(ldef.getType()), name, more);

			return def;
		}

	}

	// *********** -- ************

	/*
	 * RecordInvariantTypes can have members. This class handles lookup of members 
	 * in records.
	 * 
	 */
	private class TypeDefinitionFindNameMemberStrategy implements FindMemberNameFinderStrategy
	{

		@Override
		public Class<?> getType() {
			return ATypeDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			ATypeDefinition tdef = ATypeDefinition.class.cast(def);
			SInvariantType invType = tdef.getInvType();

			if (invType instanceof ARecordInvariantType)
				return handleRecordInvariantType(ARecordInvariantType.class.cast(invType), name, more);

			if (invType instanceof ANamedInvariantType)
				return handleNamedInvariantType(ANamedInvariantType.class.cast(invType), name, more);

			return null;

		}
	}


}

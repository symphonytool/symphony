package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.SInvariantType;
import org.overture.typechecker.LexNameTokenAssistent;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.assistant.type.ARecordInvariantTypeAssistantTC;

import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;

class CmlOvertureAssistant {

	interface FindMemberNameFinderStrategy {
		Class<?> getType();
		PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more);
	}

	
	
	/*
	 * Type erasure? Well then we gotta do it our self ... 
	 * 
	 * Each supported kind of ast node that we can lookup with findMemberName method 
	 * should be added here.
	 * 
	 */
	private final Map<Class<?>, FindMemberNameFinderStrategy> findMemberNameBaseCases = new HashMap<Class<?>, FindMemberNameFinderStrategy>();
	public CmlOvertureAssistant() {
		injectFindMemberNameBaseCase(new ValuePararagraphDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new OperationParagraphDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new ClassParagraphFindMemberStrategy());
		injectFindMemberNameBaseCase(new StateParagarphDefinitionFindMemberStragety());
		injectFindMemberNameBaseCase(new ClassClassDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new LocalDefinitionFindMemberStrategy());
		injectFindMemberNameBaseCase(new TypeDefinitionFindNameMemberStrategy());
	}


	public void injectFindMemberNameBaseCase(FindMemberNameFinderStrategy strategy)
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
		Class<T> c = (Class<T>)t.getClass();
		if (!findMemberNameBaseCases.containsKey(c)) return null;

		FindMemberNameFinderStrategy strategy = findMemberNameBaseCases.get(c);
		
		return strategy.findMemberName(t, name, more);

	}



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
			if (LexNameTokenAssistent.isEqual(def.getName(), name))
				return def;

			AClassParagraphDefinition cpar = AClassParagraphDefinition.class.cast(def); 

			// pre: def.definition is not null
			for(PDefinition d : cpar.getDefinitions())
			{
				// invariant: all elements before d is not the one we are looking for
				PDefinition member = CmlOvertureAssistant.this.findMemberName(d,name);
				if (member != null)
					return member;
			}
			// post: we did not find the element

			return null;
		}

		@Override
		public Class<?> getType() {
			return AClassParagraphDefinition.class;
		}
	}


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
			AValueParagraphDefinition vdef = (AValueParagraphDefinition)def;
			for(PDefinition d : vdef.getValueDefinitions())
			{
				if (LexNameTokenAssistent.isEqual(d.getName(), name))
					return d;
			}
			return null;
		}

		@Override
		public Class<?> getType() {
			return AValueParagraphDefinition.class;
		}
	}

	private static class OperationParagraphDefinitionFindMemberStrategy implements FindMemberNameFinderStrategy
	{

		/**
		 * 
		 * Search all Operation Definition and return the first definition with the same name as name.
		 * 
		 * @param def
		 * @param name
		 * @return
		 */
		public PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more)
		{
			AOperationParagraphDefinition odef = (AOperationParagraphDefinition)def;
			for(SOperationDefinition d : odef.getOperations())
			{
				if (LexNameTokenAssistent.isEqual(d.getName(), name))
					return d;
			}
			return null;
		}

		@Override
		public Class<?> getType() {
			return AOperationParagraphDefinition.class;
		}
	}



	private static class StateParagarphDefinitionFindMemberStragety implements FindMemberNameFinderStrategy
	{
		/**
		 * 
		 * Search all State Definitions and return the first definition with the same name as name.
		 * 
		 * @param def
		 * @param name
		 * @return
		 */
		public PDefinition findMemberName(PDefinition def, LexIdentifierToken name, Object... more)
		{
			AStateParagraphDefinition sdef = (AStateParagraphDefinition)def;
			LinkedList<PDefinition> states = sdef.getStateDefs();
			for(PDefinition d : states)
			{
				if (LexNameTokenAssistent.isEqual(d.getName(), name))
					return d;
			}
			return null;
		}

		@Override
		public Class<?> getType() {
			return AStateParagraphDefinition.class;
		}
	}


	private static class ClassClassDefinitionFindMemberStrategy implements FindMemberNameFinderStrategy
	{

		@Override
		public Class<?> getType() {
			return AClassClassDefinition.class;
		}

		@Override
		public PDefinition findMemberName(PDefinition def,
				LexIdentifierToken name, Object... more) {

			AClassClassDefinition cdef = AClassClassDefinition.class.cast(def);

			return SClassDefinitionAssistantTC.findName(cdef, (LexNameToken)name, NameScope.NAMESANDANYSTATE);

		}

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

			
			CmlTypeChecker parent = null;
			// So what if it is a record type
			if (def.getType() instanceof ARecordInvariantType)
			{
				
				CmlTypeCheckInfo cmlEnv = (CmlTypeCheckInfo)more[0];
				ARecordInvariantType recordType = (ARecordInvariantType)ldef.getType();
				AFieldField field = ARecordInvariantTypeAssistantTC.findField(recordType, name.getName());
				PDefinition defOfTheTypeOfThisLocalDef = null;
				if (field != null){
					return AstFactory.newALocalDefinition(recordType.getLocation(), field.getTagname(), NameScope.LOCAL, field.getType());
				}
				return CmlOvertureAssistant.this.findMemberName(defOfTheTypeOfThisLocalDef,name,more);
			}
			
			if (ldef.getType() instanceof ANamedInvariantType)
			{
				CmlTypeCheckInfo cmlEnv = (CmlTypeCheckInfo)more[0];
				ANamedInvariantType namedType = (ANamedInvariantType)ldef.getType();
				PDefinition defOfTheTypeOfThisLocalDef = cmlEnv.env.findName(namedType.getName(),NameScope.GLOBAL);
				return CmlOvertureAssistant.this.findMemberName(defOfTheTypeOfThisLocalDef,name,more);
			}
			else
			{
				// ups it is a basic type then which we cannot dot into.
			}

			return null;			

		}

	}
	
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
			if (invType != null)
			{
				for(PDefinition idef : invType.getDefinitions())
				{
					PDefinition found = CmlOvertureAssistant.this.findMemberName(idef, name, more);
					if (found != null) return found;
				}
			}
			
			return null;
		}
		
	}
}

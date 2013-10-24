//package eu.compassresearch.core.typechecker.assistant;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.types.PType;
//import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
//
//import eu.compassresearch.ast.definitions.AProcessDefinition;
//import eu.compassresearch.ast.types.AProcessType;
//
//public class CmlDefinitionTypeFinder extends AbstractCmlDefinitionTypeFinder
//{
//
//	public CmlDefinitionTypeFinder(ITypeCheckerAssistantFactory af)
//	{
//		super(af);
//	}
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -7520136998582515658L;
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public PType caseAProcessDefinition(AProcessDefinition node)
//			throws AnalysisException
//	{
//		return new AProcessType(node.getLocation(), true);
//	}
//
//	@Override
//	public PType caseAExplicitCmlOperationDefinition(
//			AExplicitCmlOperationDefinition node) throws AnalysisException
//	{
//		return node.getType();
//	}
//
//	@Override
//	public PType caseAImplicitCmlOperationDefinition(
//			AImplicitCmlOperationDefinition node) throws AnalysisException
//	{
//		return node.getType();
//	}
//
//	@Override
//	public PType caseACmlClassDefinition(ACmlClassDefinition node)
//			throws AnalysisException
//	{
//		return defaultSClassDefinition(node);
//	}
//}

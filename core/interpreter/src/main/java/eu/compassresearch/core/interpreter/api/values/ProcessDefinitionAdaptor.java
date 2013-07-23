package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SClassDefinitionBase;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.ClassDefinitionSettings;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.definitions.AProcessDefinition;

class ProcessDefinitionAdaptor extends SClassDefinitionBase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4779663502289850548L;
	private AProcessDefinition processDefinition;
	private AClassType classType;
	
	public ProcessDefinitionAdaptor(AProcessDefinition processDefinition)
	{
		this.processDefinition = processDefinition;
	}
	
	public AProcessDefinition getProcessDefinition()
	{
		return this.processDefinition;
	}

	@Override
	public String kindNode() {
		return processDefinition.kindNode();
	}

	@Override
	public void setLocation(ILexLocation value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public ILexLocation getLocation() {
		return processDefinition.getLocation();
	}

	@Override
	public void setName(ILexNameToken value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public ILexNameToken getName() {
		return processDefinition.getName();
	}

	@Override
	public void setNameScope(NameScope value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public NameScope getNameScope() {
		return processDefinition.getNameScope();
	}

	@Override
	public void setUsed(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getUsed() {
		return processDefinition.getUsed();
	}

	@Override
	public void setClassDefinition(SClassDefinition value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public SClassDefinition getClassDefinition() {
		return this;
	}

	@Override
	public void setAccess(AAccessSpecifierAccessSpecifier value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public AAccessSpecifierAccessSpecifier getAccess() {
		return processDefinition.getAccess();
	}

	@Override
	public void setType(PType value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public PType getType() {
		return this.processDefinition.getType();
	}

	@Override
	public void setPass(Pass value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public Pass getPass() {
		return processDefinition.getPass();
	}

//	@Override
//	public INode parent() {
//		return this.parent();
//		//return processDefinition.parent();
//	}
//
//	@Override
//	public void parent(INode parent) {
//		//processDefinition.parent(parent);
//		this.parent(parent);
//	}
	
	@Override
	public void apply(IAnalysis analysis) throws AnalysisException {
		processDefinition.apply(analysis);
	}

	@Override
	public <A> A apply(IAnswer<A> caller) throws AnalysisException {
		return processDefinition.apply(caller);
	}

	@Override
	public <Q> void apply(IQuestion<Q> caller, Q question)
			throws AnalysisException {
		processDefinition.apply(caller, question);
	}

	@Override
	public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
			throws AnalysisException {
		return processDefinition.apply(caller, question);
	}

	@Override
	public String kindPDefinition() {
		return processDefinition.kindPDefinition();
	}

	@Override
	public SClassDefinition clone() {
		return new ProcessDefinitionAdaptor(processDefinition.clone());
	}
	
	@Override
	public SClassDefinition clone(Map<INode, INode> oldToNewMap) {
		return new ProcessDefinitionAdaptor(processDefinition.clone(oldToNewMap));
	}

	@Override
	public Map<String, Object> getChildren(Boolean includeInheritedFields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kindSClassDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSupertypes(List<? extends PType> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public LinkedList<PType> getSupertypes() {
		return new LinkedList<PType>();
	}

	@Override
	public void setSupernames(List<? extends ILexNameToken> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
		
	}

	@Override
	public LinkedList<ILexNameToken> getSupernames() {
		return new LinkedList<ILexNameToken>();
	}

	@Override
	public void setDefinitions(List<? extends PDefinition> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public LinkedList<PDefinition> getDefinitions() {
		// TODO Auto-generated method stub
		return new LinkedList<PDefinition>();
	}

	@Override
	public void setAllInheritedDefinitions(List<? extends PDefinition> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public LinkedList<PDefinition> getAllInheritedDefinitions() {
		return new LinkedList<PDefinition>();
	}

	@Override
	public void setLocalInheritedDefinitions(List<? extends PDefinition> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public LinkedList<PDefinition> getLocalInheritedDefinitions() {
		return new LinkedList<PDefinition>();
	}

	@Override
	public void setHasContructors(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getHasContructors() {
		return false;
	}

	@Override
	public void setSettingHierarchy(ClassDefinitionSettings value) {

		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public ClassDefinitionSettings getSettingHierarchy() {
		return null;
	}

	@Override
	public void setSuperDefs(List<? extends SClassDefinition> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public LinkedList<SClassDefinition> getSuperDefs() {
		return new LinkedList<SClassDefinition>();
	}

	@Override
	public void setGettingInheritable(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getGettingInheritable() {
		return false;
	}

	@Override
	public void setSuperInheritedDefinitions(List<? extends PDefinition> value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public LinkedList<PDefinition> getSuperInheritedDefinitions() {
		return new LinkedList<PDefinition>();
	}

	@Override
	public void setGettingInvDefs(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getGettingInvDefs() {
		return false;
	}

	@Override
	public void setIsAbstract(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getIsAbstract() {
		return false;
	}

	@Override
	public void setIsUndefined(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getIsUndefined() {
		return false;
	}

	@Override
	public void setClasstype(PType value) {
		classType = (AClassType)value;		
	}

	@Override
	public PType getClasstype() {
		return classType;
	}

	@Override
	public void setTypeChecked(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getTypeChecked() {
		return true;
	}

	@Override
	public void setInvariant(AExplicitOperationDefinition value) {

		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

		

}

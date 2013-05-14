package eu.compassresearch.core.interpreter.values;

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
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.ClassDefinitionSettings;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.definitions.AClassDefinition;

class CmlClassDefinitionAdaptor extends SClassDefinitionBase {

	private AClassDefinition cmlClassDefinition;
	private AClassType classType;
	
	public CmlClassDefinitionAdaptor(AClassDefinition cmlClassDefinition)
	{
		this.cmlClassDefinition = cmlClassDefinition;
	}
	
	public AClassDefinition getCmlClassDefinition()
	{
		return this.cmlClassDefinition;
	}

	@Override
	public String kindNode() {
		return cmlClassDefinition.kindNode();
	}

	@Override
	public void setLocation(ILexLocation value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public ILexLocation getLocation() {
		return cmlClassDefinition.getLocation();
	}

	@Override
	public void setName(ILexNameToken value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public ILexNameToken getName() {
		return cmlClassDefinition.getName();
	}

	@Override
	public void setNameScope(NameScope value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public NameScope getNameScope() {
		return cmlClassDefinition.getNameScope();
	}

	@Override
	public void setUsed(Boolean value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public Boolean getUsed() {
		return cmlClassDefinition.getUsed();
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
		return cmlClassDefinition.getAccess();
	}

	@Override
	public void setType(PType value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");		
	}

	@Override
	public PType getType() {
		return this.cmlClassDefinition.getType();
	}

	@Override
	public void setPass(Pass value) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}

	@Override
	public Pass getPass() {
		return cmlClassDefinition.getPass();
	}

	@Override
	public INode parent() {
		
		return cmlClassDefinition.parent();
	}

	@Override
	public void parent(INode parent) {
		throw new RuntimeException("The Adaptors set methods should never be touched");
	}
	
	@Override
	public void apply(IAnalysis analysis) throws AnalysisException {
		cmlClassDefinition.apply(analysis);
	}

	@Override
	public <A> A apply(IAnswer<A> caller) throws AnalysisException {
		return cmlClassDefinition.apply(caller);
	}

	@Override
	public <Q> void apply(IQuestion<Q> caller, Q question)
			throws AnalysisException {
		cmlClassDefinition.apply(caller, question);
	}

	@Override
	public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
			throws AnalysisException {
		return cmlClassDefinition.apply(caller, question);
	}

	@Override
	public String kindPDefinition() {
		return cmlClassDefinition.kindPDefinition();
	}

	@Override
	public SClassDefinition clone() {
		return new CmlClassDefinitionAdaptor(cmlClassDefinition.clone());
	}
	
	@Override
	public SClassDefinition clone(Map<INode, INode> oldToNewMap) {
		return new CmlClassDefinitionAdaptor(cmlClassDefinition.clone(oldToNewMap));
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
		return  new LinkedList<PDefinition>();
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
		return cmlClassDefinition.getHasContructors();
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
		
		return (LinkedList)this.cmlClassDefinition.getSuperDefs();
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
	
//	@Override
//	public AExplicitOperationDefinition getInvariant() {
//
//		return this.cmlClassDefinition.getInvariant();
//	}

}

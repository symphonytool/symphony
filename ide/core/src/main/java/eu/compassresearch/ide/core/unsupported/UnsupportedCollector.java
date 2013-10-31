package eu.compassresearch.ide.core.unsupported;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;

/**
 * This class traverses an entire model and collects information on elements
 * that are not supported by a particular analysis or feature of CML.
 * 
 * <b>Warning:</b> The tree traversal is handled by this class to any subclasses
 * must invoke the super in their cases.
 * @author ldc
 *
 */
public  class UnsupportedCollector extends DepthFirstAnalysisCMLAdaptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	List<UnsupportedElementInfo> unsupporteds = new LinkedList<UnsupportedElementInfo>();
	UnsupportingFeatures feature;
	boolean unsupported;
	
	
	
	public UnsupportedCollector(UnsupportingFeatures feature)
	{
		super();
		this.unsupported=true;
		this.feature = feature;
	}



	

	
	public List<UnsupportedElementInfo> getUnsupporteds()
	{
		return unsupporteds;
	}





	public void setUnsupporteds(List<UnsupportedElementInfo> unsupporteds)
	{
		this.unsupporteds = unsupporteds;
	}





	public UnsupportingFeatures getFeature()
	{
		return feature;
	}





	public void setFeature(UnsupportingFeatures feature)
	{
		this.feature = feature;
	}


	public List<UnsupportedElementInfo> getUnsupporteds(List<INode> ast) throws AnalysisException{
		for (INode node : ast){
			node.apply(this);
		}
		return unsupporteds;
	}

	@Override
	public void defaultOutPExp(PExp node) throws AnalysisException
	{
		
	 if(unsupported){
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any expression");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()+" expressions are not supported by the " + feature.toString());
			unsupporteds.add(uei);
	 }
	 else {
		 unsupported=true;
	 }
	 
	 
	}





	@Override
	public void defaultInPDefinition(PDefinition node) throws AnalysisException
	{
		try{
		super.defaultInPDefinition(node);
		}
		catch (AnalysisException e){
			
		}
	}






	@Override
	public void caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node)
			throws AnalysisException
	{
		unsupported=false;
		// Do not touch the line below!
		super.caseAAndBooleanBinaryExp(node);
	}
	
	
	

	
	
	
	
}

package eu.compassresearch.theoremprover.visitors;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AStringLiteralExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;

@SuppressWarnings("serial")
public class TPVisitor extends
	AnswerCMLAdaptor<ThmNodeList> {

	private final static String ANALYSIS_NAME = "Theorem Prover";

	private List<PSource> sources;

	private ThmPExpVisitor expVisitor;
	private ThmPTypeVisitor typeVisitor;
	
//	private ThmStmtVisitor stmtVisitor;
//	private ThmProcVisitor procVisitor;
	private ThmDeclAndDefVisitor declAndDefVisitor;
//	private ThmActVisitor actVisitor;

	private void initialize()
	{
		expVisitor = new ThmPExpVisitor(this);
		typeVisitor = new ThmPTypeVisitor(this);
//		statementVisitor = new ThmStmtVisitor(this);
//		processVisitor = new ThmProcVisitor(this);
		declAndDefVisitor = new ThmDeclAndDefVisitor(this);
//		actionVisitor = new ThmActVisitor(this);
	}
	
	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException {		
		return node.apply(this.typeVisitor);
	}


	@Override
	public ThmNodeList defaultPExp(PExp node)
			throws AnalysisException {
		return node.apply(this.expVisitor);
	}
	

	@Override
	public ThmNodeList defaultPDefinition(PDefinition node) 
			throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor);
	}

	@Override
	public ThmNodeList defaultPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor);
	}
	
	/**
	 * This method is invoked by the command line tool when pretty printing the
	 * analysis name.
	 * 
	 * @return Pretty short name for this analysis.
	 */
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	/**
	 * Construct a ThmVisitor with the intension of checking a
	 * list of PSources. These source may refer to each other.
	 * 
	 * @param cmlSources
	 *        - Sources containing CML Paragraphs for thy gen.
	 */
	public TPVisitor(List<PSource> cmlSources) {
		initialize();
		this.sources = cmlSources;
	}

	/**
	 * Construct a ThmVisitor with the intension of checking a
	 * single source.
	 * 
	 * @param singleSource
	 *            - Source containing CML Paragraphs for thy gen.
	 */
	public TPVisitor(PSource singleSource) {
		initialize();
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}
	
	/**
	 * Construct a ThmVisitor with no source indicated
	 */
	public TPVisitor() {
		initialize();
	}


	/********************
	 * Method to generate string for a theory file from the provided source
	 ********************/
	public String generateThyString() throws IOException, AnalysisException {
		ThmNodeList nodes = new ThmNodeList();
	
		
		for (PSource source : sources) {
			for (PDefinition paragraph : source.getParagraphs()) {
				try {
					paragraph.apply(this);
				} catch (Exception e) {
					System.out.println("The COMPASS Theorem Prover could not generate \n" 		
						+ "a theory file for this cml-source: \n" + e.getMessage());
				}
			}			
		}
		
	
		//Sort nodes into dependency-order
		nodes = sortThmNodes(nodes);

		StringBuilder sb = new StringBuilder();

		//TODO: Obtain model/project/filename
		String name = "TempName";

		//TODO: May need to obtain thy name by trimming cml filename...
//		String thyName = name.substring(0, name.lastIndexOf("."));
		String thyName = name;
		
		//Add thy header 
		sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file f "+  thyName + ".cml *}\n\n");
		
		//Add generated node strings
		sb.append(nodes.toString());
			
		sb.append("\n" + "end");
		
		return sb.toString();
		
		
		//for (ThmValue tv : tpv.getValueList())
		//{
		//	sb.append(tv.toString());
		//	sb.append("\n");
		//}
		//
		//sb.append("\n");
		//sb.append("text {* VDM type declarations *}\n\n");
		//
		//for (ThmType ty : tpv.getTypeList())
		//{
		//	sb.append(ty.toString());
		//	sb.append("\n");
		//}
		//
		//sb.append("\n");
		//
	}


	/********************
	 * Method to sort a list of nodes into dependent-order
	 ********************/
	public static ThmNodeList sortThmNodes(ThmNodeList tpnodes){
   
		ThmNodeList sortedNodes = new ThmNodeList();
		ThmNode tempNode = null;
   
		while (! tpnodes.isEmpty()){
			for (Iterator<ThmNode> itr = tpnodes.listIterator(); itr.hasNext(); ) {
				tempNode = itr.next();
				if(tempNode.getnumDep() == 0 ||
				   sortedNodes.allDepsFulfilled(tempNode.getDepIds())){
					//Add to returned list.
					sortedNodes.add(tempNode);
					//Removes from original list passed to method.
					itr.remove(); 
				}
				if( tpnodes.isEmpty()){
					break;
				}
			}
		}
	
		return sortedNodes;
	}
	
	public static String generateThyStr(List<INode> ast, String thyFileName) 
			throws AnalysisException {
		

		ThmNodeList nodes = new ThmNodeList();
		for (INode node : ast) {
			try {
				nodes.addAll(node.apply(new TPVisitor()));
			}catch (Exception e) {
					System.out.println("The COMPASS Theorem Prover could not generate \n" 		
						+ "a theory file for this cml-source: \n" + e.getMessage());
				}
		}
		
		String thyName = thyFileName.substring(0, thyFileName.lastIndexOf('.'));
	
		//Sort nodes into dependency-order
		nodes = sortThmNodes(nodes);

		StringBuilder sb = new StringBuilder();
		
		//Add thy header 
		sb.append("theory " + thyName + " \n" + "  imports utp_cml \n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for "+  thyName + ".cml *}\n\n");
		
		//Add generated node strings
		sb.append(nodes.toString());
			
		sb.append("\n" + "end");
		
		return sb.toString();

	}

	
}
	
//	
//	List<ThmDecl>  declList = new LinkedList<ThmDecl>();
//	List<ThmType>  typeList = new LinkedList<ThmType>();
//    List<ThmValue> valueList = new LinkedList<ThmValue>();
//	
//	public List<ThmValue> getValueList() {
//		return valueList;
//	}
//
//
//
//	public void setValueList(List<ThmValue> valueList) {
//		this.valueList = valueList;
//	}
//
//
//
//	public List<ThmType> getTypeList() {
//		return typeList;
//	}
//
//
//
//	public void setTypeList(List<ThmType> typeList) {
//		this.typeList = typeList;
//	}
//
//
//
//	@Override
//	public void caseATypeDefinition(ATypeDefinition node)
//			throws AnalysisException {		
//		
//		ThmPTypeVisitor tv = new ThmPTypeVisitor();
//		
//		typeList.add(new ThmTypeAbbrev(node.getType().toString(), node.getType().apply(tv)));
//		
//		super.caseATypeDefinition(node);
//	}
//
//
//	@Override
//	public void caseAValueDefinition(AValueDefinition node)
//			throws AnalysisException {
//		ThmPExpVisitor ev = new ThmPExpVisitor();
//		
//		for (PDefinition d : node.getDefs()) {
//			valueList.add(new ThmValue(d.getName().toString(), node.getExpression().apply(ev)));
//		}
//		
//		super.caseAValueDefinition(node);
//	}
//
//
//
//	@Override
//	public void caseAExplicitFunctionDefinition(AExplicitFunctionDefinition arg0)
//			throws AnalysisException {
//		// TODO Auto-generated method stub
//		super.caseAExplicitFunctionDefinition(arg0);
//	}
//
//
//
//	@Override
//	public void caseAImplicitFunctionDefinition(AImplicitFunctionDefinition arg0)
//			throws AnalysisException {
//		// TODO Auto-generated method stub
//		super.caseAImplicitFunctionDefinition(arg0);
//	}
//}
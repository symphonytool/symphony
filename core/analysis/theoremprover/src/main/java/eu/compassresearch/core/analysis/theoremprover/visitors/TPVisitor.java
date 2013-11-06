package eu.compassresearch.core.analysis.theoremprover.visitors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pof.AVdmPoTree;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmTheorem;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmTheoremList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThySortException;
import eu.compassresearch.core.analysis.theoremprover.utils.UnhandledSyntaxException;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class TPVisitor extends
	QuestionAnswerCMLAdaptor<ThmVarsContext,ThmNodeList> {

	private final static String ANALYSIS_NAME = "Theorem Prover";

	private List<PSource> sources;

	private ThmTypeVisitor typeVisitor;
	private ThmValueVisitor valVisitor;
	private ThmDeclAndDefVisitor declAndDefVisitor;
	private ThmChannelVisitor chanVisitor;
	
	private ThmDepVisitor depVisitor = new ThmDepVisitor();
	private ThmStringVisitor stringVisitor = new ThmStringVisitor();
	

	private void initialize()
	{
		typeVisitor = new ThmTypeVisitor(this, depVisitor, stringVisitor);
		chanVisitor = new ThmChannelVisitor(this, depVisitor, stringVisitor);
		declAndDefVisitor = new ThmDeclAndDefVisitor(this, depVisitor, stringVisitor);
		valVisitor = new ThmValueVisitor(this, depVisitor, stringVisitor);
	}
	@Override
	public ThmNodeList caseAValueDefinition(AValueDefinition node, ThmVarsContext vars)
			throws AnalysisException {		
		return node.apply(this.valVisitor, vars);
	}
	
	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node, ThmVarsContext vars)
			throws AnalysisException {		
		return node.apply(this.typeVisitor, vars);
	}

	@Override
	public ThmNodeList caseAChannelDefinition(AChannelDefinition node, ThmVarsContext vars)
			throws AnalysisException {		
		return node.apply(this.chanVisitor, vars);
	}

	@Override
	public ThmNodeList caseAChansetDefinition(AChansetDefinition node, ThmVarsContext vars)
			throws AnalysisException {		
		return node.apply(this.chanVisitor, vars);
	}	

	@Override
	public ThmNodeList defaultPDefinition(PDefinition node, ThmVarsContext vars) 
			throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor, vars);
	}

	@Override
	public ThmNodeList defaultPSingleDeclaration(PSingleDeclaration node, ThmVarsContext vars)
			throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor, vars);
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
	
	/*****
	 * Method to generate the String for a Isabelle Theory file, given the theory file name
	 * and a list of AST nodes for a CML model
	 * @param ast - the ast nodes
	 * @param thyFileName - the filename this theory file will use
	 * @return String for the theory file
	 * @throws AnalysisException
	 */
	public static String generateThyStr(List<INode> ast, String thyFileName) 
			throws AnalysisException, UnhandledSyntaxException {
		
		ThmNodeList nodes = new ThmNodeList();
		String nodeErrors = "";
		String thyString = "";
		StringBuilder sb = new StringBuilder();
		
		
		for (INode node : ast) {
			try {
				nodes.addAll(node.apply(new TPVisitor(), new ThmVarsContext()));
			}catch (Exception e) {
					nodeErrors = nodeErrors + "(*Thy gen error:*)\n" + 
								"(*Could not generate Isabelle syntax for CML node - please submit bug report with CML file*)\n\n";
				}
		}

		//Sort nodes into dependency-order
		try
		{
			thyString = sortThmNodes(nodes).toString();
		}catch(ThySortException thye)
		{
			thyString = "(*Thy gen error:*)\n" + 
					"(*Isabelle Error when sorting nodes -- please submit bug report with CML file*)\n\n"+ thye.getSortErrorStatus() +"\n\n";
		}
		
		//retrieve the file name without the .thy file extension
		String thyName = thyFileName.substring(0, thyFileName.lastIndexOf('.'));
		
		//Add thy header 
		sb.append("theory " + thyName + " \n" + "  imports utp_cml \n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for "+  thyName + ".cml *}\n\n");
		
		//Add any node errors
		sb.append(nodeErrors);
		
		//Add generated node strings
		sb.append(thyString);
			
		sb.append("\n" + "end");
		
		NodeNameList errNodes = nodes.getErrorNodes();
		if(! errNodes.isEmpty())
		{
			throw new UnhandledSyntaxException(sb.toString(), errNodes);
		}
		return sb.toString();
	}
	

	/*******
	 * Method to sort a list of nodes into dependent-order
	 * @param tpnodes - list of ThmNodes to sort
	 * @return list of sorted nodes
	 * @throws ThySortException 
	 *******/
	public static ThmNodeList sortThmNodes(ThmNodeList thmnodes) throws ThySortException
	{
		ThmNodeList unsortedNodes = thmnodes.duplicate();
		ThmNodeList sortedNodes = new ThmNodeList();
		ThmNode tempNode = null;
		boolean passSort = false;
   
		//while there are nodes still to be sorted
		while (! unsortedNodes.isEmpty()){
			passSort = false;
			//iterate through nodes which still need sorting
			for (Iterator<ThmNode> itr = unsortedNodes.listIterator(); itr.hasNext(); ) {
				tempNode = itr.next();
				//if the current node has no dependencies, or it has a self-dependency, 
				//or all the nodes it depends upon have already been sorted...
				if(tempNode.getnumDep() == 0 ||
				   tempNode.selfDep() ||
				   sortedNodes.allDepsFulfilled(tempNode.getDepIds())){
					//Add to returned list.
					sortedNodes.add(tempNode);
					//Removes from original list passed to method.
					itr.remove(); 
					passSort = true;
				}
				//if there are no nodes left to sort.
				if( unsortedNodes.isEmpty()){
					break;
				}
			}
			if(!passSort)
			{
				throw new ThySortException(sortedNodes, unsortedNodes);
			}
		}
		return sortedNodes;
	}
	

	public static String generateEmptyThyStr(String thyFileName) 
	{
		StringBuilder sb = new StringBuilder();
		//generate theory name without extension
		String thyName = thyFileName.substring(0, thyFileName.lastIndexOf('.'));
		//new file name for user files
		String usrThyName = thyName+ "_User";

		//Add thy header 
		sb.append("theory " + usrThyName + " \n" + "  imports utp_cml " + thyName +"\n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for user created proof with "+  usrThyName + ".thy *}\n\n");


		sb.append("\n\n\n" + "end");
		
		return sb.toString();
	}
	
	/****
	 * PLACEHOLDER FOR THY-PO GENERATION - DOES NOT YET WORK!
	 * @param ast
	 * @param poList
	 * @param thyFileName
	 * @return
	 */
	public static String generatePogThyStr(List<INode> ast, IProofObligationList poList, String thyFileName) 
	{
		String pogErrors = "";
		String pogString = "";
		ThmTheoremList poThys = new ThmTheoremList();
		
		//First, obtain all the state variable names so that the theorem expressions use the correct
		//variable identifiers : $ or ^^
		NodeNameList svars = new NodeNameList();// ThmProcessUtil.getStateNames(statements);
		for (INode node : ast) {
			try 
			{
				if (node instanceof AProcessDefinition)
				{
					AProcessDefinition proc = (AProcessDefinition) node;
					if (proc.getProcess() instanceof AActionProcess)
					{
						AActionProcess act = (AActionProcess) proc.getProcess();
						svars.addAll(ThmProcessUtil.getProcessStatementNames(act));
					}
				}
			}
			catch (Exception e)
			{
				pogErrors = pogErrors + "(*Thy gen error:*)\n" + 
						"(*Could not generate Isabelle syntax for POs - please submit bug report with CML file*)\n\n";
			}
		}
	
		try 
		{
			//For each proof obligation, create a theorem
			for (IProofObligation ipo : poList)
			{
				ProofObligation po = (ProofObligation) ipo;
				//THIS BIT NEEDS MORE EFFORT!
				AVdmPoTree poValTree = po.getValueTree();
				PExp poExp = poValTree.getPredicate();
				NodeNameList bvars = new NodeNameList();
				String theoryBody = poExp.apply(new ThmStringVisitor(), new ThmVarsContext(svars, bvars));//ThmExprUtil.getIsabelleExprStr(svars, bvars, poExp);//"true";
				poThys.add(new ThmTheorem("po" + po.getUniqueName(), theoryBody, "by (cml_auto_tac)"));
			}
			pogString = poThys.toString();
		}
		catch (Exception e)
		{
			pogErrors = pogErrors + "(*Thy gen error:*)\n" + 
					"(*Could not generate Isabelle syntax for POs - please submit bug report with CML file*)\n\n";
		}

		//retrieve the file name without the .thy file extension
		String thyName = thyFileName.substring(0, thyFileName.lastIndexOf('.'));
		String poThyName = thyName+ "_PO";

		StringBuilder sb = new StringBuilder();
		//Add thy header 
		sb.append("theory " + poThyName + " \n" + "  imports utp_cml " + thyName +"\n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for proof obligations generated for "+  thyName + ".cml *}\n\n");
		
		//Add any node errors
		sb.append(pogErrors);
		
		//Add generated node strings
		sb.append(pogString);
			
		sb.append("\n" + "end");
		
		return sb.toString();
	}
	
	@Override
	public ThmNodeList createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ThmNodeList createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}	
}




///********************
// * Method to generate string for a theory file from the provided source
// ********************/
//public String generateThyString() throws IOException, AnalysisException {
//	ThmNodeList nodes = new ThmNodeList();
//
//	String nodeErrors = "";
//	String thyString = "";
//	
//	for (PSource source : sources) {
//		for (PDefinition paragraph : source.getParagraphs()) {
//			try {
//				paragraph.apply(this);
//			} catch (Exception e) {
//				nodeErrors = nodeErrors + "{*Thy gen error:*}\n" + 
//							"{*Could not generate Isabelle syntax for CML node - please submit bug report with CML file*}\n'n";
//			}
//		}
//	}
//	
//	try
//	{
//		//Sort nodes into dependency-order
//		thyString = sortThmNodes(nodes).toString();
//	}catch(ThySortException thye)
//	{
//		thyString = "{*Thy gen error:*}\n" + 
//				"{*Isabelle Error when sorting nodes - please submit bug report with CML file*}\n'n";
//		
//	}
//
//	StringBuilder sb = new StringBuilder();
//
//	//TODO: Obtain model/project/filename
//	String name = "TempName";
//
//	//TODO: May need to obtain thy name by trimming cml filename...
////	String thyName = name.substring(0, name.lastIndexOf("."));
//	String thyName = name;
//	
//	//Add thy header 
//	sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
//			+ "begin \n" + "\n");
//	sb.append("text {* Auto-generated THY file f "+  thyName + ".cml *}\n\n");
//
//	//Add any node errors
//	sb.append(nodeErrors);
//	
//	//Add generated node strings
//	sb.append(thyString);
//		
//	sb.append("\n" + "end");
//	
//	return sb.toString();
//	
//	
//	//for (ThmValue tv : tpv.getValueList())
//	//{
//	//	sb.append(tv.toString());
//	//	sb.append("\n");
//	//}
//	//
//	//sb.append("\n");
//	//sb.append("text {* VDM type declarations *}\n\n");
//	//
//	//for (ThmType ty : tpv.getTypeList())
//	//{
//	//	sb.append(ty.toString());
//	//	sb.append("\n");
//	//}
//	//
//	//sb.append("\n");
//	//
//}

package eu.compassresearch.core.analysis.theoremprover.visitors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThySortException;

@SuppressWarnings("serial")
public class TPVisitor extends
	AnswerCMLAdaptor<ThmNodeList> {

	private final static String ANALYSIS_NAME = "Theorem Prover";

	private List<PSource> sources;

	private ThmTypeVisitor typeVisitor;
	private ThmValueVisitor valVisitor;
	private ThmDeclAndDefVisitor declAndDefVisitor;
	private ThmChannelVisitor chanVisitor;

	private void initialize()
	{
		typeVisitor = new ThmTypeVisitor(this);
		chanVisitor = new ThmChannelVisitor(this);
		declAndDefVisitor = new ThmDeclAndDefVisitor(this);
		valVisitor = new ThmValueVisitor(this);
	}
	@Override
	public ThmNodeList caseAValueDefinition(AValueDefinition node)
			throws AnalysisException {		
		return node.apply(this.valVisitor);
	}
	
	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException {		
		return node.apply(this.typeVisitor);
	}

	@Override
	public ThmNodeList caseAChannelNameDefinition(AChannelNameDefinition node)
			throws AnalysisException {		
		return node.apply(this.chanVisitor);
	}

	@Override
	public ThmNodeList caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException {		
		return node.apply(this.chanVisitor);
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
	
	/*****
	 * Method to generate the String for a Isabelle Theory file, given the theory file name
	 * and a list of AST nodes for a CML model
	 * @param ast - the ast nodes
	 * @param thyFileName - the filename this theory file will use
	 * @return String for the theory file
	 * @throws AnalysisException
	 */
	public static String generateThyStr(List<INode> ast, String thyFileName) 
			throws AnalysisException {
		
		ThmNodeList nodes = new ThmNodeList();
		String nodeErrors = "";
		String thyString = "";
		StringBuilder sb = new StringBuilder();
		
		
		for (INode node : ast) {
			try {
				nodes.addAll(node.apply(new TPVisitor()));
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
		
		//retrieve the file name without the .thy file exetension
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
		
		return sb.toString();
	}
	

	/*******
	 * Method to sort a list of nodes into dependent-order
	 * @param tpnodes - list of ThmNodes to sort
	 * @return list of sorted nodes
	 * @throws ThySortException 
	 *******/
	public static ThmNodeList sortThmNodes(ThmNodeList tpnodes) throws ThySortException
	{
		ThmNodeList sortedNodes = new ThmNodeList();
		ThmNode tempNode = null;
		boolean passSort = false;
   
		//while there are nodes still to be sorted
		while (! tpnodes.isEmpty()){
			passSort = false;
			//iterate through nodes which still need sorting
			for (Iterator<ThmNode> itr = tpnodes.listIterator(); itr.hasNext(); ) {
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
				if( tpnodes.isEmpty()){
					break;
				}
			}
			if(!passSort)
			{
				throw new ThySortException(sortedNodes, tpnodes);
			}
		}
		return sortedNodes;
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

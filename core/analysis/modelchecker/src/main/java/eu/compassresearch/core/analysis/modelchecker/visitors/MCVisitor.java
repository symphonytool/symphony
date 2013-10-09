package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;

/**
 * The main MC visitor. It obtains other visitors from a factory.
 * @author Adalberto
 *
 */
public class MCVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	private StringBuilder basicContent;

	public MCVisitor(List<PSource> sources) {
		this.sources = sources;
	}
	public MCVisitor() {
		this.sources = new LinkedList<PSource>();
		basicContent = new StringBuilder();
	}
	public MCVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}

	
	@Override
	public StringBuilder defaultINode(INode node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> suitableVisitor = 
				MCVisitorFactory.getInstance().getVisitor(node);
		
		return  node.apply(suitableVisitor, question);
	}
	
	public String[] generateFormulaCodeForAll(String propertyToCheck) throws IOException,AnalysisException {

		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
		}
		for (PSource source : sources) {
			CMLModelcheckerContext context = new CMLModelcheckerContext();
			context.setPropertyToCheck(propertyToCheck);
			this.basicContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT);
			for (PDefinition paragraph : source.getParagraphs()) {
				paragraph.apply(this, context);
			}
			codes[sources.indexOf(source)] = basicContent + "\n" + context.getScriptContent()
					.toString();
		}
		return codes;
	}
	
	public String generateFormulaScript(String basicContent, List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
		
		CMLModelcheckerContext content = new CMLModelcheckerContext();
		//adds the basic content (semantics embedding) to the generated script
		
		setPropertyToCheck(propertyToCheck);
		getBasicContent().append(basicContent);
		content.getScriptContent().append(basicContent);
		for (PDefinition paragraph : definitions) {
			paragraph.apply(this, content);
		}
		return content.getScriptContent().toString();
	}
	
	public List<PSource> getSources() {
		return sources;
	}
	public void setSources(List<PSource> sources) {
		this.sources = sources;
	}
	public String getPropertyToCheck() {
		return propertyToCheck;
	}
	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
	}
	public StringBuilder getBasicContent() {
		return basicContent;
	}
	public void setBasicContent(StringBuilder basicContent) {
		this.basicContent = basicContent;
	}

	public static void main(String[] args) throws Throwable {
		//String cml_example = "src/test/resources/action-operationcall-noargs.cml";
		//String cml_example = "src/test/resources/replicated-externalchoice.cml";
		//String cml_example = "src/test/resources/replicated-generalised-parallelism.cml";
		//String cml_example = "src/test/resources/action-seq-comp2.cml";
		//String cml_example = "src/test/resources/action-vardecl.cml";
		//String cml_example = "src/test/resources/action-vardecl2.cml";
		//String cml_example = "src/test/resources/action-reference-parametrised.cml";
		//String cml_example = "src/test/resources/action-guard-stateVar.cml";
		//String cml_example = "src/test/resources/action-guard.cml";
		//String cml_example = "src/test/resources/minimondex.cml";
		//String cml_example = "src/test/resources/replicated-seqcomp.cml";
		//String cml_example = "src/test/resources/Dphils.cml";
		String cml_example = "src/test/resources/action-not-implemented.cml";
		System.out.println("Testing on " + cml_example);
		// List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		// sources.add(source);
		//String basic = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT).toString();
		//String code = CMLModelcheckerVisitor.generateFormulaScript(basic, source.getParagraphs(), Utilities.DEADLOCK_PROPERTY);
		//System.out.println(code);
		MCVisitor visitor = new MCVisitor(source);
		visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes = visitor.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		System.out.println("Analysing generated FORMULA specification");
		//IFormulaIntegrator mc = FormulaIntegrator.getInstance();
		//FormulaResult mcResult = mc.analyse(codes[0]);

		//System.out.println("File " + cml_example + " is "
		//		+ (mcResult.isSatisfiable() ? "SAT" : "UNSAT") + "\n");
		//double loadTime = mcResult.getElapsedTimeLoad();
		//double solveTime = mcResult.getElapsedTimeSolve();
		//System.out.println("Analysis time (load + solve) = " + "(" + loadTime
		//		+ " + " + solveTime + ") = " + (loadTime + solveTime)
		//		+ " seconds\n");
		//System.out.println("Base of Facts: \n");
		//System.out.println(mcResult.getFacts());
		//mc.finalize();
	}
	@Override
	public StringBuilder createNewReturnValue(INode node,
			CMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StringBuilder createNewReturnValue(Object node,
			CMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

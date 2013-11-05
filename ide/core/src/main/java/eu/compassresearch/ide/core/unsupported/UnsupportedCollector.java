package eu.compassresearch.ide.core.unsupported;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PAccess;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.traces.ATraceDefinitionTerm;
import org.overture.ast.definitions.traces.PTraceCoreDefinition;
import org.overture.ast.definitions.traces.PTraceDefinition;
import org.overture.ast.expressions.ACaseAlternative;
import org.overture.ast.expressions.ARecordModifier;
import org.overture.ast.expressions.PAlternative;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.PModifier;
import org.overture.ast.modules.PExport;
import org.overture.ast.modules.PExports;
import org.overture.ast.modules.PImport;
import org.overture.ast.modules.PImports;
import org.overture.ast.modules.PModules;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AMapletPatternMaplet;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMaplet;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.patterns.PPatternBind;
import org.overture.ast.statements.ACaseAlternativeStm;
import org.overture.ast.statements.AErrorCase;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ATixeStmtAlternative;
import org.overture.ast.statements.PAlternativeStm;
import org.overture.ast.statements.PCase;
import org.overture.ast.statements.PClause;
import org.overture.ast.statements.PObjectDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.ast.statements.PStmtAlternative;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.PAccessSpecifier;
import org.overture.ast.types.PField;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PAlternativeAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.PCMLAccess;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.patterns.PCMLPair;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.PCMLType;

/**
 * This class traverses an entire model and collects information on elements
 * that are not supported by a particular analysis or feature of CML.
 * <b>Warning:</b> The tree traversal is handled by this class to any subclasses
 * must invoke the super in their cases.
 * 
 * @author ldc
 */
public abstract class UnsupportedCollector extends DepthFirstAnalysisCMLAdaptor {

	private static final long serialVersionUID = 1L;

	List<UnsupportedElementInfo> unsupporteds = new LinkedList<UnsupportedElementInfo>();
	UnsupportingFeatures feature;
	protected boolean unsupported;

	public UnsupportedCollector(UnsupportingFeatures feature) {
		super();
		this.unsupported = true;
		this.feature = feature;
	}

	public List<UnsupportedElementInfo> getUnsupporteds() {
		return unsupporteds;
	}

	public void setUnsupporteds(List<UnsupportedElementInfo> unsupporteds) {
		this.unsupporteds = unsupporteds;
	}

	public UnsupportingFeatures getFeature() {
		return feature;
	}

	public void setFeature(UnsupportingFeatures feature) {
		this.feature = feature;
	}

	public List<UnsupportedElementInfo> getUnsupporteds(List<INode> ast)
			throws AnalysisException {
		for (INode node : ast) {
			node.apply(this);
		}
		return unsupporteds;
	}

	// SPECIAL CASES where the super nodes lack info we need.

	/**
	 * Special dedicated case since the more general {@link PModifier} does not
	 * have location info. Override as needed. But keep super call.
	 */
	@Override
	public void caseARecordModifier(ARecordModifier node)
			throws AnalysisException {

		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getTag().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.caseARecordModifier(node);
	}

	/**
	 * Special dedicated case since the more general {@link PAlternative} does
	 * not have location info. Override as needed. But keep super call.
	 */
	@Override
	public void caseACaseAlternative(ACaseAlternative node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}

	/**
	 * Special dedicated case since the more general {@link PAccessSpecifier}
	 * does not have location info. Override as needed.But keep super call. Very
	 * problematic because it also has no location information. We just get it
	 * from ancestor.
	 */

	@Override
	public void caseAAccessSpecifierAccessSpecifier(
			AAccessSpecifierAccessSpecifier node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getAncestor(PDefinition.class).getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPAccessSpecifier(node);
	}

	/**
	 * Special dedicated case since the more general {@link PField} does not
	 * have location info. Override as needed. But keep super call.
	 */
	@Override
	public void caseAFieldField(AFieldField node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getTagname().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}

	/**
	 * Special dedicated case. We just skip right on past.
	 */
	@Override
	public void defaultOutPAccess(PAccess node) throws AnalysisException {
		super.defaultOutPAccess(node);
	}

	/**
	 * Special dedicated case since the more general {@link PMaplet} does not
	 * have location info. {@link AMapletPatternMaplet} also has no location so
	 * we grab it from its parent {@link PPattern}. Override as needed. But keep
	 * super call.
	 */
	@Override
	public void caseAMapletPatternMaplet(AMapletPatternMaplet node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getFrom().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPMaplet(node);
	}

	/**
	 * Special dedicated case since the more general {@link PPair} does not have
	 * location info. Override as needed. But keep super call. We use a
	 * {@link LocatorPPair} visitor to extract the locations.
	 */
	@Override
	public void defaultOutPPair(PPair node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.apply(new LocatorPPair()));
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPPair(node);
	}
	
	@Override
	public void defaultOutPCMLPair(PCMLPair node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.apply(new LocatorPPair()));
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCMLPair(node);
	}

	/**
	 * Special dedicated case since the more general {@link PPatternBind} does
	 * not have location info. Override as needed. But keep super call.
	 */
	@Override
	public void caseADefPatternBind(ADefPatternBind node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPPatternBind(node);
	}

	/**
	 * Special case. We dangerously ignore it for now.
	 */
	@Override
	public void caseATraceDefinitionTerm(ATraceDefinitionTerm node)
			throws AnalysisException {
		// FIXME look at locations for ATraceDefinitionTerm again
		super.defaultOutPTerm(node);
	}
	/**
	 * Special case. We dangerously ignore it for now.
	 */
	@Override
	public void defaultOutPCMLAccess(PCMLAccess node) throws AnalysisException {
		// FIXME look at locations for PCMLAccess again
		super.defaultOutPCMLAccess(node);
	}
	
	/**
	 * Special case. Imports are not in CML. Skip past it.
	 */
	@Override
	public void defaultOutPImports(PImports node) throws AnalysisException {
		super.defaultOutPImports(node);
	}

	/**
	 * Special case. Modules are not in CML. No need to check.
	 */
	@Override
	public void defaultOutPModules(PModules node) throws AnalysisException {
		super.defaultOutPModules(node);
	}
	
	/**
	 * Special case. Exports are not in CML. No need to check.
	 */
	@Override
	public void defaultOutPExports(PExports node) throws AnalysisException {
		super.defaultOutPExports(node);
	}
	
	/**
	 * Special case. Top level. No need to check it.
	 */
	@Override
	public void defaultOutPSource(PSource node) throws AnalysisException {
		super.defaultOutPSource(node);
	}
	

	/**
	 * Special dedicated case since the more general {@link PAlternativeStm} does not have
	 * location info.
	 */
	@Override
	public void caseACaseAlternativeStm(ACaseAlternativeStm node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPAlternativeStm(node);
	}
	
	/**
	 * Special dedicated case since the more general {@link PStmtAlternative} does not have
	 * location info. We use the {@link PStm} for the location.
	 */
	@Override
	public void caseATixeStmtAlternative(ATixeStmtAlternative node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getStatement().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPStmtAlternative(node);
	}
	
	/**
	 * Special dedicated case since the more general {@link PCase} does not have
	 * location info.
	 */
	@Override
	public void caseAErrorCase(AErrorCase node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getName().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCase(node);
	}
	
	/**
	 * Special dedicated case since the more general {@link PClause} does not have
	 * location info.
	 */
	@Override
	public void caseAExternalClause(AExternalClause node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getMode().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPClause(node);
	}
	
	/**
	 * Special dedicated case since the more general {@link PAlternativeAction} does not have
	 * location info.
	 */
	@Override
	public void caseACaseAlternativeAction(ACaseAlternativeAction node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPAlternativeAction(node);
	}


	
	
	// DEFAULT CASES

	@Override
	public void defaultOutPExp(PExp node) throws AnalysisException {

		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}

	}

	@Override
	public void defaultOutPType(PType node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			if (node.getLocation()!=null)
				uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}
	
	@Override
	public void caseAProcessParagraphType(AProcessParagraphType node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			if (node.getLocation()!=null){
			uei.setLocation(node.getLocation());
			}
			else{
				uei.setLocation(null);
//				throw new AnalysisException(node.toString() + "has no location");
			}
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}
	
	@Override
	public void defaultOutPPattern(PPattern node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPPattern(node);
	}

	@Override
	public void defaultOutPBind(PBind node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPBind(node);
	}

	@Override
	public void defaultOutPMultipleBind(PMultipleBind node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPMultipleBind(node);
	}

	@Override
	public void defaultOutPDefinition(PDefinition node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPDefinition(node);
	}

	@Override
	public void defaultOutPTraceDefinition(PTraceDefinition node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPTraceDefinition(node);
	}

	@Override
	public void defaultOutPTraceCoreDefinition(PTraceCoreDefinition node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPTraceCoreDefinition(node);
	}



	@Override
	public void defaultOutPImport(PImport node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPImport(node);
	}



	@Override
	public void defaultOutPExport(PExport node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPExport(node);
	}

	@Override
	public void defaultOutPStm(PStm node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPStm(node);
	}

	@Override
	public void defaultOutPStateDesignator(PStateDesignator node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPStateDesignator(node);
	}

	@Override
	public void defaultOutPObjectDesignator(PObjectDesignator node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPObjectDesignator(node);
	}










	@Override
	public void defaultOutPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPSingleDeclaration(node);
	}

	@Override
	public void defaultOutPVarsetExpression(PVarsetExpression node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPVarsetExpression(node);
	}

	@Override
	public void defaultOutPProcess(PProcess node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPProcess(node);
	}

	@Override
	public void defaultOutPAction(PAction node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPAction(node);
	}

	@Override
	public void defaultOutPCommunicationParameter(PCommunicationParameter node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCommunicationParameter(node);
	}

	@Override
	public void defaultOutPParametrisation(PParametrisation node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPParametrisation(node);
	}


	@Override
	public void defaultOutPCMLType(PCMLType node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			if (node.getLocation()!=null)
				uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCMLType(node);
	}





	@Override
	public void defaultOutPCMLDefinition(PCMLDefinition node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCMLDefinition(node);
	}

	@Override
	public void defaultOutPCMLExp(PCMLExp node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any element");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultOutPCMLExp(node);
	}

	@Override
	public void defaultInPDefinition(PDefinition node) throws AnalysisException {
		try {
			super.defaultInPDefinition(node);
		} catch (AnalysisException e) {

		}
	}

}

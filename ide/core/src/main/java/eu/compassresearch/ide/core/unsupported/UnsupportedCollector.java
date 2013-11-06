package eu.compassresearch.ide.core.unsupported;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ACaseAlternative;
import org.overture.ast.expressions.ARecordModifier;
import org.overture.ast.expressions.PAlternative;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.PModifier;
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
import org.overture.ast.statements.PStm;
import org.overture.ast.statements.PStmtAlternative;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.PAccessSpecifier;
import org.overture.ast.types.PField;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.PAlternativeAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;

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
	 * have location info. We get it from the tag.
	 */
	@Override
	public void outARecordModifier(ARecordModifier node)
			throws AnalysisException {

		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getTag().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.caseARecordModifier(node);
	}

	/**
	 * Special dedicated case since the more general {@link PAlternative} does
	 * not have location info.
	 */
	@Override
	public void caseACaseAlternative(ACaseAlternative node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}

	/**
	 * Special dedicated case since the more general {@link PAccessSpecifier}
	 * does not have location info. Very problematic because it also has no
	 * location information. We get the location from its ancestor.
	 */

	@Override
	public void caseAAccessSpecifierAccessSpecifier(
			AAccessSpecifierAccessSpecifier node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getAncestor(PDefinition.class).getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPAccessSpecifier(node);
	}

	/**
	 * Special dedicated case since the more general {@link PField} does not
	 * have location info.
	 */
	@Override
	public void caseAFieldField(AFieldField node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getTagname().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}

	/**
	 * Special dedicated case since the more general {@link PMaplet} does not
	 * have location info. {@link AMapletPatternMaplet} also has no location so
	 * we grab it from its parent {@link PPattern}.
	 */
	@Override
	public void caseAMapletPatternMaplet(AMapletPatternMaplet node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getFrom().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPMaplet(node);
	}

	/**
	 * Special case since the more general {@link PPair} does not have location
	 * info. We use a {@link LocatorPPair} visitor to extract the locations.
	 */
	@Override
	public void defaultInPPair(PPair node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.apply(new LocatorPPair()));
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPPair(node);
	}

	/**
	 * Special dedicated case since the more general {@link PPatternBind} does
	 * not have location info.
	 */
	@Override
	public void caseADefPatternBind(ADefPatternBind node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPPatternBind(node);
	}

	/**
	 * Special dedicated case since the more general {@link PAlternativeStm}
	 * does not have location info.
	 */
	@Override
	public void caseACaseAlternativeStm(ACaseAlternativeStm node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPAlternativeStm(node);
	}

	/**
	 * Special dedicated case since the more general {@link PStmtAlternative}
	 * does not have location info. We use the {@link PStm} for the location.
	 */
	@Override
	public void caseATixeStmtAlternative(ATixeStmtAlternative node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getStatement().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPStmtAlternative(node);
	}

	/**
	 * Special dedicated case since the more general {@link PCase} does not have
	 * location info.
	 */
	@Override
	public void caseAErrorCase(AErrorCase node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getName().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPCase(node);
	}

	/**
	 * Special dedicated case since the more general {@link PClause} does not
	 * have location info.
	 */
	@Override
	public void caseAExternalClause(AExternalClause node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getMode().getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPClause(node);
	}

	/**
	 * Special dedicated case since the more general {@link PAlternativeAction}
	 * does not have location info.
	 */
	@Override
	public void caseACaseAlternativeAction(ACaseAlternativeAction node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.caseACaseAlternativeAction(node);
	}

	// DEFAULT CASES

	@Override
	public void defaultInPExp(PExp node) throws AnalysisException {

		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}

	}

	@Override
	public void defaultInPType(PType node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			if (node.getLocation() != null)
				uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
	}

	@Override
	public void defaultInPPattern(PPattern node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPPattern(node);
	}

	@Override
	public void defaultInPBind(PBind node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPBind(node);
	}

	@Override
	public void defaultInPMultipleBind(PMultipleBind node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPMultipleBind(node);
	}

	@Override
	public void defaultInPDefinition(PDefinition node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPDefinition(node);
	}

	@Override
	public void defaultInPStm(PStm node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPStm(node);
	}

	@Override
	public void defaultInPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPSingleDeclaration(node);
	}

	@Override
	public void defaultInPVarsetExpression(PVarsetExpression node)
			throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPVarsetExpression(node);
	}

	@Override
	public void defaultInPProcess(PProcess node) throws AnalysisException {
		if (unsupported) {
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " nodes are not supported by the " + feature.toString());
			unsupporteds.add(uei);
		} else {
			unsupported = true;
		}
		super.defaultInPProcess(node);
	}

}

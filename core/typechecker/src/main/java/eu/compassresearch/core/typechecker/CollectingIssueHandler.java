package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * Very simple LinkedList based TypeIssueHandler that simply collects errors and
 * warnings for future retrieval.
 * 
 * @author rwl
 * 
 */
class CollectingIssueHandler implements TypeIssueHandler {

	/**
	 * Creating a collecting issue handler assumes you are going to 
	 * do a fresh type checking round. 
	 * 
	 * 
	 * 
	 */
	CollectingIssueHandler(Registry reg) {
		errors = new LinkedList<CMLTypeError>();
		warnings = new LinkedList<CMLTypeWarning>();
		registry = reg;
		registry.prune(CMLIssueList.class);
	}

	private final List<CMLTypeError> errors;
	private final List<CMLTypeWarning> warnings;
	private final Registry registry;

	@Override
	public List<CMLTypeError> getTypeErrors() throws IllegalStateException {
		return errors;
	}

	@Override
	public List<CMLTypeWarning> getTypeWarnings() throws IllegalStateException {
		return warnings;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AErrorType addTypeError(INode offendingSubtree, String message) {
		CMLTypeError error = new CMLTypeError(offendingSubtree, message);
		this.errors.add(error);
		addIssueToRegistryForNode(offendingSubtree,registry, error);
		return new AErrorType(error.getLocation(), true);
	}

	private static void addIssueToRegistryForNode(INode node, Registry reg,
			CMLIssue error) {
		CMLIssueList errors = reg.lookup(node, CMLIssueList.class);
		if (errors == null) { errors = new CMLIssueList(); reg.store(node, errors); }
		errors.add(error);
	}

	@Override
	public void addTypeWarning(INode hazardousSubtree, String message) {
		CMLTypeWarning warning = new CMLTypeWarning(hazardousSubtree, message);
		this.warnings.add(warning);
		addIssueToRegistryForNode(hazardousSubtree, registry, warning);
	}

	@Override
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	@Override
	public boolean hasWarnings() {
		return !warnings.isEmpty();
	}

	@Override
	public boolean hasIssues() {
		return hasErrors() || hasWarnings();
	}

	@SuppressWarnings("deprecation")
	@Override
	public AErrorType addTypeError(INode parent, ILexLocation location, String message) {
		CMLTypeError typeError = new CMLTypeError(parent, message);
		typeError.setLocation(location);
		this.errors.add(typeError);
		addIssueToRegistryForNode(parent, registry, typeError);
		return new AErrorType(location, true);
	}


}

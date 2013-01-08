package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLIssue;

/**
 * Very simple LinkedList based TypeIssueHandler that simply collects errors and
 * warnings for future retrieval.
 * 
 * @author rwl
 * 
 */
class CollectingIssueHandler implements TypeIssueHandler {

	CollectingIssueHandler() {
		errors = new LinkedList<CMLTypeError>();
		warnings = new LinkedList<CMLTypeWarning>();
	}

	private final List<CMLTypeError> errors;
	private final List<CMLTypeWarning> warnings;

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
		return new AErrorType(error.getLocation(), true);
	}

	@Override
	public void addTypeWarning(INode hazardousSubtree, String message) {
		this.warnings.add(new CMLTypeWarning(hazardousSubtree, message));
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

	@Override
	public AErrorType addTypeError(INode parent, LexLocation location, String message) {
		CMLTypeError typeError = new CMLTypeError(parent, message);
		typeError.setLocation(location);
		this.errors.add(typeError);
		return new AErrorType(location, true);
	}


}

package eu.compassresearch.ide.core.builder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.overture.ast.lex.Dialect;
import org.overture.ast.node.INode;
import org.overture.ast.util.definitions.ClassList;
import org.overture.config.Settings;
import org.overture.ide.builders.vdmj.IBuilderVdmjConstants;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.builder.AbstractVdmBuilder;
import org.overture.parser.messages.VDMError;
import org.overture.parser.messages.VDMWarning;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeWarning;
import eu.compassresearch.ide.core.resources.ICmlModel;

public class BuilderCml extends AbstractVdmBuilder
{
	protected ClassList classes = new ClassList();

	public BuilderCml()
	{
		Settings.dialect = Dialect.CML;
	}

	@Override
	public IStatus buildModel(IVdmModel rooList)
	{
		List<VDMError> errors = new ArrayList<VDMError>();
		List<VDMWarning> warnings = new ArrayList<VDMWarning>();

		// Collection<PSource> cmlSources = new Vector<PSource>();
		// for (INode s : rooList.getRootElementList())
		// {
		// if (s instanceof PSource)
		// {
		// cmlSources.add((PSource) s);
		// }
		// }

		ICmlModel model = (ICmlModel) rooList.getAdapter(ICmlModel.class);

		// Registry reg = RegistryFactory.getInstance(getProject().getName()).getRegistry();
		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(model.getAstSource(), issueHandler);
		try
		{
			typeChecker.typeCheck();

			List<CMLTypeError> errorsThatMatter = filterErrros(issueHandler.getTypeErrors());
			for (final CMLTypeError error : errorsThatMatter)
			{
				addErrorMarker(error);
			}

			// set warning markers
			for (CMLTypeWarning warning : issueHandler.getTypeWarnings())
			{
				warnings.add(new VDMWarning(0, warning.getDescription(), warning.getLocation()));
			}
		} catch (Exception e)
		{
			try
			{
				IMarker projectMarker = ((IProject) getProject().getAdapter(IProject.class)).createMarker(IMarker.PROBLEM);
				projectMarker.setAttribute(IMarker.MESSAGE, "Type checking on this project failed.");
				projectMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			} catch (CoreException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
			new Status(IStatus.ERROR, IBuilderVdmjConstants.PLUGIN_ID, 0, "not typechecked, internal error", e);
		}

		return setMarkers(errors, warnings);
	}

	private IStatus setMarkers(List<VDMError> errors, List<VDMWarning> warnings)
	{
		boolean typeCheckFailed = !errors.isEmpty();

		if (!getProject().hasSuppressWarnings())
		{
			for (VDMWarning warning : warnings)
			{
				addWarningMarker(warning);
			}
		}
		IStatus typeChecked = null;

		if (typeCheckFailed)
		{
			typeChecked = new Status(IStatus.ERROR, IBuilderVdmjConstants.PLUGIN_ID, 0, "not typechecked", null);

		} else
		{

			typeChecked = new Status(IStatus.OK, IBuilderVdmjConstants.PLUGIN_ID, 0, "Type Checked", null);

		}
		return typeChecked;
	}

	// private void addErrorMarker(VDMError error)
	// {
	// addErrorMarker(error.location.getFile(), error.toProblemString(), error.location,
	// IBuilderVdmjConstants.PLUGIN_ID);
	// }

	private void addErrorMarker(CMLTypeError error)
	{
		// TODO RWL: This needs to be fixed to loose hanging trees with errors
		AFileSource source = (AFileSource) error.getOffendingNode().getAncestor(PSource.class);
		if (source != null)
		{
			addErrorMarker((source.getFile() == null ? error.getLocation().getFile()
					: source.getFile()), error.getDescription(), error.getLocation(), IBuilderVdmjConstants.PLUGIN_ID);
		}
	}

	private void addWarningMarker(VDMWarning error)
	{
		addWarningMarker(error.location.getFile(), error.toProblemString(), error.location, IBuilderVdmjConstants.PLUGIN_ID);
	}

	/**
	 * @see org.overture.vdmj.VDMJ#typeCheck()
	 */

	/*
	 * For each error remove the parent errors so we only see the leafs.
	 */
	private static List<CMLTypeError> filterErrros(List<CMLTypeError> errs)
	{
		// errors is the result
		List<CMLTypeError> errors = new LinkedList<CMLTypeError>();

		// errors that has a child node with an error
		List<INode> redundant = new LinkedList<INode>();

		// For each error add nodes to redundant that are parent of
		// the node with the error.
		for (CMLTypeError e : errs)
		{
			INode errorNode = e.getOffendingNode();
			errors.add(e);

			List<INode> cycleDetection = new LinkedList<INode>();
			cycleDetection.add(errorNode);
			while (errorNode.parent() != null)
			{
				errorNode = errorNode.parent();
				if (cycleDetection.contains(errorNode))
					break;
				redundant.add(errorNode);
			}
		}

		// We have the redundant parent nodes, collect the coresponding errors
		List<CMLTypeError> redundantErrors = new LinkedList<CMLTypeError>();
		for (CMLTypeError e : errors)
		{
			if (redundant.contains(e.getOffendingNode()))
				redundantErrors.add(e);
		}

		// finally remove the errors
		errors.removeAll(redundantErrors);

		return errors;

	}

}

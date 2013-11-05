package eu.compassresearch.ide.core.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.overture.ast.lex.Dialect;
import org.overture.ast.util.definitions.ClassList;
import org.overture.config.Settings;
import org.overture.ide.builders.vdmj.IBuilderVdmjConstants;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.builder.AbstractVdmBuilder;
import org.overture.parser.messages.VDMError;
import org.overture.parser.messages.VDMWarning;
import org.overture.typechecker.TypeCheckException;

import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeError;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeWarning;
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

		ICmlModel model = (ICmlModel) rooList.getAdapter(ICmlModel.class);

		ITypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		ICmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(model.getDefinitions(), issueHandler);
		try
		{
			typeChecker.typeCheck();

			List<CMLTypeError> errorsThatMatter = (issueHandler.getTypeErrors());
			for (final CMLTypeError error : errorsThatMatter)
			{
				addErrorMarker(error);
			}

			// set warning markers
			for (CMLTypeWarning warning : issueHandler.getTypeWarnings())
			{
				warnings.add(new VDMWarning(0, warning.getDescription(), warning.getLocation()));
			}
		} catch (TypeCheckException e)
		{
			issueHandler.addTypeError(e.node, e.location, e.getMessage());
		} catch (Exception e)
		{
			e.printStackTrace();
			return new Status(IStatus.ERROR, IBuilderVdmjConstants.PLUGIN_ID, 0, "not typechecked, internal error", e);
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

	private void addErrorMarker(CMLTypeError error)
	{
		addErrorMarker(error.getLocation().getFile(), error.getDescription(), error.getLocation(), IBuilderVdmjConstants.PLUGIN_ID);
	}

	private void addWarningMarker(VDMWarning error)
	{
		addWarningMarker(error.location.getFile(), error.toProblemString(), error.location, IBuilderVdmjConstants.PLUGIN_ID);
	}

}

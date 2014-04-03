package eu.compassresearch.ide.core.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.Dialect;
import org.overture.ast.util.definitions.ClassList;
import org.overture.config.Settings;
import org.overture.ide.builders.vdmj.IBuilderVdmjConstants;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.builder.AbstractVdmBuilder;
import org.overture.ide.core.utility.FileUtility;
import org.overture.parser.messages.VDMWarning;
import org.overture.typechecker.TypeCheckException;

import eu.compassresearch.core.typechecker.CmlTypeCheckerAssistantFactory;
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
		// List<VDMError> errors = new ArrayList<VDMError>();
		List<VDMWarning> warnings = new ArrayList<VDMWarning>();

		ICmlModel model = (ICmlModel) rooList.getAdapter(ICmlModel.class);

		ITypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		ICmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(model.getDefinitions(), issueHandler);
		try
		{
			// Force setup of the legacy static access assistants in Overture
			CmlTypeCheckerAssistantFactory.init(new CmlTypeCheckerAssistantFactory());
			typeChecker.typeCheck();

		} catch (TypeCheckException e)
		{
			// it is the type checkers responsibility to report all errors, this is just to indicate that it stopped
			// without completion
		} catch (Exception e)
		{
			e.printStackTrace();
			return new Status(IStatus.ERROR, IBuilderVdmjConstants.PLUGIN_ID, 0, "not typechecked, internal error", e);
		}

		List<CMLTypeError> errorsThatMatter = issueHandler.getTypeErrors();

		// set warning markers
		for (CMLTypeWarning warning : issueHandler.getTypeWarnings())
		{
			warnings.add(new VDMWarning(0, warning.getMessage(), warning.getLocation()));
		}

		return setMarkers(errorsThatMatter, warnings);
	}

	private IStatus setMarkers(List<CMLTypeError> errors,
			List<VDMWarning> warnings)
	{
		boolean typeCheckFailed = !errors.isEmpty();

		for (CMLTypeError error : errors)
		{
			addErrorMarker(error);
		}

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
		addErrorMarker(error.getLocation().getFile(), error.getMessage(), error.getLocation(), IBuilderVdmjConstants.PLUGIN_ID);
	}

	private void addWarningMarker(VDMWarning error)
	{
		addWarningMarker(error.location.getFile(), error.toProblemString(), error.location, IBuilderVdmjConstants.PLUGIN_ID);
	}

	protected void addWarningMarker(File file, String message,
			ILexLocation location, String sourceId)
	{
		FileUtility.addMarker(getProject().findIFile(file), message, location, IMarker.SEVERITY_WARNING, sourceId);
	}

	protected void addErrorMarker(File file, String message,
			ILexLocation location, String sourceId)
	{
		FileUtility.addMarker(getProject().findIFile(file), message, location, IMarker.SEVERITY_ERROR, sourceId);
	}

}

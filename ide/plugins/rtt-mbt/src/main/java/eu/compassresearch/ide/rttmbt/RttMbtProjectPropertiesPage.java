package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPropertyPage;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtProjectPropertiesPage extends FieldEditorPreferencePage implements IWorkbenchPropertyPage {

	private RttMbtClient client;
    private IAdaptable element;
    private IProject project;
	private String projectQualifiedname;

	// Project Database name
	private StringFieldEditor rttProjectDatabaseField;
	private String rttProjectDatabaseValue;
	
	public RttMbtProjectPropertiesPage() {
		super(GRID);
		setDescription("RTT-Project settings");
    	client = Activator.getClient();
    	project = null;
    	projectQualifiedname = null;
	}

	/** 
	 * this function calculates a project database name from the user id and the project name
	 */
	public String getDefaultProjectDatabasename() {
		if (client == null) {
			return null;
		}
		String dbname = client.getProjectDatabaseName();
		return dbname;
	}

	/** 
	 * retrieves a property value
	 */
	private String getPropertyValue(String key) {
		String value = null;
		QualifiedName propName = new QualifiedName(projectQualifiedname, key);
		try {
			value = project.getPersistentProperty(propName);
		} catch (CoreException e) {
			client.addErrorMessage("*** error: unable to get persistent property '" + propName + "'!");
		}
		return value;
	}

	/** 
	 * writes a property value
	 */
	private void setPropertyValue(String key, String value) {
		QualifiedName propName = new QualifiedName(projectQualifiedname, key);
		try {
			project.setPersistentProperty(propName, value);
		} catch (CoreException e) {
			client.addErrorMessage("*** error: unable to set persistent property '" + propName + "' to value '" + value + "'!");
		}
	}

	public void createFieldEditors() {

		// check if this project in RT-Tester project
		project = (IProject)element;
		String projectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
		if (!RttMbtClient.isRttProject(projectFilesystemPath)) {
			setDescription("NO RT-Tester Properties available for this project");
			project = null;
			return;
		}

		// get project information from properties element
		String projectWorkspacePath = project.getFullPath().toString();
		int pos = projectFilesystemPath.lastIndexOf(File.separator);
		client.setRttProjectPath(projectFilesystemPath);
		if (pos >= 0) {
			client.setRttProjectName(projectFilesystemPath.substring(pos + 1));
		} else {
			client.setRttProjectName(projectFilesystemPath);
		}
		pos = projectWorkspacePath.lastIndexOf(File.separator);
		if (pos > 0) {
			client.setWorkspaceProjectPrefix(projectWorkspacePath.substring(0, pos));
		} else {
			client.setWorkspaceProjectPrefix("");
		}
		client.setWorkspaceProjectName(project.getName());
		projectQualifiedname = project.getFullPath().toString().replaceAll(File.separator, ".");
		setDescription("RTT-Project settings for " + project.getFullPath());

		// add Project Database Name field
		rttProjectDatabaseField = new StringFieldEditor("RttMbtrttProjectDatabase",
                                                   "Project Database Name:",
                                                   getFieldEditorParent());
		rttProjectDatabaseField.setPreferenceStore(null);
		rttProjectDatabaseValue = getPropertyValue("RttMbtrttProjectDatabase");
		rttProjectDatabaseField.setStringValue(rttProjectDatabaseValue);
    	addField(rttProjectDatabaseField);

	}

	public void performDefaults() {
		rttProjectDatabaseField.setStringValue(client.getProjectDatabaseName());
		super.performDefaults();
	}

	public boolean performOk() {
		if (project != null) {

			// project database name
			setPropertyValue("RttMbtrttProjectDatabase", rttProjectDatabaseField.getStringValue());

			return super.performOk();
		} else {
			return super.performOk();
		}
	}

	public boolean performCancel() {
		if (project != null) {

			// project database name
			setPropertyValue("RttMbtrttProjectDatabase", rttProjectDatabaseValue);

			return super.performCancel();
		} else {
			return super.performCancel();
		}
	}

	public void init(IWorkbench workbench) {
	}


	@Override
	public IAdaptable getElement() {
        return element;
	}


	@Override
	public void setElement(IAdaptable element) {
        this.element = element;
	}
	
}

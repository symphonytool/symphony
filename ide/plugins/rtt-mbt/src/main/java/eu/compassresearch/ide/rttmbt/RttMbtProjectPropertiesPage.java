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

	// Project Database Name
	private StringFieldEditor rttProjectDatabaseField;
	private String rttProjectDatabaseValue;
	
	// Test Generation Context Name
	private StringFieldEditor rttExeCtxNameField;
	private String rttExeCtxName;
	
	// Test Execution Context Name
	private StringFieldEditor rttTgenCtxNameField;
	private String rttTgenCtxName;

	// Make tool
	private StringFieldEditor makeToolField;
	private String makeToolProperty;

	// ignore patterns for upload/download
	private StringFieldEditor ignorePatternUploadField;
	private String ignorePatternUploadProperty;

	public RttMbtProjectPropertiesPage() {
		super(GRID);
		setDescription("RTT-Project settings");
    	client = Activator.getClient();
    	project = null;
    	projectQualifiedname = null;
	}

	// file separator that is regular expression compatible
	private static String fileSeparatorPattern() {
		String pattern = File.separator;
		if (pattern.compareTo("\\") == 0) {
			pattern = "\\\\";
		}
		return pattern;
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
			client.addErrorMessage("unable to get persistent property '" + propName + "'!");
			value = null;
		}
		return value;
	}

	// static version for RttMbtClient
	static public String getPropertyValue(IProject proj, String key) {
		String value = null;
		if (proj == null) return value;
		if (key == null) return value;

		// calculate qualified name of the property key
		String qname = proj.getFullPath().toString().replaceAll(fileSeparatorPattern(), ".");
		QualifiedName propName = new QualifiedName(qname, key);
		try {
			value = proj.getPersistentProperty(propName);
		} catch (CoreException e) {
			value = null;
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
			client.addErrorMessage("unable to set persistent property '" + propName + "' to value '" + value + "'!");
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
		projectQualifiedname = project.getFullPath().toString().replaceAll(fileSeparatorPattern(), ".");
		setDescription("RTT-Project settings for " + project.getFullPath());

		// add Project Database Name field
		rttProjectDatabaseField = new StringFieldEditor("RttMbtrttProjectDatabase",
                                                   "Project Database Name:",
                                                   getFieldEditorParent());
		rttProjectDatabaseField.setPreferenceStore(null);
		rttProjectDatabaseValue = getPropertyValue("RttMbtrttProjectDatabase");
		rttProjectDatabaseField.setStringValue(rttProjectDatabaseValue);
    	addField(rttProjectDatabaseField);

		// add Test Execution Context field
    	rttExeCtxNameField = new StringFieldEditor("RttMbtRttTprocPrefix",
                                                   "Test Execution Context:",
                                                   getFieldEditorParent());
    	rttExeCtxNameField.setPreferenceStore(null);
    	rttExeCtxName = getPropertyValue("RttMbtRttTprocPrefix");
    	rttExeCtxNameField.setStringValue(rttExeCtxName);
    	addField(rttExeCtxNameField);

		// add Test Generation Context field
    	rttTgenCtxNameField = new StringFieldEditor("RttMbtTProcGenCtx",
                                                   "Test Generation Context:",
                                                   getFieldEditorParent());
    	rttTgenCtxNameField.setPreferenceStore(null);
    	rttTgenCtxName = getPropertyValue("RttMbtTProcGenCtx");
    	rttTgenCtxNameField.setStringValue(rttTgenCtxName);
    	addField(rttTgenCtxNameField);

		// add make tool field
    	makeToolField = new StringFieldEditor("RttMbtSutMakeTool",
                                              "Make tool for SUT code:",
                                              getFieldEditorParent());
    	makeToolField.setPreferenceStore(null);
    	makeToolProperty = getPropertyValue("RttMbtSutMakeTool");
    	makeToolField.setStringValue(makeToolProperty);
    	addField(makeToolField);

		// add file ignore pattern field
    	ignorePatternUploadField = new StringFieldEditor("RttMbtFileIgnorePattern",
                                                         "Test Generation Context:",
                                                         getFieldEditorParent());
    	ignorePatternUploadField.setPreferenceStore(null);
    	ignorePatternUploadProperty = getPropertyValue("RttMbtFileIgnorePattern");
    	ignorePatternUploadField.setStringValue(ignorePatternUploadProperty);
    	addField(ignorePatternUploadField);

	}

	public void performDefaults() {
		rttProjectDatabaseField.setStringValue(client.getDefaultProjectDatabaseName());
    	rttExeCtxNameField.setStringValue("TestExecution");
    	rttTgenCtxNameField.setStringValue("TestGeneration");
		makeToolField.setStringValue("make");
		ignorePatternUploadField.setStringValue(".svn:.git:*.o");
		super.performDefaults();
	}

	public boolean performOk() {
		if (project != null) {

			// project database name
			setPropertyValue("RttMbtrttProjectDatabase", rttProjectDatabaseField.getStringValue());

			// test execution context
			setPropertyValue("RttMbtRttTprocPrefix", rttExeCtxNameField.getStringValue());

			// test generation context
			setPropertyValue("RttMbtTProcGenCtx", rttTgenCtxNameField.getStringValue());

			// make tool
			setPropertyValue("RttMbtSutMakeTool", makeToolField.getStringValue());

			// file ignore pattern for upload/download
			setPropertyValue("RttMbtFileIgnorePattern", ignorePatternUploadField.getStringValue());

			return super.performOk();
		} else {
			return super.performOk();
		}
	}

	public boolean performCancel() {
		if (project != null) {

			// project database name
			setPropertyValue("RttMbtrttProjectDatabase", rttProjectDatabaseValue);

			// test execution context
			setPropertyValue("RttMbtRttTprocPrefix", rttExeCtxName);

			// test generation context
			setPropertyValue("RttMbtTProcGenCtx", rttTgenCtxName);

			// make tool
			setPropertyValue("RttMbtSutMakeTool", makeToolProperty);

			// file ignore pattern for upload/download
			setPropertyValue("RttMbtFileIgnorePattern", ignorePatternUploadProperty);

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

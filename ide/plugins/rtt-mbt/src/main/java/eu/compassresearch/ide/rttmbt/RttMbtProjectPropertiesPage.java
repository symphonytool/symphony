package eu.compassresearch.ide.rttmbt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPropertyPage;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtAdvConfParser;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtMaxStepsParser;

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

	// path of test procedure generation context (if selected)
	private String path;

	// configuration settings
	private IntegerFieldEditor MaxSolverStepsField;
	private Integer MaxSolverStepsProperty;
	private IntegerFieldEditor MaxSimStepsField;
	private Integer MaxSimStepsProperty;
	private IntegerFieldEditor AbstractInterpreterField;
	private Integer AbstractInterpreterProperty;
	private Integer SanityChecksProperty;
	private IntegerFieldEditor RobustnessTestingField;
	private Integer RobustnessTestingProperty;
	private IntegerFieldEditor RobustnessPercentageField;
	private Integer RobustnessPercentageProperty;
	private IntegerFieldEditor MaxSimultaneousInputChangesField;
	private Integer MaxSimultaneousInputChangesProperty;
	private IntegerFieldEditor MinDurationBetweenInputChangesField;
	private Integer MinDurationBetweenInputChangesProperty;
	private IntegerFieldEditor MaxDurationBetweenInputChangesField;
	private Integer MaxDurationBetweenInputChangesProperty;
	private IntegerFieldEditor ModelCheckingField;
	private Integer ModelCheckingProperty;
	private Integer GoalCoverageProperty;
	private Integer BacktrackingProperty;
	private Integer LoggerProperty;
	private Integer MaxModelCoverageProperty;
	private Integer VerbosityLevel;
	private RttMbtAdvConfParser advancedConf = new RttMbtAdvConfParser();
	private RttMbtMaxStepsParser maxSteps = new RttMbtMaxStepsParser();
	private StringFieldEditor CmlProjectNameField;
	private String CmlProjectNameProperty;
	private StringFieldEditor CmlProcessNameField;
	private String CmlProcessNameProperty;

	public RttMbtProjectPropertiesPage() {
		super(GRID);
		setDescription("RTT-Project settings");
    	project = null;
    	path = null;
    	projectQualifiedname = null;
    	client = Activator.getClient();
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

	/**
	 * read all property values from max_steps.txt and advanced.conf.csv
	 * into property variables
	 */
	private void readTgenConfig() {
		advancedConf.readAdvancedConfig(path + File.separator + "conf" + File.separator + "advanced.conf");
		maxSteps.readMaxSteps(path + File.separator + "conf" + File.separator + "max_steps.txt");
		// read properties without editors
		GoalCoverageProperty = advancedConf.getGC() ? 1 : 0;
		BacktrackingProperty = advancedConf.getBT() ? 1 : 0;
		LoggerProperty = advancedConf.getLO() ? 1 : 0;
		MaxModelCoverageProperty = advancedConf.getMM() ? 1 : 0;
		SanityChecksProperty = advancedConf.getSC() ? 1 : 0;
		VerbosityLevel = advancedConf.getVL();
	}

	/**
	 * write changes to configuration files if necessary.
	 */
	private void writeTgenConfig() {
		File output = null;
		Writer writer = null;
		BufferedWriter bWriter = null;
		try {
			// write max_steps.txt
			output = new File(path + File.separator + "conf" + File.separator + "max_steps.txt");
			writer = new FileWriter(output);
			bWriter = new BufferedWriter(writer);
			bWriter.write("MAX SOLVER STEPS;" + Integer.toString(MaxSolverStepsProperty) + "\n");
			bWriter.write("MAX SIMULATION STEPS;" + Integer.toString(MaxSimStepsProperty) + "\n");
			bWriter.close();

			// write advanced.conf
			output = new File(path + File.separator + "conf" + File.separator + "advanced.conf");
			writer = new FileWriter(output);
			bWriter = new BufferedWriter(writer);
			bWriter.write("GC;" + Integer.toString(GoalCoverageProperty) + ";if 1, cover all goals in d addgoals(ordered).conf, even if they are already covered by other procedures\n");
			bWriter.write("BT;" + Integer.toString(BacktrackingProperty) + ";switch back tracking on if 1\n");
			bWriter.write("LO;" + Integer.toString(LoggerProperty) + ";produce logger threads instead of checkers if 1\n");
			bWriter.write("AI;" + Integer.toString(AbstractInterpreterProperty) + ";use abstract interpretation for speed-up of solver, if 1\n");
			bWriter.write("MM;" + Integer.toString(MaxModelCoverageProperty) + ";maximise model coverage if 1\n");
			bWriter.write("SC;" + Integer.toString(SanityChecksProperty) + ";perform sanity checks in solver and abstract interpreter if 1\n");
			bWriter.write("RB;" + Integer.toString(RobustnessTestingProperty) + ";do robustness testing if 1\n");
			bWriter.write("RP;" + Integer.toString(RobustnessPercentageProperty) + ";if RB=1 RP defines the percentage of robustness transitions to be performed\n");
			bWriter.write("CI;" + Integer.toString(MaxSimultaneousInputChangesProperty) + ";maximal number of simultaneous input changes\n");
			bWriter.write("DI;" + Integer.toString(MinDurationBetweenInputChangesProperty) + ";minimal duration between two input changes\n");
			bWriter.write("LI;" + Integer.toString(MaxDurationBetweenInputChangesProperty) + ";upper limit for duration between two input changes\n");
			bWriter.write("MC;" + Integer.toString(ModelCheckingProperty) + ";Perform model checking instead of test generation, if 1\n");
			bWriter.write("VL;" + Integer.toString(VerbosityLevel) + ";Defines the verbosity level for the output (Range 0..4)\n");
			bWriter.close();
		} catch (IOException e) {
			client.addErrorMessage("problem writing test procedure generation context properties!");
		}
	}

	private boolean containsRttProject(String location) {

		if (location == null) {
			return false;
		}

		if (RttMbtClient.isRttProject(location)) {
			return true;
		}

		int pos = location.lastIndexOf("/");
		if (pos == -1) {
			pos = location.lastIndexOf(File.separator);
		}
		while (pos > 0) {
			location = location.substring(0, pos);
			System.out.println("testing path '" + location + "'");
			if (RttMbtClient.isRttProject(location)) {
				return true;
			}

			// get next file separator char
			pos = location.lastIndexOf("/");
			if (pos == -1) {
				pos = location.lastIndexOf(File.separator);
			}
		}

		return false;
	}

	public void createFieldEditors() {
		String tooltip;

		// check if this project in RT-Tester project
		String location = null;
		if (element instanceof IFolder) {
			IFolder folder = (IFolder)element;
			project = folder.getProject();
    		path = RttMbtClient.getAbsolutePathFromFileURI(folder.getLocationURI());
			location = path;
		} else if (element instanceof IProject) {
			project = (IProject)element;
			path = null;
			location = null;
		} else if (element instanceof IFile) {
			IFile file = (IFile)element;
			project = file.getProject();
			path = null;
			location = RttMbtClient.getAbsolutePathFromFileURI(file.getLocationURI());
		} else {
			project = null;
			path = null;
			return;
		}

		// check for non-RT-Tester projects
		if (project == null) {
			return;
		}
		String projectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
		if ((!RttMbtClient.isRttProject(projectFilesystemPath)) && (!containsRttProject(location))) {
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

		// add Project Database Name field
		tooltip = "The name of the RTT-TMS project database. Note: the database is not used in the current implementation, but will contain persistent information about the project in later versions.";
		rttProjectDatabaseField = new StringFieldEditor("RttMbtrttProjectDatabase",
                                                   "Project Database Name:",
                                                   getFieldEditorParent());
		rttProjectDatabaseField.setPreferenceStore(null);
		rttProjectDatabaseField.setEmptyStringAllowed(true);
		rttProjectDatabaseField.setStringValue("none");
		rttProjectDatabaseValue = getPropertyValue("RttMbtrttProjectDatabase");
		if (rttProjectDatabaseValue == null) { rttProjectDatabaseValue = ""; }
		rttProjectDatabaseField.setStringValue(rttProjectDatabaseValue);
		rttProjectDatabaseField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		rttProjectDatabaseField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(rttProjectDatabaseField);

		// add Test Execution Context field
		tooltip = "The name of the directory in the project where the generation of all model based test procedures is prepared. Note: the generation of each of the test procedures that are to be generated is stored in a separate sub directory of this directory.";
    	rttExeCtxNameField = new StringFieldEditor("RttMbtRttTprocPrefix",
                                                   "Test Execution Context:",
                                                   getFieldEditorParent());
    	rttExeCtxNameField.setEmptyStringAllowed(true);
    	rttExeCtxNameField.setStringValue("none");
    	rttExeCtxNameField.setPreferenceStore(null);
    	rttExeCtxName = getPropertyValue("RttMbtRttTprocPrefix");
		if (rttExeCtxName == null) { rttExeCtxName = ""; }
    	rttExeCtxNameField.setStringValue(rttExeCtxName);
    	rttExeCtxNameField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	rttExeCtxNameField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(rttExeCtxNameField);

		// add Test Generation Context field
		tooltip = "The name of the directory inside the project in which all test procedures are generated. Note: each test procedure is generated in a separate sub directory of this directory.";
    	rttTgenCtxNameField = new StringFieldEditor("RttMbtTProcGenCtx",
                                                   "Test Generation Context:",
                                                   getFieldEditorParent());
    	rttTgenCtxNameField.setEmptyStringAllowed(true);
    	rttTgenCtxNameField.setStringValue("none");
    	rttTgenCtxNameField.setPreferenceStore(null);
    	rttTgenCtxName = getPropertyValue("RttMbtTProcGenCtx");
		if (rttTgenCtxName == null) { rttTgenCtxName = ""; }
    	rttTgenCtxNameField.setStringValue(rttTgenCtxName);
    	rttTgenCtxNameField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	rttTgenCtxNameField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(rttTgenCtxNameField);

		// add make tool field
		tooltip = "The make tool that is used on the RTT-MBT server to build make targets";
    	makeToolField = new StringFieldEditor("RttMbtSutMakeTool",
                                              "Make tool for SUT code:",
                                              getFieldEditorParent());
    	makeToolField.setEmptyStringAllowed(true);
    	makeToolField.setStringValue("none");
    	makeToolField.setPreferenceStore(null);
    	makeToolProperty = getPropertyValue("RttMbtSutMakeTool");
		if (makeToolProperty == null) { makeToolProperty = ""; }
    	makeToolField.setStringValue(makeToolProperty);
    	makeToolField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	makeToolField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(makeToolField);

		// add file ignore pattern field
		tooltip = "This field contains a colon (':') separated list of patterns. Only files that do NOT match all of these patterns are uploaded to or downloaded from the RTT-MBT server";
    	ignorePatternUploadField = new StringFieldEditor("RttMbtFileIgnorePattern",
                                                         "Ignore Pattern for File Transfer:",
                                                         getFieldEditorParent());
    	ignorePatternUploadField.setEmptyStringAllowed(true);
    	ignorePatternUploadField.setStringValue("none");
    	ignorePatternUploadField.setPreferenceStore(null);
    	ignorePatternUploadProperty = getPropertyValue("RttMbtFileIgnorePattern");
		if (ignorePatternUploadProperty == null) { ignorePatternUploadProperty = ""; }
    	ignorePatternUploadField.setStringValue(ignorePatternUploadProperty);
    	ignorePatternUploadField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	ignorePatternUploadField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(ignorePatternUploadField);

		// add CML project name field
		tooltip = "This field contains the name of an associated CML project. A selected CML process in the CML project can be used as the SUT for test data generated in this RTT-MBT project.";
    	CmlProjectNameField = new StringFieldEditor("RttMbtCmlProjectName",
                                                    "Associated CML Project:",
                                                    getFieldEditorParent());
    	CmlProjectNameField.setEmptyStringAllowed(true);
    	CmlProjectNameField.setStringValue("none");
    	CmlProjectNameField.setPreferenceStore(null);
    	CmlProjectNameProperty = getPropertyValue("RttMbtCmlProjectName");
		if (CmlProjectNameProperty == null) { CmlProjectNameProperty = ""; }
    	CmlProjectNameField.setStringValue(CmlProjectNameProperty);
    	CmlProjectNameField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	CmlProjectNameField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(CmlProjectNameField);

		// add CML process name field
		tooltip = "This field contains the name of an associated CML process. This is a selected CML process in the associated CML project that can be used as the SUT for test data generated in this RTT-MBT project.";
    	CmlProcessNameField = new StringFieldEditor("RttMbtCmlProcessName",
                                                    "Associated CML Process:",
                                                    getFieldEditorParent());
    	CmlProcessNameField.setEmptyStringAllowed(true);
    	CmlProcessNameField.setStringValue("none");
    	CmlProcessNameField.setPreferenceStore(null);
    	CmlProcessNameProperty = getPropertyValue("RttMbtCmlProcessName");
		if (CmlProcessNameProperty == null) { CmlProcessNameProperty = ""; }
    	CmlProcessNameField.setStringValue(CmlProcessNameProperty);
    	CmlProcessNameField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    	CmlProcessNameField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    	addField(CmlProcessNameField);

    	if ((path != null) && (RttMbtClient.isRttMbtTestProcedure(path))) {
    		readTgenConfig();

    		// add max. solver steps field
    		tooltip = "The maximal number of model execution steps from " +
    				"the current model state that the constraint solver will  perform to look for a " +
    				"solution of the test objective to be fulfilled. A value between 20 and 100 is " +
    				"suitable for most projects.";
    		MaxSolverStepsField = new IntegerFieldEditor("RttMbtMaxSolverSteps",
    				"Max. Solver Steps:",
    				getFieldEditorParent());
    		MaxSolverStepsField.setPreferenceStore(null);
    		MaxSolverStepsProperty = maxSteps.getMaxSolverSteps();
    		MaxSolverStepsField.setStringValue(MaxSolverStepsProperty.toString());
    		MaxSolverStepsField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		MaxSolverStepsField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(MaxSolverStepsField);

    		// add max. simulation steps field
    		tooltip = "The maximal number of simulation steps to be " +
    				"performed by the generator without covering any new model transitions: if this " +
    				"number is greater than 0, the generator will try to find test data for a test " +
    				"objective also by means of random walks through the model, if the constraint " +
    				"solver could not solve the goal within the given number of steps from the " +
    				"current state.\n" +
    				"A random walk is continued as long as new portions of the model are covered by " +
    				"this walk. If a simulation step fails to cover a new model element the initial " +
    				"value specified by this parameter is decremented. The following simulation steps " +
    				"continue to decrement this value until a new model element is covered or the " +
    				"value becomes zero. In the former case the value is set back to the value of this parameter. " +
    				"In the latter case the simulation is stopped and the constraint solver " +
    				"tries to reach (one of) the remaining test goals from the model state reached " +
    				"by the simulation.  " +
    				"The default value is 0 (no simulation with random data generation).";
    		MaxSimStepsField = new IntegerFieldEditor("RttMbtMaxSimSteps",
    				"Max. Simulator Steps:",
    				getFieldEditorParent());
    		MaxSimStepsField.setPreferenceStore(null);
    		MaxSimStepsProperty = maxSteps.getMaxSimulationSteps();
    		MaxSimStepsField.setStringValue(MaxSimStepsProperty.toString());
    		MaxSimStepsField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		MaxSimStepsField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(MaxSimStepsField);

    		// add max. simultaneous input changes field
    		tooltip = "Maximal number of simultaneous input changes.\n" +
    				"This is a non-negative natural number. It specifies the" +
    				"number of inputs that may be changed simultaneously after a delay. " +
    				"The input vector to the SUT may be changed after " +
    				"time delays, during which the model state remained stable. " +
    				"In hardware-in-the-loop tests it may be desirable to change only a bounded number" +
    				"of inputs at a time, since the SUT reaction may become non-deterministic in " +
    				"presence of too many nearly simultaneous input changes. Therefore this parameter " +
    				"is set to 1 by default, meaning that after a delay at most one input " +
    				"to the SUT is allowed to be changed.\n" +
    				"For software testing, it is often allowed and even necessary to change several " +
    				"input variables to the SUT at the same time. If this is the case, this parameter " +
    				"should be set to a bound which is sufficiently high.";
    		MaxSimultaneousInputChangesField = new IntegerFieldEditor("RttMbtMaxSimultaneousInputChanges",
    				"Max. Number of simultaneous Input Changes:",
    				getFieldEditorParent());
    		MaxSimultaneousInputChangesField.setPreferenceStore(null);
    		MaxSimultaneousInputChangesProperty = advancedConf.getCI();
    		MaxSimultaneousInputChangesField.setStringValue(MaxSimultaneousInputChangesProperty.toString());
    		MaxSimultaneousInputChangesField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		MaxSimultaneousInputChangesField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(MaxSimultaneousInputChangesField);

    		// add min. duration between input changes
    		tooltip = "Minimal duration between two input changes.\n" +
    				"In hardware-in-the-loop testing the interface latency of the SUT has to be " +
    				"taken into account: if changes to the SUT occur with too high a frequency, the " +
    				"SUT will not be able to process them, because consecutive changes get lost" +
    				"already on input interface boards, or in buffers of the SUT runtime system. " +
    				"Therefore the minimal duration between input changes to the SUT should be " +
    				"respected by the testing environment. To this end, this parameter can be set to a nonnegative integer " +
    				"number, indicating the minimal duration between two input changes in time unit milliseconds.";
    		MinDurationBetweenInputChangesField = new IntegerFieldEditor("RttMbtMinDurationBetweenInputChanges",
    				"Min. Duration between Input Changes:",
    				getFieldEditorParent());
    		MinDurationBetweenInputChangesField.setPreferenceStore(null);
    		MinDurationBetweenInputChangesProperty = advancedConf.getDI();
    		MinDurationBetweenInputChangesField.setStringValue(MinDurationBetweenInputChangesProperty.toString());
    		MinDurationBetweenInputChangesField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		MinDurationBetweenInputChangesField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(MinDurationBetweenInputChangesField);

    		// add max. duration between input changes
    		tooltip = "Upper limit for the duration between two input changes.\n" +
    				"As well as changes to the SUT should not occur with too high a frequency, " +
    				"it can be useful if the tester can limit the mamimum duration between two " +
    				"input changes, to prevent the solver to generate tests that wait unreasonably " +
    				"long between input changes. This parameter can be set to a " +
    				"nonnegative integer number, indicating the upper limit for the duration " +
    				"between two input changes in time unit milliseconds. Note that the " +
    				"latency for signals has to be taken into account when defining this parameter. " +
    				"Otherwise it would be possible to define configuration in which " +
    				"the checker will never find problems in the SUT outputs.";
    		MaxDurationBetweenInputChangesField = new IntegerFieldEditor("RttMbtMaxDurationBetweenInputChanges",
    				"Max. Duration between Input Changes::",
    				getFieldEditorParent());
    		MaxDurationBetweenInputChangesField.setPreferenceStore(null);
    		MaxDurationBetweenInputChangesProperty = advancedConf.getLI();
    		MaxDurationBetweenInputChangesField.setStringValue(MaxDurationBetweenInputChangesProperty.toString());
    		MaxDurationBetweenInputChangesField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		MaxDurationBetweenInputChangesField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(MaxDurationBetweenInputChangesField);

    		// add use abstract interpreter field
    		tooltip = "Use abstract interpretation for speed-up of solver.";
    		AbstractInterpreterField = new IntegerFieldEditor("RttMbtAbstractInterpreter",
    				"Use Abstract Interpreter:",
    				getFieldEditorParent());
    		AbstractInterpreterField.setPreferenceStore(null);
    		AbstractInterpreterProperty = advancedConf.getAI() ? 1 : 0;
    		AbstractInterpreterField.setStringValue(AbstractInterpreterProperty.toString());
    		AbstractInterpreterField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		AbstractInterpreterField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(AbstractInterpreterField);

    		// add robustness testing field
    		tooltip = "Do robustness testing.\n" +
    				"If robustness tests should be performed by the test procedure to be generated, " +
    				"then this parameter has to be set to 1. In this case, the robustness " +
    				"transitions defined in TE state machines are not ignored (as it is the case when " +
    				"this parameter is 0), but are performed with the percentage specified in Robustess Percentage. " +
    				"Observe that setting this parameter to 1 only has an effect, if\n" +
    				"1. Robustess Percentage is greater than 0,\n" +
    				"2. TE components have been modelled, are active, and\n" +
    				"3. at least one TE state machine associated with an active TE component has robustness transitions.\n";
    		RobustnessTestingField = new IntegerFieldEditor("RttMbtRobustnessTesting",
    				"Robustness Testing:",
    				getFieldEditorParent());
    		RobustnessTestingField.setPreferenceStore(null);
    		RobustnessTestingProperty = advancedConf.getRB() ? 1 : 0;
    		RobustnessTestingField.setStringValue(RobustnessTestingProperty.toString());
    		RobustnessTestingField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		RobustnessTestingField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(RobustnessTestingField);

    		// add robustness testing percentage field
    		tooltip = "If Robustness testing is activated, this attribute defines the percentage of robustness transitions to be performed.\n" +
    				"Allowed values are natural numbers in range 0 --- 100. " +
    				"This parametr specifies the percentage of robustness transitions to be " +
    				"taken when residing in a TE state machine state from where some emanating " +
    				"transitions have been marked as robustness transitions, but " +
    				"also ordinary transitions exist. If the value of this parameter is RP, " +
    				"the test generator will fire approximately " +
    				"RP % robustness transitions from this state, and " + 
    				"(100 - RP) % normal behaviour transitions.";
    		RobustnessPercentageField = new IntegerFieldEditor("RttMbtRobustnessPercentage",
    				"Robustess Percentage:",
    				getFieldEditorParent());
    		RobustnessPercentageField.setPreferenceStore(null);
    		RobustnessPercentageProperty = advancedConf.getRP();
    		RobustnessPercentageField.setStringValue(RobustnessPercentageProperty.toString());
    		RobustnessPercentageField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		RobustnessPercentageField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(RobustnessPercentageField);

    		// add model checking field
    		tooltip = "Perform model checking instead of test generation.";
    		ModelCheckingField = new IntegerFieldEditor("RttMbtModelChecking",
    				"Model Checking:",
    				getFieldEditorParent());
    		ModelCheckingField.setPreferenceStore(null);
    		ModelCheckingProperty = advancedConf.getMC() ? 1 : 0;
    		ModelCheckingField.setStringValue(ModelCheckingProperty.toString());
    		ModelCheckingField.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
    		ModelCheckingField.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
    		addField(ModelCheckingField);

    	}
	}

	public void performDefaults() {
		if (rttProjectDatabaseField != null) { rttProjectDatabaseField.setStringValue(client.getDefaultProjectDatabaseName()); }
		if (rttExeCtxNameField != null) { rttExeCtxNameField.setStringValue("TestExecution"); }
		if (rttTgenCtxNameField != null) { rttTgenCtxNameField.setStringValue("TestGeneration"); }
		if (makeToolField != null) { makeToolField.setStringValue("make"); }
		if (ignorePatternUploadField != null) { ignorePatternUploadField.setStringValue(".svn:.git:*.o"); }
		if (CmlProjectNameField != null) { CmlProjectNameField.setStringValue(""); }
		if (CmlProcessNameField != null) { CmlProcessNameField.setStringValue(""); }
		if (MaxSolverStepsField != null) { MaxSolverStepsField.setStringValue(Integer.toString(100)); }
		if (MaxSimStepsField != null) { MaxSimStepsField.setStringValue(Integer.toString(0)); }
		if (AbstractInterpreterField != null) { AbstractInterpreterField.setStringValue(Integer.toString(1)); }
		if (RobustnessTestingField != null) { RobustnessTestingField.setStringValue(Integer.toString(0)); }
		if (RobustnessPercentageField != null) { RobustnessPercentageField.setStringValue(Integer.toString(0)); }
		if (MaxSimultaneousInputChangesField != null) { MaxSimultaneousInputChangesField.setStringValue(Integer.toString(1)); }
		if (MinDurationBetweenInputChangesField != null) { MinDurationBetweenInputChangesField.setStringValue(Integer.toString(10)); }
		if (MaxDurationBetweenInputChangesField != null) { MaxDurationBetweenInputChangesField.setStringValue(Integer.toString(10000)); }
		if (ModelCheckingField != null) { ModelCheckingField.setStringValue(Integer.toString(0)); }
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

			// associated CML project/process
			setPropertyValue("RttMbtCmlProjectName", CmlProjectNameField.getStringValue());
			setPropertyValue("RttMbtCmlProcessName", CmlProcessNameField.getStringValue());

			if ((path != null) && (RttMbtClient.isRttMbtTestProcedure(path))) {
				// max solver steps
				setPropertyValue("RttMbtMaxSolverSteps", MaxSolverStepsField.getStringValue());

				// max simulation steps
				setPropertyValue("RttMbtMaxSimSteps", MaxSimStepsField.getStringValue());

				// abstract interpreter
				setPropertyValue("RttMbtAbstractInterpreter", AbstractInterpreterField.getStringValue());

				// robustness testing
				setPropertyValue("RttMbtRobustnessTesting", RobustnessTestingField.getStringValue());

				// robustness percentage
				setPropertyValue("RttMbtRobustnessPercentage", RobustnessPercentageField.getStringValue());

				// max. simultanious inputs
				setPropertyValue("RttMbtMaxSimultaneousInputChanges", MaxSimultaneousInputChangesField.getStringValue());

				// min. duration between input changes
				setPropertyValue("RttMbtMinDurationBetweenInputChanges", MinDurationBetweenInputChangesField.getStringValue());

				// max. duration between input changes
				setPropertyValue("RttMbtMaxDurationBetweenInputChanges", MaxDurationBetweenInputChangesField.getStringValue());

				// model checking
				setPropertyValue("RttMbtModelChecking", ModelCheckingField.getStringValue());

	    		// copy from Integer field string values to property variables
	    		MaxSolverStepsProperty = Integer.parseInt(MaxSolverStepsField.getStringValue());
	    		MaxSimStepsProperty = Integer.parseInt(MaxSimStepsField.getStringValue());
	    		AbstractInterpreterProperty = Integer.parseInt(AbstractInterpreterField.getStringValue());
	    		RobustnessTestingProperty = Integer.parseInt(RobustnessTestingField.getStringValue());
	    		RobustnessPercentageProperty = Integer.parseInt(RobustnessPercentageField.getStringValue());
	    		MaxSimultaneousInputChangesProperty = Integer.parseInt(MaxSimultaneousInputChangesField.getStringValue());
	    		MinDurationBetweenInputChangesProperty = Integer.parseInt(MinDurationBetweenInputChangesField.getStringValue());
	    		MaxDurationBetweenInputChangesProperty = Integer.parseInt(MaxDurationBetweenInputChangesField.getStringValue());
	    		ModelCheckingProperty = Integer.parseInt(ModelCheckingField.getStringValue());

	    		// write property values to disc
	    		writeTgenConfig();
	    	}
	    	
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

			// associated CML project and process
			setPropertyValue("RttMbtCmlProjectName", CmlProjectNameProperty);
			setPropertyValue("RttMbtCmlProcessName", CmlProcessNameProperty);

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

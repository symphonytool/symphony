package eu.compassresearch.ide.rttmbt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.MultiPageEditorPart;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtAdvConfParser;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtRequirementsCoverage extends MultiPageEditorPart  {

	private enum Verdict {
		UNCOVERED,
		COVERED,
		NOTTESTED,
	    INCONCLUSIVE,
	    PASS,
	    FAIL,
	    TESTERROR,
	    UNDEFINED
	}

	private String Verdict2String(Verdict verdict) {
		String value = "";
		switch (verdict) {
		case UNCOVERED:
			value = "NOT COVERED";
			break;
		case COVERED:
			value = "COVERED";
			break;
		case NOTTESTED:
			value = "NOT TESTED";
			break;
		case INCONCLUSIVE:
			value = "INCONCLUSIVE";
			break;
		case PASS:
			value = "PASS";
			break;
		case FAIL:
			value = "FAIL";
			break;
		case TESTERROR:
			value = "TESTERROR";
			break;
		case UNDEFINED:				
			value = "UNDEFINED";
			break;
		}
		return value;
	}

	private RGB Verdict2RGB(Verdict verdict) {
		RGB value = null;
		switch (verdict) {
		case UNCOVERED:
			value = new RGB(255,255,0);
			break;
		case COVERED:
			value = new RGB(0,255,0);
			break;
		case NOTTESTED:
			value = new RGB(255,255,0);
			break;
		case INCONCLUSIVE:
			value = new RGB(255,255,0);
			break;
		case PASS:
			value = new RGB(0,255,0);
			break;
		case FAIL:
			value = new RGB(255,0,0);
			break;
		case TESTERROR:
			value = new RGB(255,0,0);
			break;
		case UNDEFINED:				
			value = new RGB(255,0,0);
			break;
		}
		return value;
	}

	String testproc;
	String testprocGenCtxFilesystemPath;
	Boolean performTest;
	Boolean IterTPGen;

	private class EnterTestProcGenCtxNameDialog extends TitleAreaDialog {

		private Text txtTestprocGenCtxName;
		private String testprocGenCtx;
		private Button performTestBox;
		private Boolean performTest;

		public EnterTestProcGenCtxNameDialog(Shell parentShell, String requirement) {
			super(parentShell);
			testprocGenCtx = "TP-" + requirement;
		}

		@Override
		public void create() {
			super.create();
			setTitle("Test Procedure Name");
			setMessage("Please enter the name of the test procedure that will be created.",
					   IMessageProvider.INFORMATION);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite container = new Composite(area, SWT.NONE);
			container.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
			GridLayout layout = new GridLayout(2, false);
			container.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
			container.setLayout(layout);
			createTestProcGenCtxName(container);
			return area;
		}

		private void createTestProcGenCtxName(Composite container) {

			// create label and input field
			Label lbtTestProcGenCtxName = new Label(container, SWT.NONE);
			lbtTestProcGenCtxName.setText("Test Procedure Name:");
			txtTestprocGenCtxName = new Text(container, SWT.BORDER);
			txtTestprocGenCtxName.setText(testprocGenCtx);
			GridData dataTestProcGenCtxName = new GridData();
			dataTestProcGenCtxName.grabExcessHorizontalSpace = true;
			dataTestProcGenCtxName.horizontalAlignment = GridData.FILL;
			txtTestprocGenCtxName.setLayoutData(dataTestProcGenCtxName);

			// create label and check box
			Label lbtPerformTest = new Label(container, SWT.NONE);
			lbtPerformTest.setText("Perform Test:");
			performTestBox = new Button(container, SWT.CHECK);
			performTestBox.setText("Start test generation and test execution after the new\n" +
			                       "test procedure generation context has been generated.");
			GridData performTestBoxData = new GridData();
			performTestBoxData.grabExcessHorizontalSpace = true;
			performTestBoxData.horizontalAlignment = GridData.FILL;
			performTestBox.setLayoutData(performTestBoxData);

		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		// save content of the Text fields because they get disposed
		// as soon as the Dialog closes
		private void saveInput() {
			testprocGenCtx = txtTestprocGenCtxName.getText();
			performTest = performTestBox.getSelection();
		}

		@Override
		protected void okPressed() {
			saveInput();
			super.okPressed();
		}

		public String getTestprocGenCtxName() {
			return testprocGenCtx;
		}

		public Boolean getPerformTest() {
			return performTest;
		}

	} 

	// widget to display the data
	private Tree reqTreeView;
	private Tree tcTreeView;

	// maps for file content
	private SortedMap<String,List<String>> req2tc = new TreeMap<String,List<String>>();
	private SortedMap<String,List<String>> tc2req = new TreeMap<String,List<String>>();
	private SortedMap<String,String> testcases = new TreeMap<String,String>();
	private SortedMap<String,Verdict> tccov = new TreeMap<String,Verdict>();
	private SortedMap<String,Verdict> reqcov = new TreeMap<String,Verdict>();
	
	// a reference to the RttMbtClient for log messages
	protected RttMbtClient client = null;
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// get RTT-MBT TMS client
    	client = Activator.getClient();
    	client.setConsoleName("Coverage");
    	// check input
		if (! (input instanceof IFileEditorInput) ) {
			throw new PartInitException("Invalid input! Expected file input");
		} else {
			IFileEditorInput iFileInput = (IFileEditorInput) input;
			IFile ifile = iFileInput.getFile();

			// init client according to selected file
			String selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(ifile.getLocationURI());
			String selectedObjectWorkspacePath = ifile.getFullPath().toString();
			String selectedObjectWorkspaceProjectName = ifile.getProject().getName();
			String selectedObjectWorkspaceProjectPath = ifile.getProject().getFullPath().toString();
			client.initPathVariablesFromSelection(selectedObjectFilesystemPath,
					                              selectedObjectWorkspacePath,
					                              selectedObjectWorkspaceProjectName,
					                              selectedObjectWorkspaceProjectPath);
	    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
			String workspacePath = workspace.getRoot().getLocation().toFile().getAbsolutePath();
			client.setWorkspacePath(workspacePath);
			client.setConsoleName(selectedObjectWorkspaceProjectName);
			IProject iproject = ifile.getProject();
			// if project specific folder names are defined: override global preferences.
			String RttMbtTProcGenCtx = RttMbtProjectPropertiesPage.getPropertyValue(iproject, "RttMbtTProcGenCtx");
			if ((RttMbtTProcGenCtx != null) && (RttMbtTProcGenCtx.length() > 0)) {
				client.setRttMbtTProcGenCtxFolderName(RttMbtTProcGenCtx);
			}
			String RttMbtRttTprocPrefix = RttMbtProjectPropertiesPage.getPropertyValue(iproject, "RttMbtRttTprocPrefix");
			if ((RttMbtRttTprocPrefix != null) && (RttMbtRttTprocPrefix.length() > 0)) {
				client.setRttMbtTestProcFolderName(RttMbtRttTprocPrefix);
			}

			// get the input stream
			InputStream istream;
			try {
				// create fileScanners for req2tc.csv and tc2req.csv
				istream = ifile.getContents();
				Scanner covScanner = new Scanner(istream);
				String modelpath = client.getRttProjectPath() + File.separator + "model" + File.separator;
				String req2tcpath = modelpath + "req2tc.csv";
				String tc2reqpath = modelpath + "tc2req.csv";
				String tcpath = modelpath + "testcases.csv";
				FileInputStream req2tcstream = new FileInputStream(req2tcpath);
				FileInputStream tc2reqstream = new FileInputStream(tc2reqpath);
				FileInputStream tcstream = new FileInputStream(tcpath);
				// file scanner initialised with input file
				Scanner req2tcScanner = new Scanner(req2tcstream);
				Scanner tc2reqScanner = new Scanner(tc2reqstream);
				Scanner tcScanner = new Scanner(tcstream);
				// read file content and create maps
				Scanner line;
				String key, value;
				List<String> list;
				// get mapping from requirements to test cases from req2tc.csv
				while (req2tcScanner.hasNextLine()) {
					line = new Scanner(req2tcScanner.nextLine());
					line.useDelimiter(";");
					if (line.hasNext()) {
						key = line.next();
						if (line.hasNext()) {
							value = line.next();
							if (!req2tc.containsKey(key)) {
								// create new entry
								list = new ArrayList<String>();
								list.add(value);
								req2tc.put(key, list);
							} else {
								// update entry
								list = req2tc.get(key);
								list.add(value);
								req2tc.put(key, list);
							}
						} else {
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
					}
				}
				// get mapping from test cases to requirements from tc2req.csv
				while (tc2reqScanner.hasNextLine()) {
					line = new Scanner(tc2reqScanner.nextLine());
					line.useDelimiter(";");
					if (line.hasNext()) {
						key = line.next();
						if (line.hasNext()) {
							value = line.next();
							if (!tc2req.containsKey(key)) {
								// create new entry
								list = new ArrayList<String>();
								list.add(value);
								tc2req.put(key, list);
							} else {
								// update entry
								list = tc2req.get(key);
								list.add(value);
								tc2req.put(key, list);
							}
						} else {
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
					}
				}
				// get list of all test cases from testcases.csv and initialize coverage with false;
				while (tcScanner.hasNextLine()) {
					line = new Scanner(tcScanner.nextLine());
					line.useDelimiter(";");
					if (line.hasNext()) {
						key = line.next();
						if (line.hasNext()) {
							value = line.next();
							testcases.put(key, value);
							tccov.put(key, Verdict.UNCOVERED);
						} else {
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv");
					}
				}
				// get test case coverage from covered_testcases.csv and update test case coverage
				while (covScanner.hasNextLine()) {
					Verdict verdict = Verdict.UNCOVERED;
					if (ifile.getName().compareTo("overall_coverage.csv") == 0) {
						key = covScanner.nextLine();
						verdict = Verdict.COVERED;
					} else if (ifile.getName().compareTo("covered_testcases.csv") == 0) {
						String raw = covScanner.nextLine();
						int start = raw.indexOf(';');
						int end = raw.lastIndexOf(';');
						if (start == -1 || end == -1) {
							key = raw;
							verdict = Verdict.COVERED;
						} else if (start == end) {
							key = raw.substring(start + 1);
							verdict = Verdict.COVERED;
						} else {
							key = raw.substring(start + 1, end);
							String covVerdict = raw.substring(end + 1);
							verdict = Verdict.INCONCLUSIVE;
							if (covVerdict.compareTo("PASS") == 0) {
								verdict = Verdict.PASS;
							}
							if (covVerdict.compareTo("FAIL") == 0) {
								verdict = Verdict.FAIL;
							}
						}
					} else {
						key = covScanner.nextLine();
						verdict = Verdict.COVERED;
					}
					if (!tccov.containsKey(key)) {
						client.addErrorMessage("[WARNING]: added coverage for unknown test case '" + key + "'");						
					}
					tccov.put(key, verdict);
				}
				// calculate requirements coverage
				Set<String> requirements = req2tc.keySet();
				Iterator<String> i = requirements.iterator();
				while (i.hasNext()) {
					String requirement = i.next();
					List<String> tcs = req2tc.get(requirement);
					Verdict verdict = Verdict.UNDEFINED;
					for (int idx = 0; idx < tcs.size(); idx++) {
						String testcase = tcs.get(idx);
						Verdict tcVerdict = tccov.get(testcase);
						switch(tcVerdict) {
						case NOTTESTED:
								verdict = Verdict.NOTTESTED;
							break;
						case PASS:
							if (verdict == Verdict.UNDEFINED) {
								verdict = Verdict.PASS;
							}
							break;
						case FAIL:
							if (verdict != Verdict.TESTERROR) {
								verdict = Verdict.FAIL;
							}
							break;
						case INCONCLUSIVE:
							if ((verdict != Verdict.FAIL) &&
								(verdict != Verdict.TESTERROR)) {
								verdict = Verdict.INCONCLUSIVE;
							}
							break;
						case TESTERROR:
							verdict = Verdict.TESTERROR;
							break;
						case COVERED:
							if (verdict == Verdict.UNDEFINED) {
								verdict = Verdict.COVERED;
							}
							break;
						case UNCOVERED:
							if ((verdict == Verdict.UNDEFINED) ||
								(verdict == Verdict.PASS) ||
								(verdict == Verdict.COVERED)) {
								verdict = Verdict.UNCOVERED;
							}
							break;
						default:
							verdict = Verdict.UNDEFINED;
							break;
						}
					}
					reqcov.put(requirement, verdict);
				}
				covScanner.close();
				req2tcScanner.close();
				tc2reqScanner.close();
				tcScanner.close();
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// @todo: add error here!
				client.addErrorMessage("[FAIL]: unable to find expected coverage file: " + e.getMessage() + "");
			}
		}
		setSite(site);
		setInput(input);
	}

	@Override
	protected void createPages() {
		createTc2ReqPage();
		createReq2TcPage();
	}

	private void addTestcase(SelectionEvent e) {
		Button button = (Button) e.getSource();
		String ltlformula = button.getData().toString();
		ElementListSelectionDialog selection =
				new ElementListSelectionDialog(getContainer().getShell(), new LabelProvider());
		selection.setTitle("Select test procedure generation context");
		File tprocRoot = new File(client.getRttProjectPath() + File.separator + client.getRttMbtTProcGenCtxFolderName());
		File[] files = tprocRoot.listFiles();
		List<File> subdirs = new ArrayList<File>();
		List<File> testprocs = new ArrayList<File>();
		int idx;
		if (files != null) {
			for (idx = 0; idx < files.length; idx++) {
				if (files[idx].isDirectory()) {
					subdirs.add(files[idx]);
				}
			}
		} else {
			client.addErrorMessage("unable to find test generation context directories!");
		}
		for (idx = 0; idx < subdirs.size(); idx++) {
			File conf = new File(subdirs.get(idx), "conf");
			if (conf.isDirectory()) {
				File configuration = new File(conf, "configuration.csv");
				if (configuration.isFile()) {
					testprocs.add(subdirs.get(idx));
				}
			}
		}
		String[] names = new String[testprocs.size()];
		for (idx = 0; idx < testprocs.size(); idx++) {
			names[idx] = testprocs.get(idx).getName();
		}
		selection.setElements(names);
		if (selection.open() == Window.OK) {
			String testproc = (String) selection.getFirstResult();
			// get additional goal file of selected test procedure
			File addgoals = null;
			for (idx = 0; idx < testprocs.size(); idx++) {
				if (testproc.compareTo(testprocs.get(idx).getName()) == 0) {
					addgoals = new File(testprocs.get(idx), File.separator + "conf" + File.separator + "addgoals.conf");
					// add test case to additional goals
					try {
						FileWriter addgoalStream = new FileWriter(addgoals.getAbsolutePath(), true);
						BufferedWriter append = new BufferedWriter(addgoalStream);
						append.write(ltlformula + "\n");
						append.close();
						addgoalStream.close();
						client.addLogMessage("added '" + ltlformula + "'");
						client.addLogMessage("to '" + addgoals.getAbsolutePath() + "'");
					} catch (IOException ex) {
						client.addErrorMessage("Unable to open '" + addgoals.getAbsolutePath() + "' for writing!");
					}
					idx = testprocs.size();
				}
			}
		} else {
			client.addErrorMessage("unable to open test generation context seleciton dialog");
		}
	}

	// add all test cases for a requirement
	private void addTestcaseForRequirement(SelectionEvent e) {
		Button button = (Button) e.getSource();
		String requirement = button.getData().toString();

		// create list of test cases (goals) for this requirement
		String goals = "";
		List<String> tcs = req2tc.get(requirement);
		if (tcs.size() == 0) {
			client.addLogMessage("no test cases found for requirement " + requirement + "!");
			return;
		}
		for (int idx = 0; idx < tcs.size(); idx++) {
			String tcTag = tcs.get(idx);
		    goals = goals + tcTag + ";" + testcases.get(tcTag) + ";\n";
		}
		
		ElementListSelectionDialog selection =
				new ElementListSelectionDialog(getContainer().getShell(), new LabelProvider());
		selection.setTitle("Select test procedure generation context");
		File tprocRoot = new File(client.getRttProjectPath() + File.separator + client.getRttMbtTProcGenCtxFolderName());
		File[] files = tprocRoot.listFiles();
		List<File> subdirs = new ArrayList<File>();
		List<File> testprocs = new ArrayList<File>();
		int idx;
		if (files != null) {
			for (idx = 0; idx < files.length; idx++) {
				if (files[idx].isDirectory()) {
					subdirs.add(files[idx]);
				}
			}
		} else {
			client.addErrorMessage("unable to find test generation context directories!");
		}
		for (idx = 0; idx < subdirs.size(); idx++) {
			File conf = new File(subdirs.get(idx), "conf");
			if (conf.isDirectory()) {
				File configuration = new File(conf, "configuration.csv");
				if (configuration.isFile()) {
					testprocs.add(subdirs.get(idx));
				}
			}
		}
		String[] names = new String[testprocs.size()];
		for (idx = 0; idx < testprocs.size(); idx++) {
			names[idx] = testprocs.get(idx).getName();
		}
		selection.setElements(names);
		if (selection.open() == Window.OK) {
			String testproc = (String) selection.getFirstResult();
			// get additional goal file of selected test procedure
			File addgoals = null;
			for (idx = 0; idx < testprocs.size(); idx++) {
				if (testproc.compareTo(testprocs.get(idx).getName()) == 0) {
					addgoals = new File(testprocs.get(idx), File.separator + "conf" + File.separator + "addgoals.conf");
					// add test case to additional goals
					try {
						FileWriter addgoalStream = new FileWriter(addgoals.getAbsolutePath(), true);
						BufferedWriter append = new BufferedWriter(addgoalStream);
						append.write(goals);
						append.close();
						addgoalStream.close();
						client.addLogMessage("added test cases for requirement " + requirement + ":\n" + goals + "to '" + addgoals.getAbsolutePath() + "'");
					} catch (IOException ex) {
						client.addErrorMessage("Unable to open '" + addgoals.getAbsolutePath() + "' for writing!");
					}
					idx = testprocs.size();
				}
			}
		} else {
			client.addErrorMessage("unable to open test generation context seleciton dialog");
		}
	}

	// create new test procedure generation context for a single test case
	private void TestSingleTestCase(SelectionEvent e) {
		Button button = (Button) e.getSource();
		String ltlformula = button.getData().toString();
		int idx = ltlformula.indexOf(';');
		String tag = "";
		if (idx > 0) {
			tag = ltlformula.substring(0, idx);
		}
		// do NOT use iterative test procedure generation
		IterTPGen = false;
		CreateAndExecuteTest(tag, ltlformula);
	}

	// create new test procedure generation context for with all test cases of the requirement as goals
	private void TestRequirement(SelectionEvent e) {
		Button button = (Button) e.getSource();
		String requirement = button.getData().toString();

		// create list of test cases (goals) for this requirement
		String goals = "";
		List<String> tcs = req2tc.get(requirement);
		if (tcs.size() == 0) {
			client.addLogMessage("no test cases found for requirement " + requirement + "!");
			return;
		}
		for (int idx = 0; idx < tcs.size(); idx++) {
			String tcTag = tcs.get(idx);
			goals = goals + tcTag + ";" + testcases.get(tcTag) + ";\n";
		}
		// use iterative test procedure generation
		IterTPGen = true;
		CreateAndExecuteTest(requirement, goals);
	}

	// perform (iterative) test generation and execution
	private void CreateAndExecuteTest(String requirement, String goals) {
		
		EnterTestProcGenCtxNameDialog dialog = new EnterTestProcGenCtxNameDialog(getContainer().getShell(), requirement);

		if (dialog.open() == Window.OK) {
			// retrieve test procedure name from dialog
			testproc = (String) dialog.getTestprocGenCtxName();
			performTest = (Boolean) dialog.getPerformTest();
		} else {
			return;
		}
		// path to new test procedure generation context
		testprocGenCtxFilesystemPath = client.getRttProjectPath() + File.separator +
				client.getRttMbtTProcGenCtxFolderName() + File.separator +
				testproc;
		File testProcDir = new File(testprocGenCtxFilesystemPath);
		if (testProcDir.exists()) {
			client.addErrorMessage("'" + testproc + "' already exists.");
			return;
		}

		// download templates to local workspace
		if (!client.downloadTemplates()) {
			client.addErrorMessage("unable to download test procedure generation context template from RTT-MBT server.");
			return;
		}

		// create new test procedure from template
		if (!client.createTestProcGenCtxFromTemplate(testproc))  {
			client.addErrorMessage("a problem occurred when creating a new test procedure generation context from the RTT-MBT template.");
			return;
		}

		if (IterTPGen) {
			// add flag 'IterTPGen;1' to advanced.conf of new test procedure generation context
			RttMbtAdvConfParser advConf = new RttMbtAdvConfParser();
			String advConfPath = testprocGenCtxFilesystemPath + File.separator + "conf" + File.separator + "advanced.conf";
			if (!advConf.readAdvancedConfig(advConfPath)) {
				client.addErrorMessage("unable to read advanced.conf '" + advConfPath + "'");
				return;
			}
			if (advConf.getIterTPGen() != 1) {
				advConf.setIterTPGen(1);
				advConf.writeAdvancedConfig(advConfPath);
			}
		}

		// add 'goals' to additional goal file of new test procedure generation context
		File addgoals = new File(testprocGenCtxFilesystemPath + File.separator + "conf" + File.separator + "addgoals.conf");
		try {
			FileWriter addgoalStream = new FileWriter(addgoals.getAbsolutePath(), true);
			BufferedWriter append = new BufferedWriter(addgoalStream);
			append.write(goals);
			append.close();
			addgoalStream.close();
		} catch (IOException ex) {
			client.addErrorMessage("Unable to open '" + addgoals.getAbsolutePath() + "' for writing!");
			return;
		}
		client.addLogMessage("sucessfully created new test procedure generation context " + testproc + ".");
		if (client.getVerboseLogging()) {
			client.addLogMessage("Test Goals of " + testproc + ":\n" + goals);
		}

		if (!performTest) {
			// check box for test generation and execution not checked in dialog
			return;
		}

		// generate test procedure(s)
		Job job = new Job("Testing "+ testproc) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				// perform actions
				IStatus overallStatus = Status.OK_STATUS;
				Activator.setMonitor(monitor);
				int idx = 1;
				String oldPostfix = "";
				String newPostfix = "_" + idx;
				Boolean success = true;
				while (success) {
					// generate test procedure
					client.addLogMessage("Generate test procedure " + testproc + oldPostfix);
					success = client.generateTestProcedure(testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// check if any goals are covered by the generated test procedure
					File covered = new File(testprocGenCtxFilesystemPath + oldPostfix + File.separator + "log" + File.separator + "covered_testcases.csv");
					if (covered.isFile()) {
						if (covered.length() == 0) {
							client.addLogMessage("no test goals covered by generated test procedure '" + testproc + oldPostfix + "' - stopping test");
							break;
						}
					} else {
						client.addLogMessage("no test goals covered by generated test procedure '" + testproc + oldPostfix + "' - stopping test.");
						break;
					}

					// clean test procedure
					client.addLogMessage("Clean test procedure " + testproc + oldPostfix);
					success = client.cleanTestProcedure(client.getRttMbtTestProcFolderName() + File.separator + testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// compile test procedure
					client.addLogMessage("Compile test procedure " + testproc + oldPostfix);
					success = client.compileTestProcedure(client.getRttMbtTestProcFolderName() + File.separator + testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// run test procedure
					client.addLogMessage("Run test procedure " + testproc + oldPostfix);
					success = client.runTestProcedure(client.getRttMbtTestProcFolderName() + File.separator + testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// reply test procedure
					client.addLogMessage("Replay test results of test procedure " + testproc + oldPostfix);
					success = client.replayTestProcedure(testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// doc test procedure
					client.addLogMessage("Generate documentation for test procedure " + testproc + oldPostfix);
					success = client.docTestProcedure(client.getRttMbtTestProcFolderName() + File.separator + testproc + oldPostfix);
					if (!success) { break; }
					if (client.isCurrentTaskCanceled()) { break; }

					// check if all goals have been covered
					success = client.prepareNextTestProcedureGeneration(testproc, oldPostfix, newPostfix);
					File tgenCtx = new File(testprocGenCtxFilesystemPath + newPostfix);
					if (!tgenCtx.isDirectory()) {
						client.addLogMessage("No test procedure generation context for remaining goals found. " +
								"Either all goals have been covered or remaining goals cannot " +
								"be covered - stopping test generation.");
						success = false;
					} else {
						client.addLogMessage("A new test procedure generation context for the remaining goals has been created - continue test generation with '" + testproc + newPostfix + "'.");
					}
					if (client.isCurrentTaskCanceled()) { break; }

					// calculate next postfix
					idx++;
					oldPostfix = newPostfix;
					newPostfix = "_" + idx;
				}

				// cleanup
				Activator.setMonitor(null);
				return overallStatus;
			}
		};
		job.schedule();
	}

	// testcase coverage
	private void createTc2ReqPage() {
		// create tree view
		tcTreeView = new Tree(getContainer(), SWT.H_SCROLL | SWT.V_SCROLL);
		tcTreeView.setHeaderVisible(true);
		int index = addPage(tcTreeView);
		setPageText(index,"Testcase Coverage");

		// header:
		String[] tc2reqHeader = {"Name", "Status", "Additional Goals"};
		for (int idx = 0; idx < tc2reqHeader.length; idx++) {
			TreeColumn column = new TreeColumn(tcTreeView, SWT.LEFT);
			column.setText(tc2reqHeader[idx]);
			column.setResizable(true);
			tcTreeView.getColumn(idx).pack();
		}

		String tcTag, reqTag;
		Set<String> tcSet = testcases.keySet();
		Iterator<String> tcIt = tcSet.iterator();
		while (tcIt.hasNext()) {
			tcTag = tcIt.next();
			TreeItem reqParent = null;
			// add test case
			reqParent = new TreeItem(tcTreeView, SWT.NONE);
			reqParent.setText(0, tcTag);
			reqParent.setText(1, Verdict2String(tccov.get(tcTag)));
			reqParent.setBackground(1, new Color(Display.getDefault(), Verdict2RGB(tccov.get(tcTag))));
			// create button to add test cases
			TreeEditor editor = new TreeEditor(tcTreeView);
			editor.horizontalAlignment = SWT.LEFT;
			editor.minimumWidth = 40;
			editor.minimumHeight = 12;
		    Button cellEditor = new Button(tcTreeView, SWT.PUSH);
		    cellEditor.setText("add");
		    cellEditor.setBackground(reqParent.getBackground());
		    cellEditor.setData(tcTag + ";" + testcases.get(tcTag) + ";");
		    cellEditor.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) { addTestcase(e); }

				@Override
				public void widgetDefaultSelected(SelectionEvent e) { addTestcase(e); }
				});
		    editor.setEditor(cellEditor, reqParent, 2);
			// add requirements
			List<String> requirements = tc2req.get(tcTag);
			for (int idx = 0; ((requirements != null) && (idx < requirements.size())); idx++) {
				reqTag = requirements.get(idx);
				TreeItem req = new TreeItem(reqParent, SWT.NONE);
				req.setText(0, reqTag);
				req.setText(1, Verdict2String(reqcov.get(reqTag)));
				req.setBackground(1, new Color(Display.getDefault(), Verdict2RGB(reqcov.get(reqTag))));
				req.setText(2, "");
			}
		}

    	// pack columns
		for (int idx = 0; idx < tc2reqHeader.length; idx++) {
			tcTreeView.getColumn(idx).pack();
		}
	}

	// requirements coverage
	private void createReq2TcPage() {
    	// init tree view
    	reqTreeView = new Tree(getContainer(), SWT.H_SCROLL | SWT.V_SCROLL);
		reqTreeView.setHeaderVisible(true);
		int index = addPage(reqTreeView);
		setPageText(index,"Requirements Coverage");

		// header:
		String[] req2tcHeader = {"Name", "Status", "Add Goals", "Create Test"};
		for (int idx = 0; idx < req2tcHeader.length; idx++) {
			TreeColumn column = new TreeColumn(reqTreeView, SWT.LEFT);
			column.setText(req2tcHeader[idx]);
			column.setResizable(true);
			reqTreeView.getColumn(idx).pack();
		}

		String tcTag, reqTag;
		Set<String> reqSet = req2tc.keySet();
		Iterator<String> reqIt = reqSet.iterator();
		while (reqIt.hasNext()) {
			reqTag = reqIt.next();
			// add requirement
			TreeItem tcParent = null;
			tcParent = new TreeItem(reqTreeView, SWT.NONE);
			tcParent.setText(0, reqTag);
			tcParent.setText(1, Verdict2String(reqcov.get(reqTag)));
			tcParent.setBackground(1, new Color(Display.getDefault(), Verdict2RGB(reqcov.get(reqTag))));
			// create button to add test cases for this requirement
			TreeEditor editor = new TreeEditor(reqTreeView);
			editor.horizontalAlignment = SWT.LEFT;
			editor.minimumWidth = 40;
			editor.minimumHeight = 12;
		    Button cellEditor = new Button(reqTreeView, SWT.PUSH);
		    cellEditor.setText("add");
		    cellEditor.setBackground(tcParent.getBackground());
		    cellEditor.setData(reqTag);
		    cellEditor.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) { addTestcaseForRequirement(e); }

				@Override
				public void widgetDefaultSelected(SelectionEvent e) { addTestcaseForRequirement(e); }
				});
		    editor.setEditor(cellEditor, tcParent, 2);
			// create button to create test procedure(s) for this requirement
		    String tooltip = "This button triggers the automated test of requirement " + reqTag +
                    ". One or more new test procedure generation contexts directories " +
                    "are created and configured to cover all test cases associated with " +
                    reqTag + ". The respective test procedure(s) can directly be generated and executed.";
			TreeEditor testeditor = new TreeEditor(reqTreeView);
			testeditor.horizontalAlignment = SWT.LEFT;
			testeditor.minimumWidth = 40;
			testeditor.minimumHeight = 12;
		    Button cellTestEditor = new Button(reqTreeView, SWT.PUSH);
		    cellTestEditor.setToolTipText(tooltip);
		    cellTestEditor.setText("test");
		    cellTestEditor.setBackground(tcParent.getBackground());
		    cellTestEditor.setData(reqTag);
		    cellTestEditor.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) { TestRequirement(e); }

				@Override
				public void widgetDefaultSelected(SelectionEvent e) { TestRequirement(e); }
				});
		    testeditor.setEditor(cellTestEditor, tcParent, 3);
			// add test cases for this requirement
			List<String> tcs = req2tc.get(reqTag);
			for (int idx = 0; idx < tcs.size(); idx++) {
				tcTag = tcs.get(idx);
				TreeItem tc = new TreeItem(tcParent, SWT.NONE);
				tc.setText(0, tcTag);
				tc.setText(1, Verdict2String(tccov.get(tcTag)));
				tc.setBackground(1, new Color(Display.getDefault(), Verdict2RGB(tccov.get(tcTag))));
				// create button to add this test case
				editor = new TreeEditor(reqTreeView);
				editor.horizontalAlignment = SWT.LEFT;
				editor.minimumWidth = 40;
				editor.minimumHeight = 12;
			    cellEditor = new Button(reqTreeView, SWT.PUSH);
			    cellEditor.setText("add");
			    cellEditor.setBackground(tcParent.getBackground());
			    cellEditor.setData(tcTag + ";" + testcases.get(tcTag) + ";");
			    cellEditor.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) { addTestcase(e); }

					@Override
					public void widgetDefaultSelected(SelectionEvent e) { addTestcase(e); }
					});
			    editor.setEditor(cellEditor, tc, 2);
				// create button to create a test procedure for this test case
			    tooltip = "This button triggers the automated test of test case " + tcTag +
			    		". A new test procedure generation contexts directory " +
			    		"is created and configured to cover this test case." +
			    		"The new test procedure can directly be generated and executed.";
			    testeditor = new TreeEditor(reqTreeView);
				testeditor.horizontalAlignment = SWT.LEFT;
				testeditor.minimumWidth = 40;
				testeditor.minimumHeight = 12;
			    cellTestEditor = new Button(reqTreeView, SWT.PUSH);
			    cellTestEditor.setToolTipText(tooltip);
			    cellTestEditor.setText("test");
			    cellTestEditor.setBackground(tcParent.getBackground());
			    cellTestEditor.setData(tcTag + ";" + testcases.get(tcTag) + ";");
			    cellTestEditor.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) { TestSingleTestCase(e); }

					@Override
					public void widgetDefaultSelected(SelectionEvent e) { TestSingleTestCase(e); }
					});
			    testeditor.setEditor(cellTestEditor, tc, 3);
			}
			tcParent.setExpanded(true);
		}
		// pack columns
		for (int idx = 0; idx < req2tcHeader.length; idx++) {
			reqTreeView.getColumn(idx).pack();
		}
	}
	
	@Override
	public void setFocus() {
		tcTreeView.setFocus();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		return;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		return;
	}
}

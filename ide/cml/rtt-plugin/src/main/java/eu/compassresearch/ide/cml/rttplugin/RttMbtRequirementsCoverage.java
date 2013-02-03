package eu.compassresearch.ide.cml.rttplugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtRequirementsCoverage extends MultiPageEditorPart  {

	// widget to display the data
	private Tree reqTreeView;
	private Tree tcTreeView;

	// maps for file content
	private SortedMap<String,List<String>> req2tc = new TreeMap<String,List<String>>();
	private SortedMap<String,List<String>> tc2req = new TreeMap<String,List<String>>();
	private SortedMap<String,String> testcases = new TreeMap<String,String>();
	private SortedMap<String,Boolean> tccov = new TreeMap<String,Boolean>();
	private SortedMap<String,Boolean> reqcov = new TreeMap<String,Boolean>();
	
	protected RttMbtClient client = null;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// get RTT-MBT TMS client
    	client = Activator.getClient();
		// - is called shortly after editor construction
		// - This marks the start of the editor lifecycle
		if (! (input instanceof IFileEditorInput) ) {
			throw new PartInitException("Invalid input! Expected file input");
		} else {
			IFileEditorInput iFileInput = (IFileEditorInput) input;
			IFile ifile = iFileInput.getFile();
			InputStream istream;
			try {
				istream = ifile.getContents();
				Scanner covScanner = new Scanner(istream);
				// create fileScanners for req2tc.csv and tc2req.csv
		    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
				String workspacepath = workspace.getRoot().getLocation().toFile().getAbsolutePath();
				String localpath = ifile.getFullPath().toString();
				localpath = localpath.replaceAll("overall_coverage.csv", "");
				String req2tcpath = workspacepath + localpath + "req2tc.csv";
				String tc2reqpath = workspacepath + localpath + "tc2req.csv";
				String tcpath = workspacepath + localpath + "testcases.csv";
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
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
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
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
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
							tccov.put(key, false);
						} else {
							client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
						}
					} else {
						client.addErrorMessage("[FAIL]: illegal file format for req2tc.csv\n");
					}
				}
				// get test case coverage from covered_testcases.csv and update testcase coverage
				while (covScanner.hasNextLine()) {
					key = covScanner.nextLine();
					if (!tccov.containsKey(key)) {
						client.addErrorMessage("[WARNING]: added coverage for unknown test case '" + key + "'\n");						
					}
					tccov.put(key, true);
				}
				// calculate requirements coverage
				Set<String> requirements = req2tc.keySet();
				Iterator<String> i = requirements.iterator();
				while (i.hasNext()) {
					String requirement = i.next();
					List<String> testcases = req2tc.get(requirement);
					Boolean covered = true;
					for (int idx = 0; (idx < testcases.size()) && (covered); idx++) {
						String testcase = testcases.get(idx);
						covered = covered && tccov.get(testcase);
					}
					reqcov.put(requirement, covered);
				}
				covScanner.close();
				req2tcScanner.close();
				tc2reqScanner.close();
				tcScanner.close();
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// @todo: add error here!
				client.addErrorMessage("[FAIL]: unable to find expected coverage file: " + e.getMessage() + "\n");
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

	// testcase coverage
	private void createTc2ReqPage() {
		// create tree view
		tcTreeView = new Tree(getContainer(), SWT.H_SCROLL | SWT.V_SCROLL);
		tcTreeView.setHeaderVisible(true);
		int index = addPage(tcTreeView);
		setPageText(index,"Testcase Coverage");

		// header:
		String[] tc2reqHeader = {"Name", "Status", "Test Procedure"};
		for (int idx = 0; idx < tc2reqHeader.length; idx++) {
			TreeColumn column = new TreeColumn(tcTreeView, SWT.LEFT);
			column.setText(tc2reqHeader[idx]);
			column.setResizable(true);
			tcTreeView.getColumn(idx).pack();
		}

		String tcTag, reqTag;
		Set<String> tcSet = tc2req.keySet();
		Iterator<String> tcIt = tcSet.iterator();
		while (tcIt.hasNext()) {
			tcTag = tcIt.next();
			List<String> requirements = tc2req.get(tcTag);
			TreeItem reqParent = null;
			for (int idx = 0; idx < requirements.size(); idx++) {
				reqTag = requirements.get(idx);
				if (idx == 0) {
					reqParent = new TreeItem(tcTreeView, SWT.NONE);
					reqParent.setText(0, tcTag);
					reqParent.setText(1, tccov.get(tcTag).toString());
					reqParent.setText(2, "-");
					client.addLogMessage(tcTag + "covered: " + tccov.get(tcTag) + "\n");
				}
				TreeItem req = new TreeItem(reqParent, SWT.NONE);
				req.setText(0, reqTag);
				req.setText(1, reqcov.get(reqTag).toString());
				req.setText(2, "-");
				client.addLogMessage("|->" + reqTag + "covered: " + reqcov.get(reqTag) + "\n");
			}
		}
	}

	// requirements coverage
	private void createReq2TcPage() {
		reqTreeView = new Tree(getContainer(), SWT.H_SCROLL | SWT.V_SCROLL);
		reqTreeView.setHeaderVisible(true);
		int index = addPage(reqTreeView);
		setPageText(index,"Requirements Coverage");

		// header:
		String[] req2tcHeader = {"Name", "Status"};
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
			List<String> testcases = req2tc.get(reqTag);
			TreeItem tcParent = null;
			for (int idx = 0; idx < testcases.size(); idx++) {
				tcTag = testcases.get(idx);
				if (idx == 0) {
					tcParent = new TreeItem(reqTreeView, SWT.NONE);
					tcParent.setText(0, reqTag);
					tcParent.setText(1, reqcov.get(reqTag).toString());
					client.addLogMessage(tcTag + "covered: " + reqcov.get(reqTag) + "\n");
				}
				TreeItem req = new TreeItem(tcParent, SWT.NONE);
				req.setText(0, tcTag);
				req.setText(1, tccov.get(tcTag).toString());
				client.addLogMessage("|->" + tcTag + "covered: " + tccov.get(tcTag) + "\n");
			}
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

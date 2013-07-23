//package eu.compassresearch.ide.ui;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.RGB;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.MessageBox;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.widgets.TableColumn;
//import org.eclipse.swt.widgets.TableItem;
//import org.eclipse.ui.part.ViewPart;
//
//public class Sanitizer extends ViewPart {
//
//	private enum Status {
//		LOADED("Loaded"), NOT_LOADED("Not Loaded");
//		private String rep;
//
//		Status(String rep) {
//			this.rep = rep;
//		}
//
//		@Override
//		public String toString() {
//			return rep;
//		}
//
//	}
//
//	public Sanitizer() {
//
//	}
//
//	private static List<String> classesToCheck;
//	static {
//		classesToCheck = new LinkedList<String>();
//		classesToCheck.add("eu.compassresearch.core.common.Registry");
//		classesToCheck.add("eu.compassresearch.core.common.RegistryFactory");
//		classesToCheck.add("eu.compassresearch.core.common.AnalysisArtifact");
//		classesToCheck
//				.add("eu.compassResearch.rttMbtTmsClientApi.RttMbtClient");
//		classesToCheck
//				.add("eu.compassresearch.core.typechecker.VanillaCmlTypeChecker");
//		classesToCheck.add("org.apache.commons.codec.binary.Base64");
//	}
//
//	private static class CheckResult {
//		public CheckResult() {
//		}
//
//		public CheckResult(Status v, String k) {
//			this.first = v;
//			this.second = k;
//
//		}
//
//		public Status first;
//		public String second;
//		public ClassLoader third;
//		public ClassLoader fourth;
//
//	}
//
//	private CheckResult checkClass(String className) {
//		CheckResult res = new CheckResult();
//		ClassLoader cl = getClass().getClassLoader();
//		try {
//
//			Class<?> c = cl.loadClass(className);
//			res.first = Status.LOADED;
//			res.second = "No Problems Detected.";
//			res.third = cl;
//			res.fourth = c.getClassLoader();
//		} catch (Exception e) {
//			res.second = e.toString();
//			res.first = Status.NOT_LOADED;
//			res.third = cl;
//			res.fourth = null;
//		}
//		return res;
//	}
//
//	@Override
//	public void createPartControl(Composite arg0) {
//
//		final Table table = new Table(arg0, SWT.MULTI | SWT.BORDER);
//		final Map<TableItem, String> reasonMap = new HashMap<TableItem, String>();
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//		String[] titles = { "Component", "Status", "Local Loader", "Loaded By" };
//		for (int i = 0; i < titles.length; i++) {
//			TableColumn column = new TableColumn(table, SWT.NONE);
//			column.setText(titles[i]);
//		}
//
//		for (String clz2ldr : classesToCheck) {
//			CheckResult s = checkClass(clz2ldr);
//			TableItem ti = new TableItem(table, SWT.NONE);
//			ti.setText(0, clz2ldr);
//			ti.setText(1, s.first.toString());
//			ti.setText(2, s.third.toString().split("@")[1]);
//			if (s.fourth == null)
//				ti.setText(3, "N/A");
//			else
//				ti.setText(3, s.fourth.toString().split("@")[1]);
//			if (s.first == Status.NOT_LOADED) {
//				ti.setBackground(new Color(Display.getDefault(), new RGB(250,
//						200, 200)));
//			} else {
//				ti.setBackground(new Color(Display.getDefault(), new RGB(200,
//						250, 200)));
//			}
//			reasonMap.put(ti, s.second);
//
//		}
//
//		for (int i = 0; i < titles.length; i++) {
//			table.getColumn(i).pack();
//		}
//
//		table.addMouseListener(new MouseListener() {
//
//			public void mouseUp(MouseEvent arg0) {
//
//			}
//
//			public void mouseDown(MouseEvent arg0) {
//			}
//
//			public void mouseDoubleClick(MouseEvent arg0) {
//				Table t = (Table) arg0.widget;
//				int selectedItemIdx = t.getSelectionIndex();
//				if (selectedItemIdx >= 0) {
//					TableItem selectedItem = t.getItem(selectedItemIdx);
//					MessageBox msgBox = new MessageBox(t.getShell());
//					msgBox.setMessage(reasonMap.get(selectedItem));
//					msgBox.setText("Load failure Reason");
//					msgBox.open();
//				}
//
//			}
//		});
//
//	}
//
//	@Override
//	public void setFocus() {
//
//	}
//
// }

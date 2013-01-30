package eu.compassresearch.ide.cml.ui.editor.core.dom;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;

import eu.compassresearch.ast.program.PSource;
// import eu.compassresearch.core.parser.ParserError;

/**
 * A CML Source unit is our DOM representation holding an AST for a one source
 * file.
 * 
 * It associates a file in the workspace.
 * 
 * @author rwl
 * 
 */
public class CmlSourceUnit {

	private static Map<IFile, CmlSourceUnit> map;
	static {
		map = new HashMap<IFile, CmlSourceUnit>();
	}

	public static interface CmlSourceChangedListener {
		public void sourceChanged(CmlSourceUnit csu);
	}

	List<CmlSourceChangedListener> changeListeners;

	public void addChangeListener(CmlSourceChangedListener listener) {

		if (changeListeners == null)
			changeListeners = new LinkedList<CmlSourceUnit.CmlSourceChangedListener>();
		changeListeners.add(listener);
	}
	
	public void clearListeners(){
	    changeListeners.clear();
	}

	/**
	 * Get a CmlSourceUnit from the a IFile resource.
	 * 
	 * @param file
	 * @return
	 */
	public static CmlSourceUnit getFromFileResource(IFile file) {
		if (map.containsKey(file))
			return map.get(file);
		CmlSourceUnit res = new CmlSourceUnit(file);
		map.put(file, res);
		return res;
	}
	
	public static Collection<CmlSourceUnit> getAllSourceUnits()
	{
		return map.values();
	}
	

	private PSource sourceAst;
	private IFile file;
	// private List<ParserError> errors;
	private boolean parsedOk;

	private CmlSourceUnit(IFile file) {
		this.file = file;
	}

	// public List<ParserError> getErrors() {
	// 	return this.errors;
	// }

	public PSource getSourceAst() {
		return sourceAst;
	}

	public void setSourceAst(PSource sourceAst, boolean parsedOk) {
	    this.sourceAst = sourceAst;
	    this.parsedOk = parsedOk;
	    notifyChange();
	}

	// public void setSourceAst(PSource sourceAst, List<ParserError> errors, boolean parsedOk) {
	// 	this.sourceAst = sourceAst;
	// 	this.errors = errors;
	// 	this.parsedOk = parsedOk;
	// 	notifyChange();
	// }

	private void notifyChange() {
		if (changeListeners != null)
			for (CmlSourceChangedListener l : changeListeners)
				l.sourceChanged(this);
	}

	@Override
	public String toString() {
		String res = "CmlSourceUnit: [file="
				+ (file == null ? "null" : file.getName()) + "]";
		res += " [sourceTreeAttached=" + (sourceAst == null ? "no" : "yes")
				+ "]";
		return res;
	}

	public boolean isParsedOk()
	{
		return this.parsedOk;
	}
	
	public IFile getFile() {
		return file;
	}
}

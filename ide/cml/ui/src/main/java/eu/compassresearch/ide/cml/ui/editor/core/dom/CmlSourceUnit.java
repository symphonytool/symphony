package eu.compassresearch.ide.cml.ui.editor.core.dom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.overture.ast.program.ASourcefileSourcefile;

import eu.compassresearch.core.lexer.ParserError;

/**
 * A CML Source unit is our DOM representation holding an AST for a one
 * source file.
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

	public void addChangeListener(CmlSourceChangedListener listener)
	{
		if (changeListeners == null)
			changeListeners = new LinkedList<CmlSourceUnit.CmlSourceChangedListener>();
		changeListeners.add(listener);
	}

	/**
	 * Get a CmlSourceUnit from the a IFile resource.
	 * @param file
	 * @return
	 */
	public static CmlSourceUnit getFromFileResource(IFile file)
	{
		if (map.containsKey(file)) return map.get(file);
		CmlSourceUnit res = new CmlSourceUnit(file);
		map.put(file,res);
		return res;
	}

	private ASourcefileSourcefile sourceAst;
	private IFile file;
	private List<ParserError> errors;

	private CmlSourceUnit(IFile file)
	{
		this.file = file;
	}

	
	public List<ParserError> getErrors()
	{
		return this.errors;
	}
	
	public ASourcefileSourcefile getSourceAst() {
		return sourceAst;
	}

	public void setSourceAst(ASourcefileSourcefile sourceAst, List<ParserError> errors) {
		this.sourceAst = sourceAst;
		this.errors = errors;
		notifyChange();
	}

	private void notifyChange() {
		if (changeListeners != null)
			for(CmlSourceChangedListener l : changeListeners)
				l.sourceChanged(this);
	}

	@Override
	public String toString()
	{
		String res = "CmlSourceUnit: [file="+(file == null ? "null" :file.getName()) + "]";
		res += " [sourceTreeAttached="+(sourceAst == null ? "no" : "yes")+"]";
		return res;
	}

	public Object getFile() {
		return file;
	}
}

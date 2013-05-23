package eu.compassresearch.ide.cml.ui.editor.core.dom;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.program.PSource;
// import eu.compassresearch.core.parser.ParserError;

/**
 * A CML Source unit is a DOM representation holding an AST for one source
 * file.
 * 
 * It associates a file in the workspace with an AST. It also holds a mapping between Files and ASTs in the project,
 * 
 * To obtain the AST associated with a file use {@link #getFromFileResource(IFile file)}.
 * To create a new association use {@link #getFromFileResource(IFile file)} (this will and insert the new file in the mapping)
 * and then use {@link #setSourceAst(PSource sourceAst, boolean parsedOk)}.
 * <br><br>
 * <b>WARNING:</b> This class is under heavy change.
 * The existing DOM solution is not compatible with the new build methods. An must be changed
 * 
 * @author Initial version by rwl
 * 
 * @author Modifications by ldc
 * 
 */
public class CmlSourceUnit implements ICmlSourceUnit {

	public static interface CmlSourceChangedListener {
		public void sourceChanged(CmlSourceUnit csu);
	}
	
	private static Map<IFile, CmlSourceUnit> map;

	static {
		map = new HashMap<IFile, CmlSourceUnit>();
	}


	//FIXME This method no longer does what it should
	@Deprecated
	public static Collection<CmlSourceUnit> getAllSourceUnits()
	{
		return map.values();
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

	List<CmlSourceChangedListener> changeListeners;

	private IFile file;

	private boolean parsedOk;
	
	private PSource sourceAst;

	private IVdmSourceUnit upSourceUnit;
	
	private CmlSourceUnit(IFile file) {
		this.file = file;
	}
	

	public void addChangeListener(CmlSourceChangedListener listener) {

		if (changeListeners == null)
			changeListeners = new LinkedList<CmlSourceUnit.CmlSourceChangedListener>();
		changeListeners.add(listener);
	}
	public void clearListeners(){
	    changeListeners.clear();
	}
	public IFile getFile() {
		return file;
	}

	public PSource getSourceAst() {
		return sourceAst;
	}

	public IVdmSourceUnit getUpSourceUnit()
	{
		return upSourceUnit;
	}

	public boolean isParsedOk()
	{
		return this.parsedOk;
	}

	@Override
	public boolean exists()
	{
		return file.exists();
	}
	@Override
	public int getElementType()
	{
		return ICmlSourceUnit.CML_SPEC;
	}
	@Override
	public Object getAdapter(Class adapter)
	{
		return null;
	}
	
	private void notifyChange() {
		if (changeListeners != null)
			for (CmlSourceChangedListener l : changeListeners)
				l.sourceChanged(this);
	}

	public void setSourceAst(PSource sourceAst, boolean parsedOk) {
	    this.sourceAst = sourceAst;
	    this.parsedOk = parsedOk;
	    notifyChange();
	}

	public void setUpSourceUnit(IVdmSourceUnit upSourceUnit)
	{
		this.upSourceUnit = upSourceUnit;
	}
	
	@Override
	public String toString() {
		String res = "CmlSourceUnit: [file="
				+ (file == null ? "null" : file.getName()) + "]";
		res += " [sourceTreeAttached=" + (sourceAst == null ? "no" : "yes")
				+ "]";
		return res;
	}
}

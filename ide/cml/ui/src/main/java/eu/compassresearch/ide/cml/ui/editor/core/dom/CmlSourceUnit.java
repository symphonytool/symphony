package eu.compassresearch.ide.cml.ui.editor.core.dom;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.overture.ast.program.ASourcefileSourcefile;

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
		
	}
	
	public void addChangeListener(CmlSourceChangedListener listener)
	{
		
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

	private CmlSourceUnit(IFile file)
	{
		this.file = file;
		
	}
	
	public ASourcefileSourcefile getSourceAst() {
		return sourceAst;
	}

	public void setSourceAst(ASourcefileSourcefile sourceAst) {
		this.sourceAst = sourceAst;
	}

	@Override
	public String toString()
	{
		String res = "CmlSourceUnit: [file="+(file == null ? "null" :file.getName()) + "]";
		res += " [sourceTreeAttached="+(sourceAst == null ? "no" : "yes")+"]";
		return res;
	}
}

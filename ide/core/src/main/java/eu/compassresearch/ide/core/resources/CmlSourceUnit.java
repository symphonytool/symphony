package eu.compassresearch.ide.core.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.program.PSource;

// import eu.compassresearch.core.parser.ParserError;

/**
 * A CML Source unit is a DOM representation holding an AST for one source file. It associates a file in the workspace
 * with an AST. It does so ny using the Overture {@link IVdmSourceUnit} class. To obtain the AST associated with a file
 * use {@link #getFromFileResource(IFile file)}. <br>
 * <br>
 * <b>WARNING:</b> This class is under heavy change because the existing DOM solution is not compatible with the new
 * build methods.
 * 
 * @author Initial version by rwl
 * @author Modifications by ldc
 */
public class CmlSourceUnit implements ICmlSourceUnit
{

	public static interface CmlSourceChangedListener
	{
		public void sourceChanged(CmlSourceUnit csu);
	}

	private static Map<IFile, CmlSourceUnit> map;

	static
	{
		map = new HashMap<IFile, CmlSourceUnit>();
	}

	// FIXME This method no longer does what it should. Remove Soon
	/**
	 * This method has been deprecated. A new way of attaining all sources is being implemented.
	 */

	@Deprecated
	public static Collection<CmlSourceUnit> getAllSourceUnits()
	{
		return map.values();
	}

	// FIXME This method no longer does what it should. Remove Soon
	/**
	 * This method has been deprecated Use {@link IFile#getAdapter(Class)} instead.
	 */
	@Deprecated
	public static CmlSourceUnit getFromFileResource(IFile file)
	{
		if (map.containsKey(file))
			return map.get(file);
		CmlSourceUnit res = new CmlSourceUnit(file);
		map.put(file, res);
		return res;
	}

	List<CmlSourceChangedListener> changeListeners;

	private IFile file;
	private IVdmSourceUnit upSourceUnit;

	private boolean parsedOk;

	private PSource sourceAst;

	private CmlSourceUnit(IFile file)
	{
		this.file = file;
	}

	public void addChangeListener(CmlSourceChangedListener listener)
	{

		if (changeListeners == null)
			changeListeners = new LinkedList<CmlSourceUnit.CmlSourceChangedListener>();
		changeListeners.add(listener);
	}

	public void clearListeners()
	{
		changeListeners.clear();
	}

	public IFile getFile()
	{
		return file;
	}

	// FIXME need to get a better connection between the two source units
	public PSource getSourceAst()
	{
		// return (PSource)upSourceUnit.getParseList().get(0);
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
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter)
	{
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	private void notifyChange()
	{
		if (changeListeners != null)
			for (CmlSourceChangedListener l : changeListeners)
				l.sourceChanged(this);
	}

	public void setSourceAst(PSource sourceAst, boolean parsedOk)
	{
		this.sourceAst = sourceAst;
		this.parsedOk = parsedOk;
		notifyChange();
	}

	public void setUpSourceUnit(IVdmSourceUnit upSourceUnit)
	{
		this.upSourceUnit = upSourceUnit;
	}

	@Override
	public String toString()
	{
		String res = "CmlSourceUnit: [file="
				+ (file == null ? "null" : file.getName()) + "]";
		res += " [sourceTreeAttached=" + (sourceAst == null ? "no" : "yes")
				+ "]";
		return res;
	}
}

package eu.compassresearch.ide.core.resources;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.program.PSource;

/**
 * An ICmlModel is the highest level abstraction for resources in the CML tool. There are few notions of files or other
 * system resources. Instead, an ICmlModel has methods related to the ATSs of a model and its type status, etc. <br>
 * Typically, most plug-ins will want to work directly with this interface rather than {@link ICmlProject}.
 * 
 * @author ldc, kel
 */
public interface ICmlModel extends IAdaptable
{

	/**
	 * Gets a list of {@link PSource} containing the ASTs of a model. <br>
	 * <b>Warning:</b> Use of this method and the {@link PSource} abstraction in general is no longer recommended. Use
	 * {@link #getAst()} instead.
	 * 
	 * @return the list of {@link PSource} representing the model.
	 */
	@Deprecated
	public abstract List<PSource> getAstSource();

	/**
	 * Gets the AST for this model. The AST is represented as a list of {@link INode}, where each node is one of the top
	 * level definitions in the model.
	 * 
	 * @return the list of {@link INode} representing the model.
	 */
	public abstract List<INode> getAst();

	/**
	 * Gets the AST for this model. The AST is represented as a list of the top level definitions ({@link PDefinition})
	 * of a model.
	 * 
	 * @return the list of {@link PDefinition}.
	 */
	public abstract List<PDefinition> getDefinitions();

	/**
	 * Gets the time the model was last checked..
	 * 
	 * @return the {@link Date} of the last check.
	 */
	public abstract Date getCheckedTime();

	/**
	 * Check if the model has been type checked without errors.
	 * 
	 * @return true if there are no type errors in the model.
	 */
	public abstract boolean isTypeCorrect();

	/**
	 * Checks if a the model has been type checked. Note that this is <b>not</b> the same as saying it has no errors
	 * (use {@link #isTypeCorrect()} method for that). This method simply checks if the model has been submitted to the
	 * typechecker.
	 * 
	 * @return true if the model has been been type checked, regardless of the outcome of said check.
	 */
	public abstract boolean isTypeChecked();

	/***
	 * Checks if a file belongs to the model. A file belongs to a model if any of the model's definitions has the file
	 * as its source location.
	 * 
	 * @param file
	 *            The {@link File} representing the model.
	 * @return true if the model contains the file. False otherwise.
	 */
	public abstract boolean hasFile(File file);

	/**
	 * Checks if a file belongs to the model. A file belongs to a model if any of the model's definitions has the file
	 * as its source location. This method is inherited from {@link IVdmModel}. In general, you will want to use
	 * {@link #hasFile(ICmlSourceUnit)}.
	 * 
	 * @param file
	 *            the {@link IVdmSourceUnit} representing the file.
	 * @return true, if the model contains the file. False otherwise.
	 */
	public abstract boolean hasFile(IVdmSourceUnit file);

	/**
	 * Checks if a file belongs to the model. A file belongs to a model if any of the model's definitions has the file
	 * as its source location.
	 * 
	 * @param file
	 *            the {@link ICmlSourceUnit} representing the file.
	 * @return true, if the model contains the file. False otherwise.
	 */
	public abstract boolean hasFile(ICmlSourceUnit file);

	/**
	 * Checks if the model has been correctly parsed.
	 * 
	 * @return true, if the model is parsed. False otherwise.
	 */
	public abstract boolean isParseCorrect();

	/**
	 * Check if the model exists. A model exists if it has at least one definition.
	 * 
	 * @return true, if the model exists. False otherwise.
	 */
	public abstract boolean exists();

	/**
	 * Get the source unit corresponding to a given file.
	 * 
	 * @param file
	 *            The {@link IFile}
	 * @return the corresponding {@link ICmlSourceUnit} if it exists. Null otherwise.
	 * @see {@link ICmlProject #findSourceUnit(IFile)} for more details.
	 */
	public abstract ICmlSourceUnit getSourceUnit(IFile file);

	/**
	 * Clean the project. This deletes all temporary and auto-generated files.
	 */
	public abstract void clean();

	/**
	 * Gets the source units for this project.
	 * 
	 * @return the list of {@link ICmlSourceUnit}
	 */
	public abstract List<ICmlSourceUnit> getSourceUnits();

	/**
	 * Refresh project. This will parse (and type check) the project again.
	 * 
	 * @param completeRefresh
	 *            true to trigger a refresh of all source units. False will only trigger the build of unparsed sources.
	 * @param monitor
	 *            null An {@link IProgressMonitor} to keep track of the progress. Can be null but then no feedback will
	 *            be shown to the user.
	 */
	public void refresh(boolean completeRefresh, IProgressMonitor monitor);

	/**
	 * Check if the internal state has a certain attribute name defined.
	 * <p/>
	 * <b>Warning:</b> This method belongs to the model registry implementation which is to be removed soon. Its use is
	 * discouraged.
	 * 
	 * @param attributeName
	 *            the name to check for
	 * @return true if the name exists
	 */
	@Deprecated
	public boolean hasAttribute(String attributeName);

	/**
	 * Gets an internal state object defined by the attribute name.<br/>
	 * The return type is defined by the class it is called with. <br>
	 * Note that the intension is that PLUG_IN ids should be used as the attribute names
	 * <p/>
	 * <b>Warning:</b> This method belongs to the model registry implementation which is to be removed soon. Its use is
	 * discouraged.
	 * 
	 * @param <K>
	 *            the key type
	 * @param attributeName
	 *            a plugin id
	 * @param returnClassType
	 *            the class type which should be returned
	 * @return if the attribute is defined then the state object is returned otherwise null
	 */
	@Deprecated
	public <K> K getAttribute(String attributeName, Class<K> returnClassType);

	/**
	 * Sets an internal state object using attribute name.<br/>
	 * If a state already exists with the attributename then it will be overwritten. <br>
	 * Note that the intension is that PLUG_IN ids should be used as the attribyteName
	 * <p/>
	 * <b>Warning:</b> This method belongs to the model registry implementation which is to be removed soon. Its use is
	 * discouraged.
	 * 
	 * @param <K>
	 *            the key type
	 * @param attributeName
	 *            the plugin id to store the state under
	 * @param value
	 *            the state to be stored
	 */
	@Deprecated
	public <K> void setAttribute(String attributeName, K value);

	/**
	 * Creates a back up of the source files of this model to the specified location. This location should be inside the
	 * project's output location.
	 * <p>
	 * <code>
	 * To obtain the the project output location use: IFolder location = (IFolder) project.getModelBuildPath().getOutput();</code>
	 * </br> And for the actual backup folder location use: <code>
	 * like location = location.getFolder("back up folder name"); </code>
	 * </p>
	 * 
	 * @param location
	 *            the {@link IFolder} location where the back up will be written to
	 */
	void backup(IFolder location);
}

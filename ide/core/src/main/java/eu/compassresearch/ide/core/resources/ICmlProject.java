package eu.compassresearch.ide.core.resources;

import java.io.File;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.overture.ide.core.resources.ModelBuildPath;

import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;

/**
 * Internal representation of a CML project. This interface establishes
 * a  mapping between the files on a project and the more abstract CML
 * concepts they represent.
 * @author ldc, kel
 *
 */
public interface ICmlProject extends IAdaptable
{
	
	/**
	 * Get a list of {@link IContentType} for all the files in the project.
	 * @return The list of {@link IContentType}.
	 */
	public List<IContentType> getContentTypeIds();

	
	/**
	 * Type check the files in this project.
	 *
	 * @param monitor An {@link IProgressMonitor} that keeps track of the type check process.
	 * @return true, if the model has been type checked without errors. False otherwise.
	 * @throws CoreException in case of an internal error in the type checking.
	 */
	public boolean typeCheck(IProgressMonitor monitor)
			throws CoreException;

	
	/**
	 * Type check the files in this project.
	 *
	 * @param clean A flag indicating whether the project should be cleaned before type checking.
	 * @param monitor An {@link IProgressMonitor} that keeps track of the type check process.
	 * @throws CoreException in case of an internal error in the type checking.
	 */
	public void typeCheck(boolean clean, IProgressMonitor monitor)
			throws CoreException;

	
	/**
	 * Gets the name of the project.
	 *
	 * @return the name
	 */
	public String getName();

	
	/**
	 * Gets the nature name of the project.
	 *
	 * @return the nature
	 */
	public String getNature();

	
	/**
	 * Gets the source units for the files in the project.
	 *
	 * @return a list of {@link ICmlSourceUnit}
	 * @throws CoreException if the files in the project cannot be converted to source units.
	 */
	public List<ICmlSourceUnit> getSourceUnits() throws CoreException;

	
	/**
	 * Gets the {@link File} corresponding to the submitted {@link IFile}. This
	 * method maps between two different abstractions for files as resources.
	 *
	 *
	 * @param file the {@link IFile}
	 * @return the {@link File}
	 * @see {@link #findIFile(File)}.
	 */
	public File getFile(IFile file);
	
	
	/**
	 * Checks if a file is part of a model.
	 *
	 * @param file the file to check
	 * @return true, if the file is part of a model. False otherwise.
	 * @throws CoreException in case of internal type checking or conversion error.
	 */
	boolean isModelFile(IFile file) throws CoreException;

	
	/**
	 * Get the {@link IFile} corresponding to the submitted {@link File}. This
	 * method maps between two different abstractions for files as resources.
	 *
	 * @param file the {@link FileIcon16}
	 * @return the {@link IFile}
	 * @see {@link #getFile(IFile)}
	 */
	public IFile findIFile(File file);

	
	/**
	 * Get a file by providing its path.
	 *
	 * @param path the {@link IPath} to the file
	 * @return the {@link File}
	 */
	public File getSystemFile(IPath path);

	
	/**
	 * Get a file by providing its path in two parts: workspace root and
	 * file path.
	 *
	 * @param wroot The {@link IWorkspaceRoot}
	 * @param path The {@link IPath} to the file
	 * @return the {@link File}
	 */
	public File getFile(IWorkspaceRoot wroot, IPath path);

	
	/**
	 * Get the source unit corresponding to a given file. This method goes up a
	 * level in abstraction. A {@link IFile} represents a file as a resource in
	 * the file system whereas a {@link ICmlSourceUnit} represents a file as part
	 * of a model. This method tries to adapt the supplied file and transform
	 * it into a source unit.
	 *
	 * @param file The {@link IFile}
	 * @return the corresponding {@link ICmlSourceUnit}
	 * @throws CoreException if it is not possible to adapt the file
	 * to a source unit.
	 * @see {@link IAdaptable #getAdapter}
	 */
	public ICmlSourceUnit findSourceUnit(IFile file)
			throws CoreException;

	
	/**
	 * Gets the model represented by this project. In CML, all models are
	 * project wide so there is only one model contained in each project.
	 *
	 * @return the {@link ICmlModel} representing the model contained in his project.
	 */
	public ICmlModel getModel();

	
	/**
	 * Gets the model build path for this project.
	 *
	 * @return the {@link ModelBuildPath}
	 */
	public ModelBuildPath getModelBuildPath();
	
	
	/**
	 * Adds markers detailing which elements of a model are not supported by
	 * a feature. This will add warnings to problems view of the main CML
	 * perspective for each unsupported element..
	 *
	 * @param ueis a list of {@link UnsupportedElementInfo} to be placed in the warnings.
	 */
	public void addUnsupportedMarkers(List<UnsupportedElementInfo> ueis);
	
	
}

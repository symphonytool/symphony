package eu.compassresearch.ide.ui.editor.syntax;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ide.ui.VdmPluginImages;
import org.overture.ide.ui.internal.viewsupport.VdmElementImageDescriptor;
import org.overture.ide.ui.internal.viewsupport.VdmElementImageProvider;

import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;

public class CmlElementImageProvider extends VdmElementImageProvider
{
	@Override
	protected ImageDescriptor computeDescriptor(Object element, int flags)
	{
		int adornmentFlags = 0;

		adornmentFlags = computeVdmAdornmentFlags(element);

		if (element instanceof AExplicitCmlOperationDefinition)
		{
			return getExplicitCmlOperationDefinitionImage((AExplicitCmlOperationDefinition) element, SMALL_ICONS, adornmentFlags);
		}else if ( element instanceof AAssignmentDefinition)
		{
			return getAssignmentDefinitionImage((AAssignmentDefinition) element, SMALL_ICONS, adornmentFlags);
		}
		return super.computeDescriptor(element, flags);
	}

	@Override
	public int computeVdmAdornmentFlags(Object element)
	{
		if(element instanceof AAssignmentDefinition)
		{
			int flags = 0;

//			flags |= VdmElementImageDescriptor.STATIC;
			flags |= VdmElementImageDescriptor.FINAL;

			return flags;
		}
		return super.computeVdmAdornmentFlags(element);
	}
	private ImageDescriptor getExplicitCmlOperationDefinitionImage(
			AExplicitCmlOperationDefinition element, int renderFlags,
			int adornmentFlags)
	{
		ImageDescriptor result = null;
		AAccessSpecifierAccessSpecifier as = element.getAccess();
		Point size = useSmallSize(renderFlags) ? SMALL_SIZE : BIG_SIZE;

		// result =
		// VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_PRIVATE);

		if (as.getAccess().toString().equals("private"))
		{
			return new VdmElementImageDescriptor(VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_PRIVATE), adornmentFlags, size);
		} else if (as.getAccess().toString().equals("public"))
		{
			return new VdmElementImageDescriptor(VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_PUBLIC), adornmentFlags, size);
		} else if (as.getAccess().toString().equals("protected"))
		{
			return new VdmElementImageDescriptor(VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_PROTECTED), adornmentFlags, size);
		} else if (as.getAccess().toString().equals("default"))
		{
			return new VdmElementImageDescriptor(VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_DEFAULT), adornmentFlags, size);
		}

		return result;
		//

		// return new
		// VdmElementImageDescriptor(VdmPluginImages.getDescriptor(VdmPluginImages.IMG_METHOD_PROTECTED),
		// 0,
		// size);
	}
	
	private ImageDescriptor getAssignmentDefinitionImage(AAssignmentDefinition element,
			int renderFlags, int adornmentFlags) {
		ImageDescriptor result = null;
		AAccessSpecifierAccessSpecifier as = element.getAccess();
		Point size = useSmallSize(renderFlags) ? SMALL_SIZE : BIG_SIZE;

		if (as.getAccess().toString().equals("private")) {
			return new VdmElementImageDescriptor(VdmPluginImages
					.getDescriptor(VdmPluginImages.IMG_FIELD_PRIVATE),
					adornmentFlags, size);
		} else if (as.getAccess().toString().equals("public")) {
			return new VdmElementImageDescriptor(VdmPluginImages
					.getDescriptor(VdmPluginImages.IMG_FIELD_PUBLIC),
					adornmentFlags, size);
		} else if (as.getAccess().toString().equals("protected")) {
			return new VdmElementImageDescriptor(VdmPluginImages
					.getDescriptor(VdmPluginImages.IMG_FIELD_PROTECTED),
					adornmentFlags, size);
		} else if (as.getAccess().toString().equals("default")) {
			return new VdmElementImageDescriptor(VdmPluginImages
					.getDescriptor(VdmPluginImages.IMG_FIELD_DEFAULT),
					adornmentFlags, size);
		}

		return result;
	}
}

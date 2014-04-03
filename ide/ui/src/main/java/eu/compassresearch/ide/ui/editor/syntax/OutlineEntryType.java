package eu.compassresearch.ide.ui.editor.syntax;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SOperationDefinition;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.PProcess;

public enum OutlineEntryType
{

	CLASS("/icons/obj16/class_obj.gif"), PROCESS(
			"/icons/obj16/process_obj2.gif"),
	// EXTERNAL_CHOICE_PROCESS(
	// "/icons/cview16/outline_external_choice_process.png"),
	// PARALLEL_PROCESS(
	// "/icons/cview16/outline_parallel_process.png"),
	FUNCTION_TOP("/icons/obj16/function_top_obj.gif"), FUNCTION_ENTRY(
			"/icons/obj16/function_entry_obj.gif"), OPERATION(
			"/icons/obj16/operation_obj.gif"), TYPE_TOP(
			"/icons/obj16/type_top_obj.gif"), TYPE_ENTRY(
			"/icons/obj16/type_entry_obj.gif"), VALUE_TOP(
			"/icons/obj16/value_top_obj.gif"), VALUE_ENTRY(
			"/icons/obj16/value_entry_obj.gif"), CHANNEL_TOP(
			"/icons/obj16/channel_obj.gif"), CHANNEL_ENTRY(
			"/icons/obj16/channel_obj.gif"), CHANSET_TOP(
			"/icons/obj16/chanset_top_obj.gif"), CHANSET_ENTRY(
			"/icons/obj16/channel_obj.gif"), ERROR("/icons/obj16/error_obj.gif"), ACTION(
			"/icons/obj16/action_go_obj.gif"), ;

	private final Image img;

	OutlineEntryType(String imgloc)
	{
		ImageDescriptor descriptor = null;
		URL url = this.getClass().getResource(imgloc);
		descriptor = ImageDescriptor.createFromURL(url);
		img = descriptor.createImage();
	}

	public Image getImage()
	{
		return img;
	}

	// private static final Map<Class<?>, Image> ICON_MAP = createMap();
	//
	// public static Image getImage(Class<?> cls) {
	// Image r = ICON_MAP.get(cls);
	// if (r == null)
	// System.err.println("No outline icon found for class "
	// + cls.getCanonicalName());
	// return r;
	// }
	//
	// private static Map<Class<?>, Image> createMap() {
	// Map<Class<?>, Image> map = new HashMap<Class<?>, Image>();
	// //map.put(key, value)
	// //TODO Add class/image pairs
	// return Collections.unmodifiableMap(map);
	// }

	public static Image getImageForElement(Object obj)
	{

		// TODO change to (class,map) format?
		// if (obj instanceof Wrapper)
		// {
		// Wrapper<?> w = (Wrapper<?>) obj;
		// obj = w.value;
		// }

		// Fetch channels and chansets
		if (obj instanceof AChannelDefinition)
			return CHANNEL_ENTRY.getImage();
		if (obj instanceof AChansetDefinition)
			return CHANSET_ENTRY.getImage();

		// Fetch Top-Level Process and Class
		if (obj instanceof AProcessDefinition)
			return PROCESS.getImage();
		if (obj instanceof PProcess)
			return PROCESS.getImage();
		if (obj instanceof SClassDefinition)
			return CLASS.getImage();

		// Fetch interior of processes and classes
		if (obj instanceof AValueDefinition)
			return VALUE_ENTRY.getImage();
		if (obj instanceof AImplicitFunctionDefinition
				|| obj instanceof AExplicitFunctionDefinition)
			return FUNCTION_ENTRY.getImage();
		if (obj instanceof ATypeDefinition)
			return TYPE_ENTRY.getImage();
		if (obj instanceof PAction)
			return ACTION.getImage();
		if (obj instanceof AActionDefinition)
			return ACTION.getImage();
		if (obj instanceof SOperationDefinition)
			return OPERATION.getImage();
		// if (obj instanceof AExternalChoiceProcess)
		// return EXTERNAL_CHOICE_PROCESS.getImage();
		// if (obj instanceof ASynchronousParallelismProcess)
		// return PARALLEL_PROCESS.getImage();

		// When all else fails...
		System.err.println("No icon found for class "
				+ obj.getClass().getCanonicalName());

		return ERROR.getImage();

	}

}

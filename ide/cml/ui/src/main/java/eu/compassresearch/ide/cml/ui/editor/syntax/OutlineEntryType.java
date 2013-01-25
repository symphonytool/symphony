package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.PProcess;

public enum OutlineEntryType {

    CLASS("/icons/cview16/outline_class.png"), PROCESS(
	    "/icons/cview16/outline_process.png"),
    // EXTERNAL_CHOICE_PROCESS(
    // "/icons/cview16/outline_external_choice_process.png"),
    // PARALLEL_PROCESS(
    // "/icons/cview16/outline_parallel_process.png"),
    FUNCTION_TOP("/icons/cview16/outline_function_top.png"), FUNCTION_ENTRY(
	    "/icons/cview16/outline_function_entry.png"), OPERATION(
	    "/icons/cview16/outline_operation.png"), TYPE_TOP(
	    "/icons/cview16/outline_type_top.png"), TYPE_ENTRY(
	    "/icons/cview16/outline_type_entry.png"), VALUE_TOP(
	    "/icons/cview16/outline_value_top.png"), VALUE_ENTRY(
	    "/icons/cview16/outline_value_entry.png"), CHANNEL_TOP(
	    "/icons/cview16/outline_channel_top.png"), CHANNEL_ENTRY(
	    "/icons/cview16/outline_channel_entry.png"), CHANSET_TOP(
	    "/icons/cview16/outline_chanset_top.png"), CHANSET_ENTRY(
	    "/icons/cview16/outline_chanset_entry.png"), ERROR(
	    "/icons/cview16/outline_error.png"), ACTION(
	    "/icons/cview16/outline_action.png"),
	    ;

    private final Image img;

    OutlineEntryType(String imgloc) {
	ImageDescriptor descriptor = null;
	URL url = this.getClass().getResource(imgloc);
	descriptor = ImageDescriptor.createFromURL(url);
	img = descriptor.createImage();
    }

    public Image getImage() {
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

	public static Image getImageForElement(Object obj) {

	// TODO change to (class,map) format?
	if (obj instanceof Wrapper) {
	    Wrapper<?> w = (Wrapper<?>) obj;

	    // Fetch Top-level definitions
	    if (w.isClass(AValuesDefinition.class))
		return VALUE_TOP.getImage();
	    if (w.isClass(ATypesDefinition.class))
		return TYPE_TOP.getImage();
	    if (w.isClass(AFunctionsDefinition.class))
		return FUNCTION_TOP.getImage();

	    // Fetch channels and chansets
	    if (w.isClass(AChannelsDefinition.class))
		return CHANNEL_TOP.getImage();
	    if (w.isClass(AChansetsDefinition.class))
		return CHANSET_TOP.getImage();
	    if (w.isClass(AChannelNameDefinition.class))
		return CHANNEL_ENTRY.getImage();
	    if (w.isClass(AChansetDefinition.class))
		return CHANSET_ENTRY.getImage();

	    // Fetch Top-Level Process and Class
	    if (w.isClass(AProcessDefinition.class))
		return PROCESS.getImage();
	    if (w.isClass(PProcess.class))
		return PROCESS.getImage();
	    if (w.isClass(AClassDefinition.class))
		return CLASS.getImage();

	    // Fetch interior of processes and classes
	    if (w.isClass(AValueDefinition.class))
		return VALUE_ENTRY.getImage();
	    if (w.isClass(AImplicitFunctionDefinition.class)
		    || w.isClass(AExplicitFunctionDefinition.class))
		return FUNCTION_ENTRY.getImage();
	    if (w.isClass(ATypeDefinition.class))
		return TYPE_ENTRY.getImage();
	    if (w.isClass(PAction.class))
		return ACTION.getImage();
	    if (w.isClass(AActionDefinition.class))
		return ACTION.getImage();
	    if (w.isClass(SCmlOperationDefinition.class))
		return OPERATION.getImage();
	    // if (w.isClass(AExternalChoiceProcess.class))
	    // return EXTERNAL_CHOICE_PROCESS.getImage();
	    // if (w.isClass(ASynchronousParallelismProcess.class))
	    // return PARALLEL_PROCESS.getImage();

	    // When all else fails...
	    System.err.println("No icon found for class "
		    + w.value.getClass().getCanonicalName());
	}
	return ERROR.getImage();

    }

}

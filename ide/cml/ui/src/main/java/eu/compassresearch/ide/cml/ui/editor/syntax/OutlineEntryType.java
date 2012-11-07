package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;

public enum OutlineEntryType {

	CLASS("/icons/cview16/outline_class.png"), 
	PROCESS("/icons/cview16/outline_process.png"), 
	FUNCTION_TOP("/icons/cview16/outline_function_top.png"),
	FUNCTION_ENTRY("/icons/cview16/outline_function_entry.png"), 
	OPERATION("/icons/cview16/outline_operation.png"),
	TYPE_TOP("/icons/cview16/outline_type_top.png"),
	TYPE_ENTRY("/icons/cview16/outline_type_entry.png"),
	VALUE_TOP("/icons/cview16/outline_value_top.png"), 
	VALUE_ENTRY("/icons/cview16/outline_value_entry.png"), 
	CHANNEL_TOP("/icons/cview16/outline_channel_top.png"),
	CHANNEL_ENTRY("/icons/cview16/outline_channel_entry.png"),
	CHANSET_TOP("/icons/cview16/outline_chanset_top.png"), 
	CHANSET_ENTRY("/icons/cview16/outline_chanset_entry.png"), 
	ERROR("/icons/cview16/outline_error.png"), 
	ACTION("/icons/cview16/outline_action.png") ;

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

	
//	private static final Map<Class<?>, Image> ICON_MAP = createMap();
//
//	public static Image getImage(Class<?> cls) {
//		Image r = ICON_MAP.get(cls);
//		if (r == null)
//			System.err.println("No outline icon found for class "
//					+ cls.getCanonicalName());
//		return r;
//	}
//
//	private static Map<Class<?>, Image> createMap() {
//		Map<Class<?>, Image> map = new HashMap<Class<?>, Image>();
//		//map.put(key, value)
//		//TODO Add class/image pairs
//		return Collections.unmodifiableMap(map);
//	}

	
	public static Image getImageForElement(Object obj) {
	 
	    //TODO fix Unparameterized references
	    //TODO change to (class,map) format?
	    Wrapper w = (Wrapper) obj;
	    
	    // Fetch Top-level definitions
	    if (w.isClass(AValueParagraphDefinition.class))
		return VALUE_TOP.getImage();
	    if (w.isClass(ATypesParagraphDefinition.class))
		return TYPE_TOP.getImage();
	    if (w.isClass(AFunctionParagraphDefinition.class))
		return FUNCTION_TOP.getImage();
	    
	    // Fetch channels and chansets
	    if (w.isClass(AChannelParagraphDefinition.class))
		return CHANNEL_TOP.getImage();
	    if (w.isClass(AChansetParagraphDefinition.class))
		return CHANSET_TOP.getImage();
	    if (w.isClass(AChannelNameDeclaration.class))
		return CHANNEL_ENTRY.getImage();
	    if (w.isClass(AChansetDefinition.class))
		return CHANSET_ENTRY.getImage();
	    
	    // Fetch Top-Level Process and Class
	    if (w.isClass(AProcessParagraphDefinition.class))
		return PROCESS.getImage();
	    if (w.isClass(AClassParagraphDefinition.class))
		return CLASS.getImage();

	    //Fetch interior of processes and classes
	    if (w.isClass(AValueDefinition.class))
		return VALUE_ENTRY.getImage();
	    if (w.isClass(AImplicitFunctionDefinition.class) || w.isClass(AExplicitFunctionDefinition.class))
		return FUNCTION_ENTRY.getImage();
	    if (w.isClass(ATypeDefinition.class))
		return TYPE_ENTRY.getImage();
	    if (w.isClass(PAction.class))
		return ACTION.getImage();
	    if (w.isClass(AActionParagraphDefinition.class))
		return ACTION.getImage();
	    if (w.isClass(SOperationDefinition.class))
		return OPERATION.getImage();
	    
	    //When all else fails...
	    System.err.println("No icon found for class "+w.value.getClass().getCanonicalName());
	    return ERROR.getImage();

	}

}

package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlTreeContentProvider.Wrapper;

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

	public static Image getImageForElement(Object obj) {
		OutlineEntryType oi = null;
		if (obj instanceof OutlineEntry)
			oi = ((OutlineEntry) obj).getRight();
		else {
			Wrapper w = (Wrapper) obj;
			if (w.getDescription() == "global value declarations")
				return VALUE_TOP.getImage();
			if (w.getDescription() == "global type declarations")
				return TYPE_TOP.getImage();
			if (w.getDescription() == "channel declarations")
				return CHANNEL_TOP.getImage();
			if (w.getDescription() == "chanset declarations")
				return CHANSET_TOP.getImage();
			if (w.getDescription() == "global function declarations")
				return FUNCTION_TOP.getImage();
			if (w.isClass(AProcessParagraphDefinition.class))
				return PROCESS.getImage();
			if (w.isClass(AClassParagraphDefinition.class))
				return CLASS.getImage();
		}

		if (oi == null)
			return ERROR.getImage();
		return oi.getImage();

	}

}

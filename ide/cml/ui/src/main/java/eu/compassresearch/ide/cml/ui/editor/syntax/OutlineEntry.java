package eu.compassresearch.ide.cml.ui.editor.syntax;

public class OutlineEntry extends Pair<String, OutlineEntryType> {

	public OutlineEntry(String label, OutlineEntryType entrytype) {
		super(label, entrytype);
	}
	
	
	public String toString(){
		return super.getLeft();
	}
}

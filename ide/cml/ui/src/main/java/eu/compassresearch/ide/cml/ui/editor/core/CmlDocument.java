package eu.compassresearch.ide.cml.ui.editor.core;

import org.overture.ide.ui.editor.core.VdmDocument;

import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlDocument extends VdmDocument {

	private CmlSourceUnit source;

	public CmlSourceUnit getCmlSourceUnit() {
		return this.source;
	}
	
	public void setCmlSourceUnit(CmlSourceUnit csu)
	{
		this.source = csu;
	}
	
	
	
}

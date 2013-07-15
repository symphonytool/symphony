package eu.compassresearch.ide.ui.editor.core;

import org.overture.ide.ui.editor.core.VdmDocument;

import eu.compassresearch.ide.core.resources.CmlSourceUnit;

public class CmlDocument extends VdmDocument
{

	private CmlSourceUnit source;

	public CmlSourceUnit getCmlSourceUnit()
	{
		return this.source;
	}

	public void setCmlSourceUnit(CmlSourceUnit csu)
	{
		this.source = csu;
	}

}

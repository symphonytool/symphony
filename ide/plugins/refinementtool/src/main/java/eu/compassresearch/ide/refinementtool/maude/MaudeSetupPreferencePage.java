package eu.compassresearch.ide.refinementtool.maude;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.ide.refinementtool.CmlRefinePlugin;
import eu.compassresearch.ide.refinementtool.RefConstants;

public class MaudeSetupPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	
	public MaudeSetupPreferencePage()
	{
		super();
		setDescription("Setup values for using Maude for refinement");
		setTitle("Maude Setup");
	}

	@Override
	public void init(IWorkbench workbench)
	{
		setDescription("Setup values for using Maude for refinement");
		setTitle("Maude Setup");
		performDefaults();
	}

	@Override
	protected void createFieldEditors()
	{
		addField(new FileFieldEditor(RefConstants.MAUDE_LOC, "Maude binary location", getFieldEditorParent()));
		addField(new FileFieldEditor(RefConstants.MAUDE_THY, "Maude CML location", getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return CmlRefinePlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected void performDefaults()
	{
		String maudeThy = getMaudeThy();
		
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(RefConstants.MAUDE_LOC, "");
		store.setDefault(RefConstants.MAUDE_THY, maudeThy);
		super.performDefaults();
	}

	private String getMaudeThy() {
		String maudeThy = "";
		String sep = "/";
		if (System.getProperty("os.name").contains("Windows")) {
			sep = "\\";
		}
		String loc = Platform.getInstallLocation().getURL().getPath() + sep + "refinement" + sep + "cml-refine.maude";
		File f = new File(loc);		
		if (f.exists()) 
		   maudeThy = f.getPath();
		
		return maudeThy;

	}
	
}

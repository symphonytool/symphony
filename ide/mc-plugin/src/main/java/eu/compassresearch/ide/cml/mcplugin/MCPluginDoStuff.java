package eu.compassresearch.ide.cml.mcplugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;

import eu.compassresearch.ide.cml.mcplugin.view.MCListView;
import eu.compassresearch.ide.cml.mcplugin.view.MCUIResult;

public class MCPluginDoStuff {
	
	private IWorkbenchSite site;
	protected IResource file;
	protected FormulaResultWrapper formulaResult;
	
	public MCPluginDoStuff(IWorkbenchSite s, IResource f, FormulaResultWrapper fr) {
		this.file = f;
		this.formulaResult = fr;
		this.site = s;
	}
	
	public void run()
	{
		final MCUIResult data = new MCUIResult(file, formulaResult);
		site.getPage().getWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				IViewPart v;

				try
				{
					v = site.getPage().showView(MCConstants.MC_OVERVIEW_TABLE);
					if(v instanceof MCListView){
						((MCListView) v).setData(data);
						MCPluginUtility mcpu = new MCPluginUtility(site);
						mcpu.openMcviewPerspective();
					}
				} catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}

package eu.compassresearch.ide.builders.cml;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.builder.AbstractVdmBuilder;
import org.overturetool.util.definitions.ClassList;

/***
 * VDM PP builder
 * 
 * @author kela <extension<br>
 *         point="org.overture.ide.builder"><br>
 *         <builder<br>
 *         class="org.overture.ide.builders.vdmj.BuilderPp"><br>
 *         </builder><br>
 *         </extension><br>
 */
public class BuilderCml extends AbstractVdmBuilder {
	protected ClassList classes = new ClassList();

	public BuilderCml() {
	}

	@Override
	public IStatus buildModel(IVdmModel rooList) {
		return Status.OK_STATUS;
	}

}

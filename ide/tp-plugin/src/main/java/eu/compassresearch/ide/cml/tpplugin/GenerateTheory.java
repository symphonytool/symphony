package eu.compassresearch.ide.cml.tpplugin;
import isabelle.Isabelle_Process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.resources.VdmSourceUnit;

import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.theoremprover.TPVisitor;

public class GenerateTheory {

	public static void toThy(IFile cmlFile) throws IOException {
		
		String cmlLoc = cmlFile.getLocation().toString(); 
		String thyFile = cmlLoc.replaceAll("\\.cml", "\\.thy");
		
		File tempPo = new File(thyFile);
		
		FileWriter fw;
		
		fw = new FileWriter(tempPo);
		
		CmlSourceUnit source = (CmlSourceUnit) cmlFile.getAdapter(VdmSourceUnit.class);
		
		TPVisitor tpVisitor = new TPVisitor();
		
		StringBuilder sb = new StringBuilder();
		
		try {
			source.getSourceAst().apply(tpVisitor);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			sb.append("Analysis Error");
			e.printStackTrace();
		}
		
		for (String s:tpVisitor.getTypeList()) {
			sb.append(s);
			sb.append("\n\n");
		}
		
		fw.write(sb.toString());
		fw.flush();
		fw.close();

		// File fileToOpen = new File(workspaceLoc, thyFile);

	}
	
}

package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AClassbodyDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.program.ASourcefileSourcefile;

import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider {

	public CmlTreeContentProvider(){
		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	private ASourcefileSourcefile current;
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof CmlSourceUnit)
		{
			// Get current source tree
			current = ((CmlSourceUnit)inputElement).getSourceAst();
			if (current == null) return new Object[0];
			
			// If there are any declarations lets see them
			LinkedList<PDeclaration> decls = current.getDecls();
			if (decls != null)
				return decls.toArray();
			else
				return new Object[0];
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof INode)
		{
			INode n = (INode)parentElement;
			if (n instanceof AClassDeclaration)
			{
				AClassDeclaration clzdecl = (AClassDeclaration)n;
				AClassbodyDefinition cd = clzdecl.getClassBody();
				return cd.getDeclarations().toArray();
			}
		}
		return new String[] {"--"};
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}


}

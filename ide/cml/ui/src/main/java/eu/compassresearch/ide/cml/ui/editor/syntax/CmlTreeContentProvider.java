package eu.compassresearch.ide.cml.ui.editor.syntax;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.overture.ast.declarations.AActionDeclaration;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.AFunctionDeclaration;
import org.overture.ast.declarations.AOperationDeclaration;
import org.overture.ast.declarations.AProcessDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.AValueDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AActionDefinition;
import org.overture.ast.definitions.AClassbodyDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.lex.LexNameToken;
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
			return decls.toArray();
		}
		return new Object[0];
	}


	private static class Wrapper<T>
	{
		private String str;
		public Wrapper(T value, String str)
		{
			this.value = value;
			this.str = str;
		}
		public T value;
		public boolean isClass(Class<?> clz)
		{
			return clz.isInstance(value);
		}

		public String toString()
		{
			return str;
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object n = parentElement;
			if (n instanceof AClassDeclaration)
			{
				List<Wrapper<PDeclaration>> res = new LinkedList<CmlTreeContentProvider.Wrapper<PDeclaration>>();
				AClassDeclaration clzdecl = (AClassDeclaration)n;
				AClassbodyDefinition cd = clzdecl.getClassBody();
				for(PDeclaration decl : cd.getDeclarations())
				{
					if (decl instanceof AValueDeclaration)
						res.add(new Wrapper<PDeclaration>(decl, "Values"));
					else
						if (decl instanceof AFunctionDeclaration)
							res.add(new Wrapper<PDeclaration>(decl, "Functions"));
						else
							if (decl instanceof AOperationDeclaration)
								res.add(new Wrapper<PDeclaration>(decl, "Operations"));
				}
				return res.toArray();
			}

			if (n instanceof AProcessDeclaration)
			{
				AProcessDeclaration processDecl = (AProcessDeclaration)n;
				List<ASingleTypeDeclaration> pd = processDecl.getProcessDefinition().getDeclarations();
				return pd.toArray();
			}

			if (n instanceof Wrapper)
			{
				Wrapper w = (Wrapper)n;
				if (((Wrapper) n).isClass(AValueDeclaration.class))
				{
					List<String> res = new LinkedList<String>();
					AValueDeclaration valDecl = (AValueDeclaration)w.value;
					if (valDecl != null)
						for (PDefinition def : valDecl.getDefinitions())
						{

							LexNameToken name = def.getName();
							res.add(name != null ? name.name : "null"+" : "+def.getType());
						}
					return res.toArray();
				}
				
				if ( ((Wrapper) n).isClass(AFunctionDeclaration.class))
				{
					List<String> res = new LinkedList<String>();
					AFunctionDeclaration fd = (AFunctionDeclaration)w.value;
					for (SFunctionDefinition fnd : fd.getFunctionDefinitions())
					{
						res.add("[F] " +notNullName(fnd.getName()));
					}
					return res.toArray();
				}
				
				if ( ((Wrapper) n).isClass(AOperationDeclaration.class))
				{
					List<String> res = new LinkedList<String>();
					AOperationDeclaration od = (AOperationDeclaration)w.value;
					for(SOperationDefinition sod : od.getOperationDefinitions())
					{
						res.add("[O] "+notNullName(sod.getName()));
					}
					return res.toArray();
				}
				
				if (((Wrapper)n).isClass(AActionDeclaration.class))
				{
					List<String> res = new LinkedList<String>();
					AActionDeclaration ad = (AActionDeclaration)n;
					for(AActionDefinition a : ad.getActionDefinition())
					{
						res.add("[A] "+notNullName(a.getName()));
					}
					return res.toArray();

				}
			}
		
		return new String[0];
	}

	private static String notNullName(LexNameToken name)
	{
		if (name == null) return "null";
		return name.name;
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

package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.definitions.SFunctionDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider
  {
    
    public CmlTreeContentProvider()
      {
        
      }
    
    @Override
    public void dispose()
      {
        
      }
    
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
      }
    
    private PSource current;
    
    @Override
    public Object[] getElements(Object inputElement)
      {
        if (inputElement instanceof CmlSourceUnit)
          {
            // Get current source tree
            current = ((CmlSourceUnit) inputElement).getSourceAst();
            if (current == null)
              return new Object[0];
            
            // If there are any declarations lets see them
            LinkedList<SParagraphDefinition> decls = current.getParagraphs();
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
    public Object[] getChildren(Object parentElement)
      {
        Object n = parentElement;
        if (n instanceof AClassParagraphDefinition)
          {
            List<Wrapper<PDefinition>> res = new LinkedList<CmlTreeContentProvider.Wrapper<PDefinition>>();
            AClassParagraphDefinition clzdecl = (AClassParagraphDefinition) n;
            for (eu.compassresearch.ast.definitions.PDefinition decl : clzdecl
                .getDefinitions())
              {
                if (decl instanceof AValueDefinition)
                  res.add(new Wrapper<PDefinition>(decl, "Values"));
                else if (decl instanceof SFunctionDefinition)
                  res.add(new Wrapper<PDefinition>(decl, "Functions"));
                else if (decl instanceof SOperationDefinition)
                  res.add(new Wrapper<PDefinition>(decl, "Operations"));
              }
            return res.toArray();
          }
        
        if (n instanceof AProcessParagraphDefinition)
          {
            AProcessParagraphDefinition processDecl = (AProcessParagraphDefinition) n;
            return new String[] { "Process[" + processDecl.getName() + "]" };
          }
        
        if (n instanceof Wrapper)
          {
            Wrapper w = (Wrapper) n;
            if (((Wrapper) n).isClass(AValueParagraphDefinition.class))
              {
                List<String> res = new LinkedList<String>();
                AValueParagraphDefinition valDecl = (AValueParagraphDefinition) w.value;
                if (valDecl != null)
                  for (PDefinition def : valDecl.getValueDefinitions())
                    {
                      
                      LexIdentifierToken name = def.getName();
                      res.add(name != null ? name.name : "null" + " : "
                          + def.getType());
                    }
                return res.toArray();
              }
            
            if (((Wrapper) n).isClass(AFunctionParagraphDefinition.class))
              {
                List<String> res = new LinkedList<String>();
                AFunctionParagraphDefinition fd = (AFunctionParagraphDefinition) w.value;
                for (SFunctionDefinition fnd : fd.getFunctionDefinitions())
                  {
                    res.add("[F] " + notNullName(fnd.getName()));
                  }
                return res.toArray();
              }
            
            if (((Wrapper) n).isClass(AOperationParagraphDefinition.class))
              {
                List<String> res = new LinkedList<String>();
                AOperationParagraphDefinition od = (AOperationParagraphDefinition) w.value;
                for (SOperationDefinition sod : od.getOperations())
                  {
                    res.add("[O] " + notNullName(sod.getName()));
                  }
                return res.toArray();
              }
            
            if (((Wrapper) n).isClass(AActionParagraphDefinition.class))
              {
                List<String> res = new LinkedList<String>();
                AActionParagraphDefinition ad = (AActionParagraphDefinition) n;
                for (AActionDefinition a : ad.getActions())
                  {
                    res.add("[A] " + notNullName(a.getName()));
                  }
                return res.toArray();
                
              }
          }
        
        return new String[0];
      }
    
    private static String notNullName(LexIdentifierToken name)
      {
        if (name == null)
          return "null";
        return name.name;
      }
    
    @Override
    public Object getParent(Object element)
      {
        return null;
      }
    
    @Override
    public boolean hasChildren(Object element)
      {
        return true;
      }
    
  }

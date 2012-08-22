package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.definitions.SFunctionDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider
  {
    
    private final Control parentControl;
    
    public CmlTreeContentProvider(Control control)
      {
        parentControl = control;
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
            List<Wrapper<PDefinition>> res = new LinkedList<CmlTreeContentProvider.Wrapper<PDefinition>>();
            for (PDefinition def : current.getParagraphs())
              res.add(Wrapper.newInstance(def, def.getName().name));
            return res.toArray();
          }
        return new Object[0];
      }
    
    private static class Wrapper<T> extends FieldDecoration
      {
        private String str;
        
        public static <T> Wrapper<T> newInstance(T value, String str)
          {
            Image icon = new Image(Display.getCurrent(),
                "/home/rwl/Desktop/PhD/develop/cml/ide/cml/ui/icons/cview16/cml_file_tsk.png");
            return new Wrapper<T>(icon, value, str);
          }
        
        public Wrapper(Image img, T value, String str)
          {
            super(img, str);
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
        
        if (n instanceof Wrapper)
          {
            Wrapper w = (Wrapper) n;
            
            if (w.isClass(AClassParagraphDefinition.class))
              {
                List<Wrapper<PDefinition>> res = new LinkedList<Wrapper<PDefinition>>();
                AClassParagraphDefinition clzdecl = (AClassParagraphDefinition) w.value;
                for (PDefinition section : clzdecl.getDefinitions())
                  {
                    if (section instanceof AValueParagraphDefinition)
                      res.add(Wrapper.newInstance(section, "Values"));
                    else if (section instanceof AFunctionParagraphDefinition)
                      res.add(Wrapper.newInstance(section, "Functions"));
                    else if (section instanceof AOperationParagraphDefinition)
                      res.add(Wrapper.newInstance(section, "Operations"));
                    else
                      res.add(Wrapper.newInstance(section,
                          section.getName().name));
                  }
                return res.toArray();
              }
            
            if (w.isClass(AValueParagraphDefinition.class))
              {
                return handleValueParagraphDefinition(
                    (AValueParagraphDefinition) w.value).toArray();
              }
            
            if (w.isClass(AProcessParagraphDefinition.class))
              {
                AProcessParagraphDefinition processDecl = (AProcessParagraphDefinition) w.value;
                AProcessDefinition def = processDecl.getProcessDefinition();
                PProcess process = def.getProcess();
                
                if (process instanceof AStateProcess)
                  {
                    List<Object> res = new LinkedList<Object>();
                    AStateProcess sp = (AStateProcess) process;
                    PAction a = sp.getAction();
                    addActions(res, a);
                    return res.toArray();
                  }
                
                return new Object[] { "Not Implemented Yet" };
              }
            
            if (w.isClass(ASequentialCompositionProcess.class))
              {
                List<Object> res = new LinkedList<Object>();
                ASequentialCompositionProcess comp = (ASequentialCompositionProcess) w.value;
                handleSequentialCompisitionProcess(res, comp);
                return res.toArray();
              }
            
            if (((Wrapper) n).isClass(AFunctionParagraphDefinition.class))
              {
                List<String> res = new LinkedList<String>();
                AFunctionParagraphDefinition fd = (AFunctionParagraphDefinition) w.value;
                for (SFunctionDefinition fnd : fd.getFunctionDefinitions())
                  {
                    res.add("" + notNullName(fnd.getName()) + ":"
                        + fnd.getType());
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
                AActionParagraphDefinition ad = (AActionParagraphDefinition) w.value;
                for (AActionDefinition a : ad.getActions())
                  {
                    res.add("[A] " + notNullName(a.getName()));
                  }
                return res.toArray();
                
              }
          }
        
        return new String[0];
      }
    
    private void handleSequentialCompisitionProcess(List<Object> res,
        ASequentialCompositionProcess comp)
      {
        PProcess left = comp.getLeft();
        PProcess right = comp.getRight();
        
        if (left instanceof ASequentialCompositionProcess)
          handleSequentialCompisitionProcess(res,
              (ASequentialCompositionProcess) left);
        else
          handleOtherProcess(res, left);
        
        if (right instanceof ASequentialCompositionProcess)
          handleSequentialCompisitionProcess(res,
              (ASequentialCompositionProcess) right);
        else
          handleOtherProcess(res, right);
      }
    
    private void handleOtherProcess(List<Object> res, PProcess p)
      {
        if (p instanceof AStateProcess)
          res.add("State Process");
        else
          res.add("** Process **");
      }
    
    private void addActions(List<Object> res, PAction a)
      {
        if (a instanceof ASkipAction)
          {
            res.add("Skip");
            return;
          }
        if (a instanceof AStopAction)
          {
            res.add("Stop");
            return;
          }
        if (a instanceof AChaosAction)
          {
            res.add("Chaos");
            return;
          }
        if (a instanceof ASequentialCompositionAction)
          {
            ASequentialCompositionAction seqa = (ASequentialCompositionAction) a;
            PAction left = seqa.getLeft();
            PAction right = seqa.getRight();
            addActions(res, left);
            addActions(res, right);
            return;
          }
      }
    
    private List<String> handleValueParagraphDefinition(
        AValueParagraphDefinition cast)
      {
        List<String> res = new LinkedList<String>();
        LinkedList<PDefinition> valDefs = cast.getValueDefinitions();
        for (PDefinition def : valDefs)
          res.add(def.getName().name + ":" + def.getType());
        return res;
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
        return element instanceof Wrapper;
      }
    
  }

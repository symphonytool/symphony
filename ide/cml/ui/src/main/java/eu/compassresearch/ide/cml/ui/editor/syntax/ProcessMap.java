package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AChannelRenamingProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AExternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInterleavingReplicatedProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASequentialCompositionReplicatedProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.process.SReplicatedProcessBase;
import eu.compassresearch.ide.cml.ui.editor.syntax.DefinitionMap.DefinitionHandler;

public class ProcessMap {

    private static class AStateProcessHandler implements ProcessHandler {

	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AActionProcess asp = (AActionProcess) proc;
	    r.add(Wrapper.newInstance(asp.getAction(), "@ "
		    + asp.getAction().toString())); //FIXME do not use toStrings()
	    for (PDefinition pdef : asp.getDefinitionParagraphs()) {
		DefinitionHandler dh = DefinitionMap.getDelegate(pdef
			.getClass());
		if (dh != null)
		    r.addAll(dh.extractSubdefinition(pdef));
	    }
	    return r;
	}

    }

    public interface ProcessHandler {
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc);
    }

    public static final Map<Class<?>, ? extends ProcessHandler> PROCESS_MAP = createMap();

    private static Map<Class<?>, ? extends ProcessHandler> createMap() {
	Map<Class<?>, ProcessHandler> map = new HashMap<Class<?>, ProcessHandler>();
	map.put(AAlphabetisedParallelismProcess.class,
		new AAlphabetisedParallelismProcessHandler());
	map.put(AAlphabetisedParallelismReplicatedProcess.class,
		new AAlphabetisedParallelismReplicatedProcessHandler());
	map.put(AChannelRenamingProcess.class,
		new AChannelRenamingProcessHandler());
	map.put(AEndDeadlineProcess.class, new AEndDeadlineProcessHandler());
	map.put(AExternalChoiceProcess.class,
		new AExternalChoiceProcessHandler());
	map.put(AExternalChoiceReplicatedProcess.class,
		new AExternalChoiceReplicatedProcessHandler());
	map.put(AGeneralisedParallelismProcess.class,
		new AGeneralisedParallelismProcessHandler());
	map.put(AGeneralisedParallelismReplicatedProcess.class,
		new AGeneralisedParallelismReplicatedProcessHandler());
	map.put(AHidingProcess.class, new AHidingProcessHandler());
	map.put(AInstantiationProcess.class, new AInstantiationProcessHandler());
	map.put(AInterleavingProcess.class, new AInterleavingProcessHandler());
	map.put(AInterleavingReplicatedProcess.class,
		new AInterleavingReplicatedProcessHandler());
	map.put(AInternalChoiceProcess.class,
		new AInternalChoiceProcessHandler());
	map.put(AInternalChoiceReplicatedProcess.class,
		new AInternalChoiceReplicatedProcessHandler());
	map.put(AInterruptProcess.class, new AInterruptProcessHandler());
//	map.put(AReferenceProcess.class, new AReferenceProcessHandler()); // Should not show up at root level.
	map.put(ASequentialCompositionProcess.class,
		new ASequentialCompositionProcessHandler());
	map.put(ASequentialCompositionReplicatedProcess.class,
		new ASequentialCompositionReplicatedProcessHandler());
	map.put(AStartDeadlineProcess.class, new AStartDeadlineProcessHandler());
	map.put(AActionProcess.class, new AStateProcessHandler());
	map.put(ASynchronousParallelismProcess.class,
		new ASynchronousParallelismProcessHandler());
	map.put(ASynchronousParallelismReplicatedProcess.class,
		new ASynchronousParallelismReplicatedProcessHandler());
	map.put(ATimedInterruptProcess.class,
		new ATimedInterruptProcessHandler());
	map.put(ATimeoutProcess.class, new ATimeoutProcessHandler());
	map.put(AUntimedTimeoutProcess.class,
		new AUntimedTimeoutProcessHandler());

	// TODO-ldc Add a ton more remaining processes :(
	return Collections.unmodifiableMap(map);
    }

    public static ProcessHandler getDelegate(Class<?> cls) {
	ProcessHandler r = PROCESS_MAP.get(cls);
	if (r == null)
	    System.err.println("No delegate found for process class "
		    + cls.getCanonicalName());
	return r;
    }

    private static class  AStartDeadlineProcessHandler implements ProcessHandler {

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AStartDeadlineProcess startdeadproc= (AStartDeadlineProcess) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(startdeadproc.getLeft());
	    sb.append(" startsby e");
//	    sb.append(startdeadproc.getExpression());
	    r.add(Wrapper.newInstance(proc, sb.toString()));

	    return r;
	}
	
    }
    
    private static class AInstantiationProcessHandler implements ProcessHandler{

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AInstantiationProcess instantproc = ( AInstantiationProcess) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(instantproc.getProcess());
	    sb.append(" (e)");
	    r.add(Wrapper.newInstance(proc, sb.toString()));

	    return r;

	}
	
    }
    
    private static class AEndDeadlineProcessHandler implements ProcessHandler{

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AEndDeadlineProcess enddeadproc = (AEndDeadlineProcess) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(enddeadproc.getLeft());
	    sb.append(" endsby e");
//	    sb.append(chanproc.getRenameExpression());
	    r.add(Wrapper.newInstance(proc, sb.toString()));

	    return r;
	}
	
    }
  
    private static class AChannelRenamingProcessHandler implements
	    ProcessHandler {

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AChannelRenamingProcess chanproc = (AChannelRenamingProcess) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(chanproc.getProcess());
	    sb.append(" [[c <- nc]] ");
//	    sb.append(chanproc.getRenameExpression());
	    r.add(Wrapper.newInstance(proc, sb.toString()));

	    return r;
	}

    }

    private static class AHidingProcessHandler implements ProcessHandler {

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    AHidingProcess hideproc = (AHidingProcess) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(hideproc.getLeft());
	    sb.append(" \\\\ cs ");
//	    sb.append("[ " + hideproc.getChansetExpression() + " ]");
	    r.add(Wrapper.newInstance(proc, sb.toString()));

	    return r;
	}

    }

    private static class GeneralReplicatedProcessHandler implements
	    ProcessHandler {

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    SReplicatedProcessBase repliproc = (SReplicatedProcessBase) proc;

	    StringBuilder sb = new StringBuilder();
	    sb.append(getReplicateSign());
	    sb.append(repliproc.getReplicatedProcess().toString());
	    r.add(Wrapper.newInstance(proc, sb.toString()));
	    return r;
	}
	
	public String getReplicateSign(){
	    return "rplc @ ";
	}

    }

    private static class ASequentialCompositionReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {

    }

    private static class AAlphabetisedParallelismReplicatedProcessHandler
	    extends GeneralReplicatedProcessHandler {
    }

    private static class AExternalChoiceReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {
    }

    private static class AGeneralisedParallelismReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {

    }

    private static class AInterleavingReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {

    }

    private static class ASynchronousParallelismReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {

    }

    private static class AInternalChoiceReplicatedProcessHandler extends
	    GeneralReplicatedProcessHandler {

    }

    private abstract static class GeneralLeftRightProcessHandler implements
	    ProcessHandler {

	public String getConnectionSymbol(PProcess proc) {
	    return " ? ";
	}

	public PProcess getLeft(PProcess proc) {
	    for (Method m : proc.getClass().getMethods()) {
		if ("getLeft".equals(m.getName())) {
		    try {
			return (PProcess) m.invoke(proc, new Object[0]);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	    return null;
	}

	public PProcess getRight(PProcess proc) {
	    for (Method m : proc.getClass().getMethods()) {
		if ("getRight".equals(m.getName())) {
		    try {
			return (PProcess) m.invoke(proc, new Object[0]);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	    return null;
	}

	@Override
	public List<Wrapper<? extends INode>> makeEntries(PProcess proc) {
	    List<Wrapper<? extends INode>> r = new LinkedList<Wrapper<? extends INode>>();
	    PProcess leftproc = getLeft(proc);
	    PProcess rightproc = getRight(proc);
//	    r.addAll(ProcessMap.getDelegate(leftproc.getClass()).makeEntries(leftproc));
	    r.add(Wrapper.newInstance(leftproc, leftproc.toString()));
	    r.add(Wrapper.newInstance(new AReferenceProcess(), getConnectionSymbol(proc)));
//	    r.addAll(ProcessMap.getDelegate(rightproc.getClass()).makeEntries(rightproc));	    
	    r.add(Wrapper.newInstance(rightproc, rightproc.toString()));
	    
//	    StringBuilder sb = new StringBuilder();
//	    sb.append(getLeft(proc).toString());
//	    sb.append(this.getConnectionSymbol(proc));
//	    sb.append(getRight(proc).toString());
//	    r.add(Wrapper.newInstance(proc, sb.toString()));
	    return r;
	}
    }

    private static class AUntimedTimeoutProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " [> ";
	}

    }

    private static class ATimedInterruptProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " /e\\ ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((ATimedInterruptProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((ATimedInterruptProcess) proc).getRight();
	}

    }

    private static class AInterruptProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " /\\ ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AInterruptProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AInterruptProcess) proc).getLeft();
	}

    }

    private static class ATimeoutProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " [ e >  ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((ATimeoutProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((ATimeoutProcess) proc).getRight();
	}

    }

    private static class ASequentialCompositionProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " ; ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((ASequentialCompositionProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((ASequentialCompositionProcess) proc).getRight();
	}

    }

    private static class AAlphabetisedParallelismProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " [ X || Y ]] ";
	    // AAlphabetisedParallelismProcess app =
	    // (AAlphabetisedParallelismProcess) proc;
	    // StringBuilder sb = new StringBuilder();
	    // sb.append("[ " + app.getLeftChansetExpression().toString());
	    // sb.append(" || ");
	    // sb.append(app.getRightChansetExpression().toString() + " ]");
	    // return sb.toString();
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AAlphabetisedParallelismProcess) proc).getLeft();

	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AAlphabetisedParallelismProcess) proc).getRight();
	}

    }

    private static class AInterleavingProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " ||| ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AInterleavingProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AInterleavingProcess) proc).getRight();
	}

    }

    private static class AInternalChoiceProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " |~| ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AInternalChoiceProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AInternalChoiceProcess) proc).getRight();
	}

    }

    private static class AExternalChoiceProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " [] ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AExternalChoiceProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AExternalChoiceProcess) proc).getRight();
	}

    }

    private static class AGeneralisedParallelismProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((AGeneralisedParallelismProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((AGeneralisedParallelismProcess) proc).getRight();
	}

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " [| cs |] ";
	}

    }

    private static class ASynchronousParallelismProcessHandler extends
	    GeneralLeftRightProcessHandler {

	@Override
	public String getConnectionSymbol(PProcess proc) {
	    return " || ";
	}

	@Override
	public PProcess getLeft(PProcess proc) {
	    return ((ASynchronousParallelismProcess) proc).getLeft();
	}

	@Override
	public PProcess getRight(PProcess proc) {
	    return ((ASynchronousParallelismProcess) proc).getRight();
	}

    }

    // private String prettyPrint(PExp chansetExpression) {
    // StringBuilder sb = new StringBuilder();
    // if (chansetExpression instanceof AEnumChansetSetExp) {
    // AEnumChansetSetExp temp = (AEnumChansetSetExp) chansetExpression;
    // sb.append("[|{| ");
    // for (LexIdentifierToken lit : temp.getIdentifiers()) {
    // sb.append(lit.toString() + ", ");
    // }
    // sb.delete(sb.length() - 2, sb.length());
    // sb.append(" |}|]");
    // }
    // return sb.toString();
    // }

}

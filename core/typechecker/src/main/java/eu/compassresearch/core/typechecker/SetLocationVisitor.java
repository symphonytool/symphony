package eu.compassresearch.core.typechecker;

import java.lang.reflect.Method;
import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PAlternativeAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.SInstantiatedAction;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.PCMLAccess;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SChannelExp;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.expressions.SVOpVarsetExpression;
import eu.compassresearch.ast.patterns.PCMLPair;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.ATcpStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.PCMLType;

@SuppressWarnings("serial")
public class SetLocationVisitor extends DepthFirstAnalysisCMLAdaptor{

	private PSource root;

	public static void updateLocations(Collection<PSource> lp)
	{

		SetLocationVisitor lv = new SetLocationVisitor();
		for(PSource s : lp)
		{
			if (s != null)
				try {
					lv.root = s;
					s.apply(lv);
				} catch (AnalysisException e) {
				}
		}
	}

	private SetLocationVisitor()
	{
	}


	private void setTheLocation(Object node)
	{
		try {
			Class<?> clz = node.getClass();
			Method getLocation = clz.getMethod("getLocation", new Class<?>[0]);
			if (getLocation == null) return;

			LexLocation oldLocation = (LexLocation) getLocation.invoke(node,new Object[0]);	

			if (oldLocation != null)
			{
				String filePath = "";
				if (root != null)
				{
					if (root instanceof AFileSource)
						filePath = ((AFileSource) root).getFile()+"";

					if (root instanceof AInputStreamSource)
						filePath = ((AInputStreamSource) root).getOrigin();

					if(root instanceof ATcpStreamSource)
						filePath = ((ATcpStreamSource) root).getIp()+"";

				}
				LexLocation newLocation = new LexLocation(filePath, oldLocation.module, oldLocation.startLine, oldLocation.startPos, oldLocation.endLine, oldLocation.endPos, oldLocation.startOffset, oldLocation.endOffset);

				Method setLocation = clz.getMethod("setLocation", new Class<?>[] {LexLocation.class});
				if (setLocation == null) return;

				setLocation.invoke(node, new Object[] {newLocation});
			}

		}
		catch (NoSuchMethodException eee)
		{
			// shut up
		}
		catch(NoSuchMethodError ee)
		{
			// shut up
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// well was not supposed to be :S
		}
	}



	@Override
	public void defaultInPSource(PSource node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCMLDefinition(PCMLDefinition node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSCmlOperationDefinition(SCmlOperationDefinition node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCMLExp(PCMLExp node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSChannelExp(SChannelExp node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSRenameChannelExp(SRenameChannelExp node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPVarsetExpression(PVarsetExpression node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSVOpVarsetExpression(SVOpVarsetExpression node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCMLType(PCMLType node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCMLAccess(PCMLAccess node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCMLPair(PCMLPair node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPProcess(PProcess node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSReplicatedProcess(SReplicatedProcess node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPAction(PAction node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSParallelAction(SParallelAction node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSInstantiatedAction(SInstantiatedAction node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSReplicatedAction(SReplicatedAction node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSStatementAction(SStatementAction node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPCommunicationParameter(PCommunicationParameter node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPParametrisation(PParametrisation node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInSGeneralAssignmentStatementAction(
			SGeneralAssignmentStatementAction node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultInPAlternativeAction(PAlternativeAction node)
			throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void defaultOutINode(INode node) throws AnalysisException {
		setTheLocation(node);
	}

	@Override
	public void caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node) throws AnalysisException {
		setTheLocation(node);
	}


}

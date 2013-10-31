package eu.compassresearch.ide.core.unsupported;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;

/**
 * This class traverses an entire model and collects information on elements that are not supported by a particular
 * analysis or feature of CML. <b>Warning:</b> The tree traversal is handled by this class to any subclasses must invoke
 * the super in their cases.
 * 
 * @author ldc
 */
public abstract class UnsupportedCollector extends DepthFirstAnalysisCMLAdaptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<UnsupportedElementInfo> unsupporteds = new LinkedList<UnsupportedElementInfo>();
	UnsupportingFeatures feature;
	protected boolean unsupported;

	public UnsupportedCollector(UnsupportingFeatures feature)
	{
		super();
		this.unsupported = true;
		this.feature = feature;
	}

	public List<UnsupportedElementInfo> getUnsupporteds()
	{
		return unsupporteds;
	}

	public void setUnsupporteds(List<UnsupportedElementInfo> unsupporteds)
	{
		this.unsupporteds = unsupporteds;
	}

	public UnsupportingFeatures getFeature()
	{
		return feature;
	}

	public void setFeature(UnsupportingFeatures feature)
	{
		this.feature = feature;
	}

	public List<UnsupportedElementInfo> getUnsupporteds(List<INode> ast)
			throws AnalysisException
	{
		for (INode node : ast)
		{
			node.apply(this);
		}
		return unsupporteds;
	}

	@Override
	public void defaultOutPExp(PExp node) throws AnalysisException
	{

		if (unsupported)
		{
			UnsupportedElementInfo uei = new UnsupportedElementInfo();
			uei.setElementname("Any expression");
			uei.setLocation(node.getLocation());
			uei.setMessage(node.getClass().getSimpleName().toString()
					+ " expressions are not supported by the "
					+ feature.toString());
			unsupporteds.add(uei);
		} else
		{
			unsupported = true;
		}

	}

//	/**
//	 * Special dedicated case since the more general {@link PModifier} does not have location info. Override as needed.
//	 * But keep super call.
//	 */
//	@Override
//	public void caseARecordModifier(ARecordModifier node)
//			throws AnalysisException
//	{
//
//		if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getTag().getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.caseARecordModifier(node);
//	}
//
//	@Override
//	public void defaultOutPAlternative(PAlternative node)
//			throws AnalysisException
//	{
//		if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//	}
//
//	@Override
//	public void defaultOutPType(PType node) throws AnalysisException
//	{
//		if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//	}
//
//	@Override
//	public void defaultOutPField(PField node) throws AnalysisException
//	{
//		if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//	}
//
//	@Override
//	public void defaultOutPAccessSpecifier(PAccessSpecifier node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPAccessSpecifier(node);
//	}
//
//	@Override
//	public void defaultOutPAccess(PAccess node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPAccess(node);
//	}
//
//	@Override
//	public void defaultOutPPattern(PPattern node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPPattern(node);
//	}
//
//	@Override
//	public void defaultOutPMaplet(PMaplet node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPMaplet(node);
//	}
//
//	@Override
//	public void defaultOutPPair(PPair node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPPair(node);
//	}
//
//	@Override
//	public void defaultOutPBind(PBind node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPBind(node);
//	}
//
//	@Override
//	public void defaultOutPMultipleBind(PMultipleBind node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPMultipleBind(node);
//	}
//
//	@Override
//	public void defaultOutPPatternBind(PPatternBind node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPPatternBind(node);
//	}
//
//	@Override
//	public void defaultOutPDefinition(PDefinition node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPDefinition(node);
//	}
//
//	@Override
//	public void defaultOutPTerm(PTerm node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPTerm(node);
//	}
//
//	@Override
//	public void defaultOutPTraceDefinition(PTraceDefinition node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPTraceDefinition(node);
//	}
//
//	@Override
//	public void defaultOutPTraceCoreDefinition(PTraceCoreDefinition node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPTraceCoreDefinition(node);
//	}
//
//	@Override
//	public void defaultOutPModules(PModules node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPModules(node);
//	}
//
//	@Override
//	public void defaultOutPImports(PImports node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPImports(node);
//	}
//
//	@Override
//	public void defaultOutPImport(PImport node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPImport(node);
//	}
//
//	@Override
//	public void defaultOutPExports(PExports node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPExports(node);
//	}
//
//	@Override
//	public void defaultOutPExport(PExport node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPExport(node);
//	}
//
//	@Override
//	public void defaultOutPStm(PStm node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPStm(node);
//	}
//
//	@Override
//	public void defaultOutPStateDesignator(PStateDesignator node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPStateDesignator(node);
//	}
//
//	@Override
//	public void defaultOutPObjectDesignator(PObjectDesignator node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPObjectDesignator(node);
//	}
//
//	@Override
//	public void defaultOutPAlternativeStm(PAlternativeStm node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPAlternativeStm(node);
//	}
//
//	@Override
//	public void defaultOutPStmtAlternative(PStmtAlternative node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPStmtAlternative(node);
//	}
//
//	@Override
//	public void defaultOutPClause(PClause node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPClause(node);
//	}
//
//	@Override
//	public void defaultOutPCase(PCase node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCase(node);
//	}
//
//	@Override
//	public void defaultOutPSource(PSource node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPSource(node);
//	}
//
//	@Override
//	public void defaultOutPSingleDeclaration(PSingleDeclaration node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPSingleDeclaration(node);
//	}
//
//	@Override
//	public void defaultOutPVarsetExpression(PVarsetExpression node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPVarsetExpression(node);
//	}
//
//	@Override
//	public void defaultOutPProcess(PProcess node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPProcess(node);
//	}
//
//	@Override
//	public void defaultOutPAction(PAction node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPAction(node);
//	}
//
//	@Override
//	public void defaultOutPCommunicationParameter(PCommunicationParameter node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCommunicationParameter(node);
//	}
//
//	@Override
//	public void defaultOutPParametrisation(PParametrisation node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPParametrisation(node);
//	}
//
//	@Override
//	public void defaultOutPAlternativeAction(PAlternativeAction node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPAlternativeAction(node);
//	}
//
//	@Override
//	public void defaultOutPCMLType(PCMLType node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCMLType(node);
//	}
//
//	@Override
//	public void defaultOutPCMLPair(PCMLPair node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCMLPair(node);
//	}
//
//	@Override
//	public void defaultOutPCMLAccess(PCMLAccess node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCMLAccess(node);
//	}
//
//	@Override
//	public void defaultOutPCMLDefinition(PCMLDefinition node)
//			throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCMLDefinition(node);
//	}
//
//	@Override
//	public void defaultOutPCMLExp(PCMLExp node) throws AnalysisException
//	{
//				if (unsupported)
//		{
//			UnsupportedElementInfo uei = new UnsupportedElementInfo();
//			uei.setElementname("Any expression");
//			uei.setLocation(node.getLocation());
//			uei.setMessage(node.getClass().getSimpleName().toString()
//					+ " expressions are not supported by the "
//					+ feature.toString());
//			unsupporteds.add(uei);
//		} else
//		{
//			unsupported = true;
//		}
//		super.defaultOutPCMLExp(node);
//	}
//
//	@Override
//	public void defaultInPDefinition(PDefinition node) throws AnalysisException
//	{
//		try
//		{
//			super.defaultInPDefinition(node);
//		} catch (AnalysisException e)
//		{
//
//		}
//	}
//
//	@Override
//	public void caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node)
//			throws AnalysisException
//	{
//		unsupported = false;
//		// Do not touch the line below!
//		super.caseAAndBooleanBinaryExp(node);
//	}

}

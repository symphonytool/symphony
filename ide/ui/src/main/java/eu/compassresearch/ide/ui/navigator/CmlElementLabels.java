package eu.compassresearch.ide.ui.navigator;

import org.eclipse.jface.viewers.StyledString;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.ide.ui.internal.viewsupport.VdmElementLabels;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.types.AChannelType;

public class CmlElementLabels extends VdmElementLabels
{
	static TextLabelCreator textLabelCreator = new TextLabelCreator();

	public static StyledString getStyledTextLabel(Object element, long flags)
	{

		if (element instanceof INode)
		{
			StyledString result = null;
			try
			{
				result = ((INode) element).apply(textLabelCreator);
			} catch (AnalysisException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result != null)
			{
				return result;
			}
		}

		return VdmElementLabels.getStyledTextLabel(element, flags);
	}

	private static class TextLabelCreator extends
			AnswerCMLAdaptor<StyledString>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public StyledString caseAExplicitCmlOperationDefinition(
				AExplicitCmlOperationDefinition node) throws AnalysisException
		{
			StyledString result = new StyledString();

			result.append(node.getName().getSimpleName());

			if (node.getType() instanceof AOperationType)
			{
				AOperationType type = (AOperationType) node.getType();
				if (type.getParameters().size() == 0)
				{
					result.append("() ");
				} else
				{
					result.append("(");
					int i = 0;
					while (i < type.getParameters().size() - 1)
					{
						PType definition = (PType) type.getParameters().get(i);
						result.append(getSimpleTypeString(definition) + ", ");

						i++;
					}
					PType definition = (PType) type.getParameters().get(i);
					result.append(getSimpleTypeString(definition) + ")");
				}

				if (type.getResult() instanceof AVoidType)
				{
					result.append(" : ()", StyledString.DECORATIONS_STYLER);
				} else
				{
					result.append(" : " + getSimpleTypeString(type.getResult()), StyledString.DECORATIONS_STYLER);
				}
			}

			return result;
		}

		@Override
		public StyledString caseAActionDefinition(AActionDefinition node)
				throws AnalysisException
		{
			StyledString result = new StyledString();
			result.append(node.getName().getName());
			return result;
		}

		
		public StyledString caseAValueDefinition(AValueDefinition node)
				throws AnalysisException
		{
			StyledString result = new StyledString();
			result.append(node.getPattern().toString());
			if (node.getType().getLocation().getModule().toLowerCase().equals("default"))
			{
				result.append(" : " + getSimpleTypeString(node.getType()), StyledString.DECORATIONS_STYLER);
			} else
			{
				result.append(" : " + // node.getType().getLocation().getModule() + "`"
						// +
						getSimpleTypeString(node.getType()), StyledString.DECORATIONS_STYLER);
			}
			return result;
		}

		@Override
		public StyledString caseAChannelNameDefinition(
				AChannelNameDefinition node) throws AnalysisException
		{
			StyledString result = new StyledString();
			result.append(node.getSingleType().getIdentifiers().toString());
			if (node.getSingleType().getType() instanceof AChannelType
					&& ((AChannelType) node.getSingleType().getType()).getType() != null)
			{
				result.append(" : "
						+ getSimpleTypeString(node.getSingleType().getType()), StyledString.DECORATIONS_STYLER);
			}

			return result;
		}
		
		@Override
		public StyledString caseAProcessDefinition(AProcessDefinition node)
				throws AnalysisException
		{
			StyledString result = new StyledString();
			
			result.append(node.getName().getName());
			if(node.getProcess() instanceof AActionProcess)
			{
			result.append(" : "
					+ ((AActionProcess)node.getProcess()).getAction(), StyledString.DECORATIONS_STYLER);
			}
			return result;
		}
		
		@Override
		public StyledString caseAStateDefinition(AStateDefinition node)
				throws AnalysisException
		{
			StyledString result = new StyledString();
			result.append(node.getName().getSimpleName());
			if (node.getType().getLocation().getModule().toLowerCase().equals("default")) {
				result.append(" : " + getSimpleTypeString(node.getType()),
						StyledString.DECORATIONS_STYLER);
			} else {
				result.append(" : " + node.getType().getLocation().getModule() + "`"
						+ getSimpleTypeString(node.getType()),
						StyledString.DECORATIONS_STYLER);
			}
			return result;
		}
		
		@Override
		public StyledString caseAAssignmentDefinition(AAssignmentDefinition node)
				throws AnalysisException
		{
			
				StyledString result = new StyledString();
				result.append(node.getName().getSimpleName());
				result.append(" : " + getSimpleTypeString(node.getType()),
						StyledString.DECORATIONS_STYLER);
				return result;
		}
		
		@Override
		public StyledString caseAChansetDefinition(AChansetDefinition node)
				throws AnalysisException
		{
			StyledString result = new StyledString();
			result.append(node.getIdentifier().toString());
			result.append(" : " + node.getChansetExpression().toString().replace('[', '{').replace(']', '}'),
					StyledString.DECORATIONS_STYLER);
			return result;
		}
		
		@Override
		public StyledString caseATypesDefinition(ATypesDefinition node)
				throws AnalysisException
		{
			return new StyledString("Types");
		}
		@Override
		public StyledString caseAValuesDefinition(AValuesDefinition node)
				throws AnalysisException
		{
			return new StyledString("Values");
		}
		
		@Override
		public StyledString caseAFunctionsDefinition(AFunctionsDefinition node)
				throws AnalysisException
		{
			return new StyledString("Functions");
		}
		
		@Override
		public StyledString caseAChannelsDefinition(AChannelsDefinition node)
				throws AnalysisException
		{
			return new StyledString("Channels");
		}
		
		@Override
		public StyledString caseAChansetsDefinition(AChansetsDefinition node)
				throws AnalysisException
		{
			return new StyledString("Channel Sets");
		}
		
	}

}

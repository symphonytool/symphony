/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package eu.compassresearch.ide.interpreter.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.overture.ide.debug.core.dbgp.IDbgpProperty;
import org.overture.ide.debug.utils.StrUtils;

public class CmlVariable extends CmlDebugElement implements IVariable
// , IRefreshableVdmVariable
{
	private final CmlStackFrame frame;
	private final String name;
	private IDbgpProperty property;
	private IValue value;
	private boolean isValueChanged = false;

	public CmlVariable(CmlStackFrame frame, String name, IDbgpProperty property)
	{
		this.frame = frame;
		this.name = name;
		this.property = property;
	}

	public IDebugTarget getDebugTarget()
	{
		return frame.getDebugTarget();
	}

	public synchronized IValue getValue() throws DebugException
	{
		if (value == null)
		{
			value = CmlValue.createValue(frame, property);
		}
		return value;
	}

	public String getName()
	{
		return name;
	}

	public String getReferenceTypeName() throws DebugException
	{
		return property.getType();
	}

	public boolean hasValueChanged() throws DebugException
	{
		return isValueChanged;
	}

	public synchronized void setValue(String expression) throws DebugException
	{
		// try
		// {
		//			if (("String".equals(property.getType())) && //$NON-NLS-1$
		//					(!expression.startsWith("'") || !expression.endsWith("'")) && //$NON-NLS-1$ //$NON-NLS-2$
		//					(!expression.startsWith("\"") || !expression.endsWith("\""))) //$NON-NLS-1$ //$NON-NLS-2$
		//				expression = "\"" + expression.replaceAll("\\\"", "\\\\\"") + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		// // if (getCoreCommands().setProperty(property.getEvalName(),
		// // frame.getLevel(), expression)) {
		// // clearEvaluationManagerCache();
		// // update();
		//
		// // if (getCoreCommands().setProperty(property.getEvalName(), property.getKey(), expression))
		// {
		// // clearEvaluationManagerCache();
		// update();
		// }
		// } catch (Exception e)
		// {
		// // TODO: localize
		// throw wrapDbgpException("VdmVariable_cantAssignVariable", e);
		// }
	}

	// private IDbgpCoreCommands getCoreCommands()
	// {
	// return ((IVdmThread) frame.getThread()).getDbgpSession().getCoreCommands();
	// }

	// private void clearEvaluationManagerCache()
	// {
	// CmlThread thread = (CmlThread) frame.getThread();
	// thread.notifyModified();
	//
	// }

	// private void update()
	// {
	// this.value = null;
	//
	// // String key = property.getKey();
	// String name = property.getEvalName();
	//
	// // TODO: Use key if provided
	// // this.property = getCoreCommands().getProperty(name, frame.getLevel());
	//
	// // this.property = getCoreCommands().getPropertyByKey(property.getPage(), name, frame.getLevel(),
	// property.getKey());
	// DebugEventHelper.fireChangeEvent(this);
	// }

	public void setValue(IValue value) throws DebugException
	{
		setValue(value.getValueString());
	}

	public boolean supportsValueModification()
	{
		return !property.isConstant();
	}

	public boolean verifyValue(String expression) throws DebugException
	{
		// TODO: perform more smart verification
		return expression != null;
	}

	public boolean verifyValue(IValue value) throws DebugException
	{
		return verifyValue(value.getValueString());
	}

	public boolean isConstant()
	{
		return property.isConstant();
	}

	public String toString()
	{
		return getName();
	}

	public String getId()
	{
		return property.getKey();
	}

	/**
	 * @param newVariable
	 * @return
	 * @throws DebugException
	 */
	public IVariable refreshVariable(IVariable newVariable)
			throws DebugException
	{
		if (newVariable instanceof CmlVariable)
		{
			final CmlVariable v = (CmlVariable) newVariable;
			if (property.hasChildren() && v.property.hasChildren())
			{
				isValueChanged = false;
				if (value != null
						&& ((CmlValue) value).hasChildrenValuesLoaded())
				{
					/*
					 * Refresh children if some of them are loaded. Since it could be a hash - it is safer to get all of
					 * the new children.
					 */
					CmlStackFrame.refreshVariables(v.getValue().getVariables(), ((CmlValue) value).variables);
				}
			}
			isValueChanged = !equals(property, v.property);
			value = v.value;
			property = v.property;
			return this;
		} else
		{
			return newVariable;
		}
	}

	private static boolean equals(IDbgpProperty p1, IDbgpProperty p2)
	{
		if (p1.hasChildren() != p2.hasChildren())
		{
			return false;
		}
		if (!StrUtils.equals(p1.getType(), p2.getType()))
		{
			return false;
		}
		if (!StrUtils.equals(p1.getValue(), p2.getValue()))
		{
			return false;
		}
		if (StrUtils.isNotEmpty(p1.getKey())
				&& StrUtils.isNotEmpty(p2.getKey()))
		{
			return p1.getKey().equals(p2.getKey());
		}
		return true;
	}
}

package eu.compassresearch.ide.interpreter.model;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.overture.ide.debug.core.VdmDebugManager;
import org.overture.ide.debug.core.VdmDebugPlugin;
import org.overture.ide.debug.core.dbgp.IDbgpProperty;
import org.overture.ide.debug.core.dbgp.IDbgpStackLevel;
import org.overture.ide.debug.core.model.IRefreshableVdmVariable;

public class CmlStackFrame extends CmlDebugElement implements IStackFrame
{
	private static class CmlVariableContainer
	{
		IVariable[] locals = null;
		IVariable[] globals = null;
		IVariable[] classes = null;

		// VdmVariableWrapper globalsWrapper = null;
		// VdmVariableWrapper classesWrapper = null;

		CmlVariableContainer sort(IDebugTarget target)
		{
			final Comparator<Object> variableComparator = VdmDebugManager.getInstance().getVariableNameComparator();
			if (locals != null)
			{
				Arrays.sort(locals, variableComparator);
			}
			if (globals != null)
			{
				Arrays.sort(globals, variableComparator);
			}
			if (classes != null)
			{
				Arrays.sort(classes, variableComparator);
			}
			return this;
		}

		private int size()
		{
			int size = 0;
			if (locals != null)
			{
				size += locals.length;
			}
			if (globals != null && globals.length > 0)
			{
				++size;
			}
			if (classes != null && classes.length > 0)
			{
				++size;
			}
			return size;
		}

		CmlVariable[] toArray(IDebugTarget target)
		{
			final int size = size();
			final CmlVariable[] result = new CmlVariable[size];
			if (size != 0)
			{
				int index = 0;
				if (globals != null && globals.length > 0)
				{
					// if (globalsWrapper == null)
					// {
					// globalsWrapper = new VdmVariableWrapper(target, "Global Variables", globals);
					// } else
					// {
					// globalsWrapper.refreshValue(globals);
					// }
					// result[index++] = globalsWrapper;
				}
				if (classes != null && classes.length > 0)
				{
					// if (classesWrapper == null)
					// {
					// classesWrapper = new VdmVariableWrapper(target, "Instance Variables", classes);
					// } else
					// {
					// classesWrapper.refreshValue(classes);
					// }
					// result[index++] = classesWrapper;
				}
				if (locals != null)
				{
					System.arraycopy(locals, 0, result, index, locals.length);
					index += locals.length;
				}
			}
			return result;
		}

		/**
		 * @return
		 */
		public boolean hasVariables()
		{
			return locals != null && locals.length != 0 || classes != null
					|| globals != null;
		}

		/**
		 * @param varName
		 * @return
		 * @throws DebugException
		 */
		public IVariable findVariable(String varName) throws DebugException
		{
			if (locals != null)
			{
				final IVariable variable = findVariable(varName, locals);
				if (variable != null)
				{
					return variable;
				}
			}
			if (globals != null)
			{
				final IVariable variable = findVariable(varName, globals);
				if (variable != null)
				{
					return variable;
				}
			}
			return null;
		}

		private static IVariable findVariable(String varName, IVariable[] vars)
				throws DebugException
		{
			for (int i = 0; i < vars.length; i++)
			{
				final IVariable var = vars[i];
				if (var.getName().equals(varName))
				{
					return var;
				}
			}
			return null;
		}
	}

	private final CmlThread thread;
	private IDbgpStackLevel level;
	private final CmlStack stack;

	private boolean needRefreshVariables = false;

	public CmlStackFrame(CmlStack stack, IDbgpStackLevel stackLevel)
	{
		this.stack = stack;
		this.thread = stack.getThread();
		this.level = stackLevel;
	}

	/**
	 * @param frame
	 * @param depth
	 * @return
	 */
	public CmlStackFrame bind(IDbgpStackLevel newLevel)
	{
		if (level.isSameMethod(newLevel))
		{
			level = newLevel;
			needRefreshVariables = true;
			return this;
		}
		return new CmlStackFrame(stack, newLevel);
	}

	@Override
	public synchronized IVariable[] getVariables() throws DebugException
	{
		checkVariablesAvailable();
		return variables.toArray(getDebugTarget());
	}

	@Override
	public boolean hasVariables() throws DebugException
	{
		checkVariablesAvailable();
		return variables.hasVariables();
	}

	/**
	 * @return
	 * @deprecated use #getSourceURI()
	 */
	public URI getFileName()
	{
		return level.getFileURI();
	}

	public int getCharStart() throws DebugException
	{
		return -1;
	}

	public int getCharEnd() throws DebugException
	{
		return -1;
	}

	public int getLineNumber() throws DebugException
	{
		return level.getLineNumber();
	}

	public int getBeginLine()
	{
		return level.getBeginLine();
	}

	public int getBeginColumn()
	{
		return level.getBeginColumn();
	}

	public int getEndLine()
	{
		return level.getEndLine();
	}

	public int getEndColumn()
	{
		return level.getEndColumn();
	}

	public String getWhere()
	{
		return level.getWhere().trim();
	}

	public String getName() throws DebugException
	{
		// String name = level.getWhere().trim();
		//
		// if (name == null || name.length() == 0) {
		// name = toString();
		// }
		//
		//		name += " (" + level.getFileURI().getPath() + ")"; //$NON-NLS-1$ //$NON-NLS-2$

		return getOnlyFileName() + " line: " + getLineNumber();
	}

	public String getOnlyFileName()
	{
		URI uri = level.getFileURI();
		String res = new File(uri).getName();// .toASCIIString();

		return res;// .substring(res.lastIndexOf('/') + 1);
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException
	{
		return new IRegisterGroup[0];
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException
	{
		return false;
	}

	@Override
	public boolean canStepInto()
	{
		return this.thread.canStepInto();
	}

	@Override
	public boolean canStepOver()
	{
		return this.thread.canStepOver();
	}

	@Override
	public boolean canStepReturn()
	{
		return this.thread.canStepReturn();
	}

	@Override
	public boolean isStepping()
	{
		return this.thread.isStepping();
	}

	@Override
	public void stepInto() throws DebugException
	{
		this.thread.stepInto();
	}

	@Override
	public void stepOver() throws DebugException
	{
		this.thread.stepOver();
	}

	@Override
	public void stepReturn() throws DebugException
	{
		this.thread.stepReturn();
	}

	@Override
	public boolean canResume()
	{
		return this.thread.canResume();
	}

	@Override
	public boolean canSuspend()
	{
		return this.thread.canSuspend();
	}

	@Override
	public boolean isSuspended()
	{
		return this.thread.isSuspended();
	}

	@Override
	public void resume() throws DebugException
	{
		this.thread.resume();
	}

	@Override
	public void suspend() throws DebugException
	{
		this.thread.suspend();
	}

	@Override
	public boolean canTerminate()
	{
		return this.thread.canTerminate();
	}

	@Override
	public boolean isTerminated()
	{
		return this.thread.isTerminated();
	}

	@Override
	public void terminate() throws DebugException
	{
		this.thread.terminate();
	}

	@Override
	public IThread getThread()
	{
		return thread;
	}

	@Override
	public IDebugTarget getDebugTarget()
	{
		return getThread().getDebugTarget();
	}

	/* variable stuff */

	public void updateVariables()
	{
		this.variables = null;
		try
		{
			checkVariablesAvailable();
		} catch (DebugException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param newVars
	 * @param oldVars
	 * @return
	 * @throws DebugException
	 */
	static IVariable[] refreshVariables(IVariable[] newVars, IVariable[] oldVars)
			throws DebugException
	{
		if (oldVars != null)
		{
			final Map<String, IVariable> map = new HashMap<String, IVariable>();
			for (int i = 0; i < oldVars.length; ++i)
			{
				final IVariable variable = oldVars[i];
				if (variable instanceof IRefreshableVdmVariable)
				{
					map.put(variable.getName(), variable);
				}
			}
			for (int i = 0; i < newVars.length; ++i)
			{
				final IVariable variable = newVars[i];
				final IRefreshableVdmVariable old;
				old = (IRefreshableVdmVariable) map.get(variable.getName());
				if (old != null)
				{
					newVars[i] = old.refreshVariable(variable);
				}
			}
		}
		return newVars;
	}

	/**
	 * @throws DebugException
	 */
	private void refreshVariables() throws DebugException
	{
		final CmlVariableContainer newVars = readAllVariables();
		newVars.sort(getDebugTarget());
		variables.locals = refreshVariables(newVars.locals, variables.locals);
		// variables.globals = refreshVariables(newVars.globals, variables.globals);
		// variables.classes = refreshVariables(newVars.classes, variables.classes);
	}

	private synchronized void checkVariablesAvailable() throws DebugException
	{
		try
		{
			if (variables == null)
			{
				variables = readAllVariables();

				variables.sort(getDebugTarget());
			} else if (needRefreshVariables)
			{
				try
				{
					refreshVariables();
				} finally
				{
					needRefreshVariables = false;
				}
			}
		} catch (Exception e)
		{
			variables = new CmlVariableContainer();
			final Status status = new Status(IStatus.ERROR, VdmDebugPlugin.PLUGIN_ID, "Unable To Load Variables", e);
			VdmDebugPlugin.log(status);
			throw new DebugException(status);
		}
	}

	private CmlVariableContainer variables = null;

	protected static CmlVariable[] readVariables(CmlStackFrame parentFrame,
			int contextId)
	{

		try
		{

			IDbgpProperty[] properties = parentFrame.thread.getCommunicationManager().getContextProperties(parentFrame.thread.id, parentFrame.getLevel(), contextId);

			CmlVariable[] variables = new CmlVariable[properties.length];

			// Workaround for bug 215215
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=215215
			// Remove this code when Tcl active state debugger fixed
			Set<String> duplicates = findDuplicateNames(properties);

			for (int i = 0; i < properties.length; ++i)
			{
				IDbgpProperty property = properties[i];
				String name = property.getName();
				if (duplicates.contains(name))
				{
					name = property.getEvalName();
				}
				variables[i] = new CmlVariable(parentFrame, name, property);
			}

			return variables;
		} catch (Exception e)
		{
			if (VdmDebugPlugin.DEBUG)
			{
				e.printStackTrace();
			}
			return new CmlVariable[0];
		}
	}

	private int getLevel()
	{
		return level.getLevel();
	}

	private static Set<String> findDuplicateNames(IDbgpProperty[] properties)
	{
		final Set<String> duplicates = new HashSet<String>();
		final Set<String> alreadyExsisting = new HashSet<String>();
		for (int i = 0; i < properties.length; ++i)
		{
			final IDbgpProperty property = properties[i];
			final String name = property.getName();
			if (!alreadyExsisting.add(name))
			{
				duplicates.add(name);
			}
		}
		return duplicates;
	}

	protected CmlVariableContainer readAllVariables()
	{

		// this.thread.getThreadManager().getAllVariables(this.thread.id,getLevel())
		// final IDbgpContextCommands commands = thread.getDbgpSession().getCoreCommands();
		//
		// ((VdmThread) this.thread).getVdmDebugTarget().printLog(new LogItem(((VdmThread)
		// this.thread).getDbgpSession().getInfo(), "REQUEST", true, "getContextNames"));
		// final Map<Integer, String> names = commands.getContextNames(getLevel());
		// ((VdmThread) this.thread).getVdmDebugTarget().printLog(new LogItem(((VdmThread)
		// this.thread).getDbgpSession().getInfo(), "RESPONSE", false, "getContextNames"));
		final CmlVariableContainer result = new CmlVariableContainer();
		// if (thread.retrieveLocalVariables()
		// && names.containsKey(new Integer(IDbgpContextCommands.LOCAL_CONTEXT_ID)))
		// {
		result.locals = readVariables(this, 0);
		// ((VdmThread) this.thread).getVdmDebugTarget().printLog(new LogItem(((VdmThread)
		// this.thread).getDbgpSession().getInfo(), "RESPONSE", false, "Read local variables"));
		// }
		// if (thread.retrieveGlobalVariables()
		// && names.containsKey(new Integer(IDbgpContextCommands.GLOBAL_CONTEXT_ID)))
		// {
		// result.globals = readVariables(this, IDbgpContextCommands.GLOBAL_CONTEXT_ID, commands);
		// ((VdmThread) this.thread).getVdmDebugTarget().printLog(new LogItem(((VdmThread)
		// this.thread).getDbgpSession().getInfo(), "RESPONSE", false, "Read Global variables"));
		// }
		// if (thread.retrieveClassVariables()
		// && names.containsKey(new Integer(IDbgpContextCommands.CLASS_CONTEXT_ID)))
		// {
		// result.classes = readVariables(this, IDbgpContextCommands.CLASS_CONTEXT_ID, commands);
		// ((VdmThread) this.thread).getVdmDebugTarget().printLog(new LogItem(((VdmThread)
		// this.thread).getDbgpSession().getInfo(), "RESPONSE", false, "Read Class variables"));
		// }
		return result;
	}

}

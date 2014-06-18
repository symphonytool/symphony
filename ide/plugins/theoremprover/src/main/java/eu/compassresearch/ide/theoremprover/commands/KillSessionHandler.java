package eu.compassresearch.ide.theoremprover.commands;

import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class KillSessionHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// we know we have a session because we checked for it outside
	//	Session sess = IsabelleCore.isabelle().session().get();

		IsabelleCore.isabelle().stop();
		
//		sess.stop();
		
		return null;
	}

}

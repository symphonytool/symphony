package eu.compassresearch.ide.refinementtool.handlers;

import org.eclipse.jface.dialogs.IInputValidator;
import org.overture.ast.node.INode;

import eu.compassresearch.ide.refinementtool.RefUtils;

public class MetaVarValidator implements IInputValidator {
	
	private String kind;
	private INode mvnode = null;
	
	public MetaVarValidator(String kind) {
		super();
		this.kind = kind;
	}

	@Override
	public String isValid(String s) {
		if (kind.toLowerCase().contains("exp")) {
			mvnode = RefUtils.parsePExp(s);
			if (mvnode != null)
				return null;
			else return "Parse error: please enter a valid expression";
	    } else {
			mvnode = RefUtils.parsePAction(s);
			if (mvnode != null)
			  return null;
			else return "Parse error: please enter a valid action";				  
		}
	}

	public INode getMVnode() {
		return mvnode;
	}	
	

}

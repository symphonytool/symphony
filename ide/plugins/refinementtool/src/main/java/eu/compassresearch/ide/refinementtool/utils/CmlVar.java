package eu.compassresearch.ide.refinementtool.utils;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.PType;

public class CmlVar {
	
	private ILexNameToken name;
	private PType type;
	
	public CmlVar(ILexNameToken name, PType type) {
		super();
		this.name = name;
		this.type = type;
	}
	public ILexNameToken getName() {
		return name;
	}
	public void setName(ILexNameToken name) {
		this.name = name;
	}
	public PType getType() {
		return type;
	}
	public void setType(PType type) {
		this.type = type;
	}

}

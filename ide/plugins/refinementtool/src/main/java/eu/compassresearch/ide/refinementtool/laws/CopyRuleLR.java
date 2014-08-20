package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ACallStm;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class CopyRuleLR implements IRefineLaw {

	@Override
	public String getName() {
		return "Copy Rule from Left to Right";
	}

	@Override
	public String getDetail() {
		return "if N = B then F(N) [= F(B)";
	}	
	
	public String getName(INode a) {
		if (a instanceof AReferenceAction) {
			return ((AReferenceAction)a).getName().getName();
		} else if (a instanceof ACallStm) {
			return ((ACallStm)a).getName().getName();
		} else
			return null;
	}
	
	public ILexNameToken getName(ACallStm a) {
		return a.getName();
	}
	
	public PDefinition getActionDefinition(INode a) {
		AActionProcess process = null;
		INode aux = null;
		for (aux = a.parent(); aux != null && !(aux instanceof AActionProcess); aux = aux.parent()) {
			
		}
		if (aux instanceof AActionProcess) {
			process = (AActionProcess) aux;
			List<PDefinition> actions = ((AActionProcess) aux).getActionDefinition().getDefinitions();
			for (PDefinition p: actions) {
				String name = getName(a);
				if (p.getName().getName().equals(name)) {
					return p;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean isApplicable(INode node) {
		// TODO Auto-generated method stub
		if (node instanceof AReferenceAction) {
			PDefinition action = getActionDefinition(node);
			if (action != null && action instanceof AActionDefinition) {
				return true;
			} else {
				return false;
			}
		} else if (node instanceof ACallStm) {
			AActionDefinition action = (AActionDefinition)getActionDefinition(node);
			if (action.getDeclarations().size() == ((ACallStm)node).getArgs().size())
				return true;
			else return false;
		} else {
			return false;
		}
	}

	@Override
	public Refinement apply(Map<String, INode> metas, INode node, int offset) {
		RefinePrettyPrinter cmlpp = new RefinePrettyPrinter();
		StringBuilder output = new StringBuilder();
		if (node instanceof AReferenceAction) {
			AReferenceAction ref = (AReferenceAction) node;
			AActionDefinition def = (AActionDefinition) getActionDefinition(ref);
			PAction action = def.getAction();
			try {
				output.append("("+action.apply(cmlpp, 0)+")");
			} catch (AnalysisException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			AActionDefinition def = (AActionDefinition)getActionDefinition(node);
			PAction action = def.getAction();
			try {
				List<PParametrisation> params = def.getDeclarations();
				List<PExp> args = ((ACallStm) node).getArgs();
				if (params.size() > 0) {
					output.append("(dcl ");
					for (int i = 0; i < params.size(); ) {
						PParametrisation p = params.get(i);
						PExp e = args.get(i);
						i++;
						ALocalDefinition decl = p.getDeclaration();
						if (p instanceof AValParametrisation) {
							output.append(decl.getName()+": "+decl.getType().toString()+" := "+e.apply(cmlpp,0));
						} else if (p instanceof AVresParametrisation) {
							output.append(decl.getName()+": "+decl.getType().toString()+" := "+e.apply(cmlpp,0));
						} else {
							output.append(decl.getName()+": "+decl.getType().toString());
						}
						if (i < params.size()) output.append(", ");
					}
					
					output.append(" @ "+action.apply(cmlpp, 0));
					
					for (int i = 0; i < params.size(); ) {
						PParametrisation p = params.get(i);
						PExp e = args.get(i);
						i++;
						ALocalDefinition decl = p.getDeclaration();
						if (p instanceof AValParametrisation) {
							
						} else if (p instanceof AVresParametrisation) {
							output.append("; "+e.apply(cmlpp,0)+" := "+decl.getName());
						} else {
							output.append("; "+e.apply(cmlpp,0)+" := "+decl.getName());
						}
					}
					output.append(")");
					
					
					
					
					
					
					
					
				} else {
					output.append(action.apply(cmlpp, 0));
				}
			} catch (AnalysisException e1) {
				e1.printStackTrace();
			}
		} 
		
		

		return new Refinement(
				output.toString(), new LinkedList<CmlProofObligation>());
	}

	@Override
	public Map<String, String> getMetas() {
		return new HashMap<String, String>();
	}
	
}

package eu.compassresearch.ide.collaboration.treeview.model;
public class Share extends Model {
	
	public Share(String name) {
		super(name);
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBoardgame(this, passAlongArgument);
	}

}

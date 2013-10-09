package eu.compassresearch.ide.collaboration.treeview.model;

public class User extends Model {
	
	public User(String name) {
		super(name);
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBook(this, passAlongArgument);
	}

}

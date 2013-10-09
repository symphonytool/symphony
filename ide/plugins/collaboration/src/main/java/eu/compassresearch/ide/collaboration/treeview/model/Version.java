package eu.compassresearch.ide.collaboration.treeview.model;

public class Version extends Model {
	
	public Version(String title) {
		super(title);
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBook(this, passAlongArgument);
	}

}

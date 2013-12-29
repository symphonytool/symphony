package eu.compassresearch.ide.collaboration.datamodel;

public class Share extends Model {
	
	private static final long serialVersionUID = 1634280045311764922L;

	public Share(String name) {
		super(name);
	}
	
	public Share clone(){
		return new Share(name);
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBoardgame(this, passAlongArgument);
	}

}

package eu.compassresearch.ide.collaboration.datamodel;

public class Share extends Model {
	
	private static final long serialVersionUID = 1634280045311764922L;

	public Share(String name, Model parent) {
		super(name, parent);
	}
	
	public Share clone(){
		return new Share(name, getParent());
	}
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		//visitor.visitBoardgame(this, passAlongArgument);
	}

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

}

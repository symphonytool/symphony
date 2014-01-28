package eu.compassresearch.ide.collaboration.datamodel;

public class Visible extends Model {
	
	private static final long serialVersionUID = 1634280045311764922L;

	public Visible(String name, Model parent) {
		super(name, parent);
	}
	
	public Visible clone(){
		return new Visible(name, getParent());
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

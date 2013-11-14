package eu.compassresearch.ide.collaboration.treeview.model;

public class User extends Model {
	
	private String postfix;
	
	public User(String name) {
		super(name);
	}
	
	public User(String name, String namePostfix)
	{
		super(name);
		postfix = namePostfix;
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		
	}
	
	@Override
	public String getName()
	{
		return this.toString();
	}
	
	@Override
	public String toString()
	{
		return (postfix != null && !postfix.isEmpty() ? name + " " + postfix : name );
	}

	public String getPostfix()
	{
		return postfix;
	}

	public void setPostfix(String postfix)
	{
		this.postfix = postfix;
	}
}

package eu.compassresearch.ide.core.unsupported;

import org.overture.ast.intf.lex.ILexLocation;


/**
 * The Class UnsupportedElementInfo holds information regarding a single
 * CML element that is not supported by a particular feature or analysis of CML.
 * 
 * Since this is displayed 
 */
public class UnsupportedElementInfo
{
	
	/** The message to display regarding unsupported element. */
	private String message;
	
	/** The unsupported element's location. */
	private ILexLocation location;
	
	/** The unsupported element's name. */
	private String elementname;
	
	
	
	/**
	 * Instantiates a new unsupported element info.
	 *
	 * @param message the message to display regarding this element
	 * @param location the location of the unsupported element
	 * @param elementname the element's name
	 */
	protected UnsupportedElementInfo(String message, ILexLocation location,
			String elementname)
	{
		super();
		this.message = message;
		this.location = location;
		this.elementname = elementname;
	}
	
	
	
	
	protected UnsupportedElementInfo()
	{
		super();
	}




	/**
	 * Gets the message to display to the user.
	 *
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Sets the message to display to the user.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public ILexLocation getLocation()
	{
		return location;
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(ILexLocation location)
	{
		this.location = location;
	}
	
	/**
	 * Gets the element name.
	 *
	 * @return the element name
	 */
	public String getElementname()
	{
		return elementname;
	}
	
	/**
	 * Sets the element name.
	 *
	 * @param elementname the new element name
	 */
	public void setElementname(String elementname)
	{
		this.elementname = elementname;
	}
	
	
	

	
}

package eu.compassresearch.core.interpreter.values;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.assistant.pattern.PTypeList;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.messages.InternalException;
import org.overture.ast.types.PType;
import org.overture.ast.util.Utils;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.typechecker.util.HelpLexNameToken;

public abstract class CmlObjectValue extends CmlValue {

	private static final long serialVersionUID = 1L;

	private static int nextObjectReference = 0;

	public final int objectReference;
	public final PType type;
	protected NameValuePairMap members;
	

	/**
	 * The list holds all object values created by this object value
	 */
	public final List<CmlObjectValue> children;

	//public ClassInvariantListener invlistener = null;

	private Object delegateObject = null;

	/**
	 * The Object value who created this instance
	 */
	public CmlObjectValue creator;

	public CmlObjectValue(PType type, NameValuePairMap members, CmlObjectValue creator)
	{
		this.objectReference = getReference();
		this.members = members;
		
		this.children = new LinkedList<CmlObjectValue>();
		this.type = type;

		if(creator != null)
		{
			setCreator(creator);
		}

		setSelf(this);
	}
	
	public CmlObjectValue(PType type, CmlObjectValue creator)
	{
		this.objectReference = getReference();
		this.members = new NameValuePairMap();
		
		this.children = new LinkedList<CmlObjectValue>();
		this.type = type;

		if(creator != null)
		{
			setCreator(creator);
		}
	}

	private static int getReference()
	{
		return ++nextObjectReference;
	}

	protected void setSelf(CmlObjectValue self)
	{
		for (NameValuePair nvp: members.asList())
 		{
			Value deref = nvp.value.deref();

 			if (deref instanceof CmlOperationValue)
 			{
 				CmlOperationValue ov = (CmlOperationValue)deref;
 				ov.setSelf(self);
 			}
 			else if (deref instanceof FunctionValue)
 			{
 				FunctionValue fv = (FunctionValue)deref;
 				//TODO: hmmm... why do functions need to know about self?
 				//fv.setSelf(self);
 			}
 			else if(deref instanceof CmlObjectValue)
			{
				((CmlObjectValue)deref).setCreator(self);
			}
 		}
	}

	@Override
	public ObjectValue objectValue(Context ctxt)
	{
		//TODO: convert into VDM ObjectValue if possible
		return null;
	}
	
	public Value get(LexNameToken field, boolean explicit)
	{
		//FIXME figure out what to do here
		LexNameToken localname = null;
		//	explicit ? field : field.getModifiedName(type.getName().name);

		// This is another case where we have to iterate with equals()
		// rather than using the map's hash, because the hash doesn't
		// take account of the TypeComparator looseness when comparing
		// qualified names. Not very efficient... so we try a raw get
		// first.

		Value rv = members.get(localname);

		if (rv == null)
		{
    		for (LexNameToken var: members.keySet())
    		{
    			if (HelpLexNameToken.isEqual( var,localname))
    			{
    				rv = members.get(var);
    				break;
    			}
    		}
		}

		return rv;
	}
	
	public ValueList getOverloads(LexNameToken field)
	{
		ValueList list = new ValueList();

		// This is another case where we have to iterate with matches()
		// rather than using the map's hash, because the hash includes the
		// overloaded type qualifiers...

		for (LexNameToken var: members.keySet())
		{
			if (var.matches(field))		// Ignore type qualifiers
			{
				list.add(members.get(var));
			}
		}

		return list;
	}
	
	public NameValuePairMap getMemberValues()
	{
		NameValuePairMap nvpm = new NameValuePairMap();
		nvpm.putAll(members);
		return nvpm;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Value)
		{
			Value val = ((Value)other).deref();

    		if (val instanceof ObjectValue)
    		{
    			return val == this;		// Direct object comparison?
    		}
		}

		return false;
	}
	
	protected boolean inToString = false;
	
	@Override
	public String toString()
	{
		if (inToString)
		{
			return "{#" + objectReference + " recursive}";
		}

		inToString = true;
		StringBuilder sb = new StringBuilder();
		sb.append(type.toString());
		sb.append("{#" + objectReference);

		for (LexNameToken name: members.keySet())
		{
			Value ov = members.get(name);
			Value v = ov.deref();

			if (!(v instanceof FunctionValue) &&
				!(v instanceof OperationValue))
			{
				sb.append(", ");
				sb.append(name.name);

				if (ov instanceof UpdatableValue)
				{
					sb.append(":=");
				}
				else
				{
					sb.append("=");
				}

				sb.append(v.toString());
			}
		}

		sb.append("}");
		inToString = false;
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		// return type.hashCode() + objectReference + superobjects.hashCode();
		return objectReference;
	}

	@Override
	public String kind()
	{
		return type.toString();
	}
	
	/**
	 * Sets the creator of this object value and adds this to
	 * the newCreator parsed as argument
	 * @param newCreator The creator of this object value
	 */
	private void setCreator(CmlObjectValue newCreator)
	{
		//establish transitive reference
		newCreator.addChild(this);
	}
	
	/**
	 * Removed the creator of this object value by detaching from the
	 * creator's child list
	 */
	public void removeCreator()
	{
		// if we are moving to a new CPU, we are no longer a part of the transitive
		// references from our creator, so let us remove ourself. This will prevent
		// us from being updated if our creator is migrating in the
		// future.
		if(this.creator != null)
		{
			this.creator.removeChild(this);
			//creator no longer needed, as we already detached ourself.
			this.creator = null;
		}
	}
	
	@Override
	public Value convertValueTo(PType to, Context ctxt) throws ValueException
	{
//		Value conv = convertToHierarchy(to);
//
//		if (conv != null)
//		{
//			return conv;
//		}
//
//		// This will fail...
//		return super.convertValueTo(to, ctxt);
		
		return null;
	}
	
	@Override
	public Object clone()
	{
		return deepCopy();
	}

	@Override
	public CmlObjectValue deepCopy()
	{
		try
		{
			// This is slow, but it has the advantage that Value copies,
			// such as the parent and subclass copies of the same
			// variable, are preserved as the same variable rather than
			// being split, as they are in naive object copies.

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(this);
			oos.close();

			ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(is);
			CmlObjectValue result = (CmlObjectValue)ois.readObject();

			result.setSelf(result);
			return result;
		}
		catch (Exception e)
		{
			throw new InternalException(5, "Illegal clone: " + e);
		}
	}
	
	/**
	 * Add a child created by this object value
	 * @param referenced
	 */
	private void addChild(CmlObjectValue referenced)
	{
		children.add(referenced);
	}

	/**
	 * Remove a child from this object value. After this the reference will no longer
	 * be considered as created by this object value
	 * @param reference
	 */
	private void removeChild(CmlObjectValue reference)
	{
		children.remove(reference);
	}
}

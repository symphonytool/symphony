package eu.compassresearch.core.interpreter.runtime;

import java.util.HashMap;
import java.util.Map;

import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.lex.LexLocation;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.typechecker.Environment;
import eu.compassresearch.transformation.CmlAstToOvertureAst;

public class CMLContext {
	
    /**
     * The enclosing Context to search if a symbol is not found in this
     */
	private CMLContext outer = null;
	private Map<LexNameToken,Value> map;
	/** The location of the context. */
	public final LexLocation location;
	/** The name of the location. */
	//public final String title;
	/** The thread state associated with this context. */
	//public ThreadState threadState = null;
	
	
	public CMLContext(LexLocation location)
	{
		map = new HashMap<LexNameToken,Value>();
		this.location = location;
	}
	
	public CMLContext(LexLocation location, CMLContext outer)
	{
		this.outer = outer;
		this.location = location;
		map = new HashMap<LexNameToken,Value>();
	}
							
	public void put(LexNameToken name, Value value)
	{
		map.put(name, value);
	}
	
	public Value lookupName(LexNameToken name)
	{
		Value v = map.get(name);
        if (v == null)
          if (outer != null)
            v = outer.lookupName(name);
        return v;
	}
	
	public Value get(Object name)
	{
		Value rv = map.get(name);

		if (rv == null)
		{
			for (LexNameToken var: map.keySet())
			{
				if (isEqual(var, name))
				{
					rv = map.get(var);
					break;
				}
			}
		}

		return rv;
	}
	
	public CMLContext locate(LexNameToken name)
	{
		Value v = get(name);

		if (v == null)
		{
			if (outer != null)
			{
				return outer.locate(name);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return this;
		}
	}
	
//	private Value lookupFromOvtName(org.overture.ast.lex.LexNameToken name)
//    {
//      LexLocation location = Environment.translateLexLocation(name.location);
//      LexNameToken cmlName = new LexNameToken(name.module,name.name,location,name.old,name.explicit);
//      Value value = lookupName(cmlName);
//      
//      return value;
//    }
	
	
	public static boolean isEqual(LexNameToken one, Object other)
	{
		if (!(other instanceof org.overture.ast.lex.LexNameToken))
		{
			return false;
		}

		LexNameToken lother = (LexNameToken)other;
		
//		if (one.typeQualifier != null && lother.getTypeQualifier() != null)
//		{
//			if (!TypeComparator.compatible(one.typeQualifier, lother.typeQualifier))
//			{
//				return false;
//			}
//		}	
//		else if ((one.typeQualifier != null && lother.getTypeQualifier() == null) ||
//				 (one.typeQualifier == null && lother.getTypeQualifier() != null))
//		{
//			return false;
//		}

		return one.matches(lother);
		
	}
	
	public static boolean isEqual(Object one, Object other)
	{
		
		if(one instanceof LexNameToken)
		{
			return isEqual((LexNameToken)one, other);
		}
		return false;
		
	}
	
	
	public static boolean isEqualOvt(LexNameToken one, Object other)
	{
		if (!(other instanceof org.overture.ast.lex.LexNameToken))
		{
			return false;
		}

		org.overture.ast.lex.LexNameToken lother =  (org.overture.ast.lex.LexNameToken)other;
		LexLocation location = Environment.translateLexLocation(lother.location);
	    LexNameToken cmlOther = new LexNameToken(lother.module,lother.name,location,lother.old,lother.explicit);
		

//		if (one.typeQualifier != null && lother.getTypeQualifier() != null)
//		{
//			if (!TypeComparator.compatible(one.typeQualifier, lother.typeQualifier))
//			{
//				return false;
//			}
//		}	
//		else if ((one.typeQualifier != null && lother.getTypeQualifier() == null) ||
//				 (one.typeQualifier == null && lother.getTypeQualifier() != null))
//		{
//			return false;
//		}

		return one.matches(cmlOther);
		
	}
	
	public static boolean isEqualOvt(Object one, Object other)
	{
		
		if(one instanceof LexNameToken)
		{
			return isEqualOvt((LexNameToken)one, other);
		}
		return false;
		
	}
	
	public org.overture.interpreter.runtime.Context getOvertureContext()
	{
		@SuppressWarnings("serial")
		class CmlOvertureContext extends Context 
		{

			private CmlOvertureContext(org.overture.ast.lex.LexLocation location, org.overture.interpreter.runtime.Context context)
			{
				super(location,"CMLWrapperContext", context);
			}

			@Override
			public Value get(Object name)
			{
				Value rv = map.get(name);

				if (rv == null)
				{
					for (LexNameToken var: map.keySet())
					{
						if (isEqualOvt(var, name))
						{
							rv = map.get(var);
							break;
						}
					}
				}

				return rv;
			}

		}
		;
		CmlAstToOvertureAst transform = new CmlAstToOvertureAst();

		org.overture.ast.lex.LexLocation overtureLoc = null;
      
      try {
		overtureLoc = (org.overture.ast.lex.LexLocation)transform.caseLexLocation(this.location);
	} catch (AnalysisException e) {

		e.printStackTrace();
	}
            
      return new CmlOvertureContext(overtureLoc,
    		  						this.outer != null ? this.outer.getOvertureContext() : null);
    }

}

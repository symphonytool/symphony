package eu.compassresearch.ast.lex;

import java.util.List;

import org.overture.ast.assistant.type.PTypeAssistant;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.PType;

//package eu.compassresearch.ast.lex;
//
//import java.io.Serializable;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.analysis.intf.IAnalysis;
//import org.overture.ast.analysis.intf.IAnswer;
//import org.overture.ast.analysis.intf.IQuestion;
//import org.overture.ast.analysis.intf.IQuestionAnswer;
//import org.overture.ast.assistant.type.PTypeAssistant;
//import org.overture.ast.intf.lex.ILexIdentifierToken;
//import org.overture.ast.intf.lex.ILexLocation;
//import org.overture.ast.intf.lex.ILexNameToken;
//import org.overture.ast.lex.LexLocation;
//import org.overture.ast.lex.VDMToken;
//import org.overture.ast.messages.InternalException;
//import org.overture.ast.types.PType;
//import org.overture.ast.util.Utils;
//
public class LexNameToken extends org.overture.ast.lex.LexNameToken// implements ILexNameToken,
// Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7741582702419450840L;

	// private static final long serialVersionUID = 1L;
	//
	// public final String module;
	// public final String name;
	// public final boolean old;
	// public final boolean explicit; // Name has an explicit
	// // module/class
	//
	// public List<PType> typeQualifier = null;
	//
	// private int hashcode = 0;
	//
	public LexNameToken(String module, String name, ILexLocation location,
			boolean old, boolean explicit)
	{
		super(module, name, location, old, explicit);
	}

	public LexNameToken(String module, String name, ILexLocation location)
	{
		super(module, name, location);
	}

	public LexNameToken(String module, ILexIdentifierToken id)
	{
		super(module, id);
	}

	public void setTypeQualifier(List<PType> types)
	{
		// if (hashcode != 0)
		{
			// if ((typeQualifier == null && types != null)
			// || (typeQualifier != null && !typeQualifier.equals(types)))
			// {
			// throw new InternalException(2, "Cannot change type qualifier: "
			// + this + " to " + types);
			// }
		}

		typeQualifier = types;
	}

	@Override
	public int hashCode()
	{
		// if (hashcode == 0)
		// {
		int hashcode = module.hashCode()
				+ name.hashCode()
				+ (old ? 1 : 0)
				+ (typeQualifier == null ? 0
						: PTypeAssistant.hashCode(typeQualifier));
		// }

		return hashcode;
	}
	//
	// public LexNameToken getPerName(ILexLocation loc)
	// {
	// return new LexNameToken(module, "per_" + name, loc);
	// }
	//
	// public LexNameToken getPreName(ILexLocation l)
	// {
	// return new LexNameToken(module, "pre_" + name, l);
	// }
	//
	// public LexNameToken getPostName(ILexLocation l)
	// {
	// return new LexNameToken(module, "post_" + name, l);
	// }
	//
	// public LexNameToken getInvName(ILexLocation l)
	// {
	// return new LexNameToken(module, "inv_" + name, l);
	// }
	//
	// public LexNameToken getInitName(ILexLocation l)
	// {
	// return new LexNameToken(module, "init_" + name, l);
	// }
	//
	// public LexIdentifierToken getIdentifier()
	// {
	// return new LexIdentifierToken(name, old, location);
	// }
	//
	// public LexNameToken getExplicit(boolean ex)
	// {
	// return new LexNameToken(module, name, location, old, ex);
	// }
	//
	// public LexNameToken getOldName()
	// {
	// return new LexNameToken(module, new LexIdentifierToken(name, true, location));
	// }
	//
	// public String getFullName()
	// {
	// // Flat specifications have blank module names
	// return (explicit ? (module.length() > 0 ? module + "`" : "") : "")
	// + name + (old ? "~" : ""); // NB. No qualifier
	// }
	//
	// public String getName()
	// {
	// return name;
	// }
	//
	// public LexNameToken getNewName()
	// {
	// return new LexNameToken(module, new LexIdentifierToken(name, false, location));
	// }
	//
	// public String getSimpleName()
	// {
	// return name;
	// }
	//
	// public LexNameToken getPreName(LexLocation l)
	// {
	// return new LexNameToken(module, "pre_" + name, l);
	// }
	//
	// public LexNameToken getPostName(LexLocation l)
	// {
	// return new LexNameToken(module, "post_" + name, l);
	// }
	//
	// public LexNameToken getInvName(LexLocation l)
	// {
	// return new LexNameToken(module, "inv_" + name, l);
	// }
	//
	// public LexNameToken getInitName(LexLocation l)
	// {
	// return new LexNameToken(module, "init_" + name, l);
	// }
	//
	// public LexNameToken getModifiedName(String classname)
	// {
	// LexNameToken mod = new LexNameToken(classname, name, location);
	// mod.setTypeQualifier(typeQualifier);
	// return mod;
	// }
	//
	// public LexNameToken getSelfName()
	// {
	// if (module.equals("CLASS"))
	// {
	// return new LexNameToken(name, "self", location);
	// } else
	// {
	// return new LexNameToken(module, "self", location);
	// }
	// }
	//
	// public LexNameToken getThreadName()
	// {
	// if (module.equals("CLASS"))
	// {
	// return new LexNameToken(name, "thread", location);
	// } else
	// {
	// return new LexNameToken(module, "thread", location);
	// }
	// }
	//
	// public LexNameToken getPerName(LexLocation loc)
	// {
	// return new LexNameToken(module, "per_" + name, loc);
	// }
	//
	// public LexNameToken getClassName()
	// {
	// return new LexNameToken("CLASS", name, location);
	// }
	//
	// public void setTypeQualifier(List<PType> types)
	// {
	// // FIXME: Type check of Variable fails if this code it here. See TCExpression.caseAVariableExp at
	// // name.setTypeQualifier(question.qualifiers);
	// // if (hashcode != 0)
	// // {
	// // if ((typeQualifier == null && types != null)
	// // || (typeQualifier != null && !typeQualifier.equals(types)))
	// // {
	// // throw new InternalException(2, "Cannot change type qualifier: "
	// // + this + " to " + types);
	// // }
	// // }
	//
	// typeQualifier = types;
	// }
	//
	// @Override
	// public boolean equals(Object other)
	// {
	// if (!(other instanceof LexNameToken))
	// {
	// return false;
	// }
	//
	// LexNameToken lother = (LexNameToken) other;
	//
	// if (typeQualifier != null && lother.getTypeQualifier() != null)
	// {
	// ClassLoader cls = ClassLoader.getSystemClassLoader();
	// try
	// {
	// @SuppressWarnings("rawtypes")
	// Class helpLexNameTokenClass = cls.loadClass("org.overture.typechecker.util.HelpLexNameToken");
	// Object helpLexNameTokenObject = helpLexNameTokenClass.newInstance();
	// @SuppressWarnings("unchecked")
	// Method isEqualMethod = helpLexNameTokenClass.getMethod("isEqual", ILexNameToken.class, Object.class);
	// Object result = isEqualMethod.invoke(helpLexNameTokenObject, this, other);
	// return (Boolean) result;
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// throw new InternalException(-1, "Use HelpLexNameToken.isEqual to compare");
	// // if (!TypeComparator.compatible(typeQualifier,
	// // lother.getTypeQualifier()))
	// // {
	// // return false;
	// // }
	// } else if ((typeQualifier != null && lother.getTypeQualifier() == null)
	// || (typeQualifier == null && lother.getTypeQualifier() != null))
	// {
	// return false;
	// }
	//
	// return matches(lother);
	// }
	//
	// // FIXME What is this method here for
	// // public boolean matches(LexNameToken other)
	// // {
	// // return module.equals(other.module) && name.equals(other.name)
	// // && old == other.old;
	// // }
	//
	// @Override
	// public int hashCode()
	// {
	// if (hashcode == 0)
	// {
	// hashcode = module.hashCode()
	// + name.hashCode()
	// + (old ? 1 : 0)
	// + (typeQualifier == null ? 0
	// : PTypeAssistant.hashCode(typeQualifier));
	// }
	//
	// return hashcode;
	// }
	//
	// @Override
	// public String toString()
	// {
	// return getName()
	// + (typeQualifier == null ? "" : "("
	// + Utils.listToString(typeQualifier) + ")");
	// }
	//
	// public LexNameToken copy()
	// {
	// LexNameToken c = new LexNameToken(module, name, location, old, explicit);
	// c.setTypeQualifier(typeQualifier);
	// return c;
	// }
	//
	// public int compareTo(LexNameToken o)
	// {
	// return toString().compareTo(o.toString());
	// }
	//
	// public String getModule()
	// {
	// return module;
	// }
	//
	// @Override
	// public LexNameToken clone()
	// {
	// return copy();
	// }
	//
	// public List<PType> getTypeQualifier()
	// {
	// return typeQualifier;
	// }
	//
	// public boolean isOld()
	// {
	// return old;
	// }
	//
	// @Override
	// public void apply(IAnalysis analysis) throws AnalysisException
	// {
	// analysis.caseILexNameToken(this);
	// }
	//
	// @Override
	// public <A> A apply(IAnswer<A> caller) throws AnalysisException
	// {
	// return caller.caseILexNameToken(this);
	// }
	//
	// @Override
	// public <Q> void apply(IQuestion<Q> caller, Q question)
	// throws AnalysisException
	// {
	// caller.caseILexNameToken(this, question);
	// }
	//
	// @Override
	// public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
	// throws AnalysisException
	// {
	// return caller.caseILexNameToken(this, question);
	// }
	//
	// /**
	// * Creates a map of all field names and their value
	// *
	// * @param includeInheritedFields
	// * if true all inherited fields are included
	// * @return a a map of names to values of all fields
	// */
	// @Override
	// public Map<String, Object> getChildren(Boolean includeInheritedFields)
	// {
	// Map<String, Object> fields = new HashMap<String, Object>();
	// if (includeInheritedFields)
	// {
	// fields.putAll(super.getChildren(includeInheritedFields));
	// }
	// fields.put("module", this.module);
	// fields.put("name", this.name);
	// fields.put("old", this.old);
	// fields.put("explicit", this.explicit);
	// return fields;
	// }
	//
	// public int compareTo(ILexNameToken o)
	// {
	// return toString().compareTo(o.toString());
	// }
	//
	// @Override
	// public boolean getExplicit()
	// {
	// return explicit;
	// }
	//
	// @Override
	// public ILexNameToken getThreadName(ILexLocation loc)
	// {
	// // FIXME Don't think this is actually used
	// throw new UnsupportedOperationException("Not yet implemented.");
	// }
	//
	// @Override
	// public List<PType> typeQualifier()
	// {
	// return typeQualifier;
	// }
	//
	// @Override
	// public boolean matches(ILexNameToken other)
	// {
	// // return this.name.equals(other.getName());
	// return module.equals(other.getModule()) && name.equals(other.getName())
	// && old == other.getOld();
	// }
	//
}

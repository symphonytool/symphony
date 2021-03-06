//package eu.compassresearch.ast.lex;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.analysis.intf.IAnalysis;
//import org.overture.ast.analysis.intf.IAnswer;
//import org.overture.ast.analysis.intf.IQuestion;
//import org.overture.ast.analysis.intf.IQuestionAnswer;
//import org.overture.ast.intf.lex.ILexIdentifierToken;
//import org.overture.ast.intf.lex.ILexLocation;
//import org.overture.ast.intf.lex.ILexNameToken;
//import org.overture.ast.lex.LexLocation;
//import org.overture.ast.lex.VDMToken;
//
//public class LexIdentifierToken extends LexToken implements ILexIdentifierToken
//  {
//    private static final long serialVersionUID = 1L;
//    public final String       name;
//    public final boolean      old;
//    
//    public LexIdentifierToken(String name, boolean old, ILexLocation location,
//        VDMToken token)
//      {
//        super(location, token);
//        this.name = name;
//        this.old = old;
//      }
//    
//    public LexIdentifierToken(String name, boolean old, ILexLocation location)
//      {
//        this(name, old, location, VDMToken.IDENTIFIER);
//      }
//    
//    public ILexNameToken getClassName()
//      {
//        // We don't know the class name of the name of a class until we've
//        // read the name. So create a new location with the right module.
//        
//        LexLocation loc = new LexLocation(location.getFile(), name,
//            location.getStartLine(), location.getStartPos(), location.getEndLine(),
//            location.getEndPos(), location.getStartOffset(), location.getEndOffset());
//        
//        return new LexNameToken("CLASS", name, loc);
//      }
//    
//    @Override
//    public boolean equals(Object other)
//      {
//        if (other instanceof LexIdentifierToken)
//          {
//            LexIdentifierToken tother = (LexIdentifierToken) other;
//            return this.name.equals(tother.getName())
//                && this.old == tother.isOld();
//          }
//        
//        return false;
//      }
//    
//    @Override
//    public int hashCode()
//      {
//        return name.hashCode() + (old ? 1 : 0);
//      }
//    
//    @Override
//    public String toString()
//      {
//        return name + (old ? "~" : "");
//      }
//    
//    public boolean isOld()
//      {
//        return old;
//      }
//    
//    public String getName()
//      {
//        return name;
//      }
//    
//    public ILexLocation getLocation()
//      {
//        return location;
//      }
//    
//    @Override
//    public ILexIdentifierToken clone()
//      {
//        return new LexIdentifierToken(name, old, location);
//      }
//    
//    @Override
//    public void apply(IAnalysis analysis) throws AnalysisException
//      {
//        analysis.caseILexIdentifierToken(this);
//      }
//    
//    @Override
//    public <A> A apply(IAnswer<A> caller) throws AnalysisException
//      {
//        return caller.caseILexIdentifierToken(this);
//      }
//    
//    @Override
//    public <Q> void apply(IQuestion<Q> caller, Q question)
//        throws AnalysisException
//      {
//        caller.caseILexIdentifierToken(this, question);
//      }
//    
//    @Override
//    public <Q, A> A apply(IQuestionAnswer<Q, A> caller, Q question)
//        throws AnalysisException
//      {
//        return caller.caseILexIdentifierToken(this, question);
//      }
//    
//    /**
//     * Creates a map of all field names and their value
//     * 
//     * @param includeInheritedFields
//     *          if true all inherited fields are included
//     * @return a a map of names to values of all fields
//     */
//    @Override
//    public Map<String, Object> getChildren(Boolean includeInheritedFields)
//      {
//        Map<String, Object> fields = new HashMap<String, Object>();
//        if (includeInheritedFields)
//          {
//            fields.putAll(super.getChildren(includeInheritedFields));
//          }
//        fields.put("name", this.name);
//        fields.put("old", this.old);
//        return fields;
//      }
//
//	@Override
//	public boolean getOld()
//	{
//		return old;
//	}
//  }

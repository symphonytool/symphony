package org.overture.transforms;

import org.overture.ast.analysis.QuestionAdaptor;
import org.overture.ast.declarations.AChannelDeclaration;
import org.overture.ast.declarations.AChannelNameDeclaration;
import org.overture.ast.declarations.AChansetDeclaration;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.AProcessDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.ATypeGlobalDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AChannelDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.program.AProgramProgram;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.program.PProgram;
import org.overture.ast.program.PSourcefile;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.PAccessSpecifier;
import org.overturetool.util.ClonableFile;
import org.overturetool.util.ClonableString;
import org.overturetool.vdmj.lex.LexBooleanToken;
import org.overturetool.vdmj.lex.LexCharacterToken;
import org.overturetool.vdmj.lex.LexIdentifierToken;
import org.overturetool.vdmj.lex.LexIntegerToken;
import org.overturetool.vdmj.lex.LexLocation;
import org.overturetool.vdmj.lex.LexNameToken;
import org.overturetool.vdmj.lex.LexQuoteToken;
import org.overturetool.vdmj.lex.LexRealToken;
import org.overturetool.vdmj.lex.LexStringToken;
import org.overturetool.vdmj.lex.LexToken;
import org.overturetool.vdmj.typechecker.ClassDefinitionSettings;
import org.overturetool.vdmj.typechecker.NameScope;

public class DotGraphVisitor extends QuestionAdaptor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 637147601437624885L;
	
	private StringBuilder resultString;
	private int nodeCount = 0;

	private String makeNewNodeName()
	{
		return "n" + nodeCount++;
		
	}
	
	public DotGraphVisitor()
	{
		resultString = new StringBuilder();
		resultString.append("\tnode [shape=record];\n");
	}
	
	public String getResultString()
	{
		return "\ndigraph ast\n{\n" + resultString.toString() + "\n}";
		
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}


	@Override
	public void caseLexToken(LexToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexToken(node, question);
	}


	@Override
	public void caseLexNameToken(LexNameToken node, String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>LexNameToken|<f1>Name:"+node.getName()+"}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
	}


	@Override
	public void caseLexIdentifierToken(LexIdentifierToken node, String question) {
				
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>LexIdentifierToken|<f1>Name:"+node.getName()+"}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
	}


	@Override
	public void caseLexBooleanToken(LexBooleanToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexBooleanToken(node, question);
	}


	@Override
	public void caseLexCharacterToken(LexCharacterToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexCharacterToken(node, question);
	}


	@Override
	public void caseLexIntegerToken(LexIntegerToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexIntegerToken(node, question);
	}


	@Override
	public void caseLexQuoteToken(LexQuoteToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexQuoteToken(node, question);
	}


	@Override
	public void caseLexRealToken(LexRealToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexRealToken(node, question);
	}


	@Override
	public void caseLexStringToken(LexStringToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexStringToken(node, question);
	}


	@Override
	public void caseClonableFile(ClonableFile node, String question) {
		// TODO Auto-generated method stub
		super.caseClonableFile(node, question);
	}


	@Override
	public void caseClonableString(ClonableString node, String question) {
		// TODO Auto-generated method stub
		super.caseClonableString(node, question);
	}


	@Override
	public void caseClassDefinitionSettings(ClassDefinitionSettings node,
			String question) {
		// TODO Auto-generated method stub
		super.caseClassDefinitionSettings(node, question);
	}


	@Override
	public void caseLexLocation(LexLocation node, String question) {
		// TODO Auto-generated method stub
		super.caseLexLocation(node, question);
	}


	@Override
	public void caseNameScope(NameScope node, String question) {
		// TODO Auto-generated method stub
		super.caseNameScope(node, question);
	}


	@Override
	public void caseBoolean(Boolean node, String question) {
		// TODO Auto-generated method stub
		super.caseBoolean(node, question);
	}


	@Override
	public void caseInteger(Integer node, String question) {
		// TODO Auto-generated method stub
		super.caseInteger(node, question);
	}


	@Override
	public void caseString(String node, String question) {
		// TODO Auto-generated method stub
		super.caseString(node, question);
	}


	@Override
	public void caseLong(Long node, String question) {
		// TODO Auto-generated method stub
		super.caseLong(node, question);
	}


	@Override
	public void caseTStatic(TStatic node, String question) {
		// TODO Auto-generated method stub
		super.caseTStatic(node, question);
	}


	@Override
	public void caseTAsync(TAsync node, String question) {
		// TODO Auto-generated method stub
		super.caseTAsync(node, question);
	}


	@Override
	public void defaultPProgram(PProgram node, String question) {
		// TODO Auto-generated method stub
		super.defaultPProgram(node, question);
	}


	@Override
	public void caseAProgramProgram(AProgramProgram node, String question) {
		// TODO Auto-generated method stub
		super.caseAProgramProgram(node, question);
	}


	@Override
	public void defaultPSourcefile(PSourcefile node, String question) {
		// TODO Auto-generated method stub
		super.defaultPSourcefile(node, question);
	}


	@Override
	public void caseASourcefileSourcefile(ASourcefileSourcefile node,
			String question) {
		
		String nodeName = makeNewNodeName();
		
		String line = nodeName  + " [label=\"{<f0>ASourcefileSourcefile|<f1>Name:"+node.getName()+"}\"];\n";
		
		resultString.append("\t"+line);
		
		for(PDeclaration decl : node.getDecls())
		{
			decl.apply(this,nodeName);
		}
	
	}


	@Override
	public void defaultPDeclaration(PDeclaration node, String question) {
		// TODO Auto-generated method stub
		super.defaultPDeclaration(node, question);
	}


	@Override
	public void caseAClassDeclaration(AClassDeclaration node, String question) {
		// TODO Auto-generated method stub
		super.caseAClassDeclaration(node, question);
	}


	@Override
	public void caseAProcessDeclaration(AProcessDeclaration node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAProcessDeclaration(node, question);
	}


	@Override
	public void caseAChannelDeclaration(AChannelDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelDeclaration|<f1>Name:channels}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		node.getChannelDefinition().apply(this,nodeName);
	}


	@Override
	public void caseAChannelNameDeclaration(AChannelNameDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelNameDeclaration}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		node.getDeclaration().apply(this, nodeName);
		
	}


	@Override
	public void caseAChansetDeclaration(AChansetDeclaration node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAChansetDeclaration(node, question);
	}

	@Override
	public void caseASingleTypeDeclaration(ASingleTypeDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		
		String typeName = "null";
		
		if (node.getType() != null)
			typeName = node.getType().toString(); 
		
		resultString.append("\t" + nodeName + " [label=\"{<f0>ASingleTypeDeclaration |<f1>Type: "+
				typeName +" }\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		for(LexIdentifierToken id : node.getIdentifiers())
		{
			id.apply(this, nodeName);
		}
		
	}


	

	@Override
	public void caseAChannelDefinition(AChannelDefinition node, String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelDefinition}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		for(AChannelNameDeclaration cnd : node.getChannelNameDecls())
		{
			cnd.apply(this,nodeName);
		}
	}

	@Override
	public void caseATypeGlobalDeclaration(ATypeGlobalDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>ATypeGlobalDeclaration}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		for(ATypeDefinition tdecl : node.getTypeDefinitions())
		{
			tdecl.apply(this,nodeName);
		}
	}

	@Override
	public void caseATypeDefinition(ATypeDefinition node, String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>ATypeDefinition}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		node.getAccess().apply(this,nodeName);
		node.getName().apply(this, nodeName);
		node.getType().apply(this,nodeName);
	}

	@Override
	public void caseACharBasicType(ACharBasicType node, String question) {
		// TODO Auto-generated method stub
		super.caseACharBasicType(node, question);
	}

	@Override
	public void caseARationalNumericBasicType(ARationalNumericBasicType node,
			String question) {
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>ARationalNumericBasicType}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
	}

	@Override
	public void defaultPAccessSpecifier(PAccessSpecifier node, String question) {

		

		
	}
	
	
	
	
		
}

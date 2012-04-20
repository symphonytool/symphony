package org.overture.transforms;

import org.overture.ast.analysis.QuestionAdaptor;
import org.overture.ast.declarations.AChannelDeclaration;
import org.overture.ast.declarations.AChannelNameDeclaration;
import org.overture.ast.declarations.AChansetDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.ATypeGlobalDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AChannelDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.SBasicType;
import org.overturetool.vdmj.lex.LexIdentifierToken;
import org.overturetool.vdmj.lex.LexNameToken;

public class DotGraphVisitor extends QuestionAdaptor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 637147601437624885L;
	
	private StringBuilder resultString;
	private int nodeCount = 0;

	public DotGraphVisitor()
	{
		resultString = new StringBuilder();
		resultString.append("\tnode [shape=record];\n");
	}
	
	public String getResultString()
	{
		return "\ndigraph ast\n{\n" + resultString.toString() + "\n}";
		
	}
	
	private String makeNewNodeName()
	{
		return "n" + nodeCount++;
		
	}
		
	private String createDefaultNode(String parentName, String nodeLabel)
	{
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>"+nodeLabel+"}\"];\n");
		resultString.append("\t" + parentName + " -> " + nodeName +"\n");
		
		return nodeName;
	}
	
	private String createDefaultNode(String parentName, String nodeLabel, String[] fields)
	{
		String nodeName = makeNewNodeName();
		
		String tmp = " [label=\"{<f0>"+nodeLabel;
		
		for(String s : fields)
		{
			tmp += " | " + s;
		}
		
		resultString.append("\t" + nodeName + tmp +"}\"];\n");
		resultString.append("\t" + parentName + " -> " + nodeName +"\n");
		
		return nodeName;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}
	
	@Override
	public void defaultINode(INode node, String question) {
		
		createDefaultNode(question,node.getClass().getSimpleName());
	}
	
	@Override
	public void defaultSBasicType(SBasicType node, String question) {
		
		createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Location:" + node.getLocation().toShortString()});
				
	}

	@Override
	public void caseLexNameToken(LexNameToken node, String question) {
		
		createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Name:" + node.getName() ,"<f2>Location:" + node.getLocation().toShortString()});
		
	}

	@Override
	public void caseLexIdentifierToken(LexIdentifierToken node, String question) {
				
		createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Name:" + node.getName() ,"<f2>Location:" + node.getLocation().toShortString()});
	}

	@Override
	public void caseASourcefileSourcefile(ASourcefileSourcefile node,
			String question) {
		
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Name:" + node.getName() });
				
		for(PDeclaration decl : node.getDecls())
		{
			decl.apply(this,nodeName);
		}
	
	}

	@Override
	public void caseAChannelDeclaration(AChannelDeclaration node,
			String question) {
				
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Name:channels","<f2>Location:" + node.getLocation().toShortString()});
				
		node.getChannelDefinition().apply(this,nodeName);
	}


	@Override
	public void caseAChannelNameDeclaration(AChannelNameDeclaration node,
			String question) {
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
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
						
		String typeName = "null";
		
		if (node.getType() != null)
			typeName = node.getType().toString(); 
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Type:" + typeName ,"<f2>Location:" + node.getLocation().toShortString()});
						
		for(LexIdentifierToken id : node.getIdentifiers())
		{
			id.apply(this, nodeName);
		}
		
	}

	@Override
	public void caseAChannelDefinition(AChannelDefinition node, String question) {
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
						
		for(AChannelNameDeclaration cnd : node.getChannelNameDecls())
		{
			cnd.apply(this,nodeName);
		}
	}

	@Override
	public void caseATypeGlobalDeclaration(ATypeGlobalDeclaration node,
			String question) {
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
		
		for(ATypeDefinition tdecl : node.getTypeDefinitions())
		{
			tdecl.apply(this,nodeName);
		}
	}

	@Override
	public void caseATypeDefinition(ATypeDefinition node, String question) {
		
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
		
		node.getAccess().apply(this,nodeName);
		node.getName().apply(this, nodeName);
		node.getType().apply(this,nodeName);
	}
		
	@Override
	public void caseAAccessSpecifierAccessSpecifier(
			AAccessSpecifierAccessSpecifier node, String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AAccessSpecifierAccessSpecifier|<f1>Access:"+ node.getAccess().toString() +"}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
	}
	
}

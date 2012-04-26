package org.overture.transforms;

import java.lang.reflect.Field;
import java.util.*;

import org.overture.ast.analysis.QuestionAdaptor;
import org.overture.ast.declarations.AChannelDeclaration;
import org.overture.ast.declarations.AChannelNameDeclaration;
import org.overture.ast.declarations.AChansetDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AChannelDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.expressions.AIntLiteralSymbolicLiteralExp;
import org.overture.ast.node.*;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.SBasicType;
import org.overturetool.vdmj.lex.LexIdentifierToken;
import org.overturetool.vdmj.lex.LexIntegerToken;
import org.overturetool.vdmj.lex.LexLocation;
import org.overturetool.vdmj.lex.LexNameToken;

public class DotGraphVisitor extends QuestionAdaptor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 637147601437624885L;
	
	public static Field[] getAllFields(Class klass) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(klass.getDeclaredFields()));
                        
        if (klass.getSuperclass() != null ) {
            fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
        }
        return fields.toArray(new Field[] {});
    }
	
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
		if (!parentName.equals(""))
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
		if (!parentName.equals(""))
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
		System.out.println("Processing : " + node.getClass().getSimpleName());
		String nodeName = createDefaultNode(question,node.getClass().getSimpleName()); 
				//new String[]{"<f1>Value: "+ node.toString() });
		
		for(Field field : getAllFields(node.getClass()))
		{
			if (!field.getName().equals("parent"))
			{
				try
				{
					field.setAccessible(true);
					Object fieldObject = field.get(node);
					System.out.println();
					System.out.print( node.getClass().getSimpleName() +"." + field.getName());
					if (fieldObject instanceof INode)
					{
						System.out.println(" IS instance of INode");
						INode childNode = (INode)fieldObject;
						childNode.apply(this,nodeName);
					}
					else if (fieldObject instanceof NodeList)
					{
						System.out.println(" IS instance of NodeList");
						NodeList<INode> childNodes = (NodeList<INode>)fieldObject;
						for(INode childNode : childNodes)
						{
							System.out.println("\t with child " + childNode.getClass().getSimpleName());
							childNode.apply(this,nodeName);
						}
					}
					else if (fieldObject instanceof LexLocation)
					{
						LexLocation locNode = (LexLocation)fieldObject;
						createDefaultNode(nodeName,locNode.getClass().getSimpleName(), 
								new String[]{"<f1>Value: In "+ locNode.file + 
								" from "+ locNode.startLine +":" + locNode.startPos +" to "+
								locNode.endLine +":" + locNode.endPos }); 
					}
					else if (fieldObject instanceof ExternalNode)
					{
						ExternalNode extNode = (ExternalNode)fieldObject;
						createDefaultNode(nodeName,extNode.getClass().getSimpleName(), 
								new String[]{"<f1>Value:" + extNode.toString()}); 
					}
					
				}
				catch(IllegalAccessException ex){}
			}
		}
	}

//	@Override
//	public void caseAIntLiteralSymbolicLiteralExp(
//			AIntLiteralSymbolicLiteralExp node, String question) {
//		
//		createDefaultNode(question,node.getClass().getSimpleName(), 
//				new String[]{"<f1>Location: "+ node.getLocation(),"<f2>Value: " + node.getValue().value }); 
//	}
		
//	@Override
//	public void defaultSBasicType(SBasicType node, String question) {
//		
//		createDefaultNode(question,node.getClass().getSimpleName(), 
//				new String[]{"<f1>Location:" + node.getLocation().toShortString()});
//				
//	}
//
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
	public void caseLexIntegerToken(LexIntegerToken node, String question) {
				
		createDefaultNode(question,node.getClass().getSimpleName(), 
				new String[]{"<f1>Value:" + node.value ,"<f2>Location:" + node.location.toShortString()});
	}
	
//
//	@Override
//	public void caseASourcefileSourcefile(ASourcefileSourcefile node,
//			String question) {
//		
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
//				new String[]{"<f1>Name:" + node.getName() });
//				
//		for(PDeclaration decl : node.getDecls())
//		{
//			decl.apply(this,nodeName);
//		}
//	
//	}
//
//	@Override
//	public void caseAChannelDeclaration(AChannelDeclaration node,
//			String question) {
//				
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
//				new String[]{"<f1>Name:channels","<f2>Location:" + node.getLocation().toShortString()});
//				
//		node.getChannelDefinition().apply(this,nodeName);
//	}
//
//
//	@Override
//	public void caseAChannelNameDeclaration(AChannelNameDeclaration node,
//			String question) {
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
//		node.getDeclaration().apply(this, nodeName);
//		
//	}
//
//
//	@Override
//	public void caseAChansetDeclaration(AChansetDeclaration node,
//			String question) {
//		// TODO Auto-generated method stub
//		super.caseAChansetDeclaration(node, question);
//	}
//
//	@Override
//	public void caseASingleTypeDeclaration(ASingleTypeDeclaration node,
//			String question) {
//						
//		String typeName = "null";
//		
//		if (node.getType() != null)
//			typeName = node.getType().toString(); 
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName(), 
//				new String[]{"<f1>Type:" + typeName ,"<f2>Location:" + node.getLocation().toShortString()});
//						
//		for(LexIdentifierToken id : node.getIdentifiers())
//		{
//			id.apply(this, nodeName);
//		}
//		
//	}
//
//	@Override
//	public void caseAChannelDefinition(AChannelDefinition node, String question) {
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
//						
//		for(AChannelNameDeclaration cnd : node.getChannelNameDecls())
//		{
//			cnd.apply(this,nodeName);
//		}
//	}
//
//	@Override
//	public void caseATypeGlobalDeclaration(ATypeGlobalDeclaration node,
//			String question) {
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
//		
//		for(ATypeDefinition tdecl : node.getTypeDefinitions())
//		{
//			tdecl.apply(this,nodeName);
//		}
//	}
	

//	@Override
//	public void caseATypeDefinition(ATypeDefinition node, String question) {
//		
//		String nodeName = createDefaultNode(question,node.getClass().getSimpleName());
//		
//		node.getAccess().apply(this,nodeName);
//		node.getName().apply(this, nodeName);
//		node.getType().apply(this,nodeName);
//		
//		//node.getInvExpression().apply(this,nodeName);
//		//node.getInvPattern().apply(this,nodeName);
//		
//	}
		
//	@Override
//	public void caseAAccessSpecifierAccessSpecifier(
//			AAccessSpecifierAccessSpecifier node, String question) {
//		
//		String nodeName = makeNewNodeName();
//		resultString.append("\t" + nodeName + " [label=\"{<f0>AAccessSpecifierAccessSpecifier|<f1>Access:"+ node.getAccess().toString() +"}\"];\n");
//		resultString.append("\t" + question + " -> " + nodeName +"\n");
//		
//	}
	
}

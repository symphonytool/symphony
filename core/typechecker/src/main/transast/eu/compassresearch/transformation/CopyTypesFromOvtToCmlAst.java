package eu.compassresearch.transformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExpBase;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexQuoteToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

public class CopyTypesFromOvtToCmlAst extends
    AnswerCMLAdaptor<org.overture.ast.node.INode>
  {
    
    public CopyTypesFromOvtToCmlAst(
        Map<org.overture.ast.node.INode, org.overture.ast.node.INode> nodeMap)
      {
        this.nodeMap = nodeMap;
      }
    
    private Map<org.overture.ast.node.INode, org.overture.ast.node.INode> nodeMap;
    
    @Override
    public org.overture.ast.node.INode defaultINode(INode node)
        throws AnalysisException
      {
        
        org.overture.ast.node.INode cmlNode = nodeMap.get(node);
        if (cmlNode == null)
          throw new AnalysisException("Tree structure not the same");
        
        if (cmlNodeAlreadyHasType(cmlNode))
          return cmlNode;
        
        org.overture.ast.types.PType ovtType = null;
        try
          {
            Class<?> clazz = node.getClass();
            Method getType = clazz.getMethod("getType", new Class<?>[0]);
            ovtType = (org.overture.ast.types.PType) getType.invoke(node,
                new Object[0]);
          } catch (Exception e)
          {
            return cmlNode;
          }
        
        PType type = typeToType(ovtType);
        if (type == null)
          throw new AnalysisException("Could not copy type " + ovtType
              + " to a cml type.");
        try
          {
            Class<?> pexpBase = PExpBase.class;
            Class<?> clazz = pexpBase;
            Method setType = clazz.getMethod("setType",
                new Class<?>[] { PType.class });
            setType.invoke(cmlNode, type);
          } catch (Exception e)
          {
            // return cmlNode;
            return super.defaultINode(node);
          }
        return cmlNode;
      }
    
    private boolean cmlNodeAlreadyHasType(org.overture.ast.node.INode cmlNode)
      {
        try
          {
            Class<?> clazz = cmlNode.getClass();
            Method getType = clazz.getMethod("getType", new Class<?>[0]);
            PType cmlType = (PType) getType.invoke(cmlNode, new Object[0]);
            return cmlType != null;
          } catch (Exception e)
          {
            return false;
          }
      }
    
    private PType typeToType(org.overture.ast.types.PType ovtType)
      {
        try
          {
            String cmlName = ovtType.getClass().getName()
                .replace("org.overture.", "eu.compassresearch.");
            
            // create cml class
            ClassLoader cl = this.getClass().getClassLoader();
            Class<?> cmlClz = cl.loadClass(cmlName);
            Constructor<?> ctor = cmlClz.getConstructor(new Class<?>[0]);
            PType cmlType = (PType) ctor.newInstance(new Object[0]);
            
            // Copy getter to setter
            mirror(cmlType, ovtType);
            
            return cmlType;
          } catch (Exception e)
          {
            return null;
          }
      }
    
    /**
     * Just even try starting to comprehend the preconditions for this functions
     * makes you stop thinking at all. But loosely put: The to-object-tree must
     * have set methods with exactly one argument for their state. The
     * from-object-tree must have get methods with no arguments for all of their
     * state.
     * 
     * If anything goes wrong the deep-copy stops for that subtree.
     * 
     * @param to
     * @param from
     */
    private void deepCopy(Object to, Object from)
      {
        try
          {
            Class<?> toClz = to.getClass();
            Class<?> fromClz = from.getClass();
            for (Method getter : fromClz.getMethods())
              {
                if (getter.getName().startsWith("get"))
                  {
                    String wantedName = getter.getName().replace("get", "set");
                    for (Method setter : toClz.getMethods())
                      {
                        if (setter.getName().equals(wantedName))
                          {
                            Object getterResult = getter.invoke(from,
                                new Object[0]);
                            Class<?> getterRet = getter.getReturnType();
                            if (isBasicJavaType(getterRet))
                              {
                                setter.invoke(to, getterResult);
                              } else
                              {
                                String cmlClzName = getterResult
                                    .getClass()
                                    .getName()
                                    .replace("org.overture.",
                                        "eu.compassresearch.");
                                Class<?> cmlClz = getClass().getClassLoader()
                                    .loadClass(cmlClzName);
                                
                                // Special case, handle LexQuoteToken
                                if (LexQuoteToken.class == cmlClz)
                                  {
                                    org.overture.ast.lex.LexQuoteToken quote = (org.overture.ast.lex.LexQuoteToken) getterResult;
                                    
                                    LexLocation cmlLocation = new LexLocation();
                                    deepCopy(cmlLocation, quote.location);
                                    String cmlFrom = quote.value;
                                    LexQuoteToken r = new LexQuoteToken(
                                        cmlFrom, cmlLocation);
                                    setter.invoke(to, r);
                                    return;
                                  }
                                Constructor<?> argClassCtor = cmlClz
                                    .getConstructor(new Class<?>[0]);
                                Object copyedArd = argClassCtor
                                    .newInstance(new Object[0]);
                                deepCopy(copyedArd, getterResult);
                                setter.invoke(to, copyedArd);
                              }
                            
                          }
                      }
                  }
                
              }
          } catch (Exception e)
          {
            e.printStackTrace();
            return;
          }
        
      }
    
    private boolean isBasicJavaType(Class<?> getterRet)
      {
        boolean res = getterRet.getName().startsWith("java.lang")
            || getterRet.getName().startsWith("java.util")
            || getterRet.getName().startsWith("java.io")
            || getterRet.getName().startsWith("java.math")
            || getterRet.getName().startsWith("java.nio")
            || getterRet.getName().startsWith("java.net")
            || getterRet.getName().startsWith("java.security")
            || getterRet.getName().startsWith("java.sql")
            || getterRet.getName().startsWith("java.awt")
            || getterRet.getName().startsWith("javax.swing")
            || getterRet.getName().startsWith("java.applet");
        return res;
      }
    
    private void mirror(PType cmlType, org.overture.ast.types.PType ovtType)
      {
        deepCopy(cmlType, ovtType);
      }
    
  }

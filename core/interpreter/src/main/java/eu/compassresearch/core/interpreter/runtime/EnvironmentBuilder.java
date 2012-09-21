package eu.compassresearch.core.interpreter.runtime;

import java.util.List;

import eu.compassresearch.ast.analysis.AnalysisAdaptor;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypeDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.definitions.SFunctionDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.Environment;

public class EnvironmentBuilder extends AnalysisAdaptor
  {
    
    /**
	 * 
	 */
    private static final long        serialVersionUID = 493918199975006733L;
    private Environment<PDefinition> env              = new Environment<PDefinition>(
                                                          null);
    
    public static Environment<PDefinition> BuildGlobalEnvironment(
        List<PSource> sources)
      {
        EnvironmentBuilder envBuilder = new EnvironmentBuilder();
        
        for (PSource source : sources)
          {
            for (SParagraphDefinition def : source.getParagraphs())
              {
                try
                  {
                    def.apply(envBuilder);
                  }
                // this should not happen when typechecked!!
                catch (AnalysisException ex)
                  {
                    ex.printStackTrace();
                  }
              }
          }
        
        return envBuilder.getEnvironment();
      }
    
    public Environment<PDefinition> getEnvironment()
      {
        return env;
      }
    
    @Override
    public void caseAClassParagraphDefinition(AClassParagraphDefinition node)
        throws AnalysisException
      {
        
        // TODO: check access specifier
        env.put(node.getName(), node);
        
      }
    
    @Override
    public void caseAProcessParagraphDefinition(AProcessParagraphDefinition node)
        throws AnalysisException
      {
        
        // TODO: check access specifier
        AProcessDefinition processDef = node.getProcessDefinition();
        env.put(processDef.getName(), processDef);
        
      }
    
    @Override
    public void caseAChansetParagraphDefinition(AChansetParagraphDefinition node)
        throws AnalysisException
      {
        
        for (AChansetDefinition chansetDef : node.getChansets())
          {
            env.put(chansetDef.getName(), chansetDef);
          }
      }
    
    @Override
    public void caseATypesParagraphDefinition(ATypesParagraphDefinition node)
        throws AnalysisException
      {
        
        for (ATypeDefinition typeDef : node.getTypes())
          {
            env.put(typeDef.getName(), typeDef);
          }
      }
    
    @Override
    public void caseAFunctionParagraphDefinition(
        AFunctionParagraphDefinition node) throws AnalysisException
      {
        
        for (SFunctionDefinition functionDef : node.getFunctionDefinitions())
          {
            env.put(functionDef.getName(), functionDef);
          }
      }
    
    @Override
    public void caseAChannelParagraphDefinition(AChannelParagraphDefinition node)
        throws AnalysisException
      {
        
        for (AChannelNameDeclaration cnd : node.getChannelNameDeclarations())
          {
            for (LexIdentifierToken channelName : cnd.getSingleType()
                .getIdentifiers())
              {
                // env.addChannel(channelName, cnd.getSingleType().getType());
              }
          }
      }
    
  }

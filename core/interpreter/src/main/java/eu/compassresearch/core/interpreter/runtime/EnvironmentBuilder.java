package eu.compassresearch.core.interpreter.runtime;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.Environment;

public class EnvironmentBuilder extends AnalysisCMLAdaptor
  {
    
    /**
	 * 
	 */
    private static final long        serialVersionUID   = 493918199975006733L;
    private Environment<PDefinition> env                = null;
    private String                   lastDefinedProcess = null;
    
    public EnvironmentBuilder(List<PSource> sources)
      {
        BuildGlobalEnvironment(sources);
      }
    
    private void BuildGlobalEnvironment(List<PSource> sources)
      {
        // EnvironmentBuilder envBuilder = new EnvironmentBuilder();
        
        env = new Environment<PDefinition>(null);
        
        for (PSource source : sources)
          {
            for (SParagraphDefinition def : source.getParagraphs())
              {
                try
                  {
                    def.apply(this);
                  }
                // this should not happen when typechecked!!
                catch (AnalysisException ex)
                  {
                    ex.printStackTrace();
                  }
              }
          }
      }
    
    public String getLastDefinedProcess()
      {
        return lastDefinedProcess;
      }
    
    public Environment<PDefinition> getGlobalEnvironment()
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
        lastDefinedProcess = processDef.getName().getName();
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
        
        for (PDefinition functionDef : node.getFunctionDefinitions())
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

package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatEnvironment;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
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

public class EnvironmentBuilder extends AnalysisCMLAdaptor
  {
    /**
	 * 
	 */
    private static final long        serialVersionUID   = 493918199975006733L;
    private List<PDefinition>        globalDefs	        = new LinkedList<PDefinition>();
    private AProcessDefinition       lastDefinedProcess = null;
    
    public EnvironmentBuilder(List<PSource> sources)
    {
    	BuildGlobalEnvironment(sources);
    }

    private void BuildGlobalEnvironment(List<PSource> sources)
      {
        // EnvironmentBuilder envBuilder = new EnvironmentBuilder();
        
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
    
    public AProcessDefinition getLastDefinedProcess()
      {
        return lastDefinedProcess;
      }
    
    public Environment getGlobalEnvironment()
      {
        return new FlatEnvironment(globalDefs);
      }
    
    @Override
    public void caseAClassParagraphDefinition(AClassParagraphDefinition node)
        throws AnalysisException
      {
        
        // TODO: check access specifier
    	globalDefs.add(node);
        //env..put(node.getName(), );
        
      }
    
    @Override
    public void caseAProcessParagraphDefinition(AProcessParagraphDefinition node)
        throws AnalysisException
      {
        
        // TODO: check access specifier
        AProcessDefinition processDef = node.getProcessDefinition();
        globalDefs.add(processDef);
        //env.put(processDef.getName(), processDef);
        lastDefinedProcess = processDef;
      }
    
    @Override
    public void caseAChansetParagraphDefinition(AChansetParagraphDefinition node)
        throws AnalysisException
      {
        
    	for (AChansetDefinition chansetDef : node.getChansets())
    	{
    		//env.put(chansetDef.getName(), chansetDef);
    		globalDefs.add(chansetDef);
    	}
      }
    
    @Override
    public void caseATypesParagraphDefinition(ATypesParagraphDefinition node)
        throws AnalysisException
      {
        
        for (PDefinition typeDef : node.getTypes())
          {
        	globalDefs.add(typeDef);
            //env.put(typeDef.getName(), (ATypeDefinition)typeDef);
          }
      }
    
    @Override
    public void caseAFunctionParagraphDefinition(
        AFunctionParagraphDefinition node) throws AnalysisException
      {
        
        for (PDefinition functionDef : node.getFunctionDefinitions())
          {
        	globalDefs.add(functionDef);
            //env.put(functionDef.getName(), functionDef);
          }
      }
    
    @Override
    public void caseAChannelParagraphDefinition(AChannelParagraphDefinition node)
        throws AnalysisException
      {
        
        for (AChannelNameDefinition cnd : node.getChannelNameDeclarations())
          {
            for (LexIdentifierToken channelName : cnd.getSingleType()
                .getIdentifiers())
              {
                // env.addChannel(channelName, cnd.getSingleType().getType());
              }
          }
      }
    
  }

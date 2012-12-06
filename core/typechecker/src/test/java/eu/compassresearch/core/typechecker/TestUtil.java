package eu.compassresearch.core.typechecker;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;

public class TestUtil
  {
    
	public static TypeIssueHandler runTypeChecker(String file)
	{
		AFileSource fileSource = new AFileSource();
		
		fileSource.setFile(new File(file));
		
		List<PSource> cmlSources = Arrays.asList(new PSource[] { fileSource });
		
		
		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		
		CmlTypeChecker checker = VanillaFactory.newTypeChecker(cmlSources, issueHandler);
		
		checker.typeCheck();
		
		return issueHandler;
	}
	
	public static String buildErrorMessage(TypeIssueHandler tc, boolean expectedTypesOk) {
		StringBuilder sb = new StringBuilder();
		if (expectedTypesOk) {
			sb.append("Expected type checking to be successful, the following errors were unexpected:\n");
			for (CMLTypeError error : tc.getTypeErrors())
				sb.append(error.toString() + "\n------\n");
			if (tc.getTypeErrors().size() > 0)
			System.out.println(tc.getTypeErrors().get(0).getStackTrace());
		} else {
			sb.append("Expected type checking to fail but it was successful.");
		}
		return sb.toString();
	}

	
    public static <T> void addTestProgram(List<Object[]> col, String src,
        Object... objs)
      {
        Object[] a = new Object[objs.length + 1];
        a[0] = src;
        System.arraycopy(objs, 0, a, 1, objs.length);
        col.add(a);
      }
    
    @SuppressWarnings("serial")
    public static Object findFirst(final Class<?> c, PSource s)
        throws AnalysisException
      {
        class Holder
          {
            Object pointer;
          }
        final Holder h = new Holder();
        
        DepthFirstAnalysisCMLAdaptor d = new DepthFirstAnalysisCMLAdaptor()
          {
            
            @Override
            public void defaultInINode(org.overture.ast.node.INode node)
                throws AnalysisException
              {
                if (node.getClass().equals(c))
                  {
                    h.pointer = node;
                    return;
                  }
                super.defaultInINode(node);
              }
            
          };
        s.apply(d);
        return h.pointer;
      }
    
    public static <T> void addFileProgram(List<Object[]> col, String filename,
        Object... objs) throws IOException
      {
        String progDir = "../../docs/cml-examples/";
        File fin = new File(progDir + filename);
        InputStream is = new FileInputStream(fin);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();
        Object[] args = new Object[objs.length + 1];
        args[0] = new String(buffer);
        System.arraycopy(objs, 0, args, 1, objs.length);
        col.add(args);
      }
    
    public static PSource makeSource(String cmlSource, String... names)
      {
        InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
        AInputStreamSource source = new AInputStreamSource();
        source.setOrigin(names != null && names.length > 0 ? names[0] : "Test Parameter");
        source.setStream(cmlSourceIn);
        return source;
      }
    
  }

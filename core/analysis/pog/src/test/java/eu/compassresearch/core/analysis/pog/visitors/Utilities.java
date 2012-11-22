package eu.compassresearch.core.analysis.pog.visitors;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;

public class Utilities {

    public static PSource makeSource(String cmlSource)
    {
      InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
      AInputStreamSource source = new AInputStreamSource();
      source.setOrigin("Test Parameter");
      source.setStream(cmlSourceIn);
      return source;
    }
    
}

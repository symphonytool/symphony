package eu.compassresearch.core.parser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

public class CmlParserRig {
    public static void main(String[] args) throws Exception {
	ANTLRInputStream stdin = new ANTLRInputStream(System.in);
	CmlLexer lexer = new CmlLexer(stdin);
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	CmlParser parser = new CmlParser(tokens);

	parser.exprbase();
	// System.out.println(parser.name());
    }
}

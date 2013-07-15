package eu.compassresearch.core.interpreter.utility;

import junit.framework.Assert;

import org.junit.Test;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;

import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;

public class TestLocationExtractor {

	@Test
	public void testExtractLocation() {
		
		AInterleavingReplicatedAction node = new AInterleavingReplicatedAction(new LexLocation("test", "Test", 0, 0, 1, 1, 0, 1), null, null, null);
		
		ILexLocation loc = LocationExtractor.extractLocation(node);
		
		Assert.assertNotNull(loc);
		
		Assert.assertEquals("Test", loc.getModule());
		
	}
}

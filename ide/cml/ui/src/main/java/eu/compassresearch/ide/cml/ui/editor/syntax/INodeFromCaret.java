package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.lang.reflect.Method;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;

public class INodeFromCaret extends DepthFirstAnalysisAdaptor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private INode bestCandidate;
    private LexLocation bestCandidateLocation;
    int caret;

    public INode getBestCandidate() {
	return bestCandidate;
    }

    public LexLocation getBestCandidateLocation() {
	return bestCandidateLocation;
    }

    public INodeFromCaret() {

    }

    public INodeFromCaret(int caret, INode starterNode) {
	this.caret = caret;
	this.bestCandidate = starterNode;
	this.bestCandidateLocation = getLocationFromObject(bestCandidate);
    }

    private LexLocation getLocationFromObject(Object o) {
	for (Method m : o.getClass().getMethods()) {
	    if ("getLocation".equals(m.getName())) {
		try {
		    LexLocation loc = (LexLocation) m.invoke(o, new Object[0]);
		    return loc;
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    
    
    public void defaultInINode(INode node) throws AnalysisException {

	try {
	    super.defaultInINode(node);
	    LexLocation nodeLoc = getLocationFromObject(node);
	    if (bestCandidateLocation == null){
		bestCandidate = node;
		bestCandidateLocation = nodeLoc;
		node.apply(this);
	    }	
	    if (nodeLoc.startOffset > caret) // we are past the caret
		return;
	    if (nodeLoc.startOffset > bestCandidateLocation.startOffset) {
		bestCandidate = node;
		bestCandidateLocation = nodeLoc;
		node.apply(this);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}

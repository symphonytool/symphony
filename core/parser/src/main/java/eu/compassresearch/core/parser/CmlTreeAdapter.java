package eu.compassresearch.core.parser;

import org.antlr.runtime.tree.TreeAdaptor;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;


public class CmlTreeAdapter implements TreeAdaptor {
    public Object create(Token payload) {
	if (payload instanceof CommonToken) {
	    return create((CommonToken)payload);
	} else {
	    return null; //FIXME
	}
    }
    public Object create(CommonToken payload) {
	return null; //FIXME
    }
    public Object dupNode(Object treeNode) {
	return null; //FIXME
    }
    public Object dupTree(Object tree) {
	return null; //FIXME
    }
    public Object nil() {
	return null; //FIXME
    }
    public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
	return null; //FIXME
    }
    public boolean isNil(Object tree) {
	return false; //FIXME
    }
    public void addChild(Object t, Object child) {
	return; //FIXME
    }
    public Object becomeRoot(Object newRoot, Object oldRoot) {
	return null; //FIXME
    }
    public Object rulePostProcessing(Object root) {
	return null; //FIXME
    }
    public int getUniqueID(Object node) {
	return 0; //FIXME
    }
 
    // R e w r i t e  R u l e s
    public Object becomeRoot(Token newRoot, Object oldRoot) {
	return null; //FIXME
    }
    public Object create(int tokenType, Token fromToken) {
	return null; //FIXME
    }
    public Object create(int tokenType, Token fromToken, String text) {
	return null; //FIXME
    }
    public Object create(int tokenType, String text) {
	return null; //FIXME
    }
 
    // C o n t e n t
    public int getType(Object t) {
	return 0; //FIXME
    }
    public void setType(Object t, int type) {
	return; //FIXME
    }
    public String getText(Object t) {
	return null; //FIXME
    }
    public void setText(Object t, String text) {
	return; //FIXME
    }
    public Token getToken(Object t) {
	return null; //FIXME
    }
    public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
	return; //FIXME
    }
    public int getTokenStartIndex(Object t) {
	return 0; //FIXME
    }
    public int getTokenStopIndex(Object t) {
	return 0; //FIXME
    }
 
    // N a v i g a t i o n  /  T r e e  P a r s i n g
    public Object getChild(Object t, int i) {
	return null; //FIXME
    }
    public void setChild(Object t, int i, Object child) {
	return; //FIXME
    }
    public Object deleteChild(Object t, int i) {
	return null; //FIXME
    }
    public int getChildCount(Object t) {
	return 0; //FIXME
    }
    public Object getParent(Object t) {
	return null; //FIXME
    }
    public void setParent(Object t, Object parent) {
	return; //FIXME
    }
    public int getChildIndex(Object t) {
	return 0; //FIXME
    }
    public void setChildIndex(Object t, int index) {
	return; //FIXME
    }
    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
	return; //FIXME
    }
}

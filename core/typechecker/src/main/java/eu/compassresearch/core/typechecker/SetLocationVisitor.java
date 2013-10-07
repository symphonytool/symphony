package eu.compassresearch.core.typechecker;

import java.lang.reflect.Method;
import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.ATcpStreamSource;
import eu.compassresearch.ast.program.PSource;

/**
 * Each subtree (PSource) knows the location (file or stream). This visitor runs
 * down the tree and sets the "file" field on all LexLocations.
 * 
 * @author rwl
 * 
 */
@SuppressWarnings("serial")
public class SetLocationVisitor extends DepthFirstAnalysisCMLAdaptor {

	private PSource root;

	public static void updateLocations(Collection<PSource> lp) {

		SetLocationVisitor lv = new SetLocationVisitor();
		for (PSource s : lp) {
			if (s != null)
				try {
					lv.root = s;
					s.apply(lv);
				} catch (AnalysisException e) {
				}
		}
	}

	private SetLocationVisitor() {
	}

	private void setTheLocation(Object node) {
		try {
			//FIXME: set the location for final fields as well e.g. LexToken
			Class<?> clz = node.getClass();
			Method getLocation = clz.getMethod("getLocation", new Class<?>[0]);
			if (getLocation == null)
				return;

			LexLocation oldLocation = (LexLocation) getLocation.invoke(node,
					new Object[0]);

			if (oldLocation != null) {
				String filePath = "";
				if (root != null) {
					if (root instanceof AFileSource)
						filePath = ((AFileSource) root).getFile() + "";

					if (root instanceof AInputStreamSource)
						filePath = ((AInputStreamSource) root).getOrigin();

					if (root instanceof ATcpStreamSource)
						filePath = ((ATcpStreamSource) root).getIp() + "";

				}
				LexLocation newLocation = new LexLocation(filePath,
						oldLocation.module, oldLocation.startLine,
						oldLocation.startPos, oldLocation.endLine,
						oldLocation.endPos, oldLocation.startOffset,
						oldLocation.endOffset);

				Method setLocation = clz.getMethod("setLocation",
						new Class<?>[] { LexLocation.class });
				if (setLocation == null)
					return;

				setLocation.invoke(node, new Object[] { newLocation });
			}

		} catch (NoSuchMethodException eee) {
			// shut up
		} catch (NoSuchMethodError ee) {
			// shut up
		} catch (Exception e) {
			e.printStackTrace();
			// well was not supposed to happen :S
		}
	}

	@Override
	public void defaultOutINode(INode node) throws AnalysisException {
		setTheLocation(node);
	}
}

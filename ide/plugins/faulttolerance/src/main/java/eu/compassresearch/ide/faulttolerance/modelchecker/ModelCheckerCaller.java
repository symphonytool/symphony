/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.modelchecker;

import java.util.Random;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class ModelCheckerCaller"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ModelCheckerCaller {

	public void verifyFailuresDivergences(String specName, String implName,
			ModelCheckingResult result) {
		// TODO Auto-generated method stub
		try {
			result.setSuccess(new Random().nextInt(10) > 5);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}

	}

	public void verifyDivergenceFreedom(String processName,
			ModelCheckingResult result) {
		// TODO Auto-generated method stub
		try {
			result.setSuccess(new Random().nextInt(10) > 5);
			Thread.sleep(1000);
			// TODO Auto-generated method stub
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2007 EclipseGraphviz contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     abstratt technologies
 *     Scott Bronson
 *******************************************************************************/
package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;



/**
 * The entry point to the Graphviz support API.
 */
public class GraphViz {
	private static final String DEFAULT_FORMAT = "svg";



	public void runDot(File dotInput) throws InterruptedException, TimeoutException, IOException {
		
		ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
		String dotCmd = "dot -O -T" + DEFAULT_FORMAT + " " + dotInput.getAbsolutePath();
		
			final ProcessController controller = new ProcessController(10000, dotCmd);
			controller.forwardErrorOutput(errorOutput);
			controller.forwardOutput(System.out);
			controller.forwardInput(System.in);
			controller.execute();
			
	}
	
	
	
	 public static void main(String[] args) throws Exception{
	 
		GraphViz gv = new GraphViz();
		String file = "D:\\eclipse-compass\\runtime-DebugMC\\Test\\generated\\modelchecker\\A.gv";
		
		gv.runDot(new File(file));
		System.out.println("finished");
	}
	
}

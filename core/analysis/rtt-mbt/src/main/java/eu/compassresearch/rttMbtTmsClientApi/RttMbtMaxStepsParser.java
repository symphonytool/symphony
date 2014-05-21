package eu.compassresearch.rttMbtTmsClientApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class RttMbtMaxStepsParser {

	/*
MAX SOLVER STEPS;100
MAX SIMULATION STEPS;0
	 */
	protected int maxSolverSteps = 100;
	protected int maxSimulationSteps = 10;

	public RttMbtMaxStepsParser() {
	}

	public Boolean readMaxSteps(String filename) {
		Boolean success = true;
		
		// Read content.
		InputStream istream;
		Scanner fileScanner = null;
		Scanner line = null;
		File advancedConf = new File(filename);
		try {
			istream = new FileInputStream(advancedConf);
			fileScanner = new Scanner(istream);
		} catch (FileNotFoundException e) {
			System.err.println("unable to open advanved configuration file " + filename);
		}
		if (fileScanner == null) {
			return false;
		}
		while (fileScanner.hasNextLine()) {
			line = new Scanner(fileScanner.nextLine());
			line.useDelimiter(";");
			String option, value;
			// read option
			if (line.hasNext()) {
				option = line.next();				
			} else {
				continue;
			}
			// read value
			if (line.hasNext()) {
				value = line.next();
			} else {
				continue;
			}
			line.close();
			// update options class
			if (option.compareTo("MAX SOLVER STEPS") == 0) {
				setMaxSolverSteps(Integer.parseInt(value));
			} else if (option.compareTo("MAX SIMULATION STEPS") == 0) {
				setMaxSimulationSteps(Integer.parseInt(value));
			} else {
				System.err.println("unknown option '" + option + "' in max steps file " + filename);
				return false;
			}
		}
		fileScanner.close();
		return success;
	}

	public int getMaxSolverSteps() {
		return maxSolverSteps;
	}

	public void setMaxSolverSteps(int maxSolverSteps) {
		this.maxSolverSteps = maxSolverSteps;
	}

	public int getMaxSimulationSteps() {
		return maxSimulationSteps;
	}

	public void setMaxSimulationSteps(int maxSimulationSteps) {
		this.maxSimulationSteps = maxSimulationSteps;
	}
}

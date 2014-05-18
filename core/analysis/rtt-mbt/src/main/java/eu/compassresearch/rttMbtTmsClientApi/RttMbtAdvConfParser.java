package eu.compassresearch.rttMbtTmsClientApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class RttMbtAdvConfParser {

	/*
GC;1;if 1, cover all goals in d addgoals(ordered).conf, even if they are already covered by other procedures
BT;0;switch back tracking on if 1
LO;0;produce logger threads instead of checkers if 1
AI;1;use abstract interpretation for speed-up of solver, if 1
MM;0;maximise model coverage if 1
SC;0;perform sanity checks in solver and abstract interpreter if 1
RB;0;do robustness testing if 1
RP;0;if RB=1 RP defines the percentage of robustness transitions to be performed
CI;1;maximal number of simultaneous input changes
DI;10;minimal duration between two input changes
LI;10;upper limit for duration between two input changes
MC;0;Perform model checking instead of test generation, if 1
	 */

	protected Boolean GC = true;
	protected Boolean BT = false;
	protected Boolean LO = false;
	protected Boolean AI = true;
	protected Boolean MM = false;
	protected Boolean SC = false;
	protected Boolean RB = false;
	protected int RP = 0;
	protected int CI = 1;
	protected int DI = 10;
	protected int LI = 10000;
	protected int VL = 1;
	protected Boolean MC = false;

	public RttMbtAdvConfParser() {
	}

	public Boolean readAdvancedConfig(String filename) {
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
			if (option.compareTo("GC") == 0) {
				setGC(value.compareTo("1") == 0);
			} else if (option.compareTo("BT") == 0) {
				setBT(value.compareTo("1") == 0);
			} else if (option.compareTo("LO") == 0) {
				setLO(value.compareTo("1") == 0);
			} else if (option.compareTo("AI") == 0) {
				setAI(value.compareTo("1") == 0);
			} else if (option.compareTo("MM") == 0) {
				setMM(value.compareTo("1") == 0);
			} else  if (option.compareTo("SC") == 0) {
				setSC(value.compareTo("1") == 0);
			} else if (option.compareTo("RB") == 0) {
				setRB(value.compareTo("1") == 0);
			} else if (option.compareTo("RP") == 0) {
				setRP(Integer.parseInt(value));
			} else if (option.compareTo("CI") == 0) {
				setCI(Integer.parseInt(value));
			} else if (option.compareTo("DI") == 0) {
				setDI(Integer.parseInt(value));
			} else if (option.compareTo("LI") == 0) {
				setLI(Integer.parseInt(value));
			} else if (option.compareTo("MC") == 0) {
				setMC(value.compareTo("1") == 0);
			} else if (option.compareTo("VL") == 0) {
				setVL(Integer.parseInt(value));
			} else {
				System.err.println("unknown option '" + option + "' in advanved configuration file " + filename);
				return false;
			}
		}
		fileScanner.close();
		return success;
	}

	public Boolean getGC() {
		return GC;
	}

	public void setGC(Boolean gC) {
		GC = gC;
	}

	public Boolean getBT() {
		return BT;
	}

	public void setBT(Boolean bT) {
		BT = bT;
	}

	public Boolean getLO() {
		return LO;
	}

	public void setLO(Boolean lO) {
		LO = lO;
	}

	public Boolean getAI() {
		return AI;
	}

	public void setAI(Boolean aI) {
		AI = aI;
	}

	public Boolean getMM() {
		return MM;
	}

	public void setMM(Boolean mM) {
		MM = mM;
	}

	public Boolean getSC() {
		return SC;
	}

	public void setSC(Boolean sC) {
		SC = sC;
	}

	public Boolean getRB() {
		return RB;
	}

	public void setRB(Boolean rB) {
		RB = rB;
	}

	public int getRP() {
		return RP;
	}

	public void setRP(int rP) {
		RP = rP;
	}

	public int getCI() {
		return CI;
	}

	public void setCI(int cI) {
		CI = cI;
	}

	public int getDI() {
		return DI;
	}

	public void setDI(int dI) {
		DI = dI;
	}

	public int getLI() {
		return LI;
	}

	public void setLI(int lI) {
		LI = lI;
	}

	public int getVL() {
		return VL;
	}

	public void setVL(int lI) {
		VL = lI;
	}

	public Boolean getMC() {
		return MC;
	}

	public void setMC(Boolean mC) {
		MC = mC;
	}
}

package eu.compassresearch.core.interpreter.remote.rttmbt.testdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueSet;

import eu.compassresearch.core.interpreter.remote.rttmbt.testdriver.TestLog.Pair;

public class VdmTestDriver implements RemoteControl {
	private long startOfTestTimeStamp = 0;
	private long globalTimestamp = 0;
	private long lastStimulationTimestamp = -1;
	private long maxStimulationTimestamp = 0;
	private long scanningResolution = 1000;
	private JSONArray jsonSignals;
	private Map<Long,List<Pair<String, String>>> testStimulations = new HashMap<Long,List<Pair<String, String>>>();
	private Map<String, List<Pair<Long,String>>> signalLog = new HashMap<String, List<Pair<Long,String>>>();
	
	@Override
	public void run(RemoteInterpreter interpreter) throws Exception
	{
		// print header
		TestLog.printTestlogEntry(globalTimestamp, TestLog.header);

		// create System Under Test
		TestLog.println("create SUT");
		createSUT(interpreter);
		getState(interpreter);

		// parse stimulations.json
		TestLog.println("parse stimulations...");
		parseSignals("stimulations.json");
		TestLog.println("test stimulations: " + testStimulations.toString());

		// delete existing replay.log
		File file = new File("replay.log");
		if (file.exists()) {
			file.delete();
		}

		// Perform Test
		startOfTestTimeStamp = new Date().getTime();
		long timestamp = 0;
		while (timestamp >= 0) {
			// begin test step
			TestLog.println("\n---\n--- begin test step at timestamp " + globalTimestamp + "\n---");

			// perform stimulations
			List<Pair<String, String>> stimulations = getStimulations(timestamp);
			performStimulations(interpreter, stimulations);
			
	        // retrieve and log changed states of the SUT
	        List<String> indications = getIndications(interpreter);
			TestLog.println("TestDriver: changedSignals " + indications);
			getState(interpreter);

	        // calculate next timestamp (and sleep until then)
	        timestamp = getNextTimestamp();
			TestLog.println("TestDriver: next timestamp: " + timestamp);
		}
		
		TestLog.println("signal log: " + signalLog.toString());

		// print footer
		TestLog.printTestlogEntry(globalTimestamp, TestLog.footer);

	}

	private void createSUT(RemoteInterpreter interpreter)  throws Exception{
		interpreter.create("ifm", "new IFM()");
		interpreter.create("sut", "new smallDwarf()");
		interpreter.execute("ifm.setSUT(sut)");
		TestLog.println("TestDriver: SUT has been created");
	}
	
    public List<Pair<String, String>> getStimulations(long timestamp) {

    	TestLog.println("retrieving stimulations between last stimulation time stamp " + lastStimulationTimestamp + " and current test step time stamp " + timestamp);

    	// retrieve stimulations from parsed stimulations.json
		List<Pair<String, String>> testStepStimulations = new ArrayList<Pair<String, String>>();
		lastStimulationTimestamp++;
		while (lastStimulationTimestamp <= timestamp) {
			List<Pair<String, String>> stimulations = testStimulations.get(lastStimulationTimestamp);
			if (stimulations != null) {
				TestLog.println("adding stimulations for time stamp " + lastStimulationTimestamp + ": " + stimulations.toString());
				testStepStimulations.addAll(stimulations);
			}
			lastStimulationTimestamp++;
		}

    	// store time stamp of the last stimulation retrieved from stimulations
    	lastStimulationTimestamp = timestamp;

		return testStepStimulations;
    }

    public void performStimulations(RemoteInterpreter interpreter,
    							    List<Pair<String, String>> stimulations) throws Exception {
    	// set SUT2TE signals in IFM
		for (int idx = 0; idx < stimulations.size(); idx++) {
			// perform stimulation
			String name = stimulations.get(idx).getLeft();
			String value = stimulations.get(idx).getRight();
			String stimulation = "ifm.set" + name + "(" + value + ")";
			// debug output
			TestLog.println("performing stimulation " + stimulation);
			interpreter.execute(stimulation);
			// create test-log entry
			TestLog.println("TM " + String.format("%011d", globalTimestamp) + " AM   0 G " + name + ": " + value);
			TestLog.printTestlogEntry(globalTimestamp, name, value);
			// create replay.log entry
			addLogEntry(globalTimestamp, name, value);
			// get SUT state for debugging
			getState(interpreter);
		}
		// call stimulation operation of IFM
		TestLog.println("call ifm.performStimulation()");
		interpreter.execute("ifm.performStimulation()");
		getState(interpreter);
	}

    public List<String> getIndications(RemoteInterpreter interpreter) throws Exception {
    	List<String> indications = new ArrayList<String>();
    	// get changed signals from IFM
    	Value changedSignals = interpreter.valueExecute("ifm.getChangedOutputs()");

    	// get current time stamp
    	long lDateTime = new Date().getTime();
    	globalTimestamp = lDateTime - startOfTestTimeStamp;

    	TestLog.println("changed signals: " + changedSignals);
		getState(interpreter);
		ValueSet set = changedSignals.setValue(null);
    	Value SUT2TE_Value;
		for (int idx = 0; idx < set.size(); idx++) {
			Value sig = set.get(idx);
			String signalName = sig.toString();
			signalName = signalName.substring(1, signalName.length() - 1);
			SUT2TE_Value = interpreter.valueExecute("ifm." + signalName);
			indications.add(signalName + ", [" + globalTimestamp + "," + SUT2TE_Value.toString() + "]");
			// add log entry
			List<Pair<Long,String>> logEntries = signalLog.get(signalName);
			if (logEntries == null) {
				logEntries = new ArrayList<Pair<Long, String>>();
			}
			logEntries.add(new Pair<Long,String>(globalTimestamp,SUT2TE_Value.toString()));
			signalLog.put(signalName, logEntries);
			// creste testlog entry
			TestLog.println("TM " + String.format("%011d", globalTimestamp) + " AM   0 G " + signalName + ": " + SUT2TE_Value.toString());
			TestLog.printTestlogEntry(globalTimestamp, signalName, SUT2TE_Value.toString());
			// create replay.log entry
			addLogEntry(globalTimestamp, signalName, SUT2TE_Value.toString());
		}

		return indications;
    }

    public long getNextTimestamp() {

    	// get current time stamp
    	long lDateTime = new Date().getTime();
    	globalTimestamp = lDateTime - startOfTestTimeStamp;

    	// next time stamp for SUT output scanning if no SUT stimulaitons
    	// are due until than time stamp.
    	long nextScanningTimestamp = globalTimestamp + scanningResolution;
    	TestLog.println("next scanning timestamp is " + nextScanningTimestamp);

    	// local time stamp variable that is used to collect stimulations
    	long timestamp = lastStimulationTimestamp;
    	TestLog.println("starting with timestamp " + timestamp + " from the last stimulation");

    	// list of stimulations
    	List<Pair<String, String>> timeStampStimulations = null;
    	do {
    		timestamp += 1;
    		timeStampStimulations = testStimulations.get(timestamp);
    	} while ((timestamp < nextScanningTimestamp) &&
    			 ((timeStampStimulations == null) || (timestamp < (globalTimestamp+1))));

    	// end of test
    	if (globalTimestamp > maxStimulationTimestamp + scanningResolution) return -1;

    	// sleep until time stamp is reached
    	TestLog.println("sleep for " + (timestamp - globalTimestamp) + "ms");
    	try {
    	    Thread.sleep(timestamp - globalTimestamp);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}

    	// calculate new global time stamp
    	lDateTime = new Date().getTime();
    	globalTimestamp = lDateTime - startOfTestTimeStamp;
    	TestLog.println("new global timestamp is " + globalTimestamp);

    	// return the new time stamp
    	return globalTimestamp;
    }

    public void logSUT2TE_Signal(String signal, String value) {
		
	}

	public void logTE2SUT_Signal(String signal, String value) {
		
	}

    public void getState(RemoteInterpreter interpreter) throws Exception {
    	if (!TestLog.isDebug()) {
    		return;
    	}
        // debug code
        ObjectValue state = (ObjectValue) interpreter.valueExecute("ifm.getstate()");
		TestLog.println("TestDriver: state dump {");
		for (NameValuePair m : state.getMemberValues().asList())
		{
			if (m.value instanceof FunctionValue ||
				m.value instanceof OperationValue)
			{
				continue;
			}
			TestLog.println(m.toString() + " ");
		}
		TestLog.println("}\n");
    }

	public void parseSignals(String inputFilePath) {

		try {
			// parse JSON input
			File inputFile = new File(inputFilePath);
			FileReader reader = new FileReader(inputFile);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reader);
			jsonSignals = (JSONArray) obj;
			TestLog.println("jsonSignals: " + jsonSignals.toJSONString());
		} catch (ParseException e) {
			System.err.println("*** error: unable to parse signals.json file!");
			return;
		} catch (FileNotFoundException e) {
			System.err.println("*** error: unable to open '" + inputFilePath + "'!");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
        // retrieve values per signal name
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> iterator = jsonSignals.iterator();
        while (iterator.hasNext()) {
        	JSONObject entry = iterator.next();
        	String name = (String) entry.get("name");
            JSONArray values = (JSONArray) entry.get("data");
        	// add values to map
            TestLog.println("adding stimulations for signal " + name + ": " + values.toJSONString());
        	@SuppressWarnings("unchecked")
        	Iterator<JSONArray> valueIterator = values.iterator();
        	while (valueIterator.hasNext()) {
        		JSONArray valuesEntry = (JSONArray) valueIterator.next();
        		Long timestamp = (Long) valuesEntry.get(0);
        		double value;
        		if (valuesEntry.get(1) instanceof Double) {
        			value = (double) valuesEntry.get(1);
        		} else {
        			Long longValue = (Long)valuesEntry.get(1);
        			value = longValue.doubleValue();
        		}
        		// debug output
                TestLog.println("adding stimulation for timestamp " + timestamp + ": " + value);
                Pair<String, String> signalValuePair = new Pair<String, String>(name, String.valueOf(value));
        		List<Pair<String, String>> stimList = testStimulations.get(timestamp);
        		if (stimList == null) {
        			stimList = new ArrayList<Pair<String, String>>();
        		}
        		stimList.add(signalValuePair);
        		testStimulations.put(timestamp, stimList);
        		// store latest stimulation timestamp
        		if (timestamp > maxStimulationTimestamp) maxStimulationTimestamp = timestamp;
        	}
        }
	}
	
	public void addLogEntry(long timestamp, String signal, String value) {
		// create long value from value
		if (value.compareToIgnoreCase("true") == 0) value = "1";
		if (value.compareToIgnoreCase("false") == 0) value = "0";
		TestLog.println("Long.parseLong(" + value + ");");
		Double replayValue = Double.parseDouble(value);
		TestLog.println("replayValue: " + replayValue);
		value = replayValue.toString();

		// create replay.log entry
		String entry = (timestamp/1000) + "." + (timestamp%1000) + ";" + signal + ";" + value + "\n";

		File file;
		try {
			// create/open replay.log
			file = new File("replay.log");
			if (!file.exists()) {
				file.createNewFile();
			}
			// append entry to file
			FileWriter writter = new FileWriter(file.getName(),true);
			BufferedWriter logfileWriter = new BufferedWriter(writter);
			logfileWriter.write(entry);
			logfileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

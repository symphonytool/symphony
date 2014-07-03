package eu.compassresearch.core.interpreter.remote.rttmbt.testdriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;
import eu.compassresearch.core.interpreter.remote.rttmbt.testdriver.TestLog.Pair;
		
// test driver from rtt-plugin SVN repository
public class CmlTestDriver implements IRemoteControl
{

	private JSONArray jsonSignals;
	private Map<Long,List<Pair<String, String>>> testStimulations = new HashMap<Long,List<Pair<String, String>>>();
	private long maxStimulationTimestamp = 0;
	private long lastStimulationTimestamp = -1;
	private long globalTimestamp = 0;

	//private String[] sampleTrace = {"tock", "tock", "setState.{<L1>, <L2>}", "switchOn.<L1>", "switchOn.<L2>", "tock", "tock", "tock", "setState.warning", "switchOff.<L2>", "switchOn.<L3>"};
	// attribute to store stimulation events for filtering
	// or do i want to perform the filtering on string compare?

	@Override
	public void run(IRemoteInterpreter interpreter) throws Exception
	{
		int traceIdx = 0;
		boolean rttTestRunning = true;
		String verdict = "PASS";
		Set<? extends CmlTransition> options;

		// prepare trace
		String channelSpec = "lib" + File.separator + "channels.json";
		String signalSpec = "lib" + File.separator + "signals.json";
		CmlTraceGenerator generator = new CmlTraceGenerator(signalSpec, channelSpec);
		List<String> trace = generator.getCmlTrace();

		// print header
		TestLog.printTestlogEntry(globalTimestamp, TestLog.header);

		while ((!interpreter.isFinished()) && (rttTestRunning)) {
			// get set of possible events in the current step
			options = interpreter.getEvents();
			if (!(options.isEmpty())) {
				CmlTransition event = null;
				String fromTrace = null;
				long timePassed = 0;
				boolean isTauOrTock = false;

				// log set of possible events:
				boolean hasTau = false;
				for (CmlTransition ev : options)
				{
					System.err.println("event: '" + ev.toString() + "'");
		            TestLog.println("event: '" + ev.toString() + "'");
		            if ((!hasTau) && (ev.toString().startsWith("tau"))) {
		            	hasTau = true;
		            }
				}
				System.err.println("hasTau: " + hasTau);

				// iterate through set of possible events
				for (CmlTransition ev : options)
				{
					// high priority for tau
					if ((hasTau) && (ev.toString().startsWith("tau"))) {
						System.err.println("select Tau event " + ev.toString());
						event = ev;
						fromTrace = "tau";
						isTauOrTock = true;
						break;
					}
					if ((hasTau) && (!(ev.toString().startsWith("tau")))) {
						System.err.println("skip non-Tau event " + ev.toString());
						continue;
					}
					// handle timed transitions
					if (ev instanceof TimedTransition) {
						if (ev.toString().compareTo(trace.get(traceIdx)) == 0) {
							System.err.println("select event #" + traceIdx + ": " + ev.toString() + " from sample trace");
							event = ev;
							fromTrace = trace.get(traceIdx);
							timePassed = 1;
							isTauOrTock = true;
							traceIdx++;
							if (traceIdx >= trace.size()) {
								rttTestRunning = false;
								verdict = "TESTERROR";
							}
							break;
						} else {
							System.err.println("skip timed transition " + ev.toString());
							continue;
						}
					}
					// select the event from the test trace
					if (!hasTau) {
						if (doEventsMatch(ev.toString(), trace.get(traceIdx))) {
							System.err.println("select event #" + traceIdx + ": " + ev.toString() + " from sample trace");
							event = ev;
							fromTrace = trace.get(traceIdx);
							traceIdx++;
							if (traceIdx >= trace.size()) {
								// no more events in test trace => terminate test
								rttTestRunning = false;
							}
							break;
						} else {
							System.err.println("event " + ev.toString() + " does not match event #" +
						                       traceIdx + ": " + trace.get(traceIdx) + " from sample trace");
						}
					}
				}

				// no valid event could be found for the next step
				if (event == null) {
					rttTestRunning = false;
					verdict = "FAIL";
					continue;
				}
				
				System.out.println("Selected: " + event + " (" + fromTrace + ")");
				if (!isTauOrTock) {
					TestLog.printTestlogEntry(globalTimestamp, fromTrace);
				}

				if (interpreter.getArgumentCount(event) > 0)
				{
					List<String> arguments = getEventArguments(fromTrace);
					if (interpreter.getArgumentCount(event) != arguments.size()) {
						System.err.println("*** error: event " + event.toString() + " has " +
					                       interpreter.getArgumentCount(event) + " arguments, trace event " +
					                       fromTrace + " has " + arguments.size() + " arguments");
						rttTestRunning = false;
						verdict = "TESTERROR";
						continue;
					}
					try {
					switch(interpreter.getArgumentCount(event)) {
					case 1:
						interpreter.select(event, arguments.get(0));
						break;
					case 2:
						interpreter.select(event, arguments.get(0), arguments.get(1));
						break;
					case 3:
						interpreter.select(event, arguments.get(0), arguments.get(1), arguments.get(2));
						break;
					case 4:
						interpreter.select(event, arguments.get(0), arguments.get(1), arguments.get(2), arguments.get(3));
						break;
					case 5:
						interpreter.select(event, arguments.get(0), arguments.get(1), arguments.get(2), arguments.get(3), arguments.get(4));
						break;
					default:
						break;
					}
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					interpreter.select(event);
				}

				// update global time stamp
				globalTimestamp += timePassed;
			}
		}
		System.err.println("************************************\n" +
		                   "Test Execution Verdict: " + verdict + 
		                   "\n************************************");
		TestLog.printTestlogEntry(globalTimestamp,
				                  TestLog.footer + "\n" +
				                  "                        * VERDICT : " + verdict + "\n" + 
				                  "                        *****************************************************************************");
	}

	/**
	 * @brief This function checks if an event that is offered by
	 *        the interpreter matches the event from the trace
	 *        events offered by the interpreter can be of the form
	 *        <channel_name>.? which matches every event starting with
	 *        <channel_name>
	 * 
	 * @param offered    the event offered by the interpreter
	 * @param fromTrace  the event from the test trace
	 * @return true if the events match, false otherwise.
	 */
	public boolean doEventsMatch(String offered, String fromTrace) {
		// check for exact match
		if (offered.toString().compareTo(fromTrace) == 0) {
			return true;
		}
		// check for match up to first "?"
		int pos = offered.indexOf("?");
		if (pos == -1) {
			System.err.println("no exact match and no '?' found in " + offered);
			return false;
		}
		if (fromTrace.startsWith(offered.substring(0, pos - 1))) {
			return true;
		} else {
			System.err.println(fromTrace + " does not start with " + offered.substring(0, pos - 1));
		}
		// events do not match
		return false;
	}

	/**
	 * @brief This function calculates the concrete input
	 *        parameters for events containing ?-patterns
	 * 
	 * @param fromTrace the event from the test trace
	 * @return A string list of arguments for the respective event
	 */
	public List<String> getEventArguments(String fromTrace) {
		List<String> arguments = new ArrayList<String>();

		// find '?'
		int pos = fromTrace.indexOf(".");
		if (pos == -1) {
			System.err.println("no '.' found in " + fromTrace);
			return arguments;
		}

		// collect arguments from the concrete event from the trace
		int parameter = 0;
		int last = pos;
		pos = fromTrace.indexOf(".", last + 1);
		while (pos != -1) {
			// extract the value
			String value = fromTrace.substring(last + 1, pos - 1);
			arguments.add(value);
			parameter++;
			System.err.println("added event value #" + parameter + ": " + value);

			last = pos;
			pos = fromTrace.indexOf(".", last + 1);
		}
		// add last argument (no training '.')
		String value = fromTrace.substring(last + 1);
		arguments.add(value);
		parameter++;
		System.err.println("added event value #" + parameter + ": " + value);

		return arguments;
	}
	

	/**
	 * @brief This function parses a signal.json file and
	 *        creates a map of test stimulations. This map
	 *        'testStimulations' maps a list of TE2SUT stimulaitons
	 *        to a time stamp. As a side effect, the largest
	 *        stimulation time stamp is stored in
	 *        'maxStimulationTimestamp'.
	 * 
	 * @param inputFilePath The full path name of the signals.json file
	 */
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

	/**
	 * @brief This function returns a list of stimulations for a given
	 *        time stamp. The 'lastStimulationTimestamp' attribute of the
	 *        test driver is used to collect all stimulation that are in the
	 *        'testStimulations' map between the last stimulation time stamp
	 *        and the current time stamp 'timestamp'
	 *        If there are no stimulations specified, an empty list is returned.
	 * @param timestamp the current time stamp
	 * @return the list of stimulations that are to be performed for this time stamp.
	 * 
	 * NOTE: i think that the last stimulation time stamp should not be used anymore, as we are using logical time and we will never miss a beat.
	 */
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
}

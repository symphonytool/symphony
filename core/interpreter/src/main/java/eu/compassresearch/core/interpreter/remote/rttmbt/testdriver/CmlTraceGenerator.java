package eu.compassresearch.core.interpreter.remote.rttmbt.testdriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CmlTraceGenerator {

	private class Pair<L,R> {
		private L left;
		private R right;
		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}
		public String toString() {
			return "(" + left.toString() + "," + right.toString() + ")";
		}
		public L getLeft() { return left; }
		public R getRight() { return right; }
	}

	// maps of signal data and channel interface information
	private Map<String,List<String>> channel2types;
	private Map<String,List<String>> enumTypes;
	private Map<String,Long> enumLiteralValues;
	private Map<String,List<Pair<Long,String>>> signalValues;

	// lists of all channels and signal names
	private List<String> channelList;
	private List<String> signalList;

	// map that assigns an event flag to each channel
	private Map<String,Integer> event_flags;

	// map that holds the latest value index per signal
	private Map<String,Long> value_index;
	
	public CmlTraceGenerator(String signalfile, String interfacefile) {

		// maps of signal data and channel interface information
		channel2types = new HashMap<String,List<String>>();
		enumTypes = new HashMap<String,List<String>>();
		enumLiteralValues = new HashMap<String,Long>();
		signalValues = new HashMap<String,List<Pair<Long,String>>>();

		// lists of all channels and signal names
		channelList = new ArrayList<String>();
		signalList = new ArrayList<String>();

		// map that assigns an event flag to each channel
		event_flags = new HashMap<String,Integer>();

		// map that holds the latest value index per signal
		value_index = new HashMap<String,Long>();

		JSONParser parser = new JSONParser();

		
		
		
		// parse interface description JSON file
		File inputFile = new File(interfacefile);
		FileReader reader;
		try {
			reader = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			System.err.println("*** error: unable to open input file '" + interfacefile + "'!");
			return;
		}
		JSONObject metadata = null;
		Object obj = null;
		try {
			obj = parser.parse(reader);
		} catch (ParseException e) {
			System.err.println("*** error: unable to parse interface meta data from '" + interfacefile + "'!");
			return;
		} catch (IOException e) {
			System.err.println("*** error: reading input file '" + interfacefile + "' failed!");
			return;
		}
		metadata = (JSONObject)obj;

		// get channel information
		JSONArray channels = (JSONArray)metadata.get("channels");
		if (channels == null) {
			System.err.println("*** error: error reading channel entries");
		} else {
			for (int cidx = 0; cidx < channels.size(); cidx++) {
				JSONObject channel = (JSONObject)channels.get(cidx);
				if (channel == null) {
					System.err.println("*** error: error reading channel entry #" + (cidx+1));
					continue;
				}
				String name = (String)channel.get("name");
				Long numArgs = (Long)channel.get("numberOfArguments");
				JSONArray types = (JSONArray)channel.get("types");
				if (name == null || numArgs == null || types == null) {
					System.err.println("*** error: error receiving information from channel entry #" + (cidx+1));
					continue;
				}
				if (types.size() != numArgs) {
					System.err.println("*** error: unexpected number of type names for channel " + name + "! Expected " + numArgs + ", found " + types.size());
					continue;
				}
				List<String> typeList = new ArrayList<String>();
				for (int tidx = 0; tidx < types.size(); tidx++) {
					typeList.add((String)types.get(tidx));
					System.err.println("added type '" + (String)types.get(tidx) + "' for parameter #" + (tidx+1) + " of channel '" + name + "'.");
				}
				channel2types.put(name, typeList);
				channelList.add(name);
			}
		}

		// get enum type information
		JSONArray enums = (JSONArray)metadata.get("enumTypes");
		if (enums == null) {
			System.err.println("*** error: error reading enum type entries");
		} else {
			for (int eidx = 0; eidx < enums.size(); eidx++) {
				JSONObject enumType = (JSONObject)enums.get(eidx);
				if (enumType == null) {
					System.err.println("*** error: error reading channel entry #" + (eidx+1));
					continue;
				}
				String name = (String)enumType.get("name");
				JSONArray values = (JSONArray)enumType.get("values");
				if (name == null || values == null) {
					System.err.println("*** error: error receiving information from enum type entry #" + (eidx+1));
					continue;
				}
				List<String> literals = new ArrayList<String>();
				for (int lidx = 0; lidx < values.size(); lidx++) {
					JSONArray value = (JSONArray)values.get(lidx);
					literals.add((String)value.get(0));
					String literal = name + "." + (String)value.get(0);
					enumLiteralValues.put(literal, (Long)value.get(1));
					System.err.println("added literal '" + (String)value.get(0) + "' (actually '" + literal + "') with value " + (Long)value.get(1) + " to enum type '" + name + "'.");
				}
				enumTypes.put(name, literals);
			}
		}

		// parse JSON signal file input
		// parse signals.json
		inputFile = new File(signalfile);
		try {
			reader = new FileReader(inputFile);
		} catch (FileNotFoundException e1) {
			System.err.println("*** error: unable to open input file '" + signalfile + "'!");
			return;
		}
		JSONArray signaldata = null;
		Object jsonsignals = null;
		try {
			jsonsignals = parser.parse(reader);
		} catch (ParseException e) {
			System.err.println("*** error: unable to parse interface meta data from '" + signalfile + "'!");
			return;
		} catch (IOException e) {
			System.err.println("*** error: reading input file '" + signalfile + "' failed!");
			return;
		}
		signaldata = (JSONArray)jsonsignals;
		for (int sidx=0; sidx < signaldata.size(); sidx++) {
			JSONObject signalentry = (JSONObject)signaldata.get(sidx);
			String name = (String)signalentry.get("name");
			JSONArray data = (JSONArray)signalentry.get("data");
			if (name == null || data == null) {
				System.err.println("*** error: error receiving information from signal entry #" + (sidx+1));
				continue;
			}
			List<Pair<Long,String>> values = new ArrayList<Pair<Long,String>>();
			String oldValue = "";
			for (int didx = 0; didx < data.size(); didx++) {
				JSONArray entry = (JSONArray)data.get(didx);
				Long timestamp = (Long)entry.get(0);
				String value = null;
				if (entry.get(0) instanceof Long) {
					Long longvalue = (Long)entry.get(1);
					value = longvalue.toString();
				} else {
					value = (String)entry.get(1);
				}
				if (value.compareTo(oldValue) != 0) {
					values.add(new Pair<Long,String>(timestamp, value));
					oldValue = value;
					System.err.println("added signal value '(" + timestamp + "," + value + ")' for signal '" + name + "'.");
				} else {
					System.err.println("skip old signal value '(" + timestamp + "," + value + ")' for signal '" + name + "'.");
				}
			}
			signalValues.put(name, values);
			signalList.add(name);
		}

		// initialize maps
		for (int idx = 0; idx < channelList.size(); idx++) {
			String name = channelList.get(idx);
			event_flags.put("m_" + name, 0);
		}
		for (int idx = 0; idx < signalList.size(); idx++) {
			String name = signalList.get(idx);
			value_index.put(name, (long) 0);
		}
}

/**
 * calculate CML trace like
 * [tock, tock, setState.{<L1>, <L2>}, switchOn.<L1>, switchOn.<L2>, tock, tock, tock, setState.{<L1>, <L3>}, switchOff.<L2>, switchOn.<L3>]
 * from signals.json file of alpha_*, beta_*, m_<channel>, X/Y_<channel>_par_* values
 * requires:
 * - mapping of channel names to meta-info objects
 * - meta-info objects containing information generated from cml-interface.json:
 *   - channel name
 *   - number of arguments
 *   - list of types (of arguments)
 *   - for literals, the type values are pairs ["<literal>",value]
 */

/**
 * sample for signals.json:
 * [{"name":"alpha_channel", "type":"signed int", "data": [[0,-1], [2,0], [3,1], [4,0]]},
 *  {"name":"alpha_flag", "type":"signed int", "data": [[0,0], [2,1], [4,0]]},
 *  {"name":"beta_channel", "type":"signed int", "data": [[0,-1], [2,0], [3,1], [4,0]]},
 *  {"name":"beta_flag", "type":"signed int", "data": [[0,0], [2,1], [4,0]]},
 *  {"name":"m_setState", "type":"signed int", "data": [[0,0], [2,1], [4,0]]},
 *  {"name":"m_switchOff", "type":"signed int", "data": [[0,0]]},
 *  {"name":"m_switchOn", "type":"signed int", "data": [[0,0],[3,1], [4,0]]},
 *  {"name":"X_setState_p1", "type":"signed int", "data": [[0,0], [1,1], [2,2]]},
 *  {"name":"Y_switchOff_p1", "type":"signed int", "data": [[0,0]]},
 *  {"name":"Y_switchOn_p1", "type":"signed int", "data": [[0,0], [3,2], [4,1]]}]
 */

/**
 * sample for cml-interface.json:
 * {"channels":[{"name":"setState","numberOfArguments":1,"types":["States_t"]},
 *              {"name":"switchOn","numberOfArguments":1,"types":["Lamps_t"]},
 *              {"name":"switchOff","numberOfArguments":1,"types":["Lamps_t"]}],
 *  "enumTypes":[{"name":"States_t","values":[["dark",0],["stop",1],["warning",2],["drive",3]]},
 *               {"name":"Lamps_t","values":[["<L1>",0]["<L2>",1]["<L3>",2]]}]
 */

	public List<String> getCmlTrace() {
		List<String> trace = new ArrayList<String>();

		// go through signal map and look for event changes
		long timestamp = 0;
		String beta_channel_string = "";
		long beta_channel = 0;
		String beta_channel_name = "";
		String flag_string = "";
		int beta_flag = 0;
		int m_flag = 0;
		boolean signalsAvailable = true;
		while (signalsAvailable) {
			// get signal values for the current time stamp
			beta_channel_string = getSignalValue("beta_channel", timestamp);
			beta_channel = Long.parseLong(beta_channel_string);
			// special handling of initial event (rttMbtCmlEventBottom == -1)
			if (beta_channel == -1) {
				// prepare next loop
				timestamp = getNextTimstamp();
				signalsAvailable = (timestamp != -1);
				continue;
			}
			beta_channel_name = getChannelName(beta_channel);
			flag_string = getSignalValue("beta_flag", timestamp);
			beta_flag = Integer.parseInt(flag_string);
			m_flag = event_flags.get("m_" + beta_channel_name);

			// check if event is selected by the environment
			if (beta_flag == 1 - m_flag) {
				String event = beta_channel_name;

				// get number of event types
				List<String> types = channel2types.get(beta_channel_name);

				// construct CML event
				for (int idx = 0; idx < types.size(); idx++) {
					String value = getSignalValue(beta_channel_name + "_p" + (idx + 1), timestamp);
					if (value == null) {
						System.err.println("*** error: unable to find channel parameter signal '" + beta_channel_name + "_p" + (idx + 1) + "'");
						return null;
					}

					// check for enum values and replace them by their literals
					String type = types.get(idx);
					List<String> enums = enumTypes.get(type);
					if (enums != null) {
						long eventValue = Long.parseLong(value);
						for (int eidx = 0; eidx < enums.size(); eidx++) {
							String literal = type + "." + enums.get(eidx);
							long literalValue = enumLiteralValues.get(literal);
							if (literalValue == eventValue) {
								value = enums.get(eidx);
								break;
							}
						}
					}

					// add value to event
					event = event + "." + value;
				}

				// add event to trace
				System.err.println("added event '" + event + "' to trace with timestamp " + timestamp);
				trace.add(event);

				// update m_flag
				event_flags.put("m_" + beta_channel_name, beta_flag);
			}

			// prepare next loop
			long oldTimestamp = timestamp;
			timestamp = getNextTimstamp();
			signalsAvailable = ((timestamp != -1) && (timestamp != oldTimestamp));
			// generate tock events
			// !TODO
		}

		return trace;
	}

	public String getSignalValue(String name, long timestamp) {
		Pair<Long,String> entry = null;
		if (value_index.get(name) == null) {
			return null;
		}
		long lastIndex = value_index.get(name);
		List<Pair<Long,String>> values = signalValues.get(name);
		if (values == null) {
			return null;
		}
		// check for illegal index
		if (values.size() <= lastIndex) {
			return null;
		}
		// if the last entry is reached: return it's value for all following time stamps
		if (values.size() == (lastIndex + 1)) {
			entry = values.get((int)lastIndex);
			return entry.getRight();
		}
		// check the time stamp of the next entry
		entry = values.get((int)(lastIndex + 1));
		while (entry.getLeft() <= timestamp) {
			// as long as the next index is still less or equal to the current
			// time stamp, increase the index. Note: this way we will skip
			// unused value changes.
			lastIndex++;
			if ((lastIndex + 1) < values.size()) {
				entry = values.get((int)(lastIndex + 1));
			} else {
				// if there is no next index to check, the current one is used.
				break;
			}
		}
		// now get the entry of the last index for this time stamp again
		entry = values.get((int)lastIndex);
		value_index.put(name, lastIndex);
		return entry.getRight();
	}

	public long getNextTimstamp() {
		boolean channelIndexChanged = false;
		boolean flagIndexChanged = false;
		Pair<Long,String> channelEntry = null;
		Pair<Long,String> flagEntry = null;
		long lastIndexChannel = value_index.get("beta_channel");
		long lastIndexFlag = value_index.get("beta_flag");
		List<Pair<Long,String>> channelValues = signalValues.get("beta_channel");
		List<Pair<Long,String>> flagValues = signalValues.get("beta_flag");
		if ((channelValues == null) || (flagValues == null)) {
			System.err.println("*** error: no channel values or flag values available!");
			return -1;
		}
		// if the last entry is reached: return -1
		if ((channelValues.size() == (lastIndexChannel + 1)) &&
		    (flagValues.size() == (lastIndexFlag + 1))) {
			System.err.println("channel and flag have both reached their last value - no more events possible!");
			return -1;
		}
		long newIndexChannel;
		long newIndexFlag;
		if (channelValues.size() == (lastIndexChannel + 1)) {
			newIndexChannel = lastIndexChannel;
		} else {
			newIndexChannel = lastIndexChannel + 1;
			channelIndexChanged = true;
		}
		if (flagValues.size() == (lastIndexFlag + 1)) {
			newIndexFlag = lastIndexFlag;
		} else {
			newIndexFlag = lastIndexFlag + 1;
			flagIndexChanged = true;
		}
		// check the time stamp of the next entry
		channelEntry = channelValues.get((int)newIndexChannel);
		flagEntry = flagValues.get((int)newIndexFlag);
		long timestampChannel = channelEntry.getLeft();
		long timestampFlag = flagEntry.getLeft();
		// return the lowest new time stamp
		if ((timestampChannel > timestampFlag) && (flagIndexChanged)) {
			// the time stamp for beta_flag is lower than for beta_channel and is new => use time stamp for beta_flag
			value_index.put("beta_flag", newIndexFlag);
			return timestampFlag;
		} else if ((timestampChannel <= timestampFlag) && (channelIndexChanged)) {
			// the time stamp for beta_channel is lower than for beta_flag and is new => use time stamp for beta_channel
			value_index.put("beta_channel", newIndexChannel);
			return timestampChannel;
		} else if ((timestampChannel <= timestampFlag) && (flagIndexChanged)) {
			// the time stamp for beta_channel is lower than for beta_flag but beta_flag is new => use time stamp for beta_flag
			value_index.put("beta_flag", newIndexFlag);
			return timestampFlag;
		} else {
			// the time stamp for beta_flag is lower than for beta_channel but beta_channel is new => use time stamp for beta_channel
			value_index.put("beta_channel", newIndexChannel);
			return timestampChannel;
		}
	}

	public String getChannelName(long enumValue) {
		String name = null;

		for (int idx = 0; idx < channelList.size(); idx++) {
			name = channelList.get(idx);
			String literal = "channels." + name;
			Long value = enumLiteralValues.get(literal);
			if (value == null) {
				System.err.println("*** error: unable to find enum literal value for 'channels." + name + "'");
				continue;
			}
			if (value == enumValue) {
				return name;
			}
		}

		System.err.println("*** error: unable to find channel name for enum literal value " + enumValue);
		return null;
	}
}

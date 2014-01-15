package eu.compassresearch.core.interpreter.cosim;

import java.io.IOException;
import java.util.Arrays;

import eu.compassresearch.core.interpreter.cosim.communication.Utils;

public class TestMain
{

	public static void main(String[] args) throws IOException
	{
		if (args[0].equals("client"))
		{
			System.out.println("Starting client");
			CoSimulationClient client = new CoSimulationClient("localhost", 8088);
			client.connect();
			client.registerImplementation("A", "P");
		} else if (args[0].equals("server"))
		{
			System.out.println("Starting server");
			CoSimulationServer server = new CoSimulationServer(Arrays.asList(new String[] {}), 8088);
			server.listen();
			server.waitForClients();
			Utils.milliPause(30000);
		}
		System.out.println("Terminating");

	}

}

package SNCUint;

import SNCUint.Clients.Client;
import SNCUint.NATInnerServer.NATServer;
import SNCUint.PublicServer.ServerClass;

import static SNCUint.CommonData.*;

public class MainEntry {

	static final String INPUT_ERROR = "ERROR: Wrong Parameter Input";
	static final String INPUT_TIP_NAT_GUI = "-n gui";
	static final String INPUT_TIP_NAT_CLI =
			"-n tcp|udp(string) inner-port(int) nat-port(int) inner-server-port(int) key(8 chars string) address(public server)";
	static final String INPUT_TIP_CNT_GUI = "-c gui";
	static final String INPUT_TIP_CNT_CLI = "-c key address(public server)";
	static final String INPUT_TIP_SER = "-s";

	static void printError()
	{
		System.out.println(INPUT_ERROR);
	}

	static void printHelp_NATServer()
	{
		System.out.println("Run As a NAT Server:");
		System.out.println("\t"+INPUT_TIP_NAT_GUI);
		System.out.println("or");
		System.out.println("\t"+INPUT_TIP_NAT_CLI);
	}

	static void printHelp_PublicServer()
	{
		System.out.println("Run As a Public Server:");
		System.out.println("\t"+INPUT_TIP_SER);
	}

	static void printHelp_Client()
	{
		System.out.println("Run As a Client:");
		System.out.println("\t"+INPUT_TIP_CNT_GUI);
		System.out.println("or");
		System.out.println("\t"+INPUT_TIP_CNT_CLI);
	}

	public static void main(String[] args)
	{

		if (args.length<1)
		{
			System.out.println(INPUT_ERROR);
			return;
		}

		switch (args[0]) {
			case "-h":
			case "-help":
				printHelp_PublicServer();
				System.out.println();
				printHelp_NATServer();
				System.out.println();
				printHelp_Client();
				System.out.println();
				break;
			case "-s":
				new ServerClass();
				break;
			case "-n":
				//				  0 (      1    )  2    		3		4
				// java -jar xxx -n [gui| tcp/udp inner-port nat-port key]

				if (args.length < 2) {
					printError();
					printHelp_NATServer();
					return;
				}

				if (args[1].equals("gui")) {
					new NATServer();
				} else {
					if (args.length < 7) {
						printError();
						printHelp_NATServer();
						return;
					}

					int innerPort;
					try {
						innerPort = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						printError();
						printHelp_NATServer();
						return;
					}

					int natPort;
					try {
						natPort = Integer.parseInt(args[3]);
					} catch (NumberFormatException e) {
						printError();
						printHelp_NATServer();
						return;
					}

					int innerSerPort;
					try {
						innerSerPort = Integer.parseInt(args[4]);
					} catch (NumberFormatException e) {
						printError();
						printHelp_NATServer();
						return;
					}

					if (args[5].length() != 8) {
						printError();
						printHelp_NATServer();
						return;
					}

					if (args[1].toLowerCase().equals("tcp")) {
						new NATServer(SOCKET_TYPE_TCP, innerPort, innerSerPort, innerSerPort, args[5], args[6]);
					} else if (args[1].toLowerCase().equals("udp")) {
						new NATServer(SOCKET_TYPE_UDP, innerPort, innerSerPort, innerSerPort, args[5], args[6]);
					} else {
						printError();
						printHelp_NATServer();
					}
				}
				break;
			case "-c":
				if (args.length < 2) {
					printError();
					printHelp_Client();
					return;
				}

				if (args[1].equals("gui")) {
					new Client();
				} else {

					if (args.length < 3) {
					printError();
					printHelp_Client();
					return;
					}

					if (args[1].length() != 8) {
						printError();
						printHelp_Client();
					}
					new Client(args[1], args[2]);
				}
				break;
		}
	}
}

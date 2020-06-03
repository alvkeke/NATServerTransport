package SNCUint.PublicServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static SNCUint.CommonData.PORT_PUBLIC_SERVER;

public class ServerClass implements ConnectionCallback{

	private ServerSocket sCommon;
	private HashMap<String, InetSocketAddress> udpSer;
	private HashMap<String, InetSocketAddress> tcpSer;

	public ServerClass()
	{

		udpSer = new HashMap<>();
		tcpSer = new HashMap<>();

		try {
			sCommon = new ServerSocket(PORT_PUBLIC_SERVER);
			System.out.println("Initialized the Center Server Socket.");
			System.out.println("Started Listened.");
			while(true) {
				Socket sClient = sCommon.accept();
				System.out.println("Got a Connection.");
				new Thread(new ConnectionHandler(this, sClient)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addUDPServer(String key, InetSocketAddress address) {
		udpSer.put(key, address);
	}

	@Override
	public void addTCPServer(String key, InetSocketAddress address) {
		tcpSer.put(key, address);
	}

	@Override
	public InetSocketAddress getUDPServer(String key) {
		return udpSer.get(key);
	}

	@Override
	public InetSocketAddress getTCPServer(String key) {
		return tcpSer.get(key);
	}


}

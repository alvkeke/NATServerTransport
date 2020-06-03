package SNCUint.NATInnerServer;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import static SNCUint.CommonData.*;

public class NATServer {

	public NATServer()
	{
		// todo: 完成图形界面
		System.out.println("Sorry, this function is not be finished.\n");
	}

	public NATServer(byte soType, int innerPort, int natPort, int innerSerPort, String key, String sIP)
	{

		if (key==null)
		{
			System.out.println("Key Input Error.");
			return;
		}

		if(soType == SOCKET_TYPE_TCP)
		{
			new Thread(new TCPConnectHandleThread(innerPort, innerSerPort)).start();
		}
		else if(soType == SOCKET_TYPE_UDP)
		{
			new Thread(new UDPConnectHandleThread(innerPort, innerSerPort)).start();
		}
		else
		{
			System.out.println("Socket Type Input Error");
			return;
		}


		try {
			InetAddress ip = InetAddress.getByName(sIP);
			InetSocketAddress publicServerAddr = new InetSocketAddress(ip, PORT_PUBLIC_SERVER);


			new Thread(new RegisterServerThread(publicServerAddr, soType, natPort, key)).start();

		} catch (UnknownHostException e) {
			System.out.println("Public Server Host not Found");
		}


//		while(true);
		while (true)
		{
			try {
				Thread.sleep(1000000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

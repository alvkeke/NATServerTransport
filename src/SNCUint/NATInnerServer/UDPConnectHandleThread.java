package SNCUint.NATInnerServer;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class UDPConnectHandleThread implements Runnable {

	private int mPort;
	private int mSerPort;
	private DatagramSocket mSocket;
	private InetSocketAddress mInnerSerAddr;
	private HashMap<InetSocketAddress, DatagramSocket> mTransSoList;
	private HashMap<DatagramSocket, InetSocketAddress> mClientAddrList;
	boolean isNormal;

	UDPConnectHandleThread(int innerPort, int innerSerPort)
	{
		isNormal = true;

		mPort = innerPort;
		mSerPort = innerSerPort;

		try {
			mInnerSerAddr = new InetSocketAddress(InetAddress.getLocalHost(), innerSerPort);
		} catch (UnknownHostException e) {
			isNormal = false;
			e.printStackTrace();
		}

		mTransSoList = new HashMap<>();
		mClientAddrList = new HashMap<>();
	}

	@Override
	public void run() {


		try {
			mSocket = new DatagramSocket(mPort);
			System.out.println("Started to receive client connection.");

			while(isNormal)
			{
				try {
					byte[] buf = new byte[1024];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					mSocket.receive(packet);

					InetSocketAddress addr = (InetSocketAddress) packet.getSocketAddress();

					DatagramSocket sTrans = mTransSoList.get(addr);

					if(sTrans!=null)
					{
						packet.setSocketAddress(mInnerSerAddr);
						sTrans.send(packet);
					}
					else
					{
						System.out.println("Got a New Connection.");
						DatagramSocket sTransNew = new DatagramSocket();
						new Thread(new UDPTransThread(mSocket, sTransNew, (InetSocketAddress) packet.getSocketAddress())).start();
						System.out.println("Started the transport Service.\n");
						mTransSoList.put(addr, sTransNew);
						packet.setSocketAddress(mInnerSerAddr);
						sTransNew.send(packet);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}


	}
}

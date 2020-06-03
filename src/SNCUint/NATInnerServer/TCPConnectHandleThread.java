package SNCUint.NATInnerServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConnectHandleThread implements Runnable {

	int mPort;
	int mInnerSerPort;
	ServerSocket mSocket;

	TCPConnectHandleThread(int innerPort, int innerSerPort)
	{
		mPort = innerPort;
		mInnerSerPort = innerSerPort;
	}

	@Override
	public void run() {

		try {

			mSocket = new ServerSocket(mPort);
			System.out.println("Started to receive client connection.");

			while(!mSocket.isClosed())
			{
				Socket client = mSocket.accept();
				System.out.println("Got a New Connection.");
				Socket sTrans = new Socket();
				sTrans.connect(new InetSocketAddress(InetAddress.getLocalHost(), mInnerSerPort));

				new Thread(new TCPTransThread(client, sTrans)).start();
				new Thread(new TCPTransThread(sTrans, client)).start();

				System.out.println("Started the transport Service.\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}

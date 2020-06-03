package SNCUint.NATInnerServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RegisterServerThread implements Runnable {

	InetSocketAddress mPublicServerAddr;
	byte mSoType;
	int mNatPort;
	String mKey;

	RegisterServerThread(InetSocketAddress publicServerAddr, byte soType, int natPort, String key)
	{
		mPublicServerAddr = publicServerAddr;
		mSoType = soType;
		mNatPort = natPort;
		mKey = key;
	}

	@Override
	public void run() {
		Socket socket = new Socket();

		try {
			socket.connect(mPublicServerAddr);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

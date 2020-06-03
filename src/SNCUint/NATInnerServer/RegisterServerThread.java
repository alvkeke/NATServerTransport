package SNCUint.NATInnerServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static SNCUint.CommonData.*;

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
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeByte(CONNECTION_TYPE_SERVER);
			dos.writeByte(mSoType);
			dos.writeInt(mNatPort);
			dos.writeBytes(mKey);

			dos.flush();
			dos.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

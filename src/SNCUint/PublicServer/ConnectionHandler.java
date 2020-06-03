package SNCUint.PublicServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import static SNCUint.CommonData.*;

public class ConnectionHandler implements Runnable {

	private ConnectionCallback mCallback = null;
	private Socket mClient = null;

	ConnectionHandler(ConnectionCallback callback, Socket client)
	{
		mCallback = callback;
		mClient = client;
	}

	@Override
	public void run() {

		if(mClient == null) return;

		try {
			mClient.setSoTimeout(5000);
			System.out.println("Started data handle thread.");
			DataInputStream dis = new DataInputStream(mClient.getInputStream());
			DataOutputStream dos = new DataOutputStream(mClient.getOutputStream());

			byte conType = dis.readByte();

			if (conType == CONNECTION_TYPE_SERVER) {

				byte soType = dis.readByte();
				int soPort = dis.readInt();

				InetAddress ipAddr = ((InetSocketAddress) mClient.getRemoteSocketAddress()).getAddress();
				InetSocketAddress socketAddr = new InetSocketAddress(ipAddr, soPort);

				byte[] bKey = new byte[8];

				for (int i = 0; i < 8; i++) {
					bKey[i] = dis.readByte();
				}

				String sKey = new String(bKey);
				System.out.println(sKey);

				if (soType == SOCKET_TYPE_TCP) {
					mCallback.addTCPServer(sKey, socketAddr);
				} else if (soType == SOCKET_TYPE_UDP) {
					mCallback.addUDPServer(sKey, socketAddr);
				}
			} else if (conType == CONNECTION_TYPE_CLIENT) {


				byte soType = dis.readByte();
				byte[] bKey = new byte[8];
				for (int i = 0; i < 8; i++) {
					bKey[i] = dis.readByte();
				}

				String sKey = new String(bKey);
				System.out.println(sKey);

				InetSocketAddress address = null;
				if (soType == SOCKET_TYPE_UDP) {
					address = mCallback.getUDPServer(sKey);
				} else if (soType == SOCKET_TYPE_TCP) {
					address = mCallback.getTCPServer(sKey);
				}

				if (address != null) {
					dos.writeInt(address.getPort());
					dos.writeBytes(address.getAddress().getHostAddress());
					System.out.println(address.getAddress().getHostAddress());
				}
				dos.flush();

			}

			Thread.sleep(3000);
			dos.close();
			dis.close();
			mClient.close();
			System.out.println("Client Closed the Connection.");

		}
		catch (SocketTimeoutException e)
		{
			try {
				mClient.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		} catch (IOException | InterruptedException e) {
			try {
				mClient.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}

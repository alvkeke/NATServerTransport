package SNCUint.NATInnerServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPTransThread implements Runnable {

	DatagramSocket mSoMain;
	DatagramSocket mSoTrans;
	InetSocketAddress mClientAddr;

	UDPTransThread(DatagramSocket mainSo, DatagramSocket transSo, InetSocketAddress clientAddr)
	{
		mSoMain = mainSo;
		mSoTrans = transSo;
		mClientAddr = clientAddr;

	}

	@Override
	public void run() {
		while(true)
		{
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				mSoTrans.receive(packet);
				packet.setSocketAddress(mClientAddr);
				mSoMain.send(packet);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

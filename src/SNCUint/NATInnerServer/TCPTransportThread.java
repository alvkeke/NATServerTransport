package SNCUint.NATInnerServer;

public class TCPTransportThread implements Runnable {

	int mPort;
	int mSerPort;

	TCPTransportThread(int innerPort, int innerSerPort)
	{
		mPort = innerPort;
		mSerPort = innerSerPort;
	}

	@Override
	public void run() {

	}
}

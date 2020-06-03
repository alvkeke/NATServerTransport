package SNCUint.NATInnerServer;

public class UDPTransportThread implements Runnable {

	int mPort;
	int mSerPort;

	UDPTransportThread(int innerPort, int innerSerPort)
	{
		mPort = innerPort;
		mSerPort = innerSerPort;
	}

	@Override
	public void run() {

	}
}

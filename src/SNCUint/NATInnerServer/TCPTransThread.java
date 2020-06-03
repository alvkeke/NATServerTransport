package SNCUint.NATInnerServer;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.Socket;

public class TCPTransThread implements Runnable {

	Socket mSoIn;
	Socket mSoOut;
	InputStreamReader mIsr;
	OutputStreamWriter mOsw;

	boolean isNormal;

	TCPTransThread(Socket sIn, Socket sOut)
	{
		isNormal = true;

		mSoIn = sIn;
		mSoOut = sOut;

		try {
			mIsr = new InputStreamReader(mSoIn.getInputStream());
			mOsw = new OutputStreamWriter(mSoOut.getOutputStream());

		} catch (IOException e) {
			isNormal = false;
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (isNormal)
		{
			try {

				char[] buf = new char[1024];

				if (mSoOut.isClosed() || mSoIn.isClosed())
				{
					return;
				}

				if(mIsr.read(buf) < 0)
				{
					mIsr.close();
					mOsw.close();
					mSoIn.close();
					mSoOut.close();
					return;
				}

				mOsw.write(buf);
				mOsw.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

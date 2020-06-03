package SNCUint.PublicServer;

import java.net.InetSocketAddress;

public interface ConnectionCallback {
	void addUDPServer(String key, InetSocketAddress address);
	void addTCPServer(String key, InetSocketAddress address);

	InetSocketAddress getUDPServer(String key);
	InetSocketAddress getTCPServer(String key);

}

package com.demo.xmppchat.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XmppTool {

	// private static final String server_ip = "10.0.2.2";
	// private static final int server_port = 5222;
	
	private static XMPPConnection con = null;
	
	private static void openConnection() {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(ServerInfo.getHost(), ServerInfo.getPort());
			con = new XMPPConnection(connConfig);
			con.connect();
		} catch (XMPPException xe) {
			xe.printStackTrace();
		}
	}

	public static XMPPConnection getConnection() {
		if (con == null) {
			openConnection();
		}
		return con;
	}

	public static void closeConnection() {
		con.disconnect();
		con = null;
	}
}

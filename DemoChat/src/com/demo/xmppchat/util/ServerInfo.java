package com.demo.xmppchat.util;

public class ServerInfo {
	public static int defaultServer = 2;

	public static final String facebook_host = "chat.facebook.com";
	public static final int facebook_port = 5222;
	public static final String facebook_name = "facebook.com";

	public static final String gtalk_host = "talk.google.com";
	public static final int gtalk_port = 5222;
	public static final String gtalk_name = "gmail.com";

	public static String local_host = "10.0.2.2";
	public static int local_port = 5222;
	public static String local_name = "cuongnm92-pc.com";

	public void setDefaultServer(int server) {
		defaultServer = server;
	}

	public int getDefaultServer() {
		return defaultServer;
	}

	public static String getHost() {
		String host = null;

		switch (defaultServer) {
		case 0:
			host = facebook_host;
			break;
		case 1:
			host = gtalk_host;
		case 2:
			host = local_host;
			break;
		default:
			break;
		}

		return host;
	}

	public static int getPort() {
		int port = 5222;

		switch (defaultServer) {
		case 0:
			port = facebook_port;
			break;
		case 1:
			port = gtalk_port;
		case 2:
			port = local_port;
			break;
		default:
			break;
		}

		return port;
	}

	public static String getName() {
		String name = null;

		switch (defaultServer) {
		case 0:
			name = facebook_name;
			break;
		case 1:
			name = gtalk_name;
		case 2:
			name = local_name;
			break;
		default:
			break;
		}

		return name;
	}

	public static void setLocalHost(String ip) {
		local_host = ip;
	}
}

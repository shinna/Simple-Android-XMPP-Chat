package com.demo.xmppchat.service;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import com.demo.xmppchat.util.ServerInfo;

import android.content.Context;
import android.util.Log;

public class ServerConnection {

	public static final String TEST = "ServerConnection";

	private static XMPPConnection connection = null;
	private Chat chat = null;
	private boolean isRuning;

	private Context context;

	public ServerConnection(Context con) {
		context = con;
	}

	public void openConnection() {
		this.isRuning = true;

		if (connection != null) {
			try {
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						ServerInfo.getHost(), ServerInfo.getPort(),
						ServerInfo.getName());

				connection = new XMPPConnection(connConfig);

			} catch (Exception e) {
				Log.d(TEST, "connection failed :" + e.toString());
			}

			try {
				connection.connect();

			} catch (XMPPException ex) {
			}
		}
	}

	public static XMPPConnection getConnection() {
		return connection;
	}

	public void closeConnection() {
		connection.disconnect();
		connection = null;
		this.isRuning = false;
	}

	public void login(String user, String pass) {
		try {
			connection.login(user, pass);
			Log.d(TEST, "Login ok");
		} catch (XMPPException e) {
			Log.d(TEST, "Login failed :" + e.toString());
		}
	}

	public boolean sendMessage(String msg) {
		if (this.isRuning && msg != null) {
			try {
				Log.d(TEST, "XMPP / sendMessage:" + msg);
				chat.sendMessage(msg);
				Log.d(TEST, "XMPP / sendMessage: SUCCESS");
			} catch (XMPPException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		Log.d(TEST, "XMPP / isRuning false");
		return false;
	}
}

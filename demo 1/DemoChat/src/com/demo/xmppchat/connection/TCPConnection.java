package com.demo.xmppchat.connection;

import java.io.*;
import java.net.*;

import com.demo.xmppchat.util.ServerInfo;

public class TCPConnection {
	private static Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public TCPConnection() {
		if (socket == null) {
			try {
				socket = new Socket(ServerInfo.getHost(), ServerInfo.getPort());
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}
		}
	}
	
	public void closeConnection() throws IOException {
		 out.close();
	     in.close();
	     socket.close();
	     socket = null;
	}
	
	public void sendToServer(String message) {
		out.write(message);
	}
	
	public BufferedReader getInStream() {
		return in;
	}
}

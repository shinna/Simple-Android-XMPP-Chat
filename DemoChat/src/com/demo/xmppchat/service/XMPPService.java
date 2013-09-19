package com.demo.xmppchat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class XMPPService extends Service {

	public static final String TEST = "XMPPService";

	private Context context;
	private ServerConnection connection;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		context = getApplicationContext();
		connection = new ServerConnection(context);
		connection.openConnection();
		Log.d(TEST, "connection ok");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
	}

	@Override
	public void onDestroy() {
		connection.closeConnection();
	}

}

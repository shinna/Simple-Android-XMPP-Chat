package com.demo.xmppchat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
	    Intent service = new Intent(context, ServerConnection.class);
	}

}

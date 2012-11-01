package br.com.pontoeletronico.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CheckOutBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("CheckOutBroadcastReceiver", "onReceive");
		
	}
}

package br.com.pontoeletronico.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class CheckOutBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("CheckOutBroadcastReceiver", "onReceive");
		
		android.os.Debug.waitForDebugger();
		
		Toast.makeText(context, "It was called the BroadcastReceiver!!!!.",
	    Toast.LENGTH_SHORT).show();
	    
		Intent service = new Intent(context, CheckOutService.class);
		context.startService(service);
		
	}
}

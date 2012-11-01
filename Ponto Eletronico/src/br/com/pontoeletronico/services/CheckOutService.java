package br.com.pontoeletronico.services;

import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Ponto;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CheckOutService extends Service {
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("CheckOutService", "onStartCommand");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i("CheckOutService", "onStart");
		
		//android.os.Debug.waitForDebugger();
		
		Context context = getApplication().getApplicationContext();
		DaoProvider daoProvider = new DaoProvider(context);
		
		RuntimeExceptionDao<Ponto, Integer> pontoDao = daoProvider.getPontoRuntimeDao();
		List<Ponto> list = null;
		
		try {
			list = pontoDao.query(pontoDao.queryBuilder()
					.where().isNull("outputDate")
					.prepare());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for (Ponto ponto : list) {
			ponto.outputDate = new Date();
			pontoDao.update(ponto);
		}
		
		Toast.makeText(getApplicationContext(), "Hor‡rio de sa’da marcado para " + list.size() + " funcion‡rio(s) esquecido(s)! =)", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		Log.i("CheckOutService", "onBind");
		
		return null;
	}
	
	@Override
	public void onDestroy() {
		
		Log.i("CheckOutService", "onDestroy");
		
		super.onDestroy();
	}
	
	
   
}

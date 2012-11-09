package br.com.pontoeletronico.util;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;
import br.com.pontoeletronico.services.CheckOutBroadcastReceiver;
import br.com.pontoeletronico.services.CheckOutService;
import br.com.pontoeletronico.services.EmailSenderService;

public class CodeSnippet {
	
	private static final long REPEAT_TIME = 1000 * 30;
	
	public static Boolean sendSMS(String phone, String message) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone, null, message, null, null);
			
		} catch (Exception e) {
			return false;
		}
		
		
		return true;
	}
	
	public static void checkOutBroadCastReceiver(Context context, DaoProvider daoProvider) {
		Log.i("CheckOutBroadcastReceiver", "startBroadCastReceiver");
		
		Date alarmDate = daoProvider.getConfiguracoesRuntimeDao().queryForId(1).checkOutLimit;
		
		Intent myIntent = new Intent(context, CheckOutBroadcastReceiver.class);
	    //PendingIntent pending = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    PendingIntent pending = PendingIntent.getBroadcast(context.getApplicationContext(), 1231515, myIntent, 0);
	    
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(new Date());
	    calendar.set(GregorianCalendar.HOUR_OF_DAY, alarmDate.getHours());
	    calendar.set(GregorianCalendar.MINUTE, alarmDate.getMinutes());
	    calendar.set(GregorianCalendar.SECOND, 0);
	    calendar.set(GregorianCalendar.MILLISECOND, 0);
	    /*if(calendar.before(new GreSgorianCalendar())){
	    	calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
	    }*/
	    
	    Date now = new Date();
	    
	    int hour = now.getHours();
	    int min = now.getMinutes();
	    int sec = now.getSeconds() + 5;
	    
	    calendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
	    calendar.set(GregorianCalendar.MINUTE, min);
	    calendar.set(GregorianCalendar.SECOND, sec);
	   
	    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pending);  //set repeating every 24 hours
	    
	}
	
	public static void stopCheckOutService(Context context) {
		context.stopService(new Intent(context, CheckOutService.class));
	}
	
	public static void startEmailSenderService(Context context) {
		Log.i("EmailSenderService", "startService");
		
		
		Intent myIntent = new Intent(context, CheckOutService.class);
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    PendingIntent pending = PendingIntent.getService(context.getApplicationContext(), 0, myIntent, 0);
	    
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
	    calendar.set(GregorianCalendar.MINUTE, 0);
	    calendar.set(GregorianCalendar.SECOND, 0);
	    calendar.set(GregorianCalendar.MILLISECOND, 0);
	    calendar.set(GregorianCalendar.DAY_OF_WEEK, 6);
	    if(calendar.before(new GregorianCalendar())){
	    	calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
	    }
	    
	    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pending);  //set repeating every 24 hours
	}
	
	public static void stopEmailSenderService(Context context) {
		context.stopService(new Intent(context, EmailSenderService.class));
	}
	
	public static void sendEmail(Activity activity) {
		/*Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"felipenolletonascimento@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    activity.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}*/
		
		try {   
            GMailSender sender = new GMailSender("exemplo01012010@gmail.com", "Teste91testE");
            sender.sendMail("There a message for you",   
                    "It's a message sent by PontoEletronico.apk!\n\nCongratulations, you can do anything. ;)",   
                    "exemplo01012010@gmail.com",   
                    "felipe.nolleto@digitaldesk.com.br");   
        } catch (Exception e) {   
            Log.e("SendMail", e.getMessage(), e);   
        } 
	}
	
	public static void closeKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	public static void initDB(DaoProvider daoProvider) {
		RuntimeExceptionDao<Configuracoes, Integer> configDao = daoProvider.getConfiguracoesRuntimeDao();
		RuntimeExceptionDao<Funcionario, Integer> funcDao = daoProvider.getFuncionarioRuntimeDao();
		
		Configuracoes config = configDao.queryForId(1);
		List<Funcionario> func = funcDao.queryForEq("User", "admin");
		
		if (config == null) {
			config = new Configuracoes(1, new Date(2012, 1, 1, 7, 30), new Date(2012, 1, 1, 18, 30));
			configDao.createIfNotExists(config);
			
		}
		if (func == null || func.size() <= 0) {
			Funcionario funcionario = new Funcionario("admin", "1234", "Admin", true);
			funcDao.createIfNotExists(funcionario);
		}
		
	}
	
	/**
	 * Verifica se existe a String de determinado ID e retorna ela. Essa String poder‡ ser encontrada no arquivo localizado 
	 * res/values/strings.xml.
	 * METODO INUTIL
	 * 
	 * @param id -
	 * 		ID da string.
	 * @return
	 * 		Retornar‡ uma String de acordo com o arquivo strings.xml.
	 */
	public static String getStringFromID(int id) {
		
		return "";
	}
	
}

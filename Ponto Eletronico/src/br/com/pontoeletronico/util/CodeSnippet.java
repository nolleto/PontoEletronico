package br.com.pontoeletronico.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;
import br.com.pontoeletronico.services.CheckOutService;
import br.com.pontoeletronico.services.EmailSenderService;

public class CodeSnippet {
	
	private static final long REPEAT_TIME = 1000 * 30;
	
	public static String problemUser(DaoProvider daoProvider ,String userName) {
		String expression = "^[a-z0-9]*$";
	    CharSequence inputStr = userName;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
		
		if (userName.length() < 4) {
			return "Nome de Usu‡rio muito curto";
		} else if (userName.length() > 25) {
			return "Nome de Usu‡rio muito extenso";
		} else if (!matcher.matches()) {
			return "Nome de usu‡rio contŽm caracters inv‡lido(‡, ’, ‹ , etc)";
		} else {
			if (checkIfExistUser(daoProvider, userName)) {
				return "Nome de Usu‡rio j‡ existe";
			} else {
				return "Nome de Usu‡rio Inv‡lido";
			}
			
			
		}
	}
	
	public static Boolean checkUser(DaoProvider daoProvider ,String user) {
		String expression = "^[a-z0-9]*$";
	    CharSequence inputStr = user.toString();

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
		
		if (user.length() >= 4 && user.length() <= 25 && matcher.matches() && !checkIfExistUser(daoProvider, user)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String problemPassword(String password1, String password2) {
		if (password1.length() < 8) {
			return "Senha muito curta";
		} else if (password1.length() > 20 ){
			return "Senha muito longa";
		} else if (!password1.equals(password2)) {
			return "As duas senhas n‹o s‹o idnticas";
		} else {
			return "Senha inv‡lida";
		}
	}
	
	public static Boolean checkPassword(String password1, String password2) {
		if (password1.length() >= 8 && password1.length() <= 20 && password1.equals(password2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String problemEmail(String email) {
		if (!checkEmail(email)) {
			return "Esse Email n‹o Ž v‡lido";
		} else {
			return "Email inv‡lido";
		}
	}
	
	public static Boolean checkEmail(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        return true;
	    } else {
	    	return false;
	    }
	}
	
	public static String problemPhone(String phone) {
		if (phone.length() == 0) {
			return "O campo Telefone est‡ em branco";
		} else if (phone.length() < 8) {
			return "O Telefone Ž muito curto";
		} else {
			return "Telefone inv‡lido";
		}
	}
	
	public static Boolean checkPhone(String phone) {
		if (phone.length() >= 8) {
			return true;
		}
		return false;
	}
	
	public static Boolean checkIfExistUser(DaoProvider daoProvider, String user) {
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = daoProvider.getFuncionarioRuntimeDao().query(daoProvider.getFuncionarioRuntimeDao().queryBuilder()
					.where().eq("User", user)
					.prepare());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (funcionarios.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean checkIfExistUserAndPassworl(DaoProvider daoProvider, String user, String pass) {
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = daoProvider.getFuncionarioRuntimeDao().query(daoProvider.getFuncionarioRuntimeDao().queryBuilder()
					.where().eq("User", user)
					.and().eq("Password", pass)
					.prepare());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (funcionarios.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean checkIsGerente(DaoProvider daoProvider, String user) {
		Funcionario funcionario = daoProvider.getFuncionarioRuntimeDao().queryForEq("User", user).get(0);
		
		if (funcionario != null && funcionario.isGerente) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static void checkIn(DaoProvider daoProvider, Funcionario funcionario) {
		Date date = daoProvider.getConfiguracoesRuntimeDao().queryForId(1).checkInLimit;
		Date dateAtual = new Date();
		
		if (date.getHours() < dateAtual.getHours() || (date.getHours() == dateAtual.getHours() && date.getMinutes() < dateAtual.getMinutes())) {
			Log.i("ERRORRRRRRR", "Hora maoir");
		}
		
		Ponto ponto = new Ponto();
		ponto.inputDate = dateAtual;
		daoProvider.getPontoRuntimeDao().create(ponto);
		
		Funcionario_Ponto funcPonto = new Funcionario_Ponto(funcionario, ponto);
		daoProvider.getFuncionario_PontoRuntimeDao().create(funcPonto);
	}
	
	public static Boolean sendSMS(String phone, String message) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone, null, message, null, null);
			
		} catch (Exception e) {
			return false;
		}
		
		
		return true;
	}
	
	public static void startCheckOutService(Context context, DaoProvider daoProvider) {
		Log.i("CheckOutService", "startService");
		
		Date alarmDate = daoProvider.getConfiguracoesRuntimeDao().queryForId(1).checkOutLimit;
		
		Intent myIntent = new Intent(context, CheckOutService.class);
	    //PendingIntent pending = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    PendingIntent pending = PendingIntent.getService(context.getApplicationContext(), 0, myIntent, 0);
	    
	    GregorianCalendar calendar2 = new GregorianCalendar();
	    calendar2.set(GregorianCalendar.HOUR_OF_DAY, alarmDate.getHours());
	    calendar2.set(GregorianCalendar.MINUTE, alarmDate.getMinutes());
	    calendar2.set(GregorianCalendar.SECOND, 0);
	    calendar2.set(GregorianCalendar.MILLISECOND, 0);
	    if(calendar2.before(new GregorianCalendar())){
	    	calendar2.add(GregorianCalendar.DAY_OF_MONTH, 1);
	    }
	    
	    /*Date now = new Date();
	    
	    int hour = now.getHours();
	    int min = now.getMinutes();
	    int sec = now.getSeconds() + 5;
	    
	    calendar2.set(GregorianCalendar.HOUR_OF_DAY, hour);
	    calendar2.set(GregorianCalendar.MINUTE, min);
	    calendar2.set(GregorianCalendar.SECOND, sec);*/
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 10);
	    calendar.set(Calendar.MINUTE, 20);
	    calendar.set(Calendar.SECOND, 00);
	    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), 24*60*60*1000 , pending);  //set repeating every 24 hours
		
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
	
	public static void createExcel(DaoProvider daoProvider) {
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(new File("myFile.xls"));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet = workbook.getSheet(0);
		
		Cell a1 = sheet.getCell(0, 0);
		
	}
	
	public static void checkOut(DaoProvider daoProvider, Funcionario_Ponto funcionarioPonto) {
		Ponto ponto = funcionarioPonto.ponto;
		ponto.outputDate = new Date();
		daoProvider.getPontoRuntimeDao().update(ponto);
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
	
}

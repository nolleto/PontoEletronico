package br.com.pontoeletronico.util;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.view.WindowManager;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;

public class CodeSnippet {
	
	
	public static Boolean checkUser(String user) {
		
		String expression = "^[a-z0-9]*$";
	    CharSequence inputStr = user.toString();

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
		
		if (user.length() >= 4 && user.length() <= 25 && matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean checkPassword(String password) {
		if (password.length() >= 8 && password.length() <= 20) {
			return true;
		
		} else {
			return false;
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
	
	public static Boolean checkPhone(String phone) {
		if (phone.length() > 0) {
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
	
	public static void closeKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
}
	
}

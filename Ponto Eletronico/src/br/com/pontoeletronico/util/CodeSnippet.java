package br.com.pontoeletronico.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ObjectAnimator;

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
	
}

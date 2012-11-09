package br.com.pontoeletronico.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.DaoProvider;

public class FormValidator {

	/**
	 * Verifica se o usu�rio � v�lido ou n�o, por exemplo, o usu�rio s� pode conter letras mai�sculas e min�sculas, sem acento e numeros, desde que n�o comece com um numero.
	 * Caso retorne um problema e deseja saber qual �, o m�todo que voc� est� procurando � {@link #problemUser(DaoProvider, String)}.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param user - 
	 * 		Nome de usu�rio para verifica��o.
	 * @return  
	 * 		true se o Usu�rio � v�lido, false caso n�o.
	 */
	public static Boolean checkUser(DaoProvider daoProvider ,String user) {
		String expression = "^[a-z0-9]*$";
	    CharSequence inputStr = user.toString();
	    
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
		
		if (user.length() >= 4 && user.length() <= 25 && matcher.matches() && !FuncionarioController.checkIfExistUser(daoProvider, user)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica qual � o erro do Nome de Usu�rio, por exemplo, se houver characteres inv�lidos, acentos e cifr�es.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param userName -
	 * 		Nome de usu�rio para verifica��o.
	 * @return
	 * 		Retornar� uma String com a descri��o do problema.
	 */
	public static String problemUser(DaoProvider daoProvider ,String userName) {
		String expression = "^[a-z0-9]*$";
	    CharSequence inputStr = userName;
	    
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
		
		if (userName.length() < 4) {
			return "Nome de Usu�rio muito curto";
		} else if (userName.length() > 25) {
			return "Nome de Usu�rio muito extenso";
		} else if (!matcher.matches()) {
			return "Nome de usu�rio cont�m caracters inv�lido(�, �, � �, etc)";
		} else {
			if (FuncionarioController.checkIfExistUser(daoProvider, userName)) {
				return "Nome de Usu�rio j� existe";
			} else {
				return "Nome de Usu�rio Inv�lido";
			}
			
			
		}
	}
	
	/**
	 * Verifica se a Senha est� de acordo com as especifica��es, por exemplo, se a Senha for muito curta, ela n�o passar�.
	 * Nesse m�todo o usu�rio passar� duas Strings, o Senha, e a Confirma��o da Senha.
	 * Caso retorne um problema e deseja saber qual �, o m�todo que voc� est� procurando � {@link #problemPassword(String, String)}
	 * 
	 * @param password1 -
	 * 		Senha do usu�rio.
	 * @param password2 -
	 * 		Confirma��o da Senha do usu�rio.
	 * @return
	 * 		true se a Senha for v�lida, false caso n�o.
	 */
	public static Boolean checkPassword(String password1, String password2) {
		if (password1.length() >= 8 && password1.length() <= 20 && password1.equals(password2)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica qual � o problema da Senha do usu�rio, por exemplo, caso as Senha n�o sejam id�nticas.
	 * 
	 * @param password1 - 	
	 * 		Senha do usu�rio.
	 * @param password2 -
	 * 		Confirma��o da Senha do usu�rio.
	 * @return
	 * 		Retornar� uma String com a descri��o do problema.
	 */
	public static String problemPassword(String password1, String password2) {
		if (password1.length() < 8) {
			return "Senha muito curta";
		} else if (password1.length() > 20 ){
			return "Senha muito longa";
		} else if (!password1.equals(password2)) {
			return "As duas senhas n�o s�o id�nticas";
		} else {
			return "Senha inv�lida";
		}
	}
	
	/**
	 * Verifica se o E-mail � v�lido, nesse metodo o E-mail passar� por uma verifica��o utilizando Express�o Regular.
	 * Caso retorne um problema e deseja saber qual �, o m�todo que voc� est� procurando � {@link #problemEmail(String)}
	 * 
	 * @param email -
	 * 		E-mail para verifica��o.
	 * @return
	 * 		true caso o E-mail seja v�lido, false, caso n�o. 
	 */
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
	
	/**
	 * Retornar� qual � o erro do E-mail, por exemplo, se ele n�o confere com o formato de um E-mail.
	 * 
	 * @param email - 
	 * 		E-mail para verifica��o.
	 * @return
	 * 		Retornar� uma String com a descri��o do problema.
	 */
	public static String problemEmail(String email) {
		if (!checkEmail(email)) {
			return "Esse Email n�o � v�lido";
		} else {
			return "Email inv�lido";
		}
	}
	
	/**
	 * Verificar se o Telefone � v�lido ou n�o
	 * M�TODO INCOMPLETO
	 * 
	 * @param phone
	 * @return
	 */
	public static Boolean checkPhone(String phone) {
		if (phone.length() >= 8) {
			return true;
		}
		return false;
	}
	
	public static String problemPhone(String phone) {
		if (phone.length() == 0) {
			return "O campo Telefone est� em branco";
		} else if (phone.length() < 8) {
			return "O Telefone � muito curto";
		} else {
			return "Telefone inv�lido";
		}
	}
	
}

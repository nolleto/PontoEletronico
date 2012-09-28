package br.com.pontoeletronico.activities;

import java.io.Serializable;


public class FuncionarioGerente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4624558402192623662L;
	public boolean isGerente;
	public int iD;
	public String Name;
	public String Email;
	public String User;
	public String Password;
	public String Adress;
	public String Phone; 
	
	public FuncionarioGerente(boolean isGerente, int iD, String Name, String Email, String User, String Password, String Adress, String Phone) {
		this.isGerente = isGerente;
		this.iD = iD;
		this.Name = Name;
		this.Email = Email;
		this.User = User;
		this.Password = Password;
		this.Adress = Adress;
		this.Phone = Phone; 
	}

	public FuncionarioGerente(String Name, String Email, String User, String Password, String Adress, String Phone) {
		this.Name = Name;
		this.Email = Email;
		this.User = User;
		this.Password = Password;
		this.Adress = Adress;
		this.Phone = Phone; 
	}
	
	public boolean isGerente() {
		return isGerente;
	}

	public void setGerente(boolean isGerente) {
		this.isGerente = isGerente;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	
	
}

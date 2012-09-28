package br.com.pontoeletronico.database;

import com.j256.ormlite.field.DatabaseField;

import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Funcionario {
	
	@DatabaseField(generatedId=true)
	public int funcionarioID;

	@DatabaseField(canBeNull=false)
	public String Name;

	@DatabaseField
	public String Email;
	
	@DatabaseField(canBeNull=false, unique=true)
	public String User;
	
	@DatabaseField(canBeNull=false)
	public String Password;
	
	@DatabaseField
	public String Adress;
	
	@DatabaseField
	public String Phone;

	public Funcionario() {};
	
	public Funcionario(String user, String password) {
		User = user;
		Password = password;
	};
	
	public Funcionario(String user, String password, String name, String email, String adress, String phone) {
		Name = name;
		User = user;
		Password = password;
		Email = email;
		Adress = adress;
		Phone = phone;
	};
	
	public int getID() {
		return funcionarioID;
	}

	public void setID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
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

package br.com.pontoeletronico.database;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Funcionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7206727363243262703L;

	@DatabaseField(generatedId=true)
	public int funcionarioID;

	@DatabaseField(canBeNull=false)
	public String Name;
	
	@DatabaseField(canBeNull=false, unique=true)
	public String User;
	
	@DatabaseField(canBeNull=false)
	public String Password;
	
	@DatabaseField
	public String Email;
	
	@DatabaseField
	public String Adress;
	
	@DatabaseField
	public String Phone;
	
	@DatabaseField(canBeNull=false)
	public Date DateCreated;
	
	@DatabaseField(canBeNull=true, columnName="funcionarioGerenteID", foreign=true, foreignAutoRefresh=true)
	public Funcionario GerenteDelegate;
	
	@DatabaseField(canBeNull=true, columnName="funcionarioConfiguracoesID", foreign=true, foreignAutoRefresh=true)
	public FuncionarioConfiguracoes funcionarioConfiguracoes;
	
	@DatabaseField
	public Boolean isGerente;

	public Funcionario() {};
	
	public Funcionario(String user, String password,String name, Boolean isGerente) {
		User = user;
		Password = password;
		Name = name;
		this.isGerente = isGerente;
	};
	
	public Funcionario(String user, String password, String name, String email, String adress, String phone, Boolean isGerente) {
		this(user, password, name, isGerente);
		Email = email;
		Adress = adress;
		Phone = phone;
	};
	
	public Funcionario(String user, String password, String name, String email, String adress, String phone, Boolean isGerente, Funcionario funcionarioDelegate, Date date) {
		this(user, password, name, email, adress, phone, isGerente);		
		this.GerenteDelegate = funcionarioDelegate;
		this.DateCreated = date;
	};
	
	/*public Funcionario(boolean isGerente, int iD, String Name, String Email, String User, String Password, String Adress, String Phone) {
		this.isGerente = isGerente;
		funcionarioID = iD;
		this.Name = Name;
		this.Email = Email;
		this.User = User;
		this.Password = Password;
		this.Adress = Adress;
		this.Phone = Phone; 
	}*/
	
	public Funcionario(int id) {
		this.funcionarioID = id;
	}

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

	public Boolean getIsGerente() {
		return isGerente;
	}

	public void setIsGerente(Boolean isGerente) {
		this.isGerente = isGerente;
	}

	public FuncionarioConfiguracoes getFuncionarioConfiguracoes() {
		return funcionarioConfiguracoes;
	}

	public void setFuncionarioConfiguracoes(
			FuncionarioConfiguracoes funcionarioConfiguracoes) {
		this.funcionarioConfiguracoes = funcionarioConfiguracoes;
	}

	public int getFuncionarioID() {
		return funcionarioID;
	}

	public void setFuncionarioID(int funcionarioID) {
		this.funcionarioID = funcionarioID;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public Funcionario getGerenteDelegate() {
		return GerenteDelegate;
	}

	public void setGerenteDelegate(Funcionario gerenteDelegate) {
		GerenteDelegate = gerenteDelegate;
	}	

	
	
}

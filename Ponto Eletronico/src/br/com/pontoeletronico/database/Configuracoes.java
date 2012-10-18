package br.com.pontoeletronico.database;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Configuracoes {

	@DatabaseField(id=true)
	public int idConfiguracoes;
	
	@DatabaseField(canBeNull=false)
	public Date checkInLimit;
	
	@DatabaseField(canBeNull=false)
	public Date checkOutLimit;
	
	@DatabaseField
	public String titleApk;
	
	@DatabaseField
	public String emailNotification;
	
	@DatabaseField
	public String phoneNotification;

	public Configuracoes() {
		
	}
	
	public Configuracoes(int id, Date checkInLimit, Date checkOutLimit) {
		this.idConfiguracoes = id;
		this.checkInLimit = checkInLimit;
		this.checkOutLimit = checkOutLimit;
	}
	
	public int getIdConfiguracoes() {
		return idConfiguracoes;
	}

	public void setIdConfiguracoes(int idConfiguracoes) {
		this.idConfiguracoes = idConfiguracoes;
	}

	public Date getCheckInLimit() {
		return checkInLimit;
	}

	public void setCheckInLimit(Date checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	public Date getCheckOutLimit() {
		return checkOutLimit;
	}

	public void setCheckOutLimit(Date checkOutLimit) {
		this.checkOutLimit = checkOutLimit;
	}

	public String getTitleApk() {
		return titleApk;
	}

	public void setTitleApk(String titleApk) {
		this.titleApk = titleApk;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getPhoneNotification() {
		return phoneNotification;
	}

	public void setPhoneNotification(String phoneNotification) {
		this.phoneNotification = phoneNotification;
	}
	
	
	
}

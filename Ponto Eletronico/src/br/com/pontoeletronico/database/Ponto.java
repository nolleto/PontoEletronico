package br.com.pontoeletronico.database;


import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Ponto {
	
	@DatabaseField(generatedId=true)
	public int pontoID;
	
	@DatabaseField
	public Date inputDate;
	
	@DatabaseField
	public Date outputDate;
	
	public Ponto() {}

	public int getID() {
		return pontoID;
	}

	public void setID(int pontoID) {
		this.pontoID = pontoID;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getOutputDate() {
		return outputDate;
	}

	public void setOutputDate(Date outputDate) {
		this.outputDate = outputDate;
	};
	
	
	
}

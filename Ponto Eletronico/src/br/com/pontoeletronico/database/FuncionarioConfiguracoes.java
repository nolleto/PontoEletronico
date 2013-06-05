package br.com.pontoeletronico.database;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FuncionarioConfiguracoes {
	
	@DatabaseField(generatedId=true)
	public int funcionarioConfiguracoesID;	
	
	@DatabaseField
	public Date FuncionarioCheckIn;
	
	@DatabaseField
	public Date FuncionarioCheckOut;
	
	public FuncionarioConfiguracoes() {};
	
	public int getFuncionarioConfiguracoesID() {
		return funcionarioConfiguracoesID;
	}

	public void setFuncionarioConfiguracoesID(int funcionarioConfiguracoesID) {
		this.funcionarioConfiguracoesID = funcionarioConfiguracoesID;
	}

	public Date getFuncionarioCheckIn() {
		return FuncionarioCheckIn;
	}

	public void setFuncionarioCheckIn(Date funcionarioCheckIn) {
		FuncionarioCheckIn = funcionarioCheckIn;
	}

	public Date getFuncionarioCheckOut() {
		return FuncionarioCheckOut;
	}

	public void setFuncionarioCheckOut(Date funcionarioCheckOut) {
		FuncionarioCheckOut = funcionarioCheckOut;
	}

	
	
}

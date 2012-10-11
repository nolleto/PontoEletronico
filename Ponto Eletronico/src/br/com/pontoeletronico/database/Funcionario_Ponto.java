package br.com.pontoeletronico.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Funcionario_Ponto {
	
	@DatabaseField(generatedId=true)
	public int funcionario_pontoID;

	@DatabaseField(columnName="funcionarioID", foreign=true, foreignAutoRefresh=true)
	public Funcionario funcionario;

	@DatabaseField(columnName="pontoID", foreign=true, foreignAutoRefresh=true, canBeNull=false)
	public Ponto ponto;
	
	public Funcionario_Ponto(Funcionario funcionario, Ponto ponto) {
		this.funcionario = funcionario;
		this.ponto = ponto;
	}
	
	public Funcionario_Ponto() {}

	public int getFuncionario_pontoID() {
		return funcionario_pontoID;
	}

	public void setFuncionario_pontoID(int funcionario_pontoID) {
		this.funcionario_pontoID = funcionario_pontoID;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	};
	
	
	
}

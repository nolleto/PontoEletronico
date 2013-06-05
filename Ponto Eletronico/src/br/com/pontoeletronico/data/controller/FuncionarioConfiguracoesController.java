package br.com.pontoeletronico.data.controller;

import java.util.Date;

import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.FuncionarioConfiguracoes;

public class FuncionarioConfiguracoesController {
	
	public static FuncionarioConfiguracoes setCheckIn(DaoProvider daoProvider, Funcionario funcionario, Date date) {
		FuncionarioConfiguracoes funcionarioConfiguracoes = funcionario.funcionarioConfiguracoes;
		 if (funcionarioConfiguracoes == null) {
			funcionarioConfiguracoes = new FuncionarioConfiguracoes();
			funcionarioConfiguracoes.FuncionarioCheckIn = date;
			daoProvider.getFuncionario_ConfiguracoesRuntimeDao().create(funcionarioConfiguracoes);
		} else {
			funcionarioConfiguracoes.FuncionarioCheckIn = date;
			daoProvider.getFuncionario_ConfiguracoesRuntimeDao().update(funcionarioConfiguracoes);
		}
		 return funcionarioConfiguracoes;
	}
	
	public static FuncionarioConfiguracoes setCheckOut(DaoProvider daoProvider, Funcionario funcionario, Date date) {
		FuncionarioConfiguracoes funcionarioConfiguracoes = funcionario.funcionarioConfiguracoes;
		 if (funcionarioConfiguracoes == null) {
			funcionarioConfiguracoes = new FuncionarioConfiguracoes();
			funcionarioConfiguracoes.FuncionarioCheckOut = date;
			daoProvider.getFuncionario_ConfiguracoesRuntimeDao().create(funcionarioConfiguracoes);
		} else {
			funcionarioConfiguracoes.FuncionarioCheckOut = date;
			daoProvider.getFuncionario_ConfiguracoesRuntimeDao().update(funcionarioConfiguracoes);
		}
		 return funcionarioConfiguracoes;
	}
	
}

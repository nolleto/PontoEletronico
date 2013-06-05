package br.com.pontoeletronico.data.controller;

import java.util.Date;

import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.DaoProvider;

public class ConfiguracoesController {

	/**
	 * Pega no Banco de Dados as configurações.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conecção com o DataBase.
	 * @return
	 * 		As configurações do aplicativo.
	 */
	public static Configuracoes getConfiguracoes(DaoProvider daoProvider) {
		return daoProvider.getConfiguracoesRuntimeDao().queryForId(1);
	}
	
	/**
	 * Seta as configurações padrão do Banco de Dados.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conecção com o DataBase.
	 */
	public static void setConfiguracoesDefault(DaoProvider daoProvider) {
		Configuracoes config = ConfiguracoesController.getConfiguracoes(daoProvider);
		if (config == null) {
			config = new Configuracoes(1, new Date(2012, 1, 1, 7, 30), new Date(2012, 1, 1, 18, 30));
			daoProvider.getConfiguracoesRuntimeDao().createIfNotExists(config);
		}
	}
	
}

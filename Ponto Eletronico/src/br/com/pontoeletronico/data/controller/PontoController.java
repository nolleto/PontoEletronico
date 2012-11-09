package br.com.pontoeletronico.data.controller;

import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.util.Log;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;

public class PontoController {

	/**
	 * Faz o Ponto de entrada do funcion�rio. Explicando como funciona, ele basicamente cria um novo objeto Ponto seta a data atual no campo inputDate.
	 * Logo depois ele cria um novo objeto Funcioario_Ponto onde ele vincula como o Funcioario e o Ponto criado.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param funcionario - 
	 * 		Objeto que deve ser o funcion�rio que vai marcar o ponto. 
	 */
	public static void checkIn(DaoProvider daoProvider, Funcionario funcionario) {
		Date date = daoProvider.getConfiguracoesRuntimeDao().queryForId(1).checkInLimit;
		Date dateAtual = new Date();
		
		if (date.getHours() < dateAtual.getHours() || (date.getHours() == dateAtual.getHours() && date.getMinutes() < dateAtual.getMinutes())) {
			Log.i("ERRORRRRRRR", "Hora maoir");
		}
		
		Ponto ponto = new Ponto();
		ponto.inputDate = dateAtual;
		daoProvider.getPontoRuntimeDao().create(ponto);
		
		Funcionario_Ponto funcPonto = new Funcionario_Ponto(funcionario, ponto);
		daoProvider.getFuncionario_PontoRuntimeDao().create(funcPonto);
	}
	
	/**
	 * Faz o Ponto de sa�da do funcion�rio. Exlpicando como funciona, ele vai receber um objeto Funcionario_Ponto do funcio�rio, 
	 * ele pegar� o objeto Ponto que vir� com a data de entrada preenchida e s� colocar� o hor�rio de sa�da. 
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param funcionarioPonto -
	 * 		Objeto que deve conter o �ltimo ponto do funcion�rio.
	 * 
	 */
	public static void checkOut(DaoProvider daoProvider, Funcionario_Ponto funcionarioPonto) {
		Ponto ponto = funcionarioPonto.ponto;
		ponto.outputDate = new Date();
		daoProvider.getPontoRuntimeDao().update(ponto);
	}
	
	
	
}

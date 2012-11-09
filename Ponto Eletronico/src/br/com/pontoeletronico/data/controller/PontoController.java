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
	 * Faz o Ponto de entrada do funcionário. Explicando como funciona, ele basicamente cria um novo objeto Ponto seta a data atual no campo inputDate.
	 * Logo depois ele cria um novo objeto Funcioario_Ponto onde ele vincula como o Funcioario e o Ponto criado.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionario - 
	 * 		Objeto que deve ser o funcionário que vai marcar o ponto. 
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
	 * Faz o Ponto de saída do funcionário. Exlpicando como funciona, ele vai receber um objeto Funcionario_Ponto do funcioário, 
	 * ele pegará o objeto Ponto que virá com a data de entrada preenchida e só colocará o horário de saída. 
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionarioPonto -
	 * 		Objeto que deve conter o último ponto do funcionário.
	 * 
	 */
	public static void checkOut(DaoProvider daoProvider, Funcionario_Ponto funcionarioPonto) {
		Ponto ponto = funcionarioPonto.ponto;
		ponto.outputDate = new Date();
		daoProvider.getPontoRuntimeDao().update(ponto);
	}
	
	
	
}

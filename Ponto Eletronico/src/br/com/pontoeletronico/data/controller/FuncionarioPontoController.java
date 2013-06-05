package br.com.pontoeletronico.data.controller;

import java.util.List;

import android.app.Activity;
import android.widget.ListAdapter;
import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.adapter.ListaVaziaAdapter;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;

import com.j256.ormlite.dao.RuntimeExceptionDao;

public class FuncionarioPontoController {

	/**
	 * Pega o último {@link Funcionario_Ponto} de uma funcionário específico.
	 * 
	 * @param daoProvider -
	 * 		Objeto de conecção com o DataBase.
	 * @param funcionarioId -
	 * 		ID do funcionário desejado.
	 * @return
	 * 		O último {@link Funcionario_Ponto} de um funcionário.
	 */
	public static Funcionario_Ponto getLastFuncionarioPontoForId(DaoProvider daoProvider, Integer funcionarioId) {
		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
		Funcionario_Ponto funcionario_Ponto = null;

		try {
			funcionario_Ponto = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
					.orderBy("funcionario_pontoID", false)
					.where().eq("funcionarioID", funcionarioId)
					.prepare()).get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return funcionario_Ponto;
	}
	
	/**
	 * Procura no Banco de Dados todos os {@link Funcionario_Ponto} de determinado {@link Funcionario}.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionarioId -
	 * 		ID do funcionário que deseja obter todos os {@link Funcionario_Ponto}.
	 * @return 
	 * 		Uma {@link List} com todos os {@link FuncionarioPontoController} do {@link Funcionario}.
	 */
	public static List<Funcionario_Ponto> getListFuncionarioPontoForId(DaoProvider daoProvider, Integer funcionarioId) {
		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
		List<Funcionario_Ponto> list = null;
		
		try {
			list = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
					.orderBy("funcionario_pontoID", false)
					.where().eq("funcionarioID", funcionarioId)
					.prepare());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}
	
	/**
	 * Verifica se o funcionário já possui pontos no Banco de Dados, caso possua retornará um {@link ListAdapter} com todos
	 * os pontos do funcionário, caso não possua retornará com uma mensagem informando ao usuário que ele ainda não
	 * possui nenhum ponto marcado.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conecção com o DataBase.
	 * @param activity -
	 * 		{@link Activity} atual.
	 * @param funcionarioId -
	 * 		ID do funcionário que deseja ver os pontos.
	 * @return
	 * 		Retorna um {@link ListAdapter} com os pontos do funcionário.
	 */
	public static ListAdapter getArrayAdpter(DaoProvider daoProvider, Activity activity, int funcionarioId) {
		List<Funcionario_Ponto> list = FuncionarioPontoController.getListFuncionarioPontoForId(daoProvider, funcionarioId);
			
		if (list == null || list.size() == 0) {
			return new ListaVaziaAdapter(activity, activity.getString(R.string.listView_Empty_Ponto));
		} else {
			return new ListaPontosAdapter(activity, list);
		}
		
	}
	
	/**
	 * Deleta todos os registros de {@link Funcionario_Ponto} do funcioário desejado.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionarioId -	
	 * 		ID do funcioário que deseja apagar os {@link Funcionario_Ponto}.
	 */
	public static void deleteFuncionarioPonto(DaoProvider daoProvider, Integer funcionarioId) {
		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
		
		List<Funcionario_Ponto> list = FuncionarioPontoController.getListFuncionarioPontoForId(daoProvider, funcionarioId);
		
		for (Funcionario_Ponto funcionario_Ponto : list) {
			PontoController.deletePonto(daoProvider, funcionario_Ponto.ponto.pontoID);
			funcPontoDao.delete(funcionario_Ponto);
		}
	}
	
	/**
	 * Somente o registro de {@link Funcionario_Ponto} do funcioário desejado.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionario_Ponto -
	 * 		{@link Funcionario_Ponto} de irá ser deletado.
	 */
	public static void deleteFuncionarioPonto(DaoProvider daoProvider, Funcionario_Ponto funcionario_Ponto) {
		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
		
		PontoController.deletePonto(daoProvider, funcionario_Ponto.ponto.pontoID);
		funcPontoDao.delete(funcionario_Ponto);
	}
}

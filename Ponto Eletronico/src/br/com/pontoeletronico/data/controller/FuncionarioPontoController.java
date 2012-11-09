package br.com.pontoeletronico.data.controller;

import java.util.List;

import android.app.Activity;
import android.widget.Adapter;
import android.widget.ListAdapter;
import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.adapter.ListaVaziaAdapter;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario_Ponto;

import com.j256.ormlite.dao.RuntimeExceptionDao;

public class FuncionarioPontoController {

	/**
	 * Verifica se o funcion�rio j� possui pontos no Banco de Dados, caso possua retornar� um {@link ListAdapter} com todos
	 * os pontos do funcion�rio, caso n�o possua retornar� com uma mensagem informando ao usu�rio que ele ainda n�o
	 * possui nenhum ponto marcado.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conec��o com o DataBase.
	 * @param activity -
	 * 		{@link Activity} atual.
	 * @param funcionarioId -
	 * 		ID do funcion�rio que deseja ver os pontos.
	 * @return
	 * 		Retorna um {@link ListAdapter} com os pontos do funcion�rio.
	 */
	public static ListAdapter getArrayAdpter(DaoProvider daoProvider, Activity activity, int funcionarioId) {
		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
		List<Funcionario_Ponto> list = null;
		
		try {
			list = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
					.where().eq("funcionarioID", funcionarioId)
					.prepare());
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		if (list == null || list.size() == 0) {
			return new ListaVaziaAdapter(activity, activity.getString(R.string.listView_Empty_Ponto));
		} else {
			return new ListaPontosAdapter(activity, list);
		}
		
	}
	
}

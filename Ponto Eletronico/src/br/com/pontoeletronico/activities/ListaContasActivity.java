package br.com.pontoeletronico.activities;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Funcionario;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class ListaContasActivity extends BaseActivity {
	ListView listView;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		listView = (ListView) findViewById(R.id.listView);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		/*
		List<Funcionario> funcionarioList = null;
		try {
			funcionarioList = funcionarioDao.query(funcionarioDao.queryBuilder()
					.orderBy("User", true)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaContasAdapter adapter = new ListaContasAdapter(this, funcionarioList);*/
		listView.setAdapter(FuncionarioController.getArrayAdapter(getHelper(), this, 1));
		
	}
	
	public static void startActivity(Activity activity) {
		Intent telaListaDeCadastros = new Intent(activity, ListaContasActivity.class);
		activity.startActivity(telaListaDeCadastros);
	}
	
}

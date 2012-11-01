package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.List;
import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaContasAdapter;
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
		setContentView(R.layout.lista_contas);
		
		listView = (ListView) findViewById(R.id.listContas_ListView);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		List<Funcionario> funcionarioList = null;
		try {
			funcionarioList = funcionarioDao.query(funcionarioDao.queryBuilder()
					.orderBy("User", true)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaContasAdapter adapter = new ListaContasAdapter(this, funcionarioList);
		listView.setAdapter(adapter);
		
	}
	
	public static void startActivity(Activity activity) {
		Intent telaListaDeCadastros = new Intent(activity, ListaContasActivity.class);
		activity.startActivity(telaListaDeCadastros);
	}
	
}

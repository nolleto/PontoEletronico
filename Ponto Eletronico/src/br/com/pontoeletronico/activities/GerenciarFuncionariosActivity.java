package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaFuncionariosAdapter;
import br.com.pontoeletronico.database.Funcionario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class GerenciarFuncionariosActivity extends BaseActivity {
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerenciar_funcionarios);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		ListView listaView = (ListView) findViewById(R.id.gerencFuncionarios_ListView);
		
		setTitleInActionBar("Somente Teste");
		
		List<Funcionario> list = null;
		try {
			list = funcionarioDao.query(funcionarioDao.queryBuilder()
					.orderBy("Name", true)
					.where().eq("isGerente", false)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaFuncionariosAdapter adapter = new ListaFuncionariosAdapter(this, getHelper(), list);
		listaView.setAdapter(adapter);
		
		
	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, GerenciarFuncionariosActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

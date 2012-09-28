package br.com.pontoeletronico.activities;

import java.util.List;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.digitaldesk.pontoeletronico.R.id;
import br.com.digitaldesk.pontoeletronico.R.layout;
import br.com.pontoeletronico.adapter.ListaContasAdapter;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.os.Bundle;
import android.widget.ListView;

public class ListaContasActivity extends BaseActivity {
	ListView listView;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_contas);
		
		listView = (ListView) findViewById(R.id.listContas_ListView);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		gerenteDao = getHelper().getGerenteRuntimeDao();
		
		List<Gerente> gerenteList = gerenteDao.queryForAll();
		List<Funcionario> funcionarioList = funcionarioDao.queryForAll();
		
		ListaContasAdapter adapter = new ListaContasAdapter(this, gerenteList, funcionarioList);
		listView.setAdapter(adapter);
		
	}
	
}

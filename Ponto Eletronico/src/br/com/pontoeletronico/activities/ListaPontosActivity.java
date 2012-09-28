package br.com.pontoeletronico.activities;

import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.digitaldesk.pontoeletronico.R.id;
import br.com.digitaldesk.pontoeletronico.R.layout;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Gerente;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ListaPontosActivity extends BaseActivity {
	ListView listView;
	List<Funcionario_Ponto> funcionarioPontoList;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_contas);
		
		int id = getIntent().getExtras().getInt("ID");
		listView = (ListView) findViewById(R.id.listContas_ListView);
		
		gerenteDao = getHelper().getGerenteRuntimeDao();
		funcionarioPontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		TextView txtTitulo = (TextView) findViewById(R.id.textView1);
		txtTitulo.setText("Pontos do Usu�rio");
		
		Funcionario funcionario = funcionarioDao.queryForId(id);
		
		if (funcionario != null) {
			try {
				funcionarioPontoList = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
						.where().eq("funcionarioID", funcionario.funcionarioID)
						.prepare());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			ListaPontosAdapter adapter = new ListaPontosAdapter(this, funcionarioPontoList);
			listView.setAdapter(adapter);
			
		} else {
			Gerente gerente = gerenteDao.queryForId(id);
			
			try {
				funcionarioPontoList = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
						.where().eq("gerenteID", gerente.gerenteID)
						.prepare());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			ListaPontosAdapter adapter = new ListaPontosAdapter(this, funcionarioPontoList);
			listView.setAdapter(adapter);
		}
		
		
		
		
		
	}
}

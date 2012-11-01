package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class InfoFuncionarioActivity extends BaseActivity {
	TextView txtName, txtUser, txtEmail, txtPhone, txtAdress;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_funcionario);
		
		id = getIntent().getExtras().getInt("ID");
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionarioPontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		
		Funcionario funcionario = funcionarioDao.queryForId(id);
		
		txtName = (TextView) findViewById(R.id.infoFunc_txtName);
		txtUser = (TextView) findViewById(R.id.infoFunc_txtUser);
		txtEmail = (TextView) findViewById(R.id.infoFunc_txtEmail);
		txtAdress = (TextView) findViewById(R.id.infoFunc_txtAdress);
		txtPhone = (TextView) findViewById(R.id.infoFunc_txtPhone);
		
		String strEmail = funcionario.Email == null || funcionario.Email.isEmpty() == true ? "Sem Email cadastrado" : funcionario.Email;
		String strPhone = funcionario.Phone == null || funcionario.Phone.isEmpty() == true ? "Sem Telefone cadastrado" : funcionario.Phone;
		String strAdress = funcionario.Adress == null || funcionario.Adress.isEmpty() == true ? "Sem Endereço cadastrado" : funcionario.Adress;
		
		txtUser.setText("User: " + funcionario.User);
		txtName.setText("Nome: " + funcionario.Name);
		txtEmail.setText("Email: " + strEmail);
		txtAdress.setText("Endereço: " + strAdress);
		txtPhone. setText("Telefone: " + strPhone);
		
		ListView listView = (ListView) findViewById(R.id.infoFunc_LitView);
		
		List<Funcionario_Ponto> list = null;
		try {
			list = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
					.where().eq("funcionarioID", id)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaPontosAdapter adapter = new ListaPontosAdapter(this, list);
		listView.setAdapter(adapter);
		
	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, InfoFuncionarioActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

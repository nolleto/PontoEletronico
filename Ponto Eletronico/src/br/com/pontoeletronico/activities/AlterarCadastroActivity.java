package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AlterarCadastroActivity extends BaseActivity {
	TextView txtUser, txtPassword, txtNome, txtEmail, txtEndereco, txtTelefone;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	Funcionario funcionario;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_cadastro);
		
		id = getIntent().getExtras().getInt("ID");
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionario = funcionarioDao.queryForId(id);
		
		txtUser = (TextView) findViewById(R.id.alterarCadastro_User);
		txtPassword = (TextView) findViewById(R.id.alterarCadastro_Password);
		txtNome = (TextView) findViewById(R.id.alterarCadastro_Nome);
		txtEmail = (TextView) findViewById(R.id.alterarCadastro_Email);
		txtTelefone = (TextView) findViewById(R.id.alterarCadastro_Telefone);
		
		txtUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarLoginActivity.startActivity(AlterarCadastroActivity.this, id, 0);
			}
		});
		
		txtPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarLoginActivity.startActivity(AlterarCadastroActivity.this, id, 1);
				
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		txtUser.setText("Usu‡rio: " + getUser());
		super.onResume();
	}
	
	public String getUser() {
			return funcionarioDao.queryForId(id).User;
	}
	
	public String getName() {
			return funcionarioDao.queryForId(id).Name;
	}
	
	public String getEmail() {
			String strEmail = funcionarioDao.queryForId(id).Email;
			return strEmail == null ? "Nenhum Email registrado." : strEmail;

	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, AlterarCadastroActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
	
}

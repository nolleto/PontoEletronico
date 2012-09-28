package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class AlterarCadastroActivity extends BaseActivity {
	TextView txtUser, txtPassword, txtNome, txtEmail, txtEndereco, txtTelefone;
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	Funcionario funcionario;
	Gerente gerente;
	int id;
	Boolean isGerente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_cadastro);
		
		id = getIntent().getExtras().getInt("ID");
		isGerente = getIntent().getExtras().getBoolean("isGerente");
		
		gerenteDao = getHelper().getGerenteRuntimeDao();
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		if (isGerente) {
			gerente = gerenteDao.queryForId(id);
		} else {
			funcionario = funcionarioDao.queryForId(id);
		}
		
		txtUser = (TextView) findViewById(R.id.alterarCadastro_User);
		txtPassword = (TextView) findViewById(R.id.alterarCadastro_Password);
		txtNome = (TextView) findViewById(R.id.alterarCadastro_Nome);
		txtEmail = (TextView) findViewById(R.id.alterarCadastro_Email);
		txtTelefone = (TextView) findViewById(R.id.alterarCadastro_Telefone);
		
		txtUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarLoginActivity.startActivity(AlterarCadastroActivity.this, id, 0, true);
			}
		});
		
		txtPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarLoginActivity.startActivity(AlterarCadastroActivity.this, id, 1, true);
				
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		txtUser.setText("Usu‡rio: " + getUser());
		super.onResume();
	}
	
	public String getUser() {
		if (isGerente) {
			return gerenteDao.queryForId(id).User;
		} else {
			return funcionarioDao.queryForId(id).User;
		}
	}
	
	public String getName() {
		if (isGerente) {
			return gerenteDao.queryForId(id).Name;
		} else {
			return funcionarioDao.queryForId(id).Name;
		}
	}
	
	public String getEmail() {
		if (isGerente) {
			String strEmail = gerenteDao.queryForId(id).Email;
			return strEmail == null ? "Nenhum Email registrado." : strEmail ;
		} else {
			String strEmail = funcionarioDao.queryForId(id).Email;
			return strEmail == null ? "Nenhum Email registrado." : strEmail ;
		}

	}
	
	public static void startActivity(Activity activity, int id, Boolean isGerente) {
		Intent intent = new Intent(activity, AlterarCadastroActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("isGerente", isGerente);
		activity.startActivity(intent);
	}
	
	
}

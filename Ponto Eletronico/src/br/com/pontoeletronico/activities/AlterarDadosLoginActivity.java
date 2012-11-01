package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AlterarDadosLoginActivity extends BaseActivity {
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	EditText txtUser, txtOldPass, txtNewPass1, txtNewPass2;
	String strOldPass;
	int id, opcao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_dados_login);
		
		id = getIntent().getExtras().getInt("ID");
		opcao = getIntent().getExtras().getInt("Opcao");
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		LinearLayout linearUser = (LinearLayout) findViewById(R.id.altLogin_Linear_User);
		LinearLayout linearPassword = (LinearLayout) findViewById(R.id.altLogin_LinearPass);
		
		if (opcao == 0) {
			linearUser.setVisibility(LinearLayout.VISIBLE);
		} else if (opcao == 1) {
			linearPassword.setVisibility(LinearLayout.VISIBLE);
			strOldPass = funcionarioDao.queryForId(id).Password.toString();
		}
		
		
		
		txtUser = (EditText) findViewById(R.id.altLogin_NewUser);
		txtOldPass = (EditText) findViewById(R.id.altLogin_OldPass);
		txtNewPass1 = (EditText) findViewById(R.id.altLogin_NewPass1);
		txtNewPass2 = (EditText) findViewById(R.id.altLogin_NewPass2);
		
		Button btnSalvar = (Button) findViewById(R.id.altLogin_BtnSalvar);
		Button btnCancelar = (Button) findViewById(R.id.altLogin_BtnCancelar);
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (opcao == 0) {
					if (CodeSnippet.checkUser(getHelper(), txtUser.getText().toString())) {
						Funcionario funcionario = funcionarioDao.queryForId(id);
						funcionario.User = txtUser.getText().toString();
						confirmarUpdate(funcionario);
						
					} else {
						makeMyDearAlert(CodeSnippet.problemUser(getHelper(), txtUser.getText().toString()));
					}
					
				} else {
					if (CodeSnippet.checkPassword(txtNewPass1.getText().toString(), txtNewPass2.getText().toString())) {
						Funcionario funcionario = funcionarioDao.queryForId(id);
						funcionario.Password = txtNewPass1.getText().toString();
						confirmarUpdate(funcionario);
						
					} else {
						makeMyDearAlert(CodeSnippet.problemPassword(txtNewPass1.getText().toString(), txtNewPass2.getText().toString()));
					}
				}
				
			}
		});
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitActivityAlert("Você realmente deseja sair?");
				
			}
		});
		
	}
	
	public void confirmarUpdate(final Funcionario funcionario) {
		optionActivityAlert(new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				funcionarioDao.update(funcionario);
				finish();
				
			}
		}, "Salvar mudan�as?");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivityAlert("Voc� realmente deseja sair?");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
	public static void startActivity(Activity activity, int id,int opcao) {
		Intent intent = new Intent(activity, AlterarDadosLoginActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("Opcao", opcao);
		activity.startActivity(intent);
	}
	
}

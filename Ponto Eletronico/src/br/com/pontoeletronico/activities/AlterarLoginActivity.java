package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AlterarLoginActivity extends BaseActivity {
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	EditText txtUser, txtOldPass, txtNewPass1, txtNewPass2;
	String strOldPass;
	int id, opcao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_login);
		
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
					if (txtUser.length() > 0) {
						if (CodeSnippet.checkIfExistUser(getHelper(), txtUser.getText().toString())) {
							optionActivityAlert(new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Funcionario funcionario = funcionarioDao.queryForId(id);
									funcionario.Name = txtUser.getText().toString();
										
									funcionarioDao.update(funcionario);
									
									finish();
									
								}
							}, "Salvar Nome?");
						} else {
							makeMyDearAlert("Nome de usu‡rio ja existente!");
						}
						
					} else {
						makeMyDearAlert("Complete o campo Nome!");
					}
				} else {
					
				}
				
			}
		});
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitActivityAlert("VocÃª realmente deseja sair?");
				
			}
		});
		
	}
	
	public static void startActivity(Activity activity, int id,int opcao) {
		Intent intent = new Intent(activity, AlterarLoginActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("Opcao", opcao);
		activity.startActivity(intent);
	}
	
}

package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.FormValidator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionario = funcionarioDao.queryForId(id);
		
		LinearLayout linearUser = (LinearLayout) findViewById(R.id.altLogin_Linear_User);
		LinearLayout linearPassword = (LinearLayout) findViewById(R.id.altLogin_LinearPass);
		
		if (opcao == 0) {
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserUser));
			linearUser.setVisibility(LinearLayout.VISIBLE);
		} else if (opcao == 1) {
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserPassword));
			linearPassword.setVisibility(LinearLayout.VISIBLE);
			strOldPass = funcionarioDao.queryForId(id).Password.toString();
		}
	
		txtUser = (EditText) findViewById(R.id.altLogin_NewUser);
		txtOldPass = (EditText) findViewById(R.id.altLogin_OldPass);
		txtNewPass1 = (EditText) findViewById(R.id.altLogin_NewPass1);
		txtNewPass2 = (EditText) findViewById(R.id.altLogin_NewPass2);
		
		txtNewPass2.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (FormValidator.checkPassword(txtNewPass1.getText().toString(), txtNewPass2.getText().toString())) {
					funcionario.Password = txtNewPass1.getText().toString();
					confirmarUpdate();
					
				} else {
					makeMyDearAlert(FormValidator.problemPassword(txtNewPass1.getText().toString(), txtNewPass2.getText().toString()));
				}
				
				return false;
			}
		});
		
		txtUser.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (FormValidator.checkUser(getHelper(), txtUser.getText().toString())) {
					funcionario.User = txtUser.getText().toString();
					confirmarUpdate();
					
				} else {
					makeMyDearAlert(FormValidator.problemUser(getHelper(), txtUser.getText().toString()));
				}
				
				return false;
			}
		});
		
	}
	
	/**
	 * O metodo simplesmente mostra para usu‡rio um {@link AlertDialog} com uma menssagem perguntando
	 * se ele deseja alterar os dados de login dele.
	 * 
	 */
	public void confirmarUpdate() {
		
		
		optionActivityAlert(new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (FuncionarioController.updateFuncionario(getHelper(), funcionario)) {
					finish();
				} else {
					makeMyDearAlert(AlterarDadosLoginActivity.this.getString(R.string.str_Error_UpdateUser));
				}
				
				
			}
		}, AlterarDadosLoginActivity.this.getString(R.string.str_SaveChanges));
	}
	
	public static void startActivity(Activity activity, int id,int opcao) {
		Intent intent = new Intent(activity, AlterarDadosLoginActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("Opcao", opcao);
		activity.startActivity(intent);
	}
	
}

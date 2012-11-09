package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;
import br.com.pontoeletronico.util.ThreeButtonDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ConfirmarCadastroActivity extends BaseActivity {
	AutoCompleteTextView txtUserAdmin;
	EditText txtPasswordAdmin;
	String strUser, strPassword, strNome, strEmail, strEndereco, strTelefone;
	Funcionario userInfo;
	
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmar_cadastro);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		userInfo = (Funcionario) getIntent().getExtras().getSerializable("UserInfo");
		
		txtUserAdmin = (AutoCompleteTextView) findViewById(R.id.confCadastro_User);
		txtPasswordAdmin = (EditText) findViewById(R.id.confCadastro_Password);
		
		strUser = userInfo.User;
		strPassword = userInfo.Password;
		strNome = userInfo.Name;
		strEmail = userInfo.Email;
		strEndereco = userInfo.Adress;
		strTelefone = userInfo.Phone;
		
        txtUserAdmin.setAdapter(FuncionarioController.getArrayAdapter(getHelper(), this, 3));
		
		txtPasswordAdmin.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				onCreateFuncionario();
				
				return false;
			}
		});
	}
	
	public void btnCancelar_touch() {
		final ThreeButtonDialog alert = new ThreeButtonDialog(this);
		alert.setCancelable(false);
		alert.setTitle("Aviso");
		alert.setLabel("Voc� deseja voltar para qual tela?");
		alert.setFirstButtonLabel("Inicial");
		alert.setFirstButtonClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(1);
				finish();
				
			}
		});
		alert.setSecondButtonLabel("Cadastro");
		alert.setSecondButtonClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(0);
				finish();
				
			}
		});
		alert.setThirdButtonLabel("Cancelar");
		alert.setThirdButtonClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alert.ploft();
				
			}
		});
		
		alert.show();
	}
	
	public void onCreateFuncionario() {
		if (txtUserAdmin.length() > 0) {
			if (txtPasswordAdmin.length() > 0) {
				
				if (FuncionarioController.checkIfExistUserAndPassworl(getHelper(), txtUserAdmin.getText().toString(), txtPasswordAdmin.getText().toString())) {
					AlertDialog.Builder alert = new AlertDialog.Builder(ConfirmarCadastroActivity.this);
					alert.setCancelable(false).setTitle(ConfirmarCadastroActivity.this.getString(R.string.simpleWord_Aviso)).setMessage(ConfirmarCadastroActivity.this.getString(R.string.str_CreateUser_Complete)).setPositiveButton(ConfirmarCadastroActivity.this.getString(R.string.simpleWord_Funcionario), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (FuncionarioController.createFuncionario(getHelper(), new Funcionario(strUser, strPassword, strNome, strEmail, strEndereco, strTelefone, false))) {
								makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_CreateUser_Sucess));
								setResult(1);
								finish();
								
							} else {
								makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_CreateUser_Fail));
							}
							
						}
					});
					
					alert.setNegativeButton(ConfirmarCadastroActivity.this.getString(R.string.simpleWord_Gerente), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (FuncionarioController.createFuncionario(getHelper(), new Funcionario(strUser, strPassword, strNome, strEmail, strEndereco, strTelefone, true))) {
								makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_CreateUser_Sucess));
								setResult(1);
								finish();
								
							} else {
								makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_CreateUser_Fail));
							}
							
						}
					});
					
					alert.create().show();
					
					
				} else {
					makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_Error_FindUser));
				}
				
			} else {
				makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_SubTitle_Password));
			}
			
		} else {
			makeMyDearAlert(ConfirmarCadastroActivity.this.getString(R.string.str_SubTitle_User));
		}
	}
	
	public static void startActivity(Activity activity, Funcionario funcionario) {
		Intent confirmar = new Intent(activity, ConfirmarCadastroActivity.class);
		Bundle extras = new Bundle();
		extras.putSerializable("UserInfo", funcionario);
		confirmar.putExtras(extras);
		activity.startActivityForResult(confirmar, 1);
	}
	
}

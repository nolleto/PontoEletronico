package br.com.pontoeletronico.activities;

import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;
import br.com.pontoeletronico.util.ThreeButtonDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmarCadastroActivity extends BaseActivity {
	EditText txtUserAdmin, txtPasswordAdmin;
	String strUser, strPassword, strNome, strEmail, strEndereco, strTelefone;
	FuncionarioGerente userInfo;
	
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmar_cadastro);
		
		gerenteDao = getHelper().getGerenteRuntimeDao();
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		userInfo = (FuncionarioGerente) getIntent().getExtras().getSerializable("UserInfo");
		
		txtUserAdmin = (EditText) findViewById(R.id.confCadastro_User);
		txtPasswordAdmin = (EditText) findViewById(R.id.confCadastro_Password);
		
		strUser = userInfo.User;
		strPassword = userInfo.Password;
		strNome = userInfo.Name;
		strEmail = userInfo.Email;
		strEndereco = userInfo.Adress;
		strTelefone = userInfo.Phone;
		
		Button btn_Cadastrar = (Button) findViewById(R.id.confCadastro_Cadastrar);
		Button btn_Cancelar = (Button) findViewById(R.id.confCadastro_Cancelar);
		
		btn_Cadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (txtUserAdmin.length() > 0) {
					if (txtPasswordAdmin.length() > 0) {
						RuntimeExceptionDao<Gerente, Integer> gerenteDao = getHelper().getGerenteRuntimeDao();
						List<Gerente> gerentes = null;
						
						try {
							gerentes = gerenteDao.query(gerenteDao.queryBuilder()
									.where().eq("User", txtUserAdmin.getText().toString())
									.and().eq("Password", txtPasswordAdmin.getText().toString()).prepare());
							
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						if (gerentes.size() == 1) {
							AlertDialog.Builder alert = new AlertDialog.Builder(ConfirmarCadastroActivity.this);
							alert.setCancelable(false).setTitle("Aviso").setMessage("Cadastrar usu�rio como Gerente ou Funcion�rio?").setPositiveButton("Funcion�rio", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									saveFuncionario();
									
								}
							});
							
							alert.setNegativeButton("Gerente", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									saveGerente();
									
								}
							});
							
							alert.create().show();
							
							
						} else {
							makeMyDearAlert("Nenhum usu�rio com esse login encontrado.\nCetifique-se que seu Usu�rio e Senha estejam digitados corretamente.");
						}
						
						
					} else {
						makeMyDearAlert("Digite sua Senha!");
					}
					
				} else {
					makeMyDearAlert("Digite seu Usu�rio!");
				}
				
			}
		});
		
		btn_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnCancelar_touch();
				
			}
		});
		
	}
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        btnCancelar_touch();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
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
	
	public void saveGerente() {
		Gerente gerente = new Gerente(strNome, strUser, strPassword, strEmail, strEndereco, strTelefone);
		Boolean sucess = true;
		
		try {
			gerenteDao.create(gerente);
		} catch (Exception e) {
			sucess = false;
		}
		
		if (sucess) {
			makeMyDearAlert("Usu�rio criado com sucesso!");
			
			setResult(1);
			finish();
			
		} else {
			makeMyDearAlert("Erro ao criar usu�rio!!!");
		}
		
	}
	
	public void saveFuncionario() {
		Funcionario funcionario = new Funcionario(strUser, strPassword, strNome, strEmail, strEndereco, strTelefone);
		Boolean sucess = true;
		
		try {
			funcionarioDao.create(funcionario);
		} catch (Exception e) {
			sucess = false;
		}
		
		if (sucess) {
			makeMyDearAlert("Usu�rio criado com sucesso!");
			
			setResult(1);
			finish();
			
		} else {
			makeMyDearAlert("Erro ao criar usu�rio!!!");
		}
	}
	
}

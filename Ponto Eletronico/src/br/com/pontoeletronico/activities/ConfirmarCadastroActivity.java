package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
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
		
		Button btn_Cadastrar = (Button) findViewById(R.id.confCadastro_Cadastrar);
		Button btn_Cancelar = (Button) findViewById(R.id.confCadastro_Cancelar);
		
		List<Funcionario> listaA = null;
		try {
			listaA = funcionarioDao.query(funcionarioDao.queryBuilder()
					.orderBy("Name", true)
					.where().eq("isGerente", true)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<String> listaB = new ArrayList<String>();
        
        for (Funcionario funcionario : listaA) {
			listaB.add(funcionario.User);
		}
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaB);
        txtUserAdmin.setAdapter(adapter);
		
		
		btn_Cadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (txtUserAdmin.length() > 0) {
					if (txtPasswordAdmin.length() > 0) {
						
						if (CodeSnippet.checkIfExistUserAndPassworl(getHelper(), txtUserAdmin.getText().toString(), txtPasswordAdmin.getText().toString())) {
							AlertDialog.Builder alert = new AlertDialog.Builder(ConfirmarCadastroActivity.this);
							alert.setCancelable(false).setTitle("Aviso").setMessage("Cadastrar usu�rio como Gerente ou Funcion�rio?").setPositiveButton("Funcion�rio", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									saveFuncionario(false);
									
								}
							});
							
							alert.setNegativeButton("Gerente", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									saveFuncionario(true);
									
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
	
	public void saveFuncionario(Boolean isGerente) {
		Funcionario funcionario = new Funcionario(strUser, strPassword, strNome, strEmail, strEndereco, strTelefone, isGerente);
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
	
	public static void startActivity(Activity activity, Funcionario funcionario) {
		Intent confirmar = new Intent(activity, ConfirmarCadastroActivity.class);
		Bundle extras = new Bundle();
		extras.putSerializable("UserInfo", funcionario);
		confirmar.putExtras(extras);
		activity.startActivityForResult(confirmar, 1);
	}
	
}

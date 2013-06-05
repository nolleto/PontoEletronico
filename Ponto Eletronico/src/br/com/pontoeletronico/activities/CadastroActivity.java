package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.FormValidator;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CadastroActivity extends BaseActivity {
	EditText txtUser, txtSenha1, txtSenha2, txtNome, txtEmail, txtEndereco, txtTelefone;
	
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = getHelper().getFuncionarioRuntimeDao();
	Funcionario novoFuncionario = new Funcionario();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setTitleInActionBar(this.getString(R.string.activity_Cadastro));
		//scrollMain = (ScrollView) findViewById(R.id.cadastro_Scroll);
		
		txtUser = (EditText) findViewById(R.id.cadastro_UserName);
		txtSenha1 = (EditText) findViewById(R.id.cadastro_password1);
		txtSenha2 = (EditText) findViewById(R.id.cadastro_password2);
		txtNome = (EditText) findViewById(R.id.cadastro_Nome);
		txtEmail = (EditText) findViewById(R.id.cadastro_Email);
		txtEndereco = (EditText) findViewById(R.id.cadastro_Endereco);
		txtTelefone = (EditText) findViewById(R.id.cadastro_Telefone);
		
		
		txtUser.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_UserName);
				
				if (FormValidator.checkUser(getHelper() ,txtUser.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);
					
				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(FormValidator.problemUser(getHelper() ,txtUser.getText().toString()));
					
					return true;
				}
				
				return false;
			}
		});
		
		txtSenha1.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Password);
				
				if (txtSenha2.length() > 0) {
					if (FormValidator.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
						info.setVisibility(LinearLayout.GONE);
						
					} else {
						info.setVisibility(LinearLayout.VISIBLE);
						info.setText(FormValidator.problemPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString()));

						return true;
						
					}
				}
					
				return false;
			}
		});
		
		txtSenha2.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Password);
				
				if (txtSenha1.length() > 0) {
					if (FormValidator.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
						info.setVisibility(LinearLayout.GONE);
						
					} else {
						info.setVisibility(LinearLayout.VISIBLE);
						info.setText(FormValidator.problemPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString()));
					
						return true;
					}
				}

				return false;
			}
		});
		
		txtNome.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Nome);
				
				if (txtNome.length() > 0) {
					info.setVisibility(LinearLayout.GONE);

				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(CadastroActivity.this.getString(R.string.str_Error_FieldUser));
					
					return true;
				}
				
				return false;
			}
		});
		
		txtEmail.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Email);
				
				if (FormValidator.checkEmail(txtEmail.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);

				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(FormValidator.problemEmail(txtEmail.getText().toString()));
					
					return true;
				}
				
				return false;
			}
		});
		
		txtEndereco.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Endereco);
				
				if (txtEndereco.length() > 0) {
					info.setVisibility(LinearLayout.GONE);

				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(CadastroActivity.this.getString(R.string.str_Error_FieldAdress));
					
					return true;
				}
				
				return false;
			}
		});
		
		txtTelefone.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Telefone);
				
				if (FormValidator.checkPhone(txtTelefone.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);

				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(FormValidator.problemPhone(txtTelefone.getText().toString()));
					
					return true;
				}
				return false;
			}
		});
		
	}
	
	/**
	 * Verifica todos os campos do cadastro, caso exista campos que não foram verificados ainda, o metodo irá 
	 * verificar ou se o campo foi alterado, ele irá verificar novamente.
	 * <br/>
	 * <br/>Campos:
	 * <br/><br/>	Usuário
	 * <br/>		Senha
	 * <br/>		E-mail
	 * <br/>		Endereço
	 * <br/>		Telefone
	 * 
	 */
	public void refreshAllFields() {
		if (txtUser != null && !txtUser.getText().toString().isEmpty() && FormValidator.checkUser(getHelper(), txtUser.getText().toString())) {
			novoFuncionario.User = txtUser.getText().toString();
		}  else {
			novoFuncionario.User = null;
		}
		if (txtSenha1 != null && !txtSenha1.getText().toString().isEmpty() && FormValidator.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
			novoFuncionario.Password = txtSenha1.getText().toString();
		}  else {
			novoFuncionario.Password = null;
		} 
		if (txtNome != null && !txtNome.getText().toString().isEmpty() && txtNome.getText().toString().length() > 0) {
			novoFuncionario.Name = txtNome.getText().toString();
		}  else {
			novoFuncionario.Name = null;
		}
		if (txtEmail != null && !txtEmail.getText().toString().isEmpty() && FormValidator.checkEmail(txtEmail.getText().toString())) {
			novoFuncionario.Email = txtEmail.getText().toString();
		}  else {
			novoFuncionario.Email = null;
		}
		if (txtEndereco != null && !txtEndereco.getText().toString().isEmpty() && txtEndereco.getText().toString().length() > 0) {
			novoFuncionario.Adress = txtEndereco.getText().toString();
		}  else {
			novoFuncionario.Adress = null;
		}
		if (txtTelefone != null && !txtTelefone.getText().toString().isEmpty() && FormValidator.checkPhone(txtTelefone.getText().toString())) {
			novoFuncionario.Phone = txtTelefone.getText().toString();
		}  else {
			novoFuncionario.Phone = null;
		}
	}
	
	/**
	 * Continua o processo de codastrar um funcionário, nesse metodo ele irá reconfirmar os campos
	 * digitados, e logo em seguida ele irá tomar a ação mais adequada.
	 * 
	 */
	public void continuarCadastro() {
		refreshAllFields();
		
		if (checkAllFields()) {
			ConfirmarCadastroActivity.startActivity(this, novoFuncionario);
		} else if (checkEssentialFields()) {
			String message = this.getString(R.string.str_CreateUser_CompleteFieldsBegin);

			message = novoFuncionario.Email == null ? message + "\nE-mail" : message;
			message = novoFuncionario.Adress == null ? message + "\n" + this.getString(R.string.simpleWord_Endereco) : message;
			message = novoFuncionario.Phone == null ? message + "\nTelefone" : message;
			
			message = message + "\n" + this.getString(R.string.str_CreateUser_CompleteFieldsEnd);
			
			optionActivityAlert(new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ConfirmarCadastroActivity.startActivity(CadastroActivity.this, novoFuncionario);
					
				}
			}, message);
			
		} else {
			String message = this.getString(R.string.str_CreateUser_CompleteFieldsBeginError) + "\n" + this.getString(R.string.simpleWord_Obrigatorios);
			
			message = novoFuncionario.User == null ? message + "\n" + this.getString(R.string.simpleWord_Usuario) : message;
			message = novoFuncionario.Password == null ? message + "\n" + this.getString(R.string.simpleWord_Senha) : message;
			message = novoFuncionario.Name == null ? message + "\n" + this.getString(R.string.simpleWord_Nome) : message;
			
			message = message + "\n\n" + this.getString(R.string.simpleWord_Restante);
			
			message = novoFuncionario.Email == null ? message + "\nE-mail" : message;
			message = novoFuncionario.Adress == null ? message + "\n" + this.getString(R.string.simpleWord_Endereco) : message;
			message = novoFuncionario.Phone == null ? message + "\nTelefone" : message;
			
			message = message + "\n" + this.getString(R.string.str_CreateUser_CompleteFieldsEndError);
			
			makeMyDearAlert(message);
		}
		
	}
	
	private boolean checkAllFields() {
		if (checkEssentialFields() && novoFuncionario.Email != null && novoFuncionario.Adress != null && novoFuncionario.Phone != null) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkEssentialFields() {
		if (novoFuncionario.User != null && novoFuncionario.Password != null && novoFuncionario.Name != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        	case android.R.id.home:
        		backActivity();

	            return true;
        	  
        	case R.id.actionCadastro_CreateUser:
        		continuarCadastro();
        		
            	break;
            	
			}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public static void startActivity(Activity activity) {
		Intent telaCadastro = new Intent(activity, CadastroActivity.class);
		activity.startActivity(telaCadastro);
	}
}

package br.com.pontoeletronico.activities;

import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CadastroActivity extends BaseActivity {
	EditText txtUser, txtSenha1, txtSenha2, txtNome, txtEmail, txtEndereco, txtTelefone;
	ScrollView scrollMain;
	Boolean user, password, nome, email, endereco,telefone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		
		scrollMain = (ScrollView) findViewById(R.id.cadastro_Scroll);
		
		txtUser = (EditText) findViewById(R.id.cadastro_UserName);
		txtSenha1 = (EditText) findViewById(R.id.cadastro_password1);
		txtSenha2 = (EditText) findViewById(R.id.cadastro_password2);
		txtNome = (EditText) findViewById(R.id.cadastro_Nome);
		txtEmail = (EditText) findViewById(R.id.cadastro_Email);
		txtEndereco = (EditText) findViewById(R.id.cadastro_Endereco);
		txtTelefone = (EditText) findViewById(R.id.cadastro_Telefone);
		
		user = password = nome = email = endereco = telefone = false;
		
		txtUser.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_UserName);
				
				if (CodeSnippet.checkUser(getHelper() ,txtUser.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);
					
					user = true;
					
				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(CodeSnippet.problemUser(getHelper() ,txtUser.getText().toString()));
					user = false;
				}
				
				if (user == false) {
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
					if (CodeSnippet.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
						info.setVisibility(LinearLayout.GONE);
						password = true;
						
					} else {
						info.setVisibility(LinearLayout.VISIBLE);
						info.setText(CodeSnippet.problemPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString()));
						password = false;
						
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
					if (CodeSnippet.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
						info.setVisibility(LinearLayout.GONE);
						password = true;
						
					} else {
						info.setVisibility(LinearLayout.VISIBLE);
						info.setText(CodeSnippet.problemPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString()));
						password = false;
					
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
					nome = true;
				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText("O campo Nome est‡ em branco");
					nome = false;
					
					return true;
				}
				
				return false;
			}
		});
		
		txtEmail.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Email);
				
				if (CodeSnippet.checkEmail(txtEmail.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);
					email = true;

				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(CodeSnippet.problemEmail(txtEmail.getText().toString()));
					email = false;
					
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
					endereco = true;
				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText("O campo Endereo est‡ em branco");
					endereco = false;
					
					return true;
				}
				
				return false;
			}
		});
		
		txtTelefone.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				TextView info = (TextView) findViewById(R.id.text_Telefone);
				
				if (CodeSnippet.checkPhone(txtTelefone.getText().toString())) {
					info.setVisibility(LinearLayout.GONE);
					telefone = true;
				} else {
					info.setVisibility(LinearLayout.VISIBLE);
					info.setText(CodeSnippet.problemPhone(txtTelefone.getText().toString()));
					telefone = false;
					
					return true;
				}
				return false;
			}
		});
		
		Button btnSalvar = (Button) findViewById(R.id.cadastro_BtnSalvar);
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnSalvar_touch();
				
			}
		});
		
		Button btnCancelar = (Button) findViewById(R.id.cadastro_BtnCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnCancelar_touch();
				
			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        btnCancelar_touch();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
	public void checkAllFields() {
		if (!user && CodeSnippet.checkUser(getHelper(), txtUser.getText().toString())) {
			user = true;
		} 
		if (!password && CodeSnippet.checkPassword(txtSenha1.getText().toString(), txtSenha2.getText().toString())) {
			password = true;
		}
		if (!nome && txtNome.getText().toString().length() > 0) {
			nome = true;
		}
		if (!email && CodeSnippet.checkEmail(txtEmail.getText().toString())) {
			email = true;
		}
		if (!endereco && txtEndereco.getText().toString().length() > 0) {
			endereco = true;
		}
		if (!telefone && CodeSnippet.checkPhone(txtTelefone.getText().toString())) {
			telefone = true;
		}
	}
	
	public void btnSalvar_touch() {
		checkAllFields();
		
		if (user && password && nome && email && endereco && telefone) {
			openConfirmarCadastro();
		} else {
			if (user && password && nome) {
				String message = "Os campos:\n\n";
				
				message = email == false ? message + "Email\n" : message;
				message = endereco == false ? message + "Endereï¿½o\n" : message;
				message = telefone == false ? message + "Telefone\n" : message;
				
				message = message + "\nEstï¿½o vazios ou invï¿½lidos.\nContinuar mesmo assim?";
				
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setCancelable(false).setTitle("Aviso").setMessage(message).setPositiveButton("Continuar", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						openConfirmarCadastro();
						
					}
				});
				alert.setNegativeButton("Cancelar",	new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				
				alert.create().show();
				
			} else {
				String message = "Complete correteamente pelo menos os Campos:\n\n";
				
				message = user == false ? message + "Usuï¿½rio para Login\n" : message;
				message = password == false ? message + "Senha\n" : message;
				message = nome == false ? message + "Nome\n" : message;
				
				message = message + "\nPara criar um novo cadastro.";
				
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setCancelable(false).setTitle("Aviso").setMessage(message).setNeutralButton("OK", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				
				alert.create().show();
			}
		}
	}
	
	public void btnCancelar_touch() {
		exitActivityAlert("VocÃª realmente deseja cancelar o cadastro?");
	
	}
	
	public void openConfirmarCadastro() {
		
		if (!CodeSnippet.checkIfExistUser(getHelper(), txtUser.getText().toString())) {
			String strUser, strPassword, strNome, strEmail, strEndereco, strTelefone;
			
			strUser = txtUser.getText().toString();
			strPassword = txtSenha1.getText().toString();
			strNome = txtNome.getText().toString();
			strEmail = txtEmail.getText().toString();
			strEndereco = txtEndereco.getText().toString();
			strTelefone = txtTelefone.getText().toString();
						
			Funcionario funcionario = new Funcionario(strUser, strPassword, strNome, strEmail, strEndereco, strTelefone);
			
			ConfirmarCadastroActivity.startActivity(CadastroActivity.this, funcionario);
			
		} else {
			makeMyDearAlert("Nome de Usuï¿½rio ja cadastrado!!!");
		}
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

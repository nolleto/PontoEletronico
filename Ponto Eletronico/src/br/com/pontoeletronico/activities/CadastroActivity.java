package br.com.pontoeletronico.activities;

import java.security.PublicKey;
import java.util.List;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;

import android.R.bool;
import android.R.string;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
		
		txtUser.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_UserName);
				ObjectAnimator fade = null;
				user = false;
				
				if (!hasFocus) {
					
					String expression = "^[a-z0-9]*$";
				    CharSequence inputStr = txtUser.getText().toString();

				    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
				    Matcher matcher = pattern.matcher(inputStr);
					
					if (txtUser.length() >= 4 && txtUser.length() <= 25 && matcher.matches()) {
						//((TextView) findViewById(R.id.text_UserName)).setVisibility(LinearLayout.GONE);
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
						user = true;

					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
						
					}
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();	
			}	
		});
		
		txtSenha1.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Password);
				ObjectAnimator fade = null;
				password = false;
				String senha1 = txtSenha1.getText().toString();
				String senha2 = txtSenha2.getText().toString();
				
				if (!hasFocus) {
					if (txtSenha1.length() >= 8 && txtSenha1.length() <= 20) {
						if (txtSenha2.length() > 0) {
							if (senha1.equals(senha2)) {
								password = true;
								fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
							} else {
								fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
								textInfo.setText("Senhas n�o coincidem");
							}
						} else {
							fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
						}
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
					
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
						textInfo.setText("A senha deve conter entre 8 a 20 caracteres");
						
					}
				} else {
					//int altura1 = txtSenha2.getHeight();
					//int altura2 = ((TextView) findViewById(R.id.text_Password)).getHeight();
						
					//scrollMain.scrollBy(0, altura1 + altura2 + txtSenha1.getHeight());
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
			}
		});
		
		txtSenha2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Password);
				ObjectAnimator fade = null;
				password = false;
				String senha1 = txtSenha1.getText().toString();
				String senha2 = txtSenha2.getText().toString();
				
				if (!hasFocus) {
					if (senha1.equals(senha2)) {
				
						if (txtSenha2.length() >= 8 && txtSenha2.length() <= 20) {
							fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
							password = true;
						
						} else {
							fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
							textInfo.setText("A senha deve conter entre 8 a 20 caracteres");
						
						}
					
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
						textInfo.setText("Senhas n�o coincidem");
					}
				} else {
					//scrollMain.scrollBy(0, txtSenha2.getHeight() + ((TextView) findViewById(R.id.text_Password)).getHeight());
				}			
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
			}
		});
		
		txtNome.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Nome);
				ObjectAnimator fade = null;
				nome = false;
				
				if (!hasFocus) {
					if (txtNome.length() > 0) {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
						nome = true;
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
					}
				} else {
					//scrollMain.scrollBy(0, txtNome.getHeight() + ((TextView) findViewById(R.id.text_Nome)).getHeight());
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
			}
		});
		
		txtEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Email);
				ObjectAnimator fade = null;
				email = false;
				
				if (!hasFocus) {
					if (txtEmail.length() > 0) {
						
						/*String expression = "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
					    CharSequence inputStr = txtUser.getText().toString();

					    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
					    Matcher matcher = pattern.matcher(inputStr);
						
					    if (matcher.matches()) {*/
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
					    	email = true;
						/*} else {
							((TextView) findViewById(R.id.text_Email)).setVisibility(LinearLayout.VISIBLE);
						}*/
						
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
					}
				} else {
					//scrollMain.scrollBy(0, txtEmail.getHeight() + ((TextView) findViewById(R.id.text_Email)).getHeight());
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
			}
		});
		
		txtEndereco.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Endereco);
				ObjectAnimator fade = null;
				endereco = false;
				
				if (!hasFocus) {
					if (!hasFocus && txtEndereco.length() > 0) {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
						endereco = true;
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
					}
				} else {
					//scrollMain.scrollBy(0, txtEndereco.getHeight() + ((TextView) findViewById(R.id.text_Endereco)).getHeight());
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
			}
		});
		
		txtTelefone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				TextView textInfo = (TextView) findViewById(R.id.text_Telefone);
				ObjectAnimator fade = null;
				telefone = false;
				
				if (!hasFocus) {
					if (!hasFocus && txtTelefone.length() > 8) {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 0f).setDuration(2000);
						telefone = true;
					} else {
						fade = ObjectAnimator.ofFloat(textInfo, "alpha", 100f).setDuration(2000);
					}
				} else {
					//scrollMain.scrollBy(0, txtTelefone.getHeight() + ((TextView) findViewById(R.id.text_Telefone)).getHeight());
				}
				
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.play(fade);
			    animatorSet.start();
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
	
	
	
	public void btnSalvar_touch() {
		TextView textInfo = (TextView) findViewById(R.id.text_Nome);
		textInfo.setFocusableInTouchMode(true);
		textInfo.requestFocus();
		
		if (user && password && nome && email && endereco && telefone) {
			openConfirmarCadastro();
		} else {
			if (user && password && nome) {
				String message = "Os campos:\n\n";
				
				message = email == false ? message + "Email\n" : message;
				message = endereco == false ? message + "Endere�o\n" : message;
				message = telefone == false ? message + "Telefone\n" : message;
				
				message = message + "\nEst�o vazios ou inv�lidos.\nContinuar mesmo assim?";
				
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
				
				message = user == false ? message + "Usu�rio para Login\n" : message;
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
		
		textInfo.setFocusableInTouchMode(false);
	}
	
	public void btnCancelar_touch() {
		
		exitActivityAlert("Você realmente deseja cancelar o cadastro?");
		
		/*AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false).setMessage("Você realmente deseja cancelar o cadastro?").setTitle("Aviso").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		});
		alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		
		alert.create().show();*/
	}
	
	public void openConfirmarCadastro() {
		List<Gerente> gerenteList = getHelper().getGerenteRuntimeDao().queryForEq("User", txtUser.getText().toString());
		List<Funcionario> funcionarioList = getHelper().getFuncionarioRuntimeDao().queryForEq("User", txtUser.getText().toString());
		
		if (gerenteList.size() < 1 && funcionarioList.size() < 1) {
			String strUser, strPassword, strNome, strEmail, strEndereco, strTelefone;
			
			strUser = txtUser.getText().toString();
			strPassword = txtSenha1.getText().toString();
			strNome = txtNome.getText().toString();
			strEmail = txtEmail.getText().toString();
			strEndereco = txtEndereco.getText().toString();
			strTelefone = txtTelefone.getText().toString();
			
			FuncionarioGerente cadastroInfo = new FuncionarioGerente(strNome, strEmail, strUser, strPassword, strEndereco, strTelefone);
			
			Intent confirmar = new Intent(CadastroActivity.this, ConfirmarCadastroActivity.class);
			Bundle extras = new Bundle();
			extras.putSerializable("UserInfo", cadastroInfo);
			confirmar.putExtras(extras);
			
			startActivityForResult(confirmar, 1);
		} else {
			makeMyDearAlert("Nome de Usu�rio ja cadastrado!!!");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

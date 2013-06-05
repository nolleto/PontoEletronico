package br.com.pontoeletronico.activities;

import java.util.Date;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.AlertWithEditText;
import br.com.pontoeletronico.util.AlertWithEditText.OnConfirmationEventListener;
import br.com.pontoeletronico.util.CodeSnippet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class PontoEletronicoActivity extends BaseActivity {
    /** Called when the activity is first created. */
	RuntimeExceptionDao<Funcionario,Integer> funcionarioDao;
	AutoCompleteTextView txtUser;
	TextView txtPassword;
	int optionOnEnterUser;
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Toast.makeText(this, "Sample Action Bar Menu Example  - ", Toast.LENGTH_SHORT).show();
		
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //DEBUG
        Button btn_Debug = (Button) findViewById(R.id.btn_create_users_debug);
        /*btn_Debug.setText("Star broadCast");
        btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAlert(null);
				
			}
		});*/
        btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShowLoad();
				
				AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
					
					@Override
					protected Void doInBackground(Void... params) {
						createUsersDEBUG();
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						PontoEletronicoActivity.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								HideLoad();
								makeMyDearAlert("Usu‡rios Criados");
								
							}
						});
						super.onPostExecute(result);
					}
				};
				asyncTask.execute();
				
			}
		});
        //btn_Debug.setVisibility(LinearLayout.GONE);
        
        /*btn_Debug.setText("Enviar Email");
        btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Mail mail = new Mail("exemplo01012010@gmail.com", "Teste91testE");
				
				String[] toArr = {"felipe.nolleto@digitaldesk.com.br","felipenolletonascimento@gmail.com"};
				mail.setTo(toArr);			
				mail.setFrom("felipe.nolleto@terra.com.br");
				mail.setSubject("Android Email");
				mail.setBody("If you received this Email, you're a locky bastard man.\n\nCongratulations.");
				
				try { 
			        if(mail.send()) { 
			          Toast.makeText(PontoEletronicoActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
			        } else { 
			          Toast.makeText(PontoEletronicoActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
			        } 
			      } catch(Exception e) { 
			        Toast.makeText(PontoEletronicoActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
			        Log.e("MailApp", "Could not send email", e); 
			      } 
			}
		});*/
        
        /*btn_Debug.setText("Criar Excel File.");
        btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExcelFile.CreateFile(getHelper());
	
			}
		});*/
        
        CodeSnippet.initDB(getHelper());
        
        //CodeSnippet.checkOutBroadCastReceiver(getApplicationContext(), getHelper());
       
        txtUser = (AutoCompleteTextView) findViewById(R.id.main_userName);        
        txtPassword = (TextView) findViewById(R.id.main_Password);
        
        funcionarioDao = getHelper().getFuncionarioRuntimeDao();   
        
        txtPassword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				ShowLoad();
				enterWithUser(true);
				
				return false;
			}
		});
     
    }
    
    /**
     * Verifica se o usu‡rio relamente existe e se o Nome us Usu‡rio e Senha est‡ correto, sen‹o ir‡ aparecer uma {@link AlertDialog} com a
     * mensagem do erro.
     * 
     * @param inThread -
     * 		
     */
    private void enterWithUser(Boolean inThread) {
		if (inThread) {
			
			AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
				
				@Override
				protected Void doInBackground(Void... params) {
					if (FuncionarioController.checkIfExistUser(getHelper(), txtUser.getText().toString())){
						funcionario = funcionarioDao.queryForEq("User", txtUser.getText().toString()).get(0);
						
						if (FuncionarioController.checkIfExistUserAndPassworl(getHelper(), txtUser.getText().toString(), txtPassword.getText().toString())) {
							optionOnEnterUser = 1;
							//TelaFuncionarioActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
						} else {
							optionOnEnterUser = 2;
							//makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.str_Error_Password));
							
						}
						
					} else {
						optionOnEnterUser = 3;
						//makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.simpleWord_Usuario) + " " + txtUser.getText().toString() + " " + PontoEletronicoActivity.this.getString(R.string.simpleWord_NaoExiste) + "!");
							
					}
					
					PontoEletronicoActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							HideLoad();
							if (optionOnEnterUser == 1) {
								TelaFuncionarioActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
							} else if (optionOnEnterUser == 2) {
								makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.str_Error_Password));
							} else if (optionOnEnterUser == 3) {
								makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.simpleWord_Usuario) + " " + txtUser.getText().toString() + " " + PontoEletronicoActivity.this.getString(R.string.simpleWord_NaoExiste) + "!");
							}
							
							
						}
					});
					
					return null;
				}
			};
			asyncTask.execute();
			
		} else {
			if (FuncionarioController.checkIfExistUser(getHelper(), txtUser.getText().toString())){
				Funcionario funcionario = funcionarioDao.queryForEq("User", txtUser.getText().toString()).get(0);
				
				if (FuncionarioController.checkIfExistUserAndPassworl(getHelper(), txtUser.getText().toString(), txtPassword.getText().toString())) {
					TelaFuncionarioActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
					
				} else {
					makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.str_Error_Password));
				}
				
			} else {
				makeMyDearAlert(PontoEletronicoActivity.this.getString(R.string.simpleWord_Usuario) + " " + txtUser.getText().toString() + " " + PontoEletronicoActivity.this.getString(R.string.simpleWord_NaoExiste) + "!");
			}
		}
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	Configuracoes config = getHelper().getConfiguracoesRuntimeDao().queryForId(1);
    	
    	if (config.titleApk == null || config.titleApk.length() == 0) {
    		AlertWithEditText alert = new AlertWithEditText(this, getHelper(),"Titulo da aplica‹o", new OnConfirmationEventListener() {

				@Override
				public void onButtonClickListener(String editText, Button button, Dialog dialog) {
					if (editText.length() > 0) {
						Configuracoes config = getHelper().getConfiguracoesRuntimeDao().queryForId(1);
						config.titleApk = editText;
						getHelper().getConfiguracoesRuntimeDao().update(config);
						setTitleInActionBar(editText);
						
						Toast.makeText(PontoEletronicoActivity.this, PontoEletronicoActivity.this.getString(R.string.str_Info_TitleApk) + " " + editText, Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
					
				}

			});
    		alert.setCancelable(false);
        	alert.show();
			return;
		}
    	
    	setTitleInActionBar(config.titleApk);
    	
        txtUser.setAdapter(FuncionarioController.getArrayAdapter(getHelper(), this, 1));
    	
    }
    
    private void createUsersDEBUG() {
    	Funcionario admin = FuncionarioController.getFuncionarioForId(getHelper(), 1);
    	
    	if (FuncionarioController.getFuncionarioWithUserAndPassword(getHelper(), "joao5", "1234") == null) {
	    	for (int i = 1; i <= 100; i++) {
				Funcionario func = null;
				String myPassword = "1234";
				
				if ((i % 2) == 0) {
					func = new Funcionario("user" + i, myPassword, "User " + i, null, "Rua Bartolomeu\n Novo Hamburgo", null, false);
				} else if ((i % 3) == 0) {
					func = new Funcionario("usuario" + i, myPassword, "Usu‡rio Teste da Silva Sauro " + i, true);
				} else if ((i % 5) == 0) {
					func = new Funcionario("joao" + i, myPassword, "Jo‹o Henrique " + i, "joao_nh" + i + "@hotmail.com", "Rua craktos\nNumero 3946\nEstacia Nova", "(52) 62" + i + "85465", false);
				} else if ((i % 7) == 0) {
					func = new Funcionario("gerente" + i, myPassword, "Gerente Dion’sio " + i, "cablocomaneiro" + i + "@gmail.com", null, null, true);
				} else {
					func = new Funcionario("funcionario" + i, myPassword, "Funcionario Le‹o Correia Lima da Silva Monteiro dos Santos " + i, false);
				}
				
				func.DateCreated = new Date();
				func.GerenteDelegate = admin;
				funcionarioDao.createIfNotExists(func);
			}
    	}
    	txtUser.setAdapter(FuncionarioController.getArrayAdapter(getHelper(), this, 1));
    }
    
}
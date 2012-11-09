package br.com.pontoeletronico.activities;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.DaoProvider;


public class BaseActivity extends Activity{
	private DaoProvider databaseHelper = null;
	private Boolean showExitMessage = true;
	
	@Override
	protected void onDestroy() {
	        super.onDestroy();
	        if (databaseHelper != null) {
                OpenHelperManager.releaseHelper();
                databaseHelper = null;
            }
	        
//	        if (((MusiCoolApp) this.getApplication()).getHelper() != null) {
//	                OpenHelperManager.releaseHelper();
//	                ((MusiCoolApp) this.getApplication()).setNullHelper();
//	        }
	}

	public DaoProvider getHelper() {
		 if (databaseHelper == null) { 
	          databaseHelper = OpenHelperManager.getHelper(this.getApplication(), DaoProvider.class);
	      }
	      return databaseHelper;
//	        return ((MusiCoolApp) this.getApplication()).getHelper();
	}
	
	/**
	 * Exibe uma mensagem via dialogo ({@link AlertDialog}), onde irá ser exibido uma mensagem informativa para o usuário.
	 * 
	 * @param message -	
	 * 		Mensagem que o dialogo irá exibir. 
	 */
	public void makeMyDearAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false).setMessage(message).setTitle("Aviso").setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		
		alert.create().show();
	}
	
	/**
	 * Exibe uma mengasem via dialogo ({@link AlertDialog}) como o objetivo de voltar para a {@link Activity} anterior, onde irá receber uma mensagem 'Sim', cuja a 
	 * ação será {@link Activity#finish()}, caso contrário, irá dar um {@link AlertDialog#dismiss()}.
	 * 
	 * @param message -	
	 * 		Mensagem que o dialogo irá exibir. 
	 */
	public void exitActivityAlert(String message) {
		optionActivityAlert(new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		}, message);
	}
	
	/**
	 * Exibe uma mengasem via dialogo ({@link AlertDialog}), onde irá receber uma mensagem e um {@link OnClickListener}, que será a ação 
	 * realizada, caso o usuário pressione 'Sim', caso contrário, irá dar um {@link AlertDialog#dismiss()}.
	 * 
	 * @param yesClickListner -
	 * 		Ação a ser tomada caso o usuário concorde/pressione Sim.
	 * @param message -	
	 * 		Mensagem de aviso que o dialogo irá exibir.
	 */
	public void optionActivityAlert(DialogInterface.OnClickListener yesClickListner ,String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false).setMessage(message).setTitle("Aviso").setNegativeButton("Não", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		})
		.setPositiveButton("Sim", yesClickListner);
		
		alert.create().show();		
	}
	
	/**
	 * Exibe uma mensagem para o usuário perguntando se ele quer voltar para activity anterior ou se for a primeira
	 * activity, se ele quer sair da aplicação.
	 * 
	 */
	public void backActivity() {
		if (this instanceof PontoEletronicoActivity) {
			exitActivityAlert(this.getString(R.string.exit_App));
		}  else if (this instanceof CadastroActivity) {
			exitActivityAlert(this.getString(R.string.exit_Cadastro));
		} else {
			//exitActivityAlert(this.getString(R.string.exit_Activity));
			finish();
		}
	}
	
	/**
	 * Coloca um nome na {@link ActionBar} da {@link Activity} atual.
	 * 
	 * @param string - 
	 * 		Titulo da {@link ActionBar}.
	 */
	public void setTitleInActionBar(String string) {
		/*TextView txtActionBar = (TextView) findViewById(Resources.getSystem().getIdentifier("action_bar_title", "id", "android"));
		txtActionBar.setText(string);*/
		
		getActionBar().setTitle(string);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && showExitMessage) {
			backActivity();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (this instanceof CadastroActivity) {
			new MenuInflater(this).inflate(R.layout.action_bar_cadastro, menu);
		} else if (this instanceof PontoEletronicoActivity) {
			new MenuInflater(this).inflate(R.layout.action_bar, menu);
		} else if (this instanceof GerenciarFuncionariosActivity) {
			new MenuInflater(this).inflate(R.layout.action_bar_gerenciar, menu);
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        	case android.R.id.home:
        		if ((this instanceof PontoEletronicoActivity) == false) {			
		            // This is called when the Home (Up) button is pressed
		            // in the Action Bar.
		            /*Intent parentActivityIntent = new Intent(this, PontoEletronicoActivity.class);
		            parentActivityIntent.addFlags(
		                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
		                    Intent.FLAG_ACTIVITY_NEW_TASK);
		            startActivity(parentActivityIntent);*/
		            backActivity();
        		}
	            return true;
        		
	            
        	case R.id.action_AddUser:
	            CadastroActivity.startActivity(this);
        		
            	break;
	            
        	case R.id.action_Lista_Users:
        		ListaContasActivity.startActivity(this);
        		
            	break;
            	
			}
		return super.onOptionsItemSelected(item);
	}
	
}

package br.com.pontoeletronico.activities;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import br.com.pontoeletronico.database.DaoProvider;


public class BaseActivity extends Activity{
	private DaoProvider databaseHelper = null;
	
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
	
	public void exitActivityAlert(String message) {
		optionActivityAlert(new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		}, message);
	}
	
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
	
}

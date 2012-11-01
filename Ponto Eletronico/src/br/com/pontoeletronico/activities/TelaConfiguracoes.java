package br.com.pontoeletronico.activities;

import java.util.Calendar;
import java.util.Date;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.util.CodeSnippet;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TelaConfiguracoes extends BaseActivity {
	EditText txtTitleApk, txtPhone, txtEmail;
	Button btnCheckIn, btnCheckOut;
	ToggleButton btnToggleEmail, btnTogglePhone;
	RuntimeExceptionDao<Configuracoes, Integer> configuracoesDao;
	Configuracoes configuracoesNaoSalvas;
	Date dateCheckOut;
	Boolean checkInSelected, titleApk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes);
		
		configuracoesDao = getHelper().getConfiguracoesRuntimeDao();
		configuracoesNaoSalvas = configuracoesDao.queryForId(1);
		
		txtTitleApk = (EditText) findViewById(R.id.config_txtTitleApk);
		txtEmail = (EditText) findViewById(R.id.config_txtEmail);
		txtPhone = (EditText) findViewById(R.id.config_txtPhone);
		txtTitleApk.clearFocus();
		CodeSnippet.closeKeyboard(this);
		
		btnCheckIn = (Button) findViewById(R.id.config_btnCheckIn);
		btnCheckOut = (Button) findViewById(R.id.config_btnCheckOut);
		
		if (configuracoesNaoSalvas.titleApk == null || configuracoesNaoSalvas.titleApk.isEmpty()) {
			txtTitleApk.setHint("App sem nome");
			titleApk = false;
			makeMyDearAlert("Atenção, o aplicativo nessecita de um Titulo.");
			
		} else {
			txtTitleApk.setText(configuracoesNaoSalvas.titleApk);
			titleApk = true;
		}
		
		dateCheckOut = new Date(2012, 01, 01, configuracoesNaoSalvas.checkOutLimit.getHours(), configuracoesNaoSalvas.checkOutLimit.getMinutes());
		refreshCheckIn();
		refreshCheckOut();
		
		Button btnSalvar = (Button) findViewById(R.id.config_BtnSalvar);
		Button btnCancelar = (Button) findViewById(R.id.config_BtnCancelar);
		
		btnToggleEmail = (ToggleButton) findViewById(R.id.config_Toggle_Email);
		btnTogglePhone = (ToggleButton) findViewById(R.id.config_Toggle_Phone);
		
btnToggleEmail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					txtEmail.setVisibility(LinearLayout.VISIBLE);
				} else {
					txtEmail.setVisibility(LinearLayout.GONE);
				}
				
			}
		});
		
		btnTogglePhone.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					txtPhone.setVisibility(LinearLayout.VISIBLE);
				} else {
					txtPhone.setVisibility(LinearLayout.GONE);
				}
				
			}
		});
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				optionActivityAlert(new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						salvarAlteracoes();
						
					}
				}, "VocÔøΩ realmente deseja salvar essa configuraÔøΩÔøΩes?");

			}
			
		});
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitActivity();
				
			}
		});
		
		btnCheckIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkInSelected = true;
				openTimePickerWithTitle("Hor√°rio entrada(limite)");
				
			}
		});
		
		btnCheckOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkInSelected = false;
				openTimePickerWithTitle("Hor√°rio saida(limite)");
				
			}
		});
		
	}
	
	private class TimePickHandler implements OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        	
            if (checkInSelected) {
            	if (hourOfDay > configuracoesNaoSalvas.checkOutLimit.getHours() || (hourOfDay == configuracoesNaoSalvas.checkOutLimit.getHours() && minute > configuracoesNaoSalvas.checkOutLimit.getMinutes())) {
					makeMyDearAlert("O horário de saida nao pode ser maior q o horário de entrada.");
				} else {
					configuracoesNaoSalvas.checkInLimit.setHours(hourOfDay);
					configuracoesNaoSalvas.checkInLimit.setMinutes(minute);
				}
				
				refreshCheckIn();
            	
			} else {
				if (hourOfDay < configuracoesNaoSalvas.checkInLimit.getHours() || (hourOfDay == configuracoesNaoSalvas.checkInLimit.getHours() && minute < configuracoesNaoSalvas.checkInLimit.getMinutes())) {
					makeMyDearAlert("O horário de saida nao pode ser maior q o horário de entrada.");
				} else {
					configuracoesNaoSalvas.checkOutLimit.setHours(hourOfDay);
					configuracoesNaoSalvas.checkOutLimit.setMinutes(minute);
				}
				
				refreshCheckOut();
			}

        }

    }
	
	public void openTimePickerWithTitle(String title) {
		final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (checkInSelected) {
			hour = configuracoesNaoSalvas.checkInLimit.getHours();
			minute = configuracoesNaoSalvas.checkInLimit.getMinutes();
		} else {
			hour = configuracoesNaoSalvas.checkOutLimit.getHours();
			minute = configuracoesNaoSalvas.checkOutLimit.getMinutes();
		}
        
		TimePickerDialog picker = new TimePickerDialog(TelaConfiguracoes.this, new TimePickHandler(), hour, minute, DateFormat.is24HourFormat(TelaConfiguracoes.this));
		picker.setTitle(title);
		picker.show();
	}
	
	public void refreshCheckIn() {
		String hora = configuracoesNaoSalvas.checkInLimit.getHours() == 0 ? "00" : String.valueOf(configuracoesNaoSalvas.checkInLimit.getHours());
		String minutos = configuracoesNaoSalvas.checkInLimit.getMinutes() == 0 ? "00" :  String.valueOf(configuracoesNaoSalvas.checkInLimit.getMinutes());
		
		btnCheckIn.setText(hora + ":" + minutos);
    }
	
	public void refreshCheckOut() {
		String hora = configuracoesNaoSalvas.checkOutLimit.getHours() == 0 ? "00" :  String.valueOf(configuracoesNaoSalvas.checkOutLimit.getHours());
		String minutos = configuracoesNaoSalvas.checkOutLimit.getMinutes() == 0 ? "00" :  String.valueOf(configuracoesNaoSalvas.checkOutLimit.getMinutes());
		
		btnCheckOut.setText(hora + ":" + minutos);
    }
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivity();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
	public void checkTitle() {
		if (txtTitleApk.length() > 0) {
			titleApk = true;
		} else {
			titleApk = false;
		}
	}
	
	public void salvarAlteracoes() {
		checkTitle();
		
		if (titleApk && !txtTitleApk.getText().toString().equals(configuracoesNaoSalvas.titleApk)) {
			configuracoesNaoSalvas.titleApk = txtTitleApk.getText().toString();
		}
		if (btnToggleEmail.isChecked()) {
			if (CodeSnippet.checkEmail(txtEmail.getText().toString())) {
				configuracoesNaoSalvas.emailNotification = txtEmail.getText().toString();
			} else {
				makeMyDearAlert(CodeSnippet.problemEmail(txtEmail.getText().toString()));
				return;
			}
		} else {
			configuracoesNaoSalvas.emailNotification = null;
		}
		
		if (btnTogglePhone.isChecked()) {
			if (CodeSnippet.checkPhone(txtPhone.getText().toString())) {
				configuracoesNaoSalvas.phoneNotification = txtPhone.getText().toString(); 
			} else {
				makeMyDearAlert(CodeSnippet.problemPhone(txtPhone.getText().toString()));
				return;
			}
		} else {
			configuracoesNaoSalvas.phoneNotification = null;
		}
		
		configuracoesDao.update(configuracoesNaoSalvas); //Salva as configurações
		
		int a, b, c, d;
		a = dateCheckOut.getMinutes();
		b = configuracoesNaoSalvas.checkOutLimit.getMinutes(); 
		c = dateCheckOut.getHours() ;
		d = configuracoesNaoSalvas.checkOutLimit.getHours();
		
		if (dateCheckOut.getMinutes() != configuracoesNaoSalvas.checkOutLimit.getMinutes() || dateCheckOut.getHours() != configuracoesNaoSalvas.checkOutLimit.getHours()) {
			CodeSnippet.startCheckOutService(getApplicationContext(), getHelper());
		}
		
		if (titleApk) {
			finish();
		} else {
			makeMyDearAlert("O aplicativo necessita de um titulo!");
		}
	}
	
	public void exitActivity() {
		checkTitle();
		if (titleApk) {
			exitActivityAlert("VocÔøΩ realmente deseja sair?");
		} else {
			makeMyDearAlert("O aplicativo necessita de um titulo!");
		}
		
	}
	
	public static void startActivity(Activity activity) {
		Intent intent = new Intent(activity, TelaConfiguracoes.class);
		activity.startActivity(intent);
	}
	
}

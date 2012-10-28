package br.com.pontoeletronico.activities;

import java.util.Calendar;
import java.util.Date;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Configuracoes;
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
	RuntimeExceptionDao<Configuracoes, Integer> configuracoesDao;
	Configuracoes configuracoes;
	Boolean checkInSelected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_configuracoes);
		
		configuracoesDao = getHelper().getConfiguracoesRuntimeDao();
		configuracoes = configuracoesDao.queryForId(1);
		
		txtTitleApk = (EditText) findViewById(R.id.config_txtTitleApk);
		txtEmail = (EditText) findViewById(R.id.config_txtEmail);
		txtPhone = (EditText) findViewById(R.id.config_txtPhone);
		
		btnCheckIn = (Button) findViewById(R.id.config_btnCheckIn);
		btnCheckOut = (Button) findViewById(R.id.config_btnCheckOut);
		
		if (configuracoes == null) {
			configuracoes = new Configuracoes(1, new Date(2012, 1, 1, 7, 30), new Date(2012, 1, 1, 18, 0));
			configuracoesDao.create(configuracoes);
		}
		
		txtTitleApk.setHint(configuracoes.titleApk == null ? "App sem nome" : configuracoes.titleApk);
		
		refreshCheckIn();
		refreshCheckOut();
		
		Button btnSalvar = (Button) findViewById(R.id.config_BtnSalvar);
		Button btnCancelar = (Button) findViewById(R.id.config_BtnCancelar);
		
		ToggleButton btnEmail = (ToggleButton) findViewById(R.id.config_Toggle_Email);
		ToggleButton btnPhone = (ToggleButton) findViewById(R.id.config_Toggle_Phone);
		
		btnEmail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				/*ScaleAnimation scale;
				
				if (isChecked) {
					scale = expandirAnimacaoExpansao();
				} else {
					scale = reduzirAnimacaoExpansao();
				}
				
				txtTitleApk.setAnimation(scale);
				txtTitleApk.startAnimation(scale);*/
				
				if (isChecked) {
					txtEmail.setVisibility(LinearLayout.VISIBLE);
				} else {
					txtEmail.setVisibility(LinearLayout.GONE);
				}
				
			}
		});
		
		btnPhone.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
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
				if (txtTitleApk.length() > 0 || configuracoes.titleApk.length() > 0) {
					
					optionActivityAlert(new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							
						}
					}, "Voc� realmente deseja salvar essa configura��es?");
				} else {
					makeMyDearAlert("O nome do aplicativo nao pode ficar em branco!");
				}
				
			}
			
		});
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitActivityAlert("Voc� realmente deseja sair?");
				
			}
		});
		
		btnCheckIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkInSelected = true;
				openTimePickerWithTitle("Horário entrada(limite)");
				
			}
		});
		
		btnCheckOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkInSelected = false;
				openTimePickerWithTitle("Horário saida(limite)");
				
			}
		});
		
	}
	
	private class TimePickHandler implements OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        	
            if (checkInSelected) {
				configuracoes.checkInLimit.setHours(hourOfDay);
				configuracoes.checkInLimit.setMinutes(minute);
				
				configuracoesDao.update(configuracoes);
				
				refreshCheckIn();
            	
			} else {
				configuracoes.checkOutLimit.setHours(hourOfDay);
				configuracoes.checkOutLimit.setMinutes(minute);
				
				configuracoesDao.update(configuracoes);
				
				refreshCheckOut();
			}

        }

    }
	
	public void openTimePickerWithTitle(String title) {
		final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

		TimePickerDialog picker = new TimePickerDialog(TelaConfiguracoes.this, new TimePickHandler(), hour, minute, DateFormat.is24HourFormat(TelaConfiguracoes.this));
		picker.setTitle(title);
		picker.show();
	}
	
	public void refreshCheckIn() {
		String hora = configuracoes.checkInLimit.getHours() == 0 ? "00" : String.valueOf(configuracoes.checkInLimit.getHours());
		String minutos = configuracoes.checkInLimit.getMinutes() == 0 ? "00" :  String.valueOf(configuracoes.checkInLimit.getMinutes());
		
		btnCheckIn.setText(hora + ":" + minutos);
    }
	
	public void refreshCheckOut() {
		String hora = configuracoes.checkOutLimit.getHours() == 0 ? "00" :  String.valueOf(configuracoes.checkOutLimit.getHours());
		String minutos = configuracoes.checkOutLimit.getMinutes() == 0 ? "00" :  String.valueOf(configuracoes.checkOutLimit.getMinutes());
		
		btnCheckOut.setText(hora + ":" + minutos);
    }
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivityAlert("Voc� realmente deseja sair?");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
	private ScaleAnimation expandirAnimacaoExpansao() {
		ScaleAnimation scaleAnim;
				
		// Expand
		scaleAnim = new ScaleAnimation(0.5f, 1, 1, 1);
		scaleAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
			}
		});
		
		scaleAnim.setDuration(2000);
				
		return scaleAnim;
	}
	
	private ScaleAnimation reduzirAnimacaoExpansao() {
		ScaleAnimation scaleAnim;
				
		scaleAnim = new ScaleAnimation(1, 0.5f, 1, 1);
		scaleAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
			}
		});
		
		scaleAnim.setDuration(2000);
				
		return scaleAnim;
	}
	
	public static void startActivity(Activity activity) {
		Intent intent = new Intent(activity, TelaConfiguracoes.class);
		activity.startActivity(intent);
	}
	
}

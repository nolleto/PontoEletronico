package br.com.pontoeletronico.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioConfiguracoesController;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.FuncionarioConfiguracoes;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class TelaFuncionarioConfiguracoesActivity extends BaseActivity {
	RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> funcionarioConfiguracoesDao;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	Button btnCheckIn, btnCheckOut; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_funcionario_configuracoes);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionarioConfiguracoesDao = getHelper().getFuncionario_ConfiguracoesRuntimeDao();
		
		funcionario = FuncionarioController.getFuncionarioForId(getHelper(), getIntent().getExtras().getInt("ID")); 
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitleInActionBar(funcionario.Name);
		
		btnCheckIn = (Button) findViewById(R.id.telaFuncConfig_CheckIn);
		btnCheckOut = (Button) findViewById(R.id.telaFuncConfig_CheckOut);
		
		btnCheckIn.setText(getCheckIn());
		btnCheckOut.setText(getCheckOut());
		
		btnCheckIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(TelaFuncionarioConfiguracoesActivity.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Date date = new Date(2012, 1, 1, hourOfDay, minute);
						
						if (funcionario.funcionarioConfiguracoes == null || funcionario.funcionarioConfiguracoes.FuncionarioCheckOut == null || date.before(funcionario.funcionarioConfiguracoes.FuncionarioCheckOut)) {
							funcionario.funcionarioConfiguracoes = FuncionarioConfiguracoesController.setCheckIn(getHelper(), funcionario, date);
							funcionarioDao.update(funcionario);
							
							btnCheckIn.setText(getCheckIn());
						} else {
							makeMyDearAlert(TelaFuncionarioConfiguracoesActivity.this.getString(R.string.str_Error_CheckIn));
						}
					}
				}, getDateCheckIn().getHours(), getDateCheckIn().getMinutes(), true);
				
				dialog.setCancelable(false);
				dialog.setTitle(R.string.str_SubTitle_ChangeUserCheckIn);
				dialog.show();
			}
		});
		
		btnCheckOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(TelaFuncionarioConfiguracoesActivity.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Date date = new Date(2012, 1, 1, hourOfDay, minute);
					
						if (funcionario.funcionarioConfiguracoes == null || funcionario.funcionarioConfiguracoes.FuncionarioCheckIn == null || date.after(funcionario.funcionarioConfiguracoes.FuncionarioCheckIn)) {
							funcionario.funcionarioConfiguracoes = FuncionarioConfiguracoesController.setCheckOut(getHelper(), funcionario, date);
							funcionarioDao.update(funcionario);
							
							btnCheckOut.setText(getCheckOut());
						} else {
							makeMyDearAlert(TelaFuncionarioConfiguracoesActivity.this.getString(R.string.str_Error_CheckOut));
						}
						
					}
				}, getDateCheckOut().getHours(), getDateCheckOut().getMinutes(), true);
				
				dialog.setCancelable(false);
				dialog.setTitle(R.string.str_SubTitle_ChangeUserCheckOut);
				dialog.show();
			}
		});
		
	}
	
	private Date getDateCheckIn() {
		if (funcionario.funcionarioConfiguracoes != null && funcionario.funcionarioConfiguracoes.FuncionarioCheckIn != null) {
			return funcionario.funcionarioConfiguracoes.FuncionarioCheckIn;
		} else {
			return new Date(); 
		}
	}
	
	private Date getDateCheckOut() {
		if (funcionario.funcionarioConfiguracoes != null && funcionario.funcionarioConfiguracoes.FuncionarioCheckOut != null) {
			return funcionario.funcionarioConfiguracoes.FuncionarioCheckOut;
		} else {
			return new Date(); 
		}
	}
	
	private String getCheckIn() {
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		if (funcionario.funcionarioConfiguracoes != null && funcionario.funcionarioConfiguracoes.FuncionarioCheckIn != null) {
			return format.format(funcionario.funcionarioConfiguracoes.FuncionarioCheckIn);
		} else {
			return this.getString(R.string.str_Current_FuncConfig_CheckInOut_Empty);
		}
	}
	
	private String getCheckOut() {
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		if (funcionario.funcionarioConfiguracoes != null && funcionario.funcionarioConfiguracoes.FuncionarioCheckOut != null) {
			return format.format(funcionario.funcionarioConfiguracoes.FuncionarioCheckOut);
		} else {
			return this.getString(R.string.str_Current_FuncConfig_CheckInOut_Empty);
		}
	}
	

	public void ResetAll() {
		RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> funcionarioConfigDao = getHelper().getFuncionario_ConfiguracoesRuntimeDao();
		FuncionarioConfiguracoes configuracoes = funcionario.funcionarioConfiguracoes;
		if (configuracoes != null) {
			funcionarioConfigDao.delete(configuracoes);
		}
		funcionario.funcionarioConfiguracoes = null;
		ReloadAll();
	}
	
	public void ResetCheckIn() {
		RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> funcionarioConfigDao = getHelper().getFuncionario_ConfiguracoesRuntimeDao();
		FuncionarioConfiguracoes configuracoes = funcionario.funcionarioConfiguracoes;
		if (configuracoes != null && configuracoes.FuncionarioCheckIn != null) {
			configuracoes.FuncionarioCheckIn = null;
			funcionarioConfigDao.update(configuracoes);
			
			funcionario.funcionarioConfiguracoes.FuncionarioCheckIn = null;
		}
		ReloadAll();
	}
	
	public void ResetCheckOut() {
		RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> funcionarioConfigDao = getHelper().getFuncionario_ConfiguracoesRuntimeDao();
		FuncionarioConfiguracoes configuracoes = funcionario.funcionarioConfiguracoes;
		if (configuracoes != null && configuracoes.FuncionarioCheckOut != null) {
			configuracoes.FuncionarioCheckOut = null;
			funcionarioConfigDao.update(configuracoes);
			
			funcionario.funcionarioConfiguracoes.FuncionarioCheckOut = null;
		}
		ReloadAll();
	}
	
	private void ReloadAll() {
		btnCheckIn.setText(getCheckIn());
		btnCheckOut.setText(getCheckOut());
	}
	
	public static void startActivity(Activity activity, int iD) {
		Intent intent = new Intent(activity, TelaFuncionarioConfiguracoesActivity.class);
		intent.putExtra("ID", iD);
		activity.startActivity(intent);
	}

}

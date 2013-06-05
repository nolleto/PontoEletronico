package br.com.pontoeletronico.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.ConfiguracoesController;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.util.ExcelFile;
import br.com.pontoeletronico.util.FormValidator;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ConfiguracoesFragment extends BaseFragment {
	EditText txtTitleApk, txtPhone, txtEmail;
	Button btnCheckIn, btnCheckOut;
	ToggleButton btnToggleEmail, btnTogglePhone;
	RuntimeExceptionDao<Configuracoes, Integer> configuracoesDao;
	Configuracoes configuracoesAtual;
	Date dateCheckOut;
	Boolean checkInSelected, titleApk;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.configuracoes, null);
		
		setSetting();
		
		configuracoesDao = activity.getHelper().getConfiguracoesRuntimeDao();
		configuracoesAtual = ConfiguracoesController.getConfiguracoes(activity.getHelper());
		
		txtTitleApk = (EditText) root.findViewById(R.id.config_txtTitleApk);
		txtEmail = (EditText) root.findViewById(R.id.config_txtEmail);
		txtPhone = (EditText) root.findViewById(R.id.config_txtPhone);
		
		txtTitleApk.clearFocus();
		
		btnCheckIn = (Button) root.findViewById(R.id.config_btnCheckIn);
		btnCheckOut = (Button) root.findViewById(R.id.config_btnCheckOut);
		
		btnToggleEmail = (ToggleButton) root.findViewById(R.id.config_Toggle_Email);
		btnTogglePhone = (ToggleButton) root.findViewById(R.id.config_Toggle_Phone);
		
		Button btnCreateXLS = (Button) root.findViewById(R.id.config_btnCreateXLS);
		btnCreateXLS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.ShowLoad();
				
				AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
					
					@Override
					protected Void doInBackground(Void... params) {
						ExcelFile.CreateFile(activity);
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						activity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								activity.makeMyDearAlert(activity.getString(R.string.str_Message_CreatedXLS));
								activity.HideLoad();
								
							}
						});
						super.onPostExecute(result);
					}
				};
				
			asyncTask.execute();
				
				
			}
		});
		
		Button btnSendXLS = (Button) root.findViewById(R.id.config_btnSendXLS);
		btnSendXLS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExcelFile.sendExcelFile(activity);
			}
		});
		
		Button btnOpenXLS = (Button) root.findViewById(R.id.config_btnOpenXLS);
		btnOpenXLS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExcelFile.openExcelFile(activity);
				
			}
		});
		
		txtTitleApk.setText(configuracoesAtual.titleApk);
		btnCheckIn.setText(getDateCkeckIn());
		btnCheckOut.setText(getDateCkeckOut());
		
		if (configuracoesAtual.emailNotification != null && !configuracoesAtual.emailNotification.equals("")) {
			btnToggleEmail.setChecked(true);
			txtEmail.setVisibility(LinearLayout.VISIBLE);
			txtEmail.setText(configuracoesAtual.emailNotification);
			
		}
		
		if (configuracoesAtual.phoneNotification != null && !configuracoesAtual.phoneNotification.equals("")) {
			btnTogglePhone.setChecked(true);
			txtPhone.setVisibility(LinearLayout.VISIBLE);
			txtPhone.setText(configuracoesAtual.phoneNotification);
		}
		
		txtTitleApk.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (txtTitleApk.getText().toString().length() > 0) {
					configuracoesAtual.titleApk = txtTitleApk.getText().toString();
					configuracoesDao.update(configuracoesAtual);
				}
				
				txtTitleApk.setText(configuracoesAtual.titleApk);
				
				return false;
			}
		});
		
		btnToggleEmail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					txtEmail.setText(configuracoesAtual.emailNotification == null ? "" : configuracoesAtual.emailNotification.toString());
					txtEmail.setVisibility(LinearLayout.VISIBLE);
				} else {
					configuracoesAtual.emailNotification = null;
					configuracoesDao.update(configuracoesAtual);
					txtEmail.setVisibility(LinearLayout.GONE);

				}
			}
		});
		
		txtEmail.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (FormValidator.checkEmail(txtEmail.getText().toString())) {
					configuracoesAtual.emailNotification = txtEmail.getText().toString();
					configuracoesDao.update(configuracoesAtual);
					
				} else {
					activity.makeMyDearAlert(FormValidator.problemEmail(txtEmail.getText().toString()));
				}
				
				return false;
			}
		});
		
		btnTogglePhone.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					txtPhone.setText(configuracoesAtual.phoneNotification == null ? "" : configuracoesAtual.phoneNotification.toString());
					txtPhone.setVisibility(LinearLayout.VISIBLE);
				} else {
					configuracoesAtual.phoneNotification = null;
					configuracoesDao.update(configuracoesAtual);
					txtPhone.setVisibility(LinearLayout.GONE);
				}
				
			}
		});
		
		txtPhone.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (FormValidator.checkPhone(txtPhone.getText().toString())) {
					configuracoesAtual.phoneNotification = txtPhone.getText().toString();
					configuracoesDao.update(configuracoesAtual);
					
				} else {
					activity.makeMyDearAlert(FormValidator.problemPhone(txtPhone.getText().toString()));
				}
				
				return false;
			}
		});
		
		btnCheckIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(activity, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Date date = new Date(2012, 1, 1, hourOfDay, minute);
						
						if (date.before(configuracoesAtual.checkOutLimit)) {
							configuracoesAtual.checkInLimit = date;
							configuracoesDao.update(configuracoesAtual);
							
							btnCheckIn.setText(getDateCkeckIn());
						} else {
							activity.makeMyDearAlert(activity.getString(R.string.str_Error_CheckIn));
						}
					}
				}, configuracoesAtual.checkInLimit.getHours(), configuracoesAtual.checkInLimit.getMinutes(), true);
				
				dialog.setCancelable(false);
				dialog.setTitle(R.string.str_SubTitle_ChangeUserCheckIn);
				dialog.show();
			}
		});
		
		btnCheckOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(activity, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Date date = new Date(2012, 1, 1, hourOfDay, minute);
					
						if (date.after(configuracoesAtual.checkInLimit)) {
							configuracoesAtual.checkOutLimit = date;
							configuracoesDao.update(configuracoesAtual);
							
							btnCheckOut.setText(getDateCkeckOut());	
						} else {
							activity.makeMyDearAlert(activity.getString(R.string.str_Error_CheckOut));
						}
					}
				}, configuracoesAtual.checkOutLimit.getHours(), configuracoesAtual.checkOutLimit.getMinutes(), true);
				
				dialog.setCancelable(false);
				dialog.setTitle(R.string.str_SubTitle_ChangeUserCheckOut);
				dialog.show();
				
			}
		});
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (btnToggleEmail.isChecked()) {
			if (configuracoesAtual.emailNotification == null || configuracoesAtual.emailNotification.equals("")) {
				btnToggleEmail.setChecked(false);
				txtEmail.setText("");
				txtEmail.setVisibility(LinearLayout.GONE);
			}
		}
		
		if (btnTogglePhone.isChecked()) {
			if (configuracoesAtual.phoneNotification == null || configuracoesAtual.phoneNotification.equals("")) {
				btnTogglePhone.setChecked(false);
				txtPhone.setText("");
				txtPhone.setVisibility(LinearLayout.GONE);
			}
		}
	}
		
	private String getDateCkeckIn() {
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		
		String strDate = format.format(configuracoesAtual.checkInLimit);
		return strDate;
	}
	
	private String getDateCkeckOut() {
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		
		String strDate = format.format(configuracoesAtual.checkOutLimit);
		return strDate;
	}
	
}

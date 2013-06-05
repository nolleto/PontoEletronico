package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.FormValidator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AlterarDadosPessoaisActivity extends BaseActivity {
	TextView txtCurrent, txtInfo;
	EditText txtField;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	int id, opcao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_dados_pessoais);
		
		id = getIntent().getExtras().getInt("ID");
		opcao = getIntent().getExtras().getInt("opcao");
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionario = funcionarioDao.queryForId(id);

		txtCurrent = (TextView) findViewById(R.id.altPessoais_txtCurrentField); //Dados Atual
		txtInfo = (TextView) findViewById(R.id.altPessoais_txtInfo); 
		txtField = (EditText) findViewById(R.id.altPessoais_NewInfo); //Campo Atual
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		switch (opcao) {
		case 0:
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserName));
			txtCurrent.setText(this.getString(R.string.str_Current_Name) + " " + funcionario.Name);
			txtInfo.setText(this.getString(R.string.str_SubTitle_NewName));
			txtField.setInputType(InputType.TYPE_CLASS_TEXT);
			
			break;
		case 1:
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserEmail));
			String email = funcionario.Email == null ? this.getString(R.string.str_Current_Email_Empty) : this.getString(R.string.str_Current_Email) + " " + funcionario.Email;
			txtInfo.setText(this.getString(R.string.str_SubTitle_NewEmail));
			txtCurrent.setText(email);
			txtField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			
			break;
		case 2:
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserAdress));
			String adress = funcionario.Adress == null ?this.getString(R.string.str_Current_Adress_Empty) : this.getString(R.string.str_Current_Adress) + " " + funcionario.Adress;
			txtInfo.setText(this.getString(R.string.str_SubTitle_NewAdress));
			txtCurrent.setText(adress);
			txtField.setInputType(InputType.TYPE_CLASS_TEXT);
			
			break;

		default:
			setTitleInActionBar(this.getString(R.string.str_SubTitle_ChangeUserPhone));
			String phone = funcionario.Phone == null ? this.getString(R.string.str_Current_Phone_Empty) : this.getString(R.string.str_Current_Phone) + " " + funcionario.Phone;
			txtInfo.setText(this.getString(R.string.str_SubTitle_NewPhone));
			txtCurrent.setText(phone);
			txtField.setInputType(InputType.TYPE_CLASS_PHONE);
			
			break;
		}
		
		txtField.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				saveInfo();
				
				return false;
			}
		});
		
	}
	
	/**
	 * Sava as mudanas feitas pelo usu‡rio no Banco de Dado.
	 * 
	 */
	public void saveInfo() {
		switch (opcao) {
		case 0:
			if (txtField.length() > 0) {
				funcionario.Name = txtField.getText().toString();
				confirmarUpdate(funcionario);
				
			} else {
				makeMyDearAlert(AlterarDadosPessoaisActivity.this.getString(R.string.str_Error_FieldUser));
			}
			
			break;
		case 1:
			if (FormValidator.checkEmail(txtField.getText().toString())) {
				funcionario.Email = txtField.getText().toString();
				confirmarUpdate(funcionario);
				
			} else {
				makeMyDearAlert(FormValidator.problemEmail(txtField.getText().toString()));
			}
			
			break;
		case 2:
			if (txtField.length() > 0) {
				funcionario.Adress = txtField.getText().toString();
				confirmarUpdate(funcionario);
				
			} else {
				makeMyDearAlert(AlterarDadosPessoaisActivity.this.getString(R.string.str_Error_FieldAdress));
			}
			
			break;

		default:
			if (FormValidator.checkPhone(txtField.getText().toString())) {
				funcionario.Phone = txtField.getText().toString();
				confirmarUpdate(funcionario);
				
			} else {
				makeMyDearAlert(FormValidator.problemPhone(txtField.getText().toString()));
			}
			
			break;
		}
	}
	
	public void confirmarUpdate(final Funcionario funcionario) {
		optionActivityAlert(new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				funcionarioDao.update(funcionario);
				finish();
				
			}
		}, AlterarDadosPessoaisActivity.this.getString(R.string.str_SaveChanges));
	}
	
	public void errorMessage() {
		makeMyDearAlert("Complete o campo acima!");
	}
	
	public static void startActicity(Activity activity, int id, int opcao) {
		Intent intent = new Intent(activity, AlterarDadosPessoaisActivity.class);
		intent.putExtra("ID", id);
		intent.putExtra("opcao", opcao);
		activity.startActivity(intent);
	}
	
}

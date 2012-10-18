package br.com.pontoeletronico.activities;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlterarDadosPessoaisActivity extends BaseActivity {
	EditText txtName, txtEmail, txtAdress, txtPhone;
	Funcionario funcionario;
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

		txtName = (EditText) findViewById(R.id.altPessoais_NewName);
		txtEmail = (EditText) findViewById(R.id.altPessoais_NewEmail);
		txtAdress = (EditText) findViewById(R.id.altPessoais_NewAdress);
		txtPhone = (EditText) findViewById(R.id.altPessoais_NewPhone);
		
		switch (opcao) {
		case 0:
			((LinearLayout) findViewById(R.id.altPessoais_Linear_Name)).setVisibility(LinearLayout.VISIBLE);
			((TextView) findViewById(R.id.altPessoais_txtCurrent_Name)).setText("Nome atual: " + funcionario.Name);
			
			break;
		case 1:
			((LinearLayout) findViewById(R.id.altPessoais_Linear_Email)).setVisibility(LinearLayout.VISIBLE);
			String email = funcionario.Email == null ? "Nenhum email registrado!" : funcionario.Email;
			((TextView) findViewById(R.id.altPessoais_txtCurrent_Email)).setText("Email atual: " + email);
			
			break;
		case 2:
			((LinearLayout) findViewById(R.id.altPessoais_Linear_Adress)).setVisibility(LinearLayout.VISIBLE);
			String adress = funcionario.Adress == null ? "Nenhum endereço resgistrado!" : funcionario.Adress;
			((TextView) findViewById(R.id.altPessoais_txtCurrent_Adress)).setText("Endereço atual: " + adress);
			
			break;

		default:
			((LinearLayout) findViewById(R.id.altPessoais_Linear_Phone)).setVisibility(LinearLayout.VISIBLE);
			String phone = funcionario.Phone == null ? "Nenhum telefone registrado!" : funcionario.Phone;
			((TextView) findViewById(R.id.altPessoais_txtCurrent_Phone)).setText("Telefone atual: " + phone);
			
			break;
		}
		
		
		Button btnSalvar = (Button) findViewById(R.id.altPessoais_BtnSalvar);
		Button btnCancelar = (Button) findViewById(R.id.altPessoais_BtnCancelar);
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (opcao) {
				case 0:
					if (txtName.length() > 0) {
						funcionario.Name = txtName.getText().toString();
						funcionarioDao.update(funcionario);
						finish();
						
					} else {
						errorMessage();
					}
					
					break;
				case 1:
					if (CodeSnippet.checkEmail(txtEmail.getText().toString())) {
						funcionario.Email = txtEmail.getText().toString();
						funcionarioDao.update(funcionario);
						finish();
						
					} else {
						makeMyDearAlert("Email inválido");
					}
					
					break;
				case 2:
					if (txtAdress.length() > 0) {
						funcionario.Adress = txtAdress.getText().toString();
						funcionarioDao.update(funcionario);
						finish();
						
					} else {
						errorMessage();
					}
					
					break;

				default:
					if (CodeSnippet.checkPhone(txtPhone.getText().toString())) {
						funcionario.Phone = txtPhone.getText().toString();
						funcionarioDao.update(funcionario);
						finish();
						
					} else {
						errorMessage();
					}
					
					break;
				}
				
			}
		});
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exitActivityAlert("Você realmente deseja sair?");
				
			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivityAlert("Você realmente deseja sair?");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
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

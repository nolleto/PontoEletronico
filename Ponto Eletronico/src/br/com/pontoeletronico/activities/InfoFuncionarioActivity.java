package br.com.pontoeletronico.activities;

import java.text.SimpleDateFormat;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoFuncionarioActivity extends BaseActivity {
	TextView txtName, txtUser, txtEmail, txtPhone, txtAdress, txtId, txtDate, txtUserDelegate, txtCheckIn, txtCheckOut, txtIsGerente;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_funcionario);
		
		id = getIntent().getExtras().getInt("ID");
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionarioPontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		
		funcionario = funcionarioDao.queryForId(id);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitleInActionBar(funcionario.Name);
		
		txtName = (TextView) findViewById(R.id.infoFunc_txtName);
		txtUser = (TextView) findViewById(R.id.infoFunc_txtUser);
		txtEmail = (TextView) findViewById(R.id.infoFunc_txtEmail);
		txtAdress = (TextView) findViewById(R.id.infoFunc_txtAdress);
		txtPhone = (TextView) findViewById(R.id.infoFunc_txtPhone);
		txtId = (TextView) findViewById(R.id.infoFunc_txtId);
		txtDate = (TextView) findViewById(R.id.infoFunc_txtDateCreated);
		txtUserDelegate = (TextView) findViewById(R.id.infoFunc_txtUserDelegate);
		txtCheckIn = (TextView) findViewById(R.id.infoFunc_txtCheckIn);
		txtCheckOut = (TextView) findViewById(R.id.infoFunc_txtCheckOut);
		txtIsGerente = (TextView) findViewById(R.id.infoFunc_txtIsGerente);
		
		txtId.setText(this.getString(R.string.simpleWord_Usuario) + " ID: " + funcionario.funcionarioID);
		txtUser.setText(this.getString(R.string.simpleWord_Usuario) + ": " + funcionario.User);
		txtName.setText(this.getString(R.string.simpleWord_Nome) + ": " + funcionario.Name);
		txtEmail.setText(this.getString(R.string.simpleWord_Email) + ": " + getEmail());
		txtAdress.setText(this.getString(R.string.simpleWord_Endereco) + ": " + getAdress());
		txtPhone. setText(this.getString(R.string.simpleWord_Telefone) + ": " + getPhone());
		txtDate.setText(this.getString(R.string.str_SubTitle_UserCreated) + ": " + getDate());
		
		String str_gerente = funcionario.isGerente == true ? this.getString(R.string.simpleWord_Sim) : this.getString(R.string.simpleWord_Nao);
		txtIsGerente.setText(this.getString(R.string.simpleWord_Gerente) + ": " + str_gerente);
		
		if (funcionario.funcionarioID == 1) {
			txtUserDelegate.setVisibility(LinearLayout.GONE);
		} else {
			txtUserDelegate.setText(this.getString(R.string.str_SubTitle_UserDelegate) + ": " + funcionario.GerenteDelegate.Name);
		}
		
		if (funcionario.funcionarioConfiguracoes != null) {
			if (funcionario.funcionarioConfiguracoes.FuncionarioCheckIn != null) {
				SimpleDateFormat format = new SimpleDateFormat("kk:mm");
				txtCheckIn.setVisibility(LinearLayout.VISIBLE);
				txtCheckIn.setText(format.format(funcionario.funcionarioConfiguracoes.FuncionarioCheckIn));
			}
			
			if (funcionario.funcionarioConfiguracoes.FuncionarioCheckOut != null) {
				SimpleDateFormat format = new SimpleDateFormat("kk:mm");
				txtCheckOut.setVisibility(LinearLayout.VISIBLE);
				txtCheckOut.setText(format.format(funcionario.funcionarioConfiguracoes.FuncionarioCheckOut));
			}
		}
		
		txtUserDelegate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoFuncionarioActivity.startActivity(InfoFuncionarioActivity.this, funcionario.GerenteDelegate.funcionarioID);
			}
		});
		
	}
	
	private String getEmail() {
		return funcionario.Email == null || funcionario.Email.isEmpty() == true ? this.getString(R.string.str_Current_Email_Empty) : funcionario.Email;
	}
	
	private String getPhone() {
		return funcionario.Phone == null || funcionario.Phone.isEmpty() == true ? this.getString(R.string.str_Current_Phone_Empty) : funcionario.Phone;
	}
	
	private String getAdress() {
		return funcionario.Adress == null || funcionario.Adress.isEmpty() == true ? this.getString(R.string.str_Current_Adress_Empty) : funcionario.Adress;
	}

	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss (EEEE)");
		return format.format(funcionario.DateCreated);
	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, InfoFuncionarioActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

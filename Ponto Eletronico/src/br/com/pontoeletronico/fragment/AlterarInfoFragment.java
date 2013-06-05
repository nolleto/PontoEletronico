package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.AlterarDadosLoginActivity;
import br.com.pontoeletronico.activities.AlterarDadosPessoaisActivity;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AlterarInfoFragment extends BaseFragment {
	TextView txtUser, txtPassword, txtNome, txtEmail, txtEndereco, txtTelefone;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.alterar_cadastro, null);
		
		setSetting();
		
		txtUser = (TextView) root.findViewById(R.id.alterarCadastro_User);
		txtPassword = (TextView) root.findViewById(R.id.alterarCadastro_Password);
		txtNome = (TextView) root.findViewById(R.id.alterarCadastro_Nome);
		txtEmail = (TextView) root.findViewById(R.id.alterarCadastro_Email);
		txtEndereco = (TextView) root.findViewById(R.id.alterarCadastro_Endereco);
		txtTelefone = (TextView) root.findViewById(R.id.alterarCadastro_Telefone);
				
		txtUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosLoginActivity.startActivity(activity, funcionarioId, 0);
			}
		});
		
		txtPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosLoginActivity.startActivity(activity, funcionarioId, 1);
				
			}
		});
		
		txtNome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosPessoaisActivity.startActicity(activity, funcionarioId, 0);
				
			}
		});
		
		txtEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosPessoaisActivity.startActicity(activity, funcionarioId, 1);
				
			}
		});
		
		txtEndereco.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosPessoaisActivity.startActicity(activity, funcionarioId, 2);
				
			}
		});
		
		txtTelefone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarDadosPessoaisActivity.startActicity(activity, funcionarioId, 3);
				
			}
		});
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		funcionario = FuncionarioController.getFuncionarioForId(activity.getHelper(), funcionarioId);
		
		txtUser.setText(this.getString(R.string.simpleWord_Usuario) + ": " + funcionario.User);
		txtPassword.setText(this.getString(R.string.simpleWord_Senha) + ": " + getPasswordDecrypted());
		txtNome.setText(this.getString(R.string.simpleWord_Nome) + ": " + funcionario.Name);
		txtEmail.setText(this.getString(R.string.simpleWord_Email) + ": " + getEmail());
		txtEndereco.setText(this.getString(R.string.simpleWord_Endereco) + ": " + getAdress());
		txtTelefone.setText(this.getString(R.string.simpleWord_Telefone) + ": " + getPhone());
	}
	
	
	private String getEmail() {
			String str = funcionario.Email;
			return str == null ? this.getString(R.string.str_Current_Email_Empty) : str;
	}
	
	private String getAdress() {
		String str = funcionario.Adress;
		return str == null ? this.getString(R.string.str_Current_Adress_Empty) : str;
	}
	
	private String getPhone() {
		String str = funcionario.Phone;
		return str == null ? this.getString(R.string.str_Current_Phone_Empty) : str;
	}
	
	private String getPasswordDecrypted() {
		String str = "";
		for (int i = 0; i < funcionario.Password.length(); i++) {
			str = str + "*";
		}
		return str;
	}
	
}

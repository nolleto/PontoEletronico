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
		
		txtUser.setText("Usuário: " + funcionario.User);
		txtNome.setText("Nome: " + funcionario.Name);
		txtEmail.setText("Email: " + getEmail());
		txtEndereco.setText("Endereço: " + getAdress());
		txtTelefone.setText("Telefone: " + getPhone());
	}
	
	
	private String getEmail() {
			String strEmail = funcionario.Email;
			return strEmail == null ? "Nenhum Email registrado." : strEmail;
	}
	
	private String getAdress() {
		String strEmail = funcionario.Adress;
		return strEmail == null ? "Nenhum endereço registrado." : strEmail;
	}
	
	private String getPhone() {
		String strEmail = funcionario.Phone;
		return strEmail == null ? "Nenhum telefone registrado." : strEmail;
	}
	
}

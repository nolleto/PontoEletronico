package br.com.pontoeletronico.activities;

import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GerenciarFuncionariosActivity extends BaseActivity {
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerenciar_funcionarios);
		
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		List<Funcionario> list = funcionarioDao.queryForEq("isGerente", false);
		
		
		
	}
	
	public static void startActivity(Activity activity) {
		Intent intent = new Intent(activity, GerenciarFuncionariosActivity.class);
		activity.startActivity(intent);
	}
	
}

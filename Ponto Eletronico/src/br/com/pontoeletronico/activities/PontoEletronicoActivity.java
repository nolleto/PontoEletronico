package br.com.pontoeletronico.activities;

import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PontoEletronicoActivity extends BaseActivity {
    /** Called when the activity is first created. */
	RuntimeExceptionDao<Funcionario,Integer> funcionarioDao;
	Button btnEntrar, btnCadastrar, btnCadastroList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnEntrar = (Button) findViewById(R.id.btn_Entrar);
        btnCadastrar = (Button) findViewById(R.id.btn_Cadastrar);
        
        funcionarioDao = getHelper().getFuncionarioRuntimeDao();
        
        List<Funcionario> admins = funcionarioDao.queryForEq("User", "admin");
        
        if (admins.size() < 1) {
        	Funcionario admin = new Funcionario("admin", "1234", "Admin", true);
			funcionarioDao.createIfNotExists(admin);
		}
        
        btnEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText user = (EditText) findViewById(R.id.userName);
				
				if (CodeSnippet.checkIfExistUser(getHelper(), user.getText().toString())){
					Funcionario funcionario = funcionarioDao.queryForEq("User", user.getText().toString()).get(0);
					
					if (CodeSnippet.checkIsGerente(getHelper(), user.getText().toString())) {
						TelaGerenteActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
					} else {
						TelaFuncionarioActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
					}
					
				} else {
					makeMyDearAlert("Gangnam Style");
					
				}
				
			}
		});
        
        btnCadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CadastroActivity.startActivity(PontoEletronicoActivity.this);
				
			}
		});
        
        btnCadastroList = (Button) findViewById(R.id.btn_Cadastros);
        btnCadastroList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListaContasActivity.startActivity(PontoEletronicoActivity.this);
				
			}
		});
        
    }
    
    @Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivityAlert("Voc realmente deseja sair?");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
    
}
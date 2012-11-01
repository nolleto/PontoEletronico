package br.com.pontoeletronico.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PontoEletronicoActivity extends BaseActivity {
    /** Called when the activity is first created. */
	RuntimeExceptionDao<Funcionario,Integer> funcionarioDao;
	AutoCompleteTextView txtUser;
	Button btnEntrar, btnCadastrar, btnCadastroList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //DEBUG
        Button btn_Debug = (Button) findViewById(R.id.btn_create_users_debug);
        /*btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createUsersDEBUG();
				
			}
		});*/
        btn_Debug.setText("Enviar um Email danadinho de bom!");
        btn_Debug.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CodeSnippet.sendEmail(PontoEletronicoActivity.this);
				
			}
		});
        
        CodeSnippet.initDB(getHelper());
        
        CodeSnippet.startCheckOutService(getApplicationContext(), getHelper());
        
        txtUser = (AutoCompleteTextView) findViewById(R.id.main_userName);
        btnEntrar = (Button) findViewById(R.id.main_btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.main_btnCadastrar);
        
        final TextView txtPassword = (TextView) findViewById(R.id.main_Password);
        
        funcionarioDao = getHelper().getFuncionarioRuntimeDao();   
        
        btnEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (CodeSnippet.checkIfExistUser(getHelper(), txtUser.getText().toString())){
					Funcionario funcionario = funcionarioDao.queryForEq("User", txtUser.getText().toString()).get(0);
					
					if (CodeSnippet.checkIfExistUserAndPassworl(getHelper(), txtUser.getText().toString(), txtPassword.getText().toString())) {
						if (CodeSnippet.checkIsGerente(getHelper(), txtUser.getText().toString())) {
							TelaGerenteActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
						} else {
							TelaFuncionarioActivity.startActivity(PontoEletronicoActivity.this, funcionario.funcionarioID);
						}
						
					} else {
						makeMyDearAlert("A Senha incorreta!");
					}
					
				} else {
					makeMyDearAlert("Usu‡rio " + txtUser.getText().toString() + " n‹o existe!");
				}
				
			}
		});
        
        btnCadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CadastroActivity.startActivity(PontoEletronicoActivity.this);
				
			}
		});
        
        btnCadastroList = (Button) findViewById(R.id.main_btnCadastros);
        btnCadastroList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListaContasActivity.startActivity(PontoEletronicoActivity.this);
				
			}
		});
        
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	TextView txtTitulo = (TextView) findViewById(R.id.main_txtTitulo);
    	Configuracoes config = getHelper().getConfiguracoesRuntimeDao().queryForId(1);
    	
    	if (config.titleApk == null || config.titleApk.length() == 0) {
			TelaConfiguracoes.startActivity(this);
			return;
		}
    	
    	txtTitulo.setText(config.titleApk == null || config.titleApk.isEmpty() ? "Titulo =)" : config.titleApk );
    	
    	List<Funcionario> listaA = funcionarioDao.queryForAll();
        ArrayList<String> listaB = new ArrayList<String>();
        
        for (Funcionario funcionario : listaA) {
			listaB.add(funcionario.User);
		}
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaB);
        
        txtUser.setAdapter(adapter);
    	
    }
    
    
    
    private void createUsersDEBUG() {
    	for (int i = 1; i <= 100; i++) {
			Funcionario func = null;
			String myPassword = "1234";
			
			if ((i % 2) == 0) {
				func = new Funcionario("user" + i, myPassword, "User " + i, null, "Rua Bartolomeu\n Novo Hamburgo", null);
			} else if ((i % 3) == 0) {
				func = new Funcionario("usuario" + i, myPassword, "Usu‡rio Teste da Silva Sauro " + i, true);
			} else if ((i % 5) == 0) {
				func = new Funcionario("joao" + i, myPassword, "Jo‹o Henrique " + i, "joao_nh" + i + "@hotmail.com", "Rua craktos\nNumero 3946\nEstacia Nova", "(52) 62" + i + "85465", false);
			} else if ((i % 7) == 0) {
				func = new Funcionario("gerente" + i, myPassword, "Gerente Dion’sio " + i, "cablocomaneiro" + i + "@gmail.com", null, null, true);
			} else {
				func = new Funcionario("funcionario" + i, myPassword, "Funcionario Le‹o Correia Lima da Silva Monteiro dos Santos " + i, false);
			}
			
			funcionarioDao.createIfNotExists(func);
		}
    	
    	makeMyDearAlert("Usu‡rios Criados");
    }
    
    @Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitActivityAlert("Vocï¿½ realmente deseja sair?");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
    
}
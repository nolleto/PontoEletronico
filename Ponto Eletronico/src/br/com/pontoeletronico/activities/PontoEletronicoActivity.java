package br.com.pontoeletronico.activities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PontoEletronicoActivity extends BaseActivity {
    /** Called when the activity is first created. */
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	RuntimeExceptionDao<Funcionario,Integer> funcionarioDao;
	Button btnEntrar, btnCadastrar, btnCadastroList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnEntrar = (Button) findViewById(R.id.btn_Entrar);
        btnCadastrar = (Button) findViewById(R.id.btn_Cadastrar);
        
        gerenteDao = getHelper().getGerenteRuntimeDao();
        funcionarioDao = getHelper().getFuncionarioRuntimeDao();
        
        List<Gerente> admins = gerenteDao.queryForEq("User", "Admin");
        Gerente admin;
        
        if (admins.size() < 1) {
        	admin = new Gerente("Admin", "admin", "1234");
			gerenteDao.createIfNotExists(admin);
		}
        
        btnEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText user = (EditText) findViewById(R.id.userName);
				EditText password = (EditText) findViewById(R.id.password);
				
				List<Funcionario> funcionarios = null;
				List<Gerente> gerentes = null;
				
				try {
					funcionarios = funcionarioDao.query(funcionarioDao.queryBuilder()
							.where().eq("User", user.getText().toString())
							.and().eq("Password", password.getText().toString())
							.prepare());
					
					gerentes = gerenteDao.query(gerenteDao.queryBuilder()
							.where().eq("User", user.getText().toString())
							.and().eq("Password", password.getText().toString())
							.prepare());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if (funcionarios != null && funcionarios.size() > 0){
					Intent telaFunc = new Intent(PontoEletronicoActivity.this, TelaFuncionarioActivity.class);
					telaFunc.putExtra("ID", funcionarios.get(0).funcionarioID);
					startActivity(telaFunc);
					
				} else if (gerentes != null && gerentes.size() > 0) {
					Intent telaGeren = new Intent(PontoEletronicoActivity.this, TelaGerenteActivity.class);
					telaGeren.putExtra("ID", gerentes.get(0).gerenteID);
					startActivity(telaGeren);
					
				} else {
					makeMyDearAlert("Gangnam Style");
					
				}
				
			}
		});
        
        btnCadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaCadastro = new Intent(PontoEletronicoActivity.this, CadastroActivity.class);
				startActivity(telaCadastro);
				
			}
		});
        
        btnCadastroList = (Button) findViewById(R.id.btn_Cadastros);
        btnCadastroList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaListaDeCadastros = new Intent(PontoEletronicoActivity.this, ListaContasActivity.class);
				startActivity(telaListaDeCadastros);
				
			}
		});
        
    }
    
    
}
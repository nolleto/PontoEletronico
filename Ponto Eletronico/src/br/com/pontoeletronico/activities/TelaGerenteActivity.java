package br.com.pontoeletronico.activities;

import java.util.ArrayList;
import java.util.Date;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.PontoController;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;
import br.com.pontoeletronico.util.CodeSnippet;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TelaGerenteActivity extends BaseActivity {
	Funcionario gerente;
	Funcionario_Ponto gerentePonto;
	RuntimeExceptionDao<Funcionario, Integer> gerenteDao;
	RuntimeExceptionDao<Ponto, Integer> pontoDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> gerentePontoDao;
	int id;

	ArrayList<Integer> listaIDs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tela_funcionario);
	}
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_gerente);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		id = getIntent().getExtras().getInt("ID");
		listaIDs = new ArrayList<Integer>();
		
		pontoDao = getHelper().getPontoRuntimeDao();
		gerentePontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		gerenteDao = getHelper().getFuncionarioRuntimeDao();
		gerente = gerenteDao.queryForId(id);
		
		setTitleInActionBar(gerente.Name);
		
		Button btnPonto = (Button) findViewById(R.id.telaGerente_Ponto);
		Button btnAltCadastro = (Button) findViewById(R.id.telaGerente_AlterarUserInfo);
		Button btnListaPontos = (Button) findViewById(R.id.telaGerente_LogPontos);
		Button btnGerenciarFunc = (Button) findViewById(R.id.telaGerente_Func);
		Button btnConfig = (Button) findViewById(R.id.telaGerente_Config);

		//Admin n‹o pode alterar seus dados
		if (gerente.User.equals("admin")) {
			btnAltCadastro.setVisibility(LinearLayout.GONE);
		}
		
		btnPonto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					gerentePonto = gerentePontoDao
							.queryForFirst(gerentePontoDao.queryBuilder()
									.orderBy("funcionario_pontoID", false)
									.where().eq("funcionarioID", gerente.funcionarioID)
									.prepare());

				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (gerentePonto == null || 
						(gerentePonto.ponto.inputDate != null && gerentePonto.ponto.outputDate != null)) {
					PontoController.checkIn(getHelper(), gerente);
				} else if (gerentePonto.ponto.inputDate != null && 
						gerentePonto.ponto.outputDate == null) {
					PontoController.checkOut(getHelper(), gerentePonto);
				} else {
					makeMyDearAlert("Erro ap dar Ponto!!!");
				}

			}
		});

		btnAltCadastro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent alterar = new Intent(TelaGerenteActivity.this,
						AlterarCadastroActivity.class);
				alterar.putExtra("ID", gerente.funcionarioID);

				startActivity(alterar);

			}
		});

		btnListaPontos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent listaPontos = new Intent(TelaGerenteActivity.this,
						ListaPontosActivity.class);
				listaPontos.putExtra("ID", gerente.funcionarioID);

				startActivity(listaPontos);

			}
		});

		btnAltCadastro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlterarCadastroActivity.startActivity(TelaGerenteActivity.this, id);

			}
		});

		btnConfig.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TelaConfiguracoes.startActivity(TelaGerenteActivity.this);
				
			}
		});
		
		btnGerenciarFunc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GerenciarFuncionariosActivity.startActivity(TelaGerenteActivity.this, id);
				
			}
		});
		
	}*/

	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, TelaGerenteActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

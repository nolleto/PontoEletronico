package br.com.pontoeletronico.activities;

import java.util.ArrayList;
import java.util.Date;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Gerente;
import br.com.pontoeletronico.database.Ponto;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TelaGerenteActivity extends BaseActivity {
	ImageView imgPonto;
	Gerente gerente;
	Funcionario_Ponto gerentePonto;
	RuntimeExceptionDao<Ponto, Integer> pontoDao;
	RuntimeExceptionDao<Gerente, Integer> gerenteDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> gerentePontoDao;
	int id;
	
	CharSequence[] items = {"Usu�rio", "Senha", "Nome", "Email", "Endere�o", "Telefone"};
	boolean[] itemsChecked = new boolean [items.length];
	ArrayList<Integer> listaIDs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_gerente);
		
		id = getIntent().getExtras().getInt("ID");
		listaIDs = new ArrayList<Integer>();
		
		pontoDao = getHelper().getPontoRuntimeDao();
		gerentePontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		gerenteDao = getHelper().getGerenteRuntimeDao();
		gerente = gerenteDao.queryForId(id);
		
		imgPonto = (ImageView) findViewById(R.id.telaGerente_Img);
		
		TextView txtNome = (TextView) findViewById(R.id.telaGerente_Nome);
		txtNome.setText(gerente.Name);
		
		Button btnPonto = (Button) findViewById(R.id.telaGerente_Ponto);
		Button btnErroPonto = (Button) findViewById(R.id.telaGerente_ErroPonto);
		Button btnAltCadastro = (Button) findViewById(R.id.telaGerente_AlterarUserInfo);
		Button btnListaPontos = (Button) findViewById(R.id.telaGerente_LogPontos);
		Button btnGerenciarFunc = (Button) findViewById(R.id.telaGerente_Func);
		Button btnConfig = (Button) findViewById(R.id.telaGerente_Config);
		
		btnPonto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					gerentePonto = gerentePontoDao.queryForFirst(gerentePontoDao.queryBuilder()
							.orderBy("funcionario_pontoID", false)
							.where().eq("gerenteID", gerente.gerenteID)
							.prepare());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (gerentePonto == null || (gerentePonto.ponto.inputDate != null && gerentePonto.ponto.outputDate != null)) {
					marcarEntrada();
				} else if (gerentePonto.ponto.inputDate != null && gerentePonto.ponto.outputDate == null){
					marcarSaida();
				} else {
					makeMyDearAlert("Erro ap dar Ponto!!!");
				}
				
			}
		});
		
		btnAltCadastro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent alterar = new Intent(TelaGerenteActivity.this, AlterarCadastroActivity.class);
				alterar.putExtra("ID", gerente.gerenteID);
				
				startActivity(alterar);
				
				
			}
		});
		
		btnListaPontos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent listaPontos = new Intent(TelaGerenteActivity.this, ListaPontosActivity.class);
				listaPontos.putExtra("ID", gerente.gerenteID);
				
				startActivity(listaPontos);	
				
			}
		});
	
		btnAltCadastro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlterarCadastroActivity.startActivity(TelaGerenteActivity.this, id, true);
				
			}
		});
		
	}
	
	public void marcarEntrada() {
		Ponto ponto = new Ponto();
		ponto.inputDate = new Date();
		pontoDao.create(ponto);
		
		Funcionario_Ponto funcPonto = new Funcionario_Ponto(gerente, ponto);
		gerentePontoDao.create(funcPonto);
		
		makeMyDearAlert("Entrada!!!");
	}
	
	public void marcarSaida() {
		Ponto ponto = gerentePonto.ponto;
		ponto.outputDate = new Date();
		pontoDao.update(ponto);
		
		makeMyDearAlert("Saída!!!");
	}
	
}

package br.com.pontoeletronico.activities;

import java.util.ArrayList;
import java.util.Date;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaGerenteActivity extends BaseActivity {
	ImageView imgPonto;
	Funcionario gerente;
	Funcionario_Ponto gerentePonto;
	RuntimeExceptionDao<Funcionario, Integer> gerenteDao;
	RuntimeExceptionDao<Ponto, Integer> pontoDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> gerentePontoDao;
	int id;

	CharSequence[] items = { "Usu�rio", "Senha", "Nome", "Email",
			"Endere�o", "Telefone" };
	boolean[] itemsChecked = new boolean[items.length];
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
		gerenteDao = getHelper().getFuncionarioRuntimeDao();
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
					marcarEntrada();
				} else if (gerentePonto.ponto.inputDate != null && 
						gerentePonto.ponto.outputDate == null) {
					marcarSaida();
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

	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, TelaGerenteActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

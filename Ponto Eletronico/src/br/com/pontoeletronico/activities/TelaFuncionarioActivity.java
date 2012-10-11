package br.com.pontoeletronico.activities;

import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFuncionarioActivity extends BaseActivity {
	ImageView imgPonto;
	Funcionario funcionario;
	Funcionario_Ponto funcionarioPonto;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	RuntimeExceptionDao<Ponto, Integer> pontoDao;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_funcionario);
		
		int id = getIntent().getExtras().getInt("ID");
		
		pontoDao = getHelper().getPontoRuntimeDao();
		funcionarioPontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		funcionario = funcionarioDao.queryForId(id);
		
		imgPonto = (ImageView) findViewById(R.id.telaFuncionario_Img);
		
		TextView txtNome = (TextView) findViewById(R.id.telaFuncionario_Nome);
		txtNome.setText(funcionario.Name);
		
		Button btnPonto = (Button) findViewById(R.id.telaFuncionario_Ponto);
		Button btnErroPonto = (Button) findViewById(R.id.telaFuncionario_ErroPonto);
		Button btnAltCadastro = (Button) findViewById(R.id.telaFuncionario_AlterarUserInfo);
		Button btnListaPontos = (Button) findViewById(R.id.telaFuncionario_LogPontos);
		
		btnPonto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<Funcionario_Ponto> list;
				
				try {
					funcionarioPonto = funcionarioPontoDao.queryForFirst(funcionarioPontoDao.queryBuilder()
							.orderBy("funcionario_pontoID", false)
							.where().eq("funcionarioID", funcionario.funcionarioID)
							.prepare());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (funcionarioPonto == null || (funcionarioPonto.ponto.inputDate != null && funcionarioPonto.ponto.outputDate != null)) {
					marcarEntrada();
				} else if (funcionarioPonto.ponto.inputDate != null && funcionarioPonto.ponto.outputDate == null){
					marcarSaida();
				} else {
					makeMyDearAlert("Erro ap dar Ponto!!!");
				}
			}
		});
		
		btnAltCadastro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent alterar = new Intent(TelaFuncionarioActivity.this, AlterarCadastroActivity.class);
				alterar.putExtra("ID", funcionario.funcionarioID);
				
				startActivity(alterar);
				
			}
		});
		
		btnListaPontos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent listaPontos = new Intent(TelaFuncionarioActivity.this, ListaPontosActivity.class);
				listaPontos.putExtra("ID", funcionario.funcionarioID);
				
				startActivity(listaPontos);	
				
			}
		});
		
	
		
	}
	
	public void marcarEntrada() {
		Ponto ponto = new Ponto();
		ponto.inputDate = new Date();
		pontoDao.create(ponto);
		
		Funcionario_Ponto funcPonto = new Funcionario_Ponto(funcionario, ponto);
		funcionarioPontoDao.create(funcPonto);
		
		makeMyDearAlert("Entrada!!!");
	}
	
	public void marcarSaida() {
		Ponto ponto = funcionarioPonto.ponto;
		ponto.outputDate = new Date();
		pontoDao.update(ponto);
		
		makeMyDearAlert("Sa�da!!!");
	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, TelaFuncionarioActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

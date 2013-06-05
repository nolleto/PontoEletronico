package br.com.pontoeletronico.activities;

import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioPontoController;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListaPontosActivity extends BaseActivity {
	ListView listView;
	ListAdapter adapter;
	List<Funcionario_Ponto> funcionarioPontoList;
	RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		listView = (ListView) findViewById(R.id.listView);
		
		funcionarioPontoDao = getHelper().getFuncionario_PontoRuntimeDao();
		funcionarioDao = getHelper().getFuncionarioRuntimeDao();
		
		funcionario = funcionarioDao.queryForId(getIntent().getExtras().getInt("ID"));
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitleInActionBar(funcionario.Name);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				if (id == 0)
					return false;
				
				final Funcionario_Ponto funcionario_Ponto = (Funcionario_Ponto) view.getTag();
				optionActivityAlert(new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						FuncionarioPontoController.deleteFuncionarioPonto(getHelper(), funcionario_Ponto);
						refreshListView();
					}
				}, getString(R.string.str_Message_DeleteItemListPonto));
				
				return false;
			}
		});
		
		refreshListView();
		
	}
	
	public void refreshListView() {
		ShowLoad();
		
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				adapter = FuncionarioPontoController.getArrayAdpter(getHelper(), ListaPontosActivity.this, funcionario.funcionarioID);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				ListaPontosActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						listView.setAdapter(adapter);
						ListaPontosActivity.this.HideLoad();
					}
				});
				super.onPostExecute(result);
			}
		};
		
		asyncTask.execute();
	}
	
	public static void startActivity(Activity activity, int id) {
		Intent intent = new Intent(activity, ListaPontosActivity.class);
		intent.putExtra("ID", id);
		activity.startActivity(intent);
	}
	
}

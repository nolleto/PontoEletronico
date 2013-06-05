package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.adapter.ListaPontosAdapter;
import br.com.pontoeletronico.data.controller.FuncionarioPontoController;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import android.content.DialogInterface;
import android.graphics.AvoidXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaDePontosFragment extends BaseFragment {
	ListView listView;
	ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.listview, null);
		
		setSetting();
		
		listView = (ListView) root.findViewById(R.id.listView);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				if (id == 0)
					return false;
				
				final Funcionario_Ponto funcionario_Ponto = (Funcionario_Ponto) view.getTag();
				activity.optionActivityAlert(new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						FuncionarioPontoController.deleteFuncionarioPonto(activity.getHelper(), funcionario_Ponto);
						refreshListView();
					}
				}, activity.getString(R.string.str_Message_DeleteItemListPonto));
				
				return false;
			}
		});
		
		refreshListView();
	}
	
	public void refreshListView() {
		activity.ShowLoad();
		
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				adapter = FuncionarioPontoController.getArrayAdpter(activity.getHelper(), activity, funcionarioId);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						listView.setAdapter(adapter);
						activity.HideLoad();
						
					}
				});
				super.onPostExecute(result);
			}
		};
		
		asyncTask.execute();
		
	}
	
	
}

package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.InfoFuncionarioActivity;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class GerenciarFuncionariosFragment extends BaseFragment {
	ListView listView;
	ListAdapter adapter;
	SearchView searchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.gerenciar_funcionarios, null);
		
		setSetting();
		
		searchView = (SearchView) root.findViewById(R.id.gerencFuncionarios_SearcView);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				refreshListView(true, query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				
				return false;
			}
			
		});
		searchView.setOnCloseListener(new OnCloseListener() {
			
			@Override
			public boolean onClose() {
				refreshListView(false, null);
				return false;
			}
		});
		
		
		listView = (ListView) root.findViewById(R.id.gerencFuncionarios_ListView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				int iD = (int) id;
				if (iD != 0) {
					InfoFuncionarioActivity.startActivity(activity, iD);
				}
				
			}
		});
		
		/*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				if (id == 0)
					return false;
				
				

				return false;
			}
		});*/
		
		refreshListView(false, null);
		
	}
	
	private void refreshListView(final Boolean isSearch, String param) {
		activity.ShowLoad();
		
		AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
			
			@Override
			protected Void doInBackground(String... params) {
				if (isSearch) {
					adapter = FuncionarioController.getAdapterSearch(activity, params[0]);
				} else {
					adapter = FuncionarioController.getAdapter(activity.getHelper(), activity);
				}
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
		
		asyncTask.execute(param);
	}
	
}

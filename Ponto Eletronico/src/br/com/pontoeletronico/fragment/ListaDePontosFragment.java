package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioPontoController;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListaDePontosFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.listview, null);
		
		setSetting();
		
		TextView txtTitle = (TextView) root.findViewById(R.id.listView_textView1);
		txtTitle.setVisibility(LinearLayout.GONE);
		
		ListView listView = (ListView) root.findViewById(R.id.listView);
		listView.setAdapter(FuncionarioPontoController.getArrayAdpter(activity.getHelper(), activity, funcionarioId));
		listView.setClickable(false);
		
	}
	
}

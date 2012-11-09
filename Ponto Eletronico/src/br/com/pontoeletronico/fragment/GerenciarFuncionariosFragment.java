package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import android.os.Bundle;
import android.widget.ListView;

public class GerenciarFuncionariosFragment extends BaseFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.gerenciar_funcionarios, null);
		
		setSetting();
		
		ListView listView = (ListView) root.findViewById(R.id.gerencFuncionarios_ListView);
		listView.setAdapter(FuncionarioController.getAdapter(activity.getHelper(), activity));
	} 
	
}

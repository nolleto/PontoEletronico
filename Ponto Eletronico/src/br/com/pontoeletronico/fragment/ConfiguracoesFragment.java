package br.com.pontoeletronico.fragment;

import br.com.pontoeletronico.R;
import android.os.Bundle;

public class ConfiguracoesFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = getActivity().getLayoutInflater().inflate(R.layout.configuracoes, null);
		
		setSetting();
		
	}
}

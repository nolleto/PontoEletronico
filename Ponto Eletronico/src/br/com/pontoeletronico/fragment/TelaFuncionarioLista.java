package br.com.pontoeletronico.fragment;

import java.util.ArrayList;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.BaseActivity;
import br.com.pontoeletronico.activities.GerenciarFuncionariosActivity;
import br.com.pontoeletronico.database.Funcionario;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TelaFuncionarioLista extends ListFragment {
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	Funcionario funcionario;
	ArrayList<String> items;
	SparseArray<Fragment> fragments;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BaseActivity activity = (BaseActivity) getActivity();
		funcionarioDao = activity.getHelper().getFuncionarioRuntimeDao();
		
		int id = activity.getIntent().getExtras().getInt("ID");
		funcionario = funcionarioDao.queryForId(id);
		items = new ArrayList<String>();
		fragments = new SparseArray<Fragment>();
		
		items.add(getString(R.string.str_Button_ListCheckInOut));
		
		if (funcionario.isGerente) {
			if (funcionario.funcionarioID != 1) { //Usuário não é Admin
				items.add(getString(R.string.str_Button_ChangeUserInfo));

			} 
			items.add(getString(R.string.activity_GerenciarFuncionarios));
			items.add(getString(R.string.str_Button_Configurations));
		} else {
			items.add(getString(R.string.str_Button_ChangeUserInfo));
		}
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, items));
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		if (items.get(position).equals(getString(R.string.str_Button_ListCheckInOut))) {
			getFragmente(position);
		} else if (items.get(position).equals(R.string.str_Button_ChangeUserInfo)) {
			
		} else if (items.get(position).equals(getString(R.string.activity_GerenciarFuncionarios))) {
			
		} else {
			
		}
		
		exibirFragmento(new ListaDePontosFragment());
		exibirFragmento(new AlterarInfoFragment());
		exibirFragmento(new GerenciarFuncionariosFragment());
		exibirFragmento(new ConfiguracoesFragment());

		
	}
	
	/**
	 * Pega um {@link Fragment} dentro de ume {@link SparseArray} ou cria e logo em seguida adiciona
	 * dentro do {@link SparseArray} com o objetivo de não precisar ficar realocando o {@link Fragment}
	 * multiplas vezes ou até mesmo não perder o progresso do usuário, caso ele tenha escrito/modificado
	 * alguma coisa e não deseje perder.
	 * 
	 * @return
	 * 		{@link Fragment}.
	 */
	private Fragment getFragmente(int position) {
		Fragment fragment = fragments.get(position);
		switch (position) {
		case 0:
			fragment = new ListaDePontosFragment();
			break;
			
		case 1:	
			fragment = new AlterarInfoFragment();
			break;
		case 2:
			fragment = new GerenciarFuncionariosFragment();
			break;
			
		default:
			fragment = new ConfiguracoesFragment();
			break;
		}
		
		if (fragment == null) {
			
			fragments.append(position, fragment);
		}
		return null;
	}
	
	/**
	 * Exiber ou troca de {@link Fragment} na tela para o usuário. 
	 * 
	 * @param details -	
	 * 		{@link Fragment} a ser exibido.
	 */
	private void exibirFragmento(Fragment details) {
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.replace(R.id.detailFragment, details);
	    
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    ft.commit();
	}
	
}

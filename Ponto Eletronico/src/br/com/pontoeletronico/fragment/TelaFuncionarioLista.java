package br.com.pontoeletronico.fragment;

import java.util.ArrayList;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.BaseActivity;
import br.com.pontoeletronico.activities.TelaFuncionarioActivity;
import br.com.pontoeletronico.adapter.ListaMenuFuncionarioAdapter;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.util.CodeSnippet;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class TelaFuncionarioLista extends ListFragment {
	TelaFuncionarioActivity activity;
	RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	ListaMenuFuncionarioAdapter adapter;
	Funcionario funcionario;
	ArrayList<String> items;
	SparseArray<Fragment> fragments;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activity = (TelaFuncionarioActivity) getActivity().getIntent().getSerializableExtra("Activity");
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
		
		adapter = new ListaMenuFuncionarioAdapter(activity, items);
		
		setListAdapter(adapter); //new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, items)
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Fragment fragment = getFragment(0, false);
		exibirFragmento(fragment);
		adapter.setSelectedView(0);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Fragment fragment = getFragment(position, false);
		exibirFragmento(fragment);
		adapter.setSelectedView(position);
		v.setSelected(true);
	}
	
	/**
	 * Pega um {@link Fragment} dentro de ume {@link SparseArray} ou cria e logo em seguida adiciona
	 * dentro do {@link SparseArray} com o objetivo de não precisar ficar realocando o {@link Fragment}
	 * multiplas vezes ou até mesmo não perder o progresso do usuário, caso ele tenha escrito/modificado
	 * alguma coisa e não deseje perder.
	 * 
	 * @param position -
	 * 		Positção do {@link Fragment}.
	 * @param reaproveitar -
	 * 		Se deseja reaprovitar o {@link Fragment}. 
	 * @return
	 * 		{@link Fragment}.
	 */
	private Fragment getFragment(int position, Boolean reaproveitar) {
		Fragment fragment = null;
		if (reaproveitar) {
			fragment = fragments.get(position);
		}
		 		
		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new ListaDePontosFragment();
				break;
				
			case 1:	
				if (funcionario.funcionarioID == 1) {
					fragment = new GerenciarFuncionariosFragment();
				} else {
					fragment = new AlterarInfoFragment();
				}
				
				break;
			case 2:
				if (funcionario.funcionarioID == 1) {
					fragment = new ConfiguracoesFragment();
				} else {
					fragment = new GerenciarFuncionariosFragment();
				}
				
				break;
				
			default:
				fragment = new ConfiguracoesFragment();
				break;
			}
			
			fragments.append(position, fragment);
		}
		
		return fragment;
	}
	
	/**
	 * Exiber ou troca de {@link Fragment} na tela para o usuário. 
	 * 
	 * @param details -	
	 * 		{@link Fragment} a ser exibido.
	 */
	private void exibirFragmento(Fragment details) {
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.replace(R.id.telaFuncionario_Detail, details);
	    
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    ft.commit();
	}
	
}

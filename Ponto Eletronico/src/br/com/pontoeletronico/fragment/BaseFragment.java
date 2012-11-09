package br.com.pontoeletronico.fragment;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.activities.BaseActivity;
import br.com.pontoeletronico.data.controller.FuncionarioController;
import br.com.pontoeletronico.database.Funcionario;
import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	public BaseActivity activity;
	public View root;
	public int funcionarioId;
	public Funcionario funcionario;
	public RuntimeExceptionDao<Funcionario, Integer> funcionarioDao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return root;
	}
	
	/**
	 * Seta as variaveis {@link BaseFragment#activity}, {@link BaseFragment#funcionarioId}, {@link BaseFragment#funcionarioDao}, {@link BaseFragment#funcionario}.
	 * 
	 */
	public void setSetting() {
		activity = (BaseActivity) getActivity();
		funcionarioId = activity.getIntent().getExtras().getInt("ID");
		funcionarioDao = activity.getHelper().getFuncionarioRuntimeDao();
		funcionario = FuncionarioController.getFuncionarioForId(activity.getHelper(), funcionarioId);
	}
	
}

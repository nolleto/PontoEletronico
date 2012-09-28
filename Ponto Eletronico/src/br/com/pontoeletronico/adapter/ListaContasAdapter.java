package br.com.pontoeletronico.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.digitaldesk.pontoeletronico.R;
import br.com.digitaldesk.pontoeletronico.R.id;
import br.com.digitaldesk.pontoeletronico.R.layout;
import br.com.pontoeletronico.activities.FuncionarioGerente;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Gerente;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaContasAdapter extends BaseAdapter {
	ArrayList<FuncionarioGerente> list;
	Context context;
	
	public ListaContasAdapter(Context context, List<Gerente> gerentes, List<Funcionario> funcionarios) {
		super();
		this.context = context;
		list = new ArrayList<FuncionarioGerente>();
		
		if (gerentes.size() > 0 && gerentes != null) {
			for (Gerente gerente : gerentes) {
				FuncionarioGerente funcionarioGerente = new FuncionarioGerente(true, gerente.gerenteID, gerente.Name, gerente.Email, gerente.User, gerente.Password, gerente.Adress, gerente.Phone);
				list.add(funcionarioGerente);
			}
		}
		if (funcionarios.size() > 0 && funcionarios != null) {
			for (Funcionario funcionario : funcionarios) {
				FuncionarioGerente funcionarioGerente = new FuncionarioGerente(false, funcionario.funcionarioID, funcionario.Name, funcionario.Email, funcionario.User, funcionario.Password, funcionario.Adress, funcionario.Phone);
				list.add(funcionarioGerente);
			}
		}
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout)View.inflate(context, R.layout.listview, null);
		}
		FuncionarioGerente item = list.get(position);
		LinearLayout view = (LinearLayout)View.inflate(context, R.layout.listview, null);
		
		TextView name = (TextView) view.findViewById(R.id.listview_Text);
		name.setText(item.User);
		
		if (item.isGerente) {
			name.setTextSize(40);
		} else {
			name.setTextSize(20);
		}
		
		return view;
	
	}

	
	
}

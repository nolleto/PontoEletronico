package br.com.pontoeletronico.adapter;

import java.util.List;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaContasAdapter extends BaseAdapter {
	List<Funcionario> list;
	Context context;
	
	public ListaContasAdapter(Context context, List<Funcionario> funcionarios) {
		super();
		this.context = context;
		this.list = funcionarios;
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
		Funcionario item = list.get(position);
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

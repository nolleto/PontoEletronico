package br.com.pontoeletronico.adapter;

import java.util.List;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario_Ponto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaPontosAdapter extends BaseAdapter {	
	List<Funcionario_Ponto> list;
	Context context;
	
	public ListaPontosAdapter(Context context, List<Funcionario_Ponto> list) {
		super();
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		Funcionario_Ponto item = list.get(position);
		LinearLayout view = (LinearLayout)View.inflate(context, R.layout.listview, null);
		
		TextView entrada = (TextView) view.findViewById(R.id.listview_Text);
		entrada.setText("Entrada: " + item.ponto.inputDate.toString());
		
		if (item.ponto.outputDate != null) {
			TextView saida = (TextView) view.findViewById(R.id.listview_Text2);
			saida.setVisibility(LinearLayout.VISIBLE);
			saida.setText("Saida: "+ item.ponto.outputDate.toString());
		}
		
		return view;
	}

}

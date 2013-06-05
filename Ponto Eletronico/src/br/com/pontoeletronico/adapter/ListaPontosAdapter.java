package br.com.pontoeletronico.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.database.Funcionario_Ponto;

import android.content.Context;
import android.text.format.DateFormat;
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
		return list.get(position).funcionario_pontoID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout)View.inflate(context, R.layout.item_listview, null);
		}
		Funcionario_Ponto item = list.get(position);
		
		Date dateEntrada = item.ponto.inputDate;
		Date dateSaida = item.ponto.outputDate;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss (EEEE)");

		TextView entrada = (TextView) convertView.findViewById(R.id.listview_Text);
		entrada.setText(context.getString(R.string.simpleWord_Entrada) + ": " + format.format(dateEntrada).toString());
		
		if (item.ponto.outputDate != null) {
			TextView saida = (TextView) convertView.findViewById(R.id.listview_Text2);
			saida.setVisibility(LinearLayout.VISIBLE);
			saida.setText(context.getString(R.string.simpleWord_Saida) + ":     "+ format.format(dateSaida).toString());
		}
			
		convertView.setTag(item);
		
		return convertView;
	}

}

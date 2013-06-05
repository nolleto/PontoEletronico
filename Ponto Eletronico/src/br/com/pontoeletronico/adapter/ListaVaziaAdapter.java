package br.com.pontoeletronico.adapter;

import br.com.pontoeletronico.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaVaziaAdapter extends BaseAdapter {
	Context context;
	String message;
	
	public ListaVaziaAdapter(Context context, String message) {
		super();
		this.context = context;
		this.message = message;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout)View.inflate(context, R.layout.item_listview, null);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.listview_Text);
		txt.setText(message);
		
		return convertView;
	}

}

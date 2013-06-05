package br.com.pontoeletronico.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import br.com.pontoeletronico.R;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaMenuFuncionarioAdapter extends BaseAdapter {
	ArrayList<String> list;
	Context context;
	SparseArray<ImageView> sparseArray;
	
	public ListaMenuFuncionarioAdapter(Context context, ArrayList<String> list) {
		super();
		this.context = context;
		this.list = list;
		sparseArray = new SparseArray<ImageView>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout)View.inflate(context, R.layout.item_listviewmenu, null);
		}
		
		TextView textView = (TextView) convertView.findViewById(R.id.listViewMenu_Text);
		textView.setText(list.get(position));
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.listViewMenu_ImageCheck);
		
		sparseArray.append(position, imageView);
		
		return convertView;
	}
	
	public void setSelectedView(int position) {
		if (sparseArray.size() > 0) {
			for (int i = 0; i < sparseArray.size(); i++) {
				ImageView imageView = sparseArray.get(i);
				imageView.setVisibility(LinearLayout.INVISIBLE);
				if (i == position) {
					imageView.setVisibility(LinearLayout.VISIBLE);
					imageView.bringToFront();
				}
			}		
		}
	}

}

package br.com.pontoeletronico.adapter;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.InfoFuncionarioActivity;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaFuncionariosAdapter extends BaseAdapter {
	Context context;
	List<Funcionario> list;
	DaoProvider daoProvider;
	Activity activity;
	
	public ListaFuncionariosAdapter(Activity activity, DaoProvider daoProvider,List<Funcionario> list) {
		super();
		this.context = activity.getBaseContext();
		this.list = list;
		this.daoProvider = daoProvider;
		this.activity = activity;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout)View.inflate(context, R.layout.item_listview_funcionario, null);
		}
		final Funcionario item = list.get(position);
		List<Funcionario_Ponto> funcPonto = null;
		
		//Cor na cell
		/*if ((position % 2) == 0) {
			view.setBackgroundColor(Color.rgb(200, 201, 202));
		}*/
		
		try {
			funcPonto = daoProvider.getFuncionario_PontoDao().queryForEq("funcionarioID", item.funcionarioID);
			
			if (funcPonto.size() > 0) { //Se tiver Pontos
				funcPonto = daoProvider.getFuncionario_PontoRuntimeDao().query(daoProvider.getFuncionario_PontoRuntimeDao().queryBuilder()
						.orderBy("funcionario_pontoID", false)
						.where().eq("funcionarioID", item.funcionarioID)
						.prepare());
			} else {
				funcPonto = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.listviewFunc_Name);
		TextView on_off = (TextView) convertView.findViewById(R.id.listviewFunc_Text2);
		TextView status = (TextView) convertView.findViewById(R.id.listviewFunc_Status);
		TextView moreInfo = (TextView) convertView.findViewById(R.id.listviewFunc_More);
		
		moreInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoFuncionarioActivity.startActivity(activity, item.funcionarioID);
				
			}
		});
		
		name.setText(item.Name);
		
		if (funcPonto != null && funcPonto.size() > 0) {
			Funcionario_Ponto funcionarioPonto = funcPonto.get(0);
			Date today = new Date();
			
			if (funcionarioPonto.ponto.outputDate == null) {
				//ta na empressa ou esqueceu do check out
				on_off.setText("ON");
				
				if (funcionarioPonto.ponto.inputDate.getDay() == today.getDay() && funcionarioPonto.ponto.inputDate.getMonth() == today.getMonth()) { //A data de Check In Ž hoje.
					//Est‡ na empressa
					status.setText("Status: Na empresa");
				} else {
					//Teoricamente nunca vai cair, mas...    (Esqueceu do CheckOut)
					status.setText("Status: Esqueceu do CheckOut");
				}
				
			} else {
				//ja saiu ou nao chegou
				on_off.setText("OFF");
				
				if (funcionarioPonto.ponto.outputDate.getDay() == today.getDay() && funcionarioPonto.ponto.outputDate.getMonth() == today.getMonth()) { //A data de Check Out Ž hoje.
					//J‡ saiu
					status.setText("Status: J‡ saiu");
					
				} else {
					//N‹o chegou
					status.setText("Status: N‹o chegou.");
				}
			}
			
			
		} else {
			on_off.setText("OFF");
			status.setText("Status: Sem atividade.");
		}
		
		return convertView;
	}

}

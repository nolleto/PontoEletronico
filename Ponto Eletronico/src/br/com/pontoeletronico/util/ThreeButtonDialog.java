package br.com.pontoeletronico.util;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.pontoeletronico.R;

public class ThreeButtonDialog {

	private Dialog dialog;
	
	private Activity currentActivity;
	
	private LinearLayout buttonBox;
	private TextView label;
	private Button btn1, btn2, btn3;
	
	private HashMap<Button, Integer> weightMap = new HashMap<Button, Integer>();
	
	public ThreeButtonDialog(Activity currentActivity) {
		this.currentActivity = currentActivity;
		onCreate();
	}
	
	private void onCreate() {
		dialog = new Dialog(currentActivity);
		dialog.setContentView(R.layout.pview_alert_three_button);
		
		buttonBox = (LinearLayout) dialog.findViewById(android.R.id.widget_frame);
		label = (TextView) dialog.findViewById(android.R.id.text1);
		btn1 = (Button) dialog.findViewById(android.R.id.button1);
		btn2 = (Button) dialog.findViewById(android.R.id.button2);
		btn3 = (Button) dialog.findViewById(android.R.id.button3);
		
		weightMap.put(btn1, 2);
		weightMap.put(btn2, 2);
		weightMap.put(btn3, 1);
	}
	
	public void setCancelable(boolean isCancelable) {
		dialog.setCancelable(isCancelable);
	}
	
	public void show() {
		dialog.show();
	}
	
	/**
	 * Método que destrói a janela de dialogo
	 * 
	 */
	public void ploft() {
		dialog.dismiss();
	}
	
	public Dialog getDialog() {
		return this.dialog;
	}
	
	/**
	 * Altera a visibilidade do botão, dependendo do estado dele e altera a soma total do weight
	 * do box de botões
	 * 
	 * @param btn botão para o tratamento
	 */
	private void toggleButton(Button btn) {
		switch(btn.getVisibility()) {
		case View.GONE:
			btn.setVisibility(View.VISIBLE);
			buttonBox.setWeightSum(buttonBox.getWeightSum() + weightMap.get(btn));
			break;
		case View.VISIBLE:
			btn.setVisibility(View.GONE);
			buttonBox.setWeightSum(buttonBox.getWeightSum() - weightMap.get(btn));
			break;
		}
	}
	
	/**
	 * Inverte a visibilidade do botão. Se está invisivel vai para visivel e vice-versa
	 * 
	 */
	public void toggleFirstButton() {
		toggleButton(btn1);
	}
	
	public void setFirstButtonLabel(String label) {
		this.btn1.setText(label);
	}
	
	public void setFirstButtonClickListener(OnClickListener listener) {
		this.btn1.setOnClickListener(listener);
	}
	
	/**
	 * Inverte a visibilidade do botão. Se está invisivel vai para visivel e vice-versa
	 * 
	 */
	public void toggleSecondButton() {
		toggleButton(btn2);
	}
	
	public void setSecondButtonLabel(String label) {
		this.btn2.setText(label);
	}
	
	public void setSecondButtonClickListener(OnClickListener listener) {
		this.btn2.setOnClickListener(listener);
	}
	
	/**
	 * Inverte a visibilidade do botão. Se está invisivel vai para visivel e vice-versa
	 * 
	 */
	public void toggleThirdButton() {
		toggleButton(btn3);
	}
	
	public void setThirdButtonLabel(String label) {
		this.btn3.setText(label);
	}
	
	public void setThirdButtonClickListener(OnClickListener listener) {
		this.btn3.setOnClickListener(listener);
	}
	
	public void setTitle(String title) {
		this.dialog.setTitle(title);
	}
	
	public void setLabel(String label) {
		this.label.setText(label);
	}
}

package br.com.pontoeletronico.util;

import br.com.pontoeletronico.database.DaoProvider;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AlertWithEditText extends Dialog {
	private OnConfirmationEventListener onConfirmated;
	public Button btn;
	public EditText editText;
	
	/**
	 * Gera um {@link Dialog} com um {@link EditText} e um {@link Button} de 'OK'. Toda vez que o usuário
	 * clicar no Botão, o método {@link AlertWithEditText.OnConfirmationEventListener} será chamdo.
	 * 
	 * @param context - 	
	 * 		{@link Context} da {@link Activity}.
	 * @param daoProvider -	
	 * 		Objeto de conceção com DataBase.
	 * @param title -
	 * 		Título do {@link Dialog}.
	 * @param onConfirmationEventListener -
	 * 		Evento disparado toda vez que o usuário clica no {@link Button} 'OK'.
	 */
	public AlertWithEditText(Context context, final DaoProvider daoProvider, String title, OnConfirmationEventListener onConfirmationEventListener) {
		super(context);
		this.onConfirmated = onConfirmationEventListener;
		this.setTitle(title);
		
		editText = new EditText(context);
		editText.setSingleLine(true);
		
		btn = new Button(context);
		btn.setText("OK");
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onConfirmated.onButtonClickListener(editText.getText().toString(), btn, AlertWithEditText.this);
				
			}
		});
		
		LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LinearLayout linear = new LinearLayout(context);
		linear.setOrientation(LinearLayout.VERTICAL);
		
		linear.addView(editText);
		linear.addView(btn);
		
		this.addContentView(linear, params);
	}
	
	/**
	 * {@link Class} abstrata.
	 * 
	 * @author digitaldeskmini
	 *
	 */
	public static abstract class OnConfirmationEventListener{
		/**
		 * Esse método será chamado toda vez que o usuário clicar no {@link Button} da {@link AlertWithEditText}.  
		 * 
		 * @param editText - 
		 * 		{@link String} retirada do {@link EditText} do {@link Dialog}.
		 * @param button -	
		 * 		{@link Button} do {@link Dialog}.
		 * @param dialog -
		 * 		{@link Dialog} onde acontece tudo.(hehehe)
		 */
		public abstract void onButtonClickListener(String editText, Button button, Dialog dialog);
		//public abstract void onConfirmClick(String editText);
	}
}

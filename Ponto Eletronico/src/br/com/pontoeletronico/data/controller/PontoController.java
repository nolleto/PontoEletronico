package br.com.pontoeletronico.data.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;
import br.com.pontoeletronico.database.Configuracoes;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;
import br.com.pontoeletronico.util.CodeSnippet;

public class PontoController {

	/**
	 * Faz o check in/out do funcionário. Esse método irá verificar de acordo com os dados
	 * no Banco de Dados, se o funcionários está entrnado(check in) ou saindo(check out)
	 * do serviço.
	 * 
	 * @param daoProvider -		
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionario -
	 * 		Objeto que deve ser o funcionário que vai marcar o ponto.
	 * @return
	 * 		Uma {@link String} com uma mensagem informativa para o usuário.
	 */	
	public static String smartCheckInOut(DaoProvider daoProvider, Funcionario funcionario) {
		Funcionario_Ponto funcionarioPonto = FuncionarioPontoController.getLastFuncionarioPontoForId(daoProvider, funcionario.funcionarioID);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		
		String strDate = format.format(date);
		
		if (funcionarioPonto == null || (funcionarioPonto.ponto.inputDate != null && funcionarioPonto.ponto.outputDate != null)) {
			PontoController.checkIn(daoProvider, funcionario);
			return "Check In feito em " + strDate;
		} else if (funcionarioPonto.ponto.inputDate != null && funcionarioPonto.ponto.outputDate == null){
			PontoController.checkOut(daoProvider, funcionarioPonto);
			return "Check Out feito em " + strDate;
		} else {
			return "Erro ao dar CheckIn/Out";
		}
	}
	
	/**
	 * Faz o Ponto de entrada do funcionário. Explicando como funciona, ele basicamente cria um novo objeto Ponto seta a data atual no campo inputDate.
	 * Logo depois ele cria um novo objeto Funcioario_Ponto onde ele vincula como o Funcioario e o Ponto criado.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionario - 
	 * 		Objeto que deve ser o funcionário que vai marcar o ponto. 
	 */
	public static void checkIn(DaoProvider daoProvider, Funcionario funcionario) {
		Date dateAtual = new Date();
		Date date = CodeSnippet.getDateCheckIn(daoProvider, funcionario);
		
		if (dateAtual.after(date)) {
			Log.i("ERRORRRRRRR", "Hora maoir");
			Configuracoes configuracoes = ConfiguracoesController.getConfiguracoes(daoProvider);
			/*if (configuracoes.emailNotification != null) {
				
			}*/
			if (configuracoes.phoneNotification != null) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
				CodeSnippet.sendSMS(configuracoes.phoneNotification, "Olá, o funcionário de ID: " + funcionario.funcionarioID + ", Nome: " + funcionario.Name + " e Usuário: " + funcionario.User + " chegou atrasado.\nData - " + format.format(new Date()));
			}
		}
		
		Ponto ponto = new Ponto();
		ponto.inputDate = dateAtual;
		daoProvider.getPontoRuntimeDao().create(ponto);
		
		Funcionario_Ponto funcPonto = new Funcionario_Ponto(funcionario, ponto);
		daoProvider.getFuncionario_PontoRuntimeDao().create(funcPonto);
	}
	
	/**
	 * Faz o Ponto de saída do funcionário. Exlpicando como funciona, ele vai receber um objeto Funcionario_Ponto do funcioário, 
	 * ele pegará o objeto Ponto que virá com a data de entrada preenchida e só colocará o horário de saída. 
	 * 
	 * @param daoProvider -
	 * 		Objeto para conecção com o DataBase.
	 * @param funcionarioPonto -
	 * 		Objeto que deve conter o último ponto do funcionário.
	 * 
	 */
	public static void checkOut(DaoProvider daoProvider, Funcionario_Ponto funcionarioPonto) {
		Ponto ponto = funcionarioPonto.ponto;
		ponto.outputDate = new Date();
		daoProvider.getPontoRuntimeDao().update(ponto);
	}
	
	/**
	 * Deleta um {@link Ponto} específico.
	 * 
	 * @param daoProvide -
	 * 		Objeto para conecção com o DataBase.
	 * @param pontoID -
	 * 		ID do {@link Ponto} que deseja deletar.
	 */
	public static void deletePonto(DaoProvider daoProvide, Integer pontoID) {
		daoProvide.getPontoRuntimeDao().deleteById(pontoID);
	}
	
}

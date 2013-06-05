package br.com.pontoeletronico.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.BaseActivity;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class ExcelFile {
	
	private static String PATH = "/Android/data/com.pontoeletronico/";  
	
	/**
	 * Abre um arquivo .xls no pr—prio celular, que Ž a lista de todos os funcion‡rios e seus respectivos
	 * pontos.
	 * 
	 * @param activity -
	 * 		{@link BaseActivity} atual.
	 */
	public static void openExcelFile(BaseActivity activity) {
		if (ExcelFile.CheckFileExcel()) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setData(Uri.fromFile(ExcelFile.getFileExcel()));
			activity.startActivity(intent);
		} else {
			activity.makeMyDearAlert(activity.getString(R.string.str_Error_FindXLS));
		}
	}
	
	/**
	 * Abre o aplicativo de email com a arquivo .xls anexado nele, deixando o resto para o 
	 * usu‡rio.
	 * 
	 * @param activity -
	 * 		{@link BaseActivity} atual.
	 */
	public static void sendExcelFile(BaseActivity activity) {
		if (ExcelFile.CheckFileExcel()) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {""});
			intent.putExtra(Intent.EXTRA_SUBJECT, "Ponto Eletronico - XLS");
			intent.putExtra(Intent.EXTRA_TEXT   , "body of email");
			intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + ExcelFile.getFileExcel()));
			
			activity.startActivity(intent);
		} else {
			activity.makeMyDearAlert(activity.getString(R.string.str_Error_FindXLS));
		}
	}
	
	/**
	 * Confere se o arquivo .xls com a lista de funcion‡rios e respectivos pontos existe.
	 * 
	 * @return
	 * 		<code>true</code> se o arquivo existir, <code>false</code> caso contr‡rio.
	 */
	public static boolean CheckFileExcel() {
    	Workbook workbook = null;
        try {
			workbook = Workbook.getWorkbook(new File(ExcelFile.getDirectory() + "/Arquivo_Ponto.xls"));
		} catch (BiffException e) {
			e.printStackTrace();
			return false;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
        
        if (workbook == null) {
			return false;
		} else {
			return true;
		}
        
    }
	
	/**
	 * Pega o {@link File} do arquivo, ou seja, o diret—rio mais o arquivo.
	 * 
	 * @return
	 * 		{@link File} do arquivo.
	 */
	public static File getFileExcel() {
		if (ExcelFile.CheckFileExcel()) {
			File file = new File(ExcelFile.getDirectory() + "/Arquivo_Ponto.xls");
			
			return file;
		} else {
			return null;
		}
	}
	
	/**
	 * Pega o {@link File} do diret—rio arquivo, ou seja, somente o diret—rio do arquivo.
	 * 
	 * @return
	 * 		{@link File} do diret—rio do arquivo.
	 */
	public static File getDirectory() {
		File dataFile = Environment.getExternalStorageDirectory();
        File file = new File(dataFile.toString() + PATH);
        file.mkdirs();
		return file;
	}
	
	/**
	 * Cria um arquivo .xls com todos os funcion‡rios e seus respectivos pontos.
	 * 
	 * @param activity -	
	 * 		{@link BaseActivity} atual.
	 */
	public static void CreateFile(BaseActivity activity) {  //this is the downloader method
		DaoProvider daoProvider = activity.getHelper();
        try {
        	//Cria o Arquivo .xls na pasta de destino
            WritableWorkbook workbook = null;
    		try {
    			workbook = Workbook.createWorkbook(new File(ExcelFile.getDirectory() + "/Arquivo_Ponto.xls"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
    		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
    		
    		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
    		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
    		
    		List<Funcionario> listFuncionarios = funcionarioDao.queryForAll();
    		
    		int linhaAtual = 2;
    		
    		Label headerNome = new Label(0, 0, "Nome do Funcion‡rio:");
    		Label headerEntrada = new Label(4, 0, "Hor‡rio Entrada:");
    		Label headerSaida = new Label(6, 0, "Hor‡rio Sa’da:");
    		
    		sheet.addCell(headerSaida);
    		sheet.addCell(headerEntrada);
    		sheet.addCell(headerNome);
    		
    		for (Funcionario funcionario : listFuncionarios) {
				//add Name
    			Label lblNome = new Label(0, linhaAtual, funcionario.Name);
    			sheet.addCell(lblNome);
    			
    			linhaAtual += 2;
    			
    			//Verificar lista de pontos
    			List<Funcionario_Ponto> listPontos = funcionarioPontoDao.query(funcionarioPontoDao.queryBuilder()
    					.where().eq("funcionarioID", funcionario.funcionarioID)
    					.prepare());
    			for (int i = 0; i < listPontos.size(); i++) {
					Ponto ponto = listPontos.get(i).ponto;
					
					Label pontoEntrada = new Label(4, linhaAtual, getDataEntrada(ponto));
					Label pontoSaida = new Label(6, linhaAtual, getDataSaida(activity, ponto));
					
					sheet.addCell(pontoSaida);
					sheet.addCell(pontoEntrada);
					
					linhaAtual++;
				}
    			
    			if (listPontos.size() == 0) {
					Label semAtividade = new Label(4, linhaAtual, "Sem atividade semanal");
					sheet.addCell(semAtividade);
				}
    			
    			linhaAtual += 2;
			}
    		
    		workbook.write();
    		workbook.close();
    		
    		} catch (Exception e) {
			// TODO: handle exception
    		}
        
	}
	
	private static String getDataEntrada(Ponto ponto) {
		return CodeSnippet.getStringFromDate(ponto.inputDate, "dd/MM/yyyy HH:mm:ss.SSS");
	}
	
	private static String getDataSaida(BaseActivity activity, Ponto ponto) {
		return ponto.outputDate == null ? activity.getString(R.string.str_Error_CreateXLS_WithoutCheckOut) : CodeSnippet.getStringFromDate(ponto.outputDate, "dd/MM/yyyy HH:mm:ss.SSS");
	}
}

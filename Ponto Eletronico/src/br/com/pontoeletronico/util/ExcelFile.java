package br.com.pontoeletronico.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Funcionario_Ponto;
import br.com.pontoeletronico.database.Ponto;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import android.os.Environment;

public class ExcelFile {
	
	private static String PATH = "/Android/com.pontoeletronico/";  
	
	public static boolean CheckFileExcel() {
    	File dataFile = Environment.getExternalStorageDirectory();
        File file = new File(dataFile.toString() + PATH);
        
        Workbook workbook = null;
        try {
			workbook = Workbook.getWorkbook(new File(file + "/Arquivo_Ponto.xls"));
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
	
	public static void CreateFile(DaoProvider daoProvider) {  //this is the downloader method
        try {
        	
        	//Cria pasta de destino
        	File dataFile = Environment.getExternalStorageDirectory();
            File file = new File(dataFile.toString() + PATH);
            file.mkdirs();
            
            //Cria o Arquivo .xls na pasta de destino
            WritableWorkbook workbook = null;
    		try {
    			workbook = Workbook.createWorkbook(new File(file + "/Arquivo_Ponto.xls"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
    		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
    		
    		RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoDao = daoProvider.getFuncionario_PontoRuntimeDao();
    		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
    		RuntimeExceptionDao<Ponto, Integer> pontoDao = daoProvider.getPontoRuntimeDao();
    		
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
					Label pontoSaida = new Label(6, linhaAtual, getDataSaida(ponto));
					
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
		Date date = ponto.inputDate;
		
		int mesInt = date.getMonth();
		
		String dia = date.getDay() < 10 ? "0" + date.getDay() : String.valueOf(date.getDay());
		int ano = date.getYear() + 1900;
		String hora = date.getHours() < 10 ? "0" + date.getHours() : String.valueOf(date.getHours());
		String min = date.getMinutes() < 10 ? "0" + date.getMinutes()  : String.valueOf(date.getMinutes());
		String sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : String.valueOf(date.getSeconds());
		
		return "" + dia + "/" + mesInt + "/" + ano + "   " + hora + ":" + min + ":" + sec;
	}
	
	private static String getDataSaida(Ponto ponto) {
		Date date = ponto.outputDate;
		
		if (date == null) {
			return "Usu‡rio n‹o tem o Ponto de sa’da!";
		}
		int mesInt = date.getMonth();
		
		String dia = date.getDay() < 10 ? "0" + date.getDay() : String.valueOf(date.getDay());
		int ano = date.getYear() + 1900;
		String hora = date.getHours() < 10 ? "0" + date.getHours() : String.valueOf(date.getHours());
		String min = date.getMinutes() < 10 ? "0" + date.getMinutes()  : String.valueOf(date.getMinutes());
		String sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : String.valueOf(date.getSeconds());
		
		return "" + dia + "/" + mesInt + "/" + ano + "   " + hora + ":" + min + ":" + sec;
	}
	
}

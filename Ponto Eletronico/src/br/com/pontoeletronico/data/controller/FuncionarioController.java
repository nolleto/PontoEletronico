package br.com.pontoeletronico.data.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.pontoeletronico.R;
import br.com.pontoeletronico.activities.BaseActivity;
import br.com.pontoeletronico.adapter.ListaFuncionariosAdapter;
import br.com.pontoeletronico.adapter.ListaVaziaAdapter;
import br.com.pontoeletronico.database.DaoProvider;
import br.com.pontoeletronico.database.Funcionario;
import br.com.pontoeletronico.database.Ponto;
import br.com.pontoeletronico.util.CodeSnippet;

public class FuncionarioController {

	
	public static ListAdapter getAdapterSearch(BaseActivity baseActivity, String search) {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		try {
			String filter = "%" + search + "%";
			Cursor cursor = baseActivity.getHelper().getReadableDatabase().rawQuery(
				"SELECT * FROM Funcionario " +
				"WHERE isGerente = 0 " +
				"AND (Name LIKE ? OR User LIKE ?) " +
				"ORDER BY Name ASC"
					, new String[]{filter, filter});
			
			while (cursor.moveToNext()) {
				Funcionario funcionario = FuncionarioController.loadFromCursor(cursor, 0);
				if (funcionario.GerenteDelegate != null)
					funcionario.GerenteDelegate = FuncionarioController.getFuncionarioForId(baseActivity.getHelper(), funcionario.GerenteDelegate.funcionarioID);
				funcionarios.add(funcionario);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (funcionarios != null && funcionarios.size() > 0) {
			return new ListaFuncionariosAdapter(baseActivity, baseActivity.getHelper(), funcionarios);
		} else {
			return new ListaVaziaAdapter(baseActivity, baseActivity.getString(R.string.listView_Empty_GerenciarFuncionarios_Search));
		}
	}
	
	private static Funcionario loadFromCursor(Cursor cursor, int startIndex) {
		Funcionario funcionario = new Funcionario();
		funcionario.funcionarioID = cursor.getInt(startIndex);
		startIndex++;
		funcionario.Name = cursor.getString(startIndex);
		startIndex++;
		funcionario.User = cursor.getString(startIndex);
		startIndex++;
		funcionario.Password = cursor.getString(startIndex);
		startIndex++;
		funcionario.Email = cursor.isNull(startIndex) ? null : cursor.getString(startIndex);
		startIndex++;
		funcionario.Adress = cursor.isNull(startIndex) ? null : cursor.getString(startIndex);
		startIndex++;
		funcionario.Phone = cursor.isNull(startIndex) ? null : cursor.getString(startIndex);
		startIndex++;
		funcionario.DateCreated = cursor.isNull(startIndex) ? null : CodeSnippet.getDateFromString(cursor.getString(startIndex), "yyy-MM-dd HH:mm:ss.SSS");
		startIndex++;
		funcionario.GerenteDelegate = cursor.isNull(startIndex) ? null : new Funcionario(cursor.getInt(startIndex));
		startIndex++;
		funcionario.isGerente = cursor.getInt(startIndex) == 1 ? true : false ;
		return funcionario;
	}

	/**
	 * Verifica no Banco de Dados se o Usu�rio j� existe.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param user -
	 * 		Nome de Usu�rio.
	 * @return
	 * 		true caso o Usu�rio n�o esteja sendo usado, false, caso contr�rio.
	 */
	public static Boolean checkIfExistUser(DaoProvider daoProvider, String user) {
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = daoProvider.getFuncionarioRuntimeDao().query(daoProvider.getFuncionarioRuntimeDao().queryBuilder()
					.where().eq("User", user)
					.prepare());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (funcionarios.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica se o Usu�rio � Gerente ou funcion�rio.
	 * 
	 * @param daoProvider - 
	 * 		Objeto para conec��o com o DataBase.
	 * @param user -
	 * 		Nome de Usu�rio.
	 * @return
	 * 		true, caso o Usu�rio seja gerente, false, caso contr�rio.
	 */
	public static Boolean checkIsGerente(DaoProvider daoProvider, String user) {
		Funcionario funcionario = daoProvider.getFuncionarioRuntimeDao().queryForEq("User", user).get(0);
		
		if (funcionario != null && funcionario.isGerente) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Verifica se o determinado Usu�rio e Senha existe, os dois devem pertencer a mesma pessoa.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param user -
	 * 		Nome de Usu�rio.
	 * @param pass -
	 * 		Senha.
	 * @return
	 * 		true caso n�o houver nenhum Usu�rio com essa Senha, false caso contr�rio.
	 */
	public static Boolean checkIfExistUserAndPassworl(DaoProvider daoProvider, String user, String pass) {
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = daoProvider.getFuncionarioRuntimeDao().query(daoProvider.getFuncionarioRuntimeDao().queryBuilder()
					.where().eq("User", user)
					.and().eq("Password", pass)
					.prepare());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (funcionarios != null && funcionarios.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica se o determinado Usu�rio e Senha existe, os dois devem pertencer a mesma pessoa.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param user -
	 * 		Nome de Usu�rio.
	 * @param pass -
	 * 		Senha.
	 * @param gerente -	
	 * 		Se precisa ser gerente.
	 * @return
	 * 		true caso n�o houver nenhum Usu�rio com essa Senha, false caso contr�rio.
	 */
	public static Boolean checkIfExistUserAndPassworl(DaoProvider daoProvider, String user, String pass, Boolean gerente) {
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = daoProvider.getFuncionarioRuntimeDao().query(daoProvider.getFuncionarioRuntimeDao().queryBuilder()
					.where().eq("User", user)
					.and().eq("Password", pass)
					.and().eq("isGerente", gerente)
					.prepare());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (funcionarios.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Cria um novo Funcion�rio no Banco de Dados. O metodo verificar� se o funcio�rio pode ser adicionado no Banco
	 * de Dados, Caso de certo, ele incluir� e retorna um {@link Boolean} true.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param funciomario -
	 * 		Funcion�rio que ser� inserido no Banco de Dados.
	 * @return
	 * 		{@link <code>true</code>} caso foi posivel adicionar o funcio�rio no Banco de Dados, {@link <code>false</code>} caso contr�rio.
	 */			
	public static Boolean createFuncionario(DaoProvider daoProvider, Funcionario funcionario) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		Boolean sucess = true;
		
		try {
			funcionarioDao.create(funcionario);
		} catch (Exception e) {
			sucess = false;
		}
		return sucess;
	}
	
	/**
	 * Atualiza o funcion�rio no Banco de Dados.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param funcionario -
	 * 		Funcion�rio de ser� atualizado no banco de Dados.
	 * @return
	 * 		{@link <code>true</code>} caso atualize o funcion�rio com sucesso, {@link <code>false</code>} caso contr�rio.
	 */
	public static Boolean updateFuncionario(DaoProvider daoProvider, Funcionario funcionario) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		
		try {
			funcionarioDao.update(funcionario);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Busca o nome de todos os funcion�rios no Banco de Dados.
	 * Existe tr�s tipo de pesquisa, voc� ir� escolher o tipo pelo par�metro OPTION.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param context -	
	 * 		O contexto da {@link Activity}.
	 * @param option -
	 * 		Op��o de procura de 1 a 3. <br/><br/>1) Todos os funcion�rios.<br/>2) Todos os funcion�rios que n�o s�o gerentes.
	 * 		<br/>3) Todos os funcion�rios que s�o gerentes.
	 * @return
	 * 		Retornar� um {@link ArrayAdapter} de {@link String} destinado paro o uso no {@link AutoCompleteTextView}.
	 */
	public static ArrayAdapter<String> getArrayAdapter(DaoProvider daoProvider, Context context, int option) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		
		List<Funcionario> listaA = null;
		
		if (option == 1) {
			listaA = funcionarioDao.queryForAll();
		} else if (option == 2) {
			try {
				listaA = funcionarioDao.query(funcionarioDao.queryBuilder()
						.orderBy("Name", true)
						.where().eq("isGerente", false)
						.prepare());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				listaA = funcionarioDao.query(funcionarioDao.queryBuilder()
						.orderBy("Name", true)
						.where().eq("isGerente", true)
						.prepare());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        ArrayList<String> listaB = new ArrayList<String>();
        
        for (Funcionario funcionario : listaA) {
			listaB.add(funcionario.User);
		}
        
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, listaB);
	}
	
	/**
	 * Busca o nome de todos os funcion�rios no Banco de Dados que n�o sejam gerente.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param activity - 
	 * 		{@link Activity} atual.
	 * @return
	 * 		Retornar� um {@link ListAdapter} destinado paro o uso no {@link ListView}.
	 */
	public static ListAdapter getAdapter(DaoProvider daoProvider, Activity activity) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		List<Funcionario> list = null;
		
		try {
			list = funcionarioDao.query(funcionarioDao.queryBuilder()
					.orderBy("Name", true)
					.where().eq("isGerente", false)
					.prepare());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null || list.size() == 0) {
			return new ListaVaziaAdapter(activity, activity.getString(R.string.listView_Empty_GerenciarFuncionarios));
		} else {
			return new ListaFuncionariosAdapter(activity, daoProvider, list); 
		}
		
	}
	
	/**
	 * Procura por um funcion�rio de determinado id no Banco de Dados.
	 * 
	 * @param daoProvider -
	 * 		Objeto para conec��o com o DataBase.
	 * @param id -
	 * 		ID do funcion�rio.
	 * @return
	 * 		Retorna o um {@link Funcionario}.
	 */
	public static Funcionario getFuncionarioForId(DaoProvider daoProvider ,int id) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		return funcionarioDao.queryForId(id);
	}
	
	/**
	 * 
	 * @param daoProvider
	 * @param user
	 * @param password
	 * @return
	 */
	public static Funcionario getFuncionarioWithUserAndPassword(DaoProvider daoProvider,String user, String password) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		List<Funcionario> funcionarios = null;
		
		try {
			funcionarios = funcionarioDao.query(funcionarioDao.queryBuilder()
					.where().eq("User", user)
					.and().eq("Password", password)
					.prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (funcionarios != null && funcionarios.size() > 0) {
			return funcionarios.get(0);
		} else {
			return null;
		}	
	}
	
	/**
	 * Pega no Banco de Dados o funcion�rio Admin.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conec��o com o DataBase.
	 * @return
	 * 		{@link Funcionario} Admin.
	 */
	public static Funcionario getFuncionarioAdmin(DaoProvider daoProvider) {
		List<Funcionario> funcionarios = daoProvider.getFuncionarioRuntimeDao().queryForEq("User", "admin");
		if (funcionarios != null && funcionarios.size() > 0) {
			return funcionarios.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Seta o funcion�rio admin no Banco de Dados.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conec��o com o DataBase.
	 */
	public static void setFuncionarioAdmin(DaoProvider daoProvider) {
		Funcionario funcionario = FuncionarioController.getFuncionarioAdmin(daoProvider);
		if (funcionario == null) {
			funcionario = new Funcionario("admin", "1234", "Admin", true);
			funcionario.DateCreated = new Date();
			daoProvider.getFuncionarioRuntimeDao().createIfNotExists(funcionario);
		}
	}
	
	/**
	 * Deleta uma {@link Funcionario} do Banco de Dados, e com isso, seua lista de {@link Ponto}
	 * tamb�m.
	 * 
	 * @param daoProvider -	
	 * 		Objeto para conec��o com DataBase.
	 * @param funcionario -
	 * 		ID do funcion�rio que vai ser exclu�do.
	 */
	public static void deleteFuncionario(DaoProvider daoProvider, Integer funcionarioId) {
		RuntimeExceptionDao<Funcionario, Integer> funcionarioDao = daoProvider.getFuncionarioRuntimeDao();
		FuncionarioPontoController.deleteFuncionarioPonto(daoProvider, funcionarioId);
		funcionarioDao.deleteById(funcionarioId);
	}
	
}

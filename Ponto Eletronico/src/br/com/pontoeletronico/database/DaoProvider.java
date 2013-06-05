package br.com.pontoeletronico.database;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import br.com.pontoeletronico.R;

public class DaoProvider extends OrmLiteSqliteOpenHelper {

	// Nome do arquivo do banco de dados
	private static final String DATABASE_NAME = "PontoEletronico.db";
	// Vers�o do banco de dados. Incrementar sempre que altera��es forem feitas no banco.
	// Modificado dia 20/07/2012
	private static final int DATABASE_VERSION = 1;
	
	public Context context;
	
	public DaoProvider(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
		this.context = context;
	}
	
	public DaoProvider(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			android.util.Log.i(DaoProvider.class.getName(), "onCreate");
			TableUtils.createTableIfNotExists(connectionSource, FuncionarioConfiguracoes.class);
			TableUtils.createTableIfNotExists(connectionSource, Funcionario.class);
			TableUtils.createTableIfNotExists(connectionSource, Funcionario_Ponto.class);
			TableUtils.createTableIfNotExists(connectionSource, Ponto.class);
			TableUtils.createTableIfNotExists(connectionSource, Configuracoes.class);
		} catch (SQLException e) {
			android.util.Log.e(DaoProvider.class.getName(), "Erro ao criar banco de dados", e);
			throw new RuntimeException(e);
		}
	}
	
	/*public void dumpDatabaseToRoot() {
		
		File sd = Environment.getExternalStoragePublicDirectory("dump");
        File data = Environment.getDataDirectory();

		String currentDBPath = "/data/com.sanemob/databases/saneMob.db";
        String backupDBPath = "saneMob.db";
        
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        
        try {
	        if (currentDB.exists()) {
	            FileChannel src = new FileInputStream(currentDB).getChannel();
	            FileChannel dst = new FileOutputStream(backupDB).getChannel();
	            src.transferTo(0, src.size(), dst);
	            src.close();
	            dst.close();
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}*/
	
	public void clearDatabase(final ConnectionSource connectionSource) {
		try {
			TransactionManager.callInTransaction(connectionSource, new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					android.util.Log.i(DaoProvider.class.getName(), "cleanDatabase");
					TableUtils.clearTable(connectionSource, FuncionarioConfiguracoes.class);
					TableUtils.clearTable(connectionSource, Funcionario.class);
					TableUtils.clearTable(connectionSource, Ponto.class);
					TableUtils.clearTable(connectionSource, Funcionario_Ponto.class);
					TableUtils.clearTable(connectionSource, Configuracoes.class);
					return null;
				}
			});
		} catch (java.sql.SQLException e) {
			android.util.Log.e(DaoProvider.class.getName(), "Erro ao limpar banco de dados", e);
			e.printStackTrace();
		}
	}
	
	// TODO: onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// Obviamente vai ser maior... Mas vai que
		/*if(newVersion > oldVersion) {
			if(oldVersion < 2) {
				try {
					TableUtils.createTableIfNotExists(connectionSource, RegistroModificacao.class);
					TableUtils.createTableIfNotExists(connectionSource, RegistroConflito.class);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				RuntimeExceptionDao<FichaPre, String> dao = getFichaPreRuntimeDao();
				dao.executeRaw("ALTER TABLE 'FichaPre' ADD COLUMN DataPreenchimento DATETIME NULL", new String[]{});
				dao.executeRaw("ALTER TABLE 'FichaPre' ADD COLUMN Consentimento INTEGER NULL", new String[]{});
				dao.executeRaw("ALTER TABLE 'FichaPre' ADD COLUMN AnestesiologistaResponsavelId INTEGER NULL", new String[]{});
				
				android.util.Log.d("DatabaseUpdate", String.format("DB upgrade oldVersion: %d newVersion: %d", 1, 2));
			}
			// Logs
			android.util.Log.d("DatabaseUpdate", String.format("DB upgrade from version %d to version %d", oldVersion, newVersion));
			Log.logLocal(context, this, LogNivelEnum.INFO, LogGrupoEnum.GERAL, "DatabaseUpgrade", String.format("DB upgrade from version %d to version %d", oldVersion, newVersion));
			database.setVersion(DATABASE_VERSION);
		}*/
	}
	
	private Dao<Funcionario, Integer> funcionarioDao;
	private Dao<Ponto, Integer> pontoDao;
	private Dao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	private Dao<Configuracoes, Integer> configuracoesDao;
	private Dao<FuncionarioConfiguracoes, Integer> funcionarioConfiguracoesDao;
	
	private RuntimeExceptionDao<Funcionario, Integer> funcionarioRuntimeDao;
	private RuntimeExceptionDao<Ponto, Integer> pontoRuntimeDao;
	private RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoRuntimeDao;
	private RuntimeExceptionDao<Configuracoes, Integer> configuracoesRuntimeDao;
	private RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> funcionarioConfiguracoesRuntimeDao;

	public Dao<Funcionario, Integer> getFuncionarioDao() throws SQLException {
		if (funcionarioDao == null) {
			funcionarioDao = getDao(Funcionario.class);
		}
		return funcionarioDao;
	}

	public Dao<Ponto, Integer> getPontoDao() throws SQLException {
		if (pontoDao == null) {
			pontoDao = getDao(Ponto.class);
		}
		return pontoDao;
	}
	
	public Dao<Configuracoes, Integer> getConfiguracoesDao() throws SQLException {
		if (configuracoesDao == null) {
			configuracoesDao = getDao(Configuracoes.class);
		}
		return configuracoesDao;
	}
	
	public Dao<Funcionario_Ponto, Integer> getFuncionario_PontoDao() throws SQLException {
		if (funcionarioPontoDao == null) {
			funcionarioPontoDao = getDao(Funcionario_Ponto.class);
		}
		return funcionarioPontoDao;
	}
	
	public Dao<FuncionarioConfiguracoes, Integer> getFuncionario_ConfiguracoesDao() throws SQLException {
		if (funcionarioConfiguracoesDao == null) {
			funcionarioConfiguracoesDao = getDao(FuncionarioConfiguracoes.class);
		}
		return funcionarioConfiguracoesDao;
	}
	
	public RuntimeExceptionDao<Funcionario, Integer> getFuncionarioRuntimeDao() {
		if (funcionarioRuntimeDao == null) {
			funcionarioRuntimeDao = getRuntimeExceptionDao(Funcionario.class);
		}
		return funcionarioRuntimeDao;
	}

	public RuntimeExceptionDao<Ponto, Integer> getPontoRuntimeDao() {
		if (pontoRuntimeDao == null) {
			pontoRuntimeDao = getRuntimeExceptionDao(Ponto.class);
		}
		return pontoRuntimeDao;
	}
	
	public RuntimeExceptionDao<Funcionario_Ponto, Integer> getFuncionario_PontoRuntimeDao() {
		if (funcionarioPontoRuntimeDao == null) {
			funcionarioPontoRuntimeDao = getRuntimeExceptionDao(Funcionario_Ponto.class);
		}
		return funcionarioPontoRuntimeDao;
	}
	
	public RuntimeExceptionDao<Configuracoes, Integer> getConfiguracoesRuntimeDao() {
		if (configuracoesRuntimeDao == null) {
			configuracoesRuntimeDao = getRuntimeExceptionDao(Configuracoes.class);
		}
		return configuracoesRuntimeDao;
	}
	
	public RuntimeExceptionDao<FuncionarioConfiguracoes, Integer> getFuncionario_ConfiguracoesRuntimeDao() {
		if (funcionarioConfiguracoesRuntimeDao == null) {
			funcionarioConfiguracoesRuntimeDao = getRuntimeExceptionDao(FuncionarioConfiguracoes.class);
		}
		return funcionarioConfiguracoesRuntimeDao;
	}
	
	@Override
	public void close() {
		super.close();
		funcionarioDao = null;
		pontoDao = null;
		funcionarioPontoDao = null;
		configuracoesDao = null;
		funcionarioConfiguracoesDao = null;
		
		funcionarioRuntimeDao = null;
		pontoRuntimeDao = null;
		funcionarioPontoRuntimeDao = null;
		configuracoesRuntimeDao = null;
		funcionarioConfiguracoesRuntimeDao = null;
	}
	
}
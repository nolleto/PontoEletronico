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
import br.com.digitaldesk.pontoeletronico.R;

public class DaoProvider extends OrmLiteSqliteOpenHelper {
	
	// Nome do arquivo do banco de dados
	private static final String DATABASE_NAME = "PontoEletronico.db";
	// Vers�o do banco de dados. Incrementar sempre que altera��es forem feitas no banco.
	// Modificado dia 20/07/2012
	private static final int DATABASE_VERSION = 1;
	
	private Context context;
	
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
			TableUtils.createTableIfNotExists(connectionSource, Funcionario.class);
			TableUtils.createTableIfNotExists(connectionSource, Gerente.class);
			TableUtils.createTableIfNotExists(connectionSource, Funcionario_Ponto.class);
			TableUtils.createTableIfNotExists(connectionSource, Ponto.class);
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
					TableUtils.clearTable(connectionSource, Funcionario.class);
					TableUtils.clearTable(connectionSource, Gerente.class);
					TableUtils.clearTable(connectionSource, Ponto.class);
					TableUtils.clearTable(connectionSource, Funcionario_Ponto.class);
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
	private Dao<Gerente, Integer> gerenteDao;
	private Dao<Ponto, Integer> pontoDao;
	private Dao<Funcionario_Ponto, Integer> funcionarioPontoDao;
	
	private RuntimeExceptionDao<Funcionario, Integer> funcionarioRuntimeDao;
	private RuntimeExceptionDao<Gerente, Integer> gerenteRuntimeDao;
	private RuntimeExceptionDao<Ponto, Integer> pontoRuntimeDao;
	private RuntimeExceptionDao<Funcionario_Ponto, Integer> funcionarioPontoRuntimeDao;

	public Dao<Funcionario, Integer> getFuncionarioDao() throws SQLException {
		if (funcionarioDao == null) {
			funcionarioDao = getDao(Funcionario.class);
		}
		return funcionarioDao;
	}

	public Dao<Gerente, Integer> getGerenteDao() throws SQLException {
		if (gerenteDao == null) {
			gerenteDao = getDao(Gerente.class);
		}
		return gerenteDao;
	}
	
	public Dao<Ponto, Integer> getPontoDao() throws SQLException {
		if (pontoDao == null) {
			pontoDao = getDao(Ponto.class);
		}
		return pontoDao;
	}
	
	public Dao<Funcionario_Ponto, Integer> getFuncionario_PontoDao() throws SQLException {
		if (funcionarioPontoDao == null) {
			funcionarioPontoDao = getDao(Funcionario_Ponto.class);
		}
		return funcionarioPontoDao;
	}
	
	public RuntimeExceptionDao<Funcionario, Integer> getFuncionarioRuntimeDao() {
		if (funcionarioRuntimeDao == null) {
			funcionarioRuntimeDao = getRuntimeExceptionDao(Funcionario.class);
		}
		return funcionarioRuntimeDao;
	}
	
	public RuntimeExceptionDao<Gerente, Integer> getGerenteRuntimeDao() {
		if (gerenteRuntimeDao == null) {
			gerenteRuntimeDao = getRuntimeExceptionDao(Gerente.class);
		}
		return gerenteRuntimeDao;
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
	
	@Override
	public void close() {
		super.close();
		funcionarioDao = null;
		gerenteDao = null;
		pontoDao = null;
		funcionarioPontoDao = null;
		
		funcionarioRuntimeDao = null;
		gerenteRuntimeDao = null;
		pontoRuntimeDao = null;
		funcionarioPontoRuntimeDao = null;
	}
	
}
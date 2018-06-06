package com.via.clms.server.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.server.services.util.DatabaseSetup;

/**
 * Local service that can be used to access the database
 */
public class DatabaseService implements Service {
	
	/** * */
	private Connection mConnection;
	
	/** * */
	private String mClass = "org.postgresql.Driver";
	
	/** * */
	private String mDriver = "postgresql";
	
	/** * */
	private String mDbUser;
	
	/** * */
	private String mDbPasswd;
	
	/** * */
	private String mDbRelation = "clms";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onConfigure() {
		System.out.println("Configuring database service");

		mDbUser = Utils.readInput("Username: ");
		mDbPasswd = Utils.readInput("Password: ");
		
		try {
			// Load Driver
			Class.forName(mClass);
			
			// Establish connection
			mConnection = DriverManager.getConnection("jdbc:" + mDriver + "://localhost/" + mDbRelation +
					"?useSSL=false" +
					"&characterEncoding=utf8" +
					"&user=" + mDbUser + "" +
					"&password=" + mDbPasswd);
			
			DatabaseSetup.configureTables(this, mDbRelation);
			
			return true;
			
		} catch (Exception e) {
			try {
				/* Establish connection to reate a database
				 * We MUST define a database before we can connect, 
				 * so a hack is to use the default 'postgres' database
				 */
				Connection conn = DriverManager.getConnection("jdbc:" + mDriver + "://localhost/postgres" +
						"?useSSL=false" +
						"&characterEncoding=utf8" +
						"&user=" + mDbUser + "" +
						"&password=" + mDbPasswd);
				
				Statement stmt = conn.createStatement();
				stmt.execute("CREATE DATABASE " + mDbRelation);
				conn.close();
				
				/*
				 * Postgres does not support changing database via SQL. 
				 * So we must disconnect and re-connect to do so.
				 */
				mConnection = DriverManager.getConnection("jdbc:" + mDriver + "://localhost/" + mDbRelation +
						"?useSSL=false" +
						"&characterEncoding=utf8" +
						"&user=" + mDbUser + "" +
						"&password=" + mDbPasswd);
				
				DatabaseSetup.configureTables(this, mDbRelation);
				
				return true;
			
			} catch (Exception ei) {
				Log.error(e);
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onShutdown() {
		System.out.println("Shutting down database service");
		
		try {
			if (mConnection != null) {
				mConnection.close();
				mConnection = null;
			}
			
		} catch (Exception e) {
			Log.error(e);
		}
	}
	
	/**
	 * Make a query on the database and return the result
	 * 
	 * @param String sql
	 * 		The SQL to run on the database
	 * 
	 * @param Object... vars
	 * 		Multiple variables that is added via SQL statement
	 */
	public ResultSet query(String sql, Object... vars) {
		try {
			PreparedStatement statement = mConnection.prepareStatement(sql);
			
			for (int i=0,x=1; i < vars.length; i++,x++) {
				if (vars[i] == null) {
					statement.setNull(x, Types.NULL);
					
				} else if (vars[i] instanceof String) {
					statement.setString(x, (String) vars[i]);
					
				} else if (vars[i] instanceof Integer) {
					statement.setInt(x, (Integer) vars[i]);
					
				} else if (vars[i] instanceof Long) {
					statement.setLong(x, (Long) vars[i]);
					
				} else if (vars[i] instanceof Double) {
					statement.setDouble(x, (Double) vars[i]);
					
				} else if (vars[i] instanceof Float) {
					statement.setFloat(x, (Float) vars[i]);
					
				} else if (vars[i] instanceof Date) {
					statement.setDate(x, (Date) vars[i]);
					
				} else if (vars[i] instanceof Time) {
					statement.setTime(x, (Time) vars[i]);
					
				} else {
					throw new RuntimeException("Invalid variable type");
				}
			}
			
			return statement.executeQuery();
			
		} catch (Exception e) {
			Log.error(e);
		}
		
		return null;
	}
	
	/**
	 * Execute a query and return the number of affected rows
	 * 
	 * @param String sql
	 * 		The SQL to run on the database
	 * 
	 * @param Object... vars
	 * 		Multiple variables that is added via SQL statement
	 */
	public int execute(String sql, Object... vars) {
		try {
			PreparedStatement statement = mConnection.prepareStatement(sql);
			
			for (int i=0,x=1; i < vars.length; i++,x++) {
				if (vars[i] == null) {
					statement.setNull(x, Types.NULL);
					
				} else if (vars[i] instanceof String) {
					statement.setString(x, (String) vars[i]);
					
				} else if (vars[i] instanceof Integer) {
					statement.setInt(x, (Integer) vars[i]);
					
				} else if (vars[i] instanceof Long) {
					statement.setLong(x, (Long) vars[i]);
					
				} else if (vars[i] instanceof Double) {
					statement.setDouble(x, (Double) vars[i]);
					
				} else if (vars[i] instanceof Float) {
					statement.setFloat(x, (Float) vars[i]);
					
				} else if (vars[i] instanceof Date) {
					statement.setDate(x, (Date) vars[i]);
					
				} else if (vars[i] instanceof Time) {
					statement.setTime(x, (Time) vars[i]);
					
				} else {
					throw new RuntimeException("Invalid variable type");
				}
			}
			
			return statement.executeUpdate();
			
		} catch (SQLException e) {
			Log.error(e);
			if (e.getSQLState() != null) {
			throw new RuntimeException("Statement insertion error");
			}
		}

		return -1;
	}
		public int executeReturnKey(String sql, Object... vars) {
		try {
			PreparedStatement statement = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			for (int i=0,x=1; i < vars.length; i++,x++) {
				if (vars[i] == null) {
					statement.setNull(x, Types.NULL);

				} else if (vars[i] instanceof String) {
					statement.setString(x, (String) vars[i]);

				} else if (vars[i] instanceof Integer) {
					statement.setInt(x, (Integer) vars[i]);

				} else if (vars[i] instanceof Long) {
					statement.setLong(x, (Long) vars[i]);

				} else if (vars[i] instanceof Double) {
					statement.setDouble(x, (Double) vars[i]);

				} else if (vars[i] instanceof Float) {
					statement.setFloat(x, (Float) vars[i]);

				} else if (vars[i] instanceof Date) {
					statement.setDate(x, (Date) vars[i]);

				} else if (vars[i] instanceof Time) {
					statement.setTime(x, (Time) vars[i]);

				} else {
					throw new RuntimeException("Invalid variable type");
				}
			}

			int r = statement.executeUpdate();
			if(r == 0) {
				return 0;
			}
			ResultSet rs = statement.getGeneratedKeys();
			if(!rs.next()) {
				return 0;
			}
			return rs.getInt(1);

		} catch (SQLException e) {
			Log.error(e);
			if (e.getSQLState() != null) {
				throw new RuntimeException("Statement insertion error");
			}
		}

		return -1;
	}
}

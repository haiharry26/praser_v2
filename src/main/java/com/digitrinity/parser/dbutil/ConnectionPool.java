package com.digitrinity.parser.dbutil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool {

	// JDBC Driver Name & Database URL
	 String JDBC_DRIVER;  
	 String JDBC_DB_URL;

	// JDBC Database Credentials
	 String JDBC_USER;
	 String JDBC_PASS;

	private static GenericObjectPool gPool = null;

	@SuppressWarnings("unused")
	public DataSource setUpPool() throws Exception {
		
		loadProperties();
		
		Class.forName(JDBC_DRIVER);

		// Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!

		if(gPool==null) {

			gPool = new GenericObjectPool();
			gPool.setMaxActive(1);
			// Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
			ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);

			// Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
			PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
			return new PoolingDataSource(gPool);
		}

		else {
			return new PoolingDataSource(gPool);
		}
	}

	private void loadProperties() throws IOException {

		InputStream input = new FileInputStream("parser/parser.properties");
		Properties prop = new Properties();
		// load a properties file
		prop.load(input);
		JDBC_DRIVER = prop.getProperty("db.drive");
		JDBC_DB_URL = prop.getProperty("db.url");
		JDBC_USER = prop.getProperty("db.username");
		JDBC_PASS = prop.getProperty("db.password");
	}

	public GenericObjectPool getConnectionPool() {
		return gPool;
	}

	// This Method Is Used To Print The Connection Pool Status
	public void printDbStatus() {
		System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
	}

}

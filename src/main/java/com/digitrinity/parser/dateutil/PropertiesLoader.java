/**
 * 
 */
package com.digitrinity.parser.dateutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Bhabaranjan
 *
 */
public class PropertiesLoader {
	public static Properties loadProperties;
	private static PropertiesLoader configLoader;
	private static Logger logger = LogManager.getLogger(PropertiesLoader.class.getName());
	static{
		loadProperties=new Properties();
		configLoader=new PropertiesLoader();
		String FileName=System.getProperty("user.dir")+ConfigLoader.PLUGIN_PATH_SEPARATOR+"default.conf";
		logger.debug("FileName "+FileName);		
		loadProperties=configLoader.getPropertiesFile(FileName);
	}
	
	/**
	 * To Load the Configuration Properties File
	 * @param config	The Properties File Name
	 * @return properties Object
	 * @throws IOException
	 */
	    private Properties getPropertiesFile(String configFileName) {
	        InputStream propsStream = null;
	        Properties props = new Properties();
	        try {
	            // first try classpath
	            propsStream = getClass().getClassLoader().getResourceAsStream(configFileName);
	            if (propsStream != null) {
	                props.load(propsStream);
	            } else {
	                throw new IOException();
	            }
	        } catch (IOException ed) {
	            // next try file location
	            File propsFile = new File(configFileName);
	            if (propsFile != null) {
	                FileInputStream fis = null;
	                try {
	                    fis = new FileInputStream(propsFile);
	                    if (fis != null) {
	                        props.load(fis);
	                    }
	                } catch (FileNotFoundException e2) {
	                    // do nothing; we will see empty props below
	                } catch (IOException e) {
	                	
	                } finally {
	                    if (fis != null) {
	                        try {
								fis.close();
							} catch (IOException e) {
								
							}
	                    }
	                }
	            }
	        } finally {
	            if (propsStream != null) {
	                try {
						propsStream.close();
					} catch (IOException e) {
						
					}
	            }
	        }

	        // check for empty properties
	        if (props.isEmpty()) {

	        }
	        return props;
	    }  	
	    public static final String LOG_FILE_NAME=loadProperties.getProperty("log.file");
	    public static final String DB_PROVIDER=loadProperties.getProperty("db.sql.provider");
	   	public static final String DB_NAME=loadProperties.getProperty("db.sql.dbname");
	    public static final String DB_HOST=loadProperties.getProperty("db.sql.host");
	    public static final String DB_PORT=loadProperties.getProperty("db.sql.port");
	    public static final String DB_SQL_DRIVER=loadProperties.getProperty("db.sql.driver");
	    public static final String DB_SQL_URL=loadProperties.getProperty("db.sql.url");
	    public static final String DB_SQL_USER=loadProperties.getProperty("db.sql.user");
	    public static final String DB_SQL_PASSWORD=loadProperties.getProperty("db.sql.password");
	     
	    

}

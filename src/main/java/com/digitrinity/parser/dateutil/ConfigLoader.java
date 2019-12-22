/**
 * 
 */
package com.digitrinity.parser.dateutil;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * @author DigitalTrinity
 *
 */
public class ConfigLoader {
	
	public static Properties loadProperties;
	private static ConfigLoader configLoader;
	public static Properties commandProperties; // to hold the read commands
	public static Properties parameterProperties;
	public static PrintStream PluginLog;
	static {
		
		loadProperties=new Properties();
		commandProperties=new Properties();
		parameterProperties=new Properties();
		configLoader=new ConfigLoader();

		loadProperties=configLoader.getPropertiesFile("application.properties");
		PluginLog=System.out;
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
	public static final int    QUEUE_ID=Integer.parseInt(loadProperties.getProperty("queue.id").trim());
	public static final int    QUEUE_SIZE=Integer.parseInt(loadProperties.getProperty("queue.size").trim());

	public static final String DB_NAME=loadProperties.getProperty("db.sql.dbname").trim();
	public static final String DB_HOST=loadProperties.getProperty("db.sql.host").trim();
	public static final String DB_PORT=loadProperties.getProperty("db.sql.port").trim();
	public static final String DB_SQL_DRIVER=loadProperties.getProperty("db.sql.driver").trim();
	public static final String DB_SQL_URL=loadProperties.getProperty("db.sql.url").trim();
	public static final String DB_SQL_USER=loadProperties.getProperty("db.sql.user").trim();

	public static final boolean LOG_DEBUG=(new Boolean(loadProperties.getProperty("log.debug").trim())).booleanValue();
	public static final boolean LOG_DEBUG_GPRS=(new Boolean(loadProperties.getProperty("log.debug.gprs").trim())).booleanValue();
	public static final boolean LOG_DEBUG_QUEUE=(new Boolean(loadProperties.getProperty("log.debug.queue").trim())).booleanValue();

	public static final String  LOG_PATH=loadProperties.getProperty("log.path").trim();
	public static final String  LOG_PATH_GPRS=loadProperties.getProperty("log.path.gprs").trim();
	public static final String  LOG_PATH_QUEUE=loadProperties.getProperty("log.path.queue").trim();
	public static final boolean DATA_CHECKSUM=(new Boolean(loadProperties.getProperty("data.checkSum").trim())).booleanValue();
	public static final int     LOG_FILE_CREATION_TIME=Integer.parseInt(loadProperties.getProperty("log.file.creation.time").trim());

	public static final String  PLUGIN_TYPE=loadProperties.getProperty("plugin.type").trim();
	public static final int     DEVICE_PORT=Integer.parseInt(loadProperties.getProperty("device.port").trim());
	public static final String  DEVICE_TYPE=loadProperties.getProperty("device.type").trim();

	public static final long    COMMAND_READ_WAIT_TIME=Long.parseLong(loadProperties.getProperty("command.read.wait.time").trim());

	public static final String  GPRS_COMMAND_SUCESS=loadProperties.getProperty("gprs.command.sucess").trim();
	public static final String  GPRS_COMMAND_FAILURE=loadProperties.getProperty("gprs.command.failure").trim();

	// OS Parameter for path separator
	public static final String PLUGIN_PATH_SEPARATOR =  loadProperties.getProperty("plugin.os.pathseparator").trim();
	public static final String PLUGIN_AUTO_COMMAND=loadProperties.getProperty("plugin.auto.command").trim();
	
	public static final String PLUGIN_Max_PacketLength=loadProperties.getProperty("plugin.max.packetlength").trim();
	public static final String PLUGIN_TIMEOUT_TCP_IDLE=loadProperties.getProperty("plugin.timeoutTcpIdeal").trim();
	public static final String TIMEOUT_TCP_PACKET=loadProperties.getProperty("plugin.tcpPacket").trim();
	public static final String TIMEOUT_TCP_SESSION=loadProperties.getProperty("plugin.tcSession").trim();
	
	public static final int     DOV_AirCON1  = (Integer.parseInt(loadProperties.getProperty("dov.aircon1.pin").trim())-1);
	public static final int     DOV_AirCON2  = (Integer.parseInt(loadProperties.getProperty("dov.aircon2.pin").trim())-1);
	public static final int     DOV_DOOROPEN = (Integer.parseInt(loadProperties.getProperty("dov.dooropen.pin").trim())-1);
	public static final int     DOV_DG       = (Integer.parseInt(loadProperties.getProperty("dov.dg.pin").trim())-1);
	public static final boolean MQTTACTIVE   = (new Boolean(loadProperties.getProperty("mqtt.active").trim())).booleanValue();
	public static final String  MQTTHOST     = loadProperties.getProperty("mqtt.host.server").trim();
	public static final String  MQTTTOPIC     = loadProperties.getProperty("mqtt.topic").trim();
	public static final String  MQTTClientID     = loadProperties.getProperty("mqtt.client.id").trim();

	
	public static final String TRACK_SERVER_PACKAGE = "com.DigitalTrinity.servers";

	public static ArrayList getDeviceStartWithList() {
		
		ArrayList deviceStartWithListArr=new ArrayList();
		String deviceStartWithList=loadProperties.getProperty("plugin.device.startingCharcters").trim();
		StringTokenizer stToken=new StringTokenizer(deviceStartWithList,",");
		while(stToken.hasMoreTokens()) {
			
			deviceStartWithListArr.add(stToken.nextToken());

		}
		if(deviceStartWithListArr.size()==0) {
			
			deviceStartWithListArr.add("Error");
		}
		return deviceStartWithListArr;
	}
}

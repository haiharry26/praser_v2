package com.digitrinity.parser.parserthread;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.digitrinity.model.MessageData;
import com.digitrinity.parser.dateutil.DateTime;
import com.digitrinity.parser.dbutil.ConnectionPool;
import com.digitrinity.parser.dbutil.DataBaseOperation;

public class ParserThread implements Runnable {

	static volatile boolean exit = false; 
	private static boolean _deviceFlag = false;
	@SuppressWarnings("rawtypes")
	private HashMap _deviceMap=new HashMap();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static HashMap <String,Object> _recordProcessMap = new HashMap();
	private static Logger logger = LogManager.getLogger(ParserThread.class.getName());

	public void run(){

		//while(true) {
		// will not run infinitely 
                  while (!exit) { 

			logger.debug("Started Processing Data...");

			try {

				int threadCount = Thread.activeCount();
				if (threadCount > 50) {

					logger.info("Too many active threads in other records. Current count = [" + threadCount + "]. Going to sleep. Will try again after some time.");
					logger.info("Please check CPU usage. Processing slowing down and creating many threads.");
					Thread.sleep(5000);
					continue;
				}

				logger.debug("Before fetchSites()-------------------");
				fetchSites();
				logger.debug(" After fetchSites()-------------------");

				if(_deviceFlag) {

					logger.debug("Before fetchRecords()-------------------");
					fetchRecords();
					logger.debug("After fetchRecords()-------------------");
					logger.trace("Total number of active Threads before sleep = [" + Thread.activeCount() + "]");
					Thread.sleep(4000);
					logger.trace("Total number of active Threads after sleep = [" + Thread.activeCount() + "]");
				}
				else {
					Thread.sleep(1000);
				}

			} catch (SQLException e1) {
				logger.error ("Failed to process", e1);
				e1.printStackTrace();
			} catch (Exception e) {
				logger.error ("Failed to process", e);
				e.printStackTrace();
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error ("Failed to process", e);
				e.printStackTrace();
			}
		}
		// so that we can stop the threads 
                exit = true; 
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fetchSites() throws Exception {

		logger.debug("Inside fetchSites...");
		_deviceMap=null;
		_deviceMap=new HashMap();

		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		ConnectionPool jdbcObj = new ConnectionPool();
		DataSource dataSource = jdbcObj.setUpPool();
		int _mapKey=0;

		InputStream input = new FileInputStream("parser/parser.properties");
		Properties prop = new Properties();
		// load a properties file
		prop.load(input);
		int port = Integer.parseInt(prop.getProperty("parser.port"));

		try {   

			jdbcObj.printDbStatus();

			// Performing Database Operation!
			logger.debug("\n=====Making A New Connection Object For Db Transaction=====\n");
			connObj = dataSource.getConnection();
			jdbcObj.printDbStatus(); 
			pstmtObj = connObj.prepareStatement("Select distinct(smSiteCode) from incomingdata  where Msgstatus=10 and iPort ="+port+" LIMIT 9");
			ResultSet rsObj = pstmtObj.executeQuery();

			while(rsObj.next()){

				String smSiteCode = rsObj.getString("smSiteCode");
				this._deviceMap.put(_mapKey + "", smSiteCode + "");
				_mapKey++;
				_deviceFlag = true;
			}

			logger.debug("Device Map After fetching sites------------------"+_deviceMap);
			rsObj.close();
			connObj.close();
			pstmtObj.close();

			logger.debug("\n=====Releasing Connection Object To Pool=====\n");            
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
					pstmtObj = null;
				}
				input.close();
			} catch(Exception sqlException) {
				logger.error ("Failed to process", sqlException);
				sqlException.printStackTrace();
			}
		}
		jdbcObj.printDbStatus();
	}

	@SuppressWarnings("unchecked")
	private void fetchRecords() throws Exception {

		logger.debug("Inside FetchRecords-------------------");
		Set<String> set=null;
		Iterator<String> itt=null;
		set = this._deviceMap.keySet();
		itt = set.iterator();
		Object key = null;
		while(itt.hasNext()) {

			key = itt.next();
			String smSiteCode = this._deviceMap.get(key).toString();
			try {

				synchronized(ParserThread._recordProcessMap) {

					if(!(ParserThread._recordProcessMap.containsKey(smSiteCode+""))) {

						ParserThread._recordProcessMap.put(smSiteCode+"", DateTime.getCurrentTimeMillis()+"");
						Thread thread = null;
						Parser parser=null;
						parser=new Parser(smSiteCode);
						logger.trace("Creating a new thread for " + smSiteCode);
						thread=	new Thread(parser);
						thread.start();
					}	
				}
			} catch (ConcurrentModificationException cmex) {

				logger.error("At fetchRecords - Concurrent Modificaton occured for Device ----------" + smSiteCode, cmex);		
				cmex.printStackTrace();

			} finally {
				ParserThread._deviceFlag = false;
			}
		}
		ParserThread._deviceFlag = false;
	}  
}

class Parser extends ParserThread implements Runnable {

	String smSiteCode;
	static Logger logger = LogManager.getLogger(Parser.class.getName());

	Parser(String dvUniqueID){
		this.smSiteCode = dvUniqueID;
	}

	static Long totalTime = new Long(0L);

	/**
	 * 
	 */
	public void run(){
		try {

			long start = DateTime.getCurrentTimeMillis();
			assembelRecordPerSite(smSiteCode);
			long end = DateTime.getCurrentTimeMillis();			
			logger.debug("Total time to parse: " + (end-start));
			logger.info("Completed processing for " + smSiteCode);

		} catch (Throwable e) {
			logger.error ("Failed to process", e);
			e.printStackTrace();
		}
	}

	protected void assembelRecordPerSite(String smSiteCode) throws Exception {

		logger.info("Inside assembelRecord--------------- ");

		DataBaseOperation DataBaseOperation = new DataBaseOperation();
		ArrayList<MessageData> msgList = DataBaseOperation.fetchDeviceRawData(smSiteCode);

		for (int i=0; i< msgList.size(); i++) {

			MessageData msgdata = msgList.get(i);
			DataProcessor ParseRecordThread = new DataProcessor();
			ParseRecordThread.parseData(msgdata.getDeviceData(),msgdata.getMsgId());
			String siteId = msgdata.getDeviceData().substring(5, 14);
			DataBaseOperation.changeStatusAfterProcess(msgdata.getMsgId(),siteId);
		}

		try {

			synchronized(ParserThread._recordProcessMap) {

				_recordProcessMap.remove(smSiteCode+"");
				logger.info("Removed from _recordProcessMap " + smSiteCode);
			}

			logger.info("After Removing _recordProcessMap " + _recordProcessMap);
		} catch (ConcurrentModificationException cmex) {

			logger.error("At parseRecord_itoc - Concurrent Modificaton occured for Device ----------" + smSiteCode, cmex);
			cmex.printStackTrace();
		}
	}
}

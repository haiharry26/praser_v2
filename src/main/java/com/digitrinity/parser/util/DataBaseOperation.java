package com.digitrinity.parser.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import com.digitrinity.model.MessageData;

public class DataBaseOperation {

	int mapKey=0;

	public ArrayList<MessageData> fetchDeviceRawData(String smSiteCode) throws Exception {

		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		ConnectionPool jdbcObj = new ConnectionPool();
		DataSource dataSource = jdbcObj.setUpPool();
		ArrayList<MessageData> msgList = new ArrayList<MessageData>();
		
		InputStream input = new FileInputStream("parser/parser.properties");
		Properties prop = new Properties();
		// load a properties file
		prop.load(input);
		int port = Integer.parseInt(prop.getProperty("parser.port"));
		
		try {   
			
			jdbcObj.printDbStatus();

			// Performing Database Operation!
			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			connObj = dataSource.getConnection();
			jdbcObj.printDbStatus(); 
			pstmtObj = connObj.prepareStatement("Select * from incomingdata  where Msgstatus=10 and iPort ="+port+" and smSiteCode = '"+smSiteCode+"' LIMIT 10");
			ResultSet rsObj = pstmtObj.executeQuery();

			while(rsObj.next()){

				String deviceMessage = rsObj.getString("Incomingdata");
				int msgId = rsObj.getInt("msgid");
				
				MessageData messageData = new MessageData(msgId,deviceMessage);
				msgList.add(messageData);
			}
			
			rsObj.close();
			connObj.close();
			pstmtObj.close();

			System.out.println("\n=====Releasing Connection Object To Pool=====\n");            
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
					pstmtObj = null;
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		jdbcObj.printDbStatus();
	
		return msgList;
	}

	public void changeStatusAfterProcess(Integer messageId, String siteId) {

		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		ConnectionPool jdbcObj = new ConnectionPool();

		try {   
			DataSource dataSource = jdbcObj.setUpPool();
			jdbcObj.printDbStatus();

			// Performing Database Operation!
			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			connObj = dataSource.getConnection();
			jdbcObj.printDbStatus(); 
			pstmtObj = connObj.prepareStatement("UPDATE incomingdata SET Msgstatus = "+11+" WHERE smSiteCode='"+siteId+"' and msgid ="+messageId);

			pstmtObj.executeUpdate();

			System.out.println("\n=====Releasing Connection Object To Pool=====\n");            
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				// Closing Connection Object
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		jdbcObj.printDbStatus();
	}
}


package com.digitrinity.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.digitrinity.parser.parserthread.ParserThread;

public class ParserMain {
	private static Logger logger = LogManager.getLogger(ParserMain.class.getName());
	
    public static void main( String[] args ) {
    	
    	logger.info("!!!!!!!Main Thread Started!!!!!!!!!!!!");
    	Thread parseRecordsThread=new Thread(new ParserThread()); 
		parseRecordsThread.start();
    }
}



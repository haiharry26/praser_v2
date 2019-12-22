package com.digitrinity.parser.parserthread;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.sql.DataSource;

import com.digitrinity.parser.util.AlarmUtil;
import com.digitrinity.parser.util.ConnectionPool;

public class AlarmProcessing {

	public void processAlarm(String alarmString, String siteId, long _recordtime, String statusString, String commStatus, int smSiteId, String sysOutLoadStatus,
			String solarStatusMod1, String solarStatusMod2, String solarStatusMod3, String solarStatusMod4, String solarStatusMod5, String solarStatusMod6, 
			String solarStatusMod7, String solarStatusMod8, String solarStatusMod9, String solarStatusMod10, String solarStatusMod11, String solarStatusMod12, 
			String solarStatusMod13, String solarStatusMod14, String solarStatusMod15, String solarStatusMod16, String solarStatusMod17, String solarStatusMod18,
			String solarStatusMod19, String solarStatusMod20, String solarStatusMod21, String solarStatusMod22, String solarStatusMod23, String solarStatusMod24, String invStatus1, 
			String invStatus2, String invStatus3, String invStatus4, String invStatus5, String invStatus6, String invStatus7, String invStatus8,
			String invStatus9, String invStatus10, String invStatus11, String invStatus12, int crClusterID, String smSiteName, int znZoneID, int rgRegionID, String znZone, int emEmpID, String emEmployeeID, String emFirstName, String emContactNo, String emEmail, String crName, String rgRegion, int smSitetypeid
			,String liBattStatus1,String liBattStatus2,String liBattStatus3,String liBattStatus4
			,String liBattStatus5,String liBattStatus6,String liBattStatus7,String liBattStatus8,String liBattStatus9,String liBattStatus10, String liBattStatus11
			,String liBattStatus12,String liBattStatus13,String liBattStatus14,String liBattStatus15,String liBattStatus16, String liBattStatus17,String liBattStatus18
			,String liBattStatus19,String liBattStatus20, long dbCreationTime, Date hpDate) {

		/*Alarm String*/
		String preBin = new BigInteger(alarmString, 16).toString(2);
		Integer length = preBin.length();
		if (length < 8) {
			for (int i = 0; i < 8 - length; i++) {
				preBin = "0" + preBin;
			}
		}

		if(preBin.length()!=64) {

			int need = 64-preBin.length();
			for (int j = 1; j<=need; j++) {
				preBin = "0" + preBin;
			}
		}
		char[] alarmCharArray = new char[64];
		if(alarmString.equalsIgnoreCase("0000000000000000")) {

			alarmCharArray = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();
		} else {
			alarmCharArray = preBin.toCharArray();
		}

		/*Status String*/
		String statusPrebin = new BigInteger(statusString, 16).toString(2);
		Integer statusLength = statusPrebin.length();
		if (statusLength < 8) {
			for (int i = 0; i < 8 - statusLength; i++) {
				statusPrebin = "0" + statusPrebin;
			}
		}

		if(statusPrebin.length()!=32) {

			int need = 32-statusPrebin.length();
			for (int j = 1; j<=need; j++) {
				statusPrebin = "0" + statusPrebin;
			}
		}
		char[] alarmCharArray1 = new char[32];
		if(statusString.equalsIgnoreCase("00000000")) {

			alarmCharArray1 = "00000000000000000000000000000000".toCharArray();
		} else {
			alarmCharArray1 = statusPrebin.toCharArray();
		}

		/*Comm Status 2 Byte*/
		String commStatusPrebin = new BigInteger(commStatus, 16).toString(2);
		Integer commstatusLength = commStatusPrebin.length();
		if (commstatusLength < 8) {
			for (int i = 0; i < 8 - commstatusLength; i++) {
				commStatusPrebin = "0" + commStatusPrebin;
			}
		}

		if(commStatusPrebin.length()!=8) {

			int need = 8-commStatusPrebin.length();
			for (int j = 1; j<=need; j++) {
				commStatusPrebin = "0" + commStatusPrebin;
			}
		}
		char[] alarmCharArray2 = new char[8];
		if(commStatus.equalsIgnoreCase("00")) {

			alarmCharArray2 = "00000000".toCharArray();
		} else {
			alarmCharArray2 = commStatusPrebin.toCharArray();
		}

		/*SysOutLoadStatus 4 Byte*/

		String sysOutLoadStatuspreBin = new BigInteger(sysOutLoadStatus, 16).toString(2);
		Integer sysOutLoadStatuslength = sysOutLoadStatuspreBin.length();
		if (sysOutLoadStatuslength < 8) {
			for (int i = 0; i < 8 - sysOutLoadStatuslength; i++) {
				sysOutLoadStatuspreBin = "0" + sysOutLoadStatuspreBin;
			}
		}

		if(sysOutLoadStatuspreBin.length()!=16) {

			int need = 16-sysOutLoadStatuspreBin.length();
			for (int j = 1; j<=need; j++) {
				sysOutLoadStatuspreBin = "0" + sysOutLoadStatuspreBin;
			}
		}
		char[] alarmCharArray3 = new char[16];
		if(sysOutLoadStatus.equalsIgnoreCase("0000")) {

			alarmCharArray3 = "0000000000000000".toCharArray();
		} else {
			alarmCharArray3 = sysOutLoadStatuspreBin.toCharArray();
		}

		/* Solar Status Mod 1 2 Byte*/

		String solarStatusMod1preBin = new BigInteger(solarStatusMod1, 16).toString(2);
		Integer solarStatusMod1length = solarStatusMod1preBin.length();
		if (solarStatusMod1length < 8) {
			for (int i = 0; i < 8 - solarStatusMod1length; i++) {
				solarStatusMod1preBin = "0" + solarStatusMod1preBin;
			}
		}

		if(solarStatusMod1preBin.length()!=4) {

			int need = 4-solarStatusMod1preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod1preBin = "0" + solarStatusMod1preBin;
			}
		}
		char[] alarmCharArray4 = new char[8];
		if(solarStatusMod1.equalsIgnoreCase("00")) {

			alarmCharArray4 = "00000000".toCharArray();
		} else {
			alarmCharArray4 = solarStatusMod1preBin.toCharArray();
		}



		/* Solar Status Mod 2 2 Byte*/

		String solarStatusMod2preBin = new BigInteger(solarStatusMod2, 16).toString(2);
		Integer solarStatusMod2length = solarStatusMod2preBin.length();
		if (solarStatusMod2length < 8) {
			for (int i = 0; i < 8 - solarStatusMod2length; i++) {
				solarStatusMod2preBin = "0" + solarStatusMod2preBin;
			}
		}

		if(solarStatusMod2preBin.length()!=4) {

			int need = 4-solarStatusMod2preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod2preBin = "0" + solarStatusMod2preBin;
			}
		}
		char[] alarmCharArray5 = new char[8];
		if(solarStatusMod2.equalsIgnoreCase("00")) {

			alarmCharArray5 = "00000000".toCharArray();
		} else {
			alarmCharArray5 = solarStatusMod2preBin.toCharArray();
		}

		/* Solar Status Mod 3  2 Byte */

		String solarStatusMod3preBin = new BigInteger(solarStatusMod3, 16).toString(2);
		Integer solarStatusMod3length = solarStatusMod3preBin.length();
		if (solarStatusMod3length < 8) {
			for (int i = 0; i < 8 - solarStatusMod3length; i++) {
				solarStatusMod3preBin = "0" + solarStatusMod3preBin;
			}
		}

		if(solarStatusMod3preBin.length()!=4) {

			int need = 4-solarStatusMod3preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod3preBin = "0" + solarStatusMod3preBin;
			}
		}
		char[] alarmCharArray6 = new char[8];
		if(solarStatusMod3.equalsIgnoreCase("00")) {

			alarmCharArray6 = "00000000".toCharArray();
		} else {
			alarmCharArray6 = solarStatusMod3preBin.toCharArray();
		}      


		/* Solar Status Mod 4  2 Byte*/

		String solarStatusMod4preBin = new BigInteger(solarStatusMod4, 16).toString(2);
		Integer solarStatusMod4length = solarStatusMod4preBin.length();
		if (solarStatusMod4length < 8) {
			for (int i = 0; i < 8 - solarStatusMod4length; i++) {
				solarStatusMod4preBin = "0" + solarStatusMod4preBin;
			}
		}

		if(solarStatusMod4preBin.length()!=4) {

			int need = 4-solarStatusMod4preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod4preBin = "0" + solarStatusMod4preBin;
			}
		}
		char[] alarmCharArray7 = new char[8];
		if(solarStatusMod4.equalsIgnoreCase("00")) {

			alarmCharArray7 = "00000000".toCharArray();
		} else {
			alarmCharArray7 = solarStatusMod4preBin.toCharArray();
		}    

		/* Solar Status Mod 5  2 Byte*/

		String solarStatusMod5preBin = new BigInteger(solarStatusMod5, 16).toString(2);
		Integer solarStatusMod5length = solarStatusMod5preBin.length();
		if (solarStatusMod5length < 8) {
			for (int i = 0; i < 8 - solarStatusMod5length; i++) {
				solarStatusMod5preBin = "0" + solarStatusMod5preBin;
			}
		}

		if(solarStatusMod5preBin.length()!=4) {

			int need = 4-solarStatusMod5preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod5preBin = "0" + solarStatusMod5preBin;
			}
		}
		char[] alarmCharArray8 = new char[8];
		if(solarStatusMod5.equalsIgnoreCase("00")) {

			alarmCharArray8 = "00000000".toCharArray();
		} else {
			alarmCharArray8 = solarStatusMod5preBin.toCharArray();
		}      


		/* Solar Status Mod 6  2 Byte*/

		String solarStatusMod6preBin = new BigInteger(solarStatusMod6, 16).toString(2);
		Integer solarStatusMod6length = solarStatusMod6preBin.length();
		if (solarStatusMod6length < 8) {
			for (int i = 0; i < 8 - solarStatusMod6length; i++) {
				solarStatusMod6preBin = "0" + solarStatusMod6preBin;
			}
		}

		if(solarStatusMod6preBin.length()!=4) {

			int need = 4-solarStatusMod6preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod6preBin = "0" + solarStatusMod6preBin;
			}
		}
		char[] alarmCharArray9 = new char[8];
		if(solarStatusMod6.equalsIgnoreCase("00")) {

			alarmCharArray9 = "00000000".toCharArray();
		} else {
			alarmCharArray9 = solarStatusMod6preBin.toCharArray();
		}      

		/* Solar Status Mod 7  2 Byte*/

		String solarStatusMod7preBin = new BigInteger(solarStatusMod7, 16).toString(2);
		Integer solarStatusMod7length = solarStatusMod7preBin.length();
		if (solarStatusMod7length < 8) {
			for (int i = 0; i < 8 - solarStatusMod7length; i++) {
				solarStatusMod7preBin = "0" + solarStatusMod7preBin;
			}
		}

		if(solarStatusMod7preBin.length()!=4) {

			int need = 4-solarStatusMod7preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod7preBin = "0" + solarStatusMod7preBin;
			}
		}
		char[] alarmCharArray10 = new char[8];
		if(solarStatusMod7.equalsIgnoreCase("00")) {

			alarmCharArray10 = "00000000".toCharArray();
		} else {
			alarmCharArray10 = solarStatusMod7preBin.toCharArray();
		}  


		/* Solar Status Mod 8  2 Byte*/

		String solarStatusMod8preBin = new BigInteger(solarStatusMod8, 16).toString(2);
		Integer solarStatusMod8length = solarStatusMod8preBin.length();
		if (solarStatusMod8length < 8) {
			for (int i = 0; i < 8 - solarStatusMod8length; i++) {
				solarStatusMod8preBin = "0" + solarStatusMod8preBin;
			}
		}

		if(solarStatusMod8preBin.length()!=4) {

			int need = 4-solarStatusMod8preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod8preBin = "0" + solarStatusMod8preBin;
			}
		}
		char[] alarmCharArray11 = new char[8];
		if(solarStatusMod8.equalsIgnoreCase("00")) {

			alarmCharArray11 = "00000000".toCharArray();
		} else {
			alarmCharArray11 = solarStatusMod8preBin.toCharArray();
		}    

		/* Solar Status Mod 9  2 Byte*/

		String solarStatusMod9preBin = new BigInteger(solarStatusMod9, 16).toString(2);
		Integer solarStatusMod9length = solarStatusMod9preBin.length();
		if (solarStatusMod9length < 8) {
			for (int i = 0; i < 8 - solarStatusMod9length; i++) {
				solarStatusMod9preBin = "0" + solarStatusMod9preBin;
			}
		}

		if(solarStatusMod9preBin.length()!=4) {

			int need = 4-solarStatusMod9preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod9preBin = "0" + solarStatusMod9preBin;
			}
		}
		char[] alarmCharArray12 = new char[8];

		if(solarStatusMod9.equalsIgnoreCase("00")) {

			alarmCharArray12 = "00000000".toCharArray();
		} else {
			alarmCharArray12 = solarStatusMod9preBin.toCharArray();
		}      

		/* Solar Status Mod 10 2 Byte*/

		String solarStatusMod10preBin = new BigInteger(solarStatusMod10, 16).toString(2);
		Integer solarStatusMod10length = solarStatusMod10preBin.length();
		if (solarStatusMod10length < 8) {
			for (int i = 0; i < 8 - solarStatusMod10length; i++) {
				solarStatusMod10preBin = "0" + solarStatusMod10preBin;
			}
		}

		if(solarStatusMod10preBin.length()!=4) {

			int need = 4-solarStatusMod10preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod10preBin = "0" + solarStatusMod10preBin;
			}
		}
		char[] alarmCharArray13 = new char[8];
		if(solarStatusMod10.equalsIgnoreCase("00")) {

			alarmCharArray13 = "00000000".toCharArray();
		} else {
			alarmCharArray13 = solarStatusMod10preBin.toCharArray();
		}  

		/* Solar Status Mod 11 2 Byte*/

		String solarStatusMod11preBin = new BigInteger(solarStatusMod11, 16).toString(2);
		Integer solarStatusMod11length = solarStatusMod11preBin.length();
		if (solarStatusMod11length < 8) {
			for (int i = 0; i < 8 - solarStatusMod11length; i++) {
				solarStatusMod11preBin = "0" + solarStatusMod11preBin;
			}
		}

		if(solarStatusMod11preBin.length()!=4) {

			int need = 4-solarStatusMod11preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod11preBin = "0" + solarStatusMod11preBin;
			}
		}
		char[] alarmCharArray14 = new char[8];
		if(solarStatusMod11.equalsIgnoreCase("00")) {

			alarmCharArray14 = "00000000".toCharArray();
		} else {
			alarmCharArray14 = solarStatusMod11preBin.toCharArray();
		}  


		/* Solar Status Mod 12 2 Byte*/

		String solarStatusMod12preBin = new BigInteger(solarStatusMod12, 16).toString(2);
		Integer solarStatusMod12length = solarStatusMod12preBin.length();
		if (solarStatusMod12length < 8) {
			for (int i = 0; i < 8 - solarStatusMod12length; i++) {
				solarStatusMod12preBin = "0" + solarStatusMod12preBin;
			}
		}

		if(solarStatusMod12preBin.length()!=4) {

			int need = 4-solarStatusMod12preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod12preBin = "0" + solarStatusMod12preBin;
			}
		}
		char[] alarmCharArray15 = new char[8];
		if(solarStatusMod12.equalsIgnoreCase("00")) {

			alarmCharArray15 = "00000000".toCharArray();
		} else {
			alarmCharArray15 = solarStatusMod12preBin.toCharArray();
		}      

		/* Solar Status Mod 13 2 Byte*/

		String solarStatusMod13preBin = new BigInteger(solarStatusMod13, 16).toString(2);
		Integer solarStatusMod13length = solarStatusMod13preBin.length();
		if (solarStatusMod13length < 8) {
			for (int i = 0; i < 8 - solarStatusMod13length; i++) {
				solarStatusMod13preBin = "0" + solarStatusMod13preBin;
			}
		}

		if(solarStatusMod13preBin.length()!=4) {

			int need = 4-solarStatusMod13preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod13preBin = "0" + solarStatusMod13preBin;
			}
		}
		char[] alarmCharArray16 = new char[8];
		if(solarStatusMod13.equalsIgnoreCase("00")) {

			alarmCharArray16 = "00000000".toCharArray();
		} else {
			alarmCharArray16 = solarStatusMod13preBin.toCharArray();
		}      

		/* Solar Status Mod 14 2 Byte*/

		String solarStatusMod14preBin = new BigInteger(solarStatusMod14, 16).toString(2);
		Integer solarStatusMod14length = solarStatusMod14preBin.length();
		if (solarStatusMod14length < 8) {
			for (int i = 0; i < 8 - solarStatusMod14length; i++) {
				solarStatusMod14preBin = "0" + solarStatusMod14preBin;
			}
		}

		if(solarStatusMod14preBin.length()!=4) {

			int need = 4-solarStatusMod14preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod14preBin = "0" + solarStatusMod14preBin;
			}
		}
		char[] alarmCharArray17 = new char[8];
		if(solarStatusMod14.equalsIgnoreCase("00")) {

			alarmCharArray17 = "00000000".toCharArray();
		} else {
			alarmCharArray17 = solarStatusMod14preBin.toCharArray();
		}      

		/* Solar Status Mod 15 2 Byte*/

		String solarStatusMod15preBin = new BigInteger(solarStatusMod15, 16).toString(2);
		Integer solarStatusMod15length = solarStatusMod15preBin.length();
		if (solarStatusMod15length < 8) {
			for (int i = 0; i < 8 - solarStatusMod15length; i++) {
				solarStatusMod15preBin = "0" + solarStatusMod15preBin;
			}
		}

		if(solarStatusMod15preBin.length()!=4) {

			int need = 4-solarStatusMod15preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod15preBin = "0" + solarStatusMod15preBin;
			}
		}
		char[] alarmCharArray18 = new char[8];
		if(solarStatusMod15.equalsIgnoreCase("00")) {

			alarmCharArray18 = "00000000".toCharArray();
		} else {
			alarmCharArray18 = solarStatusMod15preBin.toCharArray();
		}      

		/* Solar Status Mod 16 2 Byte*/

		String solarStatusMod16preBin = new BigInteger(solarStatusMod16, 16).toString(2);
		Integer solarStatusMod16length = solarStatusMod16preBin.length();
		if (solarStatusMod16length < 8) {
			for (int i = 0; i < 8 - solarStatusMod16length; i++) {
				solarStatusMod16preBin = "0" + solarStatusMod16preBin;
			}
		}

		if(solarStatusMod16preBin.length()!=4) {

			int need = 4-solarStatusMod16preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod16preBin = "0" + solarStatusMod16preBin;
			}
		}
		char[] alarmCharArray19 = new char[8];
		if(solarStatusMod16.equalsIgnoreCase("00")) {

			alarmCharArray19 = "00000000".toCharArray();
		} else {
			alarmCharArray19 = solarStatusMod16preBin.toCharArray();
		}


		/* Solar Status Mod 17 2 Byte*/

		String solarStatusMod17preBin = new BigInteger(solarStatusMod17, 16).toString(2);
		Integer solarStatusMod17length = solarStatusMod17preBin.length();
		if (solarStatusMod17length < 8) {
			for (int i = 0; i < 8 - solarStatusMod17length; i++) {
				solarStatusMod17preBin = "0" + solarStatusMod17preBin;
			}
		}

		if(solarStatusMod17preBin.length()!=4) {

			int need = 4-solarStatusMod17preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod17preBin = "0" + solarStatusMod17preBin;
			}
		}
		char[] alarmCharArray20 = new char[8];
		if(solarStatusMod17.equalsIgnoreCase("00")) {

			alarmCharArray20 = "00000000".toCharArray();
		} else {
			alarmCharArray20 = solarStatusMod17preBin.toCharArray();
		}

		/* Solar Status Mod 18 2 Byte*/

		String solarStatusMod18preBin = new BigInteger(solarStatusMod18, 16).toString(2);
		Integer solarStatusMod18length = solarStatusMod18preBin.length();
		if (solarStatusMod18length < 8) {
			for (int i = 0; i < 8 - solarStatusMod18length; i++) {
				solarStatusMod18preBin = "0" + solarStatusMod18preBin;
			}
		}

		if(solarStatusMod18preBin.length()!=4) {

			int need = 4-solarStatusMod18preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod18preBin = "0" + solarStatusMod18preBin;
			}
		}
		char[] alarmCharArray21 = new char[8];
		if(solarStatusMod18.equalsIgnoreCase("00")) {

			alarmCharArray21 = "00000000".toCharArray();
		} else {
			alarmCharArray21 = solarStatusMod18preBin.toCharArray();
		}


		/* Solar Status Mod 19 2 Byte*/

		String solarStatusMod19preBin = new BigInteger(solarStatusMod19, 16).toString(2);
		Integer solarStatusMod19length = solarStatusMod19preBin.length();
		if (solarStatusMod19length < 8) {
			for (int i = 0; i < 8 - solarStatusMod19length; i++) {
				solarStatusMod19preBin = "0" + solarStatusMod19preBin;
			}
		}

		if(solarStatusMod19preBin.length()!=4) {

			int need = 4-solarStatusMod19preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod19preBin = "0" + solarStatusMod19preBin;
			}
		}
		char[] alarmCharArray22 = new char[8];
		if(solarStatusMod19.equalsIgnoreCase("00")) {

			alarmCharArray22 = "00000000".toCharArray();
		} else {
			alarmCharArray22 = solarStatusMod19preBin.toCharArray();
		}

		/* Solar Status Mod 20 2 Byte*/

		String solarStatusMod20preBin = new BigInteger(solarStatusMod20, 16).toString(2);
		Integer solarStatusMod20length = solarStatusMod20preBin.length();
		if (solarStatusMod20length < 8) {
			for (int i = 0; i < 8 - solarStatusMod20length; i++) {
				solarStatusMod20preBin = "0" + solarStatusMod20preBin;
			}
		}

		if(solarStatusMod20preBin.length()!=4) {

			int need = 4-solarStatusMod20preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod20preBin = "0" + solarStatusMod20preBin;
			}
		}
		char[] alarmCharArray23 = new char[8];
		if(solarStatusMod20.equalsIgnoreCase("00")) {

			alarmCharArray23 = "00000000".toCharArray();
		} else {
			alarmCharArray23 = solarStatusMod20preBin.toCharArray();
		}


		/* Solar Status Mod 21 2 Byte*/

		String solarStatusMod21preBin = new BigInteger(solarStatusMod21, 16).toString(2);
		Integer solarStatusMod21length = solarStatusMod21preBin.length();
		if (solarStatusMod21length < 8) {
			for (int i = 0; i < 8 - solarStatusMod21length; i++) {
				solarStatusMod21preBin = "0" + solarStatusMod21preBin;
			}
		}

		if(solarStatusMod21preBin.length()!=4) {

			int need = 4-solarStatusMod21preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod21preBin = "0" + solarStatusMod21preBin;
			}
		}
		char[] alarmCharArray24 = new char[8];
		if(solarStatusMod21.equalsIgnoreCase("00")) {

			alarmCharArray24 = "00000000".toCharArray();
		} else {
			alarmCharArray24 = solarStatusMod21preBin.toCharArray();
		}

		/* Solar Status Mod 22 2 Byte*/

		String solarStatusMod22preBin = new BigInteger(solarStatusMod22, 16).toString(2);
		Integer solarStatusMod22length = solarStatusMod22preBin.length();
		if (solarStatusMod22length < 8) {
			for (int i = 0; i < 8 - solarStatusMod22length; i++) {
				solarStatusMod22preBin = "0" + solarStatusMod22preBin;
			}
		}

		if(solarStatusMod22preBin.length()!=4) {

			int need = 4-solarStatusMod22preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod22preBin = "0" + solarStatusMod22preBin;
			}
		}
		char[] alarmCharArray25 = new char[8];
		if(solarStatusMod22.equalsIgnoreCase("00")) {

			alarmCharArray25 = "00000000".toCharArray();
		} else {
			alarmCharArray25 = solarStatusMod22preBin.toCharArray();
		}

		/* Solar Status Mod 23 2 Byte*/

		String solarStatusMod23preBin = new BigInteger(solarStatusMod23, 16).toString(2);
		Integer solarStatusMod23length = solarStatusMod23preBin.length();
		if (solarStatusMod23length < 8) {
			for (int i = 0; i < 8 - solarStatusMod23length; i++) {
				solarStatusMod23preBin = "0" + solarStatusMod23preBin;
			}
		}

		if(solarStatusMod23preBin.length()!=4) {

			int need = 4-solarStatusMod23preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod23preBin = "0" + solarStatusMod23preBin;
			}
		}
		char[] alarmCharArray26 = new char[8];
		if(solarStatusMod23.equalsIgnoreCase("00")) {

			alarmCharArray26 = "00000000".toCharArray();
		} else {
			alarmCharArray26 = solarStatusMod23preBin.toCharArray();
		}
		/* Solar Status Mod 24 2 Byte*/

		String solarStatusMod24preBin = new BigInteger(solarStatusMod24, 16).toString(2);
		Integer solarStatusMod24length = solarStatusMod24preBin.length();
		if (solarStatusMod24length < 8) {
			for (int i = 0; i < 8 - solarStatusMod24length; i++) {
				solarStatusMod24preBin = "0" + solarStatusMod24preBin;
			}
		}

		if(solarStatusMod24preBin.length()!=4) {

			int need = 4-solarStatusMod24preBin.length();
			for (int j = 1; j<=need; j++) {
				solarStatusMod24preBin = "0" + solarStatusMod24preBin;
			}
		}
		char[] alarmCharArray27 = new char[8];
		if(solarStatusMod24.equalsIgnoreCase("00")) {

			alarmCharArray27 = "00000000".toCharArray();
		} else {
			alarmCharArray27 = solarStatusMod24preBin.toCharArray();
		}

		char[] alarmCharArray28 = processInverterAlarm(invStatus1);
		char[] alarmCharArray29 = processInverterAlarm(invStatus2);
		char[] alarmCharArray30 = processInverterAlarm(invStatus3);
		char[] alarmCharArray31 = processInverterAlarm(invStatus4);
		char[] alarmCharArray32 = processInverterAlarm(invStatus5);
		char[] alarmCharArray33 = processInverterAlarm(invStatus6);
		char[] alarmCharArray34 = processInverterAlarm(invStatus7);
		char[] alarmCharArray35 = processInverterAlarm(invStatus8);
		char[] alarmCharArray36 = processInverterAlarm(invStatus9);
		char[] alarmCharArray37 = processInverterAlarm(invStatus10);
		char[] alarmCharArray38 = processInverterAlarm(invStatus11);
		char[] alarmCharArray39 = processInverterAlarm(invStatus12);
		
		char[] liAlarm1 = processThirtyTwo(liBattStatus1);
		char[] liAlarm2 = processThirtyTwo(liBattStatus2);
		char[] liAlarm3 = processThirtyTwo(liBattStatus3);
		char[] liAlarm4 = processThirtyTwo(liBattStatus4);
		char[] liAlarm5 = processThirtyTwo(liBattStatus5);
		char[] liAlarm6 = processThirtyTwo(liBattStatus6);
		char[] liAlarm7 = processThirtyTwo(liBattStatus7);
		char[] liAlarm8 = processThirtyTwo(liBattStatus8);
		char[] liAlarm9 = processThirtyTwo(liBattStatus9);
		char[] liAlarm10 = processThirtyTwo(liBattStatus10);
		char[] liAlarm11 = processThirtyTwo(liBattStatus11);
		char[] liAlarm12 = processThirtyTwo(liBattStatus12);
		char[] liAlarm13 = processThirtyTwo(liBattStatus13);
		char[] liAlarm14 = processThirtyTwo(liBattStatus14);
		char[] liAlarm15 = processThirtyTwo(liBattStatus15);
		char[] liAlarm16 = processThirtyTwo(liBattStatus16);
		char[] liAlarm17 = processThirtyTwo(liBattStatus17);
		char[] liAlarm18 = processThirtyTwo(liBattStatus18);
		char[] liAlarm19 = processThirtyTwo(liBattStatus19);
		char[] liAlarm20 = processThirtyTwo(liBattStatus20);

		int alarm[] = new int[1048];
		alarm[0]=Character.getNumericValue(alarmCharArray[3]);
		alarm[1]=Character.getNumericValue(alarmCharArray[2]);
		alarm[2]=Character.getNumericValue(alarmCharArray[1]);
		alarm[3]=Character.getNumericValue(alarmCharArray[0]);

		alarm[4]=Character.getNumericValue(alarmCharArray[7]);
		alarm[5]=Character.getNumericValue(alarmCharArray[6]);
		alarm[6]=Character.getNumericValue(alarmCharArray[5]);
		alarm[7]=Character.getNumericValue(alarmCharArray[4]);

		alarm[8]=Character.getNumericValue(alarmCharArray[11]);
		alarm[9]=Character.getNumericValue(alarmCharArray[10]);
		alarm[10]=Character.getNumericValue(alarmCharArray[9]);
		alarm[11]=Character.getNumericValue(alarmCharArray[8]);

		alarm[12]=Character.getNumericValue(alarmCharArray[15]);
		alarm[13]=Character.getNumericValue(alarmCharArray[14]);
		alarm[14]=Character.getNumericValue(alarmCharArray[13]);
		alarm[15]=Character.getNumericValue(alarmCharArray[12]);

		alarm[16]=Character.getNumericValue(alarmCharArray[19]);
		alarm[17]=Character.getNumericValue(alarmCharArray[18]);
		alarm[18]=Character.getNumericValue(alarmCharArray[17]);
		alarm[19]=Character.getNumericValue(alarmCharArray[16]);

		alarm[20]=Character.getNumericValue(alarmCharArray[23]);
		alarm[21]=Character.getNumericValue(alarmCharArray[22]);
		alarm[22]=Character.getNumericValue(alarmCharArray[21]);
		alarm[23]=Character.getNumericValue(alarmCharArray[20]);

		alarm[24]=Character.getNumericValue(alarmCharArray[27]);
		alarm[25]=Character.getNumericValue(alarmCharArray[26]);
		alarm[26]=Character.getNumericValue(alarmCharArray[25]);
		alarm[27]=Character.getNumericValue(alarmCharArray[24]);

		alarm[28]=Character.getNumericValue(alarmCharArray[31]);
		alarm[29]=Character.getNumericValue(alarmCharArray[30]);
		alarm[30]=Character.getNumericValue(alarmCharArray[29]);
		alarm[31]=Character.getNumericValue(alarmCharArray[28]);

		alarm[32]=Character.getNumericValue(alarmCharArray[35]);
		alarm[33]=Character.getNumericValue(alarmCharArray[34]);
		alarm[34]=Character.getNumericValue(alarmCharArray[33]);
		alarm[35]=Character.getNumericValue(alarmCharArray[32]);

		alarm[36]=Character.getNumericValue(alarmCharArray[39]);
		alarm[37]=Character.getNumericValue(alarmCharArray[38]);
		alarm[38]=Character.getNumericValue(alarmCharArray[37]);
		alarm[39]=Character.getNumericValue(alarmCharArray[36]);

		alarm[40]=Character.getNumericValue(alarmCharArray[43]);
		alarm[41]=Character.getNumericValue(alarmCharArray[42]);
		alarm[42]=Character.getNumericValue(alarmCharArray[41]);
		alarm[43]=Character.getNumericValue(alarmCharArray[40]);

		alarm[44]=Character.getNumericValue(alarmCharArray[47]);
		alarm[45]=Character.getNumericValue(alarmCharArray[46]);
		alarm[46]=Character.getNumericValue(alarmCharArray[45]);
		alarm[47]=Character.getNumericValue(alarmCharArray[44]);

		alarm[48]=Character.getNumericValue(alarmCharArray[51]);
		alarm[49]=Character.getNumericValue(alarmCharArray[50]);
		alarm[50]=Character.getNumericValue(alarmCharArray[49]);
		alarm[51]=Character.getNumericValue(alarmCharArray[48]);

		alarm[52]=Character.getNumericValue(alarmCharArray[55]);
		alarm[53]=Character.getNumericValue(alarmCharArray[54]);
		alarm[54]=Character.getNumericValue(alarmCharArray[53]);
		alarm[55]=Character.getNumericValue(alarmCharArray[52]);

		alarm[56]=Character.getNumericValue(alarmCharArray[59]);
		alarm[57]=Character.getNumericValue(alarmCharArray[58]);
		alarm[58]=Character.getNumericValue(alarmCharArray[57]);
		alarm[59]=Character.getNumericValue(alarmCharArray[56]);

		alarm[60]=Character.getNumericValue(alarmCharArray[63]);
		alarm[61]=Character.getNumericValue(alarmCharArray[62]);
		alarm[62]=Character.getNumericValue(alarmCharArray[61]);
		alarm[63]=Character.getNumericValue(alarmCharArray[60]);

		/*Status String*/

		alarm[64]=Character.getNumericValue(alarmCharArray1[3]);
		alarm[65]=Character.getNumericValue(alarmCharArray1[2]);
		alarm[66]=Character.getNumericValue(alarmCharArray1[1]);
		alarm[67]=Character.getNumericValue(alarmCharArray1[0]);
		alarm[68]=Character.getNumericValue(alarmCharArray1[7]);
		alarm[69]=Character.getNumericValue(alarmCharArray1[6]);
		alarm[70]=Character.getNumericValue(alarmCharArray1[5]);
		alarm[71]=Character.getNumericValue(alarmCharArray1[4]);
		alarm[72]=Character.getNumericValue(alarmCharArray1[11]);
		alarm[73]=Character.getNumericValue(alarmCharArray1[10]);
		alarm[74]=Character.getNumericValue(alarmCharArray1[9]);
		alarm[75]=Character.getNumericValue(alarmCharArray1[8]);
		alarm[76]=Character.getNumericValue(alarmCharArray1[15]);
		alarm[77]=Character.getNumericValue(alarmCharArray1[14]);
		alarm[78]=Character.getNumericValue(alarmCharArray1[13]);
		alarm[79]=Character.getNumericValue(alarmCharArray1[12]);
		alarm[80]=Character.getNumericValue(alarmCharArray1[19]);
		alarm[81]=Character.getNumericValue(alarmCharArray1[18]);
		alarm[82]=Character.getNumericValue(alarmCharArray1[17]);
		alarm[83]=Character.getNumericValue(alarmCharArray1[16]);
		alarm[84]=Character.getNumericValue(alarmCharArray1[23]);
		alarm[85]=Character.getNumericValue(alarmCharArray1[22]);
		alarm[86]=Character.getNumericValue(alarmCharArray1[21]);
		alarm[87]=Character.getNumericValue(alarmCharArray1[20]);
		alarm[88]=Character.getNumericValue(alarmCharArray1[27]);
		alarm[89]=Character.getNumericValue(alarmCharArray1[26]);
		alarm[90]=Character.getNumericValue(alarmCharArray1[25]);
		alarm[91]=Character.getNumericValue(alarmCharArray1[24]);
		alarm[92]=Character.getNumericValue(alarmCharArray1[31]);
		alarm[93]=Character.getNumericValue(alarmCharArray1[30]);
		alarm[94]=Character.getNumericValue(alarmCharArray1[29]);
		alarm[95]=Character.getNumericValue(alarmCharArray1[28]);




		/*Comm String*/

		alarm[96]=Character.getNumericValue(alarmCharArray2[3]);
		alarm[97]=Character.getNumericValue(alarmCharArray2[2]);
		alarm[98]=Character.getNumericValue(alarmCharArray2[1]);
		alarm[99]=Character.getNumericValue(alarmCharArray2[0]);
		alarm[100]=Character.getNumericValue(alarmCharArray2[7]);
		alarm[101]=Character.getNumericValue(alarmCharArray2[6]);
		alarm[102]=Character.getNumericValue(alarmCharArray2[5]);
		alarm[103]=Character.getNumericValue(alarmCharArray2[4]);


		/*SysOutLoadStatus*/

		alarm[104]=Character.getNumericValue(alarmCharArray3[3]);
		alarm[105]=Character.getNumericValue(alarmCharArray3[2]);
		alarm[106]=Character.getNumericValue(alarmCharArray3[1]);
		alarm[107]=Character.getNumericValue(alarmCharArray3[0]);
		alarm[108]=Character.getNumericValue(alarmCharArray3[7]);
		alarm[109]=Character.getNumericValue(alarmCharArray3[6]);
		alarm[110]=Character.getNumericValue(alarmCharArray3[5]);
		alarm[111]=Character.getNumericValue(alarmCharArray3[4]);
		alarm[112]=Character.getNumericValue(alarmCharArray3[11]);
		alarm[113]=Character.getNumericValue(alarmCharArray3[10]);
		alarm[114]=Character.getNumericValue(alarmCharArray3[9]);
		alarm[115]=Character.getNumericValue(alarmCharArray3[8]);
		alarm[116]=Character.getNumericValue(alarmCharArray3[15]);
		alarm[117]=Character.getNumericValue(alarmCharArray3[14]);
		alarm[118]=Character.getNumericValue(alarmCharArray3[13]);
		alarm[119]=Character.getNumericValue(alarmCharArray3[12]);

		/*Solar 1*/
		alarm[120]=Character.getNumericValue(alarmCharArray4[3]);
		alarm[121]=Character.getNumericValue(alarmCharArray4[2]);
		alarm[122]=Character.getNumericValue(alarmCharArray4[1]);
		alarm[123]=Character.getNumericValue(alarmCharArray4[0]);
		alarm[124]=Character.getNumericValue(alarmCharArray4[7]);
		alarm[125]=Character.getNumericValue(alarmCharArray4[6]);
		alarm[126]=Character.getNumericValue(alarmCharArray4[5]);
		alarm[127]=Character.getNumericValue(alarmCharArray4[4]);
		/*Solar 2*/
		alarm[128]=Character.getNumericValue(alarmCharArray5[3]);
		alarm[129]=Character.getNumericValue(alarmCharArray5[2]);
		alarm[130]=Character.getNumericValue(alarmCharArray5[1]);
		alarm[131]=Character.getNumericValue(alarmCharArray5[0]);
		alarm[132]=Character.getNumericValue(alarmCharArray5[7]);
		alarm[133]=Character.getNumericValue(alarmCharArray5[6]);
		alarm[134]=Character.getNumericValue(alarmCharArray5[5]);
		alarm[135]=Character.getNumericValue(alarmCharArray5[4]);
		/*Solar 3*/
		alarm[136]=Character.getNumericValue(alarmCharArray6[3]);
		alarm[137]=Character.getNumericValue(alarmCharArray6[2]);
		alarm[138]=Character.getNumericValue(alarmCharArray6[1]);
		alarm[139]=Character.getNumericValue(alarmCharArray6[0]);
		alarm[140]=Character.getNumericValue(alarmCharArray6[7]);
		alarm[141]=Character.getNumericValue(alarmCharArray6[6]);
		alarm[142]=Character.getNumericValue(alarmCharArray6[5]);
		alarm[143]=Character.getNumericValue(alarmCharArray6[4]);
		/*Solar 4*/
		alarm[144]=Character.getNumericValue(alarmCharArray7[3]);
		alarm[145]=Character.getNumericValue(alarmCharArray7[2]);
		alarm[146]=Character.getNumericValue(alarmCharArray7[1]);
		alarm[147]=Character.getNumericValue(alarmCharArray7[0]);
		alarm[148]=Character.getNumericValue(alarmCharArray7[7]);
		alarm[149]=Character.getNumericValue(alarmCharArray7[6]);
		alarm[150]=Character.getNumericValue(alarmCharArray7[5]);
		alarm[151]=Character.getNumericValue(alarmCharArray7[4]);
		/*Solar 5*/
		alarm[152]=Character.getNumericValue(alarmCharArray8[3]);
		alarm[153]=Character.getNumericValue(alarmCharArray8[2]);
		alarm[154]=Character.getNumericValue(alarmCharArray8[1]);
		alarm[155]=Character.getNumericValue(alarmCharArray8[0]);
		alarm[156]=Character.getNumericValue(alarmCharArray8[7]);
		alarm[157]=Character.getNumericValue(alarmCharArray8[6]);
		alarm[158]=Character.getNumericValue(alarmCharArray8[5]);
		alarm[159]=Character.getNumericValue(alarmCharArray8[4]);
		/*Solar 6*/
		alarm[160]=Character.getNumericValue(alarmCharArray9[3]);
		alarm[161]=Character.getNumericValue(alarmCharArray9[2]);
		alarm[162]=Character.getNumericValue(alarmCharArray9[1]);
		alarm[163]=Character.getNumericValue(alarmCharArray9[0]);
		alarm[164]=Character.getNumericValue(alarmCharArray9[7]);
		alarm[165]=Character.getNumericValue(alarmCharArray9[6]);
		alarm[166]=Character.getNumericValue(alarmCharArray9[5]);
		alarm[167]=Character.getNumericValue(alarmCharArray9[4]);
		/*Solar 7*/
		alarm[168]=Character.getNumericValue(alarmCharArray10[3]);
		alarm[169]=Character.getNumericValue(alarmCharArray10[2]);
		alarm[170]=Character.getNumericValue(alarmCharArray10[1]);
		alarm[171]=Character.getNumericValue(alarmCharArray10[0]);
		alarm[172]=Character.getNumericValue(alarmCharArray10[7]);
		alarm[173]=Character.getNumericValue(alarmCharArray10[6]);
		alarm[174]=Character.getNumericValue(alarmCharArray10[5]);
		alarm[175]=Character.getNumericValue(alarmCharArray10[4]);
		/*Solar 8*/
		alarm[176]=Character.getNumericValue(alarmCharArray11[3]);
		alarm[177]=Character.getNumericValue(alarmCharArray11[2]);
		alarm[178]=Character.getNumericValue(alarmCharArray11[1]);
		alarm[179]=Character.getNumericValue(alarmCharArray11[0]);
		alarm[180]=Character.getNumericValue(alarmCharArray11[7]);
		alarm[181]=Character.getNumericValue(alarmCharArray11[6]);
		alarm[182]=Character.getNumericValue(alarmCharArray11[5]);
		alarm[183]=Character.getNumericValue(alarmCharArray11[4]);
		/*Solar 9*/
		alarm[184]=Character.getNumericValue(alarmCharArray12[3]);
		alarm[185]=Character.getNumericValue(alarmCharArray12[2]);
		alarm[186]=Character.getNumericValue(alarmCharArray12[1]);
		alarm[187]=Character.getNumericValue(alarmCharArray12[0]);
		alarm[188]=Character.getNumericValue(alarmCharArray12[7]);
		alarm[189]=Character.getNumericValue(alarmCharArray12[6]);
		alarm[190]=Character.getNumericValue(alarmCharArray12[5]);
		alarm[191]=Character.getNumericValue(alarmCharArray12[4]);
		/*Solar 10*/
		alarm[192]=Character.getNumericValue(alarmCharArray13[3]);
		alarm[193]=Character.getNumericValue(alarmCharArray13[2]);
		alarm[194]=Character.getNumericValue(alarmCharArray13[1]);
		alarm[195]=Character.getNumericValue(alarmCharArray13[0]);
		alarm[196]=Character.getNumericValue(alarmCharArray13[7]);
		alarm[197]=Character.getNumericValue(alarmCharArray13[6]);
		alarm[198]=Character.getNumericValue(alarmCharArray13[5]);
		alarm[199]=Character.getNumericValue(alarmCharArray13[4]);
		/*Solar 11*/
		alarm[200]=Character.getNumericValue(alarmCharArray14[3]);
		alarm[201]=Character.getNumericValue(alarmCharArray14[2]);
		alarm[202]=Character.getNumericValue(alarmCharArray14[1]);
		alarm[203]=Character.getNumericValue(alarmCharArray14[0]);
		alarm[204]=Character.getNumericValue(alarmCharArray14[7]);
		alarm[205]=Character.getNumericValue(alarmCharArray14[6]);
		alarm[206]=Character.getNumericValue(alarmCharArray14[5]);
		alarm[207]=Character.getNumericValue(alarmCharArray14[4]);
		/*Solar 12*/
		alarm[208]=Character.getNumericValue(alarmCharArray15[3]);
		alarm[209]=Character.getNumericValue(alarmCharArray15[2]);
		alarm[210]=Character.getNumericValue(alarmCharArray15[1]);
		alarm[211]=Character.getNumericValue(alarmCharArray15[0]);
		alarm[212]=Character.getNumericValue(alarmCharArray15[7]);
		alarm[213]=Character.getNumericValue(alarmCharArray15[6]);
		alarm[214]=Character.getNumericValue(alarmCharArray15[5]);
		alarm[215]=Character.getNumericValue(alarmCharArray15[4]);
		/*Solar 13*/
		alarm[216]=Character.getNumericValue(alarmCharArray16[3]);
		alarm[217]=Character.getNumericValue(alarmCharArray16[2]);
		alarm[218]=Character.getNumericValue(alarmCharArray16[1]);
		alarm[219]=Character.getNumericValue(alarmCharArray16[0]);
		alarm[220]=Character.getNumericValue(alarmCharArray16[7]);
		alarm[221]=Character.getNumericValue(alarmCharArray16[6]);
		alarm[222]=Character.getNumericValue(alarmCharArray16[5]);
		alarm[223]=Character.getNumericValue(alarmCharArray16[4]);
		/*Solar 14*/
		alarm[224]=Character.getNumericValue(alarmCharArray17[3]);
		alarm[225]=Character.getNumericValue(alarmCharArray17[2]);
		alarm[226]=Character.getNumericValue(alarmCharArray17[1]);
		alarm[227]=Character.getNumericValue(alarmCharArray17[0]);
		alarm[228]=Character.getNumericValue(alarmCharArray17[7]);
		alarm[229]=Character.getNumericValue(alarmCharArray17[6]);
		alarm[230]=Character.getNumericValue(alarmCharArray17[5]);
		alarm[231]=Character.getNumericValue(alarmCharArray17[4]);
		/*Solar 15*/
		alarm[232]=Character.getNumericValue(alarmCharArray18[3]);
		alarm[233]=Character.getNumericValue(alarmCharArray18[2]);
		alarm[234]=Character.getNumericValue(alarmCharArray18[1]);
		alarm[235]=Character.getNumericValue(alarmCharArray18[0]);
		alarm[236]=Character.getNumericValue(alarmCharArray18[7]);
		alarm[237]=Character.getNumericValue(alarmCharArray18[6]);
		alarm[238]=Character.getNumericValue(alarmCharArray18[5]);
		alarm[239]=Character.getNumericValue(alarmCharArray18[4]);
		/*Solar 16*/
		alarm[240]=Character.getNumericValue(alarmCharArray19[3]);
		alarm[241]=Character.getNumericValue(alarmCharArray19[2]);
		alarm[242]=Character.getNumericValue(alarmCharArray19[1]);
		alarm[243]=Character.getNumericValue(alarmCharArray19[0]);
		alarm[244]=Character.getNumericValue(alarmCharArray19[7]);
		alarm[245]=Character.getNumericValue(alarmCharArray19[6]);
		alarm[246]=Character.getNumericValue(alarmCharArray19[5]);
		alarm[247]=Character.getNumericValue(alarmCharArray19[4]);
		/*Solar 17*/
		alarm[248]=Character.getNumericValue(alarmCharArray20[3]);
		alarm[249]=Character.getNumericValue(alarmCharArray20[2]);
		alarm[250]=Character.getNumericValue(alarmCharArray20[1]);
		alarm[251]=Character.getNumericValue(alarmCharArray20[0]);
		alarm[252]=Character.getNumericValue(alarmCharArray20[7]);
		alarm[253]=Character.getNumericValue(alarmCharArray20[6]);
		alarm[254]=Character.getNumericValue(alarmCharArray20[5]);
		alarm[255]=Character.getNumericValue(alarmCharArray20[4]);
		/*Solar 18*/
		alarm[256]=Character.getNumericValue(alarmCharArray21[3]);
		alarm[257]=Character.getNumericValue(alarmCharArray21[2]);
		alarm[258]=Character.getNumericValue(alarmCharArray21[1]);
		alarm[259]=Character.getNumericValue(alarmCharArray21[0]);
		alarm[260]=Character.getNumericValue(alarmCharArray21[7]);
		alarm[261]=Character.getNumericValue(alarmCharArray21[6]);
		alarm[262]=Character.getNumericValue(alarmCharArray21[5]);
		alarm[263]=Character.getNumericValue(alarmCharArray21[4]);
		/*Solar 19*/
		alarm[264]=Character.getNumericValue(alarmCharArray22[3]);
		alarm[265]=Character.getNumericValue(alarmCharArray22[2]);
		alarm[266]=Character.getNumericValue(alarmCharArray22[1]);
		alarm[267]=Character.getNumericValue(alarmCharArray22[0]);
		alarm[268]=Character.getNumericValue(alarmCharArray22[7]);
		alarm[269]=Character.getNumericValue(alarmCharArray22[6]);
		alarm[270]=Character.getNumericValue(alarmCharArray22[5]);
		alarm[271]=Character.getNumericValue(alarmCharArray22[4]);
		/*Solar 20*/
		alarm[272]=Character.getNumericValue(alarmCharArray23[3]);
		alarm[273]=Character.getNumericValue(alarmCharArray23[2]);
		alarm[274]=Character.getNumericValue(alarmCharArray23[1]);
		alarm[275]=Character.getNumericValue(alarmCharArray23[0]);
		alarm[276]=Character.getNumericValue(alarmCharArray23[7]);
		alarm[277]=Character.getNumericValue(alarmCharArray23[6]);
		alarm[278]=Character.getNumericValue(alarmCharArray23[5]);
		alarm[279]=Character.getNumericValue(alarmCharArray23[4]);
		/*Solar 21*/
		alarm[280]=Character.getNumericValue(alarmCharArray24[3]);
		alarm[281]=Character.getNumericValue(alarmCharArray24[2]);
		alarm[282]=Character.getNumericValue(alarmCharArray24[1]);
		alarm[283]=Character.getNumericValue(alarmCharArray24[0]);
		alarm[284]=Character.getNumericValue(alarmCharArray24[7]);
		alarm[285]=Character.getNumericValue(alarmCharArray24[6]);
		alarm[286]=Character.getNumericValue(alarmCharArray24[5]);
		alarm[287]=Character.getNumericValue(alarmCharArray24[4]);
		/*Solar 22*/
		alarm[288]=Character.getNumericValue(alarmCharArray25[3]);
		alarm[289]=Character.getNumericValue(alarmCharArray25[2]);
		alarm[290]=Character.getNumericValue(alarmCharArray25[1]);
		alarm[291]=Character.getNumericValue(alarmCharArray25[0]);
		alarm[292]=Character.getNumericValue(alarmCharArray25[7]);
		alarm[293]=Character.getNumericValue(alarmCharArray25[6]);
		alarm[294]=Character.getNumericValue(alarmCharArray25[5]);
		alarm[295]=Character.getNumericValue(alarmCharArray25[4]);
		/*Solar 23*/
		alarm[296]=Character.getNumericValue(alarmCharArray26[3]);
		alarm[297]=Character.getNumericValue(alarmCharArray26[2]);
		alarm[298]=Character.getNumericValue(alarmCharArray26[1]);
		alarm[299]=Character.getNumericValue(alarmCharArray26[0]);
		alarm[300]=Character.getNumericValue(alarmCharArray26[7]);
		alarm[301]=Character.getNumericValue(alarmCharArray26[6]);
		alarm[302]=Character.getNumericValue(alarmCharArray26[5]);
		alarm[303]=Character.getNumericValue(alarmCharArray26[4]);

		/*Solar 24*/
		alarm[304]=Character.getNumericValue(alarmCharArray27[3]);
		alarm[305]=Character.getNumericValue(alarmCharArray27[2]);
		alarm[306]=Character.getNumericValue(alarmCharArray27[1]);
		alarm[307]=Character.getNumericValue(alarmCharArray27[0]);
		alarm[308]=Character.getNumericValue(alarmCharArray27[7]);
		alarm[309]=Character.getNumericValue(alarmCharArray27[6]);
		alarm[310]=Character.getNumericValue(alarmCharArray27[5]);
		alarm[311]=Character.getNumericValue(alarmCharArray27[4]);

		/*Inv 1*/
		alarm[312]=Character.getNumericValue(alarmCharArray28[3]);
		alarm[313]=Character.getNumericValue(alarmCharArray28[2]);
		alarm[314]=Character.getNumericValue(alarmCharArray28[1]);
		alarm[315]=Character.getNumericValue(alarmCharArray28[0]);
		alarm[316]=Character.getNumericValue(alarmCharArray28[7]);
		alarm[317]=Character.getNumericValue(alarmCharArray28[6]);
		alarm[318]=Character.getNumericValue(alarmCharArray28[5]);
		alarm[319]=Character.getNumericValue(alarmCharArray28[4]);

		/*Inv2*/
		alarm[320]=Character.getNumericValue(alarmCharArray29[3]);
		alarm[321]=Character.getNumericValue(alarmCharArray29[2]);
		alarm[322]=Character.getNumericValue(alarmCharArray29[1]);
		alarm[323]=Character.getNumericValue(alarmCharArray29[0]);
		alarm[324]=Character.getNumericValue(alarmCharArray29[7]);
		alarm[325]=Character.getNumericValue(alarmCharArray29[6]);
		alarm[326]=Character.getNumericValue(alarmCharArray29[5]);
		alarm[327]=Character.getNumericValue(alarmCharArray29[4]);

		/*Inv3*/
		alarm[328]=Character.getNumericValue(alarmCharArray30[3]);
		alarm[329]=Character.getNumericValue(alarmCharArray30[2]);
		alarm[330]=Character.getNumericValue(alarmCharArray30[1]);
		alarm[331]=Character.getNumericValue(alarmCharArray30[0]);
		alarm[332]=Character.getNumericValue(alarmCharArray30[7]);
		alarm[333]=Character.getNumericValue(alarmCharArray30[6]);
		alarm[334]=Character.getNumericValue(alarmCharArray30[5]);
		alarm[335]=Character.getNumericValue(alarmCharArray30[4]);

		/*Inv4*/
		alarm[336]=Character.getNumericValue(alarmCharArray31[3]);
		alarm[337]=Character.getNumericValue(alarmCharArray31[2]);
		alarm[338]=Character.getNumericValue(alarmCharArray31[1]);
		alarm[339]=Character.getNumericValue(alarmCharArray31[0]);
		alarm[340]=Character.getNumericValue(alarmCharArray31[7]);
		alarm[341]=Character.getNumericValue(alarmCharArray31[6]);
		alarm[342]=Character.getNumericValue(alarmCharArray31[5]);
		alarm[343]=Character.getNumericValue(alarmCharArray31[4]);

		/*Inv5*/
		alarm[344]=Character.getNumericValue(alarmCharArray32[3]);
		alarm[345]=Character.getNumericValue(alarmCharArray32[2]);
		alarm[346]=Character.getNumericValue(alarmCharArray32[1]);
		alarm[347]=Character.getNumericValue(alarmCharArray32[0]);
		alarm[348]=Character.getNumericValue(alarmCharArray32[7]);
		alarm[349]=Character.getNumericValue(alarmCharArray32[6]);
		alarm[350]=Character.getNumericValue(alarmCharArray32[5]);
		alarm[351]=Character.getNumericValue(alarmCharArray32[4]);
		/*Inv6*/

		alarm[352]=Character.getNumericValue(alarmCharArray33[3]);
		alarm[353]=Character.getNumericValue(alarmCharArray33[2]);
		alarm[354]=Character.getNumericValue(alarmCharArray33[1]);
		alarm[355]=Character.getNumericValue(alarmCharArray33[0]);
		alarm[356]=Character.getNumericValue(alarmCharArray33[7]);
		alarm[357]=Character.getNumericValue(alarmCharArray33[6]);
		alarm[358]=Character.getNumericValue(alarmCharArray33[5]);
		alarm[359]=Character.getNumericValue(alarmCharArray33[4]);

		/*Inv7*/
		alarm[360]=Character.getNumericValue(alarmCharArray34[3]);
		alarm[361]=Character.getNumericValue(alarmCharArray34[2]);
		alarm[362]=Character.getNumericValue(alarmCharArray34[1]);
		alarm[363]=Character.getNumericValue(alarmCharArray34[0]);
		alarm[364]=Character.getNumericValue(alarmCharArray34[7]);
		alarm[365]=Character.getNumericValue(alarmCharArray34[6]);
		alarm[366]=Character.getNumericValue(alarmCharArray34[5]);
		alarm[367]=Character.getNumericValue(alarmCharArray34[4]);

		/*Inv8*/
		alarm[368]=Character.getNumericValue(alarmCharArray35[3]);
		alarm[369]=Character.getNumericValue(alarmCharArray35[2]);
		alarm[370]=Character.getNumericValue(alarmCharArray35[1]);
		alarm[371]=Character.getNumericValue(alarmCharArray35[0]);
		alarm[372]=Character.getNumericValue(alarmCharArray35[7]);
		alarm[373]=Character.getNumericValue(alarmCharArray35[6]);
		alarm[374]=Character.getNumericValue(alarmCharArray35[5]);
		alarm[375]=Character.getNumericValue(alarmCharArray35[4]);

		/*Inv9*/
		alarm[376]=Character.getNumericValue(alarmCharArray36[3]);
		alarm[377]=Character.getNumericValue(alarmCharArray36[2]);
		alarm[378]=Character.getNumericValue(alarmCharArray36[1]);
		alarm[379]=Character.getNumericValue(alarmCharArray36[0]);
		alarm[380]=Character.getNumericValue(alarmCharArray36[7]);
		alarm[381]=Character.getNumericValue(alarmCharArray36[6]);
		alarm[382]=Character.getNumericValue(alarmCharArray36[5]);
		alarm[383]=Character.getNumericValue(alarmCharArray36[4]);

		/*Inv10*/
		alarm[384]=Character.getNumericValue(alarmCharArray37[3]);
		alarm[385]=Character.getNumericValue(alarmCharArray37[2]);
		alarm[386]=Character.getNumericValue(alarmCharArray37[1]);
		alarm[387]=Character.getNumericValue(alarmCharArray37[0]);
		alarm[388]=Character.getNumericValue(alarmCharArray37[7]);
		alarm[389]=Character.getNumericValue(alarmCharArray37[6]);
		alarm[390]=Character.getNumericValue(alarmCharArray37[5]);
		alarm[391]=Character.getNumericValue(alarmCharArray37[4]);

		/*Inv 11*/
		alarm[392]=Character.getNumericValue(alarmCharArray38[3]);
		alarm[393]=Character.getNumericValue(alarmCharArray38[2]);
		alarm[394]=Character.getNumericValue(alarmCharArray38[1]);
		alarm[395]=Character.getNumericValue(alarmCharArray38[0]);
		alarm[396]=Character.getNumericValue(alarmCharArray38[7]);
		alarm[397]=Character.getNumericValue(alarmCharArray38[6]);
		alarm[398]=Character.getNumericValue(alarmCharArray38[5]);
		alarm[399]=Character.getNumericValue(alarmCharArray38[4]);

		/*Inv12*/
		alarm[400]=Character.getNumericValue(alarmCharArray39[3]);
		alarm[401]=Character.getNumericValue(alarmCharArray39[2]);
		alarm[402]=Character.getNumericValue(alarmCharArray39[1]);
		alarm[403]=Character.getNumericValue(alarmCharArray39[0]);
		alarm[404]=Character.getNumericValue(alarmCharArray39[7]);
		alarm[405]=Character.getNumericValue(alarmCharArray39[6]);
		alarm[406]=Character.getNumericValue(alarmCharArray39[5]);
		alarm[407]=Character.getNumericValue(alarmCharArray39[4]);

		/*Li1 Byte 1 Set 1*/
		alarm[408]=Character.getNumericValue(liAlarm1[3]);
		alarm[409]=Character.getNumericValue(liAlarm1[2]);
		alarm[410]=Character.getNumericValue(liAlarm1[1]);
		alarm[411]=Character.getNumericValue(liAlarm1[0]);

		/*Li1 Byte 1 Set 2*/
		alarm[412]=Character.getNumericValue(liAlarm1[7]);
		alarm[413]=Character.getNumericValue(liAlarm1[6]);
		alarm[414]=Character.getNumericValue(liAlarm1[5]);
		alarm[415]=Character.getNumericValue(liAlarm1[4]);

		/*Li1 Byte 2 Set 1*/
		alarm[416]=Character.getNumericValue(liAlarm1[11]);
		alarm[417]=Character.getNumericValue(liAlarm1[10]);
		alarm[418]=Character.getNumericValue(liAlarm1[9]);
		alarm[419]=Character.getNumericValue(liAlarm1[8]);

		/*Li1 Byte 2 Set 2*/
		alarm[420]=Character.getNumericValue(liAlarm1[15]);
		alarm[421]=Character.getNumericValue(liAlarm1[14]);
		alarm[422]=Character.getNumericValue(liAlarm1[13]);
		alarm[423]=Character.getNumericValue(liAlarm1[12]);

		/*Li1 Byte 3 Set 1*/
		alarm[424]=Character.getNumericValue(liAlarm1[19]);
		alarm[425]=Character.getNumericValue(liAlarm1[18]);
		alarm[426]=Character.getNumericValue(liAlarm1[17]);
		alarm[427]=Character.getNumericValue(liAlarm1[16]);

		/*Li1 Byte 3 Set 2*/
		alarm[428]=Character.getNumericValue(liAlarm1[23]);
		alarm[429]=Character.getNumericValue(liAlarm1[22]);
		alarm[430]=Character.getNumericValue(liAlarm1[21]);
		alarm[431]=Character.getNumericValue(liAlarm1[20]);

		/*Li1 Byte 4 Set 1*/
		alarm[432]=Character.getNumericValue(liAlarm1[27]);
		alarm[433]=Character.getNumericValue(liAlarm1[26]);
		alarm[434]=Character.getNumericValue(liAlarm1[25]);
		alarm[435]=Character.getNumericValue(liAlarm1[24]);

		/*Li1 Byte 4 Set 2*/
		alarm[436]=Character.getNumericValue(liAlarm1[31]);
		alarm[437]=Character.getNumericValue(liAlarm1[30]);
		alarm[438]=Character.getNumericValue(liAlarm1[29]);
		alarm[439]=Character.getNumericValue(liAlarm1[28]);


		/*Li2 Byte 1 Set 1*/
		alarm[440]=Character.getNumericValue(liAlarm2[3]);
		alarm[441]=Character.getNumericValue(liAlarm2[2]);
		alarm[442]=Character.getNumericValue(liAlarm2[1]);
		alarm[443]=Character.getNumericValue(liAlarm2[0]);

		/*Li2 Byte 1 Set 2*/

		alarm[444]=Character.getNumericValue(liAlarm2[7]);
		alarm[445]=Character.getNumericValue(liAlarm2[6]);
		alarm[446]=Character.getNumericValue(liAlarm2[5]);
		alarm[447]=Character.getNumericValue(liAlarm2[4]);
		/*Li2 Byte 2 Set 1*/

		alarm[448]=Character.getNumericValue(liAlarm2[11]);
		alarm[449]=Character.getNumericValue(liAlarm2[10]);
		alarm[450]=Character.getNumericValue(liAlarm2[9]);
		alarm[451]=Character.getNumericValue(liAlarm2[8]);
		/*Li2 Byte 2 Set 2*/

		alarm[452]=Character.getNumericValue(liAlarm2[15]);
		alarm[453]=Character.getNumericValue(liAlarm2[14]);
		alarm[454]=Character.getNumericValue(liAlarm2[13]);
		alarm[455]=Character.getNumericValue(liAlarm2[12]);
		/*Li2 Byte 3 Set 1*/

		alarm[456]=Character.getNumericValue(liAlarm2[19]);
		alarm[457]=Character.getNumericValue(liAlarm2[18]);
		alarm[458]=Character.getNumericValue(liAlarm2[17]);
		alarm[459]=Character.getNumericValue(liAlarm2[16]);
		/*Li2 Byte 3 Set 2*/

		alarm[460]=Character.getNumericValue(liAlarm2[23]);
		alarm[461]=Character.getNumericValue(liAlarm2[22]);
		alarm[462]=Character.getNumericValue(liAlarm2[21]);
		alarm[463]=Character.getNumericValue(liAlarm2[20]);
		/*Li2 Byte 4 Set 1*/

		alarm[464]=Character.getNumericValue(liAlarm2[27]);
		alarm[465]=Character.getNumericValue(liAlarm2[26]);
		alarm[466]=Character.getNumericValue(liAlarm2[25]);
		alarm[467]=Character.getNumericValue(liAlarm2[24]);
		/*Li2 Byte 4 Set 2*/
		alarm[468]=Character.getNumericValue(liAlarm2[31]);
		alarm[469]=Character.getNumericValue(liAlarm2[30]);
		alarm[470]=Character.getNumericValue(liAlarm2[29]);
		alarm[471]=Character.getNumericValue(liAlarm2[28]);
		/*Li3 Byte 1 Set 1*/

		alarm[472]=Character.getNumericValue(liAlarm3[3]);
		alarm[473]=Character.getNumericValue(liAlarm3[2]);
		alarm[474]=Character.getNumericValue(liAlarm3[1]);
		alarm[475]=Character.getNumericValue(liAlarm3[0]);
		/*Li3 Byte 1 Set 2*/

		alarm[476]=Character.getNumericValue(liAlarm3[7]);
		alarm[477]=Character.getNumericValue(liAlarm3[6]);
		alarm[478]=Character.getNumericValue(liAlarm3[5]);
		alarm[479]=Character.getNumericValue(liAlarm3[4]);
		/*Li3 Byte 2 Set 1*/

		alarm[480]=Character.getNumericValue(liAlarm3[11]);
		alarm[481]=Character.getNumericValue(liAlarm3[10]);
		alarm[482]=Character.getNumericValue(liAlarm3[9]);
		alarm[483]=Character.getNumericValue(liAlarm3[8]);
		/*Li3 Byte 2 Set 2*/

		alarm[484]=Character.getNumericValue(liAlarm3[15]);
		alarm[485]=Character.getNumericValue(liAlarm3[14]);
		alarm[486]=Character.getNumericValue(liAlarm3[13]);
		alarm[487]=Character.getNumericValue(liAlarm3[12]);
		/*Li3 Byte 3 Set 1*/

		alarm[488]=Character.getNumericValue(liAlarm3[19]);
		alarm[489]=Character.getNumericValue(liAlarm3[18]);
		alarm[490]=Character.getNumericValue(liAlarm3[17]);
		alarm[491]=Character.getNumericValue(liAlarm3[16]);
		/*Li3 Byte 3 Set 2*/

		alarm[492]=Character.getNumericValue(liAlarm3[23]);
		alarm[493]=Character.getNumericValue(liAlarm3[22]);
		alarm[494]=Character.getNumericValue(liAlarm3[21]);
		alarm[495]=Character.getNumericValue(liAlarm3[20]);
		/*Li3 Byte 4 Set 1*/

		alarm[496]=Character.getNumericValue(liAlarm3[27]);
		alarm[497]=Character.getNumericValue(liAlarm3[26]);
		alarm[498]=Character.getNumericValue(liAlarm3[25]);
		alarm[499]=Character.getNumericValue(liAlarm3[24]);
		/*Li3 Byte 4 Set 2*/
		alarm[500]=Character.getNumericValue(liAlarm3[31]);
		alarm[501]=Character.getNumericValue(liAlarm3[30]);
		alarm[502]=Character.getNumericValue(liAlarm3[29]);
		alarm[503]=Character.getNumericValue(liAlarm3[28]);
		/*Li4 Byte 1 Set 1*/

		alarm[504]=Character.getNumericValue(liAlarm4[3]);
		alarm[505]=Character.getNumericValue(liAlarm4[2]);
		alarm[506]=Character.getNumericValue(liAlarm4[1]);
		alarm[507]=Character.getNumericValue(liAlarm4[0]);
		/*Li4 Byte 1 Set 2*/

		alarm[508]=Character.getNumericValue(liAlarm4[7]);
		alarm[509]=Character.getNumericValue(liAlarm4[6]);
		alarm[510]=Character.getNumericValue(liAlarm4[5]);
		alarm[511]=Character.getNumericValue(liAlarm4[4]);
		/*Li4 Byte 2 Set 1*/

		alarm[512]=Character.getNumericValue(liAlarm4[11]);
		alarm[513]=Character.getNumericValue(liAlarm4[10]);
		alarm[514]=Character.getNumericValue(liAlarm4[9]);
		alarm[515]=Character.getNumericValue(liAlarm4[8]);
		/*Li4 Byte 2 Set 2*/

		alarm[516]=Character.getNumericValue(liAlarm4[15]);
		alarm[517]=Character.getNumericValue(liAlarm4[14]);
		alarm[518]=Character.getNumericValue(liAlarm4[13]);
		alarm[519]=Character.getNumericValue(liAlarm4[12]);
		/*Li4 Byte 3 Set 1*/

		alarm[520]=Character.getNumericValue(liAlarm4[19]);
		alarm[521]=Character.getNumericValue(liAlarm4[18]);
		alarm[522]=Character.getNumericValue(liAlarm4[17]);
		alarm[523]=Character.getNumericValue(liAlarm4[16]);
		/*Li4 Byte 3 Set 2*/

		alarm[524]=Character.getNumericValue(liAlarm4[23]);
		alarm[525]=Character.getNumericValue(liAlarm4[22]);
		alarm[526]=Character.getNumericValue(liAlarm4[21]);
		alarm[527]=Character.getNumericValue(liAlarm4[20]);
		/*Li4 Byte 4 Set 1*/

		alarm[528]=Character.getNumericValue(liAlarm4[27]);
		alarm[529]=Character.getNumericValue(liAlarm4[26]);
		alarm[530]=Character.getNumericValue(liAlarm4[25]);
		alarm[531]=Character.getNumericValue(liAlarm4[24]);
		/*Li4 Byte 4 Set 2*/
		alarm[532]=Character.getNumericValue(liAlarm4[31]);
		alarm[533]=Character.getNumericValue(liAlarm4[30]);
		alarm[534]=Character.getNumericValue(liAlarm4[29]);
		alarm[535]=Character.getNumericValue(liAlarm4[28]);
		/*Li5 Byte 1 Set 1*/

		alarm[536]=Character.getNumericValue(liAlarm5[3]);
		alarm[537]=Character.getNumericValue(liAlarm5[2]);
		alarm[538]=Character.getNumericValue(liAlarm5[1]);
		alarm[539]=Character.getNumericValue(liAlarm5[0]);
		/*Li5 Byte 1 Set 2*/

		alarm[540]=Character.getNumericValue(liAlarm5[7]);
		alarm[541]=Character.getNumericValue(liAlarm5[6]);
		alarm[542]=Character.getNumericValue(liAlarm5[5]);
		alarm[543]=Character.getNumericValue(liAlarm5[4]);
		/*Li5 Byte 2 Set 1*/

		alarm[544]=Character.getNumericValue(liAlarm5[11]);
		alarm[545]=Character.getNumericValue(liAlarm5[10]);
		alarm[546]=Character.getNumericValue(liAlarm5[9]);
		alarm[547]=Character.getNumericValue(liAlarm5[8]);
		/*Li5 Byte 2 Set 2*/

		alarm[548]=Character.getNumericValue(liAlarm5[15]);
		alarm[549]=Character.getNumericValue(liAlarm5[14]);
		alarm[550]=Character.getNumericValue(liAlarm5[13]);
		alarm[551]=Character.getNumericValue(liAlarm5[12]);
		/*Li5 Byte 3 Set 1*/

		alarm[552]=Character.getNumericValue(liAlarm5[19]);
		alarm[553]=Character.getNumericValue(liAlarm5[18]);
		alarm[554]=Character.getNumericValue(liAlarm5[17]);
		alarm[555]=Character.getNumericValue(liAlarm5[16]);
		/*Li5 Byte 3 Set 2*/

		alarm[556]=Character.getNumericValue(liAlarm5[23]);
		alarm[557]=Character.getNumericValue(liAlarm5[22]);
		alarm[558]=Character.getNumericValue(liAlarm5[21]);
		alarm[559]=Character.getNumericValue(liAlarm5[20]);
		/*Li5 Byte 4 Set 1*/

		alarm[560]=Character.getNumericValue(liAlarm5[27]);
		alarm[561]=Character.getNumericValue(liAlarm5[26]);
		alarm[562]=Character.getNumericValue(liAlarm5[25]);
		alarm[563]=Character.getNumericValue(liAlarm5[24]);
		/*Li5 Byte 4 Set 2*/
		alarm[564]=Character.getNumericValue(liAlarm5[31]);
		alarm[565]=Character.getNumericValue(liAlarm5[30]);
		alarm[566]=Character.getNumericValue(liAlarm5[29]);
		alarm[567]=Character.getNumericValue(liAlarm5[28]);
		/*Li6 Byte 1 Set 1*/

		alarm[568]=Character.getNumericValue(liAlarm6[3]);
		alarm[569]=Character.getNumericValue(liAlarm6[2]);
		alarm[570]=Character.getNumericValue(liAlarm6[1]);
		alarm[571]=Character.getNumericValue(liAlarm6[0]);
		/*Li6 Byte 1 Set 2*/

		alarm[572]=Character.getNumericValue(liAlarm6[7]);
		alarm[573]=Character.getNumericValue(liAlarm6[6]);
		alarm[574]=Character.getNumericValue(liAlarm6[5]);
		alarm[575]=Character.getNumericValue(liAlarm6[4]);
		/*Li6 Byte 2 Set 1*/

		alarm[576]=Character.getNumericValue(liAlarm6[11]);
		alarm[577]=Character.getNumericValue(liAlarm6[10]);
		alarm[578]=Character.getNumericValue(liAlarm6[9]);
		alarm[579]=Character.getNumericValue(liAlarm6[8]);
		/*Li6 Byte 2 Set 2*/

		alarm[580]=Character.getNumericValue(liAlarm6[15]);
		alarm[581]=Character.getNumericValue(liAlarm6[14]);
		alarm[582]=Character.getNumericValue(liAlarm6[13]);
		alarm[583]=Character.getNumericValue(liAlarm6[12]);
		/*Li6 Byte 3 Set 1*/

		alarm[584]=Character.getNumericValue(liAlarm6[19]);
		alarm[585]=Character.getNumericValue(liAlarm6[18]);
		alarm[586]=Character.getNumericValue(liAlarm6[17]);
		alarm[587]=Character.getNumericValue(liAlarm6[16]);
		/*Li6 Byte 3 Set 2*/

		alarm[588]=Character.getNumericValue(liAlarm6[23]);
		alarm[589]=Character.getNumericValue(liAlarm6[22]);
		alarm[590]=Character.getNumericValue(liAlarm6[21]);
		alarm[591]=Character.getNumericValue(liAlarm6[20]);
		/*Li6 Byte 4 Set 1*/

		alarm[592]=Character.getNumericValue(liAlarm6[27]);
		alarm[593]=Character.getNumericValue(liAlarm6[26]);
		alarm[594]=Character.getNumericValue(liAlarm6[25]);
		alarm[595]=Character.getNumericValue(liAlarm6[24]);
		/*Li6 Byte 4 Set 2*/
		alarm[596]=Character.getNumericValue(liAlarm6[31]);
		alarm[597]=Character.getNumericValue(liAlarm6[30]);
		alarm[598]=Character.getNumericValue(liAlarm6[29]);
		alarm[599]=Character.getNumericValue(liAlarm6[28]);
		/*Li7 Byte 1 Set 1*/

		alarm[600]=Character.getNumericValue(liAlarm7[3]);
		alarm[601]=Character.getNumericValue(liAlarm7[2]);
		alarm[602]=Character.getNumericValue(liAlarm7[1]);
		alarm[603]=Character.getNumericValue(liAlarm7[0]);
		/*Li7 Byte 1 Set 2*/

		alarm[604]=Character.getNumericValue(liAlarm7[7]);
		alarm[605]=Character.getNumericValue(liAlarm7[6]);
		alarm[606]=Character.getNumericValue(liAlarm7[5]);
		alarm[607]=Character.getNumericValue(liAlarm7[4]);
		/*Li7 Byte 2 Set 1*/

		alarm[608]=Character.getNumericValue(liAlarm7[11]);
		alarm[609]=Character.getNumericValue(liAlarm7[10]);
		alarm[610]=Character.getNumericValue(liAlarm7[9]);
		alarm[611]=Character.getNumericValue(liAlarm7[8]);
		/*Li7 Byte 2 Set 2*/

		alarm[612]=Character.getNumericValue(liAlarm7[15]);
		alarm[613]=Character.getNumericValue(liAlarm7[14]);
		alarm[614]=Character.getNumericValue(liAlarm7[13]);
		alarm[615]=Character.getNumericValue(liAlarm7[12]);
		/*Li7 Byte 3 Set 1*/

		alarm[616]=Character.getNumericValue(liAlarm7[19]);
		alarm[617]=Character.getNumericValue(liAlarm7[18]);
		alarm[618]=Character.getNumericValue(liAlarm7[17]);
		alarm[619]=Character.getNumericValue(liAlarm7[16]);
		/*Li7 Byte 3 Set 2*/

		alarm[620]=Character.getNumericValue(liAlarm7[23]);
		alarm[621]=Character.getNumericValue(liAlarm7[22]);
		alarm[622]=Character.getNumericValue(liAlarm7[21]);
		alarm[623]=Character.getNumericValue(liAlarm7[20]);
		/*Li7 Byte 4 Set 1*/

		alarm[624]=Character.getNumericValue(liAlarm7[27]);
		alarm[625]=Character.getNumericValue(liAlarm7[26]);
		alarm[626]=Character.getNumericValue(liAlarm7[25]);
		alarm[627]=Character.getNumericValue(liAlarm7[24]);
		/*Li7 Byte 4 Set 2*/
		alarm[628]=Character.getNumericValue(liAlarm7[31]);
		alarm[629]=Character.getNumericValue(liAlarm7[30]);
		alarm[630]=Character.getNumericValue(liAlarm7[29]);
		alarm[631]=Character.getNumericValue(liAlarm7[28]);
		/*Li8 Byte 1 Set 1*/

		alarm[632]=Character.getNumericValue(liAlarm8[3]);
		alarm[633]=Character.getNumericValue(liAlarm8[2]);
		alarm[634]=Character.getNumericValue(liAlarm8[1]);
		alarm[635]=Character.getNumericValue(liAlarm8[0]);
		/*Li8 Byte 1 Set 2*/

		alarm[636]=Character.getNumericValue(liAlarm8[7]);
		alarm[637]=Character.getNumericValue(liAlarm8[6]);
		alarm[638]=Character.getNumericValue(liAlarm8[5]);
		alarm[639]=Character.getNumericValue(liAlarm8[4]);
		/*Li8 Byte 2 Set 1*/

		alarm[640]=Character.getNumericValue(liAlarm8[11]);
		alarm[641]=Character.getNumericValue(liAlarm8[10]);
		alarm[642]=Character.getNumericValue(liAlarm8[9]);
		alarm[643]=Character.getNumericValue(liAlarm8[8]);
		/*Li8 Byte 2 Set 2*/

		alarm[644]=Character.getNumericValue(liAlarm8[15]);
		alarm[645]=Character.getNumericValue(liAlarm8[14]);
		alarm[646]=Character.getNumericValue(liAlarm8[13]);
		alarm[647]=Character.getNumericValue(liAlarm8[12]);
		/*Li8 Byte 3 Set 1*/

		alarm[648]=Character.getNumericValue(liAlarm8[19]);
		alarm[649]=Character.getNumericValue(liAlarm8[18]);
		alarm[650]=Character.getNumericValue(liAlarm8[17]);
		alarm[651]=Character.getNumericValue(liAlarm8[16]);
		/*Li8 Byte 3 Set 2*/

		alarm[652]=Character.getNumericValue(liAlarm8[23]);
		alarm[653]=Character.getNumericValue(liAlarm8[22]);
		alarm[654]=Character.getNumericValue(liAlarm8[21]);
		alarm[655]=Character.getNumericValue(liAlarm8[20]);
		/*Li8 Byte 4 Set 1*/

		alarm[656]=Character.getNumericValue(liAlarm8[27]);
		alarm[657]=Character.getNumericValue(liAlarm8[26]);
		alarm[658]=Character.getNumericValue(liAlarm8[25]);
		alarm[659]=Character.getNumericValue(liAlarm8[24]);
		/*Li8 Byte 4 Set 2*/
		alarm[660]=Character.getNumericValue(liAlarm8[31]);
		alarm[661]=Character.getNumericValue(liAlarm8[30]);
		alarm[662]=Character.getNumericValue(liAlarm8[29]);
		alarm[663]=Character.getNumericValue(liAlarm8[28]);
		/*Li9 Byte 1 Set 1*/

		alarm[664]=Character.getNumericValue(liAlarm9[3]);
		alarm[665]=Character.getNumericValue(liAlarm9[2]);
		alarm[666]=Character.getNumericValue(liAlarm9[1]);
		alarm[667]=Character.getNumericValue(liAlarm9[0]);
		/*Li9 Byte 1 Set 2*/

		alarm[668]=Character.getNumericValue(liAlarm9[7]);
		alarm[669]=Character.getNumericValue(liAlarm9[6]);
		alarm[670]=Character.getNumericValue(liAlarm9[5]);
		alarm[671]=Character.getNumericValue(liAlarm9[4]);
		/*Li9 Byte 2 Set 1*/

		alarm[672]=Character.getNumericValue(liAlarm9[11]);
		alarm[673]=Character.getNumericValue(liAlarm9[10]);
		alarm[674]=Character.getNumericValue(liAlarm9[9]);
		alarm[675]=Character.getNumericValue(liAlarm9[8]);
		/*Li9 Byte 2 Set 2*/

		alarm[676]=Character.getNumericValue(liAlarm9[15]);
		alarm[677]=Character.getNumericValue(liAlarm9[14]);
		alarm[678]=Character.getNumericValue(liAlarm9[13]);
		alarm[679]=Character.getNumericValue(liAlarm9[12]);
		/*Li9 Byte 3 Set 1*/

		alarm[680]=Character.getNumericValue(liAlarm9[19]);
		alarm[681]=Character.getNumericValue(liAlarm9[18]);
		alarm[682]=Character.getNumericValue(liAlarm9[17]);
		alarm[683]=Character.getNumericValue(liAlarm9[16]);
		/*Li9 Byte 3 Set 2*/

		alarm[684]=Character.getNumericValue(liAlarm9[23]);
		alarm[685]=Character.getNumericValue(liAlarm9[22]);
		alarm[686]=Character.getNumericValue(liAlarm9[21]);
		alarm[687]=Character.getNumericValue(liAlarm9[20]);
		/*Li9 Byte 4 Set 1*/

		alarm[688]=Character.getNumericValue(liAlarm9[27]);
		alarm[689]=Character.getNumericValue(liAlarm9[26]);
		alarm[690]=Character.getNumericValue(liAlarm9[25]);
		alarm[691]=Character.getNumericValue(liAlarm9[24]);
		/*Li9 Byte 4 Set 2*/
		alarm[692]=Character.getNumericValue(liAlarm9[31]);
		alarm[693]=Character.getNumericValue(liAlarm9[30]);
		alarm[694]=Character.getNumericValue(liAlarm9[29]);
		alarm[695]=Character.getNumericValue(liAlarm9[28]);
		/*Li10 Byte 1 Set 1*/

		alarm[696]=Character.getNumericValue(liAlarm10[3]);
		alarm[697]=Character.getNumericValue(liAlarm10[2]);
		alarm[698]=Character.getNumericValue(liAlarm10[1]);
		alarm[699]=Character.getNumericValue(liAlarm10[0]);
		/*Li10 Byte 1 Set 2*/

		alarm[700]=Character.getNumericValue(liAlarm10[7]);
		alarm[701]=Character.getNumericValue(liAlarm10[6]);
		alarm[702]=Character.getNumericValue(liAlarm10[5]);
		alarm[703]=Character.getNumericValue(liAlarm10[4]);
		/*Li10 Byte 2 Set 1*/

		alarm[704]=Character.getNumericValue(liAlarm10[11]);
		alarm[705]=Character.getNumericValue(liAlarm10[10]);
		alarm[706]=Character.getNumericValue(liAlarm10[9]);
		alarm[707]=Character.getNumericValue(liAlarm10[8]);
		/*Li10 Byte 2 Set 2*/

		alarm[708]=Character.getNumericValue(liAlarm10[15]);
		alarm[709]=Character.getNumericValue(liAlarm10[14]);
		alarm[710]=Character.getNumericValue(liAlarm10[13]);
		alarm[711]=Character.getNumericValue(liAlarm10[12]);
		/*Li10 Byte 3 Set 1*/

		alarm[712]=Character.getNumericValue(liAlarm10[19]);
		alarm[713]=Character.getNumericValue(liAlarm10[18]);
		alarm[714]=Character.getNumericValue(liAlarm10[17]);
		alarm[715]=Character.getNumericValue(liAlarm10[16]);
		/*Li10 Byte 3 Set 2*/

		alarm[716]=Character.getNumericValue(liAlarm10[23]);
		alarm[717]=Character.getNumericValue(liAlarm10[22]);
		alarm[718]=Character.getNumericValue(liAlarm10[21]);
		alarm[719]=Character.getNumericValue(liAlarm10[20]);
		/*Li10 Byte 4 Set 1*/

		alarm[720]=Character.getNumericValue(liAlarm10[27]);
		alarm[721]=Character.getNumericValue(liAlarm10[26]);
		alarm[722]=Character.getNumericValue(liAlarm10[25]);
		alarm[723]=Character.getNumericValue(liAlarm10[24]);
		/*Li10 Byte 4 Set 2*/
		alarm[724]=Character.getNumericValue(liAlarm10[31]);
		alarm[725]=Character.getNumericValue(liAlarm10[30]);
		alarm[726]=Character.getNumericValue(liAlarm10[29]);
		alarm[727]=Character.getNumericValue(liAlarm10[28]);
		/*Li11 Byte 1 Set 1*/

		alarm[728]=Character.getNumericValue(liAlarm11[3]);
		alarm[729]=Character.getNumericValue(liAlarm11[2]);
		alarm[730]=Character.getNumericValue(liAlarm11[1]);
		alarm[731]=Character.getNumericValue(liAlarm11[0]);
		/*Li11 Byte 1 Set 2*/

		alarm[732]=Character.getNumericValue(liAlarm11[7]);
		alarm[733]=Character.getNumericValue(liAlarm11[6]);
		alarm[734]=Character.getNumericValue(liAlarm11[5]);
		alarm[735]=Character.getNumericValue(liAlarm11[4]);
		/*Li11 Byte 2 Set 1*/

		alarm[736]=Character.getNumericValue(liAlarm11[11]);
		alarm[737]=Character.getNumericValue(liAlarm11[10]);
		alarm[738]=Character.getNumericValue(liAlarm11[9]);
		alarm[739]=Character.getNumericValue(liAlarm11[8]);
		/*Li11 Byte 2 Set 2*/

		alarm[740]=Character.getNumericValue(liAlarm11[15]);
		alarm[741]=Character.getNumericValue(liAlarm11[14]);
		alarm[742]=Character.getNumericValue(liAlarm11[13]);
		alarm[743]=Character.getNumericValue(liAlarm11[12]);
		/*Li11 Byte 3 Set 1*/

		alarm[744]=Character.getNumericValue(liAlarm11[19]);
		alarm[745]=Character.getNumericValue(liAlarm11[18]);
		alarm[746]=Character.getNumericValue(liAlarm11[17]);
		alarm[747]=Character.getNumericValue(liAlarm11[16]);
		/*Li11 Byte 3 Set 2*/

		alarm[748]=Character.getNumericValue(liAlarm11[23]);
		alarm[749]=Character.getNumericValue(liAlarm11[22]);
		alarm[750]=Character.getNumericValue(liAlarm11[21]);
		alarm[751]=Character.getNumericValue(liAlarm11[20]);
		/*Li11 Byte 4 Set 1*/

		alarm[752]=Character.getNumericValue(liAlarm11[27]);
		alarm[753]=Character.getNumericValue(liAlarm11[26]);
		alarm[754]=Character.getNumericValue(liAlarm11[25]);
		alarm[755]=Character.getNumericValue(liAlarm11[24]);
		/*Li11 Byte 4 Set 2*/
		alarm[756]=Character.getNumericValue(liAlarm11[31]);
		alarm[757]=Character.getNumericValue(liAlarm11[30]);
		alarm[758]=Character.getNumericValue(liAlarm11[29]);
		alarm[759]=Character.getNumericValue(liAlarm11[28]);
		/*Li12 Byte 1 Set 1*/

		alarm[760]=Character.getNumericValue(liAlarm12[3]);
		alarm[761]=Character.getNumericValue(liAlarm12[2]);
		alarm[762]=Character.getNumericValue(liAlarm12[1]);
		alarm[763]=Character.getNumericValue(liAlarm12[0]);
		/*Li12 Byte 1 Set 2*/

		alarm[764]=Character.getNumericValue(liAlarm12[7]);
		alarm[765]=Character.getNumericValue(liAlarm12[6]);
		alarm[766]=Character.getNumericValue(liAlarm12[5]);
		alarm[767]=Character.getNumericValue(liAlarm12[4]);
		/*Li12 Byte 2 Set 1*/

		alarm[768]=Character.getNumericValue(liAlarm12[11]);
		alarm[769]=Character.getNumericValue(liAlarm12[10]);
		alarm[770]=Character.getNumericValue(liAlarm12[9]);
		alarm[771]=Character.getNumericValue(liAlarm12[8]);
		/*Li12 Byte 2 Set 2*/

		alarm[772]=Character.getNumericValue(liAlarm12[15]);
		alarm[773]=Character.getNumericValue(liAlarm12[14]);
		alarm[774]=Character.getNumericValue(liAlarm12[13]);
		alarm[775]=Character.getNumericValue(liAlarm12[12]);
		/*Li12 Byte 3 Set 1*/

		alarm[776]=Character.getNumericValue(liAlarm12[19]);
		alarm[777]=Character.getNumericValue(liAlarm12[18]);
		alarm[778]=Character.getNumericValue(liAlarm12[17]);
		alarm[779]=Character.getNumericValue(liAlarm12[16]);
		/*Li12 Byte 3 Set 2*/

		alarm[780]=Character.getNumericValue(liAlarm12[23]);
		alarm[781]=Character.getNumericValue(liAlarm12[22]);
		alarm[782]=Character.getNumericValue(liAlarm12[21]);
		alarm[783]=Character.getNumericValue(liAlarm12[20]);
		/*Li12 Byte 4 Set 1*/

		alarm[784]=Character.getNumericValue(liAlarm12[27]);
		alarm[785]=Character.getNumericValue(liAlarm12[26]);
		alarm[786]=Character.getNumericValue(liAlarm12[25]);
		alarm[787]=Character.getNumericValue(liAlarm12[24]);
		/*Li12 Byte 4 Set 2*/

		alarm[788]=Character.getNumericValue(liAlarm12[31]);
		alarm[789]=Character.getNumericValue(liAlarm12[30]);
		alarm[790]=Character.getNumericValue(liAlarm12[29]);
		alarm[791]=Character.getNumericValue(liAlarm12[28]);
		/*Li13 Byte 1 Set 1*/

		alarm[792]=Character.getNumericValue(liAlarm13[3]);
		alarm[793]=Character.getNumericValue(liAlarm13[2]);
		alarm[794]=Character.getNumericValue(liAlarm13[1]);
		alarm[795]=Character.getNumericValue(liAlarm13[0]);
		/*Li13 Byte 1 Set 2*/

		alarm[796]=Character.getNumericValue(liAlarm13[7]);
		alarm[797]=Character.getNumericValue(liAlarm13[6]);
		alarm[798]=Character.getNumericValue(liAlarm13[5]);
		alarm[799]=Character.getNumericValue(liAlarm13[4]);
		/*Li13 Byte 2 Set 1*/

		alarm[800]=Character.getNumericValue(liAlarm13[11]);
		alarm[801]=Character.getNumericValue(liAlarm13[10]);
		alarm[802]=Character.getNumericValue(liAlarm13[9]);
		alarm[803]=Character.getNumericValue(liAlarm13[8]);
		/*Li13 Byte 2 Set 2*/

		alarm[804]=Character.getNumericValue(liAlarm13[15]);
		alarm[805]=Character.getNumericValue(liAlarm13[14]);
		alarm[806]=Character.getNumericValue(liAlarm13[13]);
		alarm[807]=Character.getNumericValue(liAlarm13[12]);
		/*Li13 Byte 3 Set 1*/

		alarm[808]=Character.getNumericValue(liAlarm13[19]);
		alarm[809]=Character.getNumericValue(liAlarm13[18]);
		alarm[810]=Character.getNumericValue(liAlarm13[17]);
		alarm[811]=Character.getNumericValue(liAlarm13[16]);
		/*Li13 Byte 3 Set 2*/

		alarm[812]=Character.getNumericValue(liAlarm13[23]);
		alarm[813]=Character.getNumericValue(liAlarm13[22]);
		alarm[814]=Character.getNumericValue(liAlarm13[21]);
		alarm[815]=Character.getNumericValue(liAlarm13[20]);
		/*Li13 Byte 4 Set 1*/

		alarm[816]=Character.getNumericValue(liAlarm13[27]);
		alarm[817]=Character.getNumericValue(liAlarm13[26]);
		alarm[818]=Character.getNumericValue(liAlarm13[25]);
		alarm[819]=Character.getNumericValue(liAlarm13[24]);
		/*Li13 Byte 4 Set 2*/
		alarm[820]=Character.getNumericValue(liAlarm13[31]);
		alarm[821]=Character.getNumericValue(liAlarm13[30]);
		alarm[822]=Character.getNumericValue(liAlarm13[29]);
		alarm[823]=Character.getNumericValue(liAlarm13[28]);
		/*Li14 Byte 1 Set 1*/

		alarm[824]=Character.getNumericValue(liAlarm14[3]);
		alarm[825]=Character.getNumericValue(liAlarm14[2]);
		alarm[826]=Character.getNumericValue(liAlarm14[1]);
		alarm[827]=Character.getNumericValue(liAlarm14[0]);
		/*Li14 Byte 1 Set 2*/

		alarm[828]=Character.getNumericValue(liAlarm14[7]);
		alarm[829]=Character.getNumericValue(liAlarm14[6]);
		alarm[830]=Character.getNumericValue(liAlarm14[5]);
		alarm[831]=Character.getNumericValue(liAlarm14[4]);
		/*Li14 Byte 2 Set 1*/

		alarm[832]=Character.getNumericValue(liAlarm14[11]);
		alarm[833]=Character.getNumericValue(liAlarm14[10]);
		alarm[834]=Character.getNumericValue(liAlarm14[9]);
		alarm[835]=Character.getNumericValue(liAlarm14[8]);
		/*Li14 Byte 2 Set 2*/

		alarm[836]=Character.getNumericValue(liAlarm14[15]);
		alarm[837]=Character.getNumericValue(liAlarm14[14]);
		alarm[838]=Character.getNumericValue(liAlarm14[13]);
		alarm[839]=Character.getNumericValue(liAlarm14[12]);
		/*Li14 Byte 3 Set 1*/

		alarm[840]=Character.getNumericValue(liAlarm14[19]);
		alarm[841]=Character.getNumericValue(liAlarm14[18]);
		alarm[842]=Character.getNumericValue(liAlarm14[17]);
		alarm[843]=Character.getNumericValue(liAlarm14[16]);
		/*Li14 Byte 3 Set 2*/

		alarm[844]=Character.getNumericValue(liAlarm14[23]);
		alarm[845]=Character.getNumericValue(liAlarm14[22]);
		alarm[846]=Character.getNumericValue(liAlarm14[21]);
		alarm[847]=Character.getNumericValue(liAlarm14[20]);
		/*Li14 Byte 4 Set 1*/

		alarm[848]=Character.getNumericValue(liAlarm14[27]);
		alarm[849]=Character.getNumericValue(liAlarm14[26]);
		alarm[850]=Character.getNumericValue(liAlarm14[25]);
		alarm[851]=Character.getNumericValue(liAlarm14[24]);
		/*Li14 Byte 4 Set 2*/
		alarm[852]=Character.getNumericValue(liAlarm14[31]);
		alarm[853]=Character.getNumericValue(liAlarm14[30]);
		alarm[854]=Character.getNumericValue(liAlarm14[29]);
		alarm[855]=Character.getNumericValue(liAlarm14[28]);
		/*Li15 Byte 1 Set 1*/

		alarm[856]=Character.getNumericValue(liAlarm15[3]);
		alarm[857]=Character.getNumericValue(liAlarm15[2]);
		alarm[858]=Character.getNumericValue(liAlarm15[1]);
		alarm[859]=Character.getNumericValue(liAlarm15[0]);
		/*Li15 Byte 1 Set 2*/

		alarm[860]=Character.getNumericValue(liAlarm15[7]);
		alarm[861]=Character.getNumericValue(liAlarm15[6]);
		alarm[862]=Character.getNumericValue(liAlarm15[5]);
		alarm[863]=Character.getNumericValue(liAlarm15[4]);
		/*Li15 Byte 2 Set 1*/

		alarm[864]=Character.getNumericValue(liAlarm15[11]);
		alarm[865]=Character.getNumericValue(liAlarm15[10]);
		alarm[866]=Character.getNumericValue(liAlarm15[9]);
		alarm[867]=Character.getNumericValue(liAlarm15[8]);
		/*Li15 Byte 2 Set 2*/

		alarm[868]=Character.getNumericValue(liAlarm15[15]);
		alarm[869]=Character.getNumericValue(liAlarm15[14]);
		alarm[870]=Character.getNumericValue(liAlarm15[13]);
		alarm[871]=Character.getNumericValue(liAlarm15[12]);
		/*Li15 Byte 3 Set 1*/

		alarm[872]=Character.getNumericValue(liAlarm15[19]);
		alarm[873]=Character.getNumericValue(liAlarm15[18]);
		alarm[874]=Character.getNumericValue(liAlarm15[17]);
		alarm[875]=Character.getNumericValue(liAlarm15[16]);
		/*Li15 Byte 3 Set 2*/

		alarm[876]=Character.getNumericValue(liAlarm15[23]);
		alarm[877]=Character.getNumericValue(liAlarm15[22]);
		alarm[878]=Character.getNumericValue(liAlarm15[21]);
		alarm[879]=Character.getNumericValue(liAlarm15[20]);
		/*Li15 Byte 4 Set 1*/

		alarm[880]=Character.getNumericValue(liAlarm15[27]);
		alarm[881]=Character.getNumericValue(liAlarm15[26]);
		alarm[882]=Character.getNumericValue(liAlarm15[25]);
		alarm[883]=Character.getNumericValue(liAlarm15[24]);
		/*Li15 Byte 4 Set 2*/
		alarm[884]=Character.getNumericValue(liAlarm15[31]);
		alarm[885]=Character.getNumericValue(liAlarm15[30]);
		alarm[886]=Character.getNumericValue(liAlarm15[29]);
		alarm[887]=Character.getNumericValue(liAlarm15[28]);
		/*Li16 Byte 1 Set 1*/

		alarm[888]=Character.getNumericValue(liAlarm16[3]);
		alarm[889]=Character.getNumericValue(liAlarm16[2]);
		alarm[890]=Character.getNumericValue(liAlarm16[1]);
		alarm[891]=Character.getNumericValue(liAlarm16[0]);
		/*Li16 Byte 1 Set 2*/

		alarm[892]=Character.getNumericValue(liAlarm16[7]);
		alarm[893]=Character.getNumericValue(liAlarm16[6]);
		alarm[894]=Character.getNumericValue(liAlarm16[5]);
		alarm[895]=Character.getNumericValue(liAlarm16[4]);
		/*Li16 Byte 2 Set 1*/

		alarm[896]=Character.getNumericValue(liAlarm16[11]);
		alarm[897]=Character.getNumericValue(liAlarm16[10]);
		alarm[898]=Character.getNumericValue(liAlarm16[9]);
		alarm[899]=Character.getNumericValue(liAlarm16[8]);
		/*Li16 Byte 2 Set 2*/

		alarm[900]=Character.getNumericValue(liAlarm16[15]);
		alarm[901]=Character.getNumericValue(liAlarm16[14]);
		alarm[902]=Character.getNumericValue(liAlarm16[13]);
		alarm[903]=Character.getNumericValue(liAlarm16[12]);
		/*Li16 Byte 3 Set 1*/

		alarm[904]=Character.getNumericValue(liAlarm16[19]);
		alarm[905]=Character.getNumericValue(liAlarm16[18]);
		alarm[906]=Character.getNumericValue(liAlarm16[17]);
		alarm[907]=Character.getNumericValue(liAlarm16[16]);
		/*Li16 Byte 3 Set 2*/

		alarm[908]=Character.getNumericValue(liAlarm16[23]);
		alarm[909]=Character.getNumericValue(liAlarm16[22]);
		alarm[910]=Character.getNumericValue(liAlarm16[21]);
		alarm[911]=Character.getNumericValue(liAlarm16[20]);
		/*Li16 Byte 4 Set 1*/

		alarm[912]=Character.getNumericValue(liAlarm16[27]);
		alarm[913]=Character.getNumericValue(liAlarm16[26]);
		alarm[914]=Character.getNumericValue(liAlarm16[25]);
		alarm[915]=Character.getNumericValue(liAlarm16[24]);
		/*Li16 Byte 4 Set 2*/
		alarm[916]=Character.getNumericValue(liAlarm16[31]);
		alarm[917]=Character.getNumericValue(liAlarm16[30]);
		alarm[918]=Character.getNumericValue(liAlarm16[29]);
		alarm[919]=Character.getNumericValue(liAlarm16[28]);
		/*Li17 Byte 1 Set 1*/

		alarm[920]=Character.getNumericValue(liAlarm17[3]);
		alarm[921]=Character.getNumericValue(liAlarm17[2]);
		alarm[922]=Character.getNumericValue(liAlarm17[1]);
		alarm[923]=Character.getNumericValue(liAlarm17[0]);
		/*Li17 Byte 1 Set 2*/

		alarm[924]=Character.getNumericValue(liAlarm17[7]);
		alarm[925]=Character.getNumericValue(liAlarm17[6]);
		alarm[926]=Character.getNumericValue(liAlarm17[5]);
		alarm[927]=Character.getNumericValue(liAlarm17[4]);
		/*Li17 Byte 2 Set 1*/

		alarm[928]=Character.getNumericValue(liAlarm17[11]);
		alarm[929]=Character.getNumericValue(liAlarm17[10]);
		alarm[930]=Character.getNumericValue(liAlarm17[9]);
		alarm[931]=Character.getNumericValue(liAlarm17[8]);
		/*Li17 Byte 2 Set 2*/

		alarm[932]=Character.getNumericValue(liAlarm17[15]);
		alarm[933]=Character.getNumericValue(liAlarm17[14]);
		alarm[934]=Character.getNumericValue(liAlarm17[13]);
		alarm[935]=Character.getNumericValue(liAlarm17[12]);
		/*Li17 Byte 3 Set 1*/

		alarm[936]=Character.getNumericValue(liAlarm17[19]);
		alarm[937]=Character.getNumericValue(liAlarm17[18]);
		alarm[938]=Character.getNumericValue(liAlarm17[17]);
		alarm[939]=Character.getNumericValue(liAlarm17[16]);
		/*Li17 Byte 3 Set 2*/

		alarm[940]=Character.getNumericValue(liAlarm17[23]);
		alarm[941]=Character.getNumericValue(liAlarm17[22]);
		alarm[942]=Character.getNumericValue(liAlarm17[21]);
		alarm[943]=Character.getNumericValue(liAlarm17[20]);
		/*Li17 Byte 4 Set 1*/

		alarm[944]=Character.getNumericValue(liAlarm17[27]);
		alarm[945]=Character.getNumericValue(liAlarm17[26]);
		alarm[946]=Character.getNumericValue(liAlarm17[25]);
		alarm[947]=Character.getNumericValue(liAlarm17[24]);
		/*Li17 Byte 4 Set 2*/
		alarm[948]=Character.getNumericValue(liAlarm17[31]);
		alarm[949]=Character.getNumericValue(liAlarm17[30]);
		alarm[950]=Character.getNumericValue(liAlarm17[29]);
		alarm[951]=Character.getNumericValue(liAlarm17[28]);
		/*Li18 Byte 1 Set 1*/

		alarm[952]=Character.getNumericValue(liAlarm18[3]);
		alarm[953]=Character.getNumericValue(liAlarm18[2]);
		alarm[954]=Character.getNumericValue(liAlarm18[1]);
		alarm[955]=Character.getNumericValue(liAlarm18[0]);
		/*Li18 Byte 1 Set 2*/

		alarm[956]=Character.getNumericValue(liAlarm18[7]);
		alarm[957]=Character.getNumericValue(liAlarm18[6]);
		alarm[958]=Character.getNumericValue(liAlarm18[5]);
		alarm[959]=Character.getNumericValue(liAlarm18[4]);
		/*Li18 Byte 2 Set 1*/

		alarm[960]=Character.getNumericValue(liAlarm18[11]);
		alarm[961]=Character.getNumericValue(liAlarm18[10]);
		alarm[962]=Character.getNumericValue(liAlarm18[9]);
		alarm[963]=Character.getNumericValue(liAlarm18[8]);
		/*Li18 Byte 2 Set 2*/

		alarm[964]=Character.getNumericValue(liAlarm18[15]);
		alarm[965]=Character.getNumericValue(liAlarm18[14]);
		alarm[966]=Character.getNumericValue(liAlarm18[13]);
		alarm[967]=Character.getNumericValue(liAlarm18[12]);
		/*Li18 Byte 3 Set 1*/

		alarm[968]=Character.getNumericValue(liAlarm18[19]);
		alarm[969]=Character.getNumericValue(liAlarm18[18]);
		alarm[970]=Character.getNumericValue(liAlarm18[17]);
		alarm[971]=Character.getNumericValue(liAlarm18[16]);
		/*Li18 Byte 3 Set 2*/

		alarm[972]=Character.getNumericValue(liAlarm18[23]);
		alarm[973]=Character.getNumericValue(liAlarm18[22]);
		alarm[974]=Character.getNumericValue(liAlarm18[21]);
		alarm[975]=Character.getNumericValue(liAlarm18[20]);
		/*Li18 Byte 4 Set 1*/

		alarm[976]=Character.getNumericValue(liAlarm18[27]);
		alarm[977]=Character.getNumericValue(liAlarm18[26]);
		alarm[978]=Character.getNumericValue(liAlarm18[25]);
		alarm[979]=Character.getNumericValue(liAlarm18[24]);
		/*Li18 Byte 4 Set 2*/
		alarm[980]=Character.getNumericValue(liAlarm18[31]);
		alarm[981]=Character.getNumericValue(liAlarm18[30]);
		alarm[982]=Character.getNumericValue(liAlarm18[29]);
		alarm[983]=Character.getNumericValue(liAlarm18[28]);
		/*Li19 Byte 1 Set 1*/

		alarm[984]=Character.getNumericValue(liAlarm19[3]);
		alarm[985]=Character.getNumericValue(liAlarm19[2]);
		alarm[986]=Character.getNumericValue(liAlarm19[1]);
		alarm[987]=Character.getNumericValue(liAlarm19[0]);
		/*Li19 Byte 1 Set 2*/

		alarm[988]=Character.getNumericValue(liAlarm19[7]);
		alarm[989]=Character.getNumericValue(liAlarm19[6]);
		alarm[990]=Character.getNumericValue(liAlarm19[5]);
		alarm[991]=Character.getNumericValue(liAlarm19[4]);
		/*Li19 Byte 2 Set 1*/

		alarm[992]=Character.getNumericValue(liAlarm19[11]);
		alarm[993]=Character.getNumericValue(liAlarm19[10]);
		alarm[994]=Character.getNumericValue(liAlarm19[9]);
		alarm[995]=Character.getNumericValue(liAlarm19[8]);
		/*Li19 Byte 2 Set 2*/

		alarm[996]=Character.getNumericValue(liAlarm19[15]);
		alarm[997]=Character.getNumericValue(liAlarm19[14]);
		alarm[998]=Character.getNumericValue(liAlarm19[13]);
		alarm[999]=Character.getNumericValue(liAlarm19[12]);
		/*Li19 Byte 3 Set 1*/

		alarm[1000]=Character.getNumericValue(liAlarm19[19]);
		alarm[1001]=Character.getNumericValue(liAlarm19[18]);
		alarm[1002]=Character.getNumericValue(liAlarm19[17]);
		alarm[1003]=Character.getNumericValue(liAlarm19[16]);
		/*Li19 Byte 3 Set 2*/

		alarm[1004]=Character.getNumericValue(liAlarm19[23]);
		alarm[1005]=Character.getNumericValue(liAlarm19[22]);
		alarm[1006]=Character.getNumericValue(liAlarm19[21]);
		alarm[1007]=Character.getNumericValue(liAlarm19[20]);
		/*Li19 Byte 4 Set 1*/

		alarm[1008]=Character.getNumericValue(liAlarm19[27]);
		alarm[1009]=Character.getNumericValue(liAlarm19[26]);
		alarm[1010]=Character.getNumericValue(liAlarm19[25]);
		alarm[1011]=Character.getNumericValue(liAlarm19[24]);
		/*Li19 Byte 4 Set 2*/
		alarm[1012]=Character.getNumericValue(liAlarm19[31]);
		alarm[1013]=Character.getNumericValue(liAlarm19[30]);
		alarm[1014]=Character.getNumericValue(liAlarm19[29]);
		alarm[1015]=Character.getNumericValue(liAlarm19[28]);
		/*Li20 Byte 1 Set 1*/

		alarm[1016]=Character.getNumericValue(liAlarm20[3]);
		alarm[1017]=Character.getNumericValue(liAlarm20[2]);
		alarm[1018]=Character.getNumericValue(liAlarm20[1]);
		alarm[1019]=Character.getNumericValue(liAlarm20[0]);
		/*Li20 Byte 1 Set 2*/

		alarm[1020]=Character.getNumericValue(liAlarm20[7]);
		alarm[1021]=Character.getNumericValue(liAlarm20[6]);
		alarm[1022]=Character.getNumericValue(liAlarm20[5]);
		alarm[1023]=Character.getNumericValue(liAlarm20[4]);
		/*Li20 Byte 2 Set 1*/

		alarm[1024]=Character.getNumericValue(liAlarm20[11]);
		alarm[1025]=Character.getNumericValue(liAlarm20[10]);
		alarm[1026]=Character.getNumericValue(liAlarm20[9]);
		alarm[1027]=Character.getNumericValue(liAlarm20[8]);
		/*Li20 Byte 2 Set 2*/

		alarm[1028]=Character.getNumericValue(liAlarm20[15]);
		alarm[1029]=Character.getNumericValue(liAlarm20[14]);
		alarm[1030]=Character.getNumericValue(liAlarm20[13]);
		alarm[1031]=Character.getNumericValue(liAlarm20[12]);
		/*Li20 Byte 3 Set 1*/

		alarm[1032]=Character.getNumericValue(liAlarm20[19]);
		alarm[1033]=Character.getNumericValue(liAlarm20[18]);
		alarm[1034]=Character.getNumericValue(liAlarm20[17]);
		alarm[1035]=Character.getNumericValue(liAlarm20[16]);
		/*Li20 Byte 3 Set 2*/

		alarm[1036]=Character.getNumericValue(liAlarm20[23]);
		alarm[1037]=Character.getNumericValue(liAlarm20[22]);
		alarm[1038]=Character.getNumericValue(liAlarm20[21]);
		alarm[1039]=Character.getNumericValue(liAlarm20[20]);
		/*Li20 Byte 4 Set 1*/

		alarm[1040]=Character.getNumericValue(liAlarm20[27]);
		alarm[1041]=Character.getNumericValue(liAlarm20[26]);
		alarm[1042]=Character.getNumericValue(liAlarm20[25]);
		alarm[1043]=Character.getNumericValue(liAlarm20[24]);
		/*Li20 Byte 4 Set 2*/

		alarm[1044]=Character.getNumericValue(liAlarm20[31]);
		alarm[1045]=Character.getNumericValue(liAlarm20[30]);
		alarm[1046]=Character.getNumericValue(liAlarm20[29]);
		alarm[1047]=Character.getNumericValue(liAlarm20[28]);

		HashMap<String,Integer> alrmList1 = new HashMap<String,Integer>();
		HashMap<String,Integer> alrmList2 = new HashMap<String,Integer>();
		
		HashMap<String,String> alrmList3 = new HashMap<String,String>();
		HashMap<String,Integer> alrmList4 = new HashMap<String,Integer>();
		
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		ConnectionPool jdbcObj = new ConnectionPool();
		try {   
			DataSource dataSource = jdbcObj.setUpPool();
			jdbcObj.printDbStatus();

			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			connObj = dataSource.getConnection();
			jdbcObj.printDbStatus(); 

			pstmtObj = connObj.prepareStatement("Select * from alarmlabel");
			ResultSet rsObj2 = pstmtObj.executeQuery();
			while(rsObj2.next()){
				alrmList1.put(rsObj2.getString("alPinID"), rsObj2.getInt("alIsPinConnected"));
				alrmList2.put(rsObj2.getString("alPinID"), rsObj2.getInt("ttCreationrequired"));
				alrmList3.put(rsObj2.getString("alPinID"), rsObj2.getString("alName"));
				alrmList4.put(rsObj2.getString("alPinID"), rsObj2.getInt("alPinCriticality"));
			}
			pstmtObj.close();
			rsObj2.close();

			pstmtObj = connObj.prepareStatement("Select * from trans_alarmlaststatus where smSiteCode "
					+ "='"+siteId+"'");

			ResultSet rsObj = pstmtObj.executeQuery();
			boolean isResult = rsObj.next();
			int [] alarmLastStatus = new int [1048];
			/*
			 * In trans_alarmstatus if no data
			 * insert it first time
			 */
			if(isResult == false) {
				//Query3
				pstmtObj = connObj.prepareStatement("insert into trans_alarmlaststatus(smSiteCode,alsTimestamp,Alarm_1,Alarm_2,Alarm_3,Alarm_4,Alarm_5,Alarm_6,Alarm_7,Alarm_8,Alarm_9,Alarm_10,Alarm_11,Alarm_12,Alarm_13,Alarm_14,Alarm_15,Alarm_16,Alarm_17,Alarm_18,Alarm_19,Alarm_20,Alarm_21,Alarm_22,Alarm_23,Alarm_24,Alarm_25,Alarm_26,Alarm_27,Alarm_28,Alarm_29,Alarm_30,Alarm_31,Alarm_32,Alarm_33,Alarm_34,Alarm_35,Alarm_36,Alarm_37,Alarm_38,Alarm_39,Alarm_40,Alarm_41,Alarm_42,Alarm_43,Alarm_44,Alarm_45,Alarm_46,Alarm_47,Alarm_48,Alarm_49,Alarm_50,Alarm_51,Alarm_52,Alarm_53,Alarm_54,Alarm_55,Alarm_56,Alarm_57,Alarm_58,Alarm_59,Alarm_60,Alarm_61,Alarm_62,Alarm_63,Alarm_64,Alarm_65,Alarm_66,Alarm_67,Alarm_68,Alarm_69,Alarm_70,Alarm_71,Alarm_72,Alarm_73,Alarm_74,Alarm_75,Alarm_76,Alarm_77,Alarm_78,Alarm_79,Alarm_80,Alarm_81,Alarm_82,Alarm_83,Alarm_84,Alarm_85,Alarm_86,Alarm_87,Alarm_88,Alarm_89,Alarm_90,Alarm_91,Alarm_92,Alarm_93,Alarm_94,Alarm_95,Alarm_96,Alarm_97,Alarm_98,Alarm_99,Alarm_100,Alarm_101,Alarm_102,Alarm_103,Alarm_104,Alarm_105,Alarm_106,Alarm_107,Alarm_108,Alarm_109,Alarm_110,Alarm_111,Alarm_112,Alarm_113,Alarm_114,Alarm_115,Alarm_116,Alarm_117,Alarm_118,Alarm_119,Alarm_120,Alarm_121,Alarm_122,Alarm_123,Alarm_124,Alarm_125,Alarm_126,Alarm_127,Alarm_128,Alarm_129,Alarm_130,Alarm_131,Alarm_132,Alarm_133,Alarm_134,Alarm_135,Alarm_136,Alarm_137,Alarm_138,Alarm_139,Alarm_140,Alarm_141,Alarm_142,Alarm_143,Alarm_144,Alarm_145,Alarm_146,Alarm_147,Alarm_148,Alarm_149,Alarm_150,Alarm_151,Alarm_152,Alarm_153,Alarm_154,Alarm_155,Alarm_156,Alarm_157,Alarm_158,Alarm_159,Alarm_160,Alarm_161,Alarm_162,Alarm_163,Alarm_164,Alarm_165,Alarm_166,Alarm_167,Alarm_168,Alarm_169,Alarm_170,Alarm_171,Alarm_172,Alarm_173,Alarm_174,Alarm_175,Alarm_176,Alarm_177,Alarm_178,Alarm_179,Alarm_180,Alarm_181,Alarm_182,Alarm_183,Alarm_184,Alarm_185,Alarm_186,Alarm_187,Alarm_188,Alarm_189,Alarm_190,Alarm_191,Alarm_192,Alarm_193,Alarm_194,Alarm_195,Alarm_196,Alarm_197,Alarm_198,Alarm_199,Alarm_200,Alarm_201,Alarm_202,Alarm_203,Alarm_204,Alarm_205,Alarm_206,Alarm_207,Alarm_208,Alarm_209,Alarm_210,Alarm_211,Alarm_212,Alarm_213,Alarm_214,Alarm_215,Alarm_216,Alarm_217,Alarm_218,Alarm_219,Alarm_220,Alarm_221,Alarm_222,Alarm_223,Alarm_224,Alarm_225,Alarm_226,Alarm_227,Alarm_228,Alarm_229,Alarm_230,Alarm_231,Alarm_232,Alarm_233,Alarm_234,Alarm_235,Alarm_236,Alarm_237,Alarm_238,Alarm_239,Alarm_240,Alarm_241,Alarm_242,Alarm_243,Alarm_244,Alarm_245,Alarm_246,Alarm_247,Alarm_248,Alarm_249,Alarm_250,Alarm_251,Alarm_252,Alarm_253,Alarm_254,Alarm_255,Alarm_256,Alarm_257,Alarm_258,Alarm_259,Alarm_260,Alarm_261,Alarm_262,Alarm_263,Alarm_264,Alarm_265,Alarm_266,Alarm_267,Alarm_268,Alarm_269,Alarm_270,Alarm_271,Alarm_272,Alarm_273,Alarm_274,Alarm_275,Alarm_276,Alarm_277,Alarm_278,Alarm_279,Alarm_280,Alarm_281,Alarm_282,Alarm_283,Alarm_284,Alarm_285,Alarm_286,Alarm_287,Alarm_288,Alarm_289,Alarm_290,Alarm_291,Alarm_292,Alarm_293,Alarm_294,Alarm_295,Alarm_296,Alarm_297,Alarm_298,Alarm_299,Alarm_300,Alarm_301,Alarm_302,Alarm_303,Alarm_304,Alarm_305,Alarm_306,Alarm_307,Alarm_308,Alarm_309,Alarm_310,Alarm_311,Alarm_312,Alarm_313,Alarm_314,Alarm_315,Alarm_316,Alarm_317,Alarm_318,Alarm_319,Alarm_320,Alarm_321,Alarm_322,Alarm_323,Alarm_324,Alarm_325,Alarm_326,Alarm_327,Alarm_328,Alarm_329,Alarm_330,Alarm_331,Alarm_332,Alarm_333,Alarm_334,Alarm_335,Alarm_336,Alarm_337,Alarm_338,Alarm_339,Alarm_340,Alarm_341,Alarm_342,Alarm_343,Alarm_344,Alarm_345,Alarm_346,Alarm_347,Alarm_348,Alarm_349,Alarm_350,Alarm_351,Alarm_352,Alarm_353,Alarm_354,Alarm_355,Alarm_356,Alarm_357,Alarm_358,Alarm_359,Alarm_360,Alarm_361,Alarm_362,Alarm_363,Alarm_364,Alarm_365,Alarm_366,Alarm_367,Alarm_368,Alarm_369,Alarm_370,Alarm_371,Alarm_372,Alarm_373,Alarm_374,Alarm_375,Alarm_376,Alarm_377,Alarm_378,Alarm_379,Alarm_380,Alarm_381,Alarm_382,Alarm_383,Alarm_384,Alarm_385,Alarm_386,Alarm_387,Alarm_388,Alarm_389,Alarm_390,Alarm_391,Alarm_392,Alarm_393,Alarm_394,Alarm_395,Alarm_396,Alarm_397,Alarm_398,Alarm_399,Alarm_400,Alarm_401,Alarm_402,Alarm_403,Alarm_404,Alarm_405,Alarm_406,Alarm_407,Alarm_408,smSiteID,smSitetypeid,DBCreationTimestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmtObj.setString(1,siteId);
				pstmtObj.setLong(2 ,_recordtime);
				pstmtObj.setInt(3,alarm[0]);
				pstmtObj.setInt(4 ,alarm[1]);
				pstmtObj.setInt(5,alarm[2]);
				pstmtObj.setInt(6,alarm[3]);
				pstmtObj.setInt(7,alarm[4]);
				pstmtObj.setInt(8,alarm[5]);
				pstmtObj.setInt(9,alarm[6]);
				pstmtObj.setInt(10,alarm[7]);
				pstmtObj.setInt(11,alarm[8]);
				pstmtObj.setInt(12,alarm[9]);
				pstmtObj.setInt(13,alarm[10]);
				pstmtObj.setInt(14,alarm[11]);
				pstmtObj.setInt(15,alarm[12]);
				pstmtObj.setInt(16,alarm[13]);
				pstmtObj.setInt(17,alarm[14]);
				pstmtObj.setInt(18,alarm[15]);
				pstmtObj.setInt(19,alarm[16]);
				pstmtObj.setInt(20,alarm[17]);
				pstmtObj.setInt(21,alarm[18]);
				pstmtObj.setInt(22,alarm[19]);
				pstmtObj.setInt(23,alarm[20]);
				pstmtObj.setInt(24,alarm[21]);
				pstmtObj.setInt(25,alarm[22]);
				pstmtObj.setInt(26,alarm[23]);
				pstmtObj.setInt(27,alarm[24]);
				pstmtObj.setInt(28,alarm[25]);
				pstmtObj.setInt(29,alarm[26]);
				pstmtObj.setInt(30,alarm[27]);
				pstmtObj.setInt(31,alarm[28]);
				pstmtObj.setInt(32,alarm[29]);
				pstmtObj.setInt(33,alarm[30]);
				pstmtObj.setInt(34,alarm[31]);
				pstmtObj.setInt(35,alarm[32]);
				pstmtObj.setInt(36,alarm[33]);
				pstmtObj.setInt(37,alarm[34]);
				pstmtObj.setInt(38,alarm[35]);
				pstmtObj.setInt(39,alarm[36]);
				pstmtObj.setInt(40,alarm[37]);
				pstmtObj.setInt(41,alarm[38]);
				pstmtObj.setInt(42,alarm[39]);
				pstmtObj.setInt(43,alarm[40]);
				pstmtObj.setInt(44,alarm[41]);
				pstmtObj.setInt(45,alarm[42]);
				pstmtObj.setInt(46,alarm[43]);
				pstmtObj.setInt(47,alarm[44]);
				pstmtObj.setInt(48,alarm[45]);
				pstmtObj.setInt(49,alarm[46]);
				pstmtObj.setInt(50,alarm[47]);
				pstmtObj.setInt(51,alarm[48]);
				pstmtObj.setInt(52,alarm[49]);
				pstmtObj.setInt(53,alarm[50]);
				pstmtObj.setInt(54,alarm[51]);
				pstmtObj.setInt(55,alarm[52]);
				pstmtObj.setInt(56,alarm[53]);
				pstmtObj.setInt(57,alarm[54]);
				pstmtObj.setInt(58,alarm[55]);
				pstmtObj.setInt(59,alarm[56]);
				pstmtObj.setInt(60,alarm[57]);
				pstmtObj.setInt(61,alarm[58]);
				pstmtObj.setInt(62,alarm[59]);
				pstmtObj.setInt(63,alarm[60]);
				pstmtObj.setInt(64,alarm[61]);
				pstmtObj.setInt(65,alarm[62]);
				pstmtObj.setInt(66,alarm[63]);
				pstmtObj.setInt(67,alarm[64]);
				pstmtObj.setInt(68,alarm[65]);
				pstmtObj.setInt(69,alarm[66]);
				pstmtObj.setInt(70,alarm[67]);
				pstmtObj.setInt(71,alarm[68]);
				pstmtObj.setInt(72,alarm[69]);
				pstmtObj.setInt(73,alarm[70]);
				pstmtObj.setInt(74,alarm[71]);
				pstmtObj.setInt(75,alarm[72]);
				pstmtObj.setInt(76,alarm[73]);
				pstmtObj.setInt(77,alarm[74]);
				pstmtObj.setInt(78,alarm[75]);
				pstmtObj.setInt(79,alarm[76]);
				pstmtObj.setInt(80,alarm[77]);
				pstmtObj.setInt(81,alarm[78]);
				pstmtObj.setInt(82,alarm[79]);
				pstmtObj.setInt(83,alarm[80]);
				pstmtObj.setInt(84,alarm[81]);
				pstmtObj.setInt(85,alarm[82]);
				pstmtObj.setInt(86,alarm[83]);
				pstmtObj.setInt(87,alarm[84]);
				pstmtObj.setInt(88,alarm[85]);
				pstmtObj.setInt(89,alarm[86]);
				pstmtObj.setInt(90,alarm[87]);
				pstmtObj.setInt(91,alarm[88]);
				pstmtObj.setInt(92,alarm[89]);
				pstmtObj.setInt(93,alarm[90]);
				pstmtObj.setInt(94,alarm[91]);
				pstmtObj.setInt(95,alarm[92]);
				pstmtObj.setInt(96,alarm[93]);
				pstmtObj.setInt(97,alarm[94]);
				pstmtObj.setInt(98,alarm[95]);
				pstmtObj.setInt(99, alarm[96]);
				pstmtObj.setInt(100, alarm[97]);
				pstmtObj.setInt(101, alarm[98]);
				pstmtObj.setInt(102, alarm[99]);
				pstmtObj.setInt(103, alarm[100]);
				pstmtObj.setInt(104, alarm[101]);
				pstmtObj.setInt(105, alarm[102]);
				pstmtObj.setInt(106, alarm[103]);
				pstmtObj.setInt(107, alarm[104]);
				pstmtObj.setInt(108, alarm[105]);
				pstmtObj.setInt(109, alarm[106]);
				pstmtObj.setInt(110, alarm[107]);
				pstmtObj.setInt(111, alarm[108]);
				pstmtObj.setInt(112, alarm[109]);
				pstmtObj.setInt(113, alarm[110]);
				pstmtObj.setInt(114, alarm[111]);
				pstmtObj.setInt(115, alarm[112]);
				pstmtObj.setInt(116, alarm[113]);
				pstmtObj.setInt(117, alarm[114]);
				pstmtObj.setInt(118, alarm[115]);
				pstmtObj.setInt(119, alarm[116]);
				pstmtObj.setInt(120, alarm[117]);
				pstmtObj.setInt(121, alarm[118]);
				pstmtObj.setInt(122, alarm[119]);
				pstmtObj.setInt(123, alarm[120]);
				pstmtObj.setInt(124, alarm[121]);
				pstmtObj.setInt(125, alarm[122]);
				pstmtObj.setInt(126, alarm[123]);
				pstmtObj.setInt(127, alarm[124]);
				pstmtObj.setInt(128, alarm[125]);
				pstmtObj.setInt(129, alarm[126]);
				pstmtObj.setInt(130, alarm[127]);
				pstmtObj.setInt(131, alarm[128]);
				pstmtObj.setInt(132, alarm[129]);
				pstmtObj.setInt(133, alarm[130]);
				pstmtObj.setInt(134, alarm[131]);
				pstmtObj.setInt(135, alarm[132]);
				pstmtObj.setInt(136, alarm[133]);
				pstmtObj.setInt(137, alarm[134]);
				pstmtObj.setInt(138, alarm[135]);
				pstmtObj.setInt(139, alarm[136]);
				pstmtObj.setInt(140, alarm[137]);
				pstmtObj.setInt(141, alarm[138]);
				pstmtObj.setInt(142, alarm[139]);
				pstmtObj.setInt(143, alarm[140]);
				pstmtObj.setInt(144, alarm[141]);
				pstmtObj.setInt(145, alarm[142]);
				pstmtObj.setInt(146, alarm[143]);
				pstmtObj.setInt(147, alarm[144]);
				pstmtObj.setInt(148, alarm[145]);
				pstmtObj.setInt(149, alarm[146]);
				pstmtObj.setInt(150, alarm[147]);
				pstmtObj.setInt(151, alarm[148]);
				pstmtObj.setInt(152, alarm[149]);
				pstmtObj.setInt(153, alarm[150]);
				pstmtObj.setInt(154, alarm[151]);
				pstmtObj.setInt(155, alarm[152]);
				pstmtObj.setInt(156, alarm[153]);
				pstmtObj.setInt(157, alarm[154]);
				pstmtObj.setInt(158, alarm[155]);
				pstmtObj.setInt(159, alarm[156]);
				pstmtObj.setInt(160, alarm[157]);
				pstmtObj.setInt(161, alarm[158]);
				pstmtObj.setInt(162, alarm[159]);
				pstmtObj.setInt(163, alarm[160]);
				pstmtObj.setInt(164, alarm[161]);
				pstmtObj.setInt(165, alarm[162]);
				pstmtObj.setInt(166, alarm[163]);
				pstmtObj.setInt(167, alarm[164]);
				pstmtObj.setInt(168, alarm[165]);
				pstmtObj.setInt(169, alarm[166]);
				pstmtObj.setInt(170, alarm[167]);
				pstmtObj.setInt(171, alarm[168]);
				pstmtObj.setInt(172, alarm[169]);
				pstmtObj.setInt(173, alarm[170]);
				pstmtObj.setInt(174, alarm[171]);
				pstmtObj.setInt(175, alarm[172]);
				pstmtObj.setInt(176, alarm[173]);
				pstmtObj.setInt(177, alarm[174]);
				pstmtObj.setInt(178, alarm[175]);
				pstmtObj.setInt(179, alarm[176]);
				pstmtObj.setInt(180, alarm[177]);
				pstmtObj.setInt(181, alarm[178]);
				pstmtObj.setInt(182, alarm[179]);
				pstmtObj.setInt(183, alarm[180]);
				pstmtObj.setInt(184, alarm[181]);
				pstmtObj.setInt(185, alarm[182]);
				pstmtObj.setInt(186, alarm[183]);
				pstmtObj.setInt(187, alarm[184]);
				pstmtObj.setInt(188, alarm[185]);
				pstmtObj.setInt(189, alarm[186]);
				pstmtObj.setInt(190, alarm[187]);
				pstmtObj.setInt(191, alarm[188]);
				pstmtObj.setInt(192, alarm[189]);
				pstmtObj.setInt(193, alarm[190]);
				pstmtObj.setInt(194, alarm[191]);
				pstmtObj.setInt(195, alarm[192]);
				pstmtObj.setInt(196, alarm[193]);
				pstmtObj.setInt(197, alarm[194]);
				pstmtObj.setInt(198, alarm[195]);
				pstmtObj.setInt(199, alarm[196]);
				pstmtObj.setInt(200, alarm[197]);
				pstmtObj.setInt(201, alarm[198]);
				pstmtObj.setInt(202, alarm[199]);
				pstmtObj.setInt(203, alarm[200]);
				pstmtObj.setInt(204, alarm[201]);
				pstmtObj.setInt(205, alarm[202]);
				pstmtObj.setInt(206, alarm[203]);
				pstmtObj.setInt(207, alarm[204]);
				pstmtObj.setInt(208, alarm[205]);
				pstmtObj.setInt(209, alarm[206]);
				pstmtObj.setInt(210, alarm[207]);
				pstmtObj.setInt(211, alarm[208]);
				pstmtObj.setInt(212, alarm[209]);
				pstmtObj.setInt(213, alarm[210]);
				pstmtObj.setInt(214, alarm[211]);
				pstmtObj.setInt(215, alarm[212]);
				pstmtObj.setInt(216, alarm[213]);
				pstmtObj.setInt(217, alarm[214]);
				pstmtObj.setInt(218, alarm[215]);
				pstmtObj.setInt(219, alarm[216]);
				pstmtObj.setInt(220, alarm[217]);
				pstmtObj.setInt(221, alarm[218]);
				pstmtObj.setInt(222, alarm[219]);
				pstmtObj.setInt(223, alarm[220]);
				pstmtObj.setInt(224, alarm[221]);
				pstmtObj.setInt(225, alarm[222]);
				pstmtObj.setInt(226, alarm[223]);
				pstmtObj.setInt(227, alarm[224]);
				pstmtObj.setInt(228, alarm[225]);
				pstmtObj.setInt(229, alarm[226]);
				pstmtObj.setInt(230, alarm[227]);
				pstmtObj.setInt(231, alarm[228]);
				pstmtObj.setInt(232, alarm[229]);
				pstmtObj.setInt(233, alarm[230]);
				pstmtObj.setInt(234, alarm[231]);
				pstmtObj.setInt(235, alarm[232]);
				pstmtObj.setInt(236, alarm[233]);
				pstmtObj.setInt(237, alarm[234]);
				pstmtObj.setInt(238, alarm[235]);
				pstmtObj.setInt(239, alarm[236]);
				pstmtObj.setInt(240, alarm[237]);
				pstmtObj.setInt(241, alarm[238]);
				pstmtObj.setInt(242, alarm[239]);
				pstmtObj.setInt(243, alarm[240]);
				pstmtObj.setInt(244, alarm[241]);
				pstmtObj.setInt(245, alarm[242]);
				pstmtObj.setInt(246, alarm[243]);
				pstmtObj.setInt(247, alarm[244]);
				pstmtObj.setInt(248, alarm[245]);
				pstmtObj.setInt(249, alarm[246]);
				pstmtObj.setInt(250, alarm[247]);
				pstmtObj.setInt(251, alarm[248]);
				pstmtObj.setInt(252, alarm[249]);
				pstmtObj.setInt(253, alarm[250]);
				pstmtObj.setInt(254, alarm[251]);
				pstmtObj.setInt(255, alarm[252]);
				pstmtObj.setInt(256, alarm[253]);
				pstmtObj.setInt(257, alarm[254]);
				pstmtObj.setInt(258, alarm[255]);
				pstmtObj.setInt(259, alarm[256]);
				pstmtObj.setInt(260, alarm[257]);
				pstmtObj.setInt(261, alarm[258]);
				pstmtObj.setInt(262, alarm[259]);
				pstmtObj.setInt(263, alarm[260]);
				pstmtObj.setInt(264, alarm[261]);
				pstmtObj.setInt(265, alarm[262]);
				pstmtObj.setInt(266, alarm[263]);
				pstmtObj.setInt(267, alarm[264]);
				pstmtObj.setInt(268, alarm[265]);
				pstmtObj.setInt(269, alarm[266]);
				pstmtObj.setInt(270, alarm[267]);
				pstmtObj.setInt(271, alarm[268]);
				pstmtObj.setInt(272, alarm[269]);
				pstmtObj.setInt(273, alarm[270]);
				pstmtObj.setInt(274, alarm[271]);
				pstmtObj.setInt(275, alarm[272]);
				pstmtObj.setInt(276, alarm[273]);
				pstmtObj.setInt(277, alarm[274]);
				pstmtObj.setInt(278, alarm[275]);
				pstmtObj.setInt(279, alarm[276]);
				pstmtObj.setInt(280, alarm[277]);
				pstmtObj.setInt(281, alarm[278]);
				pstmtObj.setInt(282, alarm[279]);
				pstmtObj.setInt(283, alarm[280]);
				pstmtObj.setInt(284, alarm[281]);
				pstmtObj.setInt(285, alarm[282]);
				pstmtObj.setInt(286, alarm[283]);
				pstmtObj.setInt(287, alarm[284]);
				pstmtObj.setInt(288, alarm[285]);
				pstmtObj.setInt(289, alarm[286]);
				pstmtObj.setInt(290, alarm[287]);
				pstmtObj.setInt(291, alarm[288]);
				pstmtObj.setInt(292, alarm[289]);
				pstmtObj.setInt(293, alarm[290]);
				pstmtObj.setInt(294, alarm[291]);
				pstmtObj.setInt(295, alarm[292]);
				pstmtObj.setInt(296, alarm[293]);
				pstmtObj.setInt(297, alarm[294]);
				pstmtObj.setInt(298, alarm[295]);
				pstmtObj.setInt(299, alarm[296]);
				pstmtObj.setInt(300, alarm[297]);
				pstmtObj.setInt(301, alarm[298]);
				pstmtObj.setInt(302, alarm[299]);
				pstmtObj.setInt(303, alarm[300]);
				pstmtObj.setInt(304, alarm[301]);
				pstmtObj.setInt(305, alarm[302]);
				pstmtObj.setInt(306, alarm[303]);
				pstmtObj.setInt(307, alarm[304]);
				pstmtObj.setInt(308, alarm[305]);
				pstmtObj.setInt(309, alarm[306]);
				pstmtObj.setInt(310, alarm[307]);
				pstmtObj.setInt(311, alarm[308]);
				pstmtObj.setInt(312, alarm[309]);
				pstmtObj.setInt(313, alarm[310]);
				pstmtObj.setInt(314, alarm[311]);
				pstmtObj.setInt(315, alarm[312]);
				pstmtObj.setInt(316, alarm[313]);
				pstmtObj.setInt(317, alarm[314]);
				pstmtObj.setInt(318, alarm[315]);
				pstmtObj.setInt(319, alarm[316]);
				pstmtObj.setInt(320, alarm[317]);
				pstmtObj.setInt(321, alarm[318]);
				pstmtObj.setInt(322, alarm[319]);
				pstmtObj.setInt(323, alarm[320]);
				pstmtObj.setInt(324, alarm[321]);
				pstmtObj.setInt(325, alarm[322]);
				pstmtObj.setInt(326, alarm[323]);
				pstmtObj.setInt(327, alarm[324]);
				pstmtObj.setInt(328, alarm[325]);
				pstmtObj.setInt(329, alarm[326]);
				pstmtObj.setInt(330, alarm[327]);
				pstmtObj.setInt(331, alarm[328]);
				pstmtObj.setInt(332, alarm[329]);
				pstmtObj.setInt(333, alarm[330]);
				pstmtObj.setInt(334, alarm[331]);
				pstmtObj.setInt(335, alarm[332]);
				pstmtObj.setInt(336, alarm[333]);
				pstmtObj.setInt(337, alarm[334]);
				pstmtObj.setInt(338, alarm[335]);
				pstmtObj.setInt(339, alarm[336]);
				pstmtObj.setInt(340, alarm[337]);
				pstmtObj.setInt(341, alarm[338]);
				pstmtObj.setInt(342, alarm[339]);
				pstmtObj.setInt(343, alarm[340]);
				pstmtObj.setInt(344, alarm[341]);
				pstmtObj.setInt(345, alarm[342]);
				pstmtObj.setInt(346, alarm[343]);
				pstmtObj.setInt(347, alarm[344]);
				pstmtObj.setInt(348, alarm[345]);
				pstmtObj.setInt(349, alarm[346]);
				pstmtObj.setInt(350, alarm[347]);
				pstmtObj.setInt(351, alarm[348]);
				pstmtObj.setInt(352, alarm[349]);
				pstmtObj.setInt(353, alarm[350]);
				pstmtObj.setInt(354, alarm[351]);
				pstmtObj.setInt(355, alarm[352]);
				pstmtObj.setInt(356, alarm[353]);
				pstmtObj.setInt(357, alarm[354]);
				pstmtObj.setInt(358, alarm[355]);
				pstmtObj.setInt(359, alarm[356]);
				pstmtObj.setInt(360, alarm[357]);
				pstmtObj.setInt(361, alarm[358]);
				pstmtObj.setInt(362, alarm[359]);
				pstmtObj.setInt(363, alarm[360]);
				pstmtObj.setInt(364, alarm[361]);
				pstmtObj.setInt(365, alarm[362]);
				pstmtObj.setInt(366, alarm[363]);
				pstmtObj.setInt(367, alarm[364]);
				pstmtObj.setInt(368, alarm[365]);
				pstmtObj.setInt(369, alarm[366]);
				pstmtObj.setInt(370, alarm[367]);
				pstmtObj.setInt(371, alarm[368]);
				pstmtObj.setInt(372, alarm[369]);
				pstmtObj.setInt(373, alarm[370]);
				pstmtObj.setInt(374, alarm[371]);
				pstmtObj.setInt(375, alarm[372]);
				pstmtObj.setInt(376, alarm[373]);
				pstmtObj.setInt(377, alarm[374]);
				pstmtObj.setInt(378, alarm[375]);
				pstmtObj.setInt(379, alarm[376]);
				pstmtObj.setInt(380, alarm[377]);
				pstmtObj.setInt(381, alarm[378]);
				pstmtObj.setInt(382, alarm[379]);
				pstmtObj.setInt(383, alarm[380]);
				pstmtObj.setInt(384, alarm[381]);
				pstmtObj.setInt(385, alarm[382]);
				pstmtObj.setInt(386, alarm[383]);
				pstmtObj.setInt(387, alarm[384]);
				pstmtObj.setInt(388, alarm[385]);
				pstmtObj.setInt(389, alarm[386]);
				pstmtObj.setInt(390, alarm[387]);
				pstmtObj.setInt(391, alarm[388]);
				pstmtObj.setInt(392, alarm[389]);
				pstmtObj.setInt(393, alarm[390]);
				pstmtObj.setInt(394, alarm[391]);
				pstmtObj.setInt(395, alarm[392]);
				pstmtObj.setInt(396, alarm[393]);
				pstmtObj.setInt(397, alarm[394]);
				pstmtObj.setInt(398, alarm[395]);
				pstmtObj.setInt(399, alarm[396]);
				pstmtObj.setInt(400, alarm[397]);
				pstmtObj.setInt(401, alarm[398]);
				pstmtObj.setInt(402, alarm[399]);
				pstmtObj.setInt(403, alarm[400]);
				pstmtObj.setInt(404, alarm[401]);
				pstmtObj.setInt(405, alarm[402]);
				pstmtObj.setInt(406, alarm[403]);
				pstmtObj.setInt(407, alarm[404]);
				pstmtObj.setInt(408, alarm[405]);
				pstmtObj.setInt(409, alarm[406]);
				pstmtObj.setInt(410, alarm[407]);
				pstmtObj.setInt(411, smSiteId);
				pstmtObj.setInt(412, smSitetypeid);
				
				pstmtObj.setLong(413,dbCreationTime);
				
				int result  = pstmtObj.executeUpdate();	
				System.out.println(result);


				/* Insert into trans_alarmhistory */
				//Query2
				pstmtObj = connObj.prepareStatement("insert into trans_alarmdatahistory(smSiteCode,alhTimestamp,Alarm_1,Alarm_2,Alarm_3,Alarm_4,Alarm_5,Alarm_6,Alarm_7,Alarm_8,Alarm_9,Alarm_10,Alarm_11,Alarm_12,Alarm_13,Alarm_14,Alarm_15,Alarm_16,Alarm_17,Alarm_18,Alarm_19,Alarm_20,Alarm_21,Alarm_22,Alarm_23,Alarm_24,Alarm_25,Alarm_26,Alarm_27,Alarm_28,Alarm_29,Alarm_30,Alarm_31,Alarm_32,Alarm_33,Alarm_34,Alarm_35,Alarm_36,Alarm_37,Alarm_38,Alarm_39,Alarm_40,Alarm_41,Alarm_42,Alarm_43,Alarm_44,Alarm_45,Alarm_46,Alarm_47,Alarm_48,Alarm_49,Alarm_50,Alarm_51,Alarm_52,Alarm_53,Alarm_54,Alarm_55,Alarm_56,Alarm_57,Alarm_58,Alarm_59,Alarm_60,Alarm_61,Alarm_62,Alarm_63,Alarm_64,Alarm_65,Alarm_66,Alarm_67,Alarm_68,Alarm_69,Alarm_70,Alarm_71,Alarm_72,Alarm_73,Alarm_74,Alarm_75,Alarm_76,Alarm_77,Alarm_78,Alarm_79,Alarm_80,Alarm_81,Alarm_82,Alarm_83,Alarm_84,Alarm_85,Alarm_86,Alarm_87,Alarm_88,Alarm_89,Alarm_90,Alarm_91,Alarm_92,Alarm_93,Alarm_94,Alarm_95,Alarm_96,Alarm_97,Alarm_98,Alarm_99,Alarm_100,Alarm_101,Alarm_102,Alarm_103,Alarm_104,Alarm_105,Alarm_106,Alarm_107,Alarm_108,Alarm_109,Alarm_110,Alarm_111,Alarm_112,Alarm_113,Alarm_114,Alarm_115,Alarm_116,Alarm_117,Alarm_118,Alarm_119,Alarm_120,Alarm_121,Alarm_122,Alarm_123,Alarm_124,Alarm_125,Alarm_126,Alarm_127,Alarm_128,Alarm_129,Alarm_130,Alarm_131,Alarm_132,Alarm_133,Alarm_134,Alarm_135,Alarm_136,Alarm_137,Alarm_138,Alarm_139,Alarm_140,Alarm_141,Alarm_142,Alarm_143,Alarm_144,Alarm_145,Alarm_146,Alarm_147,Alarm_148,Alarm_149,Alarm_150,Alarm_151,Alarm_152,Alarm_153,Alarm_154,Alarm_155,Alarm_156,Alarm_157,Alarm_158,Alarm_159,Alarm_160,Alarm_161,Alarm_162,Alarm_163,Alarm_164,Alarm_165,Alarm_166,Alarm_167,Alarm_168,Alarm_169,Alarm_170,Alarm_171,Alarm_172,Alarm_173,Alarm_174,Alarm_175,Alarm_176,Alarm_177,Alarm_178,Alarm_179,Alarm_180,Alarm_181,Alarm_182,Alarm_183,Alarm_184,Alarm_185,Alarm_186,Alarm_187,Alarm_188,Alarm_189,Alarm_190,Alarm_191,Alarm_192,Alarm_193,Alarm_194,Alarm_195,Alarm_196,Alarm_197,Alarm_198,Alarm_199,Alarm_200,Alarm_201,Alarm_202,Alarm_203,Alarm_204,Alarm_205,Alarm_206,Alarm_207,Alarm_208,Alarm_209,Alarm_210,Alarm_211,Alarm_212,Alarm_213,Alarm_214,Alarm_215,Alarm_216,Alarm_217,Alarm_218,Alarm_219,Alarm_220,Alarm_221,Alarm_222,Alarm_223,Alarm_224,Alarm_225,Alarm_226,Alarm_227,Alarm_228,Alarm_229,Alarm_230,Alarm_231,Alarm_232,Alarm_233,Alarm_234,Alarm_235,Alarm_236,Alarm_237,Alarm_238,Alarm_239,Alarm_240,Alarm_241,Alarm_242,Alarm_243,Alarm_244,Alarm_245,Alarm_246,Alarm_247,Alarm_248,Alarm_249,Alarm_250,Alarm_251,Alarm_252,Alarm_253,Alarm_254,Alarm_255,Alarm_256,Alarm_257,Alarm_258,Alarm_259,Alarm_260,Alarm_261,Alarm_262,Alarm_263,Alarm_264,Alarm_265,Alarm_266,Alarm_267,Alarm_268,Alarm_269,Alarm_270,Alarm_271,Alarm_272,Alarm_273,Alarm_274,Alarm_275,Alarm_276,Alarm_277,Alarm_278,Alarm_279,Alarm_280,Alarm_281,Alarm_282,Alarm_283,Alarm_284,Alarm_285,Alarm_286,Alarm_287,Alarm_288,Alarm_289,Alarm_290,Alarm_291,Alarm_292,Alarm_293,Alarm_294,Alarm_295,Alarm_296,Alarm_297,Alarm_298,Alarm_299,Alarm_300,Alarm_301,Alarm_302,Alarm_303,Alarm_304,Alarm_305,Alarm_306,Alarm_307,Alarm_308,Alarm_309,Alarm_310,Alarm_311,Alarm_312,Alarm_313,Alarm_314,Alarm_315,Alarm_316,Alarm_317,Alarm_318,Alarm_319,Alarm_320,Alarm_321,Alarm_322,Alarm_323,Alarm_324,Alarm_325,Alarm_326,Alarm_327,Alarm_328,Alarm_329,Alarm_330,Alarm_331,Alarm_332,Alarm_333,Alarm_334,Alarm_335,Alarm_336,Alarm_337,Alarm_338,Alarm_339,Alarm_340,Alarm_341,Alarm_342,Alarm_343,Alarm_344,Alarm_345,Alarm_346,Alarm_347,Alarm_348,Alarm_349,Alarm_350,Alarm_351,Alarm_352,Alarm_353,Alarm_354,Alarm_355,Alarm_356,Alarm_357,Alarm_358,Alarm_359,Alarm_360,Alarm_361,Alarm_362,Alarm_363,Alarm_364,Alarm_365,Alarm_366,Alarm_367,Alarm_368,Alarm_369,Alarm_370,Alarm_371,Alarm_372,Alarm_373,Alarm_374,Alarm_375,Alarm_376,Alarm_377,Alarm_378,Alarm_379,Alarm_380,Alarm_381,Alarm_382,Alarm_383,Alarm_384,Alarm_385,Alarm_386,Alarm_387,Alarm_388,Alarm_389,Alarm_390,Alarm_391,Alarm_392,Alarm_393,Alarm_394,Alarm_395,Alarm_396,Alarm_397,Alarm_398,Alarm_399,Alarm_400,Alarm_401,Alarm_402,Alarm_403,Alarm_404,Alarm_405,Alarm_406,Alarm_407,Alarm_408,smSiteID,smSitetypeid,DBCreationTimestamp,hpDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmtObj.setString(1,siteId);
				pstmtObj.setLong(2,_recordtime);
				pstmtObj.setInt(3,alarm[0]);
				pstmtObj.setInt(4,alarm[1]);
				pstmtObj.setInt(5,alarm[2]);
				pstmtObj.setInt(6,alarm[3]);
				pstmtObj.setInt(7,alarm[4]);
				pstmtObj.setInt(8,alarm[5]);
				pstmtObj.setInt(9,alarm[6]);
				pstmtObj.setInt(10,alarm[7]);
				pstmtObj.setInt(11,alarm[8]);
				pstmtObj.setInt(12,alarm[9]);
				pstmtObj.setInt(13,alarm[10]);
				pstmtObj.setInt(14,alarm[11]);
				pstmtObj.setInt(15,alarm[12]);
				pstmtObj.setInt(16,alarm[13]);
				pstmtObj.setInt(17,alarm[14]);
				pstmtObj.setInt(18,alarm[15]);
				pstmtObj.setInt(19,alarm[16]);
				pstmtObj.setInt(20,alarm[17]);
				pstmtObj.setInt(21,alarm[18]);
				pstmtObj.setInt(22,alarm[19]);
				pstmtObj.setInt(23,alarm[20]);
				pstmtObj.setInt(24,alarm[21]);
				pstmtObj.setInt(25,alarm[22]);
				pstmtObj.setInt(26,alarm[23]);
				pstmtObj.setInt(27,alarm[24]);
				pstmtObj.setInt(28,alarm[25]);
				pstmtObj.setInt(29,alarm[26]);
				pstmtObj.setInt(30,alarm[27]);
				pstmtObj.setInt(31,alarm[28]);
				pstmtObj.setInt(32,alarm[29]);
				pstmtObj.setInt(33,alarm[30]);
				pstmtObj.setInt(34,alarm[31]);
				pstmtObj.setInt(35,alarm[32]);
				pstmtObj.setInt(36,alarm[33]);
				pstmtObj.setInt(37,alarm[34]);
				pstmtObj.setInt(38,alarm[35]);
				pstmtObj.setInt(39,alarm[36]);
				pstmtObj.setInt(40,alarm[37]);
				pstmtObj.setInt(41,alarm[38]);
				pstmtObj.setInt(42,alarm[39]);
				pstmtObj.setInt(43,alarm[40]);
				pstmtObj.setInt(44,alarm[41]);
				pstmtObj.setInt(45,alarm[42]);
				pstmtObj.setInt(46,alarm[43]);
				pstmtObj.setInt(47,alarm[44]);
				pstmtObj.setInt(48,alarm[45]);
				pstmtObj.setInt(49,alarm[46]);
				pstmtObj.setInt(50,alarm[47]);
				pstmtObj.setInt(51,alarm[48]);
				pstmtObj.setInt(52,alarm[49]);
				pstmtObj.setInt(53,alarm[50]);
				pstmtObj.setInt(54,alarm[51]);
				pstmtObj.setInt(55,alarm[52]);
				pstmtObj.setInt(56,alarm[53]);
				pstmtObj.setInt(57,alarm[54]);
				pstmtObj.setInt(58,alarm[55]);
				pstmtObj.setInt(59,alarm[56]);
				pstmtObj.setInt(60,alarm[57]);
				pstmtObj.setInt(61,alarm[58]);
				pstmtObj.setInt(62,alarm[59]);
				pstmtObj.setInt(63,alarm[60]);
				pstmtObj.setInt(64,alarm[61]);
				pstmtObj.setInt(65,alarm[62]);
				pstmtObj.setInt(66,alarm[63]);
				pstmtObj.setInt(67,alarm[64]);
				pstmtObj.setInt(68,alarm[65]);
				pstmtObj.setInt(69,alarm[66]);
				pstmtObj.setInt(70,alarm[67]);
				pstmtObj.setInt(71,alarm[68]);
				pstmtObj.setInt(72,alarm[69]);
				pstmtObj.setInt(73,alarm[70]);
				pstmtObj.setInt(74,alarm[71]);
				pstmtObj.setInt(75,alarm[72]);
				pstmtObj.setInt(76,alarm[73]);
				pstmtObj.setInt(77,alarm[74]);
				pstmtObj.setInt(78,alarm[75]);
				pstmtObj.setInt(79,alarm[76]);
				pstmtObj.setInt(80,alarm[77]);
				pstmtObj.setInt(81,alarm[78]);
				pstmtObj.setInt(82,alarm[79]);
				pstmtObj.setInt(83,alarm[80]);
				pstmtObj.setInt(84,alarm[81]);
				pstmtObj.setInt(85,alarm[82]);
				pstmtObj.setInt(86,alarm[83]);
				pstmtObj.setInt(87,alarm[84]);
				pstmtObj.setInt(88,alarm[85]);
				pstmtObj.setInt(89,alarm[86]);
				pstmtObj.setInt(90,alarm[87]);
				pstmtObj.setInt(91,alarm[88]);
				pstmtObj.setInt(92,alarm[89]);
				pstmtObj.setInt(93,alarm[90]);
				pstmtObj.setInt(94,alarm[91]);
				pstmtObj.setInt(95,alarm[92]);
				pstmtObj.setInt(96,alarm[93]);
				pstmtObj.setInt(97,alarm[94]);
				pstmtObj.setInt(98,alarm[95]);
				pstmtObj.setInt(99,alarm[96]);
				pstmtObj.setInt(100,alarm[97]);
				pstmtObj.setInt(101,alarm[98]);
				pstmtObj.setInt(102,alarm[99]);
				pstmtObj.setInt(103,alarm[100]);
				pstmtObj.setInt(104,alarm[101]);
				pstmtObj.setInt(105,alarm[102]);
				pstmtObj.setInt(106,alarm[103]);
				pstmtObj.setInt(107,alarm[104]);
				pstmtObj.setInt(108,alarm[105]);
				pstmtObj.setInt(109,alarm[106]);
				pstmtObj.setInt(110,alarm[107]);
				pstmtObj.setInt(111,alarm[108]);
				pstmtObj.setInt(112,alarm[109]);
				pstmtObj.setInt(113,alarm[110]);
				pstmtObj.setInt(114,alarm[111]);
				pstmtObj.setInt(115,alarm[112]);
				pstmtObj.setInt(116,alarm[113]);
				pstmtObj.setInt(117,alarm[114]);
				pstmtObj.setInt(118,alarm[115]);
				pstmtObj.setInt(119,alarm[116]);
				pstmtObj.setInt(120,alarm[117]);
				pstmtObj.setInt(121,alarm[118]);
				pstmtObj.setInt(122,alarm[119]);
				pstmtObj.setInt(123,alarm[120]);
				pstmtObj.setInt(124,alarm[121]);
				pstmtObj.setInt(125,alarm[122]);
				pstmtObj.setInt(126,alarm[123]);
				pstmtObj.setInt(127,alarm[124]);
				pstmtObj.setInt(128,alarm[125]);
				pstmtObj.setInt(129,alarm[126]);
				pstmtObj.setInt(130,alarm[127]);
				pstmtObj.setInt(131,alarm[128]);
				pstmtObj.setInt(132,alarm[129]);
				pstmtObj.setInt(133,alarm[130]);
				pstmtObj.setInt(134,alarm[131]);
				pstmtObj.setInt(135,alarm[132]);
				pstmtObj.setInt(136,alarm[133]);
				pstmtObj.setInt(137,alarm[134]);
				pstmtObj.setInt(138,alarm[135]);
				pstmtObj.setInt(139,alarm[136]);
				pstmtObj.setInt(140,alarm[137]);
				pstmtObj.setInt(141,alarm[138]);
				pstmtObj.setInt(142,alarm[139]);
				pstmtObj.setInt(143,alarm[140]);
				pstmtObj.setInt(144,alarm[141]);
				pstmtObj.setInt(145,alarm[142]);
				pstmtObj.setInt(146,alarm[143]);
				pstmtObj.setInt(147,alarm[144]);
				pstmtObj.setInt(148,alarm[145]);
				pstmtObj.setInt(149,alarm[146]);
				pstmtObj.setInt(150,alarm[147]);
				pstmtObj.setInt(151,alarm[148]);
				pstmtObj.setInt(152,alarm[149]);
				pstmtObj.setInt(153,alarm[150]);
				pstmtObj.setInt(154,alarm[151]);
				pstmtObj.setInt(155,alarm[152]);
				pstmtObj.setInt(156,alarm[153]);
				pstmtObj.setInt(157,alarm[154]);
				pstmtObj.setInt(158,alarm[155]);
				pstmtObj.setInt(159,alarm[156]);
				pstmtObj.setInt(160,alarm[157]);
				pstmtObj.setInt(161,alarm[158]);
				pstmtObj.setInt(162,alarm[159]);
				pstmtObj.setInt(163,alarm[160]);
				pstmtObj.setInt(164,alarm[161]);
				pstmtObj.setInt(165,alarm[162]);
				pstmtObj.setInt(166,alarm[163]);
				pstmtObj.setInt(167,alarm[164]);
				pstmtObj.setInt(168,alarm[165]);
				pstmtObj.setInt(169,alarm[166]);
				pstmtObj.setInt(170,alarm[167]);
				pstmtObj.setInt(171,alarm[168]);
				pstmtObj.setInt(172,alarm[169]);
				pstmtObj.setInt(173,alarm[170]);
				pstmtObj.setInt(174,alarm[171]);
				pstmtObj.setInt(175,alarm[172]);
				pstmtObj.setInt(176,alarm[173]);
				pstmtObj.setInt(177,alarm[174]);
				pstmtObj.setInt(178,alarm[175]);
				pstmtObj.setInt(179,alarm[176]);
				pstmtObj.setInt(180,alarm[177]);
				pstmtObj.setInt(181,alarm[178]);
				pstmtObj.setInt(182,alarm[179]);
				pstmtObj.setInt(183,alarm[180]);
				pstmtObj.setInt(184,alarm[181]);
				pstmtObj.setInt(185,alarm[182]);
				pstmtObj.setInt(186,alarm[183]);
				pstmtObj.setInt(187,alarm[184]);
				pstmtObj.setInt(188,alarm[185]);
				pstmtObj.setInt(189,alarm[186]);
				pstmtObj.setInt(190,alarm[187]);
				pstmtObj.setInt(191,alarm[188]);
				pstmtObj.setInt(192,alarm[189]);
				pstmtObj.setInt(193,alarm[190]);
				pstmtObj.setInt(194,alarm[191]);
				pstmtObj.setInt(195,alarm[192]);
				pstmtObj.setInt(196,alarm[193]);
				pstmtObj.setInt(197,alarm[194]);
				pstmtObj.setInt(198,alarm[195]);
				pstmtObj.setInt(199,alarm[196]);
				pstmtObj.setInt(200,alarm[197]);
				pstmtObj.setInt(201,alarm[198]);
				pstmtObj.setInt(202,alarm[199]);
				pstmtObj.setInt(203,alarm[200]);
				pstmtObj.setInt(204,alarm[201]);
				pstmtObj.setInt(205,alarm[202]);
				pstmtObj.setInt(206,alarm[203]);
				pstmtObj.setInt(207,alarm[204]);
				pstmtObj.setInt(208,alarm[205]);
				pstmtObj.setInt(209,alarm[206]);
				pstmtObj.setInt(210,alarm[207]);
				pstmtObj.setInt(211,alarm[208]);
				pstmtObj.setInt(212,alarm[209]);
				pstmtObj.setInt(213,alarm[210]);
				pstmtObj.setInt(214,alarm[211]);
				pstmtObj.setInt(215,alarm[212]);
				pstmtObj.setInt(216,alarm[213]);
				pstmtObj.setInt(217,alarm[214]);
				pstmtObj.setInt(218,alarm[215]);
				pstmtObj.setInt(219,alarm[216]);
				pstmtObj.setInt(220,alarm[217]);
				pstmtObj.setInt(221,alarm[218]);
				pstmtObj.setInt(222,alarm[219]);
				pstmtObj.setInt(223,alarm[220]);
				pstmtObj.setInt(224,alarm[221]);
				pstmtObj.setInt(225,alarm[222]);
				pstmtObj.setInt(226,alarm[223]);
				pstmtObj.setInt(227,alarm[224]);
				pstmtObj.setInt(228,alarm[225]);
				pstmtObj.setInt(229,alarm[226]);
				pstmtObj.setInt(230,alarm[227]);
				pstmtObj.setInt(231,alarm[228]);
				pstmtObj.setInt(232,alarm[229]);
				pstmtObj.setInt(233,alarm[230]);
				pstmtObj.setInt(234,alarm[231]);
				pstmtObj.setInt(235,alarm[232]);
				pstmtObj.setInt(236,alarm[233]);
				pstmtObj.setInt(237,alarm[234]);
				pstmtObj.setInt(238,alarm[235]);
				pstmtObj.setInt(239,alarm[236]);
				pstmtObj.setInt(240,alarm[237]);
				pstmtObj.setInt(241,alarm[238]);
				pstmtObj.setInt(242,alarm[239]);
				pstmtObj.setInt(243,alarm[240]);
				pstmtObj.setInt(244,alarm[241]);
				pstmtObj.setInt(245,alarm[242]);
				pstmtObj.setInt(246,alarm[243]);
				pstmtObj.setInt(247,alarm[244]);
				pstmtObj.setInt(248,alarm[245]);
				pstmtObj.setInt(249,alarm[246]);
				pstmtObj.setInt(250,alarm[247]);
				pstmtObj.setInt(251,alarm[248]);
				pstmtObj.setInt(252,alarm[249]);
				pstmtObj.setInt(253,alarm[250]);
				pstmtObj.setInt(254,alarm[251]);
				pstmtObj.setInt(255,alarm[252]);
				pstmtObj.setInt(256,alarm[253]);
				pstmtObj.setInt(257,alarm[254]);
				pstmtObj.setInt(258,alarm[255]);
				pstmtObj.setInt(259,alarm[256]);
				pstmtObj.setInt(260,alarm[257]);
				pstmtObj.setInt(261,alarm[258]);
				pstmtObj.setInt(262,alarm[259]);
				pstmtObj.setInt(263,alarm[260]);
				pstmtObj.setInt(264,alarm[261]);
				pstmtObj.setInt(265,alarm[262]);
				pstmtObj.setInt(266,alarm[263]);
				pstmtObj.setInt(267,alarm[264]);
				pstmtObj.setInt(268,alarm[265]);
				pstmtObj.setInt(269,alarm[266]);
				pstmtObj.setInt(270,alarm[267]);
				pstmtObj.setInt(271,alarm[268]);
				pstmtObj.setInt(272,alarm[269]);
				pstmtObj.setInt(273,alarm[270]);
				pstmtObj.setInt(274,alarm[271]);
				pstmtObj.setInt(275,alarm[272]);
				pstmtObj.setInt(276,alarm[273]);
				pstmtObj.setInt(277,alarm[274]);
				pstmtObj.setInt(278,alarm[275]);
				pstmtObj.setInt(279,alarm[276]);
				pstmtObj.setInt(280,alarm[277]);
				pstmtObj.setInt(281,alarm[278]);
				pstmtObj.setInt(282,alarm[279]);
				pstmtObj.setInt(283,alarm[280]);
				pstmtObj.setInt(284,alarm[281]);
				pstmtObj.setInt(285,alarm[282]);
				pstmtObj.setInt(286,alarm[283]);
				pstmtObj.setInt(287,alarm[284]);
				pstmtObj.setInt(288,alarm[285]);
				pstmtObj.setInt(289,alarm[286]);
				pstmtObj.setInt(290,alarm[287]);
				pstmtObj.setInt(291,alarm[288]);
				pstmtObj.setInt(292,alarm[289]);
				pstmtObj.setInt(293,alarm[290]);
				pstmtObj.setInt(294,alarm[291]);
				pstmtObj.setInt(295,alarm[292]);
				pstmtObj.setInt(296,alarm[293]);
				pstmtObj.setInt(297,alarm[294]);
				pstmtObj.setInt(298,alarm[295]);
				pstmtObj.setInt(299,alarm[296]);
				pstmtObj.setInt(300,alarm[297]);
				pstmtObj.setInt(301,alarm[298]);
				pstmtObj.setInt(302,alarm[299]);
				pstmtObj.setInt(303,alarm[300]);
				pstmtObj.setInt(304,alarm[301]);
				pstmtObj.setInt(305,alarm[302]);
				pstmtObj.setInt(306,alarm[303]);
				pstmtObj.setInt(307,alarm[304]);
				pstmtObj.setInt(308,alarm[305]);
				pstmtObj.setInt(309,alarm[306]);
				pstmtObj.setInt(310,alarm[307]);
				pstmtObj.setInt(311,alarm[308]);
				pstmtObj.setInt(312,alarm[309]);
				pstmtObj.setInt(313,alarm[310]);
				pstmtObj.setInt(314,alarm[311]);
				pstmtObj.setInt(315,alarm[312]);
				pstmtObj.setInt(316,alarm[313]);
				pstmtObj.setInt(317,alarm[314]);
				pstmtObj.setInt(318,alarm[315]);
				pstmtObj.setInt(319,alarm[316]);
				pstmtObj.setInt(320,alarm[317]);
				pstmtObj.setInt(321,alarm[318]);
				pstmtObj.setInt(322,alarm[319]);
				pstmtObj.setInt(323,alarm[320]);
				pstmtObj.setInt(324,alarm[321]);
				pstmtObj.setInt(325,alarm[322]);
				pstmtObj.setInt(326,alarm[323]);
				pstmtObj.setInt(327,alarm[324]);
				pstmtObj.setInt(328,alarm[325]);
				pstmtObj.setInt(329,alarm[326]);
				pstmtObj.setInt(330,alarm[327]);
				pstmtObj.setInt(331,alarm[328]);
				pstmtObj.setInt(332,alarm[329]);
				pstmtObj.setInt(333,alarm[330]);
				pstmtObj.setInt(334,alarm[331]);
				pstmtObj.setInt(335,alarm[332]);
				pstmtObj.setInt(336,alarm[333]);
				pstmtObj.setInt(337,alarm[334]);
				pstmtObj.setInt(338,alarm[335]);
				pstmtObj.setInt(339,alarm[336]);
				pstmtObj.setInt(340,alarm[337]);
				pstmtObj.setInt(341,alarm[338]);
				pstmtObj.setInt(342,alarm[339]);
				pstmtObj.setInt(343,alarm[340]);
				pstmtObj.setInt(344,alarm[341]);
				pstmtObj.setInt(345,alarm[342]);
				pstmtObj.setInt(346,alarm[343]);
				pstmtObj.setInt(347,alarm[344]);
				pstmtObj.setInt(348,alarm[345]);
				pstmtObj.setInt(349,alarm[346]);
				pstmtObj.setInt(350,alarm[347]);
				pstmtObj.setInt(351,alarm[348]);
				pstmtObj.setInt(352,alarm[349]);
				pstmtObj.setInt(353,alarm[350]);
				pstmtObj.setInt(354,alarm[351]);
				pstmtObj.setInt(355,alarm[352]);
				pstmtObj.setInt(356,alarm[353]);
				pstmtObj.setInt(357,alarm[354]);
				pstmtObj.setInt(358,alarm[355]);
				pstmtObj.setInt(359,alarm[356]);
				pstmtObj.setInt(360,alarm[357]);
				pstmtObj.setInt(361,alarm[358]);
				pstmtObj.setInt(362,alarm[359]);
				pstmtObj.setInt(363,alarm[360]);
				pstmtObj.setInt(364,alarm[361]);
				pstmtObj.setInt(365,alarm[362]);
				pstmtObj.setInt(366,alarm[363]);
				pstmtObj.setInt(367,alarm[364]);
				pstmtObj.setInt(368,alarm[365]);
				pstmtObj.setInt(369,alarm[366]);
				pstmtObj.setInt(370,alarm[367]);
				pstmtObj.setInt(371,alarm[368]);
				pstmtObj.setInt(372,alarm[369]);
				pstmtObj.setInt(373,alarm[370]);
				pstmtObj.setInt(374,alarm[371]);
				pstmtObj.setInt(375,alarm[372]);
				pstmtObj.setInt(376,alarm[373]);
				pstmtObj.setInt(377,alarm[374]);
				pstmtObj.setInt(378,alarm[375]);
				pstmtObj.setInt(379,alarm[376]);
				pstmtObj.setInt(380,alarm[377]);
				pstmtObj.setInt(381,alarm[378]);
				pstmtObj.setInt(382,alarm[379]);
				pstmtObj.setInt(383,alarm[380]);
				pstmtObj.setInt(384,alarm[381]);
				pstmtObj.setInt(385,alarm[382]);
				pstmtObj.setInt(386,alarm[383]);
				pstmtObj.setInt(387,alarm[384]);
				pstmtObj.setInt(388,alarm[385]);
				pstmtObj.setInt(389,alarm[386]);
				pstmtObj.setInt(390,alarm[387]);
				pstmtObj.setInt(391,alarm[388]);
				pstmtObj.setInt(392,alarm[389]);
				pstmtObj.setInt(393,alarm[390]);
				pstmtObj.setInt(394,alarm[391]);
				pstmtObj.setInt(395,alarm[392]);
				pstmtObj.setInt(396,alarm[393]);
				pstmtObj.setInt(397,alarm[394]);
				pstmtObj.setInt(398,alarm[395]);
				pstmtObj.setInt(399,alarm[396]);
				pstmtObj.setInt(400,alarm[397]);
				pstmtObj.setInt(401,alarm[398]);
				pstmtObj.setInt(402,alarm[399]);
				pstmtObj.setInt(403,alarm[400]);
				pstmtObj.setInt(404,alarm[401]);
				pstmtObj.setInt(405,alarm[402]);
				pstmtObj.setInt(406,alarm[403]);
				pstmtObj.setInt(407,alarm[404]);
				pstmtObj.setInt(408,alarm[405]);
				pstmtObj.setInt(409,alarm[406]);
				pstmtObj.setInt(410,alarm[407]);
				pstmtObj.setInt(411,smSiteId);
				pstmtObj.setInt(412,smSitetypeid);
				
				pstmtObj.setLong(413,dbCreationTime);
				pstmtObj.setDate(414, hpDate);
				pstmtObj.executeUpdate();	

			} else {
				
				pstmtObj = connObj.prepareStatement("Select * from trans_alarmlaststatus where smSiteCode ='"+siteId+"'");

				ResultSet transResult = pstmtObj.executeQuery();
				while(transResult.next()){

					alarmLastStatus[0] = rsObj.getInt("Alarm_1");
					alarmLastStatus[1] = rsObj.getInt("Alarm_2");
					alarmLastStatus[2] = rsObj.getInt("Alarm_3");
					alarmLastStatus[3] = rsObj.getInt("Alarm_4");
					alarmLastStatus[4] = rsObj.getInt("Alarm_5");
					alarmLastStatus[5] = rsObj.getInt("Alarm_6");
					alarmLastStatus[6] = rsObj.getInt("Alarm_7");
					alarmLastStatus[7] = rsObj.getInt("Alarm_8");
					alarmLastStatus[8] = rsObj.getInt("Alarm_9");
					alarmLastStatus[9] = rsObj.getInt("Alarm_10");
					alarmLastStatus[10] = rsObj.getInt("Alarm_11");
					alarmLastStatus[11] = rsObj.getInt("Alarm_12");
					alarmLastStatus[12] = rsObj.getInt("Alarm_13");
					alarmLastStatus[13] = rsObj.getInt("Alarm_14");
					alarmLastStatus[14] = rsObj.getInt("Alarm_15");
					alarmLastStatus[15] = rsObj.getInt("Alarm_16");
					alarmLastStatus[16] = rsObj.getInt("Alarm_17");
					alarmLastStatus[17] = rsObj.getInt("Alarm_18");
					alarmLastStatus[18] = rsObj.getInt("Alarm_19");
					alarmLastStatus[19] = rsObj.getInt("Alarm_20");
					alarmLastStatus[20] = rsObj.getInt("Alarm_21");
					alarmLastStatus[21] = rsObj.getInt("Alarm_22");
					alarmLastStatus[22] = rsObj.getInt("Alarm_23");
					alarmLastStatus[23] = rsObj.getInt("Alarm_24");
					alarmLastStatus[24] = rsObj.getInt("Alarm_25");
					alarmLastStatus[25] = rsObj.getInt("Alarm_26");
					alarmLastStatus[26] = rsObj.getInt("Alarm_27");
					alarmLastStatus[27] = rsObj.getInt("Alarm_28");
					alarmLastStatus[28] = rsObj.getInt("Alarm_29");
					alarmLastStatus[29] = rsObj.getInt("Alarm_30");
					alarmLastStatus[30] = rsObj.getInt("Alarm_31");
					alarmLastStatus[31] = rsObj.getInt("Alarm_32");
					alarmLastStatus[32] = rsObj.getInt("Alarm_33");
					alarmLastStatus[33] = rsObj.getInt("Alarm_34");
					alarmLastStatus[34] = rsObj.getInt("Alarm_35");
					alarmLastStatus[35] = rsObj.getInt("Alarm_36");
					alarmLastStatus[36] = rsObj.getInt("Alarm_37");
					alarmLastStatus[37] = rsObj.getInt("Alarm_38");
					alarmLastStatus[38] = rsObj.getInt("Alarm_39");
					alarmLastStatus[39] = rsObj.getInt("Alarm_40");
					alarmLastStatus[40] = rsObj.getInt("Alarm_41");
					alarmLastStatus[41] = rsObj.getInt("Alarm_42");
					alarmLastStatus[42] = rsObj.getInt("Alarm_43");
					alarmLastStatus[43] = rsObj.getInt("Alarm_44");
					alarmLastStatus[44] = rsObj.getInt("Alarm_45");
					alarmLastStatus[45] = rsObj.getInt("Alarm_46");
					alarmLastStatus[46] = rsObj.getInt("Alarm_47");
					alarmLastStatus[47] = rsObj.getInt("Alarm_48");
					alarmLastStatus[48] = rsObj.getInt("Alarm_49");
					alarmLastStatus[49] = rsObj.getInt("Alarm_50");
					alarmLastStatus[50] = rsObj.getInt("Alarm_51");
					alarmLastStatus[51] = rsObj.getInt("Alarm_52");
					alarmLastStatus[52] = rsObj.getInt("Alarm_53");
					alarmLastStatus[53] = rsObj.getInt("Alarm_54");
					alarmLastStatus[54] = rsObj.getInt("Alarm_55");
					alarmLastStatus[55] = rsObj.getInt("Alarm_56");
					alarmLastStatus[56] = rsObj.getInt("Alarm_57");
					alarmLastStatus[57] = rsObj.getInt("Alarm_58");
					alarmLastStatus[58] = rsObj.getInt("Alarm_59");
					alarmLastStatus[59] = rsObj.getInt("Alarm_60");
					alarmLastStatus[60] = rsObj.getInt("Alarm_61");
					alarmLastStatus[61] = rsObj.getInt("Alarm_62");
					alarmLastStatus[62] = rsObj.getInt("Alarm_63");
					alarmLastStatus[63] = rsObj.getInt("Alarm_64");
					alarmLastStatus[64] = rsObj.getInt("Alarm_65");
					alarmLastStatus[65] = rsObj.getInt("Alarm_66");
					alarmLastStatus[66] = rsObj.getInt("Alarm_67"); 
					alarmLastStatus[65] = rsObj.getInt("Alarm_66");
					alarmLastStatus[66] = rsObj.getInt("Alarm_67");
					alarmLastStatus[67] = rsObj.getInt("Alarm_68");
					alarmLastStatus[68] = rsObj.getInt("Alarm_69");
					alarmLastStatus[69] = rsObj.getInt("Alarm_70");
					alarmLastStatus[70] = rsObj.getInt("Alarm_71");
					alarmLastStatus[71] = rsObj.getInt("Alarm_72");
					alarmLastStatus[72] = rsObj.getInt("Alarm_73");
					alarmLastStatus[73] = rsObj.getInt("Alarm_74");
					alarmLastStatus[74] = rsObj.getInt("Alarm_75");
					alarmLastStatus[75] = rsObj.getInt("Alarm_76");
					alarmLastStatus[76] = rsObj.getInt("Alarm_77");
					alarmLastStatus[77] = rsObj.getInt("Alarm_78");
					alarmLastStatus[78] = rsObj.getInt("Alarm_79");
					alarmLastStatus[79] = rsObj.getInt("Alarm_80");
					alarmLastStatus[80] = rsObj.getInt("Alarm_81");
					alarmLastStatus[81] = rsObj.getInt("Alarm_82");
					alarmLastStatus[82] = rsObj.getInt("Alarm_83");
					alarmLastStatus[83] = rsObj.getInt("Alarm_84");
					alarmLastStatus[84] = rsObj.getInt("Alarm_85");
					alarmLastStatus[85] = rsObj.getInt("Alarm_86");
					alarmLastStatus[86] = rsObj.getInt("Alarm_87");
					alarmLastStatus[87] = rsObj.getInt("Alarm_88");
					alarmLastStatus[88] = rsObj.getInt("Alarm_89");
					alarmLastStatus[89] = rsObj.getInt("Alarm_90");
					alarmLastStatus[90] = rsObj.getInt("Alarm_91");
					alarmLastStatus[91] = rsObj.getInt("Alarm_92");
					alarmLastStatus[92] = rsObj.getInt("Alarm_93");
					alarmLastStatus[93] = rsObj.getInt("Alarm_94");
					alarmLastStatus[94] = rsObj.getInt("Alarm_95");
					alarmLastStatus[95] = rsObj.getInt("Alarm_96");
					alarmLastStatus[96] = rsObj.getInt("Alarm_97");
					alarmLastStatus[97] = rsObj.getInt("Alarm_98");
					alarmLastStatus[98] = rsObj.getInt("Alarm_99");
					alarmLastStatus[99] = rsObj.getInt("Alarm_100");
					alarmLastStatus[100] = rsObj.getInt("Alarm_101");
					alarmLastStatus[101] = rsObj.getInt("Alarm_102");
					alarmLastStatus[102] = rsObj.getInt("Alarm_103");
					alarmLastStatus[103] = rsObj.getInt("Alarm_104");
					alarmLastStatus[104] = rsObj.getInt("Alarm_105");
					alarmLastStatus[105] = rsObj.getInt("Alarm_106");
					alarmLastStatus[106] = rsObj.getInt("Alarm_107");
					alarmLastStatus[107] = rsObj.getInt("Alarm_108");
					alarmLastStatus[108] = rsObj.getInt("Alarm_109");
					alarmLastStatus[109] = rsObj.getInt("Alarm_110");
					alarmLastStatus[110] = rsObj.getInt("Alarm_111");
					alarmLastStatus[111] = rsObj.getInt("Alarm_112");
					alarmLastStatus[112] = rsObj.getInt("Alarm_113");
					alarmLastStatus[113] = rsObj.getInt("Alarm_114");
					alarmLastStatus[114] = rsObj.getInt("Alarm_115");
					alarmLastStatus[115] = rsObj.getInt("Alarm_116");
					alarmLastStatus[116] = rsObj.getInt("Alarm_117");
					alarmLastStatus[117] = rsObj.getInt("Alarm_118");
					alarmLastStatus[118] = rsObj.getInt("Alarm_119");
					alarmLastStatus[119] = rsObj.getInt("Alarm_120");
					alarmLastStatus[120] = rsObj.getInt("Alarm_121");
					alarmLastStatus[121] = rsObj.getInt("Alarm_122");
					alarmLastStatus[122] = rsObj.getInt("Alarm_123");
					alarmLastStatus[123] = rsObj.getInt("Alarm_124");
					alarmLastStatus[124] = rsObj.getInt("Alarm_125");
					alarmLastStatus[125] = rsObj.getInt("Alarm_126");
					alarmLastStatus[126] = rsObj.getInt("Alarm_127");
					alarmLastStatus[127] = rsObj.getInt("Alarm_128");
					alarmLastStatus[128] = rsObj.getInt("Alarm_129");
					alarmLastStatus[129] = rsObj.getInt("Alarm_130");
					alarmLastStatus[130] = rsObj.getInt("Alarm_131");
					alarmLastStatus[131] = rsObj.getInt("Alarm_132");
					alarmLastStatus[132] = rsObj.getInt("Alarm_133");
					alarmLastStatus[133] = rsObj.getInt("Alarm_134");
					alarmLastStatus[134] = rsObj.getInt("Alarm_135");
					alarmLastStatus[135] = rsObj.getInt("Alarm_136");
					alarmLastStatus[136] = rsObj.getInt("Alarm_137");
					alarmLastStatus[137] = rsObj.getInt("Alarm_138");
					alarmLastStatus[138] = rsObj.getInt("Alarm_139");
					alarmLastStatus[139] = rsObj.getInt("Alarm_140");
					alarmLastStatus[140] = rsObj.getInt("Alarm_141");
					alarmLastStatus[141] = rsObj.getInt("Alarm_142");
					alarmLastStatus[142] = rsObj.getInt("Alarm_143");
					alarmLastStatus[143] = rsObj.getInt("Alarm_144");
					alarmLastStatus[144] = rsObj.getInt("Alarm_145");
					alarmLastStatus[145] = rsObj.getInt("Alarm_146");
					alarmLastStatus[146] = rsObj.getInt("Alarm_147");
					alarmLastStatus[147] = rsObj.getInt("Alarm_148");
					alarmLastStatus[148] = rsObj.getInt("Alarm_149");
					alarmLastStatus[149] = rsObj.getInt("Alarm_150");
					alarmLastStatus[150] = rsObj.getInt("Alarm_151");
					alarmLastStatus[151] = rsObj.getInt("Alarm_152");
					alarmLastStatus[152] = rsObj.getInt("Alarm_153");
					alarmLastStatus[153] = rsObj.getInt("Alarm_154");
					alarmLastStatus[154] = rsObj.getInt("Alarm_155");
					alarmLastStatus[155] = rsObj.getInt("Alarm_156");
					alarmLastStatus[156] = rsObj.getInt("Alarm_157");
					alarmLastStatus[157] = rsObj.getInt("Alarm_158");
					alarmLastStatus[158] = rsObj.getInt("Alarm_159");
					alarmLastStatus[159] = rsObj.getInt("Alarm_160");
					alarmLastStatus[160] = rsObj.getInt("Alarm_161");
					alarmLastStatus[161] = rsObj.getInt("Alarm_162");
					alarmLastStatus[162] = rsObj.getInt("Alarm_163");
					alarmLastStatus[163] = rsObj.getInt("Alarm_164");
					alarmLastStatus[164] = rsObj.getInt("Alarm_165");
					alarmLastStatus[165] = rsObj.getInt("Alarm_166");
					alarmLastStatus[166] = rsObj.getInt("Alarm_167");
					alarmLastStatus[167] = rsObj.getInt("Alarm_168");
					alarmLastStatus[168] = rsObj.getInt("Alarm_169");
					alarmLastStatus[169] = rsObj.getInt("Alarm_170");
					alarmLastStatus[170] = rsObj.getInt("Alarm_171");
					alarmLastStatus[171] = rsObj.getInt("Alarm_172");
					alarmLastStatus[172] = rsObj.getInt("Alarm_173");
					alarmLastStatus[173] = rsObj.getInt("Alarm_174");
					alarmLastStatus[174] = rsObj.getInt("Alarm_175");
					alarmLastStatus[175] = rsObj.getInt("Alarm_176");
					alarmLastStatus[176] = rsObj.getInt("Alarm_177");
					alarmLastStatus[177] = rsObj.getInt("Alarm_178");
					alarmLastStatus[178] = rsObj.getInt("Alarm_179");
					alarmLastStatus[179] = rsObj.getInt("Alarm_180");
					alarmLastStatus[180] = rsObj.getInt("Alarm_181");
					alarmLastStatus[181] = rsObj.getInt("Alarm_182");
					alarmLastStatus[182] = rsObj.getInt("Alarm_183");
					alarmLastStatus[183] = rsObj.getInt("Alarm_184");
					alarmLastStatus[184] = rsObj.getInt("Alarm_185");
					alarmLastStatus[185] = rsObj.getInt("Alarm_186");
					alarmLastStatus[186] = rsObj.getInt("Alarm_187");
					alarmLastStatus[187] = rsObj.getInt("Alarm_188");
					alarmLastStatus[188] = rsObj.getInt("Alarm_189");
					alarmLastStatus[189] = rsObj.getInt("Alarm_190");
					alarmLastStatus[190] = rsObj.getInt("Alarm_191");
					alarmLastStatus[191] = rsObj.getInt("Alarm_192");
					alarmLastStatus[192] = rsObj.getInt("Alarm_193");
					alarmLastStatus[193] = rsObj.getInt("Alarm_194");
					alarmLastStatus[194] = rsObj.getInt("Alarm_195");
					alarmLastStatus[195] = rsObj.getInt("Alarm_196");
					alarmLastStatus[196] = rsObj.getInt("Alarm_197");
					alarmLastStatus[197] = rsObj.getInt("Alarm_198");
					alarmLastStatus[198] = rsObj.getInt("Alarm_199");
					alarmLastStatus[199] = rsObj.getInt("Alarm_200");
					alarmLastStatus[200] = rsObj.getInt("Alarm_201");
					alarmLastStatus[201] = rsObj.getInt("Alarm_202");
					alarmLastStatus[202] = rsObj.getInt("Alarm_203");
					alarmLastStatus[203] = rsObj.getInt("Alarm_204");
					alarmLastStatus[204] = rsObj.getInt("Alarm_205");
					alarmLastStatus[205] = rsObj.getInt("Alarm_206");
					alarmLastStatus[206] = rsObj.getInt("Alarm_207");
					alarmLastStatus[207] = rsObj.getInt("Alarm_208");
					alarmLastStatus[208] = rsObj.getInt("Alarm_209");
					alarmLastStatus[209] = rsObj.getInt("Alarm_210");
					alarmLastStatus[210] = rsObj.getInt("Alarm_211");
					alarmLastStatus[211] = rsObj.getInt("Alarm_212");
					alarmLastStatus[212] = rsObj.getInt("Alarm_213");
					alarmLastStatus[213] = rsObj.getInt("Alarm_214");
					alarmLastStatus[214] = rsObj.getInt("Alarm_215");
					alarmLastStatus[215] = rsObj.getInt("Alarm_216");
					alarmLastStatus[216] = rsObj.getInt("Alarm_217");
					alarmLastStatus[217] = rsObj.getInt("Alarm_218");
					alarmLastStatus[218] = rsObj.getInt("Alarm_219");
					alarmLastStatus[219] = rsObj.getInt("Alarm_220");
					alarmLastStatus[220] = rsObj.getInt("Alarm_221");
					alarmLastStatus[221] = rsObj.getInt("Alarm_222");
					alarmLastStatus[222] = rsObj.getInt("Alarm_223");
					alarmLastStatus[223] = rsObj.getInt("Alarm_224");
					alarmLastStatus[224] = rsObj.getInt("Alarm_225");
					alarmLastStatus[225] = rsObj.getInt("Alarm_226");
					alarmLastStatus[226] = rsObj.getInt("Alarm_227");
					alarmLastStatus[227] = rsObj.getInt("Alarm_228");
					alarmLastStatus[228] = rsObj.getInt("Alarm_229");
					alarmLastStatus[229] = rsObj.getInt("Alarm_230");
					alarmLastStatus[230] = rsObj.getInt("Alarm_231");
					alarmLastStatus[231] = rsObj.getInt("Alarm_232");
					alarmLastStatus[232] = rsObj.getInt("Alarm_233");
					alarmLastStatus[233] = rsObj.getInt("Alarm_234");
					alarmLastStatus[234] = rsObj.getInt("Alarm_235");
					alarmLastStatus[235] = rsObj.getInt("Alarm_236");
					alarmLastStatus[236] = rsObj.getInt("Alarm_237");
					alarmLastStatus[237] = rsObj.getInt("Alarm_238");
					alarmLastStatus[238] = rsObj.getInt("Alarm_239");
					alarmLastStatus[239] = rsObj.getInt("Alarm_240");
					alarmLastStatus[240] = rsObj.getInt("Alarm_241");
					alarmLastStatus[241] = rsObj.getInt("Alarm_242");
					alarmLastStatus[242] = rsObj.getInt("Alarm_243");
					alarmLastStatus[243] = rsObj.getInt("Alarm_244");
					alarmLastStatus[244] = rsObj.getInt("Alarm_245");
					alarmLastStatus[245] = rsObj.getInt("Alarm_246");
					alarmLastStatus[246] = rsObj.getInt("Alarm_247");
					alarmLastStatus[247] = rsObj.getInt("Alarm_248");
					alarmLastStatus[248] = rsObj.getInt("Alarm_249");
					alarmLastStatus[249] = rsObj.getInt("Alarm_250");
					alarmLastStatus[250] = rsObj.getInt("Alarm_251");
					alarmLastStatus[251] = rsObj.getInt("Alarm_252");
					alarmLastStatus[252] = rsObj.getInt("Alarm_253");
					alarmLastStatus[253] = rsObj.getInt("Alarm_254");
					alarmLastStatus[254] = rsObj.getInt("Alarm_255");
					alarmLastStatus[255] = rsObj.getInt("Alarm_256");
					alarmLastStatus[256] = rsObj.getInt("Alarm_257");
					alarmLastStatus[257] = rsObj.getInt("Alarm_258");
					alarmLastStatus[258] = rsObj.getInt("Alarm_259");
					alarmLastStatus[259] = rsObj.getInt("Alarm_260");
					alarmLastStatus[260] = rsObj.getInt("Alarm_261");
					alarmLastStatus[261] = rsObj.getInt("Alarm_262");
					alarmLastStatus[262] = rsObj.getInt("Alarm_263");
					alarmLastStatus[263] = rsObj.getInt("Alarm_264");
					alarmLastStatus[264] = rsObj.getInt("Alarm_265");
					alarmLastStatus[265] = rsObj.getInt("Alarm_266");
					alarmLastStatus[266] = rsObj.getInt("Alarm_267");
					alarmLastStatus[267] = rsObj.getInt("Alarm_268");
					alarmLastStatus[268] = rsObj.getInt("Alarm_269");
					alarmLastStatus[269] = rsObj.getInt("Alarm_270");
					alarmLastStatus[270] = rsObj.getInt("Alarm_271");
					alarmLastStatus[271] = rsObj.getInt("Alarm_272");
					alarmLastStatus[272] = rsObj.getInt("Alarm_273");
					alarmLastStatus[273] = rsObj.getInt("Alarm_274");
					alarmLastStatus[274] = rsObj.getInt("Alarm_275");
					alarmLastStatus[275] = rsObj.getInt("Alarm_276");
					alarmLastStatus[276] = rsObj.getInt("Alarm_277");
					alarmLastStatus[277] = rsObj.getInt("Alarm_278");
					alarmLastStatus[278] = rsObj.getInt("Alarm_279");
					alarmLastStatus[279] = rsObj.getInt("Alarm_280");
					alarmLastStatus[280] = rsObj.getInt("Alarm_281");
					alarmLastStatus[281] = rsObj.getInt("Alarm_282");
					alarmLastStatus[282] = rsObj.getInt("Alarm_283");
					alarmLastStatus[283] = rsObj.getInt("Alarm_284");
					alarmLastStatus[284] = rsObj.getInt("Alarm_285");
					alarmLastStatus[285] = rsObj.getInt("Alarm_286");
					alarmLastStatus[286] = rsObj.getInt("Alarm_287");
					alarmLastStatus[287] = rsObj.getInt("Alarm_288");
					alarmLastStatus[288] = rsObj.getInt("Alarm_289");
					alarmLastStatus[289] = rsObj.getInt("Alarm_290");
					alarmLastStatus[290] = rsObj.getInt("Alarm_291");
					alarmLastStatus[291] = rsObj.getInt("Alarm_292");
					alarmLastStatus[292] = rsObj.getInt("Alarm_293");
					alarmLastStatus[293] = rsObj.getInt("Alarm_294");
					alarmLastStatus[294] = rsObj.getInt("Alarm_295");
					alarmLastStatus[295] = rsObj.getInt("Alarm_296");
					alarmLastStatus[296] = rsObj.getInt("Alarm_297");
					alarmLastStatus[297] = rsObj.getInt("Alarm_298");
					alarmLastStatus[298] = rsObj.getInt("Alarm_299");
					alarmLastStatus[299] = rsObj.getInt("Alarm_300");
					alarmLastStatus[300] = rsObj.getInt("Alarm_301");
					alarmLastStatus[301] = rsObj.getInt("Alarm_302");
					alarmLastStatus[302] = rsObj.getInt("Alarm_303");
					alarmLastStatus[303] = rsObj.getInt("Alarm_304");
					alarmLastStatus[304] = rsObj.getInt("Alarm_305");
					alarmLastStatus[305] = rsObj.getInt("Alarm_306");
					alarmLastStatus[306] = rsObj.getInt("Alarm_307");
					alarmLastStatus[307] = rsObj.getInt("Alarm_308");
					alarmLastStatus[308] = rsObj.getInt("Alarm_309");
					alarmLastStatus[309] = rsObj.getInt("Alarm_310");
					alarmLastStatus[310] = rsObj.getInt("Alarm_311");
					alarmLastStatus[311] = rsObj.getInt("Alarm_312");
					alarmLastStatus[312] = rsObj.getInt("Alarm_313");
					alarmLastStatus[313] = rsObj.getInt("Alarm_314");
					alarmLastStatus[314] = rsObj.getInt("Alarm_315");
					alarmLastStatus[315] = rsObj.getInt("Alarm_316");
					alarmLastStatus[316] = rsObj.getInt("Alarm_317");
					alarmLastStatus[317] = rsObj.getInt("Alarm_318");
					alarmLastStatus[318] = rsObj.getInt("Alarm_319");
					alarmLastStatus[319] = rsObj.getInt("Alarm_320");
					alarmLastStatus[320] = rsObj.getInt("Alarm_321");
					alarmLastStatus[321] = rsObj.getInt("Alarm_322");
					alarmLastStatus[322] = rsObj.getInt("Alarm_323");
					alarmLastStatus[323] = rsObj.getInt("Alarm_324");
					alarmLastStatus[324] = rsObj.getInt("Alarm_325");
					alarmLastStatus[325] = rsObj.getInt("Alarm_326");
					alarmLastStatus[326] = rsObj.getInt("Alarm_327");
					alarmLastStatus[327] = rsObj.getInt("Alarm_328");
					alarmLastStatus[328] = rsObj.getInt("Alarm_329");
					alarmLastStatus[329] = rsObj.getInt("Alarm_330");
					alarmLastStatus[330] = rsObj.getInt("Alarm_331");
					alarmLastStatus[331] = rsObj.getInt("Alarm_332");
					alarmLastStatus[332] = rsObj.getInt("Alarm_333");
					alarmLastStatus[333] = rsObj.getInt("Alarm_334");
					alarmLastStatus[334] = rsObj.getInt("Alarm_335");
					alarmLastStatus[335] = rsObj.getInt("Alarm_336");
					alarmLastStatus[336] = rsObj.getInt("Alarm_337");
					alarmLastStatus[337] = rsObj.getInt("Alarm_338");
					alarmLastStatus[338] = rsObj.getInt("Alarm_339");
					alarmLastStatus[339] = rsObj.getInt("Alarm_340");
					alarmLastStatus[340] = rsObj.getInt("Alarm_341");
					alarmLastStatus[341] = rsObj.getInt("Alarm_342");
					alarmLastStatus[342] = rsObj.getInt("Alarm_343");
					alarmLastStatus[343] = rsObj.getInt("Alarm_344");
					alarmLastStatus[344] = rsObj.getInt("Alarm_345");
					alarmLastStatus[345] = rsObj.getInt("Alarm_346");
					alarmLastStatus[346] = rsObj.getInt("Alarm_347");
					alarmLastStatus[347] = rsObj.getInt("Alarm_348");
					alarmLastStatus[348] = rsObj.getInt("Alarm_349");
					alarmLastStatus[349] = rsObj.getInt("Alarm_350");
					alarmLastStatus[350] = rsObj.getInt("Alarm_351");
					alarmLastStatus[351] = rsObj.getInt("Alarm_352");
					alarmLastStatus[352] = rsObj.getInt("Alarm_353");
					alarmLastStatus[353] = rsObj.getInt("Alarm_354");
					alarmLastStatus[354] = rsObj.getInt("Alarm_355");
					alarmLastStatus[355] = rsObj.getInt("Alarm_356");
					alarmLastStatus[356] = rsObj.getInt("Alarm_357");
					alarmLastStatus[357] = rsObj.getInt("Alarm_358");
					alarmLastStatus[358] = rsObj.getInt("Alarm_359");
					alarmLastStatus[359] = rsObj.getInt("Alarm_360");
					alarmLastStatus[360] = rsObj.getInt("Alarm_361");
					alarmLastStatus[361] = rsObj.getInt("Alarm_362");
					alarmLastStatus[362] = rsObj.getInt("Alarm_363");
					alarmLastStatus[363] = rsObj.getInt("Alarm_364");
					alarmLastStatus[364] = rsObj.getInt("Alarm_365");
					alarmLastStatus[365] = rsObj.getInt("Alarm_366");
					alarmLastStatus[366] = rsObj.getInt("Alarm_367");
					alarmLastStatus[367] = rsObj.getInt("Alarm_368");
					alarmLastStatus[368] = rsObj.getInt("Alarm_369");
					alarmLastStatus[369] = rsObj.getInt("Alarm_370");
					alarmLastStatus[370] = rsObj.getInt("Alarm_371");
					alarmLastStatus[371] = rsObj.getInt("Alarm_372");
					alarmLastStatus[372] = rsObj.getInt("Alarm_373");
					alarmLastStatus[373] = rsObj.getInt("Alarm_374");
					alarmLastStatus[374] = rsObj.getInt("Alarm_375");
					alarmLastStatus[375] = rsObj.getInt("Alarm_376");
					alarmLastStatus[376] = rsObj.getInt("Alarm_377");
					alarmLastStatus[377] = rsObj.getInt("Alarm_378");
					alarmLastStatus[378] = rsObj.getInt("Alarm_379");
					alarmLastStatus[379] = rsObj.getInt("Alarm_380");
					alarmLastStatus[380] = rsObj.getInt("Alarm_381");
					alarmLastStatus[381] = rsObj.getInt("Alarm_382");
					alarmLastStatus[382] = rsObj.getInt("Alarm_383");
					alarmLastStatus[383] = rsObj.getInt("Alarm_384");
					alarmLastStatus[384] = rsObj.getInt("Alarm_385");
					alarmLastStatus[385] = rsObj.getInt("Alarm_386");
					alarmLastStatus[386] = rsObj.getInt("Alarm_387");
					alarmLastStatus[387] = rsObj.getInt("Alarm_388");
					alarmLastStatus[388] = rsObj.getInt("Alarm_389");
					alarmLastStatus[389] = rsObj.getInt("Alarm_390");
					alarmLastStatus[390] = rsObj.getInt("Alarm_391");
					alarmLastStatus[391] = rsObj.getInt("Alarm_392");
					alarmLastStatus[392] = rsObj.getInt("Alarm_393");
					alarmLastStatus[393] = rsObj.getInt("Alarm_394");
					alarmLastStatus[394] = rsObj.getInt("Alarm_395");
					alarmLastStatus[395] = rsObj.getInt("Alarm_396");
					alarmLastStatus[396] = rsObj.getInt("Alarm_397");
					alarmLastStatus[397] = rsObj.getInt("Alarm_398");
					alarmLastStatus[398] = rsObj.getInt("Alarm_399");
					alarmLastStatus[399] = rsObj.getInt("Alarm_400");
					alarmLastStatus[400] = rsObj.getInt("Alarm_401");
					alarmLastStatus[401] = rsObj.getInt("Alarm_402");
					alarmLastStatus[402] = rsObj.getInt("Alarm_403");
					alarmLastStatus[403] = rsObj.getInt("Alarm_404");
					alarmLastStatus[404] = rsObj.getInt("Alarm_405");
					alarmLastStatus[405] = rsObj.getInt("Alarm_406");
					alarmLastStatus[406] = rsObj.getInt("Alarm_407");
					alarmLastStatus[407] = rsObj.getInt("Alarm_408");

				}

				transResult.close();
				rsObj.close();

				/* Compare current and last if mismatch then update*/


				pstmtObj.close();
				//Query1
				pstmtObj = connObj.prepareStatement("insert into trans_alarmdatahistory(smSiteCode,alhTimestamp,Alarm_1,Alarm_2,Alarm_3,Alarm_4,Alarm_5,Alarm_6,Alarm_7,Alarm_8,Alarm_9,Alarm_10,Alarm_11,Alarm_12,Alarm_13,Alarm_14,Alarm_15,Alarm_16,Alarm_17,Alarm_18,Alarm_19,Alarm_20,Alarm_21,Alarm_22,Alarm_23,Alarm_24,Alarm_25,Alarm_26,Alarm_27,Alarm_28,Alarm_29,Alarm_30,Alarm_31,Alarm_32,Alarm_33,Alarm_34,Alarm_35,Alarm_36,Alarm_37,Alarm_38,Alarm_39,Alarm_40,Alarm_41,Alarm_42,Alarm_43,Alarm_44,Alarm_45,Alarm_46,Alarm_47,Alarm_48,Alarm_49,Alarm_50,Alarm_51,Alarm_52,Alarm_53,Alarm_54,Alarm_55,Alarm_56,Alarm_57,Alarm_58,Alarm_59,Alarm_60,Alarm_61,Alarm_62,Alarm_63,Alarm_64,Alarm_65,Alarm_66,Alarm_67,Alarm_68,Alarm_69,Alarm_70,Alarm_71,Alarm_72,Alarm_73,Alarm_74,Alarm_75,Alarm_76,Alarm_77,Alarm_78,Alarm_79,Alarm_80,Alarm_81,Alarm_82,Alarm_83,Alarm_84,Alarm_85,Alarm_86,Alarm_87,Alarm_88,Alarm_89,Alarm_90,Alarm_91,Alarm_92,Alarm_93,Alarm_94,Alarm_95,Alarm_96,Alarm_97,Alarm_98,Alarm_99,Alarm_100,Alarm_101,Alarm_102,Alarm_103,Alarm_104,Alarm_105,Alarm_106,Alarm_107,Alarm_108,Alarm_109,Alarm_110,Alarm_111,Alarm_112,Alarm_113,Alarm_114,Alarm_115,Alarm_116,Alarm_117,Alarm_118,Alarm_119,Alarm_120,Alarm_121,Alarm_122,Alarm_123,Alarm_124,Alarm_125,Alarm_126,Alarm_127,Alarm_128,Alarm_129,Alarm_130,Alarm_131,Alarm_132,Alarm_133,Alarm_134,Alarm_135,Alarm_136,Alarm_137,Alarm_138,Alarm_139,Alarm_140,Alarm_141,Alarm_142,Alarm_143,Alarm_144,Alarm_145,Alarm_146,Alarm_147,Alarm_148,Alarm_149,Alarm_150,Alarm_151,Alarm_152,Alarm_153,Alarm_154,Alarm_155,Alarm_156,Alarm_157,Alarm_158,Alarm_159,Alarm_160,Alarm_161,Alarm_162,Alarm_163,Alarm_164,Alarm_165,Alarm_166,Alarm_167,Alarm_168,Alarm_169,Alarm_170,Alarm_171,Alarm_172,Alarm_173,Alarm_174,Alarm_175,Alarm_176,Alarm_177,Alarm_178,Alarm_179,Alarm_180,Alarm_181,Alarm_182,Alarm_183,Alarm_184,Alarm_185,Alarm_186,Alarm_187,Alarm_188,Alarm_189,Alarm_190,Alarm_191,Alarm_192,Alarm_193,Alarm_194,Alarm_195,Alarm_196,Alarm_197,Alarm_198,Alarm_199,Alarm_200,Alarm_201,Alarm_202,Alarm_203,Alarm_204,Alarm_205,Alarm_206,Alarm_207,Alarm_208,Alarm_209,Alarm_210,Alarm_211,Alarm_212,Alarm_213,Alarm_214,Alarm_215,Alarm_216,Alarm_217,Alarm_218,Alarm_219,Alarm_220,Alarm_221,Alarm_222,Alarm_223,Alarm_224,Alarm_225,Alarm_226,Alarm_227,Alarm_228,Alarm_229,Alarm_230,Alarm_231,Alarm_232,Alarm_233,Alarm_234,Alarm_235,Alarm_236,Alarm_237,Alarm_238,Alarm_239,Alarm_240,Alarm_241,Alarm_242,Alarm_243,Alarm_244,Alarm_245,Alarm_246,Alarm_247,Alarm_248,Alarm_249,Alarm_250,Alarm_251,Alarm_252,Alarm_253,Alarm_254,Alarm_255,Alarm_256,Alarm_257,Alarm_258,Alarm_259,Alarm_260,Alarm_261,Alarm_262,Alarm_263,Alarm_264,Alarm_265,Alarm_266,Alarm_267,Alarm_268,Alarm_269,Alarm_270,Alarm_271,Alarm_272,Alarm_273,Alarm_274,Alarm_275,Alarm_276,Alarm_277,Alarm_278,Alarm_279,Alarm_280,Alarm_281,Alarm_282,Alarm_283,Alarm_284,Alarm_285,Alarm_286,Alarm_287,Alarm_288,Alarm_289,Alarm_290,Alarm_291,Alarm_292,Alarm_293,Alarm_294,Alarm_295,Alarm_296,Alarm_297,Alarm_298,Alarm_299,Alarm_300,Alarm_301,Alarm_302,Alarm_303,Alarm_304,Alarm_305,Alarm_306,Alarm_307,Alarm_308,Alarm_309,Alarm_310,Alarm_311,Alarm_312,Alarm_313,Alarm_314,Alarm_315,Alarm_316,Alarm_317,Alarm_318,Alarm_319,Alarm_320,Alarm_321,Alarm_322,Alarm_323,Alarm_324,Alarm_325,Alarm_326,Alarm_327,Alarm_328,Alarm_329,Alarm_330,Alarm_331,Alarm_332,Alarm_333,Alarm_334,Alarm_335,Alarm_336,Alarm_337,Alarm_338,Alarm_339,Alarm_340,Alarm_341,Alarm_342,Alarm_343,Alarm_344,Alarm_345,Alarm_346,Alarm_347,Alarm_348,Alarm_349,Alarm_350,Alarm_351,Alarm_352,Alarm_353,Alarm_354,Alarm_355,Alarm_356,Alarm_357,Alarm_358,Alarm_359,Alarm_360,Alarm_361,Alarm_362,Alarm_363,Alarm_364,Alarm_365,Alarm_366,Alarm_367,Alarm_368,Alarm_369,Alarm_370,Alarm_371,Alarm_372,Alarm_373,Alarm_374,Alarm_375,Alarm_376,Alarm_377,Alarm_378,Alarm_379,Alarm_380,Alarm_381,Alarm_382,Alarm_383,Alarm_384,Alarm_385,Alarm_386,Alarm_387,Alarm_388,Alarm_389,Alarm_390,Alarm_391,Alarm_392,Alarm_393,Alarm_394,Alarm_395,Alarm_396,Alarm_397,Alarm_398,Alarm_399,Alarm_400,Alarm_401,Alarm_402,Alarm_403,Alarm_404,Alarm_405,Alarm_406,Alarm_407,Alarm_408,smSiteID,smSitetypeid,DBCreationtimestamp,hpDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				//Error No value specified for parameter 412

				pstmtObj.setString(1,siteId);
				pstmtObj.setLong(2,_recordtime);
				pstmtObj.setInt(3 ,alarm[0]);
				pstmtObj.setInt(4 ,alarm[1]);
				pstmtObj.setInt(5,alarm[2]);
				pstmtObj.setInt(6,alarm[3]);
				pstmtObj.setInt(7,alarm[4]);
				pstmtObj.setInt(8,alarm[5]);
				pstmtObj.setInt(9,alarm[6]);
				pstmtObj.setInt(10,alarm[7]);
				pstmtObj.setInt(11,alarm[8]);
				pstmtObj.setInt(12,alarm[9]);
				pstmtObj.setInt(13,alarm[10]);
				pstmtObj.setInt(14,alarm[11]);
				pstmtObj.setInt(15,alarm[12]);
				pstmtObj.setInt(16,alarm[13]);
				pstmtObj.setInt(17,alarm[14]);
				pstmtObj.setInt(18,alarm[15]);
				pstmtObj.setInt(19,alarm[16]);
				pstmtObj.setInt(20,alarm[17]);
				pstmtObj.setInt(21,alarm[18]);
				pstmtObj.setInt(22,alarm[19]);
				pstmtObj.setInt(23,alarm[20]);
				pstmtObj.setInt(24,alarm[21]);
				pstmtObj.setInt(25,alarm[22]);
				pstmtObj.setInt(26,alarm[23]);
				pstmtObj.setInt(27,alarm[24]);
				pstmtObj.setInt(28,alarm[25]);
				pstmtObj.setInt(29,alarm[26]);
				pstmtObj.setInt(30,alarm[27]);
				pstmtObj.setInt(31,alarm[28]);
				pstmtObj.setInt(32,alarm[29]);
				pstmtObj.setInt(33,alarm[30]);
				pstmtObj.setInt(34,alarm[31]);
				pstmtObj.setInt(35,alarm[32]);
				pstmtObj.setInt(36,alarm[33]);
				pstmtObj.setInt(37,alarm[34]);
				pstmtObj.setInt(38,alarm[35]);
				pstmtObj.setInt(39,alarm[36]);
				pstmtObj.setInt(40,alarm[37]);
				pstmtObj.setInt(41,alarm[38]);
				pstmtObj.setInt(42,alarm[39]);
				pstmtObj.setInt(43,alarm[40]);
				pstmtObj.setInt(44,alarm[41]);
				pstmtObj.setInt(45,alarm[42]);
				pstmtObj.setInt(46,alarm[43]);
				pstmtObj.setInt(47,alarm[44]);
				pstmtObj.setInt(48,alarm[45]);
				pstmtObj.setInt(49,alarm[46]);
				pstmtObj.setInt(50,alarm[47]);
				pstmtObj.setInt(51,alarm[48]);
				pstmtObj.setInt(52,alarm[49]);
				pstmtObj.setInt(53,alarm[50]);
				pstmtObj.setInt(54,alarm[51]);
				pstmtObj.setInt(55,alarm[52]);
				pstmtObj.setInt(56,alarm[53]);
				pstmtObj.setInt(57,alarm[54]);
				pstmtObj.setInt(58,alarm[55]);
				pstmtObj.setInt(59,alarm[56]);
				pstmtObj.setInt(60,alarm[57]);
				pstmtObj.setInt(61,alarm[58]);
				pstmtObj.setInt(62,alarm[59]);
				pstmtObj.setInt(63,alarm[60]);
				pstmtObj.setInt(64,alarm[61]);
				pstmtObj.setInt(65,alarm[62]);
				pstmtObj.setInt(66,alarm[63]);
				pstmtObj.setInt(67,alarm[64]);
				pstmtObj.setInt(68,alarm[65]);
				pstmtObj.setInt(69,alarm[66]);
				pstmtObj.setInt(70,alarm[67]);
				pstmtObj.setInt(71,alarm[68]);
				pstmtObj.setInt(72,alarm[69]);
				pstmtObj.setInt(73,alarm[70]);
				pstmtObj.setInt(74,alarm[71]);
				pstmtObj.setInt(75,alarm[72]);
				pstmtObj.setInt(76,alarm[73]);
				pstmtObj.setInt(77,alarm[74]);
				pstmtObj.setInt(78,alarm[75]);
				pstmtObj.setInt(79,alarm[76]);
				pstmtObj.setInt(80,alarm[77]);
				pstmtObj.setInt(81,alarm[78]);
				pstmtObj.setInt(82,alarm[79]);
				pstmtObj.setInt(83,alarm[80]);
				pstmtObj.setInt(84,alarm[81]);
				pstmtObj.setInt(85,alarm[82]);
				pstmtObj.setInt(86,alarm[83]);
				pstmtObj.setInt(87,alarm[84]);
				pstmtObj.setInt(88,alarm[85]);
				pstmtObj.setInt(89,alarm[86]);
				pstmtObj.setInt(90,alarm[87]);
				pstmtObj.setInt(91,alarm[88]);
				pstmtObj.setInt(92,alarm[89]);
				pstmtObj.setInt(93,alarm[90]);
				pstmtObj.setInt(94,alarm[91]);
				pstmtObj.setInt(95,alarm[92]);
				pstmtObj.setInt(96,alarm[93]);
				pstmtObj.setInt(97,alarm[94]);
				pstmtObj.setInt(98,alarm[95]);
				pstmtObj.setInt(99, alarm[96]);
				pstmtObj.setInt(100, alarm[97]);
				pstmtObj.setInt(101, alarm[98]);
				pstmtObj.setInt(102, alarm[99]);
				pstmtObj.setInt(103, alarm[100]);
				pstmtObj.setInt(104, alarm[101]);
				pstmtObj.setInt(105, alarm[102]);
				pstmtObj.setInt(106, alarm[103]);
				pstmtObj.setInt(107, alarm[104]);
				pstmtObj.setInt(108, alarm[105]);
				pstmtObj.setInt(109, alarm[106]);
				pstmtObj.setInt(110, alarm[107]);
				pstmtObj.setInt(111, alarm[108]);
				pstmtObj.setInt(112, alarm[109]);
				pstmtObj.setInt(113, alarm[110]);
				pstmtObj.setInt(114, alarm[111]);
				pstmtObj.setInt(115, alarm[112]);
				pstmtObj.setInt(116, alarm[113]);
				pstmtObj.setInt(117, alarm[114]);
				pstmtObj.setInt(118, alarm[115]);
				pstmtObj.setInt(119, alarm[116]);
				pstmtObj.setInt(120, alarm[117]);
				pstmtObj.setInt(121, alarm[118]);
				pstmtObj.setInt(122, alarm[119]);
				pstmtObj.setInt(123, alarm[120]);
				pstmtObj.setInt(124, alarm[121]);
				pstmtObj.setInt(125, alarm[122]);
				pstmtObj.setInt(126, alarm[123]);
				pstmtObj.setInt(127, alarm[124]);
				pstmtObj.setInt(128, alarm[125]);
				pstmtObj.setInt(129, alarm[126]);
				pstmtObj.setInt(130, alarm[127]);
				pstmtObj.setInt(131, alarm[128]);
				pstmtObj.setInt(132, alarm[129]);
				pstmtObj.setInt(133, alarm[130]);
				pstmtObj.setInt(134, alarm[131]);
				pstmtObj.setInt(135, alarm[132]);
				pstmtObj.setInt(136, alarm[133]);
				pstmtObj.setInt(137, alarm[134]);
				pstmtObj.setInt(138, alarm[135]);
				pstmtObj.setInt(139, alarm[136]);
				pstmtObj.setInt(140, alarm[137]);
				pstmtObj.setInt(141, alarm[138]);
				pstmtObj.setInt(142, alarm[139]);
				pstmtObj.setInt(143, alarm[140]);
				pstmtObj.setInt(144, alarm[141]);
				pstmtObj.setInt(145, alarm[142]);
				pstmtObj.setInt(146, alarm[143]);
				pstmtObj.setInt(147, alarm[144]);
				pstmtObj.setInt(148, alarm[145]);
				pstmtObj.setInt(149, alarm[146]);
				pstmtObj.setInt(150, alarm[147]);
				pstmtObj.setInt(151, alarm[148]);
				pstmtObj.setInt(152, alarm[149]);
				pstmtObj.setInt(153, alarm[150]);
				pstmtObj.setInt(154, alarm[151]);
				pstmtObj.setInt(155, alarm[152]);
				pstmtObj.setInt(156, alarm[153]);
				pstmtObj.setInt(157, alarm[154]);
				pstmtObj.setInt(158, alarm[155]);
				pstmtObj.setInt(159, alarm[156]);
				pstmtObj.setInt(160, alarm[157]);
				pstmtObj.setInt(161, alarm[158]);
				pstmtObj.setInt(162, alarm[159]);
				pstmtObj.setInt(163, alarm[160]);
				pstmtObj.setInt(164, alarm[161]);
				pstmtObj.setInt(165, alarm[162]);
				pstmtObj.setInt(166, alarm[163]);
				pstmtObj.setInt(167, alarm[164]);
				pstmtObj.setInt(168, alarm[165]);
				pstmtObj.setInt(169, alarm[166]);
				pstmtObj.setInt(170, alarm[167]);
				pstmtObj.setInt(171, alarm[168]);
				pstmtObj.setInt(172, alarm[169]);
				pstmtObj.setInt(173, alarm[170]);
				pstmtObj.setInt(174, alarm[171]);
				pstmtObj.setInt(175, alarm[172]);
				pstmtObj.setInt(176, alarm[173]);
				pstmtObj.setInt(177, alarm[174]);
				pstmtObj.setInt(178, alarm[175]);
				pstmtObj.setInt(179, alarm[176]);
				pstmtObj.setInt(180, alarm[177]);
				pstmtObj.setInt(181, alarm[178]);
				pstmtObj.setInt(182, alarm[179]);
				pstmtObj.setInt(183, alarm[180]);
				pstmtObj.setInt(184, alarm[181]);
				pstmtObj.setInt(185, alarm[182]);
				pstmtObj.setInt(186, alarm[183]);
				pstmtObj.setInt(187, alarm[184]);
				pstmtObj.setInt(188, alarm[185]);
				pstmtObj.setInt(189, alarm[186]);
				pstmtObj.setInt(190, alarm[187]);
				pstmtObj.setInt(191, alarm[188]);
				pstmtObj.setInt(192, alarm[189]);
				pstmtObj.setInt(193, alarm[190]);
				pstmtObj.setInt(194, alarm[191]);
				pstmtObj.setInt(195, alarm[192]);
				pstmtObj.setInt(196, alarm[193]);
				pstmtObj.setInt(197, alarm[194]);
				pstmtObj.setInt(198, alarm[195]);
				pstmtObj.setInt(199, alarm[196]);
				pstmtObj.setInt(200, alarm[197]);
				pstmtObj.setInt(201, alarm[198]);
				pstmtObj.setInt(202, alarm[199]);
				pstmtObj.setInt(203, alarm[200]);
				pstmtObj.setInt(204, alarm[201]);
				pstmtObj.setInt(205, alarm[202]);
				pstmtObj.setInt(206, alarm[203]);
				pstmtObj.setInt(207, alarm[204]);
				pstmtObj.setInt(208, alarm[205]);
				pstmtObj.setInt(209, alarm[206]);
				pstmtObj.setInt(210, alarm[207]);
				pstmtObj.setInt(211, alarm[208]);
				pstmtObj.setInt(212, alarm[209]);
				pstmtObj.setInt(213, alarm[210]);
				pstmtObj.setInt(214, alarm[211]);
				pstmtObj.setInt(215, alarm[212]);
				pstmtObj.setInt(216, alarm[213]);
				pstmtObj.setInt(217, alarm[214]);
				pstmtObj.setInt(218, alarm[215]);
				pstmtObj.setInt(219, alarm[216]);
				pstmtObj.setInt(220, alarm[217]);
				pstmtObj.setInt(221, alarm[218]);
				pstmtObj.setInt(222, alarm[219]);
				pstmtObj.setInt(223, alarm[220]);
				pstmtObj.setInt(224, alarm[221]);
				pstmtObj.setInt(225, alarm[222]);
				pstmtObj.setInt(226, alarm[223]);
				pstmtObj.setInt(227, alarm[224]);
				pstmtObj.setInt(228, alarm[225]);
				pstmtObj.setInt(229, alarm[226]);
				pstmtObj.setInt(230, alarm[227]);
				pstmtObj.setInt(231, alarm[228]);
				pstmtObj.setInt(232, alarm[229]);
				pstmtObj.setInt(233, alarm[230]);
				pstmtObj.setInt(234, alarm[231]);
				pstmtObj.setInt(235, alarm[232]);
				pstmtObj.setInt(236, alarm[233]);
				pstmtObj.setInt(237, alarm[234]);
				pstmtObj.setInt(238, alarm[235]);
				pstmtObj.setInt(239, alarm[236]);
				pstmtObj.setInt(240, alarm[237]);
				pstmtObj.setInt(241, alarm[238]);
				pstmtObj.setInt(242, alarm[239]);
				pstmtObj.setInt(243, alarm[240]);
				pstmtObj.setInt(244, alarm[241]);
				pstmtObj.setInt(245, alarm[242]);
				pstmtObj.setInt(246, alarm[243]);
				pstmtObj.setInt(247, alarm[244]);
				pstmtObj.setInt(248, alarm[245]);
				pstmtObj.setInt(249, alarm[246]);
				pstmtObj.setInt(250, alarm[247]);
				pstmtObj.setInt(251, alarm[248]);
				pstmtObj.setInt(252, alarm[249]);
				pstmtObj.setInt(253, alarm[250]);
				pstmtObj.setInt(254, alarm[251]);
				pstmtObj.setInt(255, alarm[252]);
				pstmtObj.setInt(256, alarm[253]);
				pstmtObj.setInt(257, alarm[254]);
				pstmtObj.setInt(258, alarm[255]);
				pstmtObj.setInt(259, alarm[256]);
				pstmtObj.setInt(260, alarm[257]);
				pstmtObj.setInt(261, alarm[258]);
				pstmtObj.setInt(262, alarm[259]);
				pstmtObj.setInt(263, alarm[260]);
				pstmtObj.setInt(264, alarm[261]);
				pstmtObj.setInt(265, alarm[262]);
				pstmtObj.setInt(266, alarm[263]);
				pstmtObj.setInt(267, alarm[264]);
				pstmtObj.setInt(268, alarm[265]);
				pstmtObj.setInt(269, alarm[266]);
				pstmtObj.setInt(270, alarm[267]);
				pstmtObj.setInt(271, alarm[268]);
				pstmtObj.setInt(272, alarm[269]);
				pstmtObj.setInt(273, alarm[270]);
				pstmtObj.setInt(274, alarm[271]);
				pstmtObj.setInt(275, alarm[272]);
				pstmtObj.setInt(276, alarm[273]);
				pstmtObj.setInt(277, alarm[274]);
				pstmtObj.setInt(278, alarm[275]);
				pstmtObj.setInt(279, alarm[276]);
				pstmtObj.setInt(280, alarm[277]);
				pstmtObj.setInt(281, alarm[278]);
				pstmtObj.setInt(282, alarm[279]);
				pstmtObj.setInt(283, alarm[280]);
				pstmtObj.setInt(284, alarm[281]);
				pstmtObj.setInt(285, alarm[282]);
				pstmtObj.setInt(286, alarm[283]);
				pstmtObj.setInt(287, alarm[284]);
				pstmtObj.setInt(288, alarm[285]);
				pstmtObj.setInt(289, alarm[286]);
				pstmtObj.setInt(290, alarm[287]);
				pstmtObj.setInt(291, alarm[288]);
				pstmtObj.setInt(292, alarm[289]);
				pstmtObj.setInt(293, alarm[290]);
				pstmtObj.setInt(294, alarm[291]);
				pstmtObj.setInt(295, alarm[292]);
				pstmtObj.setInt(296, alarm[293]);
				pstmtObj.setInt(297, alarm[294]);
				pstmtObj.setInt(298, alarm[295]);
				pstmtObj.setInt(299, alarm[296]);
				pstmtObj.setInt(300, alarm[297]);
				pstmtObj.setInt(301, alarm[298]);
				pstmtObj.setInt(302, alarm[299]);
				pstmtObj.setInt(303, alarm[300]);
				pstmtObj.setInt(304, alarm[301]);
				pstmtObj.setInt(305, alarm[302]);
				pstmtObj.setInt(306, alarm[303]);
				pstmtObj.setInt(307, alarm[304]);
				pstmtObj.setInt(308, alarm[305]);
				pstmtObj.setInt(309, alarm[306]);
				pstmtObj.setInt(310, alarm[307]);
				pstmtObj.setInt(311, alarm[308]);
				pstmtObj.setInt(312, alarm[309]);
				pstmtObj.setInt(313, alarm[310]);
				pstmtObj.setInt(314, alarm[311]);
				pstmtObj.setInt(315, alarm[312]);
				pstmtObj.setInt(316, alarm[313]);
				pstmtObj.setInt(317, alarm[314]);
				pstmtObj.setInt(318, alarm[315]);
				pstmtObj.setInt(319, alarm[316]);
				pstmtObj.setInt(320, alarm[317]);
				pstmtObj.setInt(321, alarm[318]);
				pstmtObj.setInt(322, alarm[319]);
				pstmtObj.setInt(323, alarm[320]);
				pstmtObj.setInt(324, alarm[321]);
				pstmtObj.setInt(325, alarm[322]);
				pstmtObj.setInt(326, alarm[323]);
				pstmtObj.setInt(327, alarm[324]);
				pstmtObj.setInt(328, alarm[325]);
				pstmtObj.setInt(329, alarm[326]);
				pstmtObj.setInt(330, alarm[327]);
				pstmtObj.setInt(331, alarm[328]);
				pstmtObj.setInt(332, alarm[329]);
				pstmtObj.setInt(333, alarm[330]);
				pstmtObj.setInt(334, alarm[331]);
				pstmtObj.setInt(335, alarm[332]);
				pstmtObj.setInt(336, alarm[333]);
				pstmtObj.setInt(337, alarm[334]);
				pstmtObj.setInt(338, alarm[335]);
				pstmtObj.setInt(339, alarm[336]);
				pstmtObj.setInt(340, alarm[337]);
				pstmtObj.setInt(341, alarm[338]);
				pstmtObj.setInt(342, alarm[339]);
				pstmtObj.setInt(343, alarm[340]);
				pstmtObj.setInt(344, alarm[341]);
				pstmtObj.setInt(345, alarm[342]);
				pstmtObj.setInt(346, alarm[343]);
				pstmtObj.setInt(347, alarm[344]);
				pstmtObj.setInt(348, alarm[345]);
				pstmtObj.setInt(349, alarm[346]);
				pstmtObj.setInt(350, alarm[347]);
				pstmtObj.setInt(351, alarm[348]);
				pstmtObj.setInt(352, alarm[349]);
				pstmtObj.setInt(353, alarm[350]);
				pstmtObj.setInt(354, alarm[351]);
				pstmtObj.setInt(355, alarm[352]);
				pstmtObj.setInt(356, alarm[353]);
				pstmtObj.setInt(357, alarm[354]);
				pstmtObj.setInt(358, alarm[355]);
				pstmtObj.setInt(359, alarm[356]);
				pstmtObj.setInt(360, alarm[357]);
				pstmtObj.setInt(361, alarm[358]);
				pstmtObj.setInt(362, alarm[359]);
				pstmtObj.setInt(363, alarm[360]);
				pstmtObj.setInt(364, alarm[361]);
				pstmtObj.setInt(365, alarm[362]);
				pstmtObj.setInt(366, alarm[363]);
				pstmtObj.setInt(367, alarm[364]);
				pstmtObj.setInt(368, alarm[365]);
				pstmtObj.setInt(369, alarm[366]);
				pstmtObj.setInt(370, alarm[367]);
				pstmtObj.setInt(371, alarm[368]);
				pstmtObj.setInt(372, alarm[369]);
				pstmtObj.setInt(373, alarm[370]);
				pstmtObj.setInt(374, alarm[371]);
				pstmtObj.setInt(375, alarm[372]);
				pstmtObj.setInt(376, alarm[373]);
				pstmtObj.setInt(377, alarm[374]);
				pstmtObj.setInt(378, alarm[375]);
				pstmtObj.setInt(379, alarm[376]);
				pstmtObj.setInt(380, alarm[377]);
				pstmtObj.setInt(381, alarm[378]);
				pstmtObj.setInt(382, alarm[379]);
				pstmtObj.setInt(383, alarm[380]);
				pstmtObj.setInt(384, alarm[381]);
				pstmtObj.setInt(385, alarm[382]);
				pstmtObj.setInt(386, alarm[383]);
				pstmtObj.setInt(387, alarm[384]);
				pstmtObj.setInt(388, alarm[385]);
				pstmtObj.setInt(389, alarm[386]);
				pstmtObj.setInt(390, alarm[387]);
				pstmtObj.setInt(391, alarm[388]);
				pstmtObj.setInt(392, alarm[389]);
				pstmtObj.setInt(393, alarm[390]);
				pstmtObj.setInt(394, alarm[391]);
				pstmtObj.setInt(395, alarm[392]);
				pstmtObj.setInt(396, alarm[393]);
				pstmtObj.setInt(397, alarm[394]);
				pstmtObj.setInt(398, alarm[395]);
				pstmtObj.setInt(399, alarm[396]);
				pstmtObj.setInt(400, alarm[397]);
				pstmtObj.setInt(401, alarm[398]);
				pstmtObj.setInt(402, alarm[399]);
				pstmtObj.setInt(403, alarm[400]);
				pstmtObj.setInt(404, alarm[401]);
				pstmtObj.setInt(405, alarm[402]);
				pstmtObj.setInt(406, alarm[403]);
				pstmtObj.setInt(407, alarm[404]);
				pstmtObj.setInt(408, alarm[405]);
				pstmtObj.setInt(409, alarm[406]);
				pstmtObj.setInt(410, alarm[407]);
				pstmtObj.setInt(411, smSiteId);
				pstmtObj.setInt(412, smSitetypeid);
				
				pstmtObj.setLong(413,dbCreationTime);
				pstmtObj.setDate(414, hpDate);

				pstmtObj.executeUpdate();	
			}
			
			pstmtObj = connObj.prepareStatement("Select * from trans_ltmalarmlaststatus where smSiteCode "
					+ "='"+siteId+"'");

			ResultSet lirsObj = pstmtObj.executeQuery();
			boolean liisResult = lirsObj.next();
			
			lirsObj.close();
			AlarmUtil alarmUtil = new AlarmUtil();
			
			if(liisResult == false) {

				alarmUtil.insertIntoLiAlarmLastStatus(alarm,smSiteId,smSitetypeid,siteId,_recordtime,dbCreationTime,hpDate,connObj);
				alarmUtil.insertIntoLiTransAlarmHistory(alarm,smSiteId,smSitetypeid,siteId,_recordtime,dbCreationTime,hpDate,connObj);

			}
			else {

				pstmtObj = connObj.prepareStatement("Select * from trans_ltmalarmlaststatus where smSiteCode ='"+siteId+"'");

				ResultSet transLiResult = pstmtObj.executeQuery();
				while(transLiResult.next()){

					alarmLastStatus[408] = transLiResult.getInt("Alarm_409");
					alarmLastStatus[409] = transLiResult.getInt("Alarm_410");
					alarmLastStatus[410] = transLiResult.getInt("Alarm_411");
					alarmLastStatus[411] = transLiResult.getInt("Alarm_412");
					alarmLastStatus[412] = transLiResult.getInt("Alarm_413");
					alarmLastStatus[413] = transLiResult.getInt("Alarm_414");
					alarmLastStatus[414] = transLiResult.getInt("Alarm_415");
					alarmLastStatus[415] = transLiResult.getInt("Alarm_416");
					alarmLastStatus[416] = transLiResult.getInt("Alarm_417");
					alarmLastStatus[417] = transLiResult.getInt("Alarm_418");
					alarmLastStatus[418] = transLiResult.getInt("Alarm_419");
					alarmLastStatus[419] = transLiResult.getInt("Alarm_420");
					alarmLastStatus[420] = transLiResult.getInt("Alarm_421");
					alarmLastStatus[421] = transLiResult.getInt("Alarm_422");
					alarmLastStatus[422] = transLiResult.getInt("Alarm_423");
					alarmLastStatus[423] = transLiResult.getInt("Alarm_424");
					alarmLastStatus[424] = transLiResult.getInt("Alarm_425");
					alarmLastStatus[425] = transLiResult.getInt("Alarm_426");
					alarmLastStatus[426] = transLiResult.getInt("Alarm_427");
					alarmLastStatus[427] = transLiResult.getInt("Alarm_428");
					alarmLastStatus[428] = transLiResult.getInt("Alarm_429");
					alarmLastStatus[429] = transLiResult.getInt("Alarm_430");
					alarmLastStatus[430] = transLiResult.getInt("Alarm_431");
					alarmLastStatus[431] = transLiResult.getInt("Alarm_432");
					alarmLastStatus[432] = transLiResult.getInt("Alarm_433");
					alarmLastStatus[433] = transLiResult.getInt("Alarm_434");
					alarmLastStatus[434] = transLiResult.getInt("Alarm_435");
					alarmLastStatus[435] = transLiResult.getInt("Alarm_436");
					alarmLastStatus[436] = transLiResult.getInt("Alarm_437");
					alarmLastStatus[437] = transLiResult.getInt("Alarm_438");
					alarmLastStatus[438] = transLiResult.getInt("Alarm_439");
					alarmLastStatus[439] = transLiResult.getInt("Alarm_440");
					alarmLastStatus[440] = transLiResult.getInt("Alarm_441");
					alarmLastStatus[441] = transLiResult.getInt("Alarm_442");
					alarmLastStatus[442] = transLiResult.getInt("Alarm_443");
					alarmLastStatus[443] = transLiResult.getInt("Alarm_444");
					alarmLastStatus[444] = transLiResult.getInt("Alarm_445");
					alarmLastStatus[445] = transLiResult.getInt("Alarm_446");
					alarmLastStatus[446] = transLiResult.getInt("Alarm_447");
					alarmLastStatus[447] = transLiResult.getInt("Alarm_448");
					alarmLastStatus[448] = transLiResult.getInt("Alarm_449");
					alarmLastStatus[449] = transLiResult.getInt("Alarm_450");
					alarmLastStatus[450] = transLiResult.getInt("Alarm_451");
					alarmLastStatus[451] = transLiResult.getInt("Alarm_452");
					alarmLastStatus[452] = transLiResult.getInt("Alarm_453");
					alarmLastStatus[453] = transLiResult.getInt("Alarm_454");
					alarmLastStatus[454] = transLiResult.getInt("Alarm_455");
					alarmLastStatus[455] = transLiResult.getInt("Alarm_456");
					alarmLastStatus[456] = transLiResult.getInt("Alarm_457");
					alarmLastStatus[457] = transLiResult.getInt("Alarm_458");
					alarmLastStatus[458] = transLiResult.getInt("Alarm_459");
					alarmLastStatus[459] = transLiResult.getInt("Alarm_460");
					alarmLastStatus[460] = transLiResult.getInt("Alarm_461");
					alarmLastStatus[461] = transLiResult.getInt("Alarm_462");
					alarmLastStatus[462] = transLiResult.getInt("Alarm_463");
					alarmLastStatus[463] = transLiResult.getInt("Alarm_464");
					alarmLastStatus[464] = transLiResult.getInt("Alarm_465");
					alarmLastStatus[465] = transLiResult.getInt("Alarm_466");
					alarmLastStatus[466] = transLiResult.getInt("Alarm_467");
					alarmLastStatus[467] = transLiResult.getInt("Alarm_468");
					alarmLastStatus[468] = transLiResult.getInt("Alarm_469");
					alarmLastStatus[469] = transLiResult.getInt("Alarm_470");
					alarmLastStatus[470] = transLiResult.getInt("Alarm_471");
					alarmLastStatus[471] = transLiResult.getInt("Alarm_472");
					alarmLastStatus[472] = transLiResult.getInt("Alarm_473");
					alarmLastStatus[473] = transLiResult.getInt("Alarm_474");
					alarmLastStatus[474] = transLiResult.getInt("Alarm_475");
					alarmLastStatus[475] = transLiResult.getInt("Alarm_476");
					alarmLastStatus[476] = transLiResult.getInt("Alarm_477");
					alarmLastStatus[477] = transLiResult.getInt("Alarm_478");
					alarmLastStatus[478] = transLiResult.getInt("Alarm_479");
					alarmLastStatus[479] = transLiResult.getInt("Alarm_480");
					alarmLastStatus[480] = transLiResult.getInt("Alarm_481");
					alarmLastStatus[481] = transLiResult.getInt("Alarm_482");
					alarmLastStatus[482] = transLiResult.getInt("Alarm_483");
					alarmLastStatus[483] = transLiResult.getInt("Alarm_484");
					alarmLastStatus[484] = transLiResult.getInt("Alarm_485");
					alarmLastStatus[485] = transLiResult.getInt("Alarm_486");
					alarmLastStatus[486] = transLiResult.getInt("Alarm_487");
					alarmLastStatus[487] = transLiResult.getInt("Alarm_488");
					alarmLastStatus[488] = transLiResult.getInt("Alarm_489");
					alarmLastStatus[489] = transLiResult.getInt("Alarm_490");
					alarmLastStatus[490] = transLiResult.getInt("Alarm_491");
					alarmLastStatus[491] = transLiResult.getInt("Alarm_492");
					alarmLastStatus[492] = transLiResult.getInt("Alarm_493");
					alarmLastStatus[493] = transLiResult.getInt("Alarm_494");
					alarmLastStatus[494] = transLiResult.getInt("Alarm_495");
					alarmLastStatus[495] = transLiResult.getInt("Alarm_496");
					alarmLastStatus[496] = transLiResult.getInt("Alarm_497");
					alarmLastStatus[497] = transLiResult.getInt("Alarm_498");
					alarmLastStatus[498] = transLiResult.getInt("Alarm_499");
					alarmLastStatus[499] = transLiResult.getInt("Alarm_500");
					alarmLastStatus[500] = transLiResult.getInt("Alarm_501");
					alarmLastStatus[501] = transLiResult.getInt("Alarm_502");
					alarmLastStatus[502] = transLiResult.getInt("Alarm_503");
					alarmLastStatus[503] = transLiResult.getInt("Alarm_504");
					alarmLastStatus[504] = transLiResult.getInt("Alarm_505");
					alarmLastStatus[505] = transLiResult.getInt("Alarm_506");
					alarmLastStatus[506] = transLiResult.getInt("Alarm_507");
					alarmLastStatus[507] = transLiResult.getInt("Alarm_508");
					alarmLastStatus[508] = transLiResult.getInt("Alarm_509");
					alarmLastStatus[509] = transLiResult.getInt("Alarm_510");
					alarmLastStatus[510] = transLiResult.getInt("Alarm_511");
					alarmLastStatus[511] = transLiResult.getInt("Alarm_512");
					alarmLastStatus[512] = transLiResult.getInt("Alarm_513");
					alarmLastStatus[513] = transLiResult.getInt("Alarm_514");
					alarmLastStatus[514] = transLiResult.getInt("Alarm_515");
					alarmLastStatus[515] = transLiResult.getInt("Alarm_516");
					alarmLastStatus[516] = transLiResult.getInt("Alarm_517");
					alarmLastStatus[517] = transLiResult.getInt("Alarm_518");
					alarmLastStatus[518] = transLiResult.getInt("Alarm_519");
					alarmLastStatus[519] = transLiResult.getInt("Alarm_520");
					alarmLastStatus[520] = transLiResult.getInt("Alarm_521");
					alarmLastStatus[521] = transLiResult.getInt("Alarm_522");
					alarmLastStatus[522] = transLiResult.getInt("Alarm_523");
					alarmLastStatus[523] = transLiResult.getInt("Alarm_524");
					alarmLastStatus[524] = transLiResult.getInt("Alarm_525");
					alarmLastStatus[525] = transLiResult.getInt("Alarm_526");
					alarmLastStatus[526] = transLiResult.getInt("Alarm_527");
					alarmLastStatus[527] = transLiResult.getInt("Alarm_528");
					alarmLastStatus[528] = transLiResult.getInt("Alarm_529");
					alarmLastStatus[529] = transLiResult.getInt("Alarm_530");
					alarmLastStatus[530] = transLiResult.getInt("Alarm_531");
					alarmLastStatus[531] = transLiResult.getInt("Alarm_532");
					alarmLastStatus[532] = transLiResult.getInt("Alarm_533");
					alarmLastStatus[533] = transLiResult.getInt("Alarm_534");
					alarmLastStatus[534] = transLiResult.getInt("Alarm_535");
					alarmLastStatus[535] = transLiResult.getInt("Alarm_536");
					alarmLastStatus[536] = transLiResult.getInt("Alarm_537");
					alarmLastStatus[537] = transLiResult.getInt("Alarm_538");
					alarmLastStatus[538] = transLiResult.getInt("Alarm_539");
					alarmLastStatus[539] = transLiResult.getInt("Alarm_540");
					alarmLastStatus[540] = transLiResult.getInt("Alarm_541");
					alarmLastStatus[541] = transLiResult.getInt("Alarm_542");
					alarmLastStatus[542] = transLiResult.getInt("Alarm_543");
					alarmLastStatus[543] = transLiResult.getInt("Alarm_544");
					alarmLastStatus[544] = transLiResult.getInt("Alarm_545");
					alarmLastStatus[545] = transLiResult.getInt("Alarm_546");
					alarmLastStatus[546] = transLiResult.getInt("Alarm_547");
					alarmLastStatus[547] = transLiResult.getInt("Alarm_548");
					alarmLastStatus[548] = transLiResult.getInt("Alarm_549");
					alarmLastStatus[549] = transLiResult.getInt("Alarm_550");
					alarmLastStatus[550] = transLiResult.getInt("Alarm_551");
					alarmLastStatus[551] = transLiResult.getInt("Alarm_552");
					alarmLastStatus[552] = transLiResult.getInt("Alarm_553");
					alarmLastStatus[553] = transLiResult.getInt("Alarm_554");
					alarmLastStatus[554] = transLiResult.getInt("Alarm_555");
					alarmLastStatus[555] = transLiResult.getInt("Alarm_556");
					alarmLastStatus[556] = transLiResult.getInt("Alarm_557");
					alarmLastStatus[557] = transLiResult.getInt("Alarm_558");
					alarmLastStatus[558] = transLiResult.getInt("Alarm_559");
					alarmLastStatus[559] = transLiResult.getInt("Alarm_560");
					alarmLastStatus[560] = transLiResult.getInt("Alarm_561");
					alarmLastStatus[561] = transLiResult.getInt("Alarm_562");
					alarmLastStatus[562] = transLiResult.getInt("Alarm_563");
					alarmLastStatus[563] = transLiResult.getInt("Alarm_564");
					alarmLastStatus[564] = transLiResult.getInt("Alarm_565");
					alarmLastStatus[565] = transLiResult.getInt("Alarm_566");
					alarmLastStatus[566] = transLiResult.getInt("Alarm_567");
					alarmLastStatus[567] = transLiResult.getInt("Alarm_568");
					alarmLastStatus[568] = transLiResult.getInt("Alarm_569");
					alarmLastStatus[569] = transLiResult.getInt("Alarm_570");
					alarmLastStatus[570] = transLiResult.getInt("Alarm_571");
					alarmLastStatus[571] = transLiResult.getInt("Alarm_572");
					alarmLastStatus[572] = transLiResult.getInt("Alarm_573");
					alarmLastStatus[573] = transLiResult.getInt("Alarm_574");
					alarmLastStatus[574] = transLiResult.getInt("Alarm_575");
					alarmLastStatus[575] = transLiResult.getInt("Alarm_576");
					alarmLastStatus[576] = transLiResult.getInt("Alarm_577");
					alarmLastStatus[577] = transLiResult.getInt("Alarm_578");
					alarmLastStatus[578] = transLiResult.getInt("Alarm_579");
					alarmLastStatus[579] = transLiResult.getInt("Alarm_580");
					alarmLastStatus[580] = transLiResult.getInt("Alarm_581");
					alarmLastStatus[581] = transLiResult.getInt("Alarm_582");
					alarmLastStatus[582] = transLiResult.getInt("Alarm_583");
					alarmLastStatus[583] = transLiResult.getInt("Alarm_584");
					alarmLastStatus[584] = transLiResult.getInt("Alarm_585");
					alarmLastStatus[585] = transLiResult.getInt("Alarm_586");
					alarmLastStatus[586] = transLiResult.getInt("Alarm_587");
					alarmLastStatus[587] = transLiResult.getInt("Alarm_588");
					alarmLastStatus[588] = transLiResult.getInt("Alarm_589");
					alarmLastStatus[589] = transLiResult.getInt("Alarm_590");
					alarmLastStatus[590] = transLiResult.getInt("Alarm_591");
					alarmLastStatus[591] = transLiResult.getInt("Alarm_592");
					alarmLastStatus[592] = transLiResult.getInt("Alarm_593");
					alarmLastStatus[593] = transLiResult.getInt("Alarm_594");
					alarmLastStatus[594] = transLiResult.getInt("Alarm_595");
					alarmLastStatus[595] = transLiResult.getInt("Alarm_596");
					alarmLastStatus[596] = transLiResult.getInt("Alarm_597");
					alarmLastStatus[597] = transLiResult.getInt("Alarm_598");
					alarmLastStatus[598] = transLiResult.getInt("Alarm_599");
					alarmLastStatus[599] = transLiResult.getInt("Alarm_600");
					alarmLastStatus[600] = transLiResult.getInt("Alarm_601");
					alarmLastStatus[601] = transLiResult.getInt("Alarm_602");
					alarmLastStatus[602] = transLiResult.getInt("Alarm_603");
					alarmLastStatus[603] = transLiResult.getInt("Alarm_604");
					alarmLastStatus[604] = transLiResult.getInt("Alarm_605");
					alarmLastStatus[605] = transLiResult.getInt("Alarm_606");
					alarmLastStatus[606] = transLiResult.getInt("Alarm_607");
					alarmLastStatus[607] = transLiResult.getInt("Alarm_608");
					alarmLastStatus[608] = transLiResult.getInt("Alarm_609");
					alarmLastStatus[609] = transLiResult.getInt("Alarm_610");
					alarmLastStatus[610] = transLiResult.getInt("Alarm_611");
					alarmLastStatus[611] = transLiResult.getInt("Alarm_612");
					alarmLastStatus[612] = transLiResult.getInt("Alarm_613");
					alarmLastStatus[613] = transLiResult.getInt("Alarm_614");
					alarmLastStatus[614] = transLiResult.getInt("Alarm_615");
					alarmLastStatus[615] = transLiResult.getInt("Alarm_616");
					alarmLastStatus[616] = transLiResult.getInt("Alarm_617");
					alarmLastStatus[617] = transLiResult.getInt("Alarm_618");
					alarmLastStatus[618] = transLiResult.getInt("Alarm_619");
					alarmLastStatus[619] = transLiResult.getInt("Alarm_620");
					alarmLastStatus[620] = transLiResult.getInt("Alarm_621");
					alarmLastStatus[621] = transLiResult.getInt("Alarm_622");
					alarmLastStatus[622] = transLiResult.getInt("Alarm_623");
					alarmLastStatus[623] = transLiResult.getInt("Alarm_624");
					alarmLastStatus[624] = transLiResult.getInt("Alarm_625");
					alarmLastStatus[625] = transLiResult.getInt("Alarm_626");
					alarmLastStatus[626] = transLiResult.getInt("Alarm_627");
					alarmLastStatus[627] = transLiResult.getInt("Alarm_628");
					alarmLastStatus[628] = transLiResult.getInt("Alarm_629");
					alarmLastStatus[629] = transLiResult.getInt("Alarm_630");
					alarmLastStatus[630] = transLiResult.getInt("Alarm_631");
					alarmLastStatus[631] = transLiResult.getInt("Alarm_632");
					alarmLastStatus[632] = transLiResult.getInt("Alarm_633");
					alarmLastStatus[633] = transLiResult.getInt("Alarm_634");
					alarmLastStatus[634] = transLiResult.getInt("Alarm_635");
					alarmLastStatus[635] = transLiResult.getInt("Alarm_636");
					alarmLastStatus[636] = transLiResult.getInt("Alarm_637");
					alarmLastStatus[637] = transLiResult.getInt("Alarm_638");
					alarmLastStatus[638] = transLiResult.getInt("Alarm_639");
					alarmLastStatus[639] = transLiResult.getInt("Alarm_640");
					alarmLastStatus[640] = transLiResult.getInt("Alarm_641");
					alarmLastStatus[641] = transLiResult.getInt("Alarm_642");
					alarmLastStatus[642] = transLiResult.getInt("Alarm_643");
					alarmLastStatus[643] = transLiResult.getInt("Alarm_644");
					alarmLastStatus[644] = transLiResult.getInt("Alarm_645");
					alarmLastStatus[645] = transLiResult.getInt("Alarm_646");
					alarmLastStatus[646] = transLiResult.getInt("Alarm_647");
					alarmLastStatus[647] = transLiResult.getInt("Alarm_648");
					alarmLastStatus[648] = transLiResult.getInt("Alarm_649");
					alarmLastStatus[649] = transLiResult.getInt("Alarm_650");
					alarmLastStatus[650] = transLiResult.getInt("Alarm_651");
					alarmLastStatus[651] = transLiResult.getInt("Alarm_652");
					alarmLastStatus[652] = transLiResult.getInt("Alarm_653");
					alarmLastStatus[653] = transLiResult.getInt("Alarm_654");
					alarmLastStatus[654] = transLiResult.getInt("Alarm_655");
					alarmLastStatus[655] = transLiResult.getInt("Alarm_656");
					alarmLastStatus[656] = transLiResult.getInt("Alarm_657");
					alarmLastStatus[657] = transLiResult.getInt("Alarm_658");
					alarmLastStatus[658] = transLiResult.getInt("Alarm_659");
					alarmLastStatus[659] = transLiResult.getInt("Alarm_660");
					alarmLastStatus[660] = transLiResult.getInt("Alarm_661");
					alarmLastStatus[661] = transLiResult.getInt("Alarm_662");
					alarmLastStatus[662] = transLiResult.getInt("Alarm_663");
					alarmLastStatus[663] = transLiResult.getInt("Alarm_664");
					alarmLastStatus[664] = transLiResult.getInt("Alarm_665");
					alarmLastStatus[665] = transLiResult.getInt("Alarm_666");
					alarmLastStatus[666] = transLiResult.getInt("Alarm_667");
					alarmLastStatus[667] = transLiResult.getInt("Alarm_668");
					alarmLastStatus[668] = transLiResult.getInt("Alarm_669");
					alarmLastStatus[669] = transLiResult.getInt("Alarm_670");
					alarmLastStatus[670] = transLiResult.getInt("Alarm_671");
					alarmLastStatus[671] = transLiResult.getInt("Alarm_672");
					alarmLastStatus[672] = transLiResult.getInt("Alarm_673");
					alarmLastStatus[673] = transLiResult.getInt("Alarm_674");
					alarmLastStatus[674] = transLiResult.getInt("Alarm_675");
					alarmLastStatus[675] = transLiResult.getInt("Alarm_676");
					alarmLastStatus[676] = transLiResult.getInt("Alarm_677");
					alarmLastStatus[677] = transLiResult.getInt("Alarm_678");
					alarmLastStatus[678] = transLiResult.getInt("Alarm_679");
					alarmLastStatus[679] = transLiResult.getInt("Alarm_680");
					alarmLastStatus[680] = transLiResult.getInt("Alarm_681");
					alarmLastStatus[681] = transLiResult.getInt("Alarm_682");
					alarmLastStatus[682] = transLiResult.getInt("Alarm_683");
					alarmLastStatus[683] = transLiResult.getInt("Alarm_684");
					alarmLastStatus[684] = transLiResult.getInt("Alarm_685");
					alarmLastStatus[685] = transLiResult.getInt("Alarm_686");
					alarmLastStatus[686] = transLiResult.getInt("Alarm_687");
					alarmLastStatus[687] = transLiResult.getInt("Alarm_688");
					alarmLastStatus[688] = transLiResult.getInt("Alarm_689");
					alarmLastStatus[689] = transLiResult.getInt("Alarm_690");
					alarmLastStatus[690] = transLiResult.getInt("Alarm_691");
					alarmLastStatus[691] = transLiResult.getInt("Alarm_692");
					alarmLastStatus[692] = transLiResult.getInt("Alarm_693");
					alarmLastStatus[693] = transLiResult.getInt("Alarm_694");
					alarmLastStatus[694] = transLiResult.getInt("Alarm_695");
					alarmLastStatus[695] = transLiResult.getInt("Alarm_696");
					alarmLastStatus[696] = transLiResult.getInt("Alarm_697");
					alarmLastStatus[697] = transLiResult.getInt("Alarm_698");
					alarmLastStatus[698] = transLiResult.getInt("Alarm_699");
					alarmLastStatus[699] = transLiResult.getInt("Alarm_700");
					alarmLastStatus[700] = transLiResult.getInt("Alarm_701");
					alarmLastStatus[701] = transLiResult.getInt("Alarm_702");
					alarmLastStatus[702] = transLiResult.getInt("Alarm_703");
					alarmLastStatus[703] = transLiResult.getInt("Alarm_704");
					alarmLastStatus[704] = transLiResult.getInt("Alarm_705");
					alarmLastStatus[705] = transLiResult.getInt("Alarm_706");
					alarmLastStatus[706] = transLiResult.getInt("Alarm_707");
					alarmLastStatus[707] = transLiResult.getInt("Alarm_708");
					alarmLastStatus[708] = transLiResult.getInt("Alarm_709");
					alarmLastStatus[709] = transLiResult.getInt("Alarm_710");
					alarmLastStatus[710] = transLiResult.getInt("Alarm_711");
					alarmLastStatus[711] = transLiResult.getInt("Alarm_712");
					alarmLastStatus[712] = transLiResult.getInt("Alarm_713");
					alarmLastStatus[713] = transLiResult.getInt("Alarm_714");
					alarmLastStatus[714] = transLiResult.getInt("Alarm_715");
					alarmLastStatus[715] = transLiResult.getInt("Alarm_716");
					alarmLastStatus[716] = transLiResult.getInt("Alarm_717");
					alarmLastStatus[717] = transLiResult.getInt("Alarm_718");
					alarmLastStatus[718] = transLiResult.getInt("Alarm_719");
					alarmLastStatus[719] = transLiResult.getInt("Alarm_720");
					alarmLastStatus[720] = transLiResult.getInt("Alarm_721");
					alarmLastStatus[721] = transLiResult.getInt("Alarm_722");
					alarmLastStatus[722] = transLiResult.getInt("Alarm_723");
					alarmLastStatus[723] = transLiResult.getInt("Alarm_724");
					alarmLastStatus[724] = transLiResult.getInt("Alarm_725");
					alarmLastStatus[725] = transLiResult.getInt("Alarm_726");
					alarmLastStatus[726] = transLiResult.getInt("Alarm_727");
					alarmLastStatus[727] = transLiResult.getInt("Alarm_728");
					alarmLastStatus[728] = transLiResult.getInt("Alarm_729");
					alarmLastStatus[729] = transLiResult.getInt("Alarm_730");
					alarmLastStatus[730] = transLiResult.getInt("Alarm_731");
					alarmLastStatus[731] = transLiResult.getInt("Alarm_732");
					alarmLastStatus[732] = transLiResult.getInt("Alarm_733");
					alarmLastStatus[733] = transLiResult.getInt("Alarm_734");
					alarmLastStatus[734] = transLiResult.getInt("Alarm_735");
					alarmLastStatus[735] = transLiResult.getInt("Alarm_736");
					alarmLastStatus[736] = transLiResult.getInt("Alarm_737");
					alarmLastStatus[737] = transLiResult.getInt("Alarm_738");
					alarmLastStatus[738] = transLiResult.getInt("Alarm_739");
					alarmLastStatus[739] = transLiResult.getInt("Alarm_740");
					alarmLastStatus[740] = transLiResult.getInt("Alarm_741");
					alarmLastStatus[741] = transLiResult.getInt("Alarm_742");
					alarmLastStatus[742] = transLiResult.getInt("Alarm_743");
					alarmLastStatus[743] = transLiResult.getInt("Alarm_744");
					alarmLastStatus[744] = transLiResult.getInt("Alarm_745");
					alarmLastStatus[745] = transLiResult.getInt("Alarm_746");
					alarmLastStatus[746] = transLiResult.getInt("Alarm_747");
					alarmLastStatus[747] = transLiResult.getInt("Alarm_748");
					alarmLastStatus[748] = transLiResult.getInt("Alarm_749");
					alarmLastStatus[749] = transLiResult.getInt("Alarm_750");
					alarmLastStatus[750] = transLiResult.getInt("Alarm_751");
					alarmLastStatus[751] = transLiResult.getInt("Alarm_752");
					alarmLastStatus[752] = transLiResult.getInt("Alarm_753");
					alarmLastStatus[753] = transLiResult.getInt("Alarm_754");
					alarmLastStatus[754] = transLiResult.getInt("Alarm_755");
					alarmLastStatus[755] = transLiResult.getInt("Alarm_756");
					alarmLastStatus[756] = transLiResult.getInt("Alarm_757");
					alarmLastStatus[757] = transLiResult.getInt("Alarm_758");
					alarmLastStatus[758] = transLiResult.getInt("Alarm_759");
					alarmLastStatus[759] = transLiResult.getInt("Alarm_760");
					alarmLastStatus[760] = transLiResult.getInt("Alarm_761");
					alarmLastStatus[761] = transLiResult.getInt("Alarm_762");
					alarmLastStatus[762] = transLiResult.getInt("Alarm_763");
					alarmLastStatus[763] = transLiResult.getInt("Alarm_764");
					alarmLastStatus[764] = transLiResult.getInt("Alarm_765");
					alarmLastStatus[765] = transLiResult.getInt("Alarm_766");
					alarmLastStatus[766] = transLiResult.getInt("Alarm_767");
					alarmLastStatus[767] = transLiResult.getInt("Alarm_768");
					alarmLastStatus[768] = transLiResult.getInt("Alarm_769");
					alarmLastStatus[769] = transLiResult.getInt("Alarm_770");
					alarmLastStatus[770] = transLiResult.getInt("Alarm_771");
					alarmLastStatus[771] = transLiResult.getInt("Alarm_772");
					alarmLastStatus[772] = transLiResult.getInt("Alarm_773");
					alarmLastStatus[773] = transLiResult.getInt("Alarm_774");
					alarmLastStatus[774] = transLiResult.getInt("Alarm_775");
					alarmLastStatus[775] = transLiResult.getInt("Alarm_776");
					alarmLastStatus[776] = transLiResult.getInt("Alarm_777");
					alarmLastStatus[777] = transLiResult.getInt("Alarm_778");
					alarmLastStatus[778] = transLiResult.getInt("Alarm_779");
					alarmLastStatus[779] = transLiResult.getInt("Alarm_780");
					alarmLastStatus[780] = transLiResult.getInt("Alarm_781");
					alarmLastStatus[781] = transLiResult.getInt("Alarm_782");
					alarmLastStatus[782] = transLiResult.getInt("Alarm_783");
					alarmLastStatus[783] = transLiResult.getInt("Alarm_784");
					alarmLastStatus[784] = transLiResult.getInt("Alarm_785");
					alarmLastStatus[785] = transLiResult.getInt("Alarm_786");
					alarmLastStatus[786] = transLiResult.getInt("Alarm_787");
					alarmLastStatus[787] = transLiResult.getInt("Alarm_788");
					alarmLastStatus[788] = transLiResult.getInt("Alarm_789");
					alarmLastStatus[789] = transLiResult.getInt("Alarm_790");
					alarmLastStatus[790] = transLiResult.getInt("Alarm_791");
					alarmLastStatus[791] = transLiResult.getInt("Alarm_792");
					alarmLastStatus[792] = transLiResult.getInt("Alarm_793");
					alarmLastStatus[793] = transLiResult.getInt("Alarm_794");
					alarmLastStatus[794] = transLiResult.getInt("Alarm_795");
					alarmLastStatus[795] = transLiResult.getInt("Alarm_796");
					alarmLastStatus[796] = transLiResult.getInt("Alarm_797");
					alarmLastStatus[797] = transLiResult.getInt("Alarm_798");
					alarmLastStatus[798] = transLiResult.getInt("Alarm_799");
					alarmLastStatus[799] = transLiResult.getInt("Alarm_800");
					alarmLastStatus[800] = transLiResult.getInt("Alarm_801");
					alarmLastStatus[801] = transLiResult.getInt("Alarm_802");
					alarmLastStatus[802] = transLiResult.getInt("Alarm_803");
					alarmLastStatus[803] = transLiResult.getInt("Alarm_804");
					alarmLastStatus[804] = transLiResult.getInt("Alarm_805");
					alarmLastStatus[805] = transLiResult.getInt("Alarm_806");
					alarmLastStatus[806] = transLiResult.getInt("Alarm_807");
					alarmLastStatus[807] = transLiResult.getInt("Alarm_808");
					alarmLastStatus[808] = transLiResult.getInt("Alarm_809");
					alarmLastStatus[809] = transLiResult.getInt("Alarm_810");
					alarmLastStatus[810] = transLiResult.getInt("Alarm_811");
					alarmLastStatus[811] = transLiResult.getInt("Alarm_812");
					alarmLastStatus[812] = transLiResult.getInt("Alarm_813");
					alarmLastStatus[813] = transLiResult.getInt("Alarm_814");
					alarmLastStatus[814] = transLiResult.getInt("Alarm_815");
					alarmLastStatus[815] = transLiResult.getInt("Alarm_816");
					alarmLastStatus[816] = transLiResult.getInt("Alarm_817");
					alarmLastStatus[817] = transLiResult.getInt("Alarm_818");
					alarmLastStatus[818] = transLiResult.getInt("Alarm_819");
					alarmLastStatus[819] = transLiResult.getInt("Alarm_820");
					alarmLastStatus[820] = transLiResult.getInt("Alarm_821");
					alarmLastStatus[821] = transLiResult.getInt("Alarm_822");
					alarmLastStatus[822] = transLiResult.getInt("Alarm_823");
					alarmLastStatus[823] = transLiResult.getInt("Alarm_824");
					alarmLastStatus[824] = transLiResult.getInt("Alarm_825");
					alarmLastStatus[825] = transLiResult.getInt("Alarm_826");
					alarmLastStatus[826] = transLiResult.getInt("Alarm_827");
					alarmLastStatus[827] = transLiResult.getInt("Alarm_828");
					alarmLastStatus[828] = transLiResult.getInt("Alarm_829");
					alarmLastStatus[829] = transLiResult.getInt("Alarm_830");
					alarmLastStatus[830] = transLiResult.getInt("Alarm_831");
					alarmLastStatus[831] = transLiResult.getInt("Alarm_832");
					alarmLastStatus[832] = transLiResult.getInt("Alarm_833");
					alarmLastStatus[833] = transLiResult.getInt("Alarm_834");
					alarmLastStatus[834] = transLiResult.getInt("Alarm_835");
					alarmLastStatus[835] = transLiResult.getInt("Alarm_836");
					alarmLastStatus[836] = transLiResult.getInt("Alarm_837");
					alarmLastStatus[837] = transLiResult.getInt("Alarm_838");
					alarmLastStatus[838] = transLiResult.getInt("Alarm_839");
					alarmLastStatus[839] = transLiResult.getInt("Alarm_840");
					alarmLastStatus[840] = transLiResult.getInt("Alarm_841");
					alarmLastStatus[841] = transLiResult.getInt("Alarm_842");
					alarmLastStatus[842] = transLiResult.getInt("Alarm_843");
					alarmLastStatus[843] = transLiResult.getInt("Alarm_844");
					alarmLastStatus[844] = transLiResult.getInt("Alarm_845");
					alarmLastStatus[845] = transLiResult.getInt("Alarm_846");
					alarmLastStatus[846] = transLiResult.getInt("Alarm_847");
					alarmLastStatus[847] = transLiResult.getInt("Alarm_848");
					alarmLastStatus[848] = transLiResult.getInt("Alarm_849");
					alarmLastStatus[849] = transLiResult.getInt("Alarm_850");
					alarmLastStatus[850] = transLiResult.getInt("Alarm_851");
					alarmLastStatus[851] = transLiResult.getInt("Alarm_852");
					alarmLastStatus[852] = transLiResult.getInt("Alarm_853");
					alarmLastStatus[853] = transLiResult.getInt("Alarm_854");
					alarmLastStatus[854] = transLiResult.getInt("Alarm_855");
					alarmLastStatus[855] = transLiResult.getInt("Alarm_856");
					alarmLastStatus[856] = transLiResult.getInt("Alarm_857");
					alarmLastStatus[857] = transLiResult.getInt("Alarm_858");
					alarmLastStatus[858] = transLiResult.getInt("Alarm_859");
					alarmLastStatus[859] = transLiResult.getInt("Alarm_860");
					alarmLastStatus[860] = transLiResult.getInt("Alarm_861");
					alarmLastStatus[861] = transLiResult.getInt("Alarm_862");
					alarmLastStatus[862] = transLiResult.getInt("Alarm_863");
					alarmLastStatus[863] = transLiResult.getInt("Alarm_864");
					alarmLastStatus[864] = transLiResult.getInt("Alarm_865");
					alarmLastStatus[865] = transLiResult.getInt("Alarm_866");
					alarmLastStatus[866] = transLiResult.getInt("Alarm_867");
					alarmLastStatus[867] = transLiResult.getInt("Alarm_868");
					alarmLastStatus[868] = transLiResult.getInt("Alarm_869");
					alarmLastStatus[869] = transLiResult.getInt("Alarm_870");
					alarmLastStatus[870] = transLiResult.getInt("Alarm_871");
					alarmLastStatus[871] = transLiResult.getInt("Alarm_872");
					alarmLastStatus[872] = transLiResult.getInt("Alarm_873");
					alarmLastStatus[873] = transLiResult.getInt("Alarm_874");
					alarmLastStatus[874] = transLiResult.getInt("Alarm_875");
					alarmLastStatus[875] = transLiResult.getInt("Alarm_876");
					alarmLastStatus[876] = transLiResult.getInt("Alarm_877");
					alarmLastStatus[877] = transLiResult.getInt("Alarm_878");
					alarmLastStatus[878] = transLiResult.getInt("Alarm_879");
					alarmLastStatus[879] = transLiResult.getInt("Alarm_880");
					alarmLastStatus[880] = transLiResult.getInt("Alarm_881");
					alarmLastStatus[881] = transLiResult.getInt("Alarm_882");
					alarmLastStatus[882] = transLiResult.getInt("Alarm_883");
					alarmLastStatus[883] = transLiResult.getInt("Alarm_884");
					alarmLastStatus[884] = transLiResult.getInt("Alarm_885");
					alarmLastStatus[885] = transLiResult.getInt("Alarm_886");
					alarmLastStatus[886] = transLiResult.getInt("Alarm_887");
					alarmLastStatus[887] = transLiResult.getInt("Alarm_888");
					alarmLastStatus[888] = transLiResult.getInt("Alarm_889");
					alarmLastStatus[889] = transLiResult.getInt("Alarm_890");
					alarmLastStatus[890] = transLiResult.getInt("Alarm_891");
					alarmLastStatus[891] = transLiResult.getInt("Alarm_892");
					alarmLastStatus[892] = transLiResult.getInt("Alarm_893");
					alarmLastStatus[893] = transLiResult.getInt("Alarm_894");
					alarmLastStatus[894] = transLiResult.getInt("Alarm_895");
					alarmLastStatus[895] = transLiResult.getInt("Alarm_896");
					alarmLastStatus[896] = transLiResult.getInt("Alarm_897");
					alarmLastStatus[897] = transLiResult.getInt("Alarm_898");
					alarmLastStatus[898] = transLiResult.getInt("Alarm_899");
					alarmLastStatus[899] = transLiResult.getInt("Alarm_900");
					alarmLastStatus[900] = transLiResult.getInt("Alarm_901");
					alarmLastStatus[901] = transLiResult.getInt("Alarm_902");
					alarmLastStatus[902] = transLiResult.getInt("Alarm_903");
					alarmLastStatus[903] = transLiResult.getInt("Alarm_904");
					alarmLastStatus[904] = transLiResult.getInt("Alarm_905");
					alarmLastStatus[905] = transLiResult.getInt("Alarm_906");
					alarmLastStatus[906] = transLiResult.getInt("Alarm_907");
					alarmLastStatus[907] = transLiResult.getInt("Alarm_908");
					alarmLastStatus[908] = transLiResult.getInt("Alarm_909");
					alarmLastStatus[909] = transLiResult.getInt("Alarm_910");
					alarmLastStatus[910] = transLiResult.getInt("Alarm_911");
					alarmLastStatus[911] = transLiResult.getInt("Alarm_912");
					alarmLastStatus[912] = transLiResult.getInt("Alarm_913");
					alarmLastStatus[913] = transLiResult.getInt("Alarm_914");
					alarmLastStatus[914] = transLiResult.getInt("Alarm_915");
					alarmLastStatus[915] = transLiResult.getInt("Alarm_916");
					alarmLastStatus[916] = transLiResult.getInt("Alarm_917");
					alarmLastStatus[917] = transLiResult.getInt("Alarm_918");
					alarmLastStatus[918] = transLiResult.getInt("Alarm_919");
					alarmLastStatus[919] = transLiResult.getInt("Alarm_920");
					alarmLastStatus[920] = transLiResult.getInt("Alarm_921");
					alarmLastStatus[921] = transLiResult.getInt("Alarm_922");
					alarmLastStatus[922] = transLiResult.getInt("Alarm_923");
					alarmLastStatus[923] = transLiResult.getInt("Alarm_924");
					alarmLastStatus[924] = transLiResult.getInt("Alarm_925");
					alarmLastStatus[925] = transLiResult.getInt("Alarm_926");
					alarmLastStatus[926] = transLiResult.getInt("Alarm_927");
					alarmLastStatus[927] = transLiResult.getInt("Alarm_928");
					alarmLastStatus[928] = transLiResult.getInt("Alarm_929");
					alarmLastStatus[929] = transLiResult.getInt("Alarm_930");
					alarmLastStatus[930] = transLiResult.getInt("Alarm_931");
					alarmLastStatus[931] = transLiResult.getInt("Alarm_932");
					alarmLastStatus[932] = transLiResult.getInt("Alarm_933");
					alarmLastStatus[933] = transLiResult.getInt("Alarm_934");
					alarmLastStatus[934] = transLiResult.getInt("Alarm_935");
					alarmLastStatus[935] = transLiResult.getInt("Alarm_936");
					alarmLastStatus[936] = transLiResult.getInt("Alarm_937");
					alarmLastStatus[937] = transLiResult.getInt("Alarm_938");
					alarmLastStatus[938] = transLiResult.getInt("Alarm_939");
					alarmLastStatus[939] = transLiResult.getInt("Alarm_940");
					alarmLastStatus[940] = transLiResult.getInt("Alarm_941");
					alarmLastStatus[941] = transLiResult.getInt("Alarm_942");
					alarmLastStatus[942] = transLiResult.getInt("Alarm_943");
					alarmLastStatus[943] = transLiResult.getInt("Alarm_944");
					alarmLastStatus[944] = transLiResult.getInt("Alarm_945");
					alarmLastStatus[945] = transLiResult.getInt("Alarm_946");
					alarmLastStatus[946] = transLiResult.getInt("Alarm_947");
					alarmLastStatus[947] = transLiResult.getInt("Alarm_948");
					alarmLastStatus[948] = transLiResult.getInt("Alarm_949");
					alarmLastStatus[949] = transLiResult.getInt("Alarm_950");
					alarmLastStatus[950] = transLiResult.getInt("Alarm_951");
					alarmLastStatus[951] = transLiResult.getInt("Alarm_952");
					alarmLastStatus[952] = transLiResult.getInt("Alarm_953");
					alarmLastStatus[953] = transLiResult.getInt("Alarm_954");
					alarmLastStatus[954] = transLiResult.getInt("Alarm_955");
					alarmLastStatus[955] = transLiResult.getInt("Alarm_956");
					alarmLastStatus[956] = transLiResult.getInt("Alarm_957");
					alarmLastStatus[957] = transLiResult.getInt("Alarm_958");
					alarmLastStatus[958] = transLiResult.getInt("Alarm_959");
					alarmLastStatus[959] = transLiResult.getInt("Alarm_960");
					alarmLastStatus[960] = transLiResult.getInt("Alarm_961");
					alarmLastStatus[961] = transLiResult.getInt("Alarm_962");
					alarmLastStatus[962] = transLiResult.getInt("Alarm_963");
					alarmLastStatus[963] = transLiResult.getInt("Alarm_964");
					alarmLastStatus[964] = transLiResult.getInt("Alarm_965");
					alarmLastStatus[965] = transLiResult.getInt("Alarm_966");
					alarmLastStatus[966] = transLiResult.getInt("Alarm_967");
					alarmLastStatus[967] = transLiResult.getInt("Alarm_968");
					alarmLastStatus[968] = transLiResult.getInt("Alarm_969");
					alarmLastStatus[969] = transLiResult.getInt("Alarm_970");
					alarmLastStatus[970] = transLiResult.getInt("Alarm_971");
					alarmLastStatus[971] = transLiResult.getInt("Alarm_972");
					alarmLastStatus[972] = transLiResult.getInt("Alarm_973");
					alarmLastStatus[973] = transLiResult.getInt("Alarm_974");
					alarmLastStatus[974] = transLiResult.getInt("Alarm_975");
					alarmLastStatus[975] = transLiResult.getInt("Alarm_976");
					alarmLastStatus[976] = transLiResult.getInt("Alarm_977");
					alarmLastStatus[977] = transLiResult.getInt("Alarm_978");
					alarmLastStatus[978] = transLiResult.getInt("Alarm_979");
					alarmLastStatus[979] = transLiResult.getInt("Alarm_980");
					alarmLastStatus[980] = transLiResult.getInt("Alarm_981");
					alarmLastStatus[981] = transLiResult.getInt("Alarm_982");
					alarmLastStatus[982] = transLiResult.getInt("Alarm_983");
					alarmLastStatus[983] = transLiResult.getInt("Alarm_984");
					alarmLastStatus[984] = transLiResult.getInt("Alarm_985");
					alarmLastStatus[985] = transLiResult.getInt("Alarm_986");
					alarmLastStatus[986] = transLiResult.getInt("Alarm_987");
					alarmLastStatus[987] = transLiResult.getInt("Alarm_988");
					alarmLastStatus[988] = transLiResult.getInt("Alarm_989");
					alarmLastStatus[989] = transLiResult.getInt("Alarm_990");
					alarmLastStatus[990] = transLiResult.getInt("Alarm_991");
					alarmLastStatus[991] = transLiResult.getInt("Alarm_992");
					alarmLastStatus[992] = transLiResult.getInt("Alarm_993");
					alarmLastStatus[993] = transLiResult.getInt("Alarm_994");
					alarmLastStatus[994] = transLiResult.getInt("Alarm_995");
					alarmLastStatus[995] = transLiResult.getInt("Alarm_996");
					alarmLastStatus[996] = transLiResult.getInt("Alarm_997");
					alarmLastStatus[997] = transLiResult.getInt("Alarm_998");
					alarmLastStatus[998] = transLiResult.getInt("Alarm_999");
					alarmLastStatus[999] = transLiResult.getInt("Alarm_1000");
					alarmLastStatus[1000] = transLiResult.getInt("Alarm_1001");
					alarmLastStatus[1001] = transLiResult.getInt("Alarm_1002");
					alarmLastStatus[1002] = transLiResult.getInt("Alarm_1003");
					alarmLastStatus[1003] = transLiResult.getInt("Alarm_1004");
					alarmLastStatus[1004] = transLiResult.getInt("Alarm_1005");
					alarmLastStatus[1005] = transLiResult.getInt("Alarm_1006");
					alarmLastStatus[1006] = transLiResult.getInt("Alarm_1007");
					alarmLastStatus[1007] = transLiResult.getInt("Alarm_1008");
					alarmLastStatus[1008] = transLiResult.getInt("Alarm_1009");
					alarmLastStatus[1009] = transLiResult.getInt("Alarm_1010");
					alarmLastStatus[1010] = transLiResult.getInt("Alarm_1011");
					alarmLastStatus[1011] = transLiResult.getInt("Alarm_1012");
					alarmLastStatus[1012] = transLiResult.getInt("Alarm_1013");
					alarmLastStatus[1013] = transLiResult.getInt("Alarm_1014");
					alarmLastStatus[1014] = transLiResult.getInt("Alarm_1015");
					alarmLastStatus[1015] = transLiResult.getInt("Alarm_1016");
					alarmLastStatus[1016] = transLiResult.getInt("Alarm_1017");
					alarmLastStatus[1017] = transLiResult.getInt("Alarm_1018");
					alarmLastStatus[1018] = transLiResult.getInt("Alarm_1019");
					alarmLastStatus[1019] = transLiResult.getInt("Alarm_1020");
					alarmLastStatus[1020] = transLiResult.getInt("Alarm_1021");
					alarmLastStatus[1021] = transLiResult.getInt("Alarm_1022");
					alarmLastStatus[1022] = transLiResult.getInt("Alarm_1023");
					alarmLastStatus[1023] = transLiResult.getInt("Alarm_1024");
					alarmLastStatus[1024] = transLiResult.getInt("Alarm_1025");
					alarmLastStatus[1025] = transLiResult.getInt("Alarm_1026");
					alarmLastStatus[1026] = transLiResult.getInt("Alarm_1027");
					alarmLastStatus[1027] = transLiResult.getInt("Alarm_1028");
					alarmLastStatus[1028] = transLiResult.getInt("Alarm_1029");
					alarmLastStatus[1029] = transLiResult.getInt("Alarm_1030");
					alarmLastStatus[1030] = transLiResult.getInt("Alarm_1031");
					alarmLastStatus[1031] = transLiResult.getInt("Alarm_1032");
					alarmLastStatus[1032] = transLiResult.getInt("Alarm_1033");
					alarmLastStatus[1033] = transLiResult.getInt("Alarm_1034");
					alarmLastStatus[1034] = transLiResult.getInt("Alarm_1035");
					alarmLastStatus[1035] = transLiResult.getInt("Alarm_1036");
					alarmLastStatus[1036] = transLiResult.getInt("Alarm_1037");
					alarmLastStatus[1037] = transLiResult.getInt("Alarm_1038");
					alarmLastStatus[1038] = transLiResult.getInt("Alarm_1039");
					alarmLastStatus[1039] = transLiResult.getInt("Alarm_1040");
					alarmLastStatus[1040] = transLiResult.getInt("Alarm_1041");
					alarmLastStatus[1041] = transLiResult.getInt("Alarm_1042");
					alarmLastStatus[1042] = transLiResult.getInt("Alarm_1043");
					alarmLastStatus[1043] = transLiResult.getInt("Alarm_1044");
					alarmLastStatus[1044] = transLiResult.getInt("Alarm_1045");
					alarmLastStatus[1045] = transLiResult.getInt("Alarm_1046");
					alarmLastStatus[1046] = transLiResult.getInt("Alarm_1047");
					alarmLastStatus[1047] = transLiResult.getInt("Alarm_1048");	
				}

				transLiResult.close();
				lirsObj.close();

				/* Compare current and last if mismatch then update*/

				pstmtObj.close();
				alarmUtil.insertIntoLiTransAlarmHistory(alarm,smSiteId,smSitetypeid,siteId,_recordtime,dbCreationTime,hpDate,connObj);
			}
			
			HashMap<String,String> alDescMap = new HashMap<String,String>();
			for(int i=0; i<alarmLastStatus.length; i++) {

				if(alarmLastStatus[i] != alarm[i]) {

					int pin=i+1;
					String coulmn = "Alarm_"+pin;
					int alarmStatus = alarm[i];
					
					if(pin>=409) {
						
//						String coulmn = "Alarm_"+pin;
//						int alarmStatus = alarm[i];
						pstmtObj = connObj.prepareStatement("UPDATE trans_ltmalarmlaststatus SET "+coulmn+" = "+alarmStatus+"  , alsTimestamp="+_recordtime+" WHERE smSiteID='"+smSiteId+"'");
						System.out.println("Update Query--"+pstmtObj);
						pstmtObj.close();
					}
					
					else {
					
					
					pstmtObj = connObj.prepareStatement("UPDATE trans_alarmlaststatus SET "+coulmn+" = "+alarmStatus+"  , alsTimestamp="+_recordtime+" WHERE smSiteID='"+smSiteId+"'");
					System.out.println("Update Query--"+pstmtObj);

					pstmtObj.executeUpdate();
					pstmtObj.close();
					System.out.println("Alarm Last Status Updated");
					}

					pstmtObj = connObj.prepareStatement("Select * from alarmlabel where alPinID='"+coulmn+"'");
					ResultSet alarmLabelResult = pstmtObj.executeQuery();

					while(alarmLabelResult.next()) {

						alDescMap.put(coulmn, alarmLabelResult.getString("alDesc"));
					}

					/*Take care of Trans_AlarmRecords for open alarms */
					pstmtObj = connObj.prepareStatement("INSERT INTO trans_alarmrecords(alName,alrOpenTime,alrCloseTime,smSiteCode,alrstatus,"
							+ "smSiteID,alrPinNumber,smSiteName,crClusterID,znZoneID,znZone,emEmpID,emEmployeeID,"
							+ "emFirstName,emContactNo,emEmail,crName,rgRegionID,rgRegion,alrTTCreationStatus,"
							+ "alrTTNumber,alPinCriticality,smSitetypeid,ttStatus,hpDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					int ttCreationRequired=0;
					int ttNumber=0;

						/**
						 * Check Alarm is eligible for TT creation
						 * 
						 */
						
						if(alrmList2.get("Alarm_"+String.valueOf(i+1)) ==1) {
							ttCreationRequired=1;
							ttNumber = getMaxTTNumber(smSiteId,"Alarm_"+String.valueOf(i+1),connObj);
						}
								
						if((alrmList1.get("Alarm_"+String.valueOf(i+1))==1)) {
					if(alarmStatus ==1){

						String alarmName = alrmList3.get("Alarm_"+(i+1));
						pstmtObj.setString(1, alarmName);
						pstmtObj.setLong(2, _recordtime);
						pstmtObj.setLong(3, 0);
						pstmtObj.setString(4, siteId);
						pstmtObj.setInt(5, 1);
						pstmtObj.setInt(6, smSiteId);
						pstmtObj.setString(7, "Alarm_"+String.valueOf(i+1));
						pstmtObj.setString(8, smSiteName);
						pstmtObj.setInt(9, crClusterID);
						pstmtObj.setInt(10, znZoneID);
						pstmtObj.setString(11, znZone);

						pstmtObj.setInt(12, emEmpID);
						pstmtObj.setString(13, emEmployeeID);
						pstmtObj.setString(14, emFirstName);
						pstmtObj.setString(15, emContactNo);
						pstmtObj.setString(16, emEmail);
						pstmtObj.setString(17, crName);
						pstmtObj.setInt(18, rgRegionID);
						pstmtObj.setString(19, rgRegion);
						pstmtObj.setInt(20, ttCreationRequired);
						pstmtObj.setInt(21, ttNumber);
						pstmtObj.setInt(22, alrmList4.get("Alarm_"+String.valueOf(i+1)));
						pstmtObj.setInt(23, smSitetypeid);
						pstmtObj.setInt(24, 1);
						pstmtObj.setDate(25, hpDate);
						
						System.out.println("Error------------------"+pstmtObj);
						int rsObj3 = pstmtObj.executeUpdate();
						System.out.println("Data Inserted :" +rsObj3);

					} else if(alarmStatus ==0) {
						String alrPinNumber = "Alarm_"+String.valueOf(i+1);
						pstmtObj = connObj.prepareStatement("UPDATE trans_alarmrecords SET alrstatus = 0, "
								+ "ttStatus=0, alrCloseTime = "+_recordtime+" WHERE smSiteID='"+smSiteId+"' and "
										+ "alrPinnumber= '"+alrPinNumber+"'");
						pstmtObj.executeUpdate();
					}
				}
				}
			}

			System.out.println("\n=====Releasing Connection Object To Pool=====\n");       

			pstmtObj.close();
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
					connObj=null;
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		jdbcObj.printDbStatus();
	}

	private char[] processThirtyTwo(String invStatus) {

		String preBin = new BigInteger(invStatus, 16).toString(2);
		Integer solarStatusMod1length = preBin.length();
		if (solarStatusMod1length < 32) {
			for (int i = 0; i < 32 - solarStatusMod1length; i++) {
				preBin = "0" + preBin;
			}
		}

		if(preBin.length()!=32) {

			int need = 32-preBin.length();
			for (int j = 1; j<=need; j++) {
				preBin = "0" + preBin;
			}
		}
		char[] alarmCharArray = new char[32];
		if(invStatus.equalsIgnoreCase("00")) {

			alarmCharArray = "00000000000000000000000000000000".toCharArray();
		} else {
			alarmCharArray = preBin.toCharArray();
		}
		return alarmCharArray;
	}


	private int getMaxTTNumber(int siteId,String alarmPin, Connection connObj) throws Exception {

		PreparedStatement pstmtObj = null;
		int msgId=0;
//		int intMsgId=0;
		try {   


			// Performing Database Operation!
			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			pstmtObj = connObj.prepareStatement("Select max(alrTTNumber) from trans_alarmrecords");

			ResultSet rsObj = pstmtObj.executeQuery();

			while(rsObj.next()){
				msgId = rsObj.getInt(1);
//				intMsgId = Integer.parseInt(msgId.replace("TT", ""));
			}

			rsObj.close();
			//			connObj.close();
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

		return msgId+1;
	}

	private char[] processInverterAlarm(String invStatus) {

		String preBin = new BigInteger(invStatus, 16).toString(2);
		Integer solarStatusMod1length = preBin.length();
		if (solarStatusMod1length < 8) {
			for (int i = 0; i < 8 - solarStatusMod1length; i++) {
				preBin = "0" + preBin;
			}
		}

		if(preBin.length()!=4) {

			int need = 4-preBin.length();
			for (int j = 1; j<=need; j++) {
				preBin = "0" + preBin;
			}
		}
		char[] alarmCharArray = new char[8];
		if(invStatus.equalsIgnoreCase("00")) {

			alarmCharArray = "00000000".toCharArray();
		} else {
			alarmCharArray = preBin.toCharArray();
		}
		return alarmCharArray;
	}
}

package com.digitrinity.parser.parserthread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.digitrinity.parser.dateutil.DateTime;
import com.digitrinity.parser.dbutil.ConnectionPool;

public class DataProcessor {

	private static final TimeZone gmtTimezone   = TimeZone.getTimeZone("GMT+6:30");
	private static Logger logger = LogManager.getLogger(DataProcessor.class.getName());

	public boolean parseData(String deviceData, int messageId) throws Exception {

		logger.debug("!! Started Message Parsing !!");
		String smSiteCode =  deviceData.substring(5, 14);
		String date = deviceData.substring(14, 16);
		String hour = deviceData.substring(16, 18);
		String minutes = deviceData.substring(18, 20);
		String seconds = deviceData.substring(20, 22);
		String month = deviceData.substring(22, 24);
		String year = deviceData.substring(24, 26);
		String alarmString = deviceData.substring(26, 42);
		String statusString = deviceData.substring(42, 50);
		String commStatus = deviceData.substring(50, 52);
		float battery_Temp3 = Float.parseFloat(deviceData.substring(52, 55));
		float battery_Temp4 = Float.parseFloat(deviceData.substring(55, 58));
		float battery_Temp1 = Float.parseFloat(deviceData.substring(58, 61));
		float battery_Temp2 = Float.parseFloat(deviceData.substring(61, 64));
		int fuelLevel = Integer.parseInt(deviceData.substring(64, 67));
		int fuelLevelltr = Integer.parseInt(deviceData.substring(67, 71));
		float siteBattVolt = Float.parseFloat(deviceData.substring(71, 74));
		float mdgRVolt = Float.parseFloat(deviceData.substring(74, 77));
		float mdgYVolt = Float.parseFloat(deviceData.substring(77, 80));
		float mdgBVolt = Float.parseFloat(deviceData.substring(80, 83));
		float mdgRCurrent = Float.parseFloat(deviceData.substring(83, 87));
		float mdgYCurrent = Float.parseFloat(deviceData.substring(87, 91));
		float mdgBCurrent = Float.parseFloat(deviceData.substring(91, 95));
		float mdgRPf = Float.parseFloat(deviceData.substring(95, 98));
		float mdgyPF = Float.parseFloat(deviceData.substring(98, 101));
		float mdgBPf = Float.parseFloat(deviceData.substring(101, 104));
		float mdgRPower = Float.parseFloat(deviceData.substring(104, 107));
		float mdgyPower = Float.parseFloat(deviceData.substring(107, 110));
		float mdgBPower = Float.parseFloat(deviceData.substring(110, 113));
		float mdgFrequency = Float.parseFloat(deviceData.substring(113, 116));
		float dgRVoltage = Float.parseFloat(deviceData.substring(116, 119));
		float dgYVoltage = Float.parseFloat(deviceData.substring(119, 122));
		float dgBVoltage = Float.parseFloat(deviceData.substring(122, 125));
		float dgRCurrent = Float.parseFloat(deviceData.substring(125, 129));
		float dgYCurrent = Float.parseFloat(deviceData.substring(129, 133));
		float dgBCurrent = Float.parseFloat(deviceData.substring(133, 137));
		float dgRPf = Float.parseFloat(deviceData.substring(137, 140));
		float dgYPf = Float.parseFloat(deviceData.substring(140, 143));
		float dgBPf = Float.parseFloat(deviceData.substring(143, 146));
		float dgRPower = Float.parseFloat(deviceData.substring(146, 149));
		float dgYPower = Float.parseFloat(deviceData.substring(149, 152));
		float dgBPower = Float.parseFloat(deviceData.substring(152, 155));
		float dgFrequency = Float.parseFloat(deviceData.substring(155, 158));
		float dgBattVolt = Float.parseFloat(deviceData.substring(158, 161));
		float dgTankCapacity = Float.parseFloat(deviceData.substring(161, 166));
		float batteryChargeCurrent = Float.parseFloat(deviceData.substring(166, 170));
		float batteryDisChargeCurrent = Float.parseFloat(deviceData.substring(170, 174));
		int batterySOC = Integer.parseInt(deviceData.substring(174, 177));
		int batteryCycleCount = Integer.parseInt(deviceData.substring(177, 182));
		int batteryCapacityinAh = Integer.parseInt(deviceData.substring(182, 186));
		float batteryNextFullChgItem = Float.parseFloat(deviceData.substring(186, 192));
		int noOfBatteryString = Integer.parseInt(deviceData.substring(192, 194));
		float mdgRunningHr = Float.parseFloat(deviceData.substring(194, 202));
		float dgRunningHr = Float.parseFloat(deviceData.substring(202, 210));
		float battRunningHr = Float.parseFloat(deviceData.substring(210, 218));
		float mdgEnergy = Float.parseFloat(deviceData.substring(218, 226));
		float dgEnergy = Float.parseFloat(deviceData.substring(226, 234));
		float battDisEnergy = Float.parseFloat(deviceData.substring(234, 242));
		float battChgEnergy = Float.parseFloat(deviceData.substring(242, 250));
		int rectInRVol = Integer.parseInt(deviceData.substring(250, 253));
		int rectInYVol = Integer.parseInt(deviceData.substring(253, 256));
		int rectInBVol = Integer.parseInt(deviceData.substring(256, 259));
		float rectInRCurr = Float.parseFloat(deviceData.substring(259, 263));
		float rectInYCurr = Float.parseFloat(deviceData.substring(263, 267));
		float rectInBCurr = Float.parseFloat(deviceData.substring(267, 271));
		float rectOutV = Float.parseFloat(deviceData.substring(271, 274));
		int noOfRec = Integer.parseInt(deviceData.substring(274, 276));
		float totRecOpCurr = Float.parseFloat(deviceData.substring(276, 280));
		float totDcLoadCurr = Float.parseFloat(deviceData.substring(280, 284));
		float totDcLoadEnrgy = Float.parseFloat(deviceData.substring(284, 292));
		float opc1LoadCurrent = Float.parseFloat(deviceData.substring(292, 296));
		float opc2LoadCurrent = Float.parseFloat(deviceData.substring(296, 300));
		float opc3LoadCurrent = Float.parseFloat(deviceData.substring(300, 304));
		float opc4LoadCurrent = Float.parseFloat(deviceData.substring(304, 308));
		float opc1Energy = Float.parseFloat(deviceData.substring(308, 316));
		float opc2Energy = Float.parseFloat(deviceData.substring(316, 324));
		float opc3Energy = Float.parseFloat(deviceData.substring(324, 332));
		float opc4Energy = Float.parseFloat(deviceData.substring(332, 340));
		float pvIpVoltMpod1 = Float.parseFloat(deviceData.substring(340, 344));
		float pvIpCurrMpod1 = Float.parseFloat(deviceData.substring(344, 347));
		float pvIpPowMpod1 = Float.parseFloat(deviceData.substring(347, 350));
		float pvOpVoltMpod1 = Float.parseFloat(deviceData.substring(350, 353));
		float pvOpCurrMpod1 = Float.parseFloat(deviceData.substring(353, 356));
		float pvOpPowMpod1 = Float.parseFloat(deviceData.substring(356, 359));
		String solarStatusMod1 = deviceData.substring(359, 361);
		float pvIpVoltMod2 = Float.parseFloat(deviceData.substring(361, 365));
		float pvIpCurMod2 = Float.parseFloat(deviceData.substring(365, 368));
		float pvIpPowMod2 = Float.parseFloat(deviceData.substring(368, 371));
		float pvOpVoltMod2 = Float.parseFloat(deviceData.substring(371, 374));
		float pvOpCurrMod2 = Float.parseFloat(deviceData.substring(374, 377));
		float pvOpPowMod2 = Float.parseFloat(deviceData.substring(377, 380));
		String solarStatusMod2 =deviceData.substring(380, 382);
		float pvIpVoltMpod3 = Float.parseFloat(deviceData.substring(382, 386));
		float pvIpCurrMpod3 = Float.parseFloat(deviceData.substring(386, 389));
		float pvIpPowMpod3 = Float.parseFloat(deviceData.substring(389, 392));
		float pvOpVoltMpod3 = Float.parseFloat(deviceData.substring(392, 395));
		float pvOpCurrMpod3 = Float.parseFloat(deviceData.substring(395, 398));
		float pvOpPowMpod3 = Float.parseFloat(deviceData.substring(398, 401));
		String solarStatusMod3 = deviceData.substring(401, 403);
		float pvIpVoltMpod4 = Float.parseFloat(deviceData.substring(403, 407));
		float pvIpCurrMpod4 = Float.parseFloat(deviceData.substring(407, 410));
		float pvIpPowMpod4 = Float.parseFloat(deviceData.substring(410, 413));
		float pvOpVoltMpod4 = Float.parseFloat(deviceData.substring(413, 416));
		float pvOpCurrMpod4 = Float.parseFloat(deviceData.substring(416, 419));
		float pvOpPowMpod4 = Float.parseFloat(deviceData.substring(419, 422));
		String solarStatusMod4 = deviceData.substring(422, 424);
		float pvIpVoltMpod5 = Float.parseFloat(deviceData.substring(424, 428));
		float pvIpCurrMpod5 = Float.parseFloat(deviceData.substring(428, 431));
		float pvIpPowMpod5 = Float.parseFloat(deviceData.substring(431, 434));
		float pvOpVoltMpod5 = Float.parseFloat(deviceData.substring(434, 437));
		float pvOpCurrMpod5 = Float.parseFloat(deviceData.substring(437, 440));
		float pvOpPowMpod5 = Float.parseFloat(deviceData.substring(440, 443));
		String solarStatusMod5 =deviceData.substring(443, 445);
		float pvIpVoltMpod6 = Float.parseFloat(deviceData.substring(445, 449));
		float pvIpCurrMpod6 = Float.parseFloat(deviceData.substring(449, 452));
		float pvIpPowMpod6 = Float.parseFloat(deviceData.substring(452, 455));
		float pvOpVoltMpod6 = Float.parseFloat(deviceData.substring(455, 458));
		float pvOpCurrMpod6 = Float.parseFloat(deviceData.substring(458, 461));
		float pvOpPowMpod6 = Float.parseFloat(deviceData.substring(461, 464));
		String solarStatusMod6 = deviceData.substring(464, 466);
		float pvIpVoltMpod7 = Float.parseFloat(deviceData.substring(466, 470));
		float pvIpCurrMpod7 = Float.parseFloat(deviceData.substring(470, 473));
		float pvIpPowMpod7 = Float.parseFloat(deviceData.substring(473, 476));
		float pvOpVoltMpod7 = Float.parseFloat(deviceData.substring(476, 479));
		float pvOpCurrMpod7 = Float.parseFloat(deviceData.substring(479, 482));
		float pvOpPowMpod7 = Float.parseFloat(deviceData.substring(482, 485));
		String solarStatusMod7 = deviceData.substring(485, 487);
		float pvIpVoltMpod8 = Float.parseFloat(deviceData.substring(487, 491));

		float pvIpCurrMpod8 = Float.parseFloat(deviceData.substring(491, 494));
		float pvIpPowMpod8 = Float.parseFloat(deviceData.substring(494, 497));
		float pvOpVoltMpod8 = Float.parseFloat(deviceData.substring(497, 500));
		float pvOpCurrMpod8 = Float.parseFloat(deviceData.substring(500, 503));
		float pvOpPowMpod8 = Float.parseFloat(deviceData.substring(503, 506));
		String solarStatusMod8 = deviceData.substring(506, 508);

		float pvIpVoltMpod9 = Float.parseFloat(deviceData.substring(508, 512));
		float pvIpCurrMpod9 = Float.parseFloat(deviceData.substring(512, 515));
		float pvIpPowMpod9 = Float.parseFloat(deviceData.substring(515, 518));
		float pvOpVoltMpod9 = Float.parseFloat(deviceData.substring(518, 521));
		float pvOpCurrMpod9 = Float.parseFloat(deviceData.substring(521, 524));
		float pvOpPowMpod9 = Float.parseFloat(deviceData.substring(524, 527));
		String solarStatusMod9 = deviceData.substring(527, 529);
		float pvIpVoltMpod10 = Float.parseFloat(deviceData.substring(529, 533));
		float pvIpCurrMpod10 = Float.parseFloat(deviceData.substring(533, 536));
		float pvIpPowMpod10 = Float.parseFloat(deviceData.substring(536, 539));
		float pvOpVoltMpod10 = Float.parseFloat(deviceData.substring(539, 542));
		float pvOpCurrMpod10 = Float.parseFloat(deviceData.substring(542, 545));
		float pvOpPowMpod10 = Float.parseFloat(deviceData.substring(545, 548));
		String solarStatusMod10 = deviceData.substring(548, 550);
		float pvIpVoltMpod11 = Float.parseFloat(deviceData.substring(550, 554));
		float pvIpCurrMpod11 = Float.parseFloat(deviceData.substring(554, 557));
		float pvIpPowMpod11 = Float.parseFloat(deviceData.substring(557, 560));
		float pvOpVoltMpod11 = Float.parseFloat(deviceData.substring(560, 563));
		float pvOpCurrMpod11 = Float.parseFloat(deviceData.substring(563, 566));
		float pvOpPowMpod11 = Float.parseFloat(deviceData.substring(566, 569));
		String solarStatusMod11 =deviceData.substring(569, 571);
		float pvIpVoltMpod12 = Float.parseFloat(deviceData.substring(571, 575));
		float pvIpCurrMpod12 = Float.parseFloat(deviceData.substring(575, 578));
		float pvIpPowMpod12 = Float.parseFloat(deviceData.substring(578, 581));
		float pvOpVoltMpod12 = Float.parseFloat(deviceData.substring(581, 584));
		float pvOpCurrMpod12 = Float.parseFloat(deviceData.substring(584, 587));
		float pvOpPowMpod12 = Float.parseFloat(deviceData.substring(587, 590));
		String solarStatusMod12 = deviceData.substring(590, 592);
		int noOfSolarModule = Integer.parseInt(deviceData.substring(592, 594));
		float solarIpEnergy = Float.parseFloat(deviceData.substring(594, 602));
		float solarOpEnergy = Float.parseFloat(deviceData.substring(602, 610));
		int solarRunHour = Integer.parseInt(deviceData.substring(610, 618));
		int solarMdgRunHour = Integer.parseInt(deviceData.substring(618, 626));
		int solardgRunHour = Integer.parseInt(deviceData.substring(626, 634));
		int solarBattRunHour = Integer.parseInt(deviceData.substring(634, 642));
		String powerSource = deviceData.substring(642, 643);
		String reserved1 = deviceData.substring(643, 650);
		String reserved2 = deviceData.substring(650, 653);
		String reserved3 = deviceData.substring(653, 656);
		String reserved4 = deviceData.substring(656, 659);
		float solarTotIpPower = Float.parseFloat(deviceData.substring(659, 663));
		float solarTotOpPower = Float.parseFloat(deviceData.substring(663, 667));
		String reserved5 = deviceData.substring(667, 671);
		String reserved6 = deviceData.substring(671, 675);
		String sysOutLoadStatus = deviceData.substring(675, 679);
		int mobileDGRunHr = Integer.parseInt(deviceData.substring(679, 687));
		int teleRunHr = Integer.parseInt(deviceData.substring(687, 695));
		float teleEnergy = Float.parseFloat(deviceData.substring(695, 703));
		String reserved7 = deviceData.substring(703, 705);
		float acLoadRVolt = Float.parseFloat(deviceData.substring(705, 708));
		float acLoadYVolt = Float.parseFloat(deviceData.substring(708, 711));
		float acLoadBVolt = Float.parseFloat(deviceData.substring(711, 714));
		float acLoadRCurrent = Float.parseFloat(deviceData.substring(714, 718));
		float acLoadYCurrent = Float.parseFloat(deviceData.substring(718, 722));
		float acLoadBCurrent = Float.parseFloat(deviceData.substring(722, 726));
		float acLoadRPF = Float.parseFloat(deviceData.substring(726, 729));
		float acLoadYPF = Float.parseFloat(deviceData.substring(729, 732));
		float acLoadBPF = Float.parseFloat(deviceData.substring(732, 735));
		float acLoadRPower = Float.parseFloat(deviceData.substring(735, 738));
		float acLoadYPower = Float.parseFloat(deviceData.substring(738, 741));
		float acLoadBPower = Float.parseFloat(deviceData.substring(741, 744));
		float acLoadFreq = Float.parseFloat(deviceData.substring(744, 747));
		int acLoadRunHr = Integer.parseInt(deviceData.substring(747, 755));
		float acLoadEnergy = Float.parseFloat(deviceData.substring(755, 763));
		float invDcInVolt1 = Float.parseFloat(deviceData.substring(763, 766));
		float invDcInCurrent1 = Float.parseFloat(deviceData.substring(766, 770));
		float invAcOpVolt1 = Float.parseFloat(deviceData.substring(770, 773));
		float invAcOpCurrent1 = Float.parseFloat(deviceData.substring(773, 776));
		float invAcOpPower1 = Float.parseFloat(deviceData.substring(776, 779));
		float invTemp1 = Float.parseFloat(deviceData.substring(779, 782));
		String invStatus1 = deviceData.substring(782, 784);
		float invDcInVolt2 = Float.parseFloat(deviceData.substring(784, 787));
		float invDcInCurrent2 = Float.parseFloat(deviceData.substring(787, 791));
		float invAcOpVolt2 = Float.parseFloat(deviceData.substring(791, 794));
		float invAcOpCurrent2 = Float.parseFloat(deviceData.substring(794, 797));
		float invAcOpPower2 = Float.parseFloat(deviceData.substring(797, 800));
		float invTemp2 = Float.parseFloat(deviceData.substring(800, 803));
		String invStatus2 = deviceData.substring(803, 805);
		float invDcInVolt3 = Float.parseFloat(deviceData.substring(805, 808));
		float invDcInCurrent3 = Float.parseFloat(deviceData.substring(808, 812));
		float invAcOpVolt3 = Float.parseFloat(deviceData.substring(812, 815));
		float invAcOpCurrent3 = Float.parseFloat(deviceData.substring(815, 818));
		float invAcOpPower3 = Float.parseFloat(deviceData.substring(818, 821));
		float invTemp3 = Float.parseFloat(deviceData.substring(821, 824));
		String invStatus3 = deviceData.substring(824, 826);
		float invDcInVolt4 = Float.parseFloat(deviceData.substring(826, 829));
		float invDcInCurrent4 = Float.parseFloat(deviceData.substring(829, 833));
		float invAcOpVolt4 = Float.parseFloat(deviceData.substring(833, 836));
		float invAcOpCurrent4 = Float.parseFloat(deviceData.substring(836, 839));
		float invAcOpPower4 = Float.parseFloat(deviceData.substring(839, 842));
		float invTemp4 = Float.parseFloat(deviceData.substring(842, 845));
		String invStatus4 = deviceData.substring(845, 847);
		float invDcInVolt5 = Float.parseFloat(deviceData.substring(847, 850));
		float invDcInCurrent5 = Float.parseFloat(deviceData.substring(850, 854));
		float invAcOpVolt5 = Float.parseFloat(deviceData.substring(854, 857));
		float invAcOpCurrent5 = Float.parseFloat(deviceData.substring(857, 860));
		float invAcOpPower5 = Float.parseFloat(deviceData.substring(860, 863));
		float invTemp5 = Float.parseFloat(deviceData.substring(863, 866));
		String invStatus5 = deviceData.substring(866, 868);
		float invDcInVolt6 = Float.parseFloat(deviceData.substring(868, 871));
		float invDcInCurrent6 = Float.parseFloat(deviceData.substring(871, 875));
		float invAcOpVolt6 = Float.parseFloat(deviceData.substring(875, 878));
		float invAcOpCurrent6 = Float.parseFloat(deviceData.substring(878, 881));
		float invAcOpPower6 = Float.parseFloat(deviceData.substring(881, 884));
		float invTemp6 = Float.parseFloat(deviceData.substring(884, 887));
		String invStatus6 = deviceData.substring(887, 889);
		float invDcInVolt7 = Float.parseFloat(deviceData.substring(889, 892));
		float invDcInCurrent7 = Float.parseFloat(deviceData.substring(892, 896));
		float invAcOpVolt7 = Float.parseFloat(deviceData.substring(896, 899));
		float invAcOpCurrent7 = Float.parseFloat(deviceData.substring(899, 902));
		float invAcOpPower7 = Float.parseFloat(deviceData.substring(902, 905));
		float invTemp7 = Float.parseFloat(deviceData.substring(905, 908));
		String invStatus7 = deviceData.substring(908, 910);
		float invDcInVolt8 = Float.parseFloat(deviceData.substring(910, 913));
		float invDcInCurrent8 = Float.parseFloat(deviceData.substring(913, 917));
		float invAcOpVolt8 = Float.parseFloat(deviceData.substring(917, 920));
		float invAcOpCurrent8 = Float.parseFloat(deviceData.substring(920, 923));
		float invAcOpPower8 = Float.parseFloat(deviceData.substring(923, 926));
		float invTemp8 = Float.parseFloat(deviceData.substring(926, 929));
		String invStatus8 = deviceData.substring(929, 931);
		float invDcInVolt9 = Float.parseFloat(deviceData.substring(931, 934));
		float invDcInCurrent9 = Float.parseFloat(deviceData.substring(934, 938));
		float invAcOpVolt9 = Float.parseFloat(deviceData.substring(938, 941));
		float invAcOpCurrent9 = Float.parseFloat(deviceData.substring(941, 944));
		float invAcOpPower9 = Float.parseFloat(deviceData.substring(944, 947));
		float invTemp9 = Float.parseFloat(deviceData.substring(947, 950));
		String invStatus9 = deviceData.substring(950, 952);

		float invDcInVolt10 = Float.parseFloat(deviceData.substring(952, 955));

		float invDcInCurrent10 = Float.parseFloat(deviceData.substring(955, 959));

		float invAcOpVolt10 = Float.parseFloat(deviceData.substring(959, 962));

		float invAcOpCurrent10 = Float.parseFloat(deviceData.substring(962, 965));

		float invAcOpPower10 = Float.parseFloat(deviceData.substring(965, 968));

		float invTemp10 = Float.parseFloat(deviceData.substring(968, 971));

		String invStatus10 = deviceData.substring(971, 973);



		float invDcInVolt11 = Float.parseFloat(deviceData.substring(973, 976));

		float invDcInCurrent11 = Float.parseFloat(deviceData.substring(976, 980));

		float invAcOpVolt11 = Float.parseFloat(deviceData.substring(980, 983));

		float invAcOpCurrent11 = Float.parseFloat(deviceData.substring(983, 986));

		float invAcOpPower11 = Float.parseFloat(deviceData.substring(986, 989));

		float invTemp11 = Float.parseFloat(deviceData.substring(989, 992));

		String invStatus11 = deviceData.substring(992, 994);

		float invDcInCurrent12 = Float.parseFloat(deviceData.substring(994, 997));

		float invDcInVolt12 = Float.parseFloat(deviceData.substring(997, 1001));

		float inAcOpVoltage12 = Float.parseFloat(deviceData.substring(1001, 1004));

		float inAcOpCurrent12 = Float.parseFloat(deviceData.substring(1004, 1007));

		float inAcOpPower12 = Float.parseFloat(deviceData.substring(1007, 1010));

		float invTemp12 = Float.parseFloat(deviceData.substring(1010, 1013));


		String invStatus12 = deviceData.substring(1013, 1015);

		int noOfInvModule = Integer.parseInt(deviceData.substring(1015, 1017));


		float invRunHr = Float.parseFloat(deviceData.substring(1017, 1025));

		float invEnergy = Float.parseFloat(deviceData.substring(1025, 1033));

		float invTotIpPower = Float.parseFloat(deviceData.substring(1033, 1037));

		float invTotOpPower = Float.parseFloat(deviceData.substring(1037, 1041));
		String reserved8 = deviceData.substring(1041, 1043);


		String str56 = deviceData.substring(1043, 1046);
		str56 = str56.replace("+", "");
		float actelLoadRVolt = Float.parseFloat(str56);

		float actelLoadYVolt = Float.parseFloat(deviceData.substring(1046, 1049));

		float actelLoadBVolt = Float.parseFloat(deviceData.substring(1049, 1052));

		float actelLoadRCurr = Float.parseFloat(deviceData.substring(1052, 1056));

		float actelLoadYCurr = Float.parseFloat(deviceData.substring(1056, 1060));

		float actelLoadBCurr = Float.parseFloat(deviceData.substring(1060, 1064));

		float actelLoadRPF = Float.parseFloat(deviceData.substring(1064, 1067));

		float actelLoadYPF = Float.parseFloat(deviceData.substring(1067, 1070));

		float actelLoadBPF = Float.parseFloat(deviceData.substring(1070, 1073));

		float actelLoadRPower = Float.parseFloat(deviceData.substring(1073, 1076));

		float actelLoadYPower = Float.parseFloat(deviceData.substring(1076, 1079));

		float actelLoadBPower = Float.parseFloat(deviceData.substring(1079, 1082));

		float actelLoadFre = Float.parseFloat(deviceData.substring(1082, 1085));

		String str7 = deviceData.substring(1085, 1089);
		str7 = str7.replace("+", "");
		float pvIpVoltMod13 = Float.parseFloat(str7);

		float pvIpCurrMod13 = Float.parseFloat(deviceData.substring(1089, 1092));



		float pvOpVoltMod13 = Float.parseFloat(deviceData.substring(1092, 1095));


		float pvOpCurrMod13 = Float.parseFloat(deviceData.substring(1095, 1098));


		String solarStatusMod13 = deviceData.substring(1098, 1100);

		float pvIpVoltMod14 = Float.parseFloat(deviceData.substring(1100, 1104));

		String str1 = deviceData.substring(1104, 1107);
		str1 = str1.replace("+", "");
		float pvIpCurrMod14 = Float.parseFloat(str1);

		float pvOpVoltMod14 = Float.parseFloat(deviceData.substring(1107, 1110));


		float pvOpCurrMod14 = Float.parseFloat(deviceData.substring(1110, 1113));


		String solarStatusMod14 = deviceData.substring(1113, 1115);

		float pvIpVoltMod15 = Float.parseFloat(deviceData.substring(1115, 1119));

		float pvIpCurrMod15 = Float.parseFloat(deviceData.substring(1119, 1122));

		String str3 = deviceData.substring(1122, 1125);
		str3 = str3.replace("+", "");
		float pvOpVoltMod15 = Float.parseFloat(str3);

		float pvOpCurrMod15 = Float.parseFloat(deviceData.substring(1125, 1128));


		String solarStatusMod15 = deviceData.substring(1128, 1130);

		float pvIpVoltMod16 = Float.parseFloat(deviceData.substring(1130, 1134));

		float pvIpCurrMod16 = Float.parseFloat(deviceData.substring(1134, 1137));

		float pvOpVoltMod16 = Float.parseFloat(deviceData.substring(1137, 1140));


		String str5 = deviceData.substring(1140, 1143);
		str5 = str5.replace("+", "");
		float pvOpCurrMod16 = Float.parseFloat(str5) ;
		String solarStatusMod16 = deviceData.substring(1143, 1145);
		float pvIpVoltMod17 = Float.parseFloat(deviceData.substring(1145, 1149));
		float pvIpCurrMod17 = Float.parseFloat(deviceData.substring(1149, 1152));
		float pvOpVoltMod17 = Float.parseFloat(deviceData.substring(1152, 1155));
		float pvOpCurrMod17 = Float.parseFloat(deviceData.substring(1155, 1158));
		String solarStatusMod17 = deviceData.substring(1158, 1160);
		float pvIpVoltMod18 = Float.parseFloat(deviceData.substring(1160, 1164));
		float pvIpCurrMod18 = Float.parseFloat(deviceData.substring(1164, 1167));
		float pvOpVoltMod18 = Float.parseFloat(deviceData.substring(1167, 1170));
		float pvOpCurrMod18 = Float.parseFloat(deviceData.substring(1170, 1173));
		String solarStatusMod18 = deviceData.substring(1173, 1175);
		String str2 = deviceData.substring(1175, 1179);
		str2 = str2.replace("+", "");
		float pvIpVoltMod19 = Float.parseFloat(str2);
		float pvIpCurrMod19 = Float.parseFloat(deviceData.substring(1179, 1182));
		float pvOpVoltMod19 = Float.parseFloat(deviceData.substring(1182, 1185));
		float pvOpCurrMod19 = Float.parseFloat(deviceData.substring(1185, 1188));
		String solarStatusMod19 = deviceData.substring(1188, 1190);
		float pvIpVoltMod20 = Float.parseFloat(deviceData.substring(1190, 1194));
		String str6 = deviceData.substring(1194, 1197);
		str6 = str6.replace("+", "");
		float pvIpCurrMod20 = Float.parseFloat(str6);
		float pvOpVoltMod20 = Float.parseFloat(deviceData.substring(1197, 1200));
		float pvOpCurrMod20 = Float.parseFloat(deviceData.substring(1200, 1203));
		String solarStatusMod20 = deviceData.substring(1203, 1205);
		float pvIpVoltMod21 = Float.parseFloat(deviceData.substring(1205, 1209));
		float pvIpCurrMod21 = Float.parseFloat(deviceData.substring(1209, 1212));
		String str8 = deviceData.substring(1212, 1215);
		str8 = str8.replace("+", "");
		float pvOpVoltMod21 = Float.parseFloat(str7);
		float pvOpCurrMod21 = Float.parseFloat(deviceData.substring(1215, 1218));
		String solarStatusMod21 = deviceData.substring(1218, 1220);
		float pvIpVoltMod22 = Float.parseFloat(deviceData.substring(1220, 1224));
		float pvIpCurrMod22 = Float.parseFloat(deviceData.substring(1224, 1227));
		float pvOpVoltMod22 = Float.parseFloat(deviceData.substring(1227, 1230));


		String str81 = deviceData.substring(1230, 1233);
		str81 = str81.replace("+", "");
		float pvOpCurrMod22 = Float.parseFloat(str8);


		String solarStatusMod22 = deviceData.substring(1233, 1235);

		float pvIpVoltMod23 = Float.parseFloat(deviceData.substring(1235, 1239));

		float pvIpCurrMod23 = Float.parseFloat(deviceData.substring(1239, 1242));

		float pvOpVoltMod23 = Float.parseFloat(deviceData.substring(1242, 1245));


		float pvOpCurrMod23 = Float.parseFloat(deviceData.substring(1245, 1248));


		String solarStatusMod23 = deviceData.substring(1248, 1250);
		float pvIpVoltMod24 = Float.parseFloat(deviceData.substring(1250, 1254));

		float pvIpCurrMod24 = Float.parseFloat(deviceData.substring(1254, 1257));

		float pvOpVoltMod24 = Float.parseFloat(deviceData.substring(1257, 1260));

		float pvOpCurrMod24 = Float.parseFloat(deviceData.substring(1260, 1263));

		String solarStatusMod24 = deviceData.substring(1263, 1265);



		/* Li Battery Section 1*/
		float liBattVolt1 = Float.parseFloat(deviceData.substring(1269, 1272))/10;
		float liBattCurr1 = Float.parseFloat(deviceData.substring(1272, 1277).replace("+", ""))/100;

		int liSoc1 = Integer.parseInt(deviceData.substring(1277, 1280));
		int liSoh1 = Integer.parseInt(deviceData.substring(1280, 1283));
		float liBattTemp1 = Float.parseFloat(deviceData.substring(1283, 1287))/10;
		String liBattStatus1 = deviceData.substring(1287, 1295);

		/* Li Battery Section 2*/
		float liBattVolt2 = Float.parseFloat(deviceData.substring(1295, 1298))/10;


		float liBattCurr2 = Float.parseFloat(deviceData.substring(1298, 1303).replace("+", ""))/100;


		int liSoc2 = Integer.parseInt(deviceData.substring(1303, 1306));
		int liSoh2 = Integer.parseInt(deviceData.substring(1306, 1309));

		float liBattTemp2 = Float.parseFloat(deviceData.substring(1309, 1313))/10;

		String liBattStatus2 = deviceData.substring(1313, 1321);

		/* Li Battery Section 3*/
		float liBattVolt3 = Float.parseFloat(deviceData.substring(1321, 1324))/10;

		float liBattCurr3 = Float.parseFloat(deviceData.substring(1324, 1329).replace("+", ""))/100;

		int liSoc3 = Integer.parseInt(deviceData.substring(1329, 1332));
		int liSoh3 = Integer.parseInt(deviceData.substring(1332, 1335));

		float liBattTemp3 = Float.parseFloat(deviceData.substring(1335, 1339))/10;

		String liBattStatus3 = deviceData.substring(1339, 1347);

		/* Li Battery Section 4*/
		float liBattVolt4 = Float.parseFloat(deviceData.substring(1347, 1350))/10;
		float liBattCurr4 = Float.parseFloat(deviceData.substring(1350, 1355).replace("+", ""))/100;
		int liSoc4 = Integer.parseInt(deviceData.substring(1355, 1358));
		int liSoh4 = Integer.parseInt(deviceData.substring(1358, 1361));
		float liBattTemp4 = Float.parseFloat(deviceData.substring(1361, 1365))/10;
		String liBattStatus4 = deviceData.substring(1365, 1373);

		/* Li Battery Section 5*/
		float liBattVolt5 = Float.parseFloat(deviceData.substring(1373, 1376))/10;

		float liBattCurr5 = Float.parseFloat(deviceData.substring(1376, 1381).replace("+", ""))/100;
		int liSoc5 = Integer.parseInt(deviceData.substring(1381, 1384));
		int liSoh5 = Integer.parseInt(deviceData.substring(1384, 1387));
		float liBattTemp5 = Float.parseFloat(deviceData.substring(1387, 1391))/10;
		String liBattStatus5 = deviceData.substring(1391, 1399);

		/* Li Battery Section 6*/
		float liBattVolt6 = Float.parseFloat(deviceData.substring(1399, 1402))/10;
		float liBattCurr6 = Float.parseFloat(deviceData.substring(1402, 1407).replace("+", ""))/100;
		int liSoc6 = Integer.parseInt(deviceData.substring(1407, 1410));
		int liSoh6 = Integer.parseInt(deviceData.substring(1410, 1413));
		float liBattTemp6 = Float.parseFloat(deviceData.substring(1413, 1417))/10;
		String liBattStatus6 = deviceData.substring(1417, 1425);

		/* Li Battery Section 7*/
		float liBattVolt7 = Float.parseFloat(deviceData.substring(1425, 1428))/10;
		float liBattCurr7 = Float.parseFloat(deviceData.substring(1428, 1433).replace("+", ""))/100;
		int liSoc7 = Integer.parseInt(deviceData.substring(1433, 1436));
		int liSoh7 = Integer.parseInt(deviceData.substring(1436, 1439));
		float liBattTemp7 = Float.parseFloat(deviceData.substring(1439, 1443))/10;
		String liBattStatus7 = deviceData.substring(1443, 1451);

		/* Li Battery Section 8*/
		float liBattVolt8 = Float.parseFloat(deviceData.substring(1451, 1454))/10;
		float liBattCurr8 = Float.parseFloat(deviceData.substring(1454, 1459).replace("+", ""))/100;
		int liSoc8 = Integer.parseInt(deviceData.substring(1459, 1462));
		int liSoh8 = Integer.parseInt(deviceData.substring(1462, 1465));
		float liBattTemp8 = Float.parseFloat(deviceData.substring(1465, 1469))/10;
		String liBattStatus8 = deviceData.substring(1469, 1477);

		/* Li Battery Section 9*/
		float liBattVolt9 = Float.parseFloat(deviceData.substring(1477, 1480))/10;
		float liBattCurr9 = Float.parseFloat(deviceData.substring(1480, 1485).replace("+", ""))/100;
		int liSoc9 = Integer.parseInt(deviceData.substring(1485, 1488));
		int liSoh9 = Integer.parseInt(deviceData.substring(1488, 1491));
		float liBattTemp9 = Float.parseFloat(deviceData.substring(1491, 1495))/10;
		String liBattStatus9 = deviceData.substring(1495, 1503);

		/* Li Battery Section 10*/
		float liBattVolt10 = Float.parseFloat(deviceData.substring(1503, 1506))/10;
		float liBattCurr10 = Float.parseFloat(deviceData.substring(1506, 1511).replace("+", ""))/100;
		int liSoc10 = Integer.parseInt(deviceData.substring(1511, 1514));
		int liSoh10 = Integer.parseInt(deviceData.substring(1514, 1517));
		float liBattTemp10 = Float.parseFloat(deviceData.substring(1517, 1521))/10;
		String liBattStatus10 = deviceData.substring(1521, 1529);

		/* Li Battery Section 11*/
		float liBattVolt11 = Float.parseFloat(deviceData.substring(1529, 1532))/10;
		float liBattCurr11 = Float.parseFloat(deviceData.substring(1532, 1537).replace("+", ""))/100;
		int liSoc11 = Integer.parseInt(deviceData.substring(1537, 1540));
		int liSoh11 = Integer.parseInt(deviceData.substring(1540, 1543));
		float liBattTemp11 = Float.parseFloat(deviceData.substring(1543, 1547))/10;
		String liBattStatus11 = deviceData.substring(1547, 1555);

		/* Li Battery Section 12*/
		float liBattVolt12 = Float.parseFloat(deviceData.substring(1555, 1558))/10;
		float liBattCurr12 = Float.parseFloat(deviceData.substring(1558, 1563).replace("+", ""))/100;
		int liSoc12 = Integer.parseInt(deviceData.substring(1563, 1566));
		int liSoh12 = Integer.parseInt(deviceData.substring(1566, 1569));
		float liBattTemp12 = Float.parseFloat(deviceData.substring(1569, 1573))/10;
		String liBattStatus12 = deviceData.substring(1573, 1581);

		/* Li Battery Section 13*/
		float liBattVolt13 = Float.parseFloat(deviceData.substring(1581, 1584))/10;
		float liBattCurr13 = Float.parseFloat(deviceData.substring(1584, 1589).replace("+", ""))/100;
		int liSoc13 = Integer.parseInt(deviceData.substring(1589, 1592));
		int liSoh13 = Integer.parseInt(deviceData.substring(1592, 1595));
		float liBattTemp13 = Float.parseFloat(deviceData.substring(1595, 1599))/10;
		String liBattStatus13 = deviceData.substring(1599, 1607);

		/* Li Battery Section 14*/
		float liBattVolt14 = Float.parseFloat(deviceData.substring(1607, 1610))/10;
		float liBattCurr14 = Float.parseFloat(deviceData.substring(1610, 1615).replace("+", ""))/100;
		int liSoc14 = Integer.parseInt(deviceData.substring(1615, 1618));
		int liSoh14 = Integer.parseInt(deviceData.substring(1618, 1621));
		float liBattTemp14 = Float.parseFloat(deviceData.substring(1621, 1625))/10;
		String liBattStatus14 = deviceData.substring(1625, 1633);

		/* Li Battery Section 15*/
		float liBattVolt15 = Float.parseFloat(deviceData.substring(1633, 1636))/10;
		float liBattCurr15 = Float.parseFloat(deviceData.substring(1636, 1641).replace("+", ""))/100;
		int liSoc15 = Integer.parseInt(deviceData.substring(1641, 1644));
		int liSoh15 = Integer.parseInt(deviceData.substring(1644, 1647));
		float liBattTemp15 = Float.parseFloat(deviceData.substring(1647, 1651))/10;
		String liBattStatus15 = deviceData.substring(1651, 1659);

		/* Li Battery Section 16*/
		float liBattVolt16 = Float.parseFloat(deviceData.substring(1659, 1662))/10;
		float liBattCurr16 = Float.parseFloat(deviceData.substring(1662, 1667).replace("+", ""))/100;
		int liSoc16 = Integer.parseInt(deviceData.substring(1667, 1670));
		int liSoh16 = Integer.parseInt(deviceData.substring(1670, 1673));
		float liBattTemp16 = Float.parseFloat(deviceData.substring(1673, 1677))/10;
		String liBattStatus16 = deviceData.substring(1677, 1685);

		/* Li Battery Section 17*/
		float liBattVolt17 = Float.parseFloat(deviceData.substring(1685, 1688))/10;
		float liBattCurr17 = Float.parseFloat(deviceData.substring(1688, 1693).replace("+", ""))/100;
		int liSoc17 = Integer.parseInt(deviceData.substring(1693, 1696));
		int liSoh17 = Integer.parseInt(deviceData.substring(1696, 1699));
		float liBattTemp17 = Float.parseFloat(deviceData.substring(1699, 1703))/10;
		String liBattStatus17 = deviceData.substring(1703, 1711);

		/* Li Battery Section 18*/
		float liBattVolt18 = Float.parseFloat(deviceData.substring(1711, 1714))/10;
		float liBattCurr18 = Float.parseFloat(deviceData.substring(1714, 1719).replace("+", ""))/100;
		int liSoc18 = Integer.parseInt(deviceData.substring(1719, 1722));
		int liSoh18 = Integer.parseInt(deviceData.substring(1722, 1725));
		float liBattTemp18 = Float.parseFloat(deviceData.substring(1725, 1729))/10;
		String liBattStatus18 = deviceData.substring(1729, 1737);

		/* Li Battery Section 19*/
		float liBattVolt19 = Float.parseFloat(deviceData.substring(1737, 1740))/10;
		float liBattCurr19 = Float.parseFloat(deviceData.substring(1740, 1745).replace("+", ""))/100;
		int liSoc19 = Integer.parseInt(deviceData.substring(1745, 1748));
		int liSoh19 = Integer.parseInt(deviceData.substring(1748, 1751));
		float liBattTemp19 = Float.parseFloat(deviceData.substring(1751, 1755))/10;
		String liBattStatus19 = deviceData.substring(1755, 1763);

		/* Li Battery Section 20*/
		float liBattVolt20 = Float.parseFloat(deviceData.substring(1763, 1766))/10;
		float liBattCurr20 = Float.parseFloat(deviceData.substring(1766, 1771).replace("+", ""))/100;
		int liSoc20 = Integer.parseInt(deviceData.substring(1771, 1774));
		int liSoh20 = Integer.parseInt(deviceData.substring(1774, 1777));
		float liBattTemp20 = Float.parseFloat(deviceData.substring(1777, 1781))/10;
		String liBattStatus20 = deviceData.substring(1781, 1789);

		String stringTerm = deviceData.substring(1782, 1783);
		
		logger.debug("!! Completed Message Parsing !!");

		long _recordtime    = _parseDate(year,month,date,hour,minutes,seconds);
		long dbCreationTime = DateTime.getCurrentTimeSec();

		if (_recordtime > (dbCreationTime+3600))
			_recordtime = dbCreationTime;

		int smSiteId=0;
		int crClusterID=0;
		String smSiteName="";
		String siteCode="";
		int znZoneID = 0;
		String crName="";
		int rgRegionID=0;
		String rgRegion="";
		String znZone="";
		int smtechempid=0;
		int smSitetypeid=0;

		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		ConnectionPool jdbcObj = new ConnectionPool();
		DataSource dataSource = jdbcObj.setUpPool();
		jdbcObj.printDbStatus();

		// Performing Database Operation!
		connObj = dataSource.getConnection();
		jdbcObj.printDbStatus(); 

		pstmtObj = connObj.prepareStatement("Select * from sitemaster where smSiteCode ='"+smSiteCode+"' "
				+ "and smSiteactivestatus=1");

		ResultSet rsObj2 = pstmtObj.executeQuery();

		while(rsObj2.next()){

			smSiteId = rsObj2.getInt("smSiteID");
			smtechempid = rsObj2.getInt("smTechEmpid");
			smSiteName = rsObj2.getString("smSitename");
			crClusterID = rsObj2.getInt("crClusterID");
			siteCode = rsObj2.getString("smSiteCode");
			smSitetypeid = rsObj2.getInt("smSitetypeid");
		}

		if(smSiteId==0) {

			/* Insert into Not added Device as we didn't find the site from site master*/
			pstmtObj = connObj.prepareStatement("Insert into notaddedsite (smSiteCode,ndTimestamp) values (?,?)");

			pstmtObj.setString(1, smSiteCode);
			pstmtObj.setLong(2, _recordtime);
			pstmtObj.executeUpdate();
			connObj.close();
			pstmtObj.close();
			logger.info("Site is not configured , data not processed and returning back!!!!!"+siteCode);
			return true;
		} else {

			pstmtObj.close();
			pstmtObj = connObj.prepareStatement("Select * from clustermaster where crClusterID ="+crClusterID+"");
			ResultSet rsZone = pstmtObj.executeQuery();

			while(rsZone.next()) {

				znZoneID = rsZone.getInt("znZoneID");
				crName = rsZone.getString("crName");
			}

			pstmtObj = connObj.prepareStatement("Select * from zonemaster where znZoneID ="+znZoneID+"");
			ResultSet rszonemaster = pstmtObj.executeQuery();

			while(rszonemaster.next()) {

				znZoneID = rszonemaster.getInt("znZoneID");
				znZone = rszonemaster.getString("znZone");
				rgRegionID = rszonemaster.getInt("rgRegionID");
			}

			pstmtObj = connObj.prepareStatement("Select * from regionmaster where rgRegionID ="+rgRegionID+"");
			ResultSet rgRegionIDResult = pstmtObj.executeQuery();

			while(rgRegionIDResult.next()) {

				rgRegion = rgRegionIDResult.getString("rgRegion");
			}

			pstmtObj = connObj.prepareStatement("Select * from employeemaster where emEmpID ="+smtechempid+"");

			ResultSet employeeResult = pstmtObj.executeQuery();
			int emEmpID=0;
			String emEmployeeID="";
			String emFirstName="";
			String emContactNo="";
			String emEmail="";

			while(employeeResult.next()) {

				emEmpID = employeeResult.getInt("emEmpID");
				emEmployeeID = employeeResult.getString("emEmployeeID");
				emFirstName = employeeResult.getString("emFirstName");
				emContactNo = employeeResult.getString("emContactNo");
				emEmail = employeeResult.getString("emEmail");
			}
			pstmtObj.close();
			rsObj2.close();
			
			long millis=System.currentTimeMillis();  
			java.sql.Date hpDate=new java.sql.Date(millis);  

			try {   
				jdbcObj.printDbStatus();

				// Performing Database Operation!
				jdbcObj.printDbStatus(); 

				pstmtObj = connObj.prepareStatement("Select * from trans_latestdata  where smSiteID ="+smSiteId+"");

				ResultSet lastData = pstmtObj.executeQuery();
				long lastUpdate = 0;
				boolean isData = lastData.next();

				if(!isData) {
					pstmtObj.close();
					pstmtObj = connObj.prepareStatement("insert into trans_latestdata(AccID,smSiteID,rclTimestamp,"
							+ "Batt_3_temp,Batt_4_temp,Batt_1_temp,Batt_2_temp,Fuellevel_Percentage,Fuellevel_Ltrs,Sitebattvolt,"
							+ "MDG_R_Volt,MDG_Y_Volt,MDG_B_Volt,MDG_R_Amp,MDG_Y_Amp,MDG_B_Amp,MDG_R_PowerFactor,MDG_Y_PowerFactor,"
							+ "MDG_B_PowerFactor,MDG_R_Power,MDG_Y_Power,MDG_B_Power,MDG_Frequency,DG_R_Volt,DG_Y_Volt,DG_B_Volt,"
							+ "DG_R_Amp,DG_Y_Amp,DG_B_Amp,DG_R_PowerFactor,DG_Y_PowerFactor,DG_B_PowerFactor,DG_R_Power,DG_Y_Power,"
							+ "DG_B_Power,DG_Frequency,DG_Bat_Volt,DG_Tank_Capacity,Batt_Charging_Current,Batt_Discharging_Current,"
							+ "Batt_SOC,Batt_Cycle_Count,Batt_Capacity_AH,Batt_NextFullChargeTime,No_of_Batt_Strings,MDG_RunHrs,"
							+ "DG_RunHrs,Batt_RunHrs,MDG_Energy,DG_Energy,Batt_DisEnergy,Batt_ChargingEnergy,"
							+ "Rect_Input_R_Voltage,Rect_Input_Y_Voltage,Rect_Input_B_Voltage,Rect_Input_R_Current,"
							+ "Rect_Input_Y_Current,Rect_Input_B_Current,Rect_Output_Voltage,No_of_Rectfrs,"
							+ "Total_Rect_Output_Current,Total_DCLoad_Current,Total_DCLoad_Energy,"
							+ "Opco_1_LoadCurrent,Opco_2_LoadCurrent,Opco_3_LoadCurrent,Opco_4_LoadCurrent,"
							+ "Opco_1_Energy,Opco_2_Energy,Opco_3_Energy,Opco_4_Energy,InputVoltage_SlrModule1,"
							+ "InputCurrent_SlrModule1,InputPower_SlrModule1,OutputVoltage_SlrModule1,"
							+ "OutputCurrent_SlrModule1,OutputPower_SlrModule1,SolarModule1Status,"
							+ "InputVoltage_SlrModule2,InputCurrent_SlrModule2,InputPower_SlrModule2,OutputVoltage_SlrModule2,"
							+ "OutputCurrent_SlrModule2,OutputPower_SlrModule2,SolarModule2Status,InputVoltage_SlrModule3,"
							+ "InputCurrent_SlrModule3,InputPower_SlrModule3,OutputVoltage_SlrModule3,OutputCurrent_SlrModule3,"
							+ "OutputPower_SlrModule3,SolarModule3Status,InputVoltage_SlrModule4,InputCurrent_SlrModule4,"
							+ "InputPower_SlrModule4,OutputVoltage_SlrModule4,OutputCurrent_SlrModule4,OutputPower_SlrModule4,"
							+ "SolarModule4Status,InputVoltage_SlrModule5,InputCurrent_SlrModule5,InputPower_SlrModule5,"
							+ "OutputVoltage_SlrModule5,OutputCurrent_SlrModule5,OutputPower_SlrModule5,SolarModule5Status,"
							+ "InputVoltage_SlrModule6,InputCurrent_SlrModule6,InputPower_SlrModule6,OutputVoltage_SlrModule6,"
							+ "OutputCurrent_SlrModule6,OutputPower_SlrModule6,SolarModule6Status,InputVoltage_SlrModule7,"
							+ "InputCurrent_SlrModule7,InputPower_SlrModule7,OutputVoltage_SlrModule7,OutputCurrent_SlrModule7,"
							+ "OutputPower_SlrModule7,SolarModule7Status,InputVoltage_SlrModule8,InputCurrent_SlrModule8,"
							+ "InputPower_SlrModule8,OutputVoltage_SlrModule8,OutputCurrent_SlrModule8,OutputPower_SlrModule8,"
							+ "SolarModule8Status,InputVoltage_SlrModule9,InputCurrent_SlrModule9,InputPower_SlrModule9,"
							+ "OutputVoltage_SlrModule9,OutputCurrent_SlrModule9,OutputPower_SlrModule9,SolarModule9Status,"
							+ "InputVoltage_SlrModule10,InputCurrent_SlrModule10,InputPower_SlrModule10,OutputVoltage_SlrModule10,"
							+ "OutputCurrent_SlrModule10,OutputPower_SlrModule10,SolarModule10Status,InputVoltage_SlrModule11,"
							+ "InputCurrent_SlrModule11,InputPower_SlrModule11,OutputVoltage_SlrModule11,OutputCurrent_SlrModule11,"
							+ "OutputPower_SlrModule11,SolarModule11Status,InputVoltage_SlrModule12,InputCurrent_SlrModule12,"
							+ "InputPower_SlrModule12,OutputVoltage_SlrModule12,OutputCurrent_SlrModule12,OutputPower_SlrModule12,"
							+ "SolarModule12Status,No_of_Solar_Module,Solar_Input_Energy,Solar_Output_Energy,SolarRunHrs,"
							+ "SolarMDGRunHrs,SolarDGRunHrs,SolarBattRunHrs,PowerSource,SolarTotalnputPower,SolarTotaOutputPower,"
							+ "SysOutLoadStatus,MDGRunHrs,TeleRunHrs,TeleEnergy,CommunityLoad_R_Voltage,CommunityLoad_Y_Voltage,CommunityLoad_B_Voltage,"
							+ "CommunityLoad_R_Current,CommunityLoad_Y_Current,CommunityLoad_B_Current,CommunityLoad_R_PowerFactor,CommunityLoad_Y_PowerFactor,CommunityLoad_B_PowerFactor,"
							+ "CommunityLoad_R_Power,CommunityLoad_Y_Power,CommunityLoad_B_Power,CommunityLoad_Frequency,CommunityLoad_RunHrs,CommunityLoad_Energy,"
							+ "InverterInputVoltage_1,InverterInputCurrent_1,InverterOutVoltage_1,InverterOutputCurrent_1,"
							+ "InverterOutputEnergy_1,InverterTemp_1,Ineverter_Status_1,InverterInputVoltage_2,"
							+ "InverterInputCurrent_2,InverterOutVoltage_2,InverterOutputCurrent_2,"
							+ "InverterOutputEnergy_2,InverterTemp_2,Ineverter_Status_2,InverterInputVoltage_3,"
							+ "InverterInputCurrent_3,InverterOutVoltage_3,InverterOutputCurrent_3,"
							+ "InverterOutputEnergy_3,InverterTemp_3,Ineverter_Status_3,InverterInputVoltage_4,"
							+ "InverterInputCurrent_4,InverterOutVoltage_4,InverterOutputCurrent_4,"
							+ "InverterOutputEnergy_4,InverterTemp_4,Ineverter_Status_4,InverterInputVoltage_5,"
							+ "InverterInputCurrent_5,InverterOutVoltage_5,InverterOutputCurrent_5,"
							+ "InverterOutputEnergy_5,InverterTemp_5,Ineverter_Status_5,InverterInputVoltage_6,"
							+ "InverterInputCurrent_6,InverterOutVoltage_6,InverterOutputCurrent_6,InverterOutputEnergy_6,"
							+ "InverterTemp_6,Ineverter_Status_6,InverterInputVoltage_7,InverterInputCurrent_7,"
							+ "InverterOutVoltage_7,InverterOutputCurrent_7,InverterOutputEnergy_7,InverterTemp_7,"
							+ "Ineverter_Status_7,InverterInputVoltage_8,InverterInputCurrent_8,InverterOutVoltage_8,"
							+ "InverterOutputCurrent_8,InverterOutputEnergy_8,InverterTemp_8,Ineverter_Status_8,"
							+ "InverterInputVoltage_9,InverterInputCurrent_9,InverterOutVoltage_9,InverterOutputCurrent_9,"
							+ "InverterOutputEnergy_9,InverterTemp_9,Ineverter_Status_9,InverterInputVoltage_10,"
							+ "InverterInputCurrent_10,InverterOutVoltage_10,InverterOutputCurrent_10,"
							+ "InverterOutputEnergy_10,InverterTemp_10,Ineverter_Status_10,"
							+ "InverterInputVoltage_11,InverterInputCurrent_11,InverterOutVoltage_11,"
							+ "InverterOutputCurrent_11,InverterOutputEnergy_11,InverterTemp_11,"
							+ "Ineverter_Status_11,InverterInputVoltage_12,InverterInputCurrent_12,"
							+ "InverterOutVoltage_12,InverterOutputCurrent_12,InverterOutputEnergy_12,"
							+ "InverterTemp_12,Ineverter_Status_12,No_Of_Inverter_Module,InverterRunHrs,"
							+ "InverterEnergy,InverterInputPower,InverterOutputPower,ACTEL_R_Voltage,"
							+ "ACTEL_Y_Voltage,ACTEL_B_Voltage,ACTEL_R_Current,ACTEL_Y_Current,"
							+ "ACTEL_B_Current,ACTEL_R_PowerFactor,ACTEL_Y_PowerFactor,ACTEL_B_PowerFactor,"
							+ "ACTEL_R_Power,ACTEL_Y_Power,ACTEL_B_Power,ACTEL_Frequency,InputVoltage_SlrModule13,"
							+ "InputCurrent_SlrModule13,OutputVoltage_SlrModule13,OutputCurrent_SlrModule13,SolarModule13Status,"
							+ "InputVoltage_SlrModule14,InputCurrent_SlrModule14,OutputVoltage_SlrModule14,"
							+ "OutputCurrent_SlrModule14,SolarModule14Status,InputVoltage_SlrModule15,InputCurrent_SlrModule15,OutputVoltage_SlrModule15,OutputCurrent_SlrModule15,SolarModule15Status,InputVoltage_SlrModule16,InputCurrent_SlrModule16,OutputVoltage_SlrModule16,OutputCurrent_SlrModule16,SolarModule16Status,InputVoltage_SlrModule17,InputCurrent_SlrModule17,OutputVoltage_SlrModule17,OutputCurrent_SlrModule17,SolarModule17Status,InputVoltage_SlrModule18,InputCurrent_SlrModule18,OutputVoltage_SlrModule18,OutputCurrent_SlrModule18,SolarModule18Status,InputVoltage_SlrModule19,InputCurrent_SlrModule19,OutputVoltage_SlrModule19,OutputCurrent_SlrModule19,SolarModule19Status,InputVoltage_SlrModule20,InputCurrent_SlrModule20,OutputVoltage_SlrModule20,OutputCurrent_SlrModule20,SolarModule20Status,InputVoltage_SlrModule21,InputCurrent_SlrModule21,OutputVoltage_SlrModule21,OutputCurrent_SlrModule21,SolarModule21Status,InputVoltage_SlrModule22,InputCurrent_SlrModule22,OutputVoltage_SlrModule22,OutputCurrent_SlrModule22,SolarModule22Status,InputVoltage_SlrModule23,InputCurrent_SlrModule23,OutputVoltage_SlrModule23,OutputCurrent_SlrModule23,SolarModule23Status,InputVoltage_SlrModule24,InputCurrent_SlrModule24,OutputVoltage_SlrModule24,OutputCurrent_SlrModule24,SolarModule24Status,smSiteCode,DBCreationTimestamp,smSitetypeid,Li_BattVoltage_1,Li_BattCurrent_1,Li_SOC_1,Li_SOH_1,Li_BattTemp_1,Li_BattStatus_1,Li_BattVoltage_2,Li_BattCurrent_2,Li_SOC_2,Li_SOH_2,Li_BattTemp_2,Li_BattStatus_2,Li_BattVoltage_3,Li_BattCurrent_3,Li_SOC_3,Li_SOH_3,Li_BattTemp_3,Li_BattStatus_3,Li_BattVoltage_4,Li_BattCurrent_4,Li_SOC_4,Li_SOH_4,Li_BattTemp_4,Li_BattStatus_4,Li_BattVoltage_5,Li_BattCurrent_5,Li_SOC_5,Li_SOH_5,Li_BattTemp_5,Li_BattStatus_5,Li_BattVoltage_6,Li_BattCurrent_6,Li_SOC_6 ,Li_SOH_6 ,Li_BattTemp_6,Li_BattStatus_6,Li_BattVoltage_7,Li_BattCurrent_7,Li_SOC_7,Li_SOH_7,Li_BattTemp_7,Li_BattStatus_7,Li_BattVoltage_8,Li_BattCurrent_8,Li_SOC_8,Li_SOH_8,Li_BattTemp_8,Li_BattStatus_8,Li_BattVoltage_9,Li_BattCurrent_9,Li_SOC_9,Li_SOH_9,Li_BattTemp_9,Li_BattStatus_9,Li_BattVoltage_10,Li_BattCurrent_10,Li_SOC_10,Li_SOH_10,Li_BattTemp_10,Li_BattStatus_10,Li_BattVoltage_11,Li_BattCurrent_11,Li_SOC_11,Li_SOH_11,Li_BattTemp_11,Li_BattStatus_11,Li_BattVoltage_12,Li_BattCurrent_12,Li_SOC_12,Li_SOH_12,Li_BattTemp_12,Li_BattStatus_12,Li_BattVoltage_13,Li_BattCurrent_13,Li_SOC_13,Li_SOH_13,Li_BattTemp_13,Li_BattStatus_13,Li_BattVoltage_14,Li_BattCurrent_14,Li_SOC_14,Li_SOH_14,Li_BattTemp_14,Li_BattStatus_14,Li_BattVoltage_15,Li_BattCurrent_15,Li_SOC_15,Li_SOH_15,Li_BattTemp_15,Li_BattStatus_15,Li_BattVoltage_16,Li_BattCurrent_16,Li_SOC_16,Li_SOH_16,Li_BattTemp_16,Li_BattStatus_16,Li_BattVoltage_17,Li_BattCurrent_17,Li_SOC_17,Li_SOH_17,Li_BattTemp_17,Li_BattStatus_17,Li_BattVoltage_18,Li_BattCurrent_18,Li_SOC_18,Li_SOH_18,Li_BattTemp_18,Li_BattStatus_18,Li_BattVoltage_19,Li_BattCurrent_19,Li_SOC_19,Li_SOH_19,Li_BattTemp_19,Li_BattStatus_19,Li_BattVoltage_20,Li_BattCurrent_20,Li_SOC_20,Li_SOH_20,Li_BattTemp_20,Li_BattStatus_20) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


					pstmtObj.setInt(1, 1);
					pstmtObj.setInt(2, smSiteId);
					pstmtObj.setLong(3, _recordtime);
					pstmtObj.setFloat(4,battery_Temp3/10);
					pstmtObj.setFloat(5,battery_Temp4/10);
					pstmtObj.setFloat(6,battery_Temp1/10);
					pstmtObj.setFloat(7,battery_Temp2/10);
					pstmtObj.setInt(8,fuelLevel);
					pstmtObj.setInt(9,fuelLevelltr);
					pstmtObj.setFloat(10,siteBattVolt/10);
					pstmtObj.setFloat(11,mdgRVolt);
					pstmtObj.setFloat(12,mdgYVolt);
					pstmtObj.setFloat(13,mdgBVolt);
					pstmtObj.setFloat(14,mdgRCurrent/10);
					pstmtObj.setFloat(15,mdgYCurrent/10);
					pstmtObj.setFloat(16,mdgBCurrent/10);
					pstmtObj.setFloat(17,mdgRPf/100);
					pstmtObj.setFloat(18,mdgyPF/100);
					pstmtObj.setFloat(19,mdgBPf/100);
					pstmtObj.setFloat(20,mdgRPower/10);
					pstmtObj.setFloat(21,mdgyPower/10);
					pstmtObj.setFloat(22,mdgBPower/10);
					pstmtObj.setFloat(23,mdgFrequency/10);
					pstmtObj.setFloat(24,dgRVoltage);
					pstmtObj.setFloat(25,dgYVoltage);
					pstmtObj.setFloat(26,dgBVoltage);
					pstmtObj.setFloat(27,dgRCurrent/10);
					pstmtObj.setFloat(28,dgYCurrent/10);
					pstmtObj.setFloat(29,dgBCurrent/10);
					pstmtObj.setFloat(30,dgRPf/100);
					pstmtObj.setFloat(31,dgYPf/100);
					pstmtObj.setFloat(32,dgBPf/100);
					pstmtObj.setFloat(33,dgRPower/10);
					pstmtObj.setFloat(34,dgYPower/10);
					pstmtObj.setFloat(35,dgBPower/10);
					pstmtObj.setFloat(36,dgFrequency/10);
					pstmtObj.setFloat(37,dgBattVolt/10);
					pstmtObj.setFloat(38,dgTankCapacity);
					pstmtObj.setFloat(39,batteryChargeCurrent/10);
					pstmtObj.setFloat(40,batteryDisChargeCurrent/10);
					pstmtObj.setFloat(41,batterySOC);
					pstmtObj.setFloat(42,batteryCycleCount);
					pstmtObj.setFloat(43,batteryCapacityinAh);
					pstmtObj.setFloat(44,batteryNextFullChgItem/10);
					pstmtObj.setInt(45,noOfBatteryString);
					pstmtObj.setFloat(46,mdgRunningHr/10);
					pstmtObj.setFloat(47,dgRunningHr/10);
					pstmtObj.setFloat(48,battRunningHr/10);
					pstmtObj.setFloat(49,mdgEnergy/10);
					pstmtObj.setFloat(50,dgEnergy/10);
					pstmtObj.setFloat(51,battDisEnergy/10);
					pstmtObj.setFloat(52,battChgEnergy/10);
					pstmtObj.setInt(53,rectInRVol);
					pstmtObj.setInt(54,rectInYVol);
					pstmtObj.setInt(55,rectInBVol);
					pstmtObj.setFloat(56,rectInRCurr/10);
					pstmtObj.setFloat(57,rectInYCurr/10);
					pstmtObj.setFloat(58,rectInBCurr/10);
					pstmtObj.setFloat(59,rectOutV/10);
					pstmtObj.setInt(60,noOfRec);
					pstmtObj.setFloat(61,totRecOpCurr/10);
					pstmtObj.setFloat(62,totDcLoadCurr/10);
					pstmtObj.setFloat(63,totDcLoadEnrgy/10);
					pstmtObj.setFloat(64,opc1LoadCurrent/10);
					pstmtObj.setFloat(65,opc2LoadCurrent/10);
					pstmtObj.setFloat(66,opc3LoadCurrent/10);
					pstmtObj.setFloat(67,opc4LoadCurrent/10);
					pstmtObj.setFloat(68,opc1Energy/10);
					pstmtObj.setFloat(69,opc2Energy/10);
					pstmtObj.setFloat(70,opc3Energy/10);
					pstmtObj.setFloat(71,opc4Energy/10);
					pstmtObj.setFloat(72,pvIpVoltMpod1/10);
					pstmtObj.setFloat(73,pvIpCurrMpod1/10);
					pstmtObj.setFloat(74,pvIpPowMpod1/10);
					pstmtObj.setFloat(75,pvOpVoltMpod1/10);
					pstmtObj.setFloat(76,pvOpCurrMpod1/10);
					pstmtObj.setFloat(77,pvOpPowMpod1/10);
					pstmtObj.setString(78,solarStatusMod1);
					pstmtObj.setFloat(79,pvIpVoltMod2/10);
					pstmtObj.setFloat(80,pvIpCurMod2/10);
					pstmtObj.setFloat(81,pvIpPowMod2/10);
					pstmtObj.setFloat(82,pvOpVoltMod2/10);
					pstmtObj.setFloat(83,pvOpCurrMod2/10);
					pstmtObj.setFloat(84,pvOpPowMod2/10);
					pstmtObj.setString(85,solarStatusMod2);
					pstmtObj.setFloat(86,pvIpVoltMpod3/10);
					pstmtObj.setFloat(87,pvIpCurrMpod3/10);
					pstmtObj.setFloat(88,pvIpPowMpod3/10);
					pstmtObj.setFloat(89,pvOpVoltMpod3/10);
					pstmtObj.setFloat(90,pvOpCurrMpod3/10);
					pstmtObj.setFloat(91,pvOpPowMpod3/10);
					pstmtObj.setString(92,solarStatusMod3);
					pstmtObj.setFloat(93,pvIpVoltMpod4/10);
					pstmtObj.setFloat(94,pvIpCurrMpod4/10);
					pstmtObj.setFloat(95,pvIpPowMpod4/10);
					pstmtObj.setFloat(96,pvOpVoltMpod4/10);
					pstmtObj.setFloat(97,pvOpCurrMpod4/10);
					pstmtObj.setFloat(98,pvOpPowMpod4/10);
					pstmtObj.setString(99,solarStatusMod4);
					pstmtObj.setFloat(100,pvIpVoltMpod5/10);
					pstmtObj.setFloat(101,pvIpCurrMpod5/10);
					pstmtObj.setFloat(102,pvIpPowMpod5/10);
					pstmtObj.setFloat(103,pvOpVoltMpod5/10);
					pstmtObj.setFloat(104,pvOpCurrMpod5/10);
					pstmtObj.setFloat(105,pvOpPowMpod5/10);
					pstmtObj.setString(106,solarStatusMod5);
					pstmtObj.setFloat(107,pvIpVoltMpod6/10);
					pstmtObj.setFloat(108,pvIpCurrMpod6/10);
					pstmtObj.setFloat(109,pvIpPowMpod6/10);
					pstmtObj.setFloat(110,pvOpVoltMpod6/10);
					pstmtObj.setFloat(111,pvOpCurrMpod6/10);
					pstmtObj.setFloat(112,pvOpPowMpod6/10);
					pstmtObj.setString(113,solarStatusMod6);
					pstmtObj.setFloat(114,pvIpVoltMpod7/10);
					pstmtObj.setFloat(115,pvIpCurrMpod7/10);
					pstmtObj.setFloat(116,pvIpPowMpod7/10);
					pstmtObj.setFloat(117,pvOpVoltMpod7/10);
					pstmtObj.setFloat(118,pvOpCurrMpod7/10);
					pstmtObj.setFloat(119,pvOpPowMpod7/10);
					pstmtObj.setString(120,solarStatusMod7);
					pstmtObj.setFloat(121,pvIpVoltMpod8/10);
					pstmtObj.setFloat(122,pvIpCurrMpod8/10);
					pstmtObj.setFloat(123,pvIpPowMpod8/10);
					pstmtObj.setFloat(124,pvOpVoltMpod8/10);
					pstmtObj.setFloat(125,pvOpCurrMpod8/10);
					pstmtObj.setFloat(126,pvOpPowMpod8/10);
					pstmtObj.setString(127,solarStatusMod8);
					pstmtObj.setFloat(128,pvIpVoltMpod9/10);
					pstmtObj.setFloat(129,pvIpCurrMpod9/10);
					pstmtObj.setFloat(130,pvIpPowMpod9/10);
					pstmtObj.setFloat(131,pvOpVoltMpod9/10);
					pstmtObj.setFloat(132,pvOpCurrMpod9/10);
					pstmtObj.setFloat(133,pvOpPowMpod9/10);
					pstmtObj.setString(134,solarStatusMod9);
					pstmtObj.setFloat(135,pvIpVoltMpod10/10);
					pstmtObj.setFloat(136,pvIpCurrMpod10/10);
					pstmtObj.setFloat(137,pvIpPowMpod10/10);
					pstmtObj.setFloat(138,pvOpVoltMpod10/10);
					pstmtObj.setFloat(139,pvOpCurrMpod10/10);
					pstmtObj.setFloat(140,pvOpPowMpod10/10);
					pstmtObj.setString(141,solarStatusMod10);
					pstmtObj.setFloat(142,pvIpVoltMpod11/10);
					pstmtObj.setFloat(143,pvIpCurrMpod11/10);
					pstmtObj.setFloat(144,pvIpPowMpod11/10);
					pstmtObj.setFloat(145,pvOpVoltMpod11/10);
					pstmtObj.setFloat(146,pvOpCurrMpod11/10);
					pstmtObj.setFloat(147,pvOpPowMpod11/10);
					pstmtObj.setString(148,solarStatusMod11);
					pstmtObj.setFloat(149,pvIpVoltMpod12/10);
					pstmtObj.setFloat(150,pvIpCurrMpod12/10);
					pstmtObj.setFloat(151,pvIpPowMpod12/10);
					pstmtObj.setFloat(152,pvOpVoltMpod12/10);
					pstmtObj.setFloat(153,pvOpCurrMpod12/10);
					pstmtObj.setFloat(154,pvOpPowMpod12/10);
					pstmtObj.setString(155,solarStatusMod12);
					pstmtObj.setInt(156,noOfSolarModule);
					pstmtObj.setDouble(157,solarIpEnergy/10);
					pstmtObj.setDouble(158,solarOpEnergy/10);
					pstmtObj.setDouble(159,solarRunHour/10);
					pstmtObj.setDouble(160,solarMdgRunHour/10);
					pstmtObj.setDouble(161,solardgRunHour/10);
					pstmtObj.setDouble(162,solarBattRunHour/10);
					pstmtObj.setString(163,powerSource);
					pstmtObj.setFloat(164,solarTotIpPower/100);
					pstmtObj.setFloat(165,solarTotOpPower/100);
					pstmtObj.setString(166,sysOutLoadStatus);
					pstmtObj.setDouble(167,mobileDGRunHr/10);
					pstmtObj.setDouble(168,teleRunHr/10);
					pstmtObj.setDouble(169,teleEnergy/10);
					pstmtObj.setFloat(170,acLoadRVolt);
					pstmtObj.setFloat(171,acLoadYVolt);
					pstmtObj.setFloat(172,acLoadBVolt);
					pstmtObj.setFloat(173,acLoadRCurrent/10);
					pstmtObj.setFloat(174,acLoadYCurrent/10);
					pstmtObj.setFloat(175,acLoadBCurrent/10);
					pstmtObj.setFloat(176,acLoadRPF/100);
					pstmtObj.setFloat(177,acLoadYPF/100);
					pstmtObj.setFloat(178,acLoadBPF/100);
					pstmtObj.setFloat(179,acLoadRPower/10);
					pstmtObj.setFloat(180,acLoadYPower/10);
					pstmtObj.setFloat(181,acLoadBPower/10);
					pstmtObj.setFloat(182,acLoadFreq/10);
					pstmtObj.setDouble(183,acLoadRunHr/10);
					pstmtObj.setDouble(184,acLoadEnergy/10);
					pstmtObj.setFloat(185,invDcInVolt1/10);
					pstmtObj.setFloat(186,invDcInCurrent1/10);
					pstmtObj.setFloat(187,invAcOpVolt1);
					pstmtObj.setFloat(188,invAcOpCurrent1/10);
					pstmtObj.setFloat(189,invAcOpPower1/10);
					pstmtObj.setFloat(190,invTemp1/10);
					pstmtObj.setString(191,invStatus1);
					pstmtObj.setFloat(192,invDcInVolt2/10);
					pstmtObj.setFloat(193,invDcInCurrent2/10);
					pstmtObj.setFloat(194,invAcOpVolt2);
					pstmtObj.setFloat(195,invAcOpCurrent2/10);
					pstmtObj.setFloat(196,invAcOpPower2/10);
					pstmtObj.setFloat(197,invTemp2/10);
					pstmtObj.setString(198,invStatus2);
					pstmtObj.setFloat(199,invDcInVolt3/10);
					pstmtObj.setFloat(200,invDcInCurrent3/10);
					pstmtObj.setFloat(201,invAcOpVolt3);
					pstmtObj.setFloat(202,invAcOpCurrent3/10);
					pstmtObj.setFloat(203,invAcOpPower3/10);
					pstmtObj.setFloat(204,invTemp3/10);
					pstmtObj.setString(205,invStatus3);
					pstmtObj.setFloat(206,invDcInVolt4/10);
					pstmtObj.setFloat(207,invDcInCurrent4/10);
					pstmtObj.setFloat(208,invAcOpVolt4);
					pstmtObj.setFloat(209,invAcOpCurrent4/10);
					pstmtObj.setFloat(210,invAcOpPower4/10);
					pstmtObj.setFloat(211,invTemp4/10);
					pstmtObj.setString(212,invStatus4);
					pstmtObj.setFloat(213,invDcInVolt5/10);
					pstmtObj.setFloat(214,invDcInCurrent5/10);
					pstmtObj.setFloat(215,invAcOpVolt5);
					pstmtObj.setFloat(216,invAcOpCurrent5/10);
					pstmtObj.setFloat(217,invAcOpPower5/10);
					pstmtObj.setFloat(218,invTemp5/10);
					pstmtObj.setString(219,invStatus5);
					pstmtObj.setFloat(220,invDcInVolt6/10);
					pstmtObj.setFloat(221,invDcInCurrent6/10);
					pstmtObj.setFloat(222,invAcOpVolt6);
					pstmtObj.setFloat(223,invAcOpCurrent6/10);
					pstmtObj.setFloat(224,invAcOpPower6/10);
					pstmtObj.setFloat(225,invTemp6/10);
					pstmtObj.setString(226,invStatus6);
					pstmtObj.setFloat(227,invDcInVolt7/10);
					pstmtObj.setFloat(228,invDcInCurrent7/10);
					pstmtObj.setFloat(229,invAcOpVolt7);
					pstmtObj.setFloat(230,invAcOpCurrent7/10);
					pstmtObj.setFloat(231,invAcOpPower7/10);
					pstmtObj.setFloat(232,invTemp7/10);
					pstmtObj.setString(233,invStatus7);
					pstmtObj.setFloat(234,invDcInVolt8/10);
					pstmtObj.setFloat(235,invDcInCurrent8/10);
					pstmtObj.setFloat(236,invAcOpVolt8);
					pstmtObj.setFloat(237,invAcOpCurrent8/10);
					pstmtObj.setFloat(238,invAcOpPower8/10);
					pstmtObj.setFloat(239,invTemp8/10);
					pstmtObj.setString(240,invStatus8);
					pstmtObj.setFloat(241,invDcInVolt9/10);
					pstmtObj.setFloat(242,invDcInCurrent9/10);
					pstmtObj.setFloat(243,invAcOpVolt9);
					pstmtObj.setFloat(244,invAcOpCurrent9/10);
					pstmtObj.setFloat(245,invAcOpPower9/10);
					pstmtObj.setFloat(246,invTemp9/10);
					pstmtObj.setString(247,invStatus9);
					pstmtObj.setFloat(248,invDcInVolt10/10);
					pstmtObj.setFloat(249,invDcInCurrent10/10);
					pstmtObj.setFloat(250,invAcOpVolt10);
					pstmtObj.setFloat(251,invAcOpCurrent10/10);
					pstmtObj.setFloat(252,invAcOpPower10/10);
					pstmtObj.setFloat(253,invTemp10/10);
					pstmtObj.setString(254,invStatus10);
					pstmtObj.setFloat(255,invDcInVolt11/10);
					pstmtObj.setFloat(256,invDcInCurrent11/10);
					pstmtObj.setFloat(257,invAcOpVolt11);
					pstmtObj.setFloat(258,invAcOpCurrent11/10);
					pstmtObj.setFloat(259,invAcOpPower11/10);
					pstmtObj.setFloat(260,invTemp11/10);
					pstmtObj.setString(261,invStatus11);
					pstmtObj.setFloat(262,invDcInCurrent12/10);
					pstmtObj.setFloat(263,invDcInVolt12/10);
					pstmtObj.setFloat(264,inAcOpVoltage12);
					pstmtObj.setFloat(265,inAcOpCurrent12/10);
					pstmtObj.setFloat(266,inAcOpPower12/10);
					pstmtObj.setFloat(267,invTemp12/10);
					pstmtObj.setString(268,invStatus12);
					pstmtObj.setInt(269,noOfInvModule);
					pstmtObj.setDouble(270,invRunHr/10);
					pstmtObj.setDouble(271,invEnergy/10);
					pstmtObj.setFloat(272,invTotIpPower/100);
					pstmtObj.setFloat(273,invTotOpPower/100);
					pstmtObj.setFloat(274,actelLoadRVolt);
					pstmtObj.setFloat(275,actelLoadYVolt);
					pstmtObj.setFloat(276,actelLoadBVolt);
					pstmtObj.setFloat(277,actelLoadRCurr/10);
					pstmtObj.setFloat(278,actelLoadYCurr/10);
					pstmtObj.setFloat(279,actelLoadBCurr/10);
					pstmtObj.setFloat(280,actelLoadRPF/100);
					pstmtObj.setFloat(281,actelLoadYPF/100);
					pstmtObj.setFloat(282,actelLoadBPF/100);
					pstmtObj.setFloat(283,actelLoadRPower/10);
					pstmtObj.setFloat(284,actelLoadYPower/10);
					pstmtObj.setFloat(285,actelLoadBPower/10);
					pstmtObj.setFloat(286,actelLoadFre/10);
					pstmtObj.setFloat(287,pvIpVoltMod13/10);
					pstmtObj.setFloat(288,pvIpCurrMod13/10);
					pstmtObj.setFloat(289,pvOpVoltMod13/10);
					pstmtObj.setFloat(290,pvOpCurrMod13/10);
					pstmtObj.setString(291,solarStatusMod13);
					pstmtObj.setFloat(292,pvIpVoltMod14/10);
					pstmtObj.setFloat(293,pvIpCurrMod14/10);
					pstmtObj.setFloat(294,pvOpVoltMod14/10);
					pstmtObj.setFloat(295,pvOpCurrMod14/10);
					pstmtObj.setString(296,solarStatusMod14);
					pstmtObj.setFloat(297,pvIpVoltMod15/10);
					pstmtObj.setFloat(298,pvIpCurrMod15/10);
					pstmtObj.setFloat(299,pvOpVoltMod15/10);
					pstmtObj.setFloat(300,pvOpCurrMod15/10);
					pstmtObj.setString(301,solarStatusMod15);
					pstmtObj.setFloat(302,pvIpVoltMod16/10);
					pstmtObj.setFloat(303,pvIpCurrMod16/10);
					pstmtObj.setFloat(304,pvOpVoltMod16/10);
					pstmtObj.setFloat(305,pvOpCurrMod16/10);
					pstmtObj.setString(306,solarStatusMod16);
					pstmtObj.setFloat(307,pvIpVoltMod17/10);
					pstmtObj.setFloat(308,pvIpCurrMod17/10);
					pstmtObj.setFloat(309,pvOpVoltMod17/10);
					pstmtObj.setFloat(310,pvOpCurrMod17/10);
					pstmtObj.setString(311,solarStatusMod17);
					pstmtObj.setFloat(312,pvIpVoltMod18/10);
					pstmtObj.setFloat(313,pvIpCurrMod18/10);
					pstmtObj.setFloat(314,pvOpVoltMod18/10);
					pstmtObj.setFloat(315,pvOpCurrMod18/10);
					pstmtObj.setString(316,solarStatusMod18);
					pstmtObj.setFloat(317,pvIpVoltMod19/10);
					pstmtObj.setFloat(318,pvIpCurrMod19/10);
					pstmtObj.setFloat(319,pvOpVoltMod19/10);
					pstmtObj.setFloat(320,pvOpCurrMod19/10);
					pstmtObj.setString(321,solarStatusMod19);
					pstmtObj.setFloat(322,pvIpVoltMod20/10);
					pstmtObj.setFloat(323,pvIpCurrMod20/10);
					pstmtObj.setFloat(324,pvOpVoltMod20/10);
					pstmtObj.setFloat(325,pvOpCurrMod20/10);
					pstmtObj.setString(326,solarStatusMod20);
					pstmtObj.setFloat(327,pvIpVoltMod21/10);
					pstmtObj.setFloat(328,pvIpCurrMod21/10);
					pstmtObj.setFloat(329,pvOpVoltMod21/10);
					pstmtObj.setFloat(330,pvOpCurrMod21/10);
					pstmtObj.setString(331,solarStatusMod21);
					pstmtObj.setFloat(332,pvIpVoltMod22/10);
					pstmtObj.setFloat(333,pvIpCurrMod22/10);
					pstmtObj.setFloat(334,pvOpVoltMod22/10);
					pstmtObj.setFloat(335,pvOpCurrMod22/10);
					pstmtObj.setString(336,solarStatusMod22);
					pstmtObj.setFloat(337,pvIpVoltMod23/10);
					pstmtObj.setFloat(338,pvIpCurrMod23/10);
					pstmtObj.setFloat(339,pvOpVoltMod23/10);
					pstmtObj.setFloat(340,pvOpCurrMod23/10);
					pstmtObj.setString(341,solarStatusMod23);
					pstmtObj.setFloat(342,pvIpVoltMod24/10);
					pstmtObj.setFloat(343,pvIpCurrMod24/10);
					pstmtObj.setFloat(344,pvOpVoltMod24/10);
					pstmtObj.setFloat(345,pvOpCurrMod24/10);
					pstmtObj.setString(346,solarStatusMod24);
					pstmtObj.setString(347,smSiteCode);
					pstmtObj.setLong(348,dbCreationTime);
					pstmtObj.setInt(349,smSitetypeid);

					pstmtObj.setFloat(350,liBattVolt1);
					pstmtObj.setFloat(351,liBattCurr1);
					pstmtObj.setInt(352,liSoc1);
					pstmtObj.setFloat(353,liSoh1);
					pstmtObj.setFloat(354,liBattTemp1);
					pstmtObj.setString(355,liBattStatus1);
					/*Li2*/
					pstmtObj.setFloat(356,liBattVolt2);
					pstmtObj.setFloat(357,liBattCurr2);
					pstmtObj.setInt(358,liSoc2);
					pstmtObj.setFloat(359,liSoh2);
					pstmtObj.setFloat(360,liBattTemp2);
					pstmtObj.setString(361,liBattStatus2);
					/*Li3*/
					pstmtObj.setFloat(362,liBattVolt3);
					pstmtObj.setFloat(363,liBattCurr3);
					pstmtObj.setInt(364,liSoc3);
					pstmtObj.setFloat(365,liSoh3);
					pstmtObj.setFloat(366,liBattTemp3);
					pstmtObj.setString(367,liBattStatus3);
					/*Li4*/
					pstmtObj.setFloat(368,liBattVolt4);
					pstmtObj.setFloat(369,liBattCurr4);
					pstmtObj.setInt(370,liSoc4);
					pstmtObj.setFloat(371,liSoh4);
					pstmtObj.setFloat(372,liBattTemp4);
					pstmtObj.setString(373,liBattStatus4);
					/*Li5*/
					pstmtObj.setFloat(374,liBattVolt5);
					pstmtObj.setFloat(375,liBattCurr5);
					pstmtObj.setInt(376,liSoc5);
					pstmtObj.setFloat(377,liSoh5);
					pstmtObj.setFloat(378,liBattTemp5);
					pstmtObj.setString(379,liBattStatus5);
					/*Li6*/
					pstmtObj.setFloat(380,liBattVolt6);
					pstmtObj.setFloat(381,liBattCurr6);
					pstmtObj.setInt(382,liSoc6);
					pstmtObj.setFloat(383,liSoh6);
					pstmtObj.setFloat(384,liBattTemp6);
					pstmtObj.setString(385,liBattStatus6);
					/*Li7*/
					pstmtObj.setFloat(386,liBattVolt7);
					pstmtObj.setFloat(387,liBattCurr7);
					pstmtObj.setInt(388,liSoc7);
					pstmtObj.setFloat(389,liSoh7);
					pstmtObj.setFloat(390,liBattTemp7);
					pstmtObj.setString(391,liBattStatus7);
					/*Li8*/
					pstmtObj.setFloat(392,liBattVolt8);
					pstmtObj.setFloat(393,liBattCurr8);
					pstmtObj.setInt(394,liSoc8);
					pstmtObj.setFloat(395,liSoh8);
					pstmtObj.setFloat(396,liBattTemp8);
					pstmtObj.setString(397,liBattStatus8);
					/*Li9*/
					pstmtObj.setFloat(398,liBattVolt9);
					pstmtObj.setFloat(399,liBattCurr9);
					pstmtObj.setInt(400,liSoc9);
					pstmtObj.setFloat(401,liSoh9);
					pstmtObj.setFloat(402,liBattTemp9);
					pstmtObj.setString(403,liBattStatus9);
					/*Li10*/
					pstmtObj.setFloat(404,liBattVolt10);
					pstmtObj.setFloat(405,liBattCurr10);
					pstmtObj.setInt(406,liSoc10);
					pstmtObj.setFloat(407,liSoh10);
					pstmtObj.setFloat(408,liBattTemp10);
					pstmtObj.setString(409,liBattStatus10);
					/*Li11*/
					pstmtObj.setFloat(410,liBattVolt11);
					pstmtObj.setFloat(411,liBattCurr11);
					pstmtObj.setInt(412,liSoc11);
					pstmtObj.setFloat(413,liSoh11);
					pstmtObj.setFloat(414,liBattTemp11);
					pstmtObj.setString(415,liBattStatus11);
					/*Li12*/
					pstmtObj.setFloat(416,liBattVolt12);
					pstmtObj.setFloat(417,liBattCurr12);
					pstmtObj.setInt(418,liSoc12);
					pstmtObj.setFloat(419,liSoh12);
					pstmtObj.setFloat(420,liBattTemp12);
					pstmtObj.setString(421,liBattStatus12);
					/*Li13*/
					pstmtObj.setFloat(422,liBattVolt13);
					pstmtObj.setFloat(423,liBattCurr13);
					pstmtObj.setInt(424,liSoc13);
					pstmtObj.setFloat(425,liSoh13);
					pstmtObj.setFloat(426,liBattTemp13);
					pstmtObj.setString(427,liBattStatus13);
					/*Li14*/
					pstmtObj.setFloat(428,liBattVolt14);
					pstmtObj.setFloat(429,liBattCurr14);
					pstmtObj.setInt(430,liSoc14);
					pstmtObj.setFloat(431,liSoh14);
					pstmtObj.setFloat(432,liBattTemp14);
					pstmtObj.setString(433,liBattStatus14);
					/*Li15*/
					pstmtObj.setFloat(434,liBattVolt15);
					pstmtObj.setFloat(435,liBattCurr15);
					pstmtObj.setInt(436,liSoc15);
					pstmtObj.setFloat(437,liSoh15);
					pstmtObj.setFloat(438,liBattTemp15);
					pstmtObj.setString(439,liBattStatus15);
					/*Li16*/
					pstmtObj.setFloat(440,liBattVolt16);
					pstmtObj.setFloat(441,liBattCurr16);
					pstmtObj.setInt(442,liSoc16);
					pstmtObj.setFloat(443,liSoh16);
					pstmtObj.setFloat(444,liBattTemp16);
					pstmtObj.setString(445,liBattStatus16);
					/*Li17*/
					pstmtObj.setFloat(446,liBattVolt17);
					pstmtObj.setFloat(447,liBattCurr17);
					pstmtObj.setInt(448,liSoc17);
					pstmtObj.setFloat(449,liSoh17);
					pstmtObj.setFloat(450,liBattTemp17);
					pstmtObj.setString(451,liBattStatus17);
					/*Li18*/
					pstmtObj.setFloat(452,liBattVolt18);
					pstmtObj.setFloat(453,liBattCurr18);
					pstmtObj.setInt(454,liSoc18);
					pstmtObj.setFloat(455,liSoh18);
					pstmtObj.setFloat(456,liBattTemp18);
					pstmtObj.setString(457,liBattStatus18);
					/*Li19*/
					pstmtObj.setFloat(458,liBattVolt19);
					pstmtObj.setFloat(459,liBattCurr19);
					pstmtObj.setInt(460,liSoc19);
					pstmtObj.setFloat(461,liSoh19);
					pstmtObj.setFloat(462,liBattTemp19);
					pstmtObj.setString(463,liBattStatus19);
					/*Li20*/
					pstmtObj.setFloat(464,liBattVolt20);
					pstmtObj.setFloat(465,liBattCurr20);
					pstmtObj.setInt(466,liSoc20);
					pstmtObj.setFloat(467,liSoh20);
					pstmtObj.setFloat(468,liBattTemp20);
					pstmtObj.setString(469,liBattStatus20);

					pstmtObj.executeUpdate();
					logger.info("Data Inserted into trans_latestdata for first time for site: "+siteCode);
				} 
				else {
					
					if(isData){

						lastUpdate = lastData.getLong("rclTimestamp");
					}
					lastData.close();
					if(_recordtime>= lastUpdate) {

						pstmtObj.close();
						logger.info("Updating into trans_latestdata for site: "+siteCode);

						pstmtObj = connObj.prepareStatement("update trans_latestdata set AccID=?,rclTimestamp=?,Batt_3_temp=?,Batt_4_temp=?,Batt_1_temp=?,Batt_2_temp=?,Fuellevel_Percentage=?,Fuellevel_Ltrs=?,Sitebattvolt=?,MDG_R_Volt=?,MDG_Y_Volt=?,MDG_B_Volt=?,MDG_R_Amp=?,MDG_Y_Amp=?,MDG_B_Amp=?,MDG_R_PowerFactor=?,MDG_Y_PowerFactor=?,MDG_B_PowerFactor=?,MDG_R_Power=?,MDG_Y_Power=?,MDG_B_Power=?,MDG_Frequency=?,DG_R_Volt=?,DG_Y_Volt=?,DG_B_Volt=?,DG_R_Amp=?,DG_Y_Amp=?,DG_B_Amp=?,DG_R_PowerFactor=?,DG_Y_PowerFactor=?,DG_B_PowerFactor=?,DG_R_Power=?,DG_Y_Power=?,DG_B_Power=?,DG_Frequency=?,DG_Bat_Volt=?,DG_Tank_Capacity=?,Batt_Charging_Current=?,Batt_Discharging_Current=?,Batt_SOC=?,Batt_Cycle_Count=?,Batt_Capacity_AH=?,Batt_NextFullChargeTime=?,No_of_Batt_Strings=?,MDG_RunHrs=?,DG_RunHrs=?,Batt_RunHrs=?,MDG_Energy=?,DG_Energy=?,Batt_DisEnergy=?,Batt_ChargingEnergy=?,Rect_Input_R_Voltage=?,Rect_Input_Y_Voltage=?,Rect_Input_B_Voltage=?,Rect_Input_R_Current=?,Rect_Input_Y_Current=?,Rect_Input_B_Current=?,Rect_Output_Voltage=?,No_of_Rectfrs=?,Total_Rect_Output_Current=?,Total_DCLoad_Current=?,Total_DCLoad_Energy=?,Opco_1_LoadCurrent=?,Opco_2_LoadCurrent=?,Opco_3_LoadCurrent=?,Opco_4_LoadCurrent=?,Opco_1_Energy=?,Opco_2_Energy=?,Opco_3_Energy=?,Opco_4_Energy=?,InputVoltage_SlrModule1=?,InputCurrent_SlrModule1=?,InputPower_SlrModule1=?,OutputVoltage_SlrModule1=?,OutputCurrent_SlrModule1=?,OutputPower_SlrModule1=?,SolarModule1Status=?,InputVoltage_SlrModule2=?,InputCurrent_SlrModule2=?,InputPower_SlrModule2=?,OutputVoltage_SlrModule2=?,OutputCurrent_SlrModule2=?,OutputPower_SlrModule2=?,SolarModule2Status=?,InputVoltage_SlrModule3=?,InputCurrent_SlrModule3=?,InputPower_SlrModule3=?,OutputVoltage_SlrModule3=?,OutputCurrent_SlrModule3=?,OutputPower_SlrModule3=?,SolarModule3Status=?,InputVoltage_SlrModule4=?,InputCurrent_SlrModule4=?,InputPower_SlrModule4=?,OutputVoltage_SlrModule4=?,OutputCurrent_SlrModule4=?,OutputPower_SlrModule4=?,SolarModule4Status=?,InputVoltage_SlrModule5=?,InputCurrent_SlrModule5=?,InputPower_SlrModule5=?,OutputVoltage_SlrModule5=?,OutputCurrent_SlrModule5=?,OutputPower_SlrModule5=?,SolarModule5Status=?,InputVoltage_SlrModule6=?,InputCurrent_SlrModule6=?,InputPower_SlrModule6=?,OutputVoltage_SlrModule6=?,OutputCurrent_SlrModule6=?,OutputPower_SlrModule6=?,SolarModule6Status=?,InputVoltage_SlrModule7=?,InputCurrent_SlrModule7=?,InputPower_SlrModule7=?,OutputVoltage_SlrModule7=?,OutputCurrent_SlrModule7=?,OutputPower_SlrModule7=?,SolarModule7Status=?,InputVoltage_SlrModule8=?,InputCurrent_SlrModule8=?,InputPower_SlrModule8=?,OutputVoltage_SlrModule8=?,OutputCurrent_SlrModule8=?,OutputPower_SlrModule8=?,SolarModule8Status=?,InputVoltage_SlrModule9=?,InputCurrent_SlrModule9=?,InputPower_SlrModule9=?,OutputVoltage_SlrModule9=?,OutputCurrent_SlrModule9=?,OutputPower_SlrModule9=?,SolarModule9Status=?,InputVoltage_SlrModule10=?,InputCurrent_SlrModule10=?,InputPower_SlrModule10=?,OutputVoltage_SlrModule10=?,OutputCurrent_SlrModule10=?,OutputPower_SlrModule10=?,SolarModule10Status=?,InputVoltage_SlrModule11=?,InputCurrent_SlrModule11=?,InputPower_SlrModule11=?,OutputVoltage_SlrModule11=?,OutputCurrent_SlrModule11=?,OutputPower_SlrModule11=?,SolarModule11Status=?,InputVoltage_SlrModule12=?,InputCurrent_SlrModule12=?,InputPower_SlrModule12=?,OutputVoltage_SlrModule12=?,OutputCurrent_SlrModule12=?,OutputPower_SlrModule12=?,SolarModule12Status=?,No_of_Solar_Module=?,Solar_Input_Energy=?,Solar_Output_Energy=?,SolarRunHrs=?,SolarMDGRunHrs=?,SolarDGRunHrs=?,SolarBattRunHrs=?,PowerSource=?,SolarTotalnputPower=?,SolarTotaOutputPower=?,SysOutLoadStatus=?,MDGRunHrs=?,TeleRunHrs=?,TeleEnergy=?,CommunityLoad_R_Voltage=?,CommunityLoad_Y_Voltage=?,CommunityLoad_B_Voltage=?,CommunityLoad_R_Current=?,CommunityLoad_Y_Current=?,CommunityLoad_B_Current=?,CommunityLoad_R_PowerFactor=?,CommunityLoad_Y_PowerFactor=?,CommunityLoad_B_PowerFactor=?,CommunityLoad_R_Power=?,CommunityLoad_Y_Power=?,CommunityLoad_B_Power=?,CommunityLoad_Frequency=?,CommunityLoad_RunHrs=?,CommunityLoad_Energy=?,InverterInputVoltage_1=?,InverterInputCurrent_1=?,InverterOutVoltage_1=?,InverterOutputCurrent_1=?,InverterOutputEnergy_1=?,InverterTemp_1=?,Ineverter_Status_1=?,InverterInputVoltage_2=?,InverterInputCurrent_2=?,InverterOutVoltage_2=?,InverterOutputCurrent_2=?,InverterOutputEnergy_2=?,InverterTemp_2=?,Ineverter_Status_2=?,InverterInputVoltage_3=?,InverterInputCurrent_3=?,InverterOutVoltage_3=?,InverterOutputCurrent_3=?,InverterOutputEnergy_3=?,InverterTemp_3=?,Ineverter_Status_3=?,InverterInputVoltage_4=?,InverterInputCurrent_4=?,InverterOutVoltage_4=?,InverterOutputCurrent_4=?,InverterOutputEnergy_4=?,InverterTemp_4=?,Ineverter_Status_4=?,InverterInputVoltage_5=?,InverterInputCurrent_5=?,InverterOutVoltage_5=?,InverterOutputCurrent_5=?,InverterOutputEnergy_5=?,InverterTemp_5=?,Ineverter_Status_5=?,InverterInputVoltage_6=?,InverterInputCurrent_6=?,InverterOutVoltage_6=?,InverterOutputCurrent_6=?,InverterOutputEnergy_6=?,InverterTemp_6=?,Ineverter_Status_6=?,InverterInputVoltage_7=?,InverterInputCurrent_7=?,InverterOutVoltage_7=?,InverterOutputCurrent_7=?,InverterOutputEnergy_7=?,InverterTemp_7=?,Ineverter_Status_7=?,InverterInputVoltage_8=?,InverterInputCurrent_8=?,InverterOutVoltage_8=?,InverterOutputCurrent_8=?,InverterOutputEnergy_8=?,InverterTemp_8=?,Ineverter_Status_8=?,InverterInputVoltage_9=?,InverterInputCurrent_9=?,InverterOutVoltage_9=?,InverterOutputCurrent_9=?,InverterOutputEnergy_9=?,InverterTemp_9=?,Ineverter_Status_9=?,InverterInputVoltage_10=?,InverterInputCurrent_10=?,InverterOutVoltage_10=?,InverterOutputCurrent_10=?,InverterOutputEnergy_10=?,InverterTemp_10=?,Ineverter_Status_10=?,InverterInputVoltage_11=?,InverterInputCurrent_11=?,InverterOutVoltage_11=?,InverterOutputCurrent_11=?,InverterOutputEnergy_11=?,InverterTemp_11=?,Ineverter_Status_11=?,InverterInputVoltage_12=?,InverterInputCurrent_12=?,InverterOutVoltage_12=?,InverterOutputCurrent_12=?,InverterOutputEnergy_12=?,InverterTemp_12=?,Ineverter_Status_12=?,No_Of_Inverter_Module=?,InverterRunHrs=?,InverterEnergy=?,InverterInputPower=?,InverterOutputPower=?,ACTEL_R_Voltage=?,ACTEL_Y_Voltage=?,ACTEL_B_Voltage=?,ACTEL_R_Current=?,ACTEL_Y_Current=?,ACTEL_B_Current=?,ACTEL_R_PowerFactor=?,ACTEL_Y_PowerFactor=?,ACTEL_B_PowerFactor=?,ACTEL_R_Power=?,ACTEL_Y_Power=?,ACTEL_B_Power=?,ACTEL_Frequency=?,InputVoltage_SlrModule13=?,InputCurrent_SlrModule13=?,OutputVoltage_SlrModule13=?,OutputCurrent_SlrModule13=?,SolarModule13Status=?,InputVoltage_SlrModule14=?,InputCurrent_SlrModule14=?,OutputVoltage_SlrModule14=?,OutputCurrent_SlrModule14=?,SolarModule14Status=?,InputVoltage_SlrModule15=?,InputCurrent_SlrModule15=?,OutputVoltage_SlrModule15=?,OutputCurrent_SlrModule15=?,SolarModule15Status=?,InputVoltage_SlrModule16=?,InputCurrent_SlrModule16=?,OutputVoltage_SlrModule16=?,OutputCurrent_SlrModule16=?,SolarModule16Status=?,InputVoltage_SlrModule17=?,InputCurrent_SlrModule17=?,OutputVoltage_SlrModule17=?,OutputCurrent_SlrModule17=?,SolarModule17Status=?,InputVoltage_SlrModule18=?,InputCurrent_SlrModule18=?,OutputVoltage_SlrModule18=?,OutputCurrent_SlrModule18=?,SolarModule18Status=?,InputVoltage_SlrModule19=?,InputCurrent_SlrModule19=?,OutputVoltage_SlrModule19=?,OutputCurrent_SlrModule19=?,SolarModule19Status=?,InputVoltage_SlrModule20=?,InputCurrent_SlrModule20=?,OutputVoltage_SlrModule20=?,OutputCurrent_SlrModule20=?,SolarModule20Status=?,InputVoltage_SlrModule21=?,InputCurrent_SlrModule21=?,OutputVoltage_SlrModule21=?,OutputCurrent_SlrModule21=?,SolarModule21Status=?,InputVoltage_SlrModule22=?,InputCurrent_SlrModule22=?,OutputVoltage_SlrModule22=?,OutputCurrent_SlrModule22=?,SolarModule22Status=?,InputVoltage_SlrModule23=?,InputCurrent_SlrModule23=?,OutputVoltage_SlrModule23=?,OutputCurrent_SlrModule23=?,SolarModule23Status=?,InputVoltage_SlrModule24=?,InputCurrent_SlrModule24=?,OutputVoltage_SlrModule24=?,OutputCurrent_SlrModule24=?,SolarModule24Status=?,DBCreationTimestamp=?,Li_BattVoltage_1=? ,Li_BattCurrent_1=? ,Li_SOC_1 =? ,Li_SOH_1 =? ,Li_BattTemp_1 =? ,Li_BattStatus_1 =? ,Li_BattVoltage_2 =? ,Li_BattCurrent_2 =? ,Li_SOC_2 =? ,Li_SOH_2 =? ,Li_BattTemp_2 =? ,Li_BattStatus_2 =? ,Li_BattVoltage_3 =? ,Li_BattCurrent_3 =? ,Li_SOC_3 =? ,Li_SOH_3 =? ,Li_BattTemp_3 =? ,Li_BattStatus_3 =? ,Li_BattVoltage_4 =? ,Li_BattCurrent_4 =? ,Li_SOC_4 =? ,Li_SOH_4 =? ,Li_BattTemp_4 =? ,Li_BattStatus_4 =? ,Li_BattVoltage_5 =? ,Li_BattCurrent_5 =? ,Li_SOC_5 =? ,Li_SOH_5 =? ,Li_BattTemp_5 =? ,Li_BattStatus_5 =? ,Li_BattVoltage_6 =? ,Li_BattCurrent_6 =? ,Li_SOC_6 =? ,Li_SOH_6  =? ,Li_BattTemp_6 =? ,Li_BattStatus_6=? ,Li_BattVoltage_7 =? ,Li_BattCurrent_7 =? ,Li_SOC_7=? ,Li_SOH_7 =? ,Li_BattTemp_7 =? ,Li_BattStatus_7=? ,Li_BattVoltage_8 =? ,Li_BattCurrent_8 =? ,Li_SOC_8 =? ,Li_SOH_8 =? ,Li_BattTemp_8 =? ,Li_BattStatus_8=? ,Li_BattVoltage_9 =? ,Li_BattCurrent_9 =? ,Li_SOC_9=? ,Li_SOH_9=? ,Li_BattTemp_9 =? ,Li_BattStatus_9=? ,Li_BattVoltage_10 =? ,Li_BattCurrent_10 =? ,Li_SOC_10=? ,Li_SOH_10=? ,Li_BattTemp_10 =? ,Li_BattStatus_10=? ,Li_BattVoltage_11 =? ,Li_BattCurrent_11 =? ,Li_SOC_11=? ,Li_SOH_11 =? ,Li_BattTemp_11 =? ,Li_BattStatus_11=? ,Li_BattVoltage_12 =? ,Li_BattCurrent_12 =? ,Li_SOC_12=? ,Li_SOH_12=? ,Li_BattTemp_12 =? ,Li_BattStatus_12=? ,Li_BattVoltage_13 =? ,Li_BattCurrent_13 =? ,Li_SOC_13 =? ,Li_SOH_13 =? ,Li_BattTemp_13 =? ,Li_BattStatus_13 =? ,Li_BattVoltage_14 =? ,Li_BattCurrent_14 =? ,Li_SOC_14=? ,Li_SOH_14 =? ,Li_BattTemp_14 =? ,Li_BattStatus_14=? ,Li_BattVoltage_15 =? ,Li_BattCurrent_15 =? ,Li_SOC_15=? ,Li_SOH_15=? ,Li_BattTemp_15 =? ,Li_BattStatus_15=? ,Li_BattVoltage_16 =? ,Li_BattCurrent_16 =? ,Li_SOC_16=? ,Li_SOH_16=? ,Li_BattTemp_16 =? ,Li_BattStatus_16=? ,Li_BattVoltage_17 =? ,Li_BattCurrent_17 =? ,Li_SOC_17=? ,Li_SOH_17 =? ,Li_BattTemp_17 =? ,Li_BattStatus_17 =? ,Li_BattVoltage_18 =? ,Li_BattCurrent_18 =? ,Li_SOC_18 =? ,Li_SOH_18 =? ,Li_BattTemp_18 =? ,Li_BattStatus_18 =? ,Li_BattVoltage_19 =? ,Li_BattCurrent_19 =? ,Li_SOC_19=? ,Li_SOH_19=? ,Li_BattTemp_19 =? ,Li_BattStatus_19 =? ,Li_BattVoltage_20 =? ,Li_BattCurrent_20 =? ,Li_SOC_20=? ,Li_SOH_20=? ,Li_BattTemp_20 =? ,Li_BattStatus_20=? where smSiteID=? ;");

						pstmtObj.setInt(1, 1);
						pstmtObj.setLong(2, _recordtime);
						pstmtObj.setFloat(3,battery_Temp3/10);
						pstmtObj.setFloat(4,battery_Temp4/10);
						pstmtObj.setFloat(5,battery_Temp1/10);
						pstmtObj.setFloat(6,battery_Temp2/10);
						pstmtObj.setInt(7,fuelLevel);
						pstmtObj.setInt(8,fuelLevelltr);
						pstmtObj.setFloat(9,siteBattVolt/10);
						pstmtObj.setFloat(10,mdgRVolt);
						pstmtObj.setFloat(11,mdgYVolt);
						pstmtObj.setFloat(12,mdgBVolt);
						pstmtObj.setFloat(13,mdgRCurrent/10);
						pstmtObj.setFloat(14,mdgYCurrent/10);
						pstmtObj.setFloat(15,mdgBCurrent/10);
						pstmtObj.setFloat(16,mdgRPf/100);
						pstmtObj.setFloat(17,mdgyPF/100);
						pstmtObj.setFloat(18,mdgBPf/100);
						pstmtObj.setFloat(19,mdgRPower/10);
						pstmtObj.setFloat(20,mdgyPower/10);
						pstmtObj.setFloat(21,mdgBPower/10);
						pstmtObj.setFloat(22,mdgFrequency/10);
						pstmtObj.setFloat(23,dgRVoltage);
						pstmtObj.setFloat(24,dgYVoltage);
						pstmtObj.setFloat(25,dgBVoltage);
						pstmtObj.setFloat(26,dgRCurrent/10);
						pstmtObj.setFloat(27,dgYCurrent/10);
						pstmtObj.setFloat(28,dgBCurrent/10);
						pstmtObj.setFloat(29,dgRPf/100);
						pstmtObj.setFloat(30,dgYPf/100);
						pstmtObj.setFloat(31,dgBPf/100);
						pstmtObj.setFloat(32,dgRPower/10);
						pstmtObj.setFloat(33,dgYPower/10);
						pstmtObj.setFloat(34,dgBPower/10);
						pstmtObj.setFloat(35,dgFrequency/10);
						pstmtObj.setFloat(36,dgBattVolt/10);
						pstmtObj.setFloat(37,dgTankCapacity);
						pstmtObj.setFloat(38,batteryChargeCurrent/10);
						pstmtObj.setFloat(39,batteryDisChargeCurrent/10);
						pstmtObj.setFloat(40,batterySOC);
						pstmtObj.setFloat(41,batteryCycleCount);
						pstmtObj.setFloat(42,batteryCapacityinAh);
						pstmtObj.setFloat(43,batteryNextFullChgItem/10);
						pstmtObj.setInt(44,noOfBatteryString);
						pstmtObj.setFloat(45,mdgRunningHr/10);
						pstmtObj.setFloat(46,dgRunningHr/10);
						pstmtObj.setFloat(47,battRunningHr/10);
						pstmtObj.setFloat(48,mdgEnergy/10);
						pstmtObj.setFloat(49,dgEnergy/10);
						pstmtObj.setFloat(50,battDisEnergy/10);
						pstmtObj.setFloat(51,battChgEnergy/10);
						pstmtObj.setInt(52,rectInRVol);
						pstmtObj.setInt(53,rectInYVol);
						pstmtObj.setInt(54,rectInBVol);
						pstmtObj.setFloat(55,rectInRCurr/10);
						pstmtObj.setFloat(56,rectInYCurr/10);
						pstmtObj.setFloat(57,rectInBCurr/10);
						pstmtObj.setFloat(58,rectOutV/10);
						pstmtObj.setInt(59,noOfRec);
						pstmtObj.setFloat(60,totRecOpCurr/10);
						pstmtObj.setFloat(61,totDcLoadCurr/10);
						pstmtObj.setFloat(62,totDcLoadEnrgy/10);
						pstmtObj.setFloat(63,opc1LoadCurrent/10);
						pstmtObj.setFloat(64,opc2LoadCurrent/10);
						pstmtObj.setFloat(65,opc3LoadCurrent/10);
						pstmtObj.setFloat(66,opc4LoadCurrent/10);
						pstmtObj.setFloat(67,opc1Energy/10);
						pstmtObj.setFloat(68,opc2Energy/10);
						pstmtObj.setFloat(69,opc3Energy/10);
						pstmtObj.setFloat(70,opc4Energy/10);
						pstmtObj.setFloat(71,pvIpVoltMpod1/10);
						pstmtObj.setFloat(72,pvIpCurrMpod1/10);
						pstmtObj.setFloat(73,pvIpPowMpod1/10);
						pstmtObj.setFloat(74,pvOpVoltMpod1/10);
						pstmtObj.setFloat(75,pvOpCurrMpod1/10);
						pstmtObj.setFloat(76,pvOpPowMpod1/10);
						pstmtObj.setString(77,solarStatusMod1);
						pstmtObj.setFloat(78,pvIpVoltMod2/10);
						pstmtObj.setFloat(79,pvIpCurMod2/10);
						pstmtObj.setFloat(80,pvIpPowMod2/10);
						pstmtObj.setFloat(81,pvOpVoltMod2/10);
						pstmtObj.setFloat(82,pvOpCurrMod2/10);
						pstmtObj.setFloat(83,pvOpPowMod2/10);
						pstmtObj.setString(84,solarStatusMod2);
						pstmtObj.setFloat(85,pvIpVoltMpod3/10);
						pstmtObj.setFloat(86,pvIpCurrMpod3/10);
						pstmtObj.setFloat(87,pvIpPowMpod3/10);
						pstmtObj.setFloat(88,pvOpVoltMpod3/10);
						pstmtObj.setFloat(89,pvOpCurrMpod3/10);
						pstmtObj.setFloat(90,pvOpPowMpod3/10);
						pstmtObj.setString(91,solarStatusMod3);
						pstmtObj.setFloat(92,pvIpVoltMpod4/10);
						pstmtObj.setFloat(93,pvIpCurrMpod4/10);
						pstmtObj.setFloat(94,pvIpPowMpod4/10);
						pstmtObj.setFloat(95,pvOpVoltMpod4/10);
						pstmtObj.setFloat(96,pvOpCurrMpod4/10);
						pstmtObj.setFloat(97,pvOpPowMpod4/10);
						pstmtObj.setString(98,solarStatusMod4);
						pstmtObj.setFloat(99,pvIpVoltMpod5/10);
						pstmtObj.setFloat(100,pvIpCurrMpod5/10);
						pstmtObj.setFloat(101,pvIpPowMpod5/10);
						pstmtObj.setFloat(102,pvOpVoltMpod5/10);
						pstmtObj.setFloat(103,pvOpCurrMpod5/10);
						pstmtObj.setFloat(104,pvOpPowMpod5/10);
						pstmtObj.setString(105,solarStatusMod5);
						pstmtObj.setFloat(106,pvIpVoltMpod6/10);
						pstmtObj.setFloat(107,pvIpCurrMpod6/10);
						pstmtObj.setFloat(108,pvIpPowMpod6/10);
						pstmtObj.setFloat(109,pvOpVoltMpod6/10);
						pstmtObj.setFloat(110,pvOpCurrMpod6/10);
						pstmtObj.setFloat(111,pvOpPowMpod6/10);
						pstmtObj.setString(112,solarStatusMod6);
						pstmtObj.setFloat(113,pvIpVoltMpod7/10);
						pstmtObj.setFloat(114,pvIpCurrMpod7/10);
						pstmtObj.setFloat(115,pvIpPowMpod7/10);
						pstmtObj.setFloat(116,pvOpVoltMpod7/10);
						pstmtObj.setFloat(117,pvOpCurrMpod7/10);
						pstmtObj.setFloat(118,pvOpPowMpod7/10);
						pstmtObj.setString(119,solarStatusMod7);
						pstmtObj.setFloat(120,pvIpVoltMpod8/10);
						pstmtObj.setFloat(121,pvIpCurrMpod8/10);
						pstmtObj.setFloat(122,pvIpPowMpod8/10);
						pstmtObj.setFloat(123,pvOpVoltMpod8/10);
						pstmtObj.setFloat(124,pvOpCurrMpod8/10);
						pstmtObj.setFloat(125,pvOpPowMpod8/10);
						pstmtObj.setString(126,solarStatusMod8);
						pstmtObj.setFloat(127,pvIpVoltMpod9/10);
						pstmtObj.setFloat(128,pvIpCurrMpod9/10);
						pstmtObj.setFloat(129,pvIpPowMpod9/10);
						pstmtObj.setFloat(130,pvOpVoltMpod9/10);
						pstmtObj.setFloat(131,pvOpCurrMpod9/10);
						pstmtObj.setFloat(132,pvOpPowMpod9/10);
						pstmtObj.setString(133,solarStatusMod9);
						pstmtObj.setFloat(134,pvIpVoltMpod10/10);
						pstmtObj.setFloat(135,pvIpCurrMpod10/10);
						pstmtObj.setFloat(136,pvIpPowMpod10/10);
						pstmtObj.setFloat(137,pvOpVoltMpod10/10);
						pstmtObj.setFloat(138,pvOpCurrMpod10/10);
						pstmtObj.setFloat(139,pvOpPowMpod10/10);
						pstmtObj.setString(140,solarStatusMod10);
						pstmtObj.setFloat(141,pvIpVoltMpod11/10);
						pstmtObj.setFloat(142,pvIpCurrMpod11/10);
						pstmtObj.setFloat(143,pvIpPowMpod11/10);
						pstmtObj.setFloat(144,pvOpVoltMpod11/10);
						pstmtObj.setFloat(145,pvOpCurrMpod11/10);
						pstmtObj.setFloat(146,pvOpPowMpod11/10);
						pstmtObj.setString(147,solarStatusMod11);
						pstmtObj.setFloat(148,pvIpVoltMpod12/10);
						pstmtObj.setFloat(149,pvIpCurrMpod12/10);
						pstmtObj.setFloat(150,pvIpPowMpod12/10);
						pstmtObj.setFloat(151,pvOpVoltMpod12/10);
						pstmtObj.setFloat(152,pvOpCurrMpod12/10);
						pstmtObj.setFloat(153,pvOpPowMpod12/10);
						pstmtObj.setString(154,solarStatusMod12);
						pstmtObj.setInt(155,noOfSolarModule);
						pstmtObj.setDouble(156,solarIpEnergy/10);
						pstmtObj.setDouble(157,solarOpEnergy/10);
						pstmtObj.setDouble(158,solarRunHour/10);
						pstmtObj.setDouble(159,solarMdgRunHour/10);
						pstmtObj.setDouble(160,solardgRunHour/10);
						pstmtObj.setDouble(161,solarBattRunHour/10);
						pstmtObj.setString(162,powerSource);
						pstmtObj.setFloat(163,solarTotIpPower/100);
						pstmtObj.setFloat(164,solarTotOpPower/100);
						pstmtObj.setString(165,sysOutLoadStatus);
						pstmtObj.setDouble(166,mobileDGRunHr/10);
						pstmtObj.setDouble(167,teleRunHr/10);
						pstmtObj.setDouble(168,teleEnergy/10);
						pstmtObj.setFloat(169,acLoadRVolt);
						pstmtObj.setFloat(170,acLoadYVolt);
						pstmtObj.setFloat(171,acLoadBVolt);
						pstmtObj.setFloat(172,acLoadRCurrent/10);
						pstmtObj.setFloat(173,acLoadYCurrent/10);
						pstmtObj.setFloat(174,acLoadBCurrent/10);
						pstmtObj.setFloat(175,acLoadRPF/100);
						pstmtObj.setFloat(176,acLoadYPF/100);
						pstmtObj.setFloat(177,acLoadBPF/100);
						pstmtObj.setFloat(178,acLoadRPower/10);
						pstmtObj.setFloat(179,acLoadYPower/10);
						pstmtObj.setFloat(180,acLoadBPower/10);
						pstmtObj.setFloat(181,acLoadFreq/10);
						pstmtObj.setDouble(182,acLoadRunHr/10);
						pstmtObj.setDouble(183,acLoadEnergy/10);
						pstmtObj.setFloat(184,invDcInVolt1/10);
						pstmtObj.setFloat(185,invDcInCurrent1/10);
						pstmtObj.setFloat(186,invAcOpVolt1);
						pstmtObj.setFloat(187,invAcOpCurrent1/10);
						pstmtObj.setFloat(188,invAcOpPower1/10);
						pstmtObj.setFloat(189,invTemp1/10);
						pstmtObj.setString(190,invStatus1);
						pstmtObj.setFloat(191,invDcInVolt2/10);
						pstmtObj.setFloat(192,invDcInCurrent2/10);
						pstmtObj.setFloat(193,invAcOpVolt2);
						pstmtObj.setFloat(194,invAcOpCurrent2/10);
						pstmtObj.setFloat(195,invAcOpPower2/10);
						pstmtObj.setFloat(196,invTemp2/10);
						pstmtObj.setString(197,invStatus2);
						pstmtObj.setFloat(198,invDcInVolt3/10);
						pstmtObj.setFloat(199,invDcInCurrent3/10);
						pstmtObj.setFloat(200,invAcOpVolt3);
						pstmtObj.setFloat(201,invAcOpCurrent3/10);
						pstmtObj.setFloat(202,invAcOpPower3/10);
						pstmtObj.setFloat(203,invTemp3/10);
						pstmtObj.setString(204,invStatus3);
						pstmtObj.setFloat(205,invDcInVolt4/10);
						pstmtObj.setFloat(206,invDcInCurrent4/10);
						pstmtObj.setFloat(207,invAcOpVolt4);
						pstmtObj.setFloat(208,invAcOpCurrent4/10);
						pstmtObj.setFloat(209,invAcOpPower4/10);
						pstmtObj.setFloat(210,invTemp4/10);
						pstmtObj.setString(211,invStatus4);
						pstmtObj.setFloat(212,invDcInVolt5/10);
						pstmtObj.setFloat(213,invDcInCurrent5/10);
						pstmtObj.setFloat(214,invAcOpVolt5);
						pstmtObj.setFloat(215,invAcOpCurrent5/10);
						pstmtObj.setFloat(216,invAcOpPower5/10);
						pstmtObj.setFloat(217,invTemp5/10);
						pstmtObj.setString(218,invStatus5);
						pstmtObj.setFloat(219,invDcInVolt6/10);
						pstmtObj.setFloat(220,invDcInCurrent6/10);
						pstmtObj.setFloat(221,invAcOpVolt6);
						pstmtObj.setFloat(222,invAcOpCurrent6/10);
						pstmtObj.setFloat(223,invAcOpPower6/10);
						pstmtObj.setFloat(224,invTemp6/10);
						pstmtObj.setString(225,invStatus6);
						pstmtObj.setFloat(226,invDcInVolt7/10);
						pstmtObj.setFloat(227,invDcInCurrent7/10);
						pstmtObj.setFloat(228,invAcOpVolt7);
						pstmtObj.setFloat(229,invAcOpCurrent7/10);
						pstmtObj.setFloat(230,invAcOpPower7/10);
						pstmtObj.setFloat(231,invTemp7/10);
						pstmtObj.setString(232,invStatus7);
						pstmtObj.setFloat(233,invDcInVolt8/10);
						pstmtObj.setFloat(234,invDcInCurrent8/10);
						pstmtObj.setFloat(235,invAcOpVolt8);
						pstmtObj.setFloat(236,invAcOpCurrent8/10);
						pstmtObj.setFloat(237,invAcOpPower8/10);
						pstmtObj.setFloat(238,invTemp8/10);
						pstmtObj.setString(239,invStatus8);
						pstmtObj.setFloat(240,invDcInVolt9/10);
						pstmtObj.setFloat(241,invDcInCurrent9/10);
						pstmtObj.setFloat(242,invAcOpVolt9);
						pstmtObj.setFloat(243,invAcOpCurrent9/10);
						pstmtObj.setFloat(244,invAcOpPower9/10);
						pstmtObj.setFloat(245,invTemp9/10);
						pstmtObj.setString(246,invStatus9);
						pstmtObj.setFloat(247,invDcInVolt10/10);
						pstmtObj.setFloat(248,invDcInCurrent10/10);
						pstmtObj.setFloat(249,invAcOpVolt10);
						pstmtObj.setFloat(250,invAcOpCurrent10/10);
						pstmtObj.setFloat(251,invAcOpPower10/10);
						pstmtObj.setFloat(252,invTemp10/10);
						pstmtObj.setString(253,invStatus10);
						pstmtObj.setFloat(254,invDcInVolt11/10);
						pstmtObj.setFloat(255,invDcInCurrent11/10);
						pstmtObj.setFloat(256,invAcOpVolt11);
						pstmtObj.setFloat(257,invAcOpCurrent11/10);
						pstmtObj.setFloat(258,invAcOpPower11/10);
						pstmtObj.setFloat(259,invTemp11/10);
						pstmtObj.setString(260,invStatus11);
						pstmtObj.setFloat(261,invDcInCurrent12/10);
						pstmtObj.setFloat(262,invDcInVolt12/10);
						pstmtObj.setFloat(263,inAcOpVoltage12);
						pstmtObj.setFloat(264,inAcOpCurrent12/10);
						pstmtObj.setFloat(265,inAcOpPower12/10);
						pstmtObj.setFloat(266,invTemp12/10);
						pstmtObj.setString(267,invStatus12);
						pstmtObj.setInt(268,noOfInvModule);
						pstmtObj.setDouble(269,invRunHr/10);
						pstmtObj.setDouble(270,invEnergy/10);
						pstmtObj.setFloat(271,invTotIpPower/100);
						pstmtObj.setFloat(272,invTotOpPower/100);
						pstmtObj.setFloat(273,actelLoadRVolt);
						pstmtObj.setFloat(274,actelLoadYVolt);
						pstmtObj.setFloat(275,actelLoadBVolt);
						pstmtObj.setFloat(276,actelLoadRCurr/10);
						pstmtObj.setFloat(277,actelLoadYCurr/10);
						pstmtObj.setFloat(278,actelLoadBCurr/10);
						pstmtObj.setFloat(279,actelLoadRPF/100);
						pstmtObj.setFloat(280,actelLoadYPF/100);
						pstmtObj.setFloat(281,actelLoadBPF/100);
						pstmtObj.setFloat(282,actelLoadRPower/10);
						pstmtObj.setFloat(283,actelLoadYPower/10);
						pstmtObj.setFloat(284,actelLoadBPower/10);
						pstmtObj.setFloat(285,actelLoadFre/10);
						pstmtObj.setFloat(286,pvIpVoltMod13/10);
						pstmtObj.setFloat(287,pvIpCurrMod13/10);
						pstmtObj.setFloat(288,pvOpVoltMod13/10);
						pstmtObj.setFloat(289,pvOpCurrMod13/10);
						pstmtObj.setString(290,solarStatusMod13);
						pstmtObj.setFloat(291,pvIpVoltMod14/10);
						pstmtObj.setFloat(292,pvIpCurrMod14/10);
						pstmtObj.setFloat(293,pvOpVoltMod14/10);
						pstmtObj.setFloat(294,pvOpCurrMod14/10);
						pstmtObj.setString(295,solarStatusMod14);
						pstmtObj.setFloat(296,pvIpVoltMod15/10);
						pstmtObj.setFloat(297,pvIpCurrMod15/10);
						pstmtObj.setFloat(298,pvOpVoltMod15/10);
						pstmtObj.setFloat(299,pvOpCurrMod15/10);
						pstmtObj.setString(300,solarStatusMod15);
						pstmtObj.setFloat(301,pvIpVoltMod16/10);
						pstmtObj.setFloat(302,pvIpCurrMod16/10);
						pstmtObj.setFloat(303,pvOpVoltMod16/10);
						pstmtObj.setFloat(304,pvOpCurrMod16/10);
						pstmtObj.setString(305,solarStatusMod16);
						pstmtObj.setFloat(306,pvIpVoltMod17/10);
						pstmtObj.setFloat(307,pvIpCurrMod17/10);
						pstmtObj.setFloat(308,pvOpVoltMod17/10);
						pstmtObj.setFloat(309,pvOpCurrMod17/10);
						pstmtObj.setString(310,solarStatusMod17);
						pstmtObj.setFloat(311,pvIpVoltMod18/10);
						pstmtObj.setFloat(312,pvIpCurrMod18/10);
						pstmtObj.setFloat(313,pvOpVoltMod18/10);
						pstmtObj.setFloat(314,pvOpCurrMod18/10);
						pstmtObj.setString(315,solarStatusMod18);
						pstmtObj.setFloat(316,pvIpVoltMod19/10);
						pstmtObj.setFloat(317,pvIpCurrMod19/10);
						pstmtObj.setFloat(318,pvOpVoltMod19/10);
						pstmtObj.setFloat(319,pvOpCurrMod19/10);
						pstmtObj.setString(320,solarStatusMod19);
						pstmtObj.setFloat(321,pvIpVoltMod20/10);
						pstmtObj.setFloat(322,pvIpCurrMod20/10);
						pstmtObj.setFloat(323,pvOpVoltMod20/10);
						pstmtObj.setFloat(324,pvOpCurrMod20/10);
						pstmtObj.setString(325,solarStatusMod20);
						pstmtObj.setFloat(326,pvIpVoltMod21/10);
						pstmtObj.setFloat(327,pvIpCurrMod21/10);
						pstmtObj.setFloat(328,pvOpVoltMod21/10);
						pstmtObj.setFloat(329,pvOpCurrMod21/10);
						pstmtObj.setString(330,solarStatusMod21);
						pstmtObj.setFloat(331,pvIpVoltMod22/10);
						pstmtObj.setFloat(332,pvIpCurrMod22/10);
						pstmtObj.setFloat(333,pvOpVoltMod22/10);
						pstmtObj.setFloat(334,pvOpCurrMod22/10);
						pstmtObj.setString(335,solarStatusMod22);
						pstmtObj.setFloat(336,pvIpVoltMod23/10);
						pstmtObj.setFloat(337,pvIpCurrMod23/10);
						pstmtObj.setFloat(338,pvOpVoltMod23/10);
						pstmtObj.setFloat(339,pvOpCurrMod23/10);
						pstmtObj.setString(340,solarStatusMod23);
						pstmtObj.setFloat(341,pvIpVoltMod24/10);
						pstmtObj.setFloat(342,pvIpCurrMod24/10);
						pstmtObj.setFloat(343,pvOpVoltMod24/10);
						pstmtObj.setFloat(344,pvOpCurrMod24/10);
						pstmtObj.setString(345,solarStatusMod24);

						/*TODO*/
						pstmtObj.setLong(346,dbCreationTime);
						pstmtObj.setFloat(347,liBattVolt1);
						pstmtObj.setFloat(348,liBattCurr1);
						pstmtObj.setInt(349,liSoc1);
						pstmtObj.setFloat(350,liSoh1);
						pstmtObj.setFloat(351,liBattTemp1);
						pstmtObj.setString(352,liBattStatus1);
						/*Li2*/
						pstmtObj.setFloat(353,liBattVolt2);
						pstmtObj.setFloat(354,liBattCurr2);
						pstmtObj.setInt(355,liSoc2);
						pstmtObj.setFloat(356,liSoh2);
						pstmtObj.setFloat(357,liBattTemp2);
						pstmtObj.setString(358,liBattStatus2);
						/*Li3*/
						pstmtObj.setFloat(359,liBattVolt3);
						pstmtObj.setFloat(360,liBattCurr3);
						pstmtObj.setInt(361,liSoc3);
						pstmtObj.setFloat(362,liSoh3);
						pstmtObj.setFloat(363,liBattTemp3);
						pstmtObj.setString(364,liBattStatus3);
						/*Li4*/
						pstmtObj.setFloat(365,liBattVolt4);
						pstmtObj.setFloat(366,liBattCurr4);
						pstmtObj.setInt(367,liSoc4);
						pstmtObj.setFloat(368,liSoh4);
						pstmtObj.setFloat(369,liBattTemp4);
						pstmtObj.setString(370,liBattStatus4);
						/*Li5*/
						pstmtObj.setFloat(371,liBattVolt5);
						pstmtObj.setFloat(372,liBattCurr5);
						pstmtObj.setInt(373,liSoc5);
						pstmtObj.setFloat(374,liSoh5);
						pstmtObj.setFloat(375,liBattTemp5);
						pstmtObj.setString(376,liBattStatus5);
						/*Li6*/
						pstmtObj.setFloat(377,liBattVolt6);
						pstmtObj.setFloat(378,liBattCurr6);
						pstmtObj.setInt(379,liSoc6);
						pstmtObj.setFloat(380,liSoh6);
						pstmtObj.setFloat(381,liBattTemp6);
						pstmtObj.setString(382,liBattStatus6);
						/*Li7*/
						pstmtObj.setFloat(383,liBattVolt7);
						pstmtObj.setFloat(384,liBattCurr7);
						pstmtObj.setInt(385,liSoc7);
						pstmtObj.setFloat(386,liSoh7);
						pstmtObj.setFloat(387,liBattTemp7);
						pstmtObj.setString(388,liBattStatus7);
						/*Li8*/
						pstmtObj.setFloat(389,liBattVolt8);
						pstmtObj.setFloat(390,liBattCurr8);
						pstmtObj.setInt(391,liSoc8);
						pstmtObj.setFloat(392,liSoh8);
						pstmtObj.setFloat(393,liBattTemp8);
						pstmtObj.setString(394,liBattStatus8);
						/*Li9*/
						pstmtObj.setFloat(395,liBattVolt9);
						pstmtObj.setFloat(396,liBattCurr9);
						pstmtObj.setInt(397,liSoc9);
						pstmtObj.setFloat(398,liSoh9);
						pstmtObj.setFloat(399,liBattTemp9);
						pstmtObj.setString(400,liBattStatus9);
						/*Li10*/
						pstmtObj.setFloat(401,liBattVolt10);
						pstmtObj.setFloat(402,liBattCurr10);
						pstmtObj.setInt(403,liSoc10);
						pstmtObj.setFloat(404,liSoh10);
						pstmtObj.setFloat(405,liBattTemp10);
						pstmtObj.setString(406,liBattStatus10);
						/*Li11*/
						pstmtObj.setFloat(407,liBattVolt11);
						pstmtObj.setFloat(408,liBattCurr11);
						pstmtObj.setInt(409,liSoc11);
						pstmtObj.setFloat(410,liSoh11);
						pstmtObj.setFloat(411,liBattTemp11);
						pstmtObj.setString(412,liBattStatus11);
						/*Li12*/
						pstmtObj.setFloat(413,liBattVolt12);
						pstmtObj.setFloat(414,liBattCurr12);
						pstmtObj.setInt(415,liSoc12);
						pstmtObj.setFloat(416,liSoh12);
						pstmtObj.setFloat(417,liBattTemp12);
						pstmtObj.setString(418,liBattStatus12);
						/*Li13*/
						pstmtObj.setFloat(419,liBattVolt13);
						pstmtObj.setFloat(420,liBattCurr13);
						pstmtObj.setInt(421,liSoc13);
						pstmtObj.setFloat(422,liSoh13);
						pstmtObj.setFloat(423,liBattTemp13);
						pstmtObj.setString(424,liBattStatus13);
						/*Li14*/
						pstmtObj.setFloat(425,liBattVolt14);
						pstmtObj.setFloat(426,liBattCurr14);
						pstmtObj.setInt(427,liSoc14);
						pstmtObj.setFloat(428,liSoh14);
						pstmtObj.setFloat(429,liBattTemp14);
						pstmtObj.setString(430,liBattStatus14);
						/*Li15*/
						pstmtObj.setFloat(431,liBattVolt15);
						pstmtObj.setFloat(432,liBattCurr15);
						pstmtObj.setInt(433,liSoc15);
						pstmtObj.setFloat(434,liSoh15);
						pstmtObj.setFloat(435,liBattTemp15);
						pstmtObj.setString(436,liBattStatus15);
						/*Li16*/
						pstmtObj.setFloat(437,liBattVolt16);
						pstmtObj.setFloat(438,liBattCurr16);
						pstmtObj.setInt(439,liSoc16);
						pstmtObj.setFloat(440,liSoh16);
						pstmtObj.setFloat(441,liBattTemp16);
						pstmtObj.setString(442,liBattStatus16);
						/*Li17*/
						pstmtObj.setFloat(443,liBattVolt17);
						pstmtObj.setFloat(444,liBattCurr17);
						pstmtObj.setInt(445,liSoc17);
						pstmtObj.setFloat(446,liSoh17);
						pstmtObj.setFloat(447,liBattTemp17);
						pstmtObj.setString(448,liBattStatus17);
						/*Li18*/
						pstmtObj.setFloat(449,liBattVolt18);
						pstmtObj.setFloat(450,liBattCurr18);
						pstmtObj.setInt(451,liSoc18);
						pstmtObj.setFloat(452,liSoh18);
						pstmtObj.setFloat(453,liBattTemp18);
						pstmtObj.setString(454,liBattStatus18);
						/*Li19*/
						pstmtObj.setFloat(455,liBattVolt19);
						pstmtObj.setFloat(456,liBattCurr19);
						pstmtObj.setInt(457,liSoc19);
						pstmtObj.setFloat(458,liSoh19);
						pstmtObj.setFloat(459,liBattTemp19);
						pstmtObj.setString(460,liBattStatus19);
						/*Li20*/
						pstmtObj.setFloat(461,liBattVolt20);
						pstmtObj.setFloat(462,liBattCurr20);
						pstmtObj.setInt(463,liSoc20);
						pstmtObj.setFloat(464,liSoh20);
						pstmtObj.setFloat(465,liBattTemp20);
						pstmtObj.setString(466,liBattStatus20);
						pstmtObj.setInt(467,smSiteId);
						pstmtObj.executeUpdate();
						logger.info("Data Updated into trans_latestdata for site: "+siteCode);
					}
				}

				pstmtObj.close();
				pstmtObj = connObj.prepareStatement("insert into trans_recordsdata(smSiteID,rcdTimestamp,"
						+ "Batt_3_temp,Batt_4_temp,Batt_1_temp,Batt_2_temp,Fuellevel_Percentage,Fuellevel_Ltrs,Sitebattvolt,"
						+ "MDG_R_Volt,MDG_Y_Volt,MDG_B_Volt,MDG_R_Amp,MDG_Y_Amp,MDG_B_Amp,MDG_R_PowerFactor,MDG_Y_PowerFactor,"
						+ "MDG_B_PowerFactor,MDG_R_Power,MDG_Y_Power,MDG_B_Power,MDG_Frequency,DG_R_Volt,DG_Y_Volt,DG_B_Volt,"
						+ "DG_R_Amp,DG_Y_Amp,DG_B_Amp,DG_R_PowerFactor,DG_Y_PowerFactor,DG_B_PowerFactor,DG_R_Power,DG_Y_Power,"
						+ "DG_B_Power,DG_Frequency,DG_Bat_Volt,DG_Tank_Capacity,Batt_Charging_Current,Batt_Discharging_Current,"
						+ "Batt_SOC,Batt_Cycle_Count,Batt_Capacity_AH,Batt_NextFullChargeTime,No_of_Batt_Strings,MDG_RunHrs,"
						+ "DG_RunHrs,Batt_RunHrs,MDG_Energy,DG_Energy,Batt_DisEnergy,Batt_ChargingEnergy,"
						+ "Rect_Input_R_Voltage,Rect_Input_Y_Voltage,Rect_Input_B_Voltage,Rect_Input_R_Current,"
						+ "Rect_Input_Y_Current,Rect_Input_B_Current,Rect_Output_Voltage,No_of_Rectfrs,"
						+ "Total_Rect_Output_Current,Total_DCLoad_Current,Total_DCLoad_Energy,"
						+ "Opco_1_LoadCurrent,Opco_2_LoadCurrent,Opco_3_LoadCurrent,Opco_4_LoadCurrent,"
						+ "Opco_1_Energy,Opco_2_Energy,Opco_3_Energy,Opco_4_Energy,InputVoltage_SlrModule1,"
						+ "InputCurrent_SlrModule1,InputPower_SlrModule1,OutputVoltage_SlrModule1,"
						+ "OutputCurrent_SlrModule1,OutputPower_SlrModule1,SolarModule1Status,"
						+ "InputVoltage_SlrModule2,InputCurrent_SlrModule2,InputPower_SlrModule2,OutputVoltage_SlrModule2,"
						+ "OutputCurrent_SlrModule2,OutputPower_SlrModule2,SolarModule2Status,InputVoltage_SlrModule3,"
						+ "InputCurrent_SlrModule3,InputPower_SlrModule3,OutputVoltage_SlrModule3,OutputCurrent_SlrModule3,"
						+ "OutputPower_SlrModule3,SolarModule3Status,InputVoltage_SlrModule4,InputCurrent_SlrModule4,"
						+ "InputPower_SlrModule4,OutputVoltage_SlrModule4,OutputCurrent_SlrModule4,OutputPower_SlrModule4,"
						+ "SolarModule4Status,InputVoltage_SlrModule5,InputCurrent_SlrModule5,InputPower_SlrModule5,"
						+ "OutputVoltage_SlrModule5,OutputCurrent_SlrModule5,OutputPower_SlrModule5,SolarModule5Status,"
						+ "InputVoltage_SlrModule6,InputCurrent_SlrModule6,InputPower_SlrModule6,OutputVoltage_SlrModule6,"
						+ "OutputCurrent_SlrModule6,OutputPower_SlrModule6,SolarModule6Status,InputVoltage_SlrModule7,"
						+ "InputCurrent_SlrModule7,InputPower_SlrModule7,OutputVoltage_SlrModule7,OutputCurrent_SlrModule7,"
						+ "OutputPower_SlrModule7,SolarModule7Status,InputVoltage_SlrModule8,InputCurrent_SlrModule8,"
						+ "InputPower_SlrModule8,OutputVoltage_SlrModule8,OutputCurrent_SlrModule8,OutputPower_SlrModule8,"
						+ "SolarModule8Status,InputVoltage_SlrModule9,InputCurrent_SlrModule9,InputPower_SlrModule9,"
						+ "OutputVoltage_SlrModule9,OutputCurrent_SlrModule9,OutputPower_SlrModule9,SolarModule9Status,"
						+ "InputVoltage_SlrModule10,InputCurrent_SlrModule10,InputPower_SlrModule10,OutputVoltage_SlrModule10,"
						+ "OutputCurrent_SlrModule10,OutputPower_SlrModule10,SolarModule10Status,InputVoltage_SlrModule11,"
						+ "InputCurrent_SlrModule11,InputPower_SlrModule11,OutputVoltage_SlrModule11,OutputCurrent_SlrModule11,"
						+ "OutputPower_SlrModule11,SolarModule11Status,InputVoltage_SlrModule12,InputCurrent_SlrModule12,"
						+ "InputPower_SlrModule12,OutputVoltage_SlrModule12,OutputCurrent_SlrModule12,OutputPower_SlrModule12,"
						+ "SolarModule12Status,No_of_Solar_Module,Solar_Input_Energy,Solar_Output_Energy,SolarRunHrs,"
						+ "SolarMDGRunHrs,SolarDGRunHrs,SolarBattRunHrs,PowerSource,SolarTotalnputPower,SolarTotaOutputPower,"
						+ "SysOutLoadStatus,MDGRunHrs,TeleRunHrs,TeleEnergy,CommunityLoad_R_Voltage,CommunityLoad_Y_Voltage,CommunityLoad_B_Voltage,"
						+ "CommunityLoad_R_Current,CommunityLoad_Y_Current,CommunityLoad_B_Current,CommunityLoad_R_PowerFactor,CommunityLoad_Y_PowerFactor,CommunityLoad_B_PowerFactor,"
						+ "CommunityLoad_R_Power,CommunityLoad_Y_Power,CommunityLoad_B_Power,CommunityLoad_Frequency,CommunityLoad_RunHrs,CommunityLoad_Energy,"
						+ "InverterInputVoltage_1,InverterInputCurrent_1,InverterOutVoltage_1,InverterOutputCurrent_1,"
						+ "InverterOutputEnergy_1,InverterTemp_1,Ineverter_Status_1,InverterInputVoltage_2,"
						+ "InverterInputCurrent_2,InverterOutVoltage_2,InverterOutputCurrent_2,"
						+ "InverterOutputEnergy_2,InverterTemp_2,Ineverter_Status_2,InverterInputVoltage_3,"
						+ "InverterInputCurrent_3,InverterOutVoltage_3,InverterOutputCurrent_3,"
						+ "InverterOutputEnergy_3,InverterTemp_3,Ineverter_Status_3,InverterInputVoltage_4,"
						+ "InverterInputCurrent_4,InverterOutVoltage_4,InverterOutputCurrent_4,"
						+ "InverterOutputEnergy_4,InverterTemp_4,Ineverter_Status_4,InverterInputVoltage_5,"
						+ "InverterInputCurrent_5,InverterOutVoltage_5,InverterOutputCurrent_5,"
						+ "InverterOutputEnergy_5,InverterTemp_5,Ineverter_Status_5,InverterInputVoltage_6,"
						+ "InverterInputCurrent_6,InverterOutVoltage_6,InverterOutputCurrent_6,InverterOutputEnergy_6,"
						+ "InverterTemp_6,Ineverter_Status_6,InverterInputVoltage_7,InverterInputCurrent_7,"
						+ "InverterOutVoltage_7,InverterOutputCurrent_7,InverterOutputEnergy_7,InverterTemp_7,"
						+ "Ineverter_Status_7,InverterInputVoltage_8,InverterInputCurrent_8,InverterOutVoltage_8,"
						+ "InverterOutputCurrent_8,InverterOutputEnergy_8,InverterTemp_8,Ineverter_Status_8,"
						+ "InverterInputVoltage_9,InverterInputCurrent_9,InverterOutVoltage_9,InverterOutputCurrent_9,"
						+ "InverterOutputEnergy_9,InverterTemp_9,Ineverter_Status_9,InverterInputVoltage_10,"
						+ "InverterInputCurrent_10,InverterOutVoltage_10,InverterOutputCurrent_10,"
						+ "InverterOutputEnergy_10,InverterTemp_10,Ineverter_Status_10,"
						+ "InverterInputVoltage_11,InverterInputCurrent_11,InverterOutVoltage_11,"
						+ "InverterOutputCurrent_11,InverterOutputEnergy_11,InverterTemp_11,"
						+ "Ineverter_Status_11,InverterInputVoltage_12,InverterInputCurrent_12,"
						+ "InverterOutVoltage_12,InverterOutputCurrent_12,InverterOutputEnergy_12,"
						+ "InverterTemp_12,Ineverter_Status_12,No_Of_Inverter_Module,InverterRunHrs,"
						+ "InverterEnergy,InverterInputPower,InverterOutputPower,ACTEL_R_Voltage,"
						+ "ACTEL_Y_Voltage,ACTEL_B_Voltage,ACTEL_R_Current,ACTEL_Y_Current,"
						+ "ACTEL_B_Current,ACTEL_R_PowerFactor,ACTEL_Y_PowerFactor,ACTEL_B_PowerFactor,"
						+ "ACTEL_R_Power,ACTEL_Y_Power,ACTEL_B_Power,ACTEL_Frequency,InputVoltage_SlrModule13,"
						+ "InputCurrent_SlrModule13,OutputVoltage_SlrModule13,OutputCurrent_SlrModule13,SolarModule13Status,"
						+ "InputVoltage_SlrModule14,InputCurrent_SlrModule14,OutputVoltage_SlrModule14,"
						+ "OutputCurrent_SlrModule14,SolarModule14Status,InputVoltage_SlrModule15,InputCurrent_SlrModule15,OutputVoltage_SlrModule15,OutputCurrent_SlrModule15,SolarModule15Status,InputVoltage_SlrModule16,InputCurrent_SlrModule16,OutputVoltage_SlrModule16,OutputCurrent_SlrModule16,SolarModule16Status,InputVoltage_SlrModule17,InputCurrent_SlrModule17,OutputVoltage_SlrModule17,OutputCurrent_SlrModule17,SolarModule17Status,InputVoltage_SlrModule18,InputCurrent_SlrModule18,OutputVoltage_SlrModule18,OutputCurrent_SlrModule18,SolarModule18Status,InputVoltage_SlrModule19,InputCurrent_SlrModule19,OutputVoltage_SlrModule19,OutputCurrent_SlrModule19,SolarModule19Status,InputVoltage_SlrModule20,InputCurrent_SlrModule20,OutputVoltage_SlrModule20,OutputCurrent_SlrModule20,SolarModule20Status,InputVoltage_SlrModule21,InputCurrent_SlrModule21,OutputVoltage_SlrModule21,OutputCurrent_SlrModule21,SolarModule21Status,InputVoltage_SlrModule22,InputCurrent_SlrModule22,OutputVoltage_SlrModule22,OutputCurrent_SlrModule22,SolarModule22Status,InputVoltage_SlrModule23,InputCurrent_SlrModule23,OutputVoltage_SlrModule23,OutputCurrent_SlrModule23,SolarModule23Status,InputVoltage_SlrModule24,InputCurrent_SlrModule24,OutputVoltage_SlrModule24,OutputCurrent_SlrModule24,SolarModule24Status,smSitecode,DBCreationTimestamp,smSitetypeid,Li_BattVoltage_1 ,Li_BattCurrent_1 ,Li_SOC_1 ,Li_SOH_1 ,Li_BattTemp_1 ,Li_BattStatus_1 ,Li_BattVoltage_2 ,Li_BattCurrent_2 ,Li_SOC_2 ,Li_SOH_2 ,Li_BattTemp_2 ,Li_BattStatus_2 ,Li_BattVoltage_3 ,Li_BattCurrent_3 ,Li_SOC_3 ,Li_SOH_3 ,Li_BattTemp_3 ,Li_BattStatus_3 ,Li_BattVoltage_4 ,Li_BattCurrent_4 ,Li_SOC_4 ,Li_SOH_4 ,Li_BattTemp_4 ,Li_BattStatus_4 ,Li_BattVoltage_5 ,Li_BattCurrent_5 ,Li_SOC_5 ,Li_SOH_5 ,Li_BattTemp_5 ,Li_BattStatus_5 ,Li_BattVoltage_6 ,Li_BattCurrent_6 ,Li_SOC_6  ,Li_SOH_6  ,Li_BattTemp_6 ,Li_BattStatus_6,Li_BattVoltage_7 ,Li_BattCurrent_7 ,Li_SOC_7,Li_SOH_7 ,Li_BattTemp_7 ,Li_BattStatus_7,Li_BattVoltage_8 ,Li_BattCurrent_8 ,Li_SOC_8 ,Li_SOH_8 ,Li_BattTemp_8 ,Li_BattStatus_8,Li_BattVoltage_9 ,Li_BattCurrent_9 ,Li_SOC_9,Li_SOH_9,Li_BattTemp_9 ,Li_BattStatus_9,Li_BattVoltage_10 ,Li_BattCurrent_10 ,Li_SOC_10,Li_SOH_10,Li_BattTemp_10 ,Li_BattStatus_10,Li_BattVoltage_11 ,Li_BattCurrent_11 ,Li_SOC_11,Li_SOH_11 ,Li_BattTemp_11 ,Li_BattStatus_11,Li_BattVoltage_12 ,Li_BattCurrent_12 ,Li_SOC_12,Li_SOH_12,Li_BattTemp_12 ,Li_BattStatus_12,Li_BattVoltage_13 ,Li_BattCurrent_13 ,Li_SOC_13 ,Li_SOH_13 ,Li_BattTemp_13 ,Li_BattStatus_13 ,Li_BattVoltage_14 ,Li_BattCurrent_14 ,Li_SOC_14,Li_SOH_14 ,Li_BattTemp_14 ,Li_BattStatus_14,Li_BattVoltage_15 ,Li_BattCurrent_15 ,Li_SOC_15,Li_SOH_15,Li_BattTemp_15 ,Li_BattStatus_15,Li_BattVoltage_16 ,Li_BattCurrent_16 ,Li_SOC_16,Li_SOH_16,Li_BattTemp_16 ,Li_BattStatus_16,Li_BattVoltage_17 ,Li_BattCurrent_17 ,Li_SOC_17,Li_SOH_17 ,Li_BattTemp_17 ,Li_BattStatus_17 ,Li_BattVoltage_18 ,Li_BattCurrent_18 ,Li_SOC_18 ,Li_SOH_18 ,Li_BattTemp_18 ,Li_BattStatus_18 ,Li_BattVoltage_19 ,Li_BattCurrent_19 ,Li_SOC_19,Li_SOH_19,Li_BattTemp_19 ,Li_BattStatus_19 ,Li_BattVoltage_20 ,Li_BattCurrent_20 ,Li_SOC_20,Li_SOH_20,Li_BattTemp_20 ,Li_BattStatus_20,hpDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


				pstmtObj.setInt(1, smSiteId);
				pstmtObj.setLong(2, _recordtime);
				pstmtObj.setFloat(3,battery_Temp3/10);
				pstmtObj.setFloat(4,battery_Temp4/10);
				pstmtObj.setFloat(5,battery_Temp1/10);
				pstmtObj.setFloat(6,battery_Temp2/10);
				pstmtObj.setInt(7,fuelLevel);
				pstmtObj.setInt(8,fuelLevelltr);
				pstmtObj.setFloat(9,siteBattVolt/10);
				pstmtObj.setFloat(10,mdgRVolt);
				pstmtObj.setFloat(11,mdgYVolt);
				pstmtObj.setFloat(12,mdgBVolt);
				pstmtObj.setFloat(13,mdgRCurrent/10);
				pstmtObj.setFloat(14,mdgYCurrent/10);
				pstmtObj.setFloat(15,mdgBCurrent/10);
				pstmtObj.setFloat(16,mdgRPf/100);
				pstmtObj.setFloat(17,mdgyPF/100);
				pstmtObj.setFloat(18,mdgBPf/100);
				pstmtObj.setFloat(19,mdgRPower/10);
				pstmtObj.setFloat(20,mdgyPower/10);
				pstmtObj.setFloat(21,mdgBPower/10);
				pstmtObj.setFloat(22,mdgFrequency/10);
				pstmtObj.setFloat(23,dgRVoltage);
				pstmtObj.setFloat(24,dgYVoltage);
				pstmtObj.setFloat(25,dgBVoltage);
				pstmtObj.setFloat(26,dgRCurrent/10);
				pstmtObj.setFloat(27,dgYCurrent/10);
				pstmtObj.setFloat(28,dgBCurrent/10);
				pstmtObj.setFloat(29,dgRPf/100);
				pstmtObj.setFloat(30,dgYPf/100);
				pstmtObj.setFloat(31,dgBPf/100);
				pstmtObj.setFloat(32,dgRPower/10);
				pstmtObj.setFloat(33,dgYPower/10);
				pstmtObj.setFloat(34,dgBPower/10);
				pstmtObj.setFloat(35,dgFrequency/10);
				pstmtObj.setFloat(36,dgBattVolt/10);
				pstmtObj.setFloat(37,dgTankCapacity);
				pstmtObj.setFloat(38,batteryChargeCurrent/10);
				pstmtObj.setFloat(39,batteryDisChargeCurrent/10);
				pstmtObj.setFloat(40,batterySOC);
				pstmtObj.setFloat(41,batteryCycleCount);
				pstmtObj.setFloat(42,batteryCapacityinAh);
				pstmtObj.setFloat(43,batteryNextFullChgItem/10);
				pstmtObj.setInt(44,noOfBatteryString);
				pstmtObj.setFloat(45,mdgRunningHr/10);
				pstmtObj.setFloat(46,dgRunningHr/10);
				pstmtObj.setFloat(47,battRunningHr/10);
				pstmtObj.setFloat(48,mdgEnergy/10);
				pstmtObj.setFloat(49,dgEnergy/10);
				pstmtObj.setFloat(50,battDisEnergy/10);
				pstmtObj.setFloat(51,battChgEnergy/10);
				pstmtObj.setInt(52,rectInRVol);
				pstmtObj.setInt(53,rectInYVol);
				pstmtObj.setInt(54,rectInBVol);
				pstmtObj.setFloat(55,rectInRCurr/10);
				pstmtObj.setFloat(56,rectInYCurr/10);
				pstmtObj.setFloat(57,rectInBCurr/10);
				pstmtObj.setFloat(58,rectOutV/10);
				pstmtObj.setInt(59,noOfRec);
				pstmtObj.setFloat(60,totRecOpCurr/10);
				pstmtObj.setFloat(61,totDcLoadCurr/10);
				pstmtObj.setFloat(62,totDcLoadEnrgy/10);
				pstmtObj.setFloat(63,opc1LoadCurrent/10);
				pstmtObj.setFloat(64,opc2LoadCurrent/10);
				pstmtObj.setFloat(65,opc3LoadCurrent/10);
				pstmtObj.setFloat(66,opc4LoadCurrent/10);
				pstmtObj.setFloat(67,opc1Energy/10);
				pstmtObj.setFloat(68,opc2Energy/10);
				pstmtObj.setFloat(69,opc3Energy/10);
				pstmtObj.setFloat(70,opc4Energy/10);
				pstmtObj.setFloat(71,pvIpVoltMpod1/10);
				pstmtObj.setFloat(72,pvIpCurrMpod1/10);
				pstmtObj.setFloat(73,pvIpPowMpod1/10);
				pstmtObj.setFloat(74,pvOpVoltMpod1/10);
				pstmtObj.setFloat(75,pvOpCurrMpod1/10);
				pstmtObj.setFloat(76,pvOpPowMpod1/10);
				pstmtObj.setString(77,solarStatusMod1);
				pstmtObj.setFloat(78,pvIpVoltMod2/10);
				pstmtObj.setFloat(79,pvIpCurMod2/10);
				pstmtObj.setFloat(80,pvIpPowMod2/10);
				pstmtObj.setFloat(81,pvOpVoltMod2/10);
				pstmtObj.setFloat(82,pvOpCurrMod2/10);
				pstmtObj.setFloat(83,pvOpPowMod2/10);
				pstmtObj.setString(84,solarStatusMod2);
				pstmtObj.setFloat(85,pvIpVoltMpod3/10);
				pstmtObj.setFloat(86,pvIpCurrMpod3/10);
				pstmtObj.setFloat(87,pvIpPowMpod3/10);
				pstmtObj.setFloat(88,pvOpVoltMpod3/10);
				pstmtObj.setFloat(89,pvOpCurrMpod3/10);
				pstmtObj.setFloat(90,pvOpPowMpod3/10);
				pstmtObj.setString(91,solarStatusMod3);
				pstmtObj.setFloat(92,pvIpVoltMpod4/10);
				pstmtObj.setFloat(93,pvIpCurrMpod4/10);
				pstmtObj.setFloat(94,pvIpPowMpod4/10);
				pstmtObj.setFloat(95,pvOpVoltMpod4/10);
				pstmtObj.setFloat(96,pvOpCurrMpod4/10);
				pstmtObj.setFloat(97,pvOpPowMpod4/10);
				pstmtObj.setString(98,solarStatusMod4);
				pstmtObj.setFloat(99,pvIpVoltMpod5/10);
				pstmtObj.setFloat(100,pvIpCurrMpod5/10);
				pstmtObj.setFloat(101,pvIpPowMpod5/10);
				pstmtObj.setFloat(102,pvOpVoltMpod5/10);
				pstmtObj.setFloat(103,pvOpCurrMpod5/10);
				pstmtObj.setFloat(104,pvOpPowMpod5/10);
				pstmtObj.setString(105,solarStatusMod5);
				pstmtObj.setFloat(106,pvIpVoltMpod6/10);
				pstmtObj.setFloat(107,pvIpCurrMpod6/10);
				pstmtObj.setFloat(108,pvIpPowMpod6/10);
				pstmtObj.setFloat(109,pvOpVoltMpod6/10);
				pstmtObj.setFloat(110,pvOpCurrMpod6/10);
				pstmtObj.setFloat(111,pvOpPowMpod6/10);
				pstmtObj.setString(112,solarStatusMod6);
				pstmtObj.setFloat(113,pvIpVoltMpod7/10);
				pstmtObj.setFloat(114,pvIpCurrMpod7/10);
				pstmtObj.setFloat(115,pvIpPowMpod7/10);
				pstmtObj.setFloat(116,pvOpVoltMpod7/10);
				pstmtObj.setFloat(117,pvOpCurrMpod7/10);
				pstmtObj.setFloat(118,pvOpPowMpod7/10);
				pstmtObj.setString(119,solarStatusMod7);
				pstmtObj.setFloat(120,pvIpVoltMpod8/10);
				pstmtObj.setFloat(121,pvIpCurrMpod8/10);
				pstmtObj.setFloat(122,pvIpPowMpod8/10);
				pstmtObj.setFloat(123,pvOpVoltMpod8/10);
				pstmtObj.setFloat(124,pvOpCurrMpod8/10);
				pstmtObj.setFloat(125,pvOpPowMpod8/10);
				pstmtObj.setString(126,solarStatusMod8);
				pstmtObj.setFloat(127,pvIpVoltMpod9/10);
				pstmtObj.setFloat(128,pvIpCurrMpod9/10);
				pstmtObj.setFloat(129,pvIpPowMpod9/10);
				pstmtObj.setFloat(130,pvOpVoltMpod9/10);
				pstmtObj.setFloat(131,pvOpCurrMpod9/10);
				pstmtObj.setFloat(132,pvOpPowMpod9/10);
				pstmtObj.setString(133,solarStatusMod9);
				pstmtObj.setFloat(134,pvIpVoltMpod10/10);
				pstmtObj.setFloat(135,pvIpCurrMpod10/10);
				pstmtObj.setFloat(136,pvIpPowMpod10/10);
				pstmtObj.setFloat(137,pvOpVoltMpod10/10);
				pstmtObj.setFloat(138,pvOpCurrMpod10/10);
				pstmtObj.setFloat(139,pvOpPowMpod10/10);
				pstmtObj.setString(140,solarStatusMod10);
				pstmtObj.setFloat(141,pvIpVoltMpod11/10);
				pstmtObj.setFloat(142,pvIpCurrMpod11/10);
				pstmtObj.setFloat(143,pvIpPowMpod11/10);
				pstmtObj.setFloat(144,pvOpVoltMpod11/10);
				pstmtObj.setFloat(145,pvOpCurrMpod11/10);
				pstmtObj.setFloat(146,pvOpPowMpod11/10);
				pstmtObj.setString(147,solarStatusMod11);
				pstmtObj.setFloat(148,pvIpVoltMpod12/10);
				pstmtObj.setFloat(149,pvIpCurrMpod12/10);
				pstmtObj.setFloat(150,pvIpPowMpod12/10);
				pstmtObj.setFloat(151,pvOpVoltMpod12/10);
				pstmtObj.setFloat(152,pvOpCurrMpod12/10);
				pstmtObj.setFloat(153,pvOpPowMpod12/10);
				pstmtObj.setString(154,solarStatusMod12);
				pstmtObj.setInt(155,noOfSolarModule);
				pstmtObj.setDouble(156,solarIpEnergy/10);
				pstmtObj.setDouble(157,solarOpEnergy/10);
				pstmtObj.setDouble(158,solarRunHour/10);
				pstmtObj.setDouble(159,solarMdgRunHour/10);
				pstmtObj.setDouble(160,solardgRunHour/10);
				pstmtObj.setDouble(161,solarBattRunHour/10);
				pstmtObj.setString(162,powerSource);
				pstmtObj.setFloat(163,solarTotIpPower/100);
				pstmtObj.setFloat(164,solarTotOpPower/100);
				pstmtObj.setString(165,sysOutLoadStatus);
				pstmtObj.setDouble(166,mobileDGRunHr/10);
				pstmtObj.setDouble(167,teleRunHr/10);
				pstmtObj.setDouble(168,teleEnergy/10);
				pstmtObj.setFloat(169,acLoadRVolt);
				pstmtObj.setFloat(170,acLoadYVolt);
				pstmtObj.setFloat(171,acLoadBVolt);
				pstmtObj.setFloat(172,acLoadRCurrent/10);
				pstmtObj.setFloat(173,acLoadYCurrent/10);
				pstmtObj.setFloat(174,acLoadBCurrent/10);
				pstmtObj.setFloat(175,acLoadRPF/100);
				pstmtObj.setFloat(176,acLoadYPF/100);
				pstmtObj.setFloat(177,acLoadBPF/100);
				pstmtObj.setFloat(178,acLoadRPower/10);
				pstmtObj.setFloat(179,acLoadYPower/10);
				pstmtObj.setFloat(180,acLoadBPower/10);
				pstmtObj.setFloat(181,acLoadFreq/10);
				pstmtObj.setDouble(182,acLoadRunHr/10);
				pstmtObj.setDouble(183,acLoadEnergy/10);
				pstmtObj.setFloat(184,invDcInVolt1/10);
				pstmtObj.setFloat(185,invDcInCurrent1/10);
				pstmtObj.setFloat(186,invAcOpVolt1);
				pstmtObj.setFloat(187,invAcOpCurrent1/10);
				pstmtObj.setFloat(188,invAcOpPower1/10);
				pstmtObj.setFloat(189,invTemp1/10);
				pstmtObj.setString(190,invStatus1);
				pstmtObj.setFloat(191,invDcInVolt2/10);
				pstmtObj.setFloat(192,invDcInCurrent2/10);
				pstmtObj.setFloat(193,invAcOpVolt2);
				pstmtObj.setFloat(194,invAcOpCurrent2/10);
				pstmtObj.setFloat(195,invAcOpPower2/10);
				pstmtObj.setFloat(196,invTemp2/10);
				pstmtObj.setString(197,invStatus2);
				pstmtObj.setFloat(198,invDcInVolt3/10);
				pstmtObj.setFloat(199,invDcInCurrent3/10);
				pstmtObj.setFloat(200,invAcOpVolt3);
				pstmtObj.setFloat(201,invAcOpCurrent3/10);
				pstmtObj.setFloat(202,invAcOpPower3/10);
				pstmtObj.setFloat(203,invTemp3/10);
				pstmtObj.setString(204,invStatus3);
				pstmtObj.setFloat(205,invDcInVolt4/10);
				pstmtObj.setFloat(206,invDcInCurrent4/10);
				pstmtObj.setFloat(207,invAcOpVolt4);
				pstmtObj.setFloat(208,invAcOpCurrent4/10);
				pstmtObj.setFloat(209,invAcOpPower4/10);
				pstmtObj.setFloat(210,invTemp4/10);
				pstmtObj.setString(211,invStatus4);
				pstmtObj.setFloat(212,invDcInVolt5/10);
				pstmtObj.setFloat(213,invDcInCurrent5/10);
				pstmtObj.setFloat(214,invAcOpVolt5);
				pstmtObj.setFloat(215,invAcOpCurrent5/10);
				pstmtObj.setFloat(216,invAcOpPower5/10);
				pstmtObj.setFloat(217,invTemp5/10);
				pstmtObj.setString(218,invStatus5);
				pstmtObj.setFloat(219,invDcInVolt6/10);
				pstmtObj.setFloat(220,invDcInCurrent6/10);
				pstmtObj.setFloat(221,invAcOpVolt6);
				pstmtObj.setFloat(222,invAcOpCurrent6/10);
				pstmtObj.setFloat(223,invAcOpPower6/10);
				pstmtObj.setFloat(224,invTemp6/10);
				pstmtObj.setString(225,invStatus6);
				pstmtObj.setFloat(226,invDcInVolt7/10);
				pstmtObj.setFloat(227,invDcInCurrent7/10);
				pstmtObj.setFloat(228,invAcOpVolt7);
				pstmtObj.setFloat(229,invAcOpCurrent7/10);
				pstmtObj.setFloat(230,invAcOpPower7/10);
				pstmtObj.setFloat(231,invTemp7/10);
				pstmtObj.setString(232,invStatus7);
				pstmtObj.setFloat(233,invDcInVolt8/10);
				pstmtObj.setFloat(234,invDcInCurrent8/10);
				pstmtObj.setFloat(235,invAcOpVolt8);
				pstmtObj.setFloat(236,invAcOpCurrent8/10);
				pstmtObj.setFloat(237,invAcOpPower8/10);
				pstmtObj.setFloat(238,invTemp8/10);
				pstmtObj.setString(239,invStatus8);
				pstmtObj.setFloat(240,invDcInVolt9/10);
				pstmtObj.setFloat(241,invDcInCurrent9/10);
				pstmtObj.setFloat(242,invAcOpVolt9);
				pstmtObj.setFloat(243,invAcOpCurrent9/10);
				pstmtObj.setFloat(244,invAcOpPower9/10);
				pstmtObj.setFloat(245,invTemp9/10);
				pstmtObj.setString(246,invStatus9);
				pstmtObj.setFloat(247,invDcInVolt10/10);
				pstmtObj.setFloat(248,invDcInCurrent10/10);
				pstmtObj.setFloat(249,invAcOpVolt10);
				pstmtObj.setFloat(250,invAcOpCurrent10/10);
				pstmtObj.setFloat(251,invAcOpPower10/10);
				pstmtObj.setFloat(252,invTemp10/10);
				pstmtObj.setString(253,invStatus10);
				pstmtObj.setFloat(254,invDcInVolt11/10);
				pstmtObj.setFloat(255,invDcInCurrent11/10);
				pstmtObj.setFloat(256,invAcOpVolt11);
				pstmtObj.setFloat(257,invAcOpCurrent11/10);
				pstmtObj.setFloat(258,invAcOpPower11/10);
				pstmtObj.setFloat(259,invTemp11/10);
				pstmtObj.setString(260,invStatus11);
				pstmtObj.setFloat(261,invDcInCurrent12/10);
				pstmtObj.setFloat(262,invDcInVolt12/10);
				pstmtObj.setFloat(263,inAcOpVoltage12);
				pstmtObj.setFloat(264,inAcOpCurrent12/10);
				pstmtObj.setFloat(265,inAcOpPower12/10);
				pstmtObj.setFloat(266,invTemp12/10);
				pstmtObj.setString(267,invStatus12);
				pstmtObj.setInt(268,noOfInvModule);
				pstmtObj.setDouble(269,invRunHr/10);
				pstmtObj.setDouble(270,invEnergy/10);
				pstmtObj.setFloat(271,invTotIpPower/100);
				pstmtObj.setFloat(272,invTotOpPower/100);
				pstmtObj.setFloat(273,actelLoadRVolt);
				pstmtObj.setFloat(274,actelLoadYVolt);
				pstmtObj.setFloat(275,actelLoadBVolt);
				pstmtObj.setFloat(276,actelLoadRCurr/10);
				pstmtObj.setFloat(277,actelLoadYCurr/10);
				pstmtObj.setFloat(278,actelLoadBCurr/10);
				pstmtObj.setFloat(279,actelLoadRPF/100);
				pstmtObj.setFloat(280,actelLoadYPF/100);
				pstmtObj.setFloat(281,actelLoadBPF/100);
				pstmtObj.setFloat(282,actelLoadRPower/10);
				pstmtObj.setFloat(283,actelLoadYPower/10);
				pstmtObj.setFloat(284,actelLoadBPower/10);
				pstmtObj.setFloat(285,actelLoadFre/10);
				pstmtObj.setFloat(286,pvIpVoltMod13/10);
				pstmtObj.setFloat(287,pvIpCurrMod13/10);
				pstmtObj.setFloat(288,pvOpVoltMod13/10);
				pstmtObj.setFloat(289,pvOpCurrMod13/10);
				pstmtObj.setString(290,solarStatusMod13);
				pstmtObj.setFloat(291,pvIpVoltMod14/10);
				pstmtObj.setFloat(292,pvIpCurrMod14/10);
				pstmtObj.setFloat(293,pvOpVoltMod14/10);
				pstmtObj.setFloat(294,pvOpCurrMod14/10);
				pstmtObj.setString(295,solarStatusMod14);
				pstmtObj.setFloat(296,pvIpVoltMod15/10);
				pstmtObj.setFloat(297,pvIpCurrMod15/10);
				pstmtObj.setFloat(298,pvOpVoltMod15/10);
				pstmtObj.setFloat(299,pvOpCurrMod15/10);
				pstmtObj.setString(300,solarStatusMod15);
				pstmtObj.setFloat(301,pvIpVoltMod16/10);
				pstmtObj.setFloat(302,pvIpCurrMod16/10);
				pstmtObj.setFloat(303,pvOpVoltMod16/10);
				pstmtObj.setFloat(304,pvOpCurrMod16/10);
				pstmtObj.setString(305,solarStatusMod16);
				pstmtObj.setFloat(306,pvIpVoltMod17/10);
				pstmtObj.setFloat(307,pvIpCurrMod17/10);
				pstmtObj.setFloat(308,pvOpVoltMod17/10);
				pstmtObj.setFloat(309,pvOpCurrMod17/10);
				pstmtObj.setString(310,solarStatusMod17);
				pstmtObj.setFloat(311,pvIpVoltMod18/10);
				pstmtObj.setFloat(312,pvIpCurrMod18/10);
				pstmtObj.setFloat(313,pvOpVoltMod18/10);
				pstmtObj.setFloat(314,pvOpCurrMod18/10);
				pstmtObj.setString(315,solarStatusMod18);
				pstmtObj.setFloat(316,pvIpVoltMod19/10);
				pstmtObj.setFloat(317,pvIpCurrMod19/10);
				pstmtObj.setFloat(318,pvOpVoltMod19/10);
				pstmtObj.setFloat(319,pvOpCurrMod19/10);
				pstmtObj.setString(320,solarStatusMod19);
				pstmtObj.setFloat(321,pvIpVoltMod20/10);
				pstmtObj.setFloat(322,pvIpCurrMod20/10);
				pstmtObj.setFloat(323,pvOpVoltMod20/10);
				pstmtObj.setFloat(324,pvOpCurrMod20/10);
				pstmtObj.setString(325,solarStatusMod20);
				pstmtObj.setFloat(326,pvIpVoltMod21/10);
				pstmtObj.setFloat(327,pvIpCurrMod21/10);
				pstmtObj.setFloat(328,pvOpVoltMod21/10);
				pstmtObj.setFloat(329,pvOpCurrMod21/10);
				pstmtObj.setString(330,solarStatusMod21);
				pstmtObj.setFloat(331,pvIpVoltMod22/10);
				pstmtObj.setFloat(332,pvIpCurrMod22/10);
				pstmtObj.setFloat(333,pvOpVoltMod22/10);
				pstmtObj.setFloat(334,pvOpCurrMod22/10);
				pstmtObj.setString(335,solarStatusMod22);
				pstmtObj.setFloat(336,pvIpVoltMod23/10);
				pstmtObj.setFloat(337,pvIpCurrMod23/10);
				pstmtObj.setFloat(338,pvOpVoltMod23/10);
				pstmtObj.setFloat(339,pvOpCurrMod23/10);
				pstmtObj.setString(340,solarStatusMod23);
				pstmtObj.setFloat(341,pvIpVoltMod24/10);
				pstmtObj.setFloat(342,pvIpCurrMod24/10);
				pstmtObj.setFloat(343,pvOpVoltMod24/10);
				pstmtObj.setFloat(344,pvOpCurrMod24/10);
				pstmtObj.setString(345,solarStatusMod24);

				pstmtObj.setString(346,siteCode);
				pstmtObj.setLong(347,dbCreationTime);
				pstmtObj.setInt(348,smSitetypeid);

				pstmtObj.setFloat(349,liBattVolt1);
				pstmtObj.setFloat(350,liBattCurr1);
				pstmtObj.setInt(351,liSoc1);
				pstmtObj.setFloat(352,liSoh1);
				pstmtObj.setFloat(353,liBattTemp1);
				pstmtObj.setString(354,liBattStatus1);
				/*Li2*/
				pstmtObj.setFloat(355,liBattVolt2);
				pstmtObj.setFloat(356,liBattCurr2);
				pstmtObj.setInt(357,liSoc2);
				pstmtObj.setFloat(358,liSoh2);
				pstmtObj.setFloat(359,liBattTemp2);
				pstmtObj.setString(360,liBattStatus2);
				/*Li3*/
				pstmtObj.setFloat(361,liBattVolt3);
				pstmtObj.setFloat(362,liBattCurr3);
				pstmtObj.setInt(363,liSoc3);
				pstmtObj.setFloat(364,liSoh3);
				pstmtObj.setFloat(365,liBattTemp3);
				pstmtObj.setString(366,liBattStatus3);
				/*Li4*/
				pstmtObj.setFloat(367,liBattVolt4);
				pstmtObj.setFloat(368,liBattCurr4);
				pstmtObj.setInt(369,liSoc4);
				pstmtObj.setFloat(370,liSoh4);
				pstmtObj.setFloat(371,liBattTemp4);
				pstmtObj.setString(372,liBattStatus4);
				/*Li5*/
				pstmtObj.setFloat(373,liBattVolt5);
				pstmtObj.setFloat(374,liBattCurr5);
				pstmtObj.setInt(375,liSoc5);
				pstmtObj.setFloat(376,liSoh5);
				pstmtObj.setFloat(377,liBattTemp5);
				pstmtObj.setString(378,liBattStatus5);
				/*Li6*/
				pstmtObj.setFloat(379,liBattVolt6);
				pstmtObj.setFloat(380,liBattCurr6);
				pstmtObj.setInt(381,liSoc6);
				pstmtObj.setFloat(382,liSoh6);
				pstmtObj.setFloat(383,liBattTemp6);
				pstmtObj.setString(384,liBattStatus6);
				/*Li7*/
				pstmtObj.setFloat(385,liBattVolt7);
				pstmtObj.setFloat(386,liBattCurr7);
				pstmtObj.setInt(387,liSoc7);
				pstmtObj.setFloat(388,liSoh7);
				pstmtObj.setFloat(389,liBattTemp7);
				pstmtObj.setString(390,liBattStatus7);
				/*Li8*/
				pstmtObj.setFloat(391,liBattVolt8);
				pstmtObj.setFloat(392,liBattCurr8);
				pstmtObj.setInt(393,liSoc8);
				pstmtObj.setFloat(394,liSoh8);
				pstmtObj.setFloat(395,liBattTemp8);
				pstmtObj.setString(396,liBattStatus8);
				/*Li9*/
				pstmtObj.setFloat(397,liBattVolt9);
				pstmtObj.setFloat(398,liBattCurr9);
				pstmtObj.setInt(399,liSoc9);
				pstmtObj.setFloat(400,liSoh9);
				pstmtObj.setFloat(401,liBattTemp9);
				pstmtObj.setString(402,liBattStatus9);
				/*Li10*/
				pstmtObj.setFloat(403,liBattVolt10);
				pstmtObj.setFloat(404,liBattCurr10);
				pstmtObj.setInt(405,liSoc10);
				pstmtObj.setFloat(406,liSoh10);
				pstmtObj.setFloat(407,liBattTemp10);
				pstmtObj.setString(408,liBattStatus10);
				/*Li11*/
				pstmtObj.setFloat(409,liBattVolt11);
				pstmtObj.setFloat(410,liBattCurr11);
				pstmtObj.setInt(411,liSoc11);
				pstmtObj.setFloat(412,liSoh11);
				pstmtObj.setFloat(413,liBattTemp11);
				pstmtObj.setString(414,liBattStatus11);
				/*Li12*/
				pstmtObj.setFloat(415,liBattVolt12);
				pstmtObj.setFloat(416,liBattCurr12);
				pstmtObj.setInt(417,liSoc12);
				pstmtObj.setFloat(418,liSoh12);
				pstmtObj.setFloat(419,liBattTemp12);
				pstmtObj.setString(420,liBattStatus12);
				/*Li13*/
				pstmtObj.setFloat(421,liBattVolt13);
				pstmtObj.setFloat(422,liBattCurr13);
				pstmtObj.setInt(423,liSoc13);
				pstmtObj.setFloat(424,liSoh13);
				pstmtObj.setFloat(425,liBattTemp13);
				pstmtObj.setString(426,liBattStatus13);
				/*Li14*/
				pstmtObj.setFloat(427,liBattVolt14);
				pstmtObj.setFloat(428,liBattCurr14);
				pstmtObj.setInt(429,liSoc14);
				pstmtObj.setFloat(430,liSoh14);
				pstmtObj.setFloat(431,liBattTemp14);
				pstmtObj.setString(432,liBattStatus14);
				/*Li15*/
				pstmtObj.setFloat(433,liBattVolt15);
				pstmtObj.setFloat(434,liBattCurr15);
				pstmtObj.setInt(435,liSoc15);
				pstmtObj.setFloat(436,liSoh15);
				pstmtObj.setFloat(437,liBattTemp15);
				pstmtObj.setString(438,liBattStatus15);
				/*Li16*/
				pstmtObj.setFloat(439,liBattVolt16);
				pstmtObj.setFloat(440,liBattCurr16);
				pstmtObj.setInt(441,liSoc16);
				pstmtObj.setFloat(442,liSoh16);
				pstmtObj.setFloat(443,liBattTemp16);
				pstmtObj.setString(444,liBattStatus16);
				/*Li17*/
				pstmtObj.setFloat(445,liBattVolt17);
				pstmtObj.setFloat(446,liBattCurr17);
				pstmtObj.setInt(447,liSoc17);
				pstmtObj.setFloat(448,liSoh17);
				pstmtObj.setFloat(449,liBattTemp17);
				pstmtObj.setString(450,liBattStatus17);
				/*Li18*/
				pstmtObj.setFloat(451,liBattVolt18);
				pstmtObj.setFloat(452,liBattCurr18);
				pstmtObj.setInt(453,liSoc18);
				pstmtObj.setFloat(454,liSoh18);
				pstmtObj.setFloat(455,liBattTemp18);
				pstmtObj.setString(456,liBattStatus18);
				/*Li19*/
				pstmtObj.setFloat(457,liBattVolt19);
				pstmtObj.setFloat(458,liBattCurr19);
				pstmtObj.setInt(459,liSoc19);
				pstmtObj.setFloat(460,liSoh19);
				pstmtObj.setFloat(461,liBattTemp19);
				pstmtObj.setString(462,liBattStatus19);
				/*Li20*/
				pstmtObj.setFloat(463,liBattVolt20);
				pstmtObj.setFloat(464,liBattCurr20);
				pstmtObj.setInt(465,liSoc20);
				pstmtObj.setFloat(466,liSoh20);
				pstmtObj.setFloat(467,liBattTemp20);
				pstmtObj.setString(468,liBattStatus20);
				pstmtObj.setDate(469, hpDate);

				pstmtObj.executeUpdate();
				logger.info("Data Inserted into trans_recordsdata for site: "+siteCode);

				pstmtObj = connObj.prepareStatement("insert into trans_rawdata(smSiteID,rcdTimestamp,"
						+ "Batt_3_temp,Batt_4_temp,Batt_1_temp,Batt_2_temp,Fuellevel_Percentage,Fuellevel_Ltrs,Sitebattvolt,"
						+ "MDG_R_Volt,MDG_Y_Volt,MDG_B_Volt,MDG_R_Amp,MDG_Y_Amp,MDG_B_Amp,MDG_R_PowerFactor,MDG_Y_PowerFactor,"
						+ "MDG_B_PowerFactor,MDG_R_Power,MDG_Y_Power,MDG_B_Power,MDG_Frequency,DG_R_Volt,DG_Y_Volt,DG_B_Volt,"
						+ "DG_R_Amp,DG_Y_Amp,DG_B_Amp,DG_R_PowerFactor,DG_Y_PowerFactor,DG_B_PowerFactor,DG_R_Power,DG_Y_Power,"
						+ "DG_B_Power,DG_Frequency,DG_Bat_Volt,DG_Tank_Capacity,Batt_Charging_Current,Batt_Discharging_Current,"
						+ "Batt_SOC,Batt_Cycle_Count,Batt_Capacity_AH,Batt_NextFullChargeTime,No_of_Batt_Strings,MDG_RunHrs,"
						+ "DG_RunHrs,Batt_RunHrs,MDG_Energy,DG_Energy,Batt_DisEnergy,Batt_ChargingEnergy,"
						+ "Rect_Input_R_Voltage,Rect_Input_Y_Voltage,Rect_Input_B_Voltage,Rect_Input_R_Current,"
						+ "Rect_Input_Y_Current,Rect_Input_B_Current,Rect_Output_Voltage,No_of_Rectfrs,"
						+ "Total_Rect_Output_Current,Total_DCLoad_Current,Total_DCLoad_Energy,"
						+ "Opco_1_LoadCurrent,Opco_2_LoadCurrent,Opco_3_LoadCurrent,Opco_4_LoadCurrent,"
						+ "Opco_1_Energy,Opco_2_Energy,Opco_3_Energy,Opco_4_Energy,InputVoltage_SlrModule1,"
						+ "InputCurrent_SlrModule1,InputPower_SlrModule1,OutputVoltage_SlrModule1,"
						+ "OutputCurrent_SlrModule1,OutputPower_SlrModule1,SolarModule1Status,"
						+ "InputVoltage_SlrModule2,InputCurrent_SlrModule2,InputPower_SlrModule2,OutputVoltage_SlrModule2,"
						+ "OutputCurrent_SlrModule2,OutputPower_SlrModule2,SolarModule2Status,InputVoltage_SlrModule3,"
						+ "InputCurrent_SlrModule3,InputPower_SlrModule3,OutputVoltage_SlrModule3,OutputCurrent_SlrModule3,"
						+ "OutputPower_SlrModule3,SolarModule3Status,InputVoltage_SlrModule4,InputCurrent_SlrModule4,"
						+ "InputPower_SlrModule4,OutputVoltage_SlrModule4,OutputCurrent_SlrModule4,OutputPower_SlrModule4,"
						+ "SolarModule4Status,InputVoltage_SlrModule5,InputCurrent_SlrModule5,InputPower_SlrModule5,"
						+ "OutputVoltage_SlrModule5,OutputCurrent_SlrModule5,OutputPower_SlrModule5,SolarModule5Status,"
						+ "InputVoltage_SlrModule6,InputCurrent_SlrModule6,InputPower_SlrModule6,OutputVoltage_SlrModule6,"
						+ "OutputCurrent_SlrModule6,OutputPower_SlrModule6,SolarModule6Status,InputVoltage_SlrModule7,"
						+ "InputCurrent_SlrModule7,InputPower_SlrModule7,OutputVoltage_SlrModule7,OutputCurrent_SlrModule7,"
						+ "OutputPower_SlrModule7,SolarModule7Status,InputVoltage_SlrModule8,InputCurrent_SlrModule8,"
						+ "InputPower_SlrModule8,OutputVoltage_SlrModule8,OutputCurrent_SlrModule8,OutputPower_SlrModule8,"
						+ "SolarModule8Status,InputVoltage_SlrModule9,InputCurrent_SlrModule9,InputPower_SlrModule9,"
						+ "OutputVoltage_SlrModule9,OutputCurrent_SlrModule9,OutputPower_SlrModule9,SolarModule9Status,"
						+ "InputVoltage_SlrModule10,InputCurrent_SlrModule10,InputPower_SlrModule10,OutputVoltage_SlrModule10,"
						+ "OutputCurrent_SlrModule10,OutputPower_SlrModule10,SolarModule10Status,InputVoltage_SlrModule11,"
						+ "InputCurrent_SlrModule11,InputPower_SlrModule11,OutputVoltage_SlrModule11,OutputCurrent_SlrModule11,"
						+ "OutputPower_SlrModule11,SolarModule11Status,InputVoltage_SlrModule12,InputCurrent_SlrModule12,"
						+ "InputPower_SlrModule12,OutputVoltage_SlrModule12,OutputCurrent_SlrModule12,OutputPower_SlrModule12,"
						+ "SolarModule12Status,No_of_Solar_Module,Solar_Input_Energy,Solar_Output_Energy,SolarRunHrs,"
						+ "SolarMDGRunHrs,SolarDGRunHrs,SolarBattRunHrs,PowerSource,SolarTotalnputPower,SolarTotaOutputPower,"
						+ "SysOutLoadStatus,MDGRunHrs,TeleRunHrs,TeleEnergy,CommunityLoad_R_Voltage,CommunityLoad_Y_Voltage,CommunityLoad_B_Voltage,"
						+ "CommunityLoad_R_Current,CommunityLoad_Y_Current,CommunityLoad_B_Current,CommunityLoad_R_PowerFactor,CommunityLoad_Y_PowerFactor,CommunityLoad_B_PowerFactor,"
						+ "CommunityLoad_R_Power,CommunityLoad_Y_Power,CommunityLoad_B_Power,CommunityLoad_Frequency,CommunityLoad_RunHrs,CommunityLoad_Energy,"
						+ "InverterInputVoltage_1,InverterInputCurrent_1,InverterOutVoltage_1,InverterOutputCurrent_1,"
						+ "InverterOutputEnergy_1,InverterTemp_1,Ineverter_Status_1,InverterInputVoltage_2,"
						+ "InverterInputCurrent_2,InverterOutVoltage_2,InverterOutputCurrent_2,"
						+ "InverterOutputEnergy_2,InverterTemp_2,Ineverter_Status_2,InverterInputVoltage_3,"
						+ "InverterInputCurrent_3,InverterOutVoltage_3,InverterOutputCurrent_3,"
						+ "InverterOutputEnergy_3,InverterTemp_3,Ineverter_Status_3,InverterInputVoltage_4,"
						+ "InverterInputCurrent_4,InverterOutVoltage_4,InverterOutputCurrent_4,"
						+ "InverterOutputEnergy_4,InverterTemp_4,Ineverter_Status_4,InverterInputVoltage_5,"
						+ "InverterInputCurrent_5,InverterOutVoltage_5,InverterOutputCurrent_5,"
						+ "InverterOutputEnergy_5,InverterTemp_5,Ineverter_Status_5,InverterInputVoltage_6,"
						+ "InverterInputCurrent_6,InverterOutVoltage_6,InverterOutputCurrent_6,InverterOutputEnergy_6,"
						+ "InverterTemp_6,Ineverter_Status_6,InverterInputVoltage_7,InverterInputCurrent_7,"
						+ "InverterOutVoltage_7,InverterOutputCurrent_7,InverterOutputEnergy_7,InverterTemp_7,"
						+ "Ineverter_Status_7,InverterInputVoltage_8,InverterInputCurrent_8,InverterOutVoltage_8,"
						+ "InverterOutputCurrent_8,InverterOutputEnergy_8,InverterTemp_8,Ineverter_Status_8,"
						+ "InverterInputVoltage_9,InverterInputCurrent_9,InverterOutVoltage_9,InverterOutputCurrent_9,"
						+ "InverterOutputEnergy_9,InverterTemp_9,Ineverter_Status_9,InverterInputVoltage_10,"
						+ "InverterInputCurrent_10,InverterOutVoltage_10,InverterOutputCurrent_10,"
						+ "InverterOutputEnergy_10,InverterTemp_10,Ineverter_Status_10,"
						+ "InverterInputVoltage_11,InverterInputCurrent_11,InverterOutVoltage_11,"
						+ "InverterOutputCurrent_11,InverterOutputEnergy_11,InverterTemp_11,"
						+ "Ineverter_Status_11,InverterInputVoltage_12,InverterInputCurrent_12,"
						+ "InverterOutVoltage_12,InverterOutputCurrent_12,InverterOutputEnergy_12,"
						+ "InverterTemp_12,Ineverter_Status_12,No_Of_Inverter_Module,InverterRunHrs,"
						+ "InverterEnergy,InverterInputPower,InverterOutputPower,ACTEL_R_Voltage,"
						+ "ACTEL_Y_Voltage,ACTEL_B_Voltage,ACTEL_R_Current,ACTEL_Y_Current,"
						+ "ACTEL_B_Current,ACTEL_R_PowerFactor,ACTEL_Y_PowerFactor,ACTEL_B_PowerFactor,"
						+ "ACTEL_R_Power,ACTEL_Y_Power,ACTEL_B_Power,ACTEL_Frequency,InputVoltage_SlrModule13,"
						+ "InputCurrent_SlrModule13,OutputVoltage_SlrModule13,OutputCurrent_SlrModule13,SolarModule13Status,"
						+ "InputVoltage_SlrModule14,InputCurrent_SlrModule14,OutputVoltage_SlrModule14,"
						+ "OutputCurrent_SlrModule14,SolarModule14Status,InputVoltage_SlrModule15,InputCurrent_SlrModule15,OutputVoltage_SlrModule15,OutputCurrent_SlrModule15,SolarModule15Status,InputVoltage_SlrModule16,InputCurrent_SlrModule16,OutputVoltage_SlrModule16,OutputCurrent_SlrModule16,SolarModule16Status,InputVoltage_SlrModule17,InputCurrent_SlrModule17,OutputVoltage_SlrModule17,OutputCurrent_SlrModule17,SolarModule17Status,InputVoltage_SlrModule18,InputCurrent_SlrModule18,OutputVoltage_SlrModule18,OutputCurrent_SlrModule18,SolarModule18Status,InputVoltage_SlrModule19,InputCurrent_SlrModule19,OutputVoltage_SlrModule19,OutputCurrent_SlrModule19,SolarModule19Status,InputVoltage_SlrModule20,InputCurrent_SlrModule20,OutputVoltage_SlrModule20,OutputCurrent_SlrModule20,SolarModule20Status,InputVoltage_SlrModule21,InputCurrent_SlrModule21,OutputVoltage_SlrModule21,OutputCurrent_SlrModule21,SolarModule21Status,InputVoltage_SlrModule22,InputCurrent_SlrModule22,OutputVoltage_SlrModule22,OutputCurrent_SlrModule22,SolarModule22Status,InputVoltage_SlrModule23,InputCurrent_SlrModule23,OutputVoltage_SlrModule23,OutputCurrent_SlrModule23,SolarModule23Status,InputVoltage_SlrModule24,InputCurrent_SlrModule24,OutputVoltage_SlrModule24,OutputCurrent_SlrModule24,SolarModule24Status,Alarmstring,StatusString,CommunicationString"
						+ ",SysOutputLoadStatusstring,smSiteCode,DBCreationTimestamp,smSitetypeid,Li_BattVoltage_1 ,Li_BattCurrent_1 ,Li_SOC_1 ,Li_SOH_1 ,Li_BattTemp_1 ,Li_BattStatus_1 ,Li_BattVoltage_2 ,Li_BattCurrent_2 ,Li_SOC_2 ,Li_SOH_2 ,Li_BattTemp_2 ,Li_BattStatus_2 ,Li_BattVoltage_3 ,Li_BattCurrent_3 ,Li_SOC_3 ,Li_SOH_3 ,Li_BattTemp_3 ,Li_BattStatus_3 ,Li_BattVoltage_4 ,Li_BattCurrent_4 ,Li_SOC_4 ,Li_SOH_4 ,Li_BattTemp_4 ,Li_BattStatus_4 ,Li_BattVoltage_5 ,Li_BattCurrent_5 ,Li_SOC_5 ,Li_SOH_5 ,Li_BattTemp_5 ,Li_BattStatus_5 ,Li_BattVoltage_6 ,Li_BattCurrent_6 ,Li_SOC_6,Li_SOH_6,Li_BattTemp_6 ,Li_BattStatus_6,Li_BattVoltage_7 ,Li_BattCurrent_7 ,Li_SOC_7,Li_SOH_7 ,Li_BattTemp_7 ,Li_BattStatus_7,Li_BattVoltage_8 ,Li_BattCurrent_8 ,Li_SOC_8 ,Li_SOH_8 ,Li_BattTemp_8 ,Li_BattStatus_8,Li_BattVoltage_9 ,Li_BattCurrent_9 ,Li_SOC_9,Li_SOH_9,Li_BattTemp_9 ,Li_BattStatus_9,Li_BattVoltage_10 ,Li_BattCurrent_10 ,Li_SOC_10,Li_SOH_10,Li_BattTemp_10 ,Li_BattStatus_10,Li_BattVoltage_11 ,Li_BattCurrent_11 ,Li_SOC_11,Li_SOH_11 ,Li_BattTemp_11 ,Li_BattStatus_11,Li_BattVoltage_12 ,Li_BattCurrent_12 ,Li_SOC_12,Li_SOH_12,Li_BattTemp_12 ,Li_BattStatus_12,Li_BattVoltage_13 ,Li_BattCurrent_13 ,Li_SOC_13 ,Li_SOH_13 ,Li_BattTemp_13 ,Li_BattStatus_13 ,Li_BattVoltage_14 ,Li_BattCurrent_14 ,Li_SOC_14,Li_SOH_14 ,Li_BattTemp_14 ,Li_BattStatus_14,Li_BattVoltage_15 ,Li_BattCurrent_15 ,Li_SOC_15,Li_SOH_15,Li_BattTemp_15 ,Li_BattStatus_15,Li_BattVoltage_16 ,Li_BattCurrent_16 ,Li_SOC_16,Li_SOH_16,Li_BattTemp_16 ,Li_BattStatus_16,Li_BattVoltage_17 ,Li_BattCurrent_17 ,Li_SOC_17,Li_SOH_17 ,Li_BattTemp_17 ,Li_BattStatus_17 ,Li_BattVoltage_18 ,Li_BattCurrent_18 ,Li_SOC_18 ,Li_SOH_18 ,Li_BattTemp_18 ,Li_BattStatus_18 ,Li_BattVoltage_19 ,Li_BattCurrent_19 ,Li_SOC_19,Li_SOH_19,Li_BattTemp_19 ,Li_BattStatus_19 ,Li_BattVoltage_20 ,Li_BattCurrent_20 ,Li_SOC_20,Li_SOH_20,Li_BattTemp_20 ,Li_BattStatus_20,hpDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


				pstmtObj.setInt(1, smSiteId);
				pstmtObj.setLong(2, _recordtime);
				pstmtObj.setFloat(3,battery_Temp3/10);
				pstmtObj.setFloat(4,battery_Temp4/10);
				pstmtObj.setFloat(5,battery_Temp1/10);
				pstmtObj.setFloat(6,battery_Temp2/10);
				pstmtObj.setInt(7,fuelLevel);
				pstmtObj.setInt(8,fuelLevelltr);
				pstmtObj.setFloat(9,siteBattVolt/10);
				pstmtObj.setFloat(10,mdgRVolt);
				pstmtObj.setFloat(11,mdgYVolt);
				pstmtObj.setFloat(12,mdgBVolt);
				pstmtObj.setFloat(13,mdgRCurrent/10);
				pstmtObj.setFloat(14,mdgYCurrent/10);
				pstmtObj.setFloat(15,mdgBCurrent/10);
				pstmtObj.setFloat(16,mdgRPf/100);
				pstmtObj.setFloat(17,mdgyPF/100);
				pstmtObj.setFloat(18,mdgBPf/100);
				pstmtObj.setFloat(19,mdgRPower/10);
				pstmtObj.setFloat(20,mdgyPower/10);
				pstmtObj.setFloat(21,mdgBPower/10);
				pstmtObj.setFloat(22,mdgFrequency/10);
				pstmtObj.setFloat(23,dgRVoltage);
				pstmtObj.setFloat(24,dgYVoltage);
				pstmtObj.setFloat(25,dgBVoltage);
				pstmtObj.setFloat(26,dgRCurrent/10);
				pstmtObj.setFloat(27,dgYCurrent/10);
				pstmtObj.setFloat(28,dgBCurrent/10);
				pstmtObj.setFloat(29,dgRPf/100);
				pstmtObj.setFloat(30,dgYPf/100);
				pstmtObj.setFloat(31,dgBPf/100);
				pstmtObj.setFloat(32,dgRPower/10);
				pstmtObj.setFloat(33,dgYPower/10);
				pstmtObj.setFloat(34,dgBPower/10);
				pstmtObj.setFloat(35,dgFrequency/10);
				pstmtObj.setFloat(36,dgBattVolt/10);
				pstmtObj.setFloat(37,dgTankCapacity);
				pstmtObj.setFloat(38,batteryChargeCurrent/10);
				pstmtObj.setFloat(39,batteryDisChargeCurrent/10);
				pstmtObj.setFloat(40,batterySOC);
				pstmtObj.setFloat(41,batteryCycleCount);
				pstmtObj.setFloat(42,batteryCapacityinAh);
				pstmtObj.setFloat(43,batteryNextFullChgItem/10);
				pstmtObj.setInt(44,noOfBatteryString);
				pstmtObj.setFloat(45,mdgRunningHr/10);
				pstmtObj.setFloat(46,dgRunningHr/10);
				pstmtObj.setFloat(47,battRunningHr/10);
				pstmtObj.setFloat(48,mdgEnergy/10);
				pstmtObj.setFloat(49,dgEnergy/10);
				pstmtObj.setFloat(50,battDisEnergy/10);
				pstmtObj.setFloat(51,battChgEnergy/10);
				pstmtObj.setInt(52,rectInRVol);
				pstmtObj.setInt(53,rectInYVol);
				pstmtObj.setInt(54,rectInBVol);
				pstmtObj.setFloat(55,rectInRCurr/10);
				pstmtObj.setFloat(56,rectInYCurr/10);
				pstmtObj.setFloat(57,rectInBCurr/10);
				pstmtObj.setFloat(58,rectOutV/10);
				pstmtObj.setInt(59,noOfRec);
				pstmtObj.setFloat(60,totRecOpCurr/10);
				pstmtObj.setFloat(61,totDcLoadCurr/10);
				pstmtObj.setFloat(62,totDcLoadEnrgy/10);
				pstmtObj.setFloat(63,opc1LoadCurrent/10);
				pstmtObj.setFloat(64,opc2LoadCurrent/10);
				pstmtObj.setFloat(65,opc3LoadCurrent/10);
				pstmtObj.setFloat(66,opc4LoadCurrent/10);
				pstmtObj.setFloat(67,opc1Energy/10);
				pstmtObj.setFloat(68,opc2Energy/10);
				pstmtObj.setFloat(69,opc3Energy/10);
				pstmtObj.setFloat(70,opc4Energy/10);
				pstmtObj.setFloat(71,pvIpVoltMpod1/10);
				pstmtObj.setFloat(72,pvIpCurrMpod1/10);
				pstmtObj.setFloat(73,pvIpPowMpod1/10);
				pstmtObj.setFloat(74,pvOpVoltMpod1/10);
				pstmtObj.setFloat(75,pvOpCurrMpod1/10);
				pstmtObj.setFloat(76,pvOpPowMpod1/10);
				pstmtObj.setString(77,solarStatusMod1);
				pstmtObj.setFloat(78,pvIpVoltMod2/10);
				pstmtObj.setFloat(79,pvIpCurMod2/10);
				pstmtObj.setFloat(80,pvIpPowMod2/10);
				pstmtObj.setFloat(81,pvOpVoltMod2/10);
				pstmtObj.setFloat(82,pvOpCurrMod2/10);
				pstmtObj.setFloat(83,pvOpPowMod2/10);
				pstmtObj.setString(84,solarStatusMod2);
				pstmtObj.setFloat(85,pvIpVoltMpod3/10);
				pstmtObj.setFloat(86,pvIpCurrMpod3/10);
				pstmtObj.setFloat(87,pvIpPowMpod3/10);
				pstmtObj.setFloat(88,pvOpVoltMpod3/10);
				pstmtObj.setFloat(89,pvOpCurrMpod3/10);
				pstmtObj.setFloat(90,pvOpPowMpod3/10);
				pstmtObj.setString(91,solarStatusMod3);
				pstmtObj.setFloat(92,pvIpVoltMpod4/10);
				pstmtObj.setFloat(93,pvIpCurrMpod4/10);
				pstmtObj.setFloat(94,pvIpPowMpod4/10);
				pstmtObj.setFloat(95,pvOpVoltMpod4/10);
				pstmtObj.setFloat(96,pvOpCurrMpod4/10);
				pstmtObj.setFloat(97,pvOpPowMpod4/10);
				pstmtObj.setString(98,solarStatusMod4);
				pstmtObj.setFloat(99,pvIpVoltMpod5/10);
				pstmtObj.setFloat(100,pvIpCurrMpod5/10);
				pstmtObj.setFloat(101,pvIpPowMpod5/10);
				pstmtObj.setFloat(102,pvOpVoltMpod5/10);
				pstmtObj.setFloat(103,pvOpCurrMpod5/10);
				pstmtObj.setFloat(104,pvOpPowMpod5/10);
				pstmtObj.setString(105,solarStatusMod5);
				pstmtObj.setFloat(106,pvIpVoltMpod6/10);
				pstmtObj.setFloat(107,pvIpCurrMpod6/10);
				pstmtObj.setFloat(108,pvIpPowMpod6/10);
				pstmtObj.setFloat(109,pvOpVoltMpod6/10);
				pstmtObj.setFloat(110,pvOpCurrMpod6/10);
				pstmtObj.setFloat(111,pvOpPowMpod6/10);
				pstmtObj.setString(112,solarStatusMod6);
				pstmtObj.setFloat(113,pvIpVoltMpod7/10);
				pstmtObj.setFloat(114,pvIpCurrMpod7/10);
				pstmtObj.setFloat(115,pvIpPowMpod7/10);
				pstmtObj.setFloat(116,pvOpVoltMpod7/10);
				pstmtObj.setFloat(117,pvOpCurrMpod7/10);
				pstmtObj.setFloat(118,pvOpPowMpod7/10);
				pstmtObj.setString(119,solarStatusMod7);
				pstmtObj.setFloat(120,pvIpVoltMpod8/10);
				pstmtObj.setFloat(121,pvIpCurrMpod8/10);
				pstmtObj.setFloat(122,pvIpPowMpod8/10);
				pstmtObj.setFloat(123,pvOpVoltMpod8/10);
				pstmtObj.setFloat(124,pvOpCurrMpod8/10);
				pstmtObj.setFloat(125,pvOpPowMpod8/10);
				pstmtObj.setString(126,solarStatusMod8);
				pstmtObj.setFloat(127,pvIpVoltMpod9/10);
				pstmtObj.setFloat(128,pvIpCurrMpod9/10);
				pstmtObj.setFloat(129,pvIpPowMpod9/10);
				pstmtObj.setFloat(130,pvOpVoltMpod9/10);
				pstmtObj.setFloat(131,pvOpCurrMpod9/10);
				pstmtObj.setFloat(132,pvOpPowMpod9/10);
				pstmtObj.setString(133,solarStatusMod9);
				pstmtObj.setFloat(134,pvIpVoltMpod10/10);
				pstmtObj.setFloat(135,pvIpCurrMpod10/10);
				pstmtObj.setFloat(136,pvIpPowMpod10/10);
				pstmtObj.setFloat(137,pvOpVoltMpod10/10);
				pstmtObj.setFloat(138,pvOpCurrMpod10/10);
				pstmtObj.setFloat(139,pvOpPowMpod10/10);
				pstmtObj.setString(140,solarStatusMod10);
				pstmtObj.setFloat(141,pvIpVoltMpod11/10);
				pstmtObj.setFloat(142,pvIpCurrMpod11/10);
				pstmtObj.setFloat(143,pvIpPowMpod11/10);
				pstmtObj.setFloat(144,pvOpVoltMpod11/10);
				pstmtObj.setFloat(145,pvOpCurrMpod11/10);
				pstmtObj.setFloat(146,pvOpPowMpod11/10);
				pstmtObj.setString(147,solarStatusMod11);
				pstmtObj.setFloat(148,pvIpVoltMpod12/10);
				pstmtObj.setFloat(149,pvIpCurrMpod12/10);
				pstmtObj.setFloat(150,pvIpPowMpod12/10);
				pstmtObj.setFloat(151,pvOpVoltMpod12/10);
				pstmtObj.setFloat(152,pvOpCurrMpod12/10);
				pstmtObj.setFloat(153,pvOpPowMpod12/10);
				pstmtObj.setString(154,solarStatusMod12);
				pstmtObj.setInt(155,noOfSolarModule);
				pstmtObj.setDouble(156,solarIpEnergy/10);
				pstmtObj.setDouble(157,solarOpEnergy/10);
				pstmtObj.setDouble(158,solarRunHour/10);
				pstmtObj.setDouble(159,solarMdgRunHour/10);
				pstmtObj.setDouble(160,solardgRunHour/10);
				pstmtObj.setDouble(161,solarBattRunHour/10);
				pstmtObj.setString(162,powerSource);
				pstmtObj.setFloat(163,solarTotIpPower/100);
				pstmtObj.setFloat(164,solarTotOpPower/100);
				pstmtObj.setString(165,sysOutLoadStatus);
				pstmtObj.setDouble(166,mobileDGRunHr/10);
				pstmtObj.setDouble(167,teleRunHr/10);
				pstmtObj.setDouble(168,teleEnergy/10);
				pstmtObj.setFloat(169,acLoadRVolt);
				pstmtObj.setFloat(170,acLoadYVolt);
				pstmtObj.setFloat(171,acLoadBVolt);
				pstmtObj.setFloat(172,acLoadRCurrent/10);
				pstmtObj.setFloat(173,acLoadYCurrent/10);
				pstmtObj.setFloat(174,acLoadBCurrent/10);
				pstmtObj.setFloat(175,acLoadRPF/100);
				pstmtObj.setFloat(176,acLoadYPF/100);
				pstmtObj.setFloat(177,acLoadBPF/100);
				pstmtObj.setFloat(178,acLoadRPower/10);
				pstmtObj.setFloat(179,acLoadYPower/10);
				pstmtObj.setFloat(180,acLoadBPower/10);
				pstmtObj.setFloat(181,acLoadFreq/10);
				pstmtObj.setDouble(182,acLoadRunHr/10);
				pstmtObj.setDouble(183,acLoadEnergy/10);
				pstmtObj.setFloat(184,invDcInVolt1/10);
				pstmtObj.setFloat(185,invDcInCurrent1/10);
				pstmtObj.setFloat(186,invAcOpVolt1);
				pstmtObj.setFloat(187,invAcOpCurrent1/10);
				pstmtObj.setFloat(188,invAcOpPower1/10);
				pstmtObj.setFloat(189,invTemp1/10);
				pstmtObj.setString(190,invStatus1);
				pstmtObj.setFloat(191,invDcInVolt2/10);
				pstmtObj.setFloat(192,invDcInCurrent2/10);
				pstmtObj.setFloat(193,invAcOpVolt2);
				pstmtObj.setFloat(194,invAcOpCurrent2/10);
				pstmtObj.setFloat(195,invAcOpPower2/10);
				pstmtObj.setFloat(196,invTemp2/10);
				pstmtObj.setString(197,invStatus2);
				pstmtObj.setFloat(198,invDcInVolt3/10);
				pstmtObj.setFloat(199,invDcInCurrent3/10);
				pstmtObj.setFloat(200,invAcOpVolt3);
				pstmtObj.setFloat(201,invAcOpCurrent3/10);
				pstmtObj.setFloat(202,invAcOpPower3/10);
				pstmtObj.setFloat(203,invTemp3/10);
				pstmtObj.setString(204,invStatus3);
				pstmtObj.setFloat(205,invDcInVolt4/10);
				pstmtObj.setFloat(206,invDcInCurrent4/10);
				pstmtObj.setFloat(207,invAcOpVolt4);
				pstmtObj.setFloat(208,invAcOpCurrent4/10);
				pstmtObj.setFloat(209,invAcOpPower4/10);
				pstmtObj.setFloat(210,invTemp4/10);
				pstmtObj.setString(211,invStatus4);
				pstmtObj.setFloat(212,invDcInVolt5/10);
				pstmtObj.setFloat(213,invDcInCurrent5/10);
				pstmtObj.setFloat(214,invAcOpVolt5);
				pstmtObj.setFloat(215,invAcOpCurrent5/10);
				pstmtObj.setFloat(216,invAcOpPower5/10);
				pstmtObj.setFloat(217,invTemp5/10);
				pstmtObj.setString(218,invStatus5);
				pstmtObj.setFloat(219,invDcInVolt6/10);
				pstmtObj.setFloat(220,invDcInCurrent6/10);
				pstmtObj.setFloat(221,invAcOpVolt6);
				pstmtObj.setFloat(222,invAcOpCurrent6/10);
				pstmtObj.setFloat(223,invAcOpPower6/10);
				pstmtObj.setFloat(224,invTemp6/10);
				pstmtObj.setString(225,invStatus6);
				pstmtObj.setFloat(226,invDcInVolt7/10);
				pstmtObj.setFloat(227,invDcInCurrent7/10);
				pstmtObj.setFloat(228,invAcOpVolt7);
				pstmtObj.setFloat(229,invAcOpCurrent7/10);
				pstmtObj.setFloat(230,invAcOpPower7/10);
				pstmtObj.setFloat(231,invTemp7/10);
				pstmtObj.setString(232,invStatus7);
				pstmtObj.setFloat(233,invDcInVolt8/10);
				pstmtObj.setFloat(234,invDcInCurrent8/10);
				pstmtObj.setFloat(235,invAcOpVolt8);
				pstmtObj.setFloat(236,invAcOpCurrent8/10);
				pstmtObj.setFloat(237,invAcOpPower8/10);
				pstmtObj.setFloat(238,invTemp8/10);
				pstmtObj.setString(239,invStatus8);
				pstmtObj.setFloat(240,invDcInVolt9/10);
				pstmtObj.setFloat(241,invDcInCurrent9/10);
				pstmtObj.setFloat(242,invAcOpVolt9);
				pstmtObj.setFloat(243,invAcOpCurrent9/10);
				pstmtObj.setFloat(244,invAcOpPower9/10);
				pstmtObj.setFloat(245,invTemp9/10);
				pstmtObj.setString(246,invStatus9);
				pstmtObj.setFloat(247,invDcInVolt10/10);
				pstmtObj.setFloat(248,invDcInCurrent10/10);
				pstmtObj.setFloat(249,invAcOpVolt10);
				pstmtObj.setFloat(250,invAcOpCurrent10/10);
				pstmtObj.setFloat(251,invAcOpPower10/10);
				pstmtObj.setFloat(252,invTemp10/10);
				pstmtObj.setString(253,invStatus10);
				pstmtObj.setFloat(254,invDcInVolt11/10);
				pstmtObj.setFloat(255,invDcInCurrent11/10);
				pstmtObj.setFloat(256,invAcOpVolt11);
				pstmtObj.setFloat(257,invAcOpCurrent11/10);
				pstmtObj.setFloat(258,invAcOpPower11/10);
				pstmtObj.setFloat(259,invTemp11/10);
				pstmtObj.setString(260,invStatus11);
				pstmtObj.setFloat(261,invDcInCurrent12/10);
				pstmtObj.setFloat(262,invDcInVolt12/10);
				pstmtObj.setFloat(263,inAcOpVoltage12);
				pstmtObj.setFloat(264,inAcOpCurrent12/10);
				pstmtObj.setFloat(265,inAcOpPower12/10);
				pstmtObj.setFloat(266,invTemp12/10);
				pstmtObj.setString(267,invStatus12);
				pstmtObj.setInt(268,noOfInvModule);
				pstmtObj.setDouble(269,invRunHr/10);
				pstmtObj.setDouble(270,invEnergy/10);
				pstmtObj.setFloat(271,invTotIpPower/100);
				pstmtObj.setFloat(272,invTotOpPower/100);
				pstmtObj.setFloat(273,actelLoadRVolt);
				pstmtObj.setFloat(274,actelLoadYVolt);
				pstmtObj.setFloat(275,actelLoadBVolt);
				pstmtObj.setFloat(276,actelLoadRCurr/10);
				pstmtObj.setFloat(277,actelLoadYCurr/10);
				pstmtObj.setFloat(278,actelLoadBCurr/10);
				pstmtObj.setFloat(279,actelLoadRPF/100);
				pstmtObj.setFloat(280,actelLoadYPF/100);
				pstmtObj.setFloat(281,actelLoadBPF/100);
				pstmtObj.setFloat(282,actelLoadRPower/10);
				pstmtObj.setFloat(283,actelLoadYPower/10);
				pstmtObj.setFloat(284,actelLoadBPower/10);
				pstmtObj.setFloat(285,actelLoadFre/10);
				pstmtObj.setFloat(286,pvIpVoltMod13/10);
				pstmtObj.setFloat(287,pvIpCurrMod13/10);
				pstmtObj.setFloat(288,pvOpVoltMod13/10);
				pstmtObj.setFloat(289,pvOpCurrMod13/10);
				pstmtObj.setString(290,solarStatusMod13);
				pstmtObj.setFloat(291,pvIpVoltMod14/10);
				pstmtObj.setFloat(292,pvIpCurrMod14/10);
				pstmtObj.setFloat(293,pvOpVoltMod14/10);
				pstmtObj.setFloat(294,pvOpCurrMod14/10);
				pstmtObj.setString(295,solarStatusMod14);
				pstmtObj.setFloat(296,pvIpVoltMod15/10);
				pstmtObj.setFloat(297,pvIpCurrMod15/10);
				pstmtObj.setFloat(298,pvOpVoltMod15/10);
				pstmtObj.setFloat(299,pvOpCurrMod15/10);
				pstmtObj.setString(300,solarStatusMod15);
				pstmtObj.setFloat(301,pvIpVoltMod16/10);
				pstmtObj.setFloat(302,pvIpCurrMod16/10);
				pstmtObj.setFloat(303,pvOpVoltMod16/10);
				pstmtObj.setFloat(304,pvOpCurrMod16/10);
				pstmtObj.setString(305,solarStatusMod16);
				pstmtObj.setFloat(306,pvIpVoltMod17/10);
				pstmtObj.setFloat(307,pvIpCurrMod17/10);
				pstmtObj.setFloat(308,pvOpVoltMod17/10);
				pstmtObj.setFloat(309,pvOpCurrMod17/10);
				pstmtObj.setString(310,solarStatusMod17);
				pstmtObj.setFloat(311,pvIpVoltMod18/10);
				pstmtObj.setFloat(312,pvIpCurrMod18/10);
				pstmtObj.setFloat(313,pvOpVoltMod18/10);
				pstmtObj.setFloat(314,pvOpCurrMod18/10);
				pstmtObj.setString(315,solarStatusMod18);
				pstmtObj.setFloat(316,pvIpVoltMod19/10);
				pstmtObj.setFloat(317,pvIpCurrMod19/10);
				pstmtObj.setFloat(318,pvOpVoltMod19/10);
				pstmtObj.setFloat(319,pvOpCurrMod19/10);
				pstmtObj.setString(320,solarStatusMod19);
				pstmtObj.setFloat(321,pvIpVoltMod20/10);
				pstmtObj.setFloat(322,pvIpCurrMod20/10);
				pstmtObj.setFloat(323,pvOpVoltMod20/10);
				pstmtObj.setFloat(324,pvOpCurrMod20/10);
				pstmtObj.setString(325,solarStatusMod20);
				pstmtObj.setFloat(326,pvIpVoltMod21/10);
				pstmtObj.setFloat(327,pvIpCurrMod21/10);
				pstmtObj.setFloat(328,pvOpVoltMod21/10);
				pstmtObj.setFloat(329,pvOpCurrMod21/10);
				pstmtObj.setString(330,solarStatusMod21);
				pstmtObj.setFloat(331,pvIpVoltMod22/10);
				pstmtObj.setFloat(332,pvIpCurrMod22/10);
				pstmtObj.setFloat(333,pvOpVoltMod22/10);
				pstmtObj.setFloat(334,pvOpCurrMod22/10);
				pstmtObj.setString(335,solarStatusMod22);
				pstmtObj.setFloat(336,pvIpVoltMod23/10);
				pstmtObj.setFloat(337,pvIpCurrMod23/10);
				pstmtObj.setFloat(338,pvOpVoltMod23/10);
				pstmtObj.setFloat(339,pvOpCurrMod23/10);
				pstmtObj.setString(340,solarStatusMod23);
				pstmtObj.setFloat(341,pvIpVoltMod24/10);
				pstmtObj.setFloat(342,pvIpCurrMod24/10);
				pstmtObj.setFloat(343,pvOpVoltMod24/10);
				pstmtObj.setFloat(344,pvOpCurrMod24/10);
				pstmtObj.setString(345,solarStatusMod24);
				pstmtObj.setString(346,alarmString);
				pstmtObj.setString(347,statusString);
				pstmtObj.setString(348,commStatus);
				pstmtObj.setString(349,sysOutLoadStatus);
				pstmtObj.setString(350,siteCode);
				pstmtObj.setLong(351,dbCreationTime);
				pstmtObj.setInt(352,smSitetypeid);

				pstmtObj.setFloat(353,liBattVolt1);
				pstmtObj.setFloat(354,liBattCurr1);
				pstmtObj.setInt(355,liSoc1);
				pstmtObj.setFloat(356,liSoh1);
				pstmtObj.setFloat(357,liBattTemp1);
				pstmtObj.setString(358,liBattStatus1);
				/*Li2*/
				pstmtObj.setFloat(359,liBattVolt2);
				pstmtObj.setFloat(360,liBattCurr2);
				pstmtObj.setInt(361,liSoc2);
				pstmtObj.setFloat(362,liSoh2);
				pstmtObj.setFloat(363,liBattTemp2);
				pstmtObj.setString(364,liBattStatus2);
				/*Li3*/
				pstmtObj.setFloat(365,liBattVolt3);
				pstmtObj.setFloat(366,liBattCurr3);
				pstmtObj.setInt(367,liSoc3);
				pstmtObj.setFloat(368,liSoh3);
				pstmtObj.setFloat(369,liBattTemp3);
				pstmtObj.setString(370,liBattStatus3);
				/*Li4*/
				pstmtObj.setFloat(371,liBattVolt4);
				pstmtObj.setFloat(372,liBattCurr4);
				pstmtObj.setInt(373,liSoc4);
				pstmtObj.setFloat(374,liSoh4);
				pstmtObj.setFloat(375,liBattTemp4);
				pstmtObj.setString(376,liBattStatus4);
				/*Li5*/
				pstmtObj.setFloat(377,liBattVolt5);
				pstmtObj.setFloat(378,liBattCurr5);
				pstmtObj.setInt(379,liSoc5);
				pstmtObj.setFloat(380,liSoh5);
				pstmtObj.setFloat(381,liBattTemp5);
				pstmtObj.setString(382,liBattStatus5);
				/*Li6*/
				pstmtObj.setFloat(383,liBattVolt6);
				pstmtObj.setFloat(384,liBattCurr6);
				pstmtObj.setInt(385,liSoc6);
				pstmtObj.setFloat(386,liSoh6);
				pstmtObj.setFloat(387,liBattTemp6);
				pstmtObj.setString(388,liBattStatus6);
				/*Li7*/
				pstmtObj.setFloat(389,liBattVolt7);
				pstmtObj.setFloat(390,liBattCurr7);
				pstmtObj.setInt(391,liSoc7);
				pstmtObj.setFloat(392,liSoh7);
				pstmtObj.setFloat(393,liBattTemp7);
				pstmtObj.setString(394,liBattStatus7);
				/*Li8*/
				pstmtObj.setFloat(395,liBattVolt8);
				pstmtObj.setFloat(396,liBattCurr8);
				pstmtObj.setInt(397,liSoc8);
				pstmtObj.setFloat(398,liSoh8);
				pstmtObj.setFloat(399,liBattTemp8);
				pstmtObj.setString(400,liBattStatus8);
				/*Li9*/
				pstmtObj.setFloat(401,liBattVolt9);
				pstmtObj.setFloat(402,liBattCurr9);
				pstmtObj.setInt(403,liSoc9);
				pstmtObj.setFloat(404,liSoh9);
				pstmtObj.setFloat(405,liBattTemp9);
				pstmtObj.setString(406,liBattStatus9);
				/*Li10*/
				pstmtObj.setFloat(407,liBattVolt10);
				pstmtObj.setFloat(408,liBattCurr10);
				pstmtObj.setInt(409,liSoc10);
				pstmtObj.setFloat(410,liSoh10);
				pstmtObj.setFloat(411,liBattTemp10);
				pstmtObj.setString(412,liBattStatus10);
				/*Li11*/
				pstmtObj.setFloat(413,liBattVolt11);
				pstmtObj.setFloat(414,liBattCurr11);
				pstmtObj.setInt(415,liSoc11);
				pstmtObj.setFloat(416,liSoh11);
				pstmtObj.setFloat(417,liBattTemp11);
				pstmtObj.setString(418,liBattStatus11);
				/*Li12*/
				pstmtObj.setFloat(419,liBattVolt12);
				pstmtObj.setFloat(420,liBattCurr12);
				pstmtObj.setInt(421,liSoc12);
				pstmtObj.setFloat(422,liSoh12);
				pstmtObj.setFloat(423,liBattTemp12);
				pstmtObj.setString(424,liBattStatus12);
				/*Li13*/
				pstmtObj.setFloat(425,liBattVolt13);
				pstmtObj.setFloat(426,liBattCurr13);
				pstmtObj.setInt(427,liSoc13);
				pstmtObj.setFloat(428,liSoh13);
				pstmtObj.setFloat(429,liBattTemp13);
				pstmtObj.setString(430,liBattStatus13);
				/*Li14*/
				pstmtObj.setFloat(431,liBattVolt14);
				pstmtObj.setFloat(432,liBattCurr14);
				pstmtObj.setInt(433,liSoc14);
				pstmtObj.setFloat(434,liSoh14);
				pstmtObj.setFloat(435,liBattTemp14);
				pstmtObj.setString(436,liBattStatus14);
				/*Li15*/
				pstmtObj.setFloat(437,liBattVolt15);
				pstmtObj.setFloat(438,liBattCurr15);
				pstmtObj.setInt(439,liSoc15);
				pstmtObj.setFloat(440,liSoh15);
				pstmtObj.setFloat(441,liBattTemp15);
				pstmtObj.setString(442,liBattStatus15);
				/*Li16*/
				pstmtObj.setFloat(443,liBattVolt16);
				pstmtObj.setFloat(444,liBattCurr16);
				pstmtObj.setInt(445,liSoc16);
				pstmtObj.setFloat(446,liSoh16);
				pstmtObj.setFloat(447,liBattTemp16);
				pstmtObj.setString(448,liBattStatus16);
				/*Li17*/
				pstmtObj.setFloat(449,liBattVolt17);
				pstmtObj.setFloat(450,liBattCurr17);
				pstmtObj.setInt(451,liSoc17);
				pstmtObj.setFloat(452,liSoh17);
				pstmtObj.setFloat(453,liBattTemp17);
				pstmtObj.setString(454,liBattStatus17);
				/*Li18*/
				pstmtObj.setFloat(455,liBattVolt18);
				pstmtObj.setFloat(456,liBattCurr18);
				pstmtObj.setInt(457,liSoc18);
				pstmtObj.setFloat(458,liSoh18);
				pstmtObj.setFloat(459,liBattTemp18);
				pstmtObj.setString(460,liBattStatus18);
				/*Li19*/
				pstmtObj.setFloat(461,liBattVolt19);
				pstmtObj.setFloat(462,liBattCurr19);
				pstmtObj.setInt(463,liSoc19);
				pstmtObj.setFloat(464,liSoh19);
				pstmtObj.setFloat(465,liBattTemp19);
				pstmtObj.setString(466,liBattStatus19);
				/*Li20*/
				pstmtObj.setFloat(467,liBattVolt20);
				pstmtObj.setFloat(468,liBattCurr20);
				pstmtObj.setInt(469,liSoc20);
				pstmtObj.setFloat(470,liSoh20);
				pstmtObj.setFloat(471,liBattTemp20);
				pstmtObj.setString(472,liBattStatus20);
				pstmtObj.setDate(473, hpDate);
				pstmtObj.executeUpdate();
				logger.info("Data Inserted into trans_rawdata for site: "+siteCode);
				pstmtObj.close();

			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			} finally {
				try {
					// Closing PreparedStatement Object
					if(pstmtObj != null) {
						pstmtObj.close();
						pstmtObj=null;
					}
					// Closing Connection Object
					if(connObj != null) {
						connObj.close();
						connObj=null;
					}
				} catch(Exception sqlException) {
					logger.error ("Failed to process alarm", sqlException+ " for site: ",siteCode);
					sqlException.printStackTrace();
				}
			}
			jdbcObj.printDbStatus();

			AlarmProcessor alarmProcessing = new AlarmProcessor();
			alarmProcessing.processAlarm(alarmString,smSiteCode,_recordtime,statusString,commStatus,smSiteId,sysOutLoadStatus,
					solarStatusMod1,solarStatusMod2,solarStatusMod3,solarStatusMod4,solarStatusMod5,solarStatusMod6,solarStatusMod7,
					solarStatusMod8,solarStatusMod9,solarStatusMod10,solarStatusMod11,solarStatusMod12,solarStatusMod13,solarStatusMod14,
					solarStatusMod15,solarStatusMod16,solarStatusMod17,solarStatusMod18,solarStatusMod19,solarStatusMod20,solarStatusMod21,
					solarStatusMod22,solarStatusMod23,solarStatusMod24,invStatus1,invStatus2,invStatus3,invStatus4,invStatus5,invStatus6,invStatus7,invStatus8,invStatus9,invStatus10,invStatus11,invStatus12,crClusterID,smSiteName,znZoneID,rgRegionID,znZone,emEmpID,emEmployeeID,
					emFirstName,emContactNo,emEmail,crName,rgRegion,smSitetypeid,liBattStatus1,liBattStatus2,liBattStatus3,liBattStatus4
					,liBattStatus5,liBattStatus6,liBattStatus7,liBattStatus8,liBattStatus9,liBattStatus10,liBattStatus11
					,liBattStatus12,liBattStatus13,liBattStatus14,liBattStatus15,liBattStatus16,liBattStatus17,liBattStatus18
					,liBattStatus19,liBattStatus20,dbCreationTime,hpDate);
		}
		return true;
	}

	public long _parseDate(String year, String month,String day, String hour, String mnt, String second) {

		int YY = Integer.parseInt(year); // 07 year
		int MM = Integer.parseInt(month); // 04 month
		int DD = Integer.parseInt(day); // 18 day
		int hh = Integer.parseInt(hour); // 01 hour
		int mm = Integer.parseInt(mnt); // 48 minute
		int ss = Integer.parseInt(second); // 04 second
		if (YY < 100) { YY += 2000; }
		DateTime dt = new DateTime(gmtTimezone,YY,MM,DD,hh,mm,ss);
		return dt.getTimeSec();
	}

}
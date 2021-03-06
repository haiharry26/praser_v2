package com.digitrinity.parser.util;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlarmUtil {

	private static Logger logger = LogManager.getLogger(AlarmUtil.class.getName());

	public void insertIntoLiTransAlarmHistory(int[] alarm, int smSiteId, int smSitetypeid, String siteId,
			long _recordtime, long dbCreationTime, Date hpDate,Connection connObj) throws Exception {

		PreparedStatement pstmtObj = null;

		/* Insert into trans_alarmhistory */
		//Query2
		try {
			pstmtObj = connObj.prepareStatement("insert into trans_ltmalarmdatahistory(Alarm_409,Alarm_410,Alarm_411,Alarm_412,Alarm_413,Alarm_414,Alarm_415,Alarm_416,Alarm_417,Alarm_418,Alarm_419,Alarm_420,Alarm_421,Alarm_422,Alarm_423,Alarm_424,Alarm_425,Alarm_426,Alarm_427,Alarm_428,Alarm_429,Alarm_430,Alarm_431,Alarm_432,Alarm_433,Alarm_434,Alarm_435,Alarm_436,Alarm_437,Alarm_438,Alarm_439,Alarm_440,Alarm_441,Alarm_442,Alarm_443,Alarm_444,Alarm_445,Alarm_446,Alarm_447,Alarm_448,Alarm_449,Alarm_450,Alarm_451,Alarm_452,Alarm_453,Alarm_454,Alarm_455,Alarm_456,Alarm_457,Alarm_458,Alarm_459,Alarm_460,Alarm_461,Alarm_462,Alarm_463,Alarm_464,Alarm_465,Alarm_466,Alarm_467,Alarm_468,Alarm_469,Alarm_470,Alarm_471,Alarm_472,Alarm_473,Alarm_474,Alarm_475,Alarm_476,Alarm_477,Alarm_478,Alarm_479,Alarm_480,Alarm_481,Alarm_482,Alarm_483,Alarm_484,Alarm_485,Alarm_486,Alarm_487,Alarm_488,Alarm_489,Alarm_490,Alarm_491,Alarm_492,Alarm_493,Alarm_494,Alarm_495,Alarm_496,Alarm_497,Alarm_498,Alarm_499,Alarm_500,Alarm_501,Alarm_502,Alarm_503,Alarm_504,Alarm_505,Alarm_506,Alarm_507,Alarm_508,Alarm_509,Alarm_510,Alarm_511,Alarm_512,Alarm_513,Alarm_514,Alarm_515,Alarm_516,Alarm_517,Alarm_518,Alarm_519,Alarm_520,Alarm_521,Alarm_522,Alarm_523,Alarm_524,Alarm_525,Alarm_526,Alarm_527,Alarm_528,Alarm_529,Alarm_530,Alarm_531,Alarm_532,Alarm_533,Alarm_534,Alarm_535,Alarm_536,Alarm_537,Alarm_538,Alarm_539,Alarm_540,Alarm_541,Alarm_542,Alarm_543,Alarm_544,Alarm_545,Alarm_546,Alarm_547,Alarm_548,Alarm_549,Alarm_550,Alarm_551,Alarm_552,Alarm_553,Alarm_554,Alarm_555,Alarm_556,Alarm_557,Alarm_558,Alarm_559,Alarm_560,Alarm_561,Alarm_562,Alarm_563,Alarm_564,Alarm_565,Alarm_566,Alarm_567,Alarm_568,Alarm_569,Alarm_570,Alarm_571,Alarm_572,Alarm_573,Alarm_574,Alarm_575,Alarm_576,Alarm_577,Alarm_578,Alarm_579,Alarm_580,Alarm_581,Alarm_582,Alarm_583,Alarm_584,Alarm_585,Alarm_586,Alarm_587,Alarm_588,Alarm_589,Alarm_590,Alarm_591,Alarm_592,Alarm_593,Alarm_594,Alarm_595,Alarm_596,Alarm_597,Alarm_598,Alarm_599,Alarm_600,Alarm_601,Alarm_602,Alarm_603,Alarm_604,Alarm_605,Alarm_606,Alarm_607,Alarm_608,Alarm_609,Alarm_610,Alarm_611,Alarm_612,Alarm_613,Alarm_614,Alarm_615,Alarm_616,Alarm_617,Alarm_618,Alarm_619,Alarm_620,Alarm_621,Alarm_622,Alarm_623,Alarm_624,Alarm_625,Alarm_626,Alarm_627,Alarm_628,Alarm_629,Alarm_630,Alarm_631,Alarm_632,Alarm_633,Alarm_634,Alarm_635,Alarm_636,Alarm_637,Alarm_638,Alarm_639,Alarm_640,Alarm_641,Alarm_642,Alarm_643,Alarm_644,Alarm_645,Alarm_646,Alarm_647,Alarm_648,Alarm_649,Alarm_650,Alarm_651,Alarm_652,Alarm_653,Alarm_654,Alarm_655,Alarm_656,Alarm_657,Alarm_658,Alarm_659,Alarm_660,Alarm_661,Alarm_662,Alarm_663,Alarm_664,Alarm_665,Alarm_666,Alarm_667,Alarm_668,Alarm_669,Alarm_670,Alarm_671,Alarm_672,Alarm_673,Alarm_674,Alarm_675,Alarm_676,Alarm_677,Alarm_678,Alarm_679,Alarm_680,Alarm_681,Alarm_682,Alarm_683,Alarm_684,Alarm_685,Alarm_686,Alarm_687,Alarm_688,Alarm_689,Alarm_690,Alarm_691,Alarm_692,Alarm_693,Alarm_694,Alarm_695,Alarm_696,Alarm_697,Alarm_698,Alarm_699,Alarm_700,Alarm_701,Alarm_702,Alarm_703,Alarm_704,Alarm_705,Alarm_706,Alarm_707,Alarm_708,Alarm_709,Alarm_710,Alarm_711,Alarm_712,Alarm_713,Alarm_714,Alarm_715,Alarm_716,Alarm_717,Alarm_718,Alarm_719,Alarm_720,Alarm_721,Alarm_722,Alarm_723,Alarm_724,Alarm_725,Alarm_726,Alarm_727,Alarm_728,Alarm_729,Alarm_730,Alarm_731,Alarm_732,Alarm_733,Alarm_734,Alarm_735,Alarm_736,Alarm_737,Alarm_738,Alarm_739,Alarm_740,Alarm_741,Alarm_742,Alarm_743,Alarm_744,Alarm_745,Alarm_746,Alarm_747,Alarm_748,Alarm_749,Alarm_750,Alarm_751,Alarm_752,Alarm_753,Alarm_754,Alarm_755,Alarm_756,Alarm_757,Alarm_758,Alarm_759,Alarm_760,Alarm_761,Alarm_762,Alarm_763,Alarm_764,Alarm_765,Alarm_766,Alarm_767,Alarm_768,Alarm_769,Alarm_770,Alarm_771,Alarm_772,Alarm_773,Alarm_774,Alarm_775,Alarm_776,Alarm_777,Alarm_778,Alarm_779,Alarm_780,Alarm_781,Alarm_782,Alarm_783,Alarm_784,Alarm_785,Alarm_786,Alarm_787,Alarm_788,Alarm_789,Alarm_790,Alarm_791,Alarm_792,Alarm_793,Alarm_794,Alarm_795,Alarm_796,Alarm_797,Alarm_798,Alarm_799,Alarm_800,Alarm_801,Alarm_802,Alarm_803,Alarm_804,Alarm_805,Alarm_806,Alarm_807,Alarm_808,Alarm_809,Alarm_810,Alarm_811,Alarm_812,Alarm_813,Alarm_814,Alarm_815,Alarm_816,Alarm_817,Alarm_818,Alarm_819,Alarm_820,Alarm_821,Alarm_822,Alarm_823,Alarm_824,Alarm_825,Alarm_826,Alarm_827,Alarm_828,Alarm_829,Alarm_830,Alarm_831,Alarm_832,Alarm_833,Alarm_834,Alarm_835,Alarm_836,Alarm_837,Alarm_838,Alarm_839,Alarm_840,Alarm_841,Alarm_842,Alarm_843,Alarm_844,Alarm_845,Alarm_846,Alarm_847,Alarm_848,Alarm_849,Alarm_850,Alarm_851,Alarm_852,Alarm_853,Alarm_854,Alarm_855,Alarm_856,Alarm_857,Alarm_858,Alarm_859,Alarm_860,Alarm_861,Alarm_862,Alarm_863,Alarm_864,Alarm_865,Alarm_866,Alarm_867,Alarm_868,Alarm_869,Alarm_870,Alarm_871,Alarm_872,Alarm_873,Alarm_874,Alarm_875,Alarm_876,Alarm_877,Alarm_878,Alarm_879,Alarm_880,Alarm_881,Alarm_882,Alarm_883,Alarm_884,Alarm_885,Alarm_886,Alarm_887,Alarm_888,Alarm_889,Alarm_890,Alarm_891,Alarm_892,Alarm_893,Alarm_894,Alarm_895,Alarm_896,Alarm_897,Alarm_898,Alarm_899,Alarm_900,Alarm_901,Alarm_902,Alarm_903,Alarm_904,Alarm_905,Alarm_906,Alarm_907,Alarm_908,Alarm_909,Alarm_910,Alarm_911,Alarm_912,Alarm_913,Alarm_914,Alarm_915,Alarm_916,Alarm_917,Alarm_918,Alarm_919,Alarm_920,Alarm_921,Alarm_922,Alarm_923,Alarm_924,Alarm_925,Alarm_926,Alarm_927,Alarm_928,Alarm_929,Alarm_930,Alarm_931,Alarm_932,Alarm_933,Alarm_934,Alarm_935,Alarm_936,Alarm_937,Alarm_938,Alarm_939,Alarm_940,Alarm_941,Alarm_942,Alarm_943,Alarm_944,Alarm_945,Alarm_946,Alarm_947,Alarm_948,Alarm_949,Alarm_950,Alarm_951,Alarm_952,Alarm_953,Alarm_954,Alarm_955,Alarm_956,Alarm_957,Alarm_958,Alarm_959,Alarm_960,Alarm_961,Alarm_962,Alarm_963,Alarm_964,Alarm_965,Alarm_966,Alarm_967,Alarm_968,Alarm_969,Alarm_970,Alarm_971,Alarm_972,Alarm_973,Alarm_974,Alarm_975,Alarm_976,Alarm_977,Alarm_978,Alarm_979,Alarm_980,Alarm_981,Alarm_982,Alarm_983,Alarm_984,Alarm_985,Alarm_986,Alarm_987,Alarm_988,Alarm_989,Alarm_990,Alarm_991,Alarm_992,Alarm_993,Alarm_994,Alarm_995,Alarm_996,Alarm_997,Alarm_998,Alarm_999,Alarm_1000,Alarm_1001,Alarm_1002,Alarm_1003,Alarm_1004,Alarm_1005,Alarm_1006,Alarm_1007,Alarm_1008,Alarm_1009,Alarm_1010,Alarm_1011,Alarm_1012,Alarm_1013,Alarm_1014,Alarm_1015,Alarm_1016,Alarm_1017,Alarm_1018,Alarm_1019,Alarm_1020,Alarm_1021,Alarm_1022,Alarm_1023,Alarm_1024,Alarm_1025,Alarm_1026,Alarm_1027,Alarm_1028,Alarm_1029,Alarm_1030,Alarm_1031,Alarm_1032,Alarm_1033,Alarm_1034,Alarm_1035,Alarm_1036,Alarm_1037,Alarm_1038,Alarm_1039,Alarm_1040,Alarm_1041,Alarm_1042,Alarm_1043,Alarm_1044,Alarm_1045,Alarm_1046,Alarm_1047,Alarm_1048,smSiteID,smSitetypeid,smSiteCode,lthTimestamp,DBCreationTimestamp,hpDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmtObj.setInt(1, alarm[408]);
			pstmtObj.setInt(2, alarm[409]);
			pstmtObj.setInt(3, alarm[410]);
			pstmtObj.setInt(4, alarm[411]);
			pstmtObj.setInt(5, alarm[412]);
			pstmtObj.setInt(6, alarm[413]);
			pstmtObj.setInt(7, alarm[414]);
			pstmtObj.setInt(8, alarm[415]);
			pstmtObj.setInt(9, alarm[416]);
			pstmtObj.setInt(10, alarm[417]);
			pstmtObj.setInt(11, alarm[418]);
			pstmtObj.setInt(12, alarm[419]);
			pstmtObj.setInt(13, alarm[420]);
			pstmtObj.setInt(14, alarm[421]);
			pstmtObj.setInt(15, alarm[422]);
			pstmtObj.setInt(16, alarm[423]);
			pstmtObj.setInt(17, alarm[424]);
			pstmtObj.setInt(18, alarm[425]);
			pstmtObj.setInt(19, alarm[426]);
			pstmtObj.setInt(20, alarm[427]);
			pstmtObj.setInt(21, alarm[428]);
			pstmtObj.setInt(22, alarm[429]);
			pstmtObj.setInt(23, alarm[430]);
			pstmtObj.setInt(24, alarm[431]);
			pstmtObj.setInt(25, alarm[432]);
			pstmtObj.setInt(26, alarm[433]);
			pstmtObj.setInt(27, alarm[434]);
			pstmtObj.setInt(28, alarm[435]);
			pstmtObj.setInt(29, alarm[436]);
			pstmtObj.setInt(30, alarm[437]);
			pstmtObj.setInt(31, alarm[438]);
			pstmtObj.setInt(32, alarm[439]);
			pstmtObj.setInt(33, alarm[440]);
			pstmtObj.setInt(34, alarm[441]);
			pstmtObj.setInt(35, alarm[442]);
			pstmtObj.setInt(36, alarm[443]);
			pstmtObj.setInt(37, alarm[444]);
			pstmtObj.setInt(38, alarm[445]);
			pstmtObj.setInt(39, alarm[446]);
			pstmtObj.setInt(40, alarm[447]);
			pstmtObj.setInt(41, alarm[448]);
			pstmtObj.setInt(42, alarm[449]);
			pstmtObj.setInt(43, alarm[450]);
			pstmtObj.setInt(44, alarm[451]);
			pstmtObj.setInt(45, alarm[452]);
			pstmtObj.setInt(46, alarm[453]);
			pstmtObj.setInt(47, alarm[454]);
			pstmtObj.setInt(48, alarm[455]);
			pstmtObj.setInt(49, alarm[456]);
			pstmtObj.setInt(50, alarm[457]);
			pstmtObj.setInt(51, alarm[458]);
			pstmtObj.setInt(52, alarm[459]);
			pstmtObj.setInt(53, alarm[460]);
			pstmtObj.setInt(54, alarm[461]);
			pstmtObj.setInt(55, alarm[462]);
			pstmtObj.setInt(56, alarm[463]);
			pstmtObj.setInt(57, alarm[464]);
			pstmtObj.setInt(58, alarm[465]);
			pstmtObj.setInt(59, alarm[466]);
			pstmtObj.setInt(60, alarm[467]);
			pstmtObj.setInt(61, alarm[468]);
			pstmtObj.setInt(62, alarm[469]);
			pstmtObj.setInt(63, alarm[470]);
			pstmtObj.setInt(64, alarm[471]);
			pstmtObj.setInt(65, alarm[472]);
			pstmtObj.setInt(66, alarm[473]);
			pstmtObj.setInt(67, alarm[474]);
			pstmtObj.setInt(68, alarm[475]);
			pstmtObj.setInt(69, alarm[476]);
			pstmtObj.setInt(70, alarm[477]);
			pstmtObj.setInt(71, alarm[478]);
			pstmtObj.setInt(72, alarm[479]);
			pstmtObj.setInt(73, alarm[480]);
			pstmtObj.setInt(74, alarm[481]);
			pstmtObj.setInt(75, alarm[482]);
			pstmtObj.setInt(76, alarm[483]);
			pstmtObj.setInt(77, alarm[484]);
			pstmtObj.setInt(78, alarm[485]);
			pstmtObj.setInt(79, alarm[486]);
			pstmtObj.setInt(80, alarm[487]);
			pstmtObj.setInt(81, alarm[488]);
			pstmtObj.setInt(82, alarm[489]);
			pstmtObj.setInt(83, alarm[490]);
			pstmtObj.setInt(84, alarm[491]);
			pstmtObj.setInt(85, alarm[492]);
			pstmtObj.setInt(86, alarm[493]);
			pstmtObj.setInt(87, alarm[494]);
			pstmtObj.setInt(88, alarm[495]);
			pstmtObj.setInt(89, alarm[496]);
			pstmtObj.setInt(90, alarm[497]);
			pstmtObj.setInt(91, alarm[498]);
			pstmtObj.setInt(92, alarm[499]);
			pstmtObj.setInt(93, alarm[500]);
			pstmtObj.setInt(94, alarm[501]);
			pstmtObj.setInt(95, alarm[502]);
			pstmtObj.setInt(96, alarm[503]);
			pstmtObj.setInt(97, alarm[504]);
			pstmtObj.setInt(98, alarm[505]);
			pstmtObj.setInt(99, alarm[506]);
			pstmtObj.setInt(100, alarm[507]);
			pstmtObj.setInt(101, alarm[508]);
			pstmtObj.setInt(102, alarm[509]);
			pstmtObj.setInt(103, alarm[510]);
			pstmtObj.setInt(104, alarm[511]);
			pstmtObj.setInt(105, alarm[512]);
			pstmtObj.setInt(106, alarm[513]);
			pstmtObj.setInt(107, alarm[514]);
			pstmtObj.setInt(108, alarm[515]);
			pstmtObj.setInt(109, alarm[516]);
			pstmtObj.setInt(110, alarm[517]);
			pstmtObj.setInt(111, alarm[518]);
			pstmtObj.setInt(112, alarm[519]);
			pstmtObj.setInt(113, alarm[520]);
			pstmtObj.setInt(114, alarm[521]);
			pstmtObj.setInt(115, alarm[522]);
			pstmtObj.setInt(116, alarm[523]);
			pstmtObj.setInt(117, alarm[524]);
			pstmtObj.setInt(118, alarm[525]);
			pstmtObj.setInt(119, alarm[526]);
			pstmtObj.setInt(120, alarm[527]);
			pstmtObj.setInt(121, alarm[528]);
			pstmtObj.setInt(122, alarm[529]);
			pstmtObj.setInt(123, alarm[530]);
			pstmtObj.setInt(124, alarm[531]);
			pstmtObj.setInt(125, alarm[532]);
			pstmtObj.setInt(126, alarm[533]);
			pstmtObj.setInt(127, alarm[534]);
			pstmtObj.setInt(128, alarm[535]);
			pstmtObj.setInt(129, alarm[536]);
			pstmtObj.setInt(130, alarm[537]);
			pstmtObj.setInt(131, alarm[538]);
			pstmtObj.setInt(132, alarm[539]);
			pstmtObj.setInt(133, alarm[540]);
			pstmtObj.setInt(134, alarm[541]);
			pstmtObj.setInt(135, alarm[542]);
			pstmtObj.setInt(136, alarm[543]);
			pstmtObj.setInt(137, alarm[544]);
			pstmtObj.setInt(138, alarm[545]);
			pstmtObj.setInt(139, alarm[546]);
			pstmtObj.setInt(140, alarm[547]);
			pstmtObj.setInt(141, alarm[548]);
			pstmtObj.setInt(142, alarm[549]);
			pstmtObj.setInt(143, alarm[550]);
			pstmtObj.setInt(144, alarm[551]);
			pstmtObj.setInt(145, alarm[552]);
			pstmtObj.setInt(146, alarm[553]);
			pstmtObj.setInt(147, alarm[554]);
			pstmtObj.setInt(148, alarm[555]);
			pstmtObj.setInt(149, alarm[556]);
			pstmtObj.setInt(150, alarm[557]);
			pstmtObj.setInt(151, alarm[558]);
			pstmtObj.setInt(152, alarm[559]);
			pstmtObj.setInt(153, alarm[560]);
			pstmtObj.setInt(154, alarm[561]);
			pstmtObj.setInt(155, alarm[562]);
			pstmtObj.setInt(156, alarm[563]);
			pstmtObj.setInt(157, alarm[564]);
			pstmtObj.setInt(158, alarm[565]);
			pstmtObj.setInt(159, alarm[566]);
			pstmtObj.setInt(160, alarm[567]);
			pstmtObj.setInt(161, alarm[568]);
			pstmtObj.setInt(162, alarm[569]);
			pstmtObj.setInt(163, alarm[570]);
			pstmtObj.setInt(164, alarm[571]);
			pstmtObj.setInt(165, alarm[572]);
			pstmtObj.setInt(166, alarm[573]);
			pstmtObj.setInt(167, alarm[574]);
			pstmtObj.setInt(168, alarm[575]);
			pstmtObj.setInt(169, alarm[576]);
			pstmtObj.setInt(170, alarm[577]);
			pstmtObj.setInt(171, alarm[578]);
			pstmtObj.setInt(172, alarm[579]);
			pstmtObj.setInt(173, alarm[580]);
			pstmtObj.setInt(174, alarm[581]);
			pstmtObj.setInt(175, alarm[582]);
			pstmtObj.setInt(176, alarm[583]);
			pstmtObj.setInt(177, alarm[584]);
			pstmtObj.setInt(178, alarm[585]);
			pstmtObj.setInt(179, alarm[586]);
			pstmtObj.setInt(180, alarm[587]);
			pstmtObj.setInt(181, alarm[588]);
			pstmtObj.setInt(182, alarm[589]);
			pstmtObj.setInt(183, alarm[590]);
			pstmtObj.setInt(184, alarm[591]);
			pstmtObj.setInt(185, alarm[592]);
			pstmtObj.setInt(186, alarm[593]);
			pstmtObj.setInt(187, alarm[594]);
			pstmtObj.setInt(188, alarm[595]);
			pstmtObj.setInt(189, alarm[596]);
			pstmtObj.setInt(190, alarm[597]);
			pstmtObj.setInt(191, alarm[598]);
			pstmtObj.setInt(192, alarm[599]);
			pstmtObj.setInt(193, alarm[600]);
			pstmtObj.setInt(194, alarm[601]);
			pstmtObj.setInt(195, alarm[602]);
			pstmtObj.setInt(196, alarm[603]);
			pstmtObj.setInt(197, alarm[604]);
			pstmtObj.setInt(198, alarm[605]);
			pstmtObj.setInt(199, alarm[606]);
			pstmtObj.setInt(200, alarm[607]);
			pstmtObj.setInt(201, alarm[608]);
			pstmtObj.setInt(202, alarm[609]);
			pstmtObj.setInt(203, alarm[610]);
			pstmtObj.setInt(204, alarm[611]);
			pstmtObj.setInt(205, alarm[612]);
			pstmtObj.setInt(206, alarm[613]);
			pstmtObj.setInt(207, alarm[614]);
			pstmtObj.setInt(208, alarm[615]);
			pstmtObj.setInt(209, alarm[616]);
			pstmtObj.setInt(210, alarm[617]);
			pstmtObj.setInt(211, alarm[618]);
			pstmtObj.setInt(212, alarm[619]);
			pstmtObj.setInt(213, alarm[620]);
			pstmtObj.setInt(214, alarm[621]);
			pstmtObj.setInt(215, alarm[622]);
			pstmtObj.setInt(216, alarm[623]);
			pstmtObj.setInt(217, alarm[624]);
			pstmtObj.setInt(218, alarm[625]);
			pstmtObj.setInt(219, alarm[626]);
			pstmtObj.setInt(220, alarm[627]);
			pstmtObj.setInt(221, alarm[628]);
			pstmtObj.setInt(222, alarm[629]);
			pstmtObj.setInt(223, alarm[630]);
			pstmtObj.setInt(224, alarm[631]);
			pstmtObj.setInt(225, alarm[632]);
			pstmtObj.setInt(226, alarm[633]);
			pstmtObj.setInt(227, alarm[634]);
			pstmtObj.setInt(228, alarm[635]);
			pstmtObj.setInt(229, alarm[636]);
			pstmtObj.setInt(230, alarm[637]);
			pstmtObj.setInt(231, alarm[638]);
			pstmtObj.setInt(232, alarm[639]);
			pstmtObj.setInt(233, alarm[640]);
			pstmtObj.setInt(234, alarm[641]);
			pstmtObj.setInt(235, alarm[642]);
			pstmtObj.setInt(236, alarm[643]);
			pstmtObj.setInt(237, alarm[644]);
			pstmtObj.setInt(238, alarm[645]);
			pstmtObj.setInt(239, alarm[646]);
			pstmtObj.setInt(240, alarm[647]);
			pstmtObj.setInt(241, alarm[648]);
			pstmtObj.setInt(242, alarm[649]);
			pstmtObj.setInt(243, alarm[650]);
			pstmtObj.setInt(244, alarm[651]);
			pstmtObj.setInt(245, alarm[652]);
			pstmtObj.setInt(246, alarm[653]);
			pstmtObj.setInt(247, alarm[654]);
			pstmtObj.setInt(248, alarm[655]);
			pstmtObj.setInt(249, alarm[656]);
			pstmtObj.setInt(250, alarm[657]);
			pstmtObj.setInt(251, alarm[658]);
			pstmtObj.setInt(252, alarm[659]);
			pstmtObj.setInt(253, alarm[660]);
			pstmtObj.setInt(254, alarm[661]);
			pstmtObj.setInt(255, alarm[662]);
			pstmtObj.setInt(256, alarm[663]);
			pstmtObj.setInt(257, alarm[664]);
			pstmtObj.setInt(258, alarm[665]);
			pstmtObj.setInt(259, alarm[666]);
			pstmtObj.setInt(260, alarm[667]);
			pstmtObj.setInt(261, alarm[668]);
			pstmtObj.setInt(262, alarm[669]);
			pstmtObj.setInt(263, alarm[670]);
			pstmtObj.setInt(264, alarm[671]);
			pstmtObj.setInt(265, alarm[672]);
			pstmtObj.setInt(266, alarm[673]);
			pstmtObj.setInt(267, alarm[674]);
			pstmtObj.setInt(268, alarm[675]);
			pstmtObj.setInt(269, alarm[676]);
			pstmtObj.setInt(270, alarm[677]);
			pstmtObj.setInt(271, alarm[678]);
			pstmtObj.setInt(272, alarm[679]);
			pstmtObj.setInt(273, alarm[680]);
			pstmtObj.setInt(274, alarm[681]);
			pstmtObj.setInt(275, alarm[682]);
			pstmtObj.setInt(276, alarm[683]);
			pstmtObj.setInt(277, alarm[684]);
			pstmtObj.setInt(278, alarm[685]);
			pstmtObj.setInt(279, alarm[686]);
			pstmtObj.setInt(280, alarm[687]);
			pstmtObj.setInt(281, alarm[688]);
			pstmtObj.setInt(282, alarm[689]);
			pstmtObj.setInt(283, alarm[690]);
			pstmtObj.setInt(284, alarm[691]);
			pstmtObj.setInt(285, alarm[692]);
			pstmtObj.setInt(286, alarm[693]);
			pstmtObj.setInt(287, alarm[694]);
			pstmtObj.setInt(288, alarm[695]);
			pstmtObj.setInt(289, alarm[696]);
			pstmtObj.setInt(290, alarm[697]);
			pstmtObj.setInt(291, alarm[698]);
			pstmtObj.setInt(292, alarm[699]);
			pstmtObj.setInt(293, alarm[700]);
			pstmtObj.setInt(294, alarm[701]);
			pstmtObj.setInt(295, alarm[702]);
			pstmtObj.setInt(296, alarm[703]);
			pstmtObj.setInt(297, alarm[704]);
			pstmtObj.setInt(298, alarm[705]);
			pstmtObj.setInt(299, alarm[706]);
			pstmtObj.setInt(300, alarm[707]);
			pstmtObj.setInt(301, alarm[708]);
			pstmtObj.setInt(302, alarm[709]);
			pstmtObj.setInt(303, alarm[710]);
			pstmtObj.setInt(304, alarm[711]);
			pstmtObj.setInt(305, alarm[712]);
			pstmtObj.setInt(306, alarm[713]);
			pstmtObj.setInt(307, alarm[714]);
			pstmtObj.setInt(308, alarm[715]);
			pstmtObj.setInt(309, alarm[716]);
			pstmtObj.setInt(310, alarm[717]);
			pstmtObj.setInt(311, alarm[718]);
			pstmtObj.setInt(312, alarm[719]);
			pstmtObj.setInt(313, alarm[720]);
			pstmtObj.setInt(314, alarm[721]);
			pstmtObj.setInt(315, alarm[722]);
			pstmtObj.setInt(316, alarm[723]);
			pstmtObj.setInt(317, alarm[724]);
			pstmtObj.setInt(318, alarm[725]);
			pstmtObj.setInt(319, alarm[726]);
			pstmtObj.setInt(320, alarm[727]);
			pstmtObj.setInt(321, alarm[728]);
			pstmtObj.setInt(322, alarm[729]);
			pstmtObj.setInt(323, alarm[730]);
			pstmtObj.setInt(324, alarm[731]);
			pstmtObj.setInt(325, alarm[732]);
			pstmtObj.setInt(326, alarm[733]);
			pstmtObj.setInt(327, alarm[734]);
			pstmtObj.setInt(328, alarm[735]);
			pstmtObj.setInt(329, alarm[736]);
			pstmtObj.setInt(330, alarm[737]);
			pstmtObj.setInt(331, alarm[738]);
			pstmtObj.setInt(332, alarm[739]);
			pstmtObj.setInt(333, alarm[740]);
			pstmtObj.setInt(334, alarm[741]);
			pstmtObj.setInt(335, alarm[742]);
			pstmtObj.setInt(336, alarm[743]);
			pstmtObj.setInt(337, alarm[744]);
			pstmtObj.setInt(338, alarm[745]);
			pstmtObj.setInt(339, alarm[746]);
			pstmtObj.setInt(340, alarm[747]);
			pstmtObj.setInt(341, alarm[748]);
			pstmtObj.setInt(342, alarm[749]);
			pstmtObj.setInt(343, alarm[750]);
			pstmtObj.setInt(344, alarm[751]);
			pstmtObj.setInt(345, alarm[752]);
			pstmtObj.setInt(346, alarm[753]);
			pstmtObj.setInt(347, alarm[754]);
			pstmtObj.setInt(348, alarm[755]);
			pstmtObj.setInt(349, alarm[756]);
			pstmtObj.setInt(350, alarm[757]);
			pstmtObj.setInt(351, alarm[758]);
			pstmtObj.setInt(352, alarm[759]);
			pstmtObj.setInt(353, alarm[760]);
			pstmtObj.setInt(354, alarm[761]);
			pstmtObj.setInt(355, alarm[762]);
			pstmtObj.setInt(356, alarm[763]);
			pstmtObj.setInt(357, alarm[764]);
			pstmtObj.setInt(358, alarm[765]);
			pstmtObj.setInt(359, alarm[766]);
			pstmtObj.setInt(360, alarm[767]);
			pstmtObj.setInt(361, alarm[768]);
			pstmtObj.setInt(362, alarm[769]);
			pstmtObj.setInt(363, alarm[770]);
			pstmtObj.setInt(364, alarm[771]);
			pstmtObj.setInt(365, alarm[772]);
			pstmtObj.setInt(366, alarm[773]);
			pstmtObj.setInt(367, alarm[774]);
			pstmtObj.setInt(368, alarm[775]);
			pstmtObj.setInt(369, alarm[776]);
			pstmtObj.setInt(370, alarm[777]);
			pstmtObj.setInt(371, alarm[778]);
			pstmtObj.setInt(372, alarm[779]);
			pstmtObj.setInt(373, alarm[780]);
			pstmtObj.setInt(374, alarm[781]);
			pstmtObj.setInt(375, alarm[782]);
			pstmtObj.setInt(376, alarm[783]);
			pstmtObj.setInt(377, alarm[784]);
			pstmtObj.setInt(378, alarm[785]);
			pstmtObj.setInt(379, alarm[786]);
			pstmtObj.setInt(380, alarm[787]);
			pstmtObj.setInt(381, alarm[788]);
			pstmtObj.setInt(382, alarm[789]);
			pstmtObj.setInt(383, alarm[790]);
			pstmtObj.setInt(384, alarm[791]);
			pstmtObj.setInt(385, alarm[792]);
			pstmtObj.setInt(386, alarm[793]);
			pstmtObj.setInt(387, alarm[794]);
			pstmtObj.setInt(388, alarm[795]);
			pstmtObj.setInt(389, alarm[796]);
			pstmtObj.setInt(390, alarm[797]);
			pstmtObj.setInt(391, alarm[798]);
			pstmtObj.setInt(392, alarm[799]);
			pstmtObj.setInt(393, alarm[800]);
			pstmtObj.setInt(394, alarm[801]);
			pstmtObj.setInt(395, alarm[802]);
			pstmtObj.setInt(396, alarm[803]);
			pstmtObj.setInt(397, alarm[804]);
			pstmtObj.setInt(398, alarm[805]);
			pstmtObj.setInt(399, alarm[806]);
			pstmtObj.setInt(400, alarm[807]);
			pstmtObj.setInt(401, alarm[808]);
			pstmtObj.setInt(402, alarm[809]);
			pstmtObj.setInt(403, alarm[810]);
			pstmtObj.setInt(404, alarm[811]);
			pstmtObj.setInt(405, alarm[812]);
			pstmtObj.setInt(406, alarm[813]);
			pstmtObj.setInt(407, alarm[814]);
			pstmtObj.setInt(408, alarm[815]);
			pstmtObj.setInt(409, alarm[816]);
			pstmtObj.setInt(410, alarm[817]);
			pstmtObj.setInt(411, alarm[818]);
			pstmtObj.setInt(412, alarm[819]);
			pstmtObj.setInt(413, alarm[820]);
			pstmtObj.setInt(414, alarm[821]);
			pstmtObj.setInt(415, alarm[822]);
			pstmtObj.setInt(416, alarm[823]);
			pstmtObj.setInt(417, alarm[824]);
			pstmtObj.setInt(418, alarm[825]);
			pstmtObj.setInt(419, alarm[826]);
			pstmtObj.setInt(420, alarm[827]);
			pstmtObj.setInt(421, alarm[828]);
			pstmtObj.setInt(422, alarm[829]);
			pstmtObj.setInt(423, alarm[830]);
			pstmtObj.setInt(424, alarm[831]);
			pstmtObj.setInt(425, alarm[832]);
			pstmtObj.setInt(426, alarm[833]);
			pstmtObj.setInt(427, alarm[834]);
			pstmtObj.setInt(428, alarm[835]);
			pstmtObj.setInt(429, alarm[836]);
			pstmtObj.setInt(430, alarm[837]);
			pstmtObj.setInt(431, alarm[838]);
			pstmtObj.setInt(432, alarm[839]);
			pstmtObj.setInt(433, alarm[840]);
			pstmtObj.setInt(434, alarm[841]);
			pstmtObj.setInt(435, alarm[842]);
			pstmtObj.setInt(436, alarm[843]);
			pstmtObj.setInt(437, alarm[844]);
			pstmtObj.setInt(438, alarm[845]);
			pstmtObj.setInt(439, alarm[846]);
			pstmtObj.setInt(440, alarm[847]);
			pstmtObj.setInt(441, alarm[848]);
			pstmtObj.setInt(442, alarm[849]);
			pstmtObj.setInt(443, alarm[850]);
			pstmtObj.setInt(444, alarm[851]);
			pstmtObj.setInt(445, alarm[852]);
			pstmtObj.setInt(446, alarm[853]);
			pstmtObj.setInt(447, alarm[854]);
			pstmtObj.setInt(448, alarm[855]);
			pstmtObj.setInt(449, alarm[856]);
			pstmtObj.setInt(450, alarm[857]);
			pstmtObj.setInt(451, alarm[858]);
			pstmtObj.setInt(452, alarm[859]);
			pstmtObj.setInt(453, alarm[860]);
			pstmtObj.setInt(454, alarm[861]);
			pstmtObj.setInt(455, alarm[862]);
			pstmtObj.setInt(456, alarm[863]);
			pstmtObj.setInt(457, alarm[864]);
			pstmtObj.setInt(458, alarm[865]);
			pstmtObj.setInt(459, alarm[866]);
			pstmtObj.setInt(460, alarm[867]);
			pstmtObj.setInt(461, alarm[868]);
			pstmtObj.setInt(462, alarm[869]);
			pstmtObj.setInt(463, alarm[870]);
			pstmtObj.setInt(464, alarm[871]);
			pstmtObj.setInt(465, alarm[872]);
			pstmtObj.setInt(466, alarm[873]);
			pstmtObj.setInt(467, alarm[874]);
			pstmtObj.setInt(468, alarm[875]);
			pstmtObj.setInt(469, alarm[876]);
			pstmtObj.setInt(470, alarm[877]);
			pstmtObj.setInt(471, alarm[878]);
			pstmtObj.setInt(472, alarm[879]);
			pstmtObj.setInt(473, alarm[880]);
			pstmtObj.setInt(474, alarm[881]);
			pstmtObj.setInt(475, alarm[882]);
			pstmtObj.setInt(476, alarm[883]);
			pstmtObj.setInt(477, alarm[884]);
			pstmtObj.setInt(478, alarm[885]);
			pstmtObj.setInt(479, alarm[886]);
			pstmtObj.setInt(480, alarm[887]);
			pstmtObj.setInt(481, alarm[888]);
			pstmtObj.setInt(482, alarm[889]);
			pstmtObj.setInt(483, alarm[890]);
			pstmtObj.setInt(484, alarm[891]);
			pstmtObj.setInt(485, alarm[892]);
			pstmtObj.setInt(486, alarm[893]);
			pstmtObj.setInt(487, alarm[894]);
			pstmtObj.setInt(488, alarm[895]);
			pstmtObj.setInt(489, alarm[896]);
			pstmtObj.setInt(490, alarm[897]);
			pstmtObj.setInt(491, alarm[898]);
			pstmtObj.setInt(492, alarm[899]);
			pstmtObj.setInt(493, alarm[900]);
			pstmtObj.setInt(494, alarm[901]);
			pstmtObj.setInt(495, alarm[902]);
			pstmtObj.setInt(496, alarm[903]);
			pstmtObj.setInt(497, alarm[904]);
			pstmtObj.setInt(498, alarm[905]);
			pstmtObj.setInt(499, alarm[906]);
			pstmtObj.setInt(500, alarm[907]);
			pstmtObj.setInt(501, alarm[908]);
			pstmtObj.setInt(502, alarm[909]);
			pstmtObj.setInt(503, alarm[910]);
			pstmtObj.setInt(504, alarm[911]);
			pstmtObj.setInt(505, alarm[912]);
			pstmtObj.setInt(506, alarm[913]);
			pstmtObj.setInt(507, alarm[914]);
			pstmtObj.setInt(508, alarm[915]);
			pstmtObj.setInt(509, alarm[916]);
			pstmtObj.setInt(510, alarm[917]);
			pstmtObj.setInt(511, alarm[918]);
			pstmtObj.setInt(512, alarm[919]);
			pstmtObj.setInt(513, alarm[920]);
			pstmtObj.setInt(514, alarm[921]);
			pstmtObj.setInt(515, alarm[922]);
			pstmtObj.setInt(516, alarm[923]);
			pstmtObj.setInt(517, alarm[924]);
			pstmtObj.setInt(518, alarm[925]);
			pstmtObj.setInt(519, alarm[926]);
			pstmtObj.setInt(520, alarm[927]);
			pstmtObj.setInt(521, alarm[928]);
			pstmtObj.setInt(522, alarm[929]);
			pstmtObj.setInt(523, alarm[930]);
			pstmtObj.setInt(524, alarm[931]);
			pstmtObj.setInt(525, alarm[932]);
			pstmtObj.setInt(526, alarm[933]);
			pstmtObj.setInt(527, alarm[934]);
			pstmtObj.setInt(528, alarm[935]);
			pstmtObj.setInt(529, alarm[936]);
			pstmtObj.setInt(530, alarm[937]);
			pstmtObj.setInt(531, alarm[938]);
			pstmtObj.setInt(532, alarm[939]);
			pstmtObj.setInt(533, alarm[940]);
			pstmtObj.setInt(534, alarm[941]);
			pstmtObj.setInt(535, alarm[942]);
			pstmtObj.setInt(536, alarm[943]);
			pstmtObj.setInt(537, alarm[944]);
			pstmtObj.setInt(538, alarm[945]);
			pstmtObj.setInt(539, alarm[946]);
			pstmtObj.setInt(540, alarm[947]);
			pstmtObj.setInt(541, alarm[948]);
			pstmtObj.setInt(542, alarm[949]);
			pstmtObj.setInt(543, alarm[950]);
			pstmtObj.setInt(544, alarm[951]);
			pstmtObj.setInt(545, alarm[952]);
			pstmtObj.setInt(546, alarm[953]);
			pstmtObj.setInt(547, alarm[954]);
			pstmtObj.setInt(548, alarm[955]);
			pstmtObj.setInt(549, alarm[956]);
			pstmtObj.setInt(550, alarm[957]);
			pstmtObj.setInt(551, alarm[958]);
			pstmtObj.setInt(552, alarm[959]);
			pstmtObj.setInt(553, alarm[960]);
			pstmtObj.setInt(554, alarm[961]);
			pstmtObj.setInt(555, alarm[962]);
			pstmtObj.setInt(556, alarm[963]);
			pstmtObj.setInt(557, alarm[964]);
			pstmtObj.setInt(558, alarm[965]);
			pstmtObj.setInt(559, alarm[966]);
			pstmtObj.setInt(560, alarm[967]);
			pstmtObj.setInt(561, alarm[968]);
			pstmtObj.setInt(562, alarm[969]);
			pstmtObj.setInt(563, alarm[970]);
			pstmtObj.setInt(564, alarm[971]);
			pstmtObj.setInt(565, alarm[972]);
			pstmtObj.setInt(566, alarm[973]);
			pstmtObj.setInt(567, alarm[974]);
			pstmtObj.setInt(568, alarm[975]);
			pstmtObj.setInt(569, alarm[976]);
			pstmtObj.setInt(570, alarm[977]);
			pstmtObj.setInt(571, alarm[978]);
			pstmtObj.setInt(572, alarm[979]);
			pstmtObj.setInt(573, alarm[980]);
			pstmtObj.setInt(574, alarm[981]);
			pstmtObj.setInt(575, alarm[982]);
			pstmtObj.setInt(576, alarm[983]);
			pstmtObj.setInt(577, alarm[984]);
			pstmtObj.setInt(578, alarm[985]);
			pstmtObj.setInt(579, alarm[986]);
			pstmtObj.setInt(580, alarm[987]);
			pstmtObj.setInt(581, alarm[988]);
			pstmtObj.setInt(582, alarm[989]);
			pstmtObj.setInt(583, alarm[990]);
			pstmtObj.setInt(584, alarm[991]);
			pstmtObj.setInt(585, alarm[992]);
			pstmtObj.setInt(586, alarm[993]);
			pstmtObj.setInt(587, alarm[994]);
			pstmtObj.setInt(588, alarm[995]);
			pstmtObj.setInt(589, alarm[996]);
			pstmtObj.setInt(590, alarm[997]);
			pstmtObj.setInt(591, alarm[998]);
			pstmtObj.setInt(592, alarm[999]);
			pstmtObj.setInt(593, alarm[1000]);
			pstmtObj.setInt(594, alarm[1001]);
			pstmtObj.setInt(595, alarm[1002]);
			pstmtObj.setInt(596, alarm[1003]);
			pstmtObj.setInt(597, alarm[1004]);
			pstmtObj.setInt(598, alarm[1005]);
			pstmtObj.setInt(599, alarm[1006]);
			pstmtObj.setInt(600, alarm[1007]);
			pstmtObj.setInt(601, alarm[1008]);
			pstmtObj.setInt(602, alarm[1009]);
			pstmtObj.setInt(603, alarm[1010]);
			pstmtObj.setInt(604, alarm[1011]);
			pstmtObj.setInt(605, alarm[1012]);
			pstmtObj.setInt(606, alarm[1013]);
			pstmtObj.setInt(607, alarm[1014]);
			pstmtObj.setInt(608, alarm[1015]);
			pstmtObj.setInt(609, alarm[1016]);
			pstmtObj.setInt(610, alarm[1017]);
			pstmtObj.setInt(611, alarm[1018]);
			pstmtObj.setInt(612, alarm[1019]);
			pstmtObj.setInt(613, alarm[1020]);
			pstmtObj.setInt(614, alarm[1021]);
			pstmtObj.setInt(615, alarm[1022]);
			pstmtObj.setInt(616, alarm[1023]);
			pstmtObj.setInt(617, alarm[1024]);
			pstmtObj.setInt(618, alarm[1025]);
			pstmtObj.setInt(619, alarm[1026]);
			pstmtObj.setInt(620, alarm[1027]);
			pstmtObj.setInt(621, alarm[1028]);
			pstmtObj.setInt(622, alarm[1029]);
			pstmtObj.setInt(623, alarm[1030]);
			pstmtObj.setInt(624, alarm[1031]);
			pstmtObj.setInt(625, alarm[1032]);
			pstmtObj.setInt(626, alarm[1033]);
			pstmtObj.setInt(627, alarm[1034]);
			pstmtObj.setInt(628, alarm[1035]);
			pstmtObj.setInt(629, alarm[1036]);
			pstmtObj.setInt(630, alarm[1037]);
			pstmtObj.setInt(631, alarm[1038]);
			pstmtObj.setInt(632, alarm[1039]);
			pstmtObj.setInt(633, alarm[1040]);
			pstmtObj.setInt(634, alarm[1041]);
			pstmtObj.setInt(635, alarm[1042]);
			pstmtObj.setInt(636, alarm[1043]);
			pstmtObj.setInt(637, alarm[1044]);
			pstmtObj.setInt(638, alarm[1045]);
			pstmtObj.setInt(639, alarm[1046]);
			pstmtObj.setInt(640, alarm[1047]);
			pstmtObj.setInt(641, smSiteId);
			pstmtObj.setInt(642, smSitetypeid);
			pstmtObj.setString(643,siteId);
			pstmtObj.setLong(644,_recordtime);

			pstmtObj.setLong(645,dbCreationTime);

			pstmtObj.setDate(646,hpDate);

			pstmtObj.executeUpdate();

		} catch (SQLException e) {
			logger.error ("Failed to process trans_ltmalarmdatahistory", e);
			e.printStackTrace();
		} finally {

			if(pstmtObj != null) {
				pstmtObj.close();
				pstmtObj = null;
			}
		}
	}

	public void insertIntoLiAlarmLastStatus(int[] alarm,int smSiteId, int smSitetypeid, String siteId, long _recordtime, long dbCreationTime, Date hpDate, Connection connObj) throws Exception {

		PreparedStatement pstmtObj = null;

		try {
			//Query3
			pstmtObj = connObj.prepareStatement("insert into trans_ltmalarmlaststatus(Alarm_409,Alarm_410,Alarm_411,Alarm_412,Alarm_413,Alarm_414,Alarm_415,Alarm_416,Alarm_417,Alarm_418,Alarm_419,Alarm_420,Alarm_421,Alarm_422,Alarm_423,Alarm_424,Alarm_425,Alarm_426,Alarm_427,Alarm_428,Alarm_429,Alarm_430,Alarm_431,Alarm_432,Alarm_433,Alarm_434,Alarm_435,Alarm_436,Alarm_437,Alarm_438,Alarm_439,Alarm_440,Alarm_441,Alarm_442,Alarm_443,Alarm_444,Alarm_445,Alarm_446,Alarm_447,Alarm_448,Alarm_449,Alarm_450,Alarm_451,Alarm_452,Alarm_453,Alarm_454,Alarm_455,Alarm_456,Alarm_457,Alarm_458,Alarm_459,Alarm_460,Alarm_461,Alarm_462,Alarm_463,Alarm_464,Alarm_465,Alarm_466,Alarm_467,Alarm_468,Alarm_469,Alarm_470,Alarm_471,Alarm_472,Alarm_473,Alarm_474,Alarm_475,Alarm_476,Alarm_477,Alarm_478,Alarm_479,Alarm_480,Alarm_481,Alarm_482,Alarm_483,Alarm_484,Alarm_485,Alarm_486,Alarm_487,Alarm_488,Alarm_489,Alarm_490,Alarm_491,Alarm_492,Alarm_493,Alarm_494,Alarm_495,Alarm_496,Alarm_497,Alarm_498,Alarm_499,Alarm_500,Alarm_501,Alarm_502,Alarm_503,Alarm_504,Alarm_505,Alarm_506,Alarm_507,Alarm_508,Alarm_509,Alarm_510,Alarm_511,Alarm_512,Alarm_513,Alarm_514,Alarm_515,Alarm_516,Alarm_517,Alarm_518,Alarm_519,Alarm_520,Alarm_521,Alarm_522,Alarm_523,Alarm_524,Alarm_525,Alarm_526,Alarm_527,Alarm_528,Alarm_529,Alarm_530,Alarm_531,Alarm_532,Alarm_533,Alarm_534,Alarm_535,Alarm_536,Alarm_537,Alarm_538,Alarm_539,Alarm_540,Alarm_541,Alarm_542,Alarm_543,Alarm_544,Alarm_545,Alarm_546,Alarm_547,Alarm_548,Alarm_549,Alarm_550,Alarm_551,Alarm_552,Alarm_553,Alarm_554,Alarm_555,Alarm_556,Alarm_557,Alarm_558,Alarm_559,Alarm_560,Alarm_561,Alarm_562,Alarm_563,Alarm_564,Alarm_565,Alarm_566,Alarm_567,Alarm_568,Alarm_569,Alarm_570,Alarm_571,Alarm_572,Alarm_573,Alarm_574,Alarm_575,Alarm_576,Alarm_577,Alarm_578,Alarm_579,Alarm_580,Alarm_581,Alarm_582,Alarm_583,Alarm_584,Alarm_585,Alarm_586,Alarm_587,Alarm_588,Alarm_589,Alarm_590,Alarm_591,Alarm_592,Alarm_593,Alarm_594,Alarm_595,Alarm_596,Alarm_597,Alarm_598,Alarm_599,Alarm_600,Alarm_601,Alarm_602,Alarm_603,Alarm_604,Alarm_605,Alarm_606,Alarm_607,Alarm_608,Alarm_609,Alarm_610,Alarm_611,Alarm_612,Alarm_613,Alarm_614,Alarm_615,Alarm_616,Alarm_617,Alarm_618,Alarm_619,Alarm_620,Alarm_621,Alarm_622,Alarm_623,Alarm_624,Alarm_625,Alarm_626,Alarm_627,Alarm_628,Alarm_629,Alarm_630,Alarm_631,Alarm_632,Alarm_633,Alarm_634,Alarm_635,Alarm_636,Alarm_637,Alarm_638,Alarm_639,Alarm_640,Alarm_641,Alarm_642,Alarm_643,Alarm_644,Alarm_645,Alarm_646,Alarm_647,Alarm_648,Alarm_649,Alarm_650,Alarm_651,Alarm_652,Alarm_653,Alarm_654,Alarm_655,Alarm_656,Alarm_657,Alarm_658,Alarm_659,Alarm_660,Alarm_661,Alarm_662,Alarm_663,Alarm_664,Alarm_665,Alarm_666,Alarm_667,Alarm_668,Alarm_669,Alarm_670,Alarm_671,Alarm_672,Alarm_673,Alarm_674,Alarm_675,Alarm_676,Alarm_677,Alarm_678,Alarm_679,Alarm_680,Alarm_681,Alarm_682,Alarm_683,Alarm_684,Alarm_685,Alarm_686,Alarm_687,Alarm_688,Alarm_689,Alarm_690,Alarm_691,Alarm_692,Alarm_693,Alarm_694,Alarm_695,Alarm_696,Alarm_697,Alarm_698,Alarm_699,Alarm_700,Alarm_701,Alarm_702,Alarm_703,Alarm_704,Alarm_705,Alarm_706,Alarm_707,Alarm_708,Alarm_709,Alarm_710,Alarm_711,Alarm_712,Alarm_713,Alarm_714,Alarm_715,Alarm_716,Alarm_717,Alarm_718,Alarm_719,Alarm_720,Alarm_721,Alarm_722,Alarm_723,Alarm_724,Alarm_725,Alarm_726,Alarm_727,Alarm_728,Alarm_729,Alarm_730,Alarm_731,Alarm_732,Alarm_733,Alarm_734,Alarm_735,Alarm_736,Alarm_737,Alarm_738,Alarm_739,Alarm_740,Alarm_741,Alarm_742,Alarm_743,Alarm_744,Alarm_745,Alarm_746,Alarm_747,Alarm_748,Alarm_749,Alarm_750,Alarm_751,Alarm_752,Alarm_753,Alarm_754,Alarm_755,Alarm_756,Alarm_757,Alarm_758,Alarm_759,Alarm_760,Alarm_761,Alarm_762,Alarm_763,Alarm_764,Alarm_765,Alarm_766,Alarm_767,Alarm_768,Alarm_769,Alarm_770,Alarm_771,Alarm_772,Alarm_773,Alarm_774,Alarm_775,Alarm_776,Alarm_777,Alarm_778,Alarm_779,Alarm_780,Alarm_781,Alarm_782,Alarm_783,Alarm_784,Alarm_785,Alarm_786,Alarm_787,Alarm_788,Alarm_789,Alarm_790,Alarm_791,Alarm_792,Alarm_793,Alarm_794,Alarm_795,Alarm_796,Alarm_797,Alarm_798,Alarm_799,Alarm_800,Alarm_801,Alarm_802,Alarm_803,Alarm_804,Alarm_805,Alarm_806,Alarm_807,Alarm_808,Alarm_809,Alarm_810,Alarm_811,Alarm_812,Alarm_813,Alarm_814,Alarm_815,Alarm_816,Alarm_817,Alarm_818,Alarm_819,Alarm_820,Alarm_821,Alarm_822,Alarm_823,Alarm_824,Alarm_825,Alarm_826,Alarm_827,Alarm_828,Alarm_829,Alarm_830,Alarm_831,Alarm_832,Alarm_833,Alarm_834,Alarm_835,Alarm_836,Alarm_837,Alarm_838,Alarm_839,Alarm_840,Alarm_841,Alarm_842,Alarm_843,Alarm_844,Alarm_845,Alarm_846,Alarm_847,Alarm_848,Alarm_849,Alarm_850,Alarm_851,Alarm_852,Alarm_853,Alarm_854,Alarm_855,Alarm_856,Alarm_857,Alarm_858,Alarm_859,Alarm_860,Alarm_861,Alarm_862,Alarm_863,Alarm_864,Alarm_865,Alarm_866,Alarm_867,Alarm_868,Alarm_869,Alarm_870,Alarm_871,Alarm_872,Alarm_873,Alarm_874,Alarm_875,Alarm_876,Alarm_877,Alarm_878,Alarm_879,Alarm_880,Alarm_881,Alarm_882,Alarm_883,Alarm_884,Alarm_885,Alarm_886,Alarm_887,Alarm_888,Alarm_889,Alarm_890,Alarm_891,Alarm_892,Alarm_893,Alarm_894,Alarm_895,Alarm_896,Alarm_897,Alarm_898,Alarm_899,Alarm_900,Alarm_901,Alarm_902,Alarm_903,Alarm_904,Alarm_905,Alarm_906,Alarm_907,Alarm_908,Alarm_909,Alarm_910,Alarm_911,Alarm_912,Alarm_913,Alarm_914,Alarm_915,Alarm_916,Alarm_917,Alarm_918,Alarm_919,Alarm_920,Alarm_921,Alarm_922,Alarm_923,Alarm_924,Alarm_925,Alarm_926,Alarm_927,Alarm_928,Alarm_929,Alarm_930,Alarm_931,Alarm_932,Alarm_933,Alarm_934,Alarm_935,Alarm_936,Alarm_937,Alarm_938,Alarm_939,Alarm_940,Alarm_941,Alarm_942,Alarm_943,Alarm_944,Alarm_945,Alarm_946,Alarm_947,Alarm_948,Alarm_949,Alarm_950,Alarm_951,Alarm_952,Alarm_953,Alarm_954,Alarm_955,Alarm_956,Alarm_957,Alarm_958,Alarm_959,Alarm_960,Alarm_961,Alarm_962,Alarm_963,Alarm_964,Alarm_965,Alarm_966,Alarm_967,Alarm_968,Alarm_969,Alarm_970,Alarm_971,Alarm_972,Alarm_973,Alarm_974,Alarm_975,Alarm_976,Alarm_977,Alarm_978,Alarm_979,Alarm_980,Alarm_981,Alarm_982,Alarm_983,Alarm_984,Alarm_985,Alarm_986,Alarm_987,Alarm_988,Alarm_989,Alarm_990,Alarm_991,Alarm_992,Alarm_993,Alarm_994,Alarm_995,Alarm_996,Alarm_997,Alarm_998,Alarm_999,Alarm_1000,Alarm_1001,Alarm_1002,Alarm_1003,Alarm_1004,Alarm_1005,Alarm_1006,Alarm_1007,Alarm_1008,Alarm_1009,Alarm_1010,Alarm_1011,Alarm_1012,Alarm_1013,Alarm_1014,Alarm_1015,Alarm_1016,Alarm_1017,Alarm_1018,Alarm_1019,Alarm_1020,Alarm_1021,Alarm_1022,Alarm_1023,Alarm_1024,Alarm_1025,Alarm_1026,Alarm_1027,Alarm_1028,Alarm_1029,Alarm_1030,Alarm_1031,Alarm_1032,Alarm_1033,Alarm_1034,Alarm_1035,Alarm_1036,Alarm_1037,Alarm_1038,Alarm_1039,Alarm_1040,Alarm_1041,Alarm_1042,Alarm_1043,Alarm_1044,Alarm_1045,Alarm_1046,Alarm_1047,Alarm_1048,smSiteID,smSitetypeid,smSiteCode,ltmTimestamp,DBCreationTimestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmtObj.setInt(1, alarm[408]);
			pstmtObj.setInt(2, alarm[409]);
			pstmtObj.setInt(3, alarm[410]);
			pstmtObj.setInt(4, alarm[411]);
			pstmtObj.setInt(5, alarm[412]);
			pstmtObj.setInt(6, alarm[413]);
			pstmtObj.setInt(7, alarm[414]);
			pstmtObj.setInt(8, alarm[415]);
			pstmtObj.setInt(9, alarm[416]);
			pstmtObj.setInt(10, alarm[417]);
			pstmtObj.setInt(11, alarm[418]);
			pstmtObj.setInt(12, alarm[419]);
			pstmtObj.setInt(13, alarm[420]);
			pstmtObj.setInt(14, alarm[421]);
			pstmtObj.setInt(15, alarm[422]);
			pstmtObj.setInt(16, alarm[423]);
			pstmtObj.setInt(17, alarm[424]);
			pstmtObj.setInt(18, alarm[425]);
			pstmtObj.setInt(19, alarm[426]);
			pstmtObj.setInt(20, alarm[427]);
			pstmtObj.setInt(21, alarm[428]);
			pstmtObj.setInt(22, alarm[429]);
			pstmtObj.setInt(23, alarm[430]);
			pstmtObj.setInt(24, alarm[431]);
			pstmtObj.setInt(25, alarm[432]);
			pstmtObj.setInt(26, alarm[433]);
			pstmtObj.setInt(27, alarm[434]);
			pstmtObj.setInt(28, alarm[435]);
			pstmtObj.setInt(29, alarm[436]);
			pstmtObj.setInt(30, alarm[437]);
			pstmtObj.setInt(31, alarm[438]);
			pstmtObj.setInt(32, alarm[439]);
			pstmtObj.setInt(33, alarm[440]);
			pstmtObj.setInt(34, alarm[441]);
			pstmtObj.setInt(35, alarm[442]);
			pstmtObj.setInt(36, alarm[443]);
			pstmtObj.setInt(37, alarm[444]);
			pstmtObj.setInt(38, alarm[445]);
			pstmtObj.setInt(39, alarm[446]);
			pstmtObj.setInt(40, alarm[447]);
			pstmtObj.setInt(41, alarm[448]);
			pstmtObj.setInt(42, alarm[449]);
			pstmtObj.setInt(43, alarm[450]);
			pstmtObj.setInt(44, alarm[451]);
			pstmtObj.setInt(45, alarm[452]);
			pstmtObj.setInt(46, alarm[453]);
			pstmtObj.setInt(47, alarm[454]);
			pstmtObj.setInt(48, alarm[455]);
			pstmtObj.setInt(49, alarm[456]);
			pstmtObj.setInt(50, alarm[457]);
			pstmtObj.setInt(51, alarm[458]);
			pstmtObj.setInt(52, alarm[459]);
			pstmtObj.setInt(53, alarm[460]);
			pstmtObj.setInt(54, alarm[461]);
			pstmtObj.setInt(55, alarm[462]);
			pstmtObj.setInt(56, alarm[463]);
			pstmtObj.setInt(57, alarm[464]);
			pstmtObj.setInt(58, alarm[465]);
			pstmtObj.setInt(59, alarm[466]);
			pstmtObj.setInt(60, alarm[467]);
			pstmtObj.setInt(61, alarm[468]);
			pstmtObj.setInt(62, alarm[469]);
			pstmtObj.setInt(63, alarm[470]);
			pstmtObj.setInt(64, alarm[471]);
			pstmtObj.setInt(65, alarm[472]);
			pstmtObj.setInt(66, alarm[473]);
			pstmtObj.setInt(67, alarm[474]);
			pstmtObj.setInt(68, alarm[475]);
			pstmtObj.setInt(69, alarm[476]);
			pstmtObj.setInt(70, alarm[477]);
			pstmtObj.setInt(71, alarm[478]);
			pstmtObj.setInt(72, alarm[479]);
			pstmtObj.setInt(73, alarm[480]);
			pstmtObj.setInt(74, alarm[481]);
			pstmtObj.setInt(75, alarm[482]);
			pstmtObj.setInt(76, alarm[483]);
			pstmtObj.setInt(77, alarm[484]);
			pstmtObj.setInt(78, alarm[485]);
			pstmtObj.setInt(79, alarm[486]);
			pstmtObj.setInt(80, alarm[487]);
			pstmtObj.setInt(81, alarm[488]);
			pstmtObj.setInt(82, alarm[489]);
			pstmtObj.setInt(83, alarm[490]);
			pstmtObj.setInt(84, alarm[491]);
			pstmtObj.setInt(85, alarm[492]);
			pstmtObj.setInt(86, alarm[493]);
			pstmtObj.setInt(87, alarm[494]);
			pstmtObj.setInt(88, alarm[495]);
			pstmtObj.setInt(89, alarm[496]);
			pstmtObj.setInt(90, alarm[497]);
			pstmtObj.setInt(91, alarm[498]);
			pstmtObj.setInt(92, alarm[499]);
			pstmtObj.setInt(93, alarm[500]);
			pstmtObj.setInt(94, alarm[501]);
			pstmtObj.setInt(95, alarm[502]);
			pstmtObj.setInt(96, alarm[503]);
			pstmtObj.setInt(97, alarm[504]);
			pstmtObj.setInt(98, alarm[505]);
			pstmtObj.setInt(99, alarm[506]);
			pstmtObj.setInt(100, alarm[507]);
			pstmtObj.setInt(101, alarm[508]);
			pstmtObj.setInt(102, alarm[509]);
			pstmtObj.setInt(103, alarm[510]);
			pstmtObj.setInt(104, alarm[511]);
			pstmtObj.setInt(105, alarm[512]);
			pstmtObj.setInt(106, alarm[513]);
			pstmtObj.setInt(107, alarm[514]);
			pstmtObj.setInt(108, alarm[515]);
			pstmtObj.setInt(109, alarm[516]);
			pstmtObj.setInt(110, alarm[517]);
			pstmtObj.setInt(111, alarm[518]);
			pstmtObj.setInt(112, alarm[519]);
			pstmtObj.setInt(113, alarm[520]);
			pstmtObj.setInt(114, alarm[521]);
			pstmtObj.setInt(115, alarm[522]);
			pstmtObj.setInt(116, alarm[523]);
			pstmtObj.setInt(117, alarm[524]);
			pstmtObj.setInt(118, alarm[525]);
			pstmtObj.setInt(119, alarm[526]);
			pstmtObj.setInt(120, alarm[527]);
			pstmtObj.setInt(121, alarm[528]);
			pstmtObj.setInt(122, alarm[529]);
			pstmtObj.setInt(123, alarm[530]);
			pstmtObj.setInt(124, alarm[531]);
			pstmtObj.setInt(125, alarm[532]);
			pstmtObj.setInt(126, alarm[533]);
			pstmtObj.setInt(127, alarm[534]);
			pstmtObj.setInt(128, alarm[535]);
			pstmtObj.setInt(129, alarm[536]);
			pstmtObj.setInt(130, alarm[537]);
			pstmtObj.setInt(131, alarm[538]);
			pstmtObj.setInt(132, alarm[539]);
			pstmtObj.setInt(133, alarm[540]);
			pstmtObj.setInt(134, alarm[541]);
			pstmtObj.setInt(135, alarm[542]);
			pstmtObj.setInt(136, alarm[543]);
			pstmtObj.setInt(137, alarm[544]);
			pstmtObj.setInt(138, alarm[545]);
			pstmtObj.setInt(139, alarm[546]);
			pstmtObj.setInt(140, alarm[547]);
			pstmtObj.setInt(141, alarm[548]);
			pstmtObj.setInt(142, alarm[549]);
			pstmtObj.setInt(143, alarm[550]);
			pstmtObj.setInt(144, alarm[551]);
			pstmtObj.setInt(145, alarm[552]);
			pstmtObj.setInt(146, alarm[553]);
			pstmtObj.setInt(147, alarm[554]);
			pstmtObj.setInt(148, alarm[555]);
			pstmtObj.setInt(149, alarm[556]);
			pstmtObj.setInt(150, alarm[557]);
			pstmtObj.setInt(151, alarm[558]);
			pstmtObj.setInt(152, alarm[559]);
			pstmtObj.setInt(153, alarm[560]);
			pstmtObj.setInt(154, alarm[561]);
			pstmtObj.setInt(155, alarm[562]);
			pstmtObj.setInt(156, alarm[563]);
			pstmtObj.setInt(157, alarm[564]);
			pstmtObj.setInt(158, alarm[565]);
			pstmtObj.setInt(159, alarm[566]);
			pstmtObj.setInt(160, alarm[567]);
			pstmtObj.setInt(161, alarm[568]);
			pstmtObj.setInt(162, alarm[569]);
			pstmtObj.setInt(163, alarm[570]);
			pstmtObj.setInt(164, alarm[571]);
			pstmtObj.setInt(165, alarm[572]);
			pstmtObj.setInt(166, alarm[573]);
			pstmtObj.setInt(167, alarm[574]);
			pstmtObj.setInt(168, alarm[575]);
			pstmtObj.setInt(169, alarm[576]);
			pstmtObj.setInt(170, alarm[577]);
			pstmtObj.setInt(171, alarm[578]);
			pstmtObj.setInt(172, alarm[579]);
			pstmtObj.setInt(173, alarm[580]);
			pstmtObj.setInt(174, alarm[581]);
			pstmtObj.setInt(175, alarm[582]);
			pstmtObj.setInt(176, alarm[583]);
			pstmtObj.setInt(177, alarm[584]);
			pstmtObj.setInt(178, alarm[585]);
			pstmtObj.setInt(179, alarm[586]);
			pstmtObj.setInt(180, alarm[587]);
			pstmtObj.setInt(181, alarm[588]);
			pstmtObj.setInt(182, alarm[589]);
			pstmtObj.setInt(183, alarm[590]);
			pstmtObj.setInt(184, alarm[591]);
			pstmtObj.setInt(185, alarm[592]);
			pstmtObj.setInt(186, alarm[593]);
			pstmtObj.setInt(187, alarm[594]);
			pstmtObj.setInt(188, alarm[595]);
			pstmtObj.setInt(189, alarm[596]);
			pstmtObj.setInt(190, alarm[597]);
			pstmtObj.setInt(191, alarm[598]);
			pstmtObj.setInt(192, alarm[599]);
			pstmtObj.setInt(193, alarm[600]);
			pstmtObj.setInt(194, alarm[601]);
			pstmtObj.setInt(195, alarm[602]);
			pstmtObj.setInt(196, alarm[603]);
			pstmtObj.setInt(197, alarm[604]);
			pstmtObj.setInt(198, alarm[605]);
			pstmtObj.setInt(199, alarm[606]);
			pstmtObj.setInt(200, alarm[607]);
			pstmtObj.setInt(201, alarm[608]);
			pstmtObj.setInt(202, alarm[609]);
			pstmtObj.setInt(203, alarm[610]);
			pstmtObj.setInt(204, alarm[611]);
			pstmtObj.setInt(205, alarm[612]);
			pstmtObj.setInt(206, alarm[613]);
			pstmtObj.setInt(207, alarm[614]);
			pstmtObj.setInt(208, alarm[615]);
			pstmtObj.setInt(209, alarm[616]);
			pstmtObj.setInt(210, alarm[617]);
			pstmtObj.setInt(211, alarm[618]);
			pstmtObj.setInt(212, alarm[619]);
			pstmtObj.setInt(213, alarm[620]);
			pstmtObj.setInt(214, alarm[621]);
			pstmtObj.setInt(215, alarm[622]);
			pstmtObj.setInt(216, alarm[623]);
			pstmtObj.setInt(217, alarm[624]);
			pstmtObj.setInt(218, alarm[625]);
			pstmtObj.setInt(219, alarm[626]);
			pstmtObj.setInt(220, alarm[627]);
			pstmtObj.setInt(221, alarm[628]);
			pstmtObj.setInt(222, alarm[629]);
			pstmtObj.setInt(223, alarm[630]);
			pstmtObj.setInt(224, alarm[631]);
			pstmtObj.setInt(225, alarm[632]);
			pstmtObj.setInt(226, alarm[633]);
			pstmtObj.setInt(227, alarm[634]);
			pstmtObj.setInt(228, alarm[635]);
			pstmtObj.setInt(229, alarm[636]);
			pstmtObj.setInt(230, alarm[637]);
			pstmtObj.setInt(231, alarm[638]);
			pstmtObj.setInt(232, alarm[639]);
			pstmtObj.setInt(233, alarm[640]);
			pstmtObj.setInt(234, alarm[641]);
			pstmtObj.setInt(235, alarm[642]);
			pstmtObj.setInt(236, alarm[643]);
			pstmtObj.setInt(237, alarm[644]);
			pstmtObj.setInt(238, alarm[645]);
			pstmtObj.setInt(239, alarm[646]);
			pstmtObj.setInt(240, alarm[647]);
			pstmtObj.setInt(241, alarm[648]);
			pstmtObj.setInt(242, alarm[649]);
			pstmtObj.setInt(243, alarm[650]);
			pstmtObj.setInt(244, alarm[651]);
			pstmtObj.setInt(245, alarm[652]);
			pstmtObj.setInt(246, alarm[653]);
			pstmtObj.setInt(247, alarm[654]);
			pstmtObj.setInt(248, alarm[655]);
			pstmtObj.setInt(249, alarm[656]);
			pstmtObj.setInt(250, alarm[657]);
			pstmtObj.setInt(251, alarm[658]);
			pstmtObj.setInt(252, alarm[659]);
			pstmtObj.setInt(253, alarm[660]);
			pstmtObj.setInt(254, alarm[661]);
			pstmtObj.setInt(255, alarm[662]);
			pstmtObj.setInt(256, alarm[663]);
			pstmtObj.setInt(257, alarm[664]);
			pstmtObj.setInt(258, alarm[665]);
			pstmtObj.setInt(259, alarm[666]);
			pstmtObj.setInt(260, alarm[667]);
			pstmtObj.setInt(261, alarm[668]);
			pstmtObj.setInt(262, alarm[669]);
			pstmtObj.setInt(263, alarm[670]);
			pstmtObj.setInt(264, alarm[671]);
			pstmtObj.setInt(265, alarm[672]);
			pstmtObj.setInt(266, alarm[673]);
			pstmtObj.setInt(267, alarm[674]);
			pstmtObj.setInt(268, alarm[675]);
			pstmtObj.setInt(269, alarm[676]);
			pstmtObj.setInt(270, alarm[677]);
			pstmtObj.setInt(271, alarm[678]);
			pstmtObj.setInt(272, alarm[679]);
			pstmtObj.setInt(273, alarm[680]);
			pstmtObj.setInt(274, alarm[681]);
			pstmtObj.setInt(275, alarm[682]);
			pstmtObj.setInt(276, alarm[683]);
			pstmtObj.setInt(277, alarm[684]);
			pstmtObj.setInt(278, alarm[685]);
			pstmtObj.setInt(279, alarm[686]);
			pstmtObj.setInt(280, alarm[687]);
			pstmtObj.setInt(281, alarm[688]);
			pstmtObj.setInt(282, alarm[689]);
			pstmtObj.setInt(283, alarm[690]);
			pstmtObj.setInt(284, alarm[691]);
			pstmtObj.setInt(285, alarm[692]);
			pstmtObj.setInt(286, alarm[693]);
			pstmtObj.setInt(287, alarm[694]);
			pstmtObj.setInt(288, alarm[695]);
			pstmtObj.setInt(289, alarm[696]);
			pstmtObj.setInt(290, alarm[697]);
			pstmtObj.setInt(291, alarm[698]);
			pstmtObj.setInt(292, alarm[699]);
			pstmtObj.setInt(293, alarm[700]);
			pstmtObj.setInt(294, alarm[701]);
			pstmtObj.setInt(295, alarm[702]);
			pstmtObj.setInt(296, alarm[703]);
			pstmtObj.setInt(297, alarm[704]);
			pstmtObj.setInt(298, alarm[705]);
			pstmtObj.setInt(299, alarm[706]);
			pstmtObj.setInt(300, alarm[707]);
			pstmtObj.setInt(301, alarm[708]);
			pstmtObj.setInt(302, alarm[709]);
			pstmtObj.setInt(303, alarm[710]);
			pstmtObj.setInt(304, alarm[711]);
			pstmtObj.setInt(305, alarm[712]);
			pstmtObj.setInt(306, alarm[713]);
			pstmtObj.setInt(307, alarm[714]);
			pstmtObj.setInt(308, alarm[715]);
			pstmtObj.setInt(309, alarm[716]);
			pstmtObj.setInt(310, alarm[717]);
			pstmtObj.setInt(311, alarm[718]);
			pstmtObj.setInt(312, alarm[719]);
			pstmtObj.setInt(313, alarm[720]);
			pstmtObj.setInt(314, alarm[721]);
			pstmtObj.setInt(315, alarm[722]);
			pstmtObj.setInt(316, alarm[723]);
			pstmtObj.setInt(317, alarm[724]);
			pstmtObj.setInt(318, alarm[725]);
			pstmtObj.setInt(319, alarm[726]);
			pstmtObj.setInt(320, alarm[727]);
			pstmtObj.setInt(321, alarm[728]);
			pstmtObj.setInt(322, alarm[729]);
			pstmtObj.setInt(323, alarm[730]);
			pstmtObj.setInt(324, alarm[731]);
			pstmtObj.setInt(325, alarm[732]);
			pstmtObj.setInt(326, alarm[733]);
			pstmtObj.setInt(327, alarm[734]);
			pstmtObj.setInt(328, alarm[735]);
			pstmtObj.setInt(329, alarm[736]);
			pstmtObj.setInt(330, alarm[737]);
			pstmtObj.setInt(331, alarm[738]);
			pstmtObj.setInt(332, alarm[739]);
			pstmtObj.setInt(333, alarm[740]);
			pstmtObj.setInt(334, alarm[741]);
			pstmtObj.setInt(335, alarm[742]);
			pstmtObj.setInt(336, alarm[743]);
			pstmtObj.setInt(337, alarm[744]);
			pstmtObj.setInt(338, alarm[745]);
			pstmtObj.setInt(339, alarm[746]);
			pstmtObj.setInt(340, alarm[747]);
			pstmtObj.setInt(341, alarm[748]);
			pstmtObj.setInt(342, alarm[749]);
			pstmtObj.setInt(343, alarm[750]);
			pstmtObj.setInt(344, alarm[751]);
			pstmtObj.setInt(345, alarm[752]);
			pstmtObj.setInt(346, alarm[753]);
			pstmtObj.setInt(347, alarm[754]);
			pstmtObj.setInt(348, alarm[755]);
			pstmtObj.setInt(349, alarm[756]);
			pstmtObj.setInt(350, alarm[757]);
			pstmtObj.setInt(351, alarm[758]);
			pstmtObj.setInt(352, alarm[759]);
			pstmtObj.setInt(353, alarm[760]);
			pstmtObj.setInt(354, alarm[761]);
			pstmtObj.setInt(355, alarm[762]);
			pstmtObj.setInt(356, alarm[763]);
			pstmtObj.setInt(357, alarm[764]);
			pstmtObj.setInt(358, alarm[765]);
			pstmtObj.setInt(359, alarm[766]);
			pstmtObj.setInt(360, alarm[767]);
			pstmtObj.setInt(361, alarm[768]);
			pstmtObj.setInt(362, alarm[769]);
			pstmtObj.setInt(363, alarm[770]);
			pstmtObj.setInt(364, alarm[771]);
			pstmtObj.setInt(365, alarm[772]);
			pstmtObj.setInt(366, alarm[773]);
			pstmtObj.setInt(367, alarm[774]);
			pstmtObj.setInt(368, alarm[775]);
			pstmtObj.setInt(369, alarm[776]);
			pstmtObj.setInt(370, alarm[777]);
			pstmtObj.setInt(371, alarm[778]);
			pstmtObj.setInt(372, alarm[779]);
			pstmtObj.setInt(373, alarm[780]);
			pstmtObj.setInt(374, alarm[781]);
			pstmtObj.setInt(375, alarm[782]);
			pstmtObj.setInt(376, alarm[783]);
			pstmtObj.setInt(377, alarm[784]);
			pstmtObj.setInt(378, alarm[785]);
			pstmtObj.setInt(379, alarm[786]);
			pstmtObj.setInt(380, alarm[787]);
			pstmtObj.setInt(381, alarm[788]);
			pstmtObj.setInt(382, alarm[789]);
			pstmtObj.setInt(383, alarm[790]);
			pstmtObj.setInt(384, alarm[791]);
			pstmtObj.setInt(385, alarm[792]);
			pstmtObj.setInt(386, alarm[793]);
			pstmtObj.setInt(387, alarm[794]);
			pstmtObj.setInt(388, alarm[795]);
			pstmtObj.setInt(389, alarm[796]);
			pstmtObj.setInt(390, alarm[797]);
			pstmtObj.setInt(391, alarm[798]);
			pstmtObj.setInt(392, alarm[799]);
			pstmtObj.setInt(393, alarm[800]);
			pstmtObj.setInt(394, alarm[801]);
			pstmtObj.setInt(395, alarm[802]);
			pstmtObj.setInt(396, alarm[803]);
			pstmtObj.setInt(397, alarm[804]);
			pstmtObj.setInt(398, alarm[805]);
			pstmtObj.setInt(399, alarm[806]);
			pstmtObj.setInt(400, alarm[807]);
			pstmtObj.setInt(401, alarm[808]);
			pstmtObj.setInt(402, alarm[809]);
			pstmtObj.setInt(403, alarm[810]);
			pstmtObj.setInt(404, alarm[811]);
			pstmtObj.setInt(405, alarm[812]);
			pstmtObj.setInt(406, alarm[813]);
			pstmtObj.setInt(407, alarm[814]);
			pstmtObj.setInt(408, alarm[815]);
			pstmtObj.setInt(409, alarm[816]);
			pstmtObj.setInt(410, alarm[817]);
			pstmtObj.setInt(411, alarm[818]);
			pstmtObj.setInt(412, alarm[819]);
			pstmtObj.setInt(413, alarm[820]);
			pstmtObj.setInt(414, alarm[821]);
			pstmtObj.setInt(415, alarm[822]);
			pstmtObj.setInt(416, alarm[823]);
			pstmtObj.setInt(417, alarm[824]);
			pstmtObj.setInt(418, alarm[825]);
			pstmtObj.setInt(419, alarm[826]);
			pstmtObj.setInt(420, alarm[827]);
			pstmtObj.setInt(421, alarm[828]);
			pstmtObj.setInt(422, alarm[829]);
			pstmtObj.setInt(423, alarm[830]);
			pstmtObj.setInt(424, alarm[831]);
			pstmtObj.setInt(425, alarm[832]);
			pstmtObj.setInt(426, alarm[833]);
			pstmtObj.setInt(427, alarm[834]);
			pstmtObj.setInt(428, alarm[835]);
			pstmtObj.setInt(429, alarm[836]);
			pstmtObj.setInt(430, alarm[837]);
			pstmtObj.setInt(431, alarm[838]);
			pstmtObj.setInt(432, alarm[839]);
			pstmtObj.setInt(433, alarm[840]);
			pstmtObj.setInt(434, alarm[841]);
			pstmtObj.setInt(435, alarm[842]);
			pstmtObj.setInt(436, alarm[843]);
			pstmtObj.setInt(437, alarm[844]);
			pstmtObj.setInt(438, alarm[845]);
			pstmtObj.setInt(439, alarm[846]);
			pstmtObj.setInt(440, alarm[847]);
			pstmtObj.setInt(441, alarm[848]);
			pstmtObj.setInt(442, alarm[849]);
			pstmtObj.setInt(443, alarm[850]);
			pstmtObj.setInt(444, alarm[851]);
			pstmtObj.setInt(445, alarm[852]);
			pstmtObj.setInt(446, alarm[853]);
			pstmtObj.setInt(447, alarm[854]);
			pstmtObj.setInt(448, alarm[855]);
			pstmtObj.setInt(449, alarm[856]);
			pstmtObj.setInt(450, alarm[857]);
			pstmtObj.setInt(451, alarm[858]);
			pstmtObj.setInt(452, alarm[859]);
			pstmtObj.setInt(453, alarm[860]);
			pstmtObj.setInt(454, alarm[861]);
			pstmtObj.setInt(455, alarm[862]);
			pstmtObj.setInt(456, alarm[863]);
			pstmtObj.setInt(457, alarm[864]);
			pstmtObj.setInt(458, alarm[865]);
			pstmtObj.setInt(459, alarm[866]);
			pstmtObj.setInt(460, alarm[867]);
			pstmtObj.setInt(461, alarm[868]);
			pstmtObj.setInt(462, alarm[869]);
			pstmtObj.setInt(463, alarm[870]);
			pstmtObj.setInt(464, alarm[871]);
			pstmtObj.setInt(465, alarm[872]);
			pstmtObj.setInt(466, alarm[873]);
			pstmtObj.setInt(467, alarm[874]);
			pstmtObj.setInt(468, alarm[875]);
			pstmtObj.setInt(469, alarm[876]);
			pstmtObj.setInt(470, alarm[877]);
			pstmtObj.setInt(471, alarm[878]);
			pstmtObj.setInt(472, alarm[879]);
			pstmtObj.setInt(473, alarm[880]);
			pstmtObj.setInt(474, alarm[881]);
			pstmtObj.setInt(475, alarm[882]);
			pstmtObj.setInt(476, alarm[883]);
			pstmtObj.setInt(477, alarm[884]);
			pstmtObj.setInt(478, alarm[885]);
			pstmtObj.setInt(479, alarm[886]);
			pstmtObj.setInt(480, alarm[887]);
			pstmtObj.setInt(481, alarm[888]);
			pstmtObj.setInt(482, alarm[889]);
			pstmtObj.setInt(483, alarm[890]);
			pstmtObj.setInt(484, alarm[891]);
			pstmtObj.setInt(485, alarm[892]);
			pstmtObj.setInt(486, alarm[893]);
			pstmtObj.setInt(487, alarm[894]);
			pstmtObj.setInt(488, alarm[895]);
			pstmtObj.setInt(489, alarm[896]);
			pstmtObj.setInt(490, alarm[897]);
			pstmtObj.setInt(491, alarm[898]);
			pstmtObj.setInt(492, alarm[899]);
			pstmtObj.setInt(493, alarm[900]);
			pstmtObj.setInt(494, alarm[901]);
			pstmtObj.setInt(495, alarm[902]);
			pstmtObj.setInt(496, alarm[903]);
			pstmtObj.setInt(497, alarm[904]);
			pstmtObj.setInt(498, alarm[905]);
			pstmtObj.setInt(499, alarm[906]);
			pstmtObj.setInt(500, alarm[907]);
			pstmtObj.setInt(501, alarm[908]);
			pstmtObj.setInt(502, alarm[909]);
			pstmtObj.setInt(503, alarm[910]);
			pstmtObj.setInt(504, alarm[911]);
			pstmtObj.setInt(505, alarm[912]);
			pstmtObj.setInt(506, alarm[913]);
			pstmtObj.setInt(507, alarm[914]);
			pstmtObj.setInt(508, alarm[915]);
			pstmtObj.setInt(509, alarm[916]);
			pstmtObj.setInt(510, alarm[917]);
			pstmtObj.setInt(511, alarm[918]);
			pstmtObj.setInt(512, alarm[919]);
			pstmtObj.setInt(513, alarm[920]);
			pstmtObj.setInt(514, alarm[921]);
			pstmtObj.setInt(515, alarm[922]);
			pstmtObj.setInt(516, alarm[923]);
			pstmtObj.setInt(517, alarm[924]);
			pstmtObj.setInt(518, alarm[925]);
			pstmtObj.setInt(519, alarm[926]);
			pstmtObj.setInt(520, alarm[927]);
			pstmtObj.setInt(521, alarm[928]);
			pstmtObj.setInt(522, alarm[929]);
			pstmtObj.setInt(523, alarm[930]);
			pstmtObj.setInt(524, alarm[931]);
			pstmtObj.setInt(525, alarm[932]);
			pstmtObj.setInt(526, alarm[933]);
			pstmtObj.setInt(527, alarm[934]);
			pstmtObj.setInt(528, alarm[935]);
			pstmtObj.setInt(529, alarm[936]);
			pstmtObj.setInt(530, alarm[937]);
			pstmtObj.setInt(531, alarm[938]);
			pstmtObj.setInt(532, alarm[939]);
			pstmtObj.setInt(533, alarm[940]);
			pstmtObj.setInt(534, alarm[941]);
			pstmtObj.setInt(535, alarm[942]);
			pstmtObj.setInt(536, alarm[943]);
			pstmtObj.setInt(537, alarm[944]);
			pstmtObj.setInt(538, alarm[945]);
			pstmtObj.setInt(539, alarm[946]);
			pstmtObj.setInt(540, alarm[947]);
			pstmtObj.setInt(541, alarm[948]);
			pstmtObj.setInt(542, alarm[949]);
			pstmtObj.setInt(543, alarm[950]);
			pstmtObj.setInt(544, alarm[951]);
			pstmtObj.setInt(545, alarm[952]);
			pstmtObj.setInt(546, alarm[953]);
			pstmtObj.setInt(547, alarm[954]);
			pstmtObj.setInt(548, alarm[955]);
			pstmtObj.setInt(549, alarm[956]);
			pstmtObj.setInt(550, alarm[957]);
			pstmtObj.setInt(551, alarm[958]);
			pstmtObj.setInt(552, alarm[959]);
			pstmtObj.setInt(553, alarm[960]);
			pstmtObj.setInt(554, alarm[961]);
			pstmtObj.setInt(555, alarm[962]);
			pstmtObj.setInt(556, alarm[963]);
			pstmtObj.setInt(557, alarm[964]);
			pstmtObj.setInt(558, alarm[965]);
			pstmtObj.setInt(559, alarm[966]);
			pstmtObj.setInt(560, alarm[967]);
			pstmtObj.setInt(561, alarm[968]);
			pstmtObj.setInt(562, alarm[969]);
			pstmtObj.setInt(563, alarm[970]);
			pstmtObj.setInt(564, alarm[971]);
			pstmtObj.setInt(565, alarm[972]);
			pstmtObj.setInt(566, alarm[973]);
			pstmtObj.setInt(567, alarm[974]);
			pstmtObj.setInt(568, alarm[975]);
			pstmtObj.setInt(569, alarm[976]);
			pstmtObj.setInt(570, alarm[977]);
			pstmtObj.setInt(571, alarm[978]);
			pstmtObj.setInt(572, alarm[979]);
			pstmtObj.setInt(573, alarm[980]);
			pstmtObj.setInt(574, alarm[981]);
			pstmtObj.setInt(575, alarm[982]);
			pstmtObj.setInt(576, alarm[983]);
			pstmtObj.setInt(577, alarm[984]);
			pstmtObj.setInt(578, alarm[985]);
			pstmtObj.setInt(579, alarm[986]);
			pstmtObj.setInt(580, alarm[987]);
			pstmtObj.setInt(581, alarm[988]);
			pstmtObj.setInt(582, alarm[989]);
			pstmtObj.setInt(583, alarm[990]);
			pstmtObj.setInt(584, alarm[991]);
			pstmtObj.setInt(585, alarm[992]);
			pstmtObj.setInt(586, alarm[993]);
			pstmtObj.setInt(587, alarm[994]);
			pstmtObj.setInt(588, alarm[995]);
			pstmtObj.setInt(589, alarm[996]);
			pstmtObj.setInt(590, alarm[997]);
			pstmtObj.setInt(591, alarm[998]);
			pstmtObj.setInt(592, alarm[999]);
			pstmtObj.setInt(593, alarm[1000]);
			pstmtObj.setInt(594, alarm[1001]);
			pstmtObj.setInt(595, alarm[1002]);
			pstmtObj.setInt(596, alarm[1003]);
			pstmtObj.setInt(597, alarm[1004]);
			pstmtObj.setInt(598, alarm[1005]);
			pstmtObj.setInt(599, alarm[1006]);
			pstmtObj.setInt(600, alarm[1007]);
			pstmtObj.setInt(601, alarm[1008]);
			pstmtObj.setInt(602, alarm[1009]);
			pstmtObj.setInt(603, alarm[1010]);
			pstmtObj.setInt(604, alarm[1011]);
			pstmtObj.setInt(605, alarm[1012]);
			pstmtObj.setInt(606, alarm[1013]);
			pstmtObj.setInt(607, alarm[1014]);
			pstmtObj.setInt(608, alarm[1015]);
			pstmtObj.setInt(609, alarm[1016]);
			pstmtObj.setInt(610, alarm[1017]);
			pstmtObj.setInt(611, alarm[1018]);
			pstmtObj.setInt(612, alarm[1019]);
			pstmtObj.setInt(613, alarm[1020]);
			pstmtObj.setInt(614, alarm[1021]);
			pstmtObj.setInt(615, alarm[1022]);
			pstmtObj.setInt(616, alarm[1023]);
			pstmtObj.setInt(617, alarm[1024]);
			pstmtObj.setInt(618, alarm[1025]);
			pstmtObj.setInt(619, alarm[1026]);
			pstmtObj.setInt(620, alarm[1027]);
			pstmtObj.setInt(621, alarm[1028]);
			pstmtObj.setInt(622, alarm[1029]);
			pstmtObj.setInt(623, alarm[1030]);
			pstmtObj.setInt(624, alarm[1031]);
			pstmtObj.setInt(625, alarm[1032]);
			pstmtObj.setInt(626, alarm[1033]);
			pstmtObj.setInt(627, alarm[1034]);
			pstmtObj.setInt(628, alarm[1035]);
			pstmtObj.setInt(629, alarm[1036]);
			pstmtObj.setInt(630, alarm[1037]);
			pstmtObj.setInt(631, alarm[1038]);
			pstmtObj.setInt(632, alarm[1039]);
			pstmtObj.setInt(633, alarm[1040]);
			pstmtObj.setInt(634, alarm[1041]);
			pstmtObj.setInt(635, alarm[1042]);
			pstmtObj.setInt(636, alarm[1043]);
			pstmtObj.setInt(637, alarm[1044]);
			pstmtObj.setInt(638, alarm[1045]);
			pstmtObj.setInt(639, alarm[1046]);
			pstmtObj.setInt(640, alarm[1047]);
			pstmtObj.setInt(641, smSiteId);
			pstmtObj.setInt(642, smSitetypeid);
			pstmtObj.setString(643,siteId);
			pstmtObj.setLong(644,_recordtime);
			pstmtObj.setLong(645,dbCreationTime);
			pstmtObj.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error ("Failed to process trans_ltmalarmlaststatus", e);
		} finally {

			if(pstmtObj != null) {
				pstmtObj.close();
				pstmtObj = null;
			}
		}
	}

	public char[] processSysOutLoadStatus(String sysOutLoadStatus) {

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
		return alarmCharArray3;
	}

	public char[] processCommStatus(String commStatus) {

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
		return alarmCharArray2;
	}

	public char[] processStatusString(String statusString) {

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
		return alarmCharArray1;
	}

	public char[] processAlarmString(String alarmString) {

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
		return alarmCharArray;
	}

	public char[] processTwoByteAlarm(String twoByteStatus) {

		String twoByteStatusPreBin = new BigInteger(twoByteStatus, 16).toString(2);
		Integer twoByteLength = twoByteStatusPreBin.length();
		if (twoByteLength < 8) {
			for (int i = 0; i < 8 - twoByteLength; i++) {
				twoByteStatusPreBin = "0" + twoByteStatusPreBin;
			}
		}

		if(twoByteStatusPreBin.length()!=4) {

			int need = 4-twoByteStatusPreBin.length();
			for (int j = 1; j<=need; j++) {
				twoByteStatusPreBin = "0" + twoByteStatusPreBin;
			}
		}
		char[] alarmCharArray = new char[8];
		if(twoByteStatus.equalsIgnoreCase("00")) {

			alarmCharArray = "00000000".toCharArray();
		} else {
			alarmCharArray = twoByteStatusPreBin.toCharArray();
		}
		return alarmCharArray;
	}

	public char[] processThirtyTwo(String invStatus) {

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


	public int getMaxTTNumber(int siteId,String alarmPin, Connection connObj) throws Exception {

		PreparedStatement pstmtObj = null;
		int msgId=0;
		try {   
			// Performing Database Operation!
			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			pstmtObj = connObj.prepareStatement("Select max(alrTTNumber) from trans_alarmrecords");

			ResultSet rsObj = pstmtObj.executeQuery();

			while(rsObj.next()){
				msgId = rsObj.getInt(1);
			}

			rsObj.close();
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

	public char[] processInverterAlarm(String invStatus) {

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

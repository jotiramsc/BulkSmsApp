package com.sms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonLiterals {
	private CommonLiterals() {
	}

	public static final String DATE_FORMAT_YMD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final int DEFAULT_FREE_CREDIT = 500;
	public static final String DEFAULT_GROUP_NAME = "All Contacts";
	public static final boolean ACTIVE_STATUS = true;
	public static final boolean INACTIVE_STATUS = false;
	public static final String CONTACT_UPLOAD_FILE_NAME = "contact_temp";

	public static final String COUNTRY_CODES = "\r\n" + "-875,\r\n" + "-868,\r\n" + "-868,\r\n" + "-867,\r\n"
			+ "-807,\r\n" + "-783,\r\n" + "-766,\r\n" + "-757,\r\n" + "-683,\r\n" + "-670,\r\n" + "-669,\r\n"
			+ "-663,\r\n" + "-648,\r\n" + "-472,\r\n" + "-440,\r\n" + "-344,\r\n" + "-339,\r\n" + "-283,\r\n"
			+ "-267,\r\n" + "-267,\r\n" + "-263,\r\n" + "-245,\r\n" + "-241,\r\n" + "+1,\r\n" + "+1,\r\n" + "+7,\r\n"
			+ "+7,\r\n" + "+20,\r\n" + "+27,\r\n" + "+30,\r\n" + "+31,\r\n" + "+32,\r\n" + "+33,\r\n" + "+34,\r\n"
			+ "+36,\r\n" + "+39,\r\n" + "+40,\r\n" + "+41,\r\n" + "+43,\r\n" + "+44,\r\n" + "+45,\r\n" + "+46,\r\n"
			+ "+47,\r\n" + "+48,\r\n" + "+49,\r\n" + "+51,\r\n" + "+52,\r\n" + "+53,\r\n" + "+53,\r\n" + "+54,\r\n"
			+ "+55,\r\n" + "+56,\r\n" + "+56,\r\n" + "+57,\r\n" + "+58,\r\n" + "+60,\r\n" + "+61,\r\n" + "+61,\r\n"
			+ "+62,\r\n" + "+63,\r\n" + "+64,\r\n" + "+64,\r\n" + "+65,\r\n" + "+66,\r\n" + "+81,\r\n" + "+82,\r\n"
			+ "+84,\r\n" + "+86,\r\n" + "+90,\r\n" + "+91,\r\n" + "+92,\r\n" + "+93,\r\n" + "+94,\r\n" + "+95,\r\n"
			+ "+98,\r\n" + "+212,\r\n" + "+213,\r\n" + "+216,\r\n" + "+218,\r\n" + "+220,\r\n" + "+221,\r\n"
			+ "+222,\r\n" + "+223,\r\n" + "+224,\r\n" + "+225,\r\n" + "+226,\r\n" + "+227,\r\n" + "+228,\r\n"
			+ "+229,\r\n" + "+230,\r\n" + "+231,\r\n" + "+232,\r\n" + "+233,\r\n" + "+234,\r\n" + "+235,\r\n"
			+ "+236,\r\n" + "+237,\r\n" + "+238,\r\n" + "+239,\r\n" + "+240,\r\n" + "+241,\r\n" + "+242,\r\n"
			+ "+243,\r\n" + "+244,\r\n" + "+245,\r\n" + "+246,\r\n" + "+247,\r\n" + "+248,\r\n" + "+249,\r\n"
			+ "+250,\r\n" + "+251,\r\n" + "+252,\r\n" + "+253,\r\n" + "+254,\r\n" + "+255,\r\n" + "+255,\r\n"
			+ "+256,\r\n" + "+257,\r\n" + "+258,\r\n" + "+260,\r\n" + "+261,\r\n" + "+262,\r\n" + "+263,\r\n"
			+ "+264,\r\n" + "+265,\r\n" + "+266,\r\n" + "+267,\r\n" + "+268,\r\n" + "+269,\r\n" + "+269,\r\n"
			+ "+290,\r\n" + "+291,\r\n" + "+297,\r\n" + "+298,\r\n" + "+299,\r\n" + "+350,\r\n" + "+351,\r\n"
			+ "+352,\r\n" + "+353,\r\n" + "+354,\r\n" + "+355,\r\n" + "+356,\r\n" + "+357,\r\n" + "+358,\r\n"
			+ "+359,\r\n" + "+370,\r\n" + "+371,\r\n" + "+372,\r\n" + "+373,\r\n" + "+374,\r\n" + "+375,\r\n"
			+ "+376,\r\n" + "+377,\r\n" + "+378,\r\n" + "+380,\r\n" + "+381,\r\n" + "+382,\r\n" + "+385,\r\n"
			+ "+386,\r\n" + "+387,\r\n" + "+389,\r\n" + "+420,\r\n" + "+421,\r\n" + "+423,\r\n" + "+500,\r\n"
			+ "+501,\r\n" + "+502,\r\n" + "+503,\r\n" + "+504,\r\n" + "+505,\r\n" + "+506,\r\n" + "+507,\r\n"
			+ "+508,\r\n" + "+509,\r\n" + "+590,\r\n" + "+591,\r\n" + "+592,\r\n" + "+593,\r\n" + "+594,\r\n"
			+ "+595,\r\n" + "+596,\r\n" + "+596,\r\n" + "+597,\r\n" + "+598,\r\n" + "+599,\r\n" + "+599,\r\n"
			+ "+670,\r\n" + "+670,\r\n" + "+672,\r\n" + "+672,\r\n" + "+672,\r\n" + "+673,\r\n" + "+674,\r\n"
			+ "+675,\r\n" + "+676,\r\n" + "+677,\r\n" + "+678,\r\n" + "+679,\r\n" + "+680,\r\n" + "+681,\r\n"
			+ "+682,\r\n" + "+683,\r\n" + "+685,\r\n" + "+686,\r\n" + "+687,\r\n" + "+688,\r\n" + "+689,\r\n"
			+ "+690,\r\n" + "+691,\r\n" + "+692,\r\n" + "+800,\r\n" + "+808,\r\n" + "+808,\r\n" + "+850,\r\n"
			+ "+852,\r\n" + "+853,\r\n" + "+855,\r\n" + "+856,\r\n" + "+870,\r\n" + "+871,\r\n" + "+872,\r\n"
			+ "+873,\r\n" + "+874,\r\n" + "+878,\r\n" + "+880,\r\n" + "+881,\r\n" + "+886,\r\n" + "+960,\r\n"
			+ "+961,\r\n" + "+962,\r\n" + "+963,\r\n" + "+964,\r\n" + "+965,\r\n" + "+966,\r\n" + "+967,\r\n"
			+ "+968,\r\n" + "+970,\r\n" + "+971,\r\n" + "+972,\r\n" + "+973,\r\n" + "+974,\r\n" + "+975,\r\n"
			+ "+976,\r\n" + "+977,\r\n" + "+992,\r\n" + "+993,\r\n" + "+994,\r\n" + "+995,\r\n" + "+996,\r\n"
			+ "+998,\r\n" + "+5399,\r\n" + "+5399,\r\n" + "+88213,\r\n" + "+88216,\r\n" + "++1-787 or +1-939,\r\n"
			+ "++1-809 and +1-829,\r\n" + "++39, +379,\r\n" + "++8810, +8811,\r\n" + "++8812, +8813,\r\n"
			+ "++8816, +8817,\r\n" + "";

	public static boolean isValidMobile(String mobile, String contryCode) {
		Pattern p = Pattern.compile("^\\\\+(?:[0-9] ?){6,14}[0-9]$");

		mobile = contryCode + " " + mobile;
		Matcher m = p.matcher(mobile);
		return (m.find() && m.group().equals(mobile));
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static boolean isValidString(String str, boolean spConsider) {
		if (spConsider) {
			return str.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
		} else {
			return str.matches("[a-zA-z]+(['-][a-zA-Z]+)*");
		}
	}

	public static boolean isValidDate(String str, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			df.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean validateCountryCode(String contryCode) {
		return CommonLiterals.COUNTRY_CODES.contains(contryCode + ",");
	}

}

package com.tapacross.sns.crawler.daum.cafe.util;

/**
 * 숫자관련 유틸 클래스
 */
public class NumberUtil {
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	/**
	 * 레퍼런스 타입 변수의 값이 널일경우 프리미티브 타입으로 언박싱시 널포인터 오류가 나는것을
	 * 방지하기 위해 값이 널일경우 프리미티브 기본 값으로 리턴한다.
	 * @param val
	 * @return
	 */
	public static long toPrimitive(Long val) {
		if (val == null)
			return 0L;
		return val;
	}
	public static Double toPrimitive(Double val) {
		if (val == null)
			return 0D;
		return val;
	}
	
	/**
	 * 스트링타입의 값을 double 타입으로 형변환한다.
	 * 값이 비어있거나 널일경우 double 타입 기본값으로 리턴한다.
	 * @param val
	 * @return
	 */
	public static double stringToDouble(String val) {
		if (val == null || val.equals(""))
			return 0d;
		return Double.parseDouble(val);
	}
	
	/**
	 * 스트링타입의 값을 long 타입으로 형변환한다.
	 * 값이 비어있거나 널일경우 long 타입 기본값으로 리턴한다.
	 * @param val
	 * @return
	 */
	public static long stringToLong(String val) {
		if (val == null || val.equals(""))
			return 0l;
		return Long.parseLong(val);
	}
	
	/**
	 * 스트링타입의 값을 int 타입으로 형변환한다.
	 * 값이 비어있거나 널일경우 int 타입 기본값으로 리턴한다.
	 * @param val
	 * @return
	 */
	public static long stringToInt(String val) {
		if (val == null || val.equals(""))
			return 0;
		return Integer.parseInt(val);
	}
	
	/**
	 * !!유튜브 용
	 * 구독자 105만명, 1.01천명, 구독자 5.96만명
	 * 유튜브 구독자 수가  천, 만 단위일 경우 축약단위를 숫자형으로 바꾼다.
	 * @param countStr
	 * @return
	 */
	public static String formatNumber(String countStr) {
		final String THOUSAND_PROPLE = "천명"; //천
		final String THOUSAND = "천"; //천
		final String MILLION_PEOPLE = "만명"; //만
		final String MILLION = "만"; //만
		String formattedCount = "0";
		if (countStr.trim().length() > 1) {
			if (countStr.contains(THOUSAND) || countStr.contains(THOUSAND_PROPLE)) {
				formattedCount = Integer.toString((int)(Double.parseDouble(countStr.replace("구독자 ", "").replace("천명", "").replace("천", "")) * 1000));
//				formattedCount = Integer.toString(Integer.parseInt(countStr.replaceAll("[^0-9]", "")) * 1000);
			} else if (countStr.contains(MILLION) || countStr.contains(MILLION_PEOPLE)) {
				formattedCount = Integer.toString( (int)(Double.parseDouble(countStr.replace("구독자 ", "").replace("만명", "").replace("만", "")) * 10000));
			} else
				formattedCount = countStr.replaceAll("[^0-9]", "");
		}
		return formattedCount;
	}
	
	/**
	 * Hex -> 10진수 변환. 입력값이 널이거나 길이가 0이면 NumberFormatException이 발생한다.
	 * @param hex
	 * @return
	 */
	public static String getHexToDec(String hex) throws NumberFormatException {
		return String.valueOf(Long.parseLong(hex, 16));
	}

	/**
	 * 10진수 -> Hex 변환. 입력값이 널이거나 길이가 0이면 NumberFormatException이 발생한다.
	 * @param dec
	 * @return
	 */
	public static String getDecToHex(String dec) throws NumberFormatException {
		return Long.toHexString(Long.parseLong(dec)).toUpperCase();
	}
}

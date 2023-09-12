/*
 * Create: 2011.07.21
 */
package com.tapacross.sns.crawler.daum.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 구현후에 사용하지 않거나 사용을 제한해야 되는 메서드는 deprecate 표시를 해야한다.
 * @author Administrator
 */
public class DateUtil {
	
	public static final long	ONE_MINUTE_MILLISECOND	=	1000 * 60;
	public static final long	ONE_HOUR_MILLISECOND	=	ONE_MINUTE_MILLISECOND * 60;
	public static final long	ONE_DAY_MILLISECOND		=	ONE_HOUR_MILLISECOND * 24 ;
	public static final long	TWENTY_DAY_MILLISECOND	=	ONE_DAY_MILLISECOND * 20 ;
	
	public static final int		ONE_DAY					=	1;
	public static final int		ONE_WEEK				=	7;
	public static final int		ONE_MONTH				=	30;
	
	
	/**
	 * <pre>
	 * unused
	 * 호출 시간을 기준으로 yyyymmdd 형식의 문자열 반환
	 * </pre>
	 * @param delimiter 연 월 일 사이에 들어갈 구분자
	 * @return yyyymmdd 형식의 문자열 예)20110721
	 */
	@Deprecated
	public static String getTodayDateString(String delimiter){
		return String.format("%1$tY%2$s%1$tm%2$s%1$td", new Date(), delimiter);
	}
	
	/**
	 * 호출 시간을 기준으로 beforeOrAfterDays 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * @param beforeOrAfterDays 이전일 수 또는 경과일 수, 이전일은 음수로, 경과일은 양수
	 * @param delimiter 연 월 일 사이에 들어갈 구분자
	 * @return yyyymmdd 형식의 문자열 예)20110721
	 */
	public static String getDateString(int beforeOrAfterDays, String delimiter){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, beforeOrAfterDays);
		return String.format("%1$tY%2$s%1$tm%2$s%1$td", now.getTime(), delimiter);
	}
	
	/**
	 * date에 해당하는 yyyymmdd 문자열 반환
	 * @param date
	 * @return 
	 */
	public static String getDateString(Date date){
		return String.format("%tY%<tm%<td", date);
	}
	
	/**
	 * yyyymmdd 형식의 문자열을 받아 연,월,일을 delimeter로 구분하여 반환
	 * @param str 날짜 문자열
	 * @param delimeter 연월일 구분자
	 * @return 
	 */
	public static String formatDate(String str, String delimeter){
		if(str.length() < 8){
			return str;
		}
		
		return str.substring(0, 4) + delimeter + str.substring(4, 6) + delimeter + str.substring(6, 8);
	}
	
	/**
	 * baseDate를 기준으로 beforeOrAfterDays 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * 
	 * @param baseDate 기준일 (8자리 날짜 문자열 ex:20110920) 
	 * @param beforeOrAfterDays 이전일 수 또는 경과일 수
	 * @param delimiter 연 월 일 사이에 들어갈 구분자
	 * @return 
	 */
	public static String getDateString(String baseDate, int beforeOrAfterDays, String delimiter){
		return DateUtil.getDateString(DateUtil.getDatebyDayTime(baseDate).getTime(), beforeOrAfterDays, delimiter);
	}
	
	/**
	 * <pre>
	 * unused
	 * baseDate를 기준으로 beforeOrAfterDays 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * </pre>
	 * @param baseDate 기준일 (8자리 날짜 문자열 ex:yyyyMMdd) 
	 * @param beforeOrAfterDays 이전일 수 또는 경과일 수, 0>:이전 0<:이후
	 * @return beforeOrAfterDays를 더한 8자리 날짜 문자열 ex:20110921
	 */
	@Deprecated
	public static String getDateString(String baseDate, int beforeOrAfterDays){
		return DateUtil.getDateString(DateUtil.getDatebyDayTime(baseDate).getTime(), beforeOrAfterDays, "");
	}
	
	/**
	 * baseDate를 기준으로 beforeOrAfterDays 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * 
	 * @param baseDate 기준일
	 * @param beforeOrAfterDays 이전일 수 또는 경과일 수, +이후, -이전
	 * @param delimiter 연 월 일 사이에 들어갈 구분자
	 * @return yyyyMMdd
	 */
	public static String getDateString(Date baseDate, int beforeOrAfterDays, String delimiter){
		
		Calendar date = Calendar.getInstance();
		date.setTime(baseDate);
		date.add(Calendar.DATE, beforeOrAfterDays);
		return String.format("%1$tY%2$s%1$tm%2$s%1$td", date.getTime(), delimiter);
	}
	
	public static String getDateTimeString(Date baseDate, int beforeOrAfterDays, String delimiter, int field){
		
		Calendar date = Calendar.getInstance();
		date.setTime(baseDate);
//		date.set(Calendar.HOUR_OF_DAY, 0);
//		date.set(Calendar.MINUTE, 0);
//		date.set(Calendar.SECOND, 0);
		date.add(field, beforeOrAfterDays);
		return String.format("%1$tY%2$s%1$tm%2$s%1$td%1$tH%1$tM%1$tS", date.getTime(), delimiter);
	}

	/**
	 * 두 날짜의 차이 개월 수 반환
	 * @param dateA 20190130
	 * @param dateB 20190729
	 * @return 6
	 * @throws ParseException
	 */
	public static long getMonthBetweenAandB(String dateA, String dateB) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date startDate = sdf.parse(dateA.substring(0, 6));
		Date endDate = sdf.parse(dateB.substring(0, 6));
		
		long calDate = startDate.getTime() - endDate.getTime();
		long calDays = calDate / ONE_DAY_MILLISECOND / ONE_MONTH;
		return Math.abs(calDays);
	}
	
	/**
	 * 두 날짜의 차이 일 수 반환(A - B)
	 * @param dateA yyyyMMdd. 입력길이가 8자리를 넘어서더라도 8자리만 사용한다.
	 * @param dateB yyyyMMdd. 입력길이가 8자리를 넘어서더라도 8자리만 사용한다.
	 * @return 일수 차이(음수나 양수)
	 * @throws ParseException
	 */
	public static long dateBetweenAandB(String dateA, String dateB) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date startDate = sdf.parse(dateA.substring(0, 8));
		Date endDate = sdf.parse(dateB.substring(0, 8));
		
		long calDate = startDate.getTime() - endDate.getTime();
		long calDays = calDate / ONE_DAY_MILLISECOND;
		return Math.abs(calDays);
	}
	
	/**
	 * 두 날짜의 시간차 반환
	 * @param firstDate 2019101017
	 * @param SecondDate 2019100907
	 * @return 34
	 * @throws ParseException
	 */
	public static long hourBetweenAandB(String firstDate, String SecondDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		Date startDate = sdf.parse(firstDate.substring(0, 10));
		Date endDate = sdf.parse(SecondDate.substring(0, 10));
		
		long calDate = startDate.getTime() - endDate.getTime();
		long calhour = calDate / ONE_HOUR_MILLISECOND;
		return Math.abs(calhour);
	}
	
	/**
	 * 두 날짜의 차이 분(Min) 반환
	 * @param firstDate 20200410174552
	 * @param SecondDate 20200410174646
	 * @return 1
	 * @throws ParseException
	 */
	public static long minBetweenAandB(String firstDate, String SecondDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date startDate = sdf.parse(firstDate.substring(0, 12));
		Date endDate = sdf.parse(SecondDate.substring(0, 12));
		
		long calDate = startDate.getTime() - endDate.getTime();
		long calhour = calDate / ONE_MINUTE_MILLISECOND;
		return Math.abs(calhour);
	}
	
	/**
	 * <pre>
	 * unused
	 * / 나 . 로 날짜 표현이 포맷된 문자열을 숫자로만 구성된 문자열로 반환
	 * </pre>
	 * @param dateStr 포맷을 가진 날짜 문자열
	 * @return 숫자로만 구성되어 있는 날짜 문자열
	 */
	@Deprecated
	public static String getDateNumber(String dateStr){
		return dateStr.replaceAll("[\\./\\s]+", "");
	}
	
	/**
	 * <pre>
	 * unused
	 * 8자리 날짜 문자열(yyyymm)을 Calendar 객체로 반환. 시분초는 현재시점을 따름
	 * </pre>
	 * @param dateStr yyyMM
	 * @return 
	 */
	@Deprecated
	public static Calendar getYMDate(String dateStr) {

		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(4, 6));
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, year);
		time.set(Calendar.MONTH, month - 1);
		return time;
	}
	
	/**
	 * 입력날짜(yyyyMmdd)를 calendar 형태로 변환한다. 시분초는 0으로 초기화한다.
	 * @param dateStr yyyyMMdd
	 * @return time
	 */
	public static Calendar getDate(String dateStr){	
		int			year	= Integer.parseInt(dateStr.substring(0, 4), 10);
		int			month	= Integer.parseInt(dateStr.substring(4, 6), 10);
		int			date	= Integer.parseInt(dateStr.substring(6, 8), 10);
		Calendar	time	= Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		
		time.set(year, (month - 1), date);
		return time;
	}
	
	/**
	 * 14자리 문자열(yyyymmddHHMMSS)을 Calendar 객체로 반환
	 * @param dateStr yyyMMddHHmmss
	 * @return 
	 */
	public static Calendar getDateTime(String dateStr){
		int			year	= Integer.parseInt(dateStr.substring(0, 4), 10);
		int			month	= Integer.parseInt(dateStr.substring(4, 6), 10);
		int			date	= Integer.parseInt(dateStr.substring(6, 8), 10);
		int			hour	= Integer.parseInt(dateStr.substring(8, 10), 10);
		int			min		= Integer.parseInt(dateStr.substring(10, 12), 10);
		int			sec		= Integer.parseInt(dateStr.substring(12), 10);
		
		Calendar	time	= Calendar.getInstance();
		time.set(year, (month - 1), date, hour, min, sec);
		return time;
	}
	
	/**
	 * <pre>
	 * not used
	 * 14자리 날짜 문자열의 daytime 반환
	 * </pre>
	 * @deprecated
	 * @param dateStr
	 * @return 
	 */
	@Deprecated
	public static String getDayTime(String dateStr){	
		return dateStr.substring(0, 8);
	}
	
	/**
	 * 8자리 날짜 문자열을 Date 객체로 반환. 시분초는 현재시점을 따름
	 * @param dateStr
	 * @return 
	 */
	public static Calendar getDatebyDayTime(String dateStr){	
		
		int			year	= Integer.parseInt(dateStr.substring(0, 4), 10);
		int			month	= Integer.parseInt(dateStr.substring(4, 6), 10);
		int			date	= Integer.parseInt(dateStr.substring(6, 8), 10);
		
		Calendar	time	= Calendar.getInstance();
		
		time.set(year, (month - 1), date);
		return time;
	}
	
	/**
	 * dateFrom과 dateTo 사이의 YYYYMMDD 형식의 일자 목록을 리스트로 반환
	 * 정렬순서는 오래된 것부터 최신순이다.
	 * @param dateFrom yyyyMMdd
	 * @param dateTo yyyyMMdd
	 * @return 
	 */
	public static List<String> getDayTimeArrange(String dateFrom, String dateTo){
		List<String> list = new ArrayList<>();
		
		Calendar from	=  getDatebyDayTime(dateFrom);
		Calendar to		=  getDatebyDayTime(dateTo);
		
		while(from.compareTo(to) <= 0){
			list.add(getDateString(from.getTime(), 0 , ""));
			from.add(Calendar.DATE, 1);
		}
		
		return list;
	}
	
	/**
	 * dateFrom과 dateTo 사이의 YYYYMMDD 형식의 일자 목록을 field 단위로 값을 계산하여 리스트로 반환
	 * 시작일자와 종료일자를 포함한다. field 기준으로 오른쪽의 값은 0으로 초기화된다.
	 * 시간, 분, 초 단위는 값이 0부터 시작한다.
	 * @param dateFrom yyyyMMd
	 * @param dateTo yyyyMMdd
	 * @param field 기준 단위 Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND
	 * @return yyyyMMddHHmmss
	 */
	public static List<String> getDayTimeArrange(String dateFrom, String dateTo, int field){
		List<String> list = new ArrayList<>();
		
		Calendar from	=  getDatebyDayTime(dateFrom);
		Calendar to		=  getDatebyDayTime(dateTo);
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		to.set(Calendar.HOUR_OF_DAY, 0);
		to.set(Calendar.MINUTE, 0);
		to.set(Calendar.SECOND, 0);
		
		while(from.compareTo(to) <= 0){
			list.add(getDateTimeString(from.getTime(), 0 , "", field));
			from.add(field, 1);
		}
		
		return list;
	}
	
	
	/**
	 * unused
	 * 생성 시간을 현재 시간 기준으로 경과 시간 방식으로 표시한 거 전달
	 * @return 
	 */
	@Deprecated
	public static String getFormedTime(String dateStr){
		
		String formedTimeStr = null;
		
		if(dateStr == null){
			return "";
		}
		
		if(dateStr.length() != 14){
			return dateStr;
		}
		
		try{
			Calendar	cNow	= Calendar.getInstance();
			Calendar	cDate	= DateUtil.getDateTime(dateStr);
			
			long		cMilli	= cDate.getTimeInMillis();
			long		now		= cNow.getTimeInMillis();
			long		diff	= now - cMilli;
			
			if(diff < ONE_HOUR_MILLISECOND){
				formedTimeStr = String.format("%d분 전 (%tm.%<td %<tR)", (diff/ONE_MINUTE_MILLISECOND), cDate);
			} 
			else if(diff < ONE_DAY_MILLISECOND){
				formedTimeStr = String.format("%d시간 전 (%tm.%<td %<tR)", (diff/ONE_HOUR_MILLISECOND), cDate);
			}
			else{
				formedTimeStr = String.format("%d일 전 (%tm.%<td %<tR)", getNormalizedDayDiff(cDate, cNow) , cDate);
			}
			
			
		} catch(NumberFormatException e){
			formedTimeStr = dateStr;
		}
		
		
		return formedTimeStr;
	}
	
	/**
	 * unused
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@Deprecated
	public static int getNormalizedDayDiff(Calendar dateFrom, Calendar dateTo){
			
		String cDateFromStr = DateUtil.getDateString(dateFrom.getTime());
		String cDateToStr = DateUtil.getDateString(dateTo.getTime());

		Calendar cNormalizedNow = getDate(cDateToStr);
		Calendar cNormalizedDate = getDate(cDateFromStr);
		
		return (int)((cNormalizedNow.getTimeInMillis() - cNormalizedDate.getTimeInMillis()) / ONE_DAY_MILLISECOND);
		
	}

	/**
	 * unused
	 * 생성 시간을 yyyy-mm-dd HH:MM:SS 형식으로 표현한 문자열 반환
	 * @return 
	 */
	@Deprecated
	public static String getGeneralFormedTime(String dateStr){
		if(dateStr == null){
			return "";
		}
		
		if(dateStr.length() == 8){
			return  dateStr.substring(0, 4) +"-" + dateStr.substring(4,6) + "-" + dateStr.substring(6,8);
		}
		
		
		if(dateStr.length() == 14){
			return dateStr.substring(0, 4) +"-" + dateStr.substring(4,6) + "-" + dateStr.substring(6,8) + " "
				+ dateStr.substring(8,10) + ":" + dateStr.substring(10,12)+":"+dateStr.substring(12);
		}
		return dateStr;		
	}
	
	public static int getDayOfWeek(String dayTime){
		Calendar cal = getDatebyDayTime(dayTime);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * unused
	 * @param dayTime
	 * @return
	 */
	@Deprecated
	public static String getWeekOfMonthDisplay(String dayTime){
		Calendar cal = getDatebyDayTime(dayTime);
		int nDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(cal.DATE, 1-nDayOfWeek);
		
		return String.format("%d월 %d주", cal.get(Calendar.MONTH)+1, cal.get(Calendar.WEEK_OF_MONTH));
	}
	
	public static String[] getDayOfWeekListDisplay(){
		return new String[]
				{
					"일요일",
					"월요일",
					"화요일",
					"수요일",
					"목요일",
					"금요일",
					"토요일"
				};
	}

	/**
	 * unused
	 * @return
	 */
	@Deprecated
	public static String[] get24HourListDisplay(){
		return new String[]
				{
					"00",
					"01",
					"02",
					"03",
					"04",
					"05",
					"06",
					"07",
					"08",
					"09",
					"10",
					"11",
					"12",
					"13",
					"14",
					"15",
					"16",
					"17",
					"18",
					"19",
					"20",
					"21",
					"22",
					"23"	};
	}	
	
	/**
	 * unused
	 * date 에 대해 "0월 0주" 문자열 반환
	 * @param date
	 * @return 
	 */
	@Deprecated
	public static String getMonthWeekLabel(Calendar date){
		return (date.get(Calendar.MONTH) + 1) + "월 " + date.get(Calendar.WEEK_OF_MONTH) + "주";
	}
}

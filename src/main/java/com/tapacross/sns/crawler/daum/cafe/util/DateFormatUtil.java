package com.tapacross.sns.crawler.daum.cafe.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtil {
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final String PATTERN_RFC_1051 = "EEE MMM d HH:mm:ss z yyyy";

    
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	    
    @SuppressWarnings("rawtypes")
	private static final Collection DEFAULT_PATTERNS = Arrays.asList(
            new String[] { PATTERN_ASCTIME, PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_RFC_1051 } );
    
    /**
     * 입력받은 date 객체를 포맷에 맞게 리턴한다. 
     * thread unsafe
     * @param date 
     * @param pattern yyyyMMdd
     * @return
     */
    public static String format(Date date, String pattern) {
    	if (pattern.equals("yyyyMMdd")) 
    		return sdf.format(date.getTime());
    	else {
    		return new SimpleDateFormat(pattern).format(date.getTime());
    	}
    }
    
	/**
	 * 현재일자에서 일수를 더하거나 뺀다.
	 * @param day 0> : before, 0< : after
	 * @return yyyyMMddHHmmss
	 */
	@SuppressWarnings("static-access")
	public static String beforDay( int day ) { 
		Calendar cal = Calendar.getInstance ( );
		cal.add ( cal.DAY_OF_YEAR, - day ); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format( cal.getTime() );

	}
	
	/**
	 * 시작일자와 종료일자 사이의 일별 리스트를 리턴한다.
	 * @param startDate yyyyMMdd
	 * @param endDate yyyyMMdd
	 * @return yyyyMMdd
	 */
	@Deprecated
	public static ArrayList<String> getDaysFromAToB( String startDate, String endDate ) {

		ArrayList<String> days = new ArrayList<String>();
		int gap = getDiffDayCount( startDate, endDate ) ;
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(sdf.parse(endDate));
		} catch (Exception e) {
			
		}
		cal.add(Calendar.DATE, 1);
		
		for( int i = gap; i > -1; i-- ) {  
			cal.add(Calendar.DATE, -1);				
			days.add( sdf.format(cal.getTime()) );
		}
		
		
		return days; 
	}
	
	/**
	 * 
	 * @param fromDate yyyyMMdd
	 * @param toDate yyyyMMdd
	 * @return
	 */
	public static int getDiffDayCount(String fromDate, String toDate) {
		
		try {
			return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}	
	
	/**
	 * @param 2010-10-18T02:34:11+0000
	 * @return 20101018113411
	 * @throws ParseException
	 */
	public static String getDateFromFacebookType( String date ) throws ParseException { 
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        parser.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = parser.parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(utcDate);
	}

	/**
	 * 
	 * @param timeStamp 1302137584000
	 * @return
	 */
	public static Date getDateFromTimeStamp( String timeStamp ) {
		return new Date(Long.valueOf(timeStamp));
	} 
	
	/**
	 * 밀리세컨트까지 포함된 타임스탬프 계산
	 * @param timeStampe UTC 기준으로 13자리의 숫자(1660566934)
	 * @return 14자리의 숫자(19700120141606)
	 * @throws ParseException
	 */
	public static String getTextDateFromTimeStamp( String timeStampe) throws ParseException { 
		Date date = getDateFromTimeStamp( timeStampe );
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}
	
	/**
	 * 밀리세컨트가 제외된 타임스탬프 계산
	 * @param timeStampe UTC 기준으로 10자리의 숫자(1660566934000)
	 * @return 14자리의 숫자(20220120141606)
	 * @throws ParseException
	 */
	public static String getTextDateFromTimeStamp2( String timeStampe ) throws ParseException { 
		Date date = getDateFromTimeStamp( timeStampe + "000");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}

	/**
	 * 날짜 포멧이 14자리가 아닌경우 부족한 자릿 수 만큼 0 추가
	 * 파라미터 date 가 14자리 보다 클 경우, "" 일 경우  null return
	 * 1902071514 처럼 년도 앞자리가 빠질경우 체크 못함.
	 * @param date 201902071514
	 * @return date 20190207151400
	 */
	public static String dateIntegration(String date) {
		if(date.length() > 14 || date.length() == 0 || 
				date.equals(" ")) {
			return null;
		}
		while(date.length() < 14) {
			date += "0";
		}
		try {
			sdf2.setLenient(false);
			sdf2.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static String getChangeDateToFormat( String date ){
		SimpleDateFormat formatter_one = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String ChangeDate = formatter_two.format( frmTime );
		
		return ChangeDate;
	}
	
	/**
	 * 영문날짜 타입으로 된 문자열을 14자리 숫자로 이루어진 문자열로 리턴. 미국시간기준으로 계산
	 * @param date input date
	 * @param pattern input date pattern
	 * @return 20190220125510
	 * @throws ParseException
	 */
	public static String getChangeDateToFormat( String date, String toPattern, String fromPattern ) throws ParseException { 
		SimpleDateFormat parser = new SimpleDateFormat(toPattern, Locale.ENGLISH);
		SimpleDateFormat formatter = new SimpleDateFormat(fromPattern);
		Date formatDate = parser.parse(date);
		return formatter.format(formatDate);
	}
	
	/**
	 * 영문날짜 타입으로 된 문자열을 14자리 숫자로 이루어진 문자열로 리턴. 한국시간기준으로 계산
	 * @param date input date
	 * @param pattern input date pattern
	 * @return 20190220125510
	 * @throws ParseException
	 */
	public static String changeUtcToLocaltime( String date, String fromPattern, String toPattern ) throws ParseException {
	    TimeZone tz = TimeZone.getDefault();
		SimpleDateFormat parser = new SimpleDateFormat(fromPattern);
		SimpleDateFormat formatter = new SimpleDateFormat(toPattern);
		Date formatDate = parser.parse(date);
		
//        Date parseDate = sdf.parse(formatter.format(date));
        long milliseconds = formatDate.getTime();
        int offset = tz.getOffset(milliseconds);
        String localTime = formatter.format(milliseconds + offset);
        return localTime.replace("+0000", "");
	}
	
	@Deprecated
	public static String getChangeDateToFormatAtDatasift( String date ){
		SimpleDateFormat formatter_one = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter_one.setTimeZone(TimeZone.getTimeZone("KST"));
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String ChangeDate = formatter_two.format( frmTime );
		
		return ChangeDate;
	}
	
	@Deprecated
	public static String getChangeDateToFormatAtFacebook( String date ){
		SimpleDateFormat formatter_one = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String ChangeDate = formatter_two.format( frmTime );
		
		return ChangeDate;
	}
	
	public static String getUnixChangeDateToFormat(){
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		return dTime;
	}
	
	/**
	 * 14자리 문자열(yyyymmddHHMMSS)을 Calendar 객체로 반환
	 * @param dateStr 
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
	 * baseDate 기준으로 beforeOrAfterMonth 만큼의 이전 또는 경과 날짜 문자열 반환
	 * 
	 * @param cal 기준일 calendar
	 * @param beforeOrAfterMonth 이전 개월수(-)또는 경과 개월수(+)
	 * @param format
	 * @return format
	 */
	public static String getDateString(Calendar cal, int beforeOrAfterMonth, 
			String format){
		
		cal.add(Calendar.MONTH, beforeOrAfterMonth);
		SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA );
		String dTime = formatter.format ( cal.getTime() );
		return dTime;
	}
	
	/**
	 * baseDate를 기준으로 beforeOrAfterMonth 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * 
	 * @param baseDate 기준일 14자리 yyyyMMddHHmmss
	 * @param beforeOrAfterMonth 이전달 또는 경과달, -이전달, +경과달
	 * @param format
	 * @return 
	 */
	public static String getMonthString(String dateStr, int beforeOrAfterMonth, String format){
		Calendar cal = getDateTime(dateStr);
		cal.add(Calendar.MONTH, beforeOrAfterMonth);
		SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA );
		
		String dTime = formatter.format ( cal.getTime() );
		return dTime;
	}
	
	/**
	 * baseDate를 기준으로 beforeOrAfterMonth 만큼의 이전일 또는 경과일 날짜 문자열 반환
	 * 
	 * @param baseDate 기준일 14자리 yyyyMMddHHmmss
	 * @param beforeOrAfterDay
	 * @param format
	 * @return 
	 */
	public static String getDayString(String dateStr, int beforeOrAfterDay, String format){
		Calendar cal = getDateTime(dateStr);
		cal.add(Calendar.DATE, beforeOrAfterDay);
		SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA );
		
		String dTime = formatter.format ( cal.getTime() );
		return dTime;
	}
	
	/**
	 * @param date yyyy-MM-dd'T'HH:mm:ss+0000
	 * @return changeDate yyyyMmddHHmmss
	 * @throws ParseException
	 */
	public static String getDateFromUnixType( String date ) throws ParseException { 
		SimpleDateFormat formatter_one = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		//formatter_one.setTimeZone(TimeZone.getTimeZone("KST"));
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String ChangeDate = formatter_two.format( frmTime );
		
		return ChangeDate;
	}
	
	
	
	// 변경 ID : DateFormatUtil.java_20140317_01
	// 관련문서 : 프로그램변경보고서_DateFormatUtil.java_20140317_01.doc
	// 프로그램변경 DateFormatUtil.java_20140317_01 Start
	
	public static final long HOUR = 3600*1000; // in milli-seconds.
	
	@Deprecated
	public static String getChangeDateToNewFormat( String date ){
		SimpleDateFormat formatter_one = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String frm = String.valueOf(frmTime.getTime());

		long unixTime = Long.valueOf(frm);
		//Date changeDate = new Date(unixTime + 9 * HOUR);
		
		String ut = formatter_two.format(unixTime);
		
		return ut;
	}
	
	
	/**
	 * unused
	 * @param date
	 * @return
	 */
	@Deprecated
	public static String getChangeDateToNewFormat_FaceBook( String date ){
		SimpleDateFormat formatter_one = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
		SimpleDateFormat formatter_two = new SimpleDateFormat("yyyyMMddHHmmss");
		
		ParsePosition pos = new ParsePosition( 0 );
		Date frmTime = formatter_one.parse(date, pos);
		String frm = String.valueOf(frmTime.getTime());

		long unixTime = Long.valueOf(frm);
		Date changeDate = new Date(unixTime + 8 * HOUR);
		
		String ut = formatter_two.format(changeDate);
		
		return ut;
	}
	
	/**
	 * 유튜브 date type을 yyyyMMddHHmmss 로 변경
	 * @param beforeDate 1초 전, 1분 전, 1시간 전, 1일 전, 1개월 전 ...
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateFromYoutube(String beforeDate) {
		Calendar cal = Calendar.getInstance();
		if(beforeDate.contains("방금전")) {
			cal.add(Calendar.MINUTE, (1 * -1));
			return sdf2.format(cal.getTime());
		}
		int amount = Integer.parseInt(beforeDate.replaceAll("[^0-9]", ""));
		if(beforeDate.contains("초 전")) {
			cal.add(Calendar.SECOND, (amount * -1));
		} else if(beforeDate.contains("분 전")) {
			cal.add(Calendar.MINUTE, (amount * -1));
		} else if(beforeDate.contains("시간 전")) {
			cal.add(Calendar.HOUR_OF_DAY, (amount * -1));
		} else if(beforeDate.contains("일 전")) {
			cal.add(Calendar.DAY_OF_MONTH, (amount * -1));
		} else if(beforeDate.contains("주 전")) {
			cal.add(Calendar.WEEK_OF_MONTH, (amount * -1));
		} else if(beforeDate.contains("개월 전")) {
			cal.add(Calendar.MONTH, (amount * -1));
		} 
//		else if(beforeDate.contains("년 전")) {
//			cal.add(Calendar.YEAR, (amount * -1));
//		}
		else {
			return null;
		}
		return sdf2.format(cal.getTime());
	}
	// 프로그램변경 DateFormatUtil.java_20140317_01 End	
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lissome
 */
public class URLUtil {
	final static String  HTTP = "http://";
	final static String  HTTPS = "https://";
	
	/**
	 * url의 root url을 돌려준다.
	 * url이 http로 시작하지 않거나 잘못된 url일 경우 null을 리턴
	 * @param url 
	 * @return 
	 */
	public static String extractRootURL(String url)
	{
		if(url == null) return url;
		String rootURL = url.toLowerCase();
		
		int sIdx = -1;
		int pos = 0;
		sIdx = rootURL.indexOf(HTTP);
		if(sIdx < 0){
			sIdx = rootURL.indexOf(HTTPS);
		}
		if(sIdx >= 0){
			pos = sIdx + HTTPS.length();
		}
		else{
			return null;
		}
		
		if(pos >= url.length()) return null;
		
		int eIdx = url.indexOf('/', pos);
		if(eIdx >= 0){
			
			rootURL = rootURL.substring(sIdx, eIdx);
		}
		return rootURL;
	}
	
	/**
	 * url 중에서 입력한 구분자 다음의 값부터 마지막까지 잘라 리턴한다.
	 * 쿼리 스트링이 존재할 경우 쿼리스트링은 제외한다.
	 * @param url
	 * @param separator
	 * @return
	 */
	public static String extractLastResource(String url, String separator) {
		if(url == null) return url;
		int index = url.lastIndexOf(separator);
		String result = "";
		if (url.indexOf("?") == -1) {
			result = url.substring(index + 1, url.length()); 
		} else {
			int queryStartIndex = url.indexOf("?");
			result = url.substring(index + 1, queryStartIndex);
		}
		
		return result;
	}
	
	
	/**
	 * url에서 파라미터 정보를 키 값 형태로 추출하여 리턴한다.
	 * url 파싱시 키와 매핑되는 값이 없을경우 널을 할당한다.
	 * 
	 * url ex) list.html?c1=41932&c2=sc2, http://www.a.com/list.html?c1=41932&c2=sc2 
	 * 
	 * @param url
	 * @return
	 */
	public static Map<String, String> extractQueryString(String url) {
		int queryStringStartIndex = url.indexOf("?");
		String[] queries = url.substring(queryStringStartIndex + 1,
				url.length()).split("&");
		Map<String, String> queryMap = new HashMap<String, String>();
		for (String query : queries) {
			String value = query.split("=").length > 1 ? query.split("=")[1] : null;
			queryMap.put(query.split("=")[0], value);
		}
		return queryMap;
	}
	
	/**
	 * 기존 url에 쿼리 파라미터를 붙인다.
	 * 기존에 쿼리 파라미터가 존재할 경우와 존재하지 않을 경우를 구분해야 한다. 
	 * 미구현
	 * @param url
	 * @param param
	 * @param value
	 */
	public static String concatQueryString(String url, String param, String value) {
		return null;
	}
	
	/**
	 * 호출한 서버의 공인 ip를 리턴한다. 호출시 에러가 발생할 경우 널을 리턴한다. ip를 체크하기 위해 아마존 aws 웹사이트를
	 * 이용하며 통신이 실패할 경우 UnknownHostException이 발생한다.
	 * @return
	 * @throws IOException
	 */
	public static String getExternalHost() throws IOException {
		String str = null;
	    URL connection;
		connection = new URL("http://checkip.amazonaws.com/");
		URLConnection con = connection.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		str = reader.readLine();

	    return str;
	}
	
	/**
	 * URL에 쿼리스트링을 제거한 순수 URL을 리턴한다
	 * url의 ?이후부분을 정규식을 이용하여 제거하고 반환한다.
	 * @param url https://n.news.naver.com/mnews/article/236/0000236562?rc=N&ntype=RANKING&sid=107
	 * @return localeUrl https://n.news.naver.com/mnews/article/236/0000236562
	 */
	public static String getRemoveQueryStringUrl(String url) {
		String localeUrl = null;
		localeUrl = url.replaceAll("\\?.+", "");
		
		return localeUrl;
	}
}

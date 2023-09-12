package com.tapacross.sns.crawler.daum.cafe.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class PatternUtil {
	
	
	public static Pattern getBlogPattern( String addressCrawlType ){		
		
		Pattern regx = null;
		HashMap pattern = new HashMap();		
		pattern.put("naver", "http://blog.naver.com/PostView.nhn[?]blogId=([a-zA-Z0-9_-].*)[&]amp;logNo[=]([0-9].*)");
		pattern.put("tistory", "([http://][a-zA-Z0-9_-].*[.tistory.com].*[/])([0-9].*)");
		pattern.put("cyworld", "([http://www.cyworld.com/][a-zA-Z0-9_-].*[/])([0-9].*)");
		pattern.put("daum", "([http://blog.daum.net/][a-zA-Z0-9_-].*[/])([0-9].*)");
		pattern.put("egloos", "([http://][a-zA-Z0-9_-].*[.egloos.com].*[/])([0-9].*)");
		
		
		
		//([가-힣★●! ~._-a-zA-Z0-9]*)
		Iterator it = pattern.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();			
			try{
				if( addressCrawlType.equalsIgnoreCase( String.valueOf(e.getKey()) )){
					regx = Pattern.compile( String.valueOf(e.getValue()) );	
				}				
			} catch (Exception ex){
//				LoggerWrapper.info("RssAddressCrawler", "pattern");
			}
		}
		return regx;
	}
	
	public static Pattern getCafePattern( String addressCrawlType ){		
		
		Pattern regx = null;
		HashMap pattern = new HashMap();		
		pattern.put("naver", "([http://cafe.naver.com/][a-zA-Z0-9_-].*[?Redirect=Log&logNo].*[=])([0-9].*)");
		pattern.put("daum", "([http://cafe.daum.net/][a-zA-Z0-9_-].*[/])([0-9].*)");
		
		
		//([가-힣★●! ~._-a-zA-Z0-9]*)
		Iterator it = pattern.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();			
			try{
				if( addressCrawlType.equalsIgnoreCase( String.valueOf(e.getKey()) )){
					regx = Pattern.compile( String.valueOf(e.getValue()) );	
				}				
			} catch (Exception ex){
//				LoggerWrapper.info("RssAddressCrawler", "pattern");
			}
		}
		return regx;
	}
	
}

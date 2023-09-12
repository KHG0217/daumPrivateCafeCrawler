package com.tapacross.sns.crawler.daum.cafe.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {

	final static String specCharExp = "[\\[\\],./(){}`\\-_;&!\\\\\"'#▶:@”]";
	
	final static String mentionExp = "[@][a-zA-Z0-9]+";
	
	final static Pattern pattern;
	final static Pattern patternMention;
	
	static{
		pattern = Pattern.compile(specCharExp);
		patternMention = Pattern.compile(mentionExp);
	}
	
	public static String removeSpecChar(String content) {
		
		Matcher m = pattern.matcher(content);
		return m.replaceAll(" ");		
	}
	
	public static String removeSpecCharWithMention(String content) {
		
		Matcher m = patternMention.matcher(content);
		content = m.replaceAll(" ");
		
		return removeSpecChar(content);
	}
	
	
	
	public static <T extends Object> String serializeData(List<T> list, String separator){
		StringBuilder serializeData = new StringBuilder(1024);
		
		for(T t : list){
			if(serializeData.length() > 0){
				serializeData.append(separator);
			}
			serializeData.append(t);
		}
		return serializeData.toString();
	}

}

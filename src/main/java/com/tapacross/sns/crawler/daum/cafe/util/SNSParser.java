package com.tapacross.sns.crawler.daum.cafe.util;

public class SNSParser {

	public static String parseTagString(String inText, String siteType ) {
		String outText = inText;
		if( "T".equalsIgnoreCase(siteType) ) { 
			outText = outText.replaceAll("href=\"/", "href=\"http://twitter.com/");
			outText = outText.replaceAll("<a ", "<a target=\"_blank\" ");
		} else if( "F".equalsIgnoreCase(siteType) ) { 
			
		}

		return outText;
	}

}

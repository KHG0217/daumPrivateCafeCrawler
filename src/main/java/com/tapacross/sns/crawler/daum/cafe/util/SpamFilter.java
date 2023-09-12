package com.tapacross.sns.crawler.daum.cafe.util;

public class SpamFilter {

	
	/**
	 * first, remove Hash Tag for classification(#)
	 * second, remove FourSquare Location Infomation for classification(@)  
	 * @param body
	 * @return
	 */
	public static String removeForClassification( String body ) {
		
		if( body == null ) {
			body = "";
		}
		
		String spamChars[] = {"#"};
		for( String spamChar : spamChars ) { 
			body = body.replaceAll("[\\s]?"+spamChar+"\\S*", "");
		}
		
		return body;
	}
	
}

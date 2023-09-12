package com.tapacross.sns.crawler.daum.cafe.util;

import com.tapacross.sns.thrift.SNSInfo;

public class XmlUtil {
	
	
	
	public static int getIntegerBetweenAnB ( String body, String s, String e ) { 

		int start = 0;
		int end = 0;
		String result = "";		
		try {
			start = body.indexOf(s) + s.length() ;			
			end = start + body.substring(start).indexOf( e );
			result = body.substring(start,end);
		} catch ( StringIndexOutOfBoundsException E) {
		}
		if( "".equals( result ) ) { 
			return 0; 
		} else { 
			return Integer.valueOf( result );
		}
	}	

	
	public static String  getStringBetweenAnB ( String body, String s, String e ) { 

		int start = 0;
		int end = 0;
		String result = "";		
		try {
			start = body.indexOf(s) + s.length() ;			
			end = start + body.substring(start).indexOf( e );			
			result = body.substring(start,end).trim();						
		} catch ( StringIndexOutOfBoundsException E) {
		}
		
		return result  ;
	}

	
	/** extract character set from xml  
	 * @param xml
	 * @return
	 */
	public static String extractCharSetFromXml( String xml ) {
		if( xml == null || xml.length()== 0)
			return null;
		xml = xml.toLowerCase();
		
		int index = xml.indexOf("xml");
		if( index < 0 )
			return null;
		
		index = xml.indexOf("encoding=", index);
		if( index < 0 )
			return null;
		index = xml.indexOf('=', index);
		if( index < 0 )
			return null;
		int startIndex = ++index;
		
		if( xml.charAt(startIndex) == '"') {
			index++;
			startIndex++;
		}
		while( true ){
			int ch = xml.charAt(index);
			if( index >= xml.length() ) 
				return null;
			if( ch == '\'' || ch == '"' || ch == '>' || ch == ',' || ch == ';' )
				break;
			index++;
		}
		
		if( startIndex < index ) {
			if( xml.substring(startIndex, index).trim().startsWith("utf-8")) return "utf-8";
			if( xml.substring(startIndex, index).trim().startsWith("euc-kr")) return "euc-kr";
			return xml.substring(startIndex, index).trim().replace("&quot", "");
		}
		
		return "utf-8";

	}
	

	public static String convertSiteInfotoXml( SNSInfo snsInfo ) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<com.tapacross.sns.crawler.SiteInfo>\n");
		sb.append("  <siteId>"+snsInfo.getSiteId()+"</siteId>\n");
		sb.append("  <siteType>"+snsInfo.getSiteType()+"</siteType>\n");
		sb.append("  <url>"+snsInfo.getUrl()+"</url>\n");
		sb.append("  <siteName>"+snsInfo.getSiteName()+"</siteName>\n");
		sb.append("  <follower>"+snsInfo.getFollower()+"</follower>\n");
		sb.append("  <following>"+snsInfo.getFollowing()+"</following>\n");
		sb.append("  <listed>"+snsInfo.getListed()+"</listed>\n");
		sb.append("  <tweet>"+snsInfo.getTweet()+"</tweet>\n");
		sb.append("  <picture>"+snsInfo.getPicture()+"</picture>\n");
		sb.append("  <bio>"+snsInfo.getBio()+"</bio>\n");
		sb.append("  <web>"+snsInfo.getWeb()+"</web>\n");
		sb.append("  <location>"+snsInfo.getLocation()+"</location>\n");
		sb.append("  <ids/>\n");
		sb.append("  <snsContents/>\n");
		sb.append("  <followerList></followerList>\n");
		sb.append("  <followingList></followingList>\n");
		sb.append("</com.tapacross.sns.crawler.SiteInfo>");

		return sb.toString();
		
	}

	public static String convertKeywordstoXml(String Keyword) {
		StringBuffer sb = new StringBuffer();
		sb.append("<com.tapacross.sns.crawler.SiteInfo>\n");
		sb.append("<keyword>" + Keyword + "</keyword>\n");
		sb.append("</com.tapacross.sns.crawler.SiteInfo>");

		return sb.toString();
	}
	

	public static SNSInfo getSiteInfofromXml(String xml) {
		// TODO Auto-generated method stub
		SNSInfo snsInfo = new SNSInfo();
		snsInfo.setSiteId( getStringBetweenAnB(xml,"<siteId>","</siteId>") );
		snsInfo.setSiteType( getStringBetweenAnB(xml,"<siteType>","</siteType>") );
		snsInfo.setUrl( getStringBetweenAnB(xml,"<url>","</url>") );
		snsInfo.setSiteName( getStringBetweenAnB(xml,"<siteName>","</siteName>") );
		snsInfo.setFollower( getIntegerBetweenAnB(xml,"<follower>","</follower>") );
		snsInfo.setFollowing( getIntegerBetweenAnB(xml,"<following>","</following>") );
		snsInfo.setListed( getIntegerBetweenAnB(xml,"<listed>","</listed>") );
		snsInfo.setTweet( getIntegerBetweenAnB(xml,"<tweet>","</tweet>") );
		snsInfo.setPicture( getStringBetweenAnB(xml,"<picture>","</picture>") );
		snsInfo.setBio( getStringBetweenAnB(xml,"<bio>","</bio>") );
		snsInfo.setWeb( getStringBetweenAnB(xml,"<web>","</web>") );
		snsInfo.setLocation( getStringBetweenAnB(xml,"<location>","</location>") );
		
		return snsInfo;
		
	}

}

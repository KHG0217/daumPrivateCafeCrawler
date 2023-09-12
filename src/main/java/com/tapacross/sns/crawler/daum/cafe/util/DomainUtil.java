package com.tapacross.sns.crawler.daum.cafe.util;

import com.esmedia.log.LoggerWrapper;

/**
 * Utility class related domain, url manipulation
 * 
 * @author Jason
 * @since ver 1.0
 */
public class DomainUtil {
	
	public static final String PROTOCOL_END_SPECIFIER = "://";
	public static final String CURRENT_DIR_SPECIFIER = "./";
	public static final String PARENT_DIR_SPECIFIER = "../";
	public static final String ROOT_DIR_SPECIFIER = "/";
	public static final String PORT_START_SPECIFIER = ":";

	public static final int DEFAULT_HTTP_PORT = 80;
	
	public static String[] bizDoms = { "com", "net", "org", "gov", "edu", "mil", "int", "info",
			"biz", "go", "co", "or"
	};
	
	public static String[] nationDoms = { "as", "ar", "am", "au", "be", "bt", "bg", "cl", "cn",
			"cc", "cg", "bi", "rw", "zr", "cr", "dk", "ec", "fo", "fi", "fr",
			"de", "gr", "gt", "is", "ir", "il", "jp", "jo", "lu", "kz", "my",
			"mx", "na", "nl", "nz", "ni", "nu", "nf", "no", "pk", "ru", "sg",
			"sk", "za", "es", "ch", "li", "tw", "th", "to"
	};
	
	/**
	 * Get right most index matching target character from beginning right side index <br>
	 * if source is <b>"abc.net"</b> and rightBeginIndex is <b>3</b> and target character is <b>'.'</b> 
	 *  then it would be <b>3</b><br>
	 * if source is <b>"www.abc.net"</b> and rightBeginIndex is <b>4</b> and target character is <b>'.'</b> 
	 *  then it would be <b>3</b><br>
	 * if source is <b>"www.abc.net"</b> and rightBeginIndex is <b>8</b> and target character is <b>'.'</b> 
	 *  then it would be <b>7</b>
	 * 
	 * @param src source string
	 * @param rightBeginIndex right side beginning index for searching
	 * @param target finding character
	 * @return searched index -1 if it was not found
	 */
	public static int getRightMostIndex( String src, int rightBeginIndex, char target ) {
		int indexLast = -1;
		
		if( src != null && src.length()>0  && rightBeginIndex>=0 && rightBeginIndex<src.length()) {
			String leftMost = src.substring(0, rightBeginIndex+1 );
			indexLast = leftMost.lastIndexOf( target );
		}
		
		return indexLast;
	}

	/**
	 * Extract upper most domain name such as "kr", "com", "org", etc from host
	 * 
	 * @param host host name
	 * @return upper most domain, null if it was not found
	 */
	public static String extractUpperMostDomain( String host ) {
		if( host == null || host.length() == 0 )
			return null;
		
		int indexDot = getRightMostIndex( host, host.length()-1, '.' );
		if( indexDot < 0 )
			return null;
		
		return host.substring(indexDot+1);
	}

	/**
	 * Get left side string just before target character from right side begin index<br>
	 * if host is <b>"www.abc.net"</b> and rightBeginIndex is <b>2</b> and target is '.' 
	 *  then it would be <b>"abc.net"</b>
	 * 
	 * @param src
	 * @param rightIndex
	 * @param target, target character
	 * @return String, null if it was not found
	 */
	public static String getLeftFromIndex(String src, int rightBeginIndex, char target ) {
		if( src == null || src.length() == 0 || rightBeginIndex <= 0 )
			return null;
		
		int indexRightMost = src.length();
		for( int i=0; i<rightBeginIndex; i++)
			indexRightMost = getRightMostIndex(src, indexRightMost-1, target );
		
		if( indexRightMost < 0 )
			return null;
		
		return src.substring(indexRightMost+1);
	}

	/**
	 * Extract domain. 
	 * if host is <b>"www.abc.net"</b> then it would be <b>"abc.net"</b>.<br>
	 * if host contains nation domain like <b>"www.abc.co.kr"</b> then it would be <b>"abc.co.kr"</b>
	 * 
	 * @param host
	 * @return String
	 */
	public static String extractDomainFromHost(String host) {
		String upperMostDomain = extractUpperMostDomain( host );
		boolean isNationDomain = false;
		
		// TODO : consider better access, for example, using hash table
		for( int i=0; i<nationDoms.length; i++ ) {
			if( nationDoms[i].equals(upperMostDomain)) {
				isNationDomain = true;
				break;
			}
		}
		
		String domain = host;
		if( isNationDomain )
			domain = getLeftFromIndex( host, 3, '.' );
		else
			domain = getLeftFromIndex( host, 2, '.' );
		
		return domain;
	}

	/**
	 * Extract protocol name from hyper link. <br>
	 * if parentProtocol is <b>"ftp"</b> and hyper link is <b>"http://www.abc.net/index.html"</b> 
	 *  then it would be <b>"http"</b> <br>
	 *  
	 * @param parentProtocol, protocol of parent url contain hyper link
	 * @param hyperLink
	 * @return String
	 */
	public static String extractProtocolFromHyperLink(String parentProtocol, String hyperLink) {
		String protocol = parentProtocol;
		
		if( hyperLink == null || hyperLink.length() == 0 )
			return null;

		hyperLink = hyperLink.trim();
		if( hyperLink.charAt(0) == '\'' || hyperLink.charAt(0) == '"' )
			hyperLink = hyperLink.substring(1);
		
		int idx = hyperLink.indexOf( PROTOCOL_END_SPECIFIER );
		if( idx > 0 ){
			protocol = hyperLink.substring(0, idx );
			if( protocol.length() <=5)
				return protocol.trim();
		}
		
		return parentProtocol;
	}

	/**
	 * Extract protocol name from url. <br>
	 *  
	 * @param url
	 * @return String
	 * @throws NullPoinerException
	 */
	public static String extractProtocolFromUrl(String url) {
		String protocol = null;
		
		if( url == null || url.length() == 0 )
			throw new NullPointerException( "url is null" );

		url = url.trim();
		if( url.charAt(0) == '\'' || url.charAt(0) == '"' )
			url = url.substring(1);
		
		int idx = url.indexOf( PROTOCOL_END_SPECIFIER );
		if( idx > 0 )
			protocol = url.substring(0, idx );
		else { 
			LoggerWrapper.debug("web-crawler", "Url : " + url);
			throw new IllegalArgumentException( "url is not valid" );
		}

		return protocol.trim();
	}

	/**
	 * Extract host name from hyper link. It does not matter hyper link contains host name or not<br>
	 * If parentHost is <a>www.aa.com</a> and hyper link is <b>"http://www.abc.net/index.html"</b> 
	 *   then it would be <b>"www.abc.net"</b>.<br>
	 * If parentHost is <a>www.aa.com</a> and hyper link is <b>"/aa/index.html"</b> 
	 *   then it would be <b>"www.aa.com"</b>.<br>
	 * if hyper link is <b>"http://www.abc.net:8080/index.html"</b> then it would be <b>"www.abc.net:8080"</b>. 
	 * 
	 * @param parentHost, well formed host name containing hyper link
	 * @param hyperLink
	 * @return
	 */
	public static String extractHostFromHyperLink(String parentHost, String hyperLink) {
		String hostName = parentHost;
		
		if( hyperLink == null || hyperLink.length() == 0 )
			return null;

		if( hyperLink.startsWith(CURRENT_DIR_SPECIFIER) || hyperLink.startsWith(PARENT_DIR_SPECIFIER) ||
			hyperLink.startsWith(ROOT_DIR_SPECIFIER) ) {
			if( parentHost == null || parentHost.length() == 0 )
				throw new IllegalArgumentException( "parent host name is null.." );
			return parentHost;
		}
		
		int hostStartIndex = hyperLink.indexOf( PROTOCOL_END_SPECIFIER );
		if( hostStartIndex >= 0 ) {
			int rootDirIndex = hyperLink.indexOf(ROOT_DIR_SPECIFIER, hostStartIndex+3 );
			if( rootDirIndex >= 0 )
				hostName = hyperLink.substring(hostStartIndex+3, rootDirIndex );
			else
				hostName = hyperLink.substring(hostStartIndex+3);
		}
		else {
			int rootDirIndex = hyperLink.indexOf(ROOT_DIR_SPECIFIER );
			if( rootDirIndex >= 0 ){
				int indexDot = hyperLink.indexOf('.');
				if( indexDot >= 0 && indexDot < rootDirIndex )
					hostName = hyperLink.substring(0, rootDirIndex );
				else
					hostName = parentHost;
			}
			else
				hostName = parentHost;
		}

		return hostName;
	}

	/**
	 * Extract host name from url. 
	 * 
	 * @param url
	 * @return
	 */
	public static String extractHostFromUrl(String url) {
		if( url == null || url.length() == 0 )
			throw new NullPointerException( "url is null" );
		
		String hostName="";
		int hostStartIndex = url.indexOf( PROTOCOL_END_SPECIFIER );
		if( hostStartIndex >= 0 ) {
			int rootDirIndex = url.indexOf(ROOT_DIR_SPECIFIER, hostStartIndex+3 );
			if( rootDirIndex >= 0 )
				hostName = url.substring(hostStartIndex+3, rootDirIndex );
			else
				hostName = url.substring(hostStartIndex+3);
			
			int idx = hostName.indexOf(PORT_START_SPECIFIER);
			if( idx > 0 )
				hostName = hostName.substring(0, idx);
		}
		else {
			throw new IllegalArgumentException( "URL is not valid" );
		}

		return hostName;
	}

	/**
	 * Extract host name from hyper link. If hyper link's port is not 80, then include that port number.<br> 
	 * if hyper link is <b>"http://www.abc.net"</b> then it would be <b>"www.abc.net"</b>.<br>
	 * if hyper link is <b>"http://www.abc.net:8080"</b> then it would be <b>"www.abc.net"</b>. 
	 * 
	 * @param parentHost, well formed host name containing hyper link
	 * @param hyperLink
	 * @return
	 */
	public static String extractRealHostFromHyperLink(String parentHost, String hyperLink) {
		String hostName = extractHostFromHyperLink( parentHost, hyperLink );
		
		if( hostName != null && hostName.length() > 0 ) {
			int portStartIndex = hostName.indexOf( PORT_START_SPECIFIER );
			if( portStartIndex >= 0 )
				return hostName.substring(0, portStartIndex );
		}
		
		return hostName;
	}

	/**
	 * Extract port from host. <br>
	 * if url is <b>"www.abc.net"</b> then it would be <b>80</b> <br>
	 * if url is <b>"www.abc.net:8080</b> then it would be <b>8080</b>
	 * 
	 * @param url
	 * @return
	 * @throws NullPointerException
	 */
	public static int extractPortFromUrl( String url ) {
		String port = null;

		if( url == null || url.length() == 0 )
			throw new NullPointerException( "url is null");
		
		int fromIndex = url.indexOf(PROTOCOL_END_SPECIFIER);
		int portStartIndex = 0;
		if( fromIndex > 0 )
			portStartIndex = url.indexOf( PORT_START_SPECIFIER, fromIndex+3 );
		else
			portStartIndex = url.indexOf( PORT_START_SPECIFIER );
		
		if( portStartIndex >= 0 ){ 
			int pathIdx = url.indexOf(ROOT_DIR_SPECIFIER, portStartIndex);
			if( pathIdx > 0 )
				port = url.substring(portStartIndex+1, pathIdx );
			else
				port = url.substring(portStartIndex+1 );
		}

		int realPort = DEFAULT_HTTP_PORT;
		if( port != null )
			try{ realPort = Integer.parseInt(port); } catch( Exception e ) {}

		return realPort;
	}

	/**
	 * Extract path from hyper link. <br>
	 * If hyper link is <b>"http://www.naver.com/dir/aa.jsp?aa=bb&bb=cc"</b> then <br>
	 *   it would be <b>"/dir/aa.jsp?aa=bb&bb=cc"</b> <br>
	 * If hyper link is <b>"/dir/aa.jsp?aa=bb&bb=cc"</b> then it would be <b>"/dir/aa.jsp?aa=bb&bb=cc"</b>
	 * 
	 * @param hyperLink
	 * @return String revised path
	 */
	public static String extractPathFromHyperLink( String hyperLink ) {
		if( hyperLink == null || hyperLink.length() == 0 )
			return null;

		if( hyperLink.startsWith(CURRENT_DIR_SPECIFIER) || hyperLink.startsWith(PARENT_DIR_SPECIFIER) ||
				hyperLink.startsWith(ROOT_DIR_SPECIFIER) )
			return hyperLink;
		
		int hostStartIndex = hyperLink.indexOf( PROTOCOL_END_SPECIFIER );
		String path = hyperLink;
		
		if( hostStartIndex >= 0 ) {
			int rootDirIndex = hyperLink.indexOf(ROOT_DIR_SPECIFIER, hostStartIndex+3 );
			if( rootDirIndex >= 0 )
				path = hyperLink.substring( rootDirIndex );
			else
				path = "/";
		}
		else {
			int rootDirIndex = hyperLink.indexOf(ROOT_DIR_SPECIFIER );
			if( rootDirIndex >= 0 ){
				int indexDot = hyperLink.indexOf('.');
				if( indexDot >= 0 && indexDot < rootDirIndex )
					path = hyperLink.substring( rootDirIndex );
			}
		}

		return path;
	}
	
	/**
	 * Extract directory from path. <br>
	 * If path is <b>"/aa/bb/a.jsp"</b> then it would be <b>"/aa/bb/"</b> <br>
	 * If path is <b>"./aa/bb/a.jsp"</b> then it would be <b>"./aa/bb/"</b> <br>
	 * If path is <b>"../aa/bb/a.jsp"</b> then it would be <b>"../aa/bb/"</b>
	 * If path is <b>"http://www.abc.net/aa.jsp"</b> then it would be <b>"/"</b>
	 * 
	 * @param path
	 * @return revised directory
	 */
	public static String extractDirFromPath( String path ) {
		if( path == null || path.length() == 0 )
			return null;

		String revisedPath = extractPathFromHyperLink( path );
		//int lastDirIndex = path.lastIndexOf( ROOT_DIR_SPECIFIER );
		if( revisedPath == null || revisedPath.length() == 0 )
			return null;
		
		int lastDirIndex = revisedPath.lastIndexOf( ROOT_DIR_SPECIFIER );
		if( lastDirIndex < 0 )
			return null;
		
		return revisedPath.substring(0, lastDirIndex+1);
	}

	/**
	 * Convert any specific path which is regardless of absolute, relative path into absolute path. <br>
	 * 
	 * @param parent parent path containing current path. It should be absolute path. i.e formed starting with "/"
	 * @param current current path. It does not matter whether absolute path or relative path
	 * @return absolute path
	 * @throws NullPointerException
	 * @throws IllegalArgumentException throws when parent path is not absolute path
	 */
	public static String toAbsolutePath(String parent, String current) {
		if( parent == null || parent.length() == 0 )
			throw new NullPointerException( "parent path is null");
		if( current == null || current.length() == 0 )
			throw new NullPointerException( "current path is null");
		
		if( !parent.startsWith("/") )
			throw new IllegalArgumentException( "Parent should be abolute path" );

		String parentDir = extractDirFromPath( parent );
		String revisePath = current;
		if( current.startsWith(CURRENT_DIR_SPECIFIER)){
			revisePath = parentDir + current.substring(2);
		}
		else if( current.startsWith(PARENT_DIR_SPECIFIER)) {
			int parentEndIndex = getRightMostIndex( parentDir, parentDir.length()-2, '/' );
			
			if ( current.length() == 3 ) {
				revisePath = "/";
				return revisePath;
			}
			
			String[] occurence = current.split("\\.\\./");
			
			if( parentEndIndex >= 0  ){
				int occur = 0;
				while ( parentEndIndex >= 0 ) {
					if ( parentDir.charAt(parentEndIndex) == '/' ){
						occur++;
						if ( occur == occurence.length - 1 ) break;
					}
					parentEndIndex--;
				}
				revisePath = parentDir.substring(0, parentEndIndex+1) + current.substring(3*(occurence.length-1));
			}
			else{
				revisePath = parentDir + current.substring(3);
			}
		}
		else if( current.startsWith(ROOT_DIR_SPECIFIER)){
			revisePath = current;
		}
		else{
			revisePath = parentDir + current;
		}

		return revisePath;
	}

	/**
	 * Convert any specific path which is regardless of absolute, relative path into well formed url. <br>
	 * 
	 * @param url parent url containing current path.
	 * @param current current path. It does not matter whether absolute path or relative path
	 * @return revised url
	 * @throws NullPointerException
	 * @throws IllegalArgumentException throws when parent path is not absolute path
	 */
	public static String toAbsoluteUrl( String url, String current ){
		if( url == null || url.length() == 0 )
			throw new NullPointerException( "parent url is null");
		if( current == null || current.length() == 0 )
			throw new NullPointerException( "current path is null");

		current = current.trim();
		//if( current.startsWith( "http://") || current.startsWith("https://") )
		if( current.startsWith( "http://") || current.startsWith("HTTP://") || current.startsWith("https://") || current.startsWith("HTTPS://") )
			return current;
		
		String hostName = extractHostFromUrl( url );
		String parentPath = extractPathFromHyperLink( url );
		String protocol = extractProtocolFromUrl( url );
		int port = extractPortFromUrl( url );
		
		return port==80? protocol + "://" + hostName + toAbsolutePath(parentPath, current ) :
			protocol + "://" + hostName + ":" + port + toAbsolutePath(parentPath, current );
	}

	public static String toUpper(String test) {
		return test.toUpperCase();
	}
	
}


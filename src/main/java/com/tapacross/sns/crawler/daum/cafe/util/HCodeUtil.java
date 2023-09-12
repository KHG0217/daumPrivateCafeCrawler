package com.tapacross.sns.crawler.daum.cafe.util;

public class HCodeUtil {

	
	public static boolean containNoneKor( String data ) {

		
		for( int i = 0; i < data.length(); i++ ) { 
			String code = String.format( "%X%n", (int)data.charAt(i));
			System.out.println( code );
			if( code.compareTo("20") >= 0 && code.compareTo("7E") <= 0 ) { 
				continue;
			} else if( code.compareTo("AC00") >= 0 && code.compareTo("D7A3") <= 0 ) { 
				continue;
			} else { 
				return true;
			}
			
		}
		
		return false; 
		
	}
	
	public static void compare() { 
		System.out.println( "BACC".compareTo("BACA") ); // 2
		System.out.println( "BACC".compareTo("BACD") ); // -1
		System.out.println( "20".compareTo("D7A3") ); // -1
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapacross.sns.crawler.daum.cafe.util;

/**
 *
 * @author lissome
 */
public class EnumUtil {
	public static <T extends Enum> T  parse(Enum[] enumList, String value){
		for(Enum val : enumList){
			if(val.toString().equalsIgnoreCase(value)){
				return (T)val;
			}				
		}			
		throw new IllegalArgumentException("can not parse ["+value+"]");
	}
}

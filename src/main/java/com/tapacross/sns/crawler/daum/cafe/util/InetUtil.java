package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import com.tapacross.sns.ConfigConstants;

public class InetUtil {

//	public static ArrayList<String> getIpList() {
//		ArrayList<String> ips = new ArrayList<String>();
//
//		try {
//			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//			while (interfaces.hasMoreElements()) {
//				NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
//				Enumeration<InetAddress> inetAddress = ni.getInetAddresses();
//				while (inetAddress.hasMoreElements()) {
//					InetAddress ia = inetAddress.nextElement();
//					ips.add(ia.getHostAddress());
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ips;
//	}
	
	
	public static ArrayList<String> getIpList() { 
		
		ArrayList<String> ips = new ArrayList<String>();
		
		try {
			String filename = System.getProperty(ConfigConstants.HOMEDIR_PROP_KEY) 
								+ File.separator + "config" 
								+ File.separator + "sns" 
								+ File.separator + "localip.cfg";

			ips.add( FileUtil.readFromFile(filename).trim());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ips;
	}
}

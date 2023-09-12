package com.tapacross.sns.crawler.daum.cafe.util;

public class ThreadUtil {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sleepSec(int sesc) {
		try {
			Thread.sleep(sesc * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * use printSleep(int secs)
	 * 1000 이하 입력시 정상 동작 안됨
	 * @param millis
	 */
	@Deprecated
	public static void printSleep(long millis) {
		try {
			for (int i = 0; i < (millis / 1000); i++) {
				System.out.print(".");
				Thread.sleep(1 * 1000);
			}
			System.out.println("");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void printSleepSec(int sesc) {
		try {
			for (int i = 0; i < sesc; i++) {
				System.out.print(".");
				Thread.sleep(1 * 1000);
			}
			System.out.println("");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}

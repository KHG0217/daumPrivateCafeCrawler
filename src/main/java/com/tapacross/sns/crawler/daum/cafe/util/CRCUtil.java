package com.tapacross.sns.crawler.daum.cafe.util;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class CRCUtil {
	/**
	 * 스트링의 바이트 배열로 체크섬을 생성하여 리턴한다. 입력 문자열이 널이거나 길이가 0이면 예외가 발생한다.
	 * @param input 
	 * @return ex) 3420034464
	 */
	public static String getCRC32(String input) {
		if (input == null || input.length() == 0)
			throw new IllegalArgumentException();
		
		Checksum crc = new CRC32();
		crc.update(input.getBytes(), 0, input.getBytes().length);
		long calculated = crc.getValue();
		crc.reset();
		return String.valueOf(calculated);
	}
}

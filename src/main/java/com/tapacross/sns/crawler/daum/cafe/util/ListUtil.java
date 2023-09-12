package com.tapacross.sns.crawler.daum.cafe.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {
	/**
	 * 스트링배열을 구분자를 포함하여 단일 문자열로 리턴한다.
	 * 배열 사이즈가 0일 경우 빈문자열을 리턴한다.
	 * @param array
	 * @param delimiter 요소들 사이에 들어가는 구분값
	 * @return
	 */
	public static String join(String[] array, String delimiter) {
		String result = "";
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				result += array[i];
				continue;
			}
			result += delimiter + array[i];
		}
		return result;
	}
	
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.size() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * <pre>
	 * 구분자로 결합된 문자열 배열을 맵으로 변환한다. 
	 * 구분자로 분리시에 0번째 인덱스는 키로 사용하고 1번째 인덱스는 값으로 사용한다.
	 * 키가 동일할 경우 이전의 값은 최신값으로 덮어진다. 
	 * 맵의 키 순서는 정렬되어 있지 않다. 키는 소문자로 정규화된다.
	 * 문자열 배열 길이가 0이거나 구분자로 분리한 배열의 길이가 0일 경우 해당 로우는 건너띈다. 
	 * </pre>
	 * 
	 * @param array
	 * @param delimiter
	 * @return map
	 * 			-key=소문자화된 배열 키
	 * 			-value=배열 값
	 */
	public static Map<String, String> toMap(String[] array, String delimiter) {
		Map<String, String> map = new HashMap<>();
		if (array.length == 0)
			return map;
		
		for (String item : array) {
			String[] split = item.split(delimiter);
			map.put(split[0].toLowerCase(), split[1]);
		}
		
		return map;
	}
}
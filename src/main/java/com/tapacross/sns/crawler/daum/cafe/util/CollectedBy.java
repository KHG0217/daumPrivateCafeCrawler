package com.tapacross.sns.crawler.daum.cafe.util;

/**
 * 수집원 수집 방식, 수집 테이블의 collected_by 컬럼에 해당 값이 저장된다.
 *
 */
public class CollectedBy {
	/**
	 * 수집원 기반
	 */
	public static final String CRAWL = "C";
	
	/**
	 * 키워드 기반
	 */
	public static final String KEYWORD = "K";
}

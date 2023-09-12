package com.tapacross.sns.crawler.daum.cafe.collector

import org.junit.Test

import com.tapacross.sns.crawler.daum.cafe.collector.DaumPrivateCafeCrawlerApp

import groovy.transform.TypeChecked

@TypeChecked
class DaumPrivateCafeCrawlerAppTest {
	private final String LOGIN_ID = "23911116@sjcu.ac.kr"//남성계정
	private final String LOGIN_PW = "wlsan4807!"
	
	@Test
	def void test() {
		def app = new DaumPrivateCafeCrawlerApp()
		def args = new String[3]
		args[0] = 1
		args[1] = LOGIN_ID
		args[2] = LOGIN_PW
		app.main(args)
	}
}

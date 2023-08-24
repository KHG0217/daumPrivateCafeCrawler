package com.tapacross.sns.crawler.daum.cafe.collector.dao

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.tapacross.sns.crawler.daum.cafe.collector.dao.CrawlerDao
import com.tapacross.sns.entity.TBArticle
import com.tapacross.sns.entity.TBArticleCommunity
import groovy.transform.TypeChecked

@TypeChecked
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
class DaumPrivateCafeCrawlerDaoTest {
	@Autowired
	private CrawlerDao dao
	
	@Test
	def void testSelectNaverCrawlSite() {
		def url = "https://cafe.daum.net/ccchappydog"
		def result = dao.selectDaumCrawlSite(url)
		println result
		assert result != null
	}
}

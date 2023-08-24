package com.tapacross.sns.crawler.daum.cafe.collector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service

import com.tapacross.sns.crawler.daum.cafe.collector.dao.CrawlerDao
import com.tapacross.sns.entity.ITBArticle
import com.tapacross.sns.entity.TBArticle
import com.tapacross.sns.entity.TBCrawlSite2
import com.tapacross.sns.entity.crawl.TBCrawlCycle

import groovy.transform.TypeChecked

interface CrawlerService {
	def ITBArticle selectArticle(TBArticle article) throws DataAccessException
	def TBCrawlSite2 selectDaumCrawlSite(String url) throws DataAccessException
	def int insertSearchContent(ITBArticle article) throws DataAccessException
	def TBCrawlCycle insertCrawlCycleInfo(TBCrawlCycle crawlCycle)
}

@TypeChecked
@Controller
class DaumPrivateCafeCrawlerService implements CrawlerService {

	@Autowired
	private CrawlerDao dao
	
	@Override
	public ITBArticle selectArticle(TBArticle article) throws DataAccessException {
		return dao.selectArticle(article)
	}

	@Override
	public TBCrawlSite2 selectDaumCrawlSite(String url) throws DataAccessException {
		return dao.selectDaumCrawlSite(url)
	}

	@Override
	public int insertSearchContent(ITBArticle article) throws DataAccessException {
		return dao.insertSearchContent(article)
	}

	@Override
	public TBCrawlCycle insertCrawlCycleInfo(TBCrawlCycle crawlCycle) {
		return dao.insertCrawlCycleInfo(crawlCycle)
	}
}

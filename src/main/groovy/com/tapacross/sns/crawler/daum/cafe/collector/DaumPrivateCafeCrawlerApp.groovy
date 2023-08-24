package com.tapacross.sns.crawler.daum.cafe.collector


import org.apache.bcel.generic.RETURN
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext
import org.springframework.dao.DataAccessException

import com.tapacross.sns.crawler.StringUtil
import com.tapacross.sns.crawler.cafe.parser.DaumPrivateCafeParser
import com.tapacross.sns.crawler.daum.cafe.collector.service.CrawlerService
import com.tapacross.sns.crawler.daum.cafe.collector.service.DaumPrivateCafeCrawlerService
import com.tapacross.sns.crawler.twitter.contants.CollectedBy
import com.tapacross.sns.entity.TBArticleCommunityAdapter
import com.tapacross.sns.entity.TBCrawlSite2
import com.tapacross.sns.entity.crawl.TBCrawlCycle
import com.tapacross.sns.service.IRedisService
import com.tapacross.sns.service.RedisService
import com.tapacross.sns.thrift.SNSContent
import com.tapacross.sns.thrift.SNSContent2
import com.tapacross.sns.thrift.SNSContentAdapter
import com.tapacross.sns.util.DateFormatUtil
import com.tapacross.sns.util.KeyUtil
import com.tapacross.sns.util.ThreadUtil

import groovy.json.StringEscapeUtils
import groovy.json.internal.ArrayUtils
import groovy.transform.TypeChecked
import javassist.bytecode.stackmap.BasicBlock.Catch

import java.nio.file.Files
import java.nio.file.Paths
/**
 * 다음 폐쇄형카페를 수집한다.
 * @date 2023.08.24
 * @author hyukg
 *
 */
@TypeChecked
class DaumPrivateCafeCrawlerApp {
	private static final String DAUM_PRIVATE_CAFE_CRAWLER = "DaumPrivateCafeCrawler:"
	private final String REDIS_CAFE_CRAWL_URL_KEY_PREFIX = "crawlurl:DaumCafe:"
	private final int EXCEPTION_WAIT_TIME = 3 * 1000
	private final int CRAWL_WAIT_TIME = 1 * 1000
	
	/**
	 * 남성 계정
	 */
	private final int ACCOUNT_TYPE1_VAL = 1
	
	/**
	 * 여성계정
	 */
	private final int ACCOUNT_TYPE2_VAL = 2
	
	private String LOGIN_ID = ""
	private String LOGIN_PW = ""
	private String DRIVER_PATH = ""
	private static Logger logger = LoggerFactory.getLogger(DaumPrivateCafeCrawlerApp.class)
	private IRedisService redisService
	private CrawlerService crawlerService
	private int userType = 1
	
	private List<String> crawlCafeUrls = []
	
	private DaumPrivateCafeParser parser = new DaumPrivateCafeParser()
		
	def void init(List<String> args) {
		initArgs(args)
		
		def applicationContext =
			new GenericXmlApplicationContext("classpath:spring/application-context.xml")
		this.crawlerService =
			applicationContext.getBean(DaumPrivateCafeCrawlerService.class)
		this.redisService = applicationContext.getBean(RedisService.class)
		
		DRIVER_PATH = applicationContext.getBean(ApplicationProperty.class).get("chrome.driver.path")
		parser.login(LOGIN_ID, LOGIN_PW, DRIVER_PATH)
	}
	
	private void initArgs(List<String> args) {
		if (args.size() != 3) {
			logger.error(DAUM_PRIVATE_CAFE_CRAWLER + " Usage:<account type> <account id> <account pw>")
			logger.error(DAUM_PRIVATE_CAFE_CRAWLER + " System exit.")
			System.exit(-1)
		}
		
		int accountType = args[0] as int
		def userId = args[1]
		def userPw = args[2]
		this.LOGIN_ID = userId
		this.LOGIN_PW = userPw
		if (accountType == ACCOUNT_TYPE1_VAL) {
			this.crawlCafeUrls = new BufferedReader(new InputStreamReader(DaumPrivateCafeCrawlerApp.class
				.getResourceAsStream("/data/user1-cafe-urls.txt"))).readLines()
		} else if(accountType == ACCOUNT_TYPE2_VAL) {
			this.crawlCafeUrls = new BufferedReader(new InputStreamReader(DaumPrivateCafeCrawlerApp.class
				.getResourceAsStream("/data/user2-cafe-urls.txt"))).readLines()
		} else {
			logger.error(DAUM_PRIVATE_CAFE_CRAWLER + " Invalid account type.")
			logger.error(DAUM_PRIVATE_CAFE_CRAWLER + " System exit.")
		}
	}
	
	/**
	 * 지정한 카페의 수집원 정보를 DB에서 조회하여 수집 시작
	 */
	def void run() {
		crawlCafeUrls.each { 
			try {
				def crawlSite = crawlerService.selectDaumCrawlSite(it)
				if (crawlSite != null) {
					crawl(crawlSite)
				}else {
					logger.info("$DAUM_PRIVATE_CAFE_CRAWLER crawlSite is null. $it")
					sleep(CRAWL_WAIT_TIME)
				}
			} catch (Exception e) {
				logger.error("$DAUM_PRIVATE_CAFE_CRAWLER $e.message")
				sleep(EXCEPTION_WAIT_TIME)
			}
			
		}
		
		if (crawlCafeUrls.size() > 0) {
			insertCycleInfo(DateFormatUtil.format(new Date(), "yyyyMMddHHmmss"), userType, crawlCafeUrls.size())
		}
	}
	
	def void crawl (TBCrawlSite2 crawlSite) {
		
		try {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER Search Site: $crawlSite.siteName, $crawlSite.url")
			def list = parser.extractArticle(crawlSite.url) as List<String>
			def newArticles = list.findAll { it ->
				
				// crawlurl:DaumCafe:https://cafe.daum.net/카페명/게시판코드/게시물번호
				def redisCrawlUrlKey = "$REDIS_CAFE_CRAWL_URL_KEY_PREFIX$it"
				if (redisService.getRedisValue(redisCrawlUrlKey) == null) {
					return true
				} else {
					logger.info("$DAUM_PRIVATE_CAFE_CRAWLER exist redis. $crawlSite.siteName, $it")
					return false
				}
				
			}
			
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER parse articles. $crawlSite.siteName, $crawlSite.url, ${newArticles.size()}")
	
			def newSnsContents = newArticles.collect {
				try {
					def snsContent = parser.parseContent(it)
					
					if (!validateFields(snsContent)) {
						logger.error("$DAUM_PRIVATE_CAFE_CRAWLER Invalid content. url:$snsContent.url")
						return
				}
					logger.info("$DAUM_PRIVATE_CAFE_CRAWLER parse article. $snsContent.createDate, $snsContent.url")
					return snsContent
				}catch (Exception e) {
					logger.error(e.message)
					sleep(CRAWL_WAIT_TIME)
				}
			}.findAll { snsContent -> snsContent != null } as List<SNSContent2>
						
			def filledSnsConTents = newSnsContents.collectNested {
				SNSContent2 it ->
				it.siteName = crawlSite.siteName
				it.screenName = crawlSite.screenName
				it.siteId = crawlSite.siteId
				it.siteCode = crawlSite.siteCode
				it.articleId = KeyUtil.makeArticleIdFromSNSContent(new SNSContentAdapter(it).toSNSContent())
				return it
			} as List<SNSContent2>
			
			filledSnsConTents.each {
				def result = saveSearchContent(it)
				
				if (result == 1) {
					addVisitedUrl(it.url)
				}
			}
		} catch(Exception e) {
			logger.error("$DAUM_PRIVATE_CAFE_CRAWLER $e.message")
			ThreadUtil.sleep(EXCEPTION_WAIT_TIME)
		}		
	}
	
	private void insertCycleInfo(String endDate, int priority, int visitCount) {
		def entity = new TBCrawlCycle()
		entity.startDate = endDate
		entity.endDate = endDate
		entity.crawlerName = DAUM_PRIVATE_CAFE_CRAWLER
		entity.priority = priority
		entity.visitCount = visitCount
		try {
			def articleCount = 0
			entity.articleCount = articleCount.toInteger()

			if (null != crawlerService.insertCrawlCycleInfo(entity)) {
				logger.info("$DAUM_PRIVATE_CAFE_CRAWLER inserted crawl cycle info.")
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	/**
	 * 게시물을 DB에 추가하고 결과값을 반환한다.
	 * @param snsContent
	 * @return result
	 */
	private int saveSearchContent(SNSContent2 snsContent) {
		def adapter = new TBArticleCommunityAdapter(snsContent, CollectedBy.CRAWL)
		def result = 0
		try {
			if (crawlerService.selectArticle(adapter.toValue()) == null) {
				result = crawlerService.insertSearchContent(adapter.toValue())
				logger.info("$DAUM_PRIVATE_CAFE_CRAWLER inserted article. $result, $snsContent.createDate, $snsContent.url")
			}
			
		}catch (DataAccessException e ) {
			logger.error("$DAUM_PRIVATE_CAFE_CRAWLER saveSearchContent Exception ocuurred. $e.message, $snsContent.url")
			ThreadUtil.sleep(EXCEPTION_WAIT_TIME)		
		}	
		return result
	}
	
	private void addVisitedUrl(String articleUrl) {
		def redisCrawlUrlKey = "$REDIS_CAFE_CRAWL_URL_KEY_PREFIX$articleUrl"
		redisService.addRedisValue(redisCrawlUrlKey, "1", 1)		
	}
	
	/**
	 * 게시물 유효성을 검사한다.
	 * @param snsContent
	 * @return result
	 */
	private boolean validateFields(SNSContent2 snsContent) {
		def result = true
		
		if (snsContent.getUrl().isEmpty()) {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER Url is null. url:${snsContent.url}")
			result = false
		}
		
		if (snsContent.getTitle().isEmpty()) {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER Title is null. url:${snsContent.url}")
			result = false
		}
		
		if (snsContent.getCreateDate().size() != 14) {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER CreateDate Size Not 14 date : ${snsContent.getCreateDate()},url:${snsContent.url}")
			result = false
		}
				
		if(snsContent.getContent().isEmpty()) {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER Content is null, url:${snsContent.url}")
			result = false
		}
		
		return result
	}
	
	static void main(String[] args) {
		def dpcApp = new DaumPrivateCafeCrawlerApp()
		logger.info("$DAUM_PRIVATE_CAFE_CRAWLER init start")
		dpcApp.init(args.collect() as List<String>)
		logger.info("$DAUM_PRIVATE_CAFE_CRAWLER init end")
		while(true) {
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER run start")
			dpcApp.run()
			logger.info("$DAUM_PRIVATE_CAFE_CRAWLER run end")
		}
	}

}
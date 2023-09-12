package com.tapacross.sns.crawler.daum.cafe.collector.dao

import javax.annotation.Resource

import org.hibernate.criterion.Restrictions
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.dao.DataAccessException
import org.springframework.orm.hibernate3.HibernateTemplate
import org.springframework.stereotype.Repository
import com.tapacross.sns.crawler.daum.cafe.entity.ITBArticle
import com.tapacross.sns.crawler.daum.cafe.entity.TBArticle
import com.tapacross.sns.crawler.daum.cafe.entity.TBCrawlCycle
import com.tapacross.sns.crawler.daum.cafe.entity.TBCrawlSite2
import groovy.transform.TypeChecked

interface CrawlerDao {
	def TBArticle selectArticle(TBArticle article) throws DataAccessException
	def TBCrawlSite2 selectDaumCrawlSite(String url) throws DataAccessException 
	def int insertSearchContent(ITBArticle article) throws DataAccessException
	def TBCrawlCycle insertCrawlCycleInfo(TBCrawlCycle crawlCycle)
}

@TypeChecked
@Repository
class DaumPrivateCafeCrawlerDao implements CrawlerDao {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public TBCrawlSite2 selectDaumCrawlSite(String url)
	throws DataAccessException {
		def session = hibernateTemplate.getSessionFactory().openSession()
		try {
			return (TBCrawlSite2) session.createCriteria(TBCrawlSite2.class)
			.add(Restrictions.eq("siteType", "C"))
			.add(Restrictions.eq("status", "T"))
			.add(Restrictions.eq("priority", 101))
			.add(Restrictions.eq("siteSubCate", "다음"))
			.add(Restrictions.eq("siteCategory", "카페"))
			.add(Restrictions.eq("url", url))
			.uniqueResult()
		} finally {
			session.close()
		}
	}
	
	@Override
	public TBArticle selectArticle(TBArticle article) throws DataAccessException {
		return (TBArticle) sqlSessionTemplate.selectOne(
				"sql.resources.daumprivatecafedao.selectArticle", article);
	}
	
	@Override
	public int insertSearchContent(ITBArticle article) throws DataAccessException {
		return sqlSessionTemplate.insert(
				"sql.resources.daumprivatecafedao.insertArticle", article);
	}

	@Override
	public TBCrawlCycle insertCrawlCycleInfo(TBCrawlCycle crawlCycle) {
		return (TBCrawlCycle) hibernateTemplate.save(crawlCycle);
	}

}

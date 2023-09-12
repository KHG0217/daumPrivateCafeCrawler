package com.tapacross.sns.crawler.daum.cafe.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 수집기의 방문주기를 기록하기 위한 테이블
 * DB187에 존재
 * @author admin
 *
 */
@Entity
@Table(name = "TB_CRAWL_CYCLE")
public class TBCrawlCycle implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "START_DATE")
	private String startDate;
	
	@Id
	@Column(name = "END_DATE")
	private String endDate;
	
	@Id
	@Column(name = "CRAWLER_NAME")
	private String crawlerName;
	
	@Id
	@Column(name = "PRIORITY")
	private int priority;

	@Column(name = "ARTICLE_COUNT")
	private int articleCount;
	
	@Column(name = "VISIT_COUNT")
	private int visitCount;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getArticleCount() {
		return articleCount;
	}
	
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	
	public int getVisitCount() {
		return visitCount;
	}
	
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("startDate=").append(startDate);
		sb.append(", endDate=").append(endDate);
		sb.append(", crawlerName=").append(crawlerName);
		sb.append(", priority=").append(priority);
		sb.append(", articleCount=").append(articleCount);
		sb.append(", visitCount=").append(visitCount);
		return sb.toString();
	}
}

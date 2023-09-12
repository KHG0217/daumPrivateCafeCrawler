package com.tapacross.sns.crawler.daum.cafe.thrift;

import com.tapacross.sns.util.NumberUtil;

/**
 * snsContent 클래스를 snsContent2로 변환하는 클래스
 * snsContent2를 snsContent로 역변환 가능
 * ex) new SNSContentAdapter(snsContent).toSNSContent2()
 * @author admin
 *
 */
public class SNSContentAdapter {
	private SNSContent content1 = new SNSContent();
	private SNSContent2 content2 = new SNSContent2();
	public SNSContentAdapter(SNSContent content) {
		if (content != null) {
			content2.setArticleId(content.getArticleId());
			content2.setWriterId(content.getWriterId());
			content2.setCrawlDate(content.getCrawlDate());
			content2.setTitle(content.getTitle());
			content2.setContent(content.getContent());
			content2.setRT(content.getRT());
			content2.setReplyId(content.getReplyId());
			content2.setReplyWriterId(content.getReplyWriterId());
			content2.setRE(content.getRE());
			content2.setAddress(content.getAddress());
			content2.setLat(String.valueOf(content.getLat()));
			content2.setLng(String.valueOf(content.getLng()));
			content2.setCreateDate(content.getCreateDate());
			content2.setSiteType(content.getSiteType());
			content2.setVia(content.getVia());
			content2.setAddressStatus(null);
			content2.setMention(content.getMention());
			content2.setArticleIdOld(content.getArticleIdOld());
			content2.setUrl(content.getUrl());
			content2.setSiteSubType(content.getSiteSubType());
			content2.setContentId(content.getContentId());
			content2.setAddress2(null);
			content2.setSiteId(content.getSiteId());
			content2.setSiteCode(content.getSiteCode());
			content2.setWriterName(content.getWriterName());
			content2.setCollectedBy(content.getTwitterCrawlType());
			
			content2.setRetweetCount(content.getRetweetCount());
			content2.setFollowCount((long) content.getFollowCount());
			content2.setSiteName(content.getSiteName());
			content2.setPicture(content.getPicture());
			content2.setScreenName(content.getScreenName());
			content2.setSiteCategory(content.getSiteCategory());
			content2.setHitCount(0L);
			content2.setCommentCount(0L);
			content2.setLikeCount(0L);
		}
	}
	public SNSContentAdapter(SNSContent2 content) {
		if (content != null) {
			content1.setArticleId(NumberUtil.toPrimitive(content.getArticleId()));
			content1.setWriterId(content.getWriterId());
			content1.setCrawlDate(content.getCrawlDate());
			content1.setTitle(content.getTitle());
			content1.setContent(content.getContent());
			content1.setRT(content.getRT());
			content1.setReplyId(content.getReplyId());
			content1.setReplyWriterId(content.getReplyWriterId());
			content1.setRE(content.getRE());
			content1.setAddress(content.getAddress());
			content1.setLat(NumberUtil.stringToDouble(content.getLat()));
			content1.setLng(NumberUtil.stringToDouble(content.getLng()));
			content1.setCreateDate(content.getCreateDate());
			content1.setSiteType(content.getSiteType());
			content1.setVia(content.getVia());
			content1.setMention(content.getMention());
			content1.setArticleIdOld(NumberUtil.toPrimitive(content.getArticleIdOld()));
			content1.setUrl(content.getUrl());
			content1.setSiteSubType(content.getSiteSubType());
			content1.setContentId(content.getContentId());
			content1.setSiteId(content.getSiteId());
			content1.setSiteCode(NumberUtil.toPrimitive(content.getSiteCode()));
			content1.setWriterName(content.getWriterName());
			content1.setTwitterCrawlType(content.getCollectedBy());
			
			content1.setRetweetCount(NumberUtil.toPrimitive(content.getRetweetCount()));
			content1.setFollowCount((int) NumberUtil.toPrimitive(content.getFollowCount()));
			content1.setSiteName(content.getSiteName());
			content1.setPicture(content.getPicture());
			content1.setScreenName(content.getScreenName());
			content1.setSiteCategory(content.getSiteCategory());
		}
	}
	public SNSContent toSNSContent() {
		return content1;
	}
	public SNSContent2 toSNSContent2() {
		return content2;
	}
}
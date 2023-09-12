package com.tapacross.sns.crawler.daum.cafe.entity;

import com.tapacross.sns.crawler.daum.cafe.thrift.SNSContent;
import com.tapacross.sns.crawler.daum.cafe.thrift.SNSContent2;
import com.tapacross.sns.crawler.daum.cafe.thrift.SNSInfo;

/**
 * SNSContent 클래스를 TBArticleSearchCommunity 클래스로 변환해주는 클래스 파라미터로 SNSContent 객체를 받았지만
 * TBAritlcSearchCommunity 엔티티로 DB에 저장할 시에 사용할 수잇다.
 * 
 * @author cuckoo03
 *
 */
public class TBArticleCommunityAdapter implements ITBArticleAdapter {
	private TBArticleCommunity tbArticle;

	public TBArticleCommunityAdapter(SNSContent snsContent, String collectedBy) {
		this.tbArticle = new TBArticleCommunity();
		tbArticle.setAddress(snsContent.getAddress());
		tbArticle.setArticleId(snsContent.getArticleId());
		tbArticle.setSiteIdOld(new Long(snsContent.getSiteId()));
		tbArticle.setWriterId(snsContent.getWriterId());
		tbArticle.setTitle(snsContent.getTitle());
		tbArticle.setBody(snsContent.getContent());
		//tbArticle.setRt("F"); 지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setReplyId(snsContent.getReplyId()); 지금 네이버카페 때문에 
		tbArticle.setReplyWriterId(null);
		//tbArticle.setRe("F");지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setAddress(null);지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setLat("0"); 
		tbArticle.setLng("0");
		tbArticle.setCreateDate(snsContent.getCreateDate());
		tbArticle.setSiteType(snsContent.getSiteType());
		tbArticle.setViaUrl(snsContent.getVia());
		//tbArticle.setAddressStatus("N");지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setMention("N");지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setArticleIdOld(null);
		tbArticle.setUrl(snsContent.getUrl());
		tbArticle.setReputationType("E");
		tbArticle.setSiteSubType(snsContent.getSiteSubType());
		tbArticle.setContentId(snsContent.getContentId());
		tbArticle.setAddress2(null);
		tbArticle.setSiteId(snsContent.getSiteId());
		tbArticle.setSiteCode(Long.parseLong(snsContent.getSiteId()));
		//tbArticle.setWriterName(snsContent.getWriterId()); 지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setCollectedBy(collectedBy);
		tbArticle.setRtCount(0L);
		tbArticle.setFollowerCount(0L);
		tbArticle.setSiteName(snsContent.getSiteName());
		tbArticle.setPicture("");
		tbArticle.setScreenName(snsContent.getScreenName());
		tbArticle.setSiteCategory(snsContent.getSiteCategory());
		// hit count, 컬럼이 thrift에 없어 다른컬럼(picture)으로 값 전달
		tbArticle.setHitCount(Long.parseLong(snsContent.getPicture())); 
		// like count
		tbArticle.setLikeCount(0L);
		// comment count, 컬럼이 thrift에 없어 다른컬럼으로 값 전달
		tbArticle.setCommentCount(Long.parseLong(snsContent.getReplyId()));
		tbArticle.setIndexName("IDX_COMM_URL_"
				+ snsContent.getCreateDate().substring(2, 6));
		tbArticle.setTableName("TB_ARTICLE_SEARCH_COMM_"
				+ snsContent.getCreateDate().substring(2, 6));
		tbArticle.setSequenceName("SEQ_COMM_"
				+ snsContent.getCreateDate().substring(2, 6));
	}
	
	public TBArticleCommunityAdapter(SNSContent2 snsContent, String collectedBy) {
		this.tbArticle = new TBArticleCommunity();
		tbArticle.setAddress(snsContent.getAddress());
		tbArticle.setArticleId(snsContent.getArticleId());
		tbArticle.setSiteIdOld(new Long(snsContent.getSiteId()));
		tbArticle.setWriterId(snsContent.getWriterId());
		tbArticle.setTitle(snsContent.getTitle());
		tbArticle.setBody(snsContent.getContent());
		//tbArticle.setRt("F"); 지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setReplyId(snsContent.getReplyId()); 지금 네이버카페 때문에 
		tbArticle.setReplyWriterId(null);
		//tbArticle.setRe("F");지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setAddress(null);지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setLat("0"); 
		tbArticle.setLng("0");
		tbArticle.setCreateDate(snsContent.getCreateDate());
		tbArticle.setSiteType(snsContent.getSiteType());
		tbArticle.setViaUrl(snsContent.getVia());
		//tbArticle.setAddressStatus("N");지금 네이버카페때문에 잠시 주석처리 
		//tbArticle.setMention("N");지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setArticleIdOld(null);
		tbArticle.setUrl(snsContent.getUrl());
		tbArticle.setReputationType("E");
		tbArticle.setSiteSubType(snsContent.getSiteSubType());
		tbArticle.setContentId(snsContent.getContentId());
		tbArticle.setAddress2(null);
		tbArticle.setSiteId(snsContent.getSiteId());
		tbArticle.setSiteCode(Long.parseLong(snsContent.getSiteId()));
		//tbArticle.setWriterName(snsContent.getWriterId()); 지금 네이버카페때문에 잠시 주석처리 
		tbArticle.setCollectedBy(collectedBy);
		tbArticle.setRtCount(0L);
		tbArticle.setFollowerCount(0L);
		tbArticle.setSiteName(snsContent.getSiteName());
		tbArticle.setPicture("");
		tbArticle.setScreenName(snsContent.getScreenName());
		tbArticle.setSiteCategory(snsContent.getSiteCategory());
		tbArticle.setHitCount(snsContent.getHitCount()); 
		tbArticle.setLikeCount(snsContent.getLikeCount());
		tbArticle.setCommentCount(snsContent.getCommentCount());
		tbArticle.setTableName("TB_ARTICLE_SEARCH_COMM_"
				+ snsContent.getCreateDate().substring(2, 6));
		tbArticle.setSequenceName("SEQ_COMM_"
				+ snsContent.getCreateDate().substring(2, 6));
	}

	public SNSInfo toSNSInfo() {
		// 상세 구현 필요(필드에 값을 할당해야 함)
		return new SNSInfo();
	}

	public TBArticle toValue() {
		return (TBArticle) this.tbArticle.clone();
	}
}

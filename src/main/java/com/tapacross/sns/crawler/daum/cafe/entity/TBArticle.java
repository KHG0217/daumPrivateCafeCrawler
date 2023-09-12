package com.tapacross.sns.crawler.daum.cafe.entity;

/**
 * mybatis search table entity class
 * 수집테이블의 공통 엔티티 클래스
 * 현재는 채널별로 TBArticle관련 테이블이 있지만 추후에 하나로 통일할 예정이다.
 * 
 * @author cuckoo03
 *
 */
public class TBArticle implements Cloneable, ITBArticle {
	private Long seq;
	private Long articleId;
	private Long  siteIdOld;
	private String writerId;
	private String crawlDate;
	private String title;
	private String body;
	private String rt;
	private String replyId;
	private String replyWriterId;
	private String re;
	private String address;
	private String lat;
	private String lng;
	private String createDate;
	private String siteType;
	private String viaUrl;
	private String addressStatus;
	private String mention;
	private Long articleIdOld;
	private String url;
	private String reputationType;
	private String siteSubType;
	private String contentId;
	private String address2;
	private String siteId;
	private Long siteCode;
	private String writerName;
	private String collectedBy;
	private Long rtCount;
	private Long followerCount;
	private String siteName;
	private String picture;
	private String screenName;
	private String siteCategory;
	private Long hitCount;
	private Long commentCount;
	private String tableName;
	private String sequenceName;
	private Long likeCount;
	private Long dislikeCount;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getSiteIdOld() {
		return siteIdOld;
	}

	public void setSiteIdOld(Long sitieIdOld) {
		this.siteIdOld = sitieIdOld;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getReplyWriterId() {
		return replyWriterId;
	}

	public void setReplyWriterId(String replyWriterId) {
		this.replyWriterId = replyWriterId;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getViaUrl() {
		return viaUrl;
	}

	public void setViaUrl(String viaUrl) {
		this.viaUrl = viaUrl;
	}

	public String getAddressStatus() {
		return addressStatus;
	}

	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}

	public String getMention() {
		return mention;
	}

	public void setMention(String mention) {
		this.mention = mention;
	}

	public Long getArticleIdOld() {
		return articleIdOld;
	}

	public void setArticleIdOld(Long articleIdOld) {
		this.articleIdOld = articleIdOld;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReputationType() {
		return reputationType;
	}

	public void setReputationType(String reputationsType) {
		this.reputationType = reputationsType;
	}

	public String getSiteSubType() {
		return siteSubType;
	}

	public void setSiteSubType(String siteSubType) {
		this.siteSubType = siteSubType;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Long getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(Long siteCode) {
		this.siteCode = siteCode;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getCollectedBy() {
		return collectedBy;
	}

	public void setCollectedBy(String collectedBy) {
		this.collectedBy = collectedBy;
	}

	public Long getRtCount() {
		return rtCount;
	}

	public void setRtCount(Long rtCount) {
		this.rtCount = rtCount;
	}

	public Long getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Long followerCount) {
		this.followerCount = followerCount;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getSiteCategory() {
		return siteCategory;
	}

	public void setSiteCategory(String siteCategory) {
		this.siteCategory = siteCategory;
	}

	public Long getHitCount() {
		return hitCount;
	}

	public void setHitCount(Long hitCount) {
		this.hitCount = hitCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
	
	public Long getDislikeCount() {
		return dislikeCount;
	}
	
	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	@Override
	public String toString() {
		return "articleId=" + articleId + ", siteIdOld=" + siteIdOld
				+ ", writerId=" + writerId + ", crawlDate=" + crawlDate
				+ ", title=" + title + ", body=" + body + ", rt=" + rt
				+ ", replyId=" + replyId + ", replyWriterId=" + replyWriterId
				+ ", re=" + re + ", address=" + address + ", lat=" + lat
				+ ",lng=" + lng + ", createDate=" + createDate + ", siteType="
				+ siteType + ", viaUrl=" + viaUrl + ", addressStatus="
				+ addressStatus + ", mention=" + mention + ", articleIdOld="
				+ articleIdOld + ", url=" + url + ", reputationType="
				+ reputationType + ", siteSubType=" + siteSubType
				+ ", contentId=" + contentId + ", address2=" + address2
				+ ", siteId=" + siteId + ", siteCode=" + siteCode
				+ ", writerName=" + writerName + ", collectedBy=" + collectedBy
				+ ", rtCount=" + rtCount + ", followerCount=" + followerCount
				+ ", siteName=" + siteName + ", picture=" + picture
				+ ", screenName=" + screenName + ", siteCategory="
				+ siteCategory + ", hitCount=" + hitCount + ", commentCount="
				+ commentCount + ", likeCount=" + likeCount
				+ ", dislikeCount=" + dislikeCount
				+ ", tableName=" + tableName + ", sequenceName=" + sequenceName;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
}

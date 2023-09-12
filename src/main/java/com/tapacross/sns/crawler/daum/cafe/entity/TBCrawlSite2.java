package com.tapacross.sns.crawler.daum.cafe.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 하이버네이트용 테이블 엔티티 클래스, 주로 검색쿼리에 쓰임.
 * @author cuckoo03
 *
 */
@Entity
@Table(name = "TB_CRAWL_SITE")
public class TBCrawlSite2 implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	public TBCrawlSite2() {
	}
	
	/**
	 * 쿼리조회를 하기위해 변수를 설정하는 생성자
	 * @param siteType
	 * @param siteId
	 */
	public TBCrawlSite2(String siteType, String siteId) {
		this.siteType = siteType;
		this.siteId = siteId;
	}

	@Column(name = "SITE_ID_OLD")
	private String siteIdOld;

	@Column(name = "SITE_NAME")
	private String siteName;

	@Id
	@Column(name = "SITE_TYPE", columnDefinition = "CHAR(1)")
	private String siteType;

	@Column(name = "FOLLOWING")
	private Integer following;

	@Column(name = "FOLLOWER")
	private Integer follower;

	@Column(name = "LISTED")
	private Integer listed;

	@Column(name = "TWEET")
	private Integer tweet;

	@Column(name = "PICTURE")
	private String picture;

	@Column(name = "BIO")
	private String bio;

	@Column(name = "WEB")
	private String web;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "STATUS", columnDefinition = "CHAR(1)")
	private String status;

	@Column(name = "URL")
	private String url;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "CREATE_USER_DATE", columnDefinition = "CHAR(14)")
	private String createUserDate;

	@Column(name = "CREATE_USER_ID")
	private String createUserId;

	@Column(name = "UPDATE_USER_DATE", columnDefinition = "CHAR(14)")
	private String updateUserDate;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "SCREEN_NAME")
	private String screenName;

	@Id
	@Column(name = "SITE_ID")
	private String siteId;

	@Column(name = "SITE_CODE")
	private Long siteCode;

	@Column(name = "SITE_SUB_CATE")
	private String siteSubCate;

	@Column(name = "LAST_UPDATED_DATE", columnDefinition = "CHAR(14)")
	private String lastUpdatedDate;

	@Column(name = "HIT_COUNT_FROM_UPDATED")
	private Integer hitCountFromUpdated;

	@Column(name = "HIT_COUNT_TOTAL")
	private Integer hitCountTotal;

	@Column(name = "IS_VALID_LANG", columnDefinition = "CHAR(1)")
	private String isValidLang;

	@Column(name = "SITE_CATEGORY")
	private String siteCategory;

	@Column(name = "PUBLISHER_TYPE", columnDefinition = "CHAR(1)")
	private String publisherType;

	public String getSiteIdOld() {
		return siteIdOld;
	}

	public void setSiteIdOld(String siteIdOld) {
		this.siteIdOld = siteIdOld;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	public Integer getFollower() {
		return follower;
	}

	public void setFollower(Integer follower) {
		this.follower = follower;
	}

	public Integer getListed() {
		return listed;
	}

	public void setListed(Integer listed) {
		this.listed = listed;
	}

	public Integer getTweet() {
		return tweet;
	}

	public void setTweet(Integer tweet) {
		this.tweet = tweet;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCreateUserDate() {
		return createUserDate;
	}

	public void setCreateUserDate(String createUserDate) {
		this.createUserDate = createUserDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserDate() {
		return updateUserDate;
	}

	public void setUpdateUserDate(String updateUserDate) {
		this.updateUserDate = updateUserDate;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Long getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(Long siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteSubCate() {
		return siteSubCate;
	}

	public void setSiteSubCate(String siteSubCate) {
		this.siteSubCate = siteSubCate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getHitCountFromUpdated() {
		return hitCountFromUpdated;
	}

	public void setHitCountFromUpdated(Integer hitCountFromUpdated) {
		this.hitCountFromUpdated = hitCountFromUpdated;
	}

	public Integer getHitCountTotal() {
		return hitCountTotal;
	}

	public void setHitCountTotal(Integer hitCountTotal) {
		this.hitCountTotal = hitCountTotal;
	}

	public String getIsValidLang() {
		return isValidLang;
	}

	public void setIsValidLang(String isValidLang) {
		this.isValidLang = isValidLang;
	}

	public String getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteCategory() {
		return siteCategory;
	}

	public void setSiteCategory(String siteCategory) {
		this.siteCategory = siteCategory;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("siteIdOld=").append(siteIdOld);
		sb.append(", siteName=").append(siteName);
		sb.append(", siteType=").append(siteType);
		sb.append(", following=").append(following);
		sb.append(", follower=").append(follower);
		sb.append(", listed=").append(listed);
		sb.append(", tweet=").append(tweet);
		sb.append(", picture=").append(picture);
		sb.append(", bio=").append(bio);
		sb.append(", web=").append(web);
		sb.append(", location=").append(location);
		sb.append(", status=").append(status);
		sb.append(", url=").append(url);
		sb.append(", priority=").append(priority);
		sb.append(", createUserDate=").append(createUserDate);
		sb.append(", crateUserId=").append(createUserId);
		sb.append(", updateUserDate=").append(updateUserDate);
		sb.append(", updateUserId=").append(updateUserId);
		sb.append(", screenName=").append(screenName);
		sb.append(", siteId=").append(siteId);
		sb.append(", siteCode=").append(siteCode);
		sb.append(", siteSubCate=").append(siteSubCate);
		sb.append(", lastUpdatedDate=").append(lastUpdatedDate);
		sb.append(", hitCountFromUpdated=").append(hitCountFromUpdated);
		sb.append(", hitCountTotal=").append(hitCountTotal);
		sb.append(", isValidLang=").append(isValidLang);
		sb.append(", siteCategory=").append(siteCategory);
		sb.append(", publisherType=").append(publisherType);
		return sb.toString();
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
}

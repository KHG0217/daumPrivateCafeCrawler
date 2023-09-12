package com.tapacross.sns.crawler.daum.cafe.thrift;

import java.util.List;

/**
 * snsInfo 클래스를 대체하기 위한 클래스. snsInfo 클래스를 사용하는 코드는 변경해야 한다.
 * snsInfo 클래스는 점차 제거할 계획.
 * @author admin
 *
 */
public class SNSInfo2 {

	private String siteId;
	private String siteType;
	private String url;
	private String siteName;
	private int follower;
	private int following;
	private int listed;
	private int tweet;
	private String picture;
	private String bio;
	private String web;
	private String location;
	private String screenName;
	private List<String> ids;
	private List<SNSContent2> snsContent;
	private String followerList;
	private String followingList;
	private long siteCode;
	private String lastUpdatedDate;
	private String addressCrawlType;
	private int statusCode;
	private String status;
	private String siteCategory;
	private int priority;
	private String isValidLang;
	private String siteSubCate;
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public int getListed() {
		return listed;
	}
	public void setListed(int listed) {
		this.listed = listed;
	}
	public int getTweet() {
		return tweet;
	}
	public void setTweet(int tweet) {
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
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<SNSContent2> getSnsContent() {
		return snsContent;
	}
	public void setSnsContent(List<SNSContent2> snsContent) {
		this.snsContent = snsContent;
	}
	public String getFollowerList() {
		return followerList;
	}
	public void setFollowerList(String followerList) {
		this.followerList = followerList;
	}
	public String getFollowingList() {
		return followingList;
	}
	public void setFollowingList(String followingList) {
		this.followingList = followingList;
	}
	public long getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(long siteCode) {
		this.siteCode = siteCode;
	}
	public String getLastUpdateDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
	}
	public String getAddressCrawlType() {
		return addressCrawlType;
	}
	public void setAddressCrawlType(String addressCrawlType) {
		this.addressCrawlType = addressCrawlType;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getSiteCategory() {
		return siteCategory;
	}
	public void setSiteCategory(String siteCategory) {
		this.siteCategory = siteCategory;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getIsValidLang() {
		return isValidLang;
	}
	public void setIsValidLang(String isValidLang) {
		this.isValidLang = isValidLang;
	}
	public String getSiteSubCate() {
		return siteSubCate;
	}
	public void setSiteSubCate(String siteSubCate) {
		this.siteSubCate = siteSubCate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	  
}

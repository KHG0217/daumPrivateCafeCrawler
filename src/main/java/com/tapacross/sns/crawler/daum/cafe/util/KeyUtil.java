package com.tapacross.sns.crawler.daum.cafe.util;

import com.esmedia.log.LoggerWrapper;
import com.tapacross.sns.crawler.daum.cafe.thrift.SNSContent;

public class KeyUtil {
	
	/**
	 * Extract hashkey from article id
	 * @param articleId
	 * @return
	 */
	public static int extractHashKeyFromArticleId( long articleId ) {
		 return Integer.parseInt((Long.toString(articleId)).substring(5, 15));
	}

	/**
	 * 
	 * @param date '201812211207'  댓글 수집시 초단위 수집 불가시 부족한 길이만큼 '0' 추가
	 * @return 20181221120700
	 */
	public static String unityDate(String date) {
		 
		while(date.length() < 14) {
			date += "0";
		}
		return date;
	}
	
	
	/**
	 *
	 * snsContent의 값을 기준으로 채널별로 아티클 ID를 생성한다. 파싱된 content의 길이는 html 파서에 따라
	 * 달라질 수 있으므로 같은 게시물이라도 같은 아티클 ID가 생성됨을 보장하지는 않는다. 
	 * 아티클ID를 생성하는 규칙은 기존에 이미 만들어진 아티클ID와의 호환성을 유지해야 하기 때문에 변경에 유의해야 한다. 
	 * @param snsContent
	 * @return
	 */
	public static long makeArticleIdFromSNSContent(SNSContent snsContent) {

		String createDate = unityDate(snsContent.getCreateDate());
		String siteType = snsContent.getSiteType();
		String contentID = snsContent.getContentId();
		String writerID = snsContent.getWriterId();
		long siteCode = snsContent.getSiteCode();
		long completeByte = 0;
		String contentTitle = snsContent.getTitle();
		String contentBody = snsContent.getContent();
		String url = snsContent.getUrl();
		String commentId = snsContent.getReplyId();
		try {
			int upperByte = 0;
			
			int year = Integer.parseInt(createDate.substring(0, 4));
			int month = Integer.parseInt(createDate.substring(4, 6));
			int day = Integer.parseInt(createDate.substring(6, 8));
			int hour = Integer.parseInt(createDate.substring(8, 10));
			int min = Integer.parseInt(createDate.substring(10, 12));
			int sec = Integer.parseInt(createDate.substring(12, 14));

			upperByte += sec;
			upperByte += min * 60;
			upperByte += hour * 60 * 60;
			upperByte += day * 60 * 60 * 24;
			upperByte += month * 60 * 60 * 24 * 31;
			upperByte += (year - 2010) * 60 * 60 * 24 * 31 * 12;

			int lowerByte = 0;

			if ("T".equalsIgnoreCase(siteType)) {
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + createDate).toLowerCase().hashCode();
			} else if ("F".equalsIgnoreCase(siteType)) {
				// 재방문할 경우에도 동일한 키가 생성되고 본문과 댓글의 아티클키가 동일하게 유지되게 하려면
				// 변경되야 하는 키 생성 방식
				// siteType+contentId(siteid+postid)+writerId+siteId(해당글작성자)+createDate
				lowerByte = (siteType + "|" + contentID + "|" + siteCode + "|" + url)
						.toLowerCase().hashCode();
			} else if ("R".equalsIgnoreCase(siteType)) {
				// 재방문할 경우에도 동일한 키가 생성되고 본문과 댓글의 아티클키가 동일하게 유지되게 하려면
				// 변경되야 하는 키 생성 방식(현재는 불가)
				// siteType+contentId(createdate+url)+siteId(작성자)+
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + contentTitle + "|" + contentBody + "|" + createDate)
						.toLowerCase().hashCode();
			} else if ("M".equalsIgnoreCase(siteType)) {
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + contentTitle + "|" + contentBody + "|" + createDate)
						.toLowerCase().hashCode();
				// massmedia comment
				// media contentId =
				// String.valueOf(원문.getCreateDate().hashCode()
				//+ 원문.getTitle().hashCode()
				//+ 원문.getContent().hashCode()
			} else if ("N".equalsIgnoreCase(siteType)) { 
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + contentTitle + "|" + contentBody + "|" + createDate)
						.toLowerCase().hashCode();
			} else if ("C".equalsIgnoreCase(siteType)) {
				lowerByte = (siteType + "|" + url).toLowerCase().hashCode();
				// 공개 카페 게시물은 KeyUtil.makeArticleIdFromSNSContent룰 사용
				// 비공개 카페 게시물은 KeyUtil.makeArticleIdFromSNSContent를 사용
				// 대표 커뮤니티 게시물은 KeyUtil.makeArticleId를 사용
			} else if("D".equalsIgnoreCase(siteType)) {
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|" + contentBody)
						.toLowerCase().hashCode();
			} else if ("I".equalsIgnoreCase(siteType)) {
				// 재방문할 경우에도 동일한 키가 생성되고 본문과 댓글의 아티클키가 동일하게 유지되게 하려면
				// 변경되야 하는 키 생성 방식(현재는 불가)
				// siteType+contentId(createdate+url)+writerId(원작성자)+siteId(해당글작성자)+
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + contentTitle + "|" + contentBody + "|"
						+ createDate + "|" + url).toLowerCase().hashCode();
			} else if ("V".equalsIgnoreCase(siteType)) {
				lowerByte = (siteType + "|" + url).toLowerCase().hashCode();
			} else if ("W".equalsIgnoreCase(siteType)) { // 동영상 댓글 site_type = 'W'
				// comment_id가 제공되지 않을 경우 comment_id 생성법
//				lowerByte = (siteType + "|" + url + "|" + siteID).toLowerCase().hashCode();
				
				lowerByte = (siteType + "|" + commentId).toLowerCase().hashCode();
			} else {
				siteType = "Z";
				lowerByte = (siteType + "|" + contentID + "|" + writerID + "|"
						+ siteCode + "|" + contentTitle + "|" + contentBody + "|" + createDate)
						.toLowerCase().hashCode();
			}

			completeByte = ((long) lowerByte) << 32 >>> 32;

			completeByte += ((long) upperByte) << 32;

		} catch (Exception e) {
			LoggerWrapper.printStackTrace("makehash", e);

		}

		return completeByte;
	}
	
	public static String extractContentIdFromStatus( String status ) { 
		return status.split("/")[5];
	}

	public static String extractScreenNameFromStatus( String status ) { 
		return status.split("/")[3];
	}

		/**
		 * 커뮤니티 원문 전용 Article_id 생성
		 * url은 url끝에 문자열null이 포함되어야 한다. 기존에 null을 포함해서
		 * 만들어진 아티글ID와 같은 규칙을 유지해야 하기 때문이다.
		 * ex)http://theqoo.net/index.php?mid=review&filter_mode=normal&document_srl=406354269null
		 * @param create_date
		 * @param url
		 * @param site_id
		 * @return
		 */
		public static long makeArticleId(String create_date, String url, String site_id) {
			int upperByte = 0;
			int year = 0;
			int month = 0;
			int day = 0;
			int hour = 0;
			int min = 0;
			int sec = 0;
			int lowerByte = 0;
			long completeByte;
			upperByte = 0;
			year = Integer.parseInt(create_date.substring(0, 4));
			month = Integer.parseInt(create_date.substring(4, 6));
			day = Integer.parseInt(create_date.substring(6, 8));
			hour = Integer.parseInt(create_date.substring(8, 10));
			min = Integer.parseInt(create_date.substring(10, 12));
			sec = Integer.parseInt(create_date.substring(12, 14));
			upperByte += sec;
			upperByte += min * 60;
			upperByte += hour * 60 * 60;
			upperByte += day * 60 * 60 * 24;
			upperByte += month * 60 * 60 * 24 * 31;
			upperByte += (year - 2010) * 60 * 60 * 24 * 31 * 12;
			lowerByte = 0;
			lowerByte = (site_id + "|" + url).toLowerCase().hashCode();
			completeByte = ((long) lowerByte) << 32 >>> 32;
			completeByte += ((long) upperByte) << 32;

			return completeByte;
		}
		
		/**
		 * 수집 게시물의 contentId 를 생성해주는 메서드
		 * 추후에는 팩토리 클래스를 이용하여 채널별로 생성하는 것이 코드유지보수나 안정성 면에서 낫다.
		 * @param siteType
		 * @param siteId
		 * @param body
		 * @param url
		 * @return
		 */
		public static long makeContentId(String siteType, String siteId, String body, String url) {
			return 0l;
		}

}



package com.tapacross.sns.crawler.daum.cafe.util;

import java.util.ArrayList;

import com.esmedia.log.LoggerWrapper;
import com.tapacross.sns.thrift.SNSInfo;

public class KPIUtil {

	public static String[] getKeywords() {
		String[] keywords = {
			//총선
//			"박정근","박근혜","정봉주","안길수","고승덕","박희태","한명숙","노무현","박영선","안철수","이명박"
//			,"민주통합당","한나라당","진보신당","국민참여당","민주노동당","개혁국민정당","창조한국당","사회당","자유선진당"
//			,"민주당","새누리당"
//			,"전여옥","이동관","조윤선","정세균","나경원","김인원","김택수","남요원","송태경","유선호","정호준","이재오"
//			,"천호선","이성헌","우상호","정두언","이동거","김영호","김재홍","김진욱","이근호","이준 ","조찬우","강석호"
//			,"김성동","김혜준","김유정","박재웅","유용화","이규범","이준길","정명수","정세현","정청래","강용석","정몽준"
//			,"이계인","맹정주","정동기","허준영","정동영","전현희","길정우","김해진","박선규","차영","양재호","인재근"
//			,"총선","국회의원"
			//Issue
				
//			"봇","_bot","_b","안철수",
			"박근혜","문재인","이정희"
//			,"폭스바겐","폭스바갠","volkswagen","시로코","Scirocco","티구안","tiguan","벨로스터"
//			,"제네시스","혼다","아우디","honda","audi"
		
		};
		return keywords;
	}

	public static ArrayList<SNSInfo> makeFacebookSearchUrl( String keyword ) {
		
		SNSInfo snsInfo = new SNSInfo();
		ArrayList<SNSInfo> snsInfos = new ArrayList<SNSInfo>();
		String facebookSearchUrl = "https://graph.facebook.com/search?q="+keyword+"&type=post";
		snsInfo.setSiteType("F");
		snsInfo.setScreenName(keyword);
		snsInfo.setUrl(facebookSearchUrl);
		snsInfo.setSiteId("1414");
		snsInfos.add(snsInfo);
		LoggerWrapper.info("facebook Search URL add", facebookSearchUrl);
		
		return snsInfos;
		
	}
}

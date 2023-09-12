package com.tapacross.sns.crawler.daum.cafe.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tapacross.sns.crawler.daum.cafe.thrift.SNSContent2;
import com.tapacross.sns.crawler.daum.cafe.util.CollectedBy;
import com.tapacross.sns.crawler.daum.cafe.util.DateUtil;
import com.tapacross.sns.crawler.daum.cafe.util.ThreadUtil;

/**
 * 다음 폐쇄형카페 수집기
 * @author hyukg
 * @date 2023.08.22
 * @see - 해당 수집기는 크롬드라이버를 이용하며, 로그인후 해당 세션을 유지한 상태로 수집한다.
 * - 수집 주기가 빠를경우 계정이 block 될 수있으니 주의한다. (CRAWL_SLEEP 주기 1초 = BLOCK)
 * - 세션이 1일마다 끊어지는 현상 확인(23.08.30)
 * 	
 */
public class DaumPrivateCafeParser {
	private final int CRAWL_SLEEP = 10 * 1000;
	private WebDriver driver; 
	
	/**
	 * 인자값으로 chromedriver.exe의 경로를 읽어, 크롬드라이브를 실행한다.
	 * CAPTHCA가 발생할경우 수동으로 해제가 필요하므로 크롬드라이브 옵션의 headless는 사용하지 않는다.
	 * 현재 운영에서 사용하는 크롬드라이버 버전은 114.0.5735.199 이다. (2023.08.22)
	 * @param driverPath  
	 * 
	 */
	private void init(String driverPath) {
		System.out.println("init start");
		if(this.driver == null) {
			ChromeOptions options = new ChromeOptions();
	        options.addArguments("--incognito"); 
	        options.addArguments("--disable-cache"); 
	        options.addArguments("--disable-cookies"); 
			options.addArguments("disable-infobars");
			options.addArguments("--no-sandbox"); 
			options.addArguments("--disable-dev-shm-usage"); 
			options.addArguments("--disable-blink-features=AutomationControlled"); 
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
	        options.addArguments("Sec-Fetch-Site=same-origin");
	        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
	        
			String webDriverId = "webdriver.chrome.driver"; // 드라이버 ID
			System.setProperty(webDriverId, driverPath);
			try {
				this.driver = new ChromeDriver(options);
			} catch (Exception e) {
				System.out.println("driver new ChromeDriver(options Error)");
				e.printStackTrace();
				abort();
			}
		} else {
			System.out.println("driver is not null, abort and ReStart");
			abort();
		}
	}
	
	/**
	 * 현재 실행중인 driver를 종료한다.
	 */
	public void abort () {
		try {
			System.out.println("abort Start");
			if (this.driver != null) {
				System.out.println("driver NOT NULL");
				this.driver.close();
				System.out.println("driver CLOSE");
				this.driver.quit();
				System.out.println("driver QUIT");
				this.driver = null;
				System.out.println("driver NULL");
			} else {
				System.out.println("driver NULL");
			}
			System.out.println("abort End");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 다음 카페 아이디,비밀번호를 사용하여 로그인한다.
	 * 로그인후, 다른 곳에서 로그인시 계정 로그인이 풀릴수있으니 주의한다.
	 * Exception이 발생할 경우 false를 return한다.
	 * 
	 * @param id
	 * @param pwd
	 * @param driverPath
	 * @return loginflag
	 * @see abort처리는 DaumPrivateCafeCrawlerApp에서 처리한다.
	 */
	public boolean login(String id, String pwd, String driverPath) { 
		String loginUrl = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Flogins.daum.net%2Faccounts%2Fksso.do%3Frescue%3Dtrue%26url%3Dhttps%253A%252F%252Fwww.daum.net#login";
		boolean loginflag = true;	
		
		init(driverPath);
			
		if(driver == null) {
			loginflag =  false;
		}
			
		try {
			WebDriverWait wait = new WebDriverWait(this.driver,10); // 10초 대기 
			System.out.println("driver get login ");
			driver.get(loginUrl);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginId--1")));
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("driver get login end ");
			 
			WebElement idInput = this.driver.findElement(By.xpath("//*[@id=\"loginId--1\"]"));
			WebElement pwInput = this.driver.findElement(By.xpath("//*[@id=\"password--2\"]"));
			WebElement loginInput = this.driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div/form/div[4]/button[1]"));
			
			idInput.sendKeys(id);
			pwInput.sendKeys(pwd);
			loginInput.submit();	
			
			WebDriverWait captchaWait = new WebDriverWait(this.driver,180); // 3분대기 , 캡챠 활성시 수동해제
			captchaWait.until(ExpectedConditions.presenceOfElementLocated(By.id("wrapSearch")));
		}catch(TimeoutException e1) {
			e1.printStackTrace();
			System.out.println("check, if loginXpath has changed or page timeout or ID BLOCK");
			loginflag = false;
		}catch (Exception e) {
			e.printStackTrace();
			loginflag = false;
		}	
			
			return loginflag;
	}

	/**
	 * 다음 카페 아이디,비밀번호를 사용하여 다시 로그인한다.
	 * 로그인후, 다른 곳에서 로그인시 계정 로그인이 풀릴수있으니 주의한다.
	 * Exception이 발생할 경우 false를 return한다.
	 * 
	 * @param id
	 * @param pwd
	 * @see abort처리는 DaumPrivateCafeCrawlerApp에서 처리한다.
	 * @return reloginflag
	 */
	public boolean relogin(String id, String pwd) { 
		boolean reloginflag = true;	
		String loginUrl = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Flogins.daum.net%2Faccounts%2Fksso.do%3Frescue%3Dtrue%26url%3Dhttps%253A%252F%252Fwww.daum.net#login";
			WebDriverWait wait = new WebDriverWait(this.driver,10); // 10초 대기 
		try {
			System.out.println("driver get login ");
			driver.get(loginUrl);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginId--1")));
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("driver get login end ");
			 
			WebElement idInput = this.driver.findElement(By.xpath("//*[@id=\"loginId--1\"]"));
			WebElement pwInput = this.driver.findElement(By.xpath("//*[@id=\"password--2\"]"));
			WebElement loginInput = this.driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div/form/div[4]/button[1]"));
			
			idInput.sendKeys(id);
			pwInput.sendKeys(pwd);
			loginInput.submit();	
			
			WebDriverWait captchaWait = new WebDriverWait(this.driver,180); // 3분대기 , 캡챠 활성시 수동해제
			captchaWait.until(ExpectedConditions.presenceOfElementLocated(By.id("wrapSearch")));	
		}catch(TimeoutException e1) {
			e1.printStackTrace();
			System.out.println("check, if loginXpath has changed or page timeout or ID BLOCK");
			reloginflag = false;
		}catch (Exception e) {
			e.printStackTrace();
			reloginflag =  false;
		}
		
		return reloginflag;
	}
	
	/**
	 * 인자로 받은 카페홈 url에 접속하여, 최신게시판으로 이동후, 게시물 url을 파싱한다.
	 * 
	 * @param url
	 * @return list
	 */
	public List<String> extractArticle(String url) {
		final String DAUM_ABSOLUTE_PATH = "https://cafe.daum.net";
		ArrayList<String> list = new ArrayList<>();
		System.out.println("CRAWL_SLEEP : " + CRAWL_SLEEP);
		ThreadUtil.sleep(CRAWL_SLEEP);
		this.driver.get(url);
		this.driver.switchTo().frame(this.driver.findElement(By.tagName("iframe")));
		
		String homePageSource = this.driver.getPageSource();
		try {
			Document doc = Jsoup.parse(homePageSource);
			
			// 최신글게시판으로 이동
			String latestBoardLink = doc.select("#fldlink_recent_bbs").attr("href");
			this.driver.get(DAUM_ABSOLUTE_PATH + latestBoardLink);
			this.driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
			
			String latestBoardPageSource = this.driver.getPageSource();
			doc = Jsoup.parse(latestBoardPageSource);
					
			Elements trEls = doc.select("#article-list > tr");
			for(Element el : trEls) {
				 /* 공지글 제외 */
				 if(!el.select("tr.state_info").isEmpty()) {
					 continue;
				 }

				 String articleLink = el.select("tr > td.td_title > span > strong > span > a").attr("href");			 
				 String articleUrl = makeUrl(url, articleLink);		
				 
				 list.add(articleUrl);
			}
						
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 게시물의 url을 인자로 받아 Document source를 리턴한다.
	 * @param url
	 */
	public Document parseArticle(String url) {
		Document doc = null;
		try {
			ThreadUtil.sleep(CRAWL_SLEEP);
			this.driver.get(url);
			this.driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
			String pageSource = this.driver.getPageSource();		
			doc = Jsoup.parse(pageSource);
		}catch (UnhandledAlertException e1) { //alert 이벤트가 발생하였을 때 UnhandledAlertException 발생

            Alert alert = driver.switchTo().alert();
            
            String alertText = alert.getText();
        	ThreadUtil.sleep(500);
            if(alertText.contains("카페에 가입하시겠습니까?")) {
            	System.out.println("sign up cafe, url: " + url);
                alert.dismiss();
            } else {
            	System.out.println("check new alert case : " + alertText);
            	alert.dismiss();
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}	
	/**
	 * 게시물 페이지소스를 받아 게시물의 필요한 값을 파싱한다.
	 * @param url
	 * @return snsContent
	 */
	public SNSContent2 parseContent(Document doc ,String url) {
		SNSContent2 snsContent = new SNSContent2();
		String title = null;
		String writerId = null;
		String createDate = null;
		Long viewCount = null;
		String content = null;
		Long commentCount = null;
		
		try {			
			/* title */
			 title = doc.select("meta[property=og:title]").attr("content")
					 .replaceAll("\\uFFFD", "") // 유니코드 제거
					 .replaceAll("\\uFEFF", "") // 유니코드 제거
					 .trim();
			 snsContent.setTitle(title);
			 
			 /* content */
			 content = doc.select("#user_contents > p").text();		 
			 snsContent.setContent(content);
			 		 
			 /* createDate */
			 createDate = doc.select("#primaryContent > div.bbs_read_tit > div.info_desc > div.cover_info > span:nth-child(4)").text()
					 .replaceAll("[^0-9]", "");		
			 createDate ="20" + createDate + "00";
			 
			 snsContent.setCreateDate(createDate);
			 
			 /* writerId */
			 writerId = doc.select("#primaryContent > div.bbs_read_tit > div.info_desc > div.cover_info > a").text();
			 
			 // writerId가 없는 익명 존재
			 if (writerId.isEmpty()) {
				 writerId = "익명";
			 }
			 snsContent.setWriterId(writerId);
			 
			 /* viewCount */
			 viewCount = (long) Integer.parseInt(doc.select("#primaryContent > div.bbs_read_tit > div.info_desc > div.cover_info > span:nth-child(3)").text()
					 .replaceAll("[^0-9]", ""));
			 snsContent.setHitCount(viewCount);
			 
			 /* commentCount */
			 commentCount = (long) Integer.parseInt(doc.select("#primaryContent > div.bbs_read_tit > div.info_desc > div.cover_info > span:nth-child(5)").text()
					 .replaceAll("[^0-9]", ""));
			 snsContent.setCommentCount(commentCount);
			 
			 /* articleUrl */
			 snsContent.setUrl(url);
			 
			 /* ContentId */
			 snsContent.setContentId( String.valueOf(snsContent.getCreateDate().hashCode()
						+ snsContent.getTitle().hashCode()
						+ snsContent.getContent().hashCode()) );
			 /* siteType */
				// 2022/01 이후로 작성된 포탈카페 게시물은 커뮤니티에 속하므로 작성일자에 따라 사이트타입을 지정한다.
				Calendar createCal = DateUtil.getDatebyDayTime(createDate);
				Calendar cafeMigrationDate = DateUtil.getDatebyDayTime("20220101");
				if (createCal.getTimeInMillis() < cafeMigrationDate.getTimeInMillis()) { 
					snsContent.setSiteType("R");
				} else {
					snsContent.setSiteType("C");
				}
			 
			 /* siteSubeType*/
			 snsContent.setSiteSubType("다음");
			 
			 /* siteCategory*/
			 snsContent.setSiteCategory("카페");
			 
			 /* else */
			 snsContent.setLat(null);
			 snsContent.setLng(null);
			 snsContent.setSiteName("");
			 snsContent.setRetweetCount(0L);
			 snsContent.setFollowCount(0L);
			 snsContent.setLikeCount(0L);
			 snsContent.setCollectedBy(CollectedBy.CRAWL);		 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return snsContent;
	}

	/**
	 * 게시물을 수집하기 전, 로그인 세션이 끊어졌는지 확인후, 재 로그인을 시도한다.
	 * 재로그인은 최대 5번까지 시도한다.
	 * 재로그인 성공 여부에 따라 true /false 값을 반환한다.
	 * @param doc
	 * @param id
	 * @param pwd
	 * @return reLoginStatus
	 */
	public boolean validateAndRetryLogin(Document doc, String id, String pwd) {
		 String contentText = doc.select("#primaryContent > table > tbody > tr.pos_rel > td.cb.pos_rel > div").text();
		 boolean reLoginStatus = true;
		 if(contentText.contains("회원님은 아직 로그인을")) {			 
			reLoginStatus = relogin(id, pwd);		
			
			if(reLoginStatus == false) { 
				int num = 1;
				while (num < 6 && reLoginStatus == false) {
					System.out.println("retry Login num : " + num);
					reLoginStatus = relogin(id, pwd);
					num ++;
				}
			}		 
		 }
		return reLoginStatus;
		
	}

	/**
	 * 게시물을 수집하기 전, 해당 게시물 읽는 권한이 있는지 확인한다.
	 * 게시물을 읽는 권한 유무에 따라 true, flase 값을 반환한다.
	 * @param doc
	 * @return permissionStatus
	 */
	public boolean validatePermission(Document doc) {
		boolean permissionStatus = true;
		String permissionText = doc.select("#primaryContent > table > tbody > tr.pos_rel > td.cb.pos_rel > div > a")
				 .text()
				 .replaceAll("\\uFFFD", "") // 유니코드 제거
				 .replaceAll("\\uFEFF", "")
				 .trim();
		 if(permissionText.equals("이 카페 회원 등급 보기")) { // 게시물 회원권한 필요시
			 permissionStatus = false;
		 }
		 
		 return permissionStatus;
		
	}
	
	/**
	 * 다음카페 home url과 parse한 link값을 받아, 게시물 url로 반환한다.
	 * @param cafeUrl ex) https://cafe.daum.net/ssaumjil
	 * @param articleLink ex)/_c21_/recent_bbs_read?grpid=Uzlo&fldid=LnOm&contentval=0CgCNzzzzzzzzzzzzzzzzzzzzzzzzz&datanum=3022151&page=1&prev_page=0&listnum=20
	 * @return sbUrl
	 */
	private String makeUrl(String cafeUrl, String articleLink) {
         
		int fldidStartIndex = articleLink.indexOf("fldid=") + 6;
        int fldidEndIndex = articleLink.indexOf("&contentval=", fldidStartIndex);
       
        int datanumStartIndex = articleLink.indexOf("datanum=") + 8;
        int datanumEndIndex = articleLink.indexOf("&page=", datanumStartIndex);
        
        String fldid = articleLink.substring(fldidStartIndex, fldidEndIndex); // ex) LnOm
        String datanum = articleLink.substring(datanumStartIndex, datanumEndIndex); // ex) 3022151
        
        StringBuilder sbUrl = new StringBuilder(cafeUrl);
        sbUrl.append("/");
        sbUrl.append(fldid);
        sbUrl.append("/");
        sbUrl.append(datanum);
		return sbUrl.toString();
	}
}

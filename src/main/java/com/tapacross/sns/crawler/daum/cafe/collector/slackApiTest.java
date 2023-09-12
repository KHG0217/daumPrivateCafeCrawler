package com.tapacross.sns.crawler.daum.cafe.collector;

import com.slack.api.Slack;

public class slackApiTest {

	public static void main(String[] args) {
	    Slack slack = Slack.getInstance();
	    System.out.println(slack.status());

	}

}

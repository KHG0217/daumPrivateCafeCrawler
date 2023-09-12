package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.esmedia.blogmon.workflow.SimpleWorkFlowReader;
import com.esmedia.blogmon.workflow.WorkFlowException;
import com.esmedia.blogmon.workflow.WorkFlowReader;
import com.esmedia.blogmon.workflow.sns.SNSConfigReader;
import com.esmedia.blogmon.workflow.sns.entity.SNSConfig;
import com.esmedia.db.ConnPool;
import com.esmedia.db.ConnPoolFactory;
import com.esmedia.db.ConnPoolProperty;
import com.esmedia.log.LoggerWrapper;
import com.tapacross.sns.ConfigConstants;

public class RowTransfer {
	protected Connection conn_FROM;
	protected Connection conn_TO;
	private ConnPool connPool;
	private String configDir;
	private SNSConfig snsConfig;
	public int SelectCnt;
	public int InsertCnt;
	
	public class ROW
	{
		long SEQ;
		long ARTICLE_ID;
		long SITE_ID_OLD;
		String WRITER_ID;
		String CRAWL_DATE;
		String TITLE;
		String BODY;
		String RT;
		String REPLY_ID;
		String REPLY_WRITER_ID;
		String RE;
		String ADDRESS;
		String LAT;
		String LNG;
		String CREATE_DATE;
		String SITE_TYPE;
		String VIA_URL;
		String ADDRESS_STATUS;
		String MENTION;
		long ARTICLE_ID_OLD;
		String URL;
		String REPUTATION_TYPE;
		String SITE_SUB_TYPE;
		String CONTENT_ID;
		String ADDRESS2;
		String SITE_ID;
		long SITE_CODE;
		String WRITER_NAME;
		String COLLECTED_BY;
		long RT_COUNT;
		long FOLLOWER_COUNT;
		String SITE_NAME;
		String PICTURE;
		String SCREEN_NAME;
		String SITE_CATEGORY;
	}
	
	public static void main(String[] args) throws SQLException 
	{
		try 
		{
			RowTransfer rowtransfer = new RowTransfer();

			rowtransfer.init();
			rowtransfer.conn_FROM = rowtransfer.connPool.getConnection();
			rowtransfer.conn_TO = rowtransfer.openSecondDBConn();

			long Min = 17323663;
			long Max = 17399658;
			
			LoggerWrapper.info("RowTransfer2", "SEQ Min : " + Min);
			LoggerWrapper.info("RowTransfer2", "SEQ Max : " + Max);
			
			long sTime = System.currentTimeMillis();
			
			rowtransfer.SelectCnt = 0;
			rowtransfer.InsertCnt = 0;
			
			for (long i = Min; i < (Max + 1); i++) {
				rowtransfer.getRow(i, Max);	
			}
			
			long eTime = System.currentTimeMillis();
			
			LoggerWrapper.info("RowTransfer2", "processtime : " + (eTime-sTime)/1000 + " seconds.");
		} 
		catch (Exception e) 
		{
			LoggerWrapper.debug("RowTransfer2", e);
		}
		finally
		{
			LoggerWrapper.info("RowTransfer2", "--- Program End ---");
			System.exit(-1);
		}
	}

	public long[] getMinMax() throws SQLException
	{
		long[] ai = new long[2];
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT MIN(SEQ) ");
			sb.append(" FROM TRENDSRCH20.TB_ARTICLE_SEARCH_MEDIA_OLD ");
			
			ps = conn_FROM.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				ai[0] = rs.getLong(1);
			}
			
			sb = new StringBuffer();
			sb.append(" SELECT MAX(SEQ) ");
			sb.append(" FROM TRENDSRCH20.TB_ARTICLE_SEARCH_MEDIA_OLD ");
			
			ps = conn_FROM.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				ai[1] = rs.getLong(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return ai; 
	}
	
	public void getRow(long min, long max) throws SQLException, InterruptedException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * ");
			sb.append(" FROM TRENDSRCH20.TB_ARTICLE_SEARCH_COMMUNITY ");
			sb.append(" WHERE SEQ = ? ");

			ps = conn_FROM.prepareStatement(sb.toString());
			ps.setLong(1, min);

			rs = ps.executeQuery();

			ROW row = new ROW();
			
			while(rs.next())
			{
				row.SEQ = rs.getLong(1);
				row.ARTICLE_ID = rs.getLong(2);
				row.SITE_ID_OLD = rs.getLong(3);
				row.WRITER_ID = rs.getString(4);
				row.CRAWL_DATE = rs.getString(5);
				row.TITLE = rs.getString(6);
				row.BODY = rs.getString(7);
				row.RT = rs.getString(8);
				row.REPLY_ID = rs.getString(9);
				row.REPLY_WRITER_ID = rs.getString(10);
				row.RE = rs.getString(11);
				row.ADDRESS = rs.getString(12);
				row.LAT = rs.getString(13);
				row.LNG = rs.getString(14);
				row.CREATE_DATE = rs.getString(15);
				row.SITE_TYPE = rs.getString(16);
				row.VIA_URL = rs.getString(17);
				row.ADDRESS_STATUS = rs.getString(18);
				row.MENTION = rs.getString(19);
				row.ARTICLE_ID_OLD = rs.getLong(20);
				row.URL = rs.getString(21);
				row.REPUTATION_TYPE = rs.getString(22);
				row.SITE_SUB_TYPE = rs.getString(23);
				row.CONTENT_ID = rs.getString(24);
				row.ADDRESS2 = rs.getString(25);
				row.SITE_ID = rs.getString(26);
				row.SITE_CODE = rs.getLong(27);
				row.WRITER_NAME = rs.getString(28);
				row.COLLECTED_BY = rs.getString(29);
				row.RT_COUNT = rs.getLong(30);
				row.FOLLOWER_COUNT = rs.getLong(31);
				row.SITE_NAME = rs.getString(32);
				row.PICTURE = rs.getString(33);
				row.SCREEN_NAME = rs.getString(34);
				row.SITE_CATEGORY = rs.getString(35);
			}
			
			if(row.SEQ != 0)
			{
				SelectCnt ++;
				InsertRow(row, min, max);
			}
			else
				System.out.println("SEQ : " + min + "-" + max + " is NULL");
				
		} catch (SQLException e1) {
			rs.close();
			ps.close();
			LoggerWrapper.info("RowTransfer2", "SEQ : " + min + "-" + max + " is NULL");
			e1.printStackTrace();
		}

		rs.close();
		ps.close();
		
		//return row;
	}
	
	public void InsertRow(ROW row, long min, long max) throws SQLException, InterruptedException
	{
		PreparedStatement ps = null;
		StringBuffer sb;		
		
		try {
			sb = new StringBuffer();
			sb.append(" INSERT /*+ APPEND */ ");
			sb.append(" INTO TRENDSRCH20.TB_ARTICLE_SEARCH_COMMUNITY ");
			sb.append(" ( SEQ, ARTICLE_ID, SITE_ID_OLD, WRITER_ID, CRAWL_DATE, TITLE, BODY, RT, REPLY_ID, REPLY_WRITER_ID, RE, ADDRESS, LAT, LNG, CREATE_DATE, SITE_TYPE, VIA_URL, ADDRESS_STATUS, MENTION, ARTICLE_ID_OLD, URL, REPUTATION_TYPE, SITE_SUB_TYPE, CONTENT_ID, ADDRESS2, SITE_ID, SITE_CODE, WRITER_NAME, COLLECTED_BY, RT_COUNT, FOLLOWER_COUNT, SITE_NAME, PICTURE, SCREEN_NAME, SITE_CATEGORY ) ");
			sb.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			
			ps = conn_TO.prepareStatement(sb.toString());
			
			ps.setLong(1, row.SEQ);
			ps.setLong(2, row.ARTICLE_ID);
			ps.setLong(3, row.SITE_ID_OLD);
			ps.setString(4, row.WRITER_ID);
			ps.setString(5, row.CRAWL_DATE);
			ps.setString(6, row.TITLE);
			ps.setString(7, row.BODY);
			ps.setString(8, row.RT);
			ps.setString(9, row.REPLY_ID);
			ps.setString(10, row.REPLY_WRITER_ID);
			ps.setString(11, row.RE);
			ps.setString(12, row.ADDRESS);
			ps.setString(13, row.LAT);
			ps.setString(14, row.LNG);
			ps.setString(15, row.CREATE_DATE);
			ps.setString(16, row.SITE_TYPE);
			ps.setString(17, row.VIA_URL);
			ps.setString(18, row.ADDRESS_STATUS);
			ps.setString(19, row.MENTION);
			ps.setLong(20, row.ARTICLE_ID_OLD);
			ps.setString(21, row.URL);
			ps.setString(22, row.REPUTATION_TYPE);
			ps.setString(23, row.SITE_SUB_TYPE);
			ps.setString(24, row.CONTENT_ID);
			ps.setString(25, row.ADDRESS2);
			ps.setString(26, row.SITE_ID);
			ps.setLong(27, row.SITE_CODE);
			ps.setString(28, row.WRITER_NAME);
			ps.setString(29, row.COLLECTED_BY);
			ps.setLong(30, row.RT_COUNT);
			ps.setLong(31, row.FOLLOWER_COUNT);
			ps.setString(32, row.SITE_NAME);
			ps.setString(33, row.PICTURE);
			ps.setString(34, row.SCREEN_NAME);
			ps.setString(35, row.SITE_CATEGORY);
				
			ps.executeUpdate();
			
			InsertCnt++;
			System.out.println("Inserted SEQ : " + max + "/" + row.SEQ + "  Inserted Count : " + SelectCnt + "/" + InsertCnt);

			ps.close();
			
		} catch (SQLException e) {
			LoggerWrapper.error("RowTransfer2",e.getMessage());
			System.out.println("SEQ : " + row.SEQ + "is not Inserted.");
			ps.close();
		}
	}
	
	private void init(){
		
		loadSNSConfig();
		LoggerWrapper.info("RowTransfer2", "load sns configuration file");
		initDBConnPool();
		LoggerWrapper.info("RowTransfer2", "init database ConnectionPool");
		initHttpClient();
		LoggerWrapper.info("RowTransfer2", "http Client ");
	}
	
	public ConnPool initDBConnPool() {
		
		ConnPoolProperty prop = new ConnPoolProperty();

		prop.setUrl( "jdbc:oracle:thin:@121.254.177.187:1521:ORCL" );
		prop.setDriverClass( "oracle.jdbc.driver.OracleDriver" );
		prop.setUser( "trendsrch20" );
		prop.setPassword( "tapaman" );
		prop.setValidationQuery( " select count(*) from dual " );
		prop.setMaxWaitMillis( 3000 );
		prop.setMaxSize( 2 );
		prop.setInitSize( 1 );
				
		connPool = ConnPoolFactory.getConnectionPool( prop );
		return connPool; 
	}
	
	public Connection openSecondDBConn() {
		Connection conn= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			LoggerWrapper.info("RowTransfer2", "try to get DB Connection");
			while ( conn == null ) { 
				try {
					//DriverManager.setlsetLoginTimeout( 3 );
					String url = "jdbc:oracle:thin:@121.254.177.144:1521:ora11";
					String user = "trendsrch20";
					String password = "tapaman";
					conn = DriverManager.getConnection(url, user, password);
					
					LoggerWrapper.info("RowTransfer2", "url : "+url);
					LoggerWrapper.info("RowTransfer2", "user :"+user);
					LoggerWrapper.info("RowTransfer2", "passowrd : "+password);
					
				} catch (Exception e) { 
					LoggerWrapper.info("RowTransfer2", "sleep for next database connection");
					LoggerWrapper.printStackTrace("", e);
				}
				Thread.sleep( 3*1000 );				
			}
		} catch (Exception e) {
			LoggerWrapper.info("RowTransfer2", "Can't initialize SNS second connection...");
			LoggerWrapper.printStackTrace("RowTransfer2", e );
		} 
				
		return conn;
	}
	
	private void initHttpClient() {
		// setting for httpcleint logging 			
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "error");
	}
	
	public SNSConfig loadSNSConfig(){
		try {
			String CONFIG_DIR = System.getProperty(ConfigConstants.HOMEDIR_PROP_KEY);
			if (CONFIG_DIR == null) {
				LoggerWrapper.info("RowTransfer2", "Home directory is not set. Set using -D"
						+ ConfigConstants.HOMEDIR_PROP_KEY);
				System.exit(1);
			}
			configDir = CONFIG_DIR + File.separator + "config";
						
			WorkFlowReader entityReader = new SNSConfigReader( configDir + 
					"/sns/sns_config.xml", null ); 
			WorkFlowReader reader = new SimpleWorkFlowReader( entityReader );
			snsConfig = (SNSConfig)reader.readEntity();
			LoggerWrapper.info("RowTransfer2", "SNSConfig loaded");
						
		} catch (WorkFlowException e) {
			System.exit(1);
		}		
		return snsConfig;
	}
}

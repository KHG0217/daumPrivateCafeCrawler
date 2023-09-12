package com.tapacross.sns.crawler.daum.cafe.collector.service;

import java.util.Set;

import org.springframework.stereotype.Controller;

import com.tapacross.sns.dao.IRedisDAO;

import redis.clients.jedis.exceptions.JedisConnectionException;

@Controller
public class RedisService implements IRedisService {
	private IRedisDAO redisDao;

	public void setRedisDao(IRedisDAO redisDao) {
		this.redisDao = redisDao;
	}

	@Override
	public Long addRedisSetValue(String key, String... values) {
		return redisDao.addSetValue(key, values);
	}
	
	@Override
	public void addRedisValue(String key, String value) {
		redisDao.addValue(key, value);
	}
	
	@Override
	public void addRedisValue(String key, String value, int expire) {
		redisDao.addValue(key, value, expire);
	}
	
	@Override
	public String getRedisValue(String key) {
		return redisDao.getValue(key);
	}

	@Override
	public Object getRedisHashValue(String key, String hashKey) {
		return redisDao.getHashValue(key, hashKey);
	}

	@Override
	public Set<String> getRedisSetValue(String key) {
		return redisDao.getSetValues(key);
	}
	
	@Override
	public Long incrementValue(String key, long increment) {
		return redisDao.incrementValue(key, increment);
	}
	
	@Override
	public Long incrementHashValue(String key, String field, long increment) {
		return redisDao.incrementHashValue(key, field, increment);	
	}
	
	@Override
	public void drainQueue(String key) {
		redisDao.drainQueue(key);
	}
	
	@Override
	public Long redisListSize(String key) {
		return redisDao.listSize(key);
	}

	@Override
	public void removeRedisValue(String key) {
		redisDao.removeRedisValue(key);
	}

	@Override
	public String popRedisQueue(String key) {
		return redisDao.popQueue(key);
	}
	
	@Override
	public String leftPopBlockingQueue(String key) {
		while (true) {
			if (redisDao.queueSize(key) == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				return redisDao.leftPopQueue(key);
			}
		}
	}
	
	@Override
	public String popBlockingQueue(String key) {
		while (true) {
			if (redisDao.queueSize(key) == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				return redisDao.popQueue(key);
			}
		}
	}

	@Override
	public Long pushRedisQueue(String key, String value) {
		return redisDao.pushQueue(key, value);
	}
	
	@Override
	public Long pushFixedBlockingQueue(String key, String value, int queueSize) {
		while (true) {
			if (redisDao.queueSize(key) >= queueSize) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				return redisDao.pushQueue(key, value);
			}
		}
	}
	
	public Long pushFixedSkipQueue(String key, String value, int queueSize) {
		if (redisDao.queueSize(key) >= queueSize) {
			return 0L;
		} else {
			return redisDao.pushQueue(key, value);
		}
	}

	@Override
	public Long redisQueueSize(String key) {
		return redisDao.queueSize(key);
	}

	@Override
	public void setRedisHashValue(String key, String field, String value) {
		redisDao.setHashValue(key, field, value);
	}
	
	@Override
	public boolean setKeyExpire(String key, int expire) {
		return redisDao.setKeyExpire(key, expire);
	}

	@Override
	public int hashSize(String key) throws JedisConnectionException {
		return redisDao.hashSize(key);
	}

	@Override
	public Set<Object> getHashkeys(String key) throws JedisConnectionException {
		return redisDao.getHashkeys(key);
	}

	@Override
	public String leftPopRedisQueue(String key) throws JedisConnectionException {
		return redisDao.leftPopQueue(key);
	}

	@Override
	public void removeRedisHashKey(String key, String hashKey) throws JedisConnectionException {
		redisDao.removeRedisHashKey(key, hashKey);
	}

	@Override
	public boolean setKeyExpireForHour(String key, int expire) throws JedisConnectionException {
		return redisDao.setKeyExpireForHour(key, expire);
	}

	@Override
	public boolean setKeyExpireForMin(String key, int expire) throws JedisConnectionException {
		return redisDao.setKeyExpireForMin(key, expire);
	}

	@Override
	public Long pushFrontQueue(String key, String value) throws JedisConnectionException {
		return redisDao.pushFrontQueue(key, value);
	}

}

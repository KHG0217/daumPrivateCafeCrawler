package com.tapacross.sns.crawler.daum.cafe.collector.service;

import java.util.Set;

import org.springframework.dao.InvalidDataAccessApiUsageException;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 레디스 스프링과 연동한 래퍼 클래스
 * 레디스의 자료구조에 접근하는 메서드가 정의되어 있다.
 * 레디스 접속이 되지않을경우 
 * org.springframework.data.redis.RedisConnectionFailureException
 * 예외가 발생한다.
 * @author cuckoo03
 *
 */
public interface IRedisService {
	/**
	 * Key Value 자료구조에서 키에 값을 추가한다.
	 * 키가 이미 존재하면 기존 값을 덮어 쓴다.
	 * @param key
	 * @param value
	 */
	void addRedisValue(String key, String value)
			throws JedisConnectionException;

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expire 주어진 키의 남은 만료시간. 일 단위 
	 */
	void addRedisValue(String key, String value, int expire)
			throws JedisConnectionException;;
	
	/**
	 * Set 자료구조에서 키에 해당하는 집합에 값을 추가한다. 값은 복수로 추가할 수 있다. 
	 * 키가 이미 존재하면 집합에 값을 추가한다. 값 추가후 추가한 값 개수를 리턴한다. setkey는 예약어라 키명으로 사용할 수 없다.
	 * @param key
	 * @param values
	 * @return
	 */
	Long addRedisSetValue(String key, String... values)
			throws JedisConnectionException;;
	
	/**
	 * KeyValue 자료구조에서 키에 해당하는 값을 가져온다. 키가 존재하지 않으면 null을 리턴한다.
	 * 키로 조회한 값의 데이터 형이 string이 아니면 InvalidDataAccessApiUsageException이 발생한다. 
	 * 
	 * @param key
	 * @return
	 */
	String getRedisValue(String key) throws InvalidDataAccessApiUsageException;

	/**
	 * 
	 * 주어진 키에서 해시필드명에 해당하는 값을 리턴한다. 해당 키가 없거나 필드가 없다면 null을 리턴한다.
	 * @param key 키명
	 * @param hashKey 키에 해당하는 해시 맵내에서의 필드명
	 * @return
	 */
	Object getRedisHashValue(String key, String hashKey)
			throws JedisConnectionException;;

	/**
	 * 주어진 키에 해당하는 집합을 리턴한다.
	 * @param key
	 * @return
	 */
	Set<String> getRedisSetValue(String key)
			throws JedisConnectionException;;
	
	/**
	 * 키에 해당하는 값을 증가시킨다. 저장된 값이 숫자가 아닐경우 
	 * InvalidDataAccessApiUsageException이 발생한다.
	 * @param key
	 * @pram increment
	 * @return 증가연산후 결과값
	 * @throws JedisConnectionException, InvalidDataAccessApiUsageException
	 */
	Long incrementValue(String key, long increment)
			throws JedisConnectionException;
	
	/**
	 * 해시맵에서 필드에 해당하는 기존값에 입력한 값을 증가시킨다.
	 * 키가 존재하지 않을경우 키와 필드를 생성하여 값을 초기화한다. 
	 * 필드가 존재하지 않을경우 입력값이 초기값이 된다.
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 * @throws JedisConnectionException
	 */
	Long incrementHashValue(String key, String field, long increment)
			throws JedisConnectionException;
	
	/**
	 * 레디스 큐의 아이템을 비운다.
	 * @param key
	 * @throws JedisConnectionException
	 */
	void drainQueue(String key) throws JedisConnectionException;
	
	/**
	 * list size를 리턴한다.
	 * 
	 * @param key 조회할 리스트의 키명
	 * @return
	 */
	Long redisListSize(String key) throws JedisConnectionException;
	
	/**
	 * 키에 해당하는 리스트에서 제일 오른쪽 인덱스의 value를 꺼내온다. 즉 맨 처음에 삽입한 요소를 꺼낸다.
	 * 리스트에 값이 없을 경우 null을 리턴한다.
	 * 이 메서드는 블로킹되지 않으므로 사용시에 컨디션 분기 체크를 해야한다.
	 * 
	 * @param key
	 * @return queue item
	 */
	String popRedisQueue(String key) throws JedisConnectionException;
	
	/**
	 * 키에 해당하는 리스트에서 제일 왼쪽 인덱스의 value를 꺼내온다. 즉 마지막에 삽입한 요소를 꺼낸다.
	 * 리스트에 값이 없을 경우 null을 리턴한다.
	 * 이 메서드는 블로킹되지 않으므로 사용시에 컨디션 분기 체크를 해야한다.
	 * 
	 * @param key
	 * @return queue item
	 */
	String leftPopRedisQueue(String key) throws JedisConnectionException;
	
	/**
	 * 키에 해당하는 리스트에서 제일 왼쪽 인덱스의 value를 꺼내온다. 즉 맨 나중에 삽입한 요소를 꺼낸다.
	 * 리스트에 값이 없을 경우 대기한다.
	 * @param key
	 * @return
	 * @throws JedisConnectionException
	 */
	String leftPopBlockingQueue(String key) throws JedisConnectionException;

	/**
	 * 키에 해당하는 리스트에서 제일 오른쪽 인덱스의 value를 꺼내온다. 즉 맨 처음에 삽입한 요소를 꺼낸다.
	 * 리스트에 값이 없을 경우 대기한다.
	 * @param key
	 * @return queue item
	 */
	String popBlockingQueue(String key);
	
	/**
	 * 큐의 개수를 체크하여 가득차 있으면 대기하고 그렇지 않으면 값을 큐에 넣는다.
	 * 큐가 가득차 있는 시간이 오래 지속될 경우 익셉션을 발생시키는 코드의 구현이 필요!!   
	 * @param key
	 * @param value
	 * @param queueSize
	 * @return Integer reply, specifically, the number of elements inside the list after the push operation.
	 */
	Long pushFixedBlockingQueue(String key, String value, int queueSize) throws JedisConnectionException;
	
	/**
	 * 큐의 개수를 체크하여 가득차 있으면 값을 버리고 그렇지 않으면 값을 큐에 넣는다. 큐의 최대 개수를 유지하면서 블로킹이 필요하지 않을때 사용한다.
	 * @param key
	 * @param value
	 * @param queueSize
	 * @return 큐가 가득찰 경우 0을 리턴한다. Integer reply, specifically, the number of elements inside thelist after the push operation.
	 * @throws JedisConnectionException
	 */
	Long pushFixedSkipQueue(String key, String value, int queueSize) throws JedisConnectionException;
	
	/**
	 * 키에 해당하는 리스트에서 제일 왼쪽 인덱스에 value를 추가한다. 즉 맨 마지막에 삽입한 요소 뒤에 삽입한다.
	 * 
	 * @param key
	 * @param value
	 * @return Integer reply, specifically, the number of elements inside the list after the push operation.
	 */
	Long pushRedisQueue(String key, String value) throws JedisConnectionException;
	
	/**
	 * 키에 해당하는 리스트에서 제일 오른쪽 인덱스에 value를 추가한다. 즉 맨 첫번째에 삽입한다.
	 * 
	 * @param key
	 * @param value
	 * @return Integer reply, specifically, the number of elements inside the list after the push operation.
	 */
	Long pushFrontQueue(String key, String value) throws JedisConnectionException;

	/**
	 * 해쉬 키에 해당하는 맵에 value를 추가한다. 기존 필드키가 있다면 기존 값을 덮어쓴다.
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	void setRedisHashValue(String key, String field, String value)
			throws JedisConnectionException;

	/**
	 * 키에 해당하는 큐에서 개수를 리턴한다. 해당 키가 없다면 0을 리턴한다.
	 * 
	 * @param key
	 * @return
	 */
	Long redisQueueSize(String key) throws JedisConnectionException;

	/**
	 * 키가 존재할 경우 키의 만료일을 설정한다. 키가 없을경우 false를 리턴한다.
	 * @param key
	 * @param expire 현재기준으로 지정할 만료일수. 일단위로 입력한다.
	 * @return
	 * @throws JedisConnectionException
	 */
	boolean setKeyExpire(String key, int expire)
			throws JedisConnectionException;
	/**
	 * 키가 존재할 경우 키의 만료일을 설정한다. 키가 없을경우 false를 리턴한다.
	 * @param key
	 * @param expire 현재기준으로 지정할 만료일수. 시간 단위로 입력한다.
	 * @return
	 * @throws JedisConnectionException
	 */
	boolean setKeyExpireForHour(String key, int expire)
			throws JedisConnectionException;
	/**
	 * 키가 존재할 경우 키의 만료일을 설정한다. 키가 없을경우 false를 리턴한다.
	 * @param key
	 * @param expire 현재기준으로 지정할 만료일수. 분 단위로 입력한다.
	 * @return
	 * @throws JedisConnectionException
	 */
	boolean setKeyExpireForMin(String key, int expire)
			throws JedisConnectionException;
	
	void removeRedisValue(String key) throws JedisConnectionException;

	/**
	 * 해쉬 키에 해당하는 map size 리턴.
	 * @param key Hash key
	 * @return
	 * @throws JedisConnectionException
	 */
	int hashSize(String key) throws JedisConnectionException;
	
	/**
	 * 해쉬 키에 해당하는 데이터 삭제
	 * @param key Hash key
	 * @return
	 * @throws JedisConnectionException
	 */
	void removeRedisHashKey(String key, String hashKey) throws JedisConnectionException;
	
	/**
	 * 해쉬 키에 해당하는 map의 필드Set 리턴.
	 * @param key Hash key
	 * @return
	 * @throws JedisConnectionException
	 */
	Set<Object> getHashkeys(String key) throws JedisConnectionException;

	
}
package com.parentcalendar.services.redis

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol

@Component
class RedisService {

  def grailsApplication

  private static final log = LogFactory.getLog(this)

  @Autowired
  private Gson gson

  private JedisPool pool

  public <T> void setCache(String key, T data, int ttl) {

    Jedis jedis = getPool().getResource()
    jedis.set(key, gson.toJson(data))
    jedis.expire(key, ttl)
    getPool().returnResource(jedis)
  }

  public <T> T getCache(String key, Class<T> type) {

    T response

    Jedis jedis = getPool().getResource()
    String data = jedis.get(key)
    getPool().returnResource(jedis)

    if (null == data || data.isEmpty()) {
      return null
    }

    try {
      response = gson.fromJson(data, type)
    } catch (JsonSyntaxException ex) {
      log.error ex.getStackTrace(), ex
      throw ex
    }
     response
  }

  protected JedisPool getPool() {
    if (!pool) {
      initializePool()
    }
    pool
  }

  private void initializePool() {

    if (!pool) {
      try {
        URI redisUri = new URI(getRedisUrl())
        pool = new JedisPool(new JedisPoolConfig(),
          redisUri.getHost(),
          redisUri.getPort(),
          Protocol.DEFAULT_TIMEOUT,
          getRedisAuthentication())
      } catch (URISyntaxException ex) {
        log.error ex.getStackTrace(), ex
        throw ex
      }
    }
  }

  def getRedisUrl() {
    grailsApplication.config.redis.url
  }

  def getRedisAuthentication() {
    grailsApplication.config.redis.authentication
  }
}

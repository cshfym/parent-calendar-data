package com.parentcalendar.services.redis

import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol

@Component
class RedisService {

    private final String REDIS_URL = "104.236.129.239:6379"

    JedisPool pool

    public RedisService() {
        try {
            URI redisUri = new URI(REDIS_URL);
            pool = new JedisPool(new JedisPoolConfig(),
                    redisUri.getHost(),
                    redisUri.getPort(),
                    Protocol.DEFAULT_TIMEOUT,
                    redisUri.getUserInfo().split(":",2)[1]);
        } catch (URISyntaxException e) {
            // URI couldn't be parsed.
        }
    }

    public String getCache() {

        Jedis jedis = pool.getResource();
        jedis.set("foo", "bar");
        String value = jedis.get("foo");

        // return the instance to the pool when you're done
        pool.returnResource(jedis);

        value
    }
}

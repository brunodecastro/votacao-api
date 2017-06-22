package br.com.selecaoglobo.votacaoapi.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheHelper {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    /**
     * Coloca o objeto no cache
     * @param key
     * @param value
     */
    public void putInCache(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * Coloca o objeto no cache com tempo para expirar.
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void putInCacheExpires(String key, String value, int timeout, TimeUnit timeUnit) {
        this.stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
    
    /**
     * Incrementa um valor no cache.
     * @param key
     * @param value
     */
    public void incrementValueInCache(String key, long value) {
        this.stringRedisTemplate.opsForValue().increment(key, value);
    }
    
    /**
     * Recupera o objeto do cache.
     * @param key
     * @return String
     */
    public String getFromCache(String key) {
        try {
            return this.stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
           LOG.error("Problemas ao recuperar o dado do cache.", e);
           return null;
        }
    }
}

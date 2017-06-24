package br.com.bco.votacaoapi.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Manipula os dados dos votos por token no cache.
 * @author bruno.oliveira
 */
@Component
public class TokenVoteCache {

    private static RedisCacheHelper redisCacheHelper;
    
    @Autowired
    public void setRedisCacheHelper(RedisCacheHelper redisCacheHelper) {
        TokenVoteCache.redisCacheHelper = redisCacheHelper;
    }
    
    /**
     * Numero de votos por token.
     * @param userToken
     * @return int
     */
    public static int getCacheNumberOfVotesByToken(String userToken) {
        String objCache = redisCacheHelper.getFromCache(userToken);
        if(objCache != null)
            return Integer.parseInt(objCache);
        else
            return 0;
    } 
    
    /**
     * Numero de votos por token.
     * @param userToken
     */
    public static void putCacheNumberOfVotesByToken(String userToken, int timeout, TimeUnit timeUnit) {
        // Coloca o token como chave do cache, com tempo de expiração de 10 minutos
        redisCacheHelper.putInCacheExpires(userToken, "1", 10, TimeUnit.MINUTES);
    }    
    
    /**
     * Incrementa o numero de votos por token no cache
     * @param userToken
     */
    public static void incrementCacheNumberOfVotesByToken(String userToken) {
        redisCacheHelper.incrementValueInCache(userToken, 1);
    }
    
}

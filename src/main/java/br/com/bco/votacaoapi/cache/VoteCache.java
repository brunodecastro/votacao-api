package br.com.bco.votacaoapi.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Manipula os dados de votos no cache.
 * @author bruno.oliveira
 */
@Component
public class VoteCache {
    
    private static final String PREFIX_KEY_VOTE_RESULTS_CONTEST = "KEY_VOTE_RESULTS_CONTEST_";
    private static final String PREFIX_KEY_VOTE_RESULTS_CONTEST_CANDIDATE = "KEY_VOTE_RESULTS_CONTEST_CANDIDATE_";
    
    private static RedisCacheHelper redisCacheHelper;
    
    @Autowired
    public void setRedisCacheHelper(RedisCacheHelper redisCacheHelper) {
        VoteCache.redisCacheHelper = redisCacheHelper;
    }
    
    /**
     * Incrementa os dados do voto no cache
     * @param voteDTO
     */
    public static void incrementVotesDataInCache(String contestSlug, Integer idCandidate) {
        
        // Adiciona os votos no cache do Redis, por contest
        redisCacheHelper.incrementValueInCache(getKeyCacheForVoteResultsByContest(contestSlug), 1);
        
        // Adiciona os votos no cache do Redis, por contest e candidate
        redisCacheHelper.incrementValueInCache(getKeyCacheForVoteResultsByContestAndCandidate(contestSlug, idCandidate), 1);
    }
    
    /**
     * Retorna o cache dos votos no cache do Redis, por contest
     * @param contestSlug
     * @return String
     */
    public static String getCacheForVoteResultsByContest(String contestSlug) {
        return redisCacheHelper.getFromCache(VoteCache.getKeyCacheForVoteResultsByContest(contestSlug));
    } 
    
    /**
     * Retorna o cache dos votos no cache do Redis, por contest e candidate
     * @param contestSlug
     * @param idCandidate
     * @return String
     */
    public static String getCacheForVoteResultsByContestAndCandidate(String contestSlug, Integer idCandidate) {
        return redisCacheHelper.getFromCache(VoteCache.getKeyCacheForVoteResultsByContestAndCandidate(contestSlug, idCandidate));
    } 
    
    /**
     * Retorna a chave para o cache dos votos no cache do Redis, por contest
     * @param contestSlug
     * @return String
     */
    private static String getKeyCacheForVoteResultsByContest(String contestSlug) {
        return PREFIX_KEY_VOTE_RESULTS_CONTEST + contestSlug;
    }
    
    /**
     * Retorna a chave para o cache dos votos no cache do Redis, por contest e candidate
     * @param contestSlug
     * @param idCandidate
     * @return String
     */
    private static String getKeyCacheForVoteResultsByContestAndCandidate(String contestSlug, Integer idCandidate) {
        return PREFIX_KEY_VOTE_RESULTS_CONTEST_CANDIDATE + contestSlug + "_" + idCandidate;
    }
    
}

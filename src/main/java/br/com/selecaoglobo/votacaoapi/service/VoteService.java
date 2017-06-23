package br.com.selecaoglobo.votacaoapi.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.selecaoglobo.votacaoapi.cache.RedisCacheHelper;
import br.com.selecaoglobo.votacaoapi.dto.CandidateContestVotesDTO;
import br.com.selecaoglobo.votacaoapi.dto.ContestVotesDTO;
import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;
import br.com.selecaoglobo.votacaoapi.exception.VoteApiException;
import br.com.selecaoglobo.votacaoapi.model.Vote;
import br.com.selecaoglobo.votacaoapi.queue.VoteRabbitProducer;
import br.com.selecaoglobo.votacaoapi.queue.VoteRedisPublisher;
import br.com.selecaoglobo.votacaoapi.repository.VoteRepository;
import br.com.selecaoglobo.votacaoapi.repository.impl.VoteRepositoryImpl;

@Service
public class VoteService {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoteRabbitProducer rabbitProducer;
    
	@Autowired
	private VoteRepository voteRepository;
	
    @Autowired
    private VoteRepositoryImpl voteRepositoryImpl;
    
    @Autowired
    private RedisCacheHelper redisCacheHelper;
    
    @Autowired
    private VoteRedisPublisher voteRedisPublisher;
    
    @Autowired
    private DozerBeanMapper dozerMapper;
    
	/**
	 * Busca todos os votes
	 * @return List<Vote>
	 */
	public List<Vote> findAll() {
		return this.voteRepository.findAll();
	}
	
	/**
     * Deleta todos os votes
     */
    public void deleteAll() {
        this.voteRepository.deleteAll();
    }
	
    /**
     * Obtém a votação por candidato
     * @param contestSlug
     * @param idCandidate
     * @return CandidateContestVotesDTO
     */
    public CandidateContestVotesDTO findByContestAndCandidate(String contestSlug, Integer idCandidate) {
        List<Vote> votes = this.voteRepository.findByContestSlugAndIdCandidate(contestSlug, idCandidate);
        
        List<CandidateContestVotesDTO> candidateContestVotes = votes.stream()
                .map(entity -> dozerMapper.map(entity, CandidateContestVotesDTO.class))
                .collect(Collectors.toList());
        
        return CollectionUtils.isEmpty(candidateContestVotes) ? null : candidateContestVotes.get(0);
    }

	/**
	 * Salva o Vote
	 * @param vote
	 * @param contestSlug
	 * @param idCandidate
	 * @return Vote
	 * @throws Exception
	 */
	public Vote save(Vote vote, String contestSlug, Integer idCandidate) throws VoteApiException {
	    vote.setContestSlug(contestSlug);
	    vote.setIdCandidate(idCandidate);
		return this.voteRepository.save(vote);
	}
	
	 /**
     * Busca os votos do contest.
     * @param contestSlug
     * @return ContestVotesDTO
     */
    public ContestVotesDTO findVotesResultForContest(String contestSlug) {
        return this.voteRepositoryImpl.findVotesResultForContest(contestSlug);
    }
	
	/**
	 * Vota.
	 * @param voteDTO
	 * @throws VoteApiException
	 */
	public void vote(VoteDTO voteDTO) throws VoteApiException {
	    try {
	        // Aplica as regras de votação por token em relação ao tempo.
	        if(false)
	        this.checkVoteTokenRules(voteDTO.getUserToken());
	        
	        
//	        this.rabbitProducer.sendVote(voteDTO);
	        
	        this.voteRedisPublisher.sendVote(voteDTO);
	        
//            this.voteRepositoryImpl.votar(voteDTO.getContestSlug(), voteDTO.getIdCandidate());
        } catch (VoteApiException e) {
           throw e;
	    }catch (Exception e) {
            LOG.error("Problemas ao votar.", e);
           throw new VoteApiException("Problemas ao votar.", e);
        }
	}
	
	/**
	 * Aplica as regras de votação por token em relação ao tempo.
	 * @param userToken
	 * @throws VoteApiException
	 */
	private void checkVoteTokenRules(String userToken) throws VoteApiException {
	    
	    String objCache = this.redisCacheHelper.getFromCache(userToken);
	    if(objCache != null) {
	        if(Integer.valueOf(objCache) >= 5) {
	            throw new VoteApiException("É permitido apenas 5 votos a cada 10 minutos para um token.");
	        } else {
	            this.redisCacheHelper.incrementValueInCache(userToken, 1);
	        }
	    } else {
	        // Coloca o token como chave do cache, com tempo de expiração de 10 minutos
	        this.redisCacheHelper.putInCacheExpires(userToken, "1", 10, TimeUnit.MINUTES);
	    }
	}
}

package br.com.bco.votacaoapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bco.votacaoapi.dto.ContestVotesDTO;
import br.com.bco.votacaoapi.model.Contest;
import br.com.bco.votacaoapi.repository.ContestRepository;

@Service
public class ContestService {

	@Autowired
	private ContestRepository contestRepository;
	
   @Autowired
    private VoteService voteService;
	
	/**
	 * Busca a votação geral agrupado por contest.
	 * @return List<ContestVotesDTO>
	 */
	public List<ContestVotesDTO> findVotesResultGroupedByContest() {
	    return this.voteService.findVotesResultGroupedByContest();
	}
	
	/**
     * Deleta todos os contests
     */
    public void deleteAll() {
        this.contestRepository.deleteAll();
    }

	/**
	 * Salva o contest
	 * 
	 * @param contest
	 * @return Contest
	 * @throws Exception 
	 */
	public Contest save(Contest contest) throws Exception {
		// Valida os dados de entrada.
		this.validarDados(contest);
		
		return this.contestRepository.save(contest);
	}
	
	/**
	 * Valida os dados de entrada.
	 * @param contest
	 * @throws Exception
	 */
	private void validarDados(Contest contest) throws Exception {
		
		if(contest.getStartDate() != null && contest.getEndDate() != null) {
			
			// Verifica se a data final é menor que a data inicial
			if(contest.getEndDate().compareTo(contest.getStartDate()) < 0) {
				throw new Exception("A data final não pode ser menor que a data inicial");
			}
		}
	}
}

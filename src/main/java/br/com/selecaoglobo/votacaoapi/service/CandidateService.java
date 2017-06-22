package br.com.selecaoglobo.votacaoapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.selecaoglobo.votacaoapi.model.Candidate;
import br.com.selecaoglobo.votacaoapi.repository.CandidateRepository;

@Service
public class CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	
	/**
	 * Busca todos os candidates
	 * @return List<Candidate>
	 */
	public List<Candidate> findAll() {
		return this.candidateRepository.findAll();
	}
	
	/**
     * Deleta todos os candidates
     */
    public void deleteAll() {
        this.candidateRepository.deleteAll();
    }
	
	/**
	 * Obtém a lista de candidatos de dado Contest
	 * @param contestSlug
	 * @return List<Candidate>
	 */
    public List<Candidate> findByContestSlug(String contestSlug) {
        return this.candidateRepository.findByContestSlug(contestSlug);
    }

	/**
	 * Salva o candidate
	 * 
	 * @param candidate
	 * @param contestSlug
	 * @return Candidate
	 * @throws Exception 
	 */
	public Candidate save(Candidate candidate, String contestSlug) throws Exception {
	    candidate.setContestSlug(contestSlug);
		return this.candidateRepository.save(candidate);
	}
	
}

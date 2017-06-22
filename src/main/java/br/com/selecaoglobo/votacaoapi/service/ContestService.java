package br.com.selecaoglobo.votacaoapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.selecaoglobo.votacaoapi.model.Contest;
import br.com.selecaoglobo.votacaoapi.repository.ContestRepository;

@Service
public class ContestService {

	@Autowired
	private ContestRepository contestRepository;
	
	/**
	 * Busca todos os contests
	 * @return List<Contest>
	 */
	public List<Contest> findAll() {
		return this.contestRepository.findAll();
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

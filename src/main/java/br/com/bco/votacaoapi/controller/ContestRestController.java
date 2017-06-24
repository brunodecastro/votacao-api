package br.com.bco.votacaoapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bco.votacaoapi.dto.ContestVotesDTO;
import br.com.bco.votacaoapi.model.Contest;
import br.com.bco.votacaoapi.service.ContestService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/contests")
public class ContestRestController {

	@Autowired
	private ContestService contestService;
	
	@RequestMapping(path = "/testRestService", method = RequestMethod.GET)
	@ApiOperation(value = "Testa se o serviço rest do Controller está funcionando.")
    public String testRestService() { 
        return "OK"; 
    }
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Obtém a lista de votações agrupado por Contest")
    public List<ContestVotesDTO> getContests() throws Exception {
        return this.contestService.findVotesResultGroupedByContest();
    }

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Cria um Contest")
	public ResponseEntity<String> addContest(@Valid @RequestBody Contest contest) throws Exception {
		this.contestService.save(contest);
		return new ResponseEntity<>("Contest salvo com sucesso.", HttpStatus.CREATED);
	}

}

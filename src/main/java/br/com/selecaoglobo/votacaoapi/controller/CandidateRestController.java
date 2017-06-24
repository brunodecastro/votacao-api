package br.com.selecaoglobo.votacaoapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.selecaoglobo.votacaoapi.model.Candidate;
import br.com.selecaoglobo.votacaoapi.service.CandidateService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class CandidateRestController {

    @Autowired
    private CandidateService candidateService;
    
    @RequestMapping(path = "/candidates/testRestService", method = RequestMethod.GET)
    @ApiOperation(value = "Testa se o serviço rest do Controller está funcionando.")
    public String testRestService() { 
        return "OK"; 
    }
    
    @RequestMapping(path = "/candidates", method = RequestMethod.GET)
    @ApiOperation(value = "Obtém a lista de candidatos")
    public List<Candidate> getCandidates() throws Exception {
        return this.candidateService.findAll();
    }
    
    @RequestMapping(path = "/candidates", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta todos os candidatos")
    public ResponseEntity<String> deleteAllCandidates() throws Exception {
        this.candidateService.deleteAll();
        return new ResponseEntity<>("Candidatos deletados com sucesso.", HttpStatus.OK);
    }
    
    @RequestMapping(path = "/contest/{contest_slug}/candidates", method = RequestMethod.GET)
    @ApiOperation(value = "Obtém a lista de candidatos de dado Contest")
    public List<Candidate> getCandidates(@PathVariable(name="contest_slug", required=true)  String contestSlug) throws Exception {
        return this.candidateService.findByContestSlug(contestSlug);
    }

    @RequestMapping(path = "/contest/{contest_slug}/candidates", method = RequestMethod.POST)
    @ApiOperation(value = "Cria um candidate para um Contest")
    public ResponseEntity<String> addCandidate(@Valid @RequestBody Candidate candidate, @PathVariable(name="contest_slug", required=true)  String contestSlug) throws Exception {
        this.candidateService.save(candidate, contestSlug);
        return new ResponseEntity<>("Candidate salvo com sucesso.", HttpStatus.CREATED);
    }

}

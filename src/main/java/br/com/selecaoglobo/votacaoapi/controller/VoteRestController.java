package br.com.selecaoglobo.votacaoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.selecaoglobo.votacaoapi.dto.CandidateContestVotesDTO;
import br.com.selecaoglobo.votacaoapi.dto.ContestVotesDTO;
import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;
import br.com.selecaoglobo.votacaoapi.exception.VoteApiException;
import br.com.selecaoglobo.votacaoapi.model.Vote;
import br.com.selecaoglobo.votacaoapi.service.VoteService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/votes")
public class VoteRestController {
    
    @Autowired
    private VoteService voteService;
    
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Obtém a lista de votos")
    public List<Vote> getVotes() throws Exception {
        return this.voteService.findAll();
    }
    
    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta todos os votos")
    public ResponseEntity<String> deleteAllVotes() throws Exception {
        this.voteService.deleteAll();
        return new ResponseEntity<>("Candidatos deletados com sucesso.", HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{contest_slug}", method = RequestMethod.GET)
    @ApiOperation(value = "Obtém a votação geral do contest")
    public ContestVotesDTO findVotesResultForContest(@PathVariable(name="contest_slug", required=true)  String contestSlug) throws Exception {
        
        return this.voteService.findVotesResultForContest(contestSlug);
    }
    
    @RequestMapping(path = "/{contest_slug}/{id_candidate}", method = RequestMethod.GET)
    @ApiOperation(value = "Obtém a votação por candidato")
    public CandidateContestVotesDTO findByContestSlugAndIdCandidate(@PathVariable(name="contest_slug", required=true)  String contestSlug,
                                                      @PathVariable(name="id_candidate", required=true)  String idCandidate) throws Exception {
        
        Integer idCandidateNumber = Integer.parseInt(idCandidate);
        return this.voteService.findByContestAndCandidate(contestSlug, idCandidateNumber);
    }
    
    @RequestMapping(path = "/{contest_slug}/{id_candidate}", method = RequestMethod.POST)
    @ApiOperation(value = "Vota no Candidate para um Contest")
    public ResponseEntity<String> addVote(@PathVariable(name="contest_slug", required=true)  String contestSlug,
                                          @PathVariable(name="id_candidate", required=true)  Integer idCandidate,
                                          @RequestHeader(name="user-token", required=true) final String userToken) throws VoteApiException {
        
        this.voteService.vote(new VoteDTO(contestSlug, idCandidate, userToken));
        
        return new ResponseEntity<>("Vote salvo com sucesso.", HttpStatus.CREATED);
    }

}

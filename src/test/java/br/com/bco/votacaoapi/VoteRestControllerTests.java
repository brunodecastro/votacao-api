package br.com.bco.votacaoapi;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bco.votacaoapi.dto.CandidateContestVotesDTO;
import br.com.bco.votacaoapi.dto.ContestVotesDTO;
import br.com.bco.votacaoapi.model.Vote;
import br.com.bco.votacaoapi.service.VoteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VoteRestControllerTests {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("UTF8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private VoteService voteService;
    
    private List<ContestVotesDTO> contestVotesDTOList = new ArrayList<ContestVotesDTO>();
    private List<CandidateContestVotesDTO> candidateContestVotesDTOList = new ArrayList<CandidateContestVotesDTO>();

    
    @Before
    public void setup() throws Exception {

        ContestVotesDTO contestVotesDTO1 = new ContestVotesDTO(100, "thevoice");
        ContestVotesDTO contestVotesDTO2 = new ContestVotesDTO(200, "bbb");
        
        this.contestVotesDTOList.add(contestVotesDTO1);
        this.contestVotesDTOList.add(contestVotesDTO2);
        
        
        CandidateContestVotesDTO candidateContestVotesDTO1 = new CandidateContestVotesDTO(60, "thevoice", 1);
        CandidateContestVotesDTO candidateContestVotesDTO2 = new CandidateContestVotesDTO(40, "thevoice", 2);
        CandidateContestVotesDTO candidateContestVotesDTO3 = new CandidateContestVotesDTO(150, "bbb", 3);
        CandidateContestVotesDTO candidateContestVotesDTO4 = new CandidateContestVotesDTO(50, "bbb", 4);

        this.candidateContestVotesDTOList.add(candidateContestVotesDTO1);
        this.candidateContestVotesDTOList.add(candidateContestVotesDTO2);
        this.candidateContestVotesDTOList.add(candidateContestVotesDTO3);
        this.candidateContestVotesDTOList.add(candidateContestVotesDTO4);

    }
    
    /**
     * Verifica se o serviço rest do Contest está ativo.
     * 
     * @throws Exception
     */
    @Test
    public void restServiceAliveTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/votes/testRestService").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("OK"));
    }
    
    /**
     * Testa a votação geral do contest.
     * @throws Exception
     */
    @Test
    public void getContestsVotesTest() throws Exception {
        
        // Contest - thevoice
        ContestVotesDTO contestTheVoice = this.contestVotesDTOList.get(0);
        
        when(this.voteService.findVotesResultForContest("thevoice")).thenReturn(contestTheVoice);
        
        this.mockMvc.perform(get("/votes/thevoice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(contestTheVoice.getContestSlug())))
                .andExpect(jsonPath("$.result", is(contestTheVoice.getResult().intValue())));
        
        
        // Contest - bbb
        ContestVotesDTO contestBBB = this.contestVotesDTOList.get(1);
        
        when(this.voteService.findVotesResultForContest("bbb")).thenReturn(contestBBB);
        
        this.mockMvc.perform(get("/votes/bbb"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(contestBBB.getContestSlug())))
                .andExpect(jsonPath("$.result", is(contestBBB.getResult().intValue())));
    }
    
    /**
     * Testa a votação por candidato de um contest.
     * @throws Exception
     */
    @Test
    public void getByContestAndCandidateTests() throws Exception {
        
        // Contest - thevoice 
        // Candidate - 1
        CandidateContestVotesDTO candidateContestThevoice1 = this.candidateContestVotesDTOList.get(0);
        
        when(this.voteService.findByContestAndCandidate("thevoice", 1)).thenReturn(candidateContestThevoice1);
        
        this.mockMvc.perform(get("/votes/thevoice/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(candidateContestThevoice1.getContestSlug())))
                .andExpect(jsonPath("$.result", is(candidateContestThevoice1.getResult().intValue())));
        
        
        // Contest - thevoice 
        // Candidate - 2
        CandidateContestVotesDTO candidateContestThevoice2 = this.candidateContestVotesDTOList.get(1);
        
        when(this.voteService.findByContestAndCandidate("thevoice", 2)).thenReturn(candidateContestThevoice2);
        
        this.mockMvc.perform(get("/votes/thevoice/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(candidateContestThevoice2.getContestSlug())))
                .andExpect(jsonPath("$.result", is(candidateContestThevoice2.getResult().intValue())));
        
        
        // Contest - bbb 
        // Candidate - 3
        CandidateContestVotesDTO candidateContestBBB3 = this.candidateContestVotesDTOList.get(2);
        
        when(this.voteService.findByContestAndCandidate("bbb", 3)).thenReturn(candidateContestBBB3);
        
        this.mockMvc.perform(get("/votes/bbb/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(candidateContestBBB3.getContestSlug())))
                .andExpect(jsonPath("$.result", is(candidateContestBBB3.getResult().intValue())));
        
        
        // Contest - bbb 
        // Candidate - 4
        CandidateContestVotesDTO candidateContestBBB4 = this.candidateContestVotesDTOList.get(3);
        
        when(this.voteService.findByContestAndCandidate("bbb", 4)).thenReturn(candidateContestBBB4);
        
        this.mockMvc.perform(get("/votes/bbb/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$.contestSlug", is(candidateContestBBB4.getContestSlug())))
                .andExpect(jsonPath("$.result", is(candidateContestBBB4.getResult().intValue())));
    }
    
    
    /**
     * Testa o serviço rest de voto de um Candidate de um Contest
     * @throws Exception
     */
    @Test
    public void voteTest() throws Exception {

        Vote vote = new Vote("thevoice", 1);

        String restUrl = "/votes/" + vote.getContestSlug() + "/" + vote.getIdCandidate();
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user-token", "token1");

        mockMvc.perform(post(restUrl)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(vote)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Vote salvo com sucesso."));
    }

}

package br.com.bco.votacaoapi;

import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bco.votacaoapi.model.Candidate;
import br.com.bco.votacaoapi.service.CandidateService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "dev"})
public class CandidateRestControllerTests {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("UTF8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CandidateService candidateService;
    
    private List<Candidate> candidateList = new ArrayList<Candidate>();
    
    @Before
    public void setup() throws Exception {

        Candidate candidate1 = this.createValidCandidateFake();
        candidate1.setId(1);
        candidate1.setName("candidate1");
        
        Candidate candidate2 = this.createValidCandidateFake();
        candidate1.setId(2);
        candidate1.setName("candidate2");
        
        this.candidateList.add(candidate1);
        this.candidateList.add(candidate2);
    }
    
    /**
     * Verifica se o serviço rest do Candidate está ativo.
     * 
     * @throws Exception
     */
    @Test
    public void restServiceAliveTest() throws Exception {

        this.mockMvc.perform(get("/candidates/testRestService").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("OK"));
    }
    
    @Test
    public void getCandidatesTest() throws Exception {
        
        when(this.candidateService.findAll()).thenReturn(this.candidateList);
        
        mockMvc.perform(get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.candidateList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(this.candidateList.get(1).getId().intValue())));
    }
    
    /**
     * Testa o serviço rest de criaçaão de um Candidate
     * @throws Exception
     */
    @Test
    public void restCreateCandidateTest() throws Exception {

        Candidate candidateToSaved = this.createValidCandidateFake();

        String restUrl = "/contest/" + candidateToSaved.getContestSlug() + "/candidates";
        
        mockMvc.perform(post(restUrl).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(candidateToSaved))).andExpect(status().isCreated())
                .andExpect(content().string("Candidate salvo com sucesso."));
    }
    
    /**
     * Cria um Candidate valido.
     * @return Candidate
     */
    private Candidate createValidCandidateFake() {
        return new Candidate(1, 
                            "candidate", 
                            "avatar1", 
                            "thevoice");
    }
}

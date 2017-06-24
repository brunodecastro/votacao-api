package br.com.bco.votacaoapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bco.votacaoapi.dto.ContestVotesDTO;
import br.com.bco.votacaoapi.model.Contest;
import br.com.bco.votacaoapi.service.ContestService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VotacaoApiApplicationTests {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("UTF8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContestService contestService;
    
    private List<ContestVotesDTO> contestVotesDTOList = new ArrayList<ContestVotesDTO>();

    @Before
    public void setup() throws Exception {

        ContestVotesDTO contestVotesDTO1 = new ContestVotesDTO();
        contestVotesDTO1.setContestSlug("thevoice");
        contestVotesDTO1.setResult(100);
        
        ContestVotesDTO contestVotesDTO2 = new ContestVotesDTO();
        contestVotesDTO2.setContestSlug("bbb");
        contestVotesDTO2.setResult(200);
        
        this.contestVotesDTOList.add(contestVotesDTO1);
        this.contestVotesDTOList.add(contestVotesDTO2);
    }
    
    /**
     * Verifica se o serviço rest do Contest está ativo.
     * 
     * @throws Exception
     */
    @Test
    public void restServiceAliveTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/contests/testRestService").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("OK"));
    }
    
    @Test
    public void getCandidatesTest() throws Exception {
        
        when(this.contestService.findVotesResultGroupedByContest()).thenReturn(this.contestVotesDTOList);
        
        this.mockMvc.perform(get("/contests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].contestSlug", is(this.contestVotesDTOList.get(0).getContestSlug())))
                .andExpect(jsonPath("$[0].result", is(this.contestVotesDTOList.get(0).getResult().intValue())))
                .andExpect(jsonPath("$[1].contestSlug", is(this.contestVotesDTOList.get(1).getContestSlug())))
                .andExpect(jsonPath("$[1].result", is(this.contestVotesDTOList.get(1).getResult().intValue())));
    }

    /**
     * Verifica se a service está funcionando.
     * @throws Exception
     */
    @Test
    public void restCreateContestServiceTest() throws Exception {

        Contest contestSaved = this.createValidContestFake();

        Contest contestUnsaved = this.createValidContestFake();

        given(this.contestService.save(contestSaved)).willReturn(contestUnsaved);
    }

    /**
     * Testa o serviço rest de criaçaão de um Contest
     * @throws Exception
     */
    @Test
    public void restCreateContestTest() throws Exception {

        Contest contestToSaved = this.createValidContestFake();

        this.mockMvc.perform(post("/contests").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(contestToSaved))).andExpect(status().isCreated())
                .andExpect(content().string("Contest salvo com sucesso."));
    }

    /**
     * Cria um Contest valido.
     * @return Contest
     */
    private Contest createValidContestFake() {
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();

        calendar.add(Calendar.YEAR, 1);
        Date endDate = calendar.getTime();

        return new Contest("thevoice", "TheVoice", "The Voice Description", startDate, endDate);
    }

}

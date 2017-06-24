package br.com.selecaoglobo.votacaoapi;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Calendar;
import java.util.Date;

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

import br.com.selecaoglobo.votacaoapi.model.Contest;
import br.com.selecaoglobo.votacaoapi.service.ContestService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VotacaoApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContestService contestService;

    /**
     * Verifica se o serviço rest do Contest está ativo.
     * 
     * @throws Exception
     */
    @Test
    public void contestRestServiceAliveTest() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/contests/testRestService").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("OK"));
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

        mvc.perform(post("/contests").contentType(MediaType.APPLICATION_JSON)
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

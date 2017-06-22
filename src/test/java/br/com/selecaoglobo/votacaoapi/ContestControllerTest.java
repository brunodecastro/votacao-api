package br.com.selecaoglobo.votacaoapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.selecaoglobo.votacaoapi.controller.ContestRestController;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(ContestRestController.class)
public class ContestControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testController() throws Exception {
        
    }
}

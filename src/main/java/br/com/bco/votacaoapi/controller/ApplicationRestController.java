package br.com.bco.votacaoapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore 
@Controller
public class ApplicationRestController {

    @RequestMapping("/")
    public String index() {
        return "redirect:swagger-ui.html";        
    }
}

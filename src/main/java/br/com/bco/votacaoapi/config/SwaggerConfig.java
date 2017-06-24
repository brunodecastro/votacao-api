package br.com.bco.votacaoapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.bco.votacaoapi.controller"))
				.build()
				.apiInfo(this.metaData());

	}
	
	private ApiInfo metaData() {
        return new ApiInfoBuilder()
        		.title("Votação REST API")
        		.description("API para votação.")
        		.version("1.0")
        		.contact(new Contact("Bruno de Castro Oliveira", "https://www.linkedin.com/in/brunodecastro", "brunnodecastro@gmail.com"))
        		.build();
    }

}

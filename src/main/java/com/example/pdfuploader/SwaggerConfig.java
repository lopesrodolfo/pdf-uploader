package com.example.pdfuploader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  @Bean public Docket detalheApi() {

    Docket docket = new Docket(DocumentationType.SWAGGER_2);

    docket.select().apis(RequestHandlerSelectors.basePackage("com.example.pdfuploader")).paths(PathSelectors.any())
        .build().apiInfo(this.informacoesApi().build());

    return docket;
  }

  private ApiInfoBuilder informacoesApi() {

    ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

    apiInfoBuilder.title("PDF Uploader");
    apiInfoBuilder.description("Api para envio e assinatura de arquivo PDF.");
    apiInfoBuilder.version("1.0");
    apiInfoBuilder.termsOfServiceUrl("Termo de uso: Deve ser usada para estudos.");
    apiInfoBuilder.license("Licença - Open Source");
    apiInfoBuilder.licenseUrl("http://www.urlteste.com.br");
    apiInfoBuilder.contact(this.contato());

    return apiInfoBuilder;

  }

  private Contact contato() {

    return new Contact("Rodolfo de Vasconcelos Lopes", "https://github.com/lopesrodolfo", "rodolfovlopes@gmail.com");
  }
}

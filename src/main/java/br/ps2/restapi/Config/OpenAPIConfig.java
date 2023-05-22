package br.ps2.restapi.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
@EnableWebMvc
public class OpenAPIConfig implements WebMvcConfigurer {
    private String devUrl = "http://localhost:8080";
    private String prodUrl = "http://rafael.site.com";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("osquesobraram@gmail.com");
        contact.setName("Os que sobraram");
        contact.setUrl("https://www.osquesobraramsite.com");


        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("PS2 API")
                .version("1.0")
                .contact(contact)
                .description("API do projeto de PS2 do grupo Os que sobraram, composto por Rafael do Carmo, Gustavo Detoni e Felippe Mazuca").termsOfService("https://www.authorsite.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}

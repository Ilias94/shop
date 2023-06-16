package pl.ilias.shop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openApi(@Value("${info.version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Shop api")
                        .description("Description")
                        .version(version)
                        .contact(new Contact()
                                .name("Ilias")
                                .email("ilbelg3@gmail.com")
                                .url("github")))
                .components(new Components()
                        .addSecuritySchemes("jwtSecurity", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))); //ToDo link do githuba
    }
}

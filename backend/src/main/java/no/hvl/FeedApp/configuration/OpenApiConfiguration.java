package no.hvl.FeedApp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        var openApi = new OpenAPI();

        var info = new Info();
        info.setTitle("FeedApp");
        info.setVersion("1.0");
        info.setDescription("Some App for feeds, not for feeding!");
        openApi.setInfo(info);

        openApi.servers(List.of(
            new Server().url("http://localhost:8080").description("Local")
        )).addSecurityItem(new SecurityRequirement().addList("bearerAuth"));

        openApi.components(new io.swagger.v3.oas.models.Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .name("bearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ));

        return openApi;
    }

}

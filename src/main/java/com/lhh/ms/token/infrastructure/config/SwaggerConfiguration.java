package com.lhh.ms.token.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String INFO_TITLE = "Microservice Token";

    private static final String INFO_DESCRIPTION = "Microservice API REST Consumer Token ViveLibre";

    private static final String INFO_VERSION = "1.0.0";

    private static final String CONTACT_NAME = "Julio Martinez";

    private static final String CONTACT_EMAIL = "juliomzarate5@gmail.com";

    private static final String DOCS_DESCRIPTION = "LHH Recruitment Solutions";

    private static final String DOCS_URL = "https://lhh.com";

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title(INFO_TITLE)
                        .description(INFO_DESCRIPTION)
                        .version(INFO_VERSION)
                        .contact(
                                new Contact()
                                        .name(CONTACT_NAME)
                                        .email(CONTACT_EMAIL)
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description(DOCS_DESCRIPTION)
                        .url(DOCS_URL));
    }
}

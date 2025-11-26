package t1.employeeservice.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI filmographyProject() {
        return new OpenAPI()
                .info(new Info()
                        .description("Работа с сотрудниками")
                        .title("Сервис сотрудников")
                        .version("v0.1")
                );
    }

}

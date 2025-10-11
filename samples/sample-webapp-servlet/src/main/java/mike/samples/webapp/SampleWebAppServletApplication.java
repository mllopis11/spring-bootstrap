package mike.samples.webapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@ComponentScan(basePackages = { Application.BOOT_BASE_PACKAGE, "mike.samples.webapp" })
@OpenAPIDefinition(
        info = @Info(
                title = "Basic Web (Servlet) Application Sample",
                description = "Demonstrate a web servlet based application",
                version = "1.0",
                contact = @Contact(
                    name = "Mike",
                    url = "https://github.com/mllopis11",
                    email = "mike@my-world-company.com"
                ),
                license = @License(
                    name = "Apache 2.0",
                    url = "https://www.apache.org/licenses/LICENSE-2.0")
        )
)
public class SampleWebAppServletApplication {

    public static void main(String[] args) {

        Application.servlet(SampleWebAppServletApplication.class, args);
    }
}

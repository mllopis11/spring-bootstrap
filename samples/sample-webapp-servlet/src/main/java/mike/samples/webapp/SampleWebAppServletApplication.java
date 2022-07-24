package mike.samples.webapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import mike.bootstrap.springboot.application.Application;

@SpringBootApplication
@ComponentScan(basePackages = { Application.BOOT_BASE_PACKAGE, "mike.samples.webapp" })
@OpenAPIDefinition(
        info = @Info(
                title = "Basic Web (Servlet) Application Sample",
                description = "Demonstrate a web servlet based application",
                version = "1.0"))
public class SampleWebAppServletApplication {

    public static void main(String[] args) {

        Application.servlet(SampleWebAppServletApplication.class, args);
    }
}

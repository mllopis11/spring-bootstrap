package mike.samples.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import mike.bootstrap.app.Application;


@SpringBootApplication
@ComponentScan(basePackages = { Application.BOOT_APP_PACKAGE, "mike.samples.app" })
public class SampleBasicApplication {

    public static void main(String[] args) {
        Application.batch(SampleBasicApplication.class, args);
    }

}

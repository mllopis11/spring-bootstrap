package mike.samples.webapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import mike.bootstrap.springboot.application.Application;

@SpringBootApplication
@ComponentScan(basePackages = { Application.BOOT_BASE_PACKAGE, "mike.samples.webapp"})
public class SampleWebAppServletApplication {

	public static void main(String[] args) {
		
		Application.servlet(SampleWebAppServletApplication.class, args);
	}

}

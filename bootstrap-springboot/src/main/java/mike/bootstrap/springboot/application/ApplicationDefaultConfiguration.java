package mike.bootstrap.springboot.application;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
class ApplicationDefaultConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationDefaultConfiguration.class);
	
	@Bean
	public LocaleResolver localeResolver() {
		var fixedLocale = Locale.ENGLISH;
		
		log.info("Application fixed locale: {}", fixedLocale.getDisplayName());
		
		return new FixedLocaleResolver(fixedLocale);
	}
}

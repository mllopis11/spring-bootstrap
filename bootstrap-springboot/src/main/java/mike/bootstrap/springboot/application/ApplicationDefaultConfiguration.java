package mike.bootstrap.springboot.application;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
@ConditionalOnClass(LocaleResolver.class)
class ApplicationDefaultConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationDefaultConfiguration.class);

    @Bean
    public LocaleResolver localeResolver() {
        var fixedLocale = Locale.ENGLISH;

        log.info("Application locale (fixed): {}", fixedLocale.getDisplayName());

        return new FixedLocaleResolver(fixedLocale);
    }
}

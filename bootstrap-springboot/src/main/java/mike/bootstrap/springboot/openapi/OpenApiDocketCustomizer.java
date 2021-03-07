package mike.bootstrap.springboot.openapi;

import org.springdoc.core.Constants;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mike.bootstrap.utilities.system.AppInfo;

@Configuration
@ConditionalOnClass(Constants.class)
@ConditionalOnProperty(name = Constants.SPRINGDOC_ENABLED, matchIfMissing = true)
class OpenApiDocketCustomizer {

	/**
	 * @return customized openApi info with the current version of the application. 
	 */
	@Bean
	public OpenApiCustomiser openAPiInfoCustomizer() {
		return openApi -> openApi.getInfo().setVersion(AppInfo.version());
	}
}

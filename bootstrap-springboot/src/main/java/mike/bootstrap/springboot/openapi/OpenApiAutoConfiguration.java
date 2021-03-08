package mike.bootstrap.springboot.openapi;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springdoc.core.Constants;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

import com.fasterxml.jackson.databind.JsonSerializer;

import io.swagger.v3.oas.models.responses.ApiResponse;
import mike.bootstrap.springboot.openapi.problem.DefaultProblemConstraintJsonSerializer;
import mike.bootstrap.springboot.openapi.problem.DefaultProblemJsonSerializer;
import mike.bootstrap.utilities.system.AppInfo;

@Configuration
@ConditionalOnClass(Constants.class)
@ConditionalOnProperty(name = Constants.SPRINGDOC_ENABLED, matchIfMissing = true)
class OpenApiAutoConfiguration {

	/**
	 * @return customized openApi info with the current version of the application. 
	 */
	@Bean
	public OpenApiCustomiser openApiCustomizer(Optional<List<Map.Entry<String, ApiResponse>>> responsesToRegister) {
		return openApi -> { 
			openApi.getInfo().setVersion(AppInfo.version());
			
			responsesToRegister
				.ifPresent( responses -> responses
						.forEach(entry -> openApi.getComponents().addResponses(entry.getKey(), entry.getValue())));
		};
	}
	
	@Bean
	@ConditionalOnClass(Problem.class)
	public JsonSerializer<DefaultProblem> problemDefaultJsonSerializer() {
		return new DefaultProblemJsonSerializer();
	}
	
	@Bean
	@ConditionalOnClass(ConstraintViolationProblem.class)
	public JsonSerializer<ConstraintViolationProblem> problemConstraintDefaultJsonSerializer() {
		return new DefaultProblemConstraintJsonSerializer();
	}
}

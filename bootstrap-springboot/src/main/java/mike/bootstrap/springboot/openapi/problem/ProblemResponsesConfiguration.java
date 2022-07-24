package mike.bootstrap.springboot.openapi.problem;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

@Configuration
@ConditionalOnClass(Problem.class)
class ProblemResponsesConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ProblemResponsesConfiguration.class);

    static final String HTTP_401 = "http401";
    static final String HTTP_403 = "http403";
    static final String HTTP_500 = "http500";

    private static final Problem http401NoTokenProblem;
    private static final Problem http401InvalidTokenProblem;
    private static final Problem http403Problem;
    private static final Problem http500Problem;

    private static record ProblemExample(String summary, String description, Problem example) {}

    static {
        http401NoTokenProblem = Problem
                .builder()
                .withTitle(Status.UNAUTHORIZED.getReasonPhrase())
                .withStatus(Status.UNAUTHORIZED)
                .withDetail("Token info endpoint resonded with 400")
                .build();

        http401InvalidTokenProblem = Problem
                .builder()
                .withTitle(Status.UNAUTHORIZED.getReasonPhrase())
                .withStatus(Status.UNAUTHORIZED)
                .withDetail("Full authentication required to access this resource")
                .build();

        http403Problem = Problem
                .builder()
                .withTitle(Status.FORBIDDEN.getReasonPhrase())
                .withStatus(Status.FORBIDDEN)
                .withDetail("Access denied (insufficient privilege)")
                .build();

        http500Problem = Problem
                .builder()
                .withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withDetail("Unexpected error (contact the application support !)")
                .build();
    }

    @Bean
    public OpenApiCustomiser registerProblemSchema(SpringDocConfigProperties springDocConfigProperties) {

        log.debug("OpenApi: register Problem schemas ...");

        springDocConfigProperties.setRemoveBrokenReferenceDefinitions(false);

        var problemSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(Problem.class));

        var statusTypeSchema = ModelConverters
                .getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(StatusType.class));

        return openApi -> {
            openApi.getComponents().addSchemas(Problem.class.getSimpleName(), problemSchema.schema);
            openApi.getComponents().addSchemas(StatusType.class.getSimpleName(), statusTypeSchema.schema);
        };
    }

    @Bean
    public Map.Entry<String, ApiResponse> http401() {
        return this
                .buildSimpleResponse(HTTP_401, "Invalid authentication",
                        new ProblemExample("no token", "Authentication token not provided", http401NoTokenProblem),
                        new ProblemExample("invalid token", "Authentication token is invalid",
                                http401InvalidTokenProblem));
    }

    @Bean
    public Map.Entry<String, ApiResponse> http403() {
        return this.buildSimpleResponse(HTTP_403, "Insufficient privileges", http403Problem);
    }

    @Bean
    public Map.Entry<String, ApiResponse> http500() {
        return this.buildSimpleResponse(HTTP_500, "Internal Server Error Example", http500Problem);
    }

    private Map.Entry<String, ApiResponse> buildSimpleResponse(String code, String description, Problem problem) {

        ApiResponse response = new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType(APPLICATION_PROBLEM_JSON_VALUE,
                                new MediaType()
                                        .example(problem)
                                        .schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));

        return new AbstractMap.SimpleEntry<>(code, response);
    }

    private Map.Entry<String, ApiResponse> buildSimpleResponse(String code, String description,
            ProblemExample... problems) {

        Map<String, Example> examples = Stream
                .of(problems)
                .map(pbl -> new Example().summary(pbl.summary()).description(pbl.description()).value(pbl.example()))
                .collect(Collectors.toMap(Example::getSummary, example -> example));

        ApiResponse response = new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType(APPLICATION_PROBLEM_JSON_VALUE,
                                new MediaType()
                                        .example(examples)
                                        .schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));

        return new AbstractMap.SimpleEntry<>(code, response);
    }
}

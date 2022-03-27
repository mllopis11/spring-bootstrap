package mike.bootstrap.springboot.openapi.problem;

public class ProblemResponsesReference {

    private ProblemResponsesReference() {}

    private static final String REPSONSE_PATH = "#/components/responses/";

    public static final String UNAUTHORIZED_401 = REPSONSE_PATH + ProblemResponsesConfiguration.HTTP_401;
    public static final String FORBIDDEN_403 = REPSONSE_PATH + ProblemResponsesConfiguration.HTTP_403;
    public static final String INTERNAL_SERVER_ERROR_500 = REPSONSE_PATH + ProblemResponsesConfiguration.HTTP_500;
}

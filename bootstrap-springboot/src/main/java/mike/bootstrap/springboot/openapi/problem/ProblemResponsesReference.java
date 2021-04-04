package mike.bootstrap.springboot.openapi.problem;

public class ProblemResponsesReference {
	
	private ProblemResponsesReference() {}

	private static final String responsesPath = "#/components/responses/";
	
	public static final String UNAUTHORIZED_401 = responsesPath + ProblemResponsesConfiguration.HTTP_401;
	public static final String FORBIDDEN_403 = responsesPath + ProblemResponsesConfiguration.HTTP_403;
	public static final String INTERNAL_SERVER_ERROR_500 = responsesPath + ProblemResponsesConfiguration.HTTP_500;
}

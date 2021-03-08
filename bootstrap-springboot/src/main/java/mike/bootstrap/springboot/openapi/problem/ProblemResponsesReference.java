package mike.bootstrap.springboot.openapi.problem;

public class ProblemResponsesReference {
	
	private ProblemResponsesReference() {}

	public static final String UNAUTHORIZED_401 = "#/components/responses/" + ProblemResponsesConfiguration.HTTP_401;
	public static final String FORBIDDEN_403 = "#/components/responses/" + ProblemResponsesConfiguration.HTTP_403;
	public static final String INTERNAL_SERVER_ERROR_500 = "#/components/responses/" + ProblemResponsesConfiguration.HTTP_500;
}

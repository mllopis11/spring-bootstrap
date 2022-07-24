package mike.bootstrap.springboot.openapi.problem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

import mike.bootstrap.utilities.exceptions.ApplicationErrorException;
import mike.bootstrap.utilities.exceptions.ApplicationException;
import mike.bootstrap.utilities.exceptions.ResourceAlreadyExistException;
import mike.bootstrap.utilities.exceptions.ResourceNotAvailableException;
import mike.bootstrap.utilities.exceptions.ResourceNotFoundException;

public interface ContainerAdviceTraits extends ValidationAdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleResourceNotFound(final ResourceNotFoundException ex,
            final NativeWebRequest request) {

        return create(Status.NOT_FOUND, ex, request);
    }

    @ExceptionHandler
    default ResponseEntity<Problem> handleResourceAlreadyExists(final ResourceAlreadyExistException ex,
            final NativeWebRequest request) {

        return create(Status.CONFLICT, ex, request);
    }

    @ExceptionHandler
    default ResponseEntity<Problem> handleResourceNotAvailable(final ResourceNotAvailableException ex,
            final NativeWebRequest request) {

        return create(Status.SERVICE_UNAVAILABLE, ex, request);
    }

    @ExceptionHandler
    default ResponseEntity<Problem> handleApplicationException(final ApplicationException ex,
            final NativeWebRequest request) {

        return create(Status.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler
    default ResponseEntity<Problem> handleApplicationErrorException(final ApplicationErrorException ex,
            final NativeWebRequest request) {

        return create(Status.INTERNAL_SERVER_ERROR, ex, request);
    }
}

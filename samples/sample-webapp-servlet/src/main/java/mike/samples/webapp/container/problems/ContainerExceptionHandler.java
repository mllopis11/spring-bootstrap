package mike.samples.webapp.container.problems;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(10)
class ContainerExceptionHandler implements ContainerAdviceTraits {

}

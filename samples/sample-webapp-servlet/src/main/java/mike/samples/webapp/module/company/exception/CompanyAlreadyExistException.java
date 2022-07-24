package mike.samples.webapp.module.company.exception;

import mike.bootstrap.utilities.exceptions.ResourceAlreadyExistException;

public class CompanyAlreadyExistException extends ResourceAlreadyExistException {

    private static final long serialVersionUID = 4230120847476234214L;

    public CompanyAlreadyExistException(String name) {
        super("company already exists: " + name);
    }
}

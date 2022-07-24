package mike.samples.webapp.module.company.exception;

import mike.bootstrap.utilities.exceptions.ResourceNotFoundException;

public class CompanyNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 2320104538411483080L;

    public CompanyNotFoundException(String name) {
        super("no such company: " + name);
    }
}

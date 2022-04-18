package mike.samples.webapp.module.company.repository;

import java.util.Collection;
import java.util.Optional;

import mike.samples.webapp.module.company.domain.Company;

public interface CompanyRepository {

    Collection<Company> findAll();
    
    Optional<Company> findByName(String name);
    
    Company create(Company company);
    
    Company update(Company company);
    
    boolean delete(String name);
}

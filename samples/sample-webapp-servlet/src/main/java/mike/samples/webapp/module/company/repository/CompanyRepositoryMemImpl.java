package mike.samples.webapp.module.company.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import mike.samples.webapp.module.company.domain.Company;
import mike.samples.webapp.module.company.domain.CompanyFactory;

@Repository
class CompanyRepositoryMemImpl implements CompanyRepository {

    private static final ConcurrentMap<String, Company> companies = CompanyFactory.defaultCompanies();
    
    @Override
    public Collection<Company> findAll() {
	return companies.values();
    }

    @Override
    public Optional<Company> findByName(String name) {
	return companies.entrySet().stream()
		.filter(e -> e.getKey().equalsIgnoreCase(name))
		.map(Map.Entry::getValue)
		.findFirst();
    }

    @Override
    public Company create(Company company) {
	return this.saveCompany(company);
    }

    @Override
    public Company update(Company company) {
	return this.saveCompany(company);
    }

    @Override
    public boolean delete(String name) {
	return Optional.ofNullable(companies.remove(name)).isPresent();
    }

    /**
     * Insert or Replace the given company object
     * 
     * @param company the compaby to save
     * @return the saved company
     */
    private Company saveCompany(Company company) {
	companies.put(company.getName(), company);
	return company;
    }
}

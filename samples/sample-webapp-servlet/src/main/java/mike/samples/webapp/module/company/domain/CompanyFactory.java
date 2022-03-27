package mike.samples.webapp.module.company.domain;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class CompanyFactory {

    private CompanyFactory() {}

    private static final List<Company> Companies = List.of(
	    new Company("Google", "Technology company"),
	    new Company("Amazon", "Technology company"), 
	    new Company("Facebook", "Social networking"),
	    new Company("Apple", "Technology company"), 
	    new Company("Microsoft", "Technology company"),
	    new Company("Twitter", "Social networking"));

    public static ConcurrentMap<String, Company> defaultCompanies() {
	return Companies.stream().collect(Collectors.toConcurrentMap(Company::getName, company -> company));
    }
}

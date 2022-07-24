package mike.samples.webapp.module.company.domain;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class CompanyFactory {

    private static final String TECH_COMPANY = "Technology company";
    private static final String SOCIAL_NETWORK = "Social networking";

    private CompanyFactory() {}

    private static final List<Company> Companies = List.of(
            new Company("Google", TECH_COMPANY), new Company("Amazon", TECH_COMPANY),
            new Company("Facebook", SOCIAL_NETWORK), new Company("Apple", TECH_COMPANY),
            new Company("Microsoft", TECH_COMPANY), new Company("Twitter", SOCIAL_NETWORK));

    public static ConcurrentMap<String, Company> defaultCompanies() {
        return Companies.stream().collect(Collectors.toConcurrentMap(Company::getName, company -> company));
    }
}

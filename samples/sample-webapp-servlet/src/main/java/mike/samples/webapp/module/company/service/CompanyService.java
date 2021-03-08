package mike.samples.webapp.module.company.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mike.samples.webapp.module.company.domain.Company;
import mike.samples.webapp.module.company.domain.CompanyFactory;

@Service
public class CompanyService {

	private static final Logger log = LoggerFactory.getLogger(CompanyService.class);
	
	private final ConcurrentMap<String, Company> companies = CompanyFactory.defaultCompanies();
	
	public Collection<Company> findAll() {
		log.info("findAll: retrieve all companies ...");
		return this.companies.values();
	}
	
	public Optional<Company> findByName(String name) {
		log.info("findByName: retrieve company: {}", name);
		
		return companies.entrySet().stream()
				.filter( e -> e.getKey().equalsIgnoreCase(name) )
				.map(Map.Entry::getValue)
				.findFirst();
	}
}

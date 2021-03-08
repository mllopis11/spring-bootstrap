package mike.samples.webapp.module.company.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mike.bootstrap.springboot.openapi.problem.ProblemResponsesReference;
import mike.bootstrap.utilities.exceptions.ResourceNotFoundException;
import mike.samples.webapp.module.company.domain.Company;
import mike.samples.webapp.module.company.service.CompanyService;

@RestController
@RequestMapping(value = "/v1/companies", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Companies", description = "Companies operations")
class CompanyResources {

	private final CompanyService companyService;

	public CompanyResources(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Operation(
			method = "GET",
			summary = "Retrieve all companies", 
			description = "Returns all declared companies or an empty array if any.")
	@ApiResponse(responseCode = "200", description = "Companies", 
			content = @Content(
					mediaType = APPLICATION_JSON_VALUE, 
					array = @ArraySchema(schema = @Schema(implementation = Company.class))))
	@ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
	@GetMapping
	public Collection<Company> findAll() {
		return this.companyService.findAll();
	}

	@Operation(
			method = "GET",
			summary = "Retrieve company by its name",
			description = "Returns the company if exists otherwise raise a 404 (NOT_FOUND) error.")
	@ApiResponse(responseCode = "200", description = "Companies", 
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Company.class)))
	@ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
	@GetMapping(value = "/{name}")
	public Company findByName(@PathVariable("name") @NotBlank String name) {
		return this.companyService.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("no such company: %s", name));
	}
}

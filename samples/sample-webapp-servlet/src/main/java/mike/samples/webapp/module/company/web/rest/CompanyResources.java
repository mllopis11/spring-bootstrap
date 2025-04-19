package mike.samples.webapp.module.company.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mike.bootstrap.springboot.openapi.problem.ProblemResponsesReference;
import mike.samples.webapp.module.company.domain.Company;
import mike.samples.webapp.module.company.exception.CompanyNotFoundException;
import mike.samples.webapp.module.company.service.CompanyService;
import mike.samples.webapp.module.company.web.model.CompanyForm;

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
    @ApiResponse(
            responseCode = "200",
            description = "Companies",
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
    @ApiResponse(
            responseCode = "200",
            description = "Company",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
    @GetMapping(value = "/{name}")
    public Company findByName(@PathVariable("name") @NotBlank String name) {
        return this.companyService.findByName(name).orElseThrow(() -> new CompanyNotFoundException(name));
    }

    @Operation(
            method = "PUT",
            summary = "Create a new company",
            description = "Returns the created company. Raise a 409 (CONFLICT) if the company already exists.")
    @ApiResponse(
            responseCode = "200",
            description = "Created company",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
    @PutMapping(value = "/create")
    public Company create(@Valid @RequestBody CompanyForm form) {
        return this.companyService.create(form);
    }

    @Operation(
            method = "POST",
            summary = "Update a company",
            description = "Returns the updated company. Raise a 404 (NOT_FOUND) if the company does not exists.")
    @ApiResponse(
            responseCode = "200",
            description = "Updated company",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
    @PostMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public Company update(@Valid @RequestBody CompanyForm form) {
        return this.companyService.update(form);
    }

    @Operation(
            method = "DELETE",
            summary = "Delete a company by its name.",
            description = "Returns 200 (OK) if the company does exists or not.")
    @ApiResponse(responseCode = "200", description = "Deleted")
    @ApiResponse(responseCode = "500", ref = ProblemResponsesReference.INTERNAL_SERVER_ERROR_500)
    @DeleteMapping(value = "/delete/{name}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK, reason = "deleted")
    public void delete(@PathVariable("name") @NotBlank String name) {
        this.companyService.delete(name);
    }
}

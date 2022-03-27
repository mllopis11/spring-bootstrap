package mike.samples.webapp.module.company.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import mike.bootstrap.utilities.helpers.Dates;

@Schema
public class Company {

    @NotBlank
    private final String name;

    @NotBlank
    private String description;

    @NotNull
    @Schema(type = "string", example = "2021-01-30T00:31:26")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDttm = LocalDateTime.now();

    /**
     * Constructor
     */
    public Company(String name, String description) {
	this.name = name;
	this.description = description;
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public LocalDateTime getCreatedDttm() {
	return createdDttm;
    }

    public void setCreatedDttm(LocalDateTime createdDttm) {
	this.createdDttm = createdDttm;
    }

    @Override
    public String toString() {
	return String.format("Company [name=%s, description=%s, createdDttm=%s]", name, description,
		Dates.format(createdDttm));
    }
}

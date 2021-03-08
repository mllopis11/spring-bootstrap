package mike.samples.webapp.module.company.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class Company {

	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	private LocalDateTime createdDttm = LocalDateTime.now();
	
	/**
	 * Constructor (no args)
	 */
	public Company() {}
	
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
	public void setName(String name) {
		this.name = name;
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
}

package mike.samples.webapp.module.company.web.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Company form")
public class CompanyForm {

	@NotBlank
	@Size(min = 2, max = 20)
	@Pattern(regexp = "[\\w\\-]+")
	private String name;
	
	@NotBlank
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[\\w\\s\\-]+")
	private String description;

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

	@Override
	public String toString() {
		return String.format("CompanyForm [name=%s, description=%s]", name, description);
	}
}

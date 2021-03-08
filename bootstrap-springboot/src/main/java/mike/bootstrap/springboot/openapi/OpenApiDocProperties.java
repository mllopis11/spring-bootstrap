package mike.bootstrap.springboot.openapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:openapi-doc.properties")
@ConfigurationProperties(prefix = "springdoc.info")
class OpenApiDocProperties {

	private String contactName;
	private String contactUrl;
	private String contactEmail;
	private String licenseName;
	private String licenseUrl;
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactUrl() {
		return contactUrl;
	}
	
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}
	
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public String getLicenseName() {
		return licenseName;
	}
	
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	
	public String getLicenseUrl() {
		return licenseUrl;
	}
	
	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}
}

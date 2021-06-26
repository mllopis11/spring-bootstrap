package mike.bootstrap.springboot.openapi.problem;

import java.io.IOException;

import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;

public class DefaultProblemConstraintJsonSerializer extends JsonSerializer<ConstraintViolationProblem>{

	public DefaultProblemConstraintJsonSerializer() {
		var module = new SimpleModule();
		module.addSerializer(ConstraintViolationProblem.class, this);
		Json.mapper().registerModule(module);
		Yaml.mapper().registerModule(module);
	}
	
	@Override
	public void serialize(ConstraintViolationProblem problem, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("title", problem.getTitle());
		jsonGenerator.writeNumberField("status", problem.getStatus().getStatusCode());
		
		jsonGenerator.writeArrayFieldStart("violations");
		
		for ( Violation violation : problem.getViolations() ) {
			jsonGenerator.writeObject(violation);
		}
		
		jsonGenerator.writeEndArray();
		jsonGenerator.writeEndObject();
	}
}

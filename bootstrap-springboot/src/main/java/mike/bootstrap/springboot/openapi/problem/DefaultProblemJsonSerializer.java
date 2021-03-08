package mike.bootstrap.springboot.openapi.problem;

import java.io.IOException;

import org.zalando.problem.DefaultProblem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;

public class DefaultProblemJsonSerializer extends JsonSerializer<DefaultProblem>{

	public DefaultProblemJsonSerializer() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(DefaultProblem.class, this);
		Json.mapper().registerModule(module);
		Yaml.mapper().registerModule(module);
	}
	
	@Override
	public void serialize(DefaultProblem problem, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("title", problem.getTitle());
		jsonGenerator.writeNumberField("status", problem.getStatus().getStatusCode());
		
		if ( problem.getDetail() != null ) {
			jsonGenerator.writeStringField("detail", problem.getDetail());
		}
		
		jsonGenerator.writeEndObject();
	}
}

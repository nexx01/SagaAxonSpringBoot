package com.ashutov.userservice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@Configuration
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	//AxonConfig.java
	@Bean
	XStream xstream(){
		XStream xstream = new XStream();
		// clear out existing permissions and set own ones
		xstream.addPermission(NoTypePermission.NONE);
		// allow any type from the same package
		xstream.allowTypesByWildcard(new String[] {
				"com.ashutov.**",
				"com.example.**",
				"org.axonframework.**",
				"java.**",
				"com.thoughtworks.xstream.**"
		});

		return xstream;
	}
	// The XStream should be configured in such a way that a security solution is provided
	@Bean
	@Primary
	public Serializer serializer(XStream xStream) {
		return XStreamSerializer.builder().xStream(xStream).build();
	}
}
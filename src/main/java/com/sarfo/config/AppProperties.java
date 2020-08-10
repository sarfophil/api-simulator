package com.sarfo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("com.sarfo")
public class AppProperties {
	private Connection connection = new Connection();
	private JWTProperties jwt = new JWTProperties();

	@Data
	static class Connection {
		private String host;
		private String user;
		private String password;
		private String databaseName;
	}

	@Data
	static class JWTProperties {
		private String issuer;
		private String secret;
		private Long expirationTime;
		private String tokenPrefix;
		private String headerString;
	}
}

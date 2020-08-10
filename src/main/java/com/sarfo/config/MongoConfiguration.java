package com.sarfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableMongoRepositories(basePackages = {"com.sarfo.repository"})
@Configuration
public class MongoConfiguration extends AbstractReactiveMongoConfiguration{
	private AppProperties properties;

	public MongoConfiguration(AppProperties properties) {
		this.properties = properties;
	}

	@Override
	protected String getDatabaseName() {
		return properties.getConnection().getDatabaseName();
	}
	
	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create(connectionString());
	}
	
	@Bean
	public ConnectionString connectionString() {	
		return new ConnectionString(properties.getConnection().getHost());
	}
}

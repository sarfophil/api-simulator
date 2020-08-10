package com.sarfo.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app")
public class Application {
	@Id
	private String id;
	private String name;
	private String description;
	private List<Endpoint> endpoint = new ArrayList<>();
	private String accountId;
	public Application() {
	}

	public Application(String name,String description, List<Endpoint> endpoint) {
		super();
		this.name = name;
		this.description = description;
		this.endpoint = endpoint;
	}
}

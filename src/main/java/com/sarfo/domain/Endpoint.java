package com.sarfo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.MediaType;

@Data
public class Endpoint {
	private String name;
	private String description;
	private String url;
	private MethodType methodType;
	private String parameters;
	private String body;
	private String response;
	private String requestType;
	private String responseType;
}

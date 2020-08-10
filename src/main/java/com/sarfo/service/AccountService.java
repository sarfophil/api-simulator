package com.sarfo.service;

import com.sarfo.domain.Account;
import com.sarfo.domain.Application;
import com.sarfo.domain.Endpoint;
import com.sarfo.dto.ApplicationDto;
import com.sarfo.dto.EndpointDto;
import com.sarfo.exception.AccountExistException;
import com.sarfo.repository.AccountRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final ReactiveMongoTemplate mongoTemplate;
	private final BCryptPasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository,ReactiveMongoTemplate mongoTemplate,
						  BCryptPasswordEncoder passwordEncoder){
		this.accountRepository = accountRepository;
		this.mongoTemplate = mongoTemplate;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void createAccount(String email,String password){
		Optional<Account> accountOptional = accountRepository.findByEmail(email).blockOptional();
		if(accountOptional.isPresent()) throw new AccountExistException("Email is already taken by another user");
		else accountRepository.save(new Account(email,passwordEncoder.encode(password))).block();
	}
	
	public void addApplication(String email, ApplicationDto applicationDto) {
		Application app = applicationDto.createApplication();
		Optional<Account> accountOptional = mongoTemplate
				.findOne(Query.query(Criteria.where("email").is(email)), Account.class)
				.blockOptional();
		accountOptional.ifPresentOrElse(account -> mongoTemplate.save(app).block(),()->{
			throw new NoSuchElementException("Account is not available");
		});
	}

	public List<Application> getApplications(String email){
		Optional<Account> accountOptional = mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)),
									Account.class).blockOptional();
		Aggregation aggregation = newAggregation(
				match(Criteria.where("accountId").is(accountOptional.orElseThrow().getId())));
		Flux<Application> applicationAggregationResults = mongoTemplate
				.aggregate(aggregation,"app",Application.class);
		return applicationAggregationResults.collect(Collectors.toList()).block();
	}

	public void addEndpoints(List<EndpointDto> endpointDtoList, String appId){
		List<Endpoint> endpointList = endpointDtoList
				.stream().map(EndpointDto::createEndpoint).collect(Collectors.toList());
		Optional<Application> accountOptional = mongoTemplate
				.findOne(Query.query(Criteria.where("_id").is(appId)), Application.class)
				.blockOptional();
		accountOptional.ifPresentOrElse(application -> {
			application.setEndpoint(endpointList);
			mongoTemplate.save(application).block();
		}, () -> {
			throw new NoSuchElementException("Application is not available");
		});
	}

	public List<Endpoint> getEndpoints(String appId){
		return Optional.ofNullable(mongoTemplate.find(Query.query(Criteria.where("_id").is(appId)), Application.class)
				.blockFirst()).orElseThrow().getEndpoint();
	}

	public Endpoint getEndpointByUrl(String appId, String url, String requestMethod){
		Optional<Application> endpointOptional =
				mongoTemplate.findOne(Query.query(Criteria.where("id").is(appId)),Application.class)
				.blockOptional();
		Predicate<Endpoint> matchesUrl = (endpoint) -> endpoint.getUrl().equals(url);
		Predicate<Endpoint> matchesRequestMethod = (endpoint) -> endpoint.getMethodType().toString().equals(requestMethod);
		return endpointOptional.orElseThrow(()-> new NoSuchElementException("App Id not found"))
				.getEndpoint().stream().filter(matchesUrl.and(matchesRequestMethod))
				.findFirst().orElseThrow(()-> new NoSuchElementException("No Api endpoint found"));
	}
}

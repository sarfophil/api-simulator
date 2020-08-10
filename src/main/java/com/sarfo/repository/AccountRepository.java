package com.sarfo.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.sarfo.domain.Account;

import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveSortingRepository<Account,String>{
	Mono<Account> findByEmail(String email);
}

package com.sarfo.service;

import com.sarfo.domain.Account;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private ReactiveMongoTemplate reactiveMongoTemplate;
    public AuthService(ReactiveMongoTemplate reactiveMongoTemplate){
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Account> account = Mono.from(reactiveMongoTemplate.find(Query.query(Criteria.where("email")
                .is(email)), Account.class)).blockOptional();
        return account.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

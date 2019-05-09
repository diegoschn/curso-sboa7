package com.schneider.system.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.schneider.system.domain.User;
import com.schneider.system.repository.UserRepository;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private UserRepository repository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		repository.deleteAll();
		
		User diego = new User("a", "b", "a@gmail.com");
		User teste = new User("c", "d", "c@gmail.com");	
		
		createUserIfNotFound(diego);
		createUserIfNotFound(teste);
	}
	
	private User createUserIfNotFound(final User user) {
		Optional<User> userExists = repository.findByEmail(user.getEmail());
		
		if(userExists.isPresent()) {
			return userExists.get();
		}
		
		return repository.save(user);
	}

	

}

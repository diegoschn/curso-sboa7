package com.schneider.system.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.schneider.system.domain.Role;
import com.schneider.system.domain.User;
import com.schneider.system.repository.RoleRepository;
import com.schneider.system.repository.UserRepository;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		userRepository.deleteAll();
		roleRepository.deleteAll();
		
		Role admin = createRoleIfNotFound("ROLE_ADMIN");
		Role user = createRoleIfNotFound("ROLE_USER");
		
		User diego = new User("a", "b", "a@gmail.com");
		diego.setPassword(passwordEncoder.encode("123"));
		diego.setEnabled(true);
		User teste = new User("c", "d", "c@gmail.com");
		teste.setPassword(passwordEncoder.encode("234"));
		teste.setEnabled(true);
		
		diego.setRoles(Arrays.asList(admin));
		teste.setRoles(Arrays.asList(user));
		
		
		createUserIfNotFound(diego);
		createUserIfNotFound(teste);
		
	}
	
	private User createUserIfNotFound(final User user) {
		Optional<User> userExists = userRepository.findByEmail(user.getEmail());
		
		
		if(userExists.isPresent()) {
			System.out.println("Email existente!");
			return userExists.get();
		}
		
		return userRepository.save(user);
	}
	
	private Role createRoleIfNotFound(String name) {
		Optional<Role> roleExists = roleRepository.findByName(name);
		
		if(roleExists.isPresent()) {
			return roleExists.get();
		}
		
		return roleRepository.save(new Role(name));
	}

	

}

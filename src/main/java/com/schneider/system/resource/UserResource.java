package com.schneider.system.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.schneider.system.domain.User;
import com.schneider.system.dto.UserDTO;
import com.schneider.system.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> findAll(){
		List<User> users = service.findAll();
		List<UserDTO> listDTO = users.stream().map(x-> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping(value="/users",method=RequestMethod.POST)
	public void cadastro(@RequestBody User user) {
		service.salvar(user);
	}
}

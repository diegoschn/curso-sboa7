package com.schneider.system.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.schneider.system.domain.Role;
import com.schneider.system.domain.User;
import com.schneider.system.dto.UserDTO;
import com.schneider.system.repository.RoleRepository;
import com.schneider.system.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {
	
	@Autowired
	private UserService service;
	

	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> users = service.buscarTodos();
		List<UserDTO> listDTO = users.stream().map(x-> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}
	
	@RequestMapping(value="/users",method=RequestMethod.POST)
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
		User user = service.fromDTO(userDTO);
		return ResponseEntity.ok(new UserDTO(service.salvar(user)));
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	public ResponseEntity<UserDTO> update(@PathVariable String id,@RequestBody UserDTO userDTO) {
		User user = service.fromDTO(userDTO);
		user.setId(id);
		return ResponseEntity.ok(new UserDTO(service.atualizar(user)));
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User user = service.buscarUsuarioPorId(id);
		return ResponseEntity.ok(new UserDTO(user));
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/users/{id}/roles")
	public ResponseEntity<List<Role>> getRoles(@PathVariable String id){
	
		User user = service.buscarUsuarioPorId(id);
		
		return ResponseEntity.ok().body(user.getRoles());
	}
}

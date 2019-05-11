package com.schneider.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schneider.system.domain.User;
import com.schneider.system.dto.UserDTO;
import com.schneider.system.repository.UserRepository;
import com.schneider.system.service.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> buscarTodos(){
		return repository.findAll();
	}
	
	public User salvar(User user) {
		return repository.save(user);
	}
	
	public User buscarUsuarioPorId(String id) {
		Optional<User> user = repository.findById(id);
		
		return user.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User fromDTO(UserDTO userDTO) {
		return new User(userDTO);
	}
	
	public User atualizar(User user) {
		Optional<User> userExistente = repository.findById(user.getId());
		
		return userExistente.map(u->repository.save(new User(u.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),
				u.getPassword(),u.isEnabled())))
				.orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado"));
	}
	
	public void deletar(String id) {
		repository.deleteById(id);
	}
	
	
	
	
}

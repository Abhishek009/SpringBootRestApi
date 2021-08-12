package com.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.User;
import com.api.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	// Get the list of users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Add a user
	@PutMapping("/addUser")
	public List<User> createUser(@RequestBody User user) {
		userRepository.save(user);
		return userRepository.findAll();
	}

	// Get the user based on the id
	@GetMapping("/getUser/{id}")
	public String getUserByID(@PathVariable long id) {
		return userRepository.findById(id).toString();
	}

	// Delete the user based on the id
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
		Optional<User> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			userRepository.deleteById(userData.get().getId());
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// Update the user base on the id
	@PutMapping("/upadateUser/{id}")
	public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable long id) {
		Optional<User> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			User userToUpdate = userData.get();
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setFirstName(user.getFirstName());
			userToUpdate.setLastName(user.getLastName());
			return new ResponseEntity<>(userRepository.save(userToUpdate), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}

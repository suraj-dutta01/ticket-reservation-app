package org.jsp.reservationapi.controller;

import java.util.Optional;

import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@Valid @RequestBody UserRequest userRequest,HttpServletRequest request){
	    return userService.saveUser(userRequest,request);
	}
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@Valid @RequestBody UserRequest userRequest,@PathVariable(name = "id")int id){
		return userService.updateUser(userRequest,id);
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> findById(@PathVariable(name = "id") int id){
		return userService.findById(id);
	}
	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(@RequestParam(value = "phone") long phone,@RequestParam(value = "password")String password){
		return userService.verifyUser(phone, password);
	}
	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyuser(@RequestParam(value = "email")String email,@RequestParam(value = "password")String password){
		return userService.verifyuser(email, password);
	}
	@GetMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteuser(@PathVariable(name = "id") int id){
		return userService.deleteUser(id);
	}
    @GetMapping("/activate")
	public String activationAccount(String token) {
		return userService.activationAccount(token);
	}

}

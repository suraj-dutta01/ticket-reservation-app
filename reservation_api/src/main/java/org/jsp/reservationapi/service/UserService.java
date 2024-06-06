package org.jsp.reservationapi.service;

import java.util.Optional;


import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.exceptions.UserNotFoundException;
import org.jsp.reservationapi.exceptions.UserVerificationFailedException;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private EmailConfiguration emailConfiguration;
	
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest,HttpServletRequest request){
		ResponseStructure<UserResponse> structure=new ResponseStructure<>();
		User user=mapToUaer(userRequest);
		user.setStatus(AccountStatus.IN_ACTIVE.toString());
		user= userDao.saveUser(user);
		String activation_link=linkGeneratorService.getUserActivationLink(user, request);
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration.setText("Dear User Please Activate Your Account By Clicking on the Link: "+activation_link);
		emailConfiguration.setToAddress(user.getEmail());	
	    structure.setMessage(mailService.sendMail(emailConfiguration));
	    structure.setData(mapToUserResponse(user));
	    structure.setStatusCode(HttpStatus.CREATED.value());
	    return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	
	
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest,int id){
		ResponseStructure<UserResponse> structure=new ResponseStructure<>();
		Optional<User> resUser=userDao.findById(id);
		if(resUser.isPresent()) {
			User dbUser=resUser.get();
			dbUser.setName(userRequest.getName());
			dbUser.setAge(userRequest.getAge());
			dbUser.setEmail(userRequest.getEmail());
			dbUser.setGender(userRequest.getGender());
			dbUser.setPhone(userRequest.getPhone());
			dbUser.setPassword(userRequest.getPassword());
			structure.setMessage("user Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setData(mapToUserResponse(userDao.saveUser(dbUser)));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new UserNotFoundException("User Id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<UserResponse>> findById(int id){
		ResponseStructure<UserResponse> structure=new ResponseStructure<>();
		Optional<User> resUser=userDao.findById(id);
		if(resUser.isPresent()) {
			structure.setMessage("user Found");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToUserResponse(resUser.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("user id is invalid");
	}
	public ResponseEntity<ResponseStructure<UserResponse>> verifyUser(long phone,String password){
		ResponseStructure<UserResponse> structure=new ResponseStructure<>();
		Optional<User> resUser=userDao.verifyUser(phone, password);
		if(resUser.isPresent()) {
			User user=resUser.get();
			if(user.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new UserNotFoundException("Please Activate your account");
			
			structure.setMessage("user Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToUserResponse(resUser.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserVerificationFailedException("user Verifivation failed because of invalid cradentials");
	}
	public ResponseEntity<ResponseStructure<UserResponse>> verifyuser(String email,String password){
		ResponseStructure<UserResponse> structure=new ResponseStructure<>();
		Optional<User> resUser=userDao.verifyUser(email, password);
		if(resUser.isPresent()) {
			User user=resUser.get();
			if(user.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new UserNotFoundException("Please Activate your account");
			
			structure.setMessage("User Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToUserResponse(resUser.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserVerificationFailedException("user Verifivation failed because of invalid cradentials");
	}
	public ResponseEntity<ResponseStructure<String>> deleteUser(int id){
		ResponseStructure<String> structure=new ResponseStructure<>();
		if(userDao.findById(id).isPresent()) {
			structure.setMessage("User Deleted");
			userDao.deleteUser(id);
			structure.setData("user is deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("user id is invalid");
	}

	private User mapToUaer(UserRequest userRequest) {
		return User.builder().name(userRequest.getName()).phone(userRequest.getPhone())
				.email(userRequest.getEmail()).age(userRequest.getAge()).gender(userRequest.getGender())
				.password(userRequest.getPassword()).build();
	}
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().id(user.getId()).name(user.getName()).phone(user.getPhone()).
				email(user.getEmail()).age(user.getAge()).gender(user.getGender()).password(user.getPassword()).build();
	}
	public String activationAccount(String token) {
		Optional<User> resUser=userDao.findByToken(token);
		if(resUser.isEmpty())
			throw new UserNotFoundException("Invalid Token");
		User user=resUser.get();
		user.setStatus(AccountStatus.ACTIVE.toString());
		user.setToken(null);
		userDao.saveUser(user);
		return "Your Account has been activated";
	}

}

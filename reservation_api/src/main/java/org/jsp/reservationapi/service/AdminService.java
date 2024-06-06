package org.jsp.reservationapi.service;

import java.util.Optional;


import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exceptions.AdminNotFoundException;
import org.jsp.reservationapi.exceptions.AdminVerificationFailedException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private EmailConfiguration emailConfiguration;
	
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest,HttpServletRequest request){
		ResponseStructure<AdminResponse> structure=new ResponseStructure<>();
		Admin admin=mapToAdmin(adminRequest);
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());
		admin= adminDao.saveAdmin(admin);		
		String activation_link=linkGeneratorService.getAdminActivationLink(admin, request);
		emailConfiguration.setSubject("Activate Your Account");
		emailConfiguration.setText("Dear Admin Please Activate Your Account By Clicking on the Link: "+activation_link);
		emailConfiguration.setToAddress(admin.getEmail());
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setData(mapToAdminResponse(admin));
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest,int id){
		ResponseStructure<AdminResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(id);
		if(resAdmin.isPresent()) {
			Admin dbAdmin=resAdmin.get();
			dbAdmin.setEmail(adminRequest.getEmail());
			dbAdmin.setName(adminRequest.getName());
			dbAdmin.setGst_number(adminRequest.getGst_number());
			dbAdmin.setPhone(adminRequest.getPhone());
			dbAdmin.setPassword(adminRequest.getPassword());
			dbAdmin.setTravels_name(adminRequest.getTravels_name());
			structure.setMessage("Admin Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setData(mapToAdminResponse(adminDao.saveAdmin(dbAdmin)));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> findById(int id){
		ResponseStructure<AdminResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(id);
		if(resAdmin.isPresent()) {
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToAdminResponse(resAdmin.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(long phone,String password){
		ResponseStructure<AdminResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.verifyAdmin(phone, password);
		if(resAdmin.isPresent()) {
			Admin admin=resAdmin.get();
			if(admin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account");
			
			structure.setMessage("Admin Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToAdminResponse(resAdmin.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminVerificationFailedException("Admin Verifivation failed because of invalid cradentials");
	}
	
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(String email,String password){
		ResponseStructure<AdminResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.verifyAdmin(email, password);
		if(resAdmin.isPresent()) {
			Admin admin=resAdmin.get();
			if(admin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Activate Your Account");
	
			structure.setMessage("Admin Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(mapToAdminResponse(resAdmin.get()));
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminVerificationFailedException("Admin Verifivation failed because of invalid cradentials");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(int id){
		ResponseStructure<String> structure=new ResponseStructure<>();
		if(adminDao.findById(id).isPresent()) {
			structure.setMessage("Admin Deleted");
			adminDao.deleteById(id);
			structure.setData("Admin is deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	
	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().email(adminRequest.getEmail()).name(adminRequest.getName())
				.phone(adminRequest.getPhone()).gst_number(adminRequest.getGst_number())
				.password(adminRequest.getPassword()).travels_name(adminRequest.getTravels_name()).build();
	}
	private AdminResponse mapToAdminResponse(Admin admin) {
		return AdminResponse.builder().id(admin.getId()).name(admin.getName()).phone(admin.getPhone())
				.email(admin.getEmail()).gst_number(admin.getGst_number()).travels_name(admin.getTravels_name())
				.password(admin.getPassword()).build();
	}
	
	public String activateAccount(String token) {
		Optional<Admin> resAdmin=adminDao.findByToken(token);
		if(resAdmin.isEmpty()) {
			throw new AdminNotFoundException("Invalid Token");
		}else {
			Admin dbAdmin=resAdmin.get();
			dbAdmin.setStatus(AccountStatus.ACTIVE.toString());
			dbAdmin.setToken(null);
			adminDao.saveAdmin(dbAdmin);
			return "Your Account has been activated";
		}
	}
	
	public String forgotPassword(String email,HttpServletRequest request) {
		Optional<Admin> recAdmin=adminDao.findByEmail(email);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Invalid Email Id");
		Admin admin=recAdmin.get();
		String reset_password_link=linkGeneratorService.getAdminResetPasswordLink(admin, request);
		emailConfiguration.setToAddress(email);
		emailConfiguration.setText("Please Click On the following Link to reset password "+reset_password_link);
		emailConfiguration.setSubject("RESET YOUR PASSWORD");
		mailService.sendMail(emailConfiguration);
		return "Reset password link is send to register mail id";
	}
	public AdminResponse verifyLink(String token) {
		Optional<Admin> recAdmin=adminDao.findByToken(token);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Link Has been Expire Or invalid");
		
		Admin dbAdmin=recAdmin.get();
		dbAdmin.setToken(null);
		adminDao.saveAdmin(dbAdmin);
		return mapToAdminResponse(dbAdmin);
	}
	public String findTokenById(int id) {
		String token=adminDao.findTokenById(id);
		if(token!=null) {
			return token;
		}
		return "notoken";
	}

}

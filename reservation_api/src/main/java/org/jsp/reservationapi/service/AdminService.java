package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exceptions.AdminNotFoundException;
import org.jsp.reservationapi.exceptions.AdminVerificationFailedException;
import org.jsp.reservationapi.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(AdminRequest adminRequest){
		ResponseStructure<Admin> structure=new ResponseStructure<>();
		structure.setMessage("Admin Saved");
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setData(adminDao.saveAdmin(mapToAdmin(adminRequest)));
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(AdminRequest adminRequest,int id){
		ResponseStructure<Admin> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(id);
		if(resAdmin.isPresent()) {
			Admin dbAdmin=mapToAdmin(adminRequest);
			dbAdmin.setId(id);
			structure.setMessage("Admin Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setData(adminDao.saveAdmin(dbAdmin));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	public ResponseEntity<ResponseStructure<Admin>> findById(int id){
		ResponseStructure<Admin> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(id);
		if(resAdmin.isPresent()) {
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(resAdmin.get());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Admin id is invalid");
	}
	public ResponseEntity<ResponseStructure<Admin>> verifyAdmin(long phone,String password){
		ResponseStructure<Admin> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.verifyAdmin(phone, password);
		if(resAdmin.isPresent()) {
			structure.setMessage("Admin Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(resAdmin.get());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminVerificationFailedException("Admin Verifivation failed because of invalid cradentials");
	}
	public ResponseEntity<ResponseStructure<Admin>> verifyAdmin(String email,String password){
		ResponseStructure<Admin> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.verifyAdmin(email, password);
		if(resAdmin.isPresent()) {
			structure.setMessage("Admin Verification successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(resAdmin.get());
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
		return Admin.builder().email(adminRequest.getEmail()).name(adminRequest.getName()).phone(adminRequest.getPhone()).gst_number(adminRequest.getGst_number()).password(adminRequest.getPassword()).travels_name(adminRequest.getTravels_name()).build();
	}

}

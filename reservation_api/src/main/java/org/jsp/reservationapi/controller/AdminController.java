package org.jsp.reservationapi.controller;


import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.service.AdminService;
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

@RestController
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@RequestBody AdminRequest adminRequest){
		return adminService.saveAdmin(adminRequest);
	}
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@RequestBody AdminRequest adminRequest,@PathVariable(name = "id")int id){
		return adminService.updateAdmin(adminRequest,id);
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findById(@PathVariable(name = "id") int id){
		return adminService.findById(id);
	}
	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(@RequestParam(value = "email") String email,@RequestParam(value = "password")String password){
		return adminService.verifyAdmin(email, password);
	}
	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponseStructure<AdminResponse>> verifyAdmin(@RequestParam(value = "phone")long phone,@RequestParam(value = "password")String password){
		return adminService.verifyAdmin(phone, password);
	}
	@GetMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(@PathVariable(name = "id") int id){
		return adminService.deleteAdmin(id);
	}

}

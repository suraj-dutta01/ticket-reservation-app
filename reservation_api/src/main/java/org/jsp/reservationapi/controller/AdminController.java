package org.jsp.reservationapi.controller;

import java.io.IOException;

import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
@CrossOrigin
@RestController
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@Valid @RequestBody AdminRequest adminRequest,HttpServletRequest request){
		return adminService.saveAdmin(adminRequest,request);
	}
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@Valid @RequestBody AdminRequest adminRequest,@PathVariable(name = "id")int id){
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
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(@PathVariable(name = "id") int id){
		return adminService.deleteAdmin(id);
	}
	@GetMapping("/activate")
	public String activateAccount(@RequestParam String token) {
		return adminService.activateAccount(token);
	}
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email,HttpServletRequest request) {
		return adminService.forgotPassword( email, request);
	}
	@GetMapping("/verify-link")
	public void verifyResetPasswordLink(@RequestParam String token,HttpServletResponse respon) {
		AdminResponse adminResponse= adminService.verifyLink(token);
		if(adminResponse !=null) {
			try {
				respon.sendRedirect("http://localhost:3000/resetpassword");
			} catch (IOException e) {
                e.printStackTrace();
			}
		}
	}
	@GetMapping("/token/{id}")
	public String findTokenById(@PathVariable(value = "id") int id) {
		return adminService.findTokenById(id);
	}

}

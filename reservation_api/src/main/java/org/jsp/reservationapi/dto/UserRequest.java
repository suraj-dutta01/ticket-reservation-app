package org.jsp.reservationapi.dto;

import lombok.Data;
@Data
public class UserRequest {
	private String name;
	private String email;
	private long phone;
	private int age;
	private String gender;
	private String password;

}

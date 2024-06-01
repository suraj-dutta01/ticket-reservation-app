package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UserRequest {
	@NotBlank(message = "Name is Mendatory")
	private String name;
	@Email(message = "Provide a valid Email")
	private String email;
	private long phone;
	private int age;
    @NotBlank(message = "Gender is mendatory")
	private String gender;
    @NotBlank(message = "Password is mendatory")
    @Size(min = 6,max = 20,message = "Provide a valid password")
	private String password;

}

package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRequest {
	@NotBlank(message = "Name is Mendatory")
	private String name;
	private long phone;
	@Email(message = "Invalid Format")
	private String email;
	@NotBlank(message = "Gst number is mendatory")
	@Size(min = 15,max = 15,message = "Provide a valid Gst number")
	private String gst_number;
	@NotBlank(message = "Travels name is mendatory")
	private String travels_name;
	@NotBlank(message = "Password is mendatory")
	@Size(min = 6,max = 20,message = "Password Length between 6 to 20")
	private String password;
}

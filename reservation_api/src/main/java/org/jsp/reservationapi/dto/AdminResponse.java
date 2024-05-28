package org.jsp.reservationapi.dto;

import java.util.List;

import org.jsp.reservationapi.model.Bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {
	private int id;
	private String name;
	private long phone;
	private String email;
	private String gst_number;
	private String travels_name;
	private String password;
	private List<Bus> busses;

}

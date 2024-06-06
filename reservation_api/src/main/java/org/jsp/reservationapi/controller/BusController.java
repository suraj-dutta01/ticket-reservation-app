package org.jsp.reservationapi.controller;


import java.time.LocalDate;

import java.util.List;

import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.BusResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.service.BusService;
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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
@CrossOrigin
@RestController
@RequestMapping("/api/busses")
public class BusController {
	@Autowired
	private BusService busService;
	@PostMapping("/{id}")
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus (@Valid @RequestBody BusRequest busRequest,@PathVariable(value = "id") int admin_id){
		return busService.saveBus(busRequest, admin_id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(@Valid @RequestBody BusRequest busRequest,@PathVariable(name = "id") int bus_id){
		return busService.updateBus(busRequest, bus_id);	
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<BusResponse>> findById(@PathVariable(value = "id") int id){
		return busService.findById(id);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable(value = "id") int id){
		return busService.deleteById(id);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Bus>>> findAllBus(){
		return busService.findAllBus();
	}
	@GetMapping("/{from}/{to}/{date}")
	public ResponseEntity<ResponseStructure<List<Bus>>> findBusess(@PathVariable(value = "from") String from_location,@PathVariable(value = "to")String to_location,@PathVariable(value = "date")LocalDate date_of_departure){
		return busService.findBusess(from_location, to_location, date_of_departure);
	}
	@GetMapping("/admin/{id}")
	public ResponseEntity<ResponseStructure<List<Bus>>> findBusByAdminId(@PathVariable(value = "id") int id){
		return busService.findBusByAdminId(id);
	}
}

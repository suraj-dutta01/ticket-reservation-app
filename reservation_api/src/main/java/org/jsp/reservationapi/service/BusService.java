package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.BusResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exceptions.AdminNotFoundException;
import org.jsp.reservationapi.exceptions.BusNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusService {
	@Autowired
	private BusDao busDao;
	@Autowired
	private AdminDao adminDao;
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(BusRequest busRequest,int admin_id){
		ResponseStructure<BusResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(admin_id);
		if(resAdmin.isPresent()) {
			Bus bus=mapToBus(busRequest);
			Admin admin=resAdmin.get();
			bus.setAdmin(admin);
			structure.setMessage("Bus Saved");
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setData(mapToBusResponce(busDao.saveBus(bus)));
			admin.getBusses().add(bus);
	        adminDao.saveAdmin(admin);
			return ResponseEntity.status(HttpStatus.CREATED).body(structure);
		}
		throw new AdminNotFoundException("Bus is Not save As Admin id is invalid");	
	}
	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(BusRequest busRequest,int bus_id){
		ResponseStructure<BusResponse> structure=new ResponseStructure<>();
		Optional<Bus> resBus=busDao.findBusById(bus_id);
		if(resBus.isPresent()) {
			Bus dbBus=resBus.get();
			dbBus.setName(busRequest.getName());
			dbBus.setBus_number(busRequest.getBus_number());
			dbBus.setFrom_location(busRequest.getFrom_location());
			dbBus.setTo_location(busRequest.getTo_location());
			dbBus.setNumber_of_seats(busRequest.getNumber_of_seats());
			dbBus.setDate_of_departure(busRequest.getDate_of_departure());
			structure.setMessage("Bus is Updated");
			structure.setData(mapToBusResponce(busDao.saveBus(dbBus)));
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new BusNotFoundException("Bus is not found as id is invalid");	
	}
	public ResponseEntity<ResponseStructure<BusResponse>> findById(int id){
		ResponseStructure<BusResponse> structure=new ResponseStructure<>();
		Optional<Bus> resBus=busDao.findBusById(id);
		if(resBus.isPresent()) {
			structure.setData(mapToBusResponce(resBus.get()));
			structure.setMessage("Bus Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new BusNotFoundException("Bus not found As Id is invalid");
	}
	public ResponseEntity<ResponseStructure<String>> deleteById(int id){
		ResponseStructure<String> structure=new ResponseStructure<>();
		if (busDao.findBusById(id).isPresent()) {
			busDao.deleteBus(id);
			structure.setMessage("Bus Found");
			structure.setData("Bus Deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new BusNotFoundException("Bus Not Found As Bus Id Is Invalid");
	}
	
	private Bus mapToBus(BusRequest busRequest) {
		return Bus.builder().name(busRequest.getName()).bus_number(busRequest.getBus_number())
				.number_of_seats(busRequest.getNumber_of_seats()).from_location(busRequest.getFrom_location())
				.to_location(busRequest.getTo_location()).date_of_departure(busRequest.getDate_of_departure()).build();
	}

	private BusResponse mapToBusResponce(Bus bus) {
		return BusResponse.builder().id(bus.getId()).name(bus.getName()).bus_number(bus.getBus_number())
				.number_of_seats(bus.getNumber_of_seats()).from_location(bus.getFrom_location())
				.to_location(bus.getTo_location()).date_of_departure(bus.getDate_of_departure()).build();
	}

}
package org.jsp.reservationapi.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dao.TicketDao;
import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.BusResponse;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.exceptions.AdminNotFoundException;
import org.jsp.reservationapi.exceptions.BusNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.model.Ticket;
import org.jsp.reservationapi.util.TicketStatus;
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
	@Autowired
	private TicketDao ticketDao;
	
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(BusRequest busRequest,int admin_id){
		ResponseStructure<BusResponse> structure=new ResponseStructure<>();
		Optional<Admin> resAdmin=adminDao.findById(admin_id);
		if(resAdmin.isPresent()) {
			Bus bus=mapToBus(busRequest);
			bus.setNumber_of_avilable_seats(bus.getNumber_of_seats());
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
		Optional<Bus> resBus=busDao.findBusById(id);
		if (resBus.isPresent()) {
			Bus bus=resBus.get();
			List<Ticket> tickets=ticketDao.findByBusId(bus.getId());
			if(tickets.size()>0) {
				for(Ticket ticket:tickets) {
					ticket.setBus(null);
					ticket.setStatus(TicketStatus.CANCELLED.toString());
					ticketDao.saveTicket(ticket);
				}
				busDao.deleteBus(id);
			}else {
				busDao.deleteBus(id);
			}
			
			structure.setMessage("Bus Found");
			structure.setData("Bus Deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new BusNotFoundException("Bus Not Found As Bus Id Is Invalid");
	}
	
	public ResponseEntity<ResponseStructure<List<Bus>>> findAllBus(){
		ResponseStructure<List<Bus>>structure=new ResponseStructure<>();
		List<Bus> buses=busDao.findAllBus();
		if(buses.isEmpty()) {
		   throw new BusNotFoundException("Not any Bus Found");
		}else {
			structure.setMessage("Bus Found");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(buses);
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
	}
	
	private Bus mapToBus(BusRequest busRequest) {
		return Bus.builder().name(busRequest.getName()).bus_number(busRequest.getBus_number())
				.number_of_seats(busRequest.getNumber_of_seats()).from_location(busRequest.getFrom_location())
				.to_location(busRequest.getTo_location()).date_of_departure(busRequest.getDate_of_departure()).cost_per_seats(busRequest.getCost_per_seats()).description(busRequest.getDescription()).imageUrl(busRequest.getImageUrl()).build();
	}

	private BusResponse mapToBusResponce(Bus bus) {
		return BusResponse.builder().id(bus.getId()).name(bus.getName()).bus_number(bus.getBus_number())
				.number_of_seats(bus.getNumber_of_seats()).from_location(bus.getFrom_location())
				.to_location(bus.getTo_location()).date_of_departure(bus.getDate_of_departure()).number_of_avilable_seats(bus.getNumber_of_avilable_seats()).cost_per_seats(bus.getCost_per_seats()).description(bus.getDescription()).imageUrl(bus.getImageUrl()).build();
	}

	public ResponseEntity<ResponseStructure<List<Bus>>> findBusByAdminId(int id){
		ResponseStructure<List<Bus>>structure=new ResponseStructure<>();
		List<Bus> resBus=busDao.findByAdminId(id);
		if(resBus.isEmpty()) {
			throw new BusNotFoundException("No bus found under this admin");
		}
		structure.setData(resBus);
		structure.setMessage("Bus found under this admin");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
	public ResponseEntity<ResponseStructure<List<BusResponse>>> findBusess(String from_location,String to_location,LocalDate date_of_departure){
		ResponseStructure<List<BusResponse>>structure=new ResponseStructure<>();
		List<Bus> resBus=busDao.findBusess(from_location, to_location, date_of_departure);
		if(resBus.isEmpty()) {
			throw new BusNotFoundException("No bus found in this rute");
		}
		List<BusResponse> busess=new LinkedList<>();
		for(Bus b:resBus) {
			busess.add(mapToBusResponce(b));
		}
		structure.setData(busess);
		structure.setMessage("Bus found");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
}

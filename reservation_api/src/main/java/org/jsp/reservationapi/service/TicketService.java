package org.jsp.reservationapi.service;

import java.util.Optional;


import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dao.TicketDao;
import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.TicketResponse;
import org.jsp.reservationapi.exceptions.BusNotFoundException;
import org.jsp.reservationapi.exceptions.TicketNotFoundException;
import org.jsp.reservationapi.exceptions.UserNotFoundException;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.model.Ticket;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.util.AccountStatus;
import org.jsp.reservationapi.util.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BusDao busDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private EmailConfiguration emailConfiguration;
	public ResponseEntity<ResponseStructure<TicketResponse>> bookTicket(int userId,int busId,int numberOfSeats) {
	    ResponseStructure<TicketResponse> structure=new ResponseStructure<>();
		Optional<Bus> resBus=busDao.findBusById(busId);
		Optional<User> resUser=userDao.findById(userId);
		if(resBus.isEmpty())
			throw new BusNotFoundException("Cannot Book Ticket as Bus is invalid");
		if(resUser.isEmpty())
			throw new UserNotFoundException("Cannot book ticket as user id is invalid");
		User dbUser=resUser.get();
		if(dbUser.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
			throw new IllegalStateException("Please Active Your Account, Then Book Ticket");
		Bus dbBus=resBus.get();
		if(dbBus.getNumber_of_avilable_seats()<numberOfSeats)
			throw new IllegalArgumentException("Insufficent Seat");
		Ticket ticket=new Ticket();
		ticket.setCost(numberOfSeats*dbBus.getCost_per_seats());
		ticket.setStatus(TicketStatus.BOOKED.toString());
		ticket.setBus(dbBus);
		ticket.setUser(dbUser);
		ticket.setNumber_Of_Seats_Booked(numberOfSeats);
		dbBus.getBooked_tickets().add(ticket);
		dbUser.getTickets().add(ticket);
		dbBus.setNumber_of_avilable_seats(dbBus.getNumber_of_avilable_seats()-numberOfSeats);
		userDao.saveUser(dbUser);
		busDao.saveBus(dbBus);
		ticket=ticketDao.saveTicket(ticket);
		emailConfiguration.setToAddress(dbUser.getEmail());
		emailConfiguration.setSubject("Your Ticket Booking is Successful!");
		emailConfiguration.setText("Dear "+dbUser.getName()+", We are pleased to inform you that your bus ticket booking has been successfully completed! "
				+ "Route: "+dbBus.getFrom_location()+ " to " +dbBus.getTo_location()
				+ " Date of Journey: "+dbBus.getDate_of_departure()
				+ " Bus Number: "+dbBus.getBus_number()
				+ " Number Of Seats: "+ticket.getNumber_Of_Seats_Booked()
				+ " Total Cost: "+(ticket.getCost())
				+ " Ticket Id: "+ticket.getId()
				+ " Thank you for choosing our service for your travel needs. We wish you a pleasant and comfortable journey");
		mailService.sendMail(emailConfiguration);
		structure.setData(mapToTicketResponse(ticket, dbUser, dbBus));
		structure.setMessage("Ticket Booking Successfull");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}
	public TicketResponse mapToTicketResponse(Ticket ticket,User user,Bus bus) {
		TicketResponse ticketResponse=new TicketResponse();
		ticketResponse.setAge(user.getAge());
		ticketResponse.setBusName(bus.getName());
		ticketResponse.setBusNumber(bus.getBus_number());
		ticketResponse.setCost(ticket.getCost());
		ticketResponse.setDate_And_Time_Of_Booking(ticket.getDate_And_Time_Of_Booking());
		ticketResponse.setDateOfDeparture(bus.getDate_of_departure());
		ticketResponse.setDestination(bus.getTo_location());
		ticketResponse.setGender(user.getGender());
		ticketResponse.setId(ticket.getId());
		ticketResponse.setNumberOfSeatsBooked(ticket.getNumber_Of_Seats_Booked());
		ticketResponse.setPhone(user.getPhone());
		ticketResponse.setSource(bus.getFrom_location());
		ticketResponse.setStatus(ticket.getStatus());
		ticketResponse.setUsername(user.getName());
		return ticketResponse;
	}
	public ResponseEntity<ResponseStructure<String>> cancelTicket(int busId,int userId,int ticketId) {
		ResponseStructure<String> structure=new ResponseStructure<String>();
		Optional<Bus>resBus=busDao.findBusById(busId);
		Optional<User>resUser=userDao.findById(userId);
		Optional<Ticket>resTicket=ticketDao.findById(ticketId);
		if(resBus.isEmpty())
			throw new BusNotFoundException("Bus Id is Invalid");
		if(resUser.isEmpty())
			throw new UserNotFoundException("User Id is Invalid");
		if(resTicket.isEmpty())
			throw new TicketNotFoundException("Ticket Id is Invalid");
		Ticket ticket=resTicket.get();
		User user=resUser.get();
		Bus bus=resBus.get();
		if(ticket.getUser().getId()==user.getId() && ticket.getBus().getId()==bus.getId()) {
			ticket.setStatus(TicketStatus.CANCELLED.toString());
			bus.setNumber_of_avilable_seats(bus.getNumber_of_avilable_seats()+ticket.getNumber_Of_Seats_Booked());
			ticketDao.saveTicket(ticket);
			busDao.saveBus(bus);
			emailConfiguration.setToAddress(ticket.getUser().getEmail());
			emailConfiguration.setSubject("TICKET CANCALITATION");
			emailConfiguration.setText("Dear user, Your ticket has been cancled successfully monet will be refunder to Your Account soon");
			mailService.sendMail(emailConfiguration);
			structure.setMessage("User Bus And Ticket Found");
			structure.setData("Ticket Cancalation successfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		structure.setMessage("Please Provide Valid Cradentials");
		structure.setData("Ticket Cancalation Failed");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
		    
	}
	

}

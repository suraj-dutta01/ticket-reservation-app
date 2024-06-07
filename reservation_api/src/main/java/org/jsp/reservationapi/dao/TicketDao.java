package org.jsp.reservationapi.dao;

import java.util.List;

import org.jsp.reservationapi.model.Ticket;
import org.jsp.reservationapi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao {
	@Autowired
	private TicketRepository ticketRepository;
	
	public Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	public List<Ticket> findByBusId(int id){
		return ticketRepository.findByBusId(id);
	}
	public List<Ticket> findByUserId(int id){
		return ticketRepository.findByUserId(id);
	}

}

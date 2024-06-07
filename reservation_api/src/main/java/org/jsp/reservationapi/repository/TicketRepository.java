package org.jsp.reservationapi.repository;

import java.util.List;

import org.jsp.reservationapi.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByBusId(int id);
	List<Ticket> findByUserId(int id);
}

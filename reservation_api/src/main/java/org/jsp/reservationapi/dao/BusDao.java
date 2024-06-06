package org.jsp.reservationapi.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BusDao {
	@Autowired
	private BusRepository busRepository;
	public Bus saveBus(Bus bus) {
		return busRepository.save(bus);
	}
	public Optional<Bus> findBusById(int id){
		return busRepository.findById(id);
	}
	public void deleteBus(int id) {
		busRepository.deleteById(id);
	}
	public List<Bus> findAllBus(){
		return busRepository.findAll();
	}
	public List<Bus> findByAdminId(int id){
		return busRepository.findByAdminId(id);
	}
	public List<Bus> findBusess(String from_location,String to_location,LocalDate date_of_departure){
		return busRepository.findBusess(from_location, to_location, date_of_departure);
	}

}

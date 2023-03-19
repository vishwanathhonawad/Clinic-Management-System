package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Doctor;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.repository.DoctorRepository;

@Service
public class DoctorService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	DoctorRepository doctorRepository;

	public List<Doctor> listAllDoctors() throws DoctorNotFoundException {
		List<Doctor> listDoctors = doctorRepository.findAll();
		if (listDoctors.isEmpty()) {
			logger.error("Exception : Doctor Not Found Exception Thrown ");
			throw new DoctorNotFoundException();
		} else {
			logger.info("Returned list of all Doctors ");
			return listDoctors;
		}
	}
	
	public  Doctor findDoctorById(int doctorId) throws DoctorNotFoundException{
		Optional<Doctor> doctorOp= doctorRepository.findById(doctorId);
		if(doctorOp.isPresent()) {
			logger.info("Returned doctor with Id :{}" ,doctorId);
			return doctorOp.get();
		}else {
			logger.error("Exception : Doctor Not Found Exception Thrown ");
			throw new DoctorNotFoundException();
		}
	}
}

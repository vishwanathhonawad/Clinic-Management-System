package com.demo.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.Speciality;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.SpecialityNotExistsException;
import com.demo.spring.repository.SpecialityRepository;

@Service
public class SpecialityService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	SpecialityRepository specialityRepository;
	
	public List<Doctor> listAllDoctorsOfSpeciality(int specialityId) throws DoctorNotFoundException,SpecialityNotExistsException {
		Optional<Speciality> specialityOp=specialityRepository.findById(specialityId);
		if(specialityOp.isPresent()) {
			List<Doctor> listDoctors=specialityOp.get().getDoctors().stream().collect(Collectors.toList());
			if(listDoctors.isEmpty()) {
				logger.error("Exception : Doctor Not Found Exception Thrown ");
				throw new DoctorNotFoundException();
			}else {
				logger.info("Returned Doctors list with speciality Id : {}",specialityId);
				return listDoctors;
			}
		}else {
			logger.error("Exception : Speciality Not Exists Exception Thrown ");
			throw new SpecialityNotExistsException();
		}
	}
}

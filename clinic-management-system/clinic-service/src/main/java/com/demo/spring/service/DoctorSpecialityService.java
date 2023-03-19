package com.demo.spring.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.DoctorSpeciality;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.DoctorSpecialityExists;
import com.demo.spring.exception.DoctorSpecialityNotExists;
import com.demo.spring.exception.SpecialityNotExistsException;
import com.demo.spring.repository.DoctorRepository;
import com.demo.spring.repository.DoctorSpecialityRepository;
import com.demo.spring.repository.SpecialityRepository;
import com.demo.spring.util.Message;

@Service
public class DoctorSpecialityService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	DoctorSpecialityRepository doctorSpecialityRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	SpecialityRepository specialityRepository;

	public Message addDoctorToSpeciality(int doctorId, int specialityId)
			throws DoctorSpecialityExists, DoctorNotFoundException, SpecialityNotExistsException {

		if (doctorRepository.existsById(doctorId)) {
			if (specialityRepository.existsById(specialityId)) {
				if (doctorSpecialityRepository.getAllByDocIdAndSpecId(doctorId, specialityId).isEmpty()) {
					DoctorSpeciality doctorSpeciality = new DoctorSpeciality(doctorId, specialityId);
					doctorSpecialityRepository.save(doctorSpeciality);
					logger.info("Doctor Speciality successfully saved");
					return new Message("Doctor Successfully added to Speciality");
				} else {
					logger.error("Exception : Doctor Speciality Exists Exception Thrown ");
					throw new DoctorSpecialityExists();
				}
			} else {
				logger.error("Exception : Speciality Not Exists Exception Thrown ");
				throw new SpecialityNotExistsException();
			}
		} else {
			logger.error("Exception : Doctor Not Found Exception Thrown ");
			throw new DoctorNotFoundException();
		}
	}

	public Message removeDoctorFromSpeciality(int doctorId, int specialityId) throws DoctorSpecialityNotExists, SpecialityNotExistsException, DoctorNotFoundException {
		if (doctorRepository.existsById(doctorId)) {
			if (specialityRepository.existsById(specialityId)) {
				if (doctorSpecialityRepository.getAllByDocIdAndSpecId(doctorId, specialityId).isEmpty()) {
					logger.error("Exception : Doctor Speciality Not Exists Exception Thrown ");
					throw new DoctorSpecialityNotExists();
				} else {
					doctorSpecialityRepository.deleteDoctorSpeciality(doctorId, specialityId);
					logger.info("Doctor specialty deleted successfully");
					return new Message("Doctor Speciality Deleted");				}
			} else {
				logger.error("Exception : Speciality Not Exists Exception Thrown ");
				throw new SpecialityNotExistsException();
			}
		} else {
			logger.error("Exception : Doctor Not Found Exception Thrown ");
			throw new DoctorNotFoundException();
		}	
	}
}
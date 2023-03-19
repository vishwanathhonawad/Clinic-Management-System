package com.demo.spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SpecialityTest {

	@Test
	void testSpeciality() {
		Speciality speciality=new Speciality();
		speciality.setSpecialityId(1);
		speciality.setSpecialityName("Nurology");
		
		assertEquals(1, speciality.getSpecialityId());
		assertEquals("Nurology", speciality.getSpecialityName());
}

}

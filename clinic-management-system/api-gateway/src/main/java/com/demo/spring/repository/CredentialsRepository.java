package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, String>{

	@Query("update Credentials c set c.password=:password where c.userName=:userName ")
	@Modifying
	@Transactional
	public int updateUser(String userName,String password);
	
	@Query("select c from Credentials c where c.userName=:userName and c.password=:password")
	public List<Credentials>  findUser(String userName,String password);
}

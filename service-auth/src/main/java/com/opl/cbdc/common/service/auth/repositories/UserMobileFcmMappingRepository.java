package com.opl.cbdc.common.service.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opl.cbdc.common.service.auth.domain.UserMobileFcmMapping;

public interface UserMobileFcmMappingRepository extends JpaRepository<UserMobileFcmMapping,Long> {

	@Modifying
	@Query("update UserMobileFcmMapping set isActive = false, modifiedDate = now() where token =:token and isActive = true")
	public int inActiveByToken(@Param("token") String token);
	
}

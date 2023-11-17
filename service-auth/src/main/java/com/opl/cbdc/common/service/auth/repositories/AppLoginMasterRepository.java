package com.opl.cbdc.common.service.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opl.cbdc.common.service.auth.domain.AppLoginMaster;

public interface AppLoginMasterRepository  extends JpaRepository<AppLoginMaster, Long> {

	@Query("select mb from AppLoginMaster mb where mb.token =:token and mb.isActive = true")
	public AppLoginMaster getByToken(@Param("token") String token);
	
	@Modifying
	@Query("update AppLoginMaster mb set mb.isActive = false where mb.token =:token and mb.isActive = true")
	public int logout(@Param("token") String token);
}

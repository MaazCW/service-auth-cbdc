package com.opl.cbdc.common.service.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opl.cbdc.common.service.auth.domain.AppRequestLogMaster;

public interface AppRequestLogMasterRepository extends JpaRepository<AppRequestLogMaster,Long> {

	@Query("select mb from AppRequestLogMaster mb where mb.appLoginId =:appLoginId and mb.requestNo =:reqNo and mb.isActive = true")
	public AppRequestLogMaster checkRequestNo(@Param("appLoginId") Long appLoginId,@Param("reqNo") String reqNo);
	
	@Modifying
	@Query("update AppRequestLogMaster mb set mb.isActive = false where mb.appLoginId =:appLoginId AND mb.isActive = true")
	public int activeFalse(@Param("appLoginId") Long appLoginId);
}

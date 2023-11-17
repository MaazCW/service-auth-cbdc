package com.opl.cbdc.common.service.auth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.opl.cbdc.common.service.auth.domain.UserTokenMapping;

public interface UserTokenMappingRepository extends JpaRepository<UserTokenMapping,Long> {

	@Query(value = "select * from user_token_mapping where UPPER(CAST((AES_DECRYPT(UNHEX(user_name),'C@p!ta@W0rld#AES')) AS CHAR)) = UPPER(:username) and access_token =:accessToken and login_token =:loginToken and active = true", nativeQuery = true)
	//@Query("select o from UserTokenMapping o where o.userName =:username and o.accessToken =:accessToken and o.loginToken =:loginToken and o.active = true")
	public List<UserTokenMapping> checkAccessToken(@Param("username") String username,@Param("accessToken")  String accessToken,@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.userName =:username and o.active = true")
	public List<UserTokenMapping> findByActiveUserName(@Param("username") String username);
	
	@Modifying
	@Query("update UserTokenMapping set accessToken =:accessToken,expiresIn =:expireIn,refreshToken =:refreshToken where userName =:username and loginToken =:loginToken and active = true")
	public void updateAccessToken(@Param("username") String username,@Param("refreshToken") String refreshToken,@Param("accessToken") String accessToken,@Param("expireIn")  String expireIn,@Param("loginToken") Integer loginToken);
	
	@Modifying
	@Query("update UserTokenMapping set active = false where userName =:username and active = true")
	public void inActiveAllSessionByUserName(@Param("username") String username);
	
	@Modifying
	@Query("update UserTokenMapping set active = false where userId =:userId and active = true")
	public void inactiveLastUserLogin(@Param("userId") Long userId);
	
	@Transactional
	@Modifying
	@Query("update UserTokenMapping um set um.active = false where um.userName =:username and um.refreshToken =:refreshToken and um.loginToken =:loginToken and um.active = true")
	public int logoutUser(@Param("username") String username,@Param("refreshToken") String refreshToken,@Param("loginToken") Integer loginToken);
	
	public UserTokenMapping findByUserNameAndRefreshTokenAndLoginTokenAndActive(String username, String refreshToken,Integer loginToken, boolean active);
	
	@Query("select count(*) from UserTokenMapping o where o.userName =:username and o.refreshToken =:refreshToken and o.active = true")
	public Long isUserAlreadyActive(@Param("username") String username,@Param("refreshToken") String refreshToken);
	
	@Query("select count(*) from UserTokenMapping o where o.loginToken =:loginToken and o.active = true")
	public Long checkLoginToken(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.loginToken =:loginToken and o.domainIsactive = true")
	public UserTokenMapping getTokenByLoginToken(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.loginToken =:loginToken and o.active = true")
	public UserTokenMapping getTokenByLoginTokenForSidbi(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.loginToken =:loginToken and o.active = true")
	public UserTokenMapping getByLoginToken(@Param("loginToken") Integer loginToken);
	
	@Modifying
	@Query("update UserTokenMapping set domainIsactive = false where loginToken =:loginToken")
	public void inactiveDomainLogin(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.id IN (select max(usr.id) from UserTokenMapping usr where usr.userId =:userId)")
	public UserTokenMapping getLastLoginDetailsFromUserId(@Param("userId") Long userId);
	
	public UserTokenMapping findFirstByUserIdAndActiveOrderByIdDesc(Long userId,Boolean isActive);
	
	@Query("select count(*) from UserTokenMapping o where o.userId =:userId and o.active = true")
	public Long checkUserLogginedOrNot(@Param("userId") Long userId);

	@Modifying
	@Query("UPDATE UserTokenMapping SET clientUserId = :clientUserId WHERE userName = :username AND accessToken = :accessToken AND loginToken = :loginToken AND active = 1 ")
	public Integer updateCustomerId(@Param("clientUserId") Long clientUserId, @Param("username") String username, @Param("accessToken") String accessToken, @Param("loginToken") Integer loginToken);
	
	
	@Modifying
	@Query("update UserTokenMapping set active = false where userName =:username and userType=:userType and active = true")
	public void inActiveAllSessionByUserName(@Param("username") String username,@Param("userType") Long userType);
	
}


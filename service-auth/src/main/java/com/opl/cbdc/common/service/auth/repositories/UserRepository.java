package com.opl.cbdc.common.service.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opl.cbdc.common.service.auth.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM `users`.`users` WHERE UPPER(CAST((AES_DECRYPT(UNHEX(`email`),'C@p!ta@W0rld#AES')) AS CHAR)) = UPPER(:email) AND `user_type_id` = :userTypeId",nativeQuery = true)
    User getByCaseSensitiveEmailAndUserTypeMasterId(@Param("email") String email, @Param("userTypeId")  Long userTypeId);

	public User findOneByEmailAndUserTypeMasterId(String email, Long userTypeId);
	
	@Query("select ur from User ur where ur.email =:email and ur.password =:password")
	public User getUserByEmailAndPassword(@Param("email") String email,@Param("password") String password);
	
	public User findOneByMobileAndUserTypeMasterId(String mobileNumber, Long userTypeId);
	
	@Query("select ur from User ur where ur.mobile =:mobile")
	public User getUserByMobile(@Param("mobile") String mobile);
	
	@Query(value = "SELECT `user_role_id`,`last_business_type_id` FROM users WHERE user_id =:userId", nativeQuery = true)
	public Object[] getRoleAndBusinessTypeId(@Param("userId") Long userId);
}

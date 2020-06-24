package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.domain.CreditDomain;
import com.sms.domain.UserDomain;

public interface CreditRepository extends JpaRepository<CreditDomain, Long> {

	public UserDomain findByUserId(String username);

}

package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.domain.ContactGroupDomain;

public interface ContactGroupRepository extends JpaRepository<ContactGroupDomain, Long> {

	public ContactGroupDomain findByUserIdAndGroupName(Long userID,String groupName);

	public ContactGroupDomain findByGroupName(String groupName);

	public ContactGroupDomain findByUserId(Long userID);
}

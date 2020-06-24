package com.sms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sms.domain.ContactDomain;

public interface ContactRepository extends PagingAndSortingRepository<ContactDomain, Long> {

	@Query("select c from contact c inner join contact_group_mapping m on"
			+ " c.id=m.contactid and m.groupid = :groupid")
	List<ContactDomain> findByGroupId(@Param("groupid") Long groupId, Pageable pageable);

}

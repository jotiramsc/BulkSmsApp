package com.sms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sms.domain.MessageDomain;

public interface MessageRepository extends PagingAndSortingRepository<MessageDomain, Long> {

	List<MessageDomain> findByUserId(Long userID, Pageable pageable);

}

package com.sms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sms.domain.MessageRequestDomain;

public interface MessageRequestRepository extends PagingAndSortingRepository<MessageRequestDomain, Long> {
	List<MessageRequestDomain> findByMessageId(Long messageId, Pageable pageable);
}

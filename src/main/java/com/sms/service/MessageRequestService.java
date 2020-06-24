package com.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.sms.domain.MessageDomain;
import com.sms.domain.MessageRequestDomain;
import com.sms.model.MessageRequestModel;
import com.sms.repository.ContactRepository;
import com.sms.repository.MessageRepository;
import com.sms.repository.MessageRequestRepository;
import com.sms.util.SmsException;

@Service
public class MessageRequestService implements Mappable<MessageRequestDomain, MessageRequestModel> {

	@Autowired
	private MessageRequestRepository messageRequestRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	MessageRepository messageRepository;

	@Override
	public MessageRequestModel convertToModel(MessageRequestDomain domainObject) {
		return modelMapper.map(domainObject, MessageRequestModel.class);
	}

	@Override
	public MessageRequestDomain convertToDomain(MessageRequestModel modelObject) {
		if (modelObject.getId() != null) {
			Optional<MessageRequestDomain> domainopt = messageRequestRepository.findById(modelObject.getId());
			if (domainopt.isPresent()) {
				MessageRequestDomain destination = domainopt.get();

				modelMapper.map(modelObject, destination);
				return destination;
			}
		}

		return modelMapper.map(modelObject, MessageRequestDomain.class);
	}

	@Override
	public List<MessageRequestModel> convertToModelList(List<MessageRequestDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<MessageRequestDomain> convertToDomainList(List<MessageRequestModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public MessageRequestModel getMessageRequestById(Long id) {
		Optional<MessageRequestDomain> contactGroupDomain = messageRequestRepository.findById(id);
		return convertToModel(contactGroupDomain.isPresent() ? contactGroupDomain.get() : null);
	}

	public MessageRequestModel createOrUpdateMessageRequest(MessageRequestModel contactGroup) throws SmsException {

		if (contactGroup.getMessageId() == null)
			throw new SmsException("Please specify message in request");

		Optional<MessageDomain> optMessage = messageRepository.findById(contactGroup.getMessageId());
		if (!optMessage.isPresent())
			throw new SmsException("Invalid message in request");

		MessageDomain messageDomain = optMessage.get();

		MessageRequestDomain domain = convertToDomain(contactGroup);

		messageDomain.getRequests().add(domain);
		domain = messageRequestRepository.save(domain);
		messageRepository.save(messageDomain);
		return convertToModel(domain);
	}

	public void deleteMessageRequestById(Long id) {
		messageRequestRepository.deleteById(id);

	}

	public List<MessageRequestModel> findByMessageId(Long messsageId, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("id")));
		return convertToModelList(messageRequestRepository.findByMessageId(messsageId, pageable));
	}

}

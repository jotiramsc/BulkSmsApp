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
import com.sms.model.MessageModel;
import com.sms.repository.MessageRepository;

@Service
public class MessageService implements Mappable<MessageDomain, MessageModel> {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MessageModel convertToModel(MessageDomain domainObject) {
		return modelMapper.map(domainObject, MessageModel.class);
	}

	@Override
	public MessageDomain convertToDomain(MessageModel modelObject) {
		if (modelObject.getId() != null) {
			Optional<MessageDomain> optDomain = messageRepository.findById(modelObject.getId());
			if (optDomain.isPresent()) {
				MessageDomain destination = optDomain.get();
				modelMapper.map(modelObject, destination);
				return destination;
			}
		}
		return modelMapper.map(modelObject, MessageDomain.class);
	}

	@Override
	public List<MessageModel> convertToModelList(List<MessageDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<MessageDomain> convertToDomainList(List<MessageModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public MessageModel getMessageById(Long id) {
		Optional<MessageDomain> contactGroupDomain = messageRepository.findById(id);
		return convertToModel(contactGroupDomain.isPresent() ? contactGroupDomain.get() : null);
	}

	public MessageModel createOrUpdateMessage(MessageModel contactGroup) {
		return convertToModel(messageRepository.save(convertToDomain(contactGroup)));
	}

	public void deleteMessageById(Long id) {
		messageRepository.deleteById(id);

	}

	public List<MessageModel> getMessagesByUserId(Long userID, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("id")));
		return convertToModelList(messageRepository.findByUserId(userID, pageable));
	}

}

package com.sms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.sms.domain.ContactDomain;
import com.sms.domain.ContactGroupDomain;
import com.sms.model.ContactModel;
import com.sms.model.UserModel;
import com.sms.repository.ContactGroupRepository;
import com.sms.repository.ContactRepository;
import com.sms.util.CommonLiterals;
import com.sms.util.SmsException;

@Service
public class ContactService implements Mappable<ContactDomain, ContactModel> {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactGroupRepository contactGroupRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ContactModel convertToModel(ContactDomain domainObject) {
		return modelMapper.map(domainObject, ContactModel.class);
	}

	@Override
	public ContactDomain convertToDomain(ContactModel modelObject) {
		if (modelObject.getId() != null)

		{
			Optional<ContactDomain> optContactDomain = contactRepository.findById(modelObject.getId());
			if (optContactDomain.isPresent()) {
				ContactDomain destination = optContactDomain.get();
				modelMapper.map(modelObject, destination);
				return destination;
			}
		}
		return modelMapper.map(modelObject, ContactDomain.class);
	}

	@Override
	public List<ContactModel> convertToModelList(List<ContactDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<ContactDomain> convertToDomainList(List<ContactModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public ContactModel getContactById(Long id) {
		Optional<ContactDomain> contactDomain = contactRepository.findById(id);
		return convertToModel(contactDomain.isPresent() ? contactDomain.get() : null);
	}

	public ContactModel createOrUpdateContact(ContactModel contact) {
		return convertToModel(contactRepository.save(convertToDomain(contact)));
	}

	public void deleteContactById(Long id) {
		contactRepository.deleteById(id);

	}

	public List<ContactModel> getContactsByGroupId(Long groupId, int page, int size) {

		Optional<ContactGroupDomain> group = contactGroupRepository.findById(groupId);

		List<ContactDomain> contactDomains = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("id")));
		if (group.isPresent())
			contactDomains = contactRepository.findByGroupId(groupId, pageable);

		return convertToModelList(contactDomains);
	}

	public boolean saveUploadedContacts(List<ContactModel> modelList, UserModel userModel) throws SmsException {
		ContactGroupDomain groupDomain = contactGroupRepository.findByUserIdAndGroupName(userModel.getId(),
				CommonLiterals.DEFAULT_GROUP_NAME);

		if (groupDomain == null)
			new SmsException("No group found for user");

		List<ContactDomain> list1 = convertToDomainList(modelList);

		list1.stream().forEach(c -> {
			c.getGroups().add(groupDomain);
			c.setCreatedTs(LocalDateTime.now());
			c.setModifiedTs(LocalDateTime.now());
		});

		contactRepository.saveAll(list1);

		return false;

	}

}

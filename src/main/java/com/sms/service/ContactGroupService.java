package com.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.ContactGroupDomain;
import com.sms.model.ContactGroupModel;
import com.sms.repository.ContactGroupRepository;

@Service
public class ContactGroupService implements Mappable<ContactGroupDomain, ContactGroupModel> {

	@Autowired
	private ContactGroupRepository contactGroupRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ContactGroupModel convertToModel(ContactGroupDomain domainObject) {
		return modelMapper.map(domainObject, ContactGroupModel.class);
	}

	@Override
	public ContactGroupDomain convertToDomain(ContactGroupModel modelObject) {
		if (modelObject.getId() != null) {
			ContactGroupDomain destination = contactGroupRepository.getOne(modelObject.getId());

			modelMapper.map(modelObject, destination);
			return destination;
		}
		return modelMapper.map(modelObject, ContactGroupDomain.class);
	}

	@Override
	public List<ContactGroupModel> convertToModelList(List<ContactGroupDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<ContactGroupDomain> convertToDomainList(List<ContactGroupModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public ContactGroupModel getContactGroupById(Long id) {
		Optional<ContactGroupDomain> contactGroupDomain = contactGroupRepository.findById(id);
		return convertToModel(contactGroupDomain.isPresent() ? contactGroupDomain.get() : null);
	}

	public List<ContactGroupModel> getAllContactGroups() {
		return convertToModelList(contactGroupRepository.findAll());
	}

	public ContactGroupModel createOrUpdateContactGroup(ContactGroupModel contactGroup) {
		return convertToModel(contactGroupRepository.save(convertToDomain(contactGroup)));
	}

	public void deleteContactGroupById(Long id) {
		contactGroupRepository.deleteById(id);

	}

	public ContactGroupModel findByUserId(Long userID) {
		return convertToModel(contactGroupRepository.findByUserId(userID));
	}

}

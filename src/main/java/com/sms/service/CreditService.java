package com.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.CreditDomain;
import com.sms.model.CreditModel;
import com.sms.repository.CreditRepository;

@Service
public class CreditService implements Mappable<CreditDomain, CreditModel> {

	@Autowired
	private CreditRepository creditRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CreditModel convertToModel(CreditDomain domainObject) {
		return modelMapper.map(domainObject, CreditModel.class);
	}

	@Override
	public CreditDomain convertToDomain(CreditModel modelObject) {
		if(modelObject.getId()!=null)
		{
			CreditDomain destination = creditRepository.getOne(modelObject.getId());
			modelMapper.map(modelObject, destination);
			return destination;
		}
		return modelMapper.map(modelObject, CreditDomain.class);
	}

	@Override
	public List<CreditModel> convertToModelList(List<CreditDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<CreditDomain> convertToDomainList(List<CreditModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public CreditModel getCreditById(Long id) {
		Optional<CreditDomain> creditDomain = creditRepository.findById(id);
		return convertToModel(creditDomain.isPresent() ? creditDomain.get() : null);
	}

	public List<CreditModel> getAllCredits() {
		return convertToModelList(creditRepository.findAll());
	}

	public CreditModel createOrUpdateCredit(CreditModel credit) {
		return convertToModel(creditRepository.save(convertToDomain(credit)));
	}

	public void deleteCreditById(Long id) {
		creditRepository.deleteById(id);

	}

}

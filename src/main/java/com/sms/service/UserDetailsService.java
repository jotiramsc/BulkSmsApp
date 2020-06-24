package com.sms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.domain.ContactGroupDomain;
import com.sms.domain.CreditDomain;
import com.sms.domain.UserDomain;
import com.sms.model.UserModel;
import com.sms.repository.ContactGroupRepository;
import com.sms.repository.CreditRepository;
import com.sms.repository.UserRepository;
import com.sms.util.CommonLiterals;
import com.sms.util.EmailSender;
import com.sms.util.SmsException;

@Service
public class UserDetailsService
		implements org.springframework.security.core.userdetails.UserDetailsService, Mappable<UserDomain, UserModel> {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private CreditRepository creditRepository;
	
	@Autowired
	private ContactGroupRepository contactGroupRepository;

	@Override
	public UserModel loadUserByUsername(String username) {

		UserDomain domain = userRepository.findByUsername(username);
		return modelMapper.map(domain, UserModel.class);

	}

	public UserModel saveUser(UserModel userModel) throws SmsException {

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userModel.getUsername(),
				userModel.getEmail());
		if (null != userDetails) {
			throw new SmsException("USER_ALREADY_EXIST");
		} else {
			UserDomain detail = modelMapper.map(userModel, UserDomain.class);
			detail.setPassword(bcryptEncoder.encode(detail.getPassword()));
			detail.setEmailVerifyToken(UUID.randomUUID().toString());
			detail.setEmailVerified(false);
			detail.setCreatedTs(LocalDateTime.now());
			detail.setModifiedTs(LocalDateTime.now());

			UserDomain userDomain = userRepository.save(detail);
			setUpAccount(detail);
			emailSender.sendEmailVerificationMail(detail.getEmailVerifyToken());
			return modelMapper.map(userDomain, UserModel.class);

		}

	}

	private void setUpAccount(UserDomain userDomain) {

		// Add default credit
		CreditDomain creditDomain = new CreditDomain();
		creditDomain.setFreeCredit(CommonLiterals.DEFAULT_FREE_CREDIT);
		creditDomain.setAvailableCredit(CommonLiterals.DEFAULT_FREE_CREDIT);
		creditDomain.setCreatedTs(LocalDateTime.now());
		creditDomain.setModifiedTs(LocalDateTime.now());
		creditDomain.setUsedCredit(0);
		creditDomain.setUserId(userDomain.getId());
		creditRepository.save(creditDomain);		

		// Create default group for All Contacts
		ContactGroupDomain groupDomain = new ContactGroupDomain();
		groupDomain.setGroupName(CommonLiterals.DEFAULT_GROUP_NAME);
		groupDomain.setStatus(CommonLiterals.ACTIVE_STATUS);
		groupDomain.setCreatedTs(LocalDateTime.now());
		groupDomain.setUserId(userDomain.getId());
		groupDomain.setModifiedTs(LocalDateTime.now());		
		contactGroupRepository.save(groupDomain);

	}

	public String resetPasswordLink(UserModel userModel) throws SmsException {

		String response = null;

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userModel.getUsername(),
				userModel.getEmail());
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			userDetails.setResetToken(UUID.randomUUID().toString());
			userDetails.setResetTokenExpiry(LocalDateTime.now().plusDays(5));
			userDetails.setModifiedTs(LocalDateTime.now());
			userRepository.save(userDetails);
			emailSender.sendPasswordResetMail(userDetails.getResetToken());
			response = "Reset password link has been sent on " + userModel.getEmail();
		}

		return response;

	}

	public String resetPassword(UserModel userModel) throws SmsException {

		String response = null;

		final UserDomain userDetails = userRepository.findByResetToken(userModel.getResetToken());
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			if (validateResetToken(userDetails, userModel)) {

				userDetails.setResetToken(null);
				userDetails.setResetTokenExpiry(null);
				userDetails.setPassword(bcryptEncoder.encode(userModel.getPassword()));
				userDetails.setModifiedTs(LocalDateTime.now());
				userRepository.save(userDetails);

				response = "Password changed";

			} else {

			}

		}

		return response;

	}

	public String verifyEmail(String token) throws SmsException {

		String response = null;

		final UserDomain userDetails = userRepository.findByEmailVerifyToken(token);
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			if (userDetails.getEmailVerifyToken().equalsIgnoreCase(token)) {

				userDetails.setEmailVerified(true);
				userDetails.setEmailVerifyToken(null);
				userDetails.setModifiedTs(LocalDateTime.now());
				userRepository.save(userDetails);

				response = "Email Verification Completed";

			} else {

			}

		}

		return response;

	}

	private boolean validateResetToken(UserDomain userDetails, UserModel userModel) {
		if (userDetails.getResetToken().equalsIgnoreCase(userModel.getResetToken())
				&& (!userDetails.getResetTokenExpiry().isBefore(LocalDateTime.now()))) {
			return true;
		}
		return false;

	}

	@Override
	public UserModel convertToModel(UserDomain domainObject) {
		return modelMapper.map(domainObject, UserModel.class);
	}

	@Override
	public UserDomain convertToDomain(UserModel modelObject) {
		if (modelObject.getId() != null) {
			UserDomain destination = userRepository.getOne(modelObject.getId());
			modelMapper.map(modelObject, destination);
			return destination;
		}
		return modelMapper.map(modelObject, UserDomain.class);
	}

	@Override
	public List<UserModel> convertToModelList(List<UserDomain> domainlist) {
		return domainlist.parallelStream().map(this::convertToModel).collect(Collectors.toList());
	}

	@Override
	public List<UserDomain> convertToDomainList(List<UserModel> modelList) {
		return modelList.parallelStream().map(this::convertToDomain).collect(Collectors.toList());
	}

	public UserModel getCurrentUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return this.loadUserByUsername(username);
	}
}

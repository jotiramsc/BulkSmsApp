package com.sms.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.domain.UserDomain;
import com.sms.model.UserModel;
import com.sms.repository.UserRepository;
import com.sms.util.EmailSender;
import com.sms.util.SmsException;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailSender emailSender;

	@Override
	public UserModel loadUserByUsername(String username) {

		return modelMapper.map(userRepository.findByUsername(username), UserModel.class);

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
			
			emailSender.sendEmailVerificationMail(detail.getEmailVerifyToken());

			return modelMapper.map(userRepository.save(detail), UserModel.class);

		}

	}

	public String resetPasswordLink(UserModel userModel) throws SmsException {

		String response = null;

		final UserDomain userDetails = userRepository.findByUsernameOrEmail(userModel.getUsername(),
				userModel.getEmail());
		if (null == userDetails) {
			response = "We could not find an account for that e-mail address";
		} else {

			userDetails.setResetToken(UUID.randomUUID().toString());
			userDetails.setResetTokenExpiry(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
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
				userRepository.save(userDetails);

				response = "Email Verification Completed";

			} else {

			}

		}

		return response;

	}

	private boolean validateResetToken(UserDomain userDetails, UserModel userModel) {
		if (userDetails.getResetToken().equalsIgnoreCase(userModel.getResetToken())
				&& (!userDetails.getResetTokenExpiry().before(Timestamp.valueOf(LocalDateTime.now())))) {
			return true;
		}
		return false;

	}

}

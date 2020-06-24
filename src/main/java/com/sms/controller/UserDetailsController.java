package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.auth.JwtTokenUtil;
import com.sms.model.JwtRequest;
import com.sms.model.JwtResponse;
import com.sms.model.UserModel;
import com.sms.service.UserDetailsService;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserDetailsController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping(value = "/signin")
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws SmsException {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws SmsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new SmsException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new SmsException("INVALID_CREDENTIALS");
		}
	}

	@PostMapping(value = "/signup")
	public UserModel registerUser(@RequestBody UserModel userModel) throws SmsException {

		UserModel model = userDetailsService.saveUser(userModel);
		return model;
	}

	@PostMapping(value = "/resetPasswordLink")
	public String resetPasswordLink(@RequestBody UserModel userModel) throws SmsException {

		return userDetailsService.resetPasswordLink(userModel);
	}

	@PostMapping(value = "/resetPassword")
	public String resetPassword(@RequestBody UserModel userModel) throws SmsException {

		return userDetailsService.resetPassword(userModel);
	}

	@GetMapping(value = "/verifyEmail/{token}")
	public String verifyEmail(@PathVariable("token") String token) throws SmsException {

		return userDetailsService.verifyEmail(token);
	}

	@GetMapping(value = "/userDetails")
	public UserModel getUserDetails() throws SmsException {

		return userDetailsService.getCurrentUserDetails();
	}

}
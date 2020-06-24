package com.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.model.MessageRequestModel;
import com.sms.service.MessageRequestService;
import com.sms.service.UserDetailsService;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/requests")
@CrossOrigin
public class MessageRequestController {
	@Autowired
	MessageRequestService messageRequestService;

	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("bymessage/{messageid}/{page}/{size}")
	public ResponseEntity<List<MessageRequestModel>> getMessageRequestsByUserId(
			@PathVariable("messageid") Long messageId, @PathVariable("page") Integer page,
			@PathVariable("size") Integer size) {
		List<MessageRequestModel> list = messageRequestService.findByMessageId(messageId, page, size);
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MessageRequestModel> getMessageRequestById(@PathVariable("id") Long id) throws SmsException {
		MessageRequestModel entity = messageRequestService.getMessageRequestById(id);
		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<MessageRequestModel> createOrUpdateMessageRequest(
			@RequestBody MessageRequestModel messageRequest) throws SmsException {
		MessageRequestModel updated = null;

		updated = messageRequestService.createOrUpdateMessageRequest(messageRequest);

		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<MessageRequestModel> UpdateMessageRequest(@RequestBody MessageRequestModel messageRequest)
			throws SmsException {
		MessageRequestModel updated = messageRequestService.createOrUpdateMessageRequest(messageRequest);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteMessageRequestById(@PathVariable("id") Long id) throws SmsException {
		messageRequestService.deleteMessageRequestById(id);
		return HttpStatus.OK;
	}

}
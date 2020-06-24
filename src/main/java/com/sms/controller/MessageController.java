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

import com.sms.model.MessageModel;
import com.sms.service.MessageService;
import com.sms.service.UserDetailsService;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {
	@Autowired
	MessageService messageService;

	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("byuser/{userid}/{page}/{size}")
	public ResponseEntity<List<MessageModel>> getMessagesByUserId(@PathVariable("userid") Long userId,
			@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		List<MessageModel> list = messageService.getMessagesByUserId(userId, page, size);
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MessageModel> getMessageById(@PathVariable("id") Long id) throws SmsException {
		MessageModel entity = messageService.getMessageById(id);
		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<MessageModel> createMessage(@RequestBody MessageModel message) {
		MessageModel updated = messageService.createOrUpdateMessage(message);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}
	
		
	@PutMapping
	public ResponseEntity<MessageModel> updateMessage(@RequestBody MessageModel message) {
		MessageModel updated = messageService.createOrUpdateMessage(message);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteMessageById(@PathVariable("id") Long id) throws SmsException {
		messageService.deleteMessageById(id);
		return HttpStatus.OK;
	}

}
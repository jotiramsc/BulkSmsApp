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

import com.sms.model.ContactGroupModel;
import com.sms.service.ContactGroupService;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/groups")
@CrossOrigin
public class ContactsGroupController {
	@Autowired
	ContactGroupService contactGroupService;

	@GetMapping
	public ResponseEntity<List<ContactGroupModel>> getAllContactGroups() {
		List<ContactGroupModel> list = contactGroupService.getAllContactGroups();
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactGroupModel> getContactGroupById(@PathVariable("id") Long id) throws SmsException {
		ContactGroupModel entity = contactGroupService.getContactGroupById(id);
		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ContactGroupModel> createContactGroup(@RequestBody ContactGroupModel contactGroup) {
		ContactGroupModel updated = contactGroupService.createOrUpdateContactGroup(contactGroup);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ContactGroupModel> updateContactGroup(@RequestBody ContactGroupModel contactGroup) {
		ContactGroupModel updated = contactGroupService.createOrUpdateContactGroup(contactGroup);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteContactGroupById(@PathVariable("id") Long id) throws SmsException {
		contactGroupService.deleteContactGroupById(id);
		return HttpStatus.OK;
	}

}
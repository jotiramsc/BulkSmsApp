package com.sms.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sms.model.ContactModel;
import com.sms.model.UserModel;
import com.sms.service.ContactService;
import com.sms.service.UserDetailsService;
import com.sms.util.ContactUploader;
import com.sms.util.ExcelUploader;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/contacts")
@CrossOrigin
public class ContactController {
	@Autowired
	ContactService contactService;

	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("bygroup/{groupID}/{page}/{size}")
	public ResponseEntity<List<ContactModel>> getContactsByGroupId(@PathVariable("groupID") Long groupID,
			@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		List<ContactModel> list = contactService.getContactsByGroupId(groupID, page, size);
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactModel> getContactById(@PathVariable("id") Long id) throws SmsException {
		ContactModel entity = contactService.getContactById(id);
		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ContactModel> createContact(@RequestBody ContactModel contact) {
		ContactModel updated = contactService.createOrUpdateContact(contact);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ContactModel> updateContact(@RequestBody ContactModel contact) {
		ContactModel updated = contactService.createOrUpdateContact(contact);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteContactById(@PathVariable("id") Long id) throws SmsException {
		contactService.deleteContactById(id);
		return HttpStatus.OK;
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadContacts(@RequestParam("file") MultipartFile file) throws IOException {

		UserModel userModel = userDetailsService.getCurrentUserDetails();

		if (userModel == null)
			return new ResponseEntity<>("User Details Not Found", new HttpHeaders(), HttpStatus.UNAUTHORIZED);

		try {

			ExcelUploader<ContactModel> uploader = new ContactUploader();
			List<ContactModel> list = uploader.getFileContents(file);
			contactService.saveUploadedContacts(list, userModel);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Contacts Uploaded Successfully", new HttpHeaders(), HttpStatus.OK);

	}

}
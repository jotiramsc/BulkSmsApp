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

import com.sms.model.CreditModel;
import com.sms.service.CreditService;
import com.sms.util.SmsException;

@RestController
@RequestMapping("/credits")
@CrossOrigin
public class CreditController {
	@Autowired
	CreditService creditService;

	@GetMapping
	public ResponseEntity<List<CreditModel>> getAllCredits() {
		List<CreditModel> list = creditService.getAllCredits();
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CreditModel> getCreditById(@PathVariable("id") Long id) throws SmsException {
		CreditModel entity = creditService.getCreditById(id);
		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CreditModel> createCredit(@RequestBody CreditModel credit) {
		CreditModel updated = creditService.createOrUpdateCredit(credit);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<CreditModel> updateCredit(@RequestBody CreditModel credit) {
		CreditModel updated = creditService.createOrUpdateCredit(credit);
		return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteCreditById(@PathVariable("id") Long id) throws SmsException {
		creditService.deleteCreditById(id);
		return HttpStatus.OK;
	}

}
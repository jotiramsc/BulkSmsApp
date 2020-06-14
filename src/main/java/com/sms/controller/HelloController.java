package com.sms.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
@CrossOrigin
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
	 
		return "hello world";
	}
}

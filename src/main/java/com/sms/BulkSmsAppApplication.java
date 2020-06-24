package com.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sms.repository.ContactGroupRepository;
import com.sms.service.ContactService;

@SpringBootApplication
public class BulkSmsAppApplication {

	@Autowired
	ContactGroupRepository ContactGroupRepository;

	public static void main(String[] args) {
		SpringApplication.run(BulkSmsAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(ContactService service) {
		return args -> {

			// System.out.println(service.getContactsByGroupId(100L, 0, 100));

		};
	}

}

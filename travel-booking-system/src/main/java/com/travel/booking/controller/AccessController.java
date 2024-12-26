package com.travel.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.UserDto;
import com.travel.booking.service.AccessService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AccessController {

	AccessService service;

	@PostMapping("/getAccessToken")
	public ResponseEntity<ServiceResponse> getAccessToken(@RequestBody UserDto user) {
		return new ResponseEntity<>(service.getAccessToken(user), HttpStatus.OK);
	}
}

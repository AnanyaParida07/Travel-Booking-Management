package com.travel.booking.controller;

import java.util.List;

import org.json.simple.JSONObject;
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

import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.TravelDto;
import com.travel.booking.entity.PaymentEntity;
import com.travel.booking.service.TravelService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TravelController {
	private TravelService service;

	@GetMapping("/get/{bookingId}/{userId}/{username}")
	public ResponseEntity<TravelDto> getById(@PathVariable int bookingId, @PathVariable int userId,
			@PathVariable String username) {
		return new ResponseEntity<>(service.fetchById(bookingId, userId, username), HttpStatus.OK);
	}
  
	@GetMapping("/getPayment")  
	public ResponseEntity<List<PaymentEntity>> getPayment() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> add(@Valid @RequestBody TravelDto entity) {
		return new ResponseEntity<>(service.addTravel(entity), HttpStatus.OK);
	}

	@DeleteMapping("/remove/{bookingId}/{userId}/{username}")
	public ResponseEntity<ServiceResponse> delete(@PathVariable int bookingId, @PathVariable int userId,
			@PathVariable String username) {
		return new ResponseEntity<>(service.removeTravel(bookingId, userId, username), HttpStatus.OK);
	}

	@PutMapping("update/{bookingId}/{userId}/{username}")
	public ResponseEntity<ServiceResponse> update(@PathVariable int bookingId, @PathVariable int userId,
			@PathVariable String username, @Valid @RequestBody TravelDto entity) {
		return new ResponseEntity<>(service.updateTravel(bookingId, userId, username, entity), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<JSONObject> search(@RequestParam("searchParam") String searchDataJson,
			@RequestParam("iDisplayStart") Integer iDisplayStart,
			@RequestParam("iDisplayLength") Integer iDisplayLength) {
		return new ResponseEntity<>(service.searchBySpec(searchDataJson, iDisplayStart, iDisplayLength), HttpStatus.OK);
	}

}

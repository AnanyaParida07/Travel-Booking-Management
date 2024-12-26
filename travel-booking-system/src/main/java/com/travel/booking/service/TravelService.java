package com.travel.booking.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.TravelDto;
import com.travel.booking.entity.PaymentEntity;

public interface TravelService {

	TravelDto fetchById(int bookingId, int userId, String username);

	List<PaymentEntity> findAll();

	ServiceResponse addTravel(TravelDto entity);

	ServiceResponse removeTravel(int bookingId, int userId, String username);

	ServiceResponse updateTravel(int bookingId, int userId, String username, TravelDto entity);

	JSONObject searchBySpec(String searchDataJson, Integer iDisplayStart, Integer iDisplayLength);

}

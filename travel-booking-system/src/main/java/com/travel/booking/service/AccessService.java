package com.travel.booking.service;

import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.UserDto;

public interface AccessService {

	ServiceResponse getAccessToken(UserDto user);
}

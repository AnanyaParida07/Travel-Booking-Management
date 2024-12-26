package com.travel.booking.service;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.UserDto;
import com.travel.booking.entity.UserEntity;
import com.travel.booking.exception.UserNotFoundException;
import com.travel.booking.repository.UserRepository;
import com.travel.booking.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessServiceImpl implements AccessService{
	
	private final RestTemplate restTemplate;
	private final UserRepository userRepo;
	private final MessageSource messageSource;


	@Value("${oauth.token-url}")
	private String tokenUrl;
	@Value("${oauth.client-id}")
	private String clientId;
	@Value("${oauth.client-secret}")
	private String clientSecret;
	@Value("${oauth.grant_type}")
	private String grantType;
	
	@Override
	public ServiceResponse getAccessToken(UserDto user) {
		Optional<UserEntity> users = userRepo.findById(user.getUserName());
		if (!users.isPresent()) {
			throw new UserNotFoundException();
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData(user), headers);
			return new ServiceResponse(Constants.SUCCESS, message("interland.details.MSG009"), List
					.of(restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, JSONObject.class).getBody()));
		} catch (Exception e) {
			log.error("Error generating access token");
			throw e; 
		}
	}

	private MultiValueMap<String, String> formData(UserDto user) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", grantType); 
		formData.add("client_id", clientId);
		formData.add("username", user.getUserName());
		formData.add("password", user.getPassword());
		formData.add("client_secret", clientSecret);
		return formData;
	}

	private String message(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

}

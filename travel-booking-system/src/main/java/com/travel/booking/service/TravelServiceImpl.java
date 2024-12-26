package com.travel.booking.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.travel.booking.dto.ServiceResponse;
import com.travel.booking.dto.TravelDto;
import com.travel.booking.entity.PaymentEntity;
import com.travel.booking.entity.TravelEntity;
import com.travel.booking.entity.TravelPK;
import com.travel.booking.exception.NotFoundException;
import com.travel.booking.repository.PaymentRepository;
import com.travel.booking.repository.TravelRepository;
import com.travel.booking.specification.TravelSpecification;
import com.travel.booking.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelServiceImpl implements TravelService {

	private final TravelRepository repo;
	private final PaymentRepository paymentRepo;
	private final MessageSource messageSource;

	@Override
	public TravelDto fetchById(int bookingId, int userId, String username) {
		TravelPK travelPK = TravelPK.builder().bookingId(bookingId).userId(userId).username(username).build();
		return repo.findById(travelPK)
				.map(travelEntity -> TravelDto.builder().bookingId(travelEntity.getPkId().getBookingId())
						.userId(travelEntity.getPkId().getUserId()).username(travelEntity.getPkId().getUsername())
						.bookingDate(travelEntity.getBookingDate()).destination(travelEntity.getDestination())
						.paymentStatus(travelEntity.getPaymentStatus()).totalAmount(travelEntity.getTotalAmount())
						.phoneNumber(travelEntity.getPhoneNumber()).emailId(travelEntity.getEmailId())
						.status(travelEntity.getStatus()).build())
				.orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND));

	}

	@Override
	public List<PaymentEntity> findAll() {
		try {
			return paymentRepo.findAll();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public ServiceResponse addTravel(TravelDto entity) {
		try {
			TravelPK id = TravelPK.builder().bookingId(entity.getBookingId()).userId(entity.getUserId())
					.username(entity.getUsername()).build();
			Optional<TravelEntity> demoOptional = repo.findById(id);
			if (demoOptional.isPresent()) {
				return new ServiceResponse(Constants.FAILED, message("interland.details.MSG001"), List.of());
			} else {
				TravelEntity newEntity = TravelEntity.builder().pkId(id).bookingDate(entity.getBookingDate())
						.destination(entity.getDestination()).paymentStatus(entity.getPaymentStatus())
						.totalAmount(entity.getTotalAmount()).phoneNumber(entity.getPhoneNumber())
						.emailId(entity.getEmailId()).status(Constants.ACTIVE).build();

				repo.save(newEntity);
				log.info("Data Added Successfully");
				return new ServiceResponse(Constants.SUCCESS, message("interland.details.MSG002"), List.of());

			}
		} catch (Exception e) {
			log.error("Error adding employee", e);
			return new ServiceResponse(Constants.FAILED, message("interland.details.MSG003"), List.of());
		}
	}

	@Override
	public ServiceResponse removeTravel(int bookingId, int userId, String username) {
		try {
			TravelPK id = TravelPK.builder().bookingId(bookingId).userId(userId).username(username).build();
			Optional<TravelEntity> demoOptional = repo.findById(id);
			if (demoOptional.isPresent()) {
				TravelEntity entity = demoOptional.get();
				entity.setStatus(Constants.DELETED);
				repo.save(entity);
			}
			return new ServiceResponse(Constants.SUCCESS, message("interland.details.MSG004"), List.of());

		} catch (Exception e) {
			log.error("Error updating status", e);
			return new ServiceResponse(Constants.FAILED, message("interland.details.MSG010"), List.of());
		}
	}

	@Override
	public ServiceResponse updateTravel(int bookingId, int userId, String username, TravelDto entity) {
		try {
			TravelPK id = TravelPK.builder().bookingId(bookingId).userId(userId).username(username).build();
			Optional<TravelEntity> demoOptional = repo.findById(id);
			if (demoOptional.isPresent()) {
				TravelEntity demoUpdate = TravelEntity.builder().pkId(id).bookingDate(entity.getBookingDate())
						.destination(entity.getDestination()).paymentStatus(entity.getPaymentStatus())
						.totalAmount(entity.getTotalAmount()).phoneNumber(entity.getPhoneNumber())
						.emailId(entity.getEmailId()).status(entity.getStatus()).build();
				repo.save(demoUpdate);
				log.info("Data Updated");
				return new ServiceResponse(Constants.SUCCESS, message("interland.details.MSG007"), List.of());
			} else {
				return new ServiceResponse(Constants.FAILED, message("interland.details.MSG005"), List.of());
			}
		} catch (Exception e) {
			log.error("Error updating details", e);
			return new ServiceResponse(Constants.FAILED, message("interland.details.MSG011"), List.of());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchBySpec(String searchDataJson, Integer iDisplayStart, Integer iDisplayLength) {
		JSONObject result = new JSONObject();
		try {
			Page<TravelEntity> demoPage = repo.findAll(TravelSpecification.search(searchDataJson),
					PageRequest.of(iDisplayStart, iDisplayLength));
			List<TravelDto> demoDtos = demoPage.getContent().stream()
					.map(travelEntity -> TravelDto.builder().bookingId(travelEntity.getPkId().getBookingId())
							.userId(travelEntity.getPkId().getUserId()).username(travelEntity.getPkId().getUsername())
							.bookingDate(travelEntity.getBookingDate()).destination(travelEntity.getDestination())
							.paymentStatus(travelEntity.getPaymentStatus()).totalAmount(travelEntity.getTotalAmount())
							.phoneNumber(travelEntity.getPhoneNumber()).emailId(travelEntity.getEmailId())
							.status(travelEntity.getStatus()).build())
					.toList();

			JSONArray dataArray = new JSONArray();
			dataArray.add(demoDtos);
			result.put(Constants.TOTAL_DISPLAY_RECORD, demoPage.getTotalElements());
			result.put(Constants.AA_DATA, dataArray);
			result.put(Constants.TOTAL_RECORD, demoPage.getTotalElements());
		} catch (Exception e) {
			log.error("Error ", e);
			result.put(Constants.AA_DATA, new JSONArray());
			result.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			result.put(Constants.TOTAL_RECORD, 0);
		}
		return result;

	}
	


	private String message(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

}

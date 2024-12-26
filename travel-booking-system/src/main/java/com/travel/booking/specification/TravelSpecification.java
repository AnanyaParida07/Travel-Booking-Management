package com.travel.booking.specification;

import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.booking.entity.TravelEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TravelSpecification {

	public static Specification<TravelEntity> search(String searchParam) {

		final Logger logger = LoggerFactory.getLogger(TravelSpecification.class);

		return new Specification<TravelEntity>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TravelEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				Predicate finalPredicate = criteriaBuilder.conjunction();

				Integer bookingId = null;
				Integer userId = null;
				String username = null;
				String paymentStatus = null;
//				String status = null;
				String fromDate = null;
				String toDate = null;

				try {
					if (StringUtils.hasLength(searchParam)) {
						JSONObject searchParamJson = new ObjectMapper().readValue(searchParam, JSONObject.class);

						bookingId = searchParamJson.get("bookingId") != null
								? Integer.parseInt(searchParamJson.get("bookingId").toString())
								: null;
						userId = searchParamJson.get("userId") != null
								? Integer.parseInt(searchParamJson.get("userId").toString())
								: null; 
						username = (String) searchParamJson.get("username");
						paymentStatus = (String) searchParamJson.get("paymentStatus");
//						status = (String) searchParamJson.get("status");
						fromDate = (String) searchParamJson.get("fromDate");
						toDate = (String) searchParamJson.get("toDate");
					}

					if (bookingId != null) {
						Predicate bookingIdPredicate = criteriaBuilder.equal(root.get("pkId").get("bookingId"),
								bookingId);
						finalPredicate = criteriaBuilder.and(finalPredicate, bookingIdPredicate);
					}

					if (userId != null) {
						Predicate userIdPredicate = criteriaBuilder.equal(root.get("pkId").get("userId"), userId);
						finalPredicate = criteriaBuilder.and(finalPredicate, userIdPredicate);
					}

					if (StringUtils.hasLength(username)) {
						Predicate usernamePredicate = criteriaBuilder.like(root.get("pkId").get("username"), "%" + username + "%");
						finalPredicate = criteriaBuilder.and(finalPredicate, usernamePredicate);
					}

					if (StringUtils.hasLength(paymentStatus)) {
						Predicate paymentStatusPredicate = criteriaBuilder.equal(root.get("paymentStatus"),
								paymentStatus);
						finalPredicate = criteriaBuilder.and(finalPredicate, paymentStatusPredicate);
					}

//					if (StringUtils.hasLength(status)) {
//						Predicate statusPredicate = criteriaBuilder.like(root.get("status"), "%" + status + "%");
//						finalPredicate = criteriaBuilder.and(finalPredicate, statusPredicate);
//					}

					if (StringUtils.hasLength(fromDate) && StringUtils.hasLength(toDate)) {
						LocalDate start = LocalDate.parse(fromDate);
						LocalDate end = LocalDate.parse(toDate);
						Predicate datePredicate = criteriaBuilder.between(root.get("bookingDate"), start, end);
						finalPredicate = criteriaBuilder.and(finalPredicate, datePredicate);

					} else if (StringUtils.hasLength(fromDate)) {
						LocalDate start = LocalDate.parse(fromDate);
						Predicate datePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("bookingDate"), start);
						finalPredicate = criteriaBuilder.and(finalPredicate, datePredicate);

					} else if (StringUtils.hasLength(toDate)) {
						LocalDate end = LocalDate.parse(toDate);
						Predicate datePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("bookingDate"), end);
						finalPredicate = criteriaBuilder.and(finalPredicate, datePredicate);
					}

					query.orderBy(criteriaBuilder.asc(root.get("status")));

				} catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}
				return finalPredicate;
			}
		};
	}

}

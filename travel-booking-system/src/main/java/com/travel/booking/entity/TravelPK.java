package com.travel.booking.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TravelPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookingId;
	private int userId;
	private String username;
}

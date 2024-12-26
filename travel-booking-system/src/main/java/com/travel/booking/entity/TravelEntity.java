package com.travel.booking.entity;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRAVEL_DATA")

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TravelEntity {

	@EmbeddedId
	private TravelPK pkId;
	
	private LocalDate bookingDate;
	private String destination;
	private String paymentStatus;
	private double totalAmount;
	private String phoneNumber;
	private String emailId;
	private String status;
}

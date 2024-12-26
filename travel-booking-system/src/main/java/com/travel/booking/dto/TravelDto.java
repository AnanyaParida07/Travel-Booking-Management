package com.travel.booking.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TravelDto {

	@NotNull(message = "booking id cannot be null")
	@Min(value = 1000, message = "booking id cannot be less than 4 digits")
	@Max(value = 999999, message = "booking id cannot be more than 6 digits")
	private int bookingId;
	
	@NotNull(message = "user id cannot be null")
	@Min(value = 1000, message = "user id cannot be less than 4 digits")
	@Max(value = 999999, message = "user id cannot be more than 6 digits")
	private int userId;
	
	@NotBlank(message = "username is required")
	@Size(max = 50, message = "username cannot exceed 50 characters")
	private String username;
	
	@NotNull(message = "select date")
	private LocalDate bookingDate;
	
	@NotBlank(message = "destination name is required")
	@Size(max = 50, message = "destination cannot exceed 50 characters")
	private String destination;
	
	@NotNull(message = "select payment status")
	private String paymentStatus;
	
	@NotNull(message = "amount cannot be null")
	@Min(value = 1000, message = "enter a valid amount")
	@Max(value = 9999999, message = "enter a valid amount")
	private double totalAmount;

	@NotNull(message = "Phone number cannot be null.")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
	private String phoneNumber;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "enter a valid Email")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email format is invalid")
	private String emailId;
	
	private String status;

}

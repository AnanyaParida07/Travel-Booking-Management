package com.travel.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYMENT_DATA")

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentEntity {

	@Id
	private int id;
	private String name;

}

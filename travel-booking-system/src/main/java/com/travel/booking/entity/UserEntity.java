package com.travel.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")

@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	@Id
	private String userName;

}

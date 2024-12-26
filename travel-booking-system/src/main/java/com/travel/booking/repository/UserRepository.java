package com.travel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.booking.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, String> {

}

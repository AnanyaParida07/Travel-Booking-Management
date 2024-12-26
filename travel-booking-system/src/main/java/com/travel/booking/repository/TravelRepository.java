package com.travel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.travel.booking.entity.TravelEntity;
import com.travel.booking.entity.TravelPK;

public interface TravelRepository extends JpaRepository<TravelEntity, TravelPK> ,JpaSpecificationExecutor<TravelEntity>{

} 

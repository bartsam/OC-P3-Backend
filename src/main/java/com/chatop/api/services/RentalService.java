package com.chatop.api.services;

import java.util.List;

import com.chatop.api.dto.RentalCreateRequestDto;
import com.chatop.api.dto.RentalDetailResponseDto;
import com.chatop.api.dto.RentalListResponseDto;
import com.chatop.api.dto.RentalUpdateRequestDto;

public interface RentalService {

    List<RentalListResponseDto> findAllRentals();

    RentalDetailResponseDto findRentalById(Integer rentalId);

    void createRental(RentalCreateRequestDto dto, Integer ownerId);

    void updateRental(Integer rentalId, RentalUpdateRequestDto dto, Integer ownerId);

}
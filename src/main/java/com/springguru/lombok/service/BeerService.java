package com.springguru.lombok.service;

import com.springguru.lombok.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}

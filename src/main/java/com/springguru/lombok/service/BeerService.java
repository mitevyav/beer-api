package com.springguru.lombok.service;

import com.springguru.lombok.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeer(UUID id, Beer beer);

    void deleteById(UUID id);

    void patchById(UUID id, Beer beer);
}

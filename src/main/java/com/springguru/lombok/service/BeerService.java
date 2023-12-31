package com.springguru.lombok.service;

import com.springguru.lombok.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer);

    Boolean deleteById(UUID id);

    Optional<BeerDTO> patchById(UUID id, BeerDTO beer);
}

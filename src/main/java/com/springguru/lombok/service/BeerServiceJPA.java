package com.springguru.lombok.service;

import com.springguru.lombok.mappers.BeerMapper;
import com.springguru.lombok.model.BeerDTO;
import com.springguru.lombok.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDTO).toList();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        var savedBeer = beerRepository.save(beerMapper.beerDTOToBeer(beer));
        return beerMapper.beerToBeerDTO(savedBeer);
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beer) {
        beerRepository.findById(id).ifPresent(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            beerRepository.save(foundBeer);
        });
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchById(UUID id, BeerDTO beer) {

    }
}

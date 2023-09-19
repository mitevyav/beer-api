package com.springguru.lombok.service;

import com.springguru.lombok.entities.Beer;
import com.springguru.lombok.mappers.BeerMapper;
import com.springguru.lombok.model.BeerDTO;
import com.springguru.lombok.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {
        Optional<Beer> foundBeerOptional = beerRepository.findById(id);

        if (foundBeerOptional.isEmpty()) {
            return Optional.empty();
        }

        Beer foundBeer = foundBeerOptional.get();

        foundBeer.setBeerName(beer.getBeerName());
        foundBeer.setBeerStyle(beer.getBeerStyle());
        foundBeer.setUpc(beer.getUpc());
        foundBeer.setPrice(beer.getPrice());
        foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

        var saved = beerRepository.save(foundBeer);
        return Optional.of(beerMapper.beerToBeerDTO(saved));
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchById(UUID id, BeerDTO beer) {
        Optional<Beer> existingOptional = beerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return Optional.empty();
        }

        Beer existing = existingOptional.get();

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }

        var saved = beerRepository.save(existing);
        return Optional.of(beerMapper.beerToBeerDTO(saved));
    }
}

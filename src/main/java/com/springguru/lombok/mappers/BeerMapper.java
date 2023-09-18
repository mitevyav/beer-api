package com.springguru.lombok.mappers;

import com.springguru.lombok.entities.Beer;
import com.springguru.lombok.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}

package com.springguru.lombok.controller;

import com.springguru.lombok.model.Beer;
import com.springguru.lombok.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerById(UUID id) {
        log.debug("Get beer by id in controller was called");
        return beerService.getBeerById(id);
    }
}

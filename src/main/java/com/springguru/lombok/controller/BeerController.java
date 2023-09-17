package com.springguru.lombok.controller;

import com.springguru.lombok.model.Beer;
import com.springguru.lombok.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.springguru.lombok.controller.BeerController.BEER_PATH;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BEER_PATH)
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final BeerService beerService;

    @PatchMapping("{id}")
    public ResponseEntity patchBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        beerService.patchById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable UUID id) {
        beerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("{id}")
    public ResponseEntity updateById(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        beerService.updateBeer(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException() {
//        return ResponseEntity.notFound().build();
//    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("id") UUID id) {
        log.debug("Get beer by id in controller was called");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}

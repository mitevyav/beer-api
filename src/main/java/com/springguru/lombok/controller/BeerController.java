package com.springguru.lombok.controller;

import com.springguru.lombok.model.BeerDTO;
import com.springguru.lombok.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
    public ResponseEntity patchBeerById(@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        val patchedBeer = beerService.patchById(id, beer);

        if (patchedBeer.isEmpty()) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable UUID id) {
        var deleted = beerService.deleteById(id);

        if (!deleted) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("{id}")
    public ResponseEntity updateById(@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        var savedBeer = beerService.updateBeer(id, beer);

        if (savedBeer.isEmpty()) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException() {
//        return ResponseEntity.notFound().build();
//    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BeerDTO getBeerById(@PathVariable("id") UUID id) {
        log.debug("Get beer by id in controller was called");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}

package com.springguru.lombok.service;

import com.springguru.lombok.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeerCsvServiceTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();


    @Test
    void convertCSV() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");


        List<BeerCSVRecord> beerCSVRecords = beerCsvService.convertCSV(file);

        System.out.println(beerCSVRecords.size());

        assertThat(beerCSVRecords.size()).isGreaterThan(0);
    }

}
package com.example.breweries.services;

import com.example.breweries.entity.Brewery;
import com.example.breweries.repositories.BreweryRepository;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class OnAppStartupService implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  BreweryRepository breweryRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    List<Brewery> breweries = new ArrayList<>();

    try(InputStream inputStream = new ClassPathResource("breweries_us.csv").getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(inputStreamReader)) {

      String[] line = csvReader.readNext(); // gets rid of header row

      while((line = csvReader.readNext()) != null) {
        //brewery_name,type,address,website,state,state_breweries

        Brewery brewery = new Brewery();

        brewery.setName(line[0]);
        brewery.setType(line[1]);
        brewery.setAddress(line[2]);
        brewery.setState(line[4]);

        breweries.add(brewery);
      }
    } catch (Exception ex) {
      // should probably do something better than swallow it but  ¯\_(ツ)_/¯
    }

    breweryRepository.saveAll(breweries);

  }
}

package com.example.breweries.repositories;

import com.example.breweries.entity.Brewery;
import org.springframework.data.repository.CrudRepository;

public interface BreweryRepository extends CrudRepository<Brewery, Long> {
}

package com.apisuperheroes.controller;

import com.apisuperheroes.aop.TrackTime;
import com.apisuperheroes.dto.SuperheroeDTO;
import com.apisuperheroes.service.SuperheroesService;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SuperheroesController {

  @Autowired
  SuperheroesService superheroesService;

  @Autowired
  CacheManager cacheManager;

  @TrackTime
  @GetMapping("/allSuperheroes")
  public ResponseEntity<List<SuperheroeDTO>> getAllSuperheroes(){
    log.info("SuperheroesController: getAllSuperheroes");
    return new ResponseEntity<>(superheroesService.getAllSuperheroes(), HttpStatus.OK);
  }

  @TrackTime
  @GetMapping("/superheroes/byWord")
  public ResponseEntity<List<SuperheroeDTO>> getAllSuperheroesByWord(
      @RequestParam(value = "palabra", required = false, defaultValue = "") String palabra) {
    log.info("SuperheroesController: getAllSuperheroesByWord");
    return new ResponseEntity<>(
        superheroesService.getAllSuperheroesByWord(palabra), HttpStatus.OK);
  }

  @TrackTime
  @GetMapping("/superheroes/byId/{id}")
  public ResponseEntity<SuperheroeDTO> getSuperheroe(@PathVariable(value = "id") long id) {
    log.info("SuperheroesController: getSuperheroe");
    return new ResponseEntity<>(superheroesService.getSuperheroe(id), HttpStatus.OK);
  }

  @TrackTime
  @PutMapping("/superheroe/update")
  public ResponseEntity<SuperheroeDTO> updateSuperheroe(
      @RequestBody SuperheroeDTO superheroeDTO) {
    log.info("SuperheroesController: updateSuperheroe");
    return new ResponseEntity<>(superheroesService.updateSuperheroe(superheroeDTO), HttpStatus.OK);
  }

  @TrackTime
  @DeleteMapping("/superheroe/delete/{id}")
  public ResponseEntity<HttpStatus> deleteSuperheroe(
      @PathVariable long id) {
    log.info("SuperheroesController: deleteSuperheroe");
    superheroesService.deleteSuperheroe(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @TrackTime
  @DeleteMapping("/superheroes/cache")
  public ResponseEntity<HttpStatus> limpiarCacheSuperheroes() {
    log.info("Refrescando la caché de superheroes");
    Objects.requireNonNull(cacheManager.getCache("superheroes")).clear();
    Objects.requireNonNull(cacheManager.getCache("superheroe")).clear();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}

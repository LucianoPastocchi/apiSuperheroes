package com.apisuperheroes.service;

import com.apisuperheroes.dto.SuperheroeDTO;
import com.apisuperheroes.entity.Superheroe;
import com.apisuperheroes.exception.SuperHeroeNoEncontrado;
import com.apisuperheroes.repository.SuperheroesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SuperheroesService {

  @Autowired
  SuperheroesRepository superheroesRepository;

  @Cacheable("superheroes all")
  public List<SuperheroeDTO> getAllSuperheroes(){
    log.info("SuperheroesService: getAllSuperheroes");
    List<Superheroe> superHeroes = superheroesRepository.findAll();
    List<SuperheroeDTO> superheroeDTOS = new ArrayList<>();
    superHeroes.forEach(s -> superheroeDTOS.add(new SuperheroeDTO(s)));
    return superheroeDTOS;
  }

  @Cacheable("superheroes by word")
  public List<SuperheroeDTO> getAllSuperheroesByWord(String palabra) {

    log.info("SuperheroesService: getAllSuperheroesByWord");
    List<Superheroe> superHeroes = superheroesRepository
        .findByNameContaining(palabra.toLowerCase());
    List<SuperheroeDTO> superheroeDTOS = new ArrayList<>();
    superHeroes.forEach(s ->
        superheroeDTOS.add(new SuperheroeDTO(s))
    );
    return superheroeDTOS;
  }

  @Cacheable("superheroe by id")
  public SuperheroeDTO getSuperheroe(long id) {
    log.info("SuperheroesService: getSuperheroe");
    Optional<Superheroe> superheroe = superheroesRepository.findById(id);
    return superheroe
        .map(SuperheroeDTO::new)
        .orElseThrow(
            () -> new SuperHeroeNoEncontrado(
                String.format("No existe el superheroe con id: %s", id)));
  }

  @Caching(evict = {
      @CacheEvict(value="superheroe", allEntries=true),
      @CacheEvict(value="superheroes", allEntries=true)})
  public SuperheroeDTO updateSuperheroe(SuperheroeDTO superheroeDTO) {
    log.info("SuperheroesService: updateSuperheroe");
    Superheroe superheroe = superheroesRepository.save(new Superheroe(superheroeDTO));
    return new SuperheroeDTO(superheroe);
  }

  @Caching(evict = {
      @CacheEvict(value="superheroe", allEntries=true),
      @CacheEvict(value="superheroes", allEntries=true)})
  public void deleteSuperheroe(long id) {
    log.info("SuperheroesService: deleteSuperheroe");
    superheroesRepository.deleteById(id);
  }
}

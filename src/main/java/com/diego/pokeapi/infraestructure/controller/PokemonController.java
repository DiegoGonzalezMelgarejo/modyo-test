package com.diego.pokeapi.infraestructure.controller;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.handler.GetPokemonDetailHandler;
import com.diego.pokeapi.application.handler.GetPokemonsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {
    private final GetPokemonsHandler getPokemonsHandler;
    private final GetPokemonDetailHandler getPokemonDetailHandler;


    @GetMapping("/")
    public ResponseEntity<?> getPokemos(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "20") int limit) {
        return ResponseEntity.ok(getPokemonsHandler.getPokemons(offset, limit));

    }
    @GetMapping(value = "/{name}")
    public ResponseEntity<DetailPokemonDto> get(@PathVariable String name) {
        return ResponseEntity.ok(getPokemonDetailHandler.getPokemon(name));
    }
}

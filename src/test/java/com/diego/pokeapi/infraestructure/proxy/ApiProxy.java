package com.diego.pokeapi.infraestructure.proxy;

import com.diego.pokeapi.infraestructure.model.EvolutionChain;
import com.diego.pokeapi.infraestructure.model.PokemonResource;
import com.diego.pokeapi.infraestructure.model.PokemonResponse;
import com.diego.pokeapi.infraestructure.model.PokemonSpecies;
import com.diego.pokeapi.infraestructure.proxy.rest.PokeApiRest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PokeApiRestTest {

    private PokeApiRest pokeApiProxy;
    @Mock
    private RestTemplate restTemplate;
    private String pokemonUrl;

    @BeforeEach
    void setUp() {
        pokemonUrl = "http://some-url/poke/";
        pokeApiProxy = new PokeApiRest(restTemplate, pokemonUrl);
    }

    @Test
    void shouldGetPokemons() {
        String pageableUrl = pokemonUrl.concat("?offset=0&limit=20");
        given(restTemplate.getForEntity(pageableUrl, PokemonResponse.class))
                .willReturn(ResponseEntity.ok().build());
        pokeApiProxy.getPokemons(0, 20);
        then(restTemplate).should().getForEntity(pageableUrl, PokemonResponse.class);
    }

    @Test
    void shouldGetPokemon() {
        given(restTemplate.getForEntity(pokemonUrl.concat("name"), PokemonResource.class))
                .willReturn(ResponseEntity.ok().build());
        pokeApiProxy.getPokemonResource("name");
        then(restTemplate).should().getForEntity(pokemonUrl.concat("name"), PokemonResource.class);
    }

    @Test
    void shouldGetSpecies() {
        String resourceUrl = "some-url";
        given(restTemplate.getForEntity(resourceUrl, PokemonSpecies.class))
                .willReturn(ResponseEntity.ok().build());
        pokeApiProxy.getSpecies(resourceUrl);
        then(restTemplate).should().getForEntity(resourceUrl, PokemonSpecies.class);
    }

    @Test
    void shouldGetEvolutions() {
        String resourceUrl = "some-url";
        given(restTemplate.getForEntity(resourceUrl, EvolutionChain.class))
                .willReturn(ResponseEntity.ok().build());
        pokeApiProxy.getEvolutions(resourceUrl);
        then(restTemplate).should().getForEntity(resourceUrl, EvolutionChain.class);
    }



}
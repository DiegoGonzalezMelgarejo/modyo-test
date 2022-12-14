package com.diego.pokeapi.infraestructure.proxy;

import com.diego.pokeapi.infraestructure.exception.SourceApiClientException;
import com.diego.pokeapi.infraestructure.exception.SourceApiServerException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Test
    void shouldHandle4xxErrorWhenFetchingResource() {
        given(restTemplate.getForEntity(anyString(), any()))
                .willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        SourceApiClientException clientError = assertThrows(SourceApiClientException.class,
                () -> pokeApiProxy.getPokemonResource("name"));

        assertThat(clientError).hasMessage("Cannot resolve type 'PokemonResource' with url 'http://some-url/poke/name' due to '404 NOT_FOUND'");
        assertThat(clientError.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void shouldHandle5xxErrorWhenFetchingResource() {
        given(restTemplate.getForEntity(anyString(), any()))
                .willThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

        SourceApiServerException clientError = assertThrows(SourceApiServerException.class,
                () -> pokeApiProxy.getPokemonResource("name"));

        assertThat(clientError).hasMessage("Cannot resolve type 'PokemonResource' with url 'http://some-url/poke/name' due to '503 SERVICE_UNAVAILABLE'");
        assertThat(clientError.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
    }
    @Test
    void shouldHandleRestClientException() {
        given(restTemplate.getForEntity(anyString(), any()))
                .willThrow(new RestClientException("connection reset"));

        SourceApiServerException clientError = assertThrows(SourceApiServerException.class,
                () -> pokeApiProxy.getPokemonResource("name"));

        assertThat(clientError).hasMessage("Cannot resolve type 'PokemonResource' with url 'http://some-url/poke/name' due to 'connection reset'");
        assertThat(clientError.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
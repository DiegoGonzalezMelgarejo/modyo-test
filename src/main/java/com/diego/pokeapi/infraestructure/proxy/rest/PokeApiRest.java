package com.diego.pokeapi.infraestructure.proxy.rest;

import com.diego.pokeapi.domain.port.PokemonPort;
import com.diego.pokeapi.infraestructure.exception.SourceApiClientException;
import com.diego.pokeapi.infraestructure.exception.SourceApiServerException;
import com.diego.pokeapi.infraestructure.model.EvolutionChain;
import com.diego.pokeapi.infraestructure.model.PokemonResource;
import com.diego.pokeapi.infraestructure.model.PokemonResponse;
import com.diego.pokeapi.infraestructure.model.PokemonSpecies;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component

public class PokeApiRest {

    private final RestTemplate restTemplate;
    private final String pokemonUrl;

    public PokeApiRest(RestTemplate restTemplate,
                       @Value("${pokeapi.pokemon.url}") String pokemonUrl) {
        this.restTemplate = restTemplate;
        this.pokemonUrl = pokemonUrl;
    }

    public PokemonResponse getPokemons(long offset, long limit) {
        String pageableUrl = UriComponentsBuilder.fromHttpUrl(pokemonUrl)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .build().toUriString();
        log.info("gettingPokemons, url={}", pageableUrl);
        return fetchResource(pageableUrl, PokemonResponse.class);
    }

    @Cacheable("pokemon-resource")
    public PokemonResource getPokemonResource(String name) {
        String resourceUrl = pokemonUrl.concat(name);
        log.info("gettingPokemonResource, name={}, url={}", name, resourceUrl);
        return fetchResource(resourceUrl, PokemonResource.class);
    }

    @Cacheable("pokemon-species")
    public PokemonSpecies getSpecies(String url) {
        log.info("gettingPokemonSpeciesByUrl, url={}", url);
        return fetchResource(url, PokemonSpecies.class);
    }

    @Cacheable("evolution-chain")
    public EvolutionChain getEvolutions(String url) {
        log.info("gettingEvolutionChainByUrl, url={}", url);
        return fetchResource(url, EvolutionChain.class);
    }


    private <T> T fetchResource(String resourceUrl, Class<T> responseType) {
        try {
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(resourceUrl, responseType);
            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            log.error("cannotGetResource, url={}, statusCode={}, cause={}", resourceUrl, e.getStatusCode(),
                    e.getMostSpecificCause().getMessage());
            String message = buildErrorMessage(responseType, resourceUrl, e.getMessage());
            throw new SourceApiClientException(message, e.getStatusCode(), e);
        } catch (HttpServerErrorException e) {
            log.error("cannotGetResource, url={}, statusCode={}, cause={}", resourceUrl, e.getStatusCode(),
                    e.getMostSpecificCause().getMessage());
            String message = buildErrorMessage(responseType, resourceUrl, e.getMessage());
            throw new SourceApiServerException(message, e.getStatusCode(), e);
        } catch (RestClientException e) {
            log.error("cannotGetResource, url={}, cause={}", resourceUrl, e.getMostSpecificCause().getMessage());
            String message = buildErrorMessage(responseType, resourceUrl, e.getMessage());
            throw new SourceApiServerException(message, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private <T> String buildErrorMessage(Class<T> resourceType, String url, String error) {
        return String.format("Cannot resolve type '%s' with url '%s' due to '%s'",
                resourceType.getSimpleName(), url, error);
    }
}

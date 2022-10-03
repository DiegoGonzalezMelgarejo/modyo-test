package com.diego.pokeapi.infraestructure.adapter;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemoDto;
import com.diego.pokeapi.domain.port.PokemonPort;
import com.diego.pokeapi.infraestructure.model.EvolutionChain;
import com.diego.pokeapi.infraestructure.model.PokemonResource;
import com.diego.pokeapi.infraestructure.model.PokemonResponse;
import com.diego.pokeapi.infraestructure.model.PokemonSpecies;
import com.diego.pokeapi.infraestructure.proxy.rest.PokeApiRest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component

public class PokemonAdapter implements PokemonPort {
    private PokeApiRest pokeApiRest;

    public PokemonAdapter(PokeApiRest pokeApiRest) {
        this.pokeApiRest = pokeApiRest;
    }

    @Override
    public List<PokemoDto> getPokemons(int offset, int limit) {
        PokemonResponse pokemonResources = pokeApiRest.getPokemons(offset, limit);
        return pokemonResources.getResults().parallelStream()
                .map(resource -> pokeApiRest.getPokemonResource(resource.getName()))
                .map(PokemonResource::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public DetailPokemonDto getPokemon(String name) {
        PokemonResource pokemonResource = pokeApiRest.getPokemonResource(name);
        PokemonSpecies species = getSpecies(pokemonResource);
        List<String> evolutions = getEvolutions(species);
        String description = species.getDescription("en");
        return pokemonResource.toDomain(description, evolutions);
    }
    private PokemonSpecies getSpecies(PokemonResource pokemonResource) {
        String speciesUrl = pokemonResource.getSpecies().getUrl();
        return pokeApiRest.getSpecies(speciesUrl);
    }
    private List<String> getEvolutions(PokemonSpecies species) {
        String evolutionChainUrl = species.getEvolutionChain().getUrl();
        EvolutionChain evolutionChain = pokeApiRest.getEvolutions(evolutionChainUrl);
        return evolutionChain.getEvolutions();
    }
}

package com.diego.pokeapi.infraestructure;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemoDto;
import com.diego.pokeapi.infraestructure.adapter.PokemonAdapter;
import com.diego.pokeapi.infraestructure.model.ChainLink;
import com.diego.pokeapi.infraestructure.model.EvolutionChain;
import com.diego.pokeapi.infraestructure.model.FlavorText;
import com.diego.pokeapi.infraestructure.model.NamedResource;
import com.diego.pokeapi.infraestructure.model.PokemonAbility;
import com.diego.pokeapi.infraestructure.model.PokemonResource;
import com.diego.pokeapi.infraestructure.model.PokemonResponse;
import com.diego.pokeapi.infraestructure.model.PokemonSpecies;
import com.diego.pokeapi.infraestructure.model.PokemonSprites;
import com.diego.pokeapi.infraestructure.model.PokemonType;
import com.diego.pokeapi.infraestructure.proxy.rest.PokeApiRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
public class PokemonAdapterTest {
    @Mock
    private PokeApiRest pokeApiProxy;

    @InjectMocks
    private PokemonAdapter pokeApiAdapter;

    @Test
    void shouldGetAllPokemons() {

        String aPokemonName = "abc";
        String otherPokemonName = "xyz";

        PokemonResponse pokemonResponse = getPokemonResponse(aPokemonName, otherPokemonName);
        given(pokeApiProxy.getPokemons(0, 20))
                .willReturn(pokemonResponse);

        given(pokeApiProxy.getPokemonResource(aPokemonName))
                .willReturn(getPokemonResource());
        given(pokeApiProxy.getPokemonResource(otherPokemonName))
                .willReturn(getPokemonResource());

        List<PokemoDto> all = pokeApiAdapter.getPokemons(0, 20);

        assertThat(all).hasSize(2);

        then(pokeApiProxy).should(times(2)).getPokemonResource(anyString());
    }


    private EvolutionChain getEvolutionChain() {
        ChainLink evolvesTo = ChainLink.builder()
                .species(getNamedResource("another-specie"))
                .evolvesTo(Collections.emptyList())
                .build();
        ChainLink chainLink = ChainLink.builder()
                .species(getNamedResource("some-specie"))
                .evolvesTo(Collections.singletonList(evolvesTo))
                .build();
        return EvolutionChain.builder()
                .chain(chainLink)
                .build();
    }

    private PokemonSpecies getSpecies() {
        FlavorText flavorText = FlavorText.builder()
                .description("some flavor description")
                .language(getNamedResource("en"))
                .build();
        return PokemonSpecies.builder()
                .evolutionChain(getNamedResource("", "evolution-chain-url"))
                .flavorText(Collections.singletonList(flavorText))
                .build();
    }

    private PokemonResource getPokemonResource() {
        PokemonAbility pokemonAbility = PokemonAbility.builder()
                .ability(getNamedResource("some-ability"))
                .build();
        PokemonType pokemonType = PokemonType.builder()
                .type(getNamedResource("some-type"))
                .build();
        PokemonSprites pokemonSprites = PokemonSprites.builder()
                .frontDefault("thumbnail").frontDetail("high-quality")
                .build();
        return PokemonResource.builder()
                .id(1L)
                .name("some-name")
                .weight(100)
                .abilities(Collections.singletonList(pokemonAbility))
                .types(Collections.singletonList(pokemonType))
                .species(getNamedResource("some-specie", "species-url"))
                .sprites(pokemonSprites)
                .build();
    }

    private PokemonResponse getPokemonResponse(String firstName, String secondName) {
        List<NamedResource> results =
                Arrays.asList(getNamedResource(firstName), getNamedResource(secondName));
        return PokemonResponse.builder()
                .count(2)
                .next("some-next-link")
                .previous("some-previous-link")
                .results(results)
                .build();
    }

    private NamedResource getNamedResource(String name) {
        return getNamedResource(name, "some-url");
    }

    private NamedResource getNamedResource(String name, String url) {
        return NamedResource.builder()
                .name(name).url(url)
                .build();
    }

}

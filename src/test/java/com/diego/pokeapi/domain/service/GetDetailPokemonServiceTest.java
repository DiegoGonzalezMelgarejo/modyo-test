package com.diego.pokeapi.domain.service;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemoDto;
import com.diego.pokeapi.domain.port.PokemonPort;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class GetDetailPokemonServiceTest {
    @Mock
    private PokemonPort pokemonPort;

    @Test
    void shouldGetPokemons() {
        when(pokemonPort.getPokemon(anyString())).thenReturn(DetailPokemonDto.builder().description("d").build());
        GetDetailPokemonService getPokemonsService=new GetDetailPokemonService(this.pokemonPort);
        DetailPokemonDto result=getPokemonsService.getPokemon("d");
        Assert.assertEquals(result.getDescription(),"d");
    }
}

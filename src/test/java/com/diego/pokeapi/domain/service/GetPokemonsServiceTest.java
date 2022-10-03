package com.diego.pokeapi.domain.service;

import com.diego.pokeapi.application.dto.PokemonDto;
import com.diego.pokeapi.domain.port.PokemonPort;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPokemonsServiceTest {
    @Mock
    private PokemonPort pokemonPort;

    @Test
    void shouldGetPokemons() {
        when(pokemonPort.getPokemons(anyInt(),anyInt())).thenReturn(Arrays.asList(PokemonDto.builder().type("s").build()));
        GetPokemonsService getPokemonsService=new GetPokemonsService(this.pokemonPort);
        List<PokemonDto> result=getPokemonsService.getPokemons(0,0);
        Assert.assertEquals(result.size(),1);
    }
}

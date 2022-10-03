package com.diego.pokeapi.handler;

import com.diego.pokeapi.application.dto.PokemonDto;
import com.diego.pokeapi.application.dto.ResponseListDtos;
import com.diego.pokeapi.application.handler.GetPokemonsHandler;
import com.diego.pokeapi.domain.service.GetPokemonsService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPokemonsHandlerTest {
    @Mock
    private  GetPokemonsService getPokemonsService;
    @Test
    public void  getPokemons(){
        when(getPokemonsService.getPokemons(anyInt(),anyInt())).thenReturn(Arrays.asList(PokemonDto.builder().type("s").build()));
        GetPokemonsHandler getPokemonsHandler= new GetPokemonsHandler(getPokemonsService);
        ResponseListDtos result=getPokemonsHandler.getPokemons(0,0);
        Assert.assertEquals(result.getPokemonDtos().size(),1);
    }

}

package com.diego.pokeapi.handler;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.handler.GetPokemonDetailHandler;
import com.diego.pokeapi.domain.service.GetDetailPokemonService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class GetDetailPokemonHandlerTest {
    @Mock
    private GetDetailPokemonService getPokemonsService;
    @Test
    public void  getPokemons(){
        when(getPokemonsService.getPokemon(anyString())).thenReturn(DetailPokemonDto.builder().description("d").build());
        GetPokemonDetailHandler getPokemonsHandler= new GetPokemonDetailHandler(getPokemonsService);
        DetailPokemonDto result=getPokemonsHandler.getPokemon("D");
        Assert.assertEquals(result.getDescription(),"d");
    }

}

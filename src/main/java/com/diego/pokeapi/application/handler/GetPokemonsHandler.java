package com.diego.pokeapi.application.handler;

import com.diego.pokeapi.application.dto.PokemonDto;
import com.diego.pokeapi.application.dto.ResponseListDtos;
import com.diego.pokeapi.domain.service.GetPokemonsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPokemonsHandler {

    private final GetPokemonsService getPokemonsService;

    public GetPokemonsHandler(GetPokemonsService getPokemonsService) {
        this.getPokemonsService = getPokemonsService;
    }

    public ResponseListDtos getPokemons(int offSet, int limit){
        List<PokemonDto> pokemonDtos =getPokemonsService.getPokemons(offSet,limit);
        String prev=offSet-limit<0?"":"https://diego-modyo.herokuapp.com/pokemon?offSet="+(offSet-limit)+"&limit="+limit;
        String next="https://diego-modyo.herokuapp.com/pokemon?offSet="+(offSet+limit)+"&limit="+limit;
        return ResponseListDtos.builder().pokemonDtos(pokemonDtos).next(next).prev(prev).build();
    }
}

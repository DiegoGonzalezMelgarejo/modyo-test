package com.diego.pokeapi.application.handler;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.domain.service.GetDetailPokemonService;
import com.diego.pokeapi.domain.service.GetPokemonsService;
import org.springframework.stereotype.Component;

@Component
public class GetPokemonDetailHandler {
    private final GetDetailPokemonService getDetailPokemonService;

    public GetPokemonDetailHandler(GetDetailPokemonService getDetailPokemonService) {
        this.getDetailPokemonService = getDetailPokemonService;
    }

    public DetailPokemonDto getPokemon(String name){
        return getDetailPokemonService.getPokemon(name);
    }
}

package com.diego.pokeapi.application.handler;

import com.diego.pokeapi.application.dto.PokemoDto;
import com.diego.pokeapi.application.dto.ResponseListDtos;
import com.diego.pokeapi.domain.service.GetPokemonsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPokemonsHandler {

    private final GetPokemonsService getPokemonsService;

    public GetPokemonsHandler(GetPokemonsService getPokemonsService) {
        this.getPokemonsService = getPokemonsService;
    }

    public ResponseListDtos getPokemons(int offSet, int limit){
        List<PokemoDto> pokemoDtos=getPokemonsService.getPokemons(offSet,limit);
        String prev=offSet-limit<0?"":"http://localhost:8080/pokemon/?offSet="+(offSet-limit)+"&limit="+limit;
        String next="http://localhost:8080/pokemon/?offSet="+(offSet+limit)+"&limit="+limit;
        return ResponseListDtos.builder().pokemoDtos(pokemoDtos).next(next).prev(prev).build();
    }
}

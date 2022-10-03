package com.diego.pokeapi.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseListDtos {
    List<PokemonDto> pokemonDtos;
    String prev;
    String next;
}

package com.diego.pokeapi.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseListDtos {
    List<PokemoDto> pokemoDtos;
    String prev;
    String next;
}

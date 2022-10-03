package com.diego.pokeapi.application.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data

@SuperBuilder(toBuilder = true)
public class DetailPokemonDto extends PokemonDto {
    private String description;
    private List<String> evolutions;
}

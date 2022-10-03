package com.diego.pokeapi.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data

@SuperBuilder(toBuilder = true)
public class DetailPokemonDto extends PokemoDto {
    private String description;
    private List<String> evolutions;
}

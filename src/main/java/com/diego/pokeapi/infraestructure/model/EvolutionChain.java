package com.diego.pokeapi.infraestructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvolutionChain implements Serializable {

    private ChainLink chain;

    public List<String> getEvolutions() {
        return chain.getEvolutions();
    }
}

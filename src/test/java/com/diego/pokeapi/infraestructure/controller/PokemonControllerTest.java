package com.diego.pokeapi.infraestructure.controller;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemoDto;
import com.diego.pokeapi.application.dto.ResponseListDtos;
import com.diego.pokeapi.application.handler.GetPokemonDetailHandler;
import com.diego.pokeapi.application.handler.GetPokemonsHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PokemonController.class)
public class PokemonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPokemonsHandler getPokemonsHandler;
    @MockBean
    private GetPokemonDetailHandler getPokemonDetailHandler;

    @Test
    void shouldGetPokemonByName() throws Exception {

        DetailPokemonDto bulbasaur = DetailPokemonDto.builder()
                .name("bulbasaur")
                .type("grass, poison")
                .weight(69)
                .image("some-image-url")
                .abilities(Arrays.asList("overgrow", "chlorophyll"))
                .description("Loves to eat")
                .evolutions(Arrays.asList("bulbasaur", "ivysaur", "venusaur"))
                .build();

        given(getPokemonDetailHandler.getPokemon("bulbasaur"))
                .willReturn(bulbasaur);

        ResultActions resultActions = mockMvc.perform(get("/pokemon/bulbasaur"))
                .andExpect(status().isOk());

        resultActions
                .andExpect(jsonPath("$.name").value("bulbasaur"))
                .andExpect(jsonPath("$.type").value("grass, poison"))
                .andExpect(jsonPath("$.weight").value(69))
                .andExpect(jsonPath("$.image").value("some-image-url"))
                .andExpect(jsonPath("$.abilities").exists())
                .andExpect(jsonPath("$.abilities.length()").value(2));
    }

    @Test
    void shouldGetPokemons() throws Exception {
        PokemoDto pokemon = PokemoDto.builder()
                .name("bulbasaur")
                .type("grass, poison")
                .weight(69)
                .image("some-default-image-url")
                .abilities(Arrays.asList("overgrow", "chlorophyll"))
                .build();
        given(getPokemonsHandler.getPokemons(0, 20))
                .willReturn(ResponseListDtos.builder().build());
        ResultActions resultActions = mockMvc.perform(get("/pokemon/"))
                .andExpect(status().isOk());
        resultActions.andExpect((status().isOk()));
    }
}

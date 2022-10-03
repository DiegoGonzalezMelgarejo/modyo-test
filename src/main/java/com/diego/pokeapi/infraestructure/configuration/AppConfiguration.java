package com.diego.pokeapi.infraestructure.configuration;

import com.diego.pokeapi.domain.port.PokemonPort;
import com.diego.pokeapi.domain.service.GetDetailPokemonService;
import com.diego.pokeapi.domain.service.GetPokemonsService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.config.RequestConfig;

@Configuration
public class AppConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${pokeapi.timeout.connect:5000}") Integer connectTimeout,
                                     @Value("${pokepai.timeout.connection-request:5000}") Integer connectionRequestTimeout,
                                     @Value("${pokeapi.timeout.socket:5000}") Integer socketTimeout) {
        return restTemplateBuilder
                .requestFactory(() -> getRequestFactory(connectTimeout, connectionRequestTimeout, socketTimeout))
                .build();
    }

    private ClientHttpRequestFactory getRequestFactory(Integer connectTimeout, Integer requestTimeout,
                                                       Integer socketTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public GetPokemonsService getPokemonsService(PokemonPort pokemonPort) {
        return new GetPokemonsService(pokemonPort);
    }
    @Bean
    public GetDetailPokemonService getDetailPokemonService(PokemonPort pokemonPort) {
        return new GetDetailPokemonService(pokemonPort);
    }

}

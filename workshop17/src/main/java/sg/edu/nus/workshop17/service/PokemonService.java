package sg.edu.nus.workshop17.service;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.workshop17.model.Pokemon;

@Service
public class PokemonService {
    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";

    public Optional<Pokemon> findPokemon(String name) {
        String url = UriComponentsBuilder.fromUriString(URL)
                .path(name)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        if (resp.getStatusCodeValue() >= 400)
            return Optional.empty();
        try{
            Pokemon p = Pokemon.create(resp.getBody());
            return Optional.of(p);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

package sg.edu.nus.mockAssessment2.service;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.mockAssessment2.model.Deck;

@Service
public class DoCService {
    private static final String API_URL = "https://deckofcardsapi.com/api/deck/";

    public Deck createDeck() throws IOException {
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("new/shuffle/")
                .queryParam("deck_count", 1)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        System.out.println(">>>>>> response:" + resp.getBody());
        Deck d = Deck.create(resp.getBody());
        return d;
    }

    public Deck drawCards(String deckId, Integer count) throws IOException{
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path(deckId)
                .path("/draw/")
                .queryParam("count", count)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        System.out.println(">>>>>> response:" + resp.getBody());
        Deck d = Deck.create(resp.getBody());
        return d;
    }
}

package sg.edu.nus.workshop17CurrencyConverter.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import sg.edu.nus.workshop17CurrencyConverter.model.Currency;
import sg.edu.nus.workshop17CurrencyConverter.utils.JsonUtils;

@Service
public class ConverterService {

    @Value("${CURR_CONV_API}")
    private String API_KEY;

    private static final String API_URL = "https://free.currconv.com/api/v7/";

    public List<Currency> getCurrList() throws IOException {
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("currencies")
                .queryParam("apiKey", API_KEY)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        System.out.println(">>>>>> response:" + resp.getBody());
        List<Currency> list = Currency.createList(resp.getBody());
        return list;
    }

    public double getRate(Currency fromCurr, Currency toCurr) throws IOException {
        String query = new StringBuilder().append(fromCurr.getId()).append("_").append(toCurr.getId()).toString();
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("convert")
                .queryParam("q", query)
                .queryParam("compact", "ultra")
                .queryParam("apiKey", API_KEY)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        System.out.println(">>>>>> response:" + resp.getBody());
        JsonObject o = JsonUtils.getJsonObject(resp.getBody());
        return o.getJsonNumber(query).doubleValue();
    }
}

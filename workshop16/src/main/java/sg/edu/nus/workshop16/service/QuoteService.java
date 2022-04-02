package sg.edu.nus.workshop16.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private static final String[] QUOTES = {
            "one", "two", "three", "four", "five"
    };

    private final Random rand = new SecureRandom();

    public String getQuote() {

        return QUOTES[rand.nextInt(QUOTES.length)];
    }
}

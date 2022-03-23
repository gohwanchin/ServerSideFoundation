package sg.edu.nus.workshop17.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.workshop17.model.Pokemon;
import sg.edu.nus.workshop17.service.PokemonService;

@Controller
@RequestMapping("/search")
public class PokemonController {
    @Autowired
    PokemonService pokeSvc;

    @GetMapping
    public String getPokemon(@RequestParam String pokemon_name, Model model) {
        Optional<Pokemon> p = pokeSvc.findPokemon(pokemon_name.toLowerCase());

        if (p.isEmpty()) {
            model.addAttribute("msg", "Pokemon not found!");
            return "error";
        }
        model.addAttribute("search", pokemon_name.toUpperCase());
        model.addAttribute("photos", p.get().getPhotos());
        return "searchResults";
    }
}

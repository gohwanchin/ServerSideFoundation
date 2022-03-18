package sg.edu.nus.mockAssessment2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.mockAssessment2.model.Deck;
import sg.edu.nus.mockAssessment2.service.DoCService;

@Controller
@RequestMapping("/deck")
public class DoCController {
    @Autowired
    DoCService DoCSvc;

    @PostMapping
    public String postDeck(Model model) {
        try {
            Deck d = DoCSvc.createDeck();
            model.addAttribute("deck", d);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }
        return "newDeck";
    }

    @PostMapping("/{deckId}")
    public String postDraw(@PathVariable String deckId,
            @RequestBody MultiValueMap<String, String> form, Model model) {

        int count = 0;
        Deck d;
        String draw = form.getFirst("draw");
        try {
            count = Integer.parseInt(draw);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }

        try {
            d = DoCSvc.drawCards(deckId, count);
            model.addAttribute("deck", d);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }

        if (d.getRemaining() == 0)
            return "empty";
        return "draw";
    }
}

package sg.edu.nus.workshop17CurrencyConverter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.workshop17CurrencyConverter.model.Currency;
import sg.edu.nus.workshop17CurrencyConverter.service.ConverterService;

@Controller
public class ConverterController {
    @Autowired
    private ConverterService convSvc;

    @GetMapping("/")
    public String getIndex(Model model) {
        List<Currency> list = new ArrayList<>();
        try {
            list = convSvc.getCurrList();
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }
        model.addAttribute("currList", list);
        System.out.println("лв");
        return "index";
    }

    @GetMapping("/convert")
    public String postConvert(@RequestParam int fromCurrency, @RequestParam int toCurrency, @RequestParam int amt, Model model) {
        double rate = 0.0;
        List<Currency> list = new ArrayList<>();
        try {
            list = convSvc.getCurrList();
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }
        Currency fromCurr = list.get(fromCurrency);
        Currency toCurr = list.get(toCurrency);
        // int amt = Integer.parseInt(form.getFirst("amt"));
        try {
            rate = convSvc.getRate(fromCurr, toCurr);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "error";
        }
        double result = rate * amt;

        System.out.println(result);
        model.addAttribute("fromCurr", fromCurr);
        model.addAttribute("toCurr", toCurr);
        model.addAttribute("amt", amt);
        model.addAttribute("result", result);
        return "convert";
    }
}

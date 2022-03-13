package sg.edu.nus.revision2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/number")
public class NumberController {

    @GetMapping("/{number}")
    public String getNumber(@PathVariable String number, Model model) {
        int numInt = 0;
        try {
            numInt = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            model.addAttribute("msg", number + "not a number!!");
            return "error";
        }
        if (numInt > 30 || numInt < 0) {
            model.addAttribute("msg", number + "out of range");
            return "error";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/numbers/number").append(number).append(".jpg");
        String numUrl = sb.toString();
        model.addAttribute("num", numUrl);
        return "number";
    }
}
